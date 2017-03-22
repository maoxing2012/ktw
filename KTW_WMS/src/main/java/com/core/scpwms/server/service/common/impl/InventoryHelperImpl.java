package com.core.scpwms.server.service.common.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.business.exception.BusinessException;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.enumerate.EnuBinLockType;
import com.core.scpwms.server.model.common.ReasonCode;
import com.core.scpwms.server.model.inventory.ContainerInv;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.inventory.QuantInventory;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.util.BinValidate;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 
 * <p>
 * 处理库存数量的共通方法
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/10<br/>
 */
public class InventoryHelperImpl extends DefaultBaseManager implements
		InventoryHelper {

	public BinHelper binHelper;

	public BinHelper getBinHelper() {
		return this.binHelper;
	}

	public void setBinHelper(BinHelper binHelper) {
		this.binHelper = binHelper;
	}
	
	/**
	 * 
	 * <p>创建一条0库存</p>
	 *
	 * @param invInfo
	 * @return
	 */
	@Transactional
	public Inventory createZeroInventory( InventoryInfo invInfo ){
		if (EnuBinLockType.LOCK_T1.equals(invInfo.getBin().getLockStatus())
				|| EnuBinLockType.LOCK_T3.equals(invInfo.getBin()
						.getLockStatus())
				|| EnuBinLockType.LOCK_T4.equals(invInfo.getBin()
						.getLockStatus())) {
			throw new BusinessException(ExceptionConstant.DESC_BIN_HAS_IN_LOCK);
		}
		
		// 收货至QuantInventory
		Inventory inv = createInventory(invInfo);
		this.commonDao.store(inv);
		
		return inv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.InventoryHelper#addInvToBin(com
	 * .core.scpwms.server.domain.InventoryInfo, double)
	 */
	@Override
	public Inventory addInvToBin(InventoryInfo invInfo, double addQty, boolean withBinValiCheck) {
		// 避免0库存
		if (addQty == 0) {
			return null;
		}
		
		// 混放检查
		checkBinMix(invInfo.getBin().getId(), invInfo.getOwner(), invInfo.getQuant());
//		
//		// 库容检查
//		if( withBinValiCheck ) {
//			String binValiMes = BinValidate.AllocateBinValidate(invInfo.getBin(), invInfo.getQuant(), invInfo.getOwner(),invInfo.getPackageDetail(), new Double(addQty));
//			if (binValiMes != null) {
//				throw new BusinessException(binValiMes);
//			}
//		}
		
		// 库位锁检查
		if (EnuBinLockType.LOCK_T1.equals(invInfo.getBin().getLockStatus())
				|| EnuBinLockType.LOCK_T3.equals(invInfo.getBin().getLockStatus())
				|| EnuBinLockType.LOCK_T4.equals(invInfo.getBin().getLockStatus())) {
			throw new BusinessException(ExceptionConstant.DESC_BIN_HAS_IN_LOCK);
		}
		
		// 收货至QuantInventory
		Inventory inv = createInventory(invInfo);
		inv.addBaseQty(addQty);
		this.commonDao.store(inv);

		if (inv.getBin().getProperties().getIsLogicBin() == null || !inv.getBin().getProperties().getIsLogicBin().booleanValue()) {
			// 更新最后存货信息
			inv.getBin().getBinInvInfo().refleshLastStoreInfo(inv.getInventoryInfo());
			// 更新库容
			inv.getBin().getBinInvInfo().addInvInfoNoPallet(invInfo, addQty);
		}

		return inv;
	}
	
	public boolean isNewPallet2Bin( Long binId, String containerSeq, String palletSeq ){
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer hql = new StringBuffer( " select id from ContainerInv cInv where cInv.bin.id = :binId " );
		paramNames.add("binId");
		params.add(binId);
		
		if( containerSeq != null && containerSeq.trim().length() > 0 ){
			hql.append(" and cInv.containerSeq = :containerSeq");
			paramNames.add("containerSeq");
			params.add(containerSeq);
		}
		if( palletSeq != null && palletSeq.trim().length() > 0 ){
			hql.append(" and cInv.palletSeq = :palletSeq");
			paramNames.add("palletSeq");
			params.add(palletSeq);
		}
		
		List<Long> ids = commonDao.findByQuery(hql.toString(), paramNames.toArray(new String[paramNames.size()]), params.toArray(new Object[params.size()]));
		if( ids == null || ids.size() == 0 ){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Inventory addInvToBin4Count(InventoryInfo invInfo, double addQty, boolean withBinValiCheck) {
		// 库位验证（混放，容量等）
		if (withBinValiCheck) {
			String binValiMes = BinValidate.AllocateBinValidate(
					invInfo.getBin(), invInfo.getQuant(), invInfo.getOwner(),
					invInfo.getPackageDetail(), new Double(addQty));
			if (binValiMes != null) {
				throw new BusinessException(binValiMes);
			}
		}

		// 避免0库存
		if (addQty == 0) {
			return null;
		}

		// 收货至QuantInventory
		Inventory inv = createInventory(invInfo);
		inv.addBaseQty(addQty);
		this.commonDao.store(inv);

		if (inv.getBin().getProperties().getIsLogicBin() == null
				|| !inv.getBin().getProperties().getIsLogicBin().booleanValue()) {
			// 更新最后存货信息
			inv.getBin().getBinInvInfo().refleshLastStoreInfo(inv.getInventoryInfo());

			// 更新库容
			inv.getBin().getBinInvInfo().addInvInfoNoPallet(invInfo, addQty);
		}
		return inv;
	}

	/**
	 * 
	 * 根据参数新建库存
	 * 
	 * 目前托盘库存未实现，需要重新实现
	 */
	private Inventory createInventory(InventoryInfo invInfo) {
		Inventory inv = getInv(invInfo);

		if (inv != null)
			return inv;

		// 判断托盘是否为空
		ContainerInv cinv = null;
		if (!StringUtils.isEmpty(invInfo.getPalletSeq())) {
			List<String> paramNames = new ArrayList<String>();
			List<Object> params = new ArrayList<Object>();
			StringBuffer hql = new StringBuffer(
					" select cin.id from ContainerInv cin where 1=1 ");
			hql.append(" and cin.bin.wh.id = :whId ");
			paramNames.add("whId");
			params.add(invInfo.getBin().getWh().getId());
			
			if (!StringUtils.isEmpty(invInfo.getPalletSeq())) {
				hql.append(" and cin.palletSeq =:palletSeq");
				paramNames.add("palletSeq");
				params.add(invInfo.getPalletSeq());
			} else {
				hql.append(" and cin.palletSeq is null ");
			}

			List<Long> cinIds = commonDao.findByQuery(hql.toString(),
					paramNames.toArray(new String[paramNames.size()]),
					params.toArray(new Object[params.size()]));

			if (cinIds == null || cinIds.size() == 0) {
				cinv = new ContainerInv();

				if (!StringUtils.isEmpty(invInfo.getPalletSeq())) {
					// 托盘号
					cinv.setPalletSeq(invInfo.getPalletSeq());
					// 容器类型
					cinv.setBePallet(Boolean.TRUE);
				}
				cinv.setBin(invInfo.getBin());
				this.commonDao.store(cinv);
			} 
			else {
				cinv = commonDao.load(ContainerInv.class, cinIds.get(0));
				if( !cinv.getBin().getId().equals(invInfo.getBin().getId()) ){
					throw new BusinessException(ExceptionConstant.INPUT_BIN_NOT_MATCH_PALLET_BIN, 
							new String[]{ invInfo.getBin().getBinCode(), cinv.getBin().getBinCode() });
				}
			}
		}

		String hql = "FROM QuantInventory qi WHERE qi.wh.id = :whId AND qi.quant.id = :quantId";
		QuantInventory qi = (QuantInventory) commonDao.findByQueryUniqueResult(
				hql, new String[] { "whId", "quantId" }, new Object[] {
						invInfo.getBin().getWh().getId(),
						invInfo.getQuant().getId() });
		if (qi == null) {
			qi = new QuantInventory();
			qi.setWh(invInfo.getBin().getWh());
			qi.setQuant(invInfo.getQuant());
			this.commonDao.store(qi);
		}

		inv = new Inventory();
		inv.setInventoryInfo(invInfo, qi, cinv);

		return inv;
	}

	/**
	 * 扣减库存
	 */
	@Override
	public void removeInv(Inventory inv, Double executeQty) {

		if (executeQty.doubleValue() <= 0)
			return;

		if (inv == null) {
			// 该库存不存在！
			throw new BusinessException(ExceptionConstant.INVENTORY_NOT_FOUND);
		}

		if (EnuBinLockType.LOCK_T2.equals(inv.getBin().getLockStatus())
				|| EnuBinLockType.LOCK_T3.equals(inv.getBin().getLockStatus())
				|| EnuBinLockType.LOCK_T4.equals(inv.getBin().getLockStatus())) {
			throw new BusinessException(ExceptionConstant.SRC_BIN_HAS_OUT_LOCK);
		}

		// 使用基本数量扣减库存并更新包装数量
		inv.removeBaseAllocateQty(executeQty.doubleValue());

		boolean isDeletePalletFromBin = false;
		
		// 删除0库存
		if (inv.getBaseQty() == 0 && inv.getQueuedQty() == 0) {
			isDeletePalletFromBin = deleteInv(inv);
		}
		else{
			commonDao.store(inv);
		}

		if (inv.getBin().getProperties().getIsLogicBin() == null
				|| !inv.getBin().getProperties().getIsLogicBin().booleanValue()) {
			// 减库容
			inv.getBin().getBinInvInfo()
					.removeInvInfoNoPallet(inv.getInventoryInfo(), executeQty);
			
			// 减托盘数
			if( isDeletePalletFromBin ){
				inv.getBin().getBinInvInfo().removeCurrentPallet(1D);
			}
			
			// 更新原库存的拣货信息
			inv.getBin().getBinInvInfo()
					.refleshLastPickInfo(inv.getQuantInv().getQuant());
		}

		// // 删除没有库存的QuantInv
		// if (inv.getQuantInv().getInvs() == null
		// || inv.getQuantInv().getInvs().isEmpty()) {
		// commonDao.delete(inv.getQuantInv());
		// } else {
		// commonDao.store(inv.getQuantInv());
		// }

		// commonDao.store(inv.getQuantInv());
	}

	private void refLastStoreInfo(Inventory inv) {
		// 如果库存所在的库位的最后存放批次正好和这次要删除的库存批次一致。
		// 要重新刷新一下最后存放信息
		if (inv.getQuantInv()
				.getQuant()
				.getId()
				.equals(inv.getBin().getBinInvInfo().getBinInfo()
						.getLastLotInfo())) {
			InventoryInfo invInfo = binHelper.getLastPutawayInvInfo(inv.getBin());
			if (invInfo != null) {
				inv.getBin().getBinInvInfo().refleshLastStoreInfo(invInfo);
			}
		}
	}
	
	/**
	 * 扣减分配数，不扣减库存数
	 */
	@Override
	public void removeAllocateQty(Inventory inv, Double executeQty) {
		if (executeQty.doubleValue() <= 0)
			return;

		if (inv == null) {
			throw new BusinessException(ExceptionConstant.INVENTORY_NOT_FOUND);
		}

		if (EnuBinLockType.LOCK_T2.equals(inv.getBin().getLockStatus())
				|| EnuBinLockType.LOCK_T3.equals(inv.getBin().getLockStatus())
				|| EnuBinLockType.LOCK_T4.equals(inv.getBin().getLockStatus())) {
			throw new BusinessException(ExceptionConstant.SRC_BIN_HAS_OUT_LOCK);
		}
		
		inv.removeAllocateQty(executeQty);
	}

	/**
	 * 扣减库存，单不扣减分配数
	 */
	@Override
	public void removeInvWithoutAllocateQty(Inventory inv, Double executeQty) {
		if (executeQty.doubleValue() <= 0)
			return;

		if (inv == null) {
			throw new BusinessException(ExceptionConstant.INVENTORY_NOT_FOUND);
		}

		if (EnuBinLockType.LOCK_T2.equals(inv.getBin().getLockStatus())
				|| EnuBinLockType.LOCK_T3.equals(inv.getBin().getLockStatus())
				|| EnuBinLockType.LOCK_T4.equals(inv.getBin().getLockStatus())) {
			throw new BusinessException(ExceptionConstant.SRC_BIN_HAS_OUT_LOCK);
		}

		// 使用基本数量扣减库存并更新包装数量
		inv.removeBaseQty(executeQty.doubleValue());
		
		boolean isDeletePalletFromBin = false;
		
		// 删除0库存
		if (inv.getBaseQty() == 0 && inv.getQueuedQty() == 0) {
			isDeletePalletFromBin = deleteInv(inv);
		}
		else{
			commonDao.store(inv);
		}

		if (inv.getBin().getProperties().getIsLogicBin() == null
				|| !inv.getBin().getProperties().getIsLogicBin().booleanValue()) {
			// 减库容
			inv.getBin().getBinInvInfo()
					.removeInvInfoNoPallet(inv.getInventoryInfo(), executeQty);
			
			// 减托盘数
			if( isDeletePalletFromBin ){
				inv.getBin().getBinInvInfo().removeCurrentPallet(1D);
			}
			
			// 更新原库存的拣货信息
			inv.getBin().getBinInvInfo()
					.refleshLastPickInfo(inv.getQuantInv().getQuant());
		}

		// // 删除没有库存的QuantInv
		// if (inv.getQuantInv().getInvs() == null
		// || inv.getQuantInv().getInvs().isEmpty()) {
		// commonDao.delete(inv.getQuantInv());
		//
		// } else {
		// commonDao.store(inv.getQuantInv());
		// }
		
		
	}

	@Override
	public void removeInvWithoutAllocateQty4Count(Inventory inv,
			Double executeQty) {
		if (PrecisionUtils.compareByBasePackage(executeQty, 0D,
				inv.getBasePackage()) == 0)
			return;

		if (inv == null) {
			throw new BusinessException(ExceptionConstant.INVENTORY_NOT_FOUND);
		}

		// 使用基本数量扣减库存并更新包装数量
		inv.removeBaseQty(executeQty.doubleValue());

		if (inv.getBin().getProperties().getIsLogicBin() == null
				|| !inv.getBin().getProperties().getIsLogicBin().booleanValue()) {
			// 减库容
			inv.getBin().getBinInvInfo()
					.removeInvInfoNoPallet(inv.getInventoryInfo(), executeQty);
		}

		// 删除0库存
		if (inv.getBaseQty() == 0 && inv.getQueuedQty() == 0) {
			deleteInv(inv);
			return;
		}

		// 更新原库存的拣货信息
		inv.getBin().getBinInvInfo()
				.refleshLastPickInfo(inv.getQuantInv().getQuant());

		commonDao.store(inv);

		// 删除没有库存的QuantInv
		// if (inv.getQuantInv().getInvs() == null
		// || inv.getQuantInv().getInvs().isEmpty()) {
		// commonDao.delete(inv.getQuantInv());
		//
		// } else {
		// commonDao.store(inv.getQuantInv());
		// }
	}

	public Inventory getInv(Long binId, Quant quant, Long pkgId,
			String palletNo, String containerSeq, String trackseq,
			Date inboundDate, String status, Long ownerId) {

		Bin bin = commonDao.load(Bin.class, binId);

		if (bin == null || bin.getId() == null)
			return null;

		// 查询唯一条QuantInventory
		QuantInventory qinv = (QuantInventory) commonDao
				.findByQueryUniqueResult(
						"from QuantInventory qi where qi.wh.id=:warehouseId and qi.quant.id=:quantId",
						new String[] { "warehouseId", "quantId" },
						new Object[] { bin.getWh().getId(), quant.getId() });

		if (qinv == null)
			return null;

		// 查询唯一库存hql
		StringBuffer hql = new StringBuffer(
				"select inv.id FROM Inventory inv left join inv.owner left join inv.container WHERE 1=1 ");
		// 查询条件数组
		List<String> strList = new ArrayList<String>();
		// 查询对象数组
		List<Object> objList = new ArrayList<Object>();

		if (ownerId != null) {
			hql.append("AND inv.owner.id=:ownerId  ");
			strList.add("ownerId");
			objList.add(ownerId);
		} else {
			hql.append("AND inv.owner.id is null ");
		}

		hql.append("AND inv.bin.id=:binId  ");
		strList.add("binId");
		objList.add(binId);

		hql.append("AND inv.quantInv.id=:qiId  ");
		strList.add("qiId");
		objList.add(qinv.getId());

		hql.append("AND inv.basePackage.id=:packId  ");
		strList.add("packId");
		objList.add(pkgId);

		hql.append("AND inv.status=:invStatus  ");
		strList.add("invStatus");
		objList.add(status);

		// 入库日期
		if (inboundDate != null) {
			hql.append("AND to_char(inv.inboundDate,'YYYY-MM-DD')=:inboundDate ");
			strList.add("inboundDate");
			objList.add(DateUtil.getStringDate(inboundDate,
					DateUtil.HYPHEN_DATE_FORMAT));
		} else {
			hql.append("AND inv.inboundDate IS NULL ");
		}

		// 追踪号
		if (!StringUtils.isEmpty(trackseq)) {
			hql.append(" and inv.trackSeq=:trackSeq ");
			strList.add("trackSeq");
			objList.add(trackseq);
		} else {
			hql.append(" and inv.trackSeq is null ");
		}

		// 托盘号
		if (!StringUtils.isEmpty(palletNo)) {
			hql.append(" and inv.container.palletSeq=:palletSeq");
			strList.add("palletSeq");
			objList.add(palletNo);
		} else {
			hql.append(" and (inv.container.id is null or inv.container.palletSeq is null)");
		}

		// 容器号
		if (!StringUtils.isEmpty(containerSeq)) {
			hql.append(" and inv.containerSeq=:containerSeq");
			strList.add("containerSeq");
			objList.add(containerSeq);
		} else {
			hql.append(" and inv.containerSeq is null ");
		}

		String[] str = new String[strList.size()];
		strList.toArray(str);

		List<Long> invs = (List<Long>) commonDao.findByQuery(hql.toString(), str, objList.toArray());
		if (!invs.isEmpty()) {
			return commonDao.load(Inventory.class, invs.get(0));
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.InventoryHelper#getInv(com.core
	 * .scpwms.server.model.task.WarehouseTask)
	 */
	@Override
	public Inventory getInv(WarehouseTask wt) {
		return getInv(wt.getInvInfo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.InventoryHelper#getInv(com.core
	 * .scpwms.server.domain.InventoryInfo)
	 */
	@Override
	public Inventory getInv(InventoryInfo invInfo) {
		return getInv(invInfo.getBin().getId(), invInfo.getQuant(),
				invInfo.getPackageDetail().getId(), invInfo.getPalletSeq(),
				invInfo.getContainerSeq(), invInfo.getTrackSeq(),
				invInfo.getInboundDate(), invInfo.getInvStatus(),
				invInfo.getOwner() == null ? null : invInfo.getOwner().getId());
	}

	@Override
	public void allocateInv(Inventory inv, double allocateQty) {
		inv.allocate(allocateQty);
		
		if( inv.getQueuedQty() != null && inv.getQueuedQty() > 0 ){
			// 刷新库位的最后拣货信息
			inv.getBin().getBinInvInfo()
					.refleshLastPickInfo(inv.getQuantInv().getQuant());
		}
		commonDao.store(inv);
	}

	@Override
	public void allocateInvByPutaway(Inventory inv, Bin descBin,
			double allocateQty) {
		inv.allocate(allocateQty);
		// 刷新目标库存的最后存放信息
		descBin.getBinInvInfo().refleshLastStoreInfo(inv.getInventoryInfo());
		commonDao.store(inv);
	}

	@Override
	public void unAllocateInv(Inventory inv, double unAllocateQty) {
		inv.unAllocate(unAllocateQty);
	}

	@Override
	public void unAllocateInvByPutaway(Inventory inv, Bin descBin,
			double unAllocateQty) {
		inv.unAllocate(unAllocateQty);

		if (inv.getQuantInv().getQuant().getId()
				.equals(descBin.getBinInvInfo().getBinInfo().getLastLotInfo())) {

			// 从WT，WH取得最后拣选的信息，重新设置
			InventoryInfo invInfo = binHelper.getLastPutawayInvInfo(descBin);
			if (invInfo != null) {
				descBin.getBinInvInfo().refleshLastStoreInfo(invInfo);
			}
		}
	}

	@Override
	public List<Inventory> getInvs(Long ownerId, Long skuId, Long binId,
			LotInputData lotData, String trackSeq, String invStatus,
			String storageType) {
		List<String> params = new ArrayList<String>();
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				" from Inventory inv where (inv.baseQty - inv.queuedQty > 0) and inv.status <> 'FREEZE' and (bin.lockStatus is null or bin.lockStatus = 'LOCK_T1' )");

		if (ownerId != null) {
			sb.append(" and inv.owner.id = :ownerId ");
			params.add("ownerId");
			param.add(ownerId);
		}

		if (skuId != null) {
			sb.append(" and inv.quantInv.quant.sku.id =:skuId ");
			params.add("skuId");
			param.add(skuId);
		}

		if (storageType != null) {
			sb.append(" and inv.bin.storageType.role =:role ");
			params.add("role");
			param.add(storageType);
		}

		if (binId != null) {
			sb.append(" and inv.bin.id =:binId ");
			params.add("binId");
			param.add(binId);
		}

		if (invStatus != null) {
			sb.append(" and inv.status =:invStatus ");
			params.add("invStatus");
			param.add(invStatus);
		}

		// trackSeq
		if (!StringUtils.isEmpty(trackSeq)) {
			sb.append(" and inv.trackSeq =:trackSeq ");
			params.add("trackSeq");
			param.add(trackSeq);
		} else {
			sb.append(" and inv.trackSeq is null ");
		}
		// P1
		if (lotData != null && !StringUtils.isEmpty(lotData.getProperty1())) {
			sb.append(" and inv.quantInv.quant.lotData.property1 =:property1 ");
			params.add("property1");
			param.add(lotData.getProperty1());
		} else {
			sb.append(" and inv.quantInv.quant.lotData.property1 is null ");
		}

		// P2
		if (lotData != null && !StringUtils.isEmpty(lotData.getProperty2())) {
			sb.append(" and inv.quantInv.quant.lotData.property2 =:property2 ");
			params.add("property2");
			param.add(lotData.getProperty2());
		} else {
			sb.append(" and inv.quantInv.quant.lotData.property2 is null ");
		}

		// P3
		if (lotData != null && !StringUtils.isEmpty(lotData.getProperty3())) {
			sb.append(" and inv.quantInv.quant.lotData.property3 =:property3 ");
			params.add("property3");
			param.add(lotData.getProperty3());
		} else {
			sb.append(" and inv.quantInv.quant.lotData.property3 is null ");
		}

		// P4
		if (lotData != null && !StringUtils.isEmpty(lotData.getProperty4())) {
			sb.append(" and inv.quantInv.quant.lotData.property4 =:property4 ");
			params.add("property4");
			param.add(lotData.getProperty4());
		} else {
			sb.append(" and inv.quantInv.quant.lotData.property4 is null ");
		}

		// P5
		if (lotData != null && !StringUtils.isEmpty(lotData.getProperty5())) {
			sb.append(" and inv.quantInv.quant.lotData.property5 =:property5 ");
			params.add("property5");
			param.add(lotData.getProperty5());
		} else {
			sb.append(" and inv.quantInv.quant.lotData.property5 is null ");
		}

		// P6
		if (lotData != null && !StringUtils.isEmpty(lotData.getProperty6())) {
			sb.append(" and inv.quantInv.quant.lotData.property6 =:property6 ");
			params.add("property6");
			param.add(lotData.getProperty6());
		} else {
			sb.append(" and inv.quantInv.quant.lotData.property6 is null ");
		}

		// P7
		if (lotData != null && !StringUtils.isEmpty(lotData.getProperty7())) {
			sb.append(" and inv.quantInv.quant.lotData.property7 =:property7 ");
			params.add("property7");
			param.add(lotData.getProperty7());
		} else {
			sb.append(" and inv.quantInv.quant.lotData.property7 is null ");
		}

		// P8
		if (lotData != null && !StringUtils.isEmpty(lotData.getProperty8())) {
			sb.append(" and inv.quantInv.quant.lotData.property8 =:property8 ");
			params.add("property8");
			param.add(lotData.getProperty8());
		} else {
			sb.append(" and inv.quantInv.quant.lotData.property8 is null ");
		}

		// P9
		if (lotData != null && !StringUtils.isEmpty(lotData.getProperty9())) {
			sb.append(" and inv.quantInv.quant.lotData.property9 =:property9 ");
			params.add("property9");
			param.add(lotData.getProperty9());
		} else {
			sb.append(" and inv.quantInv.quant.lotData.property9 is null ");
		}

		// P10
		if (lotData != null && !StringUtils.isEmpty(lotData.getProperty10())) {
			sb.append(" and inv.quantInv.quant.lotData.property10 =:property10 ");
			params.add("property10");
			param.add(lotData.getProperty10());
		} else {
			sb.append(" and inv.quantInv.quant.lotData.property10 is null ");
		}

		sb.append(" order by inv.inboundDate ");
		List<Inventory> invs = commonDao.findByQuery(sb.toString(),
				params.toArray(new String[params.size()]),
				param.toArray(new Object[param.size()]));

		return invs;
	}

	@Override
	public boolean deleteInv(Inventory inv) {
		boolean isDeletePalletFromBin = false;
		
		// 删除0库存
		if (inv.getBaseQty() == 0 && inv.getQueuedQty() == 0) {
//			String taskHql = " select wt.id from WarehouseTask wt where wt.invId = :invId and wt.status <> 700 and wt.planQty > wt.executeQty ";
//			List<Long> wtIds = commonDao.findByQuery(taskHql, "invId", inv.getId());
//			// 还有待作业的WT指向这个INV的ID，不能删除。
//			if( wtIds != null && wtIds.size() > 0 )
//				return isDeletePalletFromBin;
			
			refLastStoreInfo(inv);
			commonDao.delete(inv);
			
			// 检查ContainerInv
			if( inv.getContainer() != null ){
				String hql = " select inv.id from Inventory inv where inv.container.id = :containerId ";
				List<Long> ids = commonDao.findByQuery(hql, "containerId", inv.getContainer().getId());
				if( ids == null || ids.size() == 0 ){
					isDeletePalletFromBin = true;
					commonDao.delete(inv.getContainer());
				}
			}
		}
		
		return isDeletePalletFromBin;
	}

	@Override
	public void checkBinMix(Long binId, Owner owner, Quant quant) {
		Bin bin = commonDao.load(Bin.class, binId);
		
		// 都可以混放
		if( bin.getProcessProperties().getOwnerMixed() 
				&& bin.getProcessProperties().getSkuMixed() 
				&& bin.getProcessProperties().getLotMixed()  )
			return;
		
		String hql = " select inv.id from Inventory inv where inv.bin.id = :binId";
		List<Long> invIds = commonDao.findByQuery(hql, "binId", binId);
		// 空库位，混放无限制
		if (invIds == null || invIds.size() == 0) {
			return;
		}		
		Inventory inv = commonDao.load(Inventory.class, invIds.get(0));
		Long lastOwnerId = inv.getOwner().getId();
		Long lastSkuId = inv.getQuantInv().getQuant().getId();
		
		// 货主混放
		if( bin.getProcessProperties().getOwnerMixed() == null
				|| !bin.getProcessProperties().getOwnerMixed().booleanValue() ){
			if (!lastOwnerId.equals(owner.getId())) {
				throw new BusinessException(ExceptionConstant.CANNOT_MIX_OWNER);
			}
		}

		// SKU混放过滤 ( skuMix = true,表示可以混放，其他情况都不能混放 )
		if (bin.getProcessProperties().getSkuMixed() == null
				|| !bin.getProcessProperties().getSkuMixed().booleanValue()) {
			if (!lastSkuId.equals(quant.getSku().getId())) {
				throw new BusinessException(ExceptionConstant.CANNOT_MIX_SKU);
			}
		}

		// LOT混放过滤 ( lotMix = true,表示可以混放，其他情况都不能混放 )
		if (bin.getProcessProperties().getLotMixed() == null
				|| !bin.getProcessProperties().getLotMixed().booleanValue()) {
			// 如果这个库位上有同种商品，并且Lot不一致，报错
			String hql2 = "select inv.id from Inventory inv where "
					+ "inv.bin.id = :binId and inv.quantInv.quant.sku.id = :skuId and inv.quantInv.quant.dispLot <> :dispLot";
			List<Long> lotInvs = commonDao.findByQuery(hql2, new String[]{"binId", "skuId", "dispLot"}, new Object[]{binId, quant.getSku().getId(), quant.getDispLot()});
			if( lotInvs != null && lotInvs.size() > 0 ){
				throw new BusinessException(ExceptionConstant.CANNOT_MIX_LOT);
			}
		}
	}
	
	
}
