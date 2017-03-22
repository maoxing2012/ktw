package com.core.scpwms.server.service.imports;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.supercsv.prefs.CsvPreference;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.user.Owner;

/**
 * 出库订单的导入
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface ObdFileImportWithTransManager extends BaseManager {
	
	@Transactional
	public String[] createObd(String[][] csvFile, List<String[]> errorInfos, Owner owner, boolean isForce);
	
	public CsvPreference getCsvPreference();
}
