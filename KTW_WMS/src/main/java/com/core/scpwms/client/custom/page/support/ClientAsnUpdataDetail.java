package com.core.scpwms.client.custom.page.support;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientAsnUpdataDetail implements IsSerializable {

	// 1.ID
	// 2.货主名称
	// 3.商品编号
	// 4.商品名称
	// 5.批次属性
	// 6.批次说明
	// 7.库存状态
	// 8.包装
	// 9.计划上架数量
	// 10.计划上架数量(EA)
	// 11.分配数(EA)
	// 12.上架数(EA)
	// 13.原库位
	// 14.原托盘号
	// 15.原容器号
	public String indexId;
	public String ownerCode;
	public String ownerName;
	public String skuCode;
	public String skuName;
	public String lotInfo;
	public String projectNo;
	public String invStatus;
	public String planPackage;
	public String planPackQty;
	public String planQty;
	public String allocateQty;
	public String executeQty;
	public String srcBin;
	public String palletSeq;
	public String containerSeq;

	public ClientAsnUpdataDetail() {
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getPlanQty() {
		return planQty;
	}

	public void setPlanQty(String planQty) {
		this.planQty = planQty;
	}

	public String getPlanPackQty() {
		return planPackQty;
	}

	public void setPlanPackQty(String planPackQty) {
		this.planPackQty = planPackQty;
	}

	public String getPlanPackage() {
		return planPackage;
	}

	public void setPlanPackage(String planPackage) {
		this.planPackage = planPackage;
	}

	public String getAllocateQty() {
		return allocateQty;
	}

	public void setAllocateQty(String allocateQty) {
		this.allocateQty = allocateQty;
	}

	public String getExecuteQty() {
		return executeQty;
	}

	public void setExecuteQty(String executeQty) {
		this.executeQty = executeQty;
	}

	public String getSrcBin() {
		return srcBin;
	}

	public void setSrcBin(String srcBin) {
		this.srcBin = srcBin;
	}

	public String getPalletSeq() {
		return palletSeq;
	}

	public void setPalletSeq(String palletSeq) {
		this.palletSeq = palletSeq;
	}

	public String getContainerSeq() {
		return containerSeq;
	}

	public void setContainerSeq(String containerSeq) {
		this.containerSeq = containerSeq;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getLotInfo() {
		return this.lotInfo;
	}

	public void setLotInfo(String lotInfo) {
		this.lotInfo = lotInfo;
	}

	public String getProjectNo() {
		return this.projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getInvStatus() {
		return this.invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public String getOwnerCode() {
		return this.ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

}
