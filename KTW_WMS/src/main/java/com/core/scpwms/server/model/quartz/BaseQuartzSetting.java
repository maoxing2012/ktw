package com.core.scpwms.server.model.quartz;

import java.util.Date;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.enumerate.EnuCronExpression;
import com.core.scpwms.server.enumerate.EnuJobStatus;

/**
 * 
 * <p>定时任务设置</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/09<br/>
 */
public class BaseQuartzSetting extends TrackingEntity {

    /** QuarzId*/
    private String quartzId;

    /** 任务描述*/
    private String quartzDesc;

    /** 任务Manager*/
    private String managerId;

    /** 任务Method*/
    private String methodId;

    /** 参数 */
    private String params;

    /** 上一次执行时间 */
    private Date lastExecuteDate;

    /** 
     * 执行频率设定 
     * @see EnuCronExpression
     * */
    private String cronExpression;

    /** 是否失效 */
    private Boolean disabled = Boolean.TRUE;

    /** 
     * JOB执行状态
     * @see EnuJobStatus
     * */
    private Long jobStatus;

    public String getQuartzId() {
        return quartzId;
    }

    public void setQuartzId(String quartzId) {
        this.quartzId = quartzId;
    }

    public String getQuartzDesc() {
        return quartzDesc;
    }

    public void setQuartzDesc(String quartzDesc) {
        this.quartzDesc = quartzDesc;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public Date getLastExecuteDate() {
        return lastExecuteDate;
    }

    public void setLastExecuteDate(Date lastExecuteDate) {
        this.lastExecuteDate = lastExecuteDate;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Long getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Long jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getTriggerName() {
        return this.getQuartzId() + "Trigger";
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}
