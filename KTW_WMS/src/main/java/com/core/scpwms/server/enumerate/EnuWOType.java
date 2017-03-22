package com.core.scpwms.server.enumerate;

/**
 * 标志WO的作业类型，分上架、拣选
 * 不能将任务混放
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuWOType {
	//上架
	public static final String PUTAWAY = "PUTAWAY";
	//拣选下架（要货）、
	public static final String PICKUP = "PICKUP";
	//补货下架（要货）
	public static final String INVAPPLY = "INVAPPLY";
}
