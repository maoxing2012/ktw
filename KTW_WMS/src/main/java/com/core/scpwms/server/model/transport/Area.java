package com.core.scpwms.server.model.transport;

import com.core.business.model.TrackingEntity;

public class Area extends TrackingEntity{
	
	private String code;
	
	private String name;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
