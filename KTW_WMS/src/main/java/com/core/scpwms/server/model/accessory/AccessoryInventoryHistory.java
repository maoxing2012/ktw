package com.core.scpwms.server.model.accessory;

import java.util.Date;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.domain.HistoryTrackingEntity;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 库存交易日志
 * 记录所有的库存交易（库存变化）
 * 变化原因等
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class AccessoryInventoryHistory extends TrackingEntity {
    /** 仓库  */
    private Warehouse wh;

    /** 辅料 */
    private Accessory accessory;

    /** 库位 */
    private Bin bin;

    /** 辅料库存状态 */
    private String status;

    /** Qty */
    private Double qty;

    /**
     *  交易类型 
     * @see EnuInvenHisType 
     * 
     * */
    private String hisType;

    /** 操作单号 */
    private String orderSeq;

    /** 部门 */
    private String department;

    /** 作业时间 */
    private Date operateTime;

    /** 作业人员姓名 */
    private String laborNames;

    /** 作业备注 */
    private String description;

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public Accessory getAccessory() {
        return this.accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public Bin getBin() {
        return this.bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getQty() {
        return this.qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getHisType() {
        return this.hisType;
    }

    public void setHisType(String hisType) {
        this.hisType = hisType;
    }

    public String getOrderSeq() {
        return this.orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getLaborNames() {
        return this.laborNames;
    }

    public void setLaborNames(String laborNames) {
        this.laborNames = laborNames;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
