package com.core.scpwms.server.model.fee;

import java.util.Set;

import com.core.business.model.TrackingEntity;
import com.core.business.model.domain.ContractInfo;
import com.core.scpwms.server.domain.BankInfo;

/**
 * 经营公司信息 
 *
 */
@SuppressWarnings("all")
public class Company extends TrackingEntity {

    /** 代码 */
    private String code;
    /** 名称 */
    private String name;
    /** 联系方式 */
    private ContractInfo contractInfo;
    /** 银行1 */
    private BankInfo bank1;
    /** 银行2 */
    private BankInfo bank2;
    /** 描述 */
    private String description;
    /** 是否失效 */
    private Boolean disabled = Boolean.FALSE;

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

	public BankInfo getBank1() {
		return bank1;
	}

	public void setBank1(BankInfo bank1) {
		this.bank1 = bank1;
	}

	public BankInfo getBank2() {
		return bank2;
	}

	public void setBank2(BankInfo bank2) {
		this.bank2 = bank2;
	}

}
