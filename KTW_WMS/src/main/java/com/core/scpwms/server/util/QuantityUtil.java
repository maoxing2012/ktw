/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: LocaleUtil.java
 */

package com.core.scpwms.server.util;

import com.core.scpwms.server.model.common.PackageDetail;


/**
 * 数值转化的共通类
 * @author mousachi
 *
 */
public class QuantityUtil {
	
	public static String getZenQtyInfo( Double qty, Double blIn, Double csIn, String psUnit, String blUnit, String csUnit ){
		Double[] qtys = getQtys(qty, blIn, csIn);
		StringBuffer result = new StringBuffer();
		
		if( qtys[0] > 0D ){
			result.append(StringUtil4Jp.toFullDigit(String.valueOf(qtys[0].intValue())));
			result.append(csUnit);
		}
		
		if( qtys[1] > 0D ){
			result.append(StringUtil4Jp.toFullDigit(String.valueOf(qtys[1].intValue())));
			result.append(blUnit);
		}
		
		if( qtys[2] > 0D ){
			result.append(StringUtil4Jp.toFullDigit(String.valueOf(qtys[2].intValue())));
			result.append(psUnit);
		}
		
		return result.toString();
	}
	
	public static String getQtyInfo( Double qty, PackageDetail csPack, PackageDetail blPack, PackageDetail psPack ){
		Double[] qtys = getQtys(qty, csPack, blPack, psPack);
		StringBuffer result = new StringBuffer();
		
		if( qtys[0] > 0D ){
			result.append(qtys[0].intValue());
			result.append(csPack.getName());
		}
		
		if( qtys[1] > 0D ){
			result.append(qtys[1].intValue());
			result.append(blPack.getName());
		}
		
		if( qtys[2] > 0D ){
			result.append(qtys[2].intValue());
			result.append(psPack.getName());
		}
		
		return result.toString();
	}
	
	public static Double[] getQtys( Double qty, PackageDetail csPack, PackageDetail blPack, PackageDetail psPack ){
		Double[] result = new Double[]{0D, 0D, 0D};
		
		if( qty != null && qty > 0D ){
			// 箱数
			if( csPack.getCoefficient() != null && csPack.getCoefficient() >= 1D ){
				result[0] = Double.valueOf(qty.intValue() / csPack.getCoefficient().intValue());
			}
			
			// 中箱数
			if( blPack.getCoefficient() != null && blPack.getCoefficient() >= 1D ){
				result[1] = Double.valueOf((qty.intValue() - result[0].intValue() * csPack.getCoefficient().intValue() )/ blPack.getCoefficient().intValue());
			}
			
			// 散件数
			result[2] = Double.valueOf(qty - result[0].intValue() * csPack.getCoefficient().intValue() - result[1].intValue() * blPack.getCoefficient().intValue());
		}
		
		return result;
	}
	
	public static Double[] getQtys( Double qty, Double blIn, Double csIn ){
		Double[] result = new Double[]{0D, 0D, 0D};
		
		if( qty != null && qty > 0D ){
			// 箱数
			if( csIn != null && csIn >= 1D ){
				result[0] = Double.valueOf(qty.intValue() / csIn.intValue());
			}
			
			// 中箱数
			if( blIn != null && blIn >= 1D ){
				result[1] = Double.valueOf((qty.intValue() - result[0].intValue() * csIn.intValue() )/ blIn.intValue());
			}
			
			// 散件数
			result[2] = Double.valueOf(qty - result[0].intValue() * csIn.intValue() - result[1].intValue() * blIn.intValue());
		}
		
		return result;
	}
}
