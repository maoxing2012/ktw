package com.core.scpwms.server.model.history;

import java.util.Date;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.common.OrderType;
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
public class InventoryHistory extends TrackingEntity {
    /** 仓库  */
    private Warehouse wh;

    /** 批次属性在表格内显示值 */
    private String dispLot;

    /** Qty */
    private Double qtyEach;

    /** Qty Package */
    private Double qtyPkg;

    /** 交易类型 @see EnuInvenHisType */
    private String hisType;

    /** 操作单号 */
    private String orderSeq;

    /** 操作单据类型 */
    private OrderType iot;

    /** 关联志ID */
    private Long referenceHis;

    /** 作业时间 */
    private Date operateTime;

    /** 作业人员 */
    private Labor labor;

    /** 作业人员姓名 */
    private String laborNames;

    /** 作业备注 */
    private String operateMemo;

    /** 相关单据号1（扩展用）*/
    private String relatedBill1;

    /** 相关单据号2（扩展用）*/
    private String relatedBill2;

    /** 相关单据号3（扩展用）*/
    private String relatedBill3;

    /** 操作对象库存信息 */
    private InventoryInfo invInfo;

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public String getDispLot() {
        return this.dispLot;
    }

    public void setDispLot(String dispLot) {
        this.dispLot = dispLot;
    }

    public Double getQtyEach() {
        return this.qtyEach;
    }

    public void setQtyEach(Double qtyEach) {
        this.qtyEach = qtyEach;
    }

    public Double getQtyPkg() {
        return this.qtyPkg;
    }

    public void setQtyPkg(Double qtyPkg) {
        this.qtyPkg = qtyPkg;
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

    public OrderType getIot() {
        return this.iot;
    }

    public void setIot(OrderType iot) {
        this.iot = iot;
    }

    public Long getReferenceHis() {
        return this.referenceHis;
    }

    public void setReferenceHis(Long referenceHis) {
        this.referenceHis = referenceHis;
    }

    public Date getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Labor getLabor() {
        return this.labor;
    }

    public void setLabor(Labor labor) {
        this.labor = labor;
    }

    public String getLaborNames() {
        return this.laborNames;
    }

    public void setLaborNames(String laborNames) {
        this.laborNames = laborNames;
    }

    public String getOperateMemo() {
        return this.operateMemo;
    }

    public void setOperateMemo(String operateMemo) {
        this.operateMemo = operateMemo;
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

}
