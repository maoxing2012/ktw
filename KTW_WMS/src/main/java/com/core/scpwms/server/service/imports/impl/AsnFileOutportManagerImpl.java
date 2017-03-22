package com.core.scpwms.server.service.imports.impl;

import java.util.HashMap;
import java.util.Map;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.model.imports.FileImport;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.model.file.FileImport4Wms;
import com.core.scpwms.server.service.imports.AsnFileOutportManager;
import com.core.scpwms.server.service.imports.DataFileOutportWithTransManager;
import com.core.scpwms.server.service.imports.ImportFileManager4Wms;

/**
 * 入库单的结果数据导出
 * @author mousachi
 *
 */
public class AsnFileOutportManagerImpl extends DefaultBaseManager implements AsnFileOutportManager {
	// 日本食研
	private DataFileOutportWithTransManager nsDataFileOutportWithTransManager;
	
	public DataFileOutportWithTransManager getNsDataFileOutportWithTransManager() {
		return nsDataFileOutportWithTransManager;
	}

	public void setNsDataFileOutportWithTransManager(
			DataFileOutportWithTransManager nsDataFileOutportWithTransManager) {
		this.nsDataFileOutportWithTransManager = nsDataFileOutportWithTransManager;
	}

	@Override
	public Map<String, String> outportNs(Long id) {
		return outportFile(id, nsDataFileOutportWithTransManager);
	}
	
	@Override
	public Map<String, String> outportNsCancel(Long id) {
		return outportFileCancel(id, nsDataFileOutportWithTransManager);
	}
	
	private Map<String, String> outportFileCancel( Long id, DataFileOutportWithTransManager outportManager ){
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			String results = null;
			try{
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				results = outportManager.cancelAsnOutportFile(fiw);
			}catch(BusinessException be){
				// 处理结果信息
				fileInfo.setWarnningMessage(be.getLocalizedMessage());
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( results == null ){
				// 处理结果信息
				fileInfo.setWarnningMessage(null);
				result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
			}
			
			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}
	
	private Map<String, String> outportFile( Long id, DataFileOutportWithTransManager outportManager ){
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			String results = null;
			try{
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				results = outportManager.createAsnOutportFile(fiw);
			}catch(BusinessException be){
				// 处理结果信息
				fileInfo.setWarnningMessage(be.getLocalizedMessage());
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( results == null ){
				// 处理结果信息
				fileInfo.setWarnningMessage("該当する報告データが見つかりません。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			else{
				// 处理结果信息
				fileInfo.setWarnningMessage(results);
				result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
			}

			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}
}
