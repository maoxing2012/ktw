/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : PlantManager.java
 */

package com.core.scpwms.server.service.user;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.user.Plant;

/**
 * 
 * <p>公司设定</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/01<br/>
 */
@Transactional(readOnly = false)
public interface PlantManager extends BaseManager {

    /**
     * 
     * <p>保存货主信息</p>
     *
     * @param plant
     */
    @Transactional
    void savePlant(Plant plant);

    /**
     * 
     * <p>删除货主信息</p>
     *
     * @param plantIds
     */
    @Transactional
    void deletePlant(List<Long> plantIds);

    /**
     * 
     * <p>生效货主信息</p>
     *
     * @param plantIds
     */
    @Transactional
    void active(List<Long> plantIds);

    /**
     * 
     * <p>失效货主信息</p>
     *
     * @param plantIds
     */
    @Transactional
    void unActive(List<Long> plantIds);

}
