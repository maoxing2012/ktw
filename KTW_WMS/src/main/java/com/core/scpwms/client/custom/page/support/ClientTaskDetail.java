package com.core.scpwms.client.custom.page.support;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ClientTaskDetail implements IsSerializable {

	// 1.ID（隐）
	// 2.作业号
	// 2.1.状态
	// 3.货主编号
	// 3.1.货主名称（隐）
	// 4.商品编号
	// 5.商品名称
	// 6.批次属性
	// 7.批次说明
	// 8.库存状态
	// 9.包装
	// 10.计划上架数
	// 11.计划上架数(EA)
	// 12.已上架数
	// 13.已上架数(EA)
	// 14.目标库位
	// 15.目标托盘号（隐）
	// 16.目标容器号（隐）
	// 17.原库位
	// 18.原托盘号（隐）
	// 19.原容器号（隐）
	public String taskIds;
	public String taskSequence;
	public Long status;
	public String ownerCode;
	public String ownerName;
	public String skuCode;
	public String skuName;
	public String lotInfo;
	public String projectNo;
	public String invStatus;
	public String pkgName;
	public String putawayPackQty;
	public String planQty;
	public String executePackQty;
	public String executeQty;

	public String descBin;
	public String descPalletSeq;
	public String descContainerSeq;

	public String srcBin;
	public String srcPalletSeq;
	public String srcContainerSeq;

	public ClientTaskDetail() {
	}

	public String getTaskIds() {
		return this.taskIds;
	}

	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}

	public String getTaskSequence() {
		return this.taskSequence;
	}

	public void setTaskSequence(String taskSequence) {
		this.taskSequence = taskSequence;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getOwnerCode() {
		return this.ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public String getPkgName() {
		return this.pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getPutawayPackQty() {
		return this.putawayPackQty;
	}

	public void setPutawayPackQty(String putawayPackQty) {
		this.putawayPackQty = putawayPackQty;
	}

	public String getPlanQty() {
		return this.planQty;
	}

	public void setPlanQty(String planQty) {
		this.planQty = planQty;
	}

	public String getExecutePackQty() {
		return this.executePackQty;
	}

	public void setExecutePackQty(String executePackQty) {
		this.executePackQty = executePackQty;
	}

	public String getExecuteQty() {
		return this.executeQty;
	}

	public void setExecuteQty(String executeQty) {
		this.executeQty = executeQty;
	}

	public String getDescBin() {
		return this.descBin;
	}

	public void setDescBin(String descBin) {
		this.descBin = descBin;
	}

	public String getDescPalletSeq() {
		return this.descPalletSeq;
	}

	public void setDescPalletSeq(String descPalletSeq) {
		this.descPalletSeq = descPalletSeq;
	}

	public String getDescContainerSeq() {
		return this.descContainerSeq;
	}

	public void setDescContainerSeq(String descContainerSeq) {
		this.descContainerSeq = descContainerSeq;
	}

	public String getSrcBin() {
		return this.srcBin;
	}

	public void setSrcBin(String srcBin) {
		this.srcBin = srcBin;
	}

	public String getSrcPalletSeq() {
		return this.srcPalletSeq;
	}

	public void setSrcPalletSeq(String srcPalletSeq) {
		this.srcPalletSeq = srcPalletSeq;
	}

	public String getSrcContainerSeq() {
		return this.srcContainerSeq;
	}

	public void setSrcContainerSeq(String srcContainerSeq) {
		this.srcContainerSeq = srcContainerSeq;
	}

}
