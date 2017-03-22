package com.core.scpwms.server.service.mobile.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.business.exception.BusinessException;
import com.core.business.model.security.User;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.WmsConstant4Ktw;
import com.core.scpwms.server.enumerate.EnuCountMethod;
import com.core.scpwms.server.enumerate.EnuCountStatus;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.count.CountPlan;
import com.core.scpwms.server.model.count.CountRecord;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.count.CountPlanManager;
import com.core.scpwms.server.service.mobile.MobileCommonManager;
import com.core.scpwms.server.service.mobile.MobileCountManager;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.LocaleUtil;
import com.core.scpwms.server.util.QuantityUtil;

public class MobileCountManagerImpl extends DefaultBaseManager implements MobileCountManager {
	private MobileCommonManager commonManager;
	private CountPlanManager countPlanManager;
	
	public MobileCommonManager getCommonManager() {
		return this.commonManager;
	}

	public void setCommonManager(MobileCommonManager commonManager) {
		this.commonManager = commonManager;
	}

	public CountPlanManager getCountPlanManager() {
		return this.countPlanManager;
	}

	public void setCountPlanManager(CountPlanManager countPlanManager) {
		this.countPlanManager = countPlanManager;
	}

	@Override
	public List<Map<String, Object>> searchCountPlan(Long whId, Long ownerId, String countPlanNumber) {
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();

		StringBuffer hql = new StringBuffer("select cp.id from CountPlan cp where 1=1");
		hql.append(" and cp.status in (200,300) ");
		hql.append(" and cp.warehouse.id = :whId  ");
		hql.append(" and cp.owner.id = :ownerId  ");
		paramNames.add("whId");
		params.add(whId);
		paramNames.add("ownerId");
		params.add(ownerId);
		
		if( countPlanNumber != null && countPlanNumber.trim().length() > 0 ){
			hql.append(" and cp.code = :code ");
			paramNames.add("code");
			params.add(countPlanNumber);
		}
		
		List<Long> cps = commonDao.findByQuery(hql.toString(), paramNames.toArray(new String[paramNames.size()]), params.toArray(new Object[params.size()]));
		
		if( cps != null && cps.size() > 0 ){
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>(cps.size());
			
			for( Long cpId : cps ){
				CountPlan cp = commonDao.load(CountPlan.class, cpId);
				
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("id", cp.getId());
				row.put("countPlanNumber", cp.getCode());
				row.put("description", cp.getDescription() == null ? "" : cp.getDescription());
				row.put("countMethod", LocaleUtil.getText( EnuCountMethod.class.getSimpleName() + "." + cp.getCountMethod() ));
				row.put("status", LocaleUtil.getText( EnuCountStatus.class.getSimpleName() + "." + cp.getStatus()));
//				row.put("planQty", cp.getPlanQty());
//				row.put("executeQty", cp.getCountQty()== null ? 0 : cp.getCountQty());
//				row.put("planDate", cp.getPlanDate() == null ? null : DateUtil.getStringDate(cp.getPlanDate(), DateUtil.HYPHEN_DATE_FORMAT) );
//				row.put("blind", cp.getBlindCount() != null && cp.getBlindCount().booleanValue() ? "盲盘" : "" );
//				row.put("isBlind", cp.getBlindCount() != null && cp.getBlindCount() ? "1" : "0");
//				row.put("ownerCode", cp.getOwner() == null ? null : cp.getOwner().getCode());
//				row.put("ownerName", cp.getOwner() == null ? null : cp.getOwner().getName());
//				row.put("ownerId", cp.getOwner() == null ? null : cp.getOwner().getId());
				
				result.add(row);
			}
			return result;
		}
		return null;
	}

	@Override
	public Map<String, Object> getCountInfoByQr(Long countPlanId, String binCode, String qrCode) {
		CountPlan cp = commonDao.load(CountPlan.class, countPlanId);
		
		// 货位
		String hql0 = " from Bin bin where bin.wh.id = :whId and bin.binCode = :binCode ";
		List<Bin> bins = commonDao.findByQuery(hql0, 
				new String[]{"whId", "binCode"}, new Object[]{cp.getWarehouse().getId(), binCode});
		
		if( bins == null || bins.size() == 0 ){
			throw new BusinessException(binCode + "　該当する棚が見つかりません。");
		}
		
		//ロット番号$货主CD$商品CD$賞味期限（YYYYMMDD）$在庫ステータスCD$入库日期（YYYYMMDD）$入库单号
		if( qrCode == null || !qrCode.startsWith("LOT") ){
			throw new BusinessException("不法QRコード");
		}
		
		String[] qrInfos = qrCode.split("\\$");
		if( qrInfos.length < 6 ){
			throw new BusinessException("不法QRコード");
		}
		
		// Lot
		String hql = " from Quant quant where quant.lotSequence = :lotSequence ";
		List<Quant> quants = commonDao.findByQuery(hql, "lotSequence", qrInfos[0]);
		
		if( quants == null || quants.size() == 0 ){
			throw new BusinessException(qrInfos[0] + "該当するロット情報が見つかりません。");
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("invStatus", LocaleUtil.getText( EnuInvStatus.class.getSimpleName() + "." + qrInfos[4]));
		result.put("binId", bins.get(0).getId());
		
		Sku sku = quants.get(0).getSku();
		PackageDetail csPack = sku.getProperties().getPackageInfo().getP2000();
		PackageDetail blPack = sku.getProperties().getPackageInfo().getP2100();
		PackageDetail psPack = sku.getProperties().getPackageInfo().getP1000();
		
		result.put("skuId", sku.getId());
		result.put("skuCode", sku.getCode());
		result.put("skuName", sku.getName());
		result.put("csIn", csPack.getCoefficient() == null ? 0 : csPack.getCoefficient().intValue());
		result.put("blIn", blPack.getCoefficient() == null ? 0 : blPack.getCoefficient().intValue());
		result.put("expDate", qrInfos[3]);
		result.put("csUnit", csPack.getName() == null ? "" : csPack.getName());
		result.put("blUnit", blPack.getName() == null ? "" : blPack.getName());
		result.put("psUnit", psPack.getName() == null ? "" : psPack.getName());
		
		CountRecord cr = getCountRecord(countPlanId, cp.getOwner().getId(), qrInfos, bins.get(0).getId(), quants.get(0).getId());
		
 		if( cr != null){
 			result.put("countRecordId", cr.getId());
 			if( cp.getBlindCount() != null && cp.getBlindCount().booleanValue() ){
 				result.put("qtyInfo", "************");
 			}
 			else{
 				result.put("qtyInfo", QuantityUtil.getQtyInfo(cr.getInvBaseQty(), csPack, blPack, psPack) + "(" + cr.getInvBaseQty().intValue() + psPack.getName() + ")" );
 			}
 			
		}
		else{
			result.put("countRecordId", 0L);
			if( cp.getBlindCount() != null && cp.getBlindCount().booleanValue() ){
				result.put("qtyInfo", "************");
 			}
 			else{
 				result.put("qtyInfo", "0" + psPack.getName());
 			}
		}
		
		return result;
	}
	
	@Override
	public void executeCount(Long whId, Long userId, Long ownerId, Long countPlanId, Long binId, String qrCode, Double qty) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		User user = commonDao.load(User.class, userId);
		
		WarehouseHolder.setWarehouse(wh);
		UserHolder.setUser(user);
		
		//ロット番号,货主CD，商品CD，賞味期限（YYYYMMDD），在庫ステータスCD，入库日期（YYYYMMDD），入库单号
		if( qrCode == null || !qrCode.startsWith("LOT") ){
			throw new BusinessException("不法QRコード");
		}
		
		String[] qrInfos = qrCode.split("\\$");
		if( qrInfos.length < 6 ){
			throw new BusinessException("不法QRコード");
		}
		
		// Lot
		String hql = " from Quant quant where quant.lotSequence = :lotSequence ";
		List<Quant> quants = commonDao.findByQuery(hql, "lotSequence", qrInfos[0]);
		
		if( quants == null || quants.size() == 0 ){
			throw new BusinessException(qrInfos[0] + "該当するロット情報が見つかりません。");
		}
		
		Date expDate = DateUtil.getDate(qrInfos[WmsConstant4Ktw.QR_INX_EXP_DATE], DateUtil.PURE_DATE_FORMAT);
		Date ibdDate = DateUtil.getDate(qrInfos[WmsConstant4Ktw.QR_INX_IBD_DATE], DateUtil.PURE_DATE_FORMAT);
		countPlanManager.saveRegister(countPlanId, quants.get(0).getSku().getId(), binId, qty, 
				expDate, qrInfos[WmsConstant4Ktw.QR_INX_INV_STATUS], 
				qrInfos.length >= 7 ? qrInfos[WmsConstant4Ktw.QR_INX_ASN_NUM] : null, 
				ibdDate, commonManager.getLaborIdFirst(userId));
	}
	
	private CountRecord getCountRecord( Long cpId, Long ownerId, String[] qrInfos , Long binId, Long quantId){
		
		String hql1 = " from CountRecord cr where 1=1 "
				+ " and cr.countPlan.id = :countPlanId "
				+ " and cr.invInfo.quant.id = :quantId"
				+ " and cr.invInfo.owner.id = :ownerId"
				+ " and cr.invInfo.bin.id = :binId"
				+ " and cr.invInfo.inboundDate = :inboundDate"
				+ " and cr.invInfo.invStatus = :invStatus "
				+ " and coalesce(cr.invInfo.trackSeq,'') = :asnNumber "
				+ " order by cr.id ";
		
		List<CountRecord> crs = commonDao.findByQuery(hql1, 
				new String[]{"countPlanId", "quantId", "ownerId", "binId", "inboundDate", "invStatus", "asnNumber"}, 
				new Object[]{cpId, quantId, ownerId, binId, DateUtil.getDate(qrInfos[5], DateUtil.PURE_DATE_FORMAT), qrInfos[4], qrInfos.length >= 7 ? qrInfos[6] : ""});
		
		if( crs != null && crs.size() > 0 )
			return crs.get(0);
		
		return null;
	}

	@Override
	public void checkBin4Count(Long whId, Long userId, Long ownerId, Long countPlanId, String binCode) {
		CountPlan cp = commonDao.load(CountPlan.class, countPlanId);
		
		// 如果是全盘，对库位没有限制。
		if( EnuCountMethod.ALL.equals(cp.getCountMethod()) ){
			return;
		}
		// 如果不是全盘，要验证库位在本次盘点计划中。
		else{
			String hql = "select cr.id from CountRecord cr where cr.countPlan.id = :cpId and cr.invInfo.bin.binCode = :binCode ";
			List<Long> crIdsList = commonDao.findByQuery(hql, new String[]{"cpId", "binCode"}, new Object[]{countPlanId, binCode});
			if( crIdsList == null || crIdsList.size() == 0 ){
				throw new BusinessException("棚番" + binCode + "、今回の棚卸範囲からはずれています。");
			}
			else{
				return;
			}
		}
	}

}
