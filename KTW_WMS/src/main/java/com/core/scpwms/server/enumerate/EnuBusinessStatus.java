package com.core.scpwms.server.enumerate;
/**
 * 状态
 * 100：接收
 * 200：转发成功
 * 300：转发失败
 * 400：取消转发
 */
public interface EnuBusinessStatus {
	
	public static Long STATUS100=100L;
	public static Long STATUS200=200L;
	public static Long STATUS300=300L;
	public static Long STATUS400=400L;
}
