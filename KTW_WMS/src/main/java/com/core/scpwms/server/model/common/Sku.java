package com.core.scpwms.server.model.common;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuFixDiv;
import com.core.scpwms.server.enumerate.EnuStockDiv;
import com.core.scpwms.server.enumerate.EnuStoreRole;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;

/**
 * 货品主档
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class Sku extends TrackingEntity {
	/** 公司 */
	private Plant plant;
	
	/** 货主 */
	private Owner owner;
	
	/** 商品代码 */
	private String code;

	/** 商品名称 */
	private String name;

	/** JANCode　*/
	private String code1;

	/** 特別品ＮＯ（ＮＳ） */
	private String code2;

	/** 规格 */
	private String specs;

	/** 品牌编码 */
	private String brandCode;

	/** 品牌名 */
	private String brandName;

	/**　毛重　*/
	private Double grossWeight = 0D;

	/** 净重－ 产品主单位的标准净重 */
	private Double netWeight = 0D;

	/** 皮重 － 产品主单位的标准皮重 */
	private Double tareWeight = 0D;

	/** 长度－ 产品主单位的标准长度 */
	private Double length = 0D;

	/** 宽度－ 产品主单位的标准宽度 */
	private Double width = 0D;

	/** 高度－ 产品主单位的标准高度 */
	private Double height = 0D;

	/** 体积－ 产品主单位的标准体积 */
	private Double volume = 0D;
	
	/** 其他度量值 */
	private Double metric = 0D;
	
	/** 平方(M2) */
	private Double square = 0D;

	private SkuType it1000;
	private SkuType it2000;
	private SkuType it3000;
	private SkuType it4000;

	/** 标准单价 */
	private Double price = 0D;

	/** 扩展单价 */
	private Double price_ext1 = 0D;

	/** 扩展单价 */
	private Double price_ext2 = 0D;

	/**　泡货/重货　*/
	private String deadWeight;
	
	/***
	 * 温度带区分
	 * @see EnuTemperatureDiv
	 * */
	private Long tempDiv = EnuTemperatureDiv.UNDEF;
	
	/** 库存管理区分
	 * @see EnuStockDiv
	 *  */
	private Long stockDiv = EnuStockDiv.STOCK;
	
	/** 不定贯区分 */
	private Long fixDiv = EnuFixDiv.FIX;

	/** 是否套件 */
	private Boolean isBom = Boolean.FALSE;

	/** IF接收 */
	private Boolean hubif = Boolean.TRUE;

	/** ABC设定 */
	private AbcProperties abcType;

	/** 默认供应商 */
	private BizOrg defSupplier;

	/** 是否可用 */
	private Boolean disabled = Boolean.FALSE;

	/** 描述 */
	private String description;
	
	/** SKU作业设定 */
	private SkuProperties properties;

	public Plant getPlant() {
		return this.plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}
	
	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

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

	public BizOrg getDefSupplier() {
		return this.defSupplier;
	}

	public void setDefSupplier(BizOrg defSupplier) {
		this.defSupplier = defSupplier;
	}

	public String getCode1() {
		return this.code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

	public String getCode2() {
		return this.code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public String getSpecs() {
		return this.specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public Double getGrossWeight() {
		return this.grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Double getNetWeight() {
		return this.netWeight;
	}

	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}

	public Double getTareWeight() {
		return this.tareWeight;
	}

	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}

	public Double getLength() {
		return this.length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return this.width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return this.height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getVolume() {
		return this.volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPrice_ext1() {
		return this.price_ext1;
	}

	public void setPrice_ext1(Double price_ext1) {
		this.price_ext1 = price_ext1;
	}

	public Double getPrice_ext2() {
		return this.price_ext2;
	}

	public void setPrice_ext2(Double price_ext2) {
		this.price_ext2 = price_ext2;
	}

	public String getDeadWeight() {
		return this.deadWeight;
	}

	public void setDeadWeight(String deadWeight) {
		this.deadWeight = deadWeight;
	}

	public Boolean getIsBom() {
		return this.isBom;
	}

	public void setIsBom(Boolean isBom) {
		this.isBom = isBom;
	}

	public Boolean getHubif() {
		return this.hubif;
	}

	public void setHubif(Boolean hubif) {
		this.hubif = hubif;
	}

	public AbcProperties getAbcType() {
		return this.abcType;
	}

	public void setAbcType(AbcProperties abcType) {
		this.abcType = abcType;
	}

	public SkuProperties getProperties() {
		return this.properties;
	}

	public void setProperties(SkuProperties properties) {
		this.properties = properties;
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

	public String getBrandCode() {
		return this.brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public Double getSquare() {
		return this.square;
	}

	public void setSquare(Double square) {
		this.square = square;
	}

	public SkuType getIt1000() {
		return it1000;
	}

	public void setIt1000(SkuType it1000) {
		this.it1000 = it1000;
	}

	public SkuType getIt2000() {
		return it2000;
	}

	public void setIt2000(SkuType it2000) {
		this.it2000 = it2000;
	}

	public SkuType getIt3000() {
		return it3000;
	}

	public void setIt3000(SkuType it3000) {
		this.it3000 = it3000;
	}

	public SkuType getIt4000() {
		return it4000;
	}

	public void setIt4000(SkuType it4000) {
		this.it4000 = it4000;
	}

	public Double getMetric() {
		return metric;
	}

	public void setMetric(Double metric) {
		this.metric = metric;
	}

	public Long getFixDiv() {
		return fixDiv;
	}

	public void setFixDiv(Long fixDiv) {
		this.fixDiv = fixDiv;
	}

	public Long getTempDiv() {
		return tempDiv;
	}

	public void setTempDiv(Long tempDiv) {
		this.tempDiv = tempDiv;
	}

	public Long getStockDiv() {
		return stockDiv;
	}

	public void setStockDiv(Long stockDiv) {
		this.stockDiv = stockDiv;
	}
	
}
