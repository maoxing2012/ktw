package com.core.scpwms.server.enumerate;

/**
 * 库位指定方式
 * 1、人工指定库位
 * 2、系统计算库位
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuScrapType {

    /** 日常破损 */
    public static String DAILY = "DAILY";

    /** 到货破损 */
    public static String RECEIVE = "RECEIVE";
    
    /** 加工破损 */
    public static String PROCESS = "PROCESS";

    /** 退货破损 */
    public static String BACK = "BACK";
    
    /** 调拨破损 */
    public static String ADJUST = "ADJUST";
    
    /** 其他 */
    public static String OTHER = "OTHER";

}
