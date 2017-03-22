package com.core.scpwms.client.custom.page.support;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;

//缓存画面的值，备后台调用
public class ClientRecievingParam implements IsSerializable{
	
	/**
	 * ASN明细ID
	 */
	public String asnDetailId;
	
	/**
	 * 包装单位
	 */
    public String packageId;
    
    /**
     * 
     */
    public String recieving;
    
    /**
     * 托盘号
     */
    public String palletId;
    
    /**
     * 入库日期
     */
    public String inboundDate;
    
    /**
     * 书号
     */
    public String selecSkuCode;
    
    /**
     * 书名
     */
    public String SelectSkuName;
    
    /**
     * 库位ID
     */
    public String selectBinId;
    
    /**
     * 移位计划号
     */
    public String movePlanSeq;
    
    /**
     * 移位计划ID
     */
    public Long movePlanId;
    
    public String invStatus;
    
    
    /**
     * 批次信息
     */
    public Map<String,Object> lotInfos = new HashMap<String,Object>(); 
	

    public Long getMovePlanId() {
		return movePlanId;
	}


	public void setMovePlanId(Long movePlanId) {
		this.movePlanId = movePlanId;
	}


	public ClientRecievingParam(){
	}


	public String getAsnDetailId() {
		return asnDetailId;
	}


	public void setAsnDetailId(String asnDetailId) {
		this.asnDetailId = asnDetailId;
	}


	public String getPackageId() {
		return packageId;
	}


	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}


	public String getRecieving() {
		return recieving;
	}


	public void setRecieving(String recieving) {
		this.recieving = recieving;
	}


	public Map<String, Object> getLotInfos() {
		return lotInfos;
	}


	public void setLotInfos(Map<String, Object> lotInfos) {
		this.lotInfos = lotInfos;
	}


	public String getPalletId() {
		return palletId;
	}


	public void setPalletId(String palletId) {
		this.palletId = palletId;
	}


	public String getInboundDate() {
		return inboundDate;
	}


	public void setInboundDate(String inboundDate) {
		this.inboundDate = inboundDate;
	}




	public String getSelecSkuCode() {
		return selecSkuCode;
	}


	public void setSelecSkuCode(String selecSkuCode) {
		this.selecSkuCode = selecSkuCode;
	}


	public String getSelectSkuName() {
		return SelectSkuName;
	}


	public void setSelectSkuName(String selectSkuName) {
		SelectSkuName = selectSkuName;
	}


	public String getSelectBinId() {
		return selectBinId;
	}


	public void setSelectBinId(String selectBinId) {
		this.selectBinId = selectBinId;
	}


	public String getMovePlanSeq() {
		return movePlanSeq;
	}


	public void setMovePlanSeq(String movePlanSeq) {
		this.movePlanSeq = movePlanSeq;
	}


	public String getInvStatus() {
		return invStatus;
	}


	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}



}
