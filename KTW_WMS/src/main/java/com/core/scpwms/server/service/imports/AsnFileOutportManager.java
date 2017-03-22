package com.core.scpwms.server.service.imports;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 入库实际文件导出
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface AsnFileOutportManager extends BaseManager {
	@Transactional
	public Map<String, String> outportNs(Long id);
	
	@Transactional
	public Map<String, String> outportNsCancel(Long id);
}
