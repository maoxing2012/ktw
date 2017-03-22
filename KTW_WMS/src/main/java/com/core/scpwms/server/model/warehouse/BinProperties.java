package com.core.scpwms.server.model.warehouse;

import com.core.business.model.TrackingEntity;

/**
 * 仓库别库位存储属性设定 包含： 长、宽、高、容量限定（数量、重量、体积）、库满度（%）、标准度量、满载托盘
 * 
 * 托盘存储设定（托盘数）
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 * 
 */
@SuppressWarnings("all")
public class BinProperties extends TrackingEntity {

	/** 所属仓库 */
	private Warehouse wh;

	/** 编号 */
	private String code;

	/** 名称 */
	private String name;
	/**
	 * 长(M)
	 */
	private Double length = 0D;
	/**
	 * 宽(M)
	 */
	private Double width = 0D;
	/**
	 * 高(M)
	 */
	private Double height = 0D;
	/**
	 * 体积(M3)
	 */
	private Double volume = 0D;
	/**
	 * 重量(KG)
	 */
	private Double weight = 0D;
	/**
	 * 数量(MU)
	 */
	private Double quantity = 0D;

	/** 库满度上限（%） */
	private Double fullScale = 0D;

	/** 托盘数 */
	private Double palletCount = 0D;

	/** 标准度量 */
	private Double measure = 0D;

	/** 是否失效 */
	private Boolean disabled = Boolean.FALSE;

	/** 是否托盘库位 */
	private Boolean isPalletBin = Boolean.FALSE;

	/** 是否逻辑库位 */
	private Boolean isLogicBin = Boolean.FALSE;

	/** 描述 */
	private String description;

	public Double getMeasure() {
		return measure;
	}

	public void setMeasure(Double measure) {
		this.measure = measure;
	}

	public Warehouse getWh() {
		return wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getFullScale() {
		return fullScale;
	}

	public void setFullScale(Double fullScale) {
		this.fullScale = fullScale;
	}

	public Double getPalletCount() {
		return palletCount;
	}

	public void setPalletCount(Double palletCount) {
		this.palletCount = palletCount;
	}

	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsPalletBin() {
		return this.isPalletBin;
	}

	public void setIsPalletBin(Boolean isPalletBin) {
		this.isPalletBin = isPalletBin;
	}

	public Boolean getIsLogicBin() {
		return this.isLogicBin;
	}

	public void setIsLogicBin(Boolean isLogicBin) {
		this.isLogicBin = isLogicBin;
	}

}
