package com.core.scpwms.server.model.warehouse;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;

import com.core.business.model.TrackingEntityWithVer;
import com.core.scpwms.server.enumerate.EnuBinLockType;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;

/**
 * 库位
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class Bin extends TrackingEntityWithVer {
	/** 仓库 */
	private Warehouse wh;

	/** 物理功能区 */
	private StorageType storageType;

	/** 库位存储属性 */
	private BinProperties properties;

	/** 库位代码 */
	private String binCode;

	/** 库位条码  */
	private String barCode;
	
	/** 温度带 
	 * @see EnuTemperatureDiv */
	private String temperatureDiv;

	/** 库位描述 */
	private String description;

	/** 过道 */
	private Integer aisle;

	/** 开间 */
	private Integer stack;

	/** 层 */
	private Integer depth;

	/** 列 */
	private Integer level;

	/** 拣货路径 */
	private Long sortIndex;

	/** 上架路径 */
	private Long putawayIndex;

	/**
	 * 库位锁状态
	 * @see EnuBinLockType
	 * */
	private String lockStatus;

	/** 库位组 */
	private Set<BinGroup> bins = new HashSet<BinGroup>();

	/** 库位锁定理由 */
	private String lockReason;

	/** 是否失效 */
	private Boolean disabled = Boolean.FALSE;

	/** 库位作业属性 */
	private BinProcessProperties processProperties = new BinProcessProperties();

	/** 库容信息 */
	private BinInvInfo binInvInfo = new BinInvInfo();

	public boolean equals(final Object other) {
		if (!(other instanceof Bin))
			return false;
		Bin castOther = (Bin) other;
		return new EqualsBuilder().append(getId(), castOther.getId())
				.isEquals();
	}

	public boolean isInLock() {
		if (EnuBinLockType.LOCK_T1.equalsIgnoreCase(lockStatus)
				|| EnuBinLockType.LOCK_T3.equalsIgnoreCase(lockStatus)
				|| EnuBinLockType.LOCK_T4.equalsIgnoreCase(lockStatus)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isOutLock() {
		if (EnuBinLockType.LOCK_T2.equalsIgnoreCase(lockStatus)
				|| EnuBinLockType.LOCK_T3.equalsIgnoreCase(lockStatus)
				|| EnuBinLockType.LOCK_T4.equalsIgnoreCase(lockStatus)) {
			return true;
		} else {
			return false;
		}
	}

	public StorageType getStorageType() {
		return this.storageType;
	}

	public void setStorageType(StorageType storageType) {
		this.storageType = storageType;
	}

	public BinProperties getProperties() {
		return this.properties;
	}

	public void setProperties(BinProperties properties) {
		this.properties = properties;
	}

	public String getBinCode() {
		return this.binCode;
	}

	public void setBinCode(String binCode) {
		this.binCode = binCode;
	}

	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAisle() {
		return this.aisle;
	}

	public void setAisle(Integer aisle) {
		this.aisle = aisle;
	}

	public Integer getStack() {
		return this.stack;
	}

	public void setStack(Integer stack) {
		this.stack = stack;
	}

	public Integer getDepth() {
		return this.depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Long getSortIndex() {
		return this.sortIndex;
	}

	public void setSortIndex(Long sortIndex) {
		this.sortIndex = sortIndex;
	}

	public Long getPutawayIndex() {
		return this.putawayIndex;
	}

	public void setPutawayIndex(Long putawayIndex) {
		this.putawayIndex = putawayIndex;
	}

	public String getLockStatus() {
		return this.lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getLockReason() {
		return this.lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public BinProcessProperties getProcessProperties() {
		return this.processProperties;
	}

	public void setProcessProperties(BinProcessProperties processProperties) {
		this.processProperties = processProperties;
	}

	public Set<BinGroup> getBins() {
		return bins;
	}

	public void setBins(Set<BinGroup> bins) {
		this.bins = bins;
	}

	public Warehouse getWh() {
		return this.wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public BinInvInfo getBinInvInfo() {
		return this.binInvInfo;
	}

	public void setBinInvInfo(BinInvInfo binInvInfo) {
		this.binInvInfo = binInvInfo;
	}

	public boolean isPalletBin() {
		if (properties.getPalletCount() != null
				&& properties.getPalletCount() > 0) {
			return true;
		}

		return false;
	}

	public String getTemperatureDiv() {
		return temperatureDiv;
	}

	public void setTemperatureDiv(String temperatureDiv) {
		this.temperatureDiv = temperatureDiv;
	}
	
}
