package com.core.scpwms.server.service.setting.impl;

import java.util.ArrayList;
import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.model.common.ReasonCode;
import com.core.scpwms.server.service.setting.ReasonCodeManager;

/**
 * 
 * @description 原因码设定
 * @author      曹国良@mbp
 * @createDate  2014-8-7
 * @version     V1.0
 *
 */
public class ReasonCodeManagerImpl extends DefaultBaseManager implements ReasonCodeManager {

	@SuppressWarnings("unchecked")
	@Override
	public void saveReasonCode(ReasonCode reasonCode) {
		// TODO Auto-generated method stub
		if(reasonCode.isNew()){
			
			StringBuffer hql = new StringBuffer("from ReasonCode rea where lower(rea.code) = :reaCode");
			
			List<ReasonCode> reasonList = commonDao.findByQuery(hql.toString(), 
					new String[] { "reaCode" }, 
					new Object[] { reasonCode.getCode().toLowerCase() });
			
			if(!reasonList.isEmpty()){
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}
			
			reasonCode.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		}else{
			reasonCode.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		commonDao.store(reasonCode);
	}

	@Override
	public void deleteReasonCode(List<Long> ids) {
		// TODO Auto-generated method stub
		 for (Long id : ids) {
			 delete(load(ReasonCode.class, id));
	     }
	}

	@Override
	public void enableReasonCode(List<Long> ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			ReasonCode reasonCode = load(ReasonCode.class, id);
			reasonCode.setDisabled(Boolean.FALSE);
			reasonCode.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            commonDao.store(reasonCode);
        }
	}

	@Override
	public void disableReasonCode(List<Long> ids) {
		// TODO Auto-generated method stub
		for (Long id : ids) {
			ReasonCode reasonCode = load(ReasonCode.class, id);
			reasonCode.setDisabled(Boolean.TRUE);
			reasonCode.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            commonDao.store(reasonCode);
        }
	}

	@Override
	public String getReasonCodeStr() {
		// 原因码
		String hql1 = " from ReasonCode rc where rc.disabled <> 1 order by rc.code";
		List<ReasonCode> rcs = commonDao.findByQuery(hql1);//入库不良
		StringBuffer reasonCode = new StringBuffer();
		
		// 良品
		for( ReasonCode rc : rcs ){
			if( reasonCode.length() > 0 ){
				reasonCode.append(" ");
			}
			
			reasonCode.append(rc.getCode());
			reasonCode.append(":");
			reasonCode.append(rc.getName());
		}
		
		return reasonCode.toString();
	}

	@Override
	public String getReasonCodeStr(String type) {
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		
		// 原因码
		StringBuffer hql1 = new StringBuffer( "from ReasonCode rc where 1=1 " );
		if( type != null ){
			hql1.append(" and rc.type = :rcType ");
			paramNames.add("rcType");
			params.add(type);
		}
		hql1.append(" and rc.disabled <> 1 order by rc.code");
		List<ReasonCode> rcs = commonDao.findByQuery(hql1.toString(), 
				paramNames.toArray(new String[paramNames.size()]), 
				params.toArray(new Object[params.size()]));
		StringBuffer reasonCode = new StringBuffer();
		if( rcs != null && rcs.size() > 0 ){
			for( ReasonCode rc : rcs ){
				if( reasonCode.length() > 0 ){
					reasonCode.append(" ");
				}
				
				reasonCode.append(rc.getCode());
				reasonCode.append(":");
				reasonCode.append(rc.getName());
			}
		}
		
		return reasonCode.toString();
	}

}
