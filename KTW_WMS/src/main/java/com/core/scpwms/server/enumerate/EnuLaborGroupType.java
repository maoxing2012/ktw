/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: EnuLaborGroupType.java
 */

package com.core.scpwms.server.enumerate;

/**
 * <p>作业组类型</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/21<br/>
 */
public interface EnuLaborGroupType {
    /**收货作业*/
    public static String RECEIVE = "RECEIVE";

    /**发货作业*/
    public static String SHIP = "SHIP";

    /**库内作业*/
    public static String STOCK = "STOCK";

    /**其他作业*/
    public static String OTHER = "OTHER";
}
