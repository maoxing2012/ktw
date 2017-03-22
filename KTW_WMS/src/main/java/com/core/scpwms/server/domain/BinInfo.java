package com.core.scpwms.server.domain;

import java.util.Date;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.util.PrecisionUtils;

@SuppressWarnings("all")
public class BinInfo extends DomainModel {

	/**
	 * 最后盘点日期
	 */
	private Date lastCountDate;

	/**
	 * 当前存放的货品ID
	 * */
	private Long lastSku;

	/**
	 * 当前存放的批次属性ID
	 * */
	private Long lastLotInfo;

	/**
	 * 当前存放的最后供应商
	 * */
	private Long lastSupId;
	
	/**
	 * 当前存放的货主
	 * */
	private Long lastOwnerId;

	/**
	 * 最后拣选的货品ID
	 * */
	private Long lastPickSku;

	/**
	 * 最后拣选的批次属性ID
	 * */
	private Long lastPickLotInfo;

	// 当前库容 数量、重量、体积 、标准度量
	public Double currentQty = 0D;
	public Double currentWeight = 0D;
	public Double currentVolumn = 0D;
	public Double currentMetric = 0D;
	public Double currentPallet = 0D;

	// 待上架库容 数量、重量、体积 、标准度量
	public Double queuedQty = 0D;
	public Double queuedWeight = 0D;
	public Double queuedVolumn = 0D;
	public Double queuedMetric = 0D;
	public Double queuedPallet = 0D;

	// 库满度 数量、托盘满度、重量、体积 、标准度量
	public Double minFullRate = 0D;
	public Double fullRateQty = 0D;
	public Double fullRatePallet = 0D;
	public Double fullRateWeight = 0D;
	public Double fullRateVolumn = 0D;
	public Double fullRateMetric = 0D;

	public Date getLastCountDate() {
		return this.lastCountDate;
	}

	public void setLastCountDate(Date lastCountDate) {
		this.lastCountDate = lastCountDate;
	}

	public Long getLastSku() {
		return this.lastSku;
	}

	public void setLastSku(Long lastSku) {
		this.lastSku = lastSku;
	}

	public Long getLastLotInfo() {
		return this.lastLotInfo;
	}

	public void setLastLotInfo(Long lastLotInfo) {
		this.lastLotInfo = lastLotInfo;
	}

	public Long getLastSupId() {
		return this.lastSupId;
	}

	public void setLastSupId(Long lastSupId) {
		this.lastSupId = lastSupId;
	}

	public Long getLastPickSku() {
		return this.lastPickSku;
	}

	public void setLastPickSku(Long lastPickSku) {
		this.lastPickSku = lastPickSku;
	}

	public Long getLastPickLotInfo() {
		return this.lastPickLotInfo;
	}

	public void setLastPickLotInfo(Long lastPickLotInfo) {
		this.lastPickLotInfo = lastPickLotInfo;
	}

	public Double getCurrentQty() {
		return this.currentQty;
	}

	public void setCurrentQty(Double currentQty) {
		this.currentQty = currentQty;
	}

	public Double getCurrentWeight() {
		return this.currentWeight;
	}

	public void setCurrentWeight(Double currentWeight) {
		this.currentWeight = currentWeight;
	}

	public Double getCurrentVolumn() {
		return this.currentVolumn;
	}

	public void setCurrentVolumn(Double currentVolumn) {
		this.currentVolumn = currentVolumn;
	}

	public Double getCurrentMetric() {
		return this.currentMetric;
	}

	public void setCurrentMetric(Double currentMetric) {
		this.currentMetric = currentMetric;
	}

	public Double getQueuedQty() {
		return this.queuedQty;
	}

	public void setQueuedQty(Double queuedQty) {
		this.queuedQty = queuedQty;
	}

	public Double getQueuedWeight() {
		return this.queuedWeight;
	}

	public void setQueuedWeight(Double queuedWeight) {
		this.queuedWeight = queuedWeight;
	}

	public Double getQueuedVolumn() {
		return this.queuedVolumn;
	}

	public void setQueuedVolumn(Double queuedVolumn) {
		this.queuedVolumn = queuedVolumn;
	}

	public Double getQueuedMetric() {
		return this.queuedMetric;
	}

	public void setQueuedMetric(Double queuedMetric) {
		this.queuedMetric = queuedMetric;
	}

	public Double getMinFullRate() {
		return this.minFullRate;
	}

	public void setMinFullRate(Double minFullRate) {
		this.minFullRate = minFullRate;
	}

	public Double getCurrentPallet() {
		return this.currentPallet;
	}

	public void setCurrentPallet(Double currentPallet) {
		this.currentPallet = currentPallet;
	}

	public Double getQueuedPallet() {
		return this.queuedPallet;
	}

	public void setQueuedPallet(Double queuedPallet) {
		this.queuedPallet = queuedPallet;
	}
	
	public Long getLastOwnerId() {
		return this.lastOwnerId;
	}

	public void setLastOwnerId(Long lastOwnerId) {
		this.lastOwnerId = lastOwnerId;
	}

	public Double getFullRatePallet() {
		if (fullRatePallet == null)
			return 0D;

		return this.fullRatePallet;
	}

	public Double getFullRateQty() {
		if (fullRateQty == null)
			return 0D;

		return this.fullRateQty;
	}

	public Double getFullRateWeight() {
		if (fullRateWeight == null)
			return 0D;

		return this.fullRateWeight;
	}

	public Double getFullRateVolumn() {
		if (fullRateVolumn == null)
			return 0D;

		return this.fullRateVolumn;
	}

	public Double getFullRateMetric() {
		if (fullRateMetric == null)
			return 0D;

		return this.fullRateMetric;
	}
	
	public Double getCurrentQueuedQty(){
		return currentQty.doubleValue() + queuedQty.doubleValue();
	}
	
	public Double getCurrentQueuedPallet(){
		return currentPallet.doubleValue() + queuedPallet.doubleValue();
	}
	
	public void addQueuedPallet(Double palletQty) {
		if (palletQty > 0) {
			this.queuedPallet += palletQty;
		}
	}

	public void removeQueuedPallet(Double palletQty) {
		if (palletQty > 0) {
			this.queuedPallet -= palletQty;
		}
	}

	public void addCurrentPallet(Double palletQty) {
		if (palletQty > 0) {
			this.currentPallet += palletQty;
		}
	}

	public void removeCurrentPallet(Double palletQty) {
		if (palletQty > 0) {
			this.currentPallet -= palletQty;
		}
	}

	public void setFullRateQty(Double fullRateQty) {

		if (minFullRate <= 0) {
			minFullRate = fullRateQty;
		} else if (minFullRate >= fullRateQty) {
			minFullRate = fullRateQty;
		}

		this.fullRateQty = fullRateQty;
	}

	public void setFullRateWeight(Double fullRateWeight) {
		if (minFullRate <= 0) {
			minFullRate = fullRateWeight;
		} else if (minFullRate >= fullRateWeight) {
			minFullRate = fullRateWeight;
		}
		this.fullRateWeight = fullRateWeight;
	}

	public void setFullRateVolumn(Double fullRateVolumn) {

		if (minFullRate <= 0) {
			minFullRate = fullRateVolumn;
		} else if (minFullRate >= fullRateVolumn) {
			minFullRate = fullRateVolumn;
		}

		this.fullRateVolumn = fullRateVolumn;
	}

	public void setFullRateMetric(Double fullRateMetric) {

		if (minFullRate <= 0) {
			minFullRate = fullRateMetric;
		} else if (minFullRate >= fullRateMetric) {
			minFullRate = fullRateMetric;
		}

		this.fullRateMetric = fullRateMetric;
	}

	public void setFullRatePallet(Double fullRatePallet) {
		if (minFullRate <= 0) {
			minFullRate = fullRatePallet;
		} else if (minFullRate >= fullRatePallet) {
			minFullRate = fullRatePallet;
		}

		this.fullRatePallet = fullRatePallet;
	}
	
	public void resetBinInfo(){
		// 当前库容 数量、重量、体积 、标准度量
		this.currentQty = 0D;
		this.currentWeight = 0D;
		this.currentVolumn = 0D;
		this.currentMetric = 0D;
		this.currentPallet = 0D;

		// 待上架库容 数量、重量、体积 、标准度量
		this.queuedQty = 0D;
		this.queuedWeight = 0D;
		this.queuedVolumn = 0D;
		this.queuedMetric = 0D;
		this.queuedPallet = 0D;

		// 库满度 数量、托盘满度、重量、体积 、标准度量
		this.minFullRate = 0D;
		this.fullRateQty = 0D;
		this.fullRatePallet = 0D;
		this.fullRateWeight = 0D;
		this.fullRateVolumn = 0D;
		this.fullRateMetric = 0D;
	}
}
