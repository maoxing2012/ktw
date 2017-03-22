package com.core.scpwms.server.service.common.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.enumerate.EnuSeqDateFormat;
import com.core.scpwms.server.enumerate.EnuSeqGenerateType;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.SequenceProperties;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.WmsSequenceDao;

/**
 * 
 * @description 生成番号接口实现类
 * @author MBP:xiaoyan<br/>
 * @createDate 2013-2-26
 * @version V1.0<br/>
 */
@SuppressWarnings("all")
public class BCManagerImpl extends DefaultBaseManager implements BCManager {

	private final WmsSequenceDao sequenceDao;

	private SimpleDateFormat format_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	private SimpleDateFormat format_yyMMdd = new SimpleDateFormat("yyMMdd");
	private SimpleDateFormat format_yyyy = new SimpleDateFormat("yyyy");
	private SimpleDateFormat format_yyyyMM = new SimpleDateFormat("yyyyMM");
	private SimpleDateFormat format_yyMM = new SimpleDateFormat("yyMM");

	public BCManagerImpl(WmsSequenceDao sequenceDao) {
		this.sequenceDao = sequenceDao;
	}

	private synchronized String generateSequence(String key, int number,
			String typeName) {
		String code = sequenceDao.getSequence4Postgres(key, typeName).toString();

		if (code.length() < number) {
			code = StringUtils.repeat("0", number - code.length()) + code;
		}
		return code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.BCManager#getSeqOrderType(java.
	 * lang.Integer, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String getSeqOrderType(Integer seqLength, String prefix,
			String separator1, String seqDateFormat, String separator2,
			String seqGenerateBy, String typeName) {

		// 采番号
		String seqCode = "";
		// 差值
		Integer seqSize = 0;
		seqCode += (prefix + separator1);

		if (seqDateFormat.equals("yyyyMMdd")) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String dayTime = format.format(new Date());
			seqCode += (dayTime + separator2);

		} else if (seqDateFormat.equals("yyMMdd")) {

			SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
			String dayTime = format.format(new Date());
			seqCode += (dayTime + separator2);

		} else if (seqDateFormat.equals("yyyyMM")) {

			SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
			String dayTime = format.format(new Date());
			seqCode += (dayTime + separator2);

		} else if (seqDateFormat.equals("yyMM")) {
			SimpleDateFormat format = new SimpleDateFormat("yyMM");
			String dayTime = format.format(new Date());
			seqCode += (dayTime + separator2);
		}

		if (!"".equals(seqLength)) {
			seqSize = seqLength - seqCode.length();
		}

		return seqCode + generateSequence(seqCode, seqSize, typeName);

	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see
//	 * com.core.scpwms.server.service.common.BCManager#getTaskSeq(java.lang.
//	 * String)
//	 */
//	@Override
//	public synchronized String getTaskSeq(String whCode) {
//		// WT + 10位流水号，仓库内唯一
//		return "WT" + generateSequence(whCode, 10, "WT");
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.BCManager#getWaveSeq(java.lang.
	 * String)
	 */
	@Override
	public synchronized String getWaveSeq(String whCode) {
		// WV + YYMMDD+5位流水号（按日流水）
		return "WV"
				+ format_yyMMdd.format(new Date())
				+ generateSequence(whCode + format_yyMMdd.format(new Date()),
						5, "WV");
	}

	/**
	 * 根据单据类型生成 单据序列号
	 * 
	 * 连番用KEY = 仓库 ID + '_'+ 单据类型ID + '_' + 流水号范围 （年/月/日） 数据库存储
	 * 
	 * SEQUENCE_KEY = 连番用KEY SEQUENCE_TYPE = 单据类型.单据属性.前缀 NEXT_ID = 以
	 * SEQUENCE_KEY + SEQUENCE_TYPE 连番
	 * 
	 * 返回值 = 返回前缀部分 + 返回番号 返回前缀部分 = 单据类型.单据属性.前缀 + 单据类型.单据属性.前缀分隔符 +
	 * 单据类型.单据属性.日期格式 + 单据类型.单据属性.日期后分隔符 + 流水号 返回番号 = NEXT_ID
	 */
	@Override
	public synchronized String getOrderSequence(OrderType iot, Long whId) {

		// 序列号属性
		SequenceProperties sp = iot.getSeqProperties();

		// 连番用KEY
		StringBuffer sbKey = new StringBuffer();
		sbKey.append(whId).append("_").append(iot.getSeqProperties().getId()).append("_");

		// 处理范围，默认按照YYYYMM处理
		if (!StringUtils.isEmpty(sp.getSeqGenerateBy())) {
			if (EnuSeqGenerateType.BY_YYYY.equalsIgnoreCase(sp
					.getSeqGenerateBy())) {
				sbKey.append(format_yyyy.format(new Date()));
			} else if (EnuSeqGenerateType.BY_YYYYMM.equalsIgnoreCase(sp
					.getSeqGenerateBy())) {
				sbKey.append(format_yyyyMM.format(new Date()));
			} else if (EnuSeqGenerateType.BY_YYYYMMDD.equalsIgnoreCase(sp
					.getSeqGenerateBy())) {
				sbKey.append(format_yyyyMMdd.format(new Date()));
			} else {
				sbKey.append(format_yyyyMM.format(new Date()));
			}
		}

		String code = sequenceDao.getSequence4Postgres(sbKey.toString(),
				sp.getPrefix().trim()).toString();

		// 返回部分
		StringBuffer returnValue = new StringBuffer();

		returnValue.append(sp.getPrefix().trim());

		// 前缀分隔符
		if (!StringUtils.isEmpty(sp.getSeparator1())) {
			returnValue.append(sp.getSeparator1().trim());
		}

		// 日期格式
		if (!StringUtils.isEmpty(sp.getSeqDateFormat())) {
			if (EnuSeqDateFormat.YYMM.equalsIgnoreCase(sp.getSeqDateFormat())) {
				returnValue.append(format_yyMM.format(new Date()));
			} else if (EnuSeqDateFormat.YYMMDD.equalsIgnoreCase(sp
					.getSeqDateFormat())) {
				returnValue.append(format_yyMMdd.format(new Date()));
			} else if (EnuSeqDateFormat.YYYYMM.equalsIgnoreCase(sp
					.getSeqDateFormat())) {
				returnValue.append(format_yyyyMM.format(new Date()));
			} else if (EnuSeqDateFormat.YYYYMMDD.equalsIgnoreCase(sp
					.getSeqDateFormat())) {
				returnValue.append(format_yyyyMMdd.format(new Date()));
			}
		}

		// 日期后分隔符
		if (!StringUtils.isEmpty(sp.getSeparator2())) {
			returnValue.append(sp.getSeparator2().trim());
		}

		if (sp.getSeqLength() != null) {
			if (code.length() <= (sp.getSeqLength() - returnValue.toString()
					.length())) {
				code = StringUtils.repeat("0", sp.getSeqLength()
						- returnValue.toString().length() - code.length())
						+ code;
			}
		}

		return returnValue.append(code).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.core.scpwms.server.service.common.BCManager#getLotSeq()
	 */
	@Override
	public String getLotSeq(Long skuId) {
		// skuId + 5位流水号
		return "LOT" + String.valueOf(skuId) + generateSequence(String.valueOf(skuId), 5, "QUANT");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.core.scpwms.server.service.common.BCManager#getVersionSeq()
	 */
	@Override
	public String getVersionSeq() {
		// YYMMDD+3位流水号（按日流水）
		return format_yyMMdd.format(new Date())
				+ generateSequence(format_yyMMdd.format(new Date()), 3, "VER");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.core.scpwms.server.service.common.BCManager#getVersionSeq()
	 */
	@Override
	public Long getVersionIndex( String type ) {
		// YYMMDD+3位流水号（按日流水）
		return Long.parseLong(generateSequence(type, 8, type));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.core.scpwms.server.service.common.BCManager#getVersionSeq()
	 */
	@Override
	public String getCarrierCd() {
		// 6位流水号
		return "C" + generateSequence("Carrier", 6, "Carrier");
	}
	
	@Override
	public String getInventoryId(){
		// 6位流水号
		return generateSequence("InventoryId", 0, "InventoryId");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.BCManager#getPackageSeq(java.lang
	 * .String)
	 */
	@Override
	public String getPackageSeq(String whCode) {
		// BX+8位流水号
		return "BX" + generateSequence(whCode, 8, "BX");
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.core.scpwms.server.service.common.BCManager#getBoxSequence(java.lang.Long)
	 */
	@Override
	public String getBoxSequence(Long whId){
		// 仓库ID  + 4位日期（YYMM） + 6位流水的32位编码
		String date = format_yyMM.format(new Date());
		// 按月流水的6位编码
		String seq = generateSequence(date, 6, "BOX");
		
		Long boxSeq = Long.parseLong(whId.toString() + date + seq) ;
		return boxSeq.toString(boxSeq, 32).toUpperCase();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.BCManager#getSequence(java.lang
	 * .String, int, java.lang.String)
	 */
	@Override
	public String getSequence(String key, int number, String typeName) {
		return generateSequence(key, number, typeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.BCManager#getWarehouseOrderSeq(
	 * java.lang.String)
	 */
	@Override
	public String getWarehouseOrderSeq(String whCode) {
		// WO + YYMMDD+5位流水号（按月流水）
		return "WO"
				+ format_yyMMdd.format(new Date())
				+ generateSequence(whCode + format_yyMM.format(new Date()), 5,
						"WO");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.BCManager#getStowageNumber(java
	 * .lang.String)
	 */
	@Override
	public String getStowageNumber(String whCode) {
		// ST + YYMMDD+3位流水号（按日流水）
		return "ST"
				+ format_yyMMdd.format(new Date())
				+ generateSequence(whCode + format_yyMMdd.format(new Date()), 3,
						"ST");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.BCManager#getAccessoryCode(java
	 * .lang.String)
	 */
	@Override
	public String getAccessoryCode(String whCode) {
		// 从1000开始的流水号
		return String
				.valueOf(sequenceDao.getSequence4Postgres(whCode, "ACCESSORY") + 1000L);
	}

	@Override
	public String getFeeBillCode(String whCode) {
		// ST + YYMMDD+5位流水号（按月流水）
		return "FE"
				+ format_yyMMdd.format(new Date())
				+ generateSequence(whCode + format_yyMM.format(new Date()), 5,
						"FE");
	}

	@Override
	public String getSingleGroupNo(Long waveDoc) {
		// BX+5位流水号
		return waveDoc.toString() + generateSequence(waveDoc.toString(), 5, "SingleGroup");
	}

	@Override
	public String getYamatoSequence(String fromNo, String toNo) {
		if( StringUtils.isEmpty(fromNo) ){
			fromNo = "99999900001";
		}
		
		if( StringUtils.isEmpty(toNo) ){
			toNo = "99999959999";
		}
		
		Long maxInx = Long.parseLong(toNo) - Long.parseLong(fromNo);
		Long nowInx = sequenceDao.getSequence4Postgres("YamatoSequence", "YamatoSequence");
		if( nowInx > maxInx ){
			nowInx = 0L;
			sequenceDao.resetSequence4Postgres("YamatoSequence", "YamatoSequence");
		}
		
		String seq = String.valueOf(Long.parseLong(fromNo) + nowInx);
		return seq + String.valueOf(getCheckDigit7(seq));
	}

	@Override
	public String getFukuyamaSequence(String fromNo, String toNo) {
		// 8888+5位流水号+校验位
		if( StringUtils.isEmpty(fromNo) ){
			fromNo = "888800001";
		}
		
		if( StringUtils.isEmpty(toNo) ){
			toNo = "888859999";
		}
		
		Long maxInx = Long.parseLong(toNo) - Long.parseLong(fromNo);
		Long nowInx = sequenceDao.getSequence4Postgres("FukuyamaSequence", "FukuyamaSequence");
		if( nowInx > maxInx ){
			nowInx = 0L;
			sequenceDao.resetSequence4Postgres("FukuyamaSequence", "FukuyamaSequence");
		}
		
		String seq = String.valueOf(Long.parseLong(fromNo) + nowInx);
		return seq + String.valueOf(getCheckDigit7(seq));
	}
	
	private static int getCheckDigit7( String seq ){
		Long seqLong = Long.parseLong(seq);
		return new Long(seqLong%7).intValue();
	}
	
	public static void main( String[] args ){
		System.out.println( Integer.MAX_VALUE );
		System.out.println( Long.MAX_VALUE );
	}
}
