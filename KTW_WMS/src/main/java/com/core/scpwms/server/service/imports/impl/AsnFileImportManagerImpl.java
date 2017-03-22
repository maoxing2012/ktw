package com.core.scpwms.server.service.imports.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.model.imports.FileErrorDetail;
import com.core.business.model.imports.FileImport;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.model.file.FileImport4Wms;
import com.core.scpwms.server.service.imports.AsnFileImportManager;
import com.core.scpwms.server.service.imports.AsnFileImportWithTransManager;
import com.core.scpwms.server.service.imports.ImportFileManager4Wms;
import com.core.scpwms.server.util.FileUtil4Jp;

/**
 * 入库数据导入
 * @author mousachi
 *
 */
public class AsnFileImportManagerImpl extends DefaultBaseManager implements AsnFileImportManager {
	// 日本食研
	private AsnFileImportWithTransManager nsAsnFileImportWithTransManager;
	
	@Override
	public Map<String, String> importNs(Long id) {
		return importFile(id, nsAsnFileImportWithTransManager);
	}

	@Override
	public Map<String, String> importNsForce(Long id) {
		return importFileForce(id, nsAsnFileImportWithTransManager);
	}
	
	public AsnFileImportWithTransManager getNsAsnFileImportWithTransManager() {
		return nsAsnFileImportWithTransManager;
	}

	public void setNsAsnFileImportWithTransManager(
			AsnFileImportWithTransManager nsAsnFileImportWithTransManager) {
		this.nsAsnFileImportWithTransManager = nsAsnFileImportWithTransManager;
	}
	
	private void deleteOldErrorInfo( Long fileId ){
		String sql = "delete from file_error_detail where file_id = ?";
		commonDao.executeSqlQuery(sql, new Object[]{fileId});
	}

	private void createErrorFile(List<String[]> csvResult, String filePath, FileImport fileInfo) {

		if (csvResult == null || csvResult.size() == 0)
			return;

		for (String[] error : csvResult) {
			FileErrorDetail detail = new FileErrorDetail();
			detail.setFile(fileInfo);
			detail.setRowNo(new Long(error[0]));
			detail.setNote1(error.length >= 2 ? error[1] : null);
			detail.setNote2(error.length >= 3 ? error[2] : null);
			detail.setNote3(error.length >= 4 ? error[3] : null);
			detail.setNote4(error.length >= 5 ? error[4] : null);
			detail.setNote5(error.length >= 6 ? error[5] : null);
			detail.setNote6(error.length >= 7 ? error[6] : null);
			detail.setNote7(error.length >= 8 ? error[7] : null);
			detail.setNote8(error.length >= 9 ? error[8] : null);
			detail.setNote9(error.length >= 10 ? error[9] : null);
			detail.setNote10(error.length >= 11 ? error[10] : null);
			detail.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			commonDao.store(detail);
		}
	}

	
	private Map<String, String> importFile( Long id, AsnFileImportWithTransManager asnImportManager ){
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);
		

		if (fileInfo != null) {
			String[][] csvFile = FileUtil4Jp.readCSVFile(fileInfo.getTargetName(), FileUtil4Jp.ENCODE_MS932, asnImportManager.getCsvPreference());
			List<String[]> resultList = new ArrayList<String[]>();
			String[] results = null;
			
			try{
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				results = asnImportManager.createAsn(csvFile, resultList, fiw.getOwner(), Boolean.FALSE);// 正常
			}catch(BusinessException be){
				resultList.add( new String[]{ "-1", be.getLocalizedMessage() } );
			}
			
			if( resultList != null && resultList.size() > 0 ){
				createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
				fileInfo.setWarnningMessage("インポートに失敗しました。結果詳細画面をご確認ください。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			else{
				// 处理结果信息
				fileInfo.setWarnningMessage("インポートに成功しました。" + "ヘッダー:" + results[0] + "件、明細:" + results[1] + "件。");
				result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
			}

			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}
	
	private Map<String, String> importFileForce( Long id, AsnFileImportWithTransManager asnImportManager ){
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			String[][] csvFile = FileUtil4Jp.readCSVFile(fileInfo.getTargetName(), FileUtil4Jp.ENCODE_MS932, asnImportManager.getCsvPreference());
			List<String[]> resultList = new ArrayList<String[]>();
			String[] results = null;
			
			try{
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				results = asnImportManager.createAsn(csvFile, resultList, fiw.getOwner(), Boolean.TRUE);// 强制导入
			}catch(BusinessException be){
				resultList.add( new String[]{ "-1", be.getLocalizedMessage() } );
			}
			
			if( resultList != null && resultList.size() > 0 ){
				// Delete old errorInfos
				deleteOldErrorInfo(id);
				// Create new errorInfos
				createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
			}
			
			// 处理结果信息
			fileInfo.setWarnningMessage("強制インポートに成功しました。" + "ヘッダー:" + results[0] + "件、明細:" + results[1] + "件。");
			result.put(ImportFileManager4Wms.FORCE_SUCCESS, ImportFileManager4Wms.SUCCESS);

			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}

}
