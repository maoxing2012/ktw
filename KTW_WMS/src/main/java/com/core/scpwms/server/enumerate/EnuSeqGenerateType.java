package com.core.scpwms.server.enumerate;

/**
 * 流水号产生范围（按年、按月、按天）
 * Epl：
 * 按年 PO201200000001、PO201200000002...第二年，流水号归 0
 * 按月 PO201212000001、PO201212000002...第二月，流水号归 0
 * 按天 PO201212010001、PO201212010002...第二天，流水号归 0
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuSeqGenerateType {
	/** 年 */
	public static String BY_YYYY = "YYYY";
	/** 月 */
	public static String BY_YYYYMM = "YYYYMM";
	/** 天 */
	public static String BY_YYYYMMDD = "YYYYMMDD";	
}
