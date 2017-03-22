package com.core.scpwms.server.model.warehouse;

import com.core.business.model.TrackingEntity;

/**
 * 仓库区域划分、主要用来控制RF作业区域指定。无实际意义
 * 可用来初始化所有物理区域（StorageType）
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class WhArea extends TrackingEntity {

    /** 所属仓库*/
    private Warehouse wh;

    /** 代码 */
    private String code;

    /** 名称 */
    private String name;

    /** 是否失效 默认：生效*/
    private Boolean disabled = Boolean.FALSE;
    
    /** 描述 */
    private String description;

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

}
