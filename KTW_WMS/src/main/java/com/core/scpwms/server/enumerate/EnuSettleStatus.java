package com.core.scpwms.server.enumerate;

/**
 * 结算状态
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuSettleStatus {
	/**
	 * 未结算
	 */
	public static final String  ORIGINAL = "ORIGINAL";
	/**
	 * 结算中
	 */
	public static final String  PROGRESS = "PROGRESS";
	/**
	 * 结算完成
	 */
	public static final String  COMPLETED = "COMPLETED";
}
