package com.core.scpwms.server.model.rules;

import java.util.HashSet;
import java.util.Set;

import com.core.business.model.TrackingEntityWithVer;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 库间补货策略
 * @author SUIN
 * 2013.8.1
 */
@SuppressWarnings("all")
public class WarehouseApplyRule extends TrackingEntityWithVer{
	
	/** 仓库 */
	private Warehouse wh;
	
	/** SKU*/
	private Sku sku;
	
	/** 优先级 */
	private Integer priority;
	
	/** 补货上限 */
	private Double maxStock = 0D;
	
	/** 补货下限*/
	private Double minStock = 0D;
	
	/** 补货量*/
	private Double stock = 0D;
	
	/**供应商补货策略*/
	private Set<SupplierRule> supplierRules = new HashSet<SupplierRule>();
	
	/** 生效 */
	private Boolean alive = Boolean.FALSE;

	public Boolean getAlive() {
		return alive;
	}

	public void setAlive(Boolean alive) {
		this.alive = alive;
	}

	public Warehouse getWh() {
		return wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public Double getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(Double maxStock) {
		this.maxStock = maxStock;
	}

	public Double getMinStock() {
		return minStock;
	}

	public void setMinStock(Double minStock) {
		this.minStock = minStock;
	}

	public Double getStock() {
		return stock;
	}

	public void setStock(Double stock) {
		this.stock = stock;
	}

	public Set<SupplierRule> getSupplierRules() {
		return supplierRules;
	}

	public void setSupplierRules(Set<SupplierRule> supplierRules) {
		this.supplierRules = supplierRules;
	}
	
	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}
