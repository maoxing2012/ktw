package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;
import com.core.business.model.domain.ContractInfo;

/**
 * 
 * <p>
 * 货主
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/06/26<br/>
 */
@SuppressWarnings("all")
public class Owner extends TrackingEntity {
	/** 所属公司 */
	private Plant plant;

	/** 代码 */
	private String code;

	/** 名称 */
	private String name;

	/** 简称 */
	private String shortName;

	private String co;

	private String div;

	/**　得意先倉庫コード　*/
	private String whse;

	/** 联系方式 */
	private ContractInfo contractInfo;

	/** 描述 */
	private String description;

	/** 是否失效 */
	private Boolean disabled = Boolean.FALSE;

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

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public ContractInfo getContractInfo() {
		return this.contractInfo;
	}

	public void setContractInfo(ContractInfo contractInfo) {
		this.contractInfo = contractInfo;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Plant getPlant() {
		return this.plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public String getCo() {
		return this.co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getDiv() {
		return this.div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getWhse() {
		return this.whse;
	}

	public void setWhse(String whse) {
		this.whse = whse;
	}

}
