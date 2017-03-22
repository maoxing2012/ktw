package com.core.scpwms.server.service.imports;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 商品资料导入
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface SkuFileImportWithTransManager extends BaseManager {
	
	@Transactional
	public Boolean createSku(List<String> excelLine, int index, List<String[]> errorInfos);
	
	@Transactional
	public Boolean createBarCode( List<String> excelLine, int index, List<String[]> errorInfos );
}
