package com.core.scpwms.server.model.warehouse;

import java.util.HashSet;
import java.util.Set;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuStoreRole;

/**
 * 库位组
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class BinGroup extends TrackingEntity {

    /** 所属仓库 */
    private Warehouse wh;

    /** 库区 */
    private WhArea wa;

    /** 作业类别 
     * @see EnuStoreRole 
     * */
    private String role;

    /** 代码 */
    private String code;

    /** 名称 */
    private String name;

    /** 库位组类型 */
    private String bgType;

    /** 包含库位*/
    private Set<Bin> bins = new HashSet<Bin>();

    /** 是否失效 默认：生效*/
    private Boolean disabled = Boolean.FALSE;
    
    /** 备注 */
    private String description;

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public WhArea getWa() {
        return this.wa;
    }

    public void setWa(WhArea wa) {
        this.wa = wa;
    }

    public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
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

    public String getBgType() {
        return this.bgType;
    }

    public void setBgType(String bgType) {
        this.bgType = bgType;
    }

    public Set<Bin> getBins() {
        return this.bins;
    }

    public void setBins(Set<Bin> bins) {
        this.bins = bins;
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
