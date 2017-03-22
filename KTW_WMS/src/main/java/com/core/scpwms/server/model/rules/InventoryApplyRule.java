package com.core.scpwms.server.model.rules;

import com.core.business.model.TrackingEntityWithVer;
import com.core.scpwms.server.model.common.AbcProperties;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.PackageInfo;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinGroup;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 库内补货策略
 * @author SUIN
 * 2013.8.1
 */
@SuppressWarnings("all")
public class InventoryApplyRule extends TrackingEntityWithVer{
	
	/** 仓库 */
	private Warehouse wh;
	
	/** SKU*/
	private Sku sku;
	
	/** 商品所在库位*/
	private Bin bin; 
	
	/** 拣选库位组*/
	private BinGroup descBinGroup;
	
	/** 补货上限 */
	private Double maxStock = 0D;
	
	/** 补货下限*/
	private Double minStock = 0D;
	
	/** 补货量*/
	private Double stock = 0D;
	
	/** 优先级 */
	private Integer priority;
	
	/** 生效 */
	private Boolean alive = Boolean.FALSE;
	
	/** 货主 */
	private Plant plant;
	
	/** 
	 * 上架策略中，包装级别，应对如下的情况
	 * 包装量非固定，但是又需要按照包装进行上架区分
	 * @see EnuPackageLevel
	 * */
	private String packageLevel;
	
	/** ABC分类*/
	private AbcProperties abcProperties;

	public String getPackageLevel() {
		return packageLevel;
	}

	public void setPackageLevel(String packageLevel) {
		this.packageLevel = packageLevel;
	}

	public AbcProperties getAbcProperties() {
		return this.abcProperties;
	}

	public void setAbcProperties(AbcProperties abcProperties) {
		this.abcProperties = abcProperties;
	}

	public Plant getPlant() {
		return this.plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

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

	public Bin getBin() {
		return bin;
	}

	public void setBin(Bin bin) {
		this.bin = bin;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public BinGroup getDescBinGroup() {
		return descBinGroup;
	}

	public void setDescBinGroup(BinGroup descBinGroup) {
		this.descBinGroup = descBinGroup;
	}
	
	
	
}
