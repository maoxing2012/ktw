package com.core.scpwms.server.model.task;

import java.util.Date;

import com.core.db.server.model.Entity;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.warehouse.Labor;

/**
 * NS個口明細
 * @author mousachi
 */
@SuppressWarnings("all")
public class WarehouseTaskSingle extends Entity {
	// 所属波次
	private WaveDoc waveDoc;
	
	// 出库单ID
	private Long obdId;
	
	// 比WT还要小的作业单位
	private WarehouseTask wt;
	
	// 送り状No
    private String relatedBill1;
    
    //　個口番号
    private String relatedBill2;

    // groupId
    private String relatedBill3;
    
    // 序列号
    private Long inx;
    
    //個口タイプ 
    private Long caseType;
    
    // 数量
	private Double qty = 0D;
	
	// 拣货数量
	private Double pickQty = 0D;
	
	// 复合数量
	private Double checkQty = 0D;
	
	// 拣货作业人员
	private Labor labor;
	
	// 复合作业人员
	private Labor checkLabor;
	
	// 拣货时间
	private Date operateTime;
	
	// 状态 0:未拣货， 100：拣货完成， 200，复合完成
	private Long status = 0L;
	
	// 复合时间
	private Date checkTime;

	public WaveDoc getWaveDoc() {
		return waveDoc;
	}

	public void setWaveDoc(WaveDoc waveDoc) {
		this.waveDoc = waveDoc;
	}

	public WarehouseTask getWt() {
		return wt;
	}

	public void setWt(WarehouseTask wt) {
		this.wt = wt;
	}

	public String getRelatedBill1() {
		return relatedBill1;
	}

	public void setRelatedBill1(String relatedBill1) {
		this.relatedBill1 = relatedBill1;
	}

	public String getRelatedBill2() {
		return relatedBill2;
	}

	public void setRelatedBill2(String relatedBill2) {
		this.relatedBill2 = relatedBill2;
	}

	public Long getInx() {
		return inx;
	}

	public void setInx(Long inx) {
		this.inx = inx;
	}

	public String getRelatedBill3() {
		return relatedBill3;
	}

	public void setRelatedBill3(String relatedBill3) {
		this.relatedBill3 = relatedBill3;
	}

	public Long getCaseType() {
		return caseType;
	}

	public void setCaseType(Long caseType) {
		this.caseType = caseType;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Labor getLabor() {
		return labor;
	}

	public void setLabor(Labor labor) {
		this.labor = labor;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Labor getCheckLabor() {
		return checkLabor;
	}

	public void setCheckLabor(Labor checkLabor) {
		this.checkLabor = checkLabor;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Double getPickQty() {
		return pickQty;
	}

	public void setPickQty(Double pickQty) {
		this.pickQty = pickQty;
	}

	public Double getCheckQty() {
		return checkQty;
	}

	public void setCheckQty(Double checkQty) {
		this.checkQty = checkQty;
	}

	public Long getObdId() {
		return obdId;
	}

	public void setObdId(Long obdId) {
		this.obdId = obdId;
	}


}
