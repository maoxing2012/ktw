package com.core.scpwms.server.domain;

public class OrderAnalyzeInfo {

	public Long orderId;
	
	public Long itemId;
	
	public OrderAnalyzeInfo(Long orderId,Long itemId){
		this.orderId = orderId;
		this.itemId = itemId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
}
