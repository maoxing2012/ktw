package com.core.scpwms.client.custom.page.support;

import java.util.HashMap;
import java.util.Map;

import com.core.scpview.client.ui.table.RowData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientProcessDocDetailInfo implements IsSerializable{
	
	public String id;
	//SKU AND SKU NAME
	public RowData rowData;
	
	//Package id and package's name
	public String pkgId;
	public String pkgName;

	//plan qty and plan package qty
	public String planPackQty;
	public String qty;
	
	public Map<String,String> lotInfo = new HashMap<String,String>();
	
    public ClientProcessDocDetailInfo(){
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RowData getRowData() {
		return rowData;
	}

	public void setRowData(RowData rowData) {
		this.rowData = rowData;
	}

	public String getPkgId() {
		return pkgId;
	}

	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public Map<String, String> getLotInfo() {
		return lotInfo;
	}

	public void setLotInfo(Map<String, String> lotInfo) {
		this.lotInfo = lotInfo;
	}

	

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getPlanPackQty() {
		return planPackQty;
	}

	public void setPlanPackQty(String planPackQty) {
		this.planPackQty = planPackQty;
	}

}
