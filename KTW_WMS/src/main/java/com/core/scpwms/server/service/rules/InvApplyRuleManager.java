package com.core.scpwms.server.service.rules;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.rules.InventoryApplyRule;

/**
 * 
 * <p>补货策略</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/06/24<br/>
 */
public interface InvApplyRuleManager extends BaseManager {
	
    /**
     * 
     * <p>新建</p>
     *
     * @param rule
     */
	@Transactional
	public void saveRule(InventoryApplyRule rule);
	
	/**
	 * 生效拣选规则
	 * @param doc
	 */
	@Transactional
	public void activePickUpRuleInfo(List<Long> ids);
	
	/**
	 * 失效拣选规则
	 * @param doc
	 */
	@Transactional
	public void unActivePickUpRuleInfo(List<Long> ids);
	
	/**
	 * 删除拣选规则
	 * @param doc
	 */
	@Transactional
	public void deleteRules(List<Long> ids);

}
