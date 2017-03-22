package com.core.scpwms.server.service.rules.impl;

import java.util.ArrayList;
import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.AbcProperties;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.PackageInfo;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuType;
import com.core.scpwms.server.model.rules.PickUpRule;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinGroup;
import com.core.scpwms.server.service.rules.PickUpRuleManager;

@SuppressWarnings("all")
public class PickUpRuleManagerImpl extends DefaultBaseManager implements
		PickUpRuleManager {

	@Override
	public void activePickUpRuleInfo(List<Long> ids) {
		
		for(Long id:ids){
			PickUpRule rule = this.commonDao.load(PickUpRule.class, id);
			rule.setDisabled(false);
			this.commonDao.store(rule);
		}		
		
	}

	@Override
	public void deleteRules(List<Long> ids) {
		for(Long id:ids){
			PickUpRule rule = this.commonDao.load(PickUpRule.class, id);
			this.commonDao.delete(rule);
		}
	}

	@Override
	public void savePickUpRuleInfo(PickUpRule rule) {
		//验证规则录入正确
		validatePickUpRuleInfo(rule);
		if(rule.isNew()){
			rule.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			rule.setWh(WarehouseHolder.getWarehouse());
		}else{
			rule.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		this.commonDao.store(rule);
	}
	
	/**
	 * 验证拣选策略录入
	 * @param rule
	 * @return
	 */
	public void validatePickUpRuleInfo(PickUpRule rule){
		
		//验证当前仓库  + 货主 + 优先级是否重复
		String checkHsql = "";
		List<Long> existIds = new ArrayList<Long>();
		if(rule.isNew()){
			checkHsql = "select rule.id from PickUpRule rule where rule.wh.id=:whId and rule.plant.id=:plantId and rule.priority=:priority";
			existIds = (List<Long>)this.commonDao.findByQuery(checkHsql,
					new String[]{"whId","plantId","priority"},
					new Object[]{WarehouseHolder.getWarehouse().getId(),rule.getPlant().getId(),rule.getPriority()});
		}else{
			checkHsql = "select rule.id from PickUpRule rule where rule.wh.id=:whId and rule.plant.id=:plantId and rule.priority=:priority and rule.id <> :existId";
			existIds = (List<Long>)this.commonDao.findByQuery(checkHsql,
					new String[]{"whId","plantId","priority","existId"},
					new Object[]{WarehouseHolder.getWarehouse().getId(),rule.getPlant().getId(),rule.getPriority(),rule.getId()});
		}
		
		if(existIds != null && !existIds.isEmpty()){
			throw new BusinessException("pickUpRule.error.priority.isExist");
		}
		
		//目标库位组和目标库位至少输入一项
		boolean descBinGroupEmpty = false;
		boolean descBinEmpty = false;
		
		if(rule.getDescBin() == null || rule.getDescBin().getId() == null){
			descBinEmpty = true;
		}
		if(rule.getDescBinGroup() == null || rule.getDescBinGroup().getId() == null){
			descBinGroupEmpty = true;
		}
		if(descBinGroupEmpty && descBinEmpty){
			throw new BusinessException("pickUpRule.error.des.isnull");
		}		
		
		//如果启用数量过滤，则必须录入数量开始 和结束
		if(rule.getUseQtyLimit()){
			if(rule.getQtyLimit() == null || rule.getQtyLowerLimit() == null){
				throw new BusinessException("pickUpRule.error.useQty.inputRequired");
			}
		}		
		
		//外键安全性设定
		if(rule.getDescBin() != null && rule.getDescBin().getId() != null){
			rule.setDescBin(this.commonDao.load(Bin.class, rule.getDescBin().getId()));
		}else{
			rule.setDescBin(null);
		}
		
		if(rule.getDescBinGroup() != null && rule.getDescBinGroup().getId() != null){
			rule.setDescBinGroup(this.commonDao.load(BinGroup.class, rule.getDescBinGroup().getId()));
		}else{
			rule.setDescBinGroup(null);
		}
		
		if(rule.getSrcBin() != null && rule.getSrcBin().getId() != null){
			rule.setSrcBin(this.commonDao.load(Bin.class, rule.getSrcBin().getId()));
		}else{
			rule.setSrcBin(null);
		}	
		
		if(rule.getSku() != null && rule.getSku().getId() != null){
			rule.setSku(this.commonDao.load(Sku.class, rule.getSku().getId()));
		}else{
			rule.setSku(null);
		}
		
		if(rule.getPackageInfo() != null && rule.getPackageInfo().getId() != null){
			rule.setPackageInfo(this.commonDao.load(PackageInfo.class, rule.getPackageInfo().getId()));
		}else{
			rule.setPackageInfo(null);
		}		
		
		if(rule.getPackageDetail() != null && rule.getPackageDetail().getId() != null){
			rule.setPackageDetail(this.commonDao.load(PackageDetail.class, rule.getPackageDetail().getId()));
		}else{
			rule.setPackageDetail(null);
		}	
		
		if(rule.getbType() != null && rule.getbType().getId() != null){
			rule.setbType(this.commonDao.load(SkuType.class, rule.getbType().getId()));
		}else{
			rule.setbType(null);
		}
		
		if(rule.getmType() != null && rule.getmType().getId() != null){
			rule.setmType(this.commonDao.load(SkuType.class, rule.getmType().getId()));
		}else{
			rule.setmType(null);
		}		
		
		if(rule.getlType() != null && rule.getlType().getId() != null){
			rule.setlType(this.commonDao.load(SkuType.class, rule.getlType().getId()));
		}else{
			rule.setlType(null);
		}		
		
		if(rule.getAbcProperties() != null && rule.getAbcProperties().getId() != null){
			rule.setAbcProperties(this.commonDao.load(AbcProperties.class, rule.getAbcProperties().getId()));
		}else{
			rule.setAbcProperties(null);
		}	
		
		if(rule.getIot() != null && rule.getIot().getId() != null){
			rule.setIot(this.commonDao.load(OrderType.class, rule.getIot().getId()));
		}else{
			rule.setIot(null);
		}			
		
		
	}
	

	@Override
	public void unActivePickUpRuleInfo(List<Long> ids) {
		
		for(Long id:ids){
			PickUpRule rule = this.commonDao.load(PickUpRule.class, id);
			rule.setDisabled(true);
			this.commonDao.store(rule);
		}		
		
	}

}
