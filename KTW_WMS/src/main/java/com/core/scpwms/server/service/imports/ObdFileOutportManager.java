package com.core.scpwms.server.service.imports;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 出库实际导出
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface ObdFileOutportManager extends BaseManager {
	// 出荷実績
	@Transactional
	public Map<String, String> outportNsObd1(Long id);
	
	// 出荷実績取り消し
	@Transactional
	public Map<String, String> outportNsObd1Cancel(Long id);
	
	// 出庫実績
	@Transactional
	public Map<String, String> outportNsObd2(Long id);
	
	// 出庫実績取り消し
	@Transactional
	public Map<String, String> outportNsObd2Cancel(Long id);
	
	// 送り状紐付け
	@Transactional
	public Map<String, String> outportNsObd3(Long id);
	
	// 送り状紐付け取り消し
	@Transactional
	public Map<String, String> outportNsObd3Cancel(Long id);
}
