package com.core.scpwms.server.service.imports;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.supercsv.prefs.CsvPreference;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.user.Owner;

/**
 * 入库单导入
 * @author mousachi
 *
 */
@Transactional(readOnly = false)
public interface AsnFileImportWithTransManager extends BaseManager {
	
	@Transactional
	public String[] createAsn(String[][] csvFile, List<String[]> errorInfos, Owner owner, boolean isForce);
	
	public CsvPreference getCsvPreference();
}
