/**
 * (C)2010 MBP Corporation. All rights reserved.
 * プロジェクト名 : 広域物流倉庫システム
 * システム名     : 倉庫システム
 * ファイル名     : RequestReportDetail.java
 */

package com.core.scpwms.server.model.fee;

import java.math.BigDecimal;

import com.core.db.server.model.Entity;
import com.core.scpwms.server.model.common.Sku;

/**
 * <p>请求书明细</p>
 *
 * @version 1.0
 * @author MBP :毛　幸<br/>
 * 変更履歴 <br/>
 *  2013-6-11 : MBP 毛　幸: 新規作成<br/>
 */
@SuppressWarnings("all")
public class RequestReportDetail extends Entity {

    private RequestReportHead head;

    private Sku sku;
    
    /**
     * 期数（3期：3，2期：2）
     */
    private Long termNum;
    
    /** 前期残1 */
    private Double prevQty1 = 0D;

    /** 入庫数1 */
    private Double inboundQty1 = 0D;

    /** 出庫数1 */
    private Double outboundQty1 = 0D;

    /** 調整入1 */
    private Double adjustInQty1 = 0D;

    /** 調整出1 */
    private Double adjustOutQty1 = 0D;

    /** 前期残2 */
    private Double prevQty2 = 0D;

    /** 入庫数2 */
    private Double inboundQty2 = 0D;

    /** 出庫数2 */
    private Double outboundQty2 = 0D;

    /** 調整入2 */
    private Double adjustInQty2 = 0D;

    /** 調整出2 */
    private Double adjustOutQty2 = 0D;

    /** 前期残3 */
    private Double prevQty3 = 0D;

    /** 入庫数3 */
    private Double inboundQty3 = 0D;

    /** 出庫数3 */
    private Double outboundQty3 = 0D;

    /** 調整入3 */
    private Double adjustInQty3 = 0D;

    /** 調整出3 */
    private Double adjustOutQty3 = 0D;
    
    /** 费率 */
    private FeeType feeType4Stock;
    /** 保管料単価 */
    private Double storagePrice = 0D;

    /** 费率 */
    private FeeType feeType4In;
    /** 入庫荷役料単価 */
    private Double inboundPrice = 0D;

    /** 费率 */
    private FeeType feeType4Out;
    /** 出庫荷役料単価 */
    private Double outboundPrice = 0D;
    
    private Double unitWeight;
    
    private Double unitVolume;

	public RequestReportHead getHead() {
		return head;
	}

	public void setHead(RequestReportHead head) {
		this.head = head;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public Double getPrevQty1() {
		return prevQty1;
	}

	public void setPrevQty1(Double prevQty1) {
		this.prevQty1 = prevQty1;
	}

	public Double getInboundQty1() {
		return inboundQty1;
	}

	public void setInboundQty1(Double inboundQty1) {
		this.inboundQty1 = inboundQty1;
	}

	public Double getOutboundQty1() {
		return outboundQty1;
	}

	public void setOutboundQty1(Double outboundQty1) {
		this.outboundQty1 = outboundQty1;
	}

	public Double getAdjustInQty1() {
		return adjustInQty1;
	}

	public void setAdjustInQty1(Double adjustInQty1) {
		this.adjustInQty1 = adjustInQty1;
	}

	public Double getAdjustOutQty1() {
		return adjustOutQty1;
	}

	public void setAdjustOutQty1(Double adjustOutQty1) {
		this.adjustOutQty1 = adjustOutQty1;
	}

	public Double getPrevQty2() {
		return prevQty2;
	}

	public void setPrevQty2(Double prevQty2) {
		this.prevQty2 = prevQty2;
	}

	public Double getInboundQty2() {
		return inboundQty2;
	}

	public void setInboundQty2(Double inboundQty2) {
		this.inboundQty2 = inboundQty2;
	}

	public Double getOutboundQty2() {
		return outboundQty2;
	}

	public void setOutboundQty2(Double outboundQty2) {
		this.outboundQty2 = outboundQty2;
	}

	public Double getAdjustInQty2() {
		return adjustInQty2;
	}

	public void setAdjustInQty2(Double adjustInQty2) {
		this.adjustInQty2 = adjustInQty2;
	}

	public Double getAdjustOutQty2() {
		return adjustOutQty2;
	}

	public void setAdjustOutQty2(Double adjustOutQty2) {
		this.adjustOutQty2 = adjustOutQty2;
	}

	public Double getPrevQty3() {
		return prevQty3;
	}

	public void setPrevQty3(Double prevQty3) {
		this.prevQty3 = prevQty3;
	}

	public Double getInboundQty3() {
		return inboundQty3;
	}

	public void setInboundQty3(Double inboundQty3) {
		this.inboundQty3 = inboundQty3;
	}

	public Double getOutboundQty3() {
		return outboundQty3;
	}

	public void setOutboundQty3(Double outboundQty3) {
		this.outboundQty3 = outboundQty3;
	}

	public Double getAdjustInQty3() {
		return adjustInQty3;
	}

	public void setAdjustInQty3(Double adjustInQty3) {
		this.adjustInQty3 = adjustInQty3;
	}

	public Double getAdjustOutQty3() {
		return adjustOutQty3;
	}

	public void setAdjustOutQty3(Double adjustOutQty3) {
		this.adjustOutQty3 = adjustOutQty3;
	}

	public Double getStoragePrice() {
		return storagePrice;
	}

	public void setStoragePrice(Double storagePrice) {
		this.storagePrice = storagePrice;
	}

	public Double getInboundPrice() {
		return inboundPrice;
	}

	public void setInboundPrice(Double inboundPrice) {
		this.inboundPrice = inboundPrice;
	}

	public Double getOutboundPrice() {
		return outboundPrice;
	}

	public void setOutboundPrice(Double outboundPrice) {
		this.outboundPrice = outboundPrice;
	}

	public FeeType getFeeType4Stock() {
		return feeType4Stock;
	}

	public void setFeeType4Stock(FeeType feeType4Stock) {
		this.feeType4Stock = feeType4Stock;
	}

	public FeeType getFeeType4In() {
		return feeType4In;
	}

	public void setFeeType4In(FeeType feeType4In) {
		this.feeType4In = feeType4In;
	}

	public FeeType getFeeType4Out() {
		return feeType4Out;
	}

	public void setFeeType4Out(FeeType feeType4Out) {
		this.feeType4Out = feeType4Out;
	}

	public Double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	public Double getUnitVolume() {
		return unitVolume;
	}

	public void setUnitVolume(Double unitVolume) {
		this.unitVolume = unitVolume;
	}

	public Long getTermNum() {
		return termNum;
	}

	public void setTermNum(Long termNum) {
		this.termNum = termNum;
	}

}
