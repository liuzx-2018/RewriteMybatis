package com.lzx.session;

import java.lang.reflect.Proxy;

import com.lzx.config.LzxMapperConfiguration;
import com.lzx.executor.LzxExecutor;
import com.lzx.executor.impl.LzxSimpleExecutor;
import com.lzx.proxy.LzxMapperProxy;

public class LzxSqlSession {

	private LzxExecutor executor = new LzxSimpleExecutor();

	private LzxMapperConfiguration mapperConfiguration = new LzxMapperConfiguration();

	public <T> T getMapper(Class<T> cls) {
		// jdk动态代理
		return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[] { cls }, new LzxMapperProxy(this,
		        mapperConfiguration));
	}

	public <T> T selectOne(String sql, Object parameter) {
		return executor.query(sql, parameter);
	}

}
