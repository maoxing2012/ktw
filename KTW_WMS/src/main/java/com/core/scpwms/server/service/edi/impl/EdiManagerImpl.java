package com.core.scpwms.server.service.edi.impl;

import java.util.List;

import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.model.security.User;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.model.edi.SoapInfo;
import com.core.scpwms.server.service.edi.EdiManager;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.LocaleUtil;

public class EdiManagerImpl  extends DefaultBaseManager implements EdiManager  {

	@Override
	public Long save(SoapInfo wsRequest) {
		if( wsRequest.isNew() ){
			wsRequest.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		}
		wsRequest.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		commonDao.store(wsRequest);
		
		return wsRequest.getId();
	}

	@Override
	public void delete(List<Long> ids) {
		for( Long id : ids ){
			commonDao.delete(commonDao.load(SoapInfo.class, id));
		}
	}

	@Override
	public String isValidEdiRequest(String whCd, String userName,
			String password) {
		// 是否有该仓库
		String hql1 = " select wh.id from Warehouse wh where wh.code =:whCd and wh.disabled <> true ";
		List<Long> whIds = commonDao.findByQuery(hql1, "whCd", whCd);
		if( whIds == null || whIds.size() == 0 || whIds.size()> 1){
			return LocaleUtil.getText(ExceptionConstant.CANNOT_FIND_WH) + whCd;
		}
		
		// 是否有该用户
		String hql2 = " select user.id from User user where user.loginName = :userName and user.enabled = true and user.locked <> true";
		List<Long> userIds = commonDao.findByQuery(hql2, "userName", userName);
		if( userIds == null || userIds.size() == 0 || userIds.size() > 1){
			return LocaleUtil.getText(ExceptionConstant.CANNOT_FIND_USER) + userName;
		}
		
		// 密码是否正确
		if( password == null ){
			return LocaleUtil.getText(ExceptionConstant.EMPTY_PASSWORD);
		}
		
		User user = commonDao.load(User.class, userIds.get(0));
		
		if( user.getExpiryDate() != null ){
			//TODO
		}
		
		if( !password.equals(user.getPassword()) ){
			return LocaleUtil.getText(ExceptionConstant.WRONG_PASSWORD);
		}
		
		// 该用户是否和该仓库有绑定关系
		String hql3 = "select ugw.id from UserGroupWarehouse ugw where ugw.warehouse.id = :whId and ugw.userGroup.id in ( select user.groups.id from User user where user.id = :userId) ";
		List<Long> ugwIds = commonDao.findByQuery(hql3, new String[]{"whId","userId"}, new Object[]{whIds.get(0), userIds.get(0)});
		if( ugwIds == null || ugwIds.size() == 0 ){
			return LocaleUtil.getText(ExceptionConstant.CANNOT_FIND_USERGROUP_WH) + userName;
		}
		
		// 通过验证
		return null;
	}

}
