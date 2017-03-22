package com.core.scpwms.client.custom.page.support;

import java.util.HashMap;
import java.util.Map;

import com.core.scpview.client.ui.table.RowData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientAsnDetailInfo implements IsSerializable{
	
	public String id;
	//SKU AND SKU NAME
	public RowData rowData;
	
	//Bin RowDate
		public RowData binData;
	
	//Package id and package's name
	public String pkgId;
	public String pkgName;

	//plan qty and plan package qty
	public String planPackQty;
	public String qty;
	
	//revieved and revieving
	public String revievedQty;
	public String revievingQty;
	
	public String ibdNumber;
	public String lineNo;
	
	public String skuPress;
	public String skuPrice;
	
	
	public String getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(String skuPrice) {
		this.skuPrice = skuPrice;
	}

	public String getSkuPress() {
		return skuPress;
	}

	public void setSkuPress(String skuPress) {
		this.skuPress = skuPress;
	}

	public Map<String,String> lotInfo = new HashMap<String,String>();
	
    public ClientAsnDetailInfo(){
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

	public String getRevievedQty() {
		return revievedQty;
	}

	public void setRevievedQty(String revievedQty) {
		this.revievedQty = revievedQty;
	}

	public String getRevievingQty() {
		return revievingQty;
	}

	public void setRevievingQty(String revievingQty) {
		this.revievingQty = revievingQty;
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

	public String getIbdNumber() {
		return ibdNumber;
	}

	public void setIbdNumber(String ibdNumber) {
		this.ibdNumber = ibdNumber;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public RowData getBinData() {
		return binData;
	}

	public void setBinData(RowData binData) {
		this.binData = binData;
	}

    
    

}
