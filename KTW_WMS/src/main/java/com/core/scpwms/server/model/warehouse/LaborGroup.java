package com.core.scpwms.server.model.warehouse;

import java.util.Set;

import com.core.business.model.TrackingEntity;

/**
 * 
 * @description 作业组
 * @author MBP:xiaoyan<br/>
 * @createDate 2014-1-2
 * @version V1.0<br/>
 */
@SuppressWarnings("all")
public class LaborGroup extends TrackingEntity {

	/** 所属仓库 */
	private Warehouse wh;

	/** 作业组代码 */
	private String code;

	/** 作业组名称 */
	private String name;

	/**
	 * 作作业组类别
	 * 
	 * @see EnuLaborGroupType
	 * */
	private String role;

	/** 是否失效 */
	private Boolean disabled = Boolean.FALSE;

	private Set<LaborGroupLabor> labors;

	public Warehouse getWh() {
		return this.wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
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

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Set<LaborGroupLabor> getLabors() {
		return this.labors;
	}

	public void setLabors(Set<LaborGroupLabor> labors) {
		this.labors = labors;
	}

}
