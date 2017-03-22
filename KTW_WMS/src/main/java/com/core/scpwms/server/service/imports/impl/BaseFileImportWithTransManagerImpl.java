package com.core.scpwms.server.service.imports.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.WmsConstant4Ktw;
import com.core.scpwms.server.enumerate.EnuBinLockType;
import com.core.scpwms.server.enumerate.EnuFixDiv;
import com.core.scpwms.server.enumerate.EnuInvProcessType;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuPackFlg;
import com.core.scpwms.server.enumerate.EnuStockDiv;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinProperties;
import com.core.scpwms.server.model.warehouse.StorageType;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.model.warehouse.WhArea;

public class BaseFileImportWithTransManagerImpl extends DefaultBaseManager {
	
	public static final String ASN = "ASN";
	
	public static final String OBD = "OBD";
	
	public static final String MST = "MST";
	
	public String getNsOrderTypeCode( String orderTypeCode ){
		if( WmsConstant4Ktw.OBD01.equals(orderTypeCode) ){
			return "1";
		}
		else if( WmsConstant4Ktw.OBD02.equals(orderTypeCode) ){
			return "2";
		}
		else{
			return "1";
		}
	}
	
	// 单据类型
	public Map<String, OrderType> getOrderTypeMap( String type ){
		Map<String, OrderType> resultMap = new HashMap<String, OrderType>();
		
		String hql = " from OrderType ot where ot.processType = :type and ot.disabled = false order by ot.code ";
		List<OrderType> ots = null;
		if( ASN.equals(type) ){
			ots = commonDao.findByQuery(hql, "type", EnuInvProcessType.M2000);
		}
		else if( OBD.equals(type) ){
			ots = commonDao.findByQuery(hql, "type", EnuInvProcessType.M3000);
		}
		
		if( ots != null && ots.size() > 0 ){
			for( OrderType ot : ots ){
				resultMap.put(ot.getCode(), ot);
			}
		}
		
		return resultMap;
	}
	
	// 整理Excel行的长度
	public static List<String> formatExcelLine( List<String> line, int num ){
		if( line != null && line.size() > num )
			return line.subList(0, num);
		
		else if( line !=null && line.size() < num ){
			for( int i = line.size() ; i < num ; i++ ){
				line.add("");
			}
			return line;
		}
		else 
			return line;
	}
	
	// 有效/无效的区分
	public static Boolean getDisableDiv( String div ){
		Boolean result = Boolean.FALSE;
		
		if( div != null && "無効".equals(div) )
			result = Boolean.TRUE;
		
		return result;
			
	}
	
	// 可/不可的区分
	public static Boolean getCanDiv( String div ){
		Boolean result = Boolean.FALSE;
		if( div != null && ( "可".equals(div) 
				|| "可能".equals(div) 
				|| "T".equals(div) 
				|| "True".equals(div) 
				|| "true".equals(div) ))
			result = Boolean.TRUE;
		
		return result;
	}
	
	public static String getBinLockDiv( String div ){
		if( StringUtils.isEmpty(div) )
			return null;
		
		if( "入荷不可".equals(div) || "入庫不可".equals(div) ){
			return EnuBinLockType.LOCK_T1;
		}
		
		if( "出荷不可".equals(div) || "出庫不可".equals(div) ){
			return EnuBinLockType.LOCK_T2;
		}
		
		if( "入出荷不可".equals(div) || "入出庫不可".equals(div) ){
			return EnuBinLockType.LOCK_T3;
		}
		
		return null;
	}
	
	// 温度带区分
	public static Long getTempDiv( String tempDivCd ){
		try{
			Long tempDivL = Long.parseLong(tempDivCd);
			return tempDivL;
		}catch( NumberFormatException e){
			return EnuTemperatureDiv.UNDEF;
		}
	}
	
	// 库存区分
	public static Long getStockDiv( String stockDiv ){
		if( "1".equals(stockDiv) )
			return EnuStockDiv.STOCK;
		if( "2".equals(stockDiv) )
			return EnuStockDiv.THROUGH;
		
		return EnuStockDiv.STOCK;
	}
	
	// 不定貫区分
	public static Long getFixDiv( String fixDiv ){
		if( "0".equals(fixDiv) )
			return EnuFixDiv.FIX;
		if( "1".equals(fixDiv) )
			return EnuFixDiv.UN_FIX;
		
		return EnuFixDiv.FIX;
	}
	
	// 能否同捆区分1：可以，2：不可以
	public static Long getPackFlg( String packFlg, Long tempDiv ){
		if( packFlg == null || packFlg.trim().length() == 0 )
			return null;
		if( EnuTemperatureDiv.UNDEF.equals(tempDiv) 
				|| EnuTemperatureDiv.CW.equals(tempDiv) 
				|| EnuTemperatureDiv.DRY.equals(tempDiv)){
			if( "2".equals(packFlg) ){
				return EnuPackFlg.UN_FIX;
			}
			
			return EnuPackFlg.FIX;
		}
		else{
			return EnuPackFlg.UN_FIX;
		}
	}
	
	// Wms invStatus ->Ns invStatus
	public static String getNsInvStatus( String nsInvStatus ){
		// 通常品
		if( EnuInvStatus.AVAILABLE.equals(nsInvStatus) ){
			return "1";
		}
		// 限定品
		else if( EnuInvStatus.QC.equals(nsInvStatus) ){
			return "2";
		}
		// 保留品
		else if( EnuInvStatus.FREEZE.equals(nsInvStatus) ){
			return "3";
		}
		// 不良品
		else if( EnuInvStatus.SCRAP.equals(nsInvStatus) ){
			return "4";
		}
		else{
			return "1";
		}
	}
	// NS invStatus ->Wms invStatus
	public static String getWmsInvStatus( String nsInvStatus ){
		// 通常品
		if( "1".equals(nsInvStatus) ){
			return EnuInvStatus.AVAILABLE;
		}
		// 限定品
		else if( "2".equals(nsInvStatus) ){
			return EnuInvStatus.QC;
		}
		// 保留品
		else if( "3".equals(nsInvStatus) ){
			return EnuInvStatus.FREEZE;
		}
		// 不良品
		else if( "4".equals(nsInvStatus) ){
			return EnuInvStatus.SCRAP;
		}
		else{
			return null;
		}
	}
	
	// String -> Long
	public static Long getLong( String str ){
		if( str == null || str.trim().length() == 0 )
			return 0L;
		
		try{
			return Long.parseLong(str.replaceAll(",","").trim());
		}catch( NumberFormatException e){
			return 0L;
		}
	}
	
	// String -> Integer
	public static Integer getInteger( String str ){
		if( str == null || str.trim().length() == 0 )
			return 0;
		
		try{
			return Integer.parseInt(str.replaceAll(",","").trim());
		}catch( NumberFormatException e){
			return 0;
		}
	}
	
	// String -> Double
	public static Double getDouble( String doubleStr ){
		if( doubleStr == null || doubleStr.trim().length() == 0 )
			return 0D;
		
		try{
			return Double.parseDouble(doubleStr.replaceAll(",","").trim());
		}catch( NumberFormatException e){
			return 0D;
		}
	}
	
	// 供应商
	public BizOrg getVendor( Long planId, String vendorCd ){
		String hql = " from BizOrg b where 1=1 "
				+ " and b.type = 'V' "
				+ " and b.plant.id = :plantId "
				+ " and b.code = :vendorCd ";
		List<BizOrg> vendors = commonDao.findByQuery(hql, 
				new String[]{"plantId", "vendorCd"}, 
				new Object[]{planId, vendorCd});
		
		if( vendors != null && vendors.size() > 0 )
			return vendors.get(0);
		
		return null;
	}
	
	// 货主
	public Owner getOwner( String ownerCode, Long whId ){
		String hql = "from Owner o where o.code =:ownerCode and o.plant.wh.id=:whId";
		List<Owner> owners = commonDao.findByQuery(hql, new String[] { "ownerCode", "whId" }, new Object[] { ownerCode, whId });
		if (owners != null && owners.size() > 0) {
			return owners.get(0);
		}
		return null;
	}
	
	// 商品
	public Sku getSku( String skuCd , Long ownerId ){
		String hql = "from Sku s where s.code =:skuCd and s.owner.id=:ownerId";
		List<Sku> skus = commonDao.findByQuery(hql, new String[] { "skuCd", "ownerId" }, new Object[] { skuCd, ownerId });
		if (skus != null && skus.size() > 0) {
			return skus.get(0);
		}
		return null;
	}
	
	// 管理公司
	public Plant getPlant(String plantCode, Long whId) {
		String hql = "from Plant p where p.code =:plantCode and p.wh.id=:whId";
		List<Plant> plants = commonDao.findByQuery(hql, new String[] { "plantCode", "whId" }, new Object[] { plantCode, whId });
		if (plants != null && plants.size() > 0) {
			return plants.get(0);
		}
		return null;
	}
	
	// 收件人
	public BizOrg getShop( String shopCd , Long ownerId ){
		String hql = "from BizOrg b where b.code =:shopCd and b.owner.id=:ownerId and b.type = 'C'";
		List<BizOrg> shops = commonDao.findByQuery(hql, new String[] { "shopCd", "ownerId" }, new Object[] { shopCd, ownerId });
		if (shops != null && shops.size() > 0) {
			return shops.get(0);
		}
		return null;
	}
	
	// 承运商
	public Carrier getCarrier( String carrierCode , Long plantId ){
		String hql = "from Carrier c where c.code =:carrierCode and c.plant.id=:plantId ";
		List<Carrier> carriers = commonDao.findByQuery(hql, new String[] { "carrierCode", "plantId" }, new Object[] { carrierCode, plantId });
		if (carriers != null && carriers.size() > 0) {
			return carriers.get(0);
		}
		return null;
	}
	
	// 数组转String
	public static String toString4Arr( String[] csvLine ){
		StringBuilder result = new StringBuilder();
		
		if( csvLine != null && csvLine.length > 0 ){
			for( String csvColumn : csvLine ){
				if( result.length() > 0 ){
					result.append(",");
				}
				
				result.append(csvColumn.replaceAll(",", "."));
			}
		}
		
		return result.toString();
	}
	
	// 仓库
	public Warehouse getWarehouse( String whCode ){
		String hql = "from Warehouse wh where wh.code =:whCode ";
		List<Warehouse> whs = commonDao.findByQuery(hql, new String[] { "whCode" }, new Object[] { whCode });
		if (whs != null && whs.size() > 0) {
			return whs.get(0);
		}
		return null;
	}
	
	// 库区
	public WhArea getWhArea( String areaCode, Long whId ){
		String hql = "from WhArea area where area.code =:areaCode and area.wh.id = :whId ";
		List<WhArea> whAreas = commonDao.findByQuery(hql, new String[] { "areaCode", "whId" }, new Object[] { areaCode, whId });
		if (whAreas != null && whAreas.size() > 0) {
			return whAreas.get(0);
		}
		return null;
	}
	
	// 功能区
	public StorageType getStorageType( String stCode, Long whAreaId ){
		String hql = "from StorageType st where st.code =:stCode and st.wa.id = :waId ";
		List<StorageType> sts = commonDao.findByQuery(hql, new String[] { "stCode", "waId" }, new Object[] { stCode, whAreaId });
		if (sts != null && sts.size() > 0) {
			return sts.get(0);
		}
		return null;
	}
	
	// 库位类型
	public BinProperties getBinProperties( String bpCode, Long whId ){
		String hql = "from BinProperties bp where bp.code =:bpCode and bp.wh.id = :whId ";
		List<BinProperties> bps = commonDao.findByQuery(hql, new String[] { "bpCode", "whId" }, new Object[] { bpCode, whId });
		if (bps != null && bps.size() > 0) {
			return bps.get(0);
		}
		return null;
	}
	
	// 库位
	public Bin getBin( String binCode, Long whId ){
		String hql = "from Bin bin where bin.binCode =:binCode and bin.wh.id = :whId ";
		List<Bin> bins = commonDao.findByQuery(hql, new String[] { "binCode", "whId" }, new Object[] { binCode, whId });
		if (bins != null && bins.size() > 0) {
			return bins.get(0);
		}
		return null;
	}
}
