package com.core.scpwms.server.service.security.impl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.core.business.exception.BusinessException;
import com.core.business.model.security.User;
import com.core.business.service.security.pojo.DefaultUserManager;
import com.core.business.web.security.UserHolder;
import com.core.db.util.LocalizedMessage;

@SuppressWarnings("all")
public class WmsUserManagerImpl extends DefaultUserManager {
	

	@Override
	public void saveUser(User user, Locale locale) {
		
		if (this.retrieve(user.getLoginName()) != null && user.isNew()) {
			throw new BusinessException("user.already.exsits", new String[] { user.getLoginName() });
		}
		
		User dbuser;
		if (user.isNew()) {
			dbuser = user;
			dbuser.setEnabled(true);
			dbuser.setLocked(false);
		} else {
			dbuser = load(User.class, user.getId());
		}
			
		verifyPassByRule(dbuser, user.getPassword());
		
		dbuser.setStrExtend1(user.getStrExtend1());
		dbuser.setStrExtend2(user.getStrExtend2());
		dbuser.setStrExtend3(user.getStrExtend3());
		dbuser.setStrExtend4(user.getStrExtend4());
		dbuser.setLocale(locale);
		dbuser.setExpiryDate(user.getExpiryDate());
		dbuser.setPasswordExpiryDate(user.getPasswordExpiryDate());
		dbuser.setEmail(user.getEmail());
		dbuser.setLoginName(user.getLoginName());
		dbuser.setName(user.getName());
		dbuser.setReferenceModel(user.getReferenceModel());

		this.commonDao.store(dbuser);
	}

	private void verifyPassByRule(User user, String newPassword) {
		
		if(newPassword.length() == 40){
			return;
		}
		
		String newPass = DigestUtils.shaHex(newPassword);
		user.setPassword(newPass);
	}
	
	/**
	 * 用户修改密码
	 */
	@Override
	public void modifyPassword(Map map) {
		User user = UserHolder.getUser();
		String oldPassword=String.valueOf(map.get("CURRENTOLDPSWD"));
		String newPassword=String.valueOf(map.get("NEWPSWD"));
		String confirmPassword=String.valueOf(map.get("CONFIRMPSWD"));
		if((newPassword.trim()).equals(confirmPassword.trim())){
			if((DigestUtils.shaHex(oldPassword).trim()).equals(user.getPassword().trim())){
				user.setPassword(DigestUtils.shaHex(newPassword));
				commonDao.store(user);
				LocalizedMessage.addMessage("password.Change.success");
			}else{
				throw new BusinessException("password.notCorrect");
			}
		}else{
			throw new BusinessException("newPassword.notsame");
		}
	}	
	
	@Override
	public Map getThisUser(Map m){
		Map map=new HashMap();	
		User userPswd=UserHolder.getUser();
		map.put("CURRENTUSER_ID", userPswd.getId());
		map.put("CURRENTUSER_NAME", userPswd.getLoginName());
		map.put("CURRENTUSER_PWD", userPswd.getPassword());
		return map;
	}	

}
