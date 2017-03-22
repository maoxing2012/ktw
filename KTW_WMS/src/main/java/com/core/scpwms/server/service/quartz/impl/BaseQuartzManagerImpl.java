package com.core.scpwms.server.service.quartz.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuCronExpression;
import com.core.scpwms.server.enumerate.EnuJobStatus;
import com.core.scpwms.server.enumerate.EnuTrigger;
import com.core.scpwms.server.model.quartz.BaseQuartzSetting;
import com.core.scpwms.server.model.quartz.QuartzExecuteLog;
import com.core.scpwms.server.service.quartz.BaseQuartzManager;

public class BaseQuartzManagerImpl extends DefaultBaseManager implements BaseQuartzManager {

    /** Commons logger. */
    private static final Log LOGGER = LogFactory.getLog(BaseQuartzManagerImpl.class);

    @Override
    public void disableBaseDataJob(Long id) {

        BaseQuartzSetting dbEntity = this.commonDao.load(BaseQuartzSetting.class, id);
        if (EnuJobStatus.S001 == dbEntity.getJobStatus()) {
            // nothing to do
        } else {
            dbEntity.setDisabled(Boolean.TRUE);
            this.commonDao.store(dbEntity);
        }
    }

    @Override
    public void enableBaseDataJob(Long id) {
        BaseQuartzSetting dbEntity = this.commonDao.load(BaseQuartzSetting.class, id);
        dbEntity.setDisabled(Boolean.FALSE);
        this.commonDao.store(dbEntity);
    }

    @Override
    public synchronized void resetBaseDataJob(BaseQuartzSetting qz) {
        if (qz.isNew()) {
            String hql = "select id from BaseQuartzSetting where quartzId = :quartzId";
            List<Long> ids = commonDao.findByQuery(hql, "quartzId", qz.getQuartzId());

            if (!ids.isEmpty()) {
                throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
            }
            qz.setJobStatus(EnuJobStatus.S000);
            qz.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        } else {
            qz.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
        }
        this.commonDao.store(qz);
    }

    @Override
    public void updateJobStatus(Long id, Long jobStatus, Boolean trackingDate) {

        BaseQuartzSetting dbEntity = this.commonDao.load(BaseQuartzSetting.class, id);

        dbEntity.setJobStatus(jobStatus);
        if (trackingDate) {
            dbEntity.setLastExecuteDate(new Date());
        }
        this.commonDao.store(dbEntity);
    }

    @Override
    public void saveExeucteLog(Long id, List<String[]> infoList) {
        BaseQuartzSetting dbEntity = this.commonDao.load(BaseQuartzSetting.class, id);

        Date now = new Date();
        for (String[] infos : infoList) {
            QuartzExecuteLog log = new QuartzExecuteLog();
            log.setBaseQuartzSetting(dbEntity);
            log.setInsertDate(now);
            log.setInfo(infos);
            commonDao.store(log);
        }
    }

    @Override
    public void clearJobStatus(List<Long> ids) {
        for (Long id : ids) {
            BaseQuartzSetting dbEntity = this.commonDao.load(BaseQuartzSetting.class, id);
            if (EnuJobStatus.S001.equals(dbEntity.getJobStatus())) {
                dbEntity.setJobStatus(EnuJobStatus.S000);
            }
        }
    }

    @Override
    public void delete(List<Long> ids) {
        for (Long id : ids) {
            BaseQuartzSetting dbEntity = this.commonDao.load(BaseQuartzSetting.class, id);
            if (dbEntity.getDisabled().booleanValue()) {
                commonDao.delete(dbEntity);
            }
        }

    }
}
