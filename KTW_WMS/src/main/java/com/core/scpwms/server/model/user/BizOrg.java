package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;
import com.core.business.model.domain.ContractInfo;

/**
 * 业务角色集群表
 * 客户(经营单位)/供应商/仓库/雇员
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class BizOrg extends TrackingEntity {
	/** 公司 */
	private Plant plant;
	
	/** 货主 */
	private Owner owner;
	
    /** 代码 */
    private String code;

    /** 名称 */
    private String name;

    /** 简称 */
    private String shortName;

    /** 联系方式 */
    private ContractInfo contractInfo;

    /** 作业设定*/
    private BizOrgProperties bizOrgProperties;

    /**类型type
     * @see EnuBusRolsType
     * */
    private String type;

    /**是否失效 */
    private Boolean disabled = Boolean.FALSE;

    /** 描述 */
    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ContractInfo getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(ContractInfo contractInfo) {
        this.contractInfo = contractInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BizOrgProperties getBizOrgProperties() {
        return this.bizOrgProperties;
    }

    public void setBizOrgProperties(BizOrgProperties bizOrgProperties) {
        this.bizOrgProperties = bizOrgProperties;
    }

	public Plant getPlant() {
		return this.plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

}
