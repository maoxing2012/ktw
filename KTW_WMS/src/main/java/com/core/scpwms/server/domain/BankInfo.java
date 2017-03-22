package com.core.scpwms.server.domain;

import com.core.db.server.model.DomainModel;

@SuppressWarnings("all")
public class BankInfo extends DomainModel {

    private String bankName;
    
    private String brachName;
    
    private String account;
    
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBrachName() {
		return brachName;
	}

	public void setBrachName(String brachName) {
		this.brachName = brachName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
    
}
