package com.lzx;

import com.lzx.bean.User;
import com.lzx.mapper.UserMapper;
import com.lzx.session.LzxSqlSession;

public class TestMybatis {

	public static void main(String[] args) {
		LzxSqlSession sqlsession = new LzxSqlSession();
		UserMapper mapper = sqlsession.getMapper(UserMapper.class);
		User user = mapper.getUserById("1");
		System.out.println(user);
	}

}
