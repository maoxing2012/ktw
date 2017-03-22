package com.core.scpwms.server.model.outbound;

import java.util.Date;
import java.util.Set;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.VersionalEntity;
import com.core.scpwms.server.domain.AllocateDoc;
import com.core.scpwms.server.domain.AllocateDocDetail;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuStockDiv;
import com.core.scpwms.server.enumerate.EnuWtProcessType;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 对应SO-detail结构
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 * 
 */
@SuppressWarnings("all")
public class OutboundDeliveryDetail extends VersionalEntity implements AllocateDocDetail {

	/** TRIP */
	private OutboundDelivery obd;
	
	/** 状态 @see EnuOutboundDeliveryDetailStatus */
	private Long status;
	
	/** 货主 */
	private Owner owner;

	/** 行号 */
	private Double lineNo;
	
	/** 子行号 */
	private Integer subLineNo = 1;

	/** SKU */
	private Sku sku;

	/** 期待包装 */
	private PackageDetail packageDetail;

	/** 预期箱数 */
	private Double caseQty = 0D;
	
	/** 预期数量(EA) */
	private Double planQty = 0D;

	/** 分配数量(EA) */
	private Double allocateQty = 0D;

	/** 拣选数量(EA) */
	private double pickUpQty = 0D;
	
	/** 打包数数量(EA) */
	private Double packedQty= 0D;
	
	/** 发运数量(EA) */
	private Double executeQty = 0D;
	
	/** @see EnuStockDiv */
	private Long stockDiv;
	
	/** 指定库存状态 */
	private String invStatus = EnuInvStatus.AVAILABLE;
	
	/** NS保质期*/
    private Date expDate;
    
    /** MIN保质期 */
    private Date expDateMin;
    
    /** MAX保质期 */
    private Date expDateMax;
    
    /** 指定保质期 */
    private Date expDateUni;
    
    /** 能不能混保质期，F：不能混 T：可以混 */
    private Boolean canMixExp = Boolean.TRUE;

	/** 备注信息 */
	private String description;
	
	private Set<WarehouseTask> tasks;
	
	// -------------通用项目-----------------
	// NS:单据信息
	private String extString1;
	//　NS:5伝票番号
	private String extString2;
	// NS:入力単位
	private String extString3;
	// NS:消費是区分 0:外税　１：内税　２：非課税
	private String extString4;
	private String extString5;
	private String extString6;
	private String extString7;
	private String extString8;
	private String extString9;
	private String extString10;
	
	// NS：基本単位 / 入力単位的折算系数
	private Double extDouble1;
	// NS:税率
	private Double extDouble2;
	// NS：入力数
	private Double extDouble3;
	// NS：基本数
	private Double extDouble4;
	// NS按Line合计的总数（入力単位）
	private Double extDouble5;
	// NS按Line合计的总数（PS単位）
	private Double extDouble6;
	
	// NS：入力単にの単価
	private Double extPrice1;
	private Double extPrice2;
	private Double extPrice3;
	
	private Boolean isReported = Boolean.FALSE;
	
	public OutboundDelivery getObd() {
		return this.obd;
	}

	public void setObd(OutboundDelivery obd) {
		this.obd = obd;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Owner getOwner() {
		if( this.owner == null )
			return obd.getOwner();
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Double getLineNo() {
		return this.lineNo;
	}

	public void setLineNo(Double lineNo) {
		this.lineNo = lineNo;
	}

	public Sku getSku() {
		return this.sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public PackageDetail getPackageDetail() {
		return this.packageDetail;
	}

	public void setPackageDetail(PackageDetail packageDetail) {
		this.packageDetail = packageDetail;
	}

	public Double getPlanQty() {
		return this.planQty;
	}

	public void setPlanQty(Double planQty) {
		this.planQty = planQty;
	}

	public Double getAllocateQty() {
		return this.allocateQty;
	}

	public void setAllocateQty(Double allocateQty) {
		this.allocateQty = allocateQty;
	}

	public double getPickUpQty() {
		return this.pickUpQty;
	}

	public void setPickUpQty(double pickUpQty) {
		this.pickUpQty = pickUpQty;
	}
	
	public Double getPackedQty() {
		return this.packedQty;
	}

	public void setPackedQty(Double packedQty) {
		this.packedQty = packedQty;
	}

	public Double getExecuteQty() {
		return this.executeQty;
	}

	public void setExecuteQty(Double executeQty) {
		this.executeQty = executeQty;
	}

	public String getExtString1() {
		return this.extString1;
	}

	public void setExtString1(String extString1) {
		this.extString1 = extString1;
	}

	public String getExtString2() {
		return this.extString2;
	}

	public void setExtString2(String extString2) {
		this.extString2 = extString2;
	}

	public String getExtString3() {
		return this.extString3;
	}

	public void setExtString3(String extString3) {
		this.extString3 = extString3;
	}
	
	public String getExtString4() {
		return extString4;
	}

	public void setExtString4(String extString4) {
		this.extString4 = extString4;
	}

	public String getExtString5() {
		return extString5;
	}

	public void setExtString5(String extString5) {
		this.extString5 = extString5;
	}

	public String getExtString6() {
		return extString6;
	}

	public void setExtString6(String extString6) {
		this.extString6 = extString6;
	}

	public String getExtString7() {
		return extString7;
	}

	public void setExtString7(String extString7) {
		this.extString7 = extString7;
	}

	public String getExtString8() {
		return extString8;
	}

	public void setExtString8(String extString8) {
		this.extString8 = extString8;
	}

	public String getExtString9() {
		return extString9;
	}

	public void setExtString9(String extString9) {
		this.extString9 = extString9;
	}

	public String getExtString10() {
		return extString10;
	}

	public void setExtString10(String extString10) {
		this.extString10 = extString10;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getToShipQty() {
		return DoubleUtil.sub(pickUpQty, executeQty);
	}
	
	public Double getCaseQty() {
		return this.caseQty;
	}

	public void setCaseQty(Double caseQty) {
		this.caseQty = caseQty;
	}
	
	@Override
	public OrderType OrderType() {
		return getObd().getOrderType();
	}

	@Override
	public void allocate(Inventory inventory, double quantity) {
		addAllocateQty(quantity);
	}
	
	@Override
	public void unallocate(double quantity){
		removeAllocateQty(quantity);
	}
	
	@Override
	public AllocateDoc getAllocateDoc() {
		return getObd();
	}

	@Override
	public Bin getBin() {
		return getObd().getDescBin();
	}

	@Override
	public String getTaskProcessType() {
		return EnuWtProcessType.PK_REQ;// 拣选下架
	}

	@Override
	public Double getUnAllocateQty() {
		return DoubleUtil.sub(planQty, allocateQty);
	}

	public double getUnPickUpQty() {
		return DoubleUtil.sub(planQty, pickUpQty);
	}

	public double getAllocateUnPickUpQty() {
		return DoubleUtil.sub(allocateQty, pickUpQty);
	}

	public double getUnExecuteQty() {
		return DoubleUtil.sub(planQty, executeQty);
	}

	public double getPickedUnExecuteQty() {
		return DoubleUtil.sub(pickUpQty, executeQty);
	}
	
	public double getPickedUnPackedQty() {
		return DoubleUtil.sub(pickUpQty, packedQty);
	}
	
	public double getPackedUnExecuteQty() {
		return DoubleUtil.sub(packedQty, executeQty);
	}
	
	public double getPickupPackQty(){
		return PrecisionUtils.getPackQty(pickUpQty, packageDetail);
	}
	
	public double getPackedPackQty(){
		return PrecisionUtils.getPackQty(packedQty, packageDetail);
	}
	
	public double getPickedUnPackedPackQty(){
		return PrecisionUtils.getPackQty(getPickedUnPackedQty(), packageDetail);
	}
	
	public Integer getSubLineNo() {
		return subLineNo;
	}

	public void setSubLineNo(Integer subLineNo) {
		this.subLineNo = subLineNo;
	}

	public Long getStockDiv() {
		return stockDiv;
	}

	public void setStockDiv(Long stockDiv) {
		this.stockDiv = stockDiv;
	}

	public String getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	@Override
	public Warehouse getWh() {
		return getObd().getWh();
	}

	public void addPlanQty(double qty) {
		if (qty > 0) {
			double baseQty = PrecisionUtils.formatByBasePackage(qty,
					packageDetail);
			planQty = DoubleUtil.add(planQty, baseQty);
		}
	}

	public void addAllocateQty(double qty) {
		if (qty > 0) {
			allocateQty = DoubleUtil.add(allocateQty, qty);
			if( obd != null ){
				getObd().addAllocateQty(qty);
			}
		}
	}

	public void addPickUpQty(double qty) {
		if (qty > 0) {
			pickUpQty = DoubleUtil.add(pickUpQty, qty);
			
			if( obd != null ){
				getObd().addPickUpQty(qty);
			}
		}
	}
	
	public void addPackedQty(double qty) {
		if (qty > 0) {
			packedQty= DoubleUtil.add(packedQty, qty);
			
			if( obd != null ){
				getObd().addPackedQty(qty);
			}
		}
	}
	
	public void addExecuteQty(double qty) {
		if (qty > 0) {
			executeQty = DoubleUtil.add(executeQty, qty);
			
			if( obd != null ){
				getObd().addExecuteQty(qty);
			}
		}
	}

	public void removePlanQty(double qty) {
		if (qty > 0) {
			double baseQty = PrecisionUtils.formatByBasePackage(qty,
					packageDetail);
			planQty = DoubleUtil.sub(planQty, baseQty);
			
			if( obd != null ){
				getObd().removePlanQty(qty);
			}
		}
	}

	public void removeAllocateQty(double qty) {
		if (qty > 0) {
			allocateQty = DoubleUtil.sub(allocateQty, qty);
			
			if( obd != null ){
				getObd().removeAllocateQty(qty);
			}
		}
	}

	public void removePickUpQty(double qty) {
		if (qty > 0) {
			pickUpQty = DoubleUtil.sub(pickUpQty, qty);
			
			if( obd != null ){
				getObd().removePickUpQty(qty);
			}
		}
	}
	
	public void removePackedQty(double qty) {
		if (qty > 0) {
			packedQty = DoubleUtil.sub(packedQty, qty);
			
			if( obd != null ){
				getObd().removePackedQty(qty);
			}
		}
	}

	public void removeExecuteQty(double qty) {
		if (qty > 0) {
			executeQty = DoubleUtil.sub(executeQty, qty);
			
			if( obd != null ){
				getObd().removeExecuteQty(qty);
			}
		}
	}

    public void clear() {
        // 清空波次，状态信息
        setId(null);
        obd = null;
        planQty = 0D;
    	allocateQty = 0D;
    	pickUpQty = 0D;
    	packedQty= 0D;
    	executeQty = 0D;
        status = null;
    }

	@Override
	public Set<WarehouseTask> getTasks() {
		return tasks;
	}
	
	public void setTasks(Set<WarehouseTask> tasks) {
		this.tasks = tasks;
	}

	@Override
	public LotInputData getLotData() {
		LotInputData lotData = new LotInputData();
		
//		if( expDate != null ){
//			lotData.setProperty1(DateUtil.getStringDate(expDate, DateUtil.PURE_DATE_FORMAT));
//		}
		
		return lotData;
	}

	public Double getExtDouble1() {
		return extDouble1;
	}

	public void setExtDouble1(Double extDouble1) {
		this.extDouble1 = extDouble1;
	}

	public Double getExtDouble2() {
		return extDouble2;
	}

	public void setExtDouble2(Double extDouble2) {
		this.extDouble2 = extDouble2;
	}

	public Double getExtDouble3() {
		return extDouble3;
	}

	public void setExtDouble3(Double extDouble3) {
		this.extDouble3 = extDouble3;
	}

	public Double getExtDouble4() {
		return extDouble4;
	}

	public void setExtDouble4(Double extDouble4) {
		this.extDouble4 = extDouble4;
	}

	public Double getExtDouble5() {
		return extDouble5;
	}

	public void setExtDouble5(Double extDouble5) {
		this.extDouble5 = extDouble5;
	}

	public Double getExtPrice1() {
		return extPrice1;
	}

	public void setExtPrice1(Double extPrice1) {
		this.extPrice1 = extPrice1;
	}

	public Double getExtPrice2() {
		return extPrice2;
	}

	public void setExtPrice2(Double extPrice2) {
		this.extPrice2 = extPrice2;
	}

	public Double getExtPrice3() {
		return extPrice3;
	}

	public void setExtPrice3(Double extPrice3) {
		this.extPrice3 = extPrice3;
	}

	public Date getExpDateMin() {
		return expDateMin;
	}

	public void setExpDateMin(Date expDateMin) {
		this.expDateMin = expDateMin;
	}

	public Date getExpDateMax() {
		return expDateMax;
	}

	public void setExpDateMax(Date expDateMax) {
		this.expDateMax = expDateMax;
	}

	public Boolean getCanMixExp() {
		return canMixExp;
	}

	public void setCanMixExp(Boolean canMixExp) {
		this.canMixExp = canMixExp;
	}

	public Date getExpDateUni() {
		return expDateUni;
	}

	public void setExpDateUni(Date expDateUni) {
		this.expDateUni = expDateUni;
	}

	public Double getExtDouble6() {
		return extDouble6;
	}

	public void setExtDouble6(Double extDouble6) {
		this.extDouble6 = extDouble6;
	}

	public Boolean getIsReported() {
		return isReported;
	}

	public void setIsReported(Boolean isReported) {
		this.isReported = isReported;
	}

}
