package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>盘点方式</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/11<br/>
 */
public interface EnuCountMethod {

    /** 从商品 */
    public static final String FROM_SKU = "FROM_SKU";

    /** 从库位 */
    public static final String FROM_BIN = "FROM_BIN";

    /** 循环盘点 */
    public static final String CYCLE = "CYCLE";

    /** 动碰 */
    public static final String DYNAMIC = "DYNAMIC";
    
    /** 全盘*/
    public static final String ALL = "ALL";
}
