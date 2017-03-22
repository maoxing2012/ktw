package com.core.scpwms.server.model.common;

import com.core.business.model.TrackingEntity;

/**
 * 目前暂时匹配非库存单据类型，后期在进行扩展
 *
 * 
 *
 */
@SuppressWarnings("all") 
public class StockType extends TrackingEntity {

	
	/** WMS实际单据类型*/
	private String wmsType;
	
	/** JDE非库存单据类型*/
	private String jdeType;
	
	public String getWmsType() {
		return wmsType;
	}

	public void setWmsType(String wmsType) {
		this.wmsType = wmsType;
	}

	public String getJdeType() {
		return jdeType;
	}

	public void setJdeType(String jdeType) {
		this.jdeType = jdeType;
	}


    
}
