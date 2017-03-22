/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: HistoryTrackingEntity.java
 */

package com.core.scpwms.server.domain;

import java.util.Date;

import com.core.business.model.TrackingEntity;

@SuppressWarnings("all")
public class DownloadTrackingEntity extends TrackingEntity {
	/**
	 * 下载日期
	 */
	private Date downloadDate;

	/**
	 * 下载者
	 */
	private String downloadUser;

	public Date getDownloadDate() {
		return this.downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	public String getDownloadUser() {
		return this.downloadUser;
	}

	public void setDownloadUser(String downloadUser) {
		this.downloadUser = downloadUser;
	}

}
