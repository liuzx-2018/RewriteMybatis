package com.lzx.utils;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串操作工具类，封装了一些诸如字符串非空转换、非空判断、时间日期和字符串之间转换等方法
 * 
 * Copyright: Copyright (c) 2019 Create By @author: {author liuzx}
 * 
 * @version: v1.0.0
 * @date: 2019年8月14日 下午4:32:17
 *
 */
public class LzxStringUtlis {

	/**
	 * 默认的日期格式转换对象
	 */
	private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 读取Clob字段
	 * 
	 * @param clob
	 * @return
	 * @throws Exception
	 */
	public static String clobToString(Clob clob) throws Exception
	{
		StringBuffer buffer = null;
		if (clob != null)
		{
			buffer = new StringBuffer();
			Reader reader = null;
			try
			{
				reader = clob.getCharacterStream();
				char[] bytes = new char[1024];
				int i = 0;
				while ((i = reader.read(bytes)) != -1)
				{
					buffer.append(bytes, 0, i);
				}
			} finally
			{
				if (reader != null)
				{
					reader.close();
				}
			}
		}
		return buffer == null ? null : buffer.toString();
	}

	/**
	 * 检测字符串是否为空
	 * 
	 * @param source
	 *            字符串
	 * @param trim
	 *            是否需要去除首尾空格
	 * @return
	 */
	public static boolean hasLength(String source, boolean trim)
	{
		boolean flag = Boolean.FALSE;
		if (source != null)
		{
			if (trim)
			{
				source = source.trim();
			}
			flag = source.length() > 0;
		}
		return flag;
	}

	/**
	 * 检测字符串是否为空
	 * 
	 * @param source
	 *            字符串
	 * @return
	 */
	public static boolean hasLength(String source)
	{
		return hasLength(source, Boolean.TRUE);
	}

	/**
	 * 将Date转换为字符串
	 * 
	 * @param date
	 *            需要转换的Date对象
	 * @param formatString
	 *            时间展示格式
	 * @return 时间日期字符串
	 * @throws Exception
	 */
	public static String dateToString(Date date, String formatString) throws Exception
	{
		SimpleDateFormat format = null;
		if (!hasLength(formatString))
		{
			format = DEFAULT_DATE_FORMAT;
		}
		else
		{
			format = new SimpleDateFormat(formatString);
		}
		return format.format(date);
	}

	/**
	 * 将Date转换为字符串(默认使用“yyyy-MM-dd HH:mm:ss”的格式展示时间)
	 * 
	 * @param date
	 *            需转换的日期
	 * @return 转换后的时间日期字符串
	 * @throws Exception
	 */
	public static String dateToString(Date date) throws Exception
	{
		return dateToString(date, null);
	}

	/**
	 * 将Timestamp对象转换成字符串
	 * 
	 * @param timestamp
	 *            需Timestamp对象
	 * @param formatString
	 *            时间展示格式
	 * @return 转换后的时间日期字符串
	 * @throws Exception
	 */
	public static String timestampToString(Timestamp timestamp, String formatString) throws Exception
	{
		Date date = new Date(timestamp.getTime());
		return dateToString(date, formatString);
	}

	/**
	 * 将Timestamp对象转换成字符串(默认使用“yyyy-MM-dd HH:mm:ss”的格式展示时间)
	 * 
	 * @param timestamp
	 * @return 转换后的时间日期字符串
	 * @throws Exception
	 */
	public static String timestampToString(Timestamp timestamp) throws Exception
	{
		return timestampToString(timestamp, null);
	}

	/**
	 * 如果字符串为空则将其转换为指定值
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @param replacement
	 *            如果字符串为空则返回replacement指定的值
	 * @return 转换后的字符串
	 */
	public static String parseNull(String str, String replacement)
	{
		if (!hasLength(str, Boolean.TRUE)) {
			str = replacement;
		}
		return str;
	}

	/**
	 * 如果字符串为空则将其转换为空串
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String parseNull(String str) {
		return parseNull(str, "");
	}

	/**
	 * 使用指定的字符填充原字符串,直至字符串达到指定的长度
	 * 
	 * @param source
	 *            需要被补长的字符串
	 * @param len
	 *            补长之后的长度
	 * @param fill
	 *            用于补长的字符
	 * @param append
	 *            是否在原字符串末尾追加,true:在末尾追加,false:在字符串开始的地方插入
	 * @return
	 */
	public static String fillChar(String source, int len, char fill, boolean append) {
		if (!LzxStringUtlis.hasLength(source) || source.length() < len) {
			StringBuilder builder = new StringBuilder(source);
			for (int i = 0; i < len - source.length(); i++) {
				if (append) {
					builder.append(fill);
				} else {
					builder.insert(0, fill);
				}
			}
			source = builder.toString();
		}
		return source;
	}

	/**
	 * 查找指定字符再字符串中出现的索引位置
	 * 
	 * @param str
	 *            被查找的字符串
	 * @param s
	 *            要查找的字符串
	 * @param index
	 *            第几次出现的位置
	 * @return 返回要查找字符的索引
	 */
	public static int indexStrOf(String str, String s, int index) {

		int strIndex = 0;
		int firstIndex = 0;

		for (int i = 1; i <= index; i++) {

			firstIndex = str.indexOf(s, firstIndex + 1);
			if (firstIndex == -1) {
				strIndex = str.length();
				break;
			}
			if (i == index) {
				strIndex = firstIndex;
				break;
			}
		}

		return strIndex;
	}

	/**
	 * 截取参数字符 串
	 * 
	 * @param object
	 *            方法参数
	 * @return
	 */
	public static String splitParams(Object object)
	{
		if (object == null)
		{
			return null;
		}
		String str = object.toString();
		if (str.length() > 50)
		{
			str = str.substring(0, 20) + "..." + str.substring(str.length() - 10, str.length());
		}
		return str;
	}

}