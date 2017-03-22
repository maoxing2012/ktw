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
import com.core.scpwms.server.service.imports.BinFileImportWithTransManager;
import com.core.scpwms.server.service.imports.BizOrgFileImportWithTransManager;
import com.core.scpwms.server.service.imports.FileImportManager;
import com.core.scpwms.server.service.imports.ImportFileManager4Wms;
import com.core.scpwms.server.service.imports.InventoryFileImportWithTransManager;
import com.core.scpwms.server.service.imports.SkuFileImportWithTransManager;
import com.core.scpwms.server.service.imports.TransportFileImportWithTransManager;
import com.core.scpwms.server.util.FileUtil;

public class FileImportManagerImpl extends DefaultBaseManager implements FileImportManager {
	private BinFileImportWithTransManager binFileImportWithTransManager;
	private SkuFileImportWithTransManager skuFileImportWithTransManager;
	private BizOrgFileImportWithTransManager bizOrgFileImportWithTransManager;
	private InventoryFileImportWithTransManager nsInventoryFileImportWithTransManager;
	private TransportFileImportWithTransManager transportFileImportWithTransManager;
	
	public BinFileImportWithTransManager getBinFileImportWithTransManager() {
		return this.binFileImportWithTransManager;
	}

	public void setBinFileImportWithTransManager(
			BinFileImportWithTransManager binFileImportWithTransManager) {
		this.binFileImportWithTransManager = binFileImportWithTransManager;
	}

	public SkuFileImportWithTransManager getSkuFileImportWithTransManager() {
		return this.skuFileImportWithTransManager;
	}

	public void setSkuFileImportWithTransManager(
			SkuFileImportWithTransManager skuFileImportWithTransManager) {
		this.skuFileImportWithTransManager = skuFileImportWithTransManager;
	}
	
	public BizOrgFileImportWithTransManager getBizOrgFileImportWithTransManager() {
		return bizOrgFileImportWithTransManager;
	}

	public void setBizOrgFileImportWithTransManager(
			BizOrgFileImportWithTransManager bizOrgFileImportWithTransManager) {
		this.bizOrgFileImportWithTransManager = bizOrgFileImportWithTransManager;
	}
	
	public InventoryFileImportWithTransManager getNsInventoryFileImportWithTransManager() {
		return nsInventoryFileImportWithTransManager;
	}

	public void setNsInventoryFileImportWithTransManager(
			InventoryFileImportWithTransManager nsInventoryFileImportWithTransManager) {
		this.nsInventoryFileImportWithTransManager = nsInventoryFileImportWithTransManager;
	}

	public TransportFileImportWithTransManager getTransportFileImportWithTransManager() {
		return transportFileImportWithTransManager;
	}

	public void setTransportFileImportWithTransManager(
			TransportFileImportWithTransManager transportFileImportWithTransManager) {
		this.transportFileImportWithTransManager = transportFileImportWithTransManager;
	}

	private void createErrorFile(List<String[]> csvResult, String filePath,
			FileImport fileInfo) {

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
	
	@Override
	public Map<String, String> importBin(Long id) {
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			List<List<String>> fileList = null;
			try{
				fileList = FileUtil.readExcelFile(fileInfo.getTargetName());
			}catch(Exception e){
				// 异常信息明细
				fileInfo.setWarnningMessage("拡張子「.xls」、Excel2003ブックを指定してください。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( fileList != null && fileList.size() > 0 ){
				fileList.remove(0);
				List<String[]> resultList = new ArrayList<String[]>();
				Boolean isNew = false;
				int newCount = 0;
				int editCount = 0;
				
				for (int i = 0; i < fileList.size(); i++) {
					try{
						isNew = binFileImportWithTransManager.createBin(fileList.get(i), i + 2, resultList);
						if( isNew != null && isNew ){
							newCount++;
						}
						else if( isNew != null && !isNew ){
							editCount++;
						}
					}catch(BusinessException be){
						resultList.add( new String[]{ String.valueOf(i + 2), be.getLocalizedMessage() } );
					}
				}
				
				if( resultList != null && resultList.size() > 0 ){
					// 异常信息明细
					createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
					fileInfo.setWarnningMessage("インポートに一部成功しました。新規：" + newCount + "件、更新：" + editCount + "件。結果詳細を確認してください。");
					result.put(ImportFileManager4Wms.FORCE_SUCCESS, ImportFileManager4Wms.FORCE_SUCCESS);
				}
				else{
					// 处理结果信息
					fileInfo.setWarnningMessage("インポートに成功しました。新規：" + newCount + "件、更新：" + editCount + "件。");
					result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
				}
			}

			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}

	@Override
	public Map<String, String> importSku(Long id) {
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			List<List<String>> fileList = null;
			try{
				fileList = FileUtil.readExcelFile(fileInfo.getTargetName());
			}catch(Exception e){
				// 异常信息明细
				fileInfo.setWarnningMessage("拡張子「.xls」、Excel2003ブックを指定してください。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( fileList != null && fileList.size() > 0 ){
				fileList.remove(0);
				List<String[]> resultList = new ArrayList<String[]>();
				Boolean isNew = false;
				int newCount = 0;
				int editCount = 0;
				
				for (int i = 0; i < fileList.size(); i++) {
					try{
						isNew = skuFileImportWithTransManager.createSku(fileList.get(i), i + 2, resultList);
						if( isNew != null && isNew ){
							newCount++;
						}
						else if( isNew != null && !isNew ){
							editCount++;
						}
					}catch(BusinessException be){
						resultList.add( new String[]{ String.valueOf(i + 2), be.getLocalizedMessage() } );
					}
				}
				
				if( resultList != null && resultList.size() > 0 ){
					// 异常信息明细
					createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
					fileInfo.setWarnningMessage("インポートに一部成功しました。新規：" + newCount + "件、更新：" + editCount + "件。結果詳細を確認してください。");
					result.put(ImportFileManager4Wms.FORCE_SUCCESS, ImportFileManager4Wms.FORCE_SUCCESS);
				}
				else{
					// 处理结果信息
					fileInfo.setWarnningMessage("インポートに成功しました。新規：" + newCount + "件、更新：" + editCount + "件。");
					result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
				}
			}
			
			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}
		return result;
	}

	@Override
	public Map<String, String> importVendor(Long id) {
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			List<List<String>> fileList = null;
			try{
				fileList = FileUtil.readExcelFile(fileInfo.getTargetName());
			}catch(Exception e){
				// 异常信息明细
				fileInfo.setWarnningMessage("拡張子「.xls」、Excel2003ブックを指定してください。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( fileList != null && fileList.size() > 0 ){
				fileList.remove(0);
				List<String[]> resultList = new ArrayList<String[]>();
				Boolean isNew = false;
				int newCount = 0;
				int editCount = 0;
				
				for (int i = 0; i < fileList.size(); i++) {
					try{
						isNew = bizOrgFileImportWithTransManager.createVendor(fileList.get(i), i + 2, resultList);
						if( isNew != null && isNew ){
							newCount++;
						}
						else if( isNew != null && !isNew ){
							editCount++;
						}
					}catch(BusinessException be){
						resultList.add( new String[]{ String.valueOf(i + 2), be.getLocalizedMessage() } );
					}
				}
				
				if( resultList != null && resultList.size() > 0 ){
					// 异常信息明细
					createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
					fileInfo.setWarnningMessage("インポートに一部成功しました。新規：" + newCount + "件、更新：" + editCount + "件。結果詳細を確認してください。");
					result.put(ImportFileManager4Wms.FORCE_SUCCESS, ImportFileManager4Wms.FORCE_SUCCESS);
				}
				else{
					// 处理结果信息
					fileInfo.setWarnningMessage("インポートに成功しました。新規：" + newCount + "件、更新：" + editCount + "件。");
					result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
				}
			}
			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}

	@Override
	public Map<String, String> importShop(Long id) {
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			List<List<String>> fileList = null;
			try{
				fileList = FileUtil.readExcelFile(fileInfo.getTargetName());
			}catch(Exception e){
				// 异常信息明细
				fileInfo.setWarnningMessage("拡張子「.xls」、Excel2003ブックを指定してください。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( fileList != null && fileList.size() > 0 ){
				fileList.remove(0);
				List<String[]> resultList = new ArrayList<String[]>();
				Boolean isNew = false;
				int newCount = 0;
				int editCount = 0;
				
				for (int i = 0; i < fileList.size(); i++) {
					try{
						isNew = bizOrgFileImportWithTransManager.createShop(fileList.get(i), i + 2, resultList);
						if( isNew != null && isNew ){
							newCount++;
						}
						else if( isNew != null && !isNew ){
							editCount++;
						}
					}catch(BusinessException be){
						resultList.add( new String[]{ String.valueOf(i + 2), be.getLocalizedMessage() } );
					}
				}
				
				if( resultList != null && resultList.size() > 0 ){
					// 异常信息明细
					createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
					fileInfo.setWarnningMessage("インポートに一部成功しました。新規：" + newCount + "件、更新：" + editCount + "件。結果詳細を確認してください。");
					result.put(ImportFileManager4Wms.FORCE_SUCCESS, ImportFileManager4Wms.FORCE_SUCCESS);
				}
				else{
					// 处理结果信息
					fileInfo.setWarnningMessage("インポートに成功しました。新規：" + newCount + "件、更新：" + editCount + "件。");
					result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
				}
			}

			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}

	@Override
	public Map<String, String> importBarCode(Long id) {
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			List<List<String>> fileList = null;
			try{
				fileList = FileUtil.readExcelFile(fileInfo.getTargetName());
			}catch(Exception e){
				// 异常信息明细
				fileInfo.setWarnningMessage("拡張子「.xls」、Excel2003ブックを指定してください。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( fileList != null && fileList.size() > 0 ){
				fileList.remove(0);
				List<String[]> resultList = new ArrayList<String[]>();
				Boolean isNew = false;
				int newCount = 0;
				int editCount = 0;
				
				for (int i = 0; i < fileList.size(); i++) {
					try{
						isNew = skuFileImportWithTransManager.createBarCode(fileList.get(i), i + 2, resultList);
						if( isNew != null && isNew ){
							newCount++;
						}
						else if( isNew != null && !isNew ){
							editCount++;
						}
					}catch(BusinessException be){
						resultList.add( new String[]{ String.valueOf(i + 2), be.getLocalizedMessage() } );
					}
				}
				
				if( resultList != null && resultList.size() > 0 ){
					// 异常信息明细
					createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
					fileInfo.setWarnningMessage("インポートに一部成功しました。新規：" + newCount + "件、更新：" + editCount + "件。結果詳細を確認してください。");
					result.put(ImportFileManager4Wms.FORCE_SUCCESS, ImportFileManager4Wms.FORCE_SUCCESS);
				}
				else{
					// 处理结果信息
					fileInfo.setWarnningMessage("インポートに成功しました。新規：" + newCount + "件、更新：" + editCount + "件。");
					result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
				}
			}

			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}

	@Override
	public Map<String, String> importInventory4Ns(Long id) {
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			List<List<String>> fileList = null;
			try{
				fileList = FileUtil.readExcelFile(fileInfo.getTargetName());
			}catch(Exception e){
				// 异常信息明细
				fileInfo.setWarnningMessage("拡張子「.xls」、Excel2003ブックを指定してください。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( fileList != null && fileList.size() > 0 ){
				fileList.remove(0);
				List<String[]> resultList = null;
				
				String hql = " from FileImport4Wms fiw where fiw.fileImport.id = :fiId ";
				FileImport4Wms fiw = (FileImport4Wms)commonDao.findByQueryUniqueResult(hql, new String[]{"fiId"}, new Object[]{id});
				
				try{
					resultList = nsInventoryFileImportWithTransManager.createInventory(fiw.getWh().getId(), fiw.getOwner().getId(), fileList);
				}catch(BusinessException be){
					resultList.add( new String[]{ String.valueOf(1), be.getLocalizedMessage() } );
				}
				
				if( resultList != null && resultList.size() > 0 ){
					// 异常信息明细
					createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
					fileInfo.setWarnningMessage("インポートに失敗しました。結果詳細を確認してください。");
					result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
				}
				else{
					// 处理结果信息
					fileInfo.setWarnningMessage("インポートに成功しました。");
					result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
				}
			}
			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}

	@Override
	public Map<String, String> importCity(Long id) {
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			List<List<String>> fileList = null;
			try{
				fileList = FileUtil.readExcelFile(fileInfo.getTargetName());
			}catch(Exception e){
				// 异常信息明细
				fileInfo.setWarnningMessage("拡張子「.xls」、Excel2003ブックを指定してください。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( fileList != null && fileList.size() > 0 ){
				fileList.remove(0);
				List<String[]> resultList = new ArrayList<String[]>();
				Boolean isNew = false;
				int newCount = 0;
				int editCount = 0;
				
				for (int i = 0; i < fileList.size(); i++) {
					try{
						isNew = transportFileImportWithTransManager.createCity(fileList.get(i), i + 2, resultList);
						if( isNew != null && isNew ){
							newCount++;
						}
						else if( isNew != null && !isNew ){
							editCount++;
						}
					}catch(BusinessException be){
						resultList.add( new String[]{ String.valueOf(i + 2), be.getLocalizedMessage() } );
					}
				}
				
				if( resultList != null && resultList.size() > 0 ){
					// 异常信息明细
					createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
					fileInfo.setWarnningMessage("インポートに一部成功しました。新規：" + newCount + "件、更新：" + editCount + "件。結果詳細を確認してください。");
					result.put(ImportFileManager4Wms.FORCE_SUCCESS, ImportFileManager4Wms.FORCE_SUCCESS);
				}
				else{
					// 处理结果信息
					fileInfo.setWarnningMessage("インポートに成功しました。新規：" + newCount + "件、更新：" + editCount + "件。");
					result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
				}
			}

			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}

	@Override
	public Map<String, String> importPostcode(Long id) {
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			List<List<String>> fileList = null;
			try{
				fileList = FileUtil.readExcelFile(fileInfo.getTargetName());
			}catch(Exception e){
				// 异常信息明细
				fileInfo.setWarnningMessage("拡張子「.xls」、Excel2003ブックを指定してください。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( fileList != null && fileList.size() > 0 ){
				fileList.remove(0);
				List<String[]> resultList = new ArrayList<String[]>();
				Boolean isNew = false;
				int newCount = 0;
				int editCount = 0;
				
				for (int i = 0; i < fileList.size(); i++) {
					try{
						isNew = transportFileImportWithTransManager.createPost(fileList.get(i), i + 2, resultList);
						if( isNew != null && isNew ){
							newCount++;
						}
						else if( isNew != null && !isNew ){
							editCount++;
						}
					}catch(BusinessException be){
						resultList.add( new String[]{ String.valueOf(i + 2), be.getLocalizedMessage() } );
					}
				}
				
				if( resultList != null && resultList.size() > 0 ){
					// 异常信息明细
					createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
					fileInfo.setWarnningMessage("インポートに一部成功しました。新規：" + newCount + "件、更新：" + editCount + "件。結果詳細を確認してください。");
					result.put(ImportFileManager4Wms.FORCE_SUCCESS, ImportFileManager4Wms.FORCE_SUCCESS);
				}
				else{
					// 处理结果信息
					fileInfo.setWarnningMessage("インポートに成功しました。新規：" + newCount + "件、更新：" + editCount + "件。");
					result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
				}
			}

			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}

	@Override
	public Map<String, String> importCoursePostcode(Long id) {
		Map<String, String> result = new HashMap<String, String>();
		FileImport fileInfo = getCommonDao().load(FileImport.class, id);

		if (fileInfo != null) {
			List<List<String>> fileList = null;
			try{
				fileList = FileUtil.readExcelFile(fileInfo.getTargetName());
			}catch(Exception e){
				// 异常信息明细
				fileInfo.setWarnningMessage("拡張子「.xls」、Excel2003ブックを指定してください。");
				result.put(ImportFileManager4Wms.SUCCESS_WITH_ERROR, ImportFileManager4Wms.SUCCESS_WITH_ERROR);
			}
			
			if( fileList != null && fileList.size() > 0 ){
				fileList.remove(0);
				List<String[]> resultList = new ArrayList<String[]>();
				Boolean isNew = false;
				int newCount = 0;
				int editCount = 0;
				
				for (int i = 0; i < fileList.size(); i++) {
					try{
						isNew = transportFileImportWithTransManager.createCoursePost(fileList.get(i), i + 2, resultList);
						if( isNew != null && isNew ){
							newCount++;
						}
						else if( isNew != null && !isNew ){
							editCount++;
						}
					}catch(BusinessException be){
						resultList.add( new String[]{ String.valueOf(i + 2), be.getLocalizedMessage() } );
					}
				}
				
				if( resultList != null && resultList.size() > 0 ){
					// 异常信息明细
					createErrorFile(resultList, fileInfo.getTargetName(), fileInfo);
					fileInfo.setWarnningMessage("インポートに一部成功しました。新規：" + newCount + "件、更新：" + editCount + "件。結果詳細を確認してください。");
					result.put(ImportFileManager4Wms.FORCE_SUCCESS, ImportFileManager4Wms.FORCE_SUCCESS);
				}
				else{
					// 处理结果信息
					fileInfo.setWarnningMessage("インポートに成功しました。新規：" + newCount + "件、更新：" + editCount + "件。");
					result.put(ImportFileManager4Wms.SUCCESS, ImportFileManager4Wms.SUCCESS);
				}
			}

			// 更新文件状态
			fileInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			getCommonDao().store(fileInfo);
		}

		return result;
	}

}
