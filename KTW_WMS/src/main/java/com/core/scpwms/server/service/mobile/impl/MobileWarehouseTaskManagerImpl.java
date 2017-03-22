/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: MobilePutawayManagerImpl.java
 */

package com.core.scpwms.server.service.mobile.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.core.business.exception.BusinessException;
import com.core.business.model.security.User;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.enumerate.EnuCaseType;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.enumerate.EnuWarehouseOrderStatus;
import com.core.scpwms.server.enumerate.EnuWarehouseTaskSingleStatus;
import com.core.scpwms.server.enumerate.EnuWaveStatus;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.task.WarehouseTaskProcess;
import com.core.scpwms.server.model.task.WarehouseTaskSingle;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.mobile.MobileCommonManager;
import com.core.scpwms.server.service.mobile.MobileWarehouseTaskManager;
import com.core.scpwms.server.service.task.WarehouseTaskSingleManager;
import com.core.scpwms.server.util.LocaleUtil;
import com.core.scpwms.server.util.QuantityUtil;

/**
 * 拣货/复合
 * @author mousachi
 *
 */
public class MobileWarehouseTaskManagerImpl extends DefaultBaseManager implements MobileWarehouseTaskManager {
	private WarehouseTaskSingleManager warehouseTaskSingleManager;
	private MobileCommonManager commonManager;

	public WarehouseTaskSingleManager getWarehouseTaskSingleManager() {
		return warehouseTaskSingleManager;
	}

	public void setWarehouseTaskSingleManager(
			WarehouseTaskSingleManager warehouseTaskSingleManager) {
		this.warehouseTaskSingleManager = warehouseTaskSingleManager;
	}

	public MobileCommonManager getCommonManager() {
		return commonManager;
	}

	public void setCommonManager(MobileCommonManager commonManager) {
		this.commonManager = commonManager;
	}

	@Override
	public List<Map<String, Object>> getCaseInfo(Long whId, Long ownerId, String caseNumber) {
		String hql = "select wts.id from WarehouseTaskSingle wts where 1=1"
				+ " and wts.wt.wh.id = :whId"
				+ " and wts.wt.invInfo.owner.id = :ownerId"
				+ " and wts.relatedBill2 = :caseNumber"
				+ " and wts.status < 999"
				+ " order by wts.wt.invInfo.quant.sku.id, wts.wt.invInfo.quant.dispLot"; // 商品，保质期排序
		List<Long> ids = commonDao.findByQuery(hql, new String[]{"whId", "ownerId", "caseNumber"}, new Object[]{whId, ownerId, caseNumber});
		if( ids == null || ids.size() == 0 ){
			throw new BusinessException(caseNumber + "該当する個口情報が見つかりません。");
		}
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		
		Long quantId = 0L;
		Double qty = 0D;
		WarehouseTaskSingle lastWts = null;
		for( Long wtsId : ids ){
			WarehouseTaskSingle wts = commonDao.load(WarehouseTaskSingle.class, wtsId);
			
			if( EnuWarehouseTaskSingleStatus.STATUS000.equals(wts.getStatus()) ){
				throw new BusinessException(caseNumber + "まだピック中の状態で、出荷検品できません。");
			}
			else if( EnuWarehouseTaskSingleStatus.STATUS200.equals(wts.getStatus()) ){
				throw new BusinessException(caseNumber + "すでに検品済の状態になりました。");
			}
			
			if( !quantId.equals(wts.getWt().getInvInfo().getQuant().getId()) ){
				if( lastWts != null && qty > 0D ){
					result.add(getSkuInfo(lastWts, qty));
				}
				
				quantId = wts.getWt().getInvInfo().getQuant().getId();
				qty = wts.getQty();
				lastWts = wts;
			}
			else{
				qty = qty + wts.getQty();
			}
		}
		if( lastWts != null && qty > 0D ){
			result.add(getSkuInfo(lastWts, qty));
		}
		
		return result;
	}
	
	private Map<String, Object> getSkuInfo( WarehouseTaskSingle wts, Double qty ){
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("skuId", wts.getWt().getInvInfo().getQuant().getSku().getId());
		result.put("skuCode", wts.getWt().getInvInfo().getQuant().getSku().getCode());
		result.put("skuName", wts.getWt().getInvInfo().getQuant().getSku().getName());
		result.put("specs", wts.getWt().getInvInfo().getQuant().getSku().getSpecs());
		
		PackageDetail psPack = wts.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP1000();
		PackageDetail blPack = wts.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP2100();
		PackageDetail csPack = wts.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP2000();
		
		Double[] qtys = QuantityUtil.getQtys(qty, csPack, blPack, psPack);
		
		String barcodeList = "";
		
		// 商品编号
		String skuCode = wts.getWt().getInvInfo().getQuant().getSku().getCode();
		barcodeList = barcodeList + skuCode;
		
		// JanCode
		String janCode = wts.getWt().getInvInfo().getQuant().getSku().getCode1();
		if( janCode != null && janCode.length() > 0 ){
			barcodeList += "," + janCode;
		}
		
		String hql2 = "select distinct stc.barCode from SkuInterCode stc where stc.sku.id = :skuId";
		List<String> barcodes = commonDao.findByQuery(hql2, "skuId", wts.getWt().getInvInfo().getQuant().getSku().getId());
		StringBuilder barcodesSb = new StringBuilder();
		if( barcodes != null && barcodes.size() > 0 ){
			for( String barcode : barcodes ){
				if( barcodesSb.length() > 0 ){
					barcodesSb.append(",");
				}
				barcodesSb.append(barcode);
			}
		}
		
		if( barcodesSb != null && barcodesSb.length() > 0 ){
			barcodeList += "," + barcodesSb.toString();
		}
		
		// 单品条码
		result.put("psBarCode", "");
		// 内箱条形码
		result.put("blBarCode", "");
		// 箱条形码
		result.put("csBarCode", "");
		
		if( qtys[0] != null && qtys[0] > 0D ){
			result.put("csBarCode", barcodeList);
		}
		
		if( qtys[1] != null && qtys[1] > 0D ){
			result.put("blBarCode", barcodeList);
		}
		
		if( qtys[2] != null && qtys[2] > 0D ){
			result.put("psBarCode", barcodeList);
		}
		
		result.put("psUnit", wts.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP1000().getName());
		result.put("blUnit", wts.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP2100().getName());
		result.put("csUnit", wts.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP2000().getName());
		
		// 内箱系数
		Double blIn =  wts.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP2100().getCoefficient();
		result.put("blIn", blIn != null && blIn.longValue() > 0L ? blIn.longValue() : 0L);
		
		// 外箱系数
		Double csIn = wts.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP2000().getCoefficient();
		result.put("csIn",  csIn != null && csIn.longValue() > 0L ? csIn.longValue() : 0L);
		
		result.put("expDate", wts.getWt().getInvInfo().getQuant().getDispLot());
		result.put("qty", qty.longValue());
		
		return result;
	}

	@Override
	public void checkedCase(Long whId, Long ownerId, String caseNumber, Long userId) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		User user = commonDao.load(User.class, userId);
		WarehouseHolder.setWarehouse(wh);
		UserHolder.setUser(user);
		
		String hql = "select wts.id from WarehouseTaskSingle wts where wts.status = 100 "
				+ "and wts.wt.wh.id = :whId "
				+ "and wts.wt.invInfo.owner.id = :ownerId "
				+ "and wts.relatedBill2 = :caseNumber ";
		
		List<Long> ids = commonDao.findByQuery(hql, new String[]{"whId", "ownerId", "caseNumber"}, new Object[]{whId, ownerId, caseNumber});
		
		if( ids != null && ids.size() > 0 ){
			warehouseTaskSingleManager.checkExecute(ids, commonManager.getLaborIdFirst(userId));
		}
	}

	@Override
	public Map<String, Object> getCsPickInfo(Long whId, Long ownerId, String caseNumberFrom, String caseNumberTo) {
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("select wts.id from WarehouseTaskSingle wts where 1=1 ");
		hql.append(" and wts.wt.wh.id = :whId ");
		hql.append(" and wts.wt.invInfo.owner.id = :ownerId ");
		paramNames.add("whId");
		paramNames.add("ownerId");
		params.add(whId);
		params.add(ownerId);
		
		if( !StringUtils.isEmpty(caseNumberFrom) && !StringUtils.isEmpty(caseNumberTo) ){
			hql.append(" and wts.relatedBill2 >= :caseNumberFrom ");
			hql.append(" and wts.relatedBill2 <= :caseNumberTo ");
			paramNames.add("caseNumberFrom");
			paramNames.add("caseNumberTo");
			params.add(caseNumberFrom);
			params.add(caseNumberTo);
		}
		else if( !StringUtils.isEmpty(caseNumberFrom) && StringUtils.isEmpty(caseNumberTo) ){
			hql.append(" and wts.relatedBill2 = :caseNumberFrom ");
			paramNames.add("caseNumberFrom");
			params.add(caseNumberFrom);
		}
		else{
			throw new BusinessException("個口番号を入力してください。");
		}
		
		hql.append(" order by wts.relatedBill2, wts.wt.invInfo.quant.id ");
		
		List<Long> wtsIds = commonDao.findByQuery(hql.toString(), paramNames.toArray(new String[paramNames.size()]), params.toArray(new Object[params.size()])); 
		
		// 必须同货架同Quant同类型case
		Quant quant = null;
		Bin bin = null;
		Long caseType = -1L;// 1整箱，2单箱
		Set<String> caseNumbers = new LinkedHashSet<String>();
		Double qty = 0D;
		
		for( Long wtsId : wtsIds ){
			WarehouseTaskSingle wts = commonDao.load(WarehouseTaskSingle.class, wtsId);
			if( wts.getCaseType().longValue() != EnuCaseType.BL_CASE.longValue() 
					&& wts.getCaseType().longValue() != EnuCaseType.CS_CASE.longValue() ){
				throw new BusinessException(wts.getRelatedBill3() + "バラ個口です。バラピックンぎを利用してください。");
			}
			
			if( EnuWarehouseTaskSingleStatus.STATUS100 <= wts.getStatus() ){
				throw new BusinessException(wts.getRelatedBill3() + "すでにピッキング済の状態になりました。");
			}
			caseNumbers.add(wts.getRelatedBill2());
			if( quant == null ){
				quant = wts.getWt().getInvInfo().getQuant();
				bin = wts.getWt().getInvInfo().getBin();
				caseType = wts.getCaseType();
				qty = wts.getQty();
			}
			else {
				if( !quant.getId().equals(wts.getWt().getInvInfo().getQuant().getId()) ){
					throw new BusinessException(wts.getRelatedBill2() + "ロットが違うので、一括ピッキング不可。");
				}
				if( !bin.getId().equals(wts.getWt().getInvInfo().getBin().getId()) ){
					throw new BusinessException(wts.getRelatedBill2() + "棚番が違うので、一括ピッキング不可。");
				}
				if( !caseType.equals(wts.getCaseType()) ){
					throw new BusinessException(wts.getRelatedBill2() + "個口タイプが違うので、一括ピッキング不可。");
				}
				qty = qty + wts.getQty();
			}
		}
		
		if( quant != null && bin != null ){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("caseNumbers", toString(caseNumbers));
			result.put("caseQty", caseNumbers.size());
			result.put("binId", bin.getId());
			result.put("binCode", bin.getBinCode());
			result.put("skuId", quant.getSku().getId());
			result.put("skuCode", quant.getSku().getCode());
			result.put("skuName", quant.getSku().getName());
			result.put("barcodes", getBarcodes(quant.getSku()));
			result.put("specs", quant.getSku().getSpecs());
			
			String unit = "";
			String unitCd = "";
			Long dispQty = 0L;
			Double coefficient = 0D;
			String warningMsg = "";
			Long scanNum = 1L;
			
			// 箱单位
			if( EnuCaseType.CS_CASE.longValue() == caseType.longValue() ){
				unit = quant.getSku().getProperties().getPackageInfo().getP2000().getName();
				unitCd = quant.getSku().getProperties().getPackageInfo().getP2000().getCode();
				coefficient = quant.getSku().getProperties().getPackageInfo().getP2000().getCoefficient();
				
				if( "40".equals(unitCd) ){
					scanNum = 2L;
				}
			}
			// 内箱单位
			else {
				unit = quant.getSku().getProperties().getPackageInfo().getP2100().getName();
				unitCd = quant.getSku().getProperties().getPackageInfo().getP2100().getCode();
				coefficient = quant.getSku().getProperties().getPackageInfo().getP2100().getCoefficient();
				
				if( "20".equals(unitCd) ){
					warningMsg = "＊ボールケースです。ご注意ください。";
				}
			}
			if( coefficient == null || coefficient <= 0D ){
				coefficient = 1D;
			}
			dispQty = DoubleUtil.div(qty, coefficient).longValue();
			
			result.put("qty", dispQty);
			result.put("unit", unit);
			result.put("expDate", quant.getDispLot());
			result.put("warningMsg", warningMsg);
			result.put("scanNum", scanNum);
			return result;
		}
		
		return null;
	}
	
	private String toString( Set<String> setInfos ){
		StringBuffer result = new StringBuffer();
		
		for( String setInfo : setInfos ){
			if( result.length() > 0 ){
				result.append(",");
			}
			result.append(setInfo);
		}
		return result.toString();
	}
	
	private String getBarcodes( Sku sku ){
		StringBuffer result = new StringBuffer();
		result.append( sku.getCode() );
		
		if( !StringUtils.isEmpty(sku.getCode1()) ){
			result.append(",");
			result.append(sku.getCode1());
		}
		
		String hql = " select barCode from SkuInterCode where sku.id = :skuId ";
		List<String> barcodes = commonDao.findByQuery(hql, "skuId", sku.getId());
		if( barcodes != null && barcodes.size() > 0 ){
			for( String barcode : barcodes ){
				result.append(",");
				result.append(barcode);
			}
		}
		
		return result.toString();
	}

	@Override
	public void executeCsPick(Long whId, Long ownerId, Long userId, String caseNumbers) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		User user = commonDao.load(User.class, userId);
		WarehouseHolder.setWarehouse(wh);
		UserHolder.setUser(user);
		
		String hql = "select wts.id from WarehouseTaskSingle wts where wts.status = 0 "
				+ "and wts.wt.wh.id = :whId "
				+ "and wts.wt.invInfo.owner.id = :ownerId "
				+ "and wts.relatedBill2 in (:caseNumbers) ";
		
		List<Long> ids = commonDao.findByQuery(hql, new String[]{"whId", "ownerId", "caseNumbers"}, 
				new Object[]{whId, ownerId, Arrays.asList(caseNumbers.split(","))});
		
		if( ids != null && ids.size() > 0 ){
			warehouseTaskSingleManager.pickExecute(ids, commonManager.getLaborIdFirst(userId));
			// cs拣货，拣货后直接完成复合
			warehouseTaskSingleManager.checkExecute(ids, commonManager.getLaborIdFirst(userId));
		}
		else{
			throw new BusinessException("該当するデータが見つかりません。");
		}
	}
	
	@Override
	public Map<String, Object> executeBatchPick(Long whId, Long ownerId, Long userId, Long wvId, Long wtsId) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		User user = commonDao.load(User.class, userId);
		WarehouseHolder.setWarehouse(wh);
		UserHolder.setUser(user);
		
		WarehouseTaskSingle wtSingle = commonDao.load(WarehouseTaskSingle.class, wtsId);
		
		String hql = "select wts.id from WarehouseTaskSingle wts where wts.status = 0 "
				+ " and wts.wt.wh.id = :whId "
				+ " and wts.wt.invInfo.owner.id = :ownerId "
				+ " and wts.waveDoc.id = :waveDocId"
				+ " and wts.wt.invInfo.quant.id = :quantId"
				+ " and wts.wt.invInfo.bin.id = :binId"
				+ " and wts.wt.invInfo.invStatus = :invStatus ";
		
		List<Long> ids = commonDao.findByQuery(hql, new String[]{"whId", "ownerId", "waveDocId", "quantId", "binId", "invStatus"}, 
				new Object[]{whId, ownerId,  wtSingle.getWaveDoc().getId(), 
				wtSingle.getWt().getInvInfo().getQuant().getId(), 
				wtSingle.getWt().getInvInfo().getBin().getId(), 
				wtSingle.getWt().getInvInfo().getInvStatus()});
		
		if( ids != null && ids.size() > 0 ){
			warehouseTaskSingleManager.pickExecute(ids, commonManager.getLaborIdFirst(userId));
			// cs拣货，拣货后直接完成复合
			// warehouseTaskSingleManager.checkExecute(ids, commonManager.getLaborIdFirst(userId));
		}
		else{
			throw new BusinessException("該当するデータが見つかりません。");
		}
		
		String hql2 = " select sum(obdDetail.pickUpQty), sum(obdDetail.planQty) "
				+ " from OutboundDeliveryDetail obdDetail"
				+ " where obdDetail.obd.waveDoc.id = :waveDocId "
				+ " and obdDetail.sku.stockDiv = 1"
				+ " and obdDetail.sku.tempDiv = :tempDiv ";
		
		Object qtyObd = (Object) commonDao.findByQueryUniqueResult(hql2, 
				new String[]{"waveDocId","tempDiv"}, 
				new Object[]{wvId, wtSingle.getWt().getObdDetail().getSku().getTempDiv()});
		
		Object[] qtys = (Object[])qtyObd;
		if( qtys == null || qtys[1] == null || (Double)qtys[1] == 0D ){
			return null;
		}
		else if( (Double)qtys[0] >= (Double)qtys[1] ){
			return null;
		}
		else{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("executeQty", qtys[0] == null ? 0L : ((Double)qtys[0]).longValue());
			return resultMap;
		}
	}

	@Override
	public Map<String, Object> getPsPickInfo(Long whId, Long ownerId, String caseNumber) {
		String hql = " select coalesce(sum(wts.pickQty),0), sum(wts.qty), min(wts.caseType) from WarehouseTaskSingle wts where 1=1 "
				+ " and wts.wt.wh.id = :whId "
				+ " and wts.wt.invInfo.owner.id = :ownerId "
				+ " and wts.relatedBill2 = :caseNumber ";
		Object[] qtyInfos = (Object[])commonDao.findByQueryUniqueResult(hql, 
				new String[]{"whId", "ownerId", "caseNumber"}, new Object[]{whId, ownerId, caseNumber});
		
		if( qtyInfos != null && qtyInfos.length == 3 && qtyInfos[2] != null ){
			Long caseType = (Long)qtyInfos[2];
			
			if( EnuCaseType.PS_CASE.longValue() != caseType.longValue() ){
				throw new BusinessException(caseNumber + "非バラ個口。ケースピッキングを利用してください。");
			}
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("exeQty", ((Double)qtyInfos[0]).longValue());
			result.put("planQty", ((Double)qtyInfos[1]).longValue());
			return result;
		}
		return null;
	}

	@Override
	public Long startPsPick(Long whId, Long ownerId, Long userId, String... caseNumbers) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		User user = commonDao.load(User.class, userId);
		WarehouseHolder.setWarehouse(wh);
		UserHolder.setUser(user);
		
		WarehouseTaskProcess wtp = new WarehouseTaskProcess();
		wtp.setWhId(whId);
		wtp.setOwnerId(ownerId);
		wtp.setUserId(userId);
		if( !StringUtils.isEmpty(caseNumbers[0]) ){
			wtp.setCaseNumberA(caseNumbers[0].substring(2, caseNumbers[0].length()));
		}
		if( !StringUtils.isEmpty(caseNumbers[1]) ){
			wtp.setCaseNumberB(caseNumbers[1].substring(2, caseNumbers[1].length()));
		}
		if( !StringUtils.isEmpty(caseNumbers[2]) ){
			wtp.setCaseNumberC(caseNumbers[2].substring(2, caseNumbers[2].length()));
		}
		if( !StringUtils.isEmpty(caseNumbers[3]) ){
			wtp.setCaseNumberD(caseNumbers[3].substring(2, caseNumbers[3].length()));
		}
		if( !StringUtils.isEmpty(caseNumbers[4]) ){
			wtp.setCaseNumberE(caseNumbers[4].substring(2, caseNumbers[4].length()));
		}
		if( !StringUtils.isEmpty(caseNumbers[5]) ){
			wtp.setCaseNumberF(caseNumbers[5].substring(2, caseNumbers[5].length()));
		}
		if( !StringUtils.isEmpty(caseNumbers[6]) ){
			wtp.setCaseNumberG(caseNumbers[6].substring(2, caseNumbers[6].length()));
		}
		if( !StringUtils.isEmpty(caseNumbers[7]) ){
			wtp.setCaseNumberH(caseNumbers[7].substring(2, caseNumbers[7].length()));
		}
		if( !StringUtils.isEmpty(caseNumbers[8]) ){
			wtp.setCaseNumberI(caseNumbers[8].substring(2, caseNumbers[8].length()));
		}
		commonDao.store(wtp);
		return wtp.getId();
	}
	
	private Map<String, Character> getCaseNumberMap(Long processId){
		Map<String, Character> caseNumbers = new HashMap<String, Character>();
		WarehouseTaskProcess process = commonDao.load(WarehouseTaskProcess.class, processId);
		if( !StringUtils.isEmpty(process.getCaseNumberA()) ){
			caseNumbers.put(process.getCaseNumberA(),'A');
		}
		if( !StringUtils.isEmpty(process.getCaseNumberB()) ){
			caseNumbers.put(process.getCaseNumberB(),'B');
		}
		if( !StringUtils.isEmpty(process.getCaseNumberC()) ){
			caseNumbers.put(process.getCaseNumberC(),'C');
		}	
		if( !StringUtils.isEmpty(process.getCaseNumberD()) ){
			caseNumbers.put(process.getCaseNumberD(),'D');
		}
		if( !StringUtils.isEmpty(process.getCaseNumberE()) ){
			caseNumbers.put(process.getCaseNumberE(),'E');
		}
		if( !StringUtils.isEmpty(process.getCaseNumberF()) ){
			caseNumbers.put(process.getCaseNumberF(),'F');
		}
		if( !StringUtils.isEmpty(process.getCaseNumberG()) ){
			caseNumbers.put(process.getCaseNumberG(),'G');
		}
		if( !StringUtils.isEmpty(process.getCaseNumberH()) ){
			caseNumbers.put(process.getCaseNumberH(),'H');
		}
		if( !StringUtils.isEmpty(process.getCaseNumberI()) ){
			caseNumbers.put(process.getCaseNumberI(),'I');
		}
		
		return caseNumbers;
	}

	@Override
	public Map<String, Object> getNextPickInfo(Long whId, Long ownerId, Long processId) {
		Map<String, Character> caseNumberMap = getCaseNumberMap(processId);
		
		// 总已拣货数/总数
		String hql = " select coalesce(sum(wts.pickQty),0), sum(wts.qty) from WarehouseTaskSingle wts"
				+ " where 1=1 "
				+ " and wts.wt.wh.id = :whId "
				+ " and wts.wt.invInfo.owner.id = :ownerId "
				+ " and wts.relatedBill2 in (:caseNumbers)"
				+ " and wts.status <= 100 ";
		
		Object[] qtyInfos = (Object[])commonDao.findByQueryUniqueResult(hql, 
				new String[]{"whId", "ownerId", "caseNumbers"}, new Object[]{whId, ownerId, caseNumberMap.keySet()});
		
		Map<String, Object> result = new HashMap<String, Object>();
		if( qtyInfos != null && qtyInfos.length == 2 && qtyInfos[1] != null){
			// 已经全部拣货完成
			if( ((Double)qtyInfos[0]).longValue() >= ((Double)qtyInfos[1]).longValue() ){
				return null;
			}
			
			result.put("exeQty", ((Double)qtyInfos[0]).longValue());
			result.put("planQty", ((Double)qtyInfos[1]).longValue());
		}
		else{
			return null;
		}
		
		// 按照库位编号倒序排序
		String hql1 = " select wts.id from WarehouseTaskSingle wts"
				+ " where 1=1 "
				+ " and wts.wt.wh.id = :whId "
				+ " and wts.wt.invInfo.owner.id = :ownerId "
				+ " and wts.relatedBill2 in (:caseNumbers) "
				+ " and wts.status = 0"//未拣货
				+ " order by wts.wt.invInfo.bin.binCode, "
				+ " wts.wt.invInfo.quant.sku.code,"
				+ " wts.wt.invInfo.quant.dispLot,"
				+ " wts.relatedBill2";
		
		Quant quant = null;
		Bin bin = null;
		Double qty = 0D;
		Double qtyA = 0D;
		Double qtyB = 0D;
		Double qtyC = 0D;
		Double qtyD = 0D;
		Double qtyE = 0D;
		Double qtyF = 0D;
		Double qtyG = 0D;
		Double qtyH = 0D;
		Double qtyI = 0D;
		
		List<Long> wtsIds = commonDao.findByQuery(hql1, 
				new String[]{"whId", "ownerId", "caseNumbers"}, 
				new Object[]{whId, ownerId, caseNumberMap.keySet()});
		for( Long wtsId : wtsIds ){
			WarehouseTaskSingle wts = commonDao.load(WarehouseTaskSingle.class, wtsId);
			// 第一个
			if( quant == null ){
				quant = wts.getWt().getInvInfo().getQuant();
				bin = wts.getWt().getInvInfo().getBin();
				qty = wts.getQty();
				switch (caseNumberMap.get(wts.getRelatedBill2())) {
				case 'A':
					qtyA = qtyA + wts.getQty();
					break;
				case 'B':
					qtyB = qtyB + wts.getQty();
					break;
				case 'C':
					qtyC = qtyC + wts.getQty();
					break;
				case 'D':
					qtyD = qtyD + wts.getQty();
					break;
				case 'E':
					qtyE = qtyE + wts.getQty();
					break;
				case 'F':
					qtyF = qtyF + wts.getQty();
					break;
				case 'G':
					qtyG = qtyG + wts.getQty();
					break;
				case 'H':
					qtyH = qtyH + wts.getQty();
					break;
				case 'I':
					qtyI = qtyI + wts.getQty();
					break;
				default:
					break;
				}
			}
			else{
				// 相同批次，相同库位
				if( quant.getId().equals(wts.getWt().getInvInfo().getQuant().getId()) &&
						bin.getId().equals(wts.getWt().getInvInfo().getBin().getId())){
					qty = qty + wts.getQty();
					switch (caseNumberMap.get(wts.getRelatedBill2())) {
					case 'A':
						qtyA = qtyA + wts.getQty();
						break;
					case 'B':
						qtyB = qtyB + wts.getQty();
						break;
					case 'C':
						qtyC = qtyC + wts.getQty();
						break;
					case 'D':
						qtyD = qtyD + wts.getQty();
						break;
					case 'E':
						qtyE = qtyE + wts.getQty();
						break;
					case 'F':
						qtyF = qtyF + wts.getQty();
						break;
					case 'G':
						qtyG = qtyG + wts.getQty();
						break;
					case 'H':
						qtyH = qtyH + wts.getQty();
						break;
					case 'I':
						qtyI = qtyI + wts.getQty();
						break;
					default:
						break;
					}
				}
				// 出现不同批次或者库位，结束
				else{
					break;
				}
			}
		}
		
		PackageDetail csPack = quant.getSku().getProperties().getPackageInfo().getP2000();
		PackageDetail blPack = quant.getSku().getProperties().getPackageInfo().getP2100();
		PackageDetail psPack = quant.getSku().getProperties().getPackageInfo().getP1000();
		
		Double blIn = quant.getSku().getProperties().getPackageInfo().getP2100().getCoefficient();
		
		Double[] qtysA = QuantityUtil.getQtys(qtyA, csPack, blPack, psPack);
		Double[] qtysB = QuantityUtil.getQtys(qtyB, csPack, blPack, psPack);
		Double[] qtysC = QuantityUtil.getQtys(qtyC, csPack, blPack, psPack);
		Double[] qtysD = QuantityUtil.getQtys(qtyD, csPack, blPack, psPack);
		Double[] qtysE = QuantityUtil.getQtys(qtyE, csPack, blPack, psPack);
		Double[] qtysF = QuantityUtil.getQtys(qtyF, csPack, blPack, psPack);
		Double[] qtysG = QuantityUtil.getQtys(qtyG, csPack, blPack, psPack);
		Double[] qtysH = QuantityUtil.getQtys(qtyH, csPack, blPack, psPack);
		Double[] qtysI = QuantityUtil.getQtys(qtyI, csPack, blPack, psPack);
		
		String qtyHyphenA = blIn == null || blIn.longValue() <= 0 ? "" + qtysA[2].longValue() : qtysA[1].longValue() + "-" + qtysA[2].longValue();
		String qtyHyphenB = blIn == null || blIn.longValue() <= 0 ? "" + qtysB[2].longValue() : qtysB[1].longValue() + "-" + qtysB[2].longValue();
		String qtyHyphenC = blIn == null || blIn.longValue() <= 0 ? "" + qtysC[2].longValue() : qtysC[1].longValue() + "-" + qtysC[2].longValue();
		String qtyHyphenD = blIn == null || blIn.longValue() <= 0 ? "" + qtysD[2].longValue() : qtysD[1].longValue() + "-" + qtysD[2].longValue();
		String qtyHyphenE = blIn == null || blIn.longValue() <= 0 ? "" + qtysE[2].longValue() : qtysE[1].longValue() + "-" + qtysE[2].longValue();
		String qtyHyphenF = blIn == null || blIn.longValue() <= 0 ? "" + qtysF[2].longValue() : qtysF[1].longValue() + "-" + qtysF[2].longValue();
		String qtyHyphenG = blIn == null || blIn.longValue() <= 0 ? "" + qtysG[2].longValue() : qtysG[1].longValue() + "-" + qtysG[2].longValue();
		String qtyHyphenH = blIn == null || blIn.longValue() <= 0 ? "" + qtysH[2].longValue() : qtysH[1].longValue() + "-" + qtysH[2].longValue();
		String qtyHyphenI = blIn == null || blIn.longValue() <= 0 ? "" + qtysI[2].longValue() : qtysI[1].longValue() + "-" + qtysI[2].longValue();
		
		String qtyInfoA = QuantityUtil.getQtyInfo(qtyA, csPack, blPack, psPack);
		String qtyInfoB = QuantityUtil.getQtyInfo(qtyB, csPack, blPack, psPack);
		String qtyInfoC = QuantityUtil.getQtyInfo(qtyC, csPack, blPack, psPack);
		String qtyInfoD = QuantityUtil.getQtyInfo(qtyD, csPack, blPack, psPack);
		String qtyInfoE = QuantityUtil.getQtyInfo(qtyE, csPack, blPack, psPack);
		String qtyInfoF = QuantityUtil.getQtyInfo(qtyF, csPack, blPack, psPack);
		String qtyInfoG = QuantityUtil.getQtyInfo(qtyG, csPack, blPack, psPack);
		String qtyInfoH = QuantityUtil.getQtyInfo(qtyH, csPack, blPack, psPack);
		String qtyInfoI = QuantityUtil.getQtyInfo(qtyI, csPack, blPack, psPack);
		
		if( quant != null && bin != null ){
			result.put("binId", bin.getId());
			result.put("binCode", bin.getBinCode());
			result.put("skuId", quant.getSku().getId());
			result.put("skuCode", quant.getSku().getCode());
			result.put("skuName", quant.getSku().getName());
			result.put("barcodes", getBarcodes(quant.getSku()));
			result.put("expDate", quant.getDispLot());
			result.put("qty", qty.longValue());
			result.put("qtyInfo", QuantityUtil.getQtyInfo(qty, 
					quant.getSku().getProperties().getPackageInfo().getP2000(), 
					quant.getSku().getProperties().getPackageInfo().getP2100(), 
					quant.getSku().getProperties().getPackageInfo().getP1000()) 
					+ "(" + qty.longValue() + 
					quant.getSku().getProperties().getPackageInfo().getP1000().getName() 
					+ ")");
			result.put("specs", quant.getSku().getSpecs());
			
			// 如果caseIN>0，并且这次只有ps的拣货
			String warnMsg4Ps = "";
			Double[] qtysDoubles = QuantityUtil.getQtys(qty, csPack, blPack, psPack);
			if( blIn != null && blIn > 0D && 
					( qtysDoubles != null && qtysDoubles.length == 3 && qtysDoubles[0] == 0D && qtysDoubles[1] == 0D && qtysDoubles[2] > 0D) ){
				warnMsg4Ps = "*バラ出荷です。ご注意ください!";
			}
			result.put("warnMsg4Ps", warnMsg4Ps);
			
			result.put("qtyA", qtyA.longValue());
			result.put("qtyB", qtyB.longValue());
			result.put("qtyC", qtyC.longValue());
			result.put("qtyD", qtyD.longValue());
			result.put("qtyE", qtyE.longValue());
			result.put("qtyF", qtyF.longValue());
			result.put("qtyG", qtyG.longValue());
			result.put("qtyH", qtyH.longValue());
			result.put("qtyI", qtyI.longValue());
			
			result.put("qtyHyphenA", qtyHyphenA);
			result.put("qtyHyphenB", qtyHyphenB);
			result.put("qtyHyphenC", qtyHyphenC);
			result.put("qtyHyphenD", qtyHyphenD);
			result.put("qtyHyphenE", qtyHyphenE);
			result.put("qtyHyphenF", qtyHyphenF);
			result.put("qtyHyphenG", qtyHyphenG);
			result.put("qtyHyphenH", qtyHyphenH);
			result.put("qtyHyphenI", qtyHyphenI);
			
			result.put("qtyInfoA", qtyInfoA);
			result.put("qtyInfoB", qtyInfoB);
			result.put("qtyInfoC", qtyInfoC);
			result.put("qtyInfoD", qtyInfoD);
			result.put("qtyInfoE", qtyInfoE);
			result.put("qtyInfoF", qtyInfoF);
			result.put("qtyInfoG", qtyInfoG);
			result.put("qtyInfoH", qtyInfoH);
			result.put("qtyInfoI", qtyInfoI);
		}
		
		return result;
	}

	@Override
	public boolean executePsPick(Long whId, Long ownerId, Long userId, Long processId, Long binId, Long skuId, String expDate) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		User user = commonDao.load(User.class, userId);
		WarehouseHolder.setWarehouse(wh);
		UserHolder.setUser(user);
		
		Map<String, Character> caseNumberMap = getCaseNumberMap(processId);
		String hql = "select wts.id from WarehouseTaskSingle wts where wts.status = 0 "
				+ "and wts.wt.wh.id = :whId "
				+ "and wts.wt.invInfo.owner.id = :ownerId "
				+ "and wts.wt.invInfo.bin.id = :binId "
				+ "and wts.wt.invInfo.quant.sku.id = :skuId "
				+ "and wts.wt.invInfo.quant.dispLot = :expDate "
				+ "and wts.relatedBill2 in (:caseNumbers) ";
		
		List<Long> ids = commonDao.findByQuery(hql, new String[]{"whId", "ownerId", "binId", "skuId", "expDate", "caseNumbers"}, 
				new Object[]{whId, ownerId, binId, skuId, expDate, caseNumberMap.keySet()});
		
		if( ids != null && ids.size() > 0 ){
			warehouseTaskSingleManager.pickExecute(ids, commonManager.getLaborIdFirst(userId));
		}
		
		String hql1 = "select wts.id from WarehouseTaskSingle wts where wts.status = 0 "
				+ "and wts.wt.wh.id = :whId "
				+ "and wts.wt.invInfo.owner.id = :ownerId "
				+ "and wts.relatedBill2 in (:caseNumbers) ";
		List<Long> toPickWtsIds = commonDao.findByQuery(hql1, new String[]{"whId", "ownerId", "caseNumbers"}, 
				new Object[]{whId, ownerId, caseNumberMap.keySet()});
		
		// 这次作业已经没有待拣货的任务了
		if( toPickWtsIds == null || toPickWtsIds.size() == 0 )
			return Boolean.TRUE;
		
		return Boolean.FALSE;
	}

	@Override
	public Map<String, Object> getWaveDocInfo(Long whId, Long ownerId, String waveDocNumber) {
		String[] waveDocs = waveDocNumber.split("-");
		
		StringBuffer hql = new StringBuffer("select doc.id from WaveDoc doc where 1=1 ");
		hql.append(" and doc.wh.id = :whId ");
		hql.append(" and doc.owner.id = :ownerId ");
		hql.append(" and doc.waveNumber = :waveDocNumber ");
		hql.append(" and doc.status >= :statusFrom ");
		
		List<Long> wvIdsList = commonDao.findByQuery(hql.toString(), 
				new String[]{"whId", "ownerId", "waveDocNumber", "statusFrom"}, 
				new Object[]{whId, ownerId, waveDocs[0], EnuWaveStatus.STATUS250});
		
		if( wvIdsList == null || wvIdsList.size() == 0 ){
			return null;
		}
		else{
			Long wvIdLong = wvIdsList.get(0);
			WaveDoc wv = commonDao.load(WaveDoc.class, wvIdLong);
			
			if( wv.getStatus().longValue() == EnuWaveStatus.STATUS500 ){
				throw new BusinessException("このピッキングリスト中のタスクは既に全部完了しました。");
			}
			
			String hql2 = " select sum(obdDetail.pickUpQty), sum(obdDetail.planQty) "
					+ " from OutboundDeliveryDetail obdDetail"
					+ " where obdDetail.obd.waveDoc.id = :waveDocId "
					+ " and obdDetail.sku.stockDiv = 1"
					+ " and obdDetail.sku.tempDiv = :tempDiv ";
			
			Object qtyObd = (Object) commonDao.findByQueryUniqueResult(hql2, 
					new String[]{"waveDocId","tempDiv"}, 
					new Object[]{wvIdLong, Long.parseLong(waveDocs[1]) });
			
			Object[] qtys = (Object[])qtyObd;
			if( qtys == null || qtys[1] == null || (Double)qtys[1] == 0D ){
				return null;
			}
			else if( (Double)qtys[0] >= (Double)qtys[1] ){
				throw new BusinessException("このピッキングリスト中のタスクは既に全部完了しました。");
			}
			else{
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("waveId", wvIdLong);
				resultMap.put("tempDiv", waveDocs[1]);
				resultMap.put("tempDivNm", LocaleUtil.getText(EnuTemperatureDiv.class.getSimpleName() + "." + waveDocs[1]));
				resultMap.put("executeQty", qtys[0] == null ? 0L : ((Double)qtys[0]).longValue());
				resultMap.put("planQty", qtys[1] == null ? 0L : ((Double)qtys[1]).longValue());
				
				return resultMap;
			}
		}
	}

	@Override
	public Map<String, Object> getBatchPickInfo(Long whId, Long ownerId, Long wvId, Long skuId, String binCode, String tempDiv) {
		
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("select wts.wt.invInfo.quant.dispLot, wts.wt.invInfo.invStatus, min(wts.id), sum(wts.qty)");
		hql.append(" from WarehouseTaskSingle wts where 1=1 ");
		hql.append(" and wts.wt.wh.id = :whId ");
		hql.append(" and wts.wt.invInfo.owner.id = :ownerId ");
		hql.append(" and wts.wt.invInfo.quant.sku.id = :skuId ");
		hql.append(" and wts.wt.invInfo.bin.binCode = :binCode ");
		hql.append(" and wts.wt.status not in ( :status ) ");
		hql.append(" and wts.wt.invInfo.quant.sku.tempDiv = :tempDiv ");
		hql.append(" and wts.wt.invInfo.quant.sku.stockDiv = 1L ");
		hql.append(" and wts.waveDoc.id = :wvId ");
		
		hql.append(" group by wts.wt.invInfo.quant.id, wts.wt.invInfo.quant.dispLot, wts.wt.invInfo.invStatus ");
		hql.append(" order by wts.wt.invInfo.quant.dispLot, wts.wt.invInfo.invStatus ");
		
		paramNames.add("whId");
		paramNames.add("ownerId");
		paramNames.add("skuId");
		paramNames.add("binCode");
		paramNames.add("status");
		paramNames.add("tempDiv");
		paramNames.add("wvId");
		params.add(whId);
		params.add(ownerId);
		params.add(skuId);
		params.add(binCode);
		params.add(EnuWarehouseOrderStatus.CLOSE);
		params.add(Long.parseLong(tempDiv));
		params.add(wvId);
		
		List<Object> pickInfos = commonDao.findByQuery(hql.toString(), paramNames.toArray(new String[paramNames.size()]), params.toArray(new Object[params.size()])); 
		
		if( pickInfos == null || pickInfos.size() == 0 || pickInfos.get(0) == null ){
			return null;
		}
		
		Object[] pickObjInfObjects = (Object[])pickInfos.get(0);
		String expDateString = (String)pickObjInfObjects[0];
		String invStatus = (String)pickObjInfObjects[1];
		Long minWtsIdLong = (Long)pickObjInfObjects[2];
		Double sumQty = (Double)pickObjInfObjects[3];
		
		WarehouseTaskSingle wtsSingle = commonDao.load(WarehouseTaskSingle.class, minWtsIdLong);
		if( wtsSingle != null ){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("wtId", minWtsIdLong);
			result.put("skuId", wtsSingle.getWt().getInvInfo().getQuant().getSku().getId());
			result.put("skuCode", wtsSingle.getWt().getInvInfo().getQuant().getSku().getCode());
			result.put("skuName", wtsSingle.getWt().getInvInfo().getQuant().getSku().getName());
			result.put("specs", wtsSingle.getWt().getInvInfo().getQuant().getSku().getSpecs());
			result.put("expDate", expDateString);
			
			PackageDetail csPack = wtsSingle.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP2000();
			PackageDetail blPack = wtsSingle.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP2100();
			PackageDetail psPack = wtsSingle.getWt().getInvInfo().getQuant().getSku().getProperties().getPackageInfo().getP1000();
			
			result.put("csIn", csPack.getCoefficient() == null ? 0L : csPack.getCoefficient());
			result.put("blIn", blPack.getCoefficient() == null ? 0L : blPack.getCoefficient());
			result.put("csUnit", csPack.getName() == null ? "" : csPack.getName());
			result.put("blUnit", blPack.getName() == null ? "" : blPack.getName());
			result.put("psUnit", psPack.getName() == null ? "" : psPack.getName());
			
			String qtyInfo = QuantityUtil.getQtyInfo(sumQty, csPack, blPack, psPack);
			Double[] qtys = QuantityUtil.getQtys(sumQty, csPack, blPack, psPack);
			result.put("qtyInfo", qtyInfo);
			result.put("qty", sumQty.longValue());
			
			result.put("csQty", qtys[0].longValue());
			result.put("blQty", qtys[1].longValue());
			result.put("psQty", qtys[2].longValue());
			
			result.put("invStatus", invStatus);
			result.put("invStatusNm", LocaleUtil.getText(EnuInvStatus.class.getSimpleName() + "." + invStatus));
			
			return result;
		}
		
		return null;
	}

}
