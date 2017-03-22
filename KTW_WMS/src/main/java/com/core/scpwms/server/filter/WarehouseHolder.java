package com.core.scpwms.server.filter;

import com.core.business.security.acegi.holder.SecurityContextHolder;
import com.core.scpwms.server.constant.WmsConstant;
import com.core.scpwms.server.model.warehouse.Warehouse;

public class WarehouseHolder{
	private static ThreadLocal<Warehouse> warehouses = new InheritableThreadLocal<Warehouse>();
	public static Warehouse getWarehouse(){
		
		if(SecurityContextHolder.getCurrentSession() == null){
			return warehouses.get();
		}
		
		return (Warehouse)SecurityContextHolder.getCurrentSession().getAttribute(WmsConstant.SESSION_WAREHOUSE);
	}
	
	public static void setWarehouse(Warehouse warehouse) {
		warehouses.set(warehouse);
	}
	
}
