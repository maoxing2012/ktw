package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>批次调整单状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/25<br/>
 */
public interface EnuLotChangeDocStatus {
    /** 草案 */
    public static final Long DRAFT = 100L;

    /** 发行 */
    public static final Long PUBLISH = 200L;

    /** 分配中 */
    public static final Long ALLOCATING = 210L;

    /** 分配完成 */
    public static final Long ALLOCATED = 300L;

    /** 执行中 */
    public static final Long PROCESS = 310L;

    /** 执行完成 */
    public static final Long PROCESSED = 400L;

    /** 关闭 */
    public static final Long CLOSE = 999L;

    /** 取消 */
    public static final Long CANCEL = 0L;
}
