package com.core.scpwms.server.enumerate;

/**
 * 
 * <p>
 * 瓷砖分类的关键字
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/08/08<br/>
 */
public interface EnuTileKeyWord {
	// brandName
	public static String makeboluo = "00";// 马可波罗

	public static String mengnalisha = "01";// 蒙娜丽莎

	public static String oushennuo = "02"; // 欧神诺

	public static String ld = "03";// L&D

	public static String xinzhongyuan = "04";// 新中源

	public static String jianpai = "05"; // 箭牌瓷砖

	public static String faensha = "06"; // 法恩莎瓷砖

	// name
	// 中分类：800系列
	public static String T800 = "800系列";

	// 中分类800系列下的小分类
	public static String T800_L_1 = "抛光砖(蒙娜丽莎)";

	public static String T800_L_2 = "亚光砖(蒙娜丽莎)";

	public static String T800_L_3 = "微晶石(蒙娜丽莎)";

	public static String T800_1 = "800*800";

	public static String T800_2 = "800x800";

	public static String T800_3 = "800X800";

	public static String T800_4 = "800×800";

	// 中分类：600系列
	public static String T600 = "600系列";

	public static String T600_1 = "600*600";

	public static String T600_2 = "600x600";

	public static String T600_3 = "600X600";

	public static String T600_4 = "600×600";

	// 中分类：小地砖
	public static String SMALL = "小地砖";

	// 中分类：瓷片
	public static String CIPIAN = "瓷片";

	// 中分类瓷片下的小分类
	public static String CIPIAN_1 = "450x300小(蒙娜丽莎)";

	public static String CIPIAN_2 = "660x240中(蒙娜丽莎)";

	public static String CIPIAN_3 = "600x300大(蒙娜丽莎)";

	public static String CIPIAN_4 = "800x400特大(蒙娜丽莎)";

	public static String CIPIAN_9 = "其他瓷片规格(蒙娜丽莎)";

	// 中分类：瓷砖配件
	public static String PEIJIAN = "瓷砖配件";
	
	public static String PEIJIAN_NM_1 = "砖配件";

	// 中分类瓷砖配件下的小分类
	public static String PEIJIAN_1 = "800配件(蒙娜丽莎)";

	public static String PEIJIAN_2 = "600配件(蒙娜丽莎)";
	
	public static String PEIJIAN_3 = "腰线&花片";
	
	public static String PEIJIAN_9 = "其他瓷砖配件(蒙娜丽莎)";

	// 中分类：花片
	public static String HUAPIAN = "花片";

	// 中分类：腰线
	public static String YAOXIAN = "腰线";

	// 中分类：壁饰
	public static String BISHI = "壁饰";

	// 中分类：其他
	public static String OTHER = "其他瓷砖";

	// 中分类其他下的小分类
	public static String OTHER_1 = "罗马森林(蒙娜丽莎)";

	// 中分类：宣传品
	public static String PRESENT = "宣传品";

	// code
	public static String T800_CODE = "100";

	public static String T800_CODE_1 = "1001";

	public static Double T800_CODE_1_TH = 1.3D;// 抛光砖 包装厚度3.9，3片装

	public static Double T800_CODE_1_CO = 99D; // 抛光砖 托盘包装系数

	public static String T800_CODE_2 = "1002";

	public static Double T800_CODE_2_TH = 1.3D;// 亚光砖 包装厚度3.9，3片装

	public static Double T800_CODE_2_CO = 99D; // 亚光砖 托盘包装系数

	public static String T800_CODE_3 = "1003";

	public static Double T800_CODE_3_TH = 2.25D;// 微晶石 包装厚度4.5，2片装

	public static Double T800_CODE_3_CO = 60D; // 微晶石 托盘包装系数

	public static String T600_CODE = "200";

	public static Double T600_CODE_TH = 1.15D;// 600系列 包装厚度 4.6，4片装

	public static Double T600_CODE_CO = 160D;// 600系列 托盘包装系数

	public static String SMALL_CODE = "300";

	public static Double SMALL_CODE_TH = 0.925D; // 小地砖 包装厚度15.725，17片装

	public static Double SMALL_CODE_CO = 1224D; // 小地砖 托盘包装系数

	public static String CIPIAN_CODE = "400";

	public static String CIPIAN_CODE_1 = "4001";

	public static Double CIPIAN_CODE_1_TH = 1.04D; // 小瓷片 包装厚度12.48，12片装

	public static Double CIPIAN_CODE_1_CO = 720D; // 小瓷片 托盘包装系数

	public static String CIPIAN_CODE_2 = "4002";

	public static Double CIPIAN_CODE_2_TH = 1.2D; // 小瓷片 包装厚度9.6，8片装

	public static Double CIPIAN_CODE_2_CO = 480D; // 小瓷片 托盘包装系数

	public static String CIPIAN_CODE_3 = "4003";

	public static Double CIPIAN_CODE_3_TH = 1.0D; // 小瓷片 包装厚度10，10片装

	public static Double CIPIAN_CODE_3_CO = 480D; // 小瓷片 托盘包装系数

	public static String CIPIAN_CODE_4 = "4004";

	public static Double CIPIAN_CODE_4_TH = 1.8D; // 小瓷片 包装厚度10.8，6片装

	public static Double CIPIAN_CODE_4_CO = 234D; // 小瓷片 托盘包装系数

	public static String CIPIAN_CODE_9 = "4009";

	public static String PEIJIAN_CODE = "500";

	public static Double PEIJIAN_CODE_TH = 1.0D;// 配件

	public static String PEIJIAN_CODE_1 = "5001";

	public static String PEIJIAN_CODE_2 = "5002";
	
	public static String PEIJIAN_CODE_3 = "5003";

	public static String PEIJIAN_CODE_9 = "5009";

	public static String HUAPIAN_CODE = "600";

	public static String YAOXIAN_CODE = "700";

	public static String BISHI_CODE = "800";

	public static String OTHER_CODE = "999";

	public static String OTHER_CODE_1 = "9991";

	public static Double OTHER_CODE_1_TH = 1.2D;// 罗马森林 包装厚度9.6 8片装

	public static Double OTHER_CODE_1_CO = 592D;// 罗马森林 托盘包装系数
}
