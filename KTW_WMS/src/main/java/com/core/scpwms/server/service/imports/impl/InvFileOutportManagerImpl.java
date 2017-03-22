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
import com.core.scpwms.server.service.imports.InvFileOutportManager;

/**
 * 入库单的结果数据导出
 * @author mousachi
 *
 */
public class InvFileOutportManagerImpl extends DefaultBaseManager implements InvFileOutportManager {
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
	public Map<String, String> outportNsInv(Long id) {
		return outportInvFile(id, nsDataFileOutportWithTransManager);
	}
	
	@Override
	public Map<String, String> outportNsInvCancel(Long id) {
		return outportInvFileCancel(id, nsDataFileOutportWithTransManager);
	}
	
	@Override
	public Map<String, String> outportNsCount(Long id) {
		return outportCountFile(id, nsDataFileOutportWithTransManager);
	}
	
	@Override
	public Map<String, String> outportNsCountCancel(Long id) {
		return outportCountFileCancel(id, nsDataFileOutportWithTransManager);
	}
	
	private Map<String, String> outportInvFileCancel( Long id, DataFileOutportWithTransManager outportManager ){
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			String results = null;
			try{
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				results = outportManager.cancelInvOutportFile(fiw);
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
	
	private Map<String, String> outportInvFile( Long id, DataFileOutportWithTransManager outportManager ){
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			String results = null;
			try{
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				results = outportManager.createInvOutportFile(fiw);
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
	
	private Map<String, String> outportCountFileCancel( Long id, DataFileOutportWithTransManager outportManager ){
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			String results = null;
			try{
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				results = outportManager.cancelCountOutportFile(fiw);
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
	
	private Map<String, String> outportCountFile( Long id, DataFileOutportWithTransManager outportManager ){
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			String results = null;
			try{
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				results = outportManager.createCountOutportFile(fiw);
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
