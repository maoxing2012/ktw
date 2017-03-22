package com.core.scpwms.server.service.inventory;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.domain.InvEditData;
import com.core.scpwms.server.model.inventory.ContainerInv;
import com.core.scpwms.server.model.inventory.Inventory;

/**
 * 
 * <p>
 * 库存管理
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/20<br/>
 */
@Transactional(readOnly = false)
public interface InvManager extends BaseManager {

	/**
	 * 新建库存
	 * @param ownerId
	 * @param skuId
	 * @param binId
	 * @param invStatus
	 * @param expDate
	 * @param desc
	 */
	@Transactional
	public void newInv(Long ownerId, Long skuId, Long binId, String invStatus, Date expDate, double qty, String desc);

	/**
	 * 
	 * <p>
	 * 修改库存数量
	 * </p>
	 * 
	 * @param inv
	 * @param packQty
	 * @param memo
	 */
	@Transactional
	public void editInv(Long invId, double editQty, String memo);

	/**
	 * 
	 * <p>
	 * 修改批次属性
	 * </p>
	 * 
	 * @param inv
	 * @param packQty
	 * @param newLotInfo
	 * @param meno
	 * @param lotData
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void editLot(Long invId, Date expDate, double packQty, String meno);

	/**
	 * 库位移动
	 * @param invId
	 * @param descBinId
	 * @param moveQty
	 * @param laborId
	 * @param memo
	 */
	@Transactional
	public void invMove( Long invId, Long descBinId, double moveQty, Long laborId, String memo );
	
	/**
	 * 
	 * <p>整容器库位移动</p>
	 *
	 * @param cInv
	 * @param descBinId
	 * @param laborIds
	 * @param desc
	 */
	@Transactional
	public void containerInvMove(ContainerInv cInv, Long descBinId, List<Long> laborIds,String desc);
	
	/**
	 * 
	 * <p>批量库位移动</p>
	 *
	 * @param invIds
	 * @param descBinId
	 * @param laborIds
	 */
	@Transactional
	public void invBatchMove( List<Long> invIds, Long descBinId, Long laborId);
	
	/**
	 * 
	 * <p>调整库存状态</p>
	 *
	 * @param oldInv
	 * @param exePackQty
	 * @param descInvStatus
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void changeInvStatus(Long oldInv, double changeQty, String descInvStatus, String desc);
	
	/**
	 * 批量调整库存状态
	 * @param invIds
	 * @param descInvStatus
	 * @param desc
	 */
	@Transactional
	public void batchChangeInvStatus( List<Long> invIds, String descInvStatus, String desc );

}
