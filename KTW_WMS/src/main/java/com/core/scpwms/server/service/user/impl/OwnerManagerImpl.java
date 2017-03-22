/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : PlantManagerImpl.java
 */

package com.core.scpwms.server.service.user.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.service.user.OwnerManager;

/**
 * @description   货主设定
 * @author         MBP:xiaoyan<br/>
 * @createDate    2013-2-1
 * @version        V1.0<br/>
 */
@SuppressWarnings("all")
public class OwnerManagerImpl extends DefaultBaseManager implements OwnerManager {

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.user.OwnerManager#active(java.util.List)
     */
    @Override
    public void active(List<Long> ownerIds) {
        for (Long id : ownerIds) {
            Owner owner = load(Owner.class, id);
            owner.setDisabled(false);
            commonDao.store(owner);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.user.PlantManager#deletePlant(java.util.List)
     */
    @Override
    public void deleteOwner(List<Long> ownerIds) {
        for (Long id : ownerIds) {
            delete(load(Owner.class, id));
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.user.PlantManager#savePlant(com.core.scpwms.server.model.user.Plant)
     */
    @Override
    public void saveOwner(Owner owner) {
        if (owner.isNew()) {
            String hsql = "from Owner owner where lower(owner.code)=:ownerCode and owner.plant.wh.id = :whId ";
            List<Owner> ownerList = this.commonDao.findByQuery(hsql, new String[] { "ownerCode", "whId" },
                    new Object[] { owner.getCode().toLowerCase(), WarehouseHolder.getWarehouse().getId() });
            if (ownerList != null && ownerList.size() > 0) {
                throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
            }

            owner.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
            owner.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }

        commonDao.store(owner);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.user.PlantManager#unActive(java.util.List)
     */
    @Override
    public void unActive(List<Long> ownerIds) {
        for (Long id : ownerIds) {
            Owner owner = load(Owner.class, id);
            owner.setDisabled(true);
            commonDao.store(owner);
        }

    }

}
