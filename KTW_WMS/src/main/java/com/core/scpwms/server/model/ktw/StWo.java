/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: OrderStatus.java
 */

package com.core.scpwms.server.model.ktw;

import com.core.db.server.model.Entity;

/**
 * <p>拣货区和拣货单的绑定(拣货区当前被哪张拣货占用着的信息)</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/14<br/>
 */
@SuppressWarnings("all")
public class StWo extends Entity {
    private Long stId;
    
    private Long woId;
    
    private Long userId;
    
    private String stCode;
    
    private String woCode;
    
    private String userName;

	public Long getStId() {
		return this.stId;
	}

	public void setStId(Long stId) {
		this.stId = stId;
	}

	public Long getWoId() {
		return this.woId;
	}

	public void setWoId(Long woId) {
		this.woId = woId;
	}

	public String getStCode() {
		return this.stCode;
	}

	public void setStCode(String stCode) {
		this.stCode = stCode;
	}

	public String getWoCode() {
		return this.woCode;
	}

	public void setWoCode(String woCode) {
		this.woCode = woCode;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
