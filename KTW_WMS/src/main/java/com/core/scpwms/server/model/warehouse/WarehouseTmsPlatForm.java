package com.core.scpwms.server.model.warehouse;

import java.util.Set;

import com.core.business.model.TrackingEntity;
import com.core.business.model.domain.ContractInfo;
import com.core.db.server.model.Entity;

/**
 * 
 * <p>仓库与TMS调度平台的关联关系</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014年11月28日<br/>
 */
@SuppressWarnings("all")
public class WarehouseTmsPlatForm extends Entity {
    /** 所属仓库 */
    private Warehouse wh;
    
    private String tmsPlatFormCd;
    
    private String tmsPlatFormNm;

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

	public String getTmsPlatFormCd() {
		return this.tmsPlatFormCd;
	}

	public void setTmsPlatFormCd(String tmsPlatFormCd) {
		this.tmsPlatFormCd = tmsPlatFormCd;
	}

	public String getTmsPlatFormNm() {
		return this.tmsPlatFormNm;
	}

	public void setTmsPlatFormNm(String tmsPlatFormNm) {
		this.tmsPlatFormNm = tmsPlatFormNm;
	}
    
}
