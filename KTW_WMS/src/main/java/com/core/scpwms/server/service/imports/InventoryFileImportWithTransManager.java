package com.core.scpwms.server.service.imports;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 库存数据导入
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface InventoryFileImportWithTransManager extends BaseManager {

	@Transactional
	public List<String[]> createInventory(Long whId, Long ownerId, List<List<String>> fileList);

}
