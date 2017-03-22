/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: HistoryTrackingEntity.java
 */

package com.core.scpwms.server.domain;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuKpiFreeStatus;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * <p>
 * 履历的共同抽象类
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/07<br/>
 */
@SuppressWarnings("all")
public abstract class HistoryTrackingEntity extends TrackingEntity {
	/** 仓管员 */
	private String keepers;

	/** 作业人员 */
	private String workers;

	/**
	 * 是否计费Flag
	 * 
	 * @see EnuKpiFreeStatus
	 */
	private String freeStatus;

	public abstract Warehouse getWh();

	public abstract OrderType getOrderType();

	public abstract InventoryInfo getInvInfo();

	public abstract Double getExecuteQty();

	public abstract Double getExecutePackQty();

	public abstract String getRelatedBill1();

	public abstract String getRelatedBill2();

	public abstract String getRelatedBill3();

	public abstract Double getRealTotalFee();

	public String getKeepers() {
		return this.keepers;
	}

	public void setKeepers(String keepers) {
		this.keepers = keepers;
	}

	public String getWorkers() {
		return this.workers;
	}

	public void setWorkers(String workers) {
		this.workers = workers;
	}

	public String getFreeStatus() {
		return this.freeStatus;
	}

	public void setFreeStatus(String freeStatus) {
		this.freeStatus = freeStatus;
	}

	public void addKeeperName(String keeperName) {
		if (keepers != null && !keepers.isEmpty()) {
			if (!keepers.contains(keeperName)) {
				keepers += "," + keeperName;
			}
		} else {
			keepers = keeperName;
		}
	}

	public void removeKeeperName(String keeperName) {
		if (keepers != null && !keepers.isEmpty()) {
			if (keeperName.equals(keepers)) {
				keepers = "";
			} else if (keepers.startsWith(keeperName)) {
				keepers = keepers.replace(keeperName + ",", "");
			} else {
				keepers = keepers.replace("," + keeperName, "");
			}
		}
	}

	public void addWorkerName(String workerName) {
		if (workers != null && !workers.isEmpty()) {
			if (!workers.contains(workerName)) {
				workers += "," + workerName;
			}
		} else {
			workers = workerName;
		}
	}

	public void removeWorkerName(String workerName) {
		if (workers != null && !workers.isEmpty()) {
			if (workerName.equals(workers)) {
				workers = "";
			} else if (workers.startsWith(workerName)) {
				workers = workers.replace(workerName + ",", "");
			} else {
				workers = workers.replace("," + workerName, "");
			}
		}
	}
}
