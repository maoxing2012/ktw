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
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 
 * <p>
 * TODO 这一层待定
 * 
 * 面向作业人员的库存（去掉了入库日期，入库单号，分布场所）
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2015年1月27日<br/>
 */
@SuppressWarnings("all")
public class InventoryWork extends VersionalEntity {

	/**
	 * 仓库
	 */
	private Warehouse wh;

	/**
	 * 库位信息
	 */
	private Bin bin;

	/**
	 * 所属Quant
	 */
	private QuantInventory quantInv;

	/**
	 * 基本包装
	 */
	private PackageDetail basePackage;

	/**
	 * 包装单位
	 */
	private PackageDetail currentPack;

	/**
	 * 基本单位数量
	 */
	private Double baseQty = 0D;

	/**
	 * 已分配数量(基本单位数量)
	 */
	private Double queuedQty = 0D;

	/**
	 * 可用量(不存DB)
	 */
	private Double availableQty = 0D;

	/**
	 * 库存数量(包装数量)
	 */
	private Double packQty = 0D;

	/**
	 * 库存可用状态 受限于库位的Lock属性
	 * 
	 * @see EnuInvStatus
	 */
	private String status = EnuInvStatus.AVAILABLE;

	public Warehouse getWh() {
		return this.wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
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

	public PackageDetail getCurrentPack() {
		return this.currentPack;
	}

	public void setCurrentPack(PackageDetail currentPack) {
		this.currentPack = currentPack;
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

	public Double getAvailableQty() {
		return this.availableQty;
	}

	public void setAvailableQty(Double availableQty) {
		this.availableQty = availableQty;
	}

	public Double getPackQty() {
		return this.packQty;
	}

	public void setPackQty(Double packQty) {
		this.packQty = packQty;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
