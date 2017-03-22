package com.core.scpwms.server.enumerate;

/**
 * 库内作业单状态流
 * 1、ASN创建
 * 2、库内抽检创建 
 * 3、工作计划创建
 * 注：此质检只负责库内质检/收货质检，发货质检不包含在内
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
public interface EnuQCProcessMethod {
	
	public static final String ASN = "ASN";
	public static final String INV = "INV";
	public static final String PLAN = "PLAN";

	
}
