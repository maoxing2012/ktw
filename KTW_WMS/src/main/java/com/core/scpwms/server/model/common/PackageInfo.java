package com.core.scpwms.server.model.common;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuPackageLevel;

/**
 * 包装信息
 * 内含五层包装（PackageLevel）
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class PackageInfo extends TrackingEntity {

    /** 代码 */
    private String code;

    /** 名称 */
    private String name;

    /** 基本包装 */
    private PackageDetail p1000 = new PackageDetail();

    /** 内包装 */
    private PackageDetail p2100 = new PackageDetail();

    /** 箱包装 */
    private PackageDetail p2000 = new PackageDetail();

    /** 托盘包装 */
    private PackageDetail p3000 = new PackageDetail();

    /** 其他包装 */
    private PackageDetail p4000 = null;

    /** 是否有效 */
    private Boolean disabled = Boolean.FALSE;

    /** 描述 */
    private String description;

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

    public PackageDetail getP1000() {
        return this.p1000;
    }

    public void setP1000(PackageDetail p1000) {
        this.p1000 = p1000;
    }

    public PackageDetail getP2100() {
        return this.p2100;
    }

    public void setP2100(PackageDetail p2100) {
        this.p2100 = p2100;
    }

    public PackageDetail getP2000() {
        return this.p2000;
    }

    public void setP2000(PackageDetail p2000) {
        this.p2000 = p2000;
    }

    public PackageDetail getP3000() {
        return this.p3000;
    }

    public void setP3000(PackageDetail p3000) {
        this.p3000 = p3000;
    }

    public PackageDetail getP4000() {
        return this.p4000;
    }

    public void setP4000(PackageDetail p4000) {
        this.p4000 = p4000;
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

}
