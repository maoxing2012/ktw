package com.core.scpwms.client.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WarehouseInfo implements IsSerializable {
	
	private String whId;
	private String whCode;
	private String whName;
	
	public String getWhId() {
		return whId;
	}
	public void setWhId(String whId) {
		this.whId = whId;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getWhName() {
		return whName;
	}
	public void setWhName(String whName) {
		this.whName = whName;
	}
	
	
	
	
}
