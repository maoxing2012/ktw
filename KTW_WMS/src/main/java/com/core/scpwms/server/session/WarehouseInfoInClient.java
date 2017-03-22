package com.core.scpwms.server.session;

import com.core.business.service.bean.ClientDisplayInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.util.LocalizedMessage;
import com.core.scpwms.server.filter.WarehouseHolder;

public class WarehouseInfoInClient implements ClientDisplayInfo {

	public String getAllDisplayInfo() {
		return LocalizedMessage.getLocalizedMessage("current.warehouse",
				UserHolder.getReferenceModel(),UserHolder.getLanguage())+ WarehouseHolder.getWarehouse().getName();
	}

}
