/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: MobileInventoryManagerImpl.java
 */

package com.core.scpwms.server.service.mobile.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.core.business.exception.BusinessException;
import com.core.business.model.security.User;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuStoreRole;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.QuantInventory;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.inventory.InvManager;
import com.core.scpwms.server.service.mobile.MobileCommonManager;
import com.core.scpwms.server.service.mobile.MobileInventoryManager;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.LocaleUtil;
import com.core.scpwms.server.util.QuantityUtil;

/**
 * 库存操作
 * @author mousachi
 *
 */
public class MobileInventoryManagerImpl extends DefaultBaseManager implements MobileInventoryManager {
	private MobileCommonManager commonManager;
	private InvManager invManager;
	
	public MobileCommonManager getCommonManager() {
		return commonManager;
	}
	
	public void setCommonManager(MobileCommonManager commonManager) {
		this.commonManager = commonManager;
	}
	
	public InvManager getInvManager() {
		return invManager;
	}
	
	public void setInvManager(InvManager invManager) {
		this.invManager = invManager;
	}
	
	@Override
	public List<Map<String, Object>> getInventory(Long whId, Long ownerId, String barcode, String binCode) {
		if( barcode == null || barcode.trim().length() <= 0 ){
			throw new BusinessException("商品のバーコードをスキャンしてください。");
		}
		
		Long skuId = commonManager.getSkuId(whId, ownerId, barcode);
		if( skuId == null ){
			throw new BusinessException( "該当する商品情報がみつかりません。");
		}
		
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select inv.id from Inventory inv where 1=1");
		hql.append(" and inv.wh.id = :whId ");
		hql.append(" and inv.owner.id = :ownerId ");
		hql.append(" and inv.quantInv.quant.sku.id = :skuId");
		
		paramNames.add("whId");
		paramNames.add("ownerId");
		paramNames.add("skuId");
		params.add(whId);
		params.add(ownerId);
		params.add(skuId);
		
		if( !StringUtils.isEmpty(binCode) ){
			hql.append(" and inv.bin.binCode = :binCode");
			paramNames.add("binCode");
			params.add(binCode);
		}
		
		if( barcode.startsWith("LOT") ){
			String[] qrInfos = barcode.split("\\$");
			if( qrInfos.length < 6 ){
				throw new BusinessException("不法QRコード");
			}
			
			hql.append(" and inv.quantInv.quant.lotSequence = :lotSeq");
			hql.append(" and inv.status = :invStatus");
			hql.append(" and inv.inboundDate = :ibdDate");
			hql.append(" and coalesce(inv.trackSeq,'') = :asnNumber");
			
			paramNames.add("lotSeq");
			paramNames.add("invStatus");
			paramNames.add("ibdDate");
			paramNames.add("asnNumber");
			
			
			String lotSeq = qrInfos[0];
			String invStatus = qrInfos[4];
			Date inboundDate = DateUtil.getDate(qrInfos[5], DateUtil.PURE_DATE_FORMAT);
			String asnNumber = qrInfos.length >= 7 ? qrInfos[6] : "";
			
			params.add(lotSeq);
			params.add(invStatus);
			params.add(inboundDate);
			params.add(asnNumber);
		}
		
		hql.append(" order by inv.bin.binCode ");
		
		List<Long> invIds = commonDao.findByQuery(hql.toString(), paramNames.toArray(new String[paramNames.size()]), params.toArray(new Object[params.size()]));
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		if( invIds != null && invIds.size() > 0 ){
			for( Long invId : invIds ){
				Map<String,Object> invMap = new HashMap<String, Object>();
				Inventory inv = commonDao.load(Inventory.class, invId);
				QuantInventory quantInv = inv.getQuantInv();

				invMap.put("invId", inv.getId());
				invMap.put("skuId", quantInv.getQuant().getSku().getId());
				invMap.put("skuCode", quantInv.getQuant().getSku().getCode());
				invMap.put("skuName", quantInv.getQuant().getSku().getName());
				String expDate = quantInv.getQuant().getDispLot();
				expDate = expDate.substring(0, 4) + "-" +  expDate.substring(4, 6) + "-" +  expDate.substring(6, 8);
				invMap.put("expDate", expDate);
				invMap.put("qtyInfo",  QuantityUtil.getQtyInfo(inv.getBaseQty(), 
								quantInv.getQuant().getSku().getProperties().getPackageInfo().getP2000(), 
								quantInv.getQuant().getSku().getProperties().getPackageInfo().getP2100(), 
								quantInv.getQuant().getSku().getProperties().getPackageInfo().getP1000()) 
								+ "(" +  inv.getBaseQty().intValue() 
								+ quantInv.getQuant().getSku().getProperties().getPackageInfo().getP1000().getName() +")");
				invMap.put("qty", inv.getBaseQty().intValue());
				invMap.put("invStatus", EnuInvStatus.AVAILABLE.equals(inv.getStatus()) ? "" : LocaleUtil.getText(EnuInvStatus.class.getSimpleName() + "." + inv.getStatus()));
				invMap.put("binId", inv.getBin().getId());
				invMap.put("binCode", inv.getBin().getBinCode());
				
				result.add(invMap);
			}
		}
		else{
			return null;
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getInvInfoByQr(Long whId, Long ownerId, String binCode, String qrCode) {
		//ロット番号,货主CD，商品CD，賞味期限（YYYYMMDD），在庫ステータスCD，入库日期（YYYYMMDD），入库单号
		if( qrCode == null || !qrCode.startsWith("LOT") ){
			throw new BusinessException("不法QRコード");
		}
		
		String[] qrInfos = qrCode.split("\\$");
		if( qrInfos.length < 6 ){
			throw new BusinessException("不法QRコード");
		}
		
		String hql0 = " from Bin bin where bin.wh.id = :whId and bin.binCode = :binCode ";
		List<Bin> bins = commonDao.findByQuery(hql0, 
				new String[]{"whId", "binCode"}, new Object[]{whId, binCode});
		
		if( bins == null || bins.size() == 0 ){
			throw new BusinessException(binCode + "　該当する棚が見つかりません。");
		}
		
		// 只有收货区，存活区，不良品区的
		if( !bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R1000) &&
			!bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R3000)  &&
			!bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R4040) ){
			throw new BusinessException(bins.get(0).getStorageType().getName() + "　この保管ゾーンの在庫は移動不可。");
		}
		
		String lotSeq = qrInfos[0];
		String invStatus = qrInfos[4];
		Date inboundDate = DateUtil.getDate(qrInfos[5], DateUtil.PURE_DATE_FORMAT);
		String asnNumber = qrInfos.length >= 7 ? qrInfos[6] : "";
		
		String hql = " from Inventory inv where inv.wh.id = :whId"
				+ " and inv.owner.id = :ownerId"
				+ " and inv.quantInv.quant.lotSequence = :lotSeq "
				+ " and inv.bin.id = :binId "
				+ " and inv.status = :invStatus "
				+ " and inv.inboundDate = :ibdDate "
				+ " and coalesce(inv.trackSeq,'') = :asnNumber ";
		List<Inventory> invs = commonDao.findByQuery(hql, 
				new String[]{"whId", "ownerId", "lotSeq", "binId", "invStatus", "ibdDate", "asnNumber"}, 
				new Object[]{whId, ownerId, lotSeq, bins.get(0).getId(), invStatus, inboundDate, asnNumber});
		
		if( invs != null && invs.size() > 0 ){
			Map<String, Object> result = new HashMap<String, Object>();
			Inventory inv = invs.get(0);
			result.put("invId", inv.getId());
			result.put("binId", inv.getBin().getId());
			
			Sku sku = inv.getQuantInv().getQuant().getSku();
			result.put("skuId", sku.getId());
			result.put("skuCode", sku.getCode());
			result.put("skuName", sku.getName());
			
			PackageDetail csPack = sku.getProperties().getPackageInfo().getP2000();
			PackageDetail blPack = sku.getProperties().getPackageInfo().getP2100();
			PackageDetail psPack = sku.getProperties().getPackageInfo().getP1000();
			
			result.put("csIn", csPack.getCoefficient() == null ? 0 : csPack.getCoefficient().intValue());
			result.put("blIn", blPack.getCoefficient() == null ? 0 : blPack.getCoefficient().intValue());
			result.put("expDate", inv.getQuantInv().getQuant().getDispLot());
			result.put("qtyInfo", QuantityUtil.getQtyInfo(inv.getBaseQty(), csPack, blPack, psPack) + "(" + inv.getBaseQty().intValue() + psPack.getName() +  ")");
			result.put("csUnit", csPack.getName() == null ? "" : csPack.getName());
			result.put("blUnit", blPack.getName() == null ? "" : blPack.getName());
			result.put("psUnit", psPack.getName() == null ? "" : psPack.getName());
			result.put("invStatus", LocaleUtil.getText(EnuInvStatus.class.getSimpleName() + "." + inv.getStatus()));
			
			return result;
		}
		
		return null;
	}

	@Override
	public Map<String, Object> getInvInfoListByBinCode(Long whId, Long ownerId, String binCode) {
		String hql0 = " from Bin bin where bin.wh.id = :whId and bin.binCode = :binCode ";
		List<Bin> bins = commonDao.findByQuery(hql0, 
				new String[]{"whId", "binCode"}, new Object[]{whId, binCode});
		
		if( bins == null || bins.size() == 0 ){
			throw new BusinessException(binCode + "　該当する棚が見つかりません。");
		}
		
		// 只有收货区，存活区，不良品区的
		if( !bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R1000) &&
			!bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R3000)  &&
			!bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R4040) ){
			throw new BusinessException(bins.get(0).getStorageType().getName() + "　この保管ゾーンの在庫は移動不可。");
		}
		
		String hql = " from Inventory inv where inv.wh.id = :whId"
				+ " and inv.owner.id = :ownerId"
				+ " and inv.bin.id = :binId order by inv.quantInv.quant.lotSequence";
		List<Inventory> invs = commonDao.findByQuery(hql, 
				new String[]{"whId", "ownerId", "binId"}, 
				new Object[]{whId, ownerId, bins.get(0).getId()});
		
		if( invs != null && invs.size() > 0 ){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("binId", bins.get(0).getId());
			
			List<String> invInfo = new ArrayList<String>();
			for( Inventory inv : invs ){
				StringBuffer invStr = new StringBuffer();
				Sku sku = inv.getQuantInv().getQuant().getSku();
				invStr.append(sku.getCode() + "/");
				invStr.append(sku.getName());
				invInfo.add(invStr.toString());
				
				invStr = new StringBuffer();
				String qtyInfo = QuantityUtil.getQtyInfo(inv.getBaseQty(), 
						sku.getProperties().getPackageInfo().getP2000(), 
						sku.getProperties().getPackageInfo().getP2100(), 
						sku.getProperties().getPackageInfo().getP1000());
				invStr.append(qtyInfo + "/");
				invStr.append("EXP:" + inv.getQuantInv().getQuant().getDispLot() );
				invInfo.add(invStr.toString());
			}
			
			result.put("invInfo", invInfo);
			return result;
		}
		
		return null;
	}

	@Override
	public void executeInvMove(Long whId, Long userId, Long invId, String descBinCode, Double moveQty) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		WarehouseHolder.setWarehouse(wh);
		
		User user = commonDao.load(User.class, userId);
		UserHolder.setUser(user);
		
		String hql0 = " from Bin bin where bin.wh.id = :whId and bin.binCode = :binCode ";
		List<Bin> bins = commonDao.findByQuery(hql0, new String[]{"whId", "binCode"}, new Object[]{whId, descBinCode});
		
		if( bins == null || bins.size() == 0 ){
			throw new BusinessException(descBinCode + "　該当する棚が見つかりません。");
		}
		
		// 只有收货区，存活区，不良品区的
		if( !bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R1000) &&
			!bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R3000)  &&
			!bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R4040) ){
			throw new BusinessException(bins.get(0).getStorageType().getName() + "　この保管ゾーンの在庫は移動不可。");
		}
		
		invManager.invMove(invId, bins.get(0).getId(), moveQty, commonManager.getLaborIdFirst(userId), null);
	}

	@Override
	public void executeBinMove(Long whId, Long userId, Long ownerId, Long binId, String descBinCode) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		WarehouseHolder.setWarehouse(wh);
		
		User user = commonDao.load(User.class, userId);
		UserHolder.setUser(user);
		
		String hql0 = " from Bin bin where bin.wh.id = :whId and bin.binCode = :binCode ";
		List<Bin> bins = commonDao.findByQuery(hql0, new String[]{"whId", "binCode"}, new Object[]{whId, descBinCode});
		
		if( bins == null || bins.size() == 0 ){
			throw new BusinessException(descBinCode + "　該当する棚が見つかりません。");
		}
		
		// 只有收货区，存活区，不良品区的
		if( !bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R1000) &&
			!bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R3000)  &&
			!bins.get(0).getStorageType().getRole().equals(EnuStoreRole.R4040) ){
			throw new BusinessException(bins.get(0).getStorageType().getName() + "　この保管ゾーンの在庫は移動不可。");
		}
		
		String hql = "from Inventory inv where inv.wh.id = :whId"
				+ " and inv.owner.id = :ownerId"
				+ " and inv.bin.id = :binId order by inv.quantInv.quant.lotSequence";
		List<Inventory> invs = commonDao.findByQuery(hql, 
				new String[]{"whId", "ownerId", "binId"}, 
				new Object[]{whId, ownerId, binId});
		
		Long laborId = commonManager.getLaborIdFirst(userId);
		if( invs != null && invs.size() > 0 ){
			for( Inventory inv : invs ){
				invManager.invMove(inv.getId(), bins.get(0).getId(), inv.getBaseQty(), laborId, null);
			}
		}
	}
}
