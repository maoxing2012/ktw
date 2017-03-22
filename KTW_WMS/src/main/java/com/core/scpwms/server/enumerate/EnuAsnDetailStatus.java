package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>Asn Detail的状态，和ASN一样，为了将来能扩展，这里还是作为一个新的Enu来处理</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/14<br/>
 */
public interface EnuAsnDetailStatus {
    /** 草案 */
    public static Long STATUS100 = 100L;
    /** 发行*/
    public static Long STATUS200 = 200L;
    /** 收货中*/
    public static Long STATUS300 = 300L;
    /** 收货完毕 */
    public static Long STATUS400 = 400L;
    /** 取消*/
    public static Long STATUS000 = 0L;
}
