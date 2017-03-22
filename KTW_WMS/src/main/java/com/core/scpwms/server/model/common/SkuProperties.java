package com.core.scpwms.server.model.common;

import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.enumerate.EnuTurnOverConf;
import com.core.scpwms.server.model.lot.LotInfo;

/**
 * 货品作业属性
 * 包含以下内容：
 * 1、期间设定(保质期、预警期)
 * 2、包装设定（包装、入库缺省包装、出库缺省包装、补货包装）
 * 3、作业规则设定（周转规则、批次规则、质检规则、盘点规则、补货规则）
 * 4、替代货品设定（1代1）
 * 5、BOM加工费用等
 * 
 * 注：其中质检规则、周转规则、批次规则和货主作业设定重复，如果SKU做了设定以SKU为主
 * 否则以货主设定为主。
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class SkuProperties extends DomainModel {
	
	/**批次规则*/
	private LotInfo lotInfo;
	
	/**包装*/
	private PackageInfo packageInfo = new PackageInfo();
	
	/**入库缺省包装（基于包装）*/
	private PackageDetail inboundPkg;
	
	/**出库缺省包装（基于包装）*/
	private PackageDetail outboundPkg;
	
	/**补货包装（基于包装）*/
	private PackageDetail replenishmentPkg;
	
	/** 是否启用保质期管理*/
	private Boolean useExpire = Boolean.TRUE;
	
	/** 保质期限 */
	private Integer dayOfExpiry = 0;
	
	/** 保质期限预警(天) */
	private Integer alertLeadingDays = 0;

	/** 安全库存上限 */
	private Double safetyStockUpper = 0D;

	/** 安全库存下限 */
	private Double safetyStockLower = 0D;

	/** 采购提前期(天) */
	private Integer purchaseLeadTime = 0;
	
	/** 生产日期批次对应KEY */
	private String lotExpireKey;	
	
	/** 保质期日期批次对应KEY */
	private String lotDeadLineKey;
	
	/**　缺省库存周转规则
	 * @see EnuTurnOverConf
	 */
	private String toc = EnuTurnOverConf.TOC003;
	
	/** 拆托盘分配 */
	private Boolean splitPallet =Boolean.FALSE;		
	
	/** 拆包装分配 */
	private Boolean splitPackage =Boolean.FALSE;
	
	/** 基本チェック数（日本食研） */
	private Double baseCheckQty4NS = 1D;
	
	/** 基本単位コード（日本食研）　*/
	private String baseUnitCode4NS;
	
	/** 基本単位名（日本食研） */
	private String baseUnitName4NS;
	
	/** 同梱可否フラグ（日本食研） 1:可以 2：不可以 */
	private Long packFlg4NS = 1L;
	
	public LotInfo getLotInfo() {
		return lotInfo;
	}

	public void setLotInfo(LotInfo lotInfo) {
		this.lotInfo = lotInfo;
	}

	public PackageInfo getPackageInfo() {
		return this.packageInfo;
	}

	public void setPackageInfo(PackageInfo packageInfo) {
		this.packageInfo = packageInfo;
	}

	public PackageDetail getInboundPkg() {
		return this.inboundPkg;
	}

	public void setInboundPkg(PackageDetail inboundPkg) {
		this.inboundPkg = inboundPkg;
	}

	public PackageDetail getOutboundPkg() {
		return this.outboundPkg;
	}

	public void setOutboundPkg(PackageDetail outboundPkg) {
		this.outboundPkg = outboundPkg;
	}

	public PackageDetail getReplenishmentPkg() {
		return this.replenishmentPkg;
	}

	public void setReplenishmentPkg(PackageDetail replenishmentPkg) {
		this.replenishmentPkg = replenishmentPkg;
	}

	public Boolean getUseExpire() {
		return useExpire;
	}

	public void setUseExpire(Boolean useExpire) {
		this.useExpire = useExpire;
	}

	public Integer getDayOfExpiry() {
		return dayOfExpiry;
	}

	public void setDayOfExpiry(Integer dayOfExpiry) {
		this.dayOfExpiry = dayOfExpiry;
	}

	public Integer getAlertLeadingDays() {
		return alertLeadingDays;
	}

	public void setAlertLeadingDays(Integer alertLeadingDays) {
		this.alertLeadingDays = alertLeadingDays;
	}

	public String getLotExpireKey() {
		return lotExpireKey;
	}

	public void setLotExpireKey(String lotExpireKey) {
		this.lotExpireKey = lotExpireKey;
	}

	public String getLotDeadLineKey() {
		return lotDeadLineKey;
	}

	public void setLotDeadLineKey(String lotDeadLineKey) {
		this.lotDeadLineKey = lotDeadLineKey;
	}

	public String getToc() {
		return toc;
	}

	public void setToc(String toc) {
		this.toc = toc;
	}

	public Boolean getSplitPallet() {
		return splitPallet;
	}

	public void setSplitPallet(Boolean splitPallet) {
		this.splitPallet = splitPallet;
	}

	public Boolean getSplitPackage() {
		return splitPackage;
	}

	public void setSplitPackage(Boolean splitPackage) {
		this.splitPackage = splitPackage;
	}

	public Double getBaseCheckQty4NS() {
		return baseCheckQty4NS;
	}

	public void setBaseCheckQty4NS(Double baseCheckQty4NS) {
		this.baseCheckQty4NS = baseCheckQty4NS;
	}

	public String getBaseUnitCode4NS() {
		return baseUnitCode4NS;
	}

	public void setBaseUnitCode4NS(String baseUnitCode4NS) {
		this.baseUnitCode4NS = baseUnitCode4NS;
	}

	public String getBaseUnitName4NS() {
		return baseUnitName4NS;
	}

	public void setBaseUnitName4NS(String baseUnitName4NS) {
		this.baseUnitName4NS = baseUnitName4NS;
	}

	public Double getSafetyStockUpper() {
		return safetyStockUpper;
	}

	public void setSafetyStockUpper(Double safetyStockUpper) {
		this.safetyStockUpper = safetyStockUpper;
	}

	public Double getSafetyStockLower() {
		return safetyStockLower;
	}

	public void setSafetyStockLower(Double safetyStockLower) {
		this.safetyStockLower = safetyStockLower;
	}

	public Integer getPurchaseLeadTime() {
		return purchaseLeadTime;
	}

	public void setPurchaseLeadTime(Integer purchaseLeadTime) {
		this.purchaseLeadTime = purchaseLeadTime;
	}

	public Long getPackFlg4NS() {
		return packFlg4NS;
	}

	public void setPackFlg4NS(Long packFlg4NS) {
		this.packFlg4NS = packFlg4NS;
	}

}
