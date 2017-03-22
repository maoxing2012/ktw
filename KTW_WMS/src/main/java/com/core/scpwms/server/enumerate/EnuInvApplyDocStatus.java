package com.core.scpwms.server.enumerate;

/**
 * 库内补货计划单据状态
 */
public interface EnuInvApplyDocStatus {
	/** 草案 */
	public static final Long DRAFT = 100L;
	/** 发行 */
	public static final Long PUBLISH = 200L;
	/** 分配完成 */
	public static final Long ALLOCATED = 310L;
	/** 分配中 */
	public static final Long ALLOCATING = 300L;
	/** 补货中 */
	public static final Long PICKING = 400L;
	/** 补货完毕 */
	public static final Long PICKED = 410L;
	/** 关闭 */
	public static final Long CLOSE = 600L;
	/** 取消 */
	public static final Long CANCEL = 900L;
}
