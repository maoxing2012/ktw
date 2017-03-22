package com.core.scpwms.server.model.inbound;

import java.util.Date;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.domain.AllocatePutawayDetail;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 收货履历（针对PO号、PO行、ASN、ASN行）
 * 针对ASN每次收货都有对应收货明细记录
 * 取消收货时需要清空对应收货单
 * 
 * 可以记录入库库存交易明细
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class InboundHistory extends TrackingEntity{

    /** 仓库 */
    private Warehouse wh;

    /** 所属ASN */
    private Asn asn;

    /** 所属ASN Detail */
    private AsnDetail detail;

    /** 实收数量(EA) */
    private Double executeQty;

    /** 作业人员 Labor */
    private Labor labor;

    /** 收货时间 */
    private Date receiveDate;

    /** 对应的库存信息 */
    private InventoryInfo invInfo;
    
	public Warehouse getWh() {
		return wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public Asn getAsn() {
		return asn;
	}

	public void setAsn(Asn asn) {
		this.asn = asn;
	}

	public AsnDetail getDetail() {
		return detail;
	}

	public void setDetail(AsnDetail detail) {
		this.detail = detail;
	}

	public Double getExecuteQty() {
		return executeQty;
	}

	public void setExecuteQty(Double executeQty) {
		this.executeQty = executeQty;
	}

	public Labor getLabor() {
		return labor;
	}

	public void setLabor(Labor labor) {
		this.labor = labor;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public InventoryInfo getInvInfo() {
		return invInfo;
	}

	public void setInvInfo(InventoryInfo invInfo) {
		this.invInfo = invInfo;
	}

}
