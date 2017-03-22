package com.core.scpwms.server.service.accessory.impl;

import com.core.business.exception.BusinessException;
import com.core.business.utils.DoubleUtil;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuInvenHisType;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.accessory.Accessory;
import com.core.scpwms.server.model.accessory.AccessoryInventory;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.service.accessory.AccessoryInventoryHelper;
import com.core.scpwms.server.service.accessory.AccessoryInventoryManager;
import com.core.scpwms.server.service.common.BCManager;

/**
 * 
 * @description 辅料出入库管理
 * @author      曹国良@mbp
 * @createDate  2014-8-13
 * @version     V1.0
 *
 */
public class AccessoryInventoryManagerImpl extends DefaultBaseManager implements AccessoryInventoryManager {

    private AccessoryInventoryHelper aInvHelper;

    public AccessoryInventoryHelper getaInvHelper() {
        return aInvHelper;
    }

    public void setaInvHelper(AccessoryInventoryHelper aInvHelper) {
        this.aInvHelper = aInvHelper;
    }

	@Override
    public void saveAiBin(Long aInvId, Long binId, Double moveQty, Long laborId, String description) {
        if (moveQty == null || moveQty.doubleValue() <= 0)
            return;

        AccessoryInventory oldInv = commonDao.load(AccessoryInventory.class, aInvId);
        if (oldInv.getBaseQty().doubleValue() < moveQty.doubleValue()) {
            throw new BusinessException("移動数>在庫数");
        }

        // 源库存(-)
        aInvHelper.removeInv(oldInv, moveQty);

        Labor labor = commonDao.load(Labor.class, laborId);
        // 库存履历(-)
        aInvHelper.saveAccessoryInventoryHistory(oldInv, moveQty * (-1), EnuInvenHisType.MOVE, null, description, null, labor.getName());

        // 目标库存(+)
        Bin bin = this.commonDao.load(Bin.class, binId);
        AccessoryInventory newInv = aInvHelper.addInvToBin(oldInv.getAccessory(), bin, moveQty.doubleValue(), oldInv.getStatus());

        // 库存履历(+)
        aInvHelper.saveAccessoryInventoryHistory(newInv, moveQty, EnuInvenHisType.MOVE, null, description, null, labor.getName());
    }

    @Override
    public void saveAiStatus(Long aInvId, Double transQty, String description, String status) {
        if (transQty == null || transQty.doubleValue() <= 0)
            return;

        AccessoryInventory oldInv = this.commonDao.load(AccessoryInventory.class, aInvId);

        if (status.equals(oldInv.getStatus()))
            return;

        if (oldInv.getBaseQty().doubleValue() < transQty.doubleValue()) {
            throw new BusinessException("調整数>在庫数");
        }

        // 源库存(-)
        aInvHelper.removeInv(oldInv, transQty);

        // 库存履历(-)
        aInvHelper.saveAccessoryInventoryHistory(oldInv, transQty * (-1), EnuInvenHisType.CHANGE_STATUS, null, description, null, null);

        // 目录库存(+)
        AccessoryInventory newInv = aInvHelper.addInvToBin(oldInv.getAccessory(), oldInv.getBin(), transQty, status);

        // 库存履历(+)
        aInvHelper.saveAccessoryInventoryHistory(newInv, transQty, EnuInvenHisType.CHANGE_STATUS, null, description, null, null);
    }

    @Override
    public void saveAihInbound(Long skuId, double qty, Long binId, Long laborId, String invStatus, String desc) {
        if( qty <= 0D )
        	return;
    	
    	// 添加库存
    	Accessory acc = commonDao.load(Accessory.class, skuId);
    	Bin bin = commonDao.load(Bin.class, binId);
        AccessoryInventory inv = aInvHelper.addInvToBin(acc, bin, qty, invStatus);

        // 添加履历
        Labor labor = commonDao.load(Labor.class, laborId);
        aInvHelper.saveAccessoryInventoryHistory(inv, qty, EnuInvenHisType.RECEIVING, null, desc, null, labor.getName());
    }

    @Override
    public void saveAihOutbound(Long invId, double qty, Long laborId, String desc) {
    	if( qty <= 0D )
        	return;
    	
    	// 取得库存
        AccessoryInventory inv = commonDao.load(AccessoryInventory.class, invId);

        if (inv == null || DoubleUtil.compareByPrecision(qty, inv.getBaseQty(), 0) > 0) {
        	throw new BusinessException("出庫数>在庫数");
        }

        // 扣减库存
        aInvHelper.removeInv(inv, qty);

        // 添加履历
        Labor labor = commonDao.load(Labor.class, laborId);
        aInvHelper.saveAccessoryInventoryHistory(inv, qty * (-1), EnuInvenHisType.SHIPPING, null, desc, null, labor.getName());
    }

}
