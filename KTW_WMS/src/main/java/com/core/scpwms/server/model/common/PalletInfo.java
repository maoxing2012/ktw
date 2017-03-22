package com.core.scpwms.server.model.common;

import com.core.db.server.model.DomainModel;

/**
 * 包装码盘信息
 * （单品）
 * 复杂码盘（ASN）不作为拣选拆托盘的标准
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class PalletInfo extends DomainModel {
	
	/***
	 * 包装信息
	 */
	private PackageDetail packageDetail;
	
	/** 单层码放数 */
	private Integer ti = 0;
	
	/** 堆高层数 */
	private Integer hi = 0;
	
	/** 托盘满托长 默认为0 */
	private Double length = 0D;
	
	/** 托盘满托宽 默认为0*/
	private Double width = 0D;
	
	/** 托盘满托高 默认为0*/
	private Double height = 0D;
	
	public PackageDetail getPackageDetail() {
		return packageDetail;
	}
	public void setPackageDetail(PackageDetail packageDetail) {
		this.packageDetail = packageDetail;
	}
	public Integer getTi() {
		return ti;
	}
	public void setTi(Integer ti) {
		this.ti = ti;
	}
	public Integer getHi() {
		return hi;
	}
	public void setHi(Integer hi) {
		this.hi = hi;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	
}
