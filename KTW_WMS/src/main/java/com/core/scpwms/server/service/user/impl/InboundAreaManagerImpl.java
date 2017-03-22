/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : PlantCustomerManagerImpl.java
 */

package com.core.scpwms.server.service.user.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.user.InboundArea;
import com.core.scpwms.server.service.user.InboundAreaManager;

/**
 * @description   收货区域维护
 * @author         MBP:Henry Lin<br/>
 * @createDate    2014/1/3
 * @version        V1.0<br/>
 */
public class InboundAreaManagerImpl extends DefaultBaseManager implements InboundAreaManager {


    @SuppressWarnings("unchecked")
	@Override
	public void saveInboundArea(InboundArea inboundArea) {
		if (inboundArea.isNew()) {
        	//判断是否重复记录
			StringBuffer hql = new StringBuffer("from InboundArea inboundArea where lower(inboundArea.code) = :inboundAreaCode");
			
			List<Carrier> inboundAreaList = commonDao.findByQuery(hql.toString(), 
					new String[]{"inboundAreaCode"}, 
					new Object[]{inboundArea.getCode().toLowerCase()});
			
			if(!inboundAreaList.isEmpty()){
				throw new BusinessException ("ExistInboundAreaCodeException");
			}
			inboundArea.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
        	inboundArea.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }
        commonDao.store(inboundArea); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public void enableInboundArea(List<Long> ids) {
		 for (Long id : ids) {
			 InboundArea inboundArea = load(InboundArea.class, id);
			 inboundArea.setDisabled(Boolean.FALSE);
	         commonDao.store(inboundArea);
	     }
	}

	@Override
	public void disabledInboundArea(List<Long> ids) {
		for (Long id : ids) {
			InboundArea inboundArea = load(InboundArea.class, id);
			inboundArea.setDisabled(Boolean.TRUE);
            commonDao.store(inboundArea);
        }
	}

	@Override
	public void deleteInboundArea(List<Long> ids) {
		for (Long id : ids) {
            delete(load(InboundArea.class, id));
        }
	}
 
}
