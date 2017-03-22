package com.core.scpwms.server.domain;

import java.util.Date;

import com.core.scpwms.server.domain.InventoryInfo;
/**
 * WMS库存调整相关接口数据
 * @author dengXG
 */
public class InvEditData {
	
	/** 调整后-库存属性*/
	public InventoryInfo newInvInfo;	
	
	/** 调整前-库存属性*/
	public InventoryInfo olderInvInfo;	
	
	/** 调整数量*/
	public double qty; 				
	
	/** 操作人*/
	public String operName;		
	
	/** 操作日期*/
	public Date operDate;  		
	
	/** 差异原因*/
	public String resaonCode;
	
	/** 出库单明细ID */
	private Long obdDetailId;
	
	/**备注 */
	private String desc;

	/**单号*/
	private String orderSeq;

	public InvEditData(InventoryInfo newInvInfo,InventoryInfo olderInvInfo, double qty ,String operName,Date operDate) {
		this.newInvInfo = newInvInfo;
		this.olderInvInfo = olderInvInfo;
		this.qty = qty;
		this.operName=operName;
		this.operDate=operDate;
	}
	
	/**专供盘点损益实际*/
	public InvEditData(InventoryInfo newInvInfo,InventoryInfo olderInvInfo, double qty ,String operName,Date operDate,String desc,String orderSeq) {
		this.newInvInfo = newInvInfo;
		this.olderInvInfo = olderInvInfo;
		this.qty = qty;
		this.operName=operName;
		this.operDate=operDate;
		this.orderSeq = orderSeq;
		this.desc=desc;
		this.orderSeq = orderSeq;
	}
	
	public InvEditData( InventoryInfo newInvInfo, double qty ,String operName,Date operDate, Long obdDetailId ){
		this.newInvInfo = newInvInfo;
		this.obdDetailId = obdDetailId;
		this.qty = qty;
		this.operName = operName;
		this.operDate = operDate;
	}

	/**专供盘点差异确认 */
	public InvEditData(InventoryInfo newInvInfo,InventoryInfo olderInvInfo, double qty ,String operName,Date operDate,String desc) {
		this.newInvInfo = newInvInfo;
		this.olderInvInfo = olderInvInfo;
		this.qty = qty;
		this.operName=operName;
		this.operDate=operDate;
		this.desc=desc;
	}
	
	public InventoryInfo getNewInvInfo() {
		return newInvInfo;
	}



	public void setNewInvInfo(InventoryInfo newInvInfo) {
		this.newInvInfo = newInvInfo;
	}



	public InventoryInfo getOlderInvInfo() {
		return olderInvInfo;
	}



	public void setOlderInvInfo(InventoryInfo olderInvInfo) {
		this.olderInvInfo = olderInvInfo;
	}



	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}


	public String getOperName() {
		return operName;
	}


	public void setOperName(String operName) {
		this.operName = operName;
	}


	public Date getOperDate() {
		return operDate;
	}


	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}



	public String getResaonCode() {
		return resaonCode;
	}



	public void setResaonCode(String resaonCode) {
		this.resaonCode = resaonCode;
	}



	public Long getObdDetailId() {
		return this.obdDetailId;
	}



	public void setObdDetailId(Long obdDetailId) {
		this.obdDetailId = obdDetailId;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

}
