package com.core.scpwms.server.model.inbound;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.core.business.model.domain.ContractInfo;
import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;
import com.core.scpwms.server.enumerate.EnuAsnReportStatus;
import com.core.scpwms.server.enumerate.EnuAsnStatus;
import com.core.scpwms.server.enumerate.EnuPutawayPlanStatus;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 提前到货通知 产生方式：手工创建、InboundDelivery生成（按行）
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class Asn extends OrderTrackingEntityWithStatus {

	/** 仓库信息 */
	private Warehouse wh;

	/** 公司 */
	private Plant plant;
	
	/** 货主 */
	private Owner owner;

	/** ASN Number */
	private String asnNumber;
	
	/** 运输单号 */
	private String transNumber;

	/** PO单号 */
	private String relatedBill1;

	/** 合同号 */
	private String relatedBill2;
	
	/** 原始PO号 */
	private String relatedBill3;

	/** 开单时间 */
	private Date transactionDate;
	
	/** 生效时间 */
	private Date publishDate;

	/** 预计到达时间 */
	private Date eta;

	/** 收货开始时间 */
	private Date revieveStart;

	/** 收货结束时间 */
	private Date finishStart;
	
	/** 发件人/供应商 */
	private BizOrg supplier;
	
	/** 发件人详细 */
	private ContractInfo shipFrom;
	
	/** 接口接收/手工创建 */
	private Boolean ediData = Boolean.FALSE;

	/** 预期数量（基本单位） */
	private double planQty;

	/** 实收数量（基本单位） */
	private double executeQty;
	
	/** 回告数量（基本单位） */
	private double closedQty;

	/** 单据类型(收货限定) */
	private OrderType orderType;

	/** 收货库位 */
	private Bin bin;

	/** 承运商/货运人 */
	private Carrier carrier;
	
	/** 总价 */
	private Double sumPrice = 0d;

	/** 总重 */
	private Double sumWeight = 0d;

	/** 总体积 */
	private Double sumVolume = 0d;

	/** 品项数量 */
	private double skuCount = 0D;

	/** 客户备注 */
	private String description;

	/** 仓库备注 */
	private String extDescription;
	
	/** 運送便CD */
	private String extString1;
	
	/** 運送便名 */
	private String extString2;
	
	private String extString3;
	
	private Long reportStatus = EnuAsnReportStatus.STATUS800;
	
	private Set<AsnDetail> details = new HashSet<AsnDetail>();
	
	public Warehouse getWh() {
		return wh;
	}
	public void setWh(Warehouse wh) {
		this.wh = wh;
	}
	public Plant getPlant() {
		return plant;
	}
	public void setPlant(Plant plant) {
		this.plant = plant;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public String getAsnNumber() {
		return asnNumber;
	}
	public void setAsnNumber(String asnNumber) {
		this.asnNumber = asnNumber;
	}
	public String getTransNumber() {
		return transNumber;
	}
	public void setTransNumber(String transNumber) {
		this.transNumber = transNumber;
	}
	public String getRelatedBill1() {
		return relatedBill1;
	}
	public void setRelatedBill1(String relatedBill1) {
		this.relatedBill1 = relatedBill1;
	}
	public String getRelatedBill2() {
		return relatedBill2;
	}
	public void setRelatedBill2(String relatedBill2) {
		this.relatedBill2 = relatedBill2;
	}
	public String getRelatedBill3() {
		return relatedBill3;
	}
	public void setRelatedBill3(String relatedBill3) {
		this.relatedBill3 = relatedBill3;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Date getEta() {
		return eta;
	}
	public void setEta(Date eta) {
		this.eta = eta;
	}
	public Date getRevieveStart() {
		return revieveStart;
	}
	public void setRevieveStart(Date revieveStart) {
		this.revieveStart = revieveStart;
	}
	public Date getFinishStart() {
		return finishStart;
	}
	public void setFinishStart(Date finishStart) {
		this.finishStart = finishStart;
	}
	public BizOrg getSupplier() {
		return supplier;
	}
	public void setSupplier(BizOrg supplier) {
		this.supplier = supplier;
	}
	public ContractInfo getShipFrom() {
		return shipFrom;
	}
	public void setShipFrom(ContractInfo shipFrom) {
		this.shipFrom = shipFrom;
	}
	public Boolean getEdiData() {
		return ediData;
	}
	public void setEdiData(Boolean ediData) {
		this.ediData = ediData;
	}
	public double getPlanQty() {
		return planQty;
	}
	public void setPlanQty(double planQty) {
		this.planQty = planQty;
	}
	public double getExecuteQty() {
		return executeQty;
	}
	public void setExecuteQty(double executeQty) {
		this.executeQty = executeQty;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public Bin getBin() {
		return bin;
	}
	public void setBin(Bin bin) {
		this.bin = bin;
	}
	public Carrier getCarrier() {
		return carrier;
	}
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
	public Double getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}
	public Double getSumWeight() {
		return sumWeight;
	}
	public void setSumWeight(Double sumWeight) {
		this.sumWeight = sumWeight;
	}
	public Double getSumVolume() {
		return sumVolume;
	}
	public void setSumVolume(Double sumVolume) {
		this.sumVolume = sumVolume;
	}
	public double getSkuCount() {
		return skuCount;
	}
	public void setSkuCount(double skuCount) {
		this.skuCount = skuCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getExtDescription() {
		return extDescription;
	}
	public void setExtDescription(String extDescription) {
		this.extDescription = extDescription;
	}
	public String getExtString1() {
		return extString1;
	}
	public void setExtString1(String extString1) {
		this.extString1 = extString1;
	}
	public String getExtString2() {
		return extString2;
	}
	public void setExtString2(String extString2) {
		this.extString2 = extString2;
	}
	public String getExtString3() {
		return extString3;
	}
	public void setExtString3(String extString3) {
		this.extString3 = extString3;
	}
	@Override
	public String getStatusEnum() {
		return EnuAsnStatus.class.getSimpleName();
	}
	
	public double getUnClosedQty(){
		return this.executeQty - this.closedQty;
	}
	public double getUnExecuteQty(){
		return this.planQty - this.executeQty;
	}
	public Set<AsnDetail> getDetails() {
		return details;
	}
	public void setDetails(Set<AsnDetail> details) {
		this.details = details;
	}
	public void addExecuteQty( Double qty ){
		this.executeQty += qty;
	}
	public void removeExecuteQty( Double qty ){
		this.executeQty -= qty;
	}
	
	public void addClosedQty( Double qty ){
		this.closedQty += qty;
		
		if( this.closedQty > 0 
				&& (this.reportStatus == null || this.reportStatus == EnuAsnReportStatus.STATUS800.longValue()) ){
			this.reportStatus = EnuAsnReportStatus.STATUS900;
		}
		
		if( this.closedQty <= this.executeQty 
				&& getStatus().longValue() == EnuAsnStatus.STATUS400 
				&& this.reportStatus.longValue() == EnuAsnReportStatus.STATUS900.longValue() ){
			this.reportStatus = EnuAsnReportStatus.STATUS999;
		}
	}
	public void removeClosedQty( Double qty ){
		this.closedQty -= qty;
		
		if( this.closedQty < this.executeQty ){
			this.reportStatus = EnuAsnReportStatus.STATUS900;
		}
		
		if( this.closedQty <= 0D ){
			this.reportStatus = EnuAsnReportStatus.STATUS800;
		}
	}
	
	public double getClosedQty() {
		return closedQty;
	}
	public void setClosedQty(double closedQty) {
		this.closedQty = closedQty;
	}
	public Long getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(Long reportStatus) {
		this.reportStatus = reportStatus;
	}
	
}
