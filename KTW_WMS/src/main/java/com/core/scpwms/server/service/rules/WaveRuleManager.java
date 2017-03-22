package com.core.scpwms.server.service.rules;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.rules.WaveRule;

/**
 * 
 * @description   波次策略接口
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-1-8
 * @version        V1.0<br/>
 */
@Transactional(readOnly=true)
public interface WaveRuleManager extends BaseManager {
	
	/**
	 * 
	 * <p>保存</p>
	 *
	 * @param wave
	 */
	@Transactional
	void store(WaveRule wave);
	
	/**
	 * 
	 * <p>删除</p>
	 *
	 * @param ids
	 */
	@Transactional
	void delete(List<Long> ids);
	
	/**
	 * 
	 * <p>生效</p>
	 *
	 * @param ids
	 */
	@Transactional
	void enable(List<Long> ids);
	
	/**
	 * 
	 * <p>失效</p>
	 *
	 * @param ids
	 */
	@Transactional
	void disable(List<Long> ids);

}
