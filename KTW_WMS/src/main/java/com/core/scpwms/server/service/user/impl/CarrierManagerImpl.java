/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : CarrierManagerImpl.java
 */

package com.core.scpwms.server.service.user.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.model.security.User;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinGroup;
import com.core.scpwms.server.model.warehouse.CarrierBinGroup;
import com.core.scpwms.server.service.user.BizOrgManager;
import com.core.scpwms.server.service.user.CarrierManager;

/**
 * @description 承运商设定
 * @author MBP:毛幸<br/>
 * @createDate 2013-2-5
 * @version V1.0<br/>
 */
@SuppressWarnings("all")
public class CarrierManagerImpl extends DefaultBaseManager implements CarrierManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.user.CarrierManager#deleteCarrier(java
	 * .util.List)
	 */
	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			// 入库单
			List<Long> ibdIds = commonDao
					.findByQuery(
							"select ibd.id from InboundDelivery ibd where ibd.carrier.id = :carrierId",
							"carrierId", id);
			if (ibdIds != null && ibdIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			// Asn
			List<Long> asnIds = commonDao
					.findByQuery(
							"select asn.id from Asn asn where asn.carrier.id = :carrierId",
							"carrierId", id);
			if (asnIds != null && asnIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}
			
			// 出库单
			List<Long> obdIds = commonDao
					.findByQuery(
							"select obd.id from OutboundDelivery obd where obd.carrier.id = :carrierId",
							"carrierId", id);
			if (obdIds != null && obdIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			delete(load(Carrier.class, id));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.user.CarrierManager#disableCarrier(java
	 * .util.List)
	 */
	@Override
	public void disable(List<Long> ids) {
		for (Long id : ids) {
			Carrier carrier = load(Carrier.class, id);
			carrier.setDisabled(Boolean.TRUE);
			commonDao.store(carrier);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.user.CarrierManager#enableCarrier(java
	 * .util.List)
	 */
	@Override
	public void enable(List<Long> ids) {
		for (Long id : ids) {
			Carrier carrier = load(Carrier.class, id);
			carrier.setDisabled(Boolean.FALSE);
			commonDao.store(carrier);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.user.CarrierManager#saveCarrier(com.core
	 * .scpwms.server.model.user.Carrier)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void save(Carrier carrier) {

		if (carrier.isNew()) {
			// 判断是否重复记录
			StringBuffer hql = new StringBuffer(
					"from Carrier carrier where lower(carrier.code) = :carrierCode and carrier.wh.id = :whId");

			List<Carrier> carrierList = commonDao.findByQuery(hql.toString(),
					new String[] { "carrierCode", "whId" }, new Object[] { carrier.getCode().toLowerCase(), WarehouseHolder.getWarehouse().getId() });

			if (!carrierList.isEmpty()) {
				throw new BusinessException("ExistCarrierCodeException");
			}
			carrier.setWh(WarehouseHolder.getWarehouse());
			carrier.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		} else {
			carrier.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		commonDao.store(carrier);
	}

}
