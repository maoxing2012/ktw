package com.core.scpwms.server.model.rules;

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
 * <p>拣货策略</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/06/27<br/>
 */
@SuppressWarnings("all")
public class PickUpRule extends TrackingEntity {

    /** 仓库 */
    private Warehouse wh;

    /** 优先级 */
    private Integer priority;

    /** 拣选库位组*/
    private BinGroup descBinGroup;

    /** 拣选库位 */
    private Bin descBin;

    /** 拣选至库位*/
    private Bin srcBin;

    /** 货主 */
    private Plant plant;

    /** SKU*/
    private Sku sku;

    /** 订单类型*/
    private OrderType iot;

    /** ABC分类*/
    private AbcProperties abcProperties;

    /** 商品大类*/
    private SkuType bType;

    /** 商品中类*/
    private SkuType mType;

    /** 商品小类*/
    private SkuType lType;

    /** 包装 */
    private PackageInfo packageInfo;

    /** 包装详细 */
    private PackageDetail packageDetail;

    /** 拣选库位 表示用*/
    private String targetBins;

    /** 库位筛选 表示用*/
    private String binConditions;

    /** 拣选条件 表示用*/
    private String pickUpConditions;

    /**
     * 使用数量限定
     */
    private Boolean useQtyLimit = Boolean.FALSE;

    /**
     * 数量上限
     * 针对包装数量、非EA数量
     */
    private Double qtyLimit = 0D;

    /**
     * 数量下限
     * 针对包装数量、非EA数量
     */
    private Double qtyLowerLimit = 0D;

    /**
     * 是否越库
     */
    private Boolean crossDock = Boolean.FALSE;

    /**库存状态*/
    private String invStatus;

    /**
     * 折算限定
     */
    private Boolean usePackLimit = Boolean.FALSE;

    /** 
     * 上架策略中，包装级别，应对如下的情况
     * 包装量非固定，但是又需要按照包装进行上架区分
     * @see EnuPackageLevel
     * */
    private String packageLevel;

    /** 
     * 上架策略中，包装级别，应对如下的情况
     * 包装量非固定，但是又需要按照包装进行上架区分
     * @see EnuPackageLevel
     * */
    private String packageLevelEnd;

    /**
     * 是否采用库存数量过滤
     * 如果采用：库位上库存如果不满足需求数量，则不分配；
     */
    private Boolean enoughInv = Boolean.FALSE;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 是否失效
     */
    private Boolean disabled = Boolean.FALSE;

    /**
     * 描述
     */
    private String description;

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

    public Boolean getUseQtyLimit() {
        return useQtyLimit;
    }

    public void setUseQtyLimit(Boolean useQtyLimit) {
        this.useQtyLimit = useQtyLimit;
    }

    public Double getQtyLimit() {
        return qtyLimit;
    }

    public void setQtyLimit(Double qtyLimit) {
        this.qtyLimit = qtyLimit;
    }

    public Double getQtyLowerLimit() {
        return qtyLowerLimit;
    }

    public void setQtyLowerLimit(Double qtyLowerLimit) {
        this.qtyLowerLimit = qtyLowerLimit;
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

    public OrderType getIot() {
        return iot;
    }

    public void setIot(OrderType iot) {
        this.iot = iot;
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

    public String getPickUpConditions() {
        return pickUpConditions;
    }

    public void setPickUpConditions(String pickUpConditions) {
        this.pickUpConditions = pickUpConditions;
    }

    public Boolean getCrossDock() {
        return crossDock;
    }

    public void setCrossDock(Boolean crossDock) {
        this.crossDock = crossDock;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getPackageLevel() {
        return packageLevel;
    }

    public void setPackageLevel(String packageLevel) {
        this.packageLevel = packageLevel;
    }

    public Boolean getUsePackLimit() {
        return usePackLimit;
    }

    public void setUsePackLimit(Boolean usePackLimit) {
        this.usePackLimit = usePackLimit;
    }

    public String getPackageLevelEnd() {
        return packageLevelEnd;
    }

    public void setPackageLevelEnd(String packageLevelEnd) {
        this.packageLevelEnd = packageLevelEnd;
    }

    public Boolean getEnoughInv() {
        return enoughInv;
    }

    public void setEnoughInv(Boolean enoughInv) {
        this.enoughInv = enoughInv;
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

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
