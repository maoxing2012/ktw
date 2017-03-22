package com.core.scpwms.server.session;

import com.core.business.service.bean.ClientDisplayInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.util.LocalizedMessage;

public class UserInfoInClient implements ClientDisplayInfo {

	public String getAllDisplayInfo() {
		return LocalizedMessage.getLocalizedMessage("current.user",
				UserHolder.getReferenceModel(),UserHolder.getLanguage())+
				UserHolder.getUser().getLoginName();
	}

}
