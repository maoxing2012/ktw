package com.core.scpwms.server.service.accessory;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.accessory.Accessory;

/**
 * 
 * @description 辅料商品维护
 * @author      曹国良@mbp
 * @createDate  2014-8-11
 * @version     V1.0
 *
 */
@Transactional(readOnly = false)
public interface AccessorySkuManager extends BaseManager {

    /**
     * 
     * 新增修改数据
     * 
     * @param accessory
     */
    @Transactional
    void saveAccessorySku(Accessory accessory);

    /**
     * 
     * 删除数据
     * 
     * @param ids
     */
    @Transactional
    void deleteAccessorySku(List<Long> ids);

    /**
     * 
     * 生效
     * 
     * @param ids
     */
    @Transactional
    void enableAccessorySku(List<Long> ids);

    /**
     * 
     * 失效
     * 
     * @param ids
     */
    @Transactional
    void disableAccessorySku(List<Long> ids);

}
