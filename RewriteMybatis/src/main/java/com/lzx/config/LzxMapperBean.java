package com.lzx.config;

import java.util.List;

public class LzxMapperBean {

	private String interfaceName; // 接口名

	private List<LzxFunctionBean> list; // 接口下所有方法

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public List<LzxFunctionBean> getList() {
		return list;
	}

	public void setList(List<LzxFunctionBean> list) {
		this.list = list;
	}

}
