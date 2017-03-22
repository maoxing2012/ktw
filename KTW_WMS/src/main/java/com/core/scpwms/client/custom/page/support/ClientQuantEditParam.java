package com.core.scpwms.client.custom.page.support;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientQuantEditParam implements IsSerializable{

    public String selecSkuCode;
    public String selectSkuName;
    public String quantId;
    public Map<String,Object> lotInfos = new HashMap<String,Object>(); 
	

    public ClientQuantEditParam(){
	}
    
	public String getQuantId() {
		return this.quantId;
	}

	public void setQuantId(String quantId) {
		this.quantId = quantId;
	}

	public Map<String, Object> getLotInfos() {
		return lotInfos;
	}


	public void setLotInfos(Map<String, Object> lotInfos) {
		this.lotInfos = lotInfos;
	}

	public String getSelecSkuCode() {
		return selecSkuCode;
	}


	public void setSelecSkuCode(String selecSkuCode) {
		this.selecSkuCode = selecSkuCode;
	}


	public String getSelectSkuName() {
		return selectSkuName;
	}

	public void setSelectSkuName(String selectSkuName) {
		selectSkuName = selectSkuName;
	}

}
