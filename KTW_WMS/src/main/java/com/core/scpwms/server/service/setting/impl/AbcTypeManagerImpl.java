package com.core.scpwms.server.service.setting.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.AbcProperties;
import com.core.scpwms.server.service.setting.AbcTypeManager;

/**
 * 
 * <p>
 * ABC分类设定实现类。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 戴 <br/>
 *         修改履历 <br/>
 *         2013/02/21 : MBP 戴: 初版创建<br/>
 */
public class AbcTypeManagerImpl extends DefaultBaseManager implements AbcTypeManager {

    /**
     * 
     * <p>
     * 删除一个或多个ABC分类设定.
     * </p>
     * 
     * @param ids ABC分类设定id集合
     */
    @Override
    public void deleteAbc(List<Long> ids) {

        for (Long id : ids) {
            AbcProperties abc = this.commonDao.load(AbcProperties.class, id);
            this.commonDao.delete(abc);
        }
    }

    /**
     * 
     * <p>
     * 新建或者修改一个ABC分类设定.
     * </p>
     * 
     * @param AbcProperties ABC分类设定
     */
    @Override
    public void saveAbc(AbcProperties abc) {

        abc.setWarehouse(WarehouseHolder.getWarehouse());

        if (abc.isNew()) {
            abc.setCreateInfo(new CreateInfo(UserHolder.getUser()));

            // 验证唯一建
            Long whId = abc.getWarehouse().getId();
            Long pId = abc.getPlant().getId();
            Long invId = (Long) commonDao.findByQueryUniqueResult(
                    "select abc.id from AbcProperties abc where abc.warehouse.id=:whId and abc.plant.id=:pId and abc.code=:code",
                    new String[] { "whId", "pId", "code" }, new Object[] { whId, pId, abc.getCode() });
            if (invId == null) {
                this.commonDao.store(abc);
            } else {
                throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
            }

        } else {
            abc.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }

        this.commonDao.store(abc);

    }

}
