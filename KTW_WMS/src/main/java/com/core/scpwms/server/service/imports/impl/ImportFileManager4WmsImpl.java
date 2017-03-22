package com.core.scpwms.server.service.imports.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.enumerate.EnuFileProcessStatus;
import com.core.business.model.imports.FileImport;
import com.core.business.model.imports.FileImportRule;
import com.core.business.service.imports.ImportFileRuleManager;
import com.core.business.utils.FileUploadUtils;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.utils.Constants;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.file.FileImport4Wms;
import com.core.scpwms.server.model.file.FileImportRule4Wms;
import com.core.scpwms.server.service.imports.ImportFileManager4Wms;
import com.core.scpwms.server.service.imports.ImportFileRuleManager4Wms;

public class ImportFileManager4WmsImpl extends DefaultBaseManager implements ImportFileManager4Wms, ApplicationContextAware {

	public ApplicationContext ac;
	
    public ImportFileRuleManager4Wms importFileRuleManager4Wms;
    
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.ac = arg0;
	}

    public ImportFileRuleManager4Wms getImportFileRuleManager4Wms() {
		return importFileRuleManager4Wms;
	}

	public void setImportFileRuleManager4Wms(ImportFileRuleManager4Wms importFileRuleManager4Wms) {
		this.importFileRuleManager4Wms = importFileRuleManager4Wms;
	}

	@Override
    public void dealFile(List<Long> ids) {

        for (Long id : ids) {
            // 状态改为处理中
        	importFileRuleManager4Wms.updateImportFileStatus(id);
        	
            // 处理文件
            Map<String, String> result = importFileRuleManager4Wms.dealFile(id);

            // 处理成功
            if (result.get("SUCCESS") != null) {
            	importFileRuleManager4Wms.updateImportFileToEndStatus(id);
            }
            // 有异常数据，处理中止
            else if (result.get("SUCCESS_WITH_ERROR") != null) {
            	importFileRuleManager4Wms.updateImportFileToEndStatusWithError(id);
            }
            // 跳过异常数据，处理完成
            else if (result.get("FORCE_SUCCESS") != null) {
            	importFileRuleManager4Wms.updateImportFileToForceEndStatus(id);
            } else {
                throw new BusinessException("deal.file.error");
            }
        }
    }
	
	@Override
    public void cancelFile(List<Long> ids) {

        for (Long id : ids) {
            // 状态改为处理中
//        	importFileRuleManager4Wms.updateImportFileStatus(id);
        	
            // 处理文件
            Map<String, String> result = importFileRuleManager4Wms.cancelFile(id);

            // 处理成功
            if (result.get("SUCCESS") != null) {
            	importFileRuleManager4Wms.updateImportFileToStatus(id, EnuFileProcessStatus.UPLOADED);
            }
            // 有异常数据，处理中止
            else if (result.get("SUCCESS_WITH_ERROR") != null) {
            	importFileRuleManager4Wms.updateImportFileToEndStatusWithError(id);
            }
            // 跳过异常数据，处理完成
            else if (result.get("FORCE_SUCCESS") != null) {
            	importFileRuleManager4Wms.updateImportFileToForceEndStatus(id);
            } else {
                throw new BusinessException("deal.file.error");
            }
        }
    }


    @Override
    public void forceDealFile(List<Long> ids) {

        for (Long id : ids) {
            // 状态改为处理中
        	importFileRuleManager4Wms.updateImportFileStatus(id);

            // 跳过异常数据，强制处理
            Map<String, String> result = importFileRuleManager4Wms.forceDealFile(id);

            // 处理成功
            if (result.get("SUCCESS") != null) {
            	importFileRuleManager4Wms.updateImportFileToEndStatus(id);
            }
            // 有异常数据，处理中止
            else if (result.get("SUCCESS_WITH_ERROR") != null) {
            	importFileRuleManager4Wms.updateImportFileToEndStatusWithError(id);
            }
            // 跳过异常数据，处理完成
            else if (result.get("FORCE_SUCCESS") != null) {
            	importFileRuleManager4Wms.updateImportFileToForceEndStatus(id);
            } else {
                throw new BusinessException("deal.file.error");
            }
        }
    }

    @Override
    public void deleteFile(List<Long> ids) {
        if (ids == null || ids.size() == 0)
            return;

        for (Long id : ids) {
        	importFileRuleManager4Wms.deleteImportFile(id);
        }
    }
    
    
 // ActiveT 1304901 2014/10/27
 	@Override
 	public Map getInitList(Map params) {
 		HashMap retMap = new HashMap();
 		
 		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
 		List<FileImportRule> rules = this.commonDao.loadAll(FileImportRule.class);
 		for(FileImportRule rule:rules){
 			valueMap.put(rule.getManageName() + "/" + rule.getMethodName(), rule.getRuleName());
 		}
 		retMap.put(Constants.FILE_LIST_VALUE, valueMap);
 		return retMap;
 		/*
 		List<Long> ids = (List<Long>) commonDao.findByQuery("select en.id from Enumerate en where en.enumType='UploadProcess'");
 	    
 		if(ids.isEmpty()){
 			return null;
 		} else {
 			LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
 			for(Long id : ids){
 				Enumerate en = commonDao.load(Enumerate.class, id);
 				valueMap.put(en.getEnumValue(), LocalizedMessage.getText("UploadProcess." + en.getEnumValue(), UserHolder.getReferenceModel(), UserHolder.getLanguage()));
 			}
 			retMap.put(Constants.FILE_LIST_VALUE, valueMap);
 			return retMap;
 		}
 		*/
 	}

 	// ActiveT 1304901 2014/10/27
 	@Override
 	public Map doProcess(Map params) {
 		Map retMap = new HashMap();
 		
 		Object targetAction = ac.getBean(params.get(Constants.FILE_MANAGER).toString());
 		List<Long> ids = (List<Long>) params.get(Constants.FILE_IDS);
 		try {
 			List<ArrayList<String>> contents = FileUploadUtils.processForExcel(params.get(Constants.FILE_NAME).toString());
 			if (contents == null) {
 	 			retMap.put(Constants.FILE_READ_ERROR, Constants.FILE_READ_ERROR);
 	 			return retMap;
 			}
 			Method method = MethodUtils.getAccessibleMethod(targetAction.getClass(),params.get(Constants.FILE_METHOD).toString(), new Class[]{List.class, List.class});
 			method.invoke(targetAction,new Object[]{contents, ids});
// 		} catch(InvocationTargetException e1) {
// 			throw new BusinessException("fileUpload.invoke.err", true);
// 		} catch(IllegalAccessException e2) {
// 			throw new BusinessException("fileUpload.invoke.err", true);
// 		} catch(BusinessException e3){
// 			throw e3;
// 		}
 		}catch(Exception e){
 			retMap.put(Constants.FILE_MSG, null);
 			return retMap;
 		}
 		retMap.put(Constants.FILE_MSG, Constants.FILE_MSG);
 		return retMap;
 	}

	@Override
	public void resetStatus(List<Long> ids) {
		for( Long id : ids ){
			FileImport fi = commonDao.load(FileImport.class, id);
			fi.setStatus(EnuFileProcessStatus.UPLOADED);
			commonDao.store(fi);
		}
	}

}
