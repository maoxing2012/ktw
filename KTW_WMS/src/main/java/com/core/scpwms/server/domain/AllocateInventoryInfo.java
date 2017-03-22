package com.core.scpwms.server.domain;

import java.util.Date;
import java.util.Map;

import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.warehouse.Bin;

@SuppressWarnings("all")
public class AllocateInventoryInfo extends DomainModel {

    /**库存信息*/
    public Inventory inv;

    /**库存日期*/
    private Date inboundDate;

    /**保质期处理*/
    private Date startDate;
    private Date expiryDate;

    /**对应BIN ID和代码*/
    private Long binId;
    private String binCode;

    /**可用库存*/
    private double avaliableQuantity;

    /** 标记托盘库位*/
    private String palletNo = "0";

    /** 包装单位折算系数*/
    private Double coefficient = 0D;

    public AllocateInventoryInfo(Inventory inv, Integer dayOfExpiry, Bin bin) {

        this.inv = inv;
        // 入库日期
        this.inboundDate = inv.getInboundDate();
        // 保质期处理
        Quant quant = inv.getQuantInv().getQuant();
        Map<String, Date> dates = quant.getExpireInfo();
        this.startDate = dates.get("START");
        this.expiryDate = dates.get("END");
        // 对应BIN ID和代码
        this.binId = bin.getId();
        this.binCode = bin.getBinCode();

        // 可用库存
        this.avaliableQuantity = this.inv.getAvaliableQuantity();

        // 托盘号，用来标志是托盘库存
        if (inv.getContainer() != null) {
            this.palletNo = inv.getContainer().getPalletSeq() == null ? "0" : "1";
        } else {
            this.palletNo = "0";
        }

        // 包装折算系数
        this.coefficient = inv.getBasePackage().getCoefficient() == null ? 99999D : inv.getBasePackage().getCoefficient();

    }

    public Inventory getInv() {
        return this.inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public Date getInboundDate() {
        return this.inboundDate;
    }

    public void setInboundDate(Date inboundDate) {
        this.inboundDate = inboundDate;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getBinId() {
        return this.binId;
    }

    public void setBinId(Long binId) {
        this.binId = binId;
    }

    public String getBinCode() {
        return this.binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public double getAvaliableQuantity() {
        return this.avaliableQuantity;
    }

    public void setAvaliableQuantity(double avaliableQuantity) {
        this.avaliableQuantity = avaliableQuantity;
    }

    public String getPalletNo() {
        return this.palletNo;
    }

    public void setPalletNo(String palletNo) {
        this.palletNo = palletNo;
    }

    public Double getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }
}
