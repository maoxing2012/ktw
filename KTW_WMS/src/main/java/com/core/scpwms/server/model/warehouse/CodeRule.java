package com.core.scpwms.server.model.warehouse;

import com.core.business.model.TrackingEntity;

public class CodeRule extends TrackingEntity {
	
	/** 编号 */
	private String code;	
	
	/** 名称 */
	private String name;	
	
	/** 编码前缀 */
	private String prefix;
	
	/** 编码位数 */
	private Integer figures;	
	
	/** 日期流水号 */
	private Boolean useDate = Boolean.FALSE;	
	
	/** 日期格式 */
	private String dateFormat;		
	
	/** 分割符 */
	private String separator;

	/** 是否系统自动生成 */
	private Boolean systemCreate = Boolean.FALSE;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getFigures() {
		return figures;
	}

	public void setFigures(Integer figures) {
		this.figures = figures;
	}

	public Boolean getUseDate() {
		return useDate;
	}

	public void setUseDate(Boolean useDate) {
		this.useDate = useDate;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public Boolean getSystemCreate() {
		return systemCreate;
	}

	public void setSystemCreate(Boolean systemCreate) {
		this.systemCreate = systemCreate;
	}
	
	
}
