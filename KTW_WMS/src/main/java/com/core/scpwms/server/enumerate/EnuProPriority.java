package com.core.scpwms.server.enumerate;

public interface EnuProPriority {
	/** 暂缓 */
	public static final Long DRAFT = 100L;
	/** 紧急 */
	public static final Long FAST = 300L;
	/** 正常 */
	public static final Long NORMAL = 200L;
}
 