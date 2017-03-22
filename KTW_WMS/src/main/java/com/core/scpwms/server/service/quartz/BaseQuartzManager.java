package com.core.scpwms.server.service.quartz;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.quartz.BaseQuartzSetting;

/**
 * 用来处理定时任务的数据库访问
 */
@Transactional(readOnly = true)
public interface BaseQuartzManager extends BaseManager {

    @Transactional
    public void updateJobStatus(Long id, Long jobStatus, Boolean trackingDate);

    @Transactional
    public void resetBaseDataJob(BaseQuartzSetting qz);

    @Transactional
    public void enableBaseDataJob(Long id);

    @Transactional
    public void disableBaseDataJob(Long id);

    @Transactional
    public void delete(List<Long> ids);

    @Transactional
    public void clearJobStatus(List<Long> ids);

    @Transactional
    public void saveExeucteLog(Long id, List<String[]> infos);

}
