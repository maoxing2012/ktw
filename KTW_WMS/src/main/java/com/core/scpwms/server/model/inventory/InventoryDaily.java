package com.core.scpwms.server.model.inventory;

import com.core.business.model.TrackingEntity;
import com.core.db.server.model.Entity;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * 库存基数
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class InventoryDaily extends TrackingEntity {

    /**
     * 仓库
     */
    private Warehouse wh;

    /**
     * 比较版本
     */
    private String invVersion;
    
    /**
     * 分布场所
     */
    private Owner owner;
    
    /**
     * 分布场所(冗余)
     */
    private String ownerCode;    

    /** 
     * Quant信息 
     */
    private Quant quant;
    
    /**
     * Quant信息 (冗余)
     */
    private String dispLot;   
    
    /**
     * Quant信息 (冗余)
     */
    private String skuCode;      
    
    /** 
     * 基本单位数量
     */
    private Double baseQty = 0D;


    /** 
     * 库存可用状态 
     * 受限于库位的Lock属性
     * @see EnuInvStatus
     */
    private String status = EnuInvStatus.AVAILABLE;

    /** 
     * 库存可用状态 (冗余)
     */
    private String statusCode;
    
    /**
     * 工程号
     */
    private String trackSeq;
    

	public Warehouse getWh() {
		return wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Quant getQuant() {
		return quant;
	}

	public void setQuant(Quant quant) {
		this.quant = quant;
	}

	public Double getBaseQty() {
		return baseQty;
	}

	public void setBaseQty(Double baseQty) {
		this.baseQty = baseQty;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrackSeq() {
		return trackSeq;
	}

	public void setTrackSeq(String trackSeq) {
		this.trackSeq = trackSeq;
	}

	public String getInvVersion() {
		return invVersion;
	}

	public void setInvVersion(String invVersion) {
		this.invVersion = invVersion;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getDispLot() {
		return dispLot;
	}

	public void setDispLot(String dispLot) {
		this.dispLot = dispLot;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	
	

}
