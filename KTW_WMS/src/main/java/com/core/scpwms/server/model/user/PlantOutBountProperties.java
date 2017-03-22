package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuBatchPickConf;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.PackageInfo;

/**
 * 货主作业设定（发货），包含以下内容
 * 
 * 1、缺省发货单位
 * 2、打印标签制定
 * 3、批拣方式
 * 4、预分配
 * 5、发货按单包装
 * 6、拆托盘分配（将托盘库存打散为包装库存.....待定）
 * 7、拆包装分配（将包装拆散为出货包装进行拣选，拣选后可能出现小数...待定）
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class PlantOutBountProperties extends TrackingEntity {

    /**
     * 货主
     */
    private Plant plant;
    /**
     * 缺省包装信息
     */
    private PackageInfo packageInfo;

    /**
     * 缺省发货单位
     * 发货单位优先级 
     * ASN > SKU Properties > outboundProperties
     * @see PackageInfo
     */
    private PackageDetail packageDetail;

    /**
     * 打印标签制定
     */
    private String outboundLabel;

    /**
     * 批拣方式
     * 集单/集货
     * @see EnuBatchPickConf
     */
    private String batchPick;

    /**
     * 发货按单包装
     */
    private Boolean packByOrder = Boolean.TRUE;

    /**
     * 预分配
     */
    private Boolean preAllocate = Boolean.FALSE;

    /**
     * 拆托盘分配
     */
    private Boolean splitPallet = Boolean.FALSE;

    /**
     * 拆包装分配
     */
    private Boolean splitPackage = Boolean.FALSE;

    /** 仓库前置日期(=从仓库接到订单到交货需要的时间，影响备货日期) */
    private Integer leadTime = 1;

    public Plant getPlant() {
        return this.plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public PackageInfo getPackageInfo() {
        return this.packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public PackageDetail getPackageDetail() {
        return this.packageDetail;
    }

    public void setPackageDetail(PackageDetail packageDetail) {
        this.packageDetail = packageDetail;
    }

    public String getOutboundLabel() {
        return this.outboundLabel;
    }

    public void setOutboundLabel(String outboundLabel) {
        this.outboundLabel = outboundLabel;
    }

    public String getBatchPick() {
        return this.batchPick;
    }

    public void setBatchPick(String batchPick) {
        this.batchPick = batchPick;
    }

    public Boolean getPackByOrder() {
        return this.packByOrder;
    }

    public void setPackByOrder(Boolean packByOrder) {
        this.packByOrder = packByOrder;
    }

    public Boolean getPreAllocate() {
        return this.preAllocate;
    }

    public void setPreAllocate(Boolean preAllocate) {
        this.preAllocate = preAllocate;
    }

    public Boolean getSplitPallet() {
        return this.splitPallet;
    }

    public void setSplitPallet(Boolean splitPallet) {
        this.splitPallet = splitPallet;
    }

    public Boolean getSplitPackage() {
        return this.splitPackage;
    }

    public void setSplitPackage(Boolean splitPackage) {
        this.splitPackage = splitPackage;
    }

    public Integer getLeadTime() {
        return this.leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

}
