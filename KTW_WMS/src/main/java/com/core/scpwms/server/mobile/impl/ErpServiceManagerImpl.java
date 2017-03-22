package com.core.scpwms.server.mobile.impl;

import com.core.scpwms.server.mobile.ErpServiceManager;

public class ErpServiceManagerImpl implements ErpServiceManager {

	@Override
	public String helloMobile(String param) {
		return "hello," + param;
	}
}
