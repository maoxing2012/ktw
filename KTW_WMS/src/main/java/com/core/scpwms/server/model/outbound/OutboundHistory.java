package com.core.scpwms.server.model.outbound;

import java.util.Date;

import com.core.business.model.TrackingEntity;
import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 发货履历
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class OutboundHistory extends TrackingEntity {

	/** 仓库 */
	private Warehouse wh;

	/** 备货单 */
	private OutboundDelivery obd;

	/** 备货单明细 */
	private OutboundDeliveryDetail detail;

	/** 发货数量(EA) */
	private Double executeQty;

	/** 作业人员 */
	private Labor labor;

	/** 发货时间 */
	private Date shipDate;

	/** 对应的库存信息 */
	private InventoryInfo invInfo;

	public Warehouse getWh() {
		return this.wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public OutboundDelivery getObd() {
		return this.obd;
	}

	public void setObd(OutboundDelivery obd) {
		this.obd = obd;
	}

	public OutboundDeliveryDetail getDetail() {
		return this.detail;
	}

	public void setDetail(OutboundDeliveryDetail detail) {
		this.detail = detail;
	}

	public Double getExecuteQty() {
		return this.executeQty;
	}

	public void setExecuteQty(Double executeQty) {
		this.executeQty = executeQty;
	}

	public Labor getLabor() {
		return this.labor;
	}

	public void setLabor(Labor labor) {
		this.labor = labor;
	}

	public Date getShipDate() {
		return this.shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public InventoryInfo getInvInfo() {
		return this.invInfo;
	}

	public void setInvInfo(InventoryInfo invInfo) {
		this.invInfo = invInfo;
	}

	public void addExecuteQty(double qty) {
		if (qty > 0) {
			double baseQty = PrecisionUtils.formatByBasePackage(qty,
					invInfo.getPackageDetail());
			executeQty = DoubleUtil.add(executeQty, baseQty);
		}
	}

	public void removeExecuteQty(double qty) {
		if (qty > 0) {
			double baseQty = PrecisionUtils.formatByBasePackage(qty,
					invInfo.getPackageDetail());
			executeQty = DoubleUtil.sub(executeQty, baseQty);
			detail.removeExecuteQty(baseQty);
		}
	}

}
