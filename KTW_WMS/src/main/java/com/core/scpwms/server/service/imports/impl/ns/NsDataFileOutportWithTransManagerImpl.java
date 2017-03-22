package com.core.scpwms.server.service.imports.impl.ns;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.supercsv.prefs.CsvPreference;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.constant.WmsConstant4Ktw;
import com.core.scpwms.server.enumerate.EnuAsnDetailStatus;
import com.core.scpwms.server.enumerate.EnuAsnReportStatus;
import com.core.scpwms.server.enumerate.EnuAsnStatus;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryDetailStatus;
import com.core.scpwms.server.enumerate.EnuOutboundDeliveryStatus;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.file.FileImport4Wms;
import com.core.scpwms.server.model.file.FileImport4WmsDetail;
import com.core.scpwms.server.model.inbound.Asn;
import com.core.scpwms.server.model.inbound.AsnDetail;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.imports.DataFileOutportWithTransManager;
import com.core.scpwms.server.service.imports.impl.BaseFileImportWithTransManagerImpl;
import com.core.scpwms.server.util.DateUtil;
import com.core.scpwms.server.util.FileUtil4Jp;
import com.core.scpwms.server.util.StringUtil4Jp;

/**
 * 日本食研的数据导出
 * @author mousachi
 */
public class NsDataFileOutportWithTransManagerImpl extends BaseFileImportWithTransManagerImpl implements DataFileOutportWithTransManager,NsAsnOutConstant, NsObdOutConstant {
	private OrderStatusHelper orderStatusHelper;
	
	public OrderStatusHelper getOrderStatusHelper() {
		return orderStatusHelper;
	}

	public void setOrderStatusHelper(OrderStatusHelper orderStatusHelper) {
		this.orderStatusHelper = orderStatusHelper;
	}

	@Override
	public String createAsnOutportFile(FileImport4Wms fileImport4Wms) {
		// 查到可以会接口的数据
		String hql = " select detail.id from AsnDetail detail where 1=1 "
				+ " and detail.status in (200,300,400) "// 部分收货，收货完成
				+ " and detail.closedQty = 0 "
				+ " and detail.asn.ediData = true" // 必须是EDI数据
				+ " and detail.asn.owner.id = :ownerId "
				+ " and detail.asn.wh.id = :whId "
				//  同行号，有一个收货了，全部回告
				+ " and exists "
				+ " (select oldDetail.id "
				+ " from AsnDetail oldDetail "
				+ " where oldDetail.asn.id = detail.asn.id "
				+ " and oldDetail.lineNo = detail.lineNo "
				+ " and oldDetail.status in (300,400) "
				+ " and (oldDetail.isReported is null or oldDetail.isReported = false ) ) "
				// 相同商品不能回2次
				+ " and detail.sku.id not in "
				+ " ( select distinct oldDetail.sku.id "
				+ " from AsnDetail oldDetail "
				+ " where oldDetail.asn.id = detail.asn.id"
				+ " and oldDetail.isReported = true ) "
				+ " order by detail.asn.relatedBill1, detail.lineNo, detail.subLineNo ";// 订单号，行号，子行号排序
		
		List<Long> asnDetailIds = commonDao.findByQuery(hql, new String[]{"ownerId", "whId"}, new Object[]{ fileImport4Wms.getOwner().getId(), fileImport4Wms.getWh().getId() });
		
		// 如果没有可以回到的数据，返回Null
		if( asnDetailIds == null || asnDetailIds.size() == 0 )
			return null;
		
		List<String[]> csvList = new ArrayList<String[]>( asnDetailIds.size() );
		Set<Long> asnIds = new HashSet<Long>();
		
		List<AsnDetail> newAsnDetailList = new ArrayList<AsnDetail>();
		for( Long asnDetailId : asnDetailIds ){
			AsnDetail asnDetail = commonDao.load(AsnDetail.class, asnDetailId);
			
			String[] csvLine = new String[ASN_LENGTH];
			csvLine[ASN_ORDER_NO] = asnDetail.getAsn().getRelatedBill1();
			csvLine[ASN_LINE_NO] = String.valueOf(asnDetail.getLineNo().intValue());
			csvLine[ASN_SUB_LINE_NO] = asnDetail.getSubLineNo().toString();
			
			// 实际入库日期
			String hql2 = " select min(ih.receiveDate) from InboundHistory ih where ih.detail.id = :asnDetailId ";
			Date ata = (Date)commonDao.findByQueryUniqueResult(hql2, new String[]{"asnDetailId"}, new Object[]{asnDetailId});
 			csvLine[ASN_ETA] = DateUtil.getStringDate(ata == null ? asnDetail.getAsn().getEta() : ata, DateUtil.PURE_DATE_FORMAT);// TODO
			
 			csvLine[ASN_WH_CD] = asnDetail.getAsn().getOwner().getWhse();
			csvLine[ASN_ORDER_DIV] = asnDetail.getAsn().getOrderType().getCode().equals(WmsConstant4Ktw.ASN01) ? "1" : "2";
			csvLine[ASN_TRANSPORT_BILL_NO] = asnDetail.getAsn().getExtString1();
			csvLine[ASN_SKU_CD] = asnDetail.getSku().getCode();
			csvLine[ASN_INV_STATUS] = getNsInvStatus(asnDetail.getActInvStatus() == null ? asnDetail.getInvStatus() : asnDetail.getActInvStatus());
			Sku sku = commonDao.load(Sku.class, asnDetail.getSku().getId());
			if( sku.getProperties().getUseExpire() != null && !sku.getProperties().getUseExpire().booleanValue() ){
				csvLine[ASN_EXP_DATE] = "0";
			}
			else if( asnDetail.getActExpDate() == null ){
				csvLine[ASN_EXP_DATE] = "0";
			}
			else{
				csvLine[ASN_EXP_DATE] = DateUtil.getStringDate(asnDetail.getActExpDate(), DateUtil.PURE_DATE_FORMAT);
			}
			csvLine[ASN_UNIT_DIV] = "0";
			
			Double asnBaseQty = DoubleUtil.mul(asnDetail.getExecuteQty(), sku.getProperties().getBaseCheckQty4NS());
			csvLine[ASN_BASE_QTY] =  String.format("%.2f", asnBaseQty);
			
			csvList.add(csvLine);
			
//			//如果是部分收货状态，要拆行
//			if( EnuAsnDetailStatus.STATUS300.equals(asnDetail.getStatus()) ){
//				AsnDetail newAsnDetail = new AsnDetail();
//				try{
//					BeanUtils.copyProperties(newAsnDetail, asnDetail);
//				}catch( Exception e ){
//					throw new BusinessException(ExceptionConstant.SYSTEM_EXCEPTION);
//				}
//				
//				newAsnDetail.setId(null);
//				newAsnDetail.setActExpDate(null);
//				newAsnDetail.setActInvStatus(null);
//				newAsnDetail.setExpDate(null);
//				newAsnDetail.setExecuteQty(0D);
//				newAsnDetail.setPlanQty(asnDetail.getUnexecuteQty());
//				newAsnDetail.setStatus(EnuAsnDetailStatus.STATUS200);
//				newAsnDetailList.add(newAsnDetail);
//				
//				asnDetail.setPlanQty(asnDetail.getExecuteQty());
//			}
			// 登记回告数量
			asnDetail.addClosedQty(asnDetail.getExecuteQty());
			asnDetail.setIsReported(Boolean.TRUE);
			
			asnIds.add(asnDetail.getAsn().getId());
			
			// 登记到FileImport4WmsDetail里
			FileImport4WmsDetail fileImportDetail = new FileImport4WmsDetail();
			fileImportDetail.setFileImport4Wms(fileImport4Wms);
			fileImportDetail.setRelatedClassName(AsnDetail.class.getSimpleName());
			fileImportDetail.setRelatedId(asnDetailId);
			commonDao.store(fileImportDetail);
		}
		
//		// 没有完全回告的信息，在这里要处理一下负数的问题。
//		// TODO
//		
//		// 订单状态修改
//		for( Long asnId : asnIds ){
//			String hql1 = "select detail.id from AsnDetail detail where detail.status < 999 and detail.asn.id = :asnId ";
//			List<Long> unClosedDetailIds = commonDao.findByQuery(hql1, "asnId", asnId);
//			
//			Asn asn = commonDao.load(Asn.class, asnId);
//			asn.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
//			if( unClosedDetailIds == null || unClosedDetailIds.size() == 0 ){
//				orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS999);//報告済
//			}
//			else{
//				orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS900);//一部報告済
//			}
//		}
//		
		// 生成文件
		String fileName = "WP_RNYKOJ_" + fileImport4Wms.getOwner().getWhse() + ".CSV";
		String systemFileName = "ASN_OUT_" + fileImport4Wms.getOwner().getCode() + "_" + DateUtil.getStringDate(new Date(), DateUtil.LONG_DATE_FORMAT) + ".CSV";
		String hql2 = "select value from GlobalParam where paramId = :paramId ";
		String fileBasePath = (String)commonDao.findByQueryUniqueResult(hql2, new String[]{"paramId"}, new Object[]{"tempFileDir"});
		String systemPath = fileBasePath + File.separator + "dataFile" + File.separator +  fileImport4Wms.getOwner().getCode() + File.separator;
		CsvPreference csvPre = new CsvPreference('\'', '\t', "\r\n");
		
		FileUtil4Jp.createMultiPath(systemPath);
		FileUtil4Jp.writeCSVFile(csvPre, csvList.toArray(new String[csvList.size()][ASN_LENGTH]), 
				systemPath + systemFileName, FileUtil4Jp.ENCODE_MS932);
		
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(fileName);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(systemPath + systemFileName);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return "入庫実績データを出力しました。" + csvList.size();
	}

	@Override
	public String cancelAsnOutportFile(FileImport4Wms fileImport4Wms) {
		String hql = "select detail.id from FileImport4WmsDetail detail where detail.fileImport4Wms.id = :fileId ";
		List<Long> fileImportDetailIds = commonDao.findByQuery(hql, "fileId", fileImport4Wms.getId());
		
		Set<Long> asnIds = new HashSet<Long>();
		if( fileImportDetailIds != null && fileImportDetailIds.size() > 0 ){
			for( Long fileImportDetailId : fileImportDetailIds ){
				FileImport4WmsDetail detail = commonDao.load(FileImport4WmsDetail.class, fileImportDetailId);
				Long asnDetailId = detail.getRelatedId();
				
				AsnDetail asnDetail = commonDao.load(AsnDetail.class, asnDetailId);
				asnDetail.removeClosedQty(asnDetail.getClosedQty());
				asnDetail.setIsReported(Boolean.FALSE);
				commonDao.store(asnDetail);
				asnIds.add(asnDetail.getAsn().getId());
				
				commonDao.delete(detail);
			}
		}
		
//		// 订单状态修改
//		for( Long asnId : asnIds ){
//			Asn asn = commonDao.load(Asn.class, asnId);
//			if( asn.getUnExecuteQty() <= 0 ){
//				orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS400);
//			}
//			else{
//				orderStatusHelper.changeStatus(asn, EnuAsnStatus.STATUS300);
//			}
//		}
		
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(null);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(null);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return null;
	}
	
	private static final int INV_LENGTH = 6;
	// 棚卸データ
	private static final int INV_DATE = 0;
	// 倉庫識別子
	private static final int INV_WH_CD = 1;
	// 商品コード
	private static final int INV_SKU_CD = 2;
	// 賞味期限
	private static final int INV_EXPDATE = 3;
	// 在庫ステータス
	private static final int INV_INV_STATUS = 4;
	// 棚卸数
	private static final int INV_QTY = 5;
	
	@Override
	public String createInvOutportFile(FileImport4Wms fileImport4Wms) {
		// 查到可以会接口的数据
		String sql = " select sku.code,"
				+ " case when sku.use_expire=false then '0' else quant.displot end as expDate,"
				+ " inv.status,sum(inv.base_qty)*sku.base_checkqty_4ns as qty"
				+ " from wms_inventory inv "
				+ " left join wms_quant_inventory quantInv on inv.quant_inv_id = quantInv.id" 
				+ " left join wms_quant quant on quantInv.quant_id = quant.id "
				+ " left join wms_sku sku on quant.sku = sku.id "
				+ " where inv.wh_id = ? and inv.owner_id = ? "
				+ " group by sku.code,quant.displot,inv.status,sku.base_checkqty_4ns,sku.use_expire"
				+ " order by sku.code,quant.displot,inv.status";
		List<Object[]> invInfos = commonDao.findBySqlQuery(sql, new Object[]{ fileImport4Wms.getOwner().getId(), fileImport4Wms.getWh().getId() });
		
		// 如果没有可以回到的数据，返回Null
		if( invInfos == null || invInfos.size() == 0 )
			return null;
		
		List<String[]> csvList = new ArrayList<String[]>( invInfos.size() );
		Date today = new Date();
		for( Object[] invInfoObd : invInfos ){
			String[] csvLine = new String[INV_LENGTH];
			csvLine[INV_DATE] = DateUtil.getStringDate(today, DateUtil.PURE_DATE_FORMAT);
			csvLine[INV_WH_CD] = fileImport4Wms.getOwner().getWhse();
			csvLine[INV_SKU_CD] = (String)invInfoObd[0];
			csvLine[INV_EXPDATE] = (String)invInfoObd[1];
			csvLine[INV_INV_STATUS] = getNsInvStatus((String)invInfoObd[2]);
			csvLine[INV_QTY] = String.format("%.2f", (Double)invInfoObd[3]);
			
			csvList.add(csvLine);
		}
		
		// 生成文件
		String fileName = "WP_RRZAI_" + fileImport4Wms.getOwner().getWhse() + ".CSV";
		String systemFileName = "INV_OUT_" + fileImport4Wms.getOwner().getCode() + "_" + DateUtil.getStringDate(new Date(), DateUtil.LONG_DATE_FORMAT) + ".CSV";
		String hql2 = "select value from GlobalParam where paramId = :paramId ";
		String fileBasePath = (String)commonDao.findByQueryUniqueResult(hql2, new String[]{"paramId"}, new Object[]{"tempFileDir"});
		String systemPath = fileBasePath + File.separator + "dataFile" + File.separator 
				+ fileImport4Wms.getOwner().getCode() + File.separator
				+ DateUtil.getStringDate(new Date(), DateUtil.PURE_DATE_FORMAT) + File.separator;
		CsvPreference csvPre = new CsvPreference('\'', '\t', "\r\n");
		
		FileUtil4Jp.createMultiPath(systemPath);
		FileUtil4Jp.writeCSVFile(csvPre, csvList.toArray(new String[csvList.size()][INV_LENGTH]), systemPath + systemFileName, FileUtil4Jp.ENCODE_MS932);
		
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(fileName);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(systemPath + systemFileName);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return "在庫実績データを出力しました。" + csvList.size();
	}

	@Override
	public String cancelInvOutportFile(FileImport4Wms fileImport4Wms) {
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(null);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(null);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return null;
	}

	@Override
	public String createCountOutportFile(FileImport4Wms fileImport4Wms) {
		// 查到可以会接口的数据
		String sql = " select sku.code,"
				+ " case when sku.use_expire=false then '0' else quant.displot end as expDate,"
				+ " inv.status,sum(inv.base_qty)*sku.base_checkqty_4ns as qty"
				+ " from wms_inventory inv "
				+ " left join wms_quant_inventory quantInv on inv.quant_inv_id = quantInv.id" 
				+ " left join wms_quant quant on quantInv.quant_id = quant.id "
				+ " left join wms_sku sku on quant.sku = sku.id "
				+ " where inv.wh_id = ? and inv.owner_id = ? "
				+ " group by sku.code,quant.displot,inv.status,sku.base_checkqty_4ns,sku.use_expire"
				+ " order by sku.code,quant.displot,inv.status";
		List<Object[]> invInfos = commonDao.findBySqlQuery(sql, new Object[]{ fileImport4Wms.getOwner().getId(), fileImport4Wms.getWh().getId() });
		
		// 如果没有可以回到的数据，返回Null
		if( invInfos == null || invInfos.size() == 0 )
			return null;
		
		List<String[]> csvList = new ArrayList<String[]>( invInfos.size() );
		Date today = new Date();
		for( Object[] invInfoObd : invInfos ){
			String[] csvLine = new String[INV_LENGTH];
			csvLine[INV_DATE] = DateUtil.getStringDate(today, DateUtil.PURE_DATE_FORMAT);
			csvLine[INV_WH_CD] = fileImport4Wms.getOwner().getWhse();
			csvLine[INV_SKU_CD] = (String)invInfoObd[0];
			csvLine[INV_EXPDATE] = (String)invInfoObd[1];
			csvLine[INV_INV_STATUS] = getNsInvStatus((String)invInfoObd[2]);
			csvLine[INV_QTY] = csvLine[INV_QTY] = String.format("%.2f", (Double)invInfoObd[3]);;
			
			csvList.add(csvLine);
		}
		
		// 生成文件
		String fileName = "WP_RTANAJ_" + fileImport4Wms.getOwner().getWhse() + ".CSV";
		String systemFileName = "INV_OUT_" + fileImport4Wms.getOwner().getCode() + "_" + DateUtil.getStringDate(new Date(), DateUtil.LONG_DATE_FORMAT) + ".CSV";
		String hql2 = "select value from GlobalParam where paramId = :paramId ";
		String fileBasePath = (String)commonDao.findByQueryUniqueResult(hql2, new String[]{"paramId"}, new Object[]{"tempFileDir"});
		String systemPath = fileBasePath + File.separator + "dataFile" 
				+ File.separator +  fileImport4Wms.getOwner().getCode() + File.separator
				+ DateUtil.getStringDate(new Date(), DateUtil.PURE_DATE_FORMAT) + File.separator;
		CsvPreference csvPre = new CsvPreference('\'', '\t', "\r\n");
		
		FileUtil4Jp.createMultiPath(systemPath);
		FileUtil4Jp.writeCSVFile(csvPre, csvList.toArray(new String[csvList.size()][INV_LENGTH]), systemPath + systemFileName, FileUtil4Jp.ENCODE_MS932);
		
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(fileName);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(systemPath + systemFileName);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return "棚卸実績データを出力しました。" + csvList.size();
	}

	@Override
	public String cancelCountOutportFile(FileImport4Wms fileImport4Wms) {
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(null);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(null);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return null;
	}
	
	// 出荷実績データ
	@Override
	public String createObdOutportFile(FileImport4Wms fileImport4Wms) {
		List<Object[]> detailIds = getOutboundReportDate(fileImport4Wms.getWh().getId(), fileImport4Wms.getOwner().getId(), WmsConstant4Ktw.OBD01);
		
		// 如果没有可以回到的数据，返回Null
		if( detailIds == null || detailIds.size() == 0 )
			return null;
		
		List<String[]> csvList = new ArrayList<String[]>( detailIds.size() );
		Set<Long> obdIds = new HashSet<Long>();
		
		String nowOrder = "";
		int nowLineNo = 1;
		int nowsubLineNo = 1;
		
		for( Object[] detailObd : detailIds ){
			
			String[] reverse = (String)detailObd[2] == null ? null : ((String)detailObd[2]).split(",");
			
			String orderSeq = (String)detailObd[4];
			int lineNo = ((Double)detailObd[5]).intValue();
			int subLineNo = ((Integer)detailObd[6]).intValue();
			
			if( nowOrder.equals(orderSeq) && nowLineNo == lineNo ){
				nowsubLineNo++;
			}
			else{
				nowOrder = orderSeq;
				nowLineNo = lineNo;
				nowsubLineNo = subLineNo;
			}
			
			String[] csvLine = new String[OBD_LENGTH];
			// 0:出荷・出庫倉庫コード
			csvLine[OBD_WH_CD] = fileImport4Wms.getOwner().getWhse();
			// 1:出荷・出庫区分１：出荷２：出庫３：移動
			csvLine[OBD_ORDER_DIV] = reverse != null && reverse.length > 1 ? reverse[1] : "1";
			// 2:データ種別０：通常１：サンプル
			csvLine[OBD_DATA_DIV] = reverse != null && reverse.length > 2 ? reverse[2] : "0";
			// 3:出荷・出庫日
			csvLine[OBD_SHIP_DATE] = DateUtil.getStringDate((Date)detailObd[8], DateUtil.PURE_DATE_FORMAT);
			// 4:伝票ＮＯ
			csvLine[OBD_ORDER_NO] = (String)detailObd[4];
			// 5:伝票行ＮＯ
			csvLine[OBD_LINE_NO] = String.valueOf(((Double)detailObd[5]).intValue());
			// 6:伝票行ＮＯ枝番
			csvLine[OBD_SUB_LINE_NO] = String.valueOf(nowsubLineNo);
			// 7:専用伝票区分０：自社１：専用２：ＣＳ ３：ＥＯＳ
			csvLine[OBD_BILL_DIV] = reverse != null && reverse.length > 33 ? reverse[33] : "";
			// 8:専用伝票ＮＯ
			csvLine[OBD_BILL_NO] = "1".equals(csvLine[OBD_BILL_DIV]) ? "1" : csvLine[OBD_ORDER_NO];
			// 9:運送便コード
			csvLine[OBD_CARRIER_CD] = (String)detailObd[11] == null ? "" : (String)detailObd[11];
			// 10:送り状ＮＯ
			String okuriNo = (String)detailObd[7] == null ? "" : ((String)detailObd[7]).replaceAll("-", "");
			csvLine[OBD_DELIVERY_BILL_NO] = StringUtil4Jp.fillFormat(okuriNo, 15, '0', 2);
			// 11:寄託者管理ＮＯ
			csvLine[OBD_WH_ORDER_NO] = "";
			// 12:寄託者管理行ＮＯ
			csvLine[OBD_WH_LINE_NO] = "";
			// 13:商品コード
			csvLine[OBD_SKU_CD] = reverse != null && reverse.length > 82 ? reverse[82] : (String)detailObd[9];
			// 14:在庫ステータス１:通常、２:限定品、３:保留品、４:不良品
			csvLine[OBD_INV_STATUS] = reverse != null && reverse.length > 87 ? reverse[87] : "1";
			// 15:サンプル商品区分０：製品１：小分※データ種別＝１（サンプル）の場合、必須
			csvLine[OBD_SKU_TYPE_DIV] = reverse != null && reverse.length > 81 ? reverse[81] : "";
			// 16:サンプル小分商品コード
			csvLine[OBD_SMP_SKU_CD] = reverse != null && reverse.length > 83 ? reverse[83] : "";
			// 17:出荷・出庫数（基本）
			Double obdQty = (Double)detailObd[10] == null ? 0D : (Double)detailObd[10];
			csvLine[OBD_QTY] = String.format("%.2f", DoubleUtil.round(obdQty, 2));
			// 18:賞味期限
			csvLine[OBD_EXP_DATE] = ((String)detailObd[3]) == null ? "0" : (String)detailObd[3] ;
			// 19:得意先コード（入庫倉庫コード） 
			csvLine[OBD_CUST_CD] = reverse != null && reverse.length > 43 ? reverse[43] : "";
			
			csvList.add(csvLine);
			// 明细状态修改
			Long obdDetailId = ((BigInteger)detailObd[1]).longValue();
			OutboundDeliveryDetail obdDetail = commonDao.load(OutboundDeliveryDetail.class, obdDetailId);
			obdDetail.setIsReported(Boolean.TRUE);
			
			Long obdId = ((BigInteger)detailObd[0]).longValue();
			obdIds.add(obdId);
			
			// 登记到FileImport4WmsDetail里
			FileImport4WmsDetail fileImportDetail = new FileImport4WmsDetail();
			fileImportDetail.setFileImport4Wms(fileImport4Wms);
			fileImportDetail.setRelatedClassName(OutboundDeliveryDetail.class.getSimpleName());
			fileImportDetail.setRelatedId(obdDetailId);
			commonDao.store(fileImportDetail);
		}
		
		// 订单状态修改
		for( Long obdId : obdIds ){
			OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
			obd.setReportStatus(EnuAsnReportStatus.STATUS999);//報告済
			commonDao.store(obd);
			// orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS999);
		}
		
		// 生成文件
		String fileName = "WP_RSHKAJ_" + fileImport4Wms.getOwner().getWhse() + ".CSV";
		String systemFileName = "OBD2_OUT_" + fileImport4Wms.getOwner().getCode() + 
				"_" + DateUtil.getStringDate(new Date(), DateUtil.LONG_DATE_FORMAT) + ".CSV";
		String hql2 = "select value from GlobalParam where paramId = :paramId ";
		String fileBasePath = (String)commonDao.findByQueryUniqueResult(hql2, new String[]{"paramId"}, new Object[]{"tempFileDir"});
		String systemPath = fileBasePath + File.separator + "dataFile" + File.separator 
				+  fileImport4Wms.getOwner().getCode() + File.separator
				+ DateUtil.getStringDate(new Date(), DateUtil.PURE_DATE_FORMAT) + File.separator;
		CsvPreference csvPre = new CsvPreference('\'', '\t', "\r\n");
		
		FileUtil4Jp.createMultiPath(systemPath);
		FileUtil4Jp.writeCSVFile(csvPre, csvList.toArray(new String[csvList.size()][OBD_LENGTH]), 
				systemPath + systemFileName, FileUtil4Jp.ENCODE_MS932);
		
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(fileName);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(systemPath + systemFileName);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return "出荷実績データを出力しました。" + csvList.size();
	}
	
	private List<Object[]> getOutboundReportDate( Long whId, Long ownerId, String otType ){
		StringBuffer sql = new StringBuffer();
		sql.append(" select  ");
		sql.append(" obd.id as obdId ");
		sql.append(" ,min(obdDetail.id) as obdDetailId  ");
		sql.append(" ,min(obdDetail.ext_string1) as reverse ");
		sql.append(" ,case when sku.use_expire = false then null else quant.displot end as expDate  ");
		sql.append(" ,obdDetail.ext_string2 as orderNo   ");
		sql.append(" ,obdDetail.line_no as lineNo   ");
		sql.append(" ,min(obdDetail.sub_line_no) as subLineNo  ");
		sql.append(" ,(select min(wts.related_bii1) from wms_warehouse_task_single wts where wts.obd_id = obd.id ) as okuri  ");
		sql.append(" ,obd.etd as atd  ");
		sql.append(" ,sku.code as skuCode ");
		sql.append(" ,case when sku.stock_div <> 1 then sum(obdDetail.allocate_qty) else sum(wt.plan_qty) * sku.base_checkqty_4ns end as qty ");
		sql.append(" ,c.code as carrierCd ");
		sql.append(" from wms_outbound_delivery_detail obdDetail  ");
		sql.append(" left join wms_sku sku on obdDetail.sku_id = sku.id  ");
		sql.append(" left join wms_outbound_delivery obd on obdDetail.obd_id = obd.id  ");
		sql.append(" left join wms_carrier c on c.id = obd.carrier_id  ");
		sql.append(" left join wms_order_type ot on ot.id = obd.order_type_id  ");
		sql.append(" left join wms_warehouse_task wt on wt.obd_detail_id = obdDetail.id  ");
		sql.append(" left join wms_quant quant on wt.quant_id = quant.id");
		sql.append(" where   ");
		sql.append(" obd.wh_id = ?");
		sql.append(" and obd.owner_id = ? ");
		// 部分分配
		sql.append(" and obd.status > 210 and obd.status < 999 and (obd.report_status = 800 or obd.report_status is null)  ");
		sql.append(" and to_char(obd.etd,'YYYYMMDD') <= ? ");
		sql.append(" and obdDetail.ext_string1 is not null and obd.edi_data = true");
		sql.append(" and ot.code = ?");
		sql.append(" group by   ");
		sql.append(" obd.id ");
		sql.append(" ,obdDetail.ext_string2");
		sql.append(" ,obdDetail.line_no  ");
		sql.append(" ,sku.id  ");
		sql.append(" ,quant.displot");
		sql.append(" ,c.code  ");
		sql.append(" order by  ");
		sql.append(" obdDetail.ext_string2  ");
		sql.append(" ,obdDetail.line_no  ");
		sql.append(" ,min(obdDetail.sub_line_no)  ");
		
		List<Object[]> detailIds = commonDao.findBySqlQuery(sql.toString(), 
				new Object[]{ whId, ownerId, DateUtil.getStringDate(new Date(), DateUtil.PURE_DATE_FORMAT) ,otType });
		return detailIds;
	}
	
	
	// 出庫実績データ
	@Override
	public String createObd2OutportFile(FileImport4Wms fileImport4Wms) {
		List<Object[]> detailIds = getOutboundReportDate(fileImport4Wms.getWh().getId(), fileImport4Wms.getOwner().getId(), WmsConstant4Ktw.OBD02);
		
		// 如果没有可以回到的数据，返回Null
		if( detailIds == null || detailIds.size() == 0 )
			return null;
		
		List<String[]> csvList = new ArrayList<String[]>( detailIds.size() );
		Set<Long> obdIds = new HashSet<Long>();
		String nowOrder = "";
		int nowLineNo = 1;
		int nowsubLineNo = 1;
		
		for( Object[] detailObd : detailIds ){
			
			String[] reverse = (String)detailObd[2] == null ? null : ((String)detailObd[2]).split(",");
			
			String orderSeq = (String)detailObd[4];
			int lineNo = ((Double)detailObd[5]).intValue();
			int subLineNo = ((Integer)detailObd[6]).intValue();
			
			if( nowOrder.equals(orderSeq) && nowLineNo == lineNo ){
				nowsubLineNo++;
			}
			else{
				nowOrder = orderSeq;
				nowLineNo = lineNo;
				nowsubLineNo = subLineNo;
			}
			
			String[] csvLine = new String[OBD_LENGTH];
			// 0:出荷・出庫倉庫コード
			csvLine[OBD_WH_CD] = fileImport4Wms.getOwner().getWhse();
			// 1:出荷・出庫区分１：出荷２：出庫３：移動
			csvLine[OBD_ORDER_DIV] = reverse != null && reverse.length > 1 ? reverse[1] : "2";
			// 2:データ種別０：通常１：サンプル
			csvLine[OBD_DATA_DIV] = reverse != null && reverse.length > 2 ? reverse[2] : "0";
			// 3:出荷・出庫日
			csvLine[OBD_SHIP_DATE] = DateUtil.getStringDate((Date)detailObd[8], DateUtil.PURE_DATE_FORMAT);
			// 4:伝票ＮＯ
			csvLine[OBD_ORDER_NO] = (String)detailObd[4];
			// 5:伝票行ＮＯ
			csvLine[OBD_LINE_NO] = String.valueOf(((Double)detailObd[5]).intValue());
			// 6:伝票行ＮＯ枝番
			csvLine[OBD_SUB_LINE_NO] = String.valueOf(nowsubLineNo);
			// 7:専用伝票区分０：自社１：専用２：ＣＳ ３：ＥＯＳ
			csvLine[OBD_BILL_DIV] = reverse != null && reverse.length > 33 ? reverse[33] : "";
			// 8:専用伝票ＮＯ
			csvLine[OBD_BILL_NO] = (String)detailObd[4];// 専用伝票を発行しない場合は、専用伝票№【項№9】には伝票№と同値をセットして返信して下さい。
			// 9:運送便コード
			csvLine[OBD_CARRIER_CD] = (String)detailObd[11] == null ? "" : (String)detailObd[11];
			// 10:送り状ＮＯ
			String okuriNo = (String)detailObd[7] == null ? "" : ((String)detailObd[7]).replaceAll("-", "");
			csvLine[OBD_DELIVERY_BILL_NO] = StringUtil4Jp.fillFormat(okuriNo, 15, '0', 2);
			// 11:寄託者管理ＮＯ
			csvLine[OBD_WH_ORDER_NO] = "";
			// 12:寄託者管理行ＮＯ
			csvLine[OBD_WH_LINE_NO] = "";
			// 13:商品コード
			csvLine[OBD_SKU_CD] = reverse != null && reverse.length > 82 ? reverse[82] : (String)detailObd[9];
			// 14:在庫ステータス１:通常、２:限定品、３:保留品、４:不良品
			csvLine[OBD_INV_STATUS] = reverse != null && reverse.length > 87 ? reverse[87] : "1";
			// 15:サンプル商品区分０：製品１：小分※データ種別＝１（サンプル）の場合、必須
			csvLine[OBD_SKU_TYPE_DIV] = reverse != null && reverse.length > 81 ? reverse[81] : "";
			// 16:サンプル小分商品コード
			csvLine[OBD_SMP_SKU_CD] = reverse != null && reverse.length > 83 ? reverse[83] : "";
			// 17:出荷・出庫数（基本）
			Double obdQty = (Double)detailObd[10] == null ? 0D : (Double)detailObd[10];
			csvLine[OBD_QTY] = String.format("%.2f", DoubleUtil.round(obdQty, 2));
			// 18:賞味期限
			csvLine[OBD_EXP_DATE] = ((String)detailObd[3]) == null ? "0" : (String)detailObd[3] ;
			// 19:得意先コード（入庫倉庫コード） 
			csvLine[OBD_CUST_CD] = reverse != null && reverse.length > 5 ? reverse[5] : "";
			
			csvList.add(csvLine);
			// 明细状态修改
			Long obdDetailId = ((BigInteger)detailObd[1]).longValue();
			OutboundDeliveryDetail obdDetail = commonDao.load(OutboundDeliveryDetail.class, obdDetailId);
			obdDetail.setIsReported(Boolean.TRUE);
			//obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS999);
			
			Long obdId = ((BigInteger)detailObd[0]).longValue();
			obdIds.add(obdId);
			
			// 登记到FileImport4WmsDetail里
			FileImport4WmsDetail fileImportDetail = new FileImport4WmsDetail();
			fileImportDetail.setFileImport4Wms(fileImport4Wms);
			fileImportDetail.setRelatedClassName(OutboundDeliveryDetail.class.getSimpleName());
			fileImportDetail.setRelatedId(obdDetailId);
			commonDao.store(fileImportDetail);
		}
		
		// 订单状态修改
		for( Long obdId : obdIds ){
			OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
			obd.setReportStatus(EnuAsnReportStatus.STATUS999);//報告済
			commonDao.store(obd);
			// orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS999);
		}
		
		// 生成文件
		String fileName = "WP_RSHKOJ_" + fileImport4Wms.getOwner().getWhse() + ".CSV";
		String systemFileName = "OBD2_OUT_" + fileImport4Wms.getOwner().getCode() + 
				"_" + DateUtil.getStringDate(new Date(), DateUtil.LONG_DATE_FORMAT) + ".CSV";
		String hql2 = "select value from GlobalParam where paramId = :paramId ";
		String fileBasePath = (String)commonDao.findByQueryUniqueResult(hql2, new String[]{"paramId"}, new Object[]{"tempFileDir"});
		String systemPath = fileBasePath + File.separator + "dataFile" + File.separator 
				+  fileImport4Wms.getOwner().getCode() + File.separator
				+ DateUtil.getStringDate(new Date(), DateUtil.PURE_DATE_FORMAT) + File.separator;
		CsvPreference csvPre = new CsvPreference('\'', '\t', "\r\n");
		
		FileUtil4Jp.createMultiPath(systemPath);
		FileUtil4Jp.writeCSVFile(csvPre, csvList.toArray(new String[csvList.size()][OBD_LENGTH]), 
				systemPath + systemFileName, FileUtil4Jp.ENCODE_MS932);
		
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(fileName);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(systemPath + systemFileName);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return "出庫実績データを出力しました。" + csvList.size();
	}
	
	// 送り状紐付けデータ
	@Override
	public String createObd3OutportFile(FileImport4Wms fileImport4Wms) {
		
		// 查到可以会接口的数据
		StringBuffer sql = new StringBuffer("select ");
		sql.append(" distinct wts.related_bii2 as okuri,");
		sql.append(" obd.related_bill3 as den,");
		sql.append(" to_char(obd.etd,'YYYYMMDD') as atd,");
		sql.append(" obd.id");
		sql.append(" from wms_warehouse_task_single wts");
		sql.append(" left join wms_warehouse_task wt on wts.wt_id = wt.id");
		sql.append(" left join wms_outbound_delivery_detail obdDetail on wt.obd_detail_id = obdDetail.id");
		sql.append(" left join wms_outbound_delivery obd on obdDetail.obd_id = obd.id");
		sql.append(" left join wms_order_type ot on ot.id = obd.order_type_id");
		sql.append(" left join wms_carrier carrier on carrier.id = obd.carrier_id");
		sql.append(" where obd.status > 210 and ot.code = ? "
				+ " and (obd.IS_REPORTED = false or obd.IS_REPORTED is null) "
				+ " and obd.owner_id = ? "
				+ " and obd.wh_id = ? "
				+ " and carrier.code in (?,?) "
				+ " and to_char(obd.etd,'YYYYMMDD') <= ? ");
		sql.append(" order by obd.related_bill3");
		
		List<Object[]> obdInfos = commonDao.findBySqlQuery(sql.toString(), 
				new Object[]{WmsConstant4Ktw.OBD01, fileImport4Wms.getOwner().getId(), 
			fileImport4Wms.getWh().getId(), "8", "81", DateUtil.getStringDate(new Date(), DateUtil.PURE_DATE_FORMAT) });
		
		// 如果没有可以回到的数据，返回Null
		if( obdInfos == null || obdInfos.size() == 0 )
			return null;
		
		List<String[]> csvListData = new ArrayList<String[]>( obdInfos.size() );
		
		String nowDen = "000";
		List<Object[]> nowObdInfo = new ArrayList<Object[]>();
		Set<Long> obdIds = new HashSet<Long>();
		for( Object[] obdInfo : obdInfos ){
			String den = (String)obdInfo[1];
			obdIds.add(((BigInteger)obdInfo[3]).longValue());
			if( !nowDen.equals(den) ){
				if( nowObdInfo.size() > 0 ){
					List<String[]> csvLines = getOkuriCsvLine(nowDen, nowObdInfo );
					csvListData.addAll(csvLines);
				}
				nowDen = den;
				nowObdInfo = new ArrayList<Object[]>();
			}
			nowObdInfo.add(obdInfo);
		}
		
		if( nowObdInfo.size() > 0 ){
			List<String[]> csvLines = getOkuriCsvLine(nowDen, nowObdInfo );
			csvListData.addAll(csvLines);
		}
		
		if( obdIds != null && obdIds.size() > 0 ){
			for( Long obdId : obdIds ){
				OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
				obd.setIsReported(Boolean.TRUE);
				commonDao.store(obd);
				
				// 登记到FileImport4WmsDetail里
				FileImport4WmsDetail fileImportDetail = new FileImport4WmsDetail();
				fileImportDetail.setFileImport4Wms(fileImport4Wms);
				fileImportDetail.setRelatedClassName(OutboundDelivery.class.getSimpleName());
				fileImportDetail.setRelatedId(obdId);
				commonDao.store(fileImportDetail);
			}
		}
		
		// 生成文件
		String fileName = "WP_ROKUNO_" + fileImport4Wms.getOwner().getWhse() + ".TXT";
		String systemFileName = "OBD3_OUT_" + fileImport4Wms.getOwner().getCode() + "_" + DateUtil.getStringDate(new Date(), DateUtil.LONG_DATE_FORMAT) + ".TXT";
		String hql2 = "select value from GlobalParam where paramId = :paramId ";
		String fileBasePath = (String)commonDao.findByQueryUniqueResult(hql2, new String[]{"paramId"}, new Object[]{"tempFileDir"});
		String systemPath = fileBasePath + File.separator + "dataFile" + File.separator 
				+ fileImport4Wms.getOwner().getCode() + File.separator
				+ DateUtil.getStringDate(new Date(), DateUtil.PURE_DATE_FORMAT) + File.separator;
		
		FileUtil4Jp.createMultiPath(systemPath);
		FileUtil4Jp.writeTXTStream(csvListData.toArray(new String[csvListData.size()][1]), systemPath + systemFileName, FileUtil4Jp.ENCODE_MS932);
		
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(fileName);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(systemPath + systemFileName);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return "送り状紐付けデータを出力しました。" + csvListData.size();
	}
	
	private List<String[]> getOkuriCsvLine( String den, List<Object[]> nowObdInfo ){
		List<String[]> result = new ArrayList<String[]>();		
		String[] dens = den.split(",");
		
		int k = dens.length > nowObdInfo.size() ? dens.length : nowObdInfo.size();
		for( int m = 0 ; m < k ; m++ ){
			int i = m < dens.length -1 ? m : dens.length -1;
			int j = m < nowObdInfo.size() -1 ? m : nowObdInfo.size() -1;
			
			String line = StringUtil4Jp.fillFormat(dens[i], 7, ' ', 2) + 
					StringUtil4Jp.fillFormat((String)nowObdInfo.get(j)[0], 12, ' ', 2) + 
					(String)nowObdInfo.get(j)[2];
			result.add(new String[]{line});
		}
		
		return result;
	}
	
	// 出荷実績データ取り消し
	@Override
	public String cancelObdOutportFile(FileImport4Wms fileImport4Wms) {
		return cancelObdOutportDate(fileImport4Wms);
	}
	
	// 出庫実績データ取り消し
	@Override
	public String cancelObd2OutportFile(FileImport4Wms fileImport4Wms) {
		return cancelObdOutportDate(fileImport4Wms);
	}

	// 送り状紐付けデータ取り消し
	@Override
	public String cancelObd3OutportFile(FileImport4Wms fileImport4Wms) {
		String hql = "select detail.id from FileImport4WmsDetail detail where detail.fileImport4Wms.id = :fileId ";
		List<Long> fileImportDetailIds = commonDao.findByQuery(hql, "fileId", fileImport4Wms.getId());
		
		if( fileImportDetailIds != null && fileImportDetailIds.size() > 0 ){
			for( Long fileImportDetailId : fileImportDetailIds ){
				FileImport4WmsDetail detail = commonDao.load(FileImport4WmsDetail.class, fileImportDetailId);
				Long obdId = detail.getRelatedId();
				
				OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
				obd.setIsReported(Boolean.FALSE);
				commonDao.store(obd);
				commonDao.delete(detail);
			}
		}
		
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(null);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(null);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return null;
	}
	
	private String cancelObdOutportDate(FileImport4Wms fileImport4Wms){
		String hql = "select detail.id from FileImport4WmsDetail detail where detail.fileImport4Wms.id = :fileId ";
		List<Long> fileImportDetailIds = commonDao.findByQuery(hql, "fileId", fileImport4Wms.getId());
		
		Set<Long> obdIds = new HashSet<Long>();
		if( fileImportDetailIds != null && fileImportDetailIds.size() > 0 ){
			for( Long fileImportDetailId : fileImportDetailIds ){
				FileImport4WmsDetail detail = commonDao.load(FileImport4WmsDetail.class, fileImportDetailId);
				Long obdDetailId = detail.getRelatedId();
				
				OutboundDeliveryDetail obdDetail = commonDao.load(OutboundDeliveryDetail.class, obdDetailId);
				obdDetail.setIsReported(Boolean.FALSE);
//				if( obdDetail.getUnExecuteQty() <= 0 ){
//					obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS900);
//				}
//				else{
//					obdDetail.setStatus(EnuOutboundDeliveryDetailStatus.STATUS910);
//				}
				commonDao.store(obdDetail);
				obdIds.add(obdDetail.getObd().getId());
				
				commonDao.delete(detail);
			}
		}
		
		// 订单状态修改
		for( Long obdId : obdIds ){
			OutboundDelivery obd = commonDao.load(OutboundDelivery.class, obdId);
			obd.setReportStatus(EnuAsnReportStatus.STATUS800);
//			if( obd.getUnExecuteQty() <= 0 ){
//				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS900);
//			}
//			else{
//				orderStatusHelper.changeStatus(obd, EnuOutboundDeliveryStatus.STATUS910);
//			}
			commonDao.store(obd);
		}
		
		// 修改FileImport
		fileImport4Wms.getFileImport().setOrginalName(null);// 表示文件名
		fileImport4Wms.getFileImport().setTargetName(null);
		fileImport4Wms.getFileImport().setUpdateInfo(new  UpdateInfo(UserHolder.getUser()));
		commonDao.store(fileImport4Wms.getFileImport());
		
		// 返回信息
		return null;
	}
	
}
