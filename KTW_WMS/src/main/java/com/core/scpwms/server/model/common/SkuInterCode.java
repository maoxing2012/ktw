package com.core.scpwms.server.model.common;

import com.core.db.server.model.Entity;

/**
 * 商品和国际码的关联
 * @author maoxing
 *
 */
@SuppressWarnings("all")
public class SkuInterCode extends Entity {
	private Sku sku;
	
	private String barCode;

	private PackageDetail packDetail;
	
	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public PackageDetail getPackDetail() {
		return packDetail;
	}

	public void setPackDetail(PackageDetail packDetail) {
		this.packDetail = packDetail;
	}

}
