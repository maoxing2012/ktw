package com.core.scpwms.server.enumerate;

/**
 * 货品分类
 * 目前系统分类为4类，分别为：货品大类、货品中类、货品小类、结算分类
 * 实施过程中，根据客户的需要规划货品分类，货品分类的作用如下
 * 1、统计维度
 * 2、库内货品分类对相同类别货品统一设定、统一规划
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuSkuType {
	
	/** 货品大类 */
	public static String IT1000 = "IT1000";
	
	/** 货品中类 */
	public static String IT2000 = "IT2000";
	
	/** 货品小类 */
	public static String IT3000 = "IT3000";	
	
	/** 结算分类 */
	public static String IT4000 = "IT4000";		
}
