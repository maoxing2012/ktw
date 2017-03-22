package com.core.scpwms.client.custom.page.support;

import java.util.HashMap;
import java.util.Map;

import com.core.scpview.client.ui.table.RowData;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 
 * @description   画面移位计划明细信息
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-2-20
 * @version        V1.0<br/>
 */
public class ClientMovePlanDetailInfo implements IsSerializable{
	
	/**
	 * 明细ID
	 */
	public String mpDetailId;
	
	public RowData rowData;
	
	public String plantName;
	public String srcBinCd;
	
	public String containerSeq;
	public String status;
	
	public String skuCode;
	public String skuName;
	
	public String pkgName;
	public double planQty;
	public double pkgQty;
	public double executeQty;
	public double allocateQty;
	public String lotSequence;
	public String dispLot;
	public String descBinCd;
	
	public Map<String,String> lotInfo = new HashMap<String,String>();
	
	public ClientMovePlanDetailInfo(){
		
	}

	public String getMpDetailId() {
		return this.mpDetailId;
	}

	public void setMpDetailId(String mpDetailId) {
		this.mpDetailId = mpDetailId;
	}

	public RowData getRowData() {
		return this.rowData;
	}

	public void setRowData(RowData rowData) {
		this.rowData = rowData;
	}

	public String getPlantName() {
		return this.plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getSrcBinCd() {
		return this.srcBinCd;
	}

	public void setSrcBinCd(String srcBinCd) {
		this.srcBinCd = srcBinCd;
	}

	public String getContainerSeq() {
		return this.containerSeq;
	}

	public void setContainerSeq(String containerSeq) {
		this.containerSeq = containerSeq;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSkuCode() {
		return this.skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return this.skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getPkgName() {
		return this.pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public double getPlanQty() {
		return this.planQty;
	}

	public void setPlanQty(double planQty) {
		this.planQty = planQty;
	}

	public double getPkgQty() {
		return this.pkgQty;
	}

	public void setPkgQty(double pkgQty) {
		this.pkgQty = pkgQty;
	}

	public double getExecuteQty() {
		return this.executeQty;
	}

	public void setExecuteQty(double executeQty) {
		this.executeQty = executeQty;
	}

	public double getAllocateQty() {
		return this.allocateQty;
	}

	public void setAllocateQty(double allocateQty) {
		this.allocateQty = allocateQty;
	}

	public String getLotSequence() {
		return this.lotSequence;
	}

	public void setLotSequence(String lotSequence) {
		this.lotSequence = lotSequence;
	}

	public String getDispLot() {
		return this.dispLot;
	}

	public void setDispLot(String dispLot) {
		this.dispLot = dispLot;
	}

	public String getDescBinCd() {
		return this.descBinCd;
	}

	public void setDescBinCd(String descBinCd) {
		this.descBinCd = descBinCd;
	}

	public Map<String, String> getLotInfo() {
		return this.lotInfo;
	}

	public void setLotInfo(Map<String, String> lotInfo) {
		this.lotInfo = lotInfo;
	}

}
