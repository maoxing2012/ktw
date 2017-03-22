package com.core.scpwms.client.custom.page.support;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientShippingParam implements IsSerializable{
	
	public String id;
    public String packageId;
    public String qty;
    public String binId;
    
    public Map<String,Object> lotInfos = new HashMap<String,Object>(); 
	

    public ClientShippingParam(){
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPackageId() {
		return packageId;
	}


	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}


	public String getQty() {
		return qty;
	}


	public void setQty(String qty) {
		this.qty = qty;
	}


	public String getBinId() {
		return binId;
	}


	public void setBinId(String binId) {
		this.binId = binId;
	}


	public Map<String, Object> getLotInfos() {
		return lotInfos;
	}


	public void setLotInfos(Map<String, Object> lotInfos) {
		this.lotInfos = lotInfos;
	}

}
