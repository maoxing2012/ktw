package com.core.scpwms.server.service.imports;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 库位导入
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface BinFileImportWithTransManager extends BaseManager {
	
	@Transactional
	public Boolean createBin(List<String> excelLine, int index, List<String[]> errorInfos);

}
