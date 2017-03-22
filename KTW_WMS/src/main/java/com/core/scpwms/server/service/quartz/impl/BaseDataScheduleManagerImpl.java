package com.core.scpwms.server.service.quartz.impl;

import java.util.Date;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.service.quartz.BaseQuarzJob;

public class BaseDataScheduleManagerImpl extends DefaultBaseManager implements BaseQuarzJob {

    @Override
    public void execute() {
        System.out.println("------------------------ " + new Date() + " -------------------------");
    }

    @Override
    public void execute(String param) {
        System.out.println("------------------------ " + param + " -------------------------");
    }

}
