package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;
import com.core.business.model.domain.ContractInfo;

/**
 * 
 * @description   收件人 
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-1-3
 * @version        V1.0<br/>
 */
@SuppressWarnings("all")
public class Addressee extends TrackingEntity{
	
	/**
	 * 客户 / 供应商 /....
	 */
	private BizOrg customer;
	
	/**
	 * 收件人代码
	 */
	private String code;
	
	/**
	 * 经营单位编号
	 */
	private String bizOrgCode;
	
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 收件区域
	 */
	private InboundArea area;
	
	/** 联系方式 */
    private ContractInfo contractInfo;
    
	/**是否失效 */
    private Boolean disabled = Boolean.FALSE;
    
	public BizOrg getCustomer() {
		return customer;
	}

	public void setCustomer(BizOrg customer) {
		this.customer = customer;
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

	public InboundArea getArea() {
		return this.area;
	}

	public void setArea(InboundArea area) {
		this.area = area;
	}

	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

    public ContractInfo getContractInfo() {
        return this.contractInfo;
    }

    public void setContractInfo(ContractInfo contractInfo) {
        this.contractInfo = contractInfo;
    }

	public String getBizOrgCode() {
		return this.bizOrgCode;
	}

	public void setBizOrgCode(String bizOrgCode) {
		this.bizOrgCode = bizOrgCode;
	}
	
}
