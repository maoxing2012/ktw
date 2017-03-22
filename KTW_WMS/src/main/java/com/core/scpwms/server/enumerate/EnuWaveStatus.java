package com.core.scpwms.server.enumerate;

/**
 * Processing status of the Wave Doc
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 * @see EnuWaveStatus
 */
public interface EnuWaveStatus {
    /** 草案 */
    public static Long STATUS100 = 100L;

    /** 发行*/
    public static Long STATUS200 = 200L;

    /** 部分分配*/
    public static Long STATUS210 = 210L;

    /** 分配完成 */
    public static Long STATUS220 = 220L;
    
    /** 拣货单已经生成 */
    public static Long STATUS250 = 250L;

    /** 执行中*/
    public static Long STATUS300 = 300L;

    /** 关闭 */
    public static Long STATUS500 = 999L;

    /** 取消*/
    public static Long STATUS000 = 000L;
}
