/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: ReasonCode.java
 */

package com.core.scpwms.server.model.common;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuInvStatus;

/**
 * <p>原因码</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/06/27<br/>
 */
@SuppressWarnings("all")
public class ReasonCode extends TrackingEntity {

    /** 编码 */
    private String code;

    /** 名称 */
    private String name;

    /** 业务类型
     * @see EnuInvStatus 
     */
    private String type;

    /** 是否可用 */
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

    public String getType() {
        return this.type;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
