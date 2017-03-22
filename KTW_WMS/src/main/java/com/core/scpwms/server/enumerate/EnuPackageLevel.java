package com.core.scpwms.server.enumerate;


/**
 * 包装层级，与枚举相对应
 * 系统包含：基本包装、内包装 、箱包装、托盘包装、其他包装
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuPackageLevel {
	
	/** 基本包装 */
	public static String PK1000 = "PK1000";
	
	/** 内包装 */
	public static String PK2100 = "PK2100";	
	
	/** 箱包装 */
	public static String PK2000 = "PK2000";
	
	/** 托盘包装 */
	public static String PK3000 = "PK3000";
	
	/** 其他包装 */
	public static String PK4000 = "PK4000";	
}
