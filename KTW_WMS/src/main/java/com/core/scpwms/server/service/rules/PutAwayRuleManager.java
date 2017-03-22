package com.core.scpwms.server.service.rules;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.rules.PutAwayRule;

/**
 * 上架规则管理类
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@Transactional (readOnly=true)
public interface PutAwayRuleManager extends BaseManager {

	/**
	 * 删除上架规则
	 * @param ids
	 */
	@Transactional
	public void deleteRules(List<Long> ids);
	
	/**
	 * 验证并保存上架规则
	 * @param rule
	 * @param orderTypes
	 */
	@Transactional
	public void savePutAwayRuleInfo(PutAwayRule rule);

	/**
	 * 生效上架规则
	 * @param ids
	 */
	@Transactional
	public void activePutAwayRuleInfo(List<Long> ids);
	
	/**
	 * 失效上架规则
	 * @param ids
	 */
	@Transactional
	public void unActivePutAwayRuleInfo(List<Long> ids);
	
}
