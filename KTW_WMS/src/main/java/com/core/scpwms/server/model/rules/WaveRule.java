package com.core.scpwms.server.model.rules;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuCoincidenceDegree;
import com.core.scpwms.server.enumerate.EnuShipMethod;
import com.core.scpwms.server.enumerate.EnuWaveWorkModel;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Warehouse;

@SuppressWarnings("all")
public class WaveRule extends TrackingEntity {

    /** 仓库 */
    private Warehouse wh;

    /** 波次号 */
    private String waveCode;

    /** 描述 */
    private String description;

    /**
     * 订单重合度（优先、忽略）
     * @see EnuCoincidenceDegree
     */
    private String coincidenceDegree;

    /** 最大订单数量 */
    private Double maxOrderQty = 0D;

    /** 订单品项最小 */
    private Double minOrderItems = 0D;

    /** 订单品项最大 */
    private Double maxOrderItems = 0D;

    /** 订单数量（EA）最小 */
    private Double minQty = 0D;

    /** 订单数量（EA）最大 */
    private Double maxQty = 0D;
    
    /** 订单重量（KG）最小 */
    private Double minWeight = 0D;
    
    /** 订单重量（KG）最大 */
    private Double maxWeight = 0D;
    
    /** 订单体积（M3）最小 */
    private Double minVolume = 0D;
    
    /** 订单体积（M3）最大 */
    private Double maxVolume = 0D;

    /** 订单类型 */
    private OrderType orderType;

    /**
     * 发运类型
     * @see EnuShipMethod
     */
    private String shipMethod;

    /** 路线 */
    private String route;

    /**
     * 作业方式（边拣边分、分拣库位分拣）
     * @see EnuWaveWorkModel
     */
    private String workModel;

    /**
     * 规则状态（生失效）
     */
    private Boolean disabled = Boolean.FALSE;

    /** 重量 */
    private Double weight = 0D;

    /** 体积 */
    private Double volumn = 0D;

    /** 预分配数量 */
    private Double allocatedQuantity = 0D;

    /** 订单开始时间 */
    private String orderStartTime;

    /** 订单结束时间 */
    private String orderEndTime;

    /** 
     * 执行频率设定 
     * @see EnuCronExpression
     * */
    private String cronExpression;

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public String getWaveCode() {
        return this.waveCode;
    }

    public void setWaveCode(String waveCode) {
        this.waveCode = waveCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoincidenceDegree() {
        return this.coincidenceDegree;
    }

    public void setCoincidenceDegree(String coincidenceDegree) {
        this.coincidenceDegree = coincidenceDegree;
    }

    public Double getMaxOrderQty() {
        return this.maxOrderQty;
    }

    public void setMaxOrderQty(Double maxOrderQty) {
        this.maxOrderQty = maxOrderQty;
    }

    public Double getMinOrderItems() {
        return this.minOrderItems;
    }

    public void setMinOrderItems(Double minOrderItems) {
        this.minOrderItems = minOrderItems;
    }

    public Double getMaxOrderItems() {
        return this.maxOrderItems;
    }

    public void setMaxOrderItems(Double maxOrderItems) {
        this.maxOrderItems = maxOrderItems;
    }

    public Double getMinQty() {
        return this.minQty;
    }

    public void setMinQty(Double minQty) {
        this.minQty = minQty;
    }

    public Double getMaxQty() {
        return this.maxQty;
    }

    public void setMaxQty(Double maxQty) {
        this.maxQty = maxQty;
    }

    public String getWorkModel() {
        return this.workModel;
    }

    public void setWorkModel(String workModel) {
        this.workModel = workModel;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolumn() {
        return this.volumn;
    }

    public void setVolumn(double volumn) {
        this.volumn = volumn;
    }

    public double getAllocatedQuantity() {
        return this.allocatedQuantity;
    }

    public void setAllocatedQuantity(double allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
    }

    public String getOrderStartTime() {
        return this.orderStartTime;
    }

    public void setOrderStartTime(String orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public String getOrderEndTime() {
        return this.orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getShipMethod() {
        return this.shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    public String getRoute() {
        return this.route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setVolumn(Double volumn) {
        this.volumn = volumn;
    }

    public void setAllocatedQuantity(Double allocatedQuantity) {
        this.allocatedQuantity = allocatedQuantity;
    }

    public String getCronExpression() {
        return this.cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

	public Double getMinWeight() {
		return this.minWeight;
	}

	public void setMinWeight(Double minWeight) {
		this.minWeight = minWeight;
	}

	public Double getMaxWeight() {
		return this.maxWeight;
	}

	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public Double getMinVolume() {
		return this.minVolume;
	}

	public void setMinVolume(Double minVolume) {
		this.minVolume = minVolume;
	}

	public Double getMaxVolume() {
		return this.maxVolume;
	}

	public void setMaxVolume(Double maxVolume) {
		this.maxVolume = maxVolume;
	}

}
