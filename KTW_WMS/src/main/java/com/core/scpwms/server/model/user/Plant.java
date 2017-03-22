package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;
import com.core.business.model.domain.ContractInfo;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 公司
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class Plant extends TrackingEntity {
	private Warehouse wh;
	
	/** 代码 */
	private String code;
	
	/** 名称 */
	private String name;
	
	/** 简称 */
	private String shortName;
	
	/** 联系方式 */
	private ContractInfo contractInfo;
	
	/** 收货设定*/
	private PlantInBountProperties inBountProperties ;
	
	/** 库内设定 盘点-理货-补货-RF作业设定*/
	private PlantWarehousingProperties whingProperties ;
	
	/** 发货设定*/
	private PlantOutBountProperties outBountProperties ;
	
	/**是否失效 */
	private Boolean disabled=Boolean.FALSE;

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
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public ContractInfo getContractInfo() {
		return contractInfo;
	}
	public void setContractInfo(ContractInfo contractInfo) {
		this.contractInfo = contractInfo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public PlantInBountProperties getInBountProperties() {
		return inBountProperties;
	}
	public void setInBountProperties(PlantInBountProperties inBountProperties) {
		this.inBountProperties = inBountProperties;
	}
	public PlantWarehousingProperties getWhingProperties() {
		return whingProperties;
	}
	public void setWhingProperties(PlantWarehousingProperties whingProperties) {
		this.whingProperties = whingProperties;
	}
	public PlantOutBountProperties getOutBountProperties() {
		return outBountProperties;
	}
	public void setOutBountProperties(PlantOutBountProperties outBountProperties) {
		this.outBountProperties = outBountProperties;
	}
	public Warehouse getWh() {
		return wh;
	}
	public void setWh(Warehouse wh) {
		this.wh = wh;
	}
}
