package com.core.scpwms.server.service.quartz;

import com.core.db.server.service.BaseManager;

/**
 * 执行定时JOB的Manager入口
 * 无事务支持
 */
public interface BaseQuarzJob extends BaseManager {

    public void execute();

    public void execute(String param);
}
