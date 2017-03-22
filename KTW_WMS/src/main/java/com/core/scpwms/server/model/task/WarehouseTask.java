package com.core.scpwms.server.model.task;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.AllocateDocDetail;
import com.core.scpwms.server.domain.BaseWarehouseOrder;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuInvProcessType;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.enumerate.EnuWtProcessType;
import com.core.scpwms.server.model.change.LotChangeDocDetail;
import com.core.scpwms.server.model.change.OwnerChangeDocDetail;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.model.putaway.PutawayDocDetail;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * WT
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 * 
 */
@SuppressWarnings("all")
public class WarehouseTask extends BaseWarehouseOrder {

	/** 仓库信息 */
	private Warehouse wh;

	/** WO */
	private WarehouseOrder wo;

	/** 作业号 */
	private String taskSequence;

	/**
	 * 上架作业单明细
	 */
	private PutawayDocDetail putawayDocDetail;

	/**
	 * 货主转换单明细
	 */
	private OwnerChangeDocDetail ownerChangeDocDetail;

	/**
	 * OutboundDeliveryDetail 发货单明细
	 */
	private OutboundDeliveryDetail obdDetail;
	
	/**
	 * 批次调整单明细
	 */
	private LotChangeDocDetail lotChangeDocDetail;

	/**
	 * WT作业类型 是 EnuInvProcessType的细分
	 * 
	 * @see EnuWtProcessType
	 */
	private String processType;

	/** 作业人员 */
	private Labor labor;

	/** To Bin */
	private Bin descBin;

	/** To 容器编号 */
	private String descContainerSeq;

	/** To 托盘编号 */
	private String descPalletSeq;

	/** 预期数量（基本单位） */
	private Double planQty = 0D;

	/** 预期包装数量（包装单位） */
	private Double planPackQty = 0D;

	/** 实际数量（包装单位） */
	private Double executePackQty = 0D;

	/** 实际数量（基本单位） */
	private Double executeQty = 0D;

	/** 待执行数（不存DB） */
	private Double unExecutePackQty;

	/** 作业开始时间 */
	private Date operateStartTime;
	
	/** 作业结束时间 */
	private Date operateTime;

	/** 相关单号1(ASN单号，质检单号，加工单号,移位单号等) */
	private String relatedBill1;

	/** 相关单号2 */
	private String relatedBill2;

	/** 相关单号3 */
	private String relatedBill3;

	/** 源库存信息 */
	private InventoryInfo invInfo;

	/** 这里的库存ID仅供查询 */
	private Long invId;
	
	/** 备注 */
	private String description;

	/** 作业日志 */
	private Set<WtHistory> histories = new HashSet<WtHistory>();
	
	/** 是否整托拣货 0：非整托 2：整托混SO单 3：整托不混SO单 */
	private Integer isFullPallet = 0;
	
	/* 已经固定了个口的数量 */
	private Double singledQty = 0D;;

	public WarehouseTask() {
	}

	public WarehouseTask(Long invId) {
		this.invId = invId;
	}

	public Warehouse getWh() {
		return this.wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public WarehouseOrder getWo() {
		return this.wo;
	}

	public void setWo(WarehouseOrder wo) {
		this.wo = wo;
	}

	public PutawayDocDetail getPutawayDocDetail() {
		return this.putawayDocDetail;
	}

	public void setPutawayDocDetail(PutawayDocDetail putawayDocDetail) {
		this.putawayDocDetail = putawayDocDetail;
	}

	public String getProcessType() {
		return this.processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
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

	public Double getPlanQty() {
		return this.planQty;
	}

	public void setPlanQty(Double planQty) {
		this.planQty = planQty;
	}

	public Double getPlanPackQty() {
		return this.planPackQty;
	}

	public void setPlanPackQty(Double planPackQty) {
		this.planPackQty = planPackQty;
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

	public Double getUnExecutePackQty() {
		return DoubleUtil.sub(planQty, executeQty);
	}

	public Double getUnExecutePackInputQty() {
		return unExecutePackQty;
	}

	public void setUnExecutePackQty(Double unExecutePackQty) {
		this.unExecutePackQty = unExecutePackQty;
	}

	private void setExecuteQty(double executeQty) {
		this.executeQty = executeQty;
	}

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Set<WtHistory> getHistories() {
		return this.histories;
	}

	public void setHistories(Set<WtHistory> histories) {
		this.histories = histories;
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

	public String getTaskSequence() {
		return this.taskSequence;
	}

	public void setTaskSequence(String taskSequence) {
		this.taskSequence = taskSequence;
	}

	public InventoryInfo getInvInfo() {
		return this.invInfo;
	}

	public void setInvInfo(InventoryInfo invInfo) {
		this.invInfo = invInfo;
	}

	public OwnerChangeDocDetail getOwnerChangeDocDetail() {
		return this.ownerChangeDocDetail;
	}

	public void setOwnerChangeDocDetail(
			OwnerChangeDocDetail ownerChangeDocDetail) {
		this.ownerChangeDocDetail = ownerChangeDocDetail;
	}

	public OutboundDeliveryDetail getObdDetail() {
		return this.obdDetail;
	}

	public void setObdDetail(OutboundDeliveryDetail obdDetail) {
		this.obdDetail = obdDetail;
	}

	public Long getInvId() {
		return this.invId;
	}

	public void setInvId(Long invId) {
		this.invId = invId;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LotChangeDocDetail getLotChangeDocDetail() {
		return this.lotChangeDocDetail;
	}

	public void setLotChangeDocDetail(LotChangeDocDetail lotChangeDocDetail) {
		this.lotChangeDocDetail = lotChangeDocDetail;
	}

	public Integer getIsFullPallet() {
		return this.isFullPallet;
	}

	public void setIsFullPallet(Integer isFullPallet) {
		this.isFullPallet = isFullPallet;
	}
	
	public Date getOperateStartTime() {
		return this.operateStartTime;
	}

	public void setOperateStartTime(Date operateStartTime) {
		this.operateStartTime = operateStartTime;
	}

	public boolean equals(final Object other) {
		if (!(other instanceof WarehouseTask)) {
			return false;
		}

		WarehouseTask castOther = (WarehouseTask) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}
	
	public Double getSingledQty() {
		return singledQty;
	}

	public void setSingledQty(Double singledQty) {
		this.singledQty = singledQty;
	}

	@Override
	public String getStatusEnum() {
		return EnuWarehouseOrderStatus.class.getSimpleName();
	}

	public void addSingledQty(double qty) {
		if (qty > 0) {
			singledQty = DoubleUtil.add(singledQty, qty);
		}
	}

	public void removeSingledQty(double qty) {
		if (qty > 0) {
			singledQty = DoubleUtil.sub(singledQty, qty);
		}
	}
	
	public Double getUnSingledQty(){
		return DoubleUtil.sub(planQty, singledQty);
	}
	
	public void addPlanQty(double qty) {
		if (qty > 0) {
			planQty = DoubleUtil.add(planQty, qty);
			planPackQty = PrecisionUtils.getPackQty(planQty,
					invInfo.getPackageDetail());
		}
	}

	public void removePlanQty(double qty) {
		if (qty > 0) {
			planQty = DoubleUtil.sub(planQty, qty);
			planPackQty = PrecisionUtils.getPackQty(planQty,
					invInfo.getPackageDetail());
		}
	}

	public void execute(double qty) {
		if (qty > 0) {
			executeQty = DoubleUtil.add(executeQty, qty);
			executePackQty = PrecisionUtils.getPackQty(executeQty,
					invInfo.getPackageDetail());
			// 可能存在无WO的WT
			if (wo != null) {
				wo.execute(qty);
			}
		}
	}

	public void unExecute(double qty) {
		if (qty > 0) {
			executeQty = DoubleUtil.sub(executeQty, qty);
			executePackQty = PrecisionUtils.getPackQty(executeQty,
					invInfo.getPackageDetail());
			// 可能存在无WO的WT
			if (wo != null) {
				wo.cancelExecute(qty);
			}
		}
	}

	public double getUnExecuteQty() {
		return DoubleUtil.sub(planQty, executeQty);
	}

	public String getDocSequence() {
		if (putawayDocDetail != null)
			return putawayDocDetail.getPutawayDoc().getDocSequence();

		else if (ownerChangeDocDetail != null) {
			return ownerChangeDocDetail.getOwnerChangeDoc().getDocSequence();
		}

		else if (obdDetail != null) {
			return obdDetail.getObd().getObdNumber();
		}
		
		else if (lotChangeDocDetail != null) {
			return lotChangeDocDetail.getLotChangeDoc().getDocSequence();
		}

		// TODO
		return null;
	}
	
	public String getInvProcessType(){
		if (putawayDocDetail != null)
			return EnuInvProcessType.M5000;// 上架
		
		else if (obdDetail != null) {
			return EnuInvProcessType.M6000;// 拣货
		}

		else if (ownerChangeDocDetail != null) {
			return EnuInvProcessType.M9999;// 其他
		}
		
		else if (lotChangeDocDetail != null) {
			return EnuInvProcessType.M9999;// 其他
		}

		return EnuInvProcessType.M1000;// 移位
	}

	public OrderType getDocOrderType() {
		if (putawayDocDetail != null)
			return putawayDocDetail.getPutawayDoc().getOrderType();

		else if (ownerChangeDocDetail != null) {
			return ownerChangeDocDetail.getOwnerChangeDoc().getOrderType();
		}

		else if (obdDetail != null) {
			return obdDetail.getObd().getOrderType();
		}
		
		else if (lotChangeDocDetail != null) {
			return lotChangeDocDetail.getLotChangeDoc().getOrderType();
		}
		// TODO
		return null;
	}

	public void setDetail(AllocateDocDetail allocateDocDetail) {
		if (allocateDocDetail instanceof OwnerChangeDocDetail) {
			OwnerChangeDocDetail ownerChangeDocDetail = (OwnerChangeDocDetail) allocateDocDetail;
			setOwnerChangeDocDetail(ownerChangeDocDetail);
		}

		else if (allocateDocDetail instanceof OutboundDeliveryDetail) {
			OutboundDeliveryDetail obdDetail = (OutboundDeliveryDetail) allocateDocDetail;
			setObdDetail(obdDetail);
		}
		
		else if (allocateDocDetail instanceof LotChangeDocDetail) {
			LotChangeDocDetail lotChangeDetail = (LotChangeDocDetail) allocateDocDetail;
			setLotChangeDocDetail(lotChangeDetail);
		}

		// TODO
	}
}
