package com.core.scpwms.server.model.view;

import java.util.Date;

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
public class Wh1000View extends DomainModel {
	private Long id;

	private Long rowNum;

	private Date createTime;

	private String mesType;

	private String title;

	private String content;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRowNum() {
		return this.rowNum;
	}

	public void setRowNum(Long rowNum) {
		this.rowNum = rowNum;
	}

	public String getMesType() {
		return this.mesType;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
