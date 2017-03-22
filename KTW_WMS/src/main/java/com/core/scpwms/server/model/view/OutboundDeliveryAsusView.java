package com.core.scpwms.server.model.view;

import com.core.db.server.model.DomainModel;

/**
 * 
 * <p>没有加入WO的拣货WT，按照OBDID分组的View表</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015年12月23日<br/>
 */
public class OutboundDeliveryAsusView extends DomainModel {
	private Long id;
	private Long whId;
	/** 出库单号 */
	private String obdNumber;
	/** Trip单号 */
	private String tripNumber;
	/** 承运商名称 */
	private String carrierName;
	/** 总数量 */
	private Double sumQty;
	/** 品项数 */
	private Double skuCount;
	/** 总重量 */
	private Double sumWeight;
	/** 总体积 */
	private Double sumVolume;
	/** 总箱数 */
	private Double sumCaseQty;
	/** 总任务数 */
	private Double countWt;
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getObdNumber() {
		return this.obdNumber;
	}
	public void setObdNumber(String obdNumber) {
		this.obdNumber = obdNumber;
	}
	public String getTripNumber() {
		return this.tripNumber;
	}
	public void setTripNumber(String tripNumber) {
		this.tripNumber = tripNumber;
	}
	public String getCarrierName() {
		return this.carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public Double getSumQty() {
		return this.sumQty;
	}
	public void setSumQty(Double sumQty) {
		this.sumQty = sumQty;
	}
	public Double getSkuCount() {
		return this.skuCount;
	}
	public void setSkuCount(Double skuCount) {
		this.skuCount = skuCount;
	}
	public Double getSumWeight() {
		return this.sumWeight;
	}
	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}
	public Double getSumVolume() {
		return this.sumVolume;
	}
	public void setSumVolume(Double sumVolume) {
		this.sumVolume = sumVolume;
	}
	public Double getSumCaseQty() {
		return this.sumCaseQty;
	}
	public void setSumCaseQty(Double sumCaseQty) {
		this.sumCaseQty = sumCaseQty;
	}
	public Double getCountWt() {
		return this.countWt;
	}
	public void setCountWt(Double countWt) {
		this.countWt = countWt;
	}
	public Long getWhId() {
		return this.whId;
	}
	public void setWhId(Long whId) {
		this.whId = whId;
	}
}
