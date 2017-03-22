package com.core.scpwms.server.model.apply;

import com.core.db.server.model.Entity;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuApplyDetailStatus;

/**
 * 
 * <p>
 * 报废申请明细
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/22<br/>
 */
@SuppressWarnings("all")
public class ScrapApplyDetail extends Entity {

	/** ScrapApply */
	private ScrapApply scrapApply;

	/** @see EnuApplyDetailStatus */
	private Long status;

	/** 申请数量(EA) */
	private Double qty = 0D;

	/** 库存属性 */
	private InventoryInfo invInfo;

	public ScrapApply getScrapApply() {
		return scrapApply;
	}

	public void setScrapApply(ScrapApply scrapApply) {
		this.scrapApply = scrapApply;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public InventoryInfo getInvInfo() {
		return invInfo;
	}

	public void setInvInfo(InventoryInfo invInfo) {
		this.invInfo = invInfo;
	}

}
