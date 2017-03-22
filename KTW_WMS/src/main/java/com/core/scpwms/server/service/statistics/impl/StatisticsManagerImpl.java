/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: StatisticsManagerImpl.java
 */

package com.core.scpwms.server.service.statistics.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.service.statistics.StatisticsManager;

/**
 * <p>请添加注释</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2015年3月31日<br/>
 */
public class StatisticsManagerImpl extends DefaultBaseManager implements
		StatisticsManager {

	/* (non-Javadoc)
	 * @see com.core.scpwms.server.service.statistics.StatisticsManager#getReport001(java.lang.Long)
	 */
	@Override
	public Map<String, Object> getReport001(Long whId) {
		StringBuffer sql = new StringBuffer(" select T.WH_ID,T.WH_CD,T.WH_NM");
		sql.append(" ,1 - ROUND(T.UNAVAILABLE_QTY / T.QTY,2) ");
		sql.append("    - ROUND(T.QC_QTY / T.QTY,2)");
		sql.append("    - ROUND(T.SCRAP_QTY / T.QTY,2)");
		sql.append("    - ROUND(T.FREEZE_QTY / T.QTY,2)");
		sql.append("    - ROUND(T.DT_QTY / T.QTY,2)");
		sql.append("    - ROUND(T.REPAIR_QTY / T.QTY,2)");
		sql.append("    - ROUND(T.DISCARD_QTY / T.QTY,2)");
		sql.append("    - ROUND(T.SAMPLE_QTY / T.QTY,2)");
		sql.append("     AS AVAILABLE_RATE");
		sql.append(" ,ROUND(T.UNAVAILABLE_QTY / T.QTY,2) AS UNAVAILABLE_RATE");
		sql.append(" ,ROUND(T.QC_QTY / T.QTY,2) AS QC_RATE");
		sql.append(" ,ROUND(T.SCRAP_QTY / T.QTY,2) AS SCRAP_RATE");
		sql.append(" ,ROUND(T.FREEZE_QTY / T.QTY,2) AS FREEZE_RATE");
		sql.append(" ,ROUND(T.DT_QTY / T.QTY,2) AS DT_RATE");
		sql.append(" ,ROUND(T.REPAIR_QTY / T.QTY,2) AS REPAIR_RATE");
		sql.append(" ,ROUND(T.DISCARD_QTY / T.QTY,2) AS DISCARD_RATE");
		sql.append(" ,ROUND(T.SAMPLE_QTY / T.QTY,2) AS SAMPLE_RATE");
		sql.append(" ,T.AVAILABLE_QTY");
		sql.append(" ,T.UNAVAILABLE_QTY");
		sql.append(" ,T.QC_QTY");
		sql.append(" ,T.SCRAP_QTY");
		sql.append(" ,T.FREEZE_QTY");
		sql.append(" ,T.DT_QTY");
		sql.append(" ,T.REPAIR_QTY");
		sql.append(" ,T.DISCARD_QTY");
		sql.append(" ,T.SAMPLE_QTY");
		sql.append(" ,T.QTY");
		sql.append(" from( select wh.ID AS WH_ID ,wh.CODE AS WH_CD ,wh.NAME AS WH_NM");
		sql.append(" ,sum( case when i.STATUS='AVAILABLE' then i.BASE_QTY else 0 end )  as AVAILABLE_QTY");
		sql.append(" ,sum( case when i.STATUS='UNAVAILABLE' then i.BASE_QTY else 0 end ) as UNAVAILABLE_QTY");
		sql.append(" ,sum( case when i.STATUS='QC' then i.BASE_QTY else 0 end ) as QC_QTY");
		sql.append(" ,sum( case when i.STATUS='SCRAP' then i.BASE_QTY else 0 end ) as SCRAP_QTY");
		sql.append(" ,sum( case when i.STATUS='FREEZE' then i.BASE_QTY else 0 end ) as FREEZE_QTY");
		sql.append(" ,sum( case when i.STATUS='DT' then i.BASE_QTY else 0 end ) as DT_QTY");
		sql.append(" ,sum( case when i.STATUS='REPAIR' then i.BASE_QTY else 0 end ) as REPAIR_QTY");
		sql.append(" ,sum( case when i.STATUS='DISCARD' then i.BASE_QTY else 0 end ) as DISCARD_QTY");
		sql.append(" ,sum( case when i.STATUS='SAMPLE' then i.BASE_QTY else 0 end ) as SAMPLE_QTY");
		sql.append(" ,sum(i.BASE_QTY) AS QTY");
		sql.append(" from WMS_INVENTORY i");
		sql.append(" left join WMS_WH wh on i.WH_ID = wh.ID");
		sql.append(" where 1=1 and i.WH_ID = ?");
		sql.append(" group by wh.ID,wh.CODE,wh.NAME )T");
		
		List<Object[]> inv = commonDao.findBySqlQuery(sql.toString(), new Object[]{whId});
		if( inv != null ){
			Map<String, Object> result = new HashMap<String, Object>();
			
			result.put("WH_ID", inv.get(0)[0]);
			result.put("WH_CD", inv.get(0)[1]);
			result.put("WH_NM", inv.get(0)[2]);
			result.put("AVAILABLE_QTY", inv.get(0)[12]);
			result.put("UNAVAILABLE_QTY", inv.get(0)[13]);
			result.put("QC_QTY", inv.get(0)[14]);
			result.put("SCRAP_QTY", inv.get(0)[15]);
			result.put("FREEZE_QTY", inv.get(0)[16]);
			result.put("DT_QTY", inv.get(0)[17]);
			result.put("REPAIR_QTY", inv.get(0)[18]);
			result.put("DISCARD_QTY", inv.get(0)[19]);
			result.put("SAMPLE_QTY", inv.get(0)[20]);
			result.put("QTY", inv.get(0)[21]);
			
			return result;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getReport002(Long whId, String dateFrom, String dateTo) {
		StringBuffer sql = new StringBuffer(" select T.LABOR_ID,T.LABOR_CD,T.LABOR_NM,");
		sql.append(" round(sum(coalesce(T.BASE_QTY,0)),0) AS BASE_QTY,");
		sql.append(" round(sum(coalesce(T.WEIGHT,0)),2) AS WEIGHT,");
		sql.append(" round(sum(coalesce(T.VOLUME,0)),2) AS VOLUME,");
		sql.append(" round(sum(coalesce(T.IN_QTY,0)),0) AS IN_QTY,");
		sql.append(" round(sum(coalesce(T.PA_QTY,0)),0) AS PA_QTY,");
		sql.append(" round(sum(coalesce(T.MV_QTY,0)),0) AS MV_QTY,");
		sql.append(" round(sum(coalesce( T.PK_QTY,0)),0) AS PK_QTY,");
		sql.append(" round(sum(coalesce(T.OUT_QTY,0)),0) AS OUT_QTY,");
		sql.append(" round(sum(coalesce(T.OT_QTY,0)),0) AS OT_QTY,");
		sql.append(" round(sum(coalesce( T.IN_WEIGHT,0)),2) AS IN_WEIGHT,");
		sql.append(" round(sum(coalesce(T.PA_WEIGHT,0)),2) AS PA_WEIGHT,");
		sql.append(" round(sum(coalesce(T.MV_WEIGHT,0)),2) AS MV_WEIGHT,");
		sql.append(" round(sum(coalesce(T.PK_WEIGHT,0)),2) AS PK_WEIGHT,");
		sql.append(" round(sum(coalesce(T.OUT_WEIGHT,0)),2) AS OUT_WEIGHT,");
		sql.append(" round(sum(coalesce(T.OT_WEIGHT ,0)),2) AS OT_WEIGHT,");
		sql.append(" round(sum(coalesce(T.IN_VOLUME,0)),2) AS IN_VOLUME,");
		sql.append(" round(sum(coalesce(T.PA_VOLUME,0)),2) AS PA_VOLUME,");
		sql.append(" round(sum(coalesce(T.MV_VOLUME,0)),2) AS MV_VOLUME,");
		sql.append(" round(sum(coalesce(T.PK_VOLUME,0)),2) AS PK_VOLUME,");
		sql.append(" round(sum(coalesce(T.OUT_VOLUME,0)),2) AS OUT_VOLUME,");
		sql.append(" round(sum( coalesce(T.OT_VOLUME,0)),2) AS OT_VOLUME");
		sql.append(" from ( select labor.ID AS LABOR_ID,labor.CODE AS LABOR_CD,labor.NAME AS LABOR_NM,kpi.BASE_QTY,kpi.WEIGHT,kpi.VOLUME,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M2000' then kpi.BASE_QTY else 0 end as IN_QTY,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M5000' then kpi.BASE_QTY else 0 end as PA_QTY,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M1000' then kpi.BASE_QTY else 0 end as MV_QTY,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M6000' then kpi.BASE_QTY else 0 end as PK_QTY,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M3000' then kpi.BASE_QTY else 0 end as OUT_QTY,");
		sql.append(" case when kpi.PROCESS_TYPE not in ('M2000','M5000','M1000','M6000','M3000') then kpi.BASE_QTY else 0 end as OT_QTY,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M2000' then kpi.WEIGHT else 0 end as IN_WEIGHT,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M5000' then kpi.WEIGHT else 0 end as PA_WEIGHT,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M1000' then kpi.WEIGHT else 0 end as MV_WEIGHT,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M6000' then kpi.WEIGHT else 0 end as PK_WEIGHT,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M3000' then kpi.WEIGHT else 0 end as OUT_WEIGHT,");
		sql.append(" case when kpi.PROCESS_TYPE not in ('M2000','M5000','M1000','M6000','M3000') then kpi.WEIGHT else 0 end as OT_WEIGHT,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M2000' then kpi.VOLUME else 0 end as IN_VOLUME,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M5000' then kpi.VOLUME else 0 end as PA_VOLUME,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M1000' then kpi.VOLUME else 0 end as MV_VOLUME,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M6000' then kpi.VOLUME else 0 end as PK_VOLUME,");
		sql.append(" case when kpi.PROCESS_TYPE = 'M3000' then kpi.VOLUME else 0 end as OUT_VOLUME,");
		sql.append(" case when kpi.PROCESS_TYPE not in ('M2000','M5000','M1000','M6000','M3000') then kpi.VOLUME else 0 end as OT_VOLUME");
		sql.append(" from WMS_LABOR_KPI kpi");
		sql.append(" left join WMS_LABOR labor on kpi.LABOR_ID = labor.ID");
		sql.append(" where 1=1 and kpi.WH_ID = ?");
		sql.append(" and to_char(kpi.EXECUTE_DATE, 'YYYY-MM-DD') >=?");
		sql.append(" and to_char(kpi.EXECUTE_DATE, 'YYYY-MM-DD') <=?)T");
		sql.append(" group by T.LABOR_ID,T.LABOR_CD,T.LABOR_NM");
		sql.append(" order by round(sum(coalesce(T.BASE_QTY,0)),0) desc");
		
		List<Object[]> inv = commonDao.findBySqlQuery(sql.toString(), new Object[]{whId, dateFrom, dateTo});
		if( inv != null ){
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>(inv.size());
			
			for( Object[] line : inv ){
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("LABOR_ID", line[0]);
				row.put("LABOR_CD", line[1]);
				row.put("LABOR_NM", line[2]);
				
				row.put("BASE_QTY", line[3]);
				row.put("WEIGHT", line[4]);
				row.put("VOLUME", line[5]);
				
				row.put("IN_QTY", line[6]);
				row.put("PA_QTY", line[7]);
				row.put("MV_QTY", line[8]);
				row.put("PK_QTY", line[9]);
				row.put("OUT_QTY", line[10]);
				row.put("OT_QTY", line[11]);
				
				row.put("IN_WEIGHT", line[12]);
				row.put("PA_WEIGHT", line[13]);
				row.put("MV_WEIGHT", line[14]);
				row.put("PK_WEIGHT", line[15]);
				row.put("OUT_WEIGHT", line[16]);
				row.put("OT_WEIGHT", line[17]);
				
				row.put("IN_VOLUME", line[18]);
				row.put("PA_VOLUME", line[19]);
				row.put("MV_VOLUME", line[20]);
				row.put("PK_VOLUME", line[21]);
				row.put("OUT_VOLUME", line[22]);
				row.put("OT_VOLUME", line[23]);
				
				result.add(row);
			}
			return result;
		}
		return null;
	}

}
