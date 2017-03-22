package com.core.scpwms.server.model.accessory;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.model.common.SkuType;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 
 * <p>辅料</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/08<br/>
 */
@SuppressWarnings("all")
public class Accessory extends TrackingEntity {

    /** 所属仓库 */
    private Warehouse wh;

    /** 商品代码 */
    private String code;

    /** 商品名称 */
    private String name;

    /** 规格 */
    private String specs;

    /** 品牌名 */
    private String brandName;

    /** 计量单位 */
    private String unit;

    /** 货品大类 */
    private SkuType it1000;

    /** 货品中类 */
    private SkuType it2000;

    /** 货品小类 */
    private SkuType it3000;

    /** 是否可用 */
    private Boolean disabled = Boolean.FALSE;

    /** 描述 */
    private String description;

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
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

    public String getSpecs() {
        return this.specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public SkuType getIt1000() {
        return this.it1000;
    }

    public void setIt1000(SkuType it1000) {
        this.it1000 = it1000;
    }

    public SkuType getIt2000() {
        return this.it2000;
    }

    public void setIt2000(SkuType it2000) {
        this.it2000 = it2000;
    }

    public SkuType getIt3000() {
        return this.it3000;
    }

    public void setIt3000(SkuType it3000) {
        this.it3000 = it3000;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

}
