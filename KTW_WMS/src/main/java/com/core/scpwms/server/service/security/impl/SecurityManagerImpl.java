/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: SecurityManagerImpl.java
 */

package com.core.scpwms.server.service.security.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.core.business.exception.BusinessException;
import com.core.business.model.security.User;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.db.util.LocalizedMessage;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.security.SecurityManager;
import com.core.scpwms.server.service.warehouse.WarehouseManager;
import com.core.scpwms.server.util.LocaleUtil;

/**
 * <p>请添加注释</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015/03/09<br/>
 */
public class SecurityManagerImpl extends DefaultBaseManager implements
		SecurityManager {
	
	private WarehouseManager warehouseManager;
	
	public WarehouseManager getWarehouseManager() {
		return this.warehouseManager;
	}

	public void setWarehouseManager(WarehouseManager warehouseManager) {
		this.warehouseManager = warehouseManager;
	}

	/* (non-Javadoc)
	 * @see com.core.scpwms.server.service.security.SecurityManager#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Object[] login(String userId, String password) {
		String hql = " select id from User where loginName = :userId ";
		List<Long> ids = commonDao.findByQuery(hql, "userId", userId);
		
		if( ids == null || ids.size() == 0 || ids.size() > 1 ){
			// 该用户不存在
			throw new BusinessException(ExceptionConstant.CANNOT_FIND_USER);
		}
		
		User user = commonDao.load(User.class, ids.get(0));
		doAuthenticate(user, password);
		
		Warehouse wh = warehouseManager.getWarehouseByLoginUser(user);
		if( wh == null ){
			// 没有仓库权限
			throw new BusinessException(ExceptionConstant.CANNOT_FIND_USERGROUP_WH);
		}
		
		// 如果这个用户没有绑定的作业人员
		String hql2 = "select labor.id from Labor labor where 1=1 "
				+ " and labor.user.id = :userId "
				+ " and labor.disabled = false";
		List<Long> laborIds = commonDao.findByQuery(hql2, 
				new String[]{"userId"}, new Object[]{user.getId()});
		if( laborIds == null || laborIds.size() == 0 ){
			throw new BusinessException(ExceptionConstant.CANNOT_FIND_LABOR);
		}
		
		return new Object[]{user, wh};
	}
	
	private void doAuthenticate(User user, String rawPass) throws BusinessException {
		String encodePassword = DigestUtils.shaHex(rawPass);
		if(!user.getPassword().equals(encodePassword)){
			throw new BusinessException( ExceptionConstant.WRONG_PASSWORD );//密码错误
		}
		if(user.getExpiryDate() != null && user.getExpiryDate().before(new Date())){
			throw new BusinessException( ExceptionConstant.EXPIRED_USER );//用户过期
		}
		if(user.getPasswordExpiryDate() != null && user.getPasswordExpiryDate().before(new Date())){
			throw new BusinessException( ExceptionConstant.EXPIRED_PASSWORD );//密码过期
		}
		if(!user.isEnabled()){
			throw new BusinessException( ExceptionConstant.DISABLED_USER );//失效用户
		}
		if(user.isLocked()){
			throw new BusinessException( ExceptionConstant.LOACKED_USER );//锁定用户
		}
	}
	
	/**
	 * 用户修改密码
	 */
	@Override
	public String modifyPassword(Long userId, String oldPassword, String newPassword, String confirmPassword) {
		User user = commonDao.load(User.class, userId);
		if((newPassword.trim()).equals(confirmPassword.trim())){
			if((DigestUtils.shaHex(oldPassword).trim()).equals(user.getPassword().trim())){
				user.setPassword(DigestUtils.shaHex(newPassword));
				commonDao.store(user);
				
				return LocaleUtil.getText("password.Change.success");
			}else{
				throw new BusinessException("password.notCorrect");
			}
		}else{
			throw new BusinessException("newPassword.notsame");
		}
	}

	@Override
	public String getLaborGroupRoles(Long whId, Long userId) {
		// 如果这个用户没有绑定的作业人员
		String hql1 = "select distinct lgl.group.role from LaborGroupLabor lgl where 1=1 "
				+ " and lgl.labor.id in "
				+ " ( select labor.id from Labor labor "
				+ "	where labor.wh.id = :whId "
				+ " and labor.user.id = :userId "
				+ " and labor.disabled = 0 ) ";
		List<String> roles = commonDao.findByQuery(hql1, 
				new String[]{"whId","userId"}, new Object[]{whId,userId});
		
		if( roles != null && roles.size() > 0 ){
			StringBuffer rolesStr = new StringBuffer();
			for( String role : roles ){
				if( rolesStr.length() > 0 ){
					rolesStr.append(",");
				}
				rolesStr.append(role);
			}
			return rolesStr.toString();
		}
		
		return "";
	}	
}
