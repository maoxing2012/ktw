package com.core.scpwms.server.model.transport;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Warehouse;

public class Course extends TrackingEntity{
	private Warehouse wh;
	
	private Owner owner;
	
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

	public Warehouse getWh() {
		return wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
}
