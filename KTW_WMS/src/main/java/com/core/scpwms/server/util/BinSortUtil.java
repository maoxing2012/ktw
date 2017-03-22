package com.core.scpwms.server.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BinSortUtil {

	public static List<Map<String, Object>> sortWtByBin( List<Map<String, Object>> list , final String[] params){
		sort(list, params);
		
		int condBinIndex = -1;
		for( Map<String, Object> item : list ){
			condBinIndex++;
			if( item.get("id") == null )
				break;
		}
		
		if( condBinIndex == 0 ){
			 list.remove(condBinIndex);
			 return list;
		}
		else if( condBinIndex == list.size() - 1 ){
			list.remove(condBinIndex);
			return list;
		}
		else{
			List<Map<String, Object>> list1 = list.subList(0, condBinIndex);
			List<Map<String, Object>> list2 = list.subList(condBinIndex + 1, list.size());
			
			if( list1.size() > list2.size() ){
				list2.addAll(list1);
				return list2;
			}
			else{
				list1.addAll(list2);
				return list1;
			}
		}
	}
	
	public static List<Map<String, Object>> sort( List<Map<String, Object>> list , final String[] params){
		Collections.sort(list,new Comparator<Map<String, Object>>(){
            public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
            	Long index1 = arg0.get(params[0]) == null ? 0L : (Long)arg0.get(params[0]);
            	Long index2 = arg1.get(params[0]) == null ? 0L : (Long)arg1.get(params[0]);
            	
            	String binCode1 = arg0.get(params[1]).toString();
            	String binCode2 = arg1.get(params[1]).toString();
            	
            	if( index1.longValue() == index2.longValue() )
            		return binCode1.compareTo(binCode2);
            	return index1.compareTo(index2);
            }
		});
		
		return list;
	}
}
