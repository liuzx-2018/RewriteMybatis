package com.lzx.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import com.lzx.config.LzxFunctionBean;
import com.lzx.config.LzxMapperBean;
import com.lzx.config.LzxMapperConfiguration;
import com.lzx.session.LzxSqlSession;

/**
 * Copyright: Copyright (c) 2019 Create By @author: {author liuzx}
 * 
 * @version: v1.0.0
 * @date: 2019年8月15日 上午10:36:24
 *
 */
public class LzxMapperProxy implements InvocationHandler {

	private LzxSqlSession sqlSession = new LzxSqlSession();
	private LzxMapperConfiguration mapperConfiguration = new LzxMapperConfiguration();

	public LzxMapperProxy(LzxSqlSession sqlSession, LzxMapperConfiguration mapperConfiguration) {
		super();
		this.sqlSession = sqlSession;
		this.mapperConfiguration = mapperConfiguration;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		LzxMapperBean readMapper = mapperConfiguration.readMapper("mybatis/UserMapper.xml");
		// 是否是xml文件对应的接口
		if (!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())) {
			return null;
		}
		List<LzxFunctionBean> list = readMapper.getList();
		if (null != list || 0 != list.size()) {
			for (LzxFunctionBean function : list) {
				// id是否和接口方法名一样
				if (method.getName().equals(function.getFuncName())) {
					return sqlSession.selectOne(function.getSql(), String.valueOf(args[0]));
				}
			}
		}
		return null;
	}

}