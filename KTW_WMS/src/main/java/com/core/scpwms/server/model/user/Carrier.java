package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;
import com.core.business.model.domain.ContractInfo;
import com.core.scpwms.server.enumerate.EnuCountCarrierStatus;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 承运商
 */
@SuppressWarnings("all")
public class Carrier extends TrackingEntity {
	
	private Warehouse wh;
	
	private Plant plant;
	
	/** 代码 */
	private String code;
	
	/** 名称 */
	private String name;
	
	/** 简称 */
	private String shortName;
	
	/** 联系方式 */
	private ContractInfo contractInfo;
	
	/** 承运商 作业设定*/
	private CarrierProperties carrierPropertie;

	/**是否失效 默认:生效*/
    private Boolean disabled=Boolean.FALSE; 

    /** 描述 */
    private String description;
    
	private String extString1;
	    
    private String extString2;
    
    private String extString3;

    private String extString4;
    
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

    public CarrierProperties getCarrierPropertie() {
        return this.carrierPropertie;
    }

    public void setCarrierPropertie(CarrierProperties carrierPropertie) {
        this.carrierPropertie = carrierPropertie;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public Warehouse getWh() {
		return wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public String getExtString1() {
		return extString1;
	}

	public void setExtString1(String extString1) {
		this.extString1 = extString1;
	}

	public String getExtString2() {
		return extString2;
	}

	public void setExtString2(String extString2) {
		this.extString2 = extString2;
	}

	public String getExtString3() {
		return extString3;
	}

	public void setExtString3(String extString3) {
		this.extString3 = extString3;
	}

	public String getExtString4() {
		return extString4;
	}

	public void setExtString4(String extString4) {
		this.extString4 = extString4;
	}
    
}
