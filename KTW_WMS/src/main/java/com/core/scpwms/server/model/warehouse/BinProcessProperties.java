package com.core.scpwms.server.model.warehouse;

import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.domain.BinDpsInfo;
import com.core.scpwms.server.enumerate.EnuProcessType;

/**
 * 库位作业属性设定
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class BinProcessProperties extends DomainModel {

	/** 是否是电子标签库位，（电子标签库位，新建后不能删除。商品不能混放，批次不能混放） */
	private Boolean isDps = Boolean.FALSE;
	
	private BinDpsInfo dpsInfo = new BinDpsInfo();
	
    /** 产品混放 */
	private Boolean skuMixed = Boolean.TRUE;

    /** 批次混放 */
    private Boolean lotMixed = Boolean.TRUE;

    /** 供应商混放 */
    private Boolean supplierMixed = Boolean.TRUE;
    
    /** 货主混放 */
    private Boolean ownerMixed = Boolean.TRUE;

    /** 忽略追踪号 */
    private Boolean lostSeq = Boolean.TRUE;

    /** 
     * 作业方式
     * @see EnuProcessType
     *  */
    private String processType;

    public Boolean getSkuMixed() {
        return this.skuMixed;
    }

    public Boolean getSupplierMixed() {
        return supplierMixed;
    }

    public void setSupplierMixed(Boolean supplierMixed) {
        this.supplierMixed = supplierMixed;
    }

    public void setSkuMixed(Boolean skuMixed) {
        this.skuMixed = skuMixed;
    }

    public Boolean getLotMixed() {
        return this.lotMixed;
    }

    public void setLotMixed(Boolean lotMixed) {
        this.lotMixed = lotMixed;
    }

    public String getProcessType() {
        return this.processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public Boolean getLostSeq() {
        return lostSeq;
    }

    public void setLostSeq(Boolean lostSeq) {
        this.lostSeq = lostSeq;
    }

	public Boolean getIsDps() {
		return this.isDps;
	}
	
	public BinDpsInfo getDpsInfo() {
		return this.dpsInfo;
	}

	public void setDpsInfo(BinDpsInfo dpsInfo) {
		this.dpsInfo = dpsInfo;
	}
	
	public Boolean getOwnerMixed() {
		return this.ownerMixed;
	}

	public void setOwnerMixed(Boolean ownerMixed) {
		this.ownerMixed = ownerMixed;
	}

	public void setIsDps(Boolean isDps) {
		this.isDps = isDps;
//		
//		// 电子标签的库位，商品是不能混放的。
//		if( isDps != null && isDps.booleanValue() ){
//			this.skuMixed = Boolean.FALSE;
//		}
	}
}
