package com.core.scpwms.server.model.rules;
import com.core.business.model.TrackingEntityWithVer;
import com.core.scpwms.server.model.user.BizOrg;

/**
 * 供应商补货策略
 * @author SUIN
 * 2013.8.1
 */
@SuppressWarnings("all")
public class SupplierRule extends TrackingEntityWithVer{
	
	/** 库间补货*/
	private WarehouseApplyRule whApplyRule = new WarehouseApplyRule();
	
	/** 供应商*/
	private BizOrg supplier;
	
	/**补货比例 */
	private Double rate = 0D;

	public WarehouseApplyRule getWhApplyRule() {
		return whApplyRule;
	}

	public void setWhApplyRule(WarehouseApplyRule whApplyRule) {
		this.whApplyRule = whApplyRule;
	}

	public BizOrg getSupplier() {
		return supplier;
	}

	public void setSupplier(BizOrg supplier) {
		this.supplier = supplier;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
}
