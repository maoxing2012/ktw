package com.core.scpwms.server.model.common;

import com.core.db.server.model.Entity;
import com.core.scpwms.server.model.user.BizOrg;

/**
 * @description   商品-供应商
 * @author s1203730
 * @createDate 2013/07/23
 * @version    V1.0
 */
@SuppressWarnings("all")
public class SkuSupplier extends Entity{
	
	/** 供应商 */
    private BizOrg supplier;
    
    /** 商品 */
    private Sku sku;
    
    /** 描述 */
    private String description;
    
    /**
     * 是否默认供应商
     */
    private Boolean defSupplier;

	public BizOrg getSupplier() {
		return supplier;
	}

	public void setSupplier(BizOrg supplier) {
		this.supplier = supplier;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDefSupplier() {
		return this.defSupplier;
	}

	public void setDefSupplier(Boolean defSupplier) {
		this.defSupplier = defSupplier;
	}

	
	
}
