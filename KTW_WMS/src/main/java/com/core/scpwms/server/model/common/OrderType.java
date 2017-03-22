package com.core.scpwms.server.model.common;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.model.user.Plant;

@SuppressWarnings("all")
public class OrderType extends TrackingEntity {

    /** 代码 */
    private String code;

    /** 名称 */
    private String name;

    /** 单据属性 */
    private SequenceProperties seqProperties;

    /** 
     * 作业类型
     * @see EnuInvProcessType
     */
    private String processType;

    /** 系统单据标志位 */
    private Boolean systemType = Boolean.FALSE;
    
    /** 周转统计标志 */
    private Boolean urnoverCount = Boolean.TRUE;
    
    /** 是否计费预配标志（true：预配 false：不预配） */
    private Boolean preAllocate = Boolean.TRUE;
    
    /** 是否有效 */
    private Boolean disabled = Boolean.FALSE;

    /** 描述 */
    private String description;

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

	public SequenceProperties getSeqProperties() {
		return this.seqProperties;
	}

	public void setSeqProperties(SequenceProperties seqProperties) {
		this.seqProperties = seqProperties;
	}

	public String getProcessType() {
		return this.processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
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

    public Boolean getSystemType() {
        return this.systemType;
    }

    public void setSystemType(Boolean systemType) {
        this.systemType = systemType;
    }

	public Boolean getUrnoverCount() {
		return this.urnoverCount;
	}

	public void setUrnoverCount(Boolean urnoverCount) {
		this.urnoverCount = urnoverCount;
	}

    public Boolean getPreAllocate() {
        return this.preAllocate;
    }

    public void setPreAllocate(Boolean preAllocate) {
        this.preAllocate = preAllocate;
    }

}
