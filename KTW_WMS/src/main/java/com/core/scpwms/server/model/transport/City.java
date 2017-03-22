package com.core.scpwms.server.model.transport;

import com.core.business.model.TrackingEntity;

public class City extends TrackingEntity{
	
	private Area area;
	
	private String code;
	
	private String name;
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

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
