package com.core.scpwms.server.model.rules;

import java.util.HashSet;
import java.util.Set;

import com.core.business.model.TrackingEntity;
import com.core.business.model.TrackingEntityWithVer;
import com.core.scpwms.server.enumerate.EnuPARBinSelectPriority;
import com.core.scpwms.server.enumerate.EnuPARBinValidate;
import com.core.scpwms.server.model.common.AbcProperties;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.PackageInfo;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuType;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinGroup;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * <p>
 * 上架策略
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/06/27<br/>
 */
@SuppressWarnings("all")
public class PutAwayRule extends TrackingEntity {

	/** 仓库 */
	private Warehouse wh;

	/** 优先级 */
	private Integer priority;

	/** 目标库位组 */
	private BinGroup descBinGroup;

	/** 目标库位 */
	private Bin descBin;

	/** 原始库位 */
	private Bin srcBin;

	/** 只查找同批次库位 */
	private Boolean onlySameLot;

	/**
	 * 上架策略中，库位选择限定 至少输入一项
	 * 
	 * @see EnuPARBinSelectPriority
	 * */
	private String binSelPriority1;
	private String binSelPriority2;
	private String binSelPriority3;
	private String binSelPriority4;

	/**
	 * 上架策略中，库位的容量校验 至少输入一项
	 * 
	 * @see EnuPARBinValidate
	 */
	private String binValidate1;
	private String binValidate2;
	private String binValidate3;
	private String binValidate4;

	/** 主限制 */
	private PutAwayRuleMain ruleMain = new PutAwayRuleMain();

	/** 批次限定 */
	private PutAwayRuleLot ruleLot = new PutAwayRuleLot();

	/**
	 * 使用LOT限定
	 */
	private Boolean useLotLimit = Boolean.FALSE;

	/** 货主 */
	private Plant plant;

	/** SKU */
	private Sku sku;

	/** ABC分类 */
	private AbcProperties abcProperties;

	/** 商品大类 */
	private SkuType bType;

	/** 商品中类 */
	private SkuType mType;

	/** 商品小类 */
	private SkuType lType;

	/** 包装 */
	private PackageInfo packageInfo;

	/** 包装详细 */
	private PackageDetail packageDetail;

	/** 上架库位 表示用 */
	private String targetBins;

	/** 库位筛选 表示用 */
	private String binConditions;

	/** 库位验证 表示用 */
	private String binValidates;

	/** 上架条件 表示用 */
	private String putAwayConditions;

	/** 库存状态 */
	private String invStatus;

	/**
	 * 上架策略中，包装级别，应对如下的情况 包装量非固定，但是又需要按照包装进行上架区分
	 * 
	 * @see EnuPackageLevel
	 * */
	private String packageLevel;

	/**
	 * 关联的订单
	 */
	private OrderType ot;

	/**
	 * 关联的订单
	 */
	private Set<OrderType> ots = new HashSet<OrderType>();

	/**
	 * 是否失效
	 */
	private Boolean disabled = Boolean.FALSE;

	/**
	 * 品牌名称
	 */
	private String brandName;

	/**
	 * 描述
	 */
	private String description;

	public String getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public Warehouse getWh() {
		return wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public BinGroup getDescBinGroup() {
		return descBinGroup;
	}

	public void setDescBinGroup(BinGroup descBinGroup) {
		this.descBinGroup = descBinGroup;
	}

	public Bin getDescBin() {
		return descBin;
	}

	public void setDescBin(Bin descBin) {
		this.descBin = descBin;
	}

	public Bin getSrcBin() {
		return srcBin;
	}

	public void setSrcBin(Bin srcBin) {
		this.srcBin = srcBin;
	}

	public String getBinSelPriority1() {
		return binSelPriority1;
	}

	public void setBinSelPriority1(String binSelPriority1) {
		this.binSelPriority1 = binSelPriority1;
	}

	public String getBinSelPriority2() {
		return binSelPriority2;
	}

	public void setBinSelPriority2(String binSelPriority2) {
		this.binSelPriority2 = binSelPriority2;
	}

	public String getBinSelPriority3() {
		return binSelPriority3;
	}

	public void setBinSelPriority3(String binSelPriority3) {
		this.binSelPriority3 = binSelPriority3;
	}

	public String getBinSelPriority4() {
		return binSelPriority4;
	}

	public void setBinSelPriority4(String binSelPriority4) {
		this.binSelPriority4 = binSelPriority4;
	}

	public String getBinValidate1() {
		return binValidate1;
	}

	public void setBinValidate1(String binValidate1) {
		this.binValidate1 = binValidate1;
	}

	public String getBinValidate2() {
		return binValidate2;
	}

	public void setBinValidate2(String binValidate2) {
		this.binValidate2 = binValidate2;
	}

	public String getBinValidate3() {
		return binValidate3;
	}

	public void setBinValidate3(String binValidate3) {
		this.binValidate3 = binValidate3;
	}

	public String getBinValidate4() {
		return binValidate4;
	}

	public void setBinValidate4(String binValidate4) {
		this.binValidate4 = binValidate4;
	}

	public PutAwayRuleMain getRuleMain() {
		return ruleMain;
	}

	public void setRuleMain(PutAwayRuleMain ruleMain) {
		this.ruleMain = ruleMain;
	}

	public PutAwayRuleLot getRuleLot() {
		return ruleLot;
	}

	public void setRuleLot(PutAwayRuleLot ruleLot) {
		this.ruleLot = ruleLot;
	}

	public Boolean getUseLotLimit() {
		return useLotLimit;
	}

	public void setUseLotLimit(Boolean useLotLimit) {
		this.useLotLimit = useLotLimit;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public AbcProperties getAbcProperties() {
		return abcProperties;
	}

	public void setAbcProperties(AbcProperties abcProperties) {
		this.abcProperties = abcProperties;
	}

	public SkuType getbType() {
		return bType;
	}

	public void setbType(SkuType bType) {
		this.bType = bType;
	}

	public SkuType getmType() {
		return mType;
	}

	public void setmType(SkuType mType) {
		this.mType = mType;
	}

	public SkuType getlType() {
		return lType;
	}

	public void setlType(SkuType lType) {
		this.lType = lType;
	}

	public PackageInfo getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(PackageInfo packageInfo) {
		this.packageInfo = packageInfo;
	}

	public PackageDetail getPackageDetail() {
		return packageDetail;
	}

	public void setPackageDetail(PackageDetail packageDetail) {
		this.packageDetail = packageDetail;
	}

	public boolean containBinValidate(String params) {

		if (params.equalsIgnoreCase(this.getBinValidate1())
				|| params.equalsIgnoreCase(this.getBinValidate2())
				|| params.equalsIgnoreCase(this.getBinValidate3())
				|| params.equalsIgnoreCase(this.getBinValidate4())) {
			return true;
		}

		return false;
	}

	public String getTargetBins() {

		return targetBins;
	}

	public void setTargetBins(String targetBins) {
		this.targetBins = targetBins;
	}

	public String getBinConditions() {
		return binConditions;
	}

	public void setBinConditions(String binConditions) {
		this.binConditions = binConditions;
	}

	public String getBinValidates() {
		return binValidates;
	}

	public void setBinValidates(String binValidates) {
		this.binValidates = binValidates;
	}

	public String getPutAwayConditions() {
		return putAwayConditions;
	}

	public void setPutAwayConditions(String putAwayConditions) {
		this.putAwayConditions = putAwayConditions;
	}

	public String getPackageLevel() {
		return packageLevel;
	}

	public void setPackageLevel(String packageLevel) {
		this.packageLevel = packageLevel;
	}

	public SkuType getBType() {
		return this.bType;
	}

	public void setBType(SkuType type) {
		bType = type;
	}

	public SkuType getMType() {
		return this.mType;
	}

	public void setMType(SkuType type) {
		mType = type;
	}

	public SkuType getLType() {
		return this.lType;
	}

	public void setLType(SkuType type) {
		lType = type;
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

	public OrderType getOt() {
		return this.ot;
	}

	public void setOt(OrderType ot) {
		this.ot = ot;
	}

	public Set<OrderType> getOts() {
		return this.ots;
	}

	public void setOts(Set<OrderType> ots) {
		this.ots = ots;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Boolean getOnlySameLot() {
		return this.onlySameLot;
	}

	public void setOnlySameLot(Boolean onlySameLot) {
		this.onlySameLot = onlySameLot;
	}

}
