package com.core.scpwms.server.service.setting.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.model.lot.LotEnumerate;
import com.core.scpwms.server.model.lot.LotEnumerateDetail;
import com.core.scpwms.server.service.setting.LotEnumerateManager;

/**
 * 
 * <p>
 * 批次枚举维护实现类。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 吴 <br/>
 *         修改履历 <br/>
 *         2013/02/27 : MBP 吴: 初版创建<br/>
 */
@SuppressWarnings("all")
public class LotEnumerateManagerImpl extends DefaultBaseManager implements LotEnumerateManager {

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.setting.LotEnumerateManager#saveLotEnumerate(com.core.scpwms.server.model.lot.LotEnumerate)
     */
    @Override
    public void saveLotEnumerate(LotEnumerate lotEnumerate) {
        // 新建批次
        if (lotEnumerate.isNew()) {
            // 判断是否重复记录
            StringBuffer hql = new StringBuffer("from LotEnumerate le where lower(le.code) = :code");

            List<LotEnumerate> les = commonDao.findByQuery(hql.toString(), new String[] { "code" },
                    new Object[] { lotEnumerate.getCode().toLowerCase() });

            if (!les.isEmpty()) {
                throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
            }
            lotEnumerate.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
            lotEnumerate.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }
        this.getCommonDao().store(lotEnumerate);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.setting.LotEnumerateManager#deleteLotEnumerate(java.util.List)
     */
    @Override
    public void deleteLotEnumerate(List<Long> ids) {
        for (Long id : ids) {
            this.commonDao.delete(this.commonDao.load(LotEnumerate.class, id));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.setting.LotEnumerateManager#saveDetail(java.lang.Long,
     *      com.core.scpwms.server.model.lot.LotEnumerateDetail)
     */
    @Override
    public void saveDetail(Long id, LotEnumerateDetail detail) {
        LotEnumerate lotEnum = commonDao.load(LotEnumerate.class, id);
        if (detail.isNew()) {
            detail.setLotEnumerate(lotEnum);
        }
        commonDao.store(detail);
        lotEnum.getDetails().add(detail);
        commonDao.store(lotEnum);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.setting.LotEnumerateManager#removeDetail(java.util.List)
     */
    @Override
    public void removeDetail(List<Long> ids) {
        for (Long id : ids) {
            LotEnumerateDetail detail = load(LotEnumerateDetail.class, id);
            if (detail.getLotEnumerate().getId() != null) {
                LotEnumerate lotEnmu = load(LotEnumerate.class, detail.getLotEnumerate().getId());
                lotEnmu.getDetails().remove(detail);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.setting.LotEnumerateManager#disableLLotEnumerate(java.util.List)
     */
    @Override
    public void disableLLotEnumerate(List<Long> ids) {
        for (Long id : ids) {
            LotEnumerate loteId = this.commonDao.load(LotEnumerate.class, id);
            if (loteId.getDisabled().equals(false)) {
                loteId.setDisabled(true);
                this.commonDao.store(loteId);
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.core.scpwms.server.service.setting.LotEnumerateManager#enableLotEnumerate(java.util.List)
     */
    @Override
    public void enableLotEnumerate(List<Long> ids) {
        for (Long id : ids) {
            LotEnumerate loteId = this.commonDao.load(LotEnumerate.class, id);
            if (loteId.getDisabled().equals(true)) {
                loteId.setDisabled(false);
                this.commonDao.store(loteId);
            }
        }
    }

}
