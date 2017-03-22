package com.core.scpwms.server.enumerate;

/**
 * 出库计划管理结算状态：
 * 未结算
 * 结算中
 * 结算完成
 * */
public interface EnuPayOffStatus {
	/**未结算*/
	public static Long STATUS100 = 100L;
	/**结算中*/
	public static Long STATUS200 = 200L;
	/**结算完成*/
	public static Long STATUS300 = 300L;
}
