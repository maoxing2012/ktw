package com.core.scpwms.server.enumerate;

/**
 * 系统提供四种周转规则供选择
 * 1、存货日期先进先出（默认）
 * 2、存货日期后进先出
 * 3、保质期先进先出
 * 4、保质期后进先出
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
public interface EnuTurnOverConf {
	/**
	 * 存货日期先进先出
	 */
	public static String TOC001 = "TOC001";
	
	/**
	 * 存货日期后进先出
	 */
	public static String TOC002 = "TOC002";
	/**
	 * 保质期先进先出
	 */
	public static String TOC003 = "TOC003";
	/**
	 * 保质期后进先出
	 */
	public static String TOC004 = "TOC004";
}
