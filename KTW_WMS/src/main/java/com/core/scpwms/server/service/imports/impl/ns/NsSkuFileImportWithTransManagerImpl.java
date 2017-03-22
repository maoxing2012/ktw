package com.core.scpwms.server.service.imports.impl.ns;

import java.util.List;

import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.enumerate.EnuPackageLevel;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.PackageInfo;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuInterCode;
import com.core.scpwms.server.model.common.SkuProperties;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.service.imports.impl.BaseFileImportWithTransManagerImpl;

public class NsSkuFileImportWithTransManagerImpl extends BaseFileImportWithTransManagerImpl {
	
	public Sku updateSku4NS( Long whId, NsSkuBean nsSku ){
		Owner owner = getOwner(nsSku.getOwnerCode(), whId);
		if( owner == null )
			return null;
		Sku sku = getSku(nsSku.getSkuCode(), owner.getId());
		Boolean isNew = Boolean.FALSE;
		
		// 新建
		if( sku == null ){
			isNew = Boolean.TRUE;
			sku = new Sku();
			sku.setOwner(owner);
			sku.setPlant(owner.getPlant());
			sku.setProperties(new SkuProperties());
			
			PackageInfo pi = new PackageInfo();
			sku.getProperties().setPackageInfo(pi);
			
			sku.getProperties().getPackageInfo().getP1000().setPackageInfo(pi);
			sku.getProperties().getPackageInfo().getP1000().setPackageLevel(EnuPackageLevel.PK1000);
			
			sku.getProperties().getPackageInfo().getP2100().setPackageInfo(pi);
			sku.getProperties().getPackageInfo().getP2100().setPackageLevel(EnuPackageLevel.PK2100);
			
			sku.getProperties().getPackageInfo().getP2000().setPackageInfo(pi);
			sku.getProperties().getPackageInfo().getP2000().setPackageLevel(EnuPackageLevel.PK2000);
			
			sku.getProperties().getPackageInfo().getP3000().setPackageInfo(pi);
			sku.getProperties().getPackageInfo().getP3000().setPackageLevel(EnuPackageLevel.PK3000);
			
		}
		
		// 更新
		sku.setCode(nsSku.getSkuCode());
		sku.setName(nsSku.getSkuName());
		sku.setCode1(nsSku.getJanCode());
		sku.setCode2(nsSku.getSpecNo());
		sku.setSpecs(nsSku.getSpecs());
		
		Double grossWeight = DoubleUtil.round(nsSku.getWeight() * nsSku.getBaseCheckQty(), 4);
		sku.setGrossWeight(grossWeight);
		
		sku.setTempDiv(nsSku.getWmsTempDivCode());
		sku.getProperties().setBaseCheckQty4NS(nsSku.getBaseCheckQty());
		sku.getProperties().setBaseUnitCode4NS(nsSku.getBaseUnitCode());
		sku.getProperties().setBaseUnitName4NS(nsSku.getBaseUnitName());
		sku.getProperties().setPackFlg4NS(getLong(nsSku.getPackFlg()));
		sku.getProperties().setAlertLeadingDays(6);// 固定是6
		
		// 冷藏冷冻品都不能同捆
		if( !EnuTemperatureDiv.CW.equals(sku.getTempDiv())){
			sku.getProperties().setPackFlg4NS(2L);
		}
		
		double baseCheckQty = nsSku.getBaseCheckQty();
		int csIn = DoubleUtil.div(nsSku.getCsIn(), baseCheckQty).intValue() ;
		int blIn = DoubleUtil.div(nsSku.getBlIn(), baseCheckQty).intValue() ;
		String csUnitCd = nsSku.getCsUnitCode();
		String csUnitNm = nsSku.getCsUnitName();
		String blUnitCd = nsSku.getBlUnitCode();
		String blUnitNm = nsSku.getBlUnitName();
		String psUnitCd = nsSku.getBaseUnitCode();
		String psUnitNm = nsSku.getBaseUnitName();
		
		if(blIn <= 1){
			blIn = 0;
			psUnitCd = blUnitCd;
			psUnitNm = blUnitNm;
			blUnitCd = null;
			blUnitNm = null;
		}
		
		sku.getProperties().getPackageInfo().setCode(sku.getCode());
		sku.getProperties().getPackageInfo().setName(sku.getName());
		sku.getProperties().getPackageInfo().getP1000().setCoefficient(1D);
		sku.getProperties().getPackageInfo().getP1000().setCode(psUnitCd);
		sku.getProperties().getPackageInfo().getP1000().setName(psUnitNm);
		
		sku.getProperties().getPackageInfo().getP2100().setCoefficient(new Double(blIn));
		sku.getProperties().getPackageInfo().getP2100().setCode(blUnitCd);
		sku.getProperties().getPackageInfo().getP2100().setName(blUnitNm);
		
		sku.getProperties().getPackageInfo().getP2000().setCoefficient(new Double(csIn));
		sku.getProperties().getPackageInfo().getP2000().setCode(csUnitCd);
		sku.getProperties().getPackageInfo().getP2000().setName(csUnitNm);
		
		commonDao.store(sku.getProperties().getPackageInfo());
		commonDao.store(sku.getProperties().getPackageInfo().getP1000());
		commonDao.store(sku.getProperties().getPackageInfo().getP2100());
		commonDao.store(sku.getProperties().getPackageInfo().getP2000());
		commonDao.store(sku.getProperties().getPackageInfo().getP3000());
		commonDao.store(sku);
		
		if( nsSku.getItfCode1() != null && nsSku.getItfCode1().trim().length() > 0 ){
			createBarCode(sku, EnuPackageLevel.PK2100, nsSku.getItfCode1().trim());
		}
		
		if( nsSku.getItfCode2() != null && nsSku.getItfCode2().trim().length() > 0 ){
			createBarCode(sku, EnuPackageLevel.PK2000, nsSku.getItfCode2().trim());
		}
		
		return sku;
	}
	
	private Boolean createBarCode(Sku sku, String packLevel, String itfCode) {
		if( sku == null )
			return null;
		
		if( itfCode == null || itfCode.trim().length() == 0 )
			return null;
		
		PackageDetail pd = getPackageDetail(sku.getProperties().getPackageInfo().getId(), packLevel);
		if( pd == null ){
			return null;
		}
		
		SkuInterCode barCode = getBarCode( sku.getId(), itfCode.trim());
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
		barCode.setBarCode(itfCode.trim());
		commonDao.store(barCode);
		
		return isNew;
	}
	
	private PackageDetail getPackageDetail( Long packId, String packLevel ){
		String hql = " from PackageDetail pd where pd.packageInfo.id = :packId and pd.packageLevel = :packLevel";
		List<PackageDetail> pds = commonDao.findByQuery(hql, new String[]{"packId", "packLevel"}, new Object[]{packId, packLevel});
		
		if( pds != null && pds.size() > 0 )
			return pds.get(0);
		
		return null;
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
