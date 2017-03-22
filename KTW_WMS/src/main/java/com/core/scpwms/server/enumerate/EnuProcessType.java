package com.core.scpwms.server.enumerate;

/**
 * 库位作业限定（未启用）
 * 1、手工作业
 * 2、叉车作业
 * 3、混合作业
 * 4、其他作业
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuProcessType {
	
    /** 手工作业 */
    public static String PT1000 = "PT1000";
    
    /** 叉车作业 */
    public static String PT2000 = "PT2000";
    
    /** 混合作业 */
    public static String PT3000 = "PT3000";
    
    /** 其他作业 */
    public static String PT4000 = "PT4000";
	
}
