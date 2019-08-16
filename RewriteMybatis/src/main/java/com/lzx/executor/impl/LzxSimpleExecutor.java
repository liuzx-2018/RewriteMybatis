package com.lzx.executor.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lzx.bean.User;
import com.lzx.executor.LzxExecutor;
import com.lzx.utils.LzxDBConfigurationUtils;

/**
 * Copyright: Copyright (c) 2019 Create By @author: {author liuzx}
 * 
 * @version: v1.0.0
 * @date: 2019年8月15日 下午2:57:24
 *
 */
public class LzxSimpleExecutor implements LzxExecutor {

	@Override
	public <T> T query(String sql, Object parameter) {
		Connection connection = LzxDBConfigurationUtils.build("mybatis-config.xml");
		ResultSet set = null;
		PreparedStatement pre = null;
		try {
			pre = connection.prepareStatement(sql);
			// 设置参数
			pre.setString(1, parameter.toString());
			set = pre.executeQuery();
			User u = new User();
			// 遍历结果集
			while (set.next()) {
				u.setId(set.getString(1));
				u.setUsername(set.getString(2));
				u.setPassword(set.getString(3));
			}
			return (T) u;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (set != null) {
					set.close();
				}
				if (pre != null) {
					pre.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}

}