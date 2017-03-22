package com.core.scpwms.server.enumerate;

/**
 * 容器类型管理
 * 容器包含：周转箱、货品箱、笼车、集装箱、托盘
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuContainerType {
	/** 
	 * 标准包装 
	 * 标准箱单位用材
	 * */
	public static String C0000 = "C0000";
	/** 周转箱 */
	public static String C1000 = "C1000";
	/** 笼车*/
	public static String C3000 = "C3000";
	/** 托盘 */
	public static String C5000 = "C5000";
}
