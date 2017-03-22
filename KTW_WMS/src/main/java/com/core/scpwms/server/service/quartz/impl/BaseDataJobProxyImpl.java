package com.core.scpwms.server.service.quartz.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.enumerate.EnuJobStatus;
import com.core.scpwms.server.model.quartz.BaseQuartzSetting;
import com.core.scpwms.server.service.quartz.BaseDataJobProxy;
import com.core.scpwms.server.service.quartz.BaseQuartzManager;
import com.core.scpwms.server.util.SpringUtils;

public class BaseDataJobProxyImpl extends DefaultBaseManager implements BaseDataJobProxy {

    /** Commons logger. */
    private static final Log LOGGER = LogFactory.getLog(BaseDataJobProxyImpl.class);

    private Scheduler scheduler;

    private BaseQuartzManager baseQuartzManager;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public BaseQuartzManager getBaseQuartzManager() {
        return baseQuartzManager;
    }

    public void setBaseQuartzManager(BaseQuartzManager baseQuartzManager) {
        this.baseQuartzManager = baseQuartzManager;
    }

    public void init() {
        String hql = " select id from BaseQuartzSetting where disabled = false order by quartzId ";
        List<Long> jobs = commonDao.findByQuery(hql);
        if (!jobs.isEmpty()) {
            for (Long id : jobs) {
                scheduleJob(id);
            }
        }
    }

    @Override
    public void enable(List<Long> ids) {
        baseQuartzManager.enableBaseDataJob(ids.get(0));
        scheduleJob(ids.get(0));
    }

    private void scheduleJob(Long quartzSettingId) {
        BaseQuartzSetting dbEntity = this.commonDao.load(BaseQuartzSetting.class, quartzSettingId);
        try {
            CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger(dbEntity.getTriggerName(),
                    Scheduler.DEFAULT_GROUP);

            if (null == trigger) {
                // Trigger不存在，那么创建一个
                JobDetail jobDetail = new JobDetail(dbEntity.getQuartzId(), Scheduler.DEFAULT_GROUP,
                        MyQuartzJobBean.class);
                jobDetail.getJobDataMap().put("targetObject", dbEntity);

                trigger = new CronTriggerBean();
                trigger.setName(dbEntity.getTriggerName());
                trigger.setCronExpression(dbEntity.getCronExpression());
                trigger.setGroup(Scheduler.DEFAULT_GROUP);
                this.scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // Trigger已存在，那么更新相应的定时设置
                trigger.setCronExpression(dbEntity.getCronExpression());
                this.scheduler.rescheduleJob(trigger.getName(), trigger.getGroup(), trigger);
            }

        } catch (SchedulerException ex) {
            LOGGER.info("in BaseDataJobProxyImpl.enableBaseDataJob... SchedulerException ");
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void disable(List<Long> ids) {
        baseQuartzManager.disableBaseDataJob(ids.get(0));
        try {
            BaseQuartzSetting dbEntity = this.commonDao.load(BaseQuartzSetting.class, ids.get(0));
            if (EnuJobStatus.S000.equals(dbEntity.getJobStatus())) {

                CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger(dbEntity.getTriggerName(),
                        Scheduler.DEFAULT_GROUP);

                if (null != trigger) {
                    this.scheduler.deleteJob(dbEntity.getQuartzId(), Scheduler.DEFAULT_GROUP);
                }
            }

        } catch (SchedulerException ex) {
            LOGGER.info("in BaseDataJobProxyImpl.disableBaseDataJob... SchedulerException ");
            ex.printStackTrace();
        }
    }

    @Override
    public void executeNow(List<Long> ids) {

        BaseQuartzSetting dbEntity = this.commonDao.load(BaseQuartzSetting.class, ids.get(0));

        if (EnuJobStatus.S000.equals(dbEntity.getJobStatus())) {
            baseQuartzManager.updateJobStatus(ids.get(0), EnuJobStatus.S001, Boolean.TRUE);
            String[] params = dbEntity.getParams() == null ? new String[] {} : dbEntity.getParams().split(",");
            Object result = SpringUtils.invokeBeanMethod(dbEntity.getManagerId(), dbEntity.getMethodId(), params);

            List<String[]> resultStr = new ArrayList<String[]>();
            if (result instanceof List) {
                resultStr = (List<String[]>) result;
            } else {
                resultStr.add(new String[] { "executed." });
            }
            SpringUtils.invokeBeanMethod("baseQuartzManager", "saveExeucteLog", new Object[] { dbEntity.getId(),
                    resultStr });

            baseQuartzManager.updateJobStatus(ids.get(0), EnuJobStatus.S000, Boolean.FALSE);
        }
    }
}
