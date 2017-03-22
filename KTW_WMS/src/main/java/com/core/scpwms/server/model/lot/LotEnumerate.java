package com.core.scpwms.server.model.lot;

import java.util.HashSet;
import java.util.Set;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuLotFormat;

/**
 * 如果批次属性选择[选择框]
 * 则此处作为CODE Master来描述未来业务中遇到此选择框，选择框显示的值
 * @see EnuLotFormat
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class LotEnumerate extends TrackingEntity {

    /** 编号 */
    private String code;

    /** 名称 */
    private String name;

    /**
     * 包含详细内容
     */
    private Set<LotEnumerateDetail> details = new HashSet<LotEnumerateDetail>();

    /** 是否可用 */
    private Boolean disabled = Boolean.FALSE;

    /** 
     * 属性Key描述
     * 将明细内容的DispName属性，并列显示
     * */
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

    public Set<LotEnumerateDetail> getDetails() {
        return this.details;
    }

    public void setDetails(Set<LotEnumerateDetail> details) {
        this.details = details;
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
