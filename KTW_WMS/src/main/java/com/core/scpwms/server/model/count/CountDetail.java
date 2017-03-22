package com.core.scpwms.server.model.count;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 
 * @description 盘点计划（明细）
 * @author MBP:xiaoyan<br/>
 * @createDate 2014-2-25
 * @version V1.0<br/>
 */
public class CountDetail extends TrackingEntity {

	/**
	 * 计划号
	 */
	private CountPlan countPlan;

	/**
	 * 从商品盘
	 */
	private Sku sku;

	/**
	 * 从库位盘
	 */
	private Bin bin;

	/**
	 * 行号
	 */
	private int lineNumber;

	/**
	 * 参与盘点(因为抽样比的原因,加入计划的商品或者库位可能不参与盘点)
	 */
	private Boolean toCount = Boolean.FALSE;

	public CountPlan getCountPlan() {
		return this.countPlan;
	}

	public void setCountPlan(CountPlan countPlan) {
		this.countPlan = countPlan;
	}

	public Sku getSku() {
		return this.sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public Bin getBin() {
		return this.bin;
	}

	public void setBin(Bin bin) {
		this.bin = bin;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Boolean getToCount() {
		return this.toCount;
	}

	public void setToCount(Boolean toCount) {
		this.toCount = toCount;
	}

}
