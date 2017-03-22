/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : LotInfoManager.java
 */

package com.core.scpwms.server.service.setting;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.lot.LotInfo;

/**
 * @description   批次属性设定
 * @author         MBP:毛幸<br/>
 * @createDate    2013-2-20
 * @version        V1.0<br/>
 */
@Transactional(readOnly = false)
public interface LotInfoManager extends BaseManager {

    /**
     * 
     * <p>保存批次属性信息</p>
     *
     * @param lotInfo
     */
    @Transactional
    void saveLotInfo(LotInfo lotInfo);

    /**
     * 
     * <p>删除批次属性信息</p>
     *
     * @param ids
     */
    @Transactional
    void deleteLotInfo(List<Long> ids);

    /**
     * 
     * <p>生效批次属性信息</p>
     *
     * @param ids
     */
    @Transactional
    void enableLotInfo(List<Long> ids);

    /**
     * 
     * <p>失效批次属性信息</p>
     *
     * @param ids
     */
    @Transactional
    void disableLotInfo(List<Long> ids);

    // ActiveT 1304901 2014/07/09
    Map loadLotInfo(Map params);

    // ActiveT 1304901 2014/07/10
    Map loadDetailInfo(Map params);
}
