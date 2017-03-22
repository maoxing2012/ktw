/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : PlantManagerImpl.java
 */

package com.core.scpwms.server.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuInvProcessType;
import com.core.scpwms.server.enumerate.EnuSeqDateFormat;
import com.core.scpwms.server.enumerate.EnuSeqGenerateType;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.SequenceProperties;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.user.PlantInBountProperties;
import com.core.scpwms.server.model.user.PlantOutBountProperties;
import com.core.scpwms.server.model.user.PlantWarehousingProperties;
import com.core.scpwms.server.service.setting.OtSettingManager;
import com.core.scpwms.server.service.sku.SkuManager;
import com.core.scpwms.server.service.user.OwnerManager;
import com.core.scpwms.server.service.user.PlantManager;

/**
 * @description 货主设定
 * @author MBP:xiaoyan<br/>
 * @createDate 2013-2-1
 * @version V1.0<br/>
 */
@SuppressWarnings("all")
public class PlantManagerImpl extends DefaultBaseManager implements
		PlantManager {
	private SkuManager skuManager;
	private OwnerManager ownerManager;

	public SkuManager getSkuManager() {
		return this.skuManager;
	}

	public void setSkuManager(SkuManager skuManager) {
		this.skuManager = skuManager;
	}

	public OwnerManager getOwnerManager() {
		return this.ownerManager;
	}

	public void setOwnerManager(OwnerManager ownerManager) {
		this.ownerManager = ownerManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.user.PlantManager#active(java.util.List)
	 */
	@Override
	public void active(List<Long> plantIds) {
		for (Long id : plantIds) {
			Plant plant = load(Plant.class, id);
			plant.setDisabled(false);
			commonDao.store(plant);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.user.PlantManager#deletePlant(java.util
	 * .List)
	 */
	@Override
	public void deletePlant(List<Long> plantIds) {
		for (Long id : plantIds) {
			// 删除下属所以商品
			List<Long> skuIds = commonDao
					.findByQuery(
							" select sku.id from Sku sku where sku.plant.id = :plantId ",
							"plantId", id);
			if (skuIds != null && skuIds.size() > 0) {
				skuManager.deleteSku(skuIds);
			}
			
			// 删除下属所以的分布场所
			List<Long> ownerIds = commonDao
					.findByQuery(
							" select owner.id from Owner owner where owner.plant.id = :plantId ",
							"plantId", id);
			if (ownerIds != null && ownerIds.size() > 0) {
				ownerManager.deleteOwner(ownerIds);
			}

			delete(load(Plant.class, id));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.user.PlantManager#savePlant(com.core.scpwms
	 * .server.model.user.Plant)
	 */
	@Override
	public void savePlant(Plant plant) {
		if (plant.isNew()) {
			String hsql = "from Plant plant where lower(plant.code)=:plantCode and plant.wh.id = :whId";
			List<Plant> plantList = this.commonDao.findByQuery(hsql,
					new String[] { "plantCode", "whId" }, new Object[] { plant.getCode().toLowerCase(), WarehouseHolder.getWarehouse().getId() });
			if (plantList.size() > 0) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}
			plant.setWh(WarehouseHolder.getWarehouse());
			plant.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			commonDao.store(plant);
		} else {
			plant.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(plant);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.user.PlantManager#unActive(java.util.List)
	 */
	@Override
	public void unActive(List<Long> plantIds) {
		for (Long id : plantIds) {
			Plant plant = load(Plant.class, id);
			plant.setDisabled(true);
			commonDao.store(plant);
		}

	}
}
