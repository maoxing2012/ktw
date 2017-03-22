package com.core.scpwms.server.service.imports;

import java.util.Map;

import com.core.db.server.service.BaseManager;

/**
 * 入库订单的统一导入接口
 * @author mousachi
 *
 */
public interface AsnFileImportManager extends BaseManager {

	public Map<String, String> importNs(Long id);
	
	public Map<String, String> importNsForce(Long id);
	
}
