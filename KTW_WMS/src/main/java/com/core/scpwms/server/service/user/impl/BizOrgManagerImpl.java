/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : CustomerManagerImpl.java
 */

package com.core.scpwms.server.service.user.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuBusRolsType;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.BizOrgProperties;
import com.core.scpwms.server.service.user.BizOrgManager;

/**
 * @description 收货方信息维护
 * @author MBP:毛幸<br/>
 * @createDate 2013-2-5
 * @version V1.0<br/>
 */
@SuppressWarnings("all")
public class BizOrgManagerImpl extends DefaultBaseManager implements BizOrgManager {

	@Override
	public void customerPropertiesetting(BizOrg customer, Long id) {
		BizOrgProperties properties = customer.getBizOrgProperties();
		if (properties.isNew()) {
			properties
					.setCreateInfo(new CreateInfo(new UserHolder().getUser()));
			properties.setCustomer(customer);
			this.commonDao.store(properties);
			this.commonDao.store(customer);
		} else {
			properties.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.commonDao.store(properties);
		}
	}

	@Override
	public void disableBizOrg(List<Long> ids) {
		for (Long id : ids) {
			BizOrg customer = load(BizOrg.class, id);
			customer.setDisabled(Boolean.TRUE);
			commonDao.store(customer);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.user.BizOrgManager#enableBizOrg(java.util
	 * .List)
	 */
	@Override
	public void enableBizOrg(List<Long> ids) {
		for (Long id : ids) {
			BizOrg customer = load(BizOrg.class, id);
			customer.setDisabled(Boolean.FALSE);
			commonDao.store(customer);
		}
	}

	@Override
	public void saveBizOrg(BizOrg bizOrg) {
		if (bizOrg.isNew()) {
			// 判断是否重复记录
			StringBuffer hql = new StringBuffer(
					"from BizOrg bizOrg where lower(bizOrg.code) = :bizOrgCode and bizOrg.plant.id = :plantId and bizOrg.type = :type");

			List<BizOrg> customerList = commonDao.findByQuery(hql.toString(),
					new String[] { "bizOrgCode", "plantId", "type" }, new Object[] { bizOrg.getCode().toLowerCase(), bizOrg.getPlant().getId(), bizOrg.getType() });
			
			if (!customerList.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}
			bizOrg.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		} else {
			bizOrg.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		commonDao.store(bizOrg);
	}

	@Override
	public void deleteBizOrg(List<Long> ids) {
		for (Long id : ids) {
			// 检查有无业务数据
			List<Long> ibdIds = commonDao
					.findByQuery(
							"select ibd.id from InboundDelivery ibd where ibd.supplier.id = :boId",
							"boId", id);
			if (ibdIds != null && ibdIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			List<Long> asnIds = commonDao
					.findByQuery(
							"select asn.id from Asn asn where asn.supplier.id = :boId",
							"boId", id);
			if (asnIds != null && asnIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}
			
			List<Long> obdIds = commonDao
					.findByQuery(
							"select obd.id from OutboundDelivery obd where obd.customer.id = :boId",
							"boId", id);
			if (obdIds != null && obdIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}
			
			BizOrg bizOrg = this.commonDao.load(BizOrg.class, id);
			this.commonDao.delete(bizOrg);
		}
	}

	@Override
	public void saveVendor(BizOrg bizOrg) {
		bizOrg.setType(EnuBusRolsType.V);
		saveBizOrg(bizOrg);
	}

	@Override
	public void saveShop(BizOrg bizOrg) {
		bizOrg.setType(EnuBusRolsType.C);
		saveBizOrg(bizOrg);
	}

}
