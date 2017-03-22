package com.core.scpwms.server.model.common;

import com.core.business.model.TrackingEntity;

/**
 * 作业单据代码设定
 * 
 * 作业单代码结构：
 * 前缀（必输）  + 前缀分隔符 （可选）+ 日期（可选） + 分隔符 （可选）+ 流水号
 * 
 * 设定内容：
 * 前缀、前缀分隔符、日期格式、分隔符、流水号
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class SequenceProperties extends TrackingEntity {
	
	/** 规则编号 */
	private String code;
	
	/** 规则名称 */
	private String name;
	
	/** 编码位数 */
	private Integer seqLength;
	
	/** 前缀 */
	private String prefix;
	
	/** 前缀分隔符 */
	private String separator1;

	/** 
	 * 日期格式
	 * @see EnuSeqDateFormat
	 *  */
	private String seqDateFormat;
	
	/** 日期后分隔符 */
	private String separator2;
	
	/** 流水号范围 （年/月/日） */
	private String seqGenerateBy ;
	
	/** 是否失效 */
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSeqLength() {
		return seqLength;
	}

	public void setSeqLength(Integer seqLength) {
		this.seqLength = seqLength;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSeparator1() {
		return separator1;
	}

	public void setSeparator1(String separator1) {
		this.separator1 = separator1;
	}

	public String getSeqDateFormat() {
		return seqDateFormat;
	}

	public void setSeqDateFormat(String seqDateFormat) {
		this.seqDateFormat = seqDateFormat;
	}

	public String getSeparator2() {
		return separator2;
	}

	public void setSeparator2(String separator2) {
		this.separator2 = separator2;
	}

	public String getSeqGenerateBy() {
		return seqGenerateBy;
	}

	public void setSeqGenerateBy(String seqGenerateBy) {
		this.seqGenerateBy = seqGenerateBy;
	}

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
	
}
