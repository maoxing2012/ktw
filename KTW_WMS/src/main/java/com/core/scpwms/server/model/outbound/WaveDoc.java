package com.core.scpwms.server.model.outbound;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.AllocateDoc;
import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;
import com.core.scpwms.server.enumerate.EnuWaveStatus;
import com.core.scpwms.server.enumerate.EnuWaveWorkModel;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 波次作业单
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class WaveDoc extends OrderTrackingEntityWithStatus implements AllocateDoc {

    /** 仓库 */
    private Warehouse wh;

    /** 公司 */
    private Plant plant;
    
    /** 货主 */
    private Owner owner;
    
    /**　承运商 */
    private Carrier carrier;
    
    /** 伝票種類 */
    private OrderType orderType;
    
    /**　承运商编号用逗号拼接 */
    private String carrierCodes;
    
    /** 波次号 */
    private String waveNumber;

    /** 数量汇总(EA) */
    private Double planQty = 0D;

    /** 分配数量(EA)  */
    private Double allocatedQty = 0D;

    /** 拣货数量(EA)  */
    private Double executeQty = 0D;

    /** 包含订单数量 */
    private Long orderQty = 0L;
    
    private Long orderCount1 = 0L;
    private Long orderCount2 = 0L;
    private Long orderCount3 = 0L;
    private Long orderCount4 = 0L;
    
    private Long psCaseCount = 0L;
    private Long blCaseCount = 0L;
    private Long csCaseCount = 0L;
    
    /** 包含品项数 */
    private Long skuCount = 0L;

    /** 重量汇总 */
    private Double sumWeight = 0D;

    /** 体积汇总 */
    private Double sumVolumn = 0D;

    /** 标准度量汇总 */
    private Double sumMetric = 0D;

    /** 金额汇总 */
    private Double sumPrice = 0D;

    /** 作业模式 @see EnuWaveWorkModel */
    private String workModel = EnuWaveWorkModel.SUM_ORDER_PICK;
    
    /** 预定发货日期 */
    private Date etd;
    
    private String description;
    
    @Override
    public String getStatusEnum() {
        return EnuWaveStatus.class.getSimpleName();
    }

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public String getWaveNumber() {
        return this.waveNumber;
    }

    public void setWaveNumber(String waveNumber) {
        this.waveNumber = waveNumber;
    }

    public Long getOrderQty() {
        return this.orderQty;
    }

    public void setOrderQty(Long orderQty) {
        this.orderQty = orderQty;
    }

    public Double getPlanQty() {
        return this.planQty;
    }

    public void setPlanQty(Double planQty) {
        this.planQty = planQty;
    }

    public Double getAllocatedQty() {
        return this.allocatedQty;
    }

    public void setAllocatedQty(Double allocatedQty) {
        this.allocatedQty = allocatedQty;
    }

    public Double getExecuteQty() {
        return this.executeQty;
    }

    public void setExecuteQty(Double executeQty) {
        this.executeQty = executeQty;
    }

    public Long getSkuCount() {
        return this.skuCount;
    }

    public void setSkuCount(Long skuCount) {
        this.skuCount = skuCount;
    }

    public Double getSumWeight() {
        return this.sumWeight;
    }

    public void setSumWeight(Double sumWeight) {
        this.sumWeight = sumWeight;
    }

    public Double getSumVolumn() {
        return this.sumVolumn;
    }

    public void setSumVolumn(Double sumVolumn) {
        this.sumVolumn = sumVolumn;
    }

    public Double getSumMetric() {
        return this.sumMetric;
    }

    public void setSumMetric(Double sumMetric) {
        this.sumMetric = sumMetric;
    }

    public Double getSumPrice() {
        return this.sumPrice;
    }

    public void setSumPrice(Double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getWorkModel() {
        return this.workModel;
    }

    public void setWorkModel(String workModel) {
        this.workModel = workModel;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public void addPlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.add(planQty, qty);
        }
    }

    public void addAllocatedQty(double qty) {
        if (qty > 0) {
            allocatedQty = DoubleUtil.add(allocatedQty, qty);
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

    public void removeAllocatedQty(double qty) {
        if (qty > 0) {
            allocatedQty = DoubleUtil.sub(allocatedQty, qty);
        }
    }

    public void removeExecuteQty(double qty) {
        if (qty > 0) {
            executeQty = DoubleUtil.sub(executeQty, qty);
        }
    }

    public double getUnAllocateQty() {
        return DoubleUtil.sub(planQty, allocatedQty);
    }

    public double getUnExecuteQty() {
        return DoubleUtil.sub(planQty, executeQty);
    }

    public double getAllocatedUnExecuteQty() {
        return DoubleUtil.sub(allocatedQty, executeQty);
    }

    @Override
    public Bin getDescBin() {
        return null;
    }

    @Override
    public OrderType getOrderType() {
        return null;
    }

    @Override
    public Plant getPlant() {
        return plant;
    }

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Date getEtd() {
		return etd;
	}

	public void setEtd(Date etd) {
		this.etd = etd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	public String getCarrierCodes() {
		return carrierCodes;
	}

	public void setCarrierCodes(String carrierCodes) {
		this.carrierCodes = carrierCodes;
	}

	public Long getOrderCount1() {
		return orderCount1;
	}

	public void setOrderCount1(Long orderCount1) {
		this.orderCount1 = orderCount1;
	}

	public Long getOrderCount2() {
		return orderCount2;
	}

	public void setOrderCount2(Long orderCount2) {
		this.orderCount2 = orderCount2;
	}

	public Long getOrderCount3() {
		return orderCount3;
	}

	public void setOrderCount3(Long orderCount3) {
		this.orderCount3 = orderCount3;
	}

	public Long getOrderCount4() {
		return orderCount4;
	}

	public void setOrderCount4(Long orderCount4) {
		this.orderCount4 = orderCount4;
	}

	public Long getPsCaseCount() {
		return psCaseCount;
	}

	public void setPsCaseCount(Long psCaseCount) {
		this.psCaseCount = psCaseCount;
	}

	public Long getBlCaseCount() {
		return blCaseCount;
	}

	public void setBlCaseCount(Long blCaseCount) {
		this.blCaseCount = blCaseCount;
	}

	public Long getCsCaseCount() {
		return csCaseCount;
	}

	public void setCsCaseCount(Long csCaseCount) {
		this.csCaseCount = csCaseCount;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

}
