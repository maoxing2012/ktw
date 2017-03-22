/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WhAreaManagerImpl.java
 */

package com.core.scpwms.server.service.fee.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.fee.FeeType;
import com.core.scpwms.server.service.fee.FeeTypeManager;

public class FeeTypeManagerImpl extends DefaultBaseManager implements FeeTypeManager {

	@Override
	public void store(FeeType feeType) {
		if( feeType.isNew() ){
			feeType.setWh(WarehouseHolder.getWarehouse());
			feeType.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		}
		
		feeType.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		commonDao.store(feeType);
	}

	@Override
	public void delete(List<Long> feeTypeIds) {
		for( Long feeTypeId : feeTypeIds ){
			String hql = "select rrd.id from RequestReportDetail rrd where rrd.feeType.id = :feeTypeId";
			List<Long> rrhIdsList = commonDao.findByQuery(hql, "feeTypeId", feeTypeId);
			if( rrhIdsList != null && rrhIdsList.size() > 0 ){
				throw new BusinessException(ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}
			
			commonDao.delete(commonDao.load(FeeType.class, feeTypeId));
		}
	}

	@Override
	public void disable(List<Long> feeTypeIds) {
		for( Long feeTypeId : feeTypeIds ){
			FeeType feeType = commonDao.load(FeeType.class, feeTypeId);
			feeType.setDisabled(Boolean.TRUE);
		}
	}

	@Override
	public void enable(List<Long> feeTypeIds) {
		for( Long feeTypeId : feeTypeIds ){
			FeeType feeType = commonDao.load(FeeType.class, feeTypeId);
			feeType.setDisabled(Boolean.FALSE);
		}
	}

}
