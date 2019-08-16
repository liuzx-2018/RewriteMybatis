package com.lzx.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lzx.utils.LzxDBConfigurationUtils;

/**
 * 读取mapper配置
 * 
 * Copyright: Copyright (c) 2019 Create By @author: {author liuzx}
 * 
 * @version: v1.0.0
 * @date: 2019年8月14日 下午5:49:14
 *
 */
public class LzxMapperConfiguration {

	private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	// LzxMapperRegistry mapperRegistry = new LzxMapperRegistry();

	/** 通过Ｄｏｍ４ｊ读取配置文件信息 */
	public void loadConfigurations(String resource) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = classLoader.getResourceAsStream(resource);
			Document document = new SAXReader().read(inputStream);
			Element root = document.getRootElement();
			List<Element> mappers = root.element("mappers").elements("mapper");
			for (Element mapper : mappers) {
				if (mapper.attribute("resource") != null) {
					// mapperRegistry.setKnownMappers(loadMapperXMLConfiguration(mapper.attribute("resource").getText()));
					readMapper(mapper.attribute("resource").getText());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("读取配置文件错误!");
		} finally {
			inputStream.close();
		}
	}

	/*** 通过ｄｏｍ４ｊ读取Ｍａｐｐｅｒ．ｘｍｌ中的信息 */

	@SuppressWarnings("rawtypes")
	public LzxMapperBean readMapper(String path) {
		LzxMapperBean mapper = new LzxMapperBean();
		try {
			InputStream stream = classLoader.getResourceAsStream(path);
			SAXReader reader = new SAXReader();
			Document document = reader.read(stream);
			Element root = document.getRootElement();
			mapper.setInterfaceName(root.attributeValue("namespace").trim()); // 把mapper节点的nameSpace值存为接口名
			List<LzxFunctionBean> list = new ArrayList<LzxFunctionBean>(); // 用来存储方法的List
			for (Iterator rootIter = root.elementIterator(); rootIter.hasNext();) {// 遍历根节点下所有子节点
				LzxFunctionBean fun = new LzxFunctionBean(); // 用来存储一条方法的信息
				Element e = (Element) rootIter.next();
				String sqltype = e.getName().trim();
				String funcName = e.attributeValue("id").trim();
				String sql = e.getText().trim();
				String resultType = e.attributeValue("resultType").trim();
				fun.setSqltype(sqltype);
				fun.setFuncName(funcName);
				Object newInstance = null;
				try {
					newInstance = Class.forName(resultType).newInstance();
				} catch (InstantiationException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				fun.setResultType(newInstance);
				fun.setSql(sql);
				list.add(fun);
			}
			mapper.setList(list);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return mapper;
	}

	public static void main(String[] args) {
		try {
			Element element = LzxDBConfigurationUtils.getElementByDom4j("mybatis/UserMapper.xml");
			System.out.println(element.attributeValue("namespace"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
