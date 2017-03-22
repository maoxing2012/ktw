package com.core.scpwms.server.model.putaway;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.Entity;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.util.PrecisionUtils;

@SuppressWarnings("all")
public class PutawayDocDetail extends Entity {

    /**
     * 所属单据头
     */
    private PutawayDoc putawayDoc;

    /** 预期数量（EA） */
    private double planQty;

    /** 预期包装数量（包装单位） */
    private double planPackQty;

    /** 分配数量（EA） */
    private double allocateQty;

    /** 移位数量（EA） */
    private double executeQty;

    /** 目标库位 */
    private Bin descBin;

    /**
     * 源库存信息
     */
    private InventoryInfo invInfo;
    
    /**
     * TRUE:目标库位的库容必须小于原库位的库容（用于库位优化）
     */
    private Boolean smallThanSrc;

    /**
     * 包含收货上架任务明细（分配时生成）
     */
    private Set<WarehouseTask> tasks = new HashSet<WarehouseTask>();

    public PutawayDoc getPutawayDoc() {
        return this.putawayDoc;
    }

    public void setPutawayDoc(PutawayDoc putawayDoc) {
        this.putawayDoc = putawayDoc;
    }

    public double getPlanQty() {
        return this.planQty;
    }

    public void setPlanQty(double planQty) {
        this.planQty = planQty;
    }

    public double getPlanPackQty() {
        return this.planPackQty;
    }

    public void setPlanPackQty(double planPackQty) {
        this.planPackQty = planPackQty;
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

    public Bin getDescBin() {
        return this.descBin;
    }

    public void setDescBin(Bin descBin) {
        this.descBin = descBin;
    }

    public Set<WarehouseTask> getTasks() {
        return this.tasks;
    }

    public void setTasks(Set<WarehouseTask> tasks) {
        this.tasks = tasks;
    }

    public InventoryInfo getInvInfo() {
        return this.invInfo;
    }

    public void setInvInfo(InventoryInfo invInfo) {
        this.invInfo = invInfo;
    }
    
    public Boolean getSmallThanSrc() {
		return this.smallThanSrc;
	}

	public void setSmallThanSrc(Boolean smallThanSrc) {
		this.smallThanSrc = smallThanSrc;
	}

	public void addPlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.add(planQty, qty);
            planPackQty = PrecisionUtils.getPackQty(planQty, invInfo.getPackageDetail());

            putawayDoc.addPlanQty(qty);
        }
    }

    public void removePlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.sub(planQty, qty);
            planPackQty = PrecisionUtils.getPackQty(planQty, invInfo.getPackageDetail());

            putawayDoc.removePlanQty(qty);
        }
    }

    public void allocate(double qty) {
        if (qty > 0) {
            allocateQty = DoubleUtil.add(allocateQty, qty);
            putawayDoc.allocate(qty);
        }
    }

    public void unAllocate(double qty) {
        if (qty > 0) {
            allocateQty = DoubleUtil.sub(allocateQty, qty);
            putawayDoc.unAllocate(qty);
        }
    }

    public void execute(double qty) {
        if (qty > 0) {
            executeQty = DoubleUtil.add(executeQty, qty);
            putawayDoc.execute(qty);
        }
    }

    public void cancelExecute(double qty) {
        if (qty > 0) {
            executeQty = DoubleUtil.sub(executeQty, qty);
            putawayDoc.cancelExecute(qty);
        }
    }

    public boolean hasNotAllocate() {
        return planQty > allocateQty ? true : false;
    }

    public double getUnAllocateQtyBase() {
        return DoubleUtil.sub(planQty, allocateQty);
    }

    public double getUnPutawayQty() {
        return DoubleUtil.sub(allocateQty, executeQty);
    }

}
