package com.core.scpwms.client.custom.page.picking;

import java.util.Date;

import com.core.scpview.client.utils.LocaleUtils;
import com.core.scpwms.server.util.DateUtil;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClientPickingInventory implements IsSerializable {
	// 1.ID(隐)
	// 2.库位编号
	// 3.货主编号
	// 4.货主名称(隐)
	// 5.商品编码
	// 6.商品名称
	// 7.批次号(隐)
	// 8.批次属性
	// 9.批次说明
	// 10.库存状态
	// 11.入库日期
	// 12.包装
	// 13.数量
	// 14.数量(EA)
	// 15.分配数(EA)
	// 16.托盘号(隐)
	// 17.容器号(隐)

	/** ID */
	private Long invId;

	/** 库位 */
	private String binCode;

	/** 货主编号 */
	private String ownerCode;

	/** 货主名称 */
	private String ownerName;

	/** 商品编码 */
	private String skuCode;

	/** 商品名称 */
	private String skuName;

	/** 批次号 */
	private String lotSeq;

	/** 批次属性 */
	private String lotData;

	/** 批次说明，工程号 */
	private String trackSeq;

	/** 库存状态 */
	private String status;

	/** 库存日期 */
	private Date inboundDate;

	/** 包装 */
	private String currentPack;

	/** 库存数量(包装数量) */
	private Double packQty = 0D;

	/** 基本单位数量 */
	private Double baseQty = 0D;

	/** 已分配数量(基本单位数量) */
	private Double queuedQty = 0D;

	/** 托盘(容器)号 */
	private String palletNo;

	/** 托盘(容器)号 */
	private String containerNo;

	public ClientPickingInventory() {
	}

	public Long getInvId() {
		return invId;
	}

	public void setInvId(Long invId) {
		this.invId = invId;
	}

	public String getBinCode() {
		return binCode;
	}

	public void setBinCode(String binCode) {
		this.binCode = binCode;
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

	public String getLotData() {
		return lotData;
	}

	public void setLotData(String lotData) {
		this.lotData = lotData;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getInboundDate() {
		return inboundDate;
	}

	public void setInboundDate(Date inboundDate) {
		this.inboundDate = inboundDate;
	}

	public double getBaseQty() {
		return baseQty;
	}

	public void setBaseQty(double baseQty) {
		this.baseQty = baseQty;
	}

	public double getQueuedQty() {
		return queuedQty;
	}

	public void setQueuedQty(double queuedQty) {
		this.queuedQty = queuedQty;
	}

	public String getCurrentPack() {
		return currentPack;
	}

	public void setCurrentPack(String currentPack) {
		this.currentPack = currentPack;
	}

	public Double getPackQty() {
		return packQty;
	}

	public void setPackQty(Double packQty) {
		this.packQty = packQty;
	}

	public String getPalletNo() {
		return palletNo;
	}

	public void setPalletNo(String palletNo) {
		this.palletNo = palletNo;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public void setBaseQty(Double baseQty) {
		this.baseQty = baseQty;
	}

	public void setQueuedQty(Double queuedQty) {
		this.queuedQty = queuedQty;
	}

	public String getOwnerCode() {
		return this.ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	/** 将title信息添加进<code>ListGridRecord</code>中 */
	public ListGridRecord getRecord() {
		ListGridRecord record = new ListGridRecord();

		// 1.ID(隐)
		// 2.库位编号
		// 3.货主编号
		// 4.货主名称(隐)
		// 5.商品编码
		// 6.商品名称
		// 7.批次号(隐)
		// 8.批次属性
		// 9.批次说明
		// 10.库存状态
		// 11.入库日期
		// 12.包装
		// 13.数量
		// 14.数量(EA)
		// 15.分配数(EA)
		// 16.托盘号(隐)
		// 17.容器号(隐)
		record.setAttribute("InventoryDetailDS0", invId);
		record.setAttribute("InventoryDetailDS1", binCode);
		record.setAttribute("InventoryDetailDS2", ownerCode);
		record.setAttribute("InventoryDetailDS3", ownerName);
		record.setAttribute("InventoryDetailDS4", skuCode);
		record.setAttribute("InventoryDetailDS5", skuName);
		record.setAttribute("InventoryDetailDS6", lotSeq);
		record.setAttribute("InventoryDetailDS7", lotData);
		record.setAttribute("InventoryDetailDS8", trackSeq);
		record.setAttribute("InventoryDetailDS9", getStatus(status));
		//record.setAttribute("InventoryDetailDS10", DateUtil.getStringDate(inboundDate, DateUtil.SLASH_FORMAT));
		record.setAttribute("InventoryDetailDS10", inboundDate);
		record.setAttribute("InventoryDetailDS11", currentPack);
		record.setAttribute("InventoryDetailDS12", packQty);
		record.setAttribute("InventoryDetailDS13", baseQty);
		record.setAttribute("InventoryDetailDS14", queuedQty);
		record.setAttribute("InventoryDetailDS15", palletNo);
		record.setAttribute("InventoryDetailDS16", containerNo);

		return record;
	}

	public String getStatus(String statusValue) {
		return LocaleUtils.getText("EnuInvStatus." + statusValue);
	}
}
