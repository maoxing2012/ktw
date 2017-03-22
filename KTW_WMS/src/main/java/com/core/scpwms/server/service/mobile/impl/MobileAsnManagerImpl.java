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
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuAsnDetailStatus;
import com.core.scpwms.server.enumerate.EnuAsnStatus;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inbound.Asn;
import com.core.scpwms.server.model.inbound.AsnDetail;
import com.core.scpwms.server.model.inbound.InboundHistory;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.task.WarehouseTaskProcess;
import com.core.scpwms.server.model.temp.AsnMultiProcess;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.inbound.AsnManager;
import com.core.scpwms.server.service.mobile.MobileAsnManager;
import com.core.scpwms.server.service.mobile.MobileCommonManager;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.LocaleUtil;
import com.core.scpwms.server.util.QuantityUtil;
import com.core.scpwms.server.util.StringUtil4Jp;

public class MobileAsnManagerImpl extends DefaultBaseManager implements MobileAsnManager {
	private MobileCommonManager commonManager;
	private AsnManager asnManager;
	
	public AsnManager getAsnManager() {
		return this.asnManager;
	}

	public void setAsnManager(AsnManager asnManager) {
		this.asnManager = asnManager;
	}
	
	public MobileCommonManager getCommonManager() {
		return this.commonManager;
	}

	public void setCommonManager(MobileCommonManager commonManager) {
		this.commonManager = commonManager;
	}

	@Override
	public Map<String, Object> getAsn(Long whId, Long ownerId, String asnNumber) {
		String hql = " select asn.id from Asn asn where asn.wh.id = :whId and asn.owner.id = :ownerId and asn.asnNumber = :asnNumber and asn.status > 0 ";
		List<Long> asnIds = commonDao.findByQuery(hql, new String[]{"whId", "ownerId", "asnNumber"}, new Object[]{whId, ownerId, asnNumber});
		
		if( asnIds == null || asnIds.size() == 0 )
			return null;
		
		Asn asn = commonDao.load(Asn.class, asnIds.get(0));
		
		if( EnuAsnStatus.STATUS100.equals(asn.getStatus()) ){
			throw new BusinessException("まだ新規状態ですが、事務所に入荷受付を行ってください。");
		}
		
		if( asn.getStatus().longValue() >= EnuAsnStatus.STATUS400 || asn.getUnExecuteQty() <= 0 ){
			throw new BusinessException("すでに入荷完了になりました。");
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("asnId", asn.getId());
		result.put("planQty", new Double(asn.getPlanQty()).intValue());
		result.put("exeQty", new Double(asn.getExecuteQty()).intValue());
		
		return result;
	}

	@Override
	public Map<String, Object> getAsnDetail(Long asnId, Long skuId) {
		String hql = "select detail.id from AsnDetail detail where 1=1 "
				+ " and detail.asn.id = :asnId "
				+ " and detail.sku.id = :skuId"
				+ " and detail.planQty > detail.executeQty"
				+ " and detail.status <= 300" 
				+ " and detail.status >= 200" 
				+ " order by detail.expDate";
		
		List<Long> asnDetailIds = commonDao.findByQuery(hql, new String[]{"asnId", "skuId"}, new Object[]{asnId, skuId});
		
		if( asnDetailIds == null || asnDetailIds.size() == 0 )
			return null;
		
		Asn asn = commonDao.load(Asn.class, asnId);
		Sku sku = commonDao.load(Sku.class, skuId);
		for( Long asnDetailId : asnDetailIds ){
			AsnDetail asnDetail = commonDao.load(AsnDetail.class, asnDetailId);
			if( asnDetail.getStatus().longValue() < EnuAsnDetailStatus.STATUS400 && asnDetail.getUnexecuteQty() > 0 ){
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("asnDetailId", asnDetail.getId());
				result.put("skuCode", sku.getCode());
				result.put("skuName", sku.getName());
				result.put("csIn", sku.getProperties().getPackageInfo().getP2000().getCoefficient() == null ? 
						0 : sku.getProperties().getPackageInfo().getP2000().getCoefficient().intValue());
				result.put("blIn", sku.getProperties().getPackageInfo().getP2100().getCoefficient() == null ? 
						0 : sku.getProperties().getPackageInfo().getP2100().getCoefficient().intValue());
				result.put("csUnit", sku.getProperties().getPackageInfo().getP2000().getName());
				result.put("blUnit", sku.getProperties().getPackageInfo().getP2100().getName());
				result.put("psUnit", sku.getProperties().getPackageInfo().getP1000().getName());
				result.put("psUnit", sku.getProperties().getPackageInfo().getP1000().getName());
				result.put("expDate", asnDetail.getExpDate() == null ?
						"" : DateUtil.getStringDate(asnDetail.getExpDate(), DateUtil.PURE_DATE_FORMAT));
				
				//　按商品合计预定总数和已收货总数
				Object[] planExeQty = getSkuPlanExeQty(asnId, skuId);
				result.put("skuPlanQty", ((Double)planExeQty[0]).intValue());
				result.put("skuExeQty", ((Double)planExeQty[1]).intValue());
				
				// 初次收货，如果不是初次收货返回库存的最大保质期
				String stockExpDate = isFirstInbound(asn.getWh().getId(), asn.getOwner().getId(), skuId);
				result.put("isFirst", stockExpDate == null ? Boolean.TRUE : Boolean.FALSE);
				result.put("stockExpDate", stockExpDate == null ? "" : stockExpDate);
				return result;
			}
		}
		
		throw new BusinessException(sku.getName() + ",この商品すでに入荷完了になりました。");
	}

	@Override
	public Map<String, Object> executeReceive(Long whId, Long userId, Long asnDetailId, String expDate, Double qty) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		WarehouseHolder.setWarehouse(wh);
		User user = commonDao.load(User.class, userId);
		UserHolder.setUser(user);
		
		double toReceiveQty = qty.doubleValue();
		AsnDetail asnDetail = commonDao.load(AsnDetail.class, asnDetailId);
		InboundHistory ibdHistory = null;
		
		//------------------------------1
		// 找同商品，同批次的行，
		String hql = "select detail.id from AsnDetail detail where 1=1 "
				+ " and detail.asn.id = :asnId "
				+ " and detail.sku.id = :skuId"
				+ " and detail.planQty > detail.executeQty"
				+ " and detail.status <= 300" 
				+ " and detail.status >= 200" 
				+ " and detail.expDate = :expDate";
		List<Long> asnDetailIds1 = commonDao.findByQuery(hql, new String[]{"asnId", "skuId", "expDate"},
				new Object[]{asnDetail.getAsn().getId(), asnDetail.getSku().getId(), DateUtil.getDate(expDate, DateUtil.PURE_DATE_FORMAT)} );
		if( asnDetailIds1 != null && asnDetailIds1.size() > 0 ){
			for( Long asnDetailId1 : asnDetailIds1 ){
				if( toReceiveQty <= 0D )
					break;
				double canReceiveQty = asnManager.canReceiveQty(asnDetailId1);
				if( canReceiveQty <= 0D )
					continue;
				
				double thisReceive = toReceiveQty;
				if( toReceiveQty > canReceiveQty ){
					thisReceive = canReceiveQty;
				}
				
				ibdHistory = asnManager.receive(asnDetailId1, 
						DateUtil.getDate(expDate, DateUtil.PURE_DATE_FORMAT), 
						null, thisReceive, commonManager.getLaborIdFirst(userId));
				
				toReceiveQty = toReceiveQty - thisReceive;
			}
		}
		
		// ------------------------2
		// 找同商品没有保质期限制的行，
		if( toReceiveQty > 0 ){
			String hql1 = "select detail.id from AsnDetail detail where 1=1 "
					+ " and detail.asn.id = :asnId "
					+ " and detail.sku.id = :skuId"
					+ " and detail.planQty > detail.executeQty"
					+ " and detail.status <= 300" 
					+ " and detail.status >= 200" 
					+ " and detail.expDate is null";
			List<Long> asnDetailIds2 = commonDao.findByQuery(hql1, new String[]{"asnId", "skuId"},
					new Object[]{asnDetail.getAsn().getId(), asnDetail.getSku().getId()} );
			if( asnDetailIds2 != null && asnDetailIds2.size() > 0 ){
				for( Long asnDetailId2 : asnDetailIds2 ){
					if( toReceiveQty <= 0D )
						break;
					double canReceiveQty = asnManager.canReceiveQty(asnDetailId2);
					if( canReceiveQty <= 0D )
						continue;
					
					double thisReceive = toReceiveQty;
					if( toReceiveQty > canReceiveQty ){
						thisReceive = canReceiveQty;
					}
					
					ibdHistory = asnManager.receive(asnDetailId2, 
							DateUtil.getDate(expDate, DateUtil.PURE_DATE_FORMAT), 
							null, thisReceive, commonManager.getLaborIdFirst(userId));
					
					toReceiveQty = toReceiveQty - thisReceive;
				}
			}
		}
		
		// ------------------------3
		// 再找同商品，
		if( toReceiveQty > 0 ){
			String hql2 = "select detail.id from AsnDetail detail where 1=1 "
					+ " and detail.asn.id = :asnId "
					+ " and detail.sku.id = :skuId"
					+ " and detail.planQty > detail.executeQty"
					+ " and detail.status <= 300" 
					+ " and detail.status >= 200"
					+ " order by detail.lineNo, detail.subLineNo";
			List<Long> asnDetailIds3 = commonDao.findByQuery(hql2, 
					new String[]{"asnId", "skuId"}, new Object[]{asnDetail.getAsn().getId(), asnDetail.getSku().getId()} );
			if( asnDetailIds3 != null && asnDetailIds3.size() > 0 ){
				for( Long asnDetailId3 : asnDetailIds3 ){
					if( toReceiveQty <= 0D )
						break;
					double canReceiveQty = asnManager.canReceiveQty(asnDetailId3);
					if( canReceiveQty <= 0D )
						continue;
					
					double thisReceive = toReceiveQty;
					if( toReceiveQty > canReceiveQty ){
						thisReceive = canReceiveQty;
					}
					
					ibdHistory = asnManager.receive(asnDetailId3, 
							DateUtil.getDate(expDate, DateUtil.PURE_DATE_FORMAT), 
							null, thisReceive, commonManager.getLaborIdFirst(userId));
					
					toReceiveQty = toReceiveQty - thisReceive;
				}
			}
		}
		
		if( toReceiveQty > 0 ){
			throw new BusinessException(ExceptionConstant.OVER_PLANQTY);
		}
		
		// ----------------回传结果
		Map<String, Object> result = new HashMap<String, Object>();
		// 按订单合计预定总数和已收货总数
		result.put("planQty", new Double(asnDetail.getAsn().getPlanQty()).intValue());
		result.put("exeQty", new Double(asnDetail.getAsn().getExecuteQty()).intValue());
		
		//　按商品合计预定总数和已收货总数
		Object[] planExeQty = getSkuPlanExeQty(asnDetail.getAsn().getId(), asnDetail.getSku().getId());
		result.put("skuPlanQty", ((Double)planExeQty[0]).intValue());
		result.put("skuExeQty",  ((Double)planExeQty[1]).intValue());
		// 标签信息
		if( ibdHistory != null ){
			result.put("label", getLabelInfo(ibdHistory));
		}
		
		return result;
	}
	
	private String getLabelTemp( Long tempDiv ){
		if( EnuTemperatureDiv.CW.equals(tempDiv) ){
			return "常";
		}
		if( EnuTemperatureDiv.CHILLED.equals(tempDiv) ){
			return "チ";
		}
		if( EnuTemperatureDiv.DRY.equals(tempDiv) ){
			return "Ｄ";
		}
		if( EnuTemperatureDiv.DW.equals(tempDiv) ){
			return "低";
		}
		if( EnuTemperatureDiv.LC.equals(tempDiv) ){
			return "Ｃ";
		}
		if( EnuTemperatureDiv.LD.equals(tempDiv) ){
			return "Ｆ";
		}
		if( EnuTemperatureDiv.UNDEF.equals(tempDiv) ){
			return "未";
		}
		return tempDiv.toString();
	}
	
	private Map<String, Object> getLabelInfo( InboundHistory ibdHistory ){
		Map<String, Object> label = new HashMap<String, Object>();
		label.put("ownerCode", ibdHistory.getAsn().getOwner().getCode());
		label.put("ownerName", ibdHistory.getAsn().getOwner().getName());
		String invStatus = "";
		if( !EnuInvStatus.AVAILABLE.equals(ibdHistory.getInvInfo().getInvStatus()) ){
			invStatus = LocaleUtil.getText(EnuInvStatus.class.getSimpleName() + "." + ibdHistory.getInvInfo().getInvStatus());
		}
		label.put("invStatus", invStatus);
		label.put("csIn", ibdHistory.getDetail().getSku().getProperties().getPackageInfo().getP2000().getCoefficient() == null ? 
				0 : ibdHistory.getDetail().getSku().getProperties().getPackageInfo().getP2000().getCoefficient().intValue());
		label.put("blIn", ibdHistory.getDetail().getSku().getProperties().getPackageInfo().getP2100().getCoefficient() == null ? 
				0 : ibdHistory.getDetail().getSku().getProperties().getPackageInfo().getP2100().getCoefficient().intValue());
		label.put("skuCode", ibdHistory.getDetail().getSku().getCode());
		
		String skuName = ibdHistory.getDetail().getSku().getName();
		String skuName1 = StringUtil4Jp.getString(skuName, 18);
		skuName = skuName.length() > skuName1.length() ? skuName.substring(skuName1.length(), skuName.length()) : "";
		String skuName2 = StringUtil4Jp.getString(skuName, 18);
		skuName = skuName.length() > skuName2.length() ? skuName.substring(skuName2.length(), skuName.length()) : "";
		String skuName3 = StringUtil4Jp.getString(skuName, 24);
		
		label.put("skuName1", skuName1);
		label.put("skuName2", skuName2);
		label.put("skuName3", skuName3);
		
		label.put("janCode", ibdHistory.getDetail().getSku().getCode1());
		label.put("specs", ibdHistory.getDetail().getSku().getSpecs());
		Date expDateD = DateUtil.getDate(ibdHistory.getInvInfo().getQuant().getDispLot(), DateUtil.PURE_DATE_FORMAT);
		Integer shipLimitDays = ibdHistory.getDetail().getSku().getProperties().getAlertLeadingDays();
		Date shipExpDate = DateUtil.nextNDay(expDateD, shipLimitDays == null ? 0 : -shipLimitDays);
		label.put("expDate", DateUtil.getStringDate(expDateD, DateUtil.SLASH_FORMAT));
		label.put("shipExpDate", DateUtil.getStringDate(shipExpDate, DateUtil.SLASH_FORMAT));
		label.put("ibdDate", DateUtil.getStringDate(ibdHistory.getInvInfo().getInboundDate(), DateUtil.SLASH_FORMAT));
		label.put("lotSeq", ibdHistory.getInvInfo().getQuant().getLotSequence());
		label.put("tempDiv", getLabelTemp(ibdHistory.getDetail().getSku().getTempDiv()));
		
		//ロット番号,货主CD，商品CD，賞味期限（YYYYMMDD），在庫ステータスCD，入库日期（YYYYMMDD），入库单号
		StringBuffer qrCode = new StringBuffer();
		qrCode.append(label.get("lotSeq"));
		qrCode.append("$");
		qrCode.append(label.get("ownerCode"));
		qrCode.append("$");
		qrCode.append(label.get("skuCode"));
		qrCode.append("$");
		qrCode.append(ibdHistory.getInvInfo().getQuant().getDispLot());
		qrCode.append("$");
		qrCode.append(ibdHistory.getInvInfo().getInvStatus());
		qrCode.append("$");
		qrCode.append(DateUtil.getStringDate(ibdHistory.getInvInfo().getInboundDate(), DateUtil.PURE_DATE_FORMAT));
		qrCode.append("$");
		qrCode.append(ibdHistory.getInvInfo().getTrackSeq());
		label.put("qrCode", qrCode.toString());
		
		label.put("dateTime", DateUtil.getStringDate(new Date(), DateUtil.LONG_DATE_SLASH_FORMAT));
		
		return label;
	}
	
	private Map<String, Object> getLabelInfo( Inventory inv){
		Sku sku = inv.getQuantInv().getQuant().getSku();
		
		Map<String, Object> label = new HashMap<String, Object>();
		label.put("ownerCode", inv.getOwner().getCode());
		label.put("ownerName", inv.getOwner().getName());
		String invStatus = "";
		if( !EnuInvStatus.AVAILABLE.equals(inv.getStatus()) ){
			invStatus = LocaleUtil.getText(EnuInvStatus.class.getSimpleName() + "." + inv.getStatus());
		}
		label.put("invStatus", invStatus);
		label.put("csIn", sku.getProperties().getPackageInfo().getP2000().getCoefficient() == null ? 
				0 : sku.getProperties().getPackageInfo().getP2000().getCoefficient().intValue());
		label.put("blIn", sku.getProperties().getPackageInfo().getP2100().getCoefficient() == null ? 
				0 : sku.getProperties().getPackageInfo().getP2100().getCoefficient().intValue());
		label.put("skuCode", sku.getCode());
		
		String skuName = sku.getName();
		String skuName1 = StringUtil4Jp.getString(skuName, 18);
		skuName = skuName.length() > skuName1.length() ? skuName.substring(skuName1.length(), skuName.length()) : "";
		String skuName2 = StringUtil4Jp.getString(skuName, 18);
		skuName = skuName.length() > skuName2.length() ? skuName.substring(skuName2.length(), skuName.length()) : "";
		String skuName3 = StringUtil4Jp.getString(skuName, 24);
		
		label.put("skuName1", skuName1);
		label.put("skuName2", skuName2);
		label.put("skuName3", skuName3);
		
		label.put("janCode", sku.getCode1());
		label.put("specs", sku.getSpecs());
		Date expDateD = DateUtil.getDate(inv.getQuantInv().getQuant().getDispLot(), DateUtil.PURE_DATE_FORMAT);
		Integer shipLimitDays = sku.getProperties().getAlertLeadingDays();
		Date shipExpDate = DateUtil.nextNDay(expDateD, shipLimitDays == null ? 0 : -shipLimitDays);
		label.put("expDate", DateUtil.getStringDate(expDateD, DateUtil.SLASH_FORMAT));
		label.put("shipExpDate", DateUtil.getStringDate(shipExpDate, DateUtil.SLASH_FORMAT));
		label.put("ibdDate", DateUtil.getStringDate(inv.getInboundDate(), DateUtil.SLASH_FORMAT));
		label.put("lotSeq", inv.getQuantInv().getQuant().getLotSequence());
		label.put("tempDiv", getLabelTemp(sku.getTempDiv()));
		
		//ロット番号,货主CD，商品CD，賞味期限（YYYYMMDD），在庫ステータスCD，入库日期（YYYYMMDD），入库单号
		StringBuffer qrCode = new StringBuffer();
		qrCode.append(label.get("lotSeq"));
		qrCode.append("$");
		qrCode.append(label.get("ownerCode"));
		qrCode.append("$");
		qrCode.append(label.get("skuCode"));
		qrCode.append("$");
		qrCode.append(inv.getQuantInv().getQuant().getDispLot());
		qrCode.append("$");
		qrCode.append(inv.getStatus());
		qrCode.append("$");
		qrCode.append(DateUtil.getStringDate(inv.getInboundDate(), DateUtil.PURE_DATE_FORMAT));
		qrCode.append("$");
		qrCode.append(inv.getTrackSeq());
		label.put("qrCode", qrCode.toString());
		
		label.put("dateTime", DateUtil.getStringDate(new Date(), DateUtil.LONG_DATE_SLASH_FORMAT));
		
		return label;
	}
	
	// 是否是第一次收货
	private String isFirstInbound( Long whId, Long ownerId, Long skuId ){
		String hql = " select max(inv.quantInv.quant.dispLot) from Inventory inv where 1=1 "
				+ " and inv.wh.id = :whId "
				+ " and inv.owner.id = :ownerId "
				+ " and inv.quantInv.quant.sku.id = :skuId ";
		
		String stockExpDate = (String)commonDao.findByQueryUniqueResult(hql, 
				new String[]{"whId", "ownerId", "skuId"}, new Object[]{whId, ownerId, skuId});
		
		return stockExpDate;
	}
	
	private Object[] getSkuPlanExeQty( Long asnId, Long skuId ){
		String hql = " select coalesce(sum(asnDetail.planQty),0), coalesce(sum(asnDetail.executeQty),0) "
				+ " from AsnDetail asnDetail "
				+ " where asnDetail.asn.id = :asnId "
				+ " and asnDetail.sku.id = :skuId ";
		
		Object[] result = (Object[])commonDao.findByQueryUniqueResult(hql, new String[]{"asnId", "skuId"}, new Object[]{asnId, skuId});
		if( result == null || result.length < 2)
			return new Double[]{0D, 0D};
		
		return result;
	}

	@Override
	public Map<String, Object> getLabelInfo(Long whId, Long ownerId, Long skuId, String expDate, String asnNumber) {
		String hql = "select ih.id from InboundHistory ih where 1=1 "
				+ " and ih.wh.id = :whId "
				+ " and ih.asn.owner.id = :ownerId "
				+ " and ih.asn.asnNumber = :asnNumber "
				+ " and ih.detail.sku.id = :skuId"
				+ " and ih.invInfo.quant.dispLot = :expDate "
				+ " order by ih.id desc";
		List<Long> ihIds = commonDao.findByQuery(hql, 
				new String[]{"whId", "ownerId", "asnNumber", "skuId", "expDate"}, 
				new Object[]{ whId, ownerId, asnNumber, skuId, expDate });
		
		if( ihIds == null || ihIds.size() == 0 )
			return null;
		
		Map<String, Object> result = new HashMap<String, Object>();
		// 标签信息
		result.put("label", getLabelInfo(commonDao.load(InboundHistory.class, ihIds.get(0))));
		
		return result;
	}

	@Override
	public Map<String, Object> getInvInfo(Long invId) {
		Inventory inv = commonDao.load(Inventory.class, invId);
		if( inv == null )
			return null;
		
		Sku sku = inv.getQuantInv().getQuant().getSku();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("skuId", sku.getId());
		result.put("skuCode", sku.getCode());
		result.put("skuName", sku.getName());
		result.put("invStatus", LocaleUtil.getText(EnuInvStatus.class.getSimpleName() + "." + inv.getStatus()));
		result.put("tempDiv",  LocaleUtil.getText(EnuTemperatureDiv.class.getSimpleName() + "." + sku.getTempDiv()));
		result.put("specs", sku.getSpecs());
		result.put("expDate", inv.getQuantInv().getQuant().getDispLot());
		result.put("csIn", sku.getProperties().getPackageInfo().getP2000().getCoefficient() == null ? 
				0 : sku.getProperties().getPackageInfo().getP2000().getCoefficient().intValue());
		result.put("blIn", sku.getProperties().getPackageInfo().getP2100().getCoefficient() == null ? 
				0 : sku.getProperties().getPackageInfo().getP2100().getCoefficient().intValue());
		
		result.put("csUnit", sku.getProperties().getPackageInfo().getP2000().getName() == null ? 
				"" : sku.getProperties().getPackageInfo().getP2000().getName());
		result.put("blUnit", sku.getProperties().getPackageInfo().getP2100().getName() == null ? 
				"" : sku.getProperties().getPackageInfo().getP2100().getName());
		result.put("psUnit", sku.getProperties().getPackageInfo().getP1000().getName() == null ? 
				"" : sku.getProperties().getPackageInfo().getP1000().getName());
		
		result.put("qtyInfo", QuantityUtil.getQtyInfo(inv.getBaseQty(), 
				sku.getProperties().getPackageInfo().getP2000(), 
				sku.getProperties().getPackageInfo().getP2100(), 
				sku.getProperties().getPackageInfo().getP1000()) 
				+ "(" +  inv.getBaseQty().intValue() + sku.getProperties().getPackageInfo().getP1000().getName() +")");
		result.put("qty", inv.getBaseQty().intValue());
		
		result.put("asnNumber", inv.getTrackSeq());
		result.put("ibdDate", DateUtil.getStringDate(inv.getInboundDate(), DateUtil.PURE_DATE_FORMAT) );
		
		result.put("binId", inv.getBin().getId());
		result.put("binCode", inv.getBin().getBinCode());
		
		// 标签信息
		result.put("label", getLabelInfo(inv));
		return result;
	}

	@Override
	public Long startMultiAsn(Long whId, Long ownerId, Long userId, Long... asnIds) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		User user = commonDao.load(User.class, userId);
		WarehouseHolder.setWarehouse(wh);
		UserHolder.setUser(user);
		
		AsnMultiProcess amp = new AsnMultiProcess();
		amp.setWhId(whId);
		amp.setOwnerId(ownerId);
		amp.setUserId(userId);
		
		amp.setAsnId0(asnIds[0]);
		amp.setAsnId1(asnIds[1]);
		amp.setAsnId2(asnIds[2]);
		amp.setAsnId3(asnIds[3]);
		amp.setAsnId4(asnIds[4]);
		amp.setAsnId5(asnIds[5]);
		amp.setAsnId6(asnIds[6]);
		amp.setAsnId7(asnIds[7]);
		amp.setAsnId8(asnIds[8]);
		amp.setAsnId9(asnIds[9]);
		
		commonDao.store(amp);
		return amp.getId();
	}

	@Override
	public Map<String, Object> getAsnIdBySku4Multi(Long whId, Long ownerId, Long userId, Long processId, Long skuId) {
		AsnMultiProcess amp = commonDao.load(AsnMultiProcess.class, processId);
		List<Long> asnIdsList = new ArrayList<Long>();
		if( amp.getAsnId0() != null ){
			asnIdsList.add(amp.getAsnId0());
		}
		if( amp.getAsnId1() != null ){
			asnIdsList.add(amp.getAsnId1());
		}
		if( amp.getAsnId2() != null ){
			asnIdsList.add(amp.getAsnId2());
		}
		if( amp.getAsnId3() != null ){
			asnIdsList.add(amp.getAsnId3());
		}
		if( amp.getAsnId4() != null ){
			asnIdsList.add(amp.getAsnId4());
		}
		if( amp.getAsnId5() != null ){
			asnIdsList.add(amp.getAsnId5());
		}
		if( amp.getAsnId6() != null ){
			asnIdsList.add(amp.getAsnId6());
		}
		if( amp.getAsnId7() != null ){
			asnIdsList.add(amp.getAsnId7());
		}
		if( amp.getAsnId8() != null ){
			asnIdsList.add(amp.getAsnId8());
		}
		if( amp.getAsnId9() != null ){
			asnIdsList.add(amp.getAsnId9());
		}
		
		String hql = "select distinct asnDetail.asn.id from AsnDetail asnDetail where 1=1 "
				+ " and asnDetail.asn.id in (:asnIds)"
				+ " and asnDetail.sku.id = :skuId "
				+ " and asnDetail.planQty > asnDetail.executeQty"
				+ " and asnDetail.status <= 300" 
				+ " and asnDetail.status >= 200" ;
		
		List<Long> asnIdsList2 = commonDao.findByQuery(hql, new String[]{"asnIds", "skuId"}, new Object[]{asnIdsList, skuId});
		if( asnIdsList2 == null || asnIdsList2.size() == 0 ){
			return null;
		}
		else if( asnIdsList2.size() > 1 ){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			Asn asn = commonDao.load(Asn.class, asnIdsList2.get(0));
			resultMap.put("asnId", 0L);
			resultMap.put("asnNumber", "");
			resultMap.put("exeQty", 0L);
			resultMap.put("planQty", 0L);
			
			List<Map<String, Object>> asnList = new ArrayList<Map<String,Object>>();
			for( Long asnId : asnIdsList2 ){
				Asn asn4List = commonDao.load(Asn.class, asnId);
				Map<String, Object> listMap = new HashMap<String, Object>();
				listMap.put("asnId", asn4List.getId());
				listMap.put("asnNumber", asn4List.getAsnNumber());
				listMap.put("exeQty", new Double(asn4List.getExecuteQty()).longValue());
				listMap.put("planQty", new Double(asn4List.getPlanQty()).longValue());
				
				asnList.add(listMap);
			}
			resultMap.put("asnList", asnList);
			
			return resultMap;
		}
		else{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			Asn asn = commonDao.load(Asn.class, asnIdsList2.get(0));
			resultMap.put("asnId", asn.getId());
			resultMap.put("asnNumber", asn.getAsnNumber());
			resultMap.put("exeQty", new Double(asn.getExecuteQty()).longValue());
			resultMap.put("planQty", new Double(asn.getPlanQty()).longValue());
			
			List<Map<String, Object>> asnList = new ArrayList<Map<String,Object>>();
			resultMap.put("asnList", asnList);
			
			return resultMap;
		}
	}
}
