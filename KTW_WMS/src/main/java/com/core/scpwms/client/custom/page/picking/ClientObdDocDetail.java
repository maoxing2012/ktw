package com.core.scpwms.client.custom.page.picking;

import com.core.scpview.client.utils.LocaleUtils;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClientObdDocDetail implements IsSerializable {

	// 1.ID(隐)
	// 2.客户单号
	// 3.行号(隐)
	// 4.状态
	// 5.货主编号
	// 6.货主名称(隐)
	// 7.商品编码
	// 8.商品名称
	// 9.批次属性
	// 10.批次说明
	// 11.包装
	// 12.预期数
	// 13.预期数(EA)
	// 14.分配数(EA)
	// 15.拣货数(EA)
	// 16.发货数(EA)

	/** 行ID */
	private Long docDetailId;

	/** 客户单号 */
	private String custBillNo;

	/** 行号 */
	private Double lineNo;

	/** 状态 */
	private Long status;

	/** 货主编号 */
	private String ownerCode;

	/** 货主名称 */
	private String ownerName;

	/** 货品代码 */
	private String skuCode;

	/** 货品名称 */
	private String skuName;

	/** 期待包装 */
	private String packageName;

	/** 包装数 */
	private Double packQty;

	/** 预期数量（基本单位） */
	private Double planQty;

	/** 分配数量（基本单位） */
	private Double allocateQty;

	/** 拣选数量（基本单位） */
	private Double pickUpQty = 0D;

	/** 已发运数量（基本单位） */
	private Double executeQty = 0D;

	/** 指定批次属性收货 收货时提示录入/或确认 */
	private String lotData;

	/** 批次说明(华耐) */
	private String trackSeq;

	/** 发货库位 */
	private String binCode;

	/** 发货单号 */
	private String obdNum;

	public ClientObdDocDetail() {
	}

	public Long getDocDetailId() {
		return docDetailId;
	}

	public void setDocDetailId(Long docDetailId) {
		this.docDetailId = docDetailId;
	}

	public Double getLineNo() {
		return lineNo;
	}

	public void setLineNo(Double lineNo) {
		this.lineNo = lineNo;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

	public Double getPlanQty() {
		return planQty;
	}

	public void setPlanQty(Double planQty) {
		this.planQty = planQty;
	}

	public Double getAllocateQty() {
		return allocateQty;
	}

	public void setAllocateQty(Double allocateQty) {
		this.allocateQty = allocateQty;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Double getPackQty() {
		return packQty;
	}

	public void setPackQty(Double packQty) {
		this.packQty = packQty;
	}

	public Double getPickUpQty() {
		return pickUpQty;
	}

	public void setPickUpQty(Double pickUpQty) {
		this.pickUpQty = pickUpQty;
	}

	public Double getExecuteQty() {
		return executeQty;
	}

	public void setExecuteQty(Double executeQty) {
		this.executeQty = executeQty;
	}

	public String getLotData() {
		return lotData;
	}

	public void setLotData(String lotData) {
		this.lotData = lotData;
	}

	public String getBinCode() {
		return binCode;
	}

	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}

	public String getObdNum() {
		return obdNum;
	}

	public void setObdNum(String obdNum) {
		this.obdNum = obdNum;
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

	public String getCustBillNo() {
		return this.custBillNo;
	}

	public void setCustBillNo(String custBillNo) {
		this.custBillNo = custBillNo;
	}

	public String getOwnerCode() {
		return this.ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	/** 将title信息添加进<code>ListGridRecord</code>中 */
	public ListGridRecord getRecord(Long currentId) {
		ListGridRecord record = new ListGridRecord();

		// 1.ID(隐)
		// 2.客户单号
		// 3.行号(隐)
		// 4.状态
		// 5.货主编号
		// 6.货主名称(隐)
		// 7.商品编码
		// 8.商品名称
		// 9.批次属性
		// 10.批次说明
		// 11.包装
		// 12.预期数
		// 13.预期数(EA)
		// 14.分配数(EA)
		// 15.拣货数(EA)
		// 16.发货数(EA)
		record.setAttribute("ObdDocDetailDS_0", docDetailId);
		record.setAttribute("ObdDocDetailDS_1", custBillNo);
		record.setAttribute("ObdDocDetailDS_2", lineNo);
		record.setAttribute("ObdDocDetailDS_3", getStatus(status));
		record.setAttribute("ObdDocDetailDS_4", ownerCode);
		record.setAttribute("ObdDocDetailDS_5", ownerName);
		record.setAttribute("ObdDocDetailDS_6", skuCode);
		record.setAttribute("ObdDocDetailDS_7", skuName);
		record.setAttribute("ObdDocDetailDS_8", lotData);
		record.setAttribute("ObdDocDetailDS_9", trackSeq);
		record.setAttribute("ObdDocDetailDS_10", packageName);
		record.setAttribute("ObdDocDetailDS_11", packQty);
		record.setAttribute("ObdDocDetailDS_12", planQty);
		record.setAttribute("ObdDocDetailDS_13", allocateQty);
		record.setAttribute("ObdDocDetailDS_14", pickUpQty);
		record.setAttribute("ObdDocDetailDS_15", executeQty);

		return record;
	}

	public String getStatus(Long statusValue) {
		return LocaleUtils.getText("EnuOutboundDeliveryDetailStatus."
				+ statusValue);
	}
}
