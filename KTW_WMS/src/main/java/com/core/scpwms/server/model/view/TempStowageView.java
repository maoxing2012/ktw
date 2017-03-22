package com.core.scpwms.server.model.view;

import java.util.Date;

import com.core.db.server.model.DomainModel;

/**
 * 
 * <p>
 * 入库单视图
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014年9月25日<br/>
 */
public class TempStowageView extends DomainModel {
	private Long id;
	
	private String tmsPlatFormCd;
	
	private String stowageNumber;

	private String mainDriver;

	private String carNumber;
	
	private String jdeBillNo;

	private Date updateTime;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStowageNumber() {
		return this.stowageNumber;
	}

	public void setStowageNumber(String stowageNumber) {
		this.stowageNumber = stowageNumber;
	}

	public String getMainDriver() {
		return this.mainDriver;
	}

	public void setMainDriver(String mainDriver) {
		this.mainDriver = mainDriver;
	}

	public String getCarNumber() {
		return this.carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getJdeBillNo() {
		return this.jdeBillNo;
	}

	public void setJdeBillNo(String jdeBillNo) {
		this.jdeBillNo = jdeBillNo;
	}

	public String getTmsPlatFormCd() {
		return this.tmsPlatFormCd;
	}

	public void setTmsPlatFormCd(String tmsPlatFormCd) {
		this.tmsPlatFormCd = tmsPlatFormCd;
	}

}
