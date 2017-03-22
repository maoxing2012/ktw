package com.core.scpwms.server.model.lot;

import com.core.business.model.TrackingEntity;

/**
 * 
 * <p>批次属性定义 </p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/06/25<br/>
 */
@SuppressWarnings("all")
public class LotInfo extends TrackingEntity {

    /** 代码 */
    private String code;

    /** 名称 */
    private String name;

    /**
     * 系统提供十组批次属性信息给用户自定义
     */
    private LotDetailInfo ldi1 = new LotDetailInfo();
    private LotDetailInfo ldi2 = new LotDetailInfo();
    private LotDetailInfo ldi3 = new LotDetailInfo();
    private LotDetailInfo ldi4 = new LotDetailInfo();
    private LotDetailInfo ldi5 = new LotDetailInfo();
    private LotDetailInfo ldi6 = new LotDetailInfo();
    private LotDetailInfo ldi7 = new LotDetailInfo();
    private LotDetailInfo ldi8 = new LotDetailInfo();
    private LotDetailInfo ldi9 = new LotDetailInfo();
    private LotDetailInfo ldi10 = new LotDetailInfo();

    /** 是否启用入库日期作为批次属性 */
    private Boolean inboundDateLot = Boolean.FALSE;

    /** 是否启用入库单号作为批次属性 */
    private Boolean inboundOrderLot = Boolean.FALSE;

    /** 是否启用入库日期作为库存属性 */
    private Boolean inboundDateInv = Boolean.FALSE;

    /** 是否启用入库单号作为库存属性 */
    private Boolean inboundOrderInv = Boolean.FALSE;

    /** 是否可用 */
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

    public LotDetailInfo getLdi1() {
        return this.ldi1;
    }

    public void setLdi1(LotDetailInfo ldi1) {
        this.ldi1 = ldi1;
    }

    public LotDetailInfo getLdi2() {
        return this.ldi2;
    }

    public void setLdi2(LotDetailInfo ldi2) {
        this.ldi2 = ldi2;
    }

    public LotDetailInfo getLdi3() {
        return this.ldi3;
    }

    public void setLdi3(LotDetailInfo ldi3) {
        this.ldi3 = ldi3;
    }

    public LotDetailInfo getLdi4() {
        return this.ldi4;
    }

    public void setLdi4(LotDetailInfo ldi4) {
        this.ldi4 = ldi4;
    }

    public LotDetailInfo getLdi5() {
        return this.ldi5;
    }

    public void setLdi5(LotDetailInfo ldi5) {
        this.ldi5 = ldi5;
    }

    public LotDetailInfo getLdi6() {
        return this.ldi6;
    }

    public void setLdi6(LotDetailInfo ldi6) {
        this.ldi6 = ldi6;
    }

    public LotDetailInfo getLdi7() {
        return this.ldi7;
    }

    public void setLdi7(LotDetailInfo ldi7) {
        this.ldi7 = ldi7;
    }

    public LotDetailInfo getLdi8() {
        return this.ldi8;
    }

    public void setLdi8(LotDetailInfo ldi8) {
        this.ldi8 = ldi8;
    }

    public LotDetailInfo getLdi9() {
        return this.ldi9;
    }

    public void setLdi9(LotDetailInfo ldi9) {
        this.ldi9 = ldi9;
    }

    public LotDetailInfo getLdi10() {
        return this.ldi10;
    }

    public void setLdi10(LotDetailInfo ldi10) {
        this.ldi10 = ldi10;
    }

    public Boolean getInboundDateLot() {
        return this.inboundDateLot;
    }

    public void setInboundDateLot(Boolean inboundDateLot) {
        this.inboundDateLot = inboundDateLot;
    }

    public Boolean getInboundOrderLot() {
        return this.inboundOrderLot;
    }

    public void setInboundOrderLot(Boolean inboundOrderLot) {
        this.inboundOrderLot = inboundOrderLot;
    }

    public Boolean getInboundDateInv() {
        return this.inboundDateInv;
    }

    public void setInboundDateInv(Boolean inboundDateInv) {
        this.inboundDateInv = inboundDateInv;
    }

    public Boolean getInboundOrderInv() {
        return this.inboundOrderInv;
    }

    public void setInboundOrderInv(Boolean inboundOrderInv) {
        this.inboundOrderInv = inboundOrderInv;
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
