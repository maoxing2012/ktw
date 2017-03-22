package com.core.scpwms.server.service.quartz.impl;

import java.io.Serializable;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.core.scpwms.server.service.quartz.BaseDataJobProxy;

public class InitJobCronTrigger extends CronTriggerBean implements Serializable {

    /** Commons logger. */
    private static final Log LOGGER = LogFactory.getLog(InitJobCronTrigger.class);

    private BaseDataJobProxy baseDataJobProxy;

    public BaseDataJobProxy getBaseDataJobProxy() {
        return this.baseDataJobProxy;
    }

    public void setBaseDataJobProxy(BaseDataJobProxy baseDataJobProxy) {
        this.baseDataJobProxy = baseDataJobProxy;
    }

    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        baseDataJobProxy.init();
    }

}
