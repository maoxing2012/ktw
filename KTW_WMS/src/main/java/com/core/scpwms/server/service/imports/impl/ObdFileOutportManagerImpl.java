package com.core.scpwms.server.service.imports.impl;

import java.util.HashMap;
import java.util.Map;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.model.imports.FileImport;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.model.file.FileImport4Wms;
import com.core.scpwms.server.service.imports.DataFileOutportWithTransManager;
import com.core.scpwms.server.service.imports.ImportFileManager4Wms;
import com.core.scpwms.server.service.imports.ObdFileOutportManager;

/**
 * 出库实际导出
 * @author mousachi
 *
 */
public class ObdFileOutportManagerImpl extends DefaultBaseManager implements ObdFileOutportManager {
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
	public Map<String, String> outportNsObd1(Long id) {
		return outportFile(id, nsDataFileOutportWithTransManager, 1);
	}
	
	@Override
	public Map<String, String> outportNsObd2(Long id) {
		return outportFile(id, nsDataFileOutportWithTransManager, 2);
	}
	
	@Override
	public Map<String, String> outportNsObd3(Long id) {
		return outportFile(id, nsDataFileOutportWithTransManager, 3);
	}
	
	@Override
	public Map<String, String> outportNsObd1Cancel(Long id) {
		return outportFileCancel(id, nsDataFileOutportWithTransManager, 1);
	}
	
	@Override
	public Map<String, String> outportNsObd2Cancel(Long id) {
		return outportFileCancel(id, nsDataFileOutportWithTransManager, 2);
	}
	
	@Override
	public Map<String, String> outportNsObd3Cancel(Long id) {
		return outportFileCancel(id, nsDataFileOutportWithTransManager, 3);
	}
	
	private Map<String, String> outportFileCancel( Long id, DataFileOutportWithTransManager outportManager, int obdType ){
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			String results = null;
			try{
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				
				if( obdType == 1 ){
					results = outportManager.cancelObdOutportFile(fiw);
				}
				else if( obdType == 2 ){
					results = outportManager.cancelObd2OutportFile(fiw);
				}
				else if( obdType == 3 ){
					results = outportManager.cancelObd3OutportFile(fiw);
				}
				else{
					results = outportManager.cancelObdOutportFile(fiw);
				}
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
	
	private Map<String, String> outportFile( Long id, DataFileOutportWithTransManager outportManager, int obdType ){
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			String results = null;
			try{
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				if( obdType == 1 ){
					results = outportManager.createObdOutportFile(fiw);
				}
				else if( obdType == 2 ){
					results = outportManager.createObd2OutportFile(fiw);
				}
				else if( obdType == 3 ){
					results = outportManager.createObd3OutportFile(fiw);
				}
				else{
					results = outportManager.createObdOutportFile(fiw);
				}
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
