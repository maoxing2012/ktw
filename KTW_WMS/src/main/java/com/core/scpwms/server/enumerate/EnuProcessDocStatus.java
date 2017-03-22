package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>加工单状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/25<br/>
 */
public interface EnuProcessDocStatus {
    /** 草案 */
    public static final Long DRAFT = 100L;

    /** 发行 */
    public static final Long PUBLISH = 200L;

    /** 分配中 */
    public static final Long ALLOCATING = 300L;

    /** 分配完成 */
    public static final Long ALLOCATED = 310L;

    /** 拣货中 */
    public static final Long PICKING = 400L;

    /** 拣货完毕 */
    public static final Long PICKED = 410L;

    /** 加工中 */
    public static final Long PROCESS = 500L;

    /** 加工完成 */
    public static final Long PROCESSED = 510L;

    /** 关闭 */
    public static final Long CLOSE = 600L;

    /** 取消 */
    public static final Long CANCEL = 900L;
}
