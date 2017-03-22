package com.core.scpwms.server.domain;

import java.util.Date;

import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.enumerate.EnuFeeUnit;

/**
 * 
 * <p>
 * 下订单时确定需要明确的信息
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/17<br/>
 */
@SuppressWarnings("all")
public class LaborFee extends DomainModel {
	/**
	 * 计费单位
	 * 
	 * @see EnuFeeUnit
	 */
	private String unit;

	/** 单价 */
	private Double unitFee;

	/** 费率名 */
	private String feeTypeName;

	/** 总实际费用 */
	private Double realTotalFee;

	/** 分摊到的费用 */
	private Double fee;

	/** 作业时间 */
	private Date executeDate;

	/** 标准度量 */
	private Double weight;

	/** 标准度量 */
	private Double volume;

	/** 标准度量 */
	private Double metric;

	/** 金额 */
	private Double price;

	/** 修改后的费用 */
	private Double editFee;

	/** 修改次数 */
	private Integer editCount;

	/** 修改原因 */
	private String editReason;

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getUnitFee() {
		return this.unitFee;
	}

	public void setUnitFee(Double unitFee) {
		this.unitFee = unitFee;
	}

	public String getFeeTypeName() {
		return this.feeTypeName;
	}

	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}

	public Double getRealTotalFee() {
		return this.realTotalFee;
	}

	public void setRealTotalFee(Double realTotalFee) {
		this.realTotalFee = realTotalFee;
	}

	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Date getExecuteDate() {
		return this.executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public Double getWeight() {
		return this.weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getVolume() {
		return this.volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getMetric() {
		return this.metric;
	}

	public void setMetric(Double metric) {
		this.metric = metric;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getEditFee() {
		return this.editFee;
	}

	public void setEditFee(Double editFee) {
		this.editFee = editFee;
	}

	public Integer getEditCount() {
		return this.editCount;
	}

	public void setEditCount(Integer editCount) {
		this.editCount = editCount;
	}

	public String getEditReason() {
		return this.editReason;
	}

	public void setEditReason(String editReason) {
		this.editReason = editReason;
	}

	public void clearFee() {
		unit = null;
		unitFee = null;
		feeTypeName = null;
		realTotalFee = null;
		fee = null;
		editFee = null;
	}
}
