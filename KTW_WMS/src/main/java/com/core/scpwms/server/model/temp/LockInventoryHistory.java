package com.core.scpwms.server.model.temp;

import java.util.Date;

import com.core.db.server.model.Entity;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Warehouse;

@SuppressWarnings("all")
public class LockInventoryHistory extends Entity {

	private Warehouse wh;
	
	private Owner owner;
	
	private Sku sku;
	
	private String lotInfo;
	
	private String invStatus;
	
	private Double qty;
	
	private Long processStatus;
	
	private Date insertDate;

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

	public Sku getSku() {
		return this.sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public String getInvStatus() {
		return this.invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public Double getQty() {
		return this.qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Long getProcessStatus() {
		return this.processStatus;
	}

	public void setProcessStatus(Long processStatus) {
		this.processStatus = processStatus;
	}

	public Date getInsertDate() {
		return this.insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getLotInfo() {
		return this.lotInfo;
	}

	public void setLotInfo(String lotInfo) {
		this.lotInfo = lotInfo;
	}
	
}
