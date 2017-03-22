package com.core.scpwms.server.model.inbound;

import java.util.Date;

import com.core.db.server.model.VersionalEntity;
import com.core.scpwms.server.enumerate.EnuAsnDetailStatus;
import com.core.scpwms.server.enumerate.EnuAsnReportStatus;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.user.Owner;

/**
 * Asn Detail
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class AsnDetail extends VersionalEntity {

    /** ASN */
    private Asn asn;

    /**
     * ASN明细状态
     * @see EnuAsnDetailStatus
     */
    private Long status;

    /** 行号 */
    private Double lineNo;
    
    /** 子行号 */
    private Integer subLineNo = 1;

    /** SKU */
    private Sku sku;

    /** 库存类型 */
    private Long stockDiv;
    
    /** 预期数量（基本单位） */
    private Double planQty = 0D;

    /** 实收数量（基本单位） */
    private Double executeQty = 0D;
    
    /** 回告EDI的数据（基本单位） */
    private Double closedQty = 0D;
    
    /** 预计收货的库存状态 */
    private String invStatus = EnuInvStatus.AVAILABLE;
    
    /** 预计收货的保质期*/
    private Date expDate;
    
    /** 实际收货的库存状态 */
    private String actInvStatus;
    
    /** 实际收货的保质期 */
    private Date actExpDate;

	/** 包装 */
    private PackageDetail planPackage;

    /** 扩展字段 */
    private String extString1;
    private String extString2;
    private String extString3;
    
    /** 备注 */
    private String description;

	/** 仓库备注 */
	private String extDescription;
	
	private Boolean isReported = Boolean.FALSE;

	public Asn getAsn() {
		return asn;
	}

	public void setAsn(Asn asn) {
		this.asn = asn;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Double getLineNo() {
		return lineNo;
	}

	public void setLineNo(Double lineNo) {
		this.lineNo = lineNo;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}
	
	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public Double getPlanQty() {
		return planQty;
	}

	public void setPlanQty(Double planQty) {
		this.planQty = planQty;
	}

	public Double getExecuteQty() {
		return executeQty;
	}

	public void setExecuteQty(Double executeQty) {
		this.executeQty = executeQty;
	}

	public String getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public PackageDetail getPlanPackage() {
		return planPackage;
	}

	public void setPlanPackage(PackageDetail planPackage) {
		this.planPackage = planPackage;
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
    
	public void addExecuteQty( Double qty ){
		this.executeQty += qty;
		this.asn.addExecuteQty(qty);
	}
	
	public void removeExecuteQty( Double qty ){
		this.executeQty -= qty;
		this.asn.removeExecuteQty(qty);
	}
	
	public void addClosedQty( Double qty ){
		this.closedQty += qty;
		this.asn.addClosedQty(qty);
	}
	
	public void removeClosedQty( Double qty ){
		this.closedQty -= qty;
		this.asn.removeClosedQty(qty);
	}
	
	public double getUnClosedQty(){
		return executeQty - closedQty;
	}
	
	public double getUnReceive(){
		return this.planQty - this.executeQty;
	}
	
	public Owner getOwner(){
		return asn.getOwner();
	}

	public Long getStockDiv() {
		return stockDiv;
	}

	public void setStockDiv(Long stockDiv) {
		this.stockDiv = stockDiv;
	}

	public Double getUnexecuteQty() {
		return this.planQty - this.executeQty;
	}

	public Date getActExpDate() {
		return actExpDate;
	}

	public void setActExpDate(Date actExpDate) {
		this.actExpDate = actExpDate;
	}

	public Integer getSubLineNo() {
		return subLineNo;
	}

	public void setSubLineNo(Integer subLineNo) {
		this.subLineNo = subLineNo;
	}

	public String getActInvStatus() {
		return actInvStatus;
	}

	public void setActInvStatus(String actInvStatus) {
		this.actInvStatus = actInvStatus;
	}

	public Double getClosedQty() {
		return closedQty;
	}

	public void setClosedQty(Double closedQty) {
		this.closedQty = closedQty;
	}

	public Boolean getIsReported() {
		return isReported;
	}

	public void setIsReported(Boolean isReported) {
		this.isReported = isReported;
	}

}
