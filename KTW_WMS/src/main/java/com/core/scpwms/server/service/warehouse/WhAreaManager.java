/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WhAreaManager.java
 */

package com.core.scpwms.server.service.warehouse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.warehouse.WhArea;

/**
 * @description   库区设定
 * @author         MBP:xiaoyan<br/>
 * @createDate    2013-1-31
 * @version        V1.0<br/>
 */
@Transactional(readOnly = false)
public interface WhAreaManager extends BaseManager {

    /**
     * 
     * <p>新建库区</p>
     *
     * @param whArea
     */
    @Transactional
    void storeWhArea(WhArea whArea);

    /**
     * 
     * <p>删除库区</p>
     *
     * @param whAreaIds
     */
    @Transactional
    void deleteWhArea(List<Long> whAreaIds);

    /**
     * 
     * <p>生效库区</p>
     *
     * @param whAreaIds
     */
    @Transactional
    void active(List<Long> whAreaIds);

    /**
     * 
     * <p>失效库区</p>
     *
     * @param whAreaIds
     */
    @Transactional
    void unActive(List<Long> whAreaIds);

    /**
     * 
     * <p>修改库区</p>
     *
     * @param whArea
     */
    @Transactional
    void modifyWhArea(WhArea whArea);

}
