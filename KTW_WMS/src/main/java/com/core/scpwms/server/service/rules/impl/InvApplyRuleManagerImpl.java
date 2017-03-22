package com.core.scpwms.server.service.rules.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.rules.InventoryApplyRule;
import com.core.scpwms.server.service.rules.InvApplyRuleManager;

public class InvApplyRuleManagerImpl extends DefaultBaseManager implements InvApplyRuleManager {

	@SuppressWarnings("unchecked")
	@Override
	public void saveRule(InventoryApplyRule rule) {
		
		if(rule.getDescBinGroup()==null&&rule.getBin()==null){
			throw new BusinessException ("invApplyRule.bin.error");
		}
		
		if(rule.getDescBinGroup().getId()==null){
			rule.setDescBinGroup(null);
		}
		if(rule.getBin().getId()==null){
			rule.setBin(null);
		}
		if(rule.getAbcProperties().getId()==null){
			rule.setAbcProperties(null);
		}
		
		if(rule.getSku().getId()==null){
			rule.setSku(null);
		}
		
		if(rule.isNew()){
			StringBuffer hql = new StringBuffer("from InventoryApplyRule iar where iar.wh=:whId and iar.plant=:plantId and iar.priority=:priority ");
			List<InventoryApplyRule> rules = this.commonDao.findByQuery(hql.toString(), 
					new String[]{"whId","plantId","priority"}, new Object[]{WarehouseHolder.getWarehouse(),rule.getPlant(),rule.getPriority()});
			if(!rules.isEmpty()){
				throw new BusinessException("putAwayRule.error.priority.isExist");
			}
		}else{
			StringBuffer hql = new StringBuffer("from InventoryApplyRule iar where iar.wh=:whId and iar.plant=:plantId and iar.priority=:priority and iar.id <> :iarID ");
			List<InventoryApplyRule> rules = this.commonDao.findByQuery(hql.toString(), 
					new String[]{"whId","plantId","priority","iarID"}, new Object[]{WarehouseHolder.getWarehouse(),rule.getPlant(),rule.getPriority(),rule.getId()});
			if(!rules.isEmpty()){
				throw new BusinessException("putAwayRule.error.priority.isExist");
			}
		}
		if(rule.isNew()){
			
			rule.setWh(WarehouseHolder.getWarehouse());
			rule.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		}else{
			rule.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		commonDao.store(rule);
	}

	@Override
	public void activePickUpRuleInfo(List<Long> ids) {
		for(Long id:ids){
			InventoryApplyRule rule = this.commonDao.load(InventoryApplyRule.class, id);
			rule.setAlive(false);
			this.commonDao.store(rule);
		}	
	}

	@Override
	public void unActivePickUpRuleInfo(List<Long> ids) {
		for(Long id:ids){
			InventoryApplyRule rule = this.commonDao.load(InventoryApplyRule.class, id);
			rule.setAlive(true);
			this.commonDao.store(rule);
		}	
	}

	@Override
	public void deleteRules(List<Long> ids) {
		for(Long id:ids){
			InventoryApplyRule rule = this.commonDao.load(InventoryApplyRule.class, id);
			this.commonDao.delete(rule);
		}
		
	}

}
