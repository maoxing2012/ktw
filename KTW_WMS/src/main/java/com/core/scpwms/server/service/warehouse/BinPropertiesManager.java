/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : BinPropertiesManager.java
 */

package com.core.scpwms.server.service.warehouse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.warehouse.BinProperties;

/**
 * @description   库位属性设定
 * @author         MBP:毛幸<br/>
 * @createDate    2013/02/18
 * @version        V1.0<br/>
 */
@Transactional(readOnly = false)
public interface BinPropertiesManager extends BaseManager {
    /**
     * 
     * <p>保存库位属性信息</p>
     *
     * @param binProperties
     */
    @Transactional
    void saveBinProperties( BinProperties binProperties);
    
    /**
     * 
     * <p>删除库位属性信息</p>
     *
     * @param binPropertiesIds
     */
    @Transactional
    void deleteBinProperties(List<Long> binPropertiesIds);

    /**
     * 
     * <p>生效库位属性信息</p>
     *
     * @param binPropertiesIds
     */
    @Transactional
    void enableBinProperties(List<Long> binPropertiesIds);
    
    /**
     * 
     * <p>失效库位属性信息</p>
     *
     * @param binPropertiesIds
     */
    @Transactional
    void disableBinProperties(List<Long> binPropertiesIds);
}
