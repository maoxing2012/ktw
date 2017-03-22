package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuInvAllocate;
import com.core.scpwms.server.enumerate.EnuTurnOverConf;
import com.core.scpwms.server.model.lot.LotInfo;

/**
 * 货主作业设定（收货）
 * 
 * 库内作业制定，包含但是不限于以下内容
 * 盘点周期制定（目前不使用）
 * 缺省批次属性设定(LotInfo)
 * 批次号流水设定（EnuLotNumConf）
 * RF作业设定（现场更换上架库位）
 * RF作业设定（现场更换拣选库位）
 * 缺省库存周转规则
 * 默认承运商
 * 拣选自动补货
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class PlantWarehousingProperties extends TrackingEntity {
	/**
	 * 货主
	 */
	private Plant plant;
	
	/**
	 * 盘点周期（货主别）
	 * 盘点周期优先级 （SKU > ABC > 货主）
	 */
	private Integer phyCycle = 0;
	
	/**
	 * 缺省批次属性设定，货品制定后，以货品批次属性为主
	 * SKU > 货主
	 * @see LotInfo
	 */
	private LotInfo lotInfo;
	
	/**
	 * 批次号流水设定
	 * @see EnuLotSequenceConf
	 */
	private String lotSeqConf;
	
	/**
	 * 缺省库存周转规则
	 * SKU > 货主
	 * @see EnuTurnOverConf
	 */
	private String toc;
	
	/**
	 * 默认承运商
	 */
	private Carrier carrier;
	
	/**
	 * RF作业设定(上架更换库位)
	 */
	private Boolean putawayBinFix = Boolean.TRUE;
	
	/**
	 * RF作业设定(拣选更换库位)
	 */
	private Boolean pickBinFix = Boolean.TRUE;
	
	/**
	 * 拣选自动补货
	 */
	private Boolean supplyAfterPicking = Boolean.FALSE;
	
	/**
	 * 库存分配(清库位优先/拣选次数优先)
	 * @see EnuInvAllocate
	 */
	private String invAllocate = EnuInvAllocate.CLEAR_BIN;

	public Plant getPlant() {
		return this.plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public Integer getPhyCycle() {
		return this.phyCycle;
	}

	public void setPhyCycle(Integer phyCycle) {
		this.phyCycle = phyCycle;
	}

	public LotInfo getLotInfo() {
		return this.lotInfo;
	}

	public void setLotInfo(LotInfo lotInfo) {
		this.lotInfo = lotInfo;
	}

	public String getLotSeqConf() {
		return this.lotSeqConf;
	}

	public void setLotSeqConf(String lotSeqConf) {
		this.lotSeqConf = lotSeqConf;
	}

	public String getToc() {
		return this.toc;
	}

	public void setToc(String toc) {
		this.toc = toc;
	}

	public Carrier getCarrier() {
		return this.carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	public Boolean getPutawayBinFix() {
		return this.putawayBinFix;
	}

	public void setPutawayBinFix(Boolean putawayBinFix) {
		this.putawayBinFix = putawayBinFix;
	}

	public Boolean getPickBinFix() {
		return this.pickBinFix;
	}

	public void setPickBinFix(Boolean pickBinFix) {
		this.pickBinFix = pickBinFix;
	}

	public Boolean getSupplyAfterPicking() {
		return this.supplyAfterPicking;
	}

	public void setSupplyAfterPicking(Boolean supplyAfterPicking) {
		this.supplyAfterPicking = supplyAfterPicking;
	}

	public String getInvAllocate() {
		return this.invAllocate;
	}

	public void setInvAllocate(String invAllocate) {
		this.invAllocate = invAllocate;
	}	
	
}
