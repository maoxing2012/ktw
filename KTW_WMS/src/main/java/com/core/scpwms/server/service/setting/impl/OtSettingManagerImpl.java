package com.core.scpwms.server.service.setting.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.service.setting.OtSettingManager;

/**
 * 订单类型
 * @author mousachi
 *
 */
public class OtSettingManagerImpl extends DefaultBaseManager implements
		OtSettingManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.OtSettingManager#disabledOt(java
	 * .util.List)
	 */
	@Override
	public void disabledOt(List<Long> ids) {
		for (Long id : ids) {
			OrderType ot = load(OrderType.class, id);
			ot.setDisabled(Boolean.TRUE);
			ot.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.getCommonDao().store(ot);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.OtSettingManager#enableOt(java
	 * .util.List)
	 */
	@Override
	public void enableOt(List<Long> ids) {
		for (Long id : ids) {
			OrderType ot = load(OrderType.class, id);
			ot.setDisabled(Boolean.FALSE);
			ot.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.getCommonDao().store(ot);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.OtSettingManager#saveOt(com.core
	 * .scpwms.server.model.common.OrderType)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveOt(OrderType ot) {
		if (ot.isNew()) {
			StringBuffer hql = new StringBuffer("from OrderType lot where lower(lot.code) = :lotCode");
			List<OrderType> lotList = commonDao.findByQuery(hql.toString(), "lotCode", ot.getCode().toLowerCase());

			if (!lotList.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}
			ot.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		} else {
			ot.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		this.getCommonDao().store(ot);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.OtSettingManager#deleteOt(java
	 * .util.List)
	 */
	@Override
	public void deleteOt(List<Long> ids) {
		for (Long id : ids) {
			// 履历
			List<Long> hisIds = commonDao
					.findByQuery(
							"select id from InventoryHistory ih where ih.iot.id = :otId",
							"otId", id);
			if (hisIds != null && hisIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			// 入库预订
			List<Long> inboundIds = commonDao
					.findByQuery(
							"select ibd.id from InboundDelivery ibd where ibd.orderType.id = :otId",
							"otId", id);
			if (inboundIds != null && inboundIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			// 出库预订
			List<Long> outboundIds = commonDao
					.findByQuery(
							"select obd.id from OutboundDelivery obd where obd.orderType.id = :otId",
							"otId", id);
			if (outboundIds != null && outboundIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			// 盘点单
			List<Long> countIds = commonDao
					.findByQuery(
							"select cp.id from CountPlan cp where cp.orderType.id = :otId",
							"otId", id);
			if (countIds != null && countIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			// 策略
			List<Long> parIds = commonDao
					.findByQuery(
							"select par.id from PutAwayRule par where par.ot.id = :otId",
							"otId", id);
			if (parIds != null && parIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}
			
			List<Long> purIds = commonDao
					.findByQuery(
							"select pur.id from PickUpRule pur where pur.iot.id = :otId",
							"otId", id);
			if (purIds != null && purIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}
			
			this.delete(commonDao.load(OrderType.class, id));
		}
	}

}
