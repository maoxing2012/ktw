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
public interface TransportFileImportWithTransManager extends BaseManager {
	
	@Transactional
	public Boolean createCity(List<String> excelLine, int index, List<String[]> errorInfos);
	
	@Transactional
	public Boolean createPost(List<String> excelLine, int index, List<String[]> errorInfos);
	
	@Transactional
	public Boolean createCoursePost(List<String> excelLine, int index, List<String[]> errorInfos);

}
