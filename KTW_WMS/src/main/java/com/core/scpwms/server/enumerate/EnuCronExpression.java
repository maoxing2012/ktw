package com.core.scpwms.server.enumerate;

/**
 * CronExpression.C1=每天12点自动执行
 * CronExpression.C2=每天23点自动执行
 * CronExpression.C3=每过5分钟自动执行
 * CronExpression.C4=每过10分钟自动执行
 * CronExpression.C5=每过30分钟自动执行
 * CronExpression.C6=每小时自动执行
 * CronExpression.C7=每2小时自动执行
 * CronExpression.C8=每4小时自动执行
 * CronExpression.C9=每8小时自动执行
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
public interface EnuCronExpression {
    public static String DEFAULT_VALUE = "0/5 * * * * ?";
    public static String C100_VALUE = "0 0 12 * * ?";
    public static String C200_VALUE = "0 0 23 * * ?";
    public static String C300_VALUE = "0 0/5 * * * ?";
    public static String C400_VALUE = "0 0/10 * * * ?";
    public static String C500_VALUE = "0 0/30 * * * ?";
    public static String C600_VALUE = "0 0 0/1 * * ?";
    public static String C700_VALUE = "0 0 0/2 * * ?";
    public static String C800_VALUE = "0 0 0/4 * * ?";
    public static String C900_VALUE = "0 0 0/8 * * ?";
    // public static String C9_VALUE = "0/5 * * * * ?";

}
