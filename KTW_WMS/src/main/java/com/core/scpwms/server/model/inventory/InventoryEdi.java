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
 * 面向ERP接口的库存（去掉库位，容器，包装）
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2015年1月27日<br/>
 */
@SuppressWarnings("all")
public class InventoryEdi extends VersionalEntity {

	/**
	 * 仓库
	 */
	private Warehouse wh;

	/**
	 * 分布场所
	 */
	private Owner owner;

	/**
	 * 所属Quant
	 */
	private QuantInventory quantInv;

	/**
	 * 基本包装
	 */
	private PackageDetail basePackage;

	/**
	 * 库存量(EA)
	 */
	private Double baseQty = 0D;

	/**
	 * 预分配量(EA)
	 */
	private Double preAllocateQty = 0D;

	/**
	 * 已分配量(EA)
	 */
	private Double allocateQty = 0D;

	/**
	 * 库存状态
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

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
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

	public Double getPreAllocateQty() {
		return this.preAllocateQty;
	}

	public void setPreAllocateQty(Double preAllocateQty) {
		this.preAllocateQty = preAllocateQty;
	}

	public Double getAllocateQty() {
		return this.allocateQty;
	}

	public void setAllocateQty(Double allocateQty) {
		this.allocateQty = allocateQty;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
