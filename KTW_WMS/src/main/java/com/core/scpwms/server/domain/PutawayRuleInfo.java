package com.core.scpwms.server.domain;

import java.util.Date;

import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 
 * <p>匹配上架策略需要的属性</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/17<br/>
 */
@SuppressWarnings("all")
public class PutawayRuleInfo extends DomainModel {
	private Long whId;
	
	private Long srcBinId;
	
	private Long plantId;
	
	private Long skuId;
	
	private Long orderTypeId;
	
	private String invStatus;
	
	private Long abcTypeId;
	
	private Long bTypeId;
	
	private Long mTypeId;
	
	private Long lTypeId;
	
	private String brandName;
	
	private Boolean bePallet = Boolean.FALSE;

	private Long packInfoId;
	
	private Long packDetailId;
	
	private Double toPutawayBaseQty;
	
	private Double toPutawayPackQty;
	
	private Double toPutawayPalletQty;

	private String packLevel;

	public Long getWhId() {
		return this.whId;
	}

	public void setWhId(Long whId) {
		this.whId = whId;
	}

	public Long getSrcBinId() {
		return this.srcBinId;
	}

	public void setSrcBinId(Long srcBinId) {
		this.srcBinId = srcBinId;
	}

	public Long getPlantId() {
		return this.plantId;
	}

	public void setPlantId(Long plantId) {
		this.plantId = plantId;
	}

	public Long getSkuId() {
		return this.skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public Long getOrderTypeId() {
		return this.orderTypeId;
	}

	public void setOrderTypeId(Long orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public String getInvStatus() {
		return this.invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public Long getAbcTypeId() {
		return this.abcTypeId;
	}

	public void setAbcTypeId(Long abcTypeId) {
		this.abcTypeId = abcTypeId;
	}

	public Long getbTypeId() {
		return this.bTypeId;
	}

	public void setbTypeId(Long bTypeId) {
		this.bTypeId = bTypeId;
	}

	public Long getmTypeId() {
		return this.mTypeId;
	}

	public void setmTypeId(Long mTypeId) {
		this.mTypeId = mTypeId;
	}

	public Long getlTypeId() {
		return this.lTypeId;
	}

	public void setlTypeId(Long lTypeId) {
		this.lTypeId = lTypeId;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Boolean getBePallet() {
		return this.bePallet;
	}

	public void setBePallet(Boolean bePallet) {
		this.bePallet = bePallet;
	}

	public Long getPackInfoId() {
		return this.packInfoId;
	}

	public void setPackInfoId(Long packInfoId) {
		this.packInfoId = packInfoId;
	}

	public Long getPackDetailId() {
		return this.packDetailId;
	}

	public void setPackDetailId(Long packDetailId) {
		this.packDetailId = packDetailId;
	}

	public Double getToPutawayBaseQty() {
		return this.toPutawayBaseQty;
	}

	public void setToPutawayBaseQty(Double toPutawayBaseQty) {
		this.toPutawayBaseQty = toPutawayBaseQty;
	}

	public Double getToPutawayPackQty() {
		return this.toPutawayPackQty;
	}

	public void setToPutawayPackQty(Double toPutawayPackQty) {
		this.toPutawayPackQty = toPutawayPackQty;
	}

	public Double getToPutawayPalletQty() {
		return this.toPutawayPalletQty;
	}

	public void setToPutawayPalletQty(Double toPutawayPalletQty) {
		this.toPutawayPalletQty = toPutawayPalletQty;
	}

	public String getPackLevel() {
		return this.packLevel;
	}

	public void setPackLevel(String packLevel) {
		this.packLevel = packLevel;
	}
	
}
