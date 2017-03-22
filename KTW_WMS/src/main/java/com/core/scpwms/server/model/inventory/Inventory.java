package com.core.scpwms.server.model.inventory;

import java.util.Date;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.VersionalEntity;
import com.core.scpwms.client.custom.page.picking.ClientPickingInventory;
import com.core.scpwms.server.domain.AllocatePutawayDetail;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;
import com.core.scpwms.server.domain.PutawayRuleInfo;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.ReasonCode;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 库存信息 按库位、 货品批次、 存货日期、 追踪单据号对库存进行汇总
 * 
 * Notice:此表为库存表，不许随便增加修改字段信息，如果在实施过程中确实需要增加 字段，需要提交研发组进行评估
 * 
 * 库内库存的变化都反馈到该表的对应库存的增减
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 * 
 */
@SuppressWarnings("all")
public class Inventory extends VersionalEntity {

	/** 仓库 */
	private Warehouse wh;

	/** 分布场所 */
	private Owner owner;

	/** 库位信息 */
	private Bin bin;

	/** 所属Quant */
	private QuantInventory quantInv;

	/** 基本包装 */
	private PackageDetail basePackage;

	/** 基本单位数量 */
	private Double baseQty = 0D;

	/** 已分配数量(基本单位数量) */
	private Double queuedQty = 0D;

	/** 可用量(不存DB) */
	private Double availableQty = 0D;

	/** 托盘号 */
	private ContainerInv container;

	/**
	 * 库存可用状态 受限于库位的Lock属性
	 * @see EnuInvStatus
	 */
	private String status = EnuInvStatus.AVAILABLE;
	
	/** 入库日期 */
	private Date inboundDate;

	/** Asn单号 */
	private String trackSeq;
	
	/** 容器号 */
	private String containerSeq;
	
	public Warehouse getWh() {
		return this.wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Bin getBin() {
		return this.bin;
	}

	public void setBin(Bin bin) {
		this.bin = bin;
	}

	public QuantInventory getQuantInv() {
		return this.quantInv;
	}

	public void setQuantInv(QuantInventory quantInv) {
		this.quantInv = quantInv;
	}

	public PackageDetail getBasePackage() {
		return this.basePackage;
	}

	public void setBasePackage(PackageDetail basePackage) {
		this.basePackage = basePackage;
	}

	public Double getBaseQty() {
		return this.baseQty;
	}

	public void setBaseQty(Double baseQty) {
		this.baseQty = baseQty;
	}

	public Double getQueuedQty() {
		return this.queuedQty;
	}

	public void setQueuedQty(Double queuedQty) {
		this.queuedQty = queuedQty;
	}

	public ContainerInv getContainer() {
		return this.container;
	}

	public void setContainer(ContainerInv container) {
		this.container = container;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getInboundDate() {
		return this.inboundDate;
	}

	public void setInboundDate(Date inboundDate) {
		this.inboundDate = inboundDate;
	}

	public String getTrackSeq() {
		return this.trackSeq;
	}

	public void setTrackSeq(String trackSeq) {
		this.trackSeq = trackSeq;
	}

	public Double getAvailableQty() {
		return getAvaliableQuantity();
	}

	public void setAvailableQty(Double availableQty) {
		this.availableQty = availableQty;
	}

	public Double getAvailableInputQty() {
		return availableQty;
	}

	public LotInputData getLotData() {
		return quantInv.getQuant().getLotData();
	}

	public String getContainerSeq() {
		return containerSeq;
	}

	public void setContainerSeq(String containerSeq) {
		this.containerSeq = containerSeq;
	}
	
	//获取当前库存的可用数量(基本包装)
	public double getAvaliableQuantity() {
		return DoubleUtil.sub(baseQty, queuedQty);
	}

	public void removeBaseAllocateQty(double quantity) {
		double removeQty = PrecisionUtils.formatByPackage(quantity, basePackage);
		this.baseQty = DoubleUtil.sub(baseQty, removeQty);
		this.queuedQty = DoubleUtil.sub(queuedQty, removeQty);
	}

	public void removeBaseQty(double quantity) {
		double removeQty = PrecisionUtils.formatByPackage(quantity, basePackage);
		this.baseQty = DoubleUtil.sub(baseQty, removeQty);
	}
	
	public void removeAllocateQty(double quantity){
		double removeQty = PrecisionUtils.formatByPackage(quantity, basePackage);
		this.queuedQty = DoubleUtil.sub(queuedQty, removeQty);
	}

	public void addBaseQty(double quantity) {
		double addQuantity = PrecisionUtils.formatByPackage(quantity, basePackage);
		baseQty = DoubleUtil.add(baseQty, addQuantity);
	}

	/**
	 * 库存分配
	 */
	public void allocate(double quantity) {
		double allocateQty = PrecisionUtils.formatByPackage(quantity, basePackage);
		this.queuedQty = DoubleUtil.add(queuedQty, allocateQty);
	}

	/**
	 * 取消库存分配
	 */
	public void unAllocate(double quantity) {
		double unAllocateQty = PrecisionUtils.formatByPackage(quantity, basePackage);
		this.queuedQty = DoubleUtil.sub(queuedQty, unAllocateQty);
	}

	public InventoryInfo getInventoryInfo() {
		InventoryInfo invInfo = new InventoryInfo();
		invInfo.setOwner(owner);
		invInfo.setQuant(quantInv.getQuant());
		invInfo.setBin(bin);
		invInfo.setPackageDetail(basePackage);
		invInfo.setContainerSeq(containerSeq);
		invInfo.setInboundDate(inboundDate);
		invInfo.setTrackSeq(trackSeq);
		invInfo.setInvStatus(status);

		return invInfo;
	}

	public void setInventoryInfo(InventoryInfo invInfo, QuantInventory quantInv, ContainerInv containerInv) {
		this.owner = invInfo.getOwner();
		this.quantInv = quantInv;
		this.bin = invInfo.getBin();
		this.basePackage = invInfo.getPackageDetail();
		this.container = containerInv;
		this.inboundDate = invInfo.getInboundDate();
		this.trackSeq = invInfo.getTrackSeq();
		this.status = invInfo.getInvStatus();
		this.wh = invInfo.getBin().getWh();
		this.containerSeq = invInfo.getContainerSeq();
	}

}
