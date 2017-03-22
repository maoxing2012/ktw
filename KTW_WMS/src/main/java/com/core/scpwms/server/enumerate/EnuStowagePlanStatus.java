package com.core.scpwms.server.enumerate;

/**
 * 配载单执行状态
 * 1、草案
 * 2、发布
 * 3、在途
 * 4、运抵
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuStowagePlanStatus {
    /** 草案 */
    public static final Long STATE100 = 100L;
    /** 生效 */
    public static final Long STATE200 = 200L;
    /** 装车中 */
    public static final Long STATE300 = 300L;
    /** 关闭 */
    public static final Long STATE999 = 999L;
    /** 取消 */
    public static final Long STATE000 = 0L;
}
