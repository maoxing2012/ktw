/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: OrderStatus.java
 */

package com.core.scpwms.server.model.base;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuInfoType;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * <p>
 * 系统消息
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/14<br/>
 */
@SuppressWarnings("all")
public class InfoMessage extends TrackingEntity {

	/** 标题 */
	private String title;

	/** 内容 */
	private String content;

	/** 仓库（为空时全仓库） */
	private Warehouse wh;

	/**
	 * @see EnuInfoType
	 */
	private String infoType;

	/** 是否指定 */
	private Boolean isTop;

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

	public Warehouse getWh() {
		return this.wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public String getInfoType() {
		return this.infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public Boolean getIsTop() {
		return this.isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

}
