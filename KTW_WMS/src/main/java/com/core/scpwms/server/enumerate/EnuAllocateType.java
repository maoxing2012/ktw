package com.core.scpwms.server.enumerate;

/**
 * 库位指定方式
 * 1、人工指定库位
 * 2、系统计算库位
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuAllocateType {

	/** 人工指定库位 */
	public static String ALLOCATE_01 = "ALLOCATE01";

	/** 系统计算库位 */
	public static String ALLOCATE_02 = "ALLOCATE02";

}
