package com.core.scpwms.server.model.task;

import java.util.Date;

import com.core.business.model.TrackingEntity;
import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.util.PrecisionUtils;

@SuppressWarnings("all")
public class WtHistory extends TrackingEntity {

    /** 
     * 作业任务
     */
    private WarehouseTask wt;

    /**作业人员*/
    private Labor labor;

    /** To Bin 默认为空、可手工分配、可系统自动分配 */
    private Bin descBin;

    /** To 容器编号 */
    private String descContainerSeq;

    /** To 托盘编号 */
    private String descPalletSeq;

    /** 执行数量（包装单位） */
    private Double executePackQty;

    /** 执行数量（基本单位） */
    private Double executeQty;

    /** 作业时间 */
    private Date operateTime;

    /** 关联单号1 */
    private String relatedBill1;

    /** 关联单号2 */
    private String relatedBill2;

    /** 关联单号3 */
    private String relatedBill3;

    /** 描述 */
    private String description;

    /** 库存属性 */
    private InventoryInfo invInfo;

    public WarehouseTask getWt() {
        return this.wt;
    }

    public void setWt(WarehouseTask wt) {
        this.wt = wt;
    }

    public Labor getLabor() {
        return this.labor;
    }

    public void setLabor(Labor labor) {
        this.labor = labor;
    }

    public Bin getDescBin() {
        return this.descBin;
    }

    public void setDescBin(Bin descBin) {
        this.descBin = descBin;
    }

    public String getDescContainerSeq() {
        return this.descContainerSeq;
    }

    public void setDescContainerSeq(String descContainerSeq) {
        this.descContainerSeq = descContainerSeq;
    }

    public String getDescPalletSeq() {
        return this.descPalletSeq;
    }

    public void setDescPalletSeq(String descPalletSeq) {
        this.descPalletSeq = descPalletSeq;
    }

    public Double getExecutePackQty() {
        return this.executePackQty;
    }

    public void setExecutePackQty(Double executePackQty) {
        this.executePackQty = executePackQty;
    }

    public Double getExecuteQty() {
        return this.executeQty;
    }

    public void setExecuteQty(Double executeQty) {
        this.executeQty = executeQty;
    }

    public Date getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelatedBill1() {
        return this.relatedBill1;
    }

    public void setRelatedBill1(String relatedBill1) {
        this.relatedBill1 = relatedBill1;
    }

    public String getRelatedBill2() {
        return this.relatedBill2;
    }

    public void setRelatedBill2(String relatedBill2) {
        this.relatedBill2 = relatedBill2;
    }

    public String getRelatedBill3() {
        return this.relatedBill3;
    }

    public void setRelatedBill3(String relatedBill3) {
        this.relatedBill3 = relatedBill3;
    }

    public InventoryInfo getInvInfo() {
        return this.invInfo;
    }

    public void setInvInfo(InventoryInfo invInfo) {
        this.invInfo = invInfo;
    }

    public void addExecuteQty(double qty) {
        if (qty > 0) {
            executeQty = DoubleUtil.add(executeQty, qty);
            executePackQty = PrecisionUtils.getPackQty(executeQty, invInfo.getPackageDetail());
        }
    }
    
    public void removeExecuteQty(double qty){
    	if (qty > 0) {
            executeQty = DoubleUtil.sub(executeQty, qty);
            executePackQty = PrecisionUtils.getPackQty(executeQty, invInfo.getPackageDetail());
        }
    }
}
