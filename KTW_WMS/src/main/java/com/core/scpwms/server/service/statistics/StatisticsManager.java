/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : CustomerManager.java
 */

package com.core.scpwms.server.service.statistics;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;

/**
 * @description   统计报表
 * @author         MBP:毛幸<br/>
 * @createDate    2013-2-5
 * @version        V1.0<br/>
 */
@Transactional(readOnly = false)
public interface StatisticsManager extends BaseManager {

    /**
     * 
     * <p>库存比例</p>
     *
     * @param customer
     * @param id
     */
    @Transactional
    Map<String, Object> getReport001(Long whId);
    
    /**
     * 
     * <p>作业量统计</p>
     *
     * @param whId
     * @param dateFrom
     * @param dateTo
     * @return
     */
    @Transactional
    List<Map<String, Object>> getReport002(Long whId, String dateFrom, String dateTo);

}
