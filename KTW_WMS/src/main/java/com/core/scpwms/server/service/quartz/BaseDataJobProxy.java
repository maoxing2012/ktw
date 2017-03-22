package com.core.scpwms.server.service.quartz;

import java.util.List;

import com.core.db.server.service.BaseManager;

/**
 * 执行定时JOB的Manager入口 
 * 处理基础数据的定时任务
 * 无事务支持
 */
public interface BaseDataJobProxy extends BaseManager {
    public void init();

    public void enable(List<Long> ids);

    public void disable(List<Long> ids);

    public void executeNow(List<Long> ids);

}
