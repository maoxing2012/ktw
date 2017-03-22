/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: EnuFeeUnit.java
 */

package com.core.scpwms.server.enumerate;

/**
 * <p>费率单位</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/18<br/>
 */
public interface EnuFeeUnit {
    /** 元/件 */
    public static String UNIT_PS = "UNIT_PS";
    
    /** 元/箱 */
    public static String UNIT_CS = "UNIT_CS";
    
    /** 元/個数 */
    public static String UNIT_BOX = "UNIT_BOX";

    /** 元/KG */
    public static String UNIT_WEIGHT_KG = "UNIT_WEIGHT_KG";

    /** 元/立方 */
    public static String UNIT_VOLUME_M3 = "UNIT_VOLUME_M3";
}
