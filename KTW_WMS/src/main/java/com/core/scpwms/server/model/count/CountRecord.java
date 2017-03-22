package com.core.scpwms.server.model.count;

import java.util.Date;

import com.core.business.model.TrackingEntity;
import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuCountDetailStatus;
import com.core.scpwms.server.model.common.ReasonCode;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 
 * <p>盘点单明细</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/11<br/>
 */
public class CountRecord extends TrackingEntity {

    /**
     * 盘点计划
     */
    private CountPlan countPlan;

    /**
     * 盘点状态
     * @see EnuCountDetailStatus
     */
    private Long status;

    /**
     * 库存信息
     */
    private InventoryInfo invInfo;

    /**
     * 库存数量
     */
    private Double invPackQty = 0D;

    /** 
     * 基本单位数量
     */
    private Double invBaseQty = 0D;

    /**
     * 盘点数量
     */
    private double countQuantity = 0D;

    /**
     * 差异数量
     */
    private Double deltaQuantity = 0D;

    /**
     * 盘点录入人
     */
    private String operator;

    /**
     * 盘点录入时间
     */
    private Date occurTime;

    /**
     * 操作人员
     */
    private Labor labor;

    /**
     * 差异原因
     */
    private String countReason;

    /**
     * 原因码
     */
    private ReasonCode reasonCode;
    
    /**
     * 备注
     */
    private String description;

    public CountPlan getCountPlan() {
        return this.countPlan;
    }

    public void setCountPlan(CountPlan countPlan) {
        this.countPlan = countPlan;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public InventoryInfo getInvInfo() {
        return this.invInfo;
    }

    public void setInvInfo(InventoryInfo invInfo) {
        this.invInfo = invInfo;
    }

    public Double getInvPackQty() {
        return this.invPackQty;
    }

    public void setInvPackQty(Double invPackQty) {
        this.invPackQty = invPackQty;
    }

    public Double getInvBaseQty() {
        return this.invBaseQty;
    }

    public void setInvBaseQty(Double invBaseQty) {
        this.invBaseQty = invBaseQty;
    }

    public double getCountQuantity() {
        return this.countQuantity;
    }

    public void setCountQuantity(double countQuantity) {
        this.countQuantity = countQuantity;
        updateDeltaQty();
    }

    public Double getDeltaQuantity() {
        return this.deltaQuantity;
    }

    public void setDeltaQuantity(Double deltaQuantity) {
        this.deltaQuantity = deltaQuantity;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getOccurTime() {
        return this.occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public Labor getLabor() {
        return this.labor;
    }

    public void setLabor(Labor labor) {
        this.labor = labor;
    }

    public String getCountReason() {
        return this.countReason;
    }

    public void setCountReason(String countReason) {
        this.countReason = countReason;
    }

    public ReasonCode getReasonCode() {
        return this.reasonCode;
    }

    public void setReasonCode(ReasonCode reasonCode) {
        this.reasonCode = reasonCode;
    }
    
    public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public void addPlanQty(double qty) {
        if (qty > 0) {
            invBaseQty = DoubleUtil.add(invBaseQty, qty);
            invPackQty = PrecisionUtils.getPackQty(invBaseQty, invInfo.getPackageDetail());
            countPlan.addPlanQty(qty);
        }
    }

    public void removePlanQty(double qty) {
        if (qty > 0) {
            invBaseQty = DoubleUtil.sub(invBaseQty, qty);
            invPackQty = PrecisionUtils.getPackQty(invBaseQty, invInfo.getPackageDetail());
            countPlan.removePlanQty(qty);
        }
    }

    public void updateDeltaQty() {
        deltaQuantity = DoubleUtil.sub(countQuantity, invBaseQty);
    }
}
