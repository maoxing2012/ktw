package com.core.scpwms.server.enumerate;

/**
 * 收货上架WO状态流
 * 1、Draft ---- 草案状态，生成后，状态为Draft
 * 2、Publish -- 生效后，状态变为Published，此时可分配作业人员
 * 3、Planed	  -- 作业人员分配后，状态变为已经计划
 * 4、Execute -- Task上架数量大于0 但是小于计划数量
 * 5、Close   -- 全部WT执行结束，同时 计划数量 = 上架数量
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
public interface EnuAsnPutawayOrderStatus {
	public static final Long DRAFT 		= 100L;
	public static final Long PUBLISH 	= 200L;
	public static final Long PLANED 	= 500L;
	public static final Long EXECUTE 	= 600L;// 上架完成
	public static final Long EXECUTED 	= 510L; // 上架中
	public static final Long CLOSE 		= 700L; 
}
