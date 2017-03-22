package com.core.scpwms.server.model.common;

import com.core.business.model.TrackingEntity;

/**
 * SKU类别
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class SkuType extends TrackingEntity {
    /** 代码 */
    private String code;

    /** 名称 */
    private String name;

    /** SKU类别所属的类型 
     * @see EnuSkuType
     * */
    private String typeKind;

    /** 标准度量折算系数（ 标准度量 = 体积×折算系数，如果折算系数为空，标准度量=重量） */
    private Double metricRate;

    /** IF接收 */
    private Boolean hubif = Boolean.TRUE;
    
    /** 抽检比例 */
	private Double extDouble1;

    /** 是否可用 */
    private Boolean disabled = Boolean.FALSE;

    /** 描述 */
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeKind() {
        return typeKind;
    }

    public void setTypeKind(String typeKind) {
        this.typeKind = typeKind;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getHubif() {
        return this.hubif;
    }

    public void setHubif(Boolean hubif) {
        this.hubif = hubif;
    }

    public Double getMetricRate() {
        return this.metricRate;
    }

    public void setMetricRate(Double metricRate) {
        this.metricRate = metricRate;
    }

	public Double getExtDouble1() {
		return this.extDouble1;
	}

	public void setExtDouble1(Double extDouble1) {
		this.extDouble1 = extDouble1;
	}
    
}
