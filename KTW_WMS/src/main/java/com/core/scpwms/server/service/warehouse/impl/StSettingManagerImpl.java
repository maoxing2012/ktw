package com.core.scpwms.server.service.warehouse.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.warehouse.StorageType;
import com.core.scpwms.server.service.warehouse.BinGroupManager;
import com.core.scpwms.server.service.warehouse.BinSettingManager;
import com.core.scpwms.server.service.warehouse.StSettingManager;

/**
 * 
 * <p>
 * 功能区设定实现类。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 陈 <br/>
 *         修改履历 <br/>
 *         2013/01/31 : MBP 陈: 初版创建<br/>
 */
public class StSettingManagerImpl extends DefaultBaseManager implements
		StSettingManager {
	private BinSettingManager binSettingManager;

	private BinGroupManager binGroupManager;

	public BinSettingManager getBinSettingManager() {
		return this.binSettingManager;
	}

	public void setBinSettingManager(BinSettingManager binSettingManager) {
		this.binSettingManager = binSettingManager;
	}

	public BinGroupManager getBinGroupManager() {
		return this.binGroupManager;
	}

	public void setBinGroupManager(BinGroupManager binGroupManager) {
		this.binGroupManager = binGroupManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.StSettingManager#saveST(com.
	 * core.scpwms.server.model.warehouse.StorageType)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveST(StorageType storageType) {
		if (storageType.isNew()) {
			// 判断是否重复记录
			StringBuffer hql = new StringBuffer(
					"from StorageType st where lower(st.code) = :stCode and st.wa.wh=:whId");
			List<StorageType> sts = commonDao.findByQuery(hql.toString(),
					new String[] { "stCode", "whId" }, new Object[] {
							storageType.getCode().toLowerCase(),
							WarehouseHolder.getWarehouse() });
			if (!sts.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}
			storageType.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		} else {
			storageType.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		this.getCommonDao().store(storageType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.StSettingManager#enableST(java
	 * .util.List)
	 */
	@Override
	public void enableST(List<Long> ids) {
		for (Long id : ids) {
			StorageType dbObj = this.getCommonDao().load(StorageType.class, id);
			dbObj.setDisabled(Boolean.FALSE);
			dbObj.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.getCommonDao().store(dbObj);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.StSettingManager#disabledST(
	 * java.util.List)
	 */
	@Override
	public void disabledST(List<Long> ids) {
		for (Long id : ids) {
			StorageType dbObj = this.getCommonDao().load(StorageType.class, id);
			dbObj.setDisabled(Boolean.TRUE);
			dbObj.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.getCommonDao().store(dbObj);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.StSettingManager#deleteST(java
	 * .util.List)
	 */
	@Override
	public void deleteST(List<Long> ids) {
		for (Long id : ids) {
			// 删除下属的库位
			List<Long> binIds = commonDao
					.findByQuery(
							"select bin.id from Bin bin where bin.storageType.id = :stId",
							"stId", id);
			if (binIds != null && binIds.size() > 0) {
				binSettingManager.deleteBin(binIds);
			}

			this.commonDao.delete(commonDao.load(StorageType.class, id));
		}
	}

}
