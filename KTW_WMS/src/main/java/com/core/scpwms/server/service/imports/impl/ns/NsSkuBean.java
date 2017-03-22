package com.core.scpwms.server.service.imports.impl.ns;

import com.core.scpwms.server.enumerate.EnuTemperatureDiv;

public class NsSkuBean {
	// 荷主コード
	private String ownerCode = "1000";
	//商品コード	
	private String skuCode;
	//商品名	
	private String skuName;
	//特別品ＮＯ	
	private String specNo;
	//温度帯	
	private String tempDiv;
	//基本単位当たり重量	
	private Double weight;
	//基本チェック数	
	private Double baseCheckQty;
	//基本単位コード	
	private String baseUnitCode;
	//基本単位名	
	private String baseUnitName;
	//包装入数	
	private Double blIn;
	//包装単位コード	
	private String blUnitCode;
	//包装単位名	
	private String blUnitName;
	//ケース入数	
	private Double csIn;
	//ケース単位コード	
	private String csUnitCode;
	//ケース単位名称	
	private String csUnitName;
	//包装形態名称	
	private String specs;
	//同梱不可フラグ	
	private String packFlg;
	//JANCD	
	private String janCode;
	//ITF_14_CD_B	
	private String itfCode1;
	//ITF_14_CD_C
	private String itfCode2;
	// 出荷期限日数
	private Integer shipExpDays;
	// 賞味期限日数
	private Integer expDays;
	public String getOwnerCode() {
		return ownerCode;
	}
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getSpecNo() {
		return specNo;
	}
	public void setSpecNo(String specNo) {
		this.specNo = specNo;
	}
	public String getTempDiv() {
		return tempDiv;
	}
	public void setTempDiv(String tempDiv) {
		this.tempDiv = tempDiv;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getBaseCheckQty() {
		if( baseCheckQty == null )
			return 1D;
		
		if( baseCheckQty <= 0D ){
			return 1D;
		}
		
		// 冷藏冷冻，如果基本单位数<1,作为1处理。
		if( baseCheckQty < 1D ){
			if( "1".equals(tempDiv) ){
				return 1D;
			}
			if( "2".equals(tempDiv) ){
				return 1D;
			}
		}
		
		return baseCheckQty;
	}
	public void setBaseCheckQty(Double baseCheckQty) {
		this.baseCheckQty = baseCheckQty;
	}
	public String getBaseUnitCode() {
		return baseUnitCode;
	}
	public void setBaseUnitCode(String baseUnitCode) {
		this.baseUnitCode = baseUnitCode;
	}
	public String getBaseUnitName() {
		return baseUnitName;
	}
	public void setBaseUnitName(String baseUnitName) {
		this.baseUnitName = baseUnitName;
	}
	public Double getBlIn() {
		return blIn;
	}
	public void setBlIn(Double blIn) {
		this.blIn = blIn;
	}
	public String getBlUnitCode() {
		return blUnitCode;
	}
	public void setBlUnitCode(String blUnitCode) {
		this.blUnitCode = blUnitCode;
	}
	public String getBlUnitName() {
		return blUnitName;
	}
	public void setBlUnitName(String blUnitName) {
		this.blUnitName = blUnitName;
	}
	public Double getCsIn() {
		return csIn;
	}
	public void setCsIn(Double csIn) {
		this.csIn = csIn;
	}
	public String getCsUnitCode() {
		return csUnitCode;
	}
	public void setCsUnitCode(String csUnitCode) {
		this.csUnitCode = csUnitCode;
	}
	public String getCsUnitName() {
		return csUnitName;
	}
	public void setCsUnitName(String csUnitName) {
		this.csUnitName = csUnitName;
	}
	public String getSpecs() {
		return specs;
	}
	public void setSpecs(String specs) {
		this.specs = specs;
	}
	public String getPackFlg() {
		return packFlg;
	}
	public void setPackFlg(String packFlg) {
		this.packFlg = packFlg;
	}
	public String getJanCode() {
		return janCode;
	}
	public void setJanCode(String janCode) {
		this.janCode = janCode;
	}
	public String getItfCode1() {
		return itfCode1;
	}
	public void setItfCode1(String itfCode1) {
		this.itfCode1 = itfCode1;
	}
	public String getItfCode2() {
		return itfCode2;
	}
	public void setItfCode2(String itfCode2) {
		this.itfCode2 = itfCode2;
	}
	public Integer getShipExpDays() {
		return shipExpDays;
	}
	public void setShipExpDays(Integer shipExpDays) {
		this.shipExpDays = shipExpDays;
	}
	public Integer getExpDays() {
		return expDays;
	}
	public void setExpDays(Integer expDays) {
		this.expDays = expDays;
	}
	public Long getWmsTempDivCode( ){
		if( "0".equals(tempDiv)){
			return EnuTemperatureDiv.CW;
		}
		else if( "1".equals(tempDiv) ){
			return EnuTemperatureDiv.LC;
		}
		else if( "2".equals(tempDiv) ){
			return EnuTemperatureDiv.LD;
		}
		else{
			return EnuTemperatureDiv.UNDEF;
		}
	}
}
