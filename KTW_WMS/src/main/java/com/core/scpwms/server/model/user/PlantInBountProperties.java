package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuAllocateType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.PackageInfo;

/**
 * 货主作业设定（收货），包含如下内容指定
 * 
 * 缺省包装信息（PackageInfo）
 * 库位指定方式（人工指定库位；收货时计算库位）
 * 缺省预分配规则
 * 入库标签
 * 溢收限定
 * 溢收百分比限定
 * 缺省收货单位（ASN上未指定）
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class PlantInBountProperties extends TrackingEntity {

	/**
	 * 货主
	 */
	private Plant plant;
	
	/**
	 * 缺省包装信息
	 */
	private PackageInfo PackageInfo;
	
	/**
	 * 库位指定方式
	 * @see EnuAllocateType
	 */
	private String allocateType;
	
	/**
	 * 预分配
	 * 将作为产品的缺省预分配规则
	 */
	private Boolean preAllocate = Boolean.FALSE;
	
	/**
	 * 打印标签制定
	 */
	private String inboundLabel;
	
	/**
	 * 溢收限定（false:能溢收，true:不能溢收）
	 **/
	private Boolean ibQtyLimit = Boolean.FALSE;		
	
	/**
	 * 溢收%
	 **/
	private Double ibLimit = 0D;	
	
	/**
	 * 缺省收货单位
	 * 收货单位优先级 
	 * ASN > SKU Properties > inboundProperties
	 * @see PackageInfo
	 */
	private PackageDetail packageDetail;
	
	public Plant getPlant() {
		return this.plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public PackageInfo getPackageInfo() {
		return this.PackageInfo;
	}

	public void setPackageInfo(PackageInfo packageInfo) {
		PackageInfo = packageInfo;
	}

	public String getAllocateType() {
		return this.allocateType;
	}

	public void setAllocateType(String allocateType) {
		this.allocateType = allocateType;
	}

	public Boolean getPreAllocate() {
		return this.preAllocate;
	}

	public void setPreAllocate(Boolean preAllocate) {
		this.preAllocate = preAllocate;
	}

	public String getInboundLabel() {
		return this.inboundLabel;
	}

	public void setInboundLabel(String inboundLabel) {
		this.inboundLabel = inboundLabel;
	}

	public Boolean getIbQtyLimit() {
		return this.ibQtyLimit;
	}

	public void setIbQtyLimit(Boolean ibQtyLimit) {
		this.ibQtyLimit = ibQtyLimit;
	}

	public Double getIbLimit() {
		return this.ibLimit;
	}

	public void setIbLimit(Double ibLimit) {
		this.ibLimit = ibLimit;
	}

	public PackageDetail getPackageDetail() {
		return this.packageDetail;
	}

	public void setPackageDetail(PackageDetail packageDetail) {
		this.packageDetail = packageDetail;
	}

}
