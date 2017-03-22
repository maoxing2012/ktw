package com.core.scpwms.server.model.outbound;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.core.business.model.domain.ContractInfo;
import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.AllocateDoc;
import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;
import com.core.scpwms.server.enumerate.EnuAsnReportStatus;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryStatus;
import com.core.scpwms.server.enumerate.EnuProPriority;
import com.core.scpwms.server.enumerate.EnuShipMethod;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 对应 ASUS Trip的结构
 * 
 */
@SuppressWarnings("all")
public class OutboundDelivery extends OrderTrackingEntityWithStatus implements AllocateDoc {

    /** 仓库信息 */
    private Warehouse wh;

    /** 公司 */
    private Plant plant;

    /** 货主 */
    private Owner owner;

    /** 出库单号 */
    private String obdNumber;

    /** 直接发运(简单发货) */
    private Boolean directShip = Boolean.FALSE;

    /**
     * 配送类型/发货方式
     * @see EnuShipMethod
     */
    private String shipMethod = EnuShipMethod.SHIP_METHOD_40;
    
    /**
     * 温度帯
     * @see EnuTemperatureDiv
     */
    private Long tempDiv;

    /** 单据类型 */
    private OrderType orderType;

    /** 开单时间 */
    private Date transactionDate;

    /** 发行时间 */
    public Date publishDate;

    /** 备货日期 */
    private Date stockDate;

    /** 预定仓库发货日期 */
    private Date etd;

    /** 实际发运时间 */
    private Date atd;

    /** 预定客户收货日期 */
    private Date eta;
    
    /** 拣货完成时间 */
    private Date pickDate;
    
    /** 复合完成时间 */
    private Date checkDate;
    
    /** 计划发运箱数 */
    private Double caseQty = 0D;

    /** 计划发运数量(EA) */
    private Double planQty = 0D;

    /** 分配数量(EA) */
    private Double allocateQty = 0D;

    /** 拣选数量(EA) */
    private Double pickUpQty = 0D;
    
    /** 打包数量(EA) */
    private Double packedQty = 0D;
	
    /** 发运数量(EA) */
    private Double executeQty = 0D;

    /** 总价 */
    private Double sumPrice = 0d;

    /** 总重 */
    private Double sumWeight = 0d;

    /** 总体积 */
    private Double sumVolume = 0d;
    
    private Double sumMetric = 0D;

    /** 品项数量 */
    private Long skuCount = 0L;
    
    /** 接口接收/手工创建 */
    private Boolean ediData = Boolean.FALSE;

    /** 承运商 */
    private Carrier carrier;

    /**
     * 优先级
     * @see EnuProPriority
     */
    private Long priority = -1L;

    /** 加入的波次 */
    private WaveDoc waveDoc;

    /** 集货库位 */
    private Bin descBin;

    /** 发货月台 */
    private Bin shipBin;

    /** 受注No */
    private String relatedBill1;
    
    /** 代表伝票No */
    private String relatedBill2;
    
    /** 伝票No用逗号拼接 */
    private String relatedBill3;

    /** 备注 */
    private String description;
    
    /** 仓库发运备注 */
    private String shipDesc;
    
    /**收货人*/
    private BizOrg customer;
    
    /** 送货客户信息 */
    private ContractInfo customerInfo;
    
    //　NS:（ＷＭＳ用）専用伝票区分
    private String extString1;
    //　NS:40伝票情報
    private String extString2;
    //　NS:68担当営業所コード
    private String extString3;
    // NS:34（原）専用伝票区分
    private String extString4;
    // NS:35伝票発行区分
    private String extString5;
    // NS:6納品先コード
    private String extString6;
    // NS:44得意先コード
    private String extString7;
    private String extString8;
    private String extString9;
    private String extString10;
    
    // 用来控制送り状的回传
    private Boolean isReported;
    
    // 用来控制订单的回传
    private Long reportStatus = EnuAsnReportStatus.STATUS800;
    
    /** SO Detail信息 */
    private Set<OutboundDeliveryDetail> details = new HashSet<OutboundDeliveryDetail>();
    
    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public Plant getPlant() {
        return this.plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getObdNumber() {
        return this.obdNumber;
    }

    public void setObdNumber(String obdNumber) {
        this.obdNumber = obdNumber;
    }

    public Boolean getDirectShip() {
        return this.directShip;
    }

    public void setDirectShip(Boolean directShip) {
        this.directShip = directShip;
    }

    public String getShipMethod() {
        return this.shipMethod;
    }

    public void setShipMethod(String shipMethod) {
        this.shipMethod = shipMethod;
    }

    public OrderType getOrderType() {
        return this.orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getEtd() {
        return this.etd;
    }

    public void setEtd(Date etd) {
        this.etd = etd;
    }
    
    public Date getAtd() {
        return this.atd;
    }

    public void setAtd(Date atd) {
        this.atd = atd;
    }

    public Date getEta() {
        return this.eta;
    }

    public void setEta(Date eta) {
        this.eta = eta;
    }

    public Date getPickDate() {
		return this.pickDate;
	}

	public void setPickDate(Date pickDate) {
		this.pickDate = pickDate;
	}

	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
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

    public Double getPickUpQty() {
        return this.pickUpQty;
    }

    public void setPickUpQty(Double pickUpQty) {
        this.pickUpQty = pickUpQty;
    }

    public Double getExecuteQty() {
        return this.executeQty;
    }

    public void setExecuteQty(Double executeQty) {
        this.executeQty = executeQty;
    }

    public Boolean getEdiData() {
        return this.ediData;
    }

    public void setEdiData(Boolean ediData) {
        this.ediData = ediData;
    }

    public Carrier getCarrier() {
        return this.carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public Long getPriority() {
        return this.priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public WaveDoc getWaveDoc() {
        return this.waveDoc;
    }

    public void setWaveDoc(WaveDoc waveDoc) {
        this.waveDoc = waveDoc;
    }
    
    public Bin getDescBin() {
        return this.descBin;
    }

    public void setDescBin(Bin descBin) {
        this.descBin = descBin;
    }

    public Bin getShipBin() {
        return this.shipBin;
    }

    public void setShipBin(Bin shipBin) {
        this.shipBin = shipBin;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<OutboundDeliveryDetail> getDetails() {
        return this.details;
    }

    public void setDetails(Set<OutboundDeliveryDetail> details) {
        this.details = details;
    }

    public Double getSumPrice() {
        return this.sumPrice;
    }

    public void setSumPrice(Double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public Double getSumWeight() {
        return this.sumWeight;
    }

    public void setSumWeight(Double sumWeight) {
        this.sumWeight = sumWeight;
    }

    public Double getSumVolume() {
        return this.sumVolume;
    }

    public void setSumVolume(Double sumVolume) {
        this.sumVolume = sumVolume;
    }

    public Long getSkuCount() {
        return this.skuCount;
    }

    public void setSkuCount(Long skuCount) {
        this.skuCount = skuCount;
    }

    public Date getStockDate() {
        return this.stockDate;
    }

    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }
    
    public Double getPackedQty() {
		return this.packedQty;
	}

	public void setPackedQty(Double packedQty) {
		this.packedQty = packedQty;
	}

	public String getShipDesc() {
		return this.shipDesc;
	}

	public void setShipDesc(String shipDesc) {
		this.shipDesc = shipDesc;
	}
	
	public ContractInfo getCustomerInfo() {
		return this.customerInfo;
	}

	public void setCustomerInfo(ContractInfo customerInfo) {
		this.customerInfo = customerInfo;
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

	public BizOrg getCustomer() {
		return customer;
	}

	public void setCustomer(BizOrg customer) {
		this.customer = customer;
	}

	public Double getCaseQty() {
		return caseQty;
	}

	public void setCaseQty(Double caseQty) {
		this.caseQty = caseQty;
	}

	public Double getSumMetric() {
		return sumMetric;
	}

	public void setSumMetric(Double sumMetric) {
		this.sumMetric = sumMetric;
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
	
	public Boolean getIsReported() {
		return isReported;
	}

	public void setIsReported(Boolean isReported) {
		this.isReported = isReported;
	}

	public Long getTempDiv() {
		return tempDiv;
	}

	public void setTempDiv(Long tempDiv) {
		this.tempDiv = tempDiv;
	}
	
	public Long getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(Long reportStatus) {
		this.reportStatus = reportStatus;
	}

	@Override
    public String getStatusEnum() {
        return EnuOutboundDeliveryStatus.class.getSimpleName();
    }

    public void addPlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.add(planQty, qty);
        }
    }

    public void addAllocateQty(double qty) {
        if (qty > 0) {
            allocateQty = DoubleUtil.add(allocateQty, qty);
        }
    }

    public void addPickUpQty(double qty) {
        if (qty > 0) {
            pickUpQty = DoubleUtil.add(pickUpQty, qty);
        }
    }

    public void addExecuteQty(double qty) {
        if (qty > 0) {
            executeQty = DoubleUtil.add(executeQty, qty);
        }
    }
    
    public void addPackedQty(double qty) {
        if (qty > 0) {
            packedQty = DoubleUtil.add(packedQty, qty);
        }
    }

    public void removePlanQty(double qty) {
        if (qty > 0) {
            planQty = DoubleUtil.sub(planQty, qty);
        }
    }

    public void removeAllocateQty(double qty) {
        if (qty > 0) {
            allocateQty = DoubleUtil.sub(allocateQty, qty);
            if( waveDoc != null ){
            	waveDoc.removeAllocatedQty(qty);
            }
        }
    }

    public void removePickUpQty(double qty) {
        if (qty > 0) {
            pickUpQty = DoubleUtil.sub(pickUpQty, qty);
        }
    }

    public void removeExecuteQty(double qty) {
        if (qty > 0) {
            executeQty = DoubleUtil.sub(executeQty, qty);
        }
    }
    
    public void removePackedQty(double qty) {
        if (qty > 0) {
            packedQty = DoubleUtil.sub(packedQty, qty);
        }
    }

    public double getUnAllocateQty() {
        return DoubleUtil.sub(planQty, allocateQty);
    }

    public double getUnPickupQty() {
        return DoubleUtil.sub(planQty, pickUpQty);
    }

    public double getAllocatedUnPickupQty() {
        return DoubleUtil.sub(allocateQty, pickUpQty);
    }

    public double getUnExecuteQty() {
        return DoubleUtil.sub(planQty, executeQty);
    }
    
    public double getPickedUnPackedQty(){
    	return DoubleUtil.sub(pickUpQty, packedQty);
    }
    
    public double getPickedUnExecuteQty() {
        return DoubleUtil.sub(pickUpQty, executeQty);
    }

    public void clear() {
        // 清空波次，状态信息
        setId(null);
        pickDate = null;
	    checkDate = null;
		planQty = 0D;
		allocateQty = 0D;
		pickUpQty = 0D;
	    packedQty = 0D;
		executeQty = 0D;
		sumPrice = 0d;
		sumWeight = 0d;
		sumVolume = 0d;
		skuCount = 0L;
        waveDoc = null;
        descBin = null;
        shipBin = null;
        details = new HashSet<OutboundDeliveryDetail>();
        changeStatus(null);
    }
}
