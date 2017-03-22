package com.core.scpwms.server.model.move;

import java.util.HashSet;
import java.util.Set;

import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;
import com.core.scpwms.server.enumerate.EnuMoveDocStatus;
import com.core.scpwms.server.enumerate.EnuMoveDocType;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.BinGroup;
import com.core.scpwms.server.model.warehouse.StorageType;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.model.warehouse.WhArea;
import com.google.gwt.storage.client.Storage;

/**
 * 
 * <p>移位计划（用于库位合并，上架等）</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/23<br/>
 */
@SuppressWarnings("all")
public class MoveDoc extends OrderTrackingEntityWithStatus {

    /** 仓库 */
    private Warehouse wh;

    /** 货主 */
    private Plant plant;
    
    /** 单据编号 */
    private String docSequence;

    /** 单据类型 */
    private OrderType orderType;

    /** 计划数量(EA) */
    private Double planQty = 0D;

    /** 加入上架数量(EA) */
    private Double planPutawayQty = 0D;

    /** 上架数量(EA) */
    private Double putawayQty = 0D;

    /** 生成了上架计划的话，这里存上架单号 */
    private String putawayDocSequence;

    /** 描述 */
    private String description;
    
    /** 库区 */
    private StorageType st;
    
    /** 库位组 */
    private BinGroup bg;
    
    /** 
     * @see EnuMoveDocType
     *  */
    private String moveType;
    
    /**
     * 库容下限
     */
    private Double minBinInv;
    
    /** 明细 */
    private Set<MoveDocDetail> details = new HashSet<MoveDocDetail>();

    @Override
    public String getStatusEnum() {
        return EnuMoveDocStatus.class.getSimpleName();
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

    public String getDocSequence() {
        return this.docSequence;
    }

    public void setDocSequence(String docSequence) {
        this.docSequence = docSequence;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Double getPlanQty() {
        return this.planQty;
    }

    public void setPlanQty(Double planQty) {
        this.planQty = planQty;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<MoveDocDetail> getDetails() {
        return this.details;
    }

    public void setDetails(Set<MoveDocDetail> details) {
        this.details = details;
    }

    public String getPutawayDocSequence() {
        return this.putawayDocSequence;
    }

    public void setPutawayDocSequence(String putawayDocSequence) {
        this.putawayDocSequence = putawayDocSequence;
    }
    
	public StorageType getSt() {
		return this.st;
	}

	public void setSt(StorageType st) {
		this.st = st;
	}

	public Double getMinBinInv() {
		return this.minBinInv;
	}

	public void setMinBinInv(Double minBinInv) {
		this.minBinInv = minBinInv;
	}

	public BinGroup getBg() {
		return this.bg;
	}

	public void setBg(BinGroup bg) {
		this.bg = bg;
	}

	public String getMoveType() {
		return this.moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public void addPlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.add(planQty, qty);
        }
    }

    public void removePlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.sub(planQty, qty);
        }
    }

    public void addPlanPutawayQty(double qty) {
        if (qty > 0) {
            planPutawayQty = DoubleUtil.add(planPutawayQty, qty);
        }
    }

    public void removePlanPutawayQty(double qty) {
        if (qty > 0) {
            planPutawayQty = DoubleUtil.sub(planPutawayQty, qty);
        }
    }

    public void addPutawayQty(double qty) {
        if (qty > 0) {
            putawayQty = DoubleUtil.add(putawayQty, qty);
        }
    }

    public void removePutawayQty(double qty) {
        if (qty > 0) {
            putawayQty = DoubleUtil.sub(putawayQty, qty);
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
}
