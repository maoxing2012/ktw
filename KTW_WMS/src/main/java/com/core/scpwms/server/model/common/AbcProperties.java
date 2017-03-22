package com.core.scpwms.server.model.common;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * ABC分类设定
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class AbcProperties extends TrackingEntity {
	
	/** 仓库 */
	private Warehouse warehouse;

	/** 货主 */
	private Plant plant;
	
	/** 代码 */
	private String code;
	
	/** 名称 */
	private String name;
	
	/** 统计占比 */
	private Double rate = 0.0D;
	
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

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}
