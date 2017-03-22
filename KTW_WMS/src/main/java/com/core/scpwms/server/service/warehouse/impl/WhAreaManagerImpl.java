/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WhAreaManagerImpl.java
 */

package com.core.scpwms.server.service.warehouse.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.db.util.LocalizedMessage;
import com.core.scpview.client.utils.LocaleUtils;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuStoreRole;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.warehouse.StorageType;
import com.core.scpwms.server.model.warehouse.WhArea;
import com.core.scpwms.server.service.warehouse.StSettingManager;
import com.core.scpwms.server.service.warehouse.WhAreaManager;
import com.core.scpwms.server.util.LocaleUtil;

/**
 * @description 库区实现类
 * @author MBP:xiaoyan<br/>
 * @createDate 2013-1-31
 * @version V1.0<br/>
 */
@SuppressWarnings("all")
public class WhAreaManagerImpl extends DefaultBaseManager implements
		WhAreaManager {

	private StSettingManager stManager;

	public StSettingManager getStManager() {
		return this.stManager;
	}

	public void setStManager(StSettingManager stManager) {
		this.stManager = stManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.WhAreaManager#active(java.util
	 * .List)
	 */
	@Override
	public void active(List<Long> whAreaIds) {
		for (Long id : whAreaIds) {
			WhArea whArea = commonDao.load(WhArea.class, id);
			whArea.setDisabled(false);
			whArea.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(whArea);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.WhAreaManager#deleteWhArea(java
	 * .util.List)
	 */
	@Override
	public void deleteWhArea(List<Long> whAreaIds) {
		for (Long id : whAreaIds) {
			// 删除下属的功能区（调用功能区的删除方法，该方法会删除功能区下属的库位及库位组）
			List<Long> stIds = commonDao
					.findByQuery(
							"select st.id from StorageType st where st.wa.id = :whAreaId",
							"whAreaId", id);
			if (stIds != null && stIds.size() > 0) {
				stManager.deleteST(stIds);
			}

			delete(load(WhArea.class, id));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.WhAreaManager#storeWhArea(com
	 * .core.scpwms.server.model.warehouse.WhArea)
	 */
	@Override
	public void storeWhArea(WhArea whArea){

		if (whArea.isNew()) {
			// 判断是否重复记录
			StringBuffer hql = new StringBuffer(
					"from WhArea wa where lower(wa.code) = :waCode and wa.wh=:whId");

			List<WhArea> was = commonDao.findByQuery(hql.toString(),
					new String[] { "waCode", "whId" },
					new Object[] { whArea.getCode().toLowerCase(),
							WarehouseHolder.getWarehouse() });

			if (!was.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}
			whArea.setWh(WarehouseHolder.getWarehouse());
			whArea.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			commonDao.store(whArea);
		} else {
			whArea.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		commonDao.store(whArea);
	}

	private void saveSt(String code, String name, String role, WhArea whArea) {
		StorageType st = new StorageType();
		st.setCode(code);
		st.setName(name);
		st.setRole(role);
		st.setWa(whArea);
		st.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		commonDao.store(st);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.WhAreaManager#unActive(java.
	 * util.List)
	 */
	@Override
	public void unActive(List<Long> whAreaIds) {
		for (Long id : whAreaIds) {
			WhArea whArea = commonDao.load(WhArea.class, id);
			whArea.setDisabled(true);
			whArea.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(whArea);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.WhAreaManager#modifyWhArea(com
	 * .core.scpwms.server.model.warehouse.WhArea)
	 */
	@Override
	public void modifyWhArea(WhArea whArea) {
		if (!whArea.isNew()) {
			whArea.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(whArea);
		}
	}
}
