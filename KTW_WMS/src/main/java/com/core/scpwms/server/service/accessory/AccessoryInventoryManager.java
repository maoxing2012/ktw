package com.core.scpwms.server.service.accessory;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.accessory.AccessoryInventory;
import com.core.scpwms.server.model.accessory.AccessoryInventoryHistory;

/**
 * 
 * @description 辅料出入库管理
 * @author      曹国良@mbp
 * @createDate  2014-8-13
 * @version     V1.0
 *
 */
@Transactional(readOnly = false)
public interface AccessoryInventoryManager extends BaseManager {

    /**
     * 
     * 库位调整
     * 
     * @param aInv
     * @param binId
     * @param moveQty
     * @param laborNames
     * @param description
     */
    @Transactional
    void saveAiBin(Long aInvId, Long binId, Double moveQty, Long laborId, String description);

    /**
     * 
     * 库存状态转换
     * 
     * @param aInv
     * @param transQty
     * @param description
     */
    @Transactional
    void saveAiStatus(Long aInvId, Double transQty, String description, String status);

    /**
     * 收货
     * @param skuId
     * @param qty
     * @param binId
     * @param depart
     * @param laborId
     * @param invStatus
     * @param desc
     */
    @Transactional
    void saveAihInbound(Long skuId, double qty, Long binId, Long laborId, String invStatus, String desc);

    /**
     * 出库发货
     * @param skuId
     * @param qty
     * @param binId
     * @param depart
     * @param laborId
     * @param invStatus
     * @param desc
     */
    @Transactional
    void saveAihOutbound(Long aInvId, double qty, Long laborId, String desc);

}
