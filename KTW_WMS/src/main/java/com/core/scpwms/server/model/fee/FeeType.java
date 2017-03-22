package com.core.scpwms.server.model.fee;

import java.util.Date;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuFeeUnit;
import com.core.scpwms.server.enumerate.EnuInvProcessType;
import com.core.scpwms.server.enumerate.EnuStockDiv;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuType;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * <p>计费类型</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/18<br/>
 */
@SuppressWarnings("all")
public class FeeType extends TrackingEntity {
    /** 仓库 */
    private Warehouse wh;

    /**优先级*/
    private Integer priority;

    /** 名称 */
    private String name;

    /** 货主 */
    private Owner owner;

    /**
     * 计费单位
     * @see EnuFeeUnit
     */
    private String unit;
    
    /**
     * 温度带
     * @see EnuTemperatureDiv
     */
    private Long tempDiv;
    
    /**
     * 库存区分
     * @see EnuStockDiv
     */
    private Long stockDiv;

    /**
     * 作业类型
     * @see EnuFeeType
     */
    private String processType;

    /** 订单类型 */
    private OrderType orderType;

    /** 货品大类 */
    private SkuType it1000;

    /** 货品中类 */
    private SkuType it2000;

    /** 货品小类 */
    private SkuType it3000;

    /** 商品 */
    private Sku sku;

    /** 固定费用 */
    private Double fee;

    /** 是否可用 */
    private Boolean disabled = Boolean.FALSE;

    /** 描述 */
    private String description;

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getProcessType() {
        return this.processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public SkuType getIt1000() {
        return this.it1000;
    }

    public void setIt1000(SkuType it1000) {
        this.it1000 = it1000;
    }

    public SkuType getIt2000() {
        return this.it2000;
    }

    public void setIt2000(SkuType it2000) {
        this.it2000 = it2000;
    }

    public SkuType getIt3000() {
        return this.it3000;
    }

    public void setIt3000(SkuType it3000) {
        this.it3000 = it3000;
    }

    public Double getFee() {
        return this.fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Sku getSku() {
        return this.sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

	public Long getTempDiv() {
		return tempDiv;
	}

	public void setTempDiv(Long tempDiv) {
		this.tempDiv = tempDiv;
	}

	public Long getStockDiv() {
		return stockDiv;
	}

	public void setStockDiv(Long stockDiv) {
		this.stockDiv = stockDiv;
	}


}
