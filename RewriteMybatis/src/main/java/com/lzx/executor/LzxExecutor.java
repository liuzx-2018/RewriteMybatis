package com.lzx.executor;

public interface LzxExecutor {
	public <T> T query(String statement, Object parameter);
}
