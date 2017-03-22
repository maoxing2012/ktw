package com.core.scpwms.server.domain;

public class SkuAnalyzeInfo {

	public Long skuId;
	
	public Long itemCnt;
	
	public SkuAnalyzeInfo(Long id,Long cnt){
		this.skuId = id;
		this.itemCnt = cnt;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getItemCnt() {
		return itemCnt;
	}

	public void setItemCnt(Long itemCnt) {
		this.itemCnt = itemCnt;
	}
	
}
