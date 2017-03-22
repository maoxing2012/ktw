package com.core.scpwms.server.service.rules;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.rules.PickUpRule;

/**
 * 拣选规则管理类
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@Transactional (readOnly=true)
public interface PickUpRuleManager extends BaseManager {

	/**
	 * 删除拣选规则
	 * @param doc
	 */
	@Transactional
	public void deleteRules(List<Long> ids);
	
	/**
	 * 验证并保存拣选规则
	 * @param doc
	 */
	@Transactional
	public void savePickUpRuleInfo(PickUpRule rule);

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
	
}
