package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>备货单状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/01<br/>
 */
public interface EnuOutboundDeliveryStatus {

    /** 草案 */
    public static Long STATUS100 = 100L;

    /** 发布 */
    public static Long STATUS200 = 200L;
    
    /** 加入波次 */
    public static Long STATUS210 = 210L;

    /** 部分分配 */
    public static Long STATUS310 = 310L;

    /** 分配完成 */
    public static Long STATUS400 = 400L;

    /** 部分拣货 */
    public static Long STATUS710 = 710L;

    /** 拣货完成 */
    public static Long STATUS800 = 800L;

    /** 复合中 */
    public static Long STATUS840 = 840L;

    /** 复合完成 */
    public static Long STATUS850 = 850L;
    
    /** 发运完成 */
    public static Long STATUS900 = 900L;
    
    /** 发运完成(有缺货) */
    public static Long STATUS910 = 910L;

    /** 取消 */
    public static Long STATUS000 = 0L;

//    /** 关闭 */
//    public static Long STATUS999 = 999L;
}
