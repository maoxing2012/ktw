package com.core.scpwms.server.model.rules;

import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.enumerate.EnuPARBePallet;

/**
 * 上架规则内的主要过滤条件
 * 
 * 注：包装和包装详细建立条件过滤关系，与SKU无关
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class PutAwayRuleMain extends DomainModel{

	/** 
	 * 
	 * 是否托盘
	 * @see EnuPARBePallet 
	 **/
	private String hasPallet;
	
	/**
	 * 使用数量限定
	 */
	private Boolean useQtyLimit = Boolean.FALSE;
	
	/**
	 * 数量上限
	 * 针对包装数量、非EA数量
	 */
	private Double qtyLimit = 0D;
	
	/**
	 * 数量下限
	 * 针对包装数量、非EA数量
	 */
	private Double qtyLowerLimit = 0D;
	
	/**
	 * 使用托盘数限定
	 */
	private Boolean usePalletLimit = Boolean.FALSE;
	
	/**
	 * 托盘数上限
	 */
	private Double palletQtyLimit = 0D;
	
	/**
	 * 托盘数下限
	 */
	private Double palletQtyLowerLimit = 0D;

	public String getHasPallet() {
		return hasPallet;
	}

	public void setHasPallet(String hasPallet) {
		this.hasPallet = hasPallet;
	}

	public Boolean getUseQtyLimit() {
		return useQtyLimit;
	}

	public void setUseQtyLimit(Boolean useQtyLimit) {
		this.useQtyLimit = useQtyLimit;
	}

	public Double getQtyLimit() {
		return qtyLimit;
	}

	public void setQtyLimit(Double qtyLimit) {
		this.qtyLimit = qtyLimit;
	}

	public Double getQtyLowerLimit() {
		return qtyLowerLimit;
	}

	public void setQtyLowerLimit(Double qtyLowerLimit) {
		this.qtyLowerLimit = qtyLowerLimit;
	}

	public Boolean getUsePalletLimit() {
		return this.usePalletLimit;
	}

	public void setUsePalletLimit(Boolean usePalletLimit) {
		this.usePalletLimit = usePalletLimit;
	}

	public Double getPalletQtyLimit() {
		return this.palletQtyLimit;
	}

	public void setPalletQtyLimit(Double palletQtyLimit) {
		this.palletQtyLimit = palletQtyLimit;
	}

	public Double getPalletQtyLowerLimit() {
		return this.palletQtyLowerLimit;
	}

	public void setPalletQtyLowerLimit(Double palletQtyLowerLimit) {
		this.palletQtyLowerLimit = palletQtyLowerLimit;
	}
	
}
