package com.core.scpwms.client.custom.page.support;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientLotInfo implements IsSerializable{
	
    private String title;
    private String lotType;
    private String inputType;
    private String enuCode;
    private String id;

    public ClientLotInfo(){
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLotType() {
		return lotType;
	}

	public void setLotType(String lotType) {
		this.lotType = lotType;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getEnuCode() {
		return enuCode;
	}

	public void setEnuCode(String enuCode) {
		this.enuCode = enuCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
