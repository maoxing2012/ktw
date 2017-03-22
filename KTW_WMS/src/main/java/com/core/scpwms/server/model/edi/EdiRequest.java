package com.core.scpwms.server.model.edi;

import java.util.Date;

import com.core.business.model.TrackingEntity;

public class EdiRequest extends TrackingEntity{
	/** 公司代码 */
	private Long companyId;
	
	/**
	 * 订单号
	 */
	private String billNo;
	
	/**
	 * 业务类型
	 * 01 
	 * 02 
	 * 03 
	 * 04 
	 * 05 
	 * 06 
	 * ......
	 */
	private String bizType;
	
	/**
	 * CALL BACK系统
	 */
	private String calBackSystem;
	
	/**
	 * CALL Back URL 
	 */
	private String calBackURL;	
	
	/** 请求时间*/
	private Date requestDate;
	
	
	/**
	 * 同步/异步
	 * true:同步
	 * false:异步
	 */
	private Boolean sync ;
	
	/**
	 * BaseRequest对象序列化字符串
	 * clob存储
	 */
	private String jsonParam;
	
	/**
	 * 状态
	 * 100：接收
	 * 200：转发成功
	 * 300：转发失败
	 * 400：取消转发
	 */
	private Long status;
	
	
	/**
	 * 最后提示信息
	 */
	private String errorMessage;
	
	/**
	 * 归档标记
	 * true: 已经归档
	 * false: 未归档
	 */
	private Boolean history ;
	
	/**
	 * 参数类
	 */
	private String paramClassName;

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getCalBackSystem() {
		return calBackSystem;
	}

	public void setCalBackSystem(String calBackSystem) {
		this.calBackSystem = calBackSystem;
	}

	public String getCalBackURL() {
		return calBackURL;
	}

	public void setCalBackURL(String calBackURL) {
		this.calBackURL = calBackURL;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Boolean getSync() {
		return sync;
	}

	public void setSync(Boolean sync) {
		this.sync = sync;
	}

	public String getJsonParam() {
		return jsonParam;
	}

	public void setJsonParam(String jsonParam) {
		this.jsonParam = jsonParam;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getHistory() {
		return history;
	}

	public void setHistory(Boolean history) {
		this.history = history;
	}

	public String getParamClassName() {
		return paramClassName;
	}

	public void setParamClassName(String paramClassName) {
		this.paramClassName = paramClassName;
	}
	
	
	
}
