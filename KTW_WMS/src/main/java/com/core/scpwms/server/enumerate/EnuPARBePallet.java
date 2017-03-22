package com.core.scpwms.server.enumerate;

/**
 * 上架策略中，要上架的SKU是否在托盘上
 * 如果在收货时候已经码盘，那么是有托盘的
 * 1、有托盘
 * 2、无托盘
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
public interface EnuPARBePallet {
	
	/**  有托盘 */
	public static String HASPALLET = "HASPALLET";
	
	/**  无托盘 */
	public static String NOPALLET  = "NOPALLET";
	
}
