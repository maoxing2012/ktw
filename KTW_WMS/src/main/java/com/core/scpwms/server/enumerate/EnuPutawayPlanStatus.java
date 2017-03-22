package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>生成上架计划状态</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/08/08<br/>
 */
public interface EnuPutawayPlanStatus {
	//未生成上架计划
	public static final Long STATUS100 = 100L;

	//部分生成上架计划
	public static final Long STATUS200 = 200L;
	
	// 全部生成上架计划
	public static final Long STATUS300 = 300L;
}
