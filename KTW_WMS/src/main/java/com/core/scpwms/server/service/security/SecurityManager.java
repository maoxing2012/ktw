package com.core.scpwms.server.service.security;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

@Transactional(readOnly = false)
public interface SecurityManager extends BaseManager {
	
	@Transactional
	public Object[] login( String userId, String password );
	
	@Transactional
	public String modifyPassword(Long userId, String oldPassword, String newPassword, String confirmPassword);
	
	@Transactional
	public String getLaborGroupRoles( Long whId, Long userId );
}
