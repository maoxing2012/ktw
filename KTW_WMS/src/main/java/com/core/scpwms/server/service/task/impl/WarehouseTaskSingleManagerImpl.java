/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WarehouseTaskManagerImpl.java
 */

package com.core.scpwms.server.service.task.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.supercsv.prefs.CsvPreference;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.imports.FileImport;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.constant.WmsConstant4Ktw;
import com.core.scpwms.server.enumerate.EnuCaseType;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryDetailStatus;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryStatus;
import com.core.scpwms.server.enumerate.EnuWarehouseTaskSingleStatus;
import com.core.scpwms.server.enumerate.EnuWaveStatus;
import com.core.scpwms.server.model.file.FileImport4Wms;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.task.WarehouseTaskSingle;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.imports.impl.ns.NsObdConstant;
import com.core.scpwms.server.service.task.WarehouseTaskManager;
import com.core.scpwms.server.service.task.WarehouseTaskSingleManager;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.FileUtil4Jp;
import com.core.scpwms.server.util.QuantityUtil;
import com.core.scpwms.server.util.StringUtil4Jp;

/**
 * 個口決定
 * @author mousachi
 *
 */
@SuppressWarnings("all")
public class WarehouseTaskSingleManagerImpl extends DefaultBaseManager implements WarehouseTaskSingleManager, NsObdConstant {
	
	private OrderStatusHelper orderStatusHelper;
	private BCManager bcManager;
	private WarehouseTaskManager warehouseTaskManager;
	
	public OrderStatusHelper getOrderStatusHelper() {
		return orderStatusHelper;
	}

	public void setOrderStatusHelper(OrderStatusHelper orderStatusHelper) {
		this.orderStatusHelper = orderStatusHelper;
	}
	
	public BCManager getBcManager() {
		return bcManager;
	}

	public void setBcManager(BCManager bcManager) {
		this.bcManager = bcManager;
	}
	
	public WarehouseTaskManager getWarehouseTaskManager() {
		return warehouseTaskManager;
	}

	public void setWarehouseTaskManager(WarehouseTaskManager warehouseTaskManager) {
		this.warehouseTaskManager = warehouseTaskManager;
	}

	@Override
	public void createWarehouseTaskSingle(List<Long> waveDocIds) {
		for( Long waveDocId : waveDocIds ){
			WaveDoc waveDoc = commonDao.load(WaveDoc.class, waveDocId);
			
			if( !EnuWaveStatus.STATUS210.equals(waveDoc.getStatus()) && !EnuWaveStatus.STATUS220.equals(waveDoc.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			// WT按照 承运商->温度带->専用伝票番号->伝票情報->担当営業所コード->納品先コード->代表伝票番号->受注No->OBD番号
			StringBuffer hql = new StringBuffer( " select wt.obdDetail.obd.id, wt.id from WarehouseTask wt where wt.obdDetail.obd.waveDoc.id = :waveDocId " );
			hql.append(" order by wt.obdDetail.obd.carrier.code, ");//承运商
			hql.append("wt.obdDetail.sku.tempDiv, ");//温度带
			hql.append("wt.obdDetail.obd.extString1, ");//専用伝票番号
			hql.append("wt.obdDetail.obd.extString2, ");//伝票情報
			hql.append("wt.obdDetail.obd.extString3, ");//担当営業所コード
			hql.append("wt.obdDetail.obd.customer.code, ");//納品先コード
			hql.append("wt.obdDetail.obd.relatedBill2, ");//代表伝票番号
			hql.append("wt.obdDetail.obd.relatedBill1, ");//受注No
			hql.append("wt.obdDetail.obd.obdNumber");//OBD番号
			
			List<Object[]> wtObdIds = commonDao.findByQuery(hql.toString(), "waveDocId", waveDocId);
			
			if( wtObdIds == null || wtObdIds.size() == 0 )
				return ;
			
			// 同OBD_ID的WT为一组，生成个口
			List<Long> groupWtIds = null;
			Long tempObdId = 0L;
			
			for( Object[] wtObdId : wtObdIds ){
				Long obdId = (Long)wtObdId[0];
				Long wtId = (Long)wtObdId[1];
				
				if( !tempObdId.equals(obdId) ){
					if( groupWtIds != null && groupWtIds.size() > 0 ){
						createSingle(groupWtIds, tempObdId, waveDoc);
					}
					
					groupWtIds = new ArrayList<Long>();
					tempObdId = obdId;
				}
				groupWtIds.add(wtId);
			}
 			
			if( groupWtIds != null && groupWtIds.size() > 0 ){
				createSingle(groupWtIds, tempObdId, waveDoc);
			}
			
			orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS250);
		}
	}
	
	private void createSingle( List<Long> wtIds, Long obdId, WaveDoc waveDoc ){
		// -------------第一轮-----------------------
		// 同商品，同保质期，同库位，合计数量
		StringBuffer sql = new StringBuffer();
		sql.append(" select sku.pack_flg_4ns as packFlg, ");
		sql.append(" wt.quant_id as quantId,");
		sql.append(" quant.displot as displot,");
		sql.append(" sku.code as skuCode,");
		sql.append(" wt.bin_id as binId,");
		sql.append(" bin.code as binCode,");
		sql.append(" p2000.coefficient as csIn,");
		sql.append(" p2100.coefficient as blIn,");
		sql.append(" sum(wt.plan_qty - wt.singled_qty)");
		sql.append(" from wms_warehouse_task wt");
		sql.append(" left join wms_outbound_delivery_detail obdDetail ");
		sql.append(" on wt.obd_detail_id = obdDetail.id");
		sql.append(" left join wms_outbound_delivery obd");
		sql.append(" on obdDetail.obd_id = obd.id");
		sql.append(" left join wms_sku sku on obdDetail.sku_id = sku.id");
		sql.append(" left join wms_package_detail p2000 on p2000.package_id = sku.pack_info_id and p2000.package_level = 'PK2000'");
		sql.append(" left join wms_package_detail p2100 on p2100.package_id = sku.pack_info_id and p2100.package_level = 'PK2100'");
		sql.append(" left join wms_bin bin on wt.bin_id = bin.id");
		sql.append(" left join wms_quant quant on wt.quant_id = quant.id");
		sql.append(" where obd.id = ?");
		sql.append(" group by");
		sql.append(" sku.pack_flg_4ns,");
		sql.append(" wt.quant_id,");
		sql.append(" quant.displot,");
		sql.append(" wt.bin_id,");
		sql.append(" p2000.coefficient,");
		sql.append(" p2100.coefficient,");
		sql.append(" bin.code,");
		sql.append(" sku.code");
		sql.append(" order by");
		sql.append(" sku.pack_flg_4ns desc,");//--同捆不可的排在前面
		sql.append(" bin.code, ");//--按拣货顺序排序
		sql.append(" to_number(sku.code,'999999999999')");// NS		
		List<Object> wtSumInfos = commonDao.findBySqlQuery(sql.toString(), new Object[]{obdId});
		
		String singleGroupSeq = bcManager.getSingleGroupNo(waveDoc.getId());
		long inx = 0L;
		
		// 整箱
		for( Object wtSumInfoObd : wtSumInfos){
			Object[] wtSumInfo = (Object[])wtSumInfoObd;
			Long packFlg4NS = ((BigInteger)wtSumInfo[0]).longValue();
			Long quantId = ((BigInteger)wtSumInfo[1]).longValue();
			String skuCode = (String)wtSumInfo[3];
			Long binId = ((BigInteger)wtSumInfo[4]).longValue();
			Double csIn = (Double)wtSumInfo[6];
			Double blIn = (Double)wtSumInfo[7];
			Double qty = (Double)wtSumInfo[8];
			
			int caseQty = 0;
			if( csIn != null && csIn.doubleValue() > 0D ){
				caseQty = qty.intValue() / csIn.intValue();
			}
			
			if( caseQty > 0 ){
				for( int i = 0 ; i < caseQty ; i++ ){
					// 整箱个口固定
					createCase( quantId, binId, csIn, obdId, waveDoc, singleGroupSeq, ++inx, EnuCaseType.CS_CASE );// 整箱
				}
			}
		}
		
		String hql = " select max(wt.singledQty) from WarehouseTask wt where wt.obdDetail.obd.id = :obdId ";
		commonDao.findByQuery(hql, "obdId", obdId);
		
		// ------------------第二轮-------------------------
		// 剩余可以同捆的商品，按照BL单位编号，商品编号排序
		StringBuffer sql2 = new StringBuffer();
		sql2.append(" select sku.pack_flg_4ns as packFlg, ");
		sql2.append(" wt.quant_id as quantId,");
		sql2.append(" quant.displot as displot,");
		sql2.append(" sku.code as skuCode,");
		sql2.append(" wt.bin_id as binId,");
		sql2.append(" bin.code as binCode,");
		sql2.append(" p2000.coefficient as csIn,");
		sql2.append(" p2100.coefficient as blIn,");
		sql2.append(" sum(wt.plan_qty - wt.singled_qty)");
		sql2.append(" from wms_warehouse_task wt");
		sql2.append(" left join wms_outbound_delivery_detail obdDetail ");
		sql2.append(" on wt.obd_detail_id = obdDetail.id");
		sql2.append(" left join wms_outbound_delivery obd");
		sql2.append(" on obdDetail.obd_id = obd.id");
		sql2.append(" left join wms_sku sku on obdDetail.sku_id = sku.id");
		sql2.append(" left join wms_package_detail p2000 on p2000.package_id = sku.pack_info_id and p2000.package_level = 'PK2000'");
		sql2.append(" left join wms_package_detail p2100 on p2100.package_id = sku.pack_info_id and p2100.package_level = 'PK2100'");
		sql2.append(" left join wms_bin bin on wt.bin_id = bin.id");
		sql2.append(" left join wms_quant quant on wt.quant_id = quant.id");
		sql2.append(" where obd.id = ? and (wt.plan_qty - wt.singled_qty) > 0");
		sql2.append(" group by");
		sql2.append(" sku.pack_flg_4ns,");
		sql2.append(" wt.quant_id,");
		sql2.append(" quant.displot,");
		sql2.append(" wt.bin_id,");
		sql2.append(" p2000.coefficient,");
		sql2.append(" p2100.coefficient,");
		sql2.append(" p2100.code,");
		sql2.append(" bin.code,");
		sql2.append(" sku.code");
		sql2.append(" order by");
		sql2.append(" sku.pack_flg_4ns desc,");//--同捆不可的排在前面
		sql2.append(" p2100.code, ");//--按照BL单位编号
		sql2.append(" to_number(sku.code,'999999999999')");// --NS商品编号排序
		List<Object[]> wtSumInfos2 = commonDao.findBySqlQuery(sql2.toString(), new Object[]{obdId});
		
		List<Object[]> skuInfos = new ArrayList<Object[]>();
		double sumedVolume = 0D;
		double sumedQty = 0D;
		int skuCount = 0;
		String lastSku = "";
		Double lastBlIn = null;
		if( wtSumInfos2 != null && wtSumInfos2.size() > 0 ){
			for( Object[] wtSumInfo2 : wtSumInfos2 ){
				Long packFlg4NS = ((BigInteger)wtSumInfo2[0]).longValue();
				Long quantId = ((BigInteger)wtSumInfo2[1]).longValue();
				String skuCode = (String)wtSumInfo2[3];
				Long binId = ((BigInteger)wtSumInfo2[4]).longValue();
				Double csIn = (Double)wtSumInfo2[6];
				Double blIn = (Double)wtSumInfo2[7];
				Double qty = (Double)wtSumInfo2[8];
				
				if( packFlg4NS == null ){
					throw new BusinessException(skuCode + "この商品、同梱フラグが設定されていません。設定してください。");
				}
				// 同捆不可的个口固定
				if( packFlg4NS.longValue() == 2L){
					int caseQty = 0;
					int blQty = 0;
					int psQty = 0;
					
					if( csIn != null && csIn.doubleValue() > 0D ){
						caseQty = qty.intValue() / csIn.intValue();
					}
					if( blIn != null && blIn.doubleValue() > 0D ){
						blQty = ( qty.intValue() - caseQty * csIn.intValue() ) / blIn.intValue();
					}
					psQty = qty.intValue() - caseQty * csIn.intValue() - blQty * blIn.intValue();
					
					// 中箱个口固定
					if( blQty == 1 ){
						createCase( quantId, binId, blIn, obdId, waveDoc, singleGroupSeq, ++inx, EnuCaseType.BL_CASE );// 中箱
					}
					// 散件箱
					else if( blQty > 1 ){
						createCase( quantId, binId, new Double(qty.intValue() - caseQty * csIn.intValue()), obdId, waveDoc, singleGroupSeq, ++inx, EnuCaseType.PS_CASE );// 散箱
					}
					
					// 散件箱个口固定
					if( psQty > 0 ){
						createCase( quantId, binId, new Double( psQty ), obdId, waveDoc, singleGroupSeq, ++inx, EnuCaseType.PS_CASE );// 散箱
					}
				}
				// 同捆可的个口固定
				else{
					double volume = 0;
					if( csIn != null && csIn.intValue() >= 1 ){
						volume = DoubleUtil.div(qty, csIn, 2);// 保留小数点以后2位，四舍五入
					}
					
					if( !lastSku.equals(skuCode) ){
						skuCount += 1;
						lastSku = skuCode;
						lastBlIn = blIn;
					}
					
					sumedVolume += volume;
					sumedQty += qty;
					
					sumedVolume = DoubleUtil.round(sumedVolume, 2);
					
					if( sumedVolume > 1D || skuCount > 5){
						long caseType = EnuCaseType.PS_CASE;
						createPsCase(skuInfos, obdId, waveDoc, singleGroupSeq, ++inx, caseType);
						
						skuInfos = new ArrayList<Object[]>();
						sumedVolume = 0D;
						sumedQty = 0D;
						skuCount = 0;
						lastSku = "";
						lastBlIn = null;
						
						skuInfos.add(new Object[]{quantId, binId});
						double thisVolume = 0;
						if( csIn != null && csIn.intValue() >= 1 ){
							thisVolume = DoubleUtil.div(qty, csIn, 2);// 保留小数点以后2位，四舍五入
						}
						
						if( !lastSku.equals(skuCode) ){
							skuCount += 1;
							lastSku = skuCode;
							lastBlIn = blIn;
						}
						
						sumedVolume += thisVolume;
						sumedQty += qty;
						
					}
					else{
						skuInfos.add(new Object[]{quantId, binId});
					}
				}
			}
			
			if( skuInfos.size() > 0 ){
				long caseType = EnuCaseType.PS_CASE;
				createPsCase(skuInfos, obdId, waveDoc, singleGroupSeq, ++inx, caseType);
			}
		}
		
		// 重新排号
		String hql1 = "from WarehouseTaskSingle wts where wts.wt.obdDetail.obd.id = :obdId order by wts.caseType, wts.inx ";
		List<WarehouseTaskSingle> wtsList = commonDao.findByQuery(hql1, "obdId", obdId);
		long lastInx = -1L;
		long newInx = 0L;
		
		String transSeq = getTransportSeq(obdId);
		String[] transBoxSeqs = new String[]{"", ""};
		for( WarehouseTaskSingle wts : wtsList ){
			if( lastInx != wts.getInx().longValue() ){
				lastInx = wts.getInx().longValue();
				newInx++;
				transBoxSeqs = setTransBoxSequence(obdId, transSeq, wts.getRelatedBill3(), newInx);
			}
			wts.setInx(newInx);
			// 送り状NO&箱号
			wts.setRelatedBill1(transBoxSeqs[0]);// 送り状NO
			wts.setRelatedBill2(transBoxSeqs[1]);// 箱号
		}
		
	}
	
	private void createPsCase( List<Object[]> skuInfos, Long obdId, WaveDoc wv, String singleGroupSeq, long inx, Long caseType ){
		for( Object[] skuInfo : skuInfos ){
			String hql = " select wt.id from WarehouseTask wt "
					+ " where wt.obdDetail.obd.id = :obdId "
					+ " and wt.invInfo.quant.id = :quantId"
					+ " and wt.invInfo.bin.id= :binId"
					+ " and (wt.planQty - wt.singledQty > 0)"
					+ " order by wt.invInfo.inboundDate, wt.invInfo.trackSeq";
			List<Long> wtIds = commonDao.findByQuery(hql, new String[]{"obdId", "quantId", "binId"}, 
					new Object[]{ obdId, skuInfo[0], skuInfo[1] });
			
			for( Long wtId : wtIds ){
				WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);
				
				WarehouseTaskSingle ts = new WarehouseTaskSingle();
				ts.setObdId(obdId);
				ts.setWaveDoc(wv);
				ts.setWt(wt);
				ts.setRelatedBill3(singleGroupSeq);// Group号
				ts.setInx(inx);
				ts.setCaseType(caseType);
				ts.setQty(wt.getUnSingledQty());
				commonDao.store(ts);
				
				wt.addSingledQty(wt.getUnSingledQty());
			}
		}
	}

	private void createCase( Long quantId, Long binId, Double qty, Long obdId, WaveDoc wv, String singleGroupSeq, long inx, Long caseType ){
		String hql = " select wt.id from WarehouseTask wt "
				+ " where wt.obdDetail.obd.id = :obdId "
				+ " and wt.invInfo.quant.id = :quantId"
				+ " and wt.invInfo.bin.id= :binId"
				+ " and (wt.planQty - wt.singledQty > 0)"
				+ " order by wt.invInfo.inboundDate, wt.invInfo.trackSeq";
		
		List<Long> wtIds = commonDao.findByQuery(hql, new String[]{"obdId", "quantId", "binId"}, new Object[]{obdId, quantId, binId});
		
		Double toSingleQty = qty;
		
		for( Long wtId : wtIds ){
			if( toSingleQty <= 0D )
				break;
			
			WarehouseTask wt = commonDao.load(WarehouseTask.class, wtId);
			
			Double thisSingleQty = toSingleQty;
			if( wt.getUnSingledQty() < thisSingleQty ){
				thisSingleQty = wt.getUnSingledQty();
			}
			
			WarehouseTaskSingle ts = new WarehouseTaskSingle();
			ts.setObdId(obdId);
			ts.setWaveDoc(wv);
			ts.setWt(wt);
			ts.setRelatedBill3(singleGroupSeq);// Group号
			ts.setInx(inx);
			ts.setCaseType(caseType);
			ts.setQty(thisSingleQty);
			commonDao.store(ts);
			
			wt.addSingledQty(thisSingleQty);
			toSingleQty = toSingleQty - thisSingleQty;
		}
	}
	
	private String[] setTransBoxSequence( Long obdId, String transSeq, String groupId, Long inx  ){
		String[] result = new String[]{"", ""};
		
		OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
		Long carrierId = obd.getCarrier() == null ? -1L : obd.getCarrier().getId();
		String carrierCode = obd.getCarrier() == null ? "" : obd.getCarrier().getCode();
		String custBillNo = obd.getRelatedBill2();//代表伝票ＮＯ
		String carrierFrom = obd.getCarrier() == null ? "" : obd.getCarrier().getExtString1();
		String carrierTo = obd.getCarrier() == null ? "" : obd.getCarrier().getExtString2();
		
		
		// ヤマト　送り状No = 個口番号
		if( WmsConstant4Ktw.CARRIER_YAMATO.equals(carrierCode) || WmsConstant4Ktw.CARRIER_YAMATO_COOL.equals(carrierCode) ){
			String newTransSeq = getBoxSequence(carrierId, carrierCode, carrierFrom, carrierTo, transSeq, groupId, inx);
			result[0] = newTransSeq;
			result[1] = newTransSeq;
		}
		// 福山
		else if( WmsConstant4Ktw.CARRIER_FUKUYAMA.equals(carrierCode) ){
			String boxSeq = getBoxSequence(carrierId, carrierCode, carrierFrom, carrierTo, transSeq, groupId, inx);
			
			result[0] = transSeq;
			result[1] = boxSeq;
		}
		// そのた
		else{
			String boxSeq = getBoxSequence(carrierId, carrierCode, carrierFrom, carrierTo, transSeq, groupId, inx);
			
			result[0] = transSeq;
			result[1] = boxSeq;
		}
		
		return result;
	}
	
	// 根据各个承运商的需要生成送り状NO
	private String getTransportSeq(Long obdId ){
		OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
		Long carrierId = obd.getCarrier() == null ? -1L : obd.getCarrier().getId();
		String carrierCode = obd.getCarrier() == null ? "" : obd.getCarrier().getCode();
		String custBillNo = obd.getRelatedBill2() == null ? (obd.getRelatedBill1() == null ? 
				obd.getObdNumber().substring(obd.getObdNumber().length() - 11, obd.getObdNumber().length()) : obd.getRelatedBill1()) : obd.getRelatedBill2();//代表伝票ＮＯ
		String carrierFrom = obd.getCarrier() == null ? "" : obd.getCarrier().getExtString1();
		String carrierTo = obd.getCarrier() == null ? "" : obd.getCarrier().getExtString2();
		
		// ヤマト　送り状No = 個口番号
		if( WmsConstant4Ktw.CARRIER_YAMATO.equals(carrierCode) || WmsConstant4Ktw.CARRIER_YAMATO_COOL.equals(carrierCode) ){
			return "";
		}
		// 福山　送り状No
		else if( WmsConstant4Ktw.CARRIER_FUKUYAMA.equals(carrierCode) ){
			return bcManager.getFukuyamaSequence(carrierFrom,carrierTo);
		}
		// そのた
		else{
			// 3桁の運送便コード　+ "-"　+ 代表伝票Ｎｏ
			String carrierCode3Digit = StringUtil4Jp.fillFormat(carrierCode, 3, '0', 2);
			return carrierCode3Digit + "-" + custBillNo;
		}
	}
	
	// 根据各个承运商的需要生成箱号
	private String getBoxSequence( Long carrierId, String carrierCode, String carrierFrom, String carrierTo, String transSeq, String groupId, Long inx  ){
		// ヤマト　個口番号＝系统番号
		if( WmsConstant4Ktw.CARRIER_YAMATO.equals(carrierCode) || WmsConstant4Ktw.CARRIER_YAMATO_COOL.equals(carrierCode) ){
			return bcManager.getYamatoSequence(carrierFrom,carrierTo);
		}
		// 福山　個口番号＝送り状No+3桁inx
		else if( WmsConstant4Ktw.CARRIER_FUKUYAMA.equals(carrierCode) ){
			String inxStr = String.valueOf(inx);
			inxStr = StringUtils.repeat("0", 3 - inxStr.length()) + inxStr;
			return transSeq + inxStr;
		}
		// それ以外　個口番号＝グループＩＤ+3桁inx
		else{
			String inxStr = String.valueOf(inx);
			inxStr = StringUtils.repeat("0", 3 - inxStr.length()) + inxStr;
			return groupId + inxStr;
		}
	}
	
	@Override
	public void cancelWavehouseTaskSingle(List<Long> waveDocIds) {
		for( Long waveDocId : waveDocIds ){
			WaveDoc waveDoc = commonDao.load(WaveDoc.class, waveDocId);
			
			if( !EnuWaveStatus.STATUS250.equals(waveDoc.getStatus())){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			// WT的Singed数设置0
			String sql2 = " update wms_warehouse_task set singled_qty = 0 "
					+ "where id in "
					+ "( select distinct s.WT_ID from WMS_WAREHOUSE_TASK_SINGLE s where s.WAVE_DOC_ID = ? ) ";
			commonDao.executeSqlQuery(sql2, new Object[]{waveDocId});
			
			// 删除所有个口信息
			String sql = " delete from WMS_WAREHOUSE_TASK_SINGLE s where s.WAVE_DOC_ID = ? ";
			commonDao.executeSqlQuery(sql, new Object[]{waveDocId});
			
			if( waveDoc.getUnAllocateQty() > 0 ){
				// 部分分配
				orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS210);
			}
			else{
				// 分配完成
				orderStatusHelper.changeStatus(waveDoc, EnuWaveStatus.STATUS220);
			}
		}
	}

	@Override
	public void pickExecute(List<Long> wtsIds, Long laborId) {
		for( Long wtsId : wtsIds ){
			WarehouseTaskSingle wts = commonDao.load(WarehouseTaskSingle.class, wtsId);
			
			if( wts.getStatus() != null && !EnuWarehouseTaskSingleStatus.STATUS000.equals(wts.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			// 执行拣货
			warehouseTaskManager.pickTaskExecute(wts.getWt().getId(), wts.getQty(), wts.getRelatedBill2(), laborId);
			
			// 拣货数
			wts.setPickQty(wts.getQty());
			// 状态
			wts.setStatus(EnuWarehouseTaskSingleStatus.STATUS100);
			// 拣货人
			wts.setLabor(commonDao.load(Labor.class, laborId));
			// 拣货时间
			wts.setOperateTime(new Date());
			commonDao.store(wts);
		}
	}

	@Override
	public void checkExecute(List<Long> wtsIds, Long laborId) {
		Set<OutboundDelivery> obdSet = new HashSet<OutboundDelivery>();
		Set<OutboundDeliveryDetail> obdDetailSet = new HashSet<OutboundDeliveryDetail>();
		
		for( Long wtsId : wtsIds ){
			WarehouseTaskSingle wts = commonDao.load(WarehouseTaskSingle.class, wtsId);
			
			if( wts.getStatus() != null && !EnuWarehouseTaskSingleStatus.STATUS100.equals(wts.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			wts.getWt().getObdDetail().addPackedQty(wts.getPickQty());
			obdDetailSet.add(wts.getWt().getObdDetail());
			obdSet.add(wts.getWt().getObdDetail().getObd());
			
			// 拣货数
			wts.setCheckQty(wts.getPickQty());
			// 状态
			wts.setStatus(EnuWarehouseTaskSingleStatus.STATUS200);
			// 拣货人
			wts.setCheckLabor(commonDao.load(Labor.class, laborId));
			// 拣货时间
			wts.setCheckTime(new Date());
			commonDao.store(wts);
		}
		
		// 修改出库单明细的状态
		for( OutboundDeliveryDetail obdDetail : obdDetailSet ){
			if( EnuOutboundDeliveryDetailStatus.STATUS840 > obdDetail.getStatus().longValue() && obdDetail.getPackedQty() > 0D ){
				obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS840);// 复合中
			}
			
			if( EnuOutboundDeliveryDetailStatus.STATUS840 == obdDetail.getStatus().longValue() 
					&& obdDetail.getAllocateUnPickUpQty() <= 0D && obdDetail.getPickedUnPackedQty() <= 0D ){
				obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS850);// 复合完成
			}
		}
		
		// 修改出库单的状态
		for( OutboundDelivery obd : obdSet ){
			if( EnuOutboundDeliveryStatus.STATUS840 > obd.getStatus().longValue() && obd.getPackedQty() > 0D ){
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS840);// 复合中
			}
			
			if( EnuOutboundDeliveryStatus.STATUS840 == obd.getStatus().longValue() 
					&& obd.getAllocatedUnPickupQty() <= 0D && obd.getPickedUnPackedQty() <= 0D ){
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS850);// 复合完成
			}
		}
	}
	

	@Override
	public void cancelCheck(List<Long> wtsIds) {
		Set<OutboundDelivery> obdSet = new HashSet<OutboundDelivery>();
		Set<OutboundDeliveryDetail> obdDetailSet = new HashSet<OutboundDeliveryDetail>();
		
		for( Long wtsId : wtsIds ){
			WarehouseTaskSingle wts = commonDao.load(WarehouseTaskSingle.class, wtsId);
			
			if( wts.getStatus() != null && !EnuWarehouseTaskSingleStatus.STATUS200.equals(wts.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			wts.getWt().getObdDetail().removePackedQty(wts.getPickQty());
			obdDetailSet.add(wts.getWt().getObdDetail());
			obdSet.add(wts.getWt().getObdDetail().getObd());
			
			// 检品数
			wts.setCheckQty(0D);
			wts.setStatus(EnuWarehouseTaskSingleStatus.STATUS100);
			wts.setCheckLabor(null);
			wts.setCheckTime(null);
			commonDao.store(wts);
		}
		
		// 修改出库单明细的状态
		for( OutboundDeliveryDetail obdDetail : obdDetailSet ){
			if( EnuOutboundDeliveryDetailStatus.STATUS850.longValue() == obdDetail.getStatus().longValue() && obdDetail.getPickedUnPackedQty() > 0 ){
				obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS840);// 复合中
			}
			
			if( EnuOutboundDeliveryDetailStatus.STATUS840 == obdDetail.getStatus().longValue() && obdDetail.getPackedQty() <= 0D ){
				if( obdDetail.getAllocateUnPickUpQty() <= 0D ){
					obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS800);// 拣货完成
				}
				else{
					obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS710);// 拣货中
				}
			}
		}
		
		// 修改出库单的状态
		for( OutboundDelivery obd : obdSet ){
			if( EnuOutboundDeliveryStatus.STATUS850.longValue() == obd.getStatus().longValue() && obd.getPickedUnPackedQty() > 0D ){
				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS840);// 复合中
			}
			
			if( EnuOutboundDeliveryStatus.STATUS840 == obd.getStatus().longValue() && obd.getPackedQty() <= 0D ){
				if( obd.getAllocatedUnPickupQty() <= 0 ){
					orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS800);// 拣货完成
				}
				else{
					orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS710);// 拣货中
				}
			}
		}
	}

	
	private String getSql(){
		StringBuilder sql = new StringBuilder();
		sql.append(" select ");
		sql.append(" wv.id as wvId ");//0
		sql.append(" ,obd.id as obdId ");//1
		sql.append(" ,obd.related_bill1 as custBillNo ");//2
		sql.append(" ,obd.related_bill2 as minDeliveryNo  ");//3
		sql.append(" ,obd.related_bill3 as allDeliveryNo ");//4
		sql.append(" ,obd.ext_string1 as order_div ");//5
		sql.append(" ,wts.related_bii1 as okuriNo ");//6
		sql.append(" ,wts.related_bii3 as groupId ");//7
		sql.append(" ,wts.related_bii2 as boxNo ");//8
		sql.append(" ,wts.case_type ");//9
		sql.append(" ,wts.inx as inx");//10
		sql.append(" ,sku.id as skuId");//11
		sql.append(" ,sku.code as skuCode");//12
		sql.append(" ,sum(wts.qty) as qty ");//13
		sql.append(" ,min(obdDetail.ext_string1) infos ");//14
		sql.append(" ,(select max(inx) from wms_warehouse_task_single wts where wts.obd_id = obd.id ) as maxInx ");//15
		sql.append(" ,(select pd.name from wms_package_detail pd where pd.package_level = 'PK1000' and pd.package_id = sku.pack_info_id) as psUnit  ");//16
		sql.append(" ,(select pd.name from wms_package_detail pd where pd.package_level = 'PK2100' and pd.package_id = sku.pack_info_id) as blUnit  ");//17
		sql.append(" ,(select pd.name from wms_package_detail pd where pd.package_level = 'PK2000' and pd.package_id = sku.pack_info_id) as csUnit  ");//18
		sql.append(" ,(select pd.coefficient from wms_package_detail pd where pd.package_level = 'PK2100' and pd.package_id = sku.pack_info_id) as blIn  ");//19
		sql.append(" ,(select pd.coefficient from wms_package_detail pd where pd.package_level = 'PK2000' and pd.package_id = sku.pack_info_id) as csIn  ");//20
		sql.append(" ,(select round(cast(sum(obdDetail.allocate_qty * coalesce(sku.gross_weight,0)) as numeric),0) ");
		sql.append("	from wms_outbound_delivery_detail obdDetail left join wms_sku sku on obdDetail.sku_id = sku.id ");
		sql.append("	where obdDetail.obd_id = obd.id  ) as weight  ");//21
		sql.append(" ,min(bin.code) as binCode");//22
		sql.append(" ,min(obdDetail.line_no) as lineNo ");//23
		sql.append(" from wms_warehouse_task_single wts  ");
		sql.append(" left join wms_warehouse_task wt on wts.wt_id = wt.id ");
		sql.append(" left join wms_bin bin on wt.bin_id = bin.id");
		sql.append(" left join wms_wave_doc wv on wts.wave_doc_id = wv.id ");
		sql.append(" left join wms_outbound_delivery_detail obdDetail on wt.obd_detail_id = obdDetail.id ");
		sql.append(" left join wms_outbound_delivery obd on obdDetail.obd_id = obd.id ");
		sql.append(" left join wms_quant quant on wt.quant_id = quant.id ");
		sql.append(" left join wms_sku sku on quant.sku = sku.id ");
		sql.append(" where wv.id = ? and obd.carrier_id = ?");
		sql.append(" group by ");
		sql.append(" wv.id ");
		sql.append(" ,obd.id ");
		sql.append(" ,obd.related_bill1  ");
		sql.append(" ,obd.related_bill2 ");
		sql.append(" ,obd.related_bill3 ");
		sql.append(" ,obd.ext_string1 ");
		sql.append(" ,wts.related_bii1 ");
		sql.append(" ,wts.related_bii3 ");
		sql.append(" ,wts.related_bii2 ");
		sql.append(" ,wts.case_type ");
		sql.append(" ,wts.inx ");
		sql.append(" ,sku.id ");
		sql.append(" ,sku.code ");
		sql.append(" order by ");
		sql.append(" (case when wts.inx = 1 then 1 else 2 end)  ");// 1号箱排最前面
		sql.append(" ,sku.temp_div ");// 温度帯(常温、冷蔵、冷凍)
		sql.append(" ,(case when wts.inx = 1 then obd.ext_string1 else '' end)  ");// 只有1号箱需要按照纳品书类型分
		sql.append(" ,(case when wts.case_type = 1 then 1 else 2 end) ");// 散箱->(中箱，外箱)
		
		//sql.append(" ,(case when wts.case_type >= 2 and sku.temp_div in (1,5) then min(bin.code) else '' end ) desc ");// 按箱拣货，常温，用库位排序倒序
		sql.append(" ,(case when wts.case_type >= 2 and sku.temp_div in (1,5) then min(bin.code) else '' end ) ");// 按箱拣货，常温，用库位排序
		sql.append(" ,(case when wts.case_type >= 2 and sku.temp_div not in (1,5) then min(bin.code) else '' end ) ");// 按箱拣货，非常温，用库位排序
		
		sql.append(" ,(case when wts.case_type >= 2 then sku.code else '' end ) ");// 按箱拣货，再用商品排序
		sql.append(" ,wts.case_type ");// 按个口的类型 散箱 -> 内箱->外箱
		sql.append(" ,wts.related_bii3 ");// GroupID
		sql.append(" ,wts.inx "); // 箱序号
		sql.append(" ,lineNo "); // 订单行号
		
		return sql.toString();
	}
	
	private Long createYamatoCsv( Long waveDocId, Long carrierId, String carrierCode ){
		WaveDoc waveDoc = commonDao.load(WaveDoc.class, waveDocId);
		
		List<Object[]> caseInfos = commonDao.findBySqlQuery(getSql(), new Object[]{waveDocId, carrierId});
		
		List<String[]> csvList = new ArrayList<String[]>( caseInfos.size() );
		String todayStr = DateUtil.getStringDate(new Date(), DateUtil.PURE_DATE_FORMAT);
		String caseNumber = "";
		List<Object[]> skuInfos = new ArrayList<Object[]>(5);
		Carrier carrier = commonDao.load(Carrier.class, carrierId);
		
		for( Object[] caseInfoObj : caseInfos ){
			String thisCaseNumber = (String)caseInfoObj[8];
			
			// 新箱
			if( !caseNumber.equals(thisCaseNumber) ){
				if(skuInfos.size() > 0){
					String[] csvLine = getYamatoCsvLine(skuInfos, todayStr, carrier);
					csvList.add(csvLine);
				}
				
				caseNumber = thisCaseNumber;
				skuInfos = new ArrayList<Object[]>(5);
			}
			skuInfos.add(caseInfoObj);
		}
		
		// 最后一箱
		if(skuInfos.size() > 0){
			String[] csvLine = getYamatoCsvLine(skuInfos, todayStr, carrier);
			csvList.add(csvLine);
		}
		
		// 生成文件
		return createCsvFile("yamato" + carrierCode, waveDoc.getWh(), waveDoc.getOwner(), csvList.toArray(new String[csvList.size()][40])) ;
	}
	
	private long createCsvFile(String prex, Warehouse wh, Owner owner, String[][] csvData ){
		// 生成文件
		String systemFileName = prex +"_" + DateUtil.getStringDate(new Date(), DateUtil.LONG_DATE_FORMAT) + ".CSV";
		String hql2 = "select value from GlobalParam where paramId = :paramId ";
		String fileBasePath = (String)commonDao.findByQueryUniqueResult(hql2, new String[]{"paramId"}, new Object[]{"tempFileDir"});
		String systemPath = fileBasePath + File.separator + "dataFile" + File.separator +  prex + File.separator;
		CsvPreference csvPre = CsvPreference.STANDARD_PREFERENCE;
		
		FileUtil4Jp.createMultiPath(systemPath);
		FileUtil4Jp.writeCSVFile(csvPre, csvData, systemPath + systemFileName, FileUtil4Jp.ENCODE_MS932);
		
		FileImport4Wms fileImport4Wms = new FileImport4Wms();
		fileImport4Wms.setWh(wh);
		fileImport4Wms.setOwner(owner);
		FileImport fileImport = new FileImport();
		fileImport4Wms.setFileImport(fileImport);
		fileImport.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		fileImport.setOrginalName(systemFileName);
		fileImport.setTargetName(systemPath + systemFileName);
		commonDao.store(fileImport);
		commonDao.store(fileImport4Wms);
		
		return fileImport.getId();
	}
	
	private String[] getYamatoCsvLine( List<Object[]> skuInfos, String todayStr, Carrier carrier ){
		Object[] caseInfoObj = skuInfos.get(0);
		String[] reverse = ((String)caseInfoObj[14]).split(",");
		
		int i = 0;
		String[] csvLine = new String[40];
		//1:運送依頼番号 20
		csvLine[i++] = StringUtil4Jp.getString((String)caseInfoObj[3], 20) ; //3:代表伝票No
		//2:運送送り状番号 12
		csvLine[i++] = StringUtil4Jp.getString((String)caseInfoObj[6], 12);//6:送り状No
		//3:集荷希望日 8
		csvLine[i++] = StringUtil4Jp.getString(reverse[24], 8);//【項番２５】出荷日
		//4:荷受人住所ｺｰﾄﾞ 5
		csvLine[i++] = "";
		//5:荷受人ｺｰﾄ 17
		csvLine[i++] = StringUtil4Jp.getString(reverse[5], 17);//【項番６　】納品先コード
		//6:荷受人名(漢字） 30
		csvLine[i++] = StringUtil4Jp.getString(reverse[9]+"　"+reverse[5], 30) ;//【項番１０】受取人＋全角ブランク＋【項番６】納品先コード
		//7:荷受人電話番号 15
		csvLine[i++] = StringUtil4Jp.getString(reverse[13], 15);//【項番１４】電話番号
		//8:荷受人郵便番号 7
		csvLine[i++] = StringUtil4Jp.getString(reverse[12].replace("-", ""), 7);//【項番１３】郵便番号,ハイフン除いて移送
		
		//9:荷受人住所 
		String add = reverse[10] + reverse[11]; // 【項番１１】住所１ + 【項番１２】住所２
		String add1 = StringUtil4Jp.getString(add, 32);
		add = add.length() > add1.length() ? add.substring(add1.length(), add.length()) : "";
		String add2 = StringUtil4Jp.getString(add, 32);
		add = add.length() > add2.length() ? add.substring(add2.length(), add.length()) : "";
		String add3 = StringUtil4Jp.getString(add, 32);
		
		//9:荷受人住所(漢字）１ 32
		csvLine[i++] = add1;
		//10:荷受人住所(漢字）２ 32
		csvLine[i++] = add2;
		//11:荷受人住所（漢字）３ 32
		csvLine[i++] = add3;
		
		
		//12:荷受人部門名１ 50
		csvLine[i++] = StringUtil4Jp.getString(reverse[6], 50);// 【項番７　】納品先名
		//13:荷受人部門名２ 50
		csvLine[i++] = StringUtil4Jp.getString(reverse[7]+"　"+reverse[8], 50) ; // 【項番８　】店舗名＋全角ブランク＋  【項番９　】部署名
		//14:荷送人ｺｰﾄﾞ 17
		csvLine[i++] = "";
		//15:荷送人名(漢字） 30
		csvLine[i++] = StringUtil4Jp.getString(reverse[68]+"　"+reverse[69], 30) ; //【項番６９】会社名＋全角ブランク＋【項番７０】担当営業所名
		//16:荷送人電話番号 15
		csvLine[i++] = StringUtil4Jp.getString(reverse[72], 15);//【項番７３】担当営業所電話番号
		//17:荷送人郵便番号 7
		csvLine[i++] = StringUtil4Jp.getString(reverse[73].replace("-", ""), 7);//【項番７４】担当営業所郵便番号,ハイフン除いて移送
		
		//18:荷送人住所（漢字）１ 32
		String yAdd = reverse[70] + reverse[71]; // 【項番７１】担当営業所住所１ + 【項番７２】担当営業所住所２
		String yAdd1 = StringUtil4Jp.getString(yAdd, 32);
		yAdd = yAdd.length() > yAdd1.length() ? yAdd.substring(yAdd1.length(), yAdd.length()) : "";
		String yAdd2 = StringUtil4Jp.getString(yAdd, 32);
		yAdd = yAdd.length() > yAdd2.length() ? yAdd.substring(yAdd2.length(), yAdd.length()) : "";
		String yAdd3 = StringUtil4Jp.getString(yAdd, 32);
		
		csvLine[i++] = yAdd1;
		//19:荷送人住所（漢字）2 32
		csvLine[i++] = yAdd2;
		//20:荷送人住所（漢字）3 32
		csvLine[i++] = yAdd3;
		
		//21:荷送人部門名
		String memo = reverse[29]; //【項番３０】備考（送り状）
		String memo1 = StringUtil4Jp.getString(memo, 50);
		memo = memo.length() > memo1.length() ? memo.substring(memo1.length(), memo.length()) : "";
		String memo2 = StringUtil4Jp.getString(memo, 50);
		
		//21:荷送人部門名１ 50
		csvLine[i++] = memo1;
		//22:荷送人部門名2 50
		csvLine[i++] = memo2;
		
		//23:運賃請求先コード1 12
		csvLine[i++] = StringUtil4Jp.getString(carrier.getExtString3() == null ? "" : carrier.getExtString3(), 12); // YTC顧客コード（電話番号）
		//24:運賃請求先コード2 3
		csvLine[i++] = StringUtil4Jp.getString(carrier.getExtString4() == null ? "" : carrier.getExtString4(), 3);// YTC顧客コード（枝番）
		
		//25:荷物取扱条件１ 1
		csvLine[i++] = ""; // 印字しない
		//26:荷物取扱条件２ 1
		csvLine[i++] = ""; // 印字しない
		
		//27:配達付帯作業(漢字） 30
		csvLine[i++] = ((BigInteger)caseInfoObj[10]).toString() + "／" + ((BigInteger)caseInfoObj[15]).toString();
		
		//28:品代金 7
		Double sumAmount = 0D;
		for( Object[] skuInfo : skuInfos ){
			String[] csvInfos = ((String)skuInfo[14]).split(",");
			
			// 单价，如果单价是0或者空，就不用计算
			Double price = StringUtils.isEmpty(csvInfos[INPUT_PRICE]) ? 0D : Double.parseDouble(csvInfos[INPUT_PRICE]);
			
			if( price != null && price.doubleValue() > 0D ){
				// 税率
				Double taxRate = StringUtils.isEmpty(csvInfos[TAX_RATE]) ? 0 : Double.parseDouble(csvInfos[TAX_RATE]);
				
				// 税区分
				String taxDiv = csvInfos[TAX_DIV];//0外税
				
				// 基本数和入力数的折算系数
				Double baseInputCoef = DoubleUtil.div(Double.parseDouble(csvInfos[BASE_QTY]), Double.parseDouble(csvInfos[INPUT_QTY]));
				
				// 基本数和PS数量的折算系数
				Double baseCheckQty = Double.parseDouble(csvInfos[BASE_CHECK_QTY]);
				
				// 将PS数折算成入力数
				Double qty = (Double)skuInfo[13];
				qty = DoubleUtil.mul(qty, baseCheckQty);// 折算成基本单位数
				qty = DoubleUtil.div(qty, baseInputCoef);// 折算成入力数
				
				Double amount = DoubleUtil.mul(qty, price);
				
				// 加上税
				if( "0".equals(taxDiv)  && taxRate != null && taxRate.doubleValue() > 0D){
					amount = amount * ( 1 + taxRate / 100 );
				}
				
				// 累计
				sumAmount += amount.longValue();
			}
		}
		csvLine[i++] = String.valueOf(sumAmount.longValue());
		
		//29:保管温度区分 1 （0：常温　1：冷蔵　2：冷凍）
		// 【常温】：空白　／　【冷凍】：１　／　【冷蔵】：２
		String tempDiv = reverse[32]; // 【項番３３】温度帯区分
		if( "0".equals(tempDiv) ){
			tempDiv = "";
		}
		else if( "1".equals(tempDiv) ){
			tempDiv = "2";
		}
		else if( "2".equals(tempDiv) ){
			tempDiv = "1";
		}
		else{
			tempDiv = "";
		}
		csvLine[i++] = tempDiv; 
		
		//30:運送品標記用品名ｺｰﾄﾞ１ 30
		csvLine[i++] = "";
		
		//31:商品名１(漢字） 50
		StringBuilder column31 = new StringBuilder("食品　伝票発行あり　");
		String billType = reverse[33]; // 【項番３４】専用伝票区分
		String ifPrint = reverse[34]; // 【項番３５】伝票発行区分
		String custCd = reverse[43]; // 【項番４４】得意先コード
		String shopCd = reverse[5]; // 【項番６　】納品先コード
		String billInfo = reverse[39]; // 【項番４０】伝票情報
		
		// ■伝票発行区分「0：する」且つ「得意先コード＝納品先コード」の場合
		// 「伝票発行あり」＋「専用伝票区分の伝票名」をセット
		if( "0".equals(ifPrint) && custCd.equals(shopCd) ){
			if( "0".equals(billType) ){
				column31.append("自社伝票");
			}
			else if( "1".equals(billType) ){
				column31.append("専用伝票");
			}
			else if( "2".equals(billType) ){
				if( StringUtils.isEmpty(billInfo.trim()) ){
					column31.append("チェーン伝票");
				}
				else{
					column31.append("チェーン伝票(専用)");
				}
			}
		}
		// 「伝票発行あり」＋「出荷案内書」をセット
		else{
			column31.append("出荷案内書");
		}
		csvLine[i++] = column31.toString();
		
		//32:運送品標記用品名ｺｰﾄﾞ２ 30
		csvLine[i++] = ""; // 印字しない
		
		//33:商品名２(漢字） 50
		//※同一バッチ同一納品先は集約し全ての伝票№を印字
		//※伝票№＋関連する伝票№を全角ブランクを挟んで表示する
		//桁数の関係で表示できない場合は、最後に「＊」を表示する
		//（伝票は最大３伝票表示とする）
		String billNo = (String)caseInfoObj[4];
		StringBuilder billStr = new StringBuilder();
		if( billNo != null ){
			String[] billNos = billNo.split(",");
			for( int k = 0; k < 3 && k < billNos.length ; k++ ){
				if( billStr.length() > 0 ){
					billStr.append("　");
				}
				billStr.append(billNos[k]);
			}
			
			// 伝票数＞３の場合、*印字
			if( billNos.length > 3 ){
				billStr.append("＊");
			}
		}
		csvLine[i++] = StringUtil4Jp.getString("伝票№:" + billStr.toString(), 50);
		
		//34:記述式運送梱包寸法 4
		// 【常温】「0101」セット ／【冷蔵・冷凍】「0301」セット
		// 【代引常温】「0401」セット ／【代引冷蔵冷凍】「0501」セット
		tempDiv = reverse[32]; // 【項番３３】温度帯区分
		String column34 = "";
		if( "0".equals(tempDiv) ){
			column34 = "0102";
		}
		else{
			column34 = "0303";
		}
		csvLine[i++] = StringUtil4Jp.getString(column34, 4);
		
		//35:着荷指定日 8
		csvLine[i++] = StringUtil4Jp.getString(reverse[26], 8);//【項番２７】納品指定日
		
		//36:着荷指定時刻条件 2
		String timeSpec = reverse[27];//【項番２８】納品時間
		if( "8".equals(timeSpec) ){
			timeSpec = "08";
		}
		else if("0".equals(timeSpec)){
			timeSpec = "";
		}
		csvLine[i++] = StringUtil4Jp.getString(timeSpec, 2);
		
		//37:個数(依頼） 3
		csvLine[i++] = "1";
		
		//38:出荷区分 1
		csvLine[i++] = "0";
		//39:更新日付 8
		csvLine[i++] = todayStr;
		//40:予備 500
		StringBuilder skuStr = new StringBuilder();
		
		// 如果是BL或者CS的箱子，显示库位号
		Long caseType = ((BigInteger)caseInfoObj[9]).longValue();
		String binCode = (String)caseInfoObj[22];
		
		for( int j = 0; j < 5; j++ ){
			if( j <= skuInfos.size() - 1 ){
				Object[] skuInfo = skuInfos.get(j);
				String[] reverseSku = ((String)skuInfo[14]).split(",");
				// 商品名;特別品No;包装形態名;数量＋単位;
				skuStr.append(reverseSku[84]).append(";");
				skuStr.append(reverseSku[86]).append(";");
				skuStr.append(reverseSku[100]).append(";");
				
				String csUnit = (String)skuInfo[18];
				String blUnit = (String)skuInfo[17];
				String psUnit = (String)skuInfo[16];
				Double qty = (Double)skuInfo[13];
				Double blIn = (Double)skuInfo[19];
				Double csIn = (Double)skuInfo[20];
				skuStr.append(QuantityUtil.getZenQtyInfo(qty, blIn, csIn, psUnit, blUnit, csUnit)).append(";");

			}
			// 常温
			else if( "0".equals(tempDiv) && EnuCaseType.CS_CASE.longValue() == caseType.longValue() && j == 4 ){
				skuStr.append(binCode + ";;;;");
			}
			// 冷蔵・冷凍
			else if( ("1".equals(tempDiv) || "2".equals(tempDiv) ) 
					&& (EnuCaseType.CS_CASE.longValue() == caseType.longValue() || EnuCaseType.BL_CASE.longValue() == caseType.longValue()) 
					&& j == 1 ){
				skuStr.append(binCode + ";;;;");
			}
			else{
				skuStr.append(";;;;");
			}
		}
		
		csvLine[i++] = skuStr.toString();
		return csvLine;
	}
	
	private String[] getFukuyamaCsvLine( List<Object[]> skuInfos, String todayStr ){
		Object[] caseInfoObj = skuInfos.get(0);
		String[] reverse = ((String)caseInfoObj[14]).split(",");
		int i = 0;
		String[] csvLine = new String[58];
		//1:
		csvLine[i++] = "";
		//2:出荷日 8
		csvLine[i++] = reverse[24];//【項番２５】
		//3:担当営業所コード 7
		csvLine[i++] = reverse[67];//【項番６８】
		//4:会社名 10
		csvLine[i++] = reverse[68];//【項番６９】
		//5:担当営業所名 10
		csvLine[i++] = reverse[69];//【項番70】
		//6:担当営業所〒 8
		csvLine[i++] = reverse[73];//【項番74】
		//7:担当営業所住所１ 17,17
		String add = reverse[70] + reverse[71];
		String add1 = StringUtil4Jp.getString(add, 34);
		add = add.length() > add1.length() ? add.substring(add1.length(), add.length()) : "";
		String add2 = StringUtil4Jp.getString(add, 34);
		
		csvLine[i++] = add1;//【項番71】
		//8:担当営業所住所２
		csvLine[i++] = add2;//【項番72】
		//9:
		csvLine[i++] = "";
		//10:担当営業所TEL 14
		csvLine[i++] = reverse[72];//【項番73】
		//11:
		csvLine[i++] = "";
		//12:納品先コード 8
		csvLine[i++] = reverse[5];//【項番6】
		//13:納品先名 20
		csvLine[i++] = reverse[6];//【項番7】
		//14:店舗名 10
		csvLine[i++] = reverse[7];//【項番8】
		//15:部署名 10
		csvLine[i++] = reverse[8];//【項番9】
		//16:納品先部門名 10
		csvLine[i++] = reverse[76];//【項番77】
		//17:受取人 10
		csvLine[i++] = reverse[9];//【項番10】
		//18:郵便番号 8
		csvLine[i++] = reverse[12];//【項番13】
		//19:住所１ 20
		csvLine[i++] = reverse[10];//【項番11】
		//20:住所２ 20
		csvLine[i++] = reverse[11];//【項番12】
		//21:
		csvLine[i++] = "";
		//22:電話番号 13
		csvLine[i++] = reverse[13];//【項番14】
		//23:配達指定日 8
		csvLine[i++] = reverse[26];//【項番27】
		//24:
		csvLine[i++] = "食品";
		//25:
		csvLine[i++] = "";
		//26:備考（送り状） 10
		csvLine[i++] = reverse[29];//【項番30】
		//27:
		csvLine[i++] = "";
		
		//28:"伝票発行区分専用伝票区分伝票情報得意先コード納品先コード" 15
		StringBuilder column31 = new StringBuilder("伝票発行あり　");
		String billType = reverse[33]; // 【項番３４】専用伝票区分
		String ifPrint = reverse[34]; // 【項番３５】伝票発行区分
		String custCd = reverse[43]; // 【項番４４】得意先コード
		String shopCd = reverse[5]; // 【項番６　】納品先コード
		String billInfo = reverse[39]; // 【項番４０】伝票情報
		
		// ■伝票発行区分「0：する」且つ「得意先コード＝納品先コード」の場合
		// 「伝票発行あり」＋「専用伝票区分の伝票名」をセット
		if( "0".equals(ifPrint) && custCd.equals(shopCd) ){
			if( "0".equals(billType) ){
				column31.append("自社伝票");
			}
			else if( "1".equals(billType) ){
				column31.append("専用伝票");
			}
			else if( "2".equals(billType) ){
				if( StringUtils.isEmpty(billInfo.trim()) ){
					column31.append("チェーン伝票");
				}
				else{
					column31.append("チェーン伝票(専用)");
				}
			}
		}
		// 「伝票発行あり」＋「出荷案内書」をセット
		else{
			column31.append("出荷案内書");
		}
		csvLine[i++] = column31.toString();
		
		//29:伝票No 10
		String billNo = (String)caseInfoObj[4];
		StringBuilder billStr = new StringBuilder();
		if( billNo != null ){
			String[] billNos = billNo.split(",");
			for( int k = 0; k < 3 && k < billNos.length ; k++ ){
				if( billStr.length() > 0 ){
					billStr.append("　");
				}
				billStr.append(billNos[k]);
			}
			
			// 伝票数＞３の場合、*印字
			if( billNos.length > 3 ){
				billStr.append("＊");
			}
		}
		csvLine[i++] = "伝票№：" + billStr.toString();
		
		//30:注文No
		csvLine[i++] = "注文№：" + reverse[28];//【項番29】
		//31:
		csvLine[i++] = "";
		//32:【倉庫側で計算した個口数合計】
		csvLine[i++] = ((BigInteger)caseInfoObj[15]).toString();
		//33:
		csvLine[i++] = "";
		//34:【倉庫側で計算した重量合計】
		csvLine[i++] = String.valueOf(((BigDecimal)caseInfoObj[21]).longValue());
		//35:
		csvLine[i++] = "";
		//36:
		csvLine[i++] = "";
		
		// 如果是BL或者CS的箱子，显示库位号
		Long caseType = ((BigInteger)caseInfoObj[9]).longValue();
		String binCode = (String)caseInfoObj[22];
		
		for( int j = 0; j < 5; j++ ){
			if( j <= skuInfos.size() - 1 ){
				Object[] skuInfo = skuInfos.get(j);
				String[] reverseSku = ((String)skuInfo[14]).split(",");
				// 商品名
				csvLine[i++] = reverseSku[84];
				// 特別品No
				csvLine[i++] = reverseSku[86];
				// 包装形態名
				csvLine[i++] = reverseSku[100];
				
				// 数量＋単位
				String csUnit = (String)skuInfo[18];
				String blUnit = (String)skuInfo[17];
				String psUnit = (String)skuInfo[16];
				Double qty = (Double)skuInfo[13];
				Double blIn = (Double)skuInfo[19];
				Double csIn = (Double)skuInfo[20];
				csvLine[i++] = QuantityUtil.getZenQtyInfo(qty, blIn, csIn, psUnit, blUnit, csUnit);
			}
			else if((EnuCaseType.CS_CASE.longValue() == caseType.longValue() || EnuCaseType.BL_CASE.longValue() == caseType.longValue()) && j == 4 ){
				csvLine[i++] = binCode;//商品名
				csvLine[i++] = "";//特別品No
				csvLine[i++] = "";//包装形態
				csvLine[i++] = "";//梱包結果＋見合単位
			}
			else{
				csvLine[i++] = "";//商品名
				csvLine[i++] = "";//特別品No
				csvLine[i++] = "";//包装形態
				csvLine[i++] = "";//梱包結果＋見合単位
			}
		}
		
		//57:【倉庫側で発番した送り状番号】
		csvLine[i++] = (String)caseInfoObj[6];//6:送り状No
		
		//58:【倉庫側で発番した明細番号】
		String inx = ((BigInteger)caseInfoObj[10]).toString();
		String inxStr = StringUtils.repeat("0", 3 - inx.length()) + inx;
		csvLine[i++] = inxStr;
		
		return csvLine;
	}
	
	@Override
	public Long createYamatoCsv(List<Long> waveDocIds) {
		String hql = " select carrier.id from Carrier carrier "
				+ "where carrier.code = :code and "
				+ "carrier.plant.id = ( select wv.plant.id from WaveDoc wv where wv.id = :wvId ) ";
		List<Long> carrierId = commonDao.findByQuery(hql, new String[]{"code", "wvId"}, 
				new Object[]{WmsConstant4Ktw.CARRIER_YAMATO, waveDocIds.get(0)});
		if( carrierId == null || carrierId.size() == 0 ){
			throw new BusinessException("運送業者マスタ不備、コード：" + WmsConstant4Ktw.CARRIER_YAMATO);
		}
		
		return createYamatoCsv(waveDocIds.get(0), carrierId.get(0), WmsConstant4Ktw.CARRIER_YAMATO);
	}
	
	@Override
	public Long createYamatoCoolCsv(List<Long> waveDocIds) {
		String hql = " select carrier.id from Carrier carrier "
				+ "where carrier.code = :code and "
				+ "carrier.plant.id = ( select wv.plant.id from WaveDoc wv where wv.id = :wvId ) ";
		List<Long> carrierId = commonDao.findByQuery(hql, new String[]{"code", "wvId"}, 
				new Object[]{WmsConstant4Ktw.CARRIER_YAMATO_COOL, waveDocIds.get(0)});
		if( carrierId == null || carrierId.size() == 0 ){
			throw new BusinessException("運送業者マスタ不備、コード：" + WmsConstant4Ktw.CARRIER_YAMATO_COOL);
		}
		
		return createYamatoCsv(waveDocIds.get(0), carrierId.get(0), WmsConstant4Ktw.CARRIER_YAMATO_COOL);
	}

	@Override
	public Long createFukuyamaCsv(List<Long> ids) {
		WaveDoc waveDoc = commonDao.load(WaveDoc.class, ids.get(0));
		
		String hql = " select carrier.id from Carrier carrier "
				+ "where carrier.code = :code and "
				+ "carrier.plant.id = ( select wv.plant.id from WaveDoc wv where wv.id = :wvId ) ";
		List<Long> carrierIds = commonDao.findByQuery(hql, new String[]{"code", "wvId"}, 
				new Object[]{WmsConstant4Ktw.CARRIER_FUKUYAMA, ids.get(0)});
		if( carrierIds == null || carrierIds.size() == 0 ){
			throw new BusinessException("運送業者マスタ不備、コード：" + WmsConstant4Ktw.CARRIER_FUKUYAMA);
		}
		
		List<Object[]> caseInfos = commonDao.findBySqlQuery(getSql(), new Object[]{ids.get(0), carrierIds.get(0)});
		
		List<String[]> csvList = new ArrayList<String[]>( caseInfos.size() );
		String todayStr = DateUtil.getStringDate(new Date(), DateUtil.PURE_DATE_FORMAT);
		String caseNumber = "";
		List<Object[]> skuInfos = new ArrayList<Object[]>(5);
		
		for( Object[] caseInfoObj : caseInfos ){
			String thisCaseNumber = (String)caseInfoObj[8];
			
			// 新箱
			if( !caseNumber.equals(thisCaseNumber) ){
				if(skuInfos.size() > 0){
					String[] csvLine = getFukuyamaCsvLine(skuInfos, todayStr);
					csvList.add(csvLine);
				}
				
				caseNumber = thisCaseNumber;
				skuInfos = new ArrayList<Object[]>(5);
			}
			skuInfos.add(caseInfoObj);
		}
		
		// 最后一箱
		if(skuInfos.size() > 0){
			String[] csvLine = getFukuyamaCsvLine(skuInfos, todayStr);
			csvList.add(csvLine);
		}
		
		// 生成文件
		return createCsvFile("fukuyama" + WmsConstant4Ktw.CARRIER_FUKUYAMA, waveDoc.getWh(), waveDoc.getOwner(), csvList.toArray(new String[csvList.size()][58])) ;
	}

	@Override
	public void cancelPick(List<Long> wtsIds) {
		for( Long wtsId : wtsIds ){
			WarehouseTaskSingle wts = commonDao.load(WarehouseTaskSingle.class, wtsId);
			if( !EnuWarehouseTaskSingleStatus.STATUS100.equals(wts.getStatus()) ){
				throw new BusinessException(ExceptionConstant.ERROR_STATUS);
			}
			
			// 执行拣货
			// warehouseTaskManager.pickTaskExecute(wts.getWt().getId(), wts.getQty(), wts.getRelatedBill2(), laborId);
			
			// 拣货数
			wts.setPickQty(0D);
			// 状态
			wts.setStatus(EnuWarehouseTaskSingleStatus.STATUS000);
			// 拣货人
			wts.setLabor(null);
			// 拣货时间
			wts.setOperateTime(null);
			commonDao.store(wts);
			
		}
	}

}
