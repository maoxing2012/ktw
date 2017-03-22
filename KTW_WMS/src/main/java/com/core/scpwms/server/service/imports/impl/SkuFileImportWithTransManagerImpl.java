package com.core.scpwms.server.service.imports.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.enumerate.EnuFixDiv;
import com.core.scpwms.server.enumerate.EnuPackFlg;
import com.core.scpwms.server.enumerate.EnuPackageLevel;
import com.core.scpwms.server.enumerate.EnuStockDiv;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.PackageInfo;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuInterCode;
import com.core.scpwms.server.model.common.SkuProperties;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.service.imports.SkuFileImportWithTransManager;

public class SkuFileImportWithTransManagerImpl extends BaseFileImportWithTransManagerImpl implements SkuFileImportWithTransManager {
	private static final int COLUMN_NUM = 39;
	//0：ID	
	private static final int ID = 0;
	//1：荷主番号	
	private static final int OWNER_CD = 1;
	//2：荷主名	
	//private static final int OWNER_NM = 2;
	//3：商品コード
	private static final int SKU_CD = 3;
	//4：商品名	
	private static final int SKU_NM = 4;
	//5：状態	
	private static final int STATUS = 5;
	//6：規格	
	private static final int SPECS = 6;
	//7：温度帯区分CD	
	private static final int TEMP_DIV_CD = 7;
	//8：温度帯区分	
	//private static final int TEMP_DIV = 8;
	//9：在庫区分CD	
	private static final int STOCK_DIV_CD = 9;
	//10：在庫区分	
	//private static final int STOCK_DIV = 10;
	//11：不定貫区分CD	
	private static final int FIX_DIV_CD = 11;
	//12：不定貫区分	
	//private static final int FIX_DIV = 12;
	//13：ベンダー番号	
	private static final int VENDOR_CD = 13;
	//14：ベンダー名
	//private static final int VENDOR_NM = 14;
	//15：ブランド名	
	private static final int BRAND = 15;
	//16：総重量(KG)	
	private static final int WEIGHT = 16;
	//17：体積(M3)	
	private static final int VOLUME = 17;
	//18：JANコード	
	private static final int JAN_CODE = 18;
	//19：基本単位CD	
	private static final int PC_UNIT_CD = 19;
	//20：基本単位名	
	private static final int PC_UNIT_NM = 20;
	//21：ボール入数	
	private static final int BOL_UNIT_IN = 21;
	//22：ボール単位CD	
	private static final int BOL_UNIT_CD = 22;
	//23：ボール単位名	
	private static final int BOL_UNIT_NM = 23;
	//24：ケース入数	
	private static final int CS_UNIT_IN = 24;
	//25：ケース単位CD	
	private static final int CS_UNIT_CD = 25;
	//26：ケース単位名	
	private static final int CS_UNIT_NM = 26;
	//27：パレット入数	
	private static final int PT_UNIT_IN = 27;
	//28：パレット単位CD	
	private static final int PT_UNIT_CD = 28;
	//29：パレット単位名	
	private static final int PT_UNIT_NM = 29;
	//30：入荷期限日数	
	private static final int ASN_EXP_DAYS = 30;
	//31：出荷期限日数	
	private static final int OBD_EXP_DAYS = 31;
	//32：賞味期限日数	
	private static final int EXP_DAYS = 32;
	//33：基本チェック数(NS)	
	private static final int CHECK_QTY = 33;
	//34：基本単位コード(NS)	
	private static final int CHECK_UNIT_CD = 34;
	//35：基本単位名(NS)	
	private static final int CHECK_UNIT_NM = 35;
	//36：同梱可否フラグ(NS)	
	private static final int PACK_FLG_CD = 36;
	//37：同梱可否(NS)
	//private static final int PACK_FLG = 37;
	//38：賞味期限管理
	private static final int USE_EXPIRE = 38;
	
	@Override
	public Boolean createSku(List<String> excelLine, int index, List<String[]> result) {
		excelLine = formatExcelLine(excelLine, COLUMN_NUM);
		
		if( StringUtils.isEmpty(excelLine.get(OWNER_CD).trim()) ){
			result.add(new String[]{String.valueOf(index), "荷主番号	：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(SKU_CD).trim()) ){
			result.add(new String[]{String.valueOf(index), "商品コード：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(SKU_NM).trim()) ){
			result.add(new String[]{String.valueOf(index), "商品名：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(TEMP_DIV_CD).trim()) ){
			result.add(new String[]{String.valueOf(index), "温度帯区分CD：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(PC_UNIT_CD).trim()) ){
			result.add(new String[]{String.valueOf(index), "ピース単位CD：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(PC_UNIT_NM).trim()) ){
			result.add(new String[]{String.valueOf(index), "ピース単位名：必須項目。"});
			return null;
		}
		
		Owner owner = getOwner(excelLine.get(OWNER_CD).trim(), WarehouseHolder.getWarehouse().getId());
		if( owner == null ){
			result.add(new String[]{ String.valueOf(index), "荷主番号：" + excelLine.get(OWNER_CD) + "、マスタデータ不備。" });
			return null;
		}
		
		Boolean isNew = null;
		Sku sku = getSku(excelLine.get(SKU_CD).trim(), owner.getId());
		if( sku == null ){
			sku = new Sku();
			sku.setOwner(owner);
			sku.setPlant(owner.getPlant());
			sku.setCode(excelLine.get(SKU_CD).trim());
			sku.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			isNew = Boolean.TRUE;
		}
		else{
			sku.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			isNew = Boolean.FALSE;
		}
		
		sku.setName(excelLine.get(SKU_NM).trim());
		sku.setDisabled(getDisableDiv(excelLine.get(STATUS).trim()));
		sku.setSpecs(excelLine.get(SPECS).trim());
		sku.setTempDiv(getTempDiv(excelLine.get(TEMP_DIV_CD).trim()));
		sku.setStockDiv(getStockDiv(excelLine.get(STOCK_DIV_CD).trim()));
		sku.setFixDiv(getFixDiv(excelLine.get(FIX_DIV_CD).trim()));
		sku.setBrandName(excelLine.get(BRAND).trim());
		sku.setCode1(excelLine.get(JAN_CODE).trim());
		
		// Vendor
		if( !StringUtils.isEmpty( excelLine.get(VENDOR_CD).trim() ) ){
			BizOrg vendor = getVendor(owner.getPlant().getId(), excelLine.get(VENDOR_CD).trim());
			if( vendor == null ){
				result.add(new String[]{ String.valueOf(index), "ベンダー番号：" + excelLine.get(VENDOR_CD).trim() + "、マスタデータ不備。" });
				return null;
			}
			else{
				sku.setDefSupplier(vendor);
			}
		}
		else{
			sku.setDefSupplier(null);
		}
		// Weight
		if( !StringUtils.isEmpty(excelLine.get(WEIGHT).trim())){
			sku.setGrossWeight(getDouble(excelLine.get(WEIGHT).trim()));
		}
		// Volume
		if( !StringUtils.isEmpty(excelLine.get(VOLUME).trim())){
			sku.setVolume(getDouble(excelLine.get(VOLUME).trim()));
		}
		// EXP_DAYS
		if( sku.getProperties() == null ){
			sku.setProperties(new SkuProperties());
		}
		sku.getProperties().setPurchaseLeadTime(getInteger(excelLine.get(ASN_EXP_DAYS).trim()));
		sku.getProperties().setAlertLeadingDays(getInteger(excelLine.get(OBD_EXP_DAYS).trim()));
		sku.getProperties().setDayOfExpiry(getInteger(excelLine.get(EXP_DAYS).trim()));
		// USE_EXPIRY
		if( "false".equals(excelLine.get(USE_EXPIRE).trim()) || "いいは".equals(excelLine.get(USE_EXPIRE).trim()) || "0".equals(excelLine.get(USE_EXPIRE).trim()) ){
			sku.getProperties().setUseExpire(Boolean.FALSE);
		}
		else{
			sku.getProperties().setUseExpire(Boolean.TRUE);
		}
		
		// NS
		sku.getProperties().setBaseCheckQty4NS(getDouble(excelLine.get(CHECK_QTY).trim()));
		sku.getProperties().setBaseUnitCode4NS(excelLine.get(CHECK_UNIT_CD).trim());
		sku.getProperties().setBaseUnitName4NS(excelLine.get(CHECK_UNIT_NM).trim());
		sku.getProperties().setPackFlg4NS(getPackFlg(excelLine.get(PACK_FLG_CD).trim(), sku.getTempDiv()));
		
		// PackInfo
		PackageInfo pi = sku.getProperties().getPackageInfo();
		if( pi.isNew() ){
			pi.setCode(sku.getCode());
			pi.setName(sku.getName());
		}
		// PackDetail
		PackageDetail p1000 = pi.getP1000();
		if( p1000 == null ){
			p1000 = new PackageDetail();
			pi.setP1000(p1000);
		}
		if( p1000.isNew() ){
			p1000.setPackageLevel(EnuPackageLevel.PK1000);
			p1000.setPackageInfo(pi);
		}
		p1000.setCoefficient(1D);
		p1000.setCode(excelLine.get(PC_UNIT_CD).trim());
		p1000.setName(excelLine.get(PC_UNIT_NM).trim());
		
		PackageDetail p2100 = pi.getP2100();
		if( p2100 == null ){
			p2100 = new PackageDetail();
			pi.setP2100(p2100);
		}
		if( p2100.isNew() ){
			p2100.setPackageLevel(EnuPackageLevel.PK2100);
			p2100.setPackageInfo(pi);
		}
		p2100.setCoefficient( getDouble(excelLine.get(BOL_UNIT_IN).trim()) );
		p2100.setCode(excelLine.get(BOL_UNIT_CD).trim());
		p2100.setName(excelLine.get(BOL_UNIT_NM).trim());
		
		PackageDetail p2000 = pi.getP2000();
		if( p2000 == null ){
			p2000 = new PackageDetail();
			pi.setP2000(p2000);
		}
		if( p2000.isNew() ){
			p2000.setPackageLevel(EnuPackageLevel.PK2000);
			p2000.setPackageInfo(pi);
		}
		p2000.setCoefficient( getDouble(excelLine.get(CS_UNIT_IN).trim()) );
		p2000.setCode(excelLine.get(CS_UNIT_CD).trim());
		p2000.setName(excelLine.get(CS_UNIT_NM).trim());
		
		PackageDetail p3000 = pi.getP3000();
		if( p3000 == null ){
			p3000 = new PackageDetail();
			pi.setP3000(p3000);
		}
		if( p3000.isNew() ){
			p3000.setPackageLevel(EnuPackageLevel.PK3000);
			p3000.setPackageInfo(pi);
		}
		p3000.setCoefficient( getDouble(excelLine.get(PT_UNIT_IN).trim()) );
		p3000.setCode(excelLine.get(PT_UNIT_CD).trim());
		p3000.setName(excelLine.get(PT_UNIT_NM).trim());
		
		commonDao.store(pi);
		commonDao.store(p1000);
		commonDao.store(p2100);
		commonDao.store(p2000);
		commonDao.store(p3000);
		commonDao.store(sku);
		
		return isNew;
	}
	
	private PackageDetail getPackageDetail( Long packId, String packLevel ){
		String hql = " from PackageDetail pd where pd.packageInfo.id = :packId and pd.packageLevel = :packLevel";
		List<PackageDetail> pds = commonDao.findByQuery(hql, new String[]{"packId", "packLevel"}, new Object[]{packId, packLevel});
		
		if( pds != null && pds.size() > 0 )
			return pds.get(0);
		
		return null;
	}

	//0:ID	
	//1:荷主番号	
	//2:荷主名	
	//3:商品コード	
	//4:商品名	
	//5:包装単位CD	
	//6:包装単位	
	//7:バーコード
	@Override
	public Boolean createBarCode(List<String> excelLine, int index, List<String[]> result) {
		excelLine = formatExcelLine(excelLine, 8);
		
		if( StringUtils.isEmpty(excelLine.get(1).trim()) ){
			result.add(new String[]{String.valueOf(index), "荷主番号	：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(3).trim()) ){
			result.add(new String[]{String.valueOf(index), "商品コード：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(5).trim()) ){
			result.add(new String[]{String.valueOf(index), "包装単位CD：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(7).trim()) ){
			result.add(new String[]{String.valueOf(index), "バーコード：必須項目。"});
			return null;
		}
		
		Owner owner = getOwner(excelLine.get(1).trim(), WarehouseHolder.getWarehouse().getId());
		if( owner == null ){
			result.add(new String[]{ String.valueOf(index), "荷主番号：" + excelLine.get(1) + "、マスタデータ不備。" });
			return null;
		}
		
		Sku sku = getSku(excelLine.get(3).trim(), owner.getId());
		if( sku == null ){
			result.add(new String[]{ String.valueOf(index), "商品コード：" + excelLine.get(3) + "、マスタデータ不備。" });
			return null;
		}
		
		PackageDetail pd = getPackageDetail(sku.getProperties().getPackageInfo().getId(), excelLine.get(5).trim());
		if( pd == null ){
			result.add(new String[]{ String.valueOf(index), "包装単位CD：" + excelLine.get(5) + "、マスタデータ不備。" });
			return null;
		}
		
		SkuInterCode barCode = getBarCode( sku.getId(), excelLine.get(7).trim() );
		Boolean isNew = null;
		if( barCode == null ){
			barCode = new SkuInterCode();
			barCode.setSku(sku);
			isNew = Boolean.TRUE;
		}
		else{
			isNew = Boolean.FALSE;
		}
		
		barCode.setPackDetail(pd);
		barCode.setBarCode(excelLine.get(7).trim());
		commonDao.store(barCode);
		return isNew;
	}
	
	private SkuInterCode getBarCode( Long skuId, String barCode ){
		String hql = " select s.id from SkuInterCode s where s.sku.id = :skuId and s.barCode = :barCode ";
		List<Long> barCodeIds = commonDao.findByQuery(hql, new String[]{"skuId", "barCode"}, new Object[]{skuId, barCode});
		
		if( barCodeIds != null && barCodeIds.size() > 0 ){
			return commonDao.load(SkuInterCode.class, barCodeIds.get(0));
		}
		else{
			return null;
		}
	}

}
