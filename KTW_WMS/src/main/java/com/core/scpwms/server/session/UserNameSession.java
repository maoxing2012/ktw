package com.core.scpwms.server.session;


import com.core.business.service.bean.base.BaseParamBean;
import com.core.business.web.security.UserHolder;

public class UserNameSession extends BaseParamBean{

	public String getServerGlobalParamValue() {
		return UserHolder.getUser().getName();
	}

	public String getClientGlobalParamValue() {
		return UserHolder.getUser().getName();
	}
}
