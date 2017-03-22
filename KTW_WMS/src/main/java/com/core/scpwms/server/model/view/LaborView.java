package com.core.scpwms.server.model.view;

import com.core.db.server.model.DomainModel;

/**
 * 
 * <p>
 * 作业人员视图
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014年9月25日<br/>
 */
public class LaborView extends DomainModel {
	private Long id;
	
	private Long whId;
	
	private String name;

	private String code;

	private String type;

	private String lgNm;
	
    private Boolean disabled = Boolean.FALSE;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLgNm() {
		return this.lgNm;
	}

	public void setLgNm(String lgNm) {
		this.lgNm = lgNm;
	}

	public Long getWhId() {
		return this.whId;
	}

	public void setWhId(Long whId) {
		this.whId = whId;
	}

	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

}
