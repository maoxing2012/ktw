package com.core.scpwms.server.enumerate;

/**
 * 库内货品作业类型
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuInvProcessType {

    /** 库内移位 */
    public static String M1000 = "M1000";

    /** 收货作业 */
    public static String M2000 = "M2000";

    /** 发货作业 */
    public static String M3000 = "M3000";

    /** 质检作业 */
    public static String M4010 = "M4010";

    /** 包装转换 */
    public static String M4020 = "M4020";

    /** 加工作业 */
    public static String M4030 = "M4030";

    /** 货主转换 */
    public static String M4040 = "M4040";
    
    /** 库内破损 */
    public static String M4050 = "M4050";

    /** 上架作业 */
    public static String M5000 = "M5000";

    /** 下架拣货 */
    public static String M6000 = "M6000";

    /** 盘点作业 */
    public static String M8000 = "M8000";

    /** 其他 */
    public static String M9999 = "M9999";

}
