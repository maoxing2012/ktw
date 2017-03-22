package com.core.scpwms.server.service.imports;

import java.util.Map;

import com.core.db.server.service.BaseManager;

/**
 * 基础信息导入的统一接口
 * @author mousachi
 *
 */
public interface FileImportManager extends BaseManager {

	public Map<String, String> importBin(Long id);
	
	public Map<String, String> importSku(Long id);
	
	public Map<String, String> importVendor(Long id);
	
	public Map<String, String> importShop(Long id);
	
	public Map<String, String> importBarCode(Long id);
	
	public Map<String, String> importInventory4Ns( Long id );
	
	public Map<String, String> importCity( Long id );
	
	public Map<String, String> importPostcode( Long id );
	
	public Map<String, String> importCoursePostcode( Long id );
}
