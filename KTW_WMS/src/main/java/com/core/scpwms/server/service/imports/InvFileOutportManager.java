package com.core.scpwms.server.service.imports;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 库存信息导出
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface InvFileOutportManager extends BaseManager {
	@Transactional
	public Map<String, String> outportNsInv(Long id);
	
	@Transactional
	public Map<String, String> outportNsInvCancel(Long id);
	
	@Transactional
	public Map<String, String> outportNsCount(Long id);
	
	@Transactional
	public Map<String, String> outportNsCountCancel(Long id);
}
