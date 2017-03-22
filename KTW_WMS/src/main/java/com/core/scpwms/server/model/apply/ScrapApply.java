package com.core.scpwms.server.model.apply;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;
import com.core.scpwms.server.enumerate.EnuApplyStatus;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * <p>
 * 破损报废申请单
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/23<br/>
 */
@SuppressWarnings("all")
public class ScrapApply extends OrderTrackingEntityWithStatus {

	/** 仓库信息 */
	private Warehouse wh;

	/** 公司 */
	private Plant plant;
	
	/** 货主 */
	private Owner owner;
	
	/** 仓库破损单 */
	private OrderType orderType;
	
    /** 申请日期 */
    private Date applyDate;

	/** 申请编号 */
	private String applyNumber;

	/** 申请数量(EA) */
	private double qty = 0d;
	
	/** 品相数 */
	private double skuCount = 0d;

	/** 备注 */
	private String description;

	/** 报废明细 */
	private Set<ScrapApplyDetail> details = new HashSet<ScrapApplyDetail>();

	@Override
	public String getStatusEnum() {
		return EnuApplyStatus.class.getSimpleName();
	}

	public Warehouse getWh() {
		return wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public String getApplyNumber() {
		return applyNumber;
	}

	public void setApplyNumber(String applyNumber) {
		this.applyNumber = applyNumber;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<ScrapApplyDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<ScrapApplyDetail> details) {
		this.details = details;
	}

	public double getSkuCount() {
		return skuCount;
	}

	public void setSkuCount(double skuCount) {
		this.skuCount = skuCount;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}


}
