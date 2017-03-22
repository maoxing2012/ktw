package com.core.scpwms.server.session;


import com.core.business.service.bean.base.BaseParamBean;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.warehouse.Warehouse;

public class WarehouseSession extends BaseParamBean{

	public Warehouse getServerGlobalParamValue() {
		return WarehouseHolder.getWarehouse();
	}

	public Long getClientGlobalParamValue() {
		return WarehouseHolder.getWarehouse().getId();
	}
}
