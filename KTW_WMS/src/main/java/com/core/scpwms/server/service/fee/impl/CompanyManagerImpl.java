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
import com.core.scpwms.server.model.fee.Company;
import com.core.scpwms.server.service.fee.CompanyManager;

public class CompanyManagerImpl extends DefaultBaseManager implements CompanyManager {

	@Override
	public void store(Company company) {
		if( company.isNew() ){
			company.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		}
		
		company.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		commonDao.store(company);
	}

	@Override
	public void delete(List<Long> companyIds) {
		for( Long cpId : companyIds ){
			String hql = "select rrh.id from RequestReportHead  rrh where rrh.company.id = :cpId";
			List<Long> rrhIdsList = commonDao.findByQuery(hql, "cpId", cpId);
			if( rrhIdsList != null && rrhIdsList.size() > 0 ){
				throw new BusinessException(ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}
			
			commonDao.delete(commonDao.load(Company.class, cpId));
		}
	}

	@Override
	public void disable(List<Long> companyIds) {
		for( Long cpId : companyIds ){
			Company cp = commonDao.load(Company.class, cpId);
			cp.setDisabled(Boolean.TRUE);
		}
	}

	@Override
	public void enable(List<Long> companyIds) {
		for( Long cpId : companyIds ){
			Company cp = commonDao.load(Company.class, cpId);
			cp.setDisabled(Boolean.FALSE);
		}
	}

}
