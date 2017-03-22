package com.core.scpwms.server.model.rules;
import com.core.business.model.TrackingEntityWithVer;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 库位补货策略
 * @author SUIN
 * 2013.8.1
 */
@SuppressWarnings("all")
public class BinRule extends TrackingEntityWithVer{
	
	/** 库内补货*/
	private InventoryApplyRule invApplyRule = new InventoryApplyRule();
	
	/** 拣货库位*/
	private Bin srcBin;
	
	/**补货比例 */
	private Double rate = 0D;
	
	/**优先级*/
	private Integer priority;

	public InventoryApplyRule getInvApplyRule() {
		return invApplyRule;
	}

	public void setInvApplyRule(InventoryApplyRule invApplyRule) {
		this.invApplyRule = invApplyRule;
	}


	public Bin getSrcBin() {
		return srcBin;
	}

	public void setSrcBin(Bin srcBin) {
		this.srcBin = srcBin;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}
