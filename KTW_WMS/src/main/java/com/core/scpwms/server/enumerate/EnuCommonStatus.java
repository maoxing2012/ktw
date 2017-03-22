/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: EnuCommonStatus.java
 */

package com.core.scpwms.server.enumerate;

/**
 * <p>共通状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014年12月23日<br/>
 */
public interface EnuCommonStatus {
	/** 取消 */
	public static Long STATUS000 = 0L;
	
	/** 草案 */
	public static Long STATUS100 = 100L;
	
	/** 发行 */
	public static Long STATUS200 = 200L;
	
	/** 关闭 */
	public static Long STATUS999 = 999L;
}
