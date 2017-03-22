/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: MobileUtil.java
 */

package com.core.scpwms.server.mobile.bean;

import java.util.List;
import java.util.Map;

/**
 * <p>请添加注释</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015/03/19<br/>
 */
public class MobileUtil {
	public static String getRoute( List<Map<String, Object>> lst ){
		StringBuffer result = new StringBuffer();
		
		for( Map<String, Object> row : lst ){
			if( row.get("id") != null ){
				result.append(row.get("id").toString());
				result.append(",");
			}
		}
		
		if(result.length() > 0){
			result.deleteCharAt(result.length() - 1);
		}
		
		return result.toString();
	}
	
	public static String[] getNewRoute( String route, String crId ){
		String[] result= null;
		
		if( route == null || route.length() == 0 )
			return result;
		
		String[] oldRoutes = route.split(",");
		
		if( oldRoutes != null && oldRoutes.length == 1 ){
			return result;
		}
		else if ( oldRoutes != null && oldRoutes.length > 1 ){
			StringBuffer newRoute = new StringBuffer();
			String nextId = null;
			for( int i = 0 ; i < oldRoutes.length ; i++ ){
				if( crId.equals(oldRoutes[i]) ){
					if( i == oldRoutes.length - 1 ){
						nextId = oldRoutes[0];
					}
					else{
						nextId = oldRoutes[i+1];
					}
				}
				else{
					newRoute.append(oldRoutes[i]);
					newRoute.append(",");
				}
			}
			
			if( newRoute.length() > 0 ){
				newRoute.deleteCharAt(newRoute.length() - 1);
			}
			result = new String[]{newRoute.toString(), nextId};
		}
		
		return result;
	}

}
