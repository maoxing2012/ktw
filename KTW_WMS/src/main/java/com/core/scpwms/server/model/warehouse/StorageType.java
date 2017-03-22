package com.core.scpwms.server.model.warehouse;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.domain.PrintInfo;
import com.core.scpwms.server.enumerate.EnuStoreRole;

/**
 * 物理功能区，包含库区作业属性（StoreRole）
 * 所属库区
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class StorageType extends TrackingEntity {

    /** 所属库区*/
    private WhArea wa;

    /** 代码 */
    private String code;

    /** 名称 */
    private String name;

    /** 作业类别 
     * @see EnuStoreRole 
     * */
    private String role;

    /** 是否失效 默认：生效*/
    private Boolean disabled = Boolean.FALSE;

    /** 
     * 库位编码格式
     * 两位前缀  分隔符   过道   分隔符     开间   层 列
     */
    private String maskFormat;
    
    /**
     * 温度带
     */
    private Long tempDiv;
    
    public WhArea getWa() {
        return this.wa;
    }

    public void setWa(WhArea wa) {
        this.wa = wa;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getMaskFormat() {
        return maskFormat;
    }

    public void setMaskFormat(String maskFormat) {
        this.maskFormat = maskFormat;
    }

	public Long getTempDiv() {
		return tempDiv;
	}

	public void setTempDiv(Long tempDiv) {
		this.tempDiv = tempDiv;
	}
	
}
