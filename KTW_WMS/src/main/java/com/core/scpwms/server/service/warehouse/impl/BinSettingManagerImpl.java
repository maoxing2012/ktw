package com.core.scpwms.server.service.warehouse.impl;

import java.util.ArrayList;
import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinBinGroup;
import com.core.scpwms.server.model.warehouse.BinInvInfo;
import com.core.scpwms.server.model.warehouse.BinProperties;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.warehouse.BinSettingManager;

/**
 * <p>
 * 库位设定接口实现。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 吴 <br/>
 *         修改履历 <br/>
 *         2013/02/18 : MBP 吴: 初版创建<br/>
 */
public class BinSettingManagerImpl extends DefaultBaseManager implements
		BinSettingManager {

	private BinHelper binHelper;

	public BinHelper getBinHelper() {
		return this.binHelper;
	}

	public void setBinHelper(BinHelper binHelper) {
		this.binHelper = binHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.BinSettingManager#saveBin(com
	 * .core.scpwms.server.model.warehouse.Bin)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveBin(Bin bin) {
		if (bin.isNew()) {
			// 判断是否重复记录
			StringBuffer hql = new StringBuffer(
					"from Bin bin where lower(bin.binCode) = :binCode and bin.wh=:whId");
			List<Bin> bins = commonDao.findByQuery(hql.toString(),
					new String[] { "binCode", "whId" },
					new Object[] { bin.getBinCode().toLowerCase(),
							WarehouseHolder.getWarehouse() });
			if (!bins.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}
			bin.getProcessProperties().setSupplierMixed(Boolean.TRUE);
			bin.setWh(WarehouseHolder.getWarehouse());
			bin.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			bin.getBinInvInfo().setBin(bin);

			commonDao.store(bin.getBinInvInfo());
			commonDao.store(bin);
		} else {
			Bin oldBin = commonDao.load(Bin.class, bin.getId());
			Boolean oldLotMix = oldBin.getProcessProperties().getLotMixed() == null ? Boolean.FALSE
					: oldBin.getProcessProperties().getLotMixed();
			Boolean oldSkuMix = oldBin.getProcessProperties().getSkuMixed() == null ? Boolean.FALSE
					: oldBin.getProcessProperties().getSkuMixed();
			Boolean oldOwnerMix = oldBin.getProcessProperties()
					.getOwnerMixed() == null ? Boolean.FALSE : oldBin
					.getProcessProperties().getOwnerMixed();

			// 原来是可以混放货主的，修改为不能混放货主，需要验证该库位上存放的是不是同一货主
			if (!(oldOwnerMix.equals(bin.getProcessProperties().getOwnerMixed()))) {
				if (bin.getProcessProperties().getOwnerMixed() == null
						|| !bin.getProcessProperties().getOwnerMixed()) {
					if (!binHelper.isAllSameLot(bin.getId())) {
						throw new BusinessException(
								ExceptionConstant.CANNOT_CHANGE_TO_UNMIX_OWNER_AS_HAS_DIFFERENT);
					}
				}
			}
			
			// 原来是可以混放批次的，修改为不能混放批次，需要验证该库位上存放的是不是同一种批次
			if (!(oldLotMix.equals(bin.getProcessProperties().getLotMixed()))) {
				if (bin.getProcessProperties().getLotMixed() == null
						|| !bin.getProcessProperties().getLotMixed()) {
					if (!binHelper.isAllSameLot(bin.getId())) {
						throw new BusinessException(
								ExceptionConstant.CANNOT_CHANGE_TO_UNMIX_LOT_AS_HAS_DIFFERENT);
					}
				}
			}

			// 原来是可以混放商品的，修改为不能混放商品，需要验证该库位上存放的是不是同一种商品
			if (!(oldSkuMix.equals(bin.getProcessProperties().getSkuMixed()))) {
				if (bin.getProcessProperties().getSkuMixed() == null
						|| !bin.getProcessProperties().getSkuMixed()) {
					if (!binHelper.isAllSameSku(bin.getId())) {
						throw new BusinessException(
								ExceptionConstant.CANNOT_CHANGE_TO_UNMIX_SKU_AS_HAS_DIFFERENT);
					}
				}
			}
			
			oldBin.setTemperatureDiv(bin.getTemperatureDiv());
			oldBin.setAisle(bin.getAisle());
			oldBin.setBarCode(bin.getBarCode());
			oldBin.setBinCode(bin.getBinCode());
			oldBin.setDepth(bin.getDepth());
			oldBin.setDescription(bin.getDescription());
			oldBin.setLevel(bin.getLevel());
			oldBin.setPutawayIndex(bin.getPutawayIndex());
			oldBin.setSortIndex(bin.getSortIndex());
			oldBin.setStack(bin.getStack());
			oldBin.setStorageType(bin.getStorageType());
			oldBin.getProcessProperties().setOwnerMixed(
					bin.getProcessProperties().getOwnerMixed());
			oldBin.getProcessProperties().setSkuMixed(
					bin.getProcessProperties().getSkuMixed());
			oldBin.getProcessProperties().setLotMixed(
					bin.getProcessProperties().getLotMixed());
			oldBin.getProcessProperties().setSupplierMixed(
					bin.getProcessProperties().getSupplierMixed());
			oldBin.getProcessProperties().setIsDps(
					bin.getProcessProperties().getIsDps());
			BinProperties bp = commonDao.load(BinProperties.class, bin
					.getProperties().getId());
			oldBin.setProperties(bp);
			oldBin.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(oldBin);
		}

		// 需要重新刷一下库容信息
//		binHelper.refreshBinInvInfo(bin.getId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.BinSettingManager#enableBin(
	 * java.util.List)
	 */
	@Override
	public void enableBin(List<Long> ids) {
		for (Long id : ids) {
			Bin bin = this.commonDao.load(Bin.class, id);
			bin.setDisabled(Boolean.FALSE);
			bin.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.commonDao.store(bin);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.BinSettingManager#disableBin
	 * (java.util.List)
	 */
	@Override
	public void disableBin(List<Long> ids) {
		for (Long id : ids) {
			Bin bin = this.commonDao.load(Bin.class, id);
			bin.setDisabled(Boolean.TRUE);
			bin.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.commonDao.store(bin);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.BinSettingManager#lockBin(java
	 * .lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void lockBin(String lockStatus, String lockReason, List<Long> ids) {
		for (Long id : ids) {
			Bin bin = this.commonDao.load(Bin.class, id);
			bin.setLockStatus(lockStatus);
			bin.setLockReason(lockReason);
			bin.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.commonDao.store(bin);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.BinSettingManager#unlockBin(
	 * java.util.List)
	 */
	@Override
	public void unlockBin(List<Long> ids) {
		for (Long id : ids) {
			Bin bin = this.commonDao.load(Bin.class, id);
			bin.setLockStatus(null);
			bin.setLockReason(null);
			bin.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.commonDao.store(bin);
		}

	}

	/**
	 * <p>
	 * 批量创建库位。
	 * </p>
	 * 
	 * @param aisleFrom
	 *            起始道
	 * @param aisleTo
	 *            截止道
	 * @param stackFrom
	 *            起始间
	 * @param stackTo
	 *            截止间
	 * @param depthFrom
	 *            起始列
	 * @param depthTo
	 *            截止列
	 * @param levelFrom
	 *            起始层
	 * @param levelTo
	 *            截止层
	 * @param bin
	 *            库位实例
	 * @param prefix
	 *            编码前缀
	 * @param aisleCount
	 *            道编码位数
	 * @param aisleSeparator
	 *            道编码分隔符
	 * @param stackCount
	 *            开间编码位数
	 * @param stackString
	 *            开间编码分隔符
	 * @param depthCount
	 *            列编码位数
	 * @param depthString
	 *            列编码分隔符
	 * @param levelCount
	 *            层编码位数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void createBins(Integer aisleFrom, Integer aisleTo,
			Integer stackFrom, Integer stackTo, Integer depthFrom,
			Integer depthTo, Integer levelFrom, Integer levelTo, Bin bin,
			String prefix, Integer aisleCount, String aisleString,
			Integer stackCount, String stackString, Integer depthCount,
			String depthString, Integer levelCount) {

		// 计算库位
		Integer aisle = aisleFrom;
		Integer stack = stackFrom;
		Integer depth = depthFrom;
		Integer level = levelFrom;

		if (aisleFrom > aisleTo || stackFrom > stackTo || depthFrom > depthTo
				|| levelFrom > levelTo) {
			throw new BusinessException("FromOvertakeTo");
		}

		while (aisle <= aisleTo) {

			stack = stackFrom;
			while (stack <= stackTo) {

				depth = depthFrom;
				while (depth <= depthTo) {

					level = levelFrom;
					while (level <= levelTo) {

						// 生成编码
						String c1 = "", c2 = "", c3 = "", c4 = "", code = "";
						if (aisleCount > 0) {
							c1 = String
									.format("%1$0" + aisleCount + "d", aisle);
							code = code + c1 + aisleString;
						}
						if (stackCount > 0) {
							c2 = String
									.format("%1$0" + stackCount + "d", stack);
							code = code + c2 + stackString;
						}
						if (depthCount > 0) {
							c3 = String
									.format("%1$0" + depthCount + "d", depth);
							code = code + c3 + depthString;
						}
						if (levelCount > 0) {
							c4 = String
									.format("%1$0" + levelCount + "d", level);
							code += c4;
						}

						// 检查该库位是否已存在
						String binCode = prefix + code;

						StringBuffer hql = new StringBuffer(
								"from Bin bin where lower(bin.binCode) = :binCode and bin.wh=:whId");
						List<Bin> bins = commonDao.findByQuery(hql.toString(),
								new String[] { "binCode", "whId" },
								new Object[] { binCode.toLowerCase(),
										WarehouseHolder.getWarehouse() });
						if (bins != null && bins.size() > 0) {
							continue;
						}

						Bin newBin = new Bin();

						newBin.setWh(WarehouseHolder.getWarehouse());
						newBin.setBinCode(binCode);
						newBin.setAisle(aisle);
						newBin.setStack(stack);
						newBin.setDepth(depth);
						newBin.setLevel(level);
						newBin.setStorageType(bin.getStorageType());
						newBin.setProperties(bin.getProperties());
						newBin.setBarCode(bin.getBarCode());
						newBin.setDescription(bin.getDescription());
						newBin.setProcessProperties(bin.getProcessProperties());
						newBin.setDisabled(true);
						newBin.setLockStatus(null);
						newBin.setDisabled(Boolean.FALSE);
						newBin.setBinInvInfo(new BinInvInfo());

						newBin.getBinInvInfo().setBin(newBin);
						commonDao.store(newBin.getBinInvInfo());
						commonDao.store(newBin);

						level++;
					}
					depth++;
				}
				stack++;
			}
			aisle++;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.BinSettingManager#deleteBin(
	 * java.util.List)
	 */
	@Override
	public void deleteBin(List<Long> ids) {
		for (Long id : ids) {
			Bin bin = commonDao.load(Bin.class, id);

			// 电子标签库位
			if (bin.getProcessProperties().getIsDps() != null
					&& bin.getProcessProperties().getIsDps().booleanValue()) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_BIN_AS_DPS,
						new String[] { bin.getBinCode() });
			}

			// 是否有加入策略
			String hql2 = "select pur.id from PickUpRule pur where pur.descBin.id =:binId or pur.srcBin.id =:binId";
			List<Long> pickRuleIds = commonDao.findByQuery(hql2, "binId", id);
			if (pickRuleIds != null && pickRuleIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_BIN_AS_ADD_RULE,
						new String[] { bin.getBinCode() });
			}

			String hql3 = "select pur.id from PutAwayRule pur where pur.descBin.id =:binId or pur.srcBin.id =:binId";
			List<Long> putawayRuleIds = commonDao
					.findByQuery(hql3, "binId", id);
			if (putawayRuleIds != null && putawayRuleIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_BIN_AS_ADD_RULE,
						new String[] { bin.getBinCode() });
			}

			// 是否有InventoryHistory
			String hql4 = "select ih.id from InventoryHistory ih where ih.invInfo.bin.id =:binId";
			List<Long> historyIds = commonDao.findByQuery(hql4, "binId", id);
			if (historyIds != null && historyIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_BIN_AS_HAS_HISTORY,
						new String[] { bin.getBinCode() });
			}
			
			// 删除库位组和库位的绑定
			List<BinBinGroup> bbgs = commonDao.findByQuery("from BinBinGroup bbg where bbg.bin.id = :binId", "binId", id);
			if( bbgs != null && bbgs.size() > 0 ){
				for( BinBinGroup bbg : bbgs ){
					commonDao.delete(bbg);
				}
			}

			bin.getBinInvInfo().setBin(null);
			this.delete(bin.getBinInvInfo());
			this.delete(bin);
		}

	}

	@Override
	public List<String[]> refBinInfo(String whCode) {
		// 刷新最后存放批次，最后存放商品信息
		String hql = " select bin.id from Bin bin where (bin.wh.code =:whCode or :whCode is null ) and bin.properties.isLogicBin <> 1 and bin.storageType.role in ('R3000','R4040') ";

		List<String[]> resultList = new ArrayList<String[]>();
		List<Long> binIds = commonDao.findByQuery(hql, "whCode", whCode);

		if (binIds != null && binIds.size() > 0) {
			for (Long id : binIds) {
				String[] result = binHelper.refBinInfo(id);

				if (result != null) {
					resultList.add(result);
				}
			}
		}
		return resultList;
	}

	@Override
	public Bin autoCreateBin(Long whId, String stType) {
		// 逻辑库位属性
		String hql = " select bp.id from BinProperties bp where bp.wh.id = :whId and bd.disabled <> 1 and bd.isLogicBin = 1 ";
		List<Long> bpIds = commonDao.findByQuery(hql, "whId", whId);
		if( bpIds == null || bpIds.size() == 0 ){
			throw new BusinessException(ExceptionConstant.CANNOT_FIND_LOGIC_BIN_PROPERTIES);
		}
		
		BinProperties bp = commonDao.load(BinProperties.class, bpIds.get(0));
		
		Bin bin = new Bin();
		Warehouse wh = new Warehouse();
		wh.setId(whId);
		bin.setWh(wh);
		
		// TODO
		
		return null;
	}
}
