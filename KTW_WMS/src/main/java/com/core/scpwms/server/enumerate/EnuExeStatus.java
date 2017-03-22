package com.core.scpwms.server.enumerate;

/**
 * Message执行状态
 */
public interface EnuExeStatus {
	
	/** 创建 */
	public static Long STATUS0 = 900L;
	
	/** 已转发 */
	public static Long STATUS1 = 901L;
	
	/** 转发失败 */
	public static Long STATUS9 = 800L;
	
}
