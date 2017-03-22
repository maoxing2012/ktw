/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: OrderStatusHelperImpl.java
 */

package com.core.scpwms.server.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuShipMethod;
import com.core.scpwms.server.enumerate.EnuStoreRole;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.service.common.BinHelper;

/**
 * <p>
 * 一些常用的共通方法定义在这里
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/14<br/>
 */
@SuppressWarnings("all")
public class BinHelperImpl extends DefaultBaseManager implements BinHelper {

	// @Override
	// public Quant getLaskPickQuant(Bin bin) {
	// // 此方法用于当拣选分配，拣选执行被取消时，要重置库位属性中的最后拣选SKU，LOT
	// // 做法是，如果有未执行完成的拣选WT，取最后一个的QUANT信息
	// // 如果没有未执行完成WT，从拣选WH中取得最后一个的QUANT信息
	//
	// // 未执行完成的拣选WT的最后一个
	// String hql =
	// " SELECT wt.invInfo.quant.id FROM WarehouseTask wt WHERE wt.invInfo.bin.id =:binId AND ( wt.status =200  OR wt.status =500 OR wt.status =600) AND wt.processType like'%REQ' ORDER BY wt.id DESC";
	// List<Long> quantIds = commonDao.findByQuery(hql, "binId", bin.getId());
	//
	// if (quantIds != null && quantIds.size() > 0) {
	// Quant quant = commonDao.load(Quant.class, quantIds.get(0));
	// return quant;
	// }
	//
	// // 有库存，拣选WH的最后一个
	// String hql2 =
	// " SELECT wh.invInfo.quant.id FROM WtHistory wh WHERE wh.invInfo.bin.id =:binId AND wh.wt.processType like'%REQ' ORDER BY wh.id DESC";
	// List<Long> quantIds2 = commonDao
	// .findByQuery(hql2, "binId", bin.getId());
	// if (quantIds2 != null && quantIds2.size() > 0) {
	// Quant quant = commonDao.load(Quant.class, quantIds2.get(0));
	// return quant;
	// }
	//
	// return null;
	// }
	
	
	@Override
	public Quant getLastPutawayQuant(Bin bin) {
		InventoryInfo invInfo = getLastPutawayInvInfo(bin);
		
		if( invInfo == null )
			return null;
		
		return invInfo.getQuant();
	}
	
	@Override
	public InventoryInfo getLastPutawayInvInfo(Bin bin) {
		// 此方法用于当上架分配，上架执行被取消时，要重置库位属性中的最后存放SKU，LOT
		// 做法是，如果有未执行完成的上架WT，取最后一个的QUANT信息
		// 如果没有未执行完成WT，库位上也没有库存，返回NULL，最后存放信息都清空
		// 如果库位上有库存，按库存ID排序后，取第一个

		// 未执行完成的上架WT的最后一个
		String hql = " SELECT wt.id FROM WarehouseTask wt WHERE wt.descBin.id =:binId AND ( wt.planQty - wt.executeQty > 0 ) AND ( wt.status =200  OR wt.status =500 OR wt.status =600) AND wt.processType like'%PUTAWAY' ORDER BY wt.id DESC";
		List<Long> wtIds = commonDao.findByQuery(hql, "binId", bin.getId());

		if (wtIds != null && wtIds.size() > 0) {
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtIds.get(0));
			return wt.getInvInfo();
		}

		// 如果库位上没有库存，返回NULL
		String hql3 = " SELECT inv.id FROM Inventory inv where inv.bin.id = :binId and inv.baseQty > 0 order by inv.id desc";
		List<Long> invIds = commonDao.findByQuery(hql3, "binId", bin.getId());
		if (invIds == null || invIds.size() == 0) {
			return null;
		}

		// 有库存，随机算个库存的批次
		Inventory inv = commonDao.load(Inventory.class, invIds.get(0));
		return inv.getInventoryInfo();
	}
	
	@Override
	public Bin getEmptyCheckBin(Long whId) {
		List<String> paramName = new ArrayList<String>();
		List<Object> param = new ArrayList<Object>();
		
		StringBuffer hql = new StringBuffer( " SELECT bin.id FROM Bin bin WHERE bin.storageType.role = 'R5000' AND bin.disabled=false AND bin.wh.id = :whId ");
		paramName.add("whId");
		param.add(whId);

		hql.append(" ORDER BY bin.binCode ");

		List<Long> bins = commonDao.findByQuery(hql.toString(), paramName.toArray(new String[paramName.size()]), param.toArray(new Object[param.size()]));
		if (bins != null && bins.size() > 0) {
			return commonDao.load(Bin.class, bins.get(0));
		}

		return null;
	}

	@Override
	public synchronized Bin getEmptyCountBin(Long whId) {
		// 在不混单的复核区找一个没有库存，也没有订单占着的复核库位
		String hql = " SELECT bin.id FROM Bin bin "
				+ "WHERE bin.storageType.role = 'R8020' "
				+ "AND bin.disabled=false "
				+ "AND bin.wh.id = :whId "
				+ "ORDER BY bin.binCode ";
		
		List<Long> bins = commonDao.findByQuery(hql.toString(), "whId", whId);
		if (bins != null && bins.size() > 0) {
			return commonDao.load(Bin.class, bins.get(0));
		}

		return null;
	}
	
	@Override
	public synchronized Bin getEmptyShipBin(Long whId, Long carrierId){
		StringBuffer hql = new StringBuffer(
				" SELECT bin.id  FROM Bin bin WHERE bin.storageType.role = 'R7000' AND bin.disabled=0 AND bin.wh.id = :whId ");
		hql.append(" AND NOT EXISTS (SELECT inv.id FROM Inventory inv WHERE inv.bin.id =  bin.id) ");
		hql.append(" AND NOT EXISTS (SELECT obd.id FROM OutboundDelivery obd WHERE obd.status >= 200 AND obd.status <900 AND obd.shipBin.id = bin.id)");
		
		if( carrierId == null ){
			throw new BusinessException("请指定承运商。");
		}
		
		String hql2 = "select bbp.bin.id from BinBinGroup bbp where bbp.binGroup.role = 'R7000' and bbp.bin.disabled <> 1 and bbp.binGroup.id in"
				+ " ( select cbp.bg.id from CarrierBinGroup cbp where cbp.wh.id = :whId "
				+ "and cbp.carrier.id = :carrierId )";
		List<Bin> binListIds = commonDao.findByQuery(hql2, 
				new String[]{"whId", "carrierId"}, new Object[]{whId, carrierId});
		if( binListIds == null || binListIds.size() == 0 ){
			throw new BusinessException("该承运商没有绑定的发货月台。");
		}
		
		hql.append(" AND bin.id in (:binIds) ");
		hql.append(" ORDER BY bin.sortIndex, bin.binCode ");
		
		List<Long> bins = commonDao.findByQuery(hql.toString(), new String[]{"whId","binIds"}, new Object[]{whId, binListIds});
		if (bins != null && bins.size() > 0) {
			return commonDao.load(Bin.class, bins.get(0));
		}

		return null;
	}
	
	@Override
	public boolean isAllSameOwner(Long binId) {
		String hql = " select distinct inv.owner.id from Inventory inv where inv.bin.id = :binId ";
		List<Long> ids = commonDao.findByQuery(hql, "binId", binId);

		if (ids != null && ids.size() > 1)
			return false;

		return true;
	}

	@Override
	public boolean isAllSameLot(Long binId) {
		String hql = " select distinct inv.quantInv.quant.id from Inventory inv where inv.bin.id = :binId ";
		List<Long> ids = commonDao.findByQuery(hql, "binId", binId);

		if (ids != null && ids.size() > 1)
			return false;

		return true;
	}

	@Override
	public boolean isAllSameSku(Long binId) {
		String hql = " select distinct inv.quantInv.quant.sku.id from Inventory inv where inv.bin.id = :binId ";
		List<Long> ids = commonDao.findByQuery(hql, "binId", binId);

		if (ids != null && ids.size() > 1)
			return false;

		return true;
	}

	@Override
	public String[] refBinInfo(Long binId) {
		String[] result = null;

		Bin bin = commonDao.load(Bin.class, binId);
		InventoryInfo invInfo = getLastPutawayInvInfo(bin);
		if (invInfo == null) {
			if (bin.getBinInvInfo().getBinInfo().getLastLotInfo() != null) {
				bin.getBinInvInfo().clearBinInfo();
				result = new String[] { bin.getBinCode(), "null", "null" };
			}
		} else {
			if (!invInfo.getQuant().getId().equals(
					bin.getBinInvInfo().getBinInfo().getLastLotInfo())
					|| (invInfo.getOwner() != null && 
					!invInfo.getOwner().getId().equals(bin.getBinInvInfo().getBinInfo().getLastOwnerId()))) {
				bin.getBinInvInfo().refleshLastStoreInfo(invInfo);
				result = new String[] { bin.getBinCode(), invInfo.getQuant().getDispLot(),
						invInfo.getQuant().getSku().getCode() };
			}
		}
		return result;
	}

	@Override
	public void refreshBinInvInfo(Long binId) {
		Bin bin = commonDao.load(Bin.class, binId);

		// 逻辑库位直接跳过
		if (bin.getProperties().getIsLogicBin() != null
				&& bin.getProperties().getIsLogicBin()) {
			return;
		}

		// 库存数
		String hql1 = " select nvl(sum( inv.baseQty ),0) from Inventory inv where inv.bin.id = :binId ";
		Double currentQty = (Double) commonDao.findByQueryUniqueResult(hql1,
				new String[] { "binId" }, new Object[] { binId });
		
		// 库存容器数
		String palletHql1 = " select count(distinct cInv.containerSeq)  from ContainerInv cInv where cInv.bin.id = :binId ";
		Long currentPalletQty = (Long) commonDao.findByQueryUniqueResult(palletHql1,
				new String[] { "binId" }, new Object[] { binId });
		
		// 待上架数
		String hql2 = " SELECT nvl(sum( wt.planQty - wt.executeQty ),0) FROM WarehouseTask wt WHERE wt.descBin.id =:binId AND ( wt.planQty - wt.executeQty > 0 ) AND ( wt.status =200  OR wt.status =500 OR wt.status =600) AND wt.processType like'%PUTAWAY'";
		Double queuedQty = (Double) commonDao.findByQueryUniqueResult(hql2,
				new String[] { "binId" }, new Object[] { binId });
		
		// 待上架托盘数
		String palletHql2 = " SELECT count(distinct wt.invInfo.containerSeq) FROM WarehouseTask wt WHERE wt.descBin.id =:binId AND ( wt.planQty - wt.executeQty > 0 ) AND ( wt.status =200  OR wt.status =500 OR wt.status =600) AND wt.processType like'%PUTAWAY'";
		Long queuedPalletQty = (Long) commonDao.findByQueryUniqueResult(palletHql2,
				new String[] { "binId" }, new Object[] { binId });
		
		// 如果库存数和待上架数都是空
		if ((currentQty == null || currentQty <= 0D)
				&& (queuedQty == null || queuedQty <= 0D)) {
			// 最新存放信息情况
			bin.getBinInvInfo().clearBinInfo();
			// 库容信息清空
			bin.getBinInvInfo().getBinInfo().resetBinInfo();
		} else {
			// 库存重量
			String hql3 = " select nvl(sum( inv.baseQty * nvl(inv.quantInv.quant.sku.grossWeight,0) ),0) from Inventory inv where inv.bin.id = :binId ";
			Double currentWeight = (Double) commonDao.findByQueryUniqueResult(
					hql3, new String[] { "binId" }, new Object[] { binId });

			// 库存体积
			String hql4 = " select nvl(sum( inv.baseQty * nvl(inv.quantInv.quant.sku.volume,0) ),0) from Inventory inv where inv.bin.id = :binId ";
			Double currentVolume = (Double) commonDao.findByQueryUniqueResult(
					hql4, new String[] { "binId" }, new Object[] { binId });

			// 库存标准度量
			String hql5 = " select nvl(sum( inv.baseQty * nvl(inv.quantInv.quant.sku.metric,0) ),0) from Inventory inv where inv.bin.id = :binId ";
			Double currentMetric = (Double) commonDao.findByQueryUniqueResult(
					hql5, new String[] { "binId" }, new Object[] { binId });

			// 库存折算托盘数
//			String hql6 = " select nvl(sum( case when inv.basePackage.packageInfo.p3000.coefficient > 0  then round(inv.baseQty/(inv.basePackage.packageInfo.p3000.coefficient),4) else 0 end ),0) from Inventory inv where inv.bin.id = :binId ";
//			Double currentPallet = (Double) commonDao.findByQueryUniqueResult(
//					hql6, new String[] { "binId" }, new Object[] { binId });

			// 待上架重量
			String hql7 = " SELECT nvl(sum( (wt.planQty - wt.executeQty) * nvl(wt.invInfo.quant.sku.grossWeight,0)),0) FROM WarehouseTask wt WHERE wt.descBin.id =:binId AND ( wt.planQty - wt.executeQty > 0 ) AND ( wt.status =200  OR wt.status =500 OR wt.status =600) AND wt.processType like'%PUTAWAY'";
			Double queuedWeight = (Double) commonDao.findByQueryUniqueResult(
					hql7, new String[] { "binId" }, new Object[] { binId });

			// 待上架体积
			String hql8 = " SELECT nvl(sum( (wt.planQty - wt.executeQty) * nvl(wt.invInfo.quant.sku.volume,0) ),0) FROM WarehouseTask wt WHERE wt.descBin.id =:binId AND ( wt.planQty - wt.executeQty > 0 ) AND ( wt.status =200  OR wt.status =500 OR wt.status =600) AND wt.processType like'%PUTAWAY'";
			Double queuedVolume = (Double) commonDao.findByQueryUniqueResult(
					hql8, new String[] { "binId" }, new Object[] { binId });

			// 待上架标准度量
			String hql9 = " SELECT nvl(sum( (wt.planQty - wt.executeQty) * nvl(wt.invInfo.quant.sku.metric,0) ),0) FROM WarehouseTask wt WHERE wt.descBin.id =:binId AND ( wt.planQty - wt.executeQty > 0 ) AND ( wt.status =200  OR wt.status =500 OR wt.status =600) AND wt.processType like'%PUTAWAY'";
			Double queuedMetric = (Double) commonDao.findByQueryUniqueResult(
					hql9, new String[] { "binId" }, new Object[] { binId });

			// 待上架折算托盘数
//			String hql10 = " SELECT nvl(sum( case when wt.invInfo.packageDetail.packageInfo.p3000.coefficient > 0  then round((wt.planQty - wt.executeQty)/(wt.invInfo.packageDetail.packageInfo.p3000.coefficient),4) else 0 end ),0) FROM WarehouseTask wt WHERE wt.descBin.id =:binId AND ( wt.planQty - wt.executeQty > 0 ) AND ( wt.status =200  OR wt.status =500 OR wt.status =600) AND wt.processType like'%PUTAWAY'";
//			Double queuedPallet = (Double) commonDao.findByQueryUniqueResult(
//					hql10, new String[] { "binId" }, new Object[] { binId });

			bin.getBinInvInfo().getBinInfo()
					.setCurrentQty(currentQty == null ? 0D : currentQty);
			bin.getBinInvInfo()
					.getBinInfo()
					.setCurrentWeight(
							currentWeight == null ? 0D : currentWeight);
			bin.getBinInvInfo()
					.getBinInfo()
					.setCurrentVolumn(
							currentVolume == null ? 0D : currentVolume);
			bin.getBinInvInfo()
					.getBinInfo()
					.setCurrentMetric(
							currentMetric == null ? 0D : currentMetric);
			bin.getBinInvInfo()
					.getBinInfo()
					.setCurrentPallet(
							currentPalletQty == null ? 0D : currentPalletQty.doubleValue());

			bin.getBinInvInfo().getBinInfo()
					.setQueuedQty(queuedQty == null ? 0D : queuedQty);
			bin.getBinInvInfo().getBinInfo()
					.setQueuedWeight(queuedWeight == null ? 0D : queuedWeight);
			bin.getBinInvInfo().getBinInfo()
					.setQueuedVolumn(queuedVolume == null ? 0D : queuedVolume);
			bin.getBinInvInfo().getBinInfo()
					.setQueuedMetric(queuedMetric == null ? 0D : queuedMetric);
			bin.getBinInvInfo().getBinInfo()
					.setQueuedPallet(queuedPalletQty == null ? 0D : queuedPalletQty.doubleValue());

			// 刷新库容率
			bin.getBinInvInfo().releshRate();

			// 刷新最后混放信息
			refBinInfo(binId);
		}

		commonDao.store(bin.getBinInvInfo());
	}

	@Override
	public void refreshBinInvInfo(List<Long> binIds) {
		for (Long id : binIds) {
			refreshBinInvInfo(id);
		}
	}

	@Override
	public void refreshBinInvInfoBySku(Long skuId) {
		// 如果这个SKU已经有库存(或待上架任务)，需要重新计算一下所在库位的库容。
		String hql = " select distinct inv.bin.id from Inventory inv where inv.quantInv.quant.sku.id = :skuId ";
		List<Long> binIds = commonDao.findByQuery(hql, "skuId", skuId);
		if (binIds != null && binIds.size() > 0) {
			refreshBinInvInfo(binIds);
		}
		
		// 如果这个SKU已经有上架任务未完成，需要从新计算一下目标库位的库容
		String hql2 = "SELECT distinct wt.descBin.id FROM WarehouseTask wt WHERE wt.invInfo.quant.sku.id =:skuId AND ( wt.planQty - wt.executeQty > 0 ) AND ( wt.status =200  OR wt.status =500 OR wt.status =600) AND wt.processType like'%PUTAWAY'";
		List<Long> binIds2 = commonDao.findByQuery(hql2, "skuId", skuId);
		if (binIds2 != null && binIds2.size() > 0) {
			refreshBinInvInfo(binIds2);
		}
	}
	
	@Override
	public Bin getCheckBin(OutboundDelivery obd) {
		if (obd.getDescBin() == null) {
			// 排他
			Bin checkBin = getEmptyCheckBin(obd.getWh().getId());
			if (checkBin == null) {
				throw new BusinessException( ExceptionConstant.CHECK_BIN_NOT_FIND);
			}

			obd.setDescBin(checkBin);
			return checkBin;
		}
		return obd.getDescBin();
	}

	@Override
	public Bin getShipBin(Long whId) {
		StringBuffer hql = new StringBuffer(
				" SELECT bin.id FROM Bin bin WHERE 1=1 "
				+ " AND bin.storageType.role = 'R9000' "
				+ " AND bin.disabled=0 "
				+ " AND bin.wh.id = :whId ");
		hql.append(" ORDER BY bin.binCode ");

		List<Long> bins = commonDao.findByQuery(hql.toString(), "whId", whId);
		if (bins != null && bins.size() > 0) {
			return commonDao.load(Bin.class, bins.get(0));
		}
		return null;
	}

}
