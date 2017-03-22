package com.core.scpwms.client.custom.page.support;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientSkuParam implements IsSerializable{
	
	public String docId;
    public String skuId;
    public String packageId;
    public String qty;
    
    public String binId;
    public String lineNo;
    public String ibdNumber;
    
    public String beginLing;
    public String endLine;
    public String ibdAll;
    
    public Map<String,Object> lotInfos = new HashMap<String,Object>(); 
	

    public ClientSkuParam(){
	}


	public String getSkuId() {
		return skuId;
	}


	public void setSkuId(String skuId) {
		this.skuId = skuId;
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


	public Map<String, Object> getLotInfos() {
		return lotInfos;
	}


	public void setLotInfos(Map<String, Object> lotInfos) {
		this.lotInfos = lotInfos;
	}


	public String getDocId() {
		return docId;
	}


	public void setDocId(String docId) {
		this.docId = docId;
	}


	public String getBinId() {
		return binId;
	}


	public void setBinId(String binId) {
		this.binId = binId;
	}


	public String getLineNo() {
		return lineNo;
	}


	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}


	public String getIbdNumber() {
		return ibdNumber;
	}


	public void setIbdNumber(String ibdNumber) {
		this.ibdNumber = ibdNumber;
	}


	public String getBeginLing() {
		return beginLing;
	}


	public void setBeginLing(String beginLing) {
		this.beginLing = beginLing;
	}


	public String getEndLine() {
		return endLine;
	}


	public void setEndLine(String endLine) {
		this.endLine = endLine;
	}


	public String getIbdAll() {
		return ibdAll;
	}


	public void setIbdAll(String ibdAll) {
		this.ibdAll = ibdAll;
	}


}
