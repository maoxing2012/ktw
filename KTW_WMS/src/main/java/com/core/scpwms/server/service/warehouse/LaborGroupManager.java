package com.core.scpwms.server.service.warehouse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.warehouse.LaborGroup;

/**
 * 
 * @description    作业角色（作业组） 接口
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-1-2
 * @version        V1.0<br/>
 */
@Transactional(readOnly = false)
public interface LaborGroupManager extends BaseManager {

    /**
     * 
     * <p>保存</p>
     *
     * @param laborGroup
     */
    @Transactional
    void store(LaborGroup laborGroup);

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
