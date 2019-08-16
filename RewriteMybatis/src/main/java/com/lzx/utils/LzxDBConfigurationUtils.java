package com.lzx.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 通过解析xml获取数据库配置工具类
 * 
 * Copyright: Copyright (c) 2019 Create By @author: {author liuzx}
 * 
 * @version: v1.0.0
 * @date: 2019年8月15日 下午3:03:13
 *
 */
public class LzxDBConfigurationUtils {

	private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	public static Connection build(String resource) {
		try {
			Element root = getElementByDom4j(resource);
			return eval(root);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error occured while evaling xml " + resource);
		}
	}

	public static Element getElementByDom4j(String resource) throws DocumentException {
		InputStream inputStream = classLoader.getResourceAsStream(resource);
		Document document = new SAXReader().read(inputStream);
		Element root = document.getRootElement();
		return root;
	}

	/**
	 * 解析数据库配置并创建连接
	 * 
	 * @param root
	 * @return
	 * @throws ClassNotFoundException
	 */
	private static Connection eval(Element node) throws ClassNotFoundException {
		String driverClass = null;
		String url = null;
		String username = null;
		String password = null;
		if (node.element("properties") != null) {
			// 获取properties属性节点
			for (Object item : node.elements("properties")) {
				Element i = (Element) item;
				String value = getValue(i);
				String name = i.attributeValue("name");
				// 赋值
				switch (name) {
				case "driverClass":
					driverClass = value;
					break;
				case "url":
					url = value;
					break;
				case "username":
					username = value;
					break;
				case "password":
					password = value;
					break;
				default:
					throw new RuntimeException("<property> unknown name");
				}
			}
		} else if (node.element("environments") != null) {
			// 获取<environments>标签下节点内容
			String defaultName = node.element("environments").attributeValue("default");
			// 遍历environments节点
			for (Object item : node.element("environments").elements("environment")) {
				Element i = (Element) item;
				String name = i.attributeValue("id");
				if (defaultName.equals(name)) {
					// 获取property属性节点
					for (Object propertyItem : i.element("dataSource").elements("property")) {
						Element p = (Element) propertyItem;
						String value = getValue(p);
						String propertyName = p.attributeValue("name");
						// 赋值
						switch (propertyName) {
						case "driverClass":
							driverClass = value;
							break;
						case "url":
							url = value;
							break;
						case "username":
							username = value;
							break;
						case "password":
							password = value;
							break;
						default:
							throw new RuntimeException("<property> unknown name");
						}
					}

				}
			}
		} else {
			throw new RuntimeException(
			        "error occured while evaling xml ,node must be contains <properties></properties>"
			                + " or <environments></environments>");
		}

		// 获取environment节点

		Class.forName(driverClass);
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	// 读取property属性值，如果有value值,则读取 没有设置value,则读取内容
	private static String getValue(Element node) {
		return node.hasContent() ? node.getText() : node.attributeValue("value");
	}

	public static void main(String[] args) throws DocumentException {
		Element element = getElementByDom4j("mybatis-config.xml");
		List<Element> list = element.elements("environments");
		System.out.println(list.get(0).attributeValue("default"));
	}

}
