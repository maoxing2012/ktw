package com.core.scpwms.server.model.task;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.BaseWarehouseOrder;
import com.core.scpwms.server.enumerate.EnuPickMethod;
import com.core.scpwms.server.enumerate.EnuWOType;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.StorageType;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * @description   WarehouseOrder
 * @author         MBP:毛幸<br/>
 * @createDate    2013/08/20
 * @version        V1.0<br/>
 */
@SuppressWarnings("all")
public class WarehouseOrder extends BaseWarehouseOrder {

    /** 仓库信息 */
    private Warehouse wh;

    /** 作业单号 */
    private String orderSequence;
    
    /** 波次号 */
    private WaveDoc waveDoc;
    
    /** 
     * 作业单类型
     * @see EnuWOType
     */
    private String warehouseOrderType;

    /** 计划开始时间 */
    private Date startDate;

    /** 计划结束时间 */
    private Date endDate;

    /** 作业人员 */
    private Labor labor;

    /** 预期数量（基本单位） */
    private double planQty;

    /** 实际数量（基本单位） */
    private double executeQty;

    private Double skuCount = 0d;

    private Double sumWeight = 0d;

    private Double sumVolume = 0d;

    private Double sumMetric = 0d;

    private Double sumPrice = 0d;

    /** 包含明细 */
    private Set<WarehouseTask> tasks = new HashSet<WarehouseTask>();

    /** 执行开始时间 */
    private Date executeStartDate;

    /** 执行结束时间 */
    private Date executeEndDate;

    /** 分配时间 */
    private Date allocateDate;
    
    private String description;
    
    /** 功能区 */
    private StorageType st;
    
    /** 是否已经打印拣货单 */
    private Boolean isPrinted = Boolean.FALSE;
    
    /** 有尾箱 */
    private Boolean hasHalfFullCase;
    
    /** 拣货方式 
     * @see EnuPickMethod
     * */
    private String pickMethod;

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public String getOrderSequence() {
        return this.orderSequence;
    }

    public void setOrderSequence(String orderSequence) {
        this.orderSequence = orderSequence;
    }

    public String getWarehouseOrderType() {
        return this.warehouseOrderType;
    }

    public void setWarehouseOrderType(String warehouseOrderType) {
        this.warehouseOrderType = warehouseOrderType;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Labor getLabor() {
        return this.labor;
    }

    public void setLabor(Labor labor) {
        this.labor = labor;
    }

    public double getPlanQty() {
        return this.planQty;
    }

    public void setPlanQty(double planQty) {
        this.planQty = planQty;
    }

    public double getExecuteQty() {
        return this.executeQty;
    }

    public void setExecuteQty(double executeQty) {
        this.executeQty = executeQty;
    }

    public Set<WarehouseTask> getTasks() {
        return this.tasks;
    }

    public void setTasks(Set<WarehouseTask> tasks) {
        this.tasks = tasks;
    }

    public Date getExecuteStartDate() {
        return this.executeStartDate;
    }

    public void setExecuteStartDate(Date executeStartDate) {
        this.executeStartDate = executeStartDate;
    }

    public Date getExecuteEndDate() {
        return this.executeEndDate;
    }

    public void setExecuteEndDate(Date executeEndDate) {
        this.executeEndDate = executeEndDate;
    }

    public Date getAllocateDate() {
        return this.allocateDate;
    }

    public void setAllocateDate(Date allocateDate) {
        this.allocateDate = allocateDate;
    }

    public Double getSumWeight() {
        return this.sumWeight;
    }

    public void setSumWeight(Double sumWeight) {
        this.sumWeight = sumWeight;
    }

    public Double getSumVolume() {
        return this.sumVolume;
    }

    public void setSumVolume(Double sumVolume) {
        this.sumVolume = sumVolume;
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

    public Double getSkuCount() {
        return this.skuCount;
    }

    public void setSkuCount(Double skuCount) {
        this.skuCount = skuCount;
    }
    
    public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public StorageType getSt() {
		return this.st;
	}

	public void setSt(StorageType st) {
		this.st = st;
	}
	
	public Boolean getIsPrinted() {
		return this.isPrinted;
	}

	public void setIsPrinted(Boolean isPrinted) {
		this.isPrinted = isPrinted;
	}

	public Boolean getHasHalfFullCase() {
		return this.hasHalfFullCase;
	}

	public void setHasHalfFullCase(Boolean hasHalfFullCase) {
		this.hasHalfFullCase = hasHalfFullCase;
	}

	public String getPickMethod() {
		return this.pickMethod;
	}

	public void setPickMethod(String pickMethod) {
		this.pickMethod = pickMethod;
	}
	
	
	public WaveDoc getWaveDoc() {
		return waveDoc;
	}

	public void setWaveDoc(WaveDoc waveDoc) {
		this.waveDoc = waveDoc;
	}

	/**
     * 
     * <p>加执行数量</p>
     *
     * @param qty
     */
    public void execute(double qty) {
        if (qty > 0) {
            executeQty = DoubleUtil.add(executeQty, qty);
        }
    }

    /**
     * 
     * <p>减执行数量</p>
     *
     * @param qty
     */
    public void cancelExecute(double qty) {
        if (qty > 0) {
            executeQty = DoubleUtil.sub(executeQty, qty);
        }
    }

    /**
     * 
     * <p>加计划数</p>
     *
     * @param qty
     */
    public void addPlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.add(planQty, qty);
        }
    }

    /**
     * 
     * <p>减计划数</p>
     *
     * @param qty
     */
    public void removePlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.sub(planQty, qty);
        }
    }

    @Override
    public String getStatusEnum() {
        return EnuWarehouseOrderStatus.class.getSimpleName();
    }
    
    public double getUnExecuteQty(){
    	return DoubleUtil.sub(planQty, executeQty);
    }
}
