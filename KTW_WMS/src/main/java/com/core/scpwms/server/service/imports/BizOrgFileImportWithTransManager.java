package com.core.scpwms.server.service.imports;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * 供应商/收货人信息导入
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface BizOrgFileImportWithTransManager extends BaseManager {

	@Transactional
	public Boolean createVendor(List<String> excelLine, int index, List<String[]> errorInfos);
	
	@Transactional
	public Boolean createShop(List<String> excelLine, int index, List<String[]> errorInfos);

}
