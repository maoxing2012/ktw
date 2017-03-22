package com.core.scpwms.server.service.fee.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.enumerate.EnuFeeType;
import com.core.scpwms.server.enumerate.EnuFeeUnit;
import com.core.scpwms.server.enumerate.EnuRequestReportStatus;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.enumerate.EnuTermDiv;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.fee.FeeType;
import com.core.scpwms.server.model.fee.RequestReportDetail;
import com.core.scpwms.server.model.fee.RequestReportHead;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.service.fee.RequestReportManager;
import com.core.scpwms.server.util.DateUtil;
import com.sun.corba.se.impl.orbutil.concurrent.Sync;

/**
 * 
 * <p>
 * 请求书处理
 * </p>
 * 
 * @version 1.0
 * @author MBP :毛　幸<br/>
 *         変更履歴 <br/>
 *         2013-6-11 : MBP 毛　幸: 新規作成<br/>
 */
public class RequestReportManagerImpl extends DefaultBaseManager implements
		RequestReportManager {
	private static List<Long> allTempDivs = new ArrayList<Long>();
	
	private static List<Long> coolTempDivs = new ArrayList<Long>();
	
	private static List<Long> dryTempDivs = new ArrayList<Long>();
	
	// 构建线程安全的Set
	private static Set<Long> headIdSet = Collections.synchronizedSet(new HashSet<Long>());
	
	static{
		allTempDivs.add(EnuTemperatureDiv.CW);
		allTempDivs.add(EnuTemperatureDiv.LC);
		allTempDivs.add(EnuTemperatureDiv.LD);
		
		dryTempDivs.add(EnuTemperatureDiv.CW);
		
		coolTempDivs.add(EnuTemperatureDiv.LC);
		coolTempDivs.add(EnuTemperatureDiv.LD);
	}
	
	@Override
	public void deleteRequest(List<Long> ids) {
		for (Long id : ids) {
			RequestReportHead head = getCommonDao().load( RequestReportHead.class, id);
			if (EnuRequestReportStatus.STATUS100.equals(head.getStatus())) {
				// 删除原来的数据
				commonDao.executeByNamed("deleteRequestReportDetail", new String[] { "headId" }, new Object[] { head.getId() });
				getCommonDao().delete(head);
			}
		}
	}

	@Override
	public void doProcess(List<Long> ids) {
		RequestReportHead head = getCommonDao().load(RequestReportHead.class, ids.get(0));
		if (EnuRequestReportStatus.STATUS100.equals(head.getStatus())) {
			// ------------删除原来的数据
			commonDao.executeByNamed("deleteRequestReportDetail", new String[] { "headId" }, new Object[] { head.getId() });

			// ------------分期统计数量
			if( EnuTermDiv.TERM_DIV200.equals(head.getTermDiv()) ){
				// 2期
				createRequestDetail2Term(head, allTempDivs);
			}
			else if( EnuTermDiv.TERM_DIV300.equals(head.getTermDiv()) ){
				// 3期
				createRequestDetail3Term(head, allTempDivs);
			}
			else if( EnuTermDiv.TERM_DIV400.equals(head.getTermDiv()) ){
				// 常温3期
				createRequestDetail3Term(head, dryTempDivs);
				// 冷蔵冷凍2期
				createRequestDetail2Term(head, coolTempDivs);
			}
			else{
				throw new BusinessException("請求パターン、対応不可。");
			}

			head.setStatus(EnuRequestReportStatus.STATUS200);
			head.setProcessDate(new Date());
			getCommonDao().store(head);
		}
	}
	
	@Override
	public void doProcess2(List<Long> ids) {
	    synchronized(headIdSet){
            if( headIdSet.contains(ids.get(0)) ){
                return;
            }
    	    headIdSet.add(ids.get(0));
	    }

	    RequestReportHead head = getCommonDao().load(RequestReportHead.class, ids.get(0));
        if (EnuRequestReportStatus.STATUS100.equals(head.getStatus())) {
            // ------------删除原来的数据
            commonDao.executeByNamed("deleteRequestReportDetail", new String[] { "headId" }, new Object[] { head.getId() });

			// ------------分期统计数量
			if( EnuTermDiv.TERM_DIV200.equals(head.getTermDiv()) ){
				// 2期
				createRequestDetail2TermNew(head, allTempDivs);
			}
			else if( EnuTermDiv.TERM_DIV300.equals(head.getTermDiv()) ){
				// 3期
				createRequestDetail3TermNew(head, allTempDivs);
			}
			else if( EnuTermDiv.TERM_DIV400.equals(head.getTermDiv()) ){
				// 常温3期
				createRequestDetail3TermNew(head, dryTempDivs);
				// 冷蔵冷凍2期
				createRequestDetail2TermNew(head, coolTempDivs);
			}
			else{
				throw new BusinessException("請求パターン、対応不可。");
			}

			head.setStatus(EnuRequestReportStatus.STATUS200);
			head.setProcessDate(new Date());
			getCommonDao().store(head);
		}
        
        headIdSet.remove(ids.get(0));
	}

	private void createRequestDetail2Term(RequestReportHead head, List<Long> tempDivs) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select sku.id ");
		// 当前库存
		hql.append(" ,(select sum( inv.baseQty ) from Inventory inv where inv.quantInv.quant.sku.id = sku.id) as nowQty ");
		// 统计周期的最后一天到当前这段时间内的变化量
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and to_char( ih.operateTime, 'YYYYMMDD' ) > :lastDay ) as last2NowAdQty ");
		
		// 第一期的收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('100','110') and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term1f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term1t ) as in1 ");
		// 第一期的发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('200','210') and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term1f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term1t ) as out1 ");
		// 第一期的调整收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('600','300') and ih.qtyEach > 0 and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term1f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term1t ) as aIn1 ");
		// 第一期的调整发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('700','300') and ih.qtyEach < 0 and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term1f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term1t ) as aOut1 ");
		
		// 第二期的收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('100','110') and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term2f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term2t ) as in2 ");
		// 第二期的发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('200','210') and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term2f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term2t ) as out2 ");
		// 第二期的调整收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('600','300') and ih.qtyEach > 0 and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term2f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term2t ) as aIn2 ");
		// 第二期的调整发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('700','300') and ih.qtyEach < 0 and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term2f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term2t ) as aOut2 ");
		
		hql.append(" from Sku sku where sku.owner.id = :ownerId and sku.tempDiv in (:tempDivs) ");
		
		List<Object> skuQtyInfosList = commonDao.findByQuery(hql.toString(), 
				new String[]{"ownerId", "tempDivs", "lastDay", "term1f", "term1t", "term2f", "term2t"}, 
				new Object[]{head.getOwner().getId(), tempDivs, 
				DateUtil.getStringDate( head.getDayTo(), DateUtil.PURE_DATE_FORMAT),
				
				DateUtil.getStringDate( head.getTerm21F(), DateUtil.PURE_DATE_FORMAT),
				DateUtil.getStringDate( head.getTerm21T(), DateUtil.PURE_DATE_FORMAT),
				
				DateUtil.getStringDate( head.getTerm22F(), DateUtil.PURE_DATE_FORMAT),
				DateUtil.getStringDate( head.getTerm22T(), DateUtil.PURE_DATE_FORMAT)});
		
		if( skuQtyInfosList != null && skuQtyInfosList.size() > 0 ){
			for( Object skuQtyInfoObd : skuQtyInfosList ){
				Object[] skuInfos = (Object[])skuQtyInfoObd;
				
				Long skuId = (Long)skuInfos[0];
				Double nowQtyDouble = skuInfos[1] == null ? 0D : (Double)skuInfos[1];
				Double last2NowAdQty = skuInfos[2] == null ? 0D : (Double)skuInfos[2];
				
				Double in1 = skuInfos[3] == null ? 0D : (Double)skuInfos[3];
				Double out1 = skuInfos[4] == null ? 0D : (Double)skuInfos[4];
				Double aIn1 = skuInfos[5] == null ? 0D : (Double)skuInfos[5];
				Double aOut1 = skuInfos[6] == null ? 0D : (Double)skuInfos[6];
				
				Double in2 = skuInfos[7] == null ? 0D : (Double)skuInfos[7];
				Double out2 = skuInfos[8] == null ? 0D : (Double)skuInfos[8];
				Double aIn2 = skuInfos[9] == null ? 0D : (Double)skuInfos[9];
				Double aOut2 = skuInfos[10] == null ? 0D : (Double)skuInfos[10];
				
				if( nowQtyDouble == 0 && last2NowAdQty == 0
						&& in1 == 0 && out1 == 0 && aIn1 == 0 && aOut1 == 0 
						&& in2 == 0 && out2 == 0 && aIn2 == 0 && aOut2 == 0 ){
					continue;
				}
				
				RequestReportDetail detail = new RequestReportDetail();
				detail.setHead(head);
				detail.setTermNum(2L);//2期
				Sku sku = commonDao.load(Sku.class, skuId);
				detail.setSku(sku);
				detail.setUnitWeight(sku.getGrossWeight());
				detail.setUnitVolume(sku.getVolume());
				
				/** 前期残1 */
				detail.setPrevQty1(nowQtyDouble - last2NowAdQty - in2 - aIn2 + out2 + aOut2 - in1 - aIn1 + out1 + aOut1);
				
			    /** 入庫数1 */
				detail.setInboundQty1(in1);
			    /** 出庫数1 */
				detail.setOutboundQty1(out1);
			    /** 調整入1 */
				detail.setAdjustInQty1(aIn1);
			    /** 調整出1 */
				detail.setAdjustOutQty1(aOut1);
				
			    /** 前期残2 */
				detail.setPrevQty2(nowQtyDouble - last2NowAdQty - in2 - aIn2 + out2 + aOut2);
				
			    /** 入庫数2 */
				detail.setInboundQty2(in2);
			    /** 出庫数2 */
				detail.setOutboundQty2(out2);
			    /** 調整入2 */
				detail.setAdjustInQty2(aIn2);
			    /** 調整出2 */
				detail.setAdjustOutQty2(aOut2);
				
				// 费用
				setFee(detail);
				
				commonDao.store(detail);
			}
		}
	}
	
	private void createRequestDetail2TermNew(RequestReportHead head, List<Long> tempDivs) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select sku.id ");
		// 当前库存
		hql.append(" ,(select sum( inv.baseQty ) from Inventory inv where inv.quantInv.quant.sku.id = sku.id) as nowQty ");
		// 统计周期的最后一天到当前这段时间内的变化量
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.operateTime > :lastDay ) as last2NowAdQty ");
		
		// 第一期的收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('100','110') and ih.operateTime >= :term1f and ih.operateTime <= :term1t ) as in1 ");
		// 第一期的发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('200','210') and ih.operateTime >= :term1f and ih.operateTime <= :term1t ) as out1 ");
		// 第一期的调整收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('600','300') and ih.qtyEach > 0 and ih.operateTime >= :term1f and ih.operateTime <= :term1t ) as aIn1 ");
		// 第一期的调整发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('700','300') and ih.qtyEach < 0 and ih.operateTime >= :term1f and ih.operateTime <= :term1t ) as aOut1 ");
		
		// 第二期的收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('100','110') and ih.operateTime >= :term2f and ih.operateTime <= :term2t ) as in2 ");
		// 第二期的发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('200','210') and ih.operateTime >= :term2f and ih.operateTime <= :term2t ) as out2 ");
		// 第二期的调整收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('600','300') and ih.qtyEach > 0 and ih.operateTime >= :term2f and ih.operateTime <= :term2t ) as aIn2 ");
		// 第二期的调整发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('700','300') and ih.qtyEach < 0 and ih.operateTime >= :term2f and ih.operateTime <= :term2t ) as aOut2 ");
		
		hql.append(" from Sku sku where sku.owner.id = :ownerId and sku.tempDiv in (:tempDivs) ");
		
		List<Object> skuQtyInfosList = commonDao.findByQuery(hql.toString(), 
				new String[]{"ownerId", "tempDivs", "lastDay", "term1f", "term1t", "term2f", "term2t"}, 
				new Object[]{head.getOwner().getId(), tempDivs, 
				DateUtil.getDate(DateUtil.getStringDate( head.getDayTo(), DateUtil.HYPHEN_DATE_FORMAT) + " 24:00:00", DateUtil.HYPHEN_DATETIME_FORMAT),
				
				DateUtil.getDate(DateUtil.getStringDate( head.getTerm21F(), DateUtil.HYPHEN_DATE_FORMAT) + " 00:00:00", DateUtil.HYPHEN_DATETIME_FORMAT),
				DateUtil.getDate(DateUtil.getStringDate( head.getTerm21T(), DateUtil.HYPHEN_DATE_FORMAT) + " 24:00:00", DateUtil.HYPHEN_DATETIME_FORMAT),
				
				DateUtil.getDate(DateUtil.getStringDate( head.getTerm22F(), DateUtil.HYPHEN_DATE_FORMAT) + " 00:00:00", DateUtil.HYPHEN_DATETIME_FORMAT),
				DateUtil.getDate(DateUtil.getStringDate( head.getTerm22T(), DateUtil.HYPHEN_DATE_FORMAT) + " 24:00:00", DateUtil.HYPHEN_DATETIME_FORMAT)});
		
		if( skuQtyInfosList != null && skuQtyInfosList.size() > 0 ){
			for( Object skuQtyInfoObd : skuQtyInfosList ){
				Object[] skuInfos = (Object[])skuQtyInfoObd;
				
				Long skuId = (Long)skuInfos[0];
				Double nowQtyDouble = skuInfos[1] == null ? 0D : (Double)skuInfos[1];
				Double last2NowAdQty = skuInfos[2] == null ? 0D : (Double)skuInfos[2];
				
				Double in1 = skuInfos[3] == null ? 0D : (Double)skuInfos[3];
				Double out1 = skuInfos[4] == null ? 0D : (Double)skuInfos[4];
				Double aIn1 = skuInfos[5] == null ? 0D : (Double)skuInfos[5];
				Double aOut1 = skuInfos[6] == null ? 0D : (Double)skuInfos[6];
				
				Double in2 = skuInfos[7] == null ? 0D : (Double)skuInfos[7];
				Double out2 = skuInfos[8] == null ? 0D : (Double)skuInfos[8];
				Double aIn2 = skuInfos[9] == null ? 0D : (Double)skuInfos[9];
				Double aOut2 = skuInfos[10] == null ? 0D : (Double)skuInfos[10];
				
				if( nowQtyDouble == 0 && last2NowAdQty == 0
						&& in1 == 0 && out1 == 0 && aIn1 == 0 && aOut1 == 0 
						&& in2 == 0 && out2 == 0 && aIn2 == 0 && aOut2 == 0 ){
					continue;
				}
				
				RequestReportDetail detail = new RequestReportDetail();
				detail.setHead(head);
				detail.setTermNum(2L);//2期
				Sku sku = commonDao.load(Sku.class, skuId);
				detail.setSku(sku);
				detail.setUnitWeight(sku.getGrossWeight());
				detail.setUnitVolume(sku.getVolume());
				
				/** 前期残1 */
				detail.setPrevQty1(nowQtyDouble - last2NowAdQty - in2 - aIn2 + out2 + aOut2 - in1 - aIn1 + out1 + aOut1);
				
			    /** 入庫数1 */
				detail.setInboundQty1(in1);
			    /** 出庫数1 */
				detail.setOutboundQty1(out1);
			    /** 調整入1 */
				detail.setAdjustInQty1(aIn1);
			    /** 調整出1 */
				detail.setAdjustOutQty1(aOut1);
				
			    /** 前期残2 */
				detail.setPrevQty2(nowQtyDouble - last2NowAdQty - in2 - aIn2 + out2 + aOut2);
				
			    /** 入庫数2 */
				detail.setInboundQty2(in2);
			    /** 出庫数2 */
				detail.setOutboundQty2(out2);
			    /** 調整入2 */
				detail.setAdjustInQty2(aIn2);
			    /** 調整出2 */
				detail.setAdjustOutQty2(aOut2);
				
				// 费用
				setFee(detail);
				
				commonDao.store(detail);
			}
		}
	}
	
	private void createRequestDetail3Term(RequestReportHead head, List<Long> tempDivs) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select sku.id ");
		
		// 当前库存
		hql.append(" ,(select sum( inv.baseQty ) from Inventory inv where inv.quantInv.quant.sku.id = sku.id) as nowQty ");
		// 统计周期的最后一天到当前这段时间内的变化量
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and to_char( ih.operateTime, 'YYYYMMDD' ) > :lastDay ) as last2NowAdQty ");
		
		// 第一期的收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('100','110') and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term1f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term1t ) as in1 ");
		// 第一期的发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('200','210') and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term1f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term1t ) as out1 ");
		// 第一期的调整收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('600','300') and ih.qtyEach > 0 and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term1f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term1t ) as aIn1 ");
		// 第一期的调整发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('700','300') and ih.qtyEach < 0 and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term1f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term1t ) as aOut1 ");
		
		// 第二期的收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('100','110') and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term2f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term2t ) as in2 ");
		// 第二期的发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('200','210') and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term2f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term2t ) as out2 ");
		// 第二期的调整收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('600','300') and ih.qtyEach > 0 and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term2f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term2t ) as aIn2 ");
		// 第二期的调整发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('700','300') and ih.qtyEach < 0 and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term2f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term2t ) as aOut2 ");
		
		// 第三期的收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('100','110') and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term3f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term3t ) as in3 ");
		// 第三期的发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('200','210') and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term3f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term3t ) as out3 ");
		// 第三期的调整收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('600','300') and ih.qtyEach > 0 and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term3f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term3t ) as aIn3 ");
		// 第三期的调整发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('700','300') and ih.qtyEach < 0 and to_char( ih.operateTime, 'YYYYMMDD' ) >= :term3f and to_char( ih.operateTime, 'YYYYMMDD' ) <= :term3t ) as aOut3 ");
		
		hql.append(" from Sku sku where sku.owner.id = :ownerId and sku.tempDiv in (:tempDivs) ");
		
		List<Object> skuQtyInfosList = commonDao.findByQuery(hql.toString(), 
				new String[]{"ownerId", "tempDivs", "lastDay", "term1f", "term1t", "term2f", "term2t", "term3f", "term3t"}, 
				new Object[]{head.getOwner().getId(), tempDivs, 
				DateUtil.getStringDate( head.getDayTo(), DateUtil.PURE_DATE_FORMAT),
				
				DateUtil.getStringDate( head.getDayF1(), DateUtil.PURE_DATE_FORMAT),
				DateUtil.getStringDate( head.getDayT1(), DateUtil.PURE_DATE_FORMAT),
				
				DateUtil.getStringDate( head.getDayF2(), DateUtil.PURE_DATE_FORMAT),
				DateUtil.getStringDate( head.getDayT2(), DateUtil.PURE_DATE_FORMAT),
				
				DateUtil.getStringDate( head.getDayF3(), DateUtil.PURE_DATE_FORMAT),
				DateUtil.getStringDate( head.getDayT3(), DateUtil.PURE_DATE_FORMAT)});
		
		if( skuQtyInfosList != null && skuQtyInfosList.size() > 0 ){
			for( Object skuQtyInfoObd : skuQtyInfosList ){
				Object[] skuInfos = (Object[])skuQtyInfoObd;
				
				Long skuId = (Long)skuInfos[0];
				Double nowQtyDouble = skuInfos[1] == null ? 0D : (Double)skuInfos[1];
				Double last2NowAdQty = skuInfos[2] == null ? 0D : (Double)skuInfos[2];
				
				Double in1 = skuInfos[3] == null ? 0D : (Double)skuInfos[3];
				Double out1 = skuInfos[4] == null ? 0D : (Double)skuInfos[4];
				Double aIn1 = skuInfos[5] == null ? 0D : (Double)skuInfos[5];
				Double aOut1 = skuInfos[6] == null ? 0D : (Double)skuInfos[6];
				
				Double in2 = skuInfos[7] == null ? 0D : (Double)skuInfos[7];
				Double out2 = skuInfos[8] == null ? 0D : (Double)skuInfos[8];
				Double aIn2 = skuInfos[9] == null ? 0D : (Double)skuInfos[9];
				Double aOut2 = skuInfos[10] == null ? 0D : (Double)skuInfos[10];
				
				Double in3 = skuInfos[11] == null ? 0D : (Double)skuInfos[11];
				Double out3 = skuInfos[12] == null ? 0D : (Double)skuInfos[12];
				Double aIn3 = skuInfos[13] == null ? 0D : (Double)skuInfos[13];
				Double aOut3 = skuInfos[14] == null ? 0D : (Double)skuInfos[14];
				
				if( nowQtyDouble == 0 && last2NowAdQty == 0
						&& in1 == 0 && out1 == 0 && aIn1 == 0 && aOut1 == 0 
						&& in2 == 0 && out2 == 0 && aIn2 == 0 && aOut2 == 0
						&& in3 == 0 && out3 == 0 && aIn3 == 0 && aOut3 == 0){
					continue;
				}
				
				RequestReportDetail detail = new RequestReportDetail();
				detail.setHead(head);
				detail.setTermNum(3L);// 3期
				Sku sku = commonDao.load(Sku.class, skuId);
				detail.setSku(sku);
				detail.setUnitWeight(sku.getGrossWeight());
				detail.setUnitVolume(sku.getVolume());
				
				/** 前期残1 */
				detail.setPrevQty1(nowQtyDouble - last2NowAdQty - in3 - aIn3 + out3 + aOut3 - in2 - aIn2 + out2 + aOut2 - in1 - aIn1 + out1 + aOut1);
				
			    /** 入庫数1 */
				detail.setInboundQty1(in1);
			    /** 出庫数1 */
				detail.setOutboundQty1(out1);
			    /** 調整入1 */
				detail.setAdjustInQty1(aIn1);
			    /** 調整出1 */
				detail.setAdjustOutQty1(aOut1);
				
			    /** 前期残2 */
				detail.setPrevQty2(nowQtyDouble - last2NowAdQty - in3 - aIn3 + out3 + aOut3 - in2 - aIn2 + out2 + aOut2);
				
			    /** 入庫数2 */
				detail.setInboundQty2(in2);
			    /** 出庫数2 */
				detail.setOutboundQty2(out2);
			    /** 調整入2 */
				detail.setAdjustInQty2(aIn2);
			    /** 調整出2 */
				detail.setAdjustOutQty2(aOut2);
				
				/** 前期残3 */
				detail.setPrevQty3(nowQtyDouble - last2NowAdQty - in3 - aIn3 + out3 + aOut3);
				
			    /** 入庫数3 */
				detail.setInboundQty3(in3);
			    /** 出庫数3 */
				detail.setOutboundQty3(out3);
			    /** 調整入3 */
				detail.setAdjustInQty3(aIn3);
			    /** 調整出3 */
				detail.setAdjustOutQty3(aOut3);
				
				// 费用
				setFee(detail);
				
				commonDao.store(detail);
			}
		}
	}
	
	private void createRequestDetail3TermNew(RequestReportHead head, List<Long> tempDivs) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select sku.id ");
		
		// 当前库存
		hql.append(" ,(select sum( inv.baseQty ) from Inventory inv where inv.quantInv.quant.sku.id = sku.id) as nowQty ");
		// 统计周期的最后一天到当前这段时间内的变化量
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.operateTime > :lastDay ) as last2NowAdQty ");
		
		// 第一期的收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('100','110') and ih.operateTime >= :term1f and ih.operateTime <= :term1t ) as in1 ");
		// 第一期的发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('200','210') and ih.operateTime >= :term1f and ih.operateTime <= :term1t ) as out1 ");
		// 第一期的调整收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('600','300') and ih.qtyEach > 0 and ih.operateTime >= :term1f and ih.operateTime <= :term1t ) as aIn1 ");
		// 第一期的调整发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('700','300') and ih.qtyEach < 0 and ih.operateTime >= :term1f and ih.operateTime <= :term1t ) as aOut1 ");
		
		// 第二期的收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('100','110') and ih.operateTime >= :term2f and ih.operateTime <= :term2t ) as in2 ");
		// 第二期的发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('200','210') and ih.operateTime >= :term2f and ih.operateTime <= :term2t ) as out2 ");
		// 第二期的调整收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('600','300') and ih.qtyEach > 0 and ih.operateTime >= :term2f and ih.operateTime <= :term2t ) as aIn2 ");
		// 第二期的调整发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('700','300') and ih.qtyEach < 0 and ih.operateTime >= :term2f and ih.operateTime <= :term2t ) as aOut2 ");
		
		// 第三期的收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('100','110') and ih.operateTime >= :term3f and ih.operateTime <= :term3t ) as in3 ");
		// 第三期的发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('200','210') and ih.operateTime >= :term3f and ih.operateTime <= :term3t ) as out3 ");
		// 第三期的调整收货
		hql.append(" ,( select sum( ih.qtyEach ) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('600','300') and ih.qtyEach > 0 and ih.operateTime >= :term3f and ih.operateTime <= :term3t ) as aIn3 ");
		// 第三期的调整发货
		hql.append(" ,( select sum( ih.qtyEach )*(-1) from InventoryHistory ih where ih.invInfo.quant.sku.id = sku.id and ih.hisType in ('700','300') and ih.qtyEach < 0 and ih.operateTime >= :term3f and ih.operateTime <= :term3t ) as aOut3 ");
		
		hql.append(" from Sku sku where sku.owner.id = :ownerId and sku.tempDiv in (:tempDivs) ");
		
		List<Object> skuQtyInfosList = commonDao.findByQuery(hql.toString(), 
				new String[]{"ownerId", "tempDivs", "lastDay", "term1f", "term1t", "term2f", "term2t", "term3f", "term3t"}, 
				new Object[]{head.getOwner().getId(), tempDivs, 
				DateUtil.getDate(DateUtil.getStringDate( head.getDayTo(), DateUtil.HYPHEN_DATE_FORMAT) + " 24:00:00", DateUtil.HYPHEN_DATETIME_FORMAT),
				
				DateUtil.getDate(DateUtil.getStringDate( head.getDayF1(), DateUtil.HYPHEN_DATE_FORMAT) + " 00:00:00", DateUtil.HYPHEN_DATETIME_FORMAT),
				DateUtil.getDate(DateUtil.getStringDate( head.getDayT1(), DateUtil.HYPHEN_DATE_FORMAT) + " 24:00:00", DateUtil.HYPHEN_DATETIME_FORMAT),
				
				DateUtil.getDate(DateUtil.getStringDate( head.getDayF2(), DateUtil.HYPHEN_DATE_FORMAT) + " 00:00:00", DateUtil.HYPHEN_DATETIME_FORMAT),
				DateUtil.getDate(DateUtil.getStringDate( head.getDayT2(), DateUtil.HYPHEN_DATE_FORMAT) + " 24:00:00", DateUtil.HYPHEN_DATETIME_FORMAT),
				
				DateUtil.getDate(DateUtil.getStringDate( head.getDayF3(), DateUtil.HYPHEN_DATE_FORMAT) + " 00:00:00", DateUtil.HYPHEN_DATETIME_FORMAT),
				DateUtil.getDate(DateUtil.getStringDate( head.getDayT3(), DateUtil.HYPHEN_DATE_FORMAT) + " 24:00:00", DateUtil.HYPHEN_DATETIME_FORMAT)});
		
		if( skuQtyInfosList != null && skuQtyInfosList.size() > 0 ){
			for( Object skuQtyInfoObd : skuQtyInfosList ){
				Object[] skuInfos = (Object[])skuQtyInfoObd;
				
				Long skuId = (Long)skuInfos[0];
				Double nowQtyDouble = skuInfos[1] == null ? 0D : (Double)skuInfos[1];
				Double last2NowAdQty = skuInfos[2] == null ? 0D : (Double)skuInfos[2];
				
				Double in1 = skuInfos[3] == null ? 0D : (Double)skuInfos[3];
				Double out1 = skuInfos[4] == null ? 0D : (Double)skuInfos[4];
				Double aIn1 = skuInfos[5] == null ? 0D : (Double)skuInfos[5];
				Double aOut1 = skuInfos[6] == null ? 0D : (Double)skuInfos[6];
				
				Double in2 = skuInfos[7] == null ? 0D : (Double)skuInfos[7];
				Double out2 = skuInfos[8] == null ? 0D : (Double)skuInfos[8];
				Double aIn2 = skuInfos[9] == null ? 0D : (Double)skuInfos[9];
				Double aOut2 = skuInfos[10] == null ? 0D : (Double)skuInfos[10];
				
				Double in3 = skuInfos[11] == null ? 0D : (Double)skuInfos[11];
				Double out3 = skuInfos[12] == null ? 0D : (Double)skuInfos[12];
				Double aIn3 = skuInfos[13] == null ? 0D : (Double)skuInfos[13];
				Double aOut3 = skuInfos[14] == null ? 0D : (Double)skuInfos[14];
				
				if( nowQtyDouble == 0 && last2NowAdQty == 0
						&& in1 == 0 && out1 == 0 && aIn1 == 0 && aOut1 == 0 
						&& in2 == 0 && out2 == 0 && aIn2 == 0 && aOut2 == 0
						&& in3 == 0 && out3 == 0 && aIn3 == 0 && aOut3 == 0){
					continue;
				}
				
				RequestReportDetail detail = new RequestReportDetail();
				detail.setHead(head);
				detail.setTermNum(3L);// 3期
				Sku sku = commonDao.load(Sku.class, skuId);
				detail.setSku(sku);
				detail.setUnitWeight(sku.getGrossWeight());
				detail.setUnitVolume(sku.getVolume());
				
				/** 前期残1 */
				detail.setPrevQty1(nowQtyDouble - last2NowAdQty - in3 - aIn3 + out3 + aOut3 - in2 - aIn2 + out2 + aOut2 - in1 - aIn1 + out1 + aOut1);
				
			    /** 入庫数1 */
				detail.setInboundQty1(in1);
			    /** 出庫数1 */
				detail.setOutboundQty1(out1);
			    /** 調整入1 */
				detail.setAdjustInQty1(aIn1);
			    /** 調整出1 */
				detail.setAdjustOutQty1(aOut1);
				
			    /** 前期残2 */
				detail.setPrevQty2(nowQtyDouble - last2NowAdQty - in3 - aIn3 + out3 + aOut3 - in2 - aIn2 + out2 + aOut2);
				
			    /** 入庫数2 */
				detail.setInboundQty2(in2);
			    /** 出庫数2 */
				detail.setOutboundQty2(out2);
			    /** 調整入2 */
				detail.setAdjustInQty2(aIn2);
			    /** 調整出2 */
				detail.setAdjustOutQty2(aOut2);
				
				/** 前期残3 */
				detail.setPrevQty3(nowQtyDouble - last2NowAdQty - in3 - aIn3 + out3 + aOut3);
				
			    /** 入庫数3 */
				detail.setInboundQty3(in3);
			    /** 出庫数3 */
				detail.setOutboundQty3(out3);
			    /** 調整入3 */
				detail.setAdjustInQty3(aIn3);
			    /** 調整出3 */
				detail.setAdjustOutQty3(aOut3);
				
				// 费用
				setFee(detail);
				
				commonDao.store(detail);
			}
		}
	}
	
	private void setFee( RequestReportDetail detail ){
		Sku sku = detail.getSku();
		// 保管料
		FeeType feeTypeStock = getFeeType(sku.getOwner(), sku, EnuFeeType.F3000);
		if( feeTypeStock == null ){
			throw new BusinessException(sku.getName() + "、保管料単価を設定してください。");
		}
		detail.setFeeType4Stock(feeTypeStock);
		detail.setStoragePrice(getPsPrice(feeTypeStock.getUnit(), feeTypeStock.getFee(), sku));
		
		
		// 入荷荷役料
		FeeType feeTypeIn = getFeeType(sku.getOwner(), sku, EnuFeeType.F1000);
		if( feeTypeIn == null ){
			throw new BusinessException(sku.getName() + "、入荷荷役単価を設定してください。");
		}
		detail.setFeeType4In(feeTypeIn);
		detail.setInboundPrice(getPsPrice(feeTypeIn.getUnit(), feeTypeIn.getFee(), sku));
		
		// 出荷荷役料
		FeeType feeTypeOut = getFeeType(sku.getOwner(), sku, EnuFeeType.F2000);
		if( feeTypeIn == null ){
			throw new BusinessException(sku.getName() + "、出荷荷役単価を設定してください。");
		}
		detail.setFeeType4Out(feeTypeOut);
		detail.setOutboundPrice(getPsPrice(feeTypeOut.getUnit(), feeTypeOut.getFee(), sku));
		
//		BigDecimal stockQty = new BigDecimal( detail.getPrevQty1() + detail.getInboundQty1() + detail.getAdjustInQty1() - detail.getAdjustOutQty1()
//				+ detail.getPrevQty2() + detail.getInboundQty2() + detail.getAdjustInQty2() - detail.getAdjustOutQty2()
//				+ detail.getPrevQty3() + detail.getInboundQty3() + detail.getAdjustInQty3() - detail.getAdjustOutQty3());
//		
//		BigDecimal inboundQty = new BigDecimal( detail.getInboundQty1() + detail.getInboundQty2() + detail.getInboundQty3());
//		BigDecimal outboundQty = new BigDecimal( detail.getOutboundQty1() + detail.getOutboundQty2() + detail.getOutboundQty3());
//		
//		detail.setStockQty(stockQty);
//		detail.setInboundQty(inboundQty);
//		detail.setOutboundQty(outboundQty);
//		
//		BigDecimal stockAmount = new BigDecimal(detail.getStoragePrice()).multiply(detail.getStockQty());
//		BigDecimal inboundAmount = new BigDecimal(detail.getInboundPrice()).multiply(detail.getInboundQty());
//		BigDecimal outboundAmount = new BigDecimal(detail.getOutboundPrice()).multiply(detail.getOutboundQty());
//		
//		detail.setStockAmount(stockAmount);
//		detail.setInboundAmount(inboundAmount);
//		detail.setOutboundAmount(outboundAmount);
	}
	
	private Double getPsPrice( String unit, Double price, Sku sku ){
		// 重量
		if( EnuFeeUnit.UNIT_WEIGHT_KG.equals(unit) ){
			Double priceDouble = price * (sku.getGrossWeight() == null ? 0: sku.getGrossWeight());
			return DoubleUtil.round(priceDouble, 4);
		}
		// 体积
		else if( EnuFeeUnit.UNIT_VOLUME_M3.equals(unit) ){
			Double priceDouble = price * (sku.getVolume() == null ? 0: sku.getVolume());
			return DoubleUtil.round(priceDouble, 4);
		}
		// PS
		else if( EnuFeeUnit.UNIT_PS.equals(unit) ){
			return DoubleUtil.round(price, 4);
		}
		// CS
		else if( EnuFeeUnit.UNIT_CS.equals(unit) ){
			Double csInDouble = sku.getProperties().getPackageInfo().getP2000().getCoefficient();
			if( csInDouble == null || csInDouble < 1D )
				csInDouble = 1D;
			Double priceDouble = price / csInDouble;
			return DoubleUtil.round(priceDouble, 4);
		}
		else{
			return 0D;
		}
	}
	
	private FeeType getFeeType( Owner owner, Sku sku, String feeType ){
		
		StringBuffer hqlString = new StringBuffer(" from FeeType ft where ft.disabled = false and ft.owner.id = :ownerId ");
		hqlString.append( " and ft.processType = :feeType " );
		hqlString.append( " and (ft.sku.id = :skuId or ft.sku.id is null ) " );
		hqlString.append( " and (ft.tempDiv = :tempDiv or ft.tempDiv is null )" );
		hqlString.append( " and (ft.stockDiv = :stockDiv or ft.stockDiv is null )" );
		hqlString.append( " order by ft.priority" );
		
		List<FeeType> feeTypes = commonDao.findByQuery(hqlString.toString(), 
				new String[]{"ownerId", "feeType", "skuId", "tempDiv", "stockDiv"}, 
				new Object[]{owner.getId(), feeType, sku.getId(), sku.getTempDiv(), sku.getStockDiv()});
		
		if( feeTypes != null && feeTypes.size() > 0 ){
			return feeTypes.get(0);
		}
		
		return null;
	}
	
	@Override
	public void editRequest(RequestReportHead head) {
		// 对象月（201306）
		Integer targetMonth = head.getTargetMonth();
		// 结算日（15）
		Integer accountDay = head.getAccountDateDiv().intValue();

		// 结算周期的开始结束日期
		Date[] fromToDaysDates = getFromToDays(targetMonth, accountDay);
		head.setDayFrom(fromToDaysDates[0]);
		head.setDayTo(fromToDaysDates[1]);

		// 3期的节点
		Date[][] term3Days = setPartDays3Term(targetMonth, accountDay);
		head.setDayF1(term3Days[0][0]);
		head.setDayT1(term3Days[1][0]);
		head.setDayF2(term3Days[0][1]);
		head.setDayT2(term3Days[1][1]);
		head.setDayF3(term3Days[0][2]);
		head.setDayT3(term3Days[1][2]);

		// 2期的节点
		Date[][] term2Days = setPartDays2Term(targetMonth, accountDay);
		head.setTerm21F(term2Days[0][0]);
		head.setTerm21T(term2Days[1][0]);
		head.setTerm22F(term2Days[0][1]);
		head.setTerm22T(term2Days[1][1]);

		if (head.isNew()) {
			head.setStatus(EnuRequestReportStatus.STATUS100);
			head.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		} else {
			head.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		getCommonDao().store(head);
	}

	private static Date[] getFromToDays(Integer targetMonth, Integer accountDay) {
		// 对象月的最后一天
		Integer lastDay = DateUtil.getMaximumDay(
				String.valueOf(targetMonth / 100),
				String.valueOf(targetMonth % 100));

		// 对象月的末日
		Date endDay = DateUtil.getDate(
				String.valueOf(targetMonth * 100 + accountDay),
				DateUtil.PURE_DATE_FORMAT);
		if (accountDay == 31 && lastDay < accountDay) {
			endDay = DateUtil.getDate(
					String.valueOf(targetMonth * 100 + lastDay),
					DateUtil.PURE_DATE_FORMAT);
		}

		// 对象月的开始日
		Date startDay = DateUtil.getDate(String.valueOf(targetMonth * 100 + 1),
				DateUtil.PURE_DATE_FORMAT);
		Integer lastMonth = targetMonth - 1;
		if (lastMonth % 100 == 0) {
			lastMonth = (lastMonth / 100 - 1) * 100 + 12;
		}
		if (accountDay == 15) {
			startDay = DateUtil.getDate(String.valueOf(lastMonth * 100 + 16),
					DateUtil.PURE_DATE_FORMAT);
		} else if (accountDay == 20) {
			startDay = DateUtil.getDate(String.valueOf(lastMonth * 100 + 21),
					DateUtil.PURE_DATE_FORMAT);
		}

		return new Date[] { startDay, endDay };
	}

	// 3期
	private static Date[][] setPartDays3Term(Integer targetMonth,
			Integer accountDay) {
		Date[][] result = new Date[2][];

		Date[] fromToDates = getFromToDays(targetMonth, accountDay);
		Date startDay = fromToDates[0];
		Date endDay = fromToDates[1];
		Date[] startDays = new Date[] { startDay, null, null };
		Date[] endDays = new Date[] { null, null, endDay };

		result[0] = startDays;
		result[1] = endDays;

		// ２０締め
		if (accountDay == 20) {
			// x
			endDays[0] = DateUtil.nextNDay(endDay, -20);
			// 10
			startDays[1] = DateUtil.nextNDay(endDay, -19);
			endDays[1] = DateUtil.nextNDay(endDay, -10);
			// 10
			startDays[2] = DateUtil.nextNDay(endDay, -9);
		}
		// １５締め
		else if (accountDay == 15) {
			// 10
			endDays[0] = DateUtil.nextNDay(startDay, 9);
			// x
			startDays[1] = DateUtil.nextNDay(startDay, 10);
			endDays[1] = DateUtil.nextNDay(endDay, -10);
			// 10
			startDays[2] = DateUtil.nextNDay(endDay, -9);
		}
		// ３１締め
		else {
			// 10
			endDays[0] = DateUtil.nextNDay(startDay, 9);
			// 10
			startDays[1] = DateUtil.nextNDay(startDay, 10);
			endDays[1] = DateUtil.nextNDay(startDay, 19);
			// x
			startDays[2] = DateUtil.nextNDay(startDay, 20);
		}

		return result;
	}

	// 2期
	private static Date[][] setPartDays2Term(Integer targetMonth,
			Integer accountDay) {
		Date[][] result = new Date[2][];

		Date[] fromToDates = getFromToDays(targetMonth, accountDay);
		Date startDay = fromToDates[0];
		Date endDay = fromToDates[1];
		Date[] startDays = new Date[] { startDay, null };
		Date[] endDays = new Date[] { null, endDay };
		result[0] = startDays;
		result[1] = endDays;

		// １５締め
		if (accountDay == 15) {
			startDays[1] = DateUtil.getDate(
					String.valueOf(targetMonth * 100 + 1),
					DateUtil.PURE_DATE_FORMAT);
			endDays[0] = DateUtil.nextNDay(startDays[1], -1);
		} else {
			endDays[0] = DateUtil.nextNDay(startDay, 14);
			startDays[1] = DateUtil.nextNDay(startDay, 15);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.core.scpwms.server.service.report.RequestReportManager#
	 * deleteRequestDetail(java.util.List)
	 */
	@Override
	public void deleteRequestDetail(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				RequestReportHead head = getCommonDao().load(RequestReportHead.class, id);
				if (head != null) {
					// 删除原来的数据
					commonDao.executeByNamed("deleteRequestReportDetail", new String[] { "headId" }, new Object[] { head.getId() });

					head.setStatus(EnuRequestReportStatus.STATUS100);
					head.setSumAmount(0D);
					head.setProcessDate(null);
					head.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
					commonDao.store(head);
				}
			}
		}

	}

}
