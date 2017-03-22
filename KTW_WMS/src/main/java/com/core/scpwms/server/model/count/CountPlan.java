package com.core.scpwms.server.model.count;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;
import com.core.scpwms.server.enumerate.EnuCountMethod;
import com.core.scpwms.server.enumerate.EnuCountStatus;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.StorageType;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * @description   盘点计划（头）
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-2-25
 * @version        V1.0<br/>
 */
public class CountPlan extends OrderTrackingEntityWithStatus {

    /**
     * 仓库
     */
    private Warehouse warehouse;

    /**
     * 公司（原货主）
     */
    private Plant plant;
    
    /**
     * 货主
     */
    private Owner owner;

    /**
     * 订单类型（盘点单）
     */
    private OrderType orderType;

    /**
     * 盘点计划号
     */
    private String code;

    /**
     * 盘点方式
     * @see EnuCountMethod
     */
    private String countMethod;

    /**
     * 描述
     */
    private String description;

    /**
     * 盘点计划生成日期
     */
    private Date planDate;

    /**
     * 动碰期间(F)
     */
    private Date dynamicCountFrom;

    /**
     * 动碰期间(T)
     */
    private Date dynamicCountTo;
    
    /**
     * 动碰区域
     */
    private StorageType st;

    /**
     * 盘点明细
     */
    private Set<CountDetail> details = new HashSet<CountDetail>();

    /**
     * 盘点记录
     */
    private Set<CountRecord> records = new HashSet<CountRecord>();

    /**
     * 如果是复盘单，需要记录上次盘点计划
     */
    private CountPlan parrentPlan;

    /**
     * 盘点库位（用户存盘点过程中产生的差异）
     */
    private Bin bin;

    /**
     * 是否带盘点锁
     */
    private Boolean binLock;

    /**
     * 是否是自动生成
     */
    private Boolean autoCreate;

    /**
     * 是否盲盘
     */
    private Boolean blindCount;

    /**
     * 理论库存数
     */
    private Double planQty;

    /**
     * 盘点数
     */
    private Double countQty;

    /**
     * 盘盈
     */
    private Double plusDeltaQty;

    /**
     * 盘亏
     */
    private Double minusDeltaQty;

    /**
     * 总重
     */
    private Double sumWeight;

    /**
     * 总体积
     */
    private Double sumVolume;

    /**
     * 总价值
     */
    private Double sumPrice;

    /**
     * 总标准度量
     */
    private Double sumMetric;

    /**
     * 品项数
     */
    private Long skuCount;

    /**
     * 批次数
     */
    private Long quantCount;

    /**
     * 抽样比(%)
     */
    private Double sampleRatio = 100D;

    public Warehouse getWarehouse() {
        return this.warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountMethod() {
        return this.countMethod;
    }

    public void setCountMethod(String countMethod) {
        this.countMethod = countMethod;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CountDetail> getDetails() {
        return this.details;
    }

    public void setDetails(Set<CountDetail> details) {
        this.details = details;
    }

    public Set<CountRecord> getRecords() {
        return this.records;
    }

    public void setRecords(Set<CountRecord> records) {
        this.records = records;
    }

    public CountPlan getParrentPlan() {
        return this.parrentPlan;
    }

    public void setParrentPlan(CountPlan parrentPlan) {
        this.parrentPlan = parrentPlan;
    }

    public Boolean getBinLock() {
        return this.binLock;
    }

    public void setBinLock(Boolean binLock) {
        this.binLock = binLock;
    }

    public Boolean getAutoCreate() {
        return this.autoCreate;
    }

    public void setAutoCreate(Boolean autoCreate) {
        this.autoCreate = autoCreate;
    }

    public Boolean getBlindCount() {
        return this.blindCount;
    }

    public void setBlindCount(Boolean blindCount) {
        this.blindCount = blindCount;
    }

    public Double getPlanQty() {
        return this.planQty;
    }

    public void setPlanQty(Double planQty) {
        this.planQty = planQty;
    }

    public Double getCountQty() {
        return this.countQty;
    }

    public void setCountQty(Double countQty) {
        this.countQty = countQty;
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

    public Long getSkuCount() {
        return this.skuCount;
    }

    public void setSkuCount(Long skuCount) {
        this.skuCount = skuCount;
    }

    public Long getQuantCount() {
        return this.quantCount;
    }

    public void setQuantCount(Long quantCount) {
        this.quantCount = quantCount;
    }

    public Double getPlusDeltaQty() {
        return this.plusDeltaQty;
    }

    public void setPlusDeltaQty(Double plusDeltaQty) {
        this.plusDeltaQty = plusDeltaQty;
    }

    public Double getMinusDeltaQty() {
        return this.minusDeltaQty;
    }

    public void setMinusDeltaQty(Double minusDeltaQty) {
        this.minusDeltaQty = minusDeltaQty;
    }

    public Double getSumPrice() {
        return this.sumPrice;
    }

    public void setSumPrice(Double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Double getSumMetric() {
        return this.sumMetric;
    }

    public void setSumMetric(Double sumMetric) {
        this.sumMetric = sumMetric;
    }

    public Bin getBin() {
        return this.bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public Date getPlanDate() {
        return this.planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Plant getPlant() {
        return this.plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Date getDynamicCountFrom() {
        return this.dynamicCountFrom;
    }

    public void setDynamicCountFrom(Date dynamicCountFrom) {
        this.dynamicCountFrom = dynamicCountFrom;
    }

    public Date getDynamicCountTo() {
        return this.dynamicCountTo;
    }

    public void setDynamicCountTo(Date dynamicCountTo) {
        this.dynamicCountTo = dynamicCountTo;
    }

    public Double getSampleRatio() {
        return this.sampleRatio;
    }

    public void setSampleRatio(Double sampleRatio) {
        this.sampleRatio = sampleRatio;
    }
    
    public StorageType getSt() {
		return this.st;
	}

	public void setSt(StorageType st) {
		this.st = st;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Override
    public String getStatusEnum() {
        return EnuCountStatus.class.getSimpleName();
    }

    public void addPlanQty(double qty) {
        planQty = DoubleUtil.add(planQty, qty);
    }

    public void removePlanQty(double qty) {
        planQty = DoubleUtil.sub(planQty, qty);
    }

}
