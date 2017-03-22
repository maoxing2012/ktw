package com.core.scpwms.server.model.move;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.Entity;
import com.core.scpwms.server.domain.AllocatePutawayDetail;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 
 * <p>移位计划明细</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/23<br/>
 */
@SuppressWarnings("all")
public class MoveDocDetail extends Entity implements AllocatePutawayDetail {

    /** 质检计划 */
    private MoveDoc doc;

    /** 预期数量(EA) */
    private double planQty = 0D;

    /** 预期包装数量 */
    private Double planPackQty = 0D;

    /** 计划上架数量(EA) */
    private Double planPutawayQty = 0D;

    /** 上架数量(EA) */
    private Double putawayQty = 0D;

    /** 库存属性 */
    private InventoryInfo invInfo;

    /** 避免同一个库存被反复添加，这里存一个库存ID来做验证 */
    private Long inventoryId;

    public MoveDoc getDoc() {
        return this.doc;
    }

    public void setDoc(MoveDoc doc) {
        this.doc = doc;
    }

    public double getPlanQty() {
        return this.planQty;
    }

    public void setPlanQty(double planQty) {
        this.planQty = planQty;
    }

    public Double getPlanPackQty() {
        return this.planPackQty;
    }

    public void setPlanPackQty(Double planPackQty) {
        this.planPackQty = planPackQty;
    }

    public Double getPlanPutawayQty() {
        return this.planPutawayQty;
    }

    public void setPlanPutawayQty(Double planPutawayQty) {
        this.planPutawayQty = planPutawayQty;
    }

    public Double getPutawayQty() {
        return this.putawayQty;
    }

    public void setPutawayQty(Double putawayQty) {
        this.putawayQty = putawayQty;
    }

    public InventoryInfo getInvInfo() {
        return this.invInfo;
    }

    public void setInvInfo(InventoryInfo invInfo) {
        this.invInfo = invInfo;
    }

    public Long getInventoryId() {
        return this.inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public void addPlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.add(planQty, qty);
            planPackQty = PrecisionUtils.getPackQty(planQty, invInfo.getPackageDetail());
            doc.addPlanQty(qty);
        }
    }

    public void removePlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.sub(planQty, qty);
            planPackQty = PrecisionUtils.getPackQty(planQty, invInfo.getPackageDetail());
            doc.removePlanQty(qty);
        }
    }

    public void addPlanPutawayQty(double qty) {
        if (qty > 0) {
            planPutawayQty = DoubleUtil.add(planPutawayQty, qty);
            doc.addPlanPutawayQty(qty);
        }
    }

    public void removePlanPutawayQty(double qty) {
        if (qty > 0) {
            planPutawayQty = DoubleUtil.sub(planPutawayQty, qty);
            doc.removePlanPutawayQty(qty);
        }
    }

    public void addPutawayQty(double qty) {
        if (qty > 0) {
            putawayQty = DoubleUtil.add(putawayQty, qty);
            doc.addPutawayQty(qty);
        }
    }

    public void removePutawayQty(double qty) {
        if (qty > 0) {
            putawayQty = DoubleUtil.sub(putawayQty, qty);
            doc.removePutawayQty(qty);
        }
    }

    public double getUnPlanPutawayQty() {
        return DoubleUtil.sub(planQty, planPutawayQty);
    }

    public double getUnPutawayQty() {
        return DoubleUtil.sub(planQty, putawayQty);
    }

    public double getPlanedUnPutawayQty() {
        return DoubleUtil.sub(planPutawayQty, putawayQty);
    }

    @Override
    public Plant getPlant() {
        return getDoc().getPlant();
    }

    @Override
    public Warehouse getWh() {
        return getDoc().getWh();
    }

    @Override
    public String getDocSequence() {
        return getDoc().getDocSequence();
    }

    @Override
    public void setPutawayDocSequence(String putawayDocSequence) {
        getDoc().setPutawayDocSequence(putawayDocSequence);
    }
}
