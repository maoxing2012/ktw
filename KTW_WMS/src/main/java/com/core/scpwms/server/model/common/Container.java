package com.core.scpwms.server.model.common;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuContainerType;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 库内容器
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class Container extends TrackingEntity {
	
	/** 仓库 */
	private Warehouse warehouse;
	
	/** 代码 */
	private String code;
	
	/** 名称 */
	private String name;
	
	/** 容器类型 
	 * @see EnuContainerType
	 * */
	private String containerType;

	/** 重量 */
	private Double weight = 0D;
	
	/** 体积 */
	private Double volume = 0D;
	
	/** 长 */
	private Double length = 0D;
	
	/** 宽 */
	private Double width = 0D;
	
	/** 高 */
	private Double height = 0D;
	
	/** 是否失效 */
	private Boolean disabled = Boolean.FALSE;
	
	/** 描述 */
	private String description;

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
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

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Boolean getDisabled() {
		return disabled;
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

	public Double getLength() {
		return this.length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return this.width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return this.height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

}
