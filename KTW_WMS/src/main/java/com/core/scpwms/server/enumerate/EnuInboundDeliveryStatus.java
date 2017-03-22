package com.core.scpwms.server.enumerate;

/**
 * 入库单据状态，按照执行顺序
 * 单据状态为Long型数据，对应节点扩展后，按照数字大小来区分流程顺序
 * 
 * 新建作业 --〉单据状态为：打开
 * 激活作业 --〉单据状态为：在途
 * 生成ASN ---〉单据状态为：已计划（已创建ASN）
 * ANS收货 ---〉单据状态为：收货中 （接收量大于零但小于预期量）
 * 收货完成 --〉单据状态为：收货完成（接收量大于或等于预期量）
 * 取消作业 ---〉单据状态为：已取消（同时取消未接收ASN）
 * 结算确认 --->单据状态为：关闭（仅仅是按钮状态变更）
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuInboundDeliveryStatus {
	/** 打开 */
	public static Long STATUS100 = 100L;
	/** 发行 */
	public static Long STATUS200 = 200L;
	/** 已计划（已加入ASN）*/
	public static Long STATUS300 = 300L;
	/** 收货中 */
	public static Long STATUS400 = 400L;
	/** 收货完成 */
	public static Long STATUS500 = 500L;
	/** 取消*/
	public static Long STATUS000 = 0L;	
	/** 关闭*/
	public static Long STATUS999 = 999L;
}
