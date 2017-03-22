package com.core.scpwms.server.enumerate;

/**
 * 发货单状态
 * 
 * 草案（Draft）
 * 发布(Publish)
 * 预约后发布（要求有发货库位）
 * Planed(已经加入备货计划)
 * 部分发运（PartShipped）
 * 完全发运（Shipped）
 * 取消(Cancel)、
 * 完成(Close)
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuOutboundRequetStatus {
	
	/** 草案 */
	public static Long STATUS100 = 100L;
	/** 发布 */
	public static Long STATUS200 = 200L;
	
	/** 加入备货计划 */
	public static Long STATUS300 = 300L;
	
	/** 发运完成 */
	public static Long STATUS400 = 400L;
	
	//抛弃部分发运状态
//	/** 部分发运 */
//	public static Long STATUS410 = 410L;
	
	/** 取消*/
	public static Long STATUS000 = 0L;	
	/** 关闭*/
	public static Long STATUS999 = 999L;
}
