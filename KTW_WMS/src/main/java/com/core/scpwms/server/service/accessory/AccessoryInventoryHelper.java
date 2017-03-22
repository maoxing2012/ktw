package com.core.scpwms.server.service.accessory;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.accessory.Accessory;
import com.core.scpwms.server.model.accessory.AccessoryInventory;
import com.core.scpwms.server.model.accessory.AccessoryInventoryHistory;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 
 * <p>辅料的库存辅助类</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/08<br/>
 */
@Transactional(readOnly = false)
public interface AccessoryInventoryHelper extends BaseManager {

    /**
     * 
     * <p>在指定库位增加库存</p>
     *
     * @param acc
     * @param bin
     * @param qty
     * @param status
     * @return
     */
    @Transactional
    public AccessoryInventory addInvToBin(Accessory acc, Bin bin, double qty, String status);

    /**
     * 
     * <p>扣减库存，但不扣减分配数</p>
     *
     * @param inv
     * @param qty
     */
    @Transactional
    public void removeInv(AccessoryInventory inv, Double qty);

    /**
     * 
     * <p>取得库存</p>
     *
     * @param accId
     * @param binId
     * @param status
     * @return
     */
    @Transactional
    public AccessoryInventory getInv(Long accId, Long binId, String status);

    /**
     * 
     * <p>辅料库存履历</p>
     *
     * @param inv
     * @param qty
     * @param hisType
     * @param orderSeq
     * @param memo
     * @param department
     * @param laborNames
     * @return
     */
    @Transactional
    AccessoryInventoryHistory saveAccessoryInventoryHistory(AccessoryInventory inv, Double qty, String hisType,
            String orderSeq, String memo, String department, String laborNames);

}
