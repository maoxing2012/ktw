package com.core.scpwms.server.model.change;

import java.util.Date;
import java.util.Set;

import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.AllocateDoc;
import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;
import com.core.scpwms.server.enumerate.EnuMoveDocStatus;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * <p>货主转换单</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/24<br/>
 */
@SuppressWarnings("all")
public class OwnerChangeDoc extends OrderTrackingEntityWithStatus implements AllocateDoc {

    /** 仓库 */
    private Warehouse wh;

    /** 公司（传统意义上货主） */
    private Plant plant;

    /** 原货主（分布场所） */
    private Owner srcOwner;

    /** 目标货主（分布场所） */
    private Owner descOwner;

    /** 单号 */
    private String docSequence;

    /** 计划数量(EA) */
    private Double planQty = 0d;

    /** 库存分配数量(EA) */
    private Double allocateQty = 0d;

    /** 执行数量(EA) */
    private Double executeQty = 0d;

    /** 描述 */
    private String description;

    /** 单据类型 */
    private OrderType orderType;

    /** 客户订单类型 */
    private String jdeOrderType;

    /** 订单公司 */
    private String companyCode;

    /** JDE开单时间 */
    private Date transactionDate;

    /**  接口接收/手工创建  */
    private Boolean ediData = Boolean.FALSE;

    /**  相关单据号1（JDE单号）*/
    private String relatedBill1;
    private String relatedBill2;
    private String relatedBill3;

    /** 扩展字段(开单员) */
    private String extString1;
    private String extString2;
    private String extString3;

    /** 明细 */
    private Set<OwnerChangeDocDetail> details;

    @Override
    public String getStatusEnum() {
        return EnuMoveDocStatus.class.getSimpleName();
    }

    @Override
    public Bin getDescBin() {
        return null;
    }

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public Plant getPlant() {
        return this.plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Owner getSrcOwner() {
        return this.srcOwner;
    }

    public void setSrcOwner(Owner srcOwner) {
        this.srcOwner = srcOwner;
    }

    public Owner getDescOwner() {
        return this.descOwner;
    }

    public void setDescOwner(Owner descOwner) {
        this.descOwner = descOwner;
    }

    public String getDocSequence() {
        return this.docSequence;
    }

    public void setDocSequence(String docSequence) {
        this.docSequence = docSequence;
    }

    public Double getPlanQty() {
        return this.planQty;
    }

    private void setPlanQty(Double planQty) {
        this.planQty = planQty;
    }

    public Double getAllocateQty() {
        return this.allocateQty;
    }

    private void setAllocateQty(Double allocateQty) {
        this.allocateQty = allocateQty;
    }

    public Double getExecuteQty() {
        return this.executeQty;
    }

    private void setExecuteQty(Double executeQty) {
        this.executeQty = executeQty;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getJdeOrderType() {
        return this.jdeOrderType;
    }

    public void setJdeOrderType(String jdeOrderType) {
        this.jdeOrderType = jdeOrderType;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean getEdiData() {
        return this.ediData;
    }

    public void setEdiData(Boolean ediData) {
        this.ediData = ediData;
    }

    public String getRelatedBill1() {
        return this.relatedBill1;
    }

    public void setRelatedBill1(String relatedBill1) {
        this.relatedBill1 = relatedBill1;
    }

    public String getRelatedBill2() {
        return this.relatedBill2;
    }

    public void setRelatedBill2(String relatedBill2) {
        this.relatedBill2 = relatedBill2;
    }

    public String getRelatedBill3() {
        return this.relatedBill3;
    }

    public void setRelatedBill3(String relatedBill3) {
        this.relatedBill3 = relatedBill3;
    }

    public String getExtString1() {
        return this.extString1;
    }

    public void setExtString1(String extString1) {
        this.extString1 = extString1;
    }

    public String getExtString2() {
        return this.extString2;
    }

    public void setExtString2(String extString2) {
        this.extString2 = extString2;
    }

    public String getExtString3() {
        return this.extString3;
    }

    public void setExtString3(String extString3) {
        this.extString3 = extString3;
    }

    public Set<OwnerChangeDocDetail> getDetails() {
        return this.details;
    }

    public void setDetails(Set<OwnerChangeDocDetail> details) {
        this.details = details;
    }

    public void addPlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.add(planQty, qty);
        }
    }

    public void addAllocateQty(double qty) {
        if (qty > 0) {
            allocateQty = DoubleUtil.add(allocateQty, qty);
        }
    }

    public void addExecuteQty(double qty) {
        if (qty > 0) {
            executeQty = DoubleUtil.add(executeQty, qty);
        }
    }

    public void removePlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.sub(planQty, qty);
        }
    }

    public void removeAllocateQty(double qty) {
        if (qty > 0) {
            allocateQty = DoubleUtil.sub(allocateQty, qty);
        }
    }

    public void removeExecuteQty(double qty) {
        if (qty > 0) {
            executeQty = DoubleUtil.sub(executeQty, qty);
        }
    }

    /**
     * 
     * <p>未分配数</p>
     *
     * @return
     */
    public double getUnAllocateQty() {
        return DoubleUtil.sub(planQty, allocateQty);
    }

    /**
     * 
     * <p>未执行数</p>
     *
     * @return
     */
    public double getUnExecuteQty() {
        return DoubleUtil.sub(planQty, executeQty);
    }

    /**
     * 
     * <p>已经分配单未执行数</p>
     *
     * @return
     */
    public double getAllocatedUnExecuteQty() {
        return DoubleUtil.sub(allocateQty, executeQty);
    }
}
