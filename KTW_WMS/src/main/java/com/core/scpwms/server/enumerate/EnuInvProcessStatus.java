package com.core.scpwms.server.enumerate;

/**
 * 库内作业单状态流
 * 1、Draft ---- 草案状态
 * 2、Publish -- 发布后，状态变为Published，此时库内作业组可看到任务单据、可分配库位
 * 3、Allocate-- 库位分配后，状态变为已经分配，此时已经生成对应WT
 * 4、Planing -- 部分WT已经建成WO
 * 5、Planed  -- 全部WT已经建成WO
 * 6、loading  -- 备货中
 * 7、loaded  -- 备货完成
 * 8、execute -- 作业执行（执行数量 〉 0）
 * 9、executed -- 全部作业执行结束，同时 执行数量 = 计划数量（是否变成Close可手工驱动、可自动驱动，暂时作为手工）
 * 10、Close   -- 全部作业执行结束，同时 执行数量 = 计划数量
 * 11、Cancel  -- 单据取消
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
public interface EnuInvProcessStatus {
	
	/** 草案 */
	public static final Long DRAFT 		= 100L;
	/** 已发布 */
	public static final Long PUBLISH 	= 200L;
	/** 已分配 */
	public static final Long ALLOCATE 	= 300L;
	/** 执行中 */
	public static final Long PLANING 	= 310L;
	/**执行完毕 */
	public static final Long PLANED 	= 320L;
	/** 备货中 */
	public static final Long LOADING 	= 330L;
	/** 备货完成 */
	public static final Long LOADED 	= 340L;
	
	public static final Long EXECUTE 	= 400L;
	/** 已执行 */
	public static final Long EXECUTED 	= 500L;
	/** 关闭 */
	public static final Long CLOSE 		= 600L;
	/** 取消 */
	public static final Long CANCEL 	= 700L;
	
}
