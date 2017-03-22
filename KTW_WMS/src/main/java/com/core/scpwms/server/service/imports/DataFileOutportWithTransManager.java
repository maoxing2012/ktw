package com.core.scpwms.server.service.imports;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.file.FileImport4Wms;

/**
 * 实际数据导出
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface DataFileOutportWithTransManager extends BaseManager {
	// 入库实际
	@Transactional
	public String createAsnOutportFile(FileImport4Wms fileImport4Wms);
	
	// 入库实际取消
	@Transactional
	public String cancelAsnOutportFile(FileImport4Wms fileImport4Wms);
	
	// 库存实际
	@Transactional
	public String createInvOutportFile(FileImport4Wms fileImport4Wms);
	
	// 库存实际取消
	@Transactional
	public String cancelInvOutportFile(FileImport4Wms fileImport4Wms);
	
	// 盘点实际
	@Transactional
	public String createCountOutportFile(FileImport4Wms fileImport4Wms);
	
	// 盘点实际取消
	@Transactional
	public String cancelCountOutportFile(FileImport4Wms fileImport4Wms);
	
	// 出库实际
	@Transactional
	public String createObdOutportFile(FileImport4Wms fileImport4Wms);
	
	// 出库实际取消
	@Transactional
	public String cancelObdOutportFile(FileImport4Wms fileImport4Wms);
	
	// 其他出库实际
	@Transactional
	public String createObd2OutportFile(FileImport4Wms fileImport4Wms);
	
	// 其他出库实际取消
	@Transactional
	public String cancelObd2OutportFile(FileImport4Wms fileImport4Wms);
	
	// 其他出库实际
	@Transactional
	public String createObd3OutportFile(FileImport4Wms fileImport4Wms);
	
	// 其他出库实际取消
	@Transactional
	public String cancelObd3OutportFile(FileImport4Wms fileImport4Wms);
}
