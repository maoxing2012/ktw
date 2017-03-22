/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : ContainerManagerImpl.java
 */

package com.core.scpwms.server.service.sku.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.Container;
import com.core.scpwms.server.service.sku.ContainerManager;

/**
 * @description   包材管理实现类
 * @author         MBP:xiaoyan<br/>
 * @createDate    2013-2-21
 * @version        V1.0<br/>
 */
public class ContainerManagerImpl extends DefaultBaseManager implements ContainerManager {

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.ContainerManager#deleteContainer(java.util.List)
     */
    @Override
    public void deleteContainer(List<Long> ids) {
        for (Long id : ids) {
            delete(load(Container.class, id));
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.ContainerManager#disableContainer(java.util.List)
     */
    @Override
    public void disableContainer(List<Long> ids) {
        for (Long id : ids) {
            Container container = commonDao.load(Container.class, id);
            container.setDisabled(Boolean.TRUE);
            container.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            commonDao.store(container);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.ContainerManager#enableContainer(java.util.List)
     */
    @Override
    public void enableContainer(List<Long> ids) {
        for (Long id : ids) {
            Container container = commonDao.load(Container.class, id);
            container.setDisabled(Boolean.FALSE);
            container.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
            commonDao.store(container);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.warehouse.ContainerManager#saveContainer(com.core.scpwms.server.model.common.Container)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void saveContainer(Container cantainer) {
        if (cantainer.isNew()) {
            String hql = "from Container c where c.code=:code ";
            List<Container> containerList = this.commonDao.findByQuery(hql, new String[] { "code" },
                    new Object[] { cantainer.getCode() });
            if (containerList != null && containerList.size() > 0) {
                throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
            }

            cantainer.setWarehouse(WarehouseHolder.getWarehouse());
            cantainer.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
            cantainer.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }
        commonDao.store(cantainer);
    }

}
