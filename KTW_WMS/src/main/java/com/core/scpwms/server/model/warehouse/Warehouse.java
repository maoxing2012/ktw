package com.core.scpwms.server.model.warehouse;

import java.util.Set;

import com.core.business.model.TrackingEntity;
import com.core.business.model.domain.ContractInfo;

/**
 * 仓库基本类 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class Warehouse extends TrackingEntity {

    /** 代码 */
    private String code;

    /** 名称 */
    private String name;

    /** 简称 */
    private String shortName;
    
    /** 联系方式 */
    private ContractInfo contractInfo;

    /** 用户仓库 */
    private Set<UserGroupWarehouse> userGroupWarehouses;

    /** 仓库详细设定 */
    private WarehouseProperties properties;

    /** 是否失效 :默认生效*/
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

    public Set<UserGroupWarehouse> getUserGroupWarehouses() {
        return userGroupWarehouses;
    }

    public void setUserGroupWarehouses(Set<UserGroupWarehouse> userGroupWarehouses) {
        this.userGroupWarehouses = userGroupWarehouses;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public WarehouseProperties getProperties() {
        return this.properties;
    }

    public void setProperties(WarehouseProperties properties) {
        this.properties = properties;
    }
    
}
