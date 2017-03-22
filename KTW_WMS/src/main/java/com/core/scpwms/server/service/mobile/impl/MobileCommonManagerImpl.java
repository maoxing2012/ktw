package com.core.scpwms.server.service.mobile.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.core.business.exception.BusinessException;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuStockDiv;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuInterCode;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.QuantInventory;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.service.mobile.MobileCommonManager;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.LocaleUtil;
import com.core.scpwms.server.util.QuantityUtil;

public class MobileCommonManagerImpl extends DefaultBaseManager implements MobileCommonManager {

	@Override
	public List<Long> getLaborId(Long userId) {
		String hql = " select labor.id from Labor labor where labor.user.id = :userId ";
		return commonDao.findByQuery(hql, "userId", userId);
	}

	@Override
	public List<Map<String, Object>> getOwnerList(Long userId) {
		String hql = " select distinct labor.wh.id "
				+ " from Labor labor "
				+ " where labor.user.id = :userId ";
		List<Long> whIds = commonDao.findByQuery(hql, "userId", userId);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		if( whIds == null || whIds.size() == 0 )
			return result;
		
		// 仓库ID下，所有有效的货主
		String hql2 = " from Owner owner where owner.plant.wh.id in (:whIds) order by owner.code ";
		List<Owner> owners = commonDao.findByQuery(hql2, "whIds", whIds);
		if( owners == null || owners.size() == 0 )
			return result;
		
		for( Owner owner : owners ){
			Map<String, Object> ownerMap = new HashMap<String, Object>();
			ownerMap.put("id", owner.getId());
			ownerMap.put("code", owner.getCode());
			ownerMap.put("name", owner.getShortName());
			ownerMap.put("whId", owner.getPlant().getWh().getId());
			ownerMap.put("whCode", owner.getPlant().getWh().getCode());
			ownerMap.put("whName", owner.getPlant().getWh().getShortName());
			
			result.add(ownerMap);
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getSkuIdInfos(Long whId, Long ownerId, String skuBarCode) {
		if( StringUtils.isEmpty(skuBarCode) )
			return null;
		
		if( skuBarCode.contains("$") ){
			skuBarCode = skuBarCode.split("\\$")[0];
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		// 如果是LOT开头
		if( skuBarCode.startsWith("LOT") ){
			String hql = " select qi.id from QuantInventory qi "
					+ " where qi.wh.id = :whId "
					+ " and qi.quant.lotSequence = :lot ";
			List<Long> qiIds = commonDao.findByQuery(hql, new String[]{"whId", "lot"}, new Object[]{whId, skuBarCode});
			if( qiIds == null || qiIds.size() == 0 )
				return null;
			QuantInventory qi = commonDao.load(QuantInventory.class, qiIds.get(0));
			result.put("skuId", qi.getQuant().getSku().getId());
			result.put("skuCode", qi.getQuant().getSku().getCode());
			result.put("skuName", qi.getQuant().getSku().getName());
			result.put("unit", qi.getQuant().getSku().getProperties().getPackageInfo().getP1000().getName());
			result.put("coefficient", qi.getQuant().getSku().getProperties().getPackageInfo().getP1000().getCoefficient().intValue());
			return result;
		}
		// 非LOT开头
		else{
			// 作为商品编号或者Jancode查询
			String hql = " select sku.id from Sku sku where sku.owner.id = :ownerId and (sku.code= :skuCode or sku.code1 = :skuCode)";
			List<Long> skuIds = commonDao.findByQuery(hql, new String[]{"ownerId", "skuCode"}, new Object[]{ownerId, skuBarCode});
			// 只有1条
			if( skuIds != null && skuIds.size() == 1 ){
				Sku sku = commonDao.load(Sku.class, skuIds.get(0));
				result.put("skuId", sku.getId());
				result.put("skuCode", sku.getCode());
				result.put("skuName", sku.getName());
				result.put("unit", sku.getProperties().getPackageInfo().getP1000().getName());
				result.put("coefficient", sku.getProperties().getPackageInfo().getP1000().getCoefficient().intValue());
				return result;
			}
			// 多条
			else if( skuIds != null && skuIds.size() > 1 ){
				List<Map<String, Object>> skuList = new ArrayList<Map<String,Object>>(skuIds.size());
				for( Long skuId : skuIds ){
					Map<String, Object> skuMap = new HashMap<String, Object>();
					Sku sku = commonDao.load(Sku.class, skuId);
					skuMap.put("id", sku.getId());
					skuMap.put("code", sku.getCode());
					skuMap.put("name", sku.getName());
					skuMap.put("unit", sku.getProperties().getPackageInfo().getP1000().getName());
					skuMap.put("coefficient",  1L);
					skuList.add(skuMap);
				}
				result.put("skuList", skuList);
				result.put("skuId", 0L);
				result.put("skuCode", "");
				result.put("skuName", "");
				result.put("unit", "");
				result.put("coefficient", 0L);
				return result;
			}
			// 没有，作为条形码查询
			else{
				String hql2 = "select stc.id from SkuInterCode stc where stc.sku.owner.id = :ownerId and stc.barCode = :skuCode order by stc.sku.code";
				List<Long> interCodeIds = commonDao.findByQuery(hql2, new String[]{"ownerId", "skuCode"}, new Object[]{ownerId, skuBarCode});
				if( interCodeIds == null || interCodeIds.size() == 0 ){
					return null;
				}
				// 只有1条
				else if( interCodeIds != null && interCodeIds.size() == 1 ){
					SkuInterCode interCode = commonDao.load(SkuInterCode.class, interCodeIds.get(0));
					result.put("skuId", interCode.getSku().getId());
					result.put("skuCode", interCode.getSku().getCode());
					result.put("skuName", interCode.getSku().getName());
					
					// 包装系数
					Double packCof = interCode.getPackDetail().getCoefficient();
					result.put("unit", interCode.getPackDetail().getName() == null ? "" : interCode.getPackDetail().getName());
					if( packCof == null || packCof.intValue() < 1 ){
						result.put("coefficient",  1);
					}
					else{
						result.put("coefficient",  packCof.intValue());
					}
					
					return result;
				}
				// 多条
				else{
					List<Map<String, Object>> skuList = new ArrayList<Map<String,Object>>(interCodeIds.size());
					for( Long interCodeId : interCodeIds ){
						Map<String, Object> skuMap = new HashMap<String, Object>();
						SkuInterCode interCode = commonDao.load(SkuInterCode.class, interCodeId);
						skuMap.put("id", interCode.getSku().getId());
						skuMap.put("code", interCode.getSku().getCode());
						skuMap.put("name", interCode.getSku().getName());
						
						Double packCof = interCode.getPackDetail().getCoefficient();
						skuMap.put("unit", interCode.getPackDetail().getName() == null ? "" : interCode.getPackDetail().getName());
						if( packCof == null || packCof.intValue() < 1 ){
							skuMap.put("coefficient",  1);
						}
						else{
							skuMap.put("coefficient",  packCof.intValue());
						}
						skuList.add(skuMap);
					}
					result.put("skuList", skuList);
					result.put("skuId", 0L);
					result.put("skuCode", "");
					result.put("skuName", "");
					result.put("unit", "");
					result.put("coefficient", 0L);
					return result;
				}
			}
		}
	}
	
	@Override
	public Long getSkuId(Long whId, Long ownerId, String skuBarCode) {
		if( StringUtils.isEmpty(skuBarCode) )
			return null;
		
		if( skuBarCode.contains("$") ){
			skuBarCode = skuBarCode.split("\\$")[0];
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		// 如果是LOT开头
		if( skuBarCode.startsWith("LOT") ){
			String hql = " select qi.id from QuantInventory qi where qi.wh.id = :whId and qi.quant.lotSequence = :lot ";
			List<Long> qiIds = commonDao.findByQuery(hql, new String[]{"whId", "lot"}, new Object[]{whId, skuBarCode});
			if( qiIds == null || qiIds.size() == 0 )
				return null;
			QuantInventory qi = commonDao.load(QuantInventory.class, qiIds.get(0));
			return qi.getQuant().getSku().getId();
		}
		// 非LOT开头
		else{
			// 作为商品编号或者Jancode查询
			String hql = " select sku.id from Sku sku where sku.owner.id = :ownerId and (sku.code= :skuCode or sku.code1 = :skuCode)";
			List<Long> skuIds = commonDao.findByQuery(hql, new String[]{"ownerId", "skuCode"}, new Object[]{ownerId, skuBarCode});
			// 只有1条
			if( skuIds != null && skuIds.size() == 1 ){
				Sku sku = commonDao.load(Sku.class, skuIds.get(0));
				return sku.getId();
			}
			// 多条
			else if( skuIds != null && skuIds.size() > 1 ){
				return null;
			}
			// 没有，作为条形码查询
			else{
				String hql2 = "select stc.id from SkuInterCode stc where stc.sku.owner.id = :ownerId and stc.barCode = :skuCode order by stc.sku.code";
				List<Long> interCodeIds = commonDao.findByQuery(hql2, new String[]{"ownerId", "skuCode"}, new Object[]{ownerId, skuBarCode});
				if( interCodeIds == null || interCodeIds.size() == 0 ){
					return null;
				}
				// 只有1条
				else if( interCodeIds != null && interCodeIds.size() == 1 ){
					SkuInterCode interCode = commonDao.load(SkuInterCode.class, interCodeIds.get(0));
					return interCode.getSku().getId();
				}
				// 多条
				else{
					return null;
				}
			}
		}
	}

	@Override
	public Map<String, Object> getSkuInfos(Long whId, Long ownerId, Long skuId) {
		Sku sku = commonDao.load(Sku.class, skuId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("skuCode", sku.getCode());
		result.put("skuName", sku.getName());
		result.put("specs", sku.getSpecs());
		result.put("csIn", sku.getProperties().getPackageInfo().getP2000().getCoefficient().intValue());
		result.put("blIn", sku.getProperties().getPackageInfo().getP2100().getCoefficient().intValue());
		result.put("csUnit", sku.getProperties().getPackageInfo().getP2000().getName());
		result.put("blUnit", sku.getProperties().getPackageInfo().getP2100().getName());
		result.put("psUnit", sku.getProperties().getPackageInfo().getP1000().getName());
		result.put("tempDiv", LocaleUtil.getText(EnuTemperatureDiv.class.getSimpleName() + "." + sku.getTempDiv()));
		result.put("stockDiv", LocaleUtil.getText(EnuStockDiv.class.getSimpleName() + "." + sku.getStockDiv()));
		
		String hql = "select coalesce(sum(inv.baseQty),0) from Inventory inv "
				+ " where inv.quantInv.quant.sku.id = :skuId "
				+ " and inv.owner.id = :ownerId "
				+ " and inv.wh.id = :whId ";
		Double invQty = (Double)commonDao.findByQueryUniqueResult(hql, 
				new String[]{"skuId", "ownerId", "whId"}, new Object[]{skuId, ownerId, whId});
		//30箱1中箱1件（311件）
		result.put("qtyInfo", QuantityUtil.getQtyInfo(invQty, 
				sku.getProperties().getPackageInfo().getP2000(), 
				sku.getProperties().getPackageInfo().getP2100(), 
				sku.getProperties().getPackageInfo().getP1000()) 
				+ "(" + invQty.intValue() + sku.getProperties().getPackageInfo().getP1000().getName() + ")");
		
		return result;
	}
	
	@Override
	public Map<String, Object> getSkuInfos(Long whId, Long ownerId, String qrCode) {
		//ロット番号,货主CD，商品CD，賞味期限（YYYYMMDD），在庫ステータスCD，入库日期（YYYYMMDD），入库单号
		if( qrCode == null || !qrCode.startsWith("LOT") ){
			throw new BusinessException("不法QRコード");
		}
		
		String[] qrInfos = qrCode.split("\\$");
		if( qrInfos.length < 6 ){
			throw new BusinessException("不法QRコード");
		}
		
		String lotSeq = qrInfos[0];
		String skuCode = qrInfos[2];
		String invStatus = qrInfos[4];
		Date inboundDate = DateUtil.getDate(qrInfos[5], DateUtil.PURE_DATE_FORMAT);
		String asnNumber = qrInfos.length >= 7 ? qrInfos[6] : "";
		
		String hql0 = " from Sku sku where sku.owner.id = :ownerId and sku.code = :skuCode ";
		List<Sku> skus = commonDao.findByQuery(hql0, new String[]{"ownerId", "skuCode"}, new Object[]{ownerId, skuCode});
		
		if( skus == null || skus.size() == 0 ){
			throw new BusinessException("該当する商品情報がみつかりません。");
		}
		
		Sku sku = skus.get(0);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("skuCode", sku.getCode());
		result.put("skuName", sku.getName());
		result.put("specs", sku.getSpecs());
		result.put("csIn", sku.getProperties().getPackageInfo().getP2000().getCoefficient().intValue());
		result.put("blIn", sku.getProperties().getPackageInfo().getP2100().getCoefficient().intValue());
		result.put("csUnit", sku.getProperties().getPackageInfo().getP2000().getName());
		result.put("blUnit", sku.getProperties().getPackageInfo().getP2100().getName());
		result.put("psUnit", sku.getProperties().getPackageInfo().getP1000().getName());
		result.put("tempDiv", LocaleUtil.getText(EnuTemperatureDiv.class.getSimpleName() + "." + sku.getTempDiv()));
		result.put("stockDiv", LocaleUtil.getText(EnuInvStatus.class.getSimpleName() + "." + invStatus));
		
		String hql = "select coalesce(sum(inv.baseQty),0) from Inventory inv "
				+ " where 1=1"
				+ " and inv.quantInv.quant.sku.id = :skuId "
				+ " and inv.owner.id = :ownerId "
				+ " and inv.wh.id = :whId "
				+ " and inv.status = :invStatus "
				+ " and coalesce(inv.trackSeq,'') = :asnNumber"
				+ " and inv.inboundDate = :inboundDate"
				+ " and inv.quantInv.quant.lotSequence = :lotSeq";
		
		Double invQty = (Double)commonDao.findByQueryUniqueResult(hql, 
				new String[]{"skuId", "ownerId", "whId", "invStatus", "asnNumber", "inboundDate", "lotSeq"}, 
				new Object[]{sku.getId(), ownerId, whId, invStatus, asnNumber, inboundDate, lotSeq});
		
		if( invQty == null )
			invQty = 0D;
		
		//30箱1中箱1件（311件）
		result.put("qtyInfo", QuantityUtil.getQtyInfo(invQty, 
				sku.getProperties().getPackageInfo().getP2000(), 
				sku.getProperties().getPackageInfo().getP2100(), 
				sku.getProperties().getPackageInfo().getP1000()) 
				+ "(" + invQty.intValue() + sku.getProperties().getPackageInfo().getP1000().getName() + ")");
		
		return result;
	}


	@Override
	public Long getLaborIdFirst(Long userId) {
		List<Long> laborIds = getLaborId(userId);
		
		if( laborIds != null && laborIds.size() > 0 )
			return laborIds.get(0);
		return null;
	}
}
