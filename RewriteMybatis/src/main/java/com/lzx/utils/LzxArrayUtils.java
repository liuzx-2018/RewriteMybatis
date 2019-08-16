package com.lzx.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * 用于数组、集合操作的工具类
 * 
 * Copyright: Copyright (c) 2019 Create By @author: {author liuzx}
 * 
 * @version: v1.0.0
 * @date: 2019年8月14日 下午4:35:47
 *
 */
public class LzxArrayUtils {

	/**
	 * 检查集合是否存在元素
	 * 
	 * @param collection
	 * @return
	 */
	public static boolean hasObject(Collection<?> collection)
	{
		return collection != null && collection.size() > 0;
	}

	/**
	 * 检查Map是否存在元素
	 * 
	 * @param map
	 * @return
	 */
	public static boolean hasObject(Map<?, ?> map)
	{
		return map != null && map.size() > 0;
	}

	/**
	 * 检查数组是否存在元素
	 * 
	 * @param array
	 * @return
	 */
	public static boolean hasObject(Object[] array)
	{
		return array != null && array.length > 0;
	}

	/**
	 * 检查数组中是否包含指定元素
	 * 
	 * @param array
	 *            数组
	 * @param element
	 *            指定元素
	 * @return
	 */
	public static boolean contains(Object[] array, Object element) {
		boolean flag = Boolean.FALSE;
		if (LzxArrayUtils.hasObject(array)) {
			flag = Arrays.asList(array).contains(element);
		}
		return flag;
	}

}
