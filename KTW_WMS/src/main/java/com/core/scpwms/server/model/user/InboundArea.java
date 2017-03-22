package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 
 * @description   收件区域维护
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-1-3
 * @version        V1.0<br/>
 */
@SuppressWarnings("all")
public class InboundArea extends TrackingEntity {

    /**
     * 代码
     */
    private String code;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 邮编范围（开始）
     */
    private Long zipCodeStart;

    /**
     * 邮编范围（结束）
     */
    private Long zipCodeEnd;

    /**
     * 是否失效
     */
    private Boolean disabled = Boolean.FALSE;

    /**
     * 描述
     */
    private String description;

    /**
     * 待发运集货区域
     */
    private Bin shipBin;

    public Long getZipCodeStart() {
        return zipCodeStart;
    }

    public void setZipCodeStart(Long zipCodeStart) {
        this.zipCodeStart = zipCodeStart;
    }

    public Long getZipCodeEnd() {
        return zipCodeEnd;
    }

    public void setZipCodeEnd(Long zipCodeEnd) {
        this.zipCodeEnd = zipCodeEnd;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Bin getShipBin() {
        return shipBin;
    }

    public void setShipBin(Bin shipBin) {
        this.shipBin = shipBin;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
