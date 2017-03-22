/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: AccessoryInventoryHelperImpl.java
 */

package com.core.scpwms.server.service.accessory.impl;

import java.util.Date;
import java.util.List;

import com.core.business.model.domain.CreateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.accessory.Accessory;
import com.core.scpwms.server.model.accessory.AccessoryInventory;
import com.core.scpwms.server.model.accessory.AccessoryInventoryHistory;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.service.accessory.AccessoryInventoryHelper;

/**
 * <p>辅料的库存辅助类</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/08<br/>
 */
public class AccessoryInventoryHelperImpl extends DefaultBaseManager implements AccessoryInventoryHelper {

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.common.AccessoryInventoryHelper#addInvToBin(com.core.scpwms.server.model.common.Accessory,
     *      com.core.scpwms.server.model.warehouse.Bin, double,
     *      java.lang.String)
     */
    @Override
    public AccessoryInventory addInvToBin(Accessory acc, Bin bin, double qty, String status) {
        AccessoryInventory ai = getInv(acc.getId(), bin.getId(), status);

        if (ai != null) {
            ai.addQty(qty);
            commonDao.store(ai);
            return ai;
        }

        AccessoryInventory newAInv = new AccessoryInventory();
        newAInv.setAccessory(acc);
        newAInv.setBaseQty(qty);
        newAInv.setBin(bin);
        newAInv.setWh(WarehouseHolder.getWarehouse());
        newAInv.setStatus(status);
        this.commonDao.store(newAInv);

        return newAInv;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.common.AccessoryInventoryHelper#getInv(java.lang.Long,
     *      java.lang.Long, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public AccessoryInventory getInv(Long accId, Long binId, String status) {
        String hql = "FROM AccessoryInventory ai WHERE ai.accessory.id = :accId AND ai.bin.id = :binId AND ai.status = :status";
        List<AccessoryInventory> list = commonDao.findByQuery(hql, new String[] { "accId", "binId", "status" },
                new Object[] { accId, binId, status });

        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.common.AccessoryInventoryHelper#removeInv(com.core.scpwms.server.model.inventory.Inventory,
     *      java.lang.Double)
     */
    @Override
    public void removeInv(AccessoryInventory inv, Double qty) {
        if (inv != null && qty != null) {
            inv.removeQty(qty.doubleValue());
            commonDao.store(inv);

            if (inv.getBaseQty().doubleValue() <= 0) {
                commonDao.delete(inv);
            }
        }
    }

    @Override
    public AccessoryInventoryHistory saveAccessoryInventoryHistory(AccessoryInventory inv, Double qty, String hisType,
            String orderSeq, String memo, String department, String laborNames) {

        AccessoryInventoryHistory invHistory = new AccessoryInventoryHistory();

        invHistory.setWh(inv.getWh());
        invHistory.setAccessory(inv.getAccessory());
        invHistory.setBin(inv.getBin());
        invHistory.setStatus(inv.getStatus());
        invHistory.setQty(qty);
        invHistory.setHisType(hisType);
        invHistory.setOrderSeq(orderSeq);
        invHistory.setDepartment(department);
        invHistory.setOperateTime(new Date());
        invHistory.setLaborNames(laborNames);
        invHistory.setDescription(memo);

        invHistory.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        commonDao.store(invHistory);

        return invHistory;
    }

}
