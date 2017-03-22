package com.core.scpwms.server.enumerate;

/**
 * Processing status of the ASN
 * 
 * 新建按钮 --> Draft(草案)
 * 预约           --> Published（已发行、从inbound delivery创建出来的ASN默认状态为发行）
 * 			   发行后、可以使用[取消发行]按钮。此处需要录入[收货点、预计开始时间]
 * 			 发行后的ASN可以执行收货。
 * 收货中     --> Partial goods receipt （接收量大于零但小于预期量）
 * 收货完毕 --> Goods receipt complete （接收量大于或等于预期量）
 * 关闭 --->单据状态为：Closed（仅仅是按钮状态变更）
 * 取消作业 --->单据状态为：Canceled
 * 重新预约 --> Published
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 * @see EnuInboundDeliveryStatus
 */
public interface EnuAsnStatus {
	/** 草案 */
	public static Long STATUS100 = 100L;
	/** 发行*/
	public static Long STATUS200 = 200L;
	/** 收货中*/
	public static Long STATUS300 = 300L;
	/** 收货完毕 */
	public static Long STATUS400 = 400L;
	/** 取消*/
	public static Long STATUS000 = 000L;	
}
