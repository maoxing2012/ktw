package com.core.scpwms.client.custom.page.support;

import java.util.HashMap;
import java.util.Map;

import com.core.scpview.client.ui.table.RowData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientObdDetailInfo implements IsSerializable{
	
	public String id;
	//SKU AND SKU NAME
	public RowData rowData;
	
	//Package id and package's name
	public String pkgId;
	public String pkgName;
	public String binId;

	//plan qty and plan package qty
	public String planPackQty;
	public String qty;
	
	//revieved and revieving
	public String shippedQty;
	public String shipingQty;
	
	public Map<String,String> lotInfo = new HashMap<String,String>();
	
    public ClientObdDetailInfo(){
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

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getBinId() {
		return binId;
	}

	public void setBinId(String binId) {
		this.binId = binId;
	}

	public String getPlanPackQty() {
		return planPackQty;
	}

	public void setPlanPackQty(String planPackQty) {
		this.planPackQty = planPackQty;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getShippedQty() {
		return shippedQty;
	}

	public void setShippedQty(String shippedQty) {
		this.shippedQty = shippedQty;
	}

	public String getShipingQty() {
		return shipingQty;
	}

	public void setShipingQty(String shipingQty) {
		this.shipingQty = shipingQty;
	}

	public Map<String, String> getLotInfo() {
		return lotInfo;
	}

	public void setLotInfo(Map<String, String> lotInfo) {
		this.lotInfo = lotInfo;
	}


    

}
