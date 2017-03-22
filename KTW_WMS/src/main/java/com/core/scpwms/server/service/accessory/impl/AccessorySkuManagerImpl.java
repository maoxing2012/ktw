package com.core.scpwms.server.service.accessory.impl;

import java.util.List;

import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.accessory.Accessory;
import com.core.scpwms.server.service.accessory.AccessorySkuManager;
import com.core.scpwms.server.service.common.BCManager;

/**
 * 
 * @description 辅料商品维护
 * @author      曹国良@mbp
 * @createDate  2014-8-11
 * @version     V1.0
 *
 */
public class AccessorySkuManagerImpl extends DefaultBaseManager implements AccessorySkuManager {

    private BCManager bcManager;

    public BCManager getBcManager() {
        return this.bcManager;
    }

    public void setBcManager(BCManager bcManager) {
        this.bcManager = bcManager;
    }

    @Override
    public void saveAccessorySku(Accessory accessory) {
        if (accessory.getIt1000() != null && accessory.getIt1000().getId() == null) {
            accessory.setIt1000(null);
        }
        if (accessory.getIt2000() != null && accessory.getIt2000().getId() == null) {
            accessory.setIt2000(null);
        }
        if (accessory.getIt3000() != null && accessory.getIt3000().getId() == null) {
            accessory.setIt3000(null);
        }

        if (accessory.isNew()) {
            accessory.setWh(WarehouseHolder.getWarehouse());
            accessory.setCode(bcManager.getAccessoryCode(accessory.getWh().getCode()));// 自动生成编号
            accessory.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
            accessory.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }
        commonDao.store(accessory);
    }

    @Override
    public void deleteAccessorySku(List<Long> ids) {
        for (Long id : ids) {
            delete(load(Accessory.class, id));
        }
    }

    @Override
    public void enableAccessorySku(List<Long> ids) {
        for (Long id : ids) {
            Accessory accessory = this.commonDao.load(Accessory.class, id);
            accessory.setDisabled(Boolean.FALSE);
            accessory.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            this.commonDao.store(accessory);
        }
    }

    @Override
    public void disableAccessorySku(List<Long> ids) {
        for (Long id : ids) {
            Accessory accessory = this.commonDao.load(Accessory.class, id);
            accessory.setDisabled(Boolean.TRUE);
            accessory.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            this.commonDao.store(accessory);
        }
    }

}
