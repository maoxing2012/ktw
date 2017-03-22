/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: MyQuartzJobBean.java
 */

package com.core.scpwms.server.service.quartz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.core.scpwms.server.enumerate.EnuJobStatus;
import com.core.scpwms.server.model.quartz.BaseQuartzSetting;
import com.core.scpwms.server.model.quartz.QuartzExecuteLog;
import com.core.scpwms.server.util.SpringUtils;

/**
 * <p>请添加注释</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/15<br/>
 */
public class MyQuartzJobBean extends QuartzJobBean {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        BaseQuartzSetting targetBean = (BaseQuartzSetting) context.getMergedJobDataMap().get("targetObject");

        if (targetBean == null)
            return;

        SpringUtils.invokeBeanMethod("baseQuartzManager", "updateJobStatus", new Object[] { targetBean.getId(),
                EnuJobStatus.S001, Boolean.TRUE });

        String[] params = targetBean.getParams() == null ? new String[] {} : targetBean.getParams().split(",");
        Object result = SpringUtils.invokeBeanMethod(targetBean.getManagerId(), targetBean.getMethodId(), params);

        List<String[]> resultStr = new ArrayList<String[]>();
        if (result instanceof List) {
            resultStr = (List<String[]>) result;
        } else {
            resultStr.add(new String[] { "executed." });
        }

        SpringUtils.invokeBeanMethod("baseQuartzManager", "saveExeucteLog", new Object[] { targetBean.getId(),
                resultStr });

        SpringUtils.invokeBeanMethod("baseQuartzManager", "updateJobStatus", new Object[] { targetBean.getId(),
                EnuJobStatus.S000, Boolean.FALSE });

    }
}
