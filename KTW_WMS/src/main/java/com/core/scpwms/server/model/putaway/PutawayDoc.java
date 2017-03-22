package com.core.scpwms.server.model.putaway;

import java.util.HashSet;
import java.util.Set;

import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;
import com.core.scpwms.server.enumerate.EnuPutawayDocStatus;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 目前收货上架单是单独处理，其他库内作业后产生的上架但均用此单据
 * 上架单(本质上是移位单据)，包含质检上架、加工上架
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class PutawayDoc extends OrderTrackingEntityWithStatus {

    /**
     * 货主
     */
    private Plant plant;

    /**
     * 仓库
     */
    private Warehouse wh;

    /**
     * 单据类型
     */
    private OrderType orderType;

    /**
     * 单据编号
     */
    private String docSequence;

    /** 预期数量（EA） */
    private double planQty;

    /** 分配数量（EA） */
    private double allocateQty;

    /** 上架数量（EA） */
    private double executeQty;

    /**
     * 备注信息
     */
    private String description;

    /**
     * 上架计划明细
     */
    private Set<PutawayDocDetail> details = new HashSet<PutawayDocDetail>();

    /**
     * 相关单号1(ASN单号，质检单号，加工单号等等)
     */
    private String relatedBill1;

    /**
     * 相关单号2
     */
    private String relatedBill2;

    /**
     * 相关单号3
     */
    private String relatedBill3;

    public Plant getPlant() {
        return this.plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getDocSequence() {
        return this.docSequence;
    }

    public void setDocSequence(String docSequence) {
        this.docSequence = docSequence;
    }

    public double getPlanQty() {
        return this.planQty;
    }

    public void setPlanQty(double planQty) {
        this.planQty = planQty;
    }

    public double getAllocateQty() {
        return this.allocateQty;
    }

    public void setAllocateQty(double allocateQty) {
        this.allocateQty = allocateQty;
    }

    public double getExecuteQty() {
        return this.executeQty;
    }

    public void setExecuteQty(double executeQty) {
        this.executeQty = executeQty;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PutawayDocDetail> getDetails() {
        return this.details;
    }

    public void setDetails(Set<PutawayDocDetail> details) {
        this.details = details;
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

    @Override
    public String getStatusEnum() {
        return EnuPutawayDocStatus.class.getSimpleName();
    }

    public void addPlanQty(double qty) {
        if (qty > 0) {
            this.planQty = DoubleUtil.add(planQty, qty);
        }
    }

    public void removePlanQty(double qty) {
        if (qty > 0) {
            this.planQty = DoubleUtil.sub(planQty, qty);
        }
    }

    /**
     * 
     * <p>分配</p>
     *
     * @param qty
     */
    public void allocate(double qty) {
        if (qty > 0) {
            this.allocateQty = DoubleUtil.add(allocateQty, qty);
        }
    }

    /**
     * 
     * <p>取消分配</p>
     *
     * @param qty
     */
    public void unAllocate(double qty) {
        if (qty > 0) {
            this.allocateQty = DoubleUtil.sub(allocateQty, qty);
        }
    }

    /**
     * 
     * <p>上架</p>
     *
     * @param qty
     */
    public void execute(double qty) {
        if (qty > 0) {
            this.executeQty = DoubleUtil.add(executeQty, qty);
        }
    }

    /**
     * 
     * <p>取消上架</p>
     *
     * @param qty
     */
    public void cancelExecute(double qty) {
        if (qty > 0) {
            this.executeQty = DoubleUtil.sub(executeQty, qty);
        }
    }

    /**
     * 
     * <p>取得未分配数</p>
     *
     */
    public double getUnAllocateQty() {
        return DoubleUtil.sub(planQty, allocateQty);
    }

    /**
     * 
     * <p>取得未上架数量</p>
     *
     * @return
     */
    public double getUnPutawayQty() {
        return DoubleUtil.sub(allocateQty, executeQty);
    }
}
