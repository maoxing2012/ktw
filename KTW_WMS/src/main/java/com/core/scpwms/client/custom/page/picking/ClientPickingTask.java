package com.core.scpwms.client.custom.page.picking;

import java.util.Date;

import com.core.scpview.client.utils.LocaleUtils;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClientPickingTask implements IsSerializable {

	// 1.ID(隐)
	// 2.作业号
	// 3.状态
	// 4.货主编号
	// 5.货主名称(隐)
	// 6.商品编号
	// 7.商品名称
	// 8.批次号(隐)
	// 9.批次属性
	// 10.批次说明
	// 11.库存状态
	// 12.入库日期
	// 13.包装
	// 14.计划拣选数
	// 15.计划拣选数(EA)
	// 16.拣选库位
	// 17.拣选托盘号(隐)
	// 18.拣选容器号(隐)
	// 19.目标库位
	// 20.目标托盘号(隐)
	// 21.目标容器号(隐)

	// 序号
	private Long taskIds;
	// 作业号
	private String docSequence;
	// 状态
	private Long status;
	// 货主编号
	private String ownerCode;
	// 货主名称
	private String ownerName;
	// 商品编号
	private String skuCode;
	// 商品名称
	private String skuName;
	// 批次号
	private String lotSeq;
	// 批次信息
	private String lot;
	// 批次说明
	private String trackSeq;
	// 包装单位
	private String pkgName;
	// 包装数量
	private Double packQty;
	// 计划数量(EA)
	private Double planQty;
	// 目标库位
	private String descBin;
	// 目标托盘号
	private String descPalletSeq;
	// 目标容器号
	private String descContainerSeq;
	// 原始库位
	private String srcBin;
	// 原始托盘号
	private String srcPalletSeq;
	// 原始容器号
	private String srcContainerSeq;
	// 库存状态
	private String invStatus;
	// 入库日期
	private Date inboundDate;

	public ClientPickingTask() {
	}

	public Long getTaskIds() {
		return taskIds;
	}

	public void setTaskIds(Long taskIds) {
		this.taskIds = taskIds;
	}

	public String getDocSequence() {
		return docSequence;
	}

	public void setDocSequence(String docSequence) {
		this.docSequence = docSequence;
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

	public String getLotSeq() {
		return lotSeq;
	}

	public void setLotSeq(String lotSeq) {
		this.lotSeq = lotSeq;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public Double getPackQty() {
		return packQty;
	}

	public void setPackQty(Double packQty) {
		this.packQty = packQty;
	}

	public Double getPlanQty() {
		return planQty;
	}

	public void setPlanQty(Double planQty) {
		this.planQty = planQty;
	}

	public String getDescBin() {
		return descBin;
	}

	public void setDescBin(String descBin) {
		this.descBin = descBin;
	}

	public String getDescPalletSeq() {
		return descPalletSeq;
	}

	public void setDescPalletSeq(String descPalletSeq) {
		this.descPalletSeq = descPalletSeq;
	}

	public String getDescContainerSeq() {
		return descContainerSeq;
	}

	public void setDescContainerSeq(String descContainerSeq) {
		this.descContainerSeq = descContainerSeq;
	}

	public String getSrcBin() {
		return srcBin;
	}

	public void setSrcBin(String srcBin) {
		this.srcBin = srcBin;
	}

	public String getSrcPalletSeq() {
		return srcPalletSeq;
	}

	public void setSrcPalletSeq(String srcPalletSeq) {
		this.srcPalletSeq = srcPalletSeq;
	}

	public String getSrcContainerSeq() {
		return srcContainerSeq;
	}

	public void setSrcContainerSeq(String srcContainerSeq) {
		this.srcContainerSeq = srcContainerSeq;
	}

	public Date getInboundDate() {
		return inboundDate;
	}

	public void setInboundDate(Date inboundDate) {
		this.inboundDate = inboundDate;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getTrackSeq() {
		return this.trackSeq;
	}

	public void setTrackSeq(String trackSeq) {
		this.trackSeq = trackSeq;
	}

	public String getInvStatus() {
		return this.invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
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
	
	public ListGridRecord getRecord() {
		ListGridRecord record = new ListGridRecord();

		// 1.ID(隐)
		// 2.作业号
		// 3.状态
		// 4.货主编号
		// 5.货主名称(隐)
		// 6.商品编号
		// 7.商品名称
		// 8.批次号(隐)
		// 9.批次属性
		// 10.批次说明
		// 11.库存状态
		// 12.入库日期
		// 13.包装
		// 14.计划拣选数
		// 15.计划拣选数(EA)
		// 16.拣选库位
		// 17.拣选托盘号(隐)
		// 18.拣选容器号(隐)
		// 19.目标库位
		// 20.目标托盘号(隐)
		// 21.目标容器号(隐)
		record.setAttribute("PickTaskDS_0", taskIds);
		record.setAttribute("PickTaskDS_1", docSequence);
		record.setAttribute("PickTaskDS_2", getStatus(status));
		record.setAttribute("PickTaskDS_3", ownerCode);
		record.setAttribute("PickTaskDS_4", ownerName);
		record.setAttribute("PickTaskDS_5", skuCode);
		record.setAttribute("PickTaskDS_6", skuName);
		record.setAttribute("PickTaskDS_7", lotSeq);
		record.setAttribute("PickTaskDS_8", lot);
		record.setAttribute("PickTaskDS_9", trackSeq);
		record.setAttribute("PickTaskDS_10", getInvStatus(invStatus));
		record.setAttribute("PickTaskDS_11", inboundDate);
		record.setAttribute("PickTaskDS_12", pkgName);
		record.setAttribute("PickTaskDS_13", packQty);
		record.setAttribute("PickTaskDS_14", planQty);
		record.setAttribute("PickTaskDS_15", srcBin);
		record.setAttribute("PickTaskDS_16", srcPalletSeq);
		record.setAttribute("PickTaskDS_17", srcContainerSeq);
		record.setAttribute("PickTaskDS_18", descBin);
		record.setAttribute("PickTaskDS_19", descPalletSeq);
		record.setAttribute("PickTaskDS_20", descContainerSeq);
		
		return record;
	}

	private String getInvStatus(String invStatus) {
		return LocaleUtils.getText("EnuInvStatus." + invStatus);
	}
	
	private String getStatus(Long status){
		return LocaleUtils.getText("EnuWarehouseOrderStatus." + status);
	}

}
