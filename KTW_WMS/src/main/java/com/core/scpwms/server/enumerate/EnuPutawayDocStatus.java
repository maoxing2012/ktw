package com.core.scpwms.server.enumerate;

/**
 * 上架单状态流
 */
@SuppressWarnings("all")
public interface EnuPutawayDocStatus {

    /**
     * 草案
     */
    //public static final Long DRAFT = 100L;

    /**
     * 发行
     */
    public static final Long PUBLISH = 200L;

    /**
     * 分配中
     */
    public static final Long ALLOCATING = 210L;

    /**
     * 分配
     */
    public static final Long ALLOCATE = 300L;

    /**
     * 计划中
     */
    public static final Long PLANING = 400L;

    /**
     * 计划完成
     */
    public static final Long PLANED = 500L;

    /**
     * 执行中
     */
    public static final Long EXECUTEING = 600L;

    /**
     * 关闭(执行完成，自动关闭，也可以手工强行关闭)
     */
    public static final Long CLOSE = 700L;

    /**
     * 已取消
     */
    public static final Long CANCEL = 0L;

}
