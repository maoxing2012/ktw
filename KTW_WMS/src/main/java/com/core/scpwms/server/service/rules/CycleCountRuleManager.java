package com.core.scpwms.server.service.rules;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.rules.CycleCountRule;

/**
 * 
 * <p>循环盘点策略管理</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/13<br/>
 */
@Transactional(readOnly = true)
public interface CycleCountRuleManager extends BaseManager {

    /**
     * 
     * <p>保存</p>
     *
     * @param rule
     */
    @Transactional
    void store(CycleCountRule rule);

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
