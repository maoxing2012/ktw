package com.core.scpwms.server.mobile.bean;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class MobileRequest implements IsSerializable{
	private String pageId;
	
	private Long whId;
	
	private Long ownerId;
	
	private Long userId;
	
	//参数
	private Map<String,Object> parameters = new HashMap<String, Object>();

	public Map<String, Object> getParameters() {
		return this.parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public String getPageId() {
		return this.pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Long getWhId() {
		return this.whId;
	}

	public void setWhId(Long whId) {
		this.whId = whId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

}
