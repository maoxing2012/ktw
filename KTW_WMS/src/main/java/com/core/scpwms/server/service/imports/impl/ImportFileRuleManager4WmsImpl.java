package com.core.scpwms.server.service.imports.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.model.enumerate.EnuFileProcessStatus;
import com.core.business.model.imports.FileImport;
import com.core.business.model.imports.FileImportRule;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuImportRuleType;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.file.FileImport4Wms;
import com.core.scpwms.server.model.file.FileImportRule4Wms;
import com.core.scpwms.server.service.imports.ImportFileRuleManager4Wms;

public class ImportFileRuleManager4WmsImpl extends DefaultBaseManager implements ImportFileRuleManager4Wms, ApplicationContextAware { 
	public ApplicationContext ac;

	@Override
    public void save(FileImportRule4Wms importRule) {
		if( importRule.getWh() != null && importRule.getWh().getId() == null ){
			importRule.setWh(null);
		}
		
		if( importRule.getOwner() != null && importRule.getOwner().getId() == null ){
			importRule.setOwner(null);
		}
		
        if (importRule.isNew()) {
        	importRule.getFileImportRule().setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
        	importRule.getFileImportRule().setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }
        
        this.commonDao.store(importRule.getFileImportRule());
        this.commonDao.store(importRule);
    }

    @Override
    public void updateImportFileStatus(Long id) {
        FileImport fi = this.commonDao.load(FileImport.class, id);
        
        if( !EnuFileProcessStatus.UPLOADED.equals(fi.getStatus()) 
        		&& !EnuFileProcessStatus.FAILED.equals(fi.getStatus()) ){
        	throw new BusinessException(ExceptionConstant.ERROR_STATUS);
        }
        
        if (fi.getImportRule() == null) {
            throw new BusinessException("file.import.rule.notfound");
        }

        fi.setStatus(EnuFileProcessStatus.PROCESSING);
        this.commonDao.store(fi);
    }
    
    @Override
    public void updateImportFileToStatus(Long id, String fileStatus) {
        FileImport fi = this.commonDao.load(FileImport.class, id);
        fi.setStatus(fileStatus);
        fi.setWarnning(Boolean.FALSE);
        fi.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        this.commonDao.store(fi);
    }

    @Override
    public void updateImportFileToEndStatus(Long id) {
        FileImport fi = this.commonDao.load(FileImport.class, id);
        fi.setStatus(EnuFileProcessStatus.COMPLETE);
        fi.setWarnning(Boolean.FALSE);
        fi.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        this.commonDao.store(fi);
    }

    @Override
    public void updateImportFileToEndStatusWithError(Long id) {
        FileImport fi = this.commonDao.load(FileImport.class, id);
        fi.setStatus(EnuFileProcessStatus.FAILED);
        fi.setWarnning(Boolean.TRUE);
        fi.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        this.commonDao.store(fi);
    }

    @Override
    public void updateImportFileToForceEndStatus(Long id) {
        FileImport fi = this.commonDao.load(FileImport.class, id);
        fi.setStatus(EnuFileProcessStatus.FORCE_COMPLETE);
        fi.setWarnning(Boolean.TRUE);
        fi.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        this.commonDao.store(fi);
    }

    @Override
    public void deleteImportFile(Long id) {
        FileImport fi = this.commonDao.load(FileImport.class, id);
        fi.setStatus(EnuFileProcessStatus.DELETE);
        fi.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        this.commonDao.store(fi);
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.ac = arg0;
    }

    public Map executeWithoutProcessException(String actionName, String methodName, Long fileId) {
        Object targetAction = ac.getBean(actionName);
        Map methodRet = null;
        try {
            Method method = MethodUtils.getAccessibleMethod(targetAction.getClass(), methodName, Long.class);
            methodRet = (Map) method.invoke(targetAction, new Object[] { fileId });
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof InvocationTargetException) {
            }
            if (logger.isErrorEnabled()) {
                logger.error(e);
            }
        }
        return methodRet;
    }

    @Override
    public Map<String, String> dealFile(Long id) {
        FileImport fi = this.commonDao.load(FileImport.class, id);
        
        return executeWithoutProcessException(fi.getImportRule().getManageName(), fi.getImportRule().getMethodName(), fi.getId());
    }
    
    @Override
    public Map<String, String> cancelFile(Long id) {
        FileImport fi = this.commonDao.load(FileImport.class, id);
        
        return executeWithoutProcessException(fi.getImportRule().getManageName(), fi.getImportRule().getMethodName() + "Cancel", fi.getId());
    }

    @Override
    public Map<String, String> forceDealFile(Long id) {
        FileImport fi = this.commonDao.load(FileImport.class, id);
        
        return executeWithoutProcessException(fi.getImportRule().getManageName(), fi.getImportRule().getMethodName() + "Force", fi.getId());
    }

    @Override
    public Map initClientData4Mst(Map params) {
    	return initClientData(EnuImportRuleType.MASTER);
    }
    
    @Override
    public Map initClientData4Asn(Map params) {
    	return initClientData(EnuImportRuleType.ASN);
    }
    
    @Override
    public Map initClientData4Obd(Map params) {
        return initClientData(EnuImportRuleType.OBD);
    }
    
    private Map initClientData( String ruleType ){
    	Map result = new HashMap();
        Map valueMap = new HashMap();
        
        String hql = "select rule.id from FileImportRule4Wms rule left join rule.wh wh where 1=1 "
        		+ " and rule.fileImportRule.disabled = false "
        		+ " and rule.fileImportRule.ruleType = :ruleType "
        		+ " and (wh.id is null or wh.id = :whId) "
        		+ " order by rule.fileImportRule.ruleCode ";
        List<Long> ruleIds = this.commonDao.findByQuery(hql, new String[]{ "ruleType", "whId"}, new Object[]{ruleType, WarehouseHolder.getWarehouse().getId()});
        for (Long ruleId : ruleIds) {
        	FileImportRule4Wms rule = commonDao.load(FileImportRule4Wms.class, ruleId);
            valueMap.put(rule.getId().toString(), rule.getFileImportRule().getRuleName());
        }
        result.put("valueMap", valueMap);

        return result;
    }
    

    @Override
    public Map updateMasterFileInfo(Map params) {

        Long ruleId = 0L;
        try {
            ruleId = Long.parseLong((String) params.get("RULE_SELECT"));
        } catch (Exception ex) {
        }

        FileImport4Wms fi = new FileImport4Wms();
        fi.setFileImport(new FileImport());

        if (ruleId > 0) {
        	FileImportRule4Wms fileImportRule = this.commonDao.load(FileImportRule4Wms.class, ruleId);
        	fi.setWh(WarehouseHolder.getWarehouse());
        	fi.setOwner(fileImportRule.getOwner());
            fi.getFileImport().setImportRule(fileImportRule.getFileImportRule());
        }
        
        String orginalName = (String) params.get("ORG_NAME");
        if( orginalName.lastIndexOf(File.separator) >=0 ){
        	orginalName = orginalName.substring(orginalName.lastIndexOf(File.separator) + 1, orginalName.length());
        }
        
        fi.getFileImport().setOrginalName(orginalName);
        fi.getFileImport().setTargetName((String) params.get("TARGET_NAME"));
        fi.getFileImport().setCreateInfo(new CreateInfo(UserHolder.getUser()));
        fi.getFileImport().setStatus(EnuFileProcessStatus.UPLOADED);
        
        this.commonDao.store(fi.getFileImport());
        this.commonDao.store(fi);

        return initClientData4Mst(params);
    }

	@Override
	public void saveOutputFile(Long ruleId) {
		FileImportRule4Wms fileImportRuleW = commonDao.load(FileImportRule4Wms.class, ruleId);
		FileImportRule fileImportRule = fileImportRuleW.getFileImportRule();
				
        FileImport f = new FileImport();
        f.setImportRule(fileImportRule);
        f.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        f.setStatus(EnuFileProcessStatus.UPLOADED);
        commonDao.store(f);
        
        FileImport4Wms fi = new FileImport4Wms();
        fi.setFileImport(f);
        fi.setWh(fileImportRuleW.getWh());
    	fi.setOwner(fileImportRuleW.getOwner());
    	
        commonDao.store(fi);
	}
}
