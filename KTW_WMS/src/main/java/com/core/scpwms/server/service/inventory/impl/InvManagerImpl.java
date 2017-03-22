/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : InvManagerImpl.java
 */
package com.core.scpwms.server.service.inventory.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.core.business.exception.BusinessException;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.domain.InvEditData;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuBinLockType;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuInvenHisType;
import com.core.scpwms.server.enumerate.EnuStoreRole;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.enumerate.EnuWtProcessType;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.ReasonCode;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.history.InventoryHistory;
import com.core.scpwms.server.model.inventory.ContainerInv;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.task.WtHistory;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.GetJdeInventoryDao;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.common.InventoryHistoryHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.inventory.InvManager;
import com.core.scpwms.server.service.inventory.QuantManager;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.LotValidate;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 
 * <p>
 * 库存管理实现类。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 戴 <br/>
 *         修改履历 <br/>
 *         2013/02/28 : MBP 戴: 初版创建<br/>
 */
@SuppressWarnings("all")
public class InvManagerImpl extends DefaultBaseManager implements InvManager {

	private InventoryHelper invHelper;
	private InventoryHistoryHelper historyHelper;
	private BCManager bcManager;
	private QuantManager quantManager;
	private OrderStatusHelper orderStatusHelper;

	public InventoryHelper getInvHelper() {
		return this.invHelper;
	}

	public void setInvHelper(InventoryHelper invHelper) {
		this.invHelper = invHelper;
	}

	public InventoryHistoryHelper getHistoryHelper() {
		return this.historyHelper;
	}

	public void setHistoryHelper(InventoryHistoryHelper historyHelper) {
		this.historyHelper = historyHelper;
	}

	public BCManager getBcManager() {
		return this.bcManager;
	}

	public void setBcManager(BCManager bcManager) {
		this.bcManager = bcManager;
	}

	public QuantManager getQuantManager() {
		return this.quantManager;
	}

	public void setQuantManager(QuantManager quantManager) {
		this.quantManager = quantManager;
	}

	public OrderStatusHelper getOrderStatusHelper() {
		return this.orderStatusHelper;
	}

	public void setOrderStatusHelper(OrderStatusHelper orderStatusHelper) {
		this.orderStatusHelper = orderStatusHelper;
	}

	@Override
	public void containerInvMove(ContainerInv cInv, Long descBinId, List<Long> laborIds,String desc) {
		List<Inventory> invs = commonDao.findByQuery("from Inventory inv where inv.container.id = :containerInvId", "containerInvId", cInv.getId());
		
		String containerSeq = null;
		List<Object[]> addInvInfo = new ArrayList<Object[]>();
		for( Inventory inv : invs ){
			double moveQty = inv.getBaseQty();
			
			if( containerSeq == null ){
				containerSeq = inv.getContainer().getContainerSeq();
			}
			
			// 源库位=目标库位
			if (inv.getBin().getId().equals(descBinId)) {
				throw new BusinessException(ExceptionConstant.SAME_SRC_DESC_BIN);
			}
			

			// 冻结状态不能移动
			if (EnuInvStatus.FREEZE.equals(inv.getStatus())) {
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}

			// 有分配数
			if (inv.getQueuedQty() != null && inv.getQueuedQty() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_MOVE_AS_MORE_THAN_AVAILABLE_QTY);
			}
			
			addInvInfo.add(new Object[]{inv.getInventoryInfo(), moveQty, inv.getId()});
			
			// 源库存（-）
			invHelper.removeInvWithoutAllocateQty(inv, moveQty);
			// 库存履历（-）
			historyHelper.saveInvHistory(inv, moveQty * (-1), null, null,EnuInvenHisType.MOVE, null, null, desc, null, null);
		}
		
		for( Object[] invInfoArray : addInvInfo ){
			Double moveQty = (Double)invInfoArray[1];
			
			// 目标库存（+）
			InventoryInfo invInfo = new InventoryInfo();
			try{
				BeanUtils.copyProperties(invInfo, invInfoArray[0]);
			}catch( Exception e ){
				throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
			}
			Bin descBin = commonDao.load(Bin.class, descBinId);
			invInfo.setBin(descBin);
			invInfo.setContainerSeq(containerSeq);
			Inventory newInv = invHelper.addInvToBin(invInfo, moveQty, true);

			// 库存履历（+）
			historyHelper.saveInvHistory(newInv, moveQty, null, null,EnuInvenHisType.MOVE, null, null, desc, null, null);

			// 自动生成一个WT
			WarehouseTask wt = new WarehouseTask((Long)invInfoArray[2]);
			wt.setWh(descBin.getWh());
//			wt.setTaskSequence(bcManager.getTaskSeq(wt.getWh().getCode()));
			wt.setProcessType(EnuWtProcessType.INV_MOVE);
			wt.setLabor(null);
			wt.setDescBin(descBin);
			wt.setInvInfo((InventoryInfo)invInfoArray[0]);
			wt.addPlanQty(moveQty);
			wt.execute(moveQty);
			wt.setOperateTime(new Date());
			commonDao.store(wt);
			orderStatusHelper.changeStatus(wt, EnuWarehouseOrderStatus.CLOSE);

			// 登记一条作业履历WH
			WtHistory wh = historyHelper.saveWtHistory(wt,
					(InventoryInfo)invInfoArray[0], newInv.getInventoryInfo(), null,
					moveQty, Boolean.TRUE, null);
		}
	}
	
	@Override
	public void invMove(Long invId, Long descBinId, double moveQty, Long laborId, String memo) {
		Inventory inv = commonDao.load(Inventory.class, invId);
		
		// 源库位=目标库位
		if (inv.getBin().getId().equals(descBinId)) {
			return;
		}
		
		// 源库位不能有出库锁
		if( inv.getBin().getLockStatus() != null && 
				(EnuBinLockType.LOCK_T2.equals(inv.getBin().getLockStatus()) 
						|| EnuBinLockType.LOCK_T3.equals(inv.getBin().getLockStatus()) 
						|| EnuBinLockType.LOCK_T4.equals(inv.getBin().getLockStatus())) ){
			throw new BusinessException( "移動元の棚番が出庫ロックされています。" );
		}
		
		// 目标库位不能有入库锁
		if( inv.getBin().getLockStatus() != null && 
				(EnuBinLockType.LOCK_T1.equals(inv.getBin().getLockStatus()) 
						|| EnuBinLockType.LOCK_T3.equals(inv.getBin().getLockStatus()) 
						|| EnuBinLockType.LOCK_T4.equals(inv.getBin().getLockStatus())) ){
			throw new BusinessException( "移動先の棚番が入庫ロックされています。" );
		}
		
		moveQty = PrecisionUtils.getBaseQty(moveQty, inv.getBasePackage());

		// 移动数量不能超过可用量
		if (DoubleUtil.compareByPrecision(moveQty, inv.getAvailableQty(), 0) > 0) {
			throw new BusinessException( ExceptionConstant.CANNOT_MOVE_AS_MORE_THAN_AVAILABLE_QTY);
		}
		
		Labor labor = commonDao.load(Labor.class, laborId);
		// 源库存（-）
		invHelper.removeInvWithoutAllocateQty(inv, moveQty);
		// 库存履历（-）
		InventoryHistory removeInvHis = historyHelper.saveInvHistory(inv, moveQty * (-1), null, labor, EnuInvenHisType.MOVE, null, null, memo, null, null);

		// 目标库存（+）
		InventoryInfo invInfo = new InventoryInfo();
		try{
			BeanUtils.copyProperties(invInfo, inv.getInventoryInfo());
		}catch( Exception e ){
			throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
		}
		invInfo.setBin(commonDao.load(Bin.class, descBinId));
		Inventory newInv = invHelper.addInvToBin(invInfo, moveQty, true);

		// 库存履历（+）
		historyHelper.saveInvHistory(newInv, moveQty, null, labor, EnuInvenHisType.MOVE, null, removeInvHis.getId(), memo, null, null);
	}
	
	@Override
	public void invBatchMove(List<Long> invIds, Long descBinId, Long laborId) {
		if (invIds != null && invIds.size() > 0) {
			for (Long invId : invIds) {
				Inventory inv = commonDao.load(Inventory.class, invId);
				invMove(invId, descBinId, inv.getAvaliableQuantity(), laborId, null);
			}
		}
	}

	@Override
	public void newInv(Long ownerId, Long skuId, Long binId, String invStatus, Date expDate, double qty, String desc) {
		if (qty <= 0) {
			return;
		}
		
		LotInputData inputLot = new LotInputData();
		inputLot.setProperty1(DateUtil.getStringDate(expDate, DateUtil.PURE_DATE_FORMAT));
		Date inboundDate = new Date();
		Sku sku = commonDao.load(Sku.class, skuId);
		Quant quant = quantManager.getQuantInfo(sku.getId(), inputLot);

		// 创建收货库存
		double recievingQty = PrecisionUtils.getBaseQty(qty, sku.getProperties().getPackageInfo().getP1000());
		InventoryInfo invInfo = new InventoryInfo();
		invInfo.setQuant(quant);
		invInfo.setBin(commonDao.load(Bin.class, binId));
		invInfo.setPackageDetail(sku.getProperties().getPackageInfo().getP1000());
		invInfo.setInboundDate(inboundDate);
		invInfo.setTrackSeq(null);
		invInfo.setInvStatus(invStatus);
		invInfo.setOwner(commonDao.load(Owner.class, ownerId));
		
		Inventory newInv = invHelper.addInvToBin(invInfo, recievingQty, true);

		// 创建库存履历
		historyHelper.saveInvHistory(newInv, recievingQty, null, null, EnuInvenHisType.INVENTORY_ADJUST, null, null, desc, null, null);
	}

	@Override
	public void editInv(Long invId, double editQty, String memo) {
		// 避免出现负库存
		if (editQty < 0) {
			return;
		}
		
		Inventory oldInv = commonDao.load(Inventory.class, invId);
		double updateQty = DoubleUtil.sub(oldInv.getBaseQty(), PrecisionUtils.getBaseQty(editQty, oldInv.getBasePackage()));

		// 库存减少
		if (updateQty >= 0) {
			// 减少数量不能超过可用量
			if (DoubleUtil.compareByPrecision(oldInv.getAvaliableQuantity(), updateQty, 0) < 0) {
				throw new BusinessException( "調整数>在庫数-引当数" );
			}

			invHelper.removeInvWithoutAllocateQty(oldInv, updateQty);
			historyHelper.saveInvHistory(oldInv, updateQty * (-1), null, null, EnuInvenHisType.INVENTORY_ADJUST, null, null, memo, null, null);
		}
		// 库存增加
		else {
			updateQty = updateQty * (-1);

			invHelper.addInvToBin(oldInv.getInventoryInfo(), updateQty, true);
			historyHelper.saveInvHistory(oldInv, updateQty, null, null, EnuInvenHisType.INVENTORY_ADJUST, null, null, memo, null, null);
		}
	}

	@Override
	public void editLot(Long invId, Date expDate, double qty, String memo) {
		Inventory oldInv = commonDao.load(Inventory.class, invId);
		
		String oldExpDate = oldInv.getQuantInv().getQuant().getDispLot();
		String newExpDate = DateUtil.getStringDate(expDate, DateUtil.PURE_DATE_FORMAT);
		
		if( oldExpDate.equals(newExpDate) )
			return;
		
		// 盘点和集货口,发货月台不能调整库存状态
		Bin bin = oldInv.getBin();
		if( EnuStoreRole.R5000.equals(bin.getStorageType().getRole()) || EnuStoreRole.R8020.equals(bin.getStorageType().getRole()) ){
			throw new BusinessException("ロット変更不可。");
		}

		LotInputData newlotInfo = new LotInputData();
		newlotInfo.setProperty1(newExpDate);

		// Quant信息取得
		Quant descQuant = quantManager.getQuantInfo(oldInv.getQuantInv().getQuant().getSku().getId(), newlotInfo);

		// 需要调整批次的数量
		double editLotQty = PrecisionUtils.getBaseQty(qty, oldInv.getBasePackage());

		// 调整的数据不能大于该库存的可用量
		if (DoubleUtil.compareByPrecision(oldInv.getAvailableQty(), editLotQty, 0) < 0) {
			throw new BusinessException("調整数>在庫数-引当数");
		}

		// 源库存（-）
		invHelper.removeInvWithoutAllocateQty(oldInv, editLotQty);

		// 库存履历（-）
		InventoryHistory removeInvHis = historyHelper.saveInvHistory(oldInv, editLotQty * (-1), null, null, EnuInvenHisType.MODIFY_LOT, null, null, memo, null, null);

		// 目标库存（+）
		InventoryInfo newInvInfo = new InventoryInfo();
		try{
			BeanUtils.copyProperties(newInvInfo, oldInv.getInventoryInfo());
		}catch( Exception e ){
			throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
		}
		newInvInfo.setQuant(descQuant);
		Inventory newInv = invHelper.addInvToBin(newInvInfo, editLotQty, true);

		// 库存履历（+）
		historyHelper.saveInvHistory(newInv, editLotQty, null, null, EnuInvenHisType.MODIFY_LOT, null, removeInvHis.getId(), memo, null, null);

	}

	@Override
	public void changeInvStatus(Long invId, double changeQty, String descInvStatus, String desc) {
		Inventory inv = commonDao.load(Inventory.class, invId);
		
		double exeQty = PrecisionUtils.getBaseQty(changeQty, inv.getBasePackage());
		Bin bin = commonDao.load(Bin.class, inv.getBin().getId());
		
		// 盘点和集货口,发货月台不能调整库存状态
		if( EnuStoreRole.R5000.equals(bin.getStorageType().getRole()) || EnuStoreRole.R8020.equals(bin.getStorageType().getRole()) ){
			throw new BusinessException("ステータス変更不可。");
		}
		
		// 超过库存可用量
		if (DoubleUtil.compareByPrecision(inv.getAvaliableQuantity(), exeQty, 0) < 0) {
			throw new BusinessException("調整数>在庫数-引当数");
		}

		// 如果质检对象的库存状态不是良品，修改库存状态
		if (!descInvStatus.equals(inv.getStatus())) {
			// 库存(-)
			invHelper.removeInvWithoutAllocateQty(inv, exeQty);
			// 库存履历(-)
			InventoryHistory removeInvHis = historyHelper.saveInvHistory(inv, exeQty * (-1), null, null, EnuInvenHisType.CHANGE_STATUS, null, null, desc, null, null);
	
			// 库存(+)
			InventoryInfo newInvInfo = new InventoryInfo();
			try{
				BeanUtils.copyProperties(newInvInfo, inv.getInventoryInfo());
			}catch( Exception e ){
				throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
			}
			newInvInfo.setInvStatus(descInvStatus);
			Inventory newInv = invHelper.addInvToBin(newInvInfo, exeQty, false);
	
			// 库存履历(+)
			historyHelper.saveInvHistory(newInv, exeQty, null, null, EnuInvenHisType.CHANGE_STATUS, null, removeInvHis.getId(), desc, null, null);
		} else {
			return;
		}
	}

	@Override
	public void batchChangeInvStatus(List<Long> invIds, String descInvStatus, String desc) {
		for( Long invId : invIds ){
			Inventory inv = commonDao.load(Inventory.class, invId);
			changeInvStatus(invId, inv.getAvailableQty(), descInvStatus, desc);
		}
	}
}
