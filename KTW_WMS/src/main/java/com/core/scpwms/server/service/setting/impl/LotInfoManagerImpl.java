/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : LotInfoManagerImpl.java
 */

package com.core.scpwms.server.service.setting.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.MethodUtils;

import com.core.business.exception.BusinessException;
import com.core.business.model.EntityFactory;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.model.Entity;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.ui.lot.ClientLotInfo;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuLotFieldType;
import com.core.scpwms.server.enumerate.EnuLotFormat;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.lot.LotConstant;
import com.core.scpwms.server.model.lot.LotInfo;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.service.setting.LotInfoManager;

/**
 * @description 批次属性设定
 * @author MBP:毛幸<br/>
 * @createDate 2013-2-20
 * @version V1.0<br/>
 */
public class LotInfoManagerImpl extends DefaultBaseManager implements
		LotInfoManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.LotInfoManager#deleteLotInfo(java
	 * .util.List)
	 */
	@Override
	public void deleteLotInfo(List<Long> ids) {
		for (Long id : ids) {
			// 是否已经绑定了商品
			List<Long> skuIds = commonDao
					.findByQuery(
							"select sku.id from Sku sku where sku.properties.lotInfo.id = :lotId ",
							"lotId", id);

			if (skuIds != null && skuIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			delete(load(LotInfo.class, id));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.LotInfoManager#disableLotInfo(
	 * java.util.List)
	 */
	@Override
	public void disableLotInfo(List<Long> ids) {
		for (Long id : ids) {
			LotInfo lotInfo = load(LotInfo.class, id);
			lotInfo.setDisabled(Boolean.TRUE);
			lotInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(lotInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.LotInfoManager#enableLotInfo(java
	 * .util.List)
	 */
	@Override
	public void enableLotInfo(List<Long> ids) {
		for (Long id : ids) {
			LotInfo lotInfo = load(LotInfo.class, id);
			lotInfo.setDisabled(Boolean.FALSE);
			lotInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(lotInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.LotInfoManager#saveLotInfo(com
	 * .core.scpwms.server.model.lot.LotInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveLotInfo(LotInfo lotInfo) {
		if (lotInfo.isNew()) {

			// 判断是否重复记录
			StringBuffer hql = new StringBuffer(
					"from LotInfo lot where lower(lot.code) = :lotCode");

			List<LotInfo> lotList = commonDao.findByQuery(hql.toString(),
					new String[] { "lotCode" }, new Object[] { lotInfo
							.getCode().toLowerCase() });

			if (!lotList.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}

			lotInfo.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		} else {
			lotInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		commonDao.store(lotInfo);
	}

	// ActiveT 1304901 2014/07/09
	@Override
	public Map loadLotInfo(Map params) {
		Map retMap = new HashMap();
		// 批次属性如果是下拉框，则去取得下拉框内容
		Map<String, LinkedHashMap<String, String>> listInfos = new HashMap<String, LinkedHashMap<String, String>>();
		// 批次属性返回
		Map<String, ClientLotInfo> lotInfos = new HashMap<String, ClientLotInfo>();
		Long skuId = (Long) params.get("id");
		Sku sku = this.commonDao.load(Sku.class, skuId);
		if (sku.getProperties() != null
				&& sku.getProperties().getLotInfo() != null) {
			LotInfo lotInfo = sku.getProperties().getLotInfo();

			StringBuffer contentListHsql = new StringBuffer();
			contentListHsql
					.append("select led.code,led.name from LotEnumerateDetail led where led.lotEnumerate.enuCode=:enucode order by led.lineNo");

			// 如果批次属性不是禁用
			if (lotInfo.getLdi1() != null
					&& !EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotInfo
							.getLdi1().getInputType())) {
				lotInfos.put(LotConstant.LOT_1, lotInfo.getLdi1()
						.convertToClient());

				if (EnuLotFormat.LOTFORMAT_LIST.equalsIgnoreCase(lotInfo
						.getLdi1().getLotType())) {
					// 如果批次属性是下拉框，下拉框内容取得，放入listInfos
					List<Object[]> contentList = this.commonDao.findByQuery(
							contentListHsql.toString(),
							new String[] { "enucode" }, new Object[] { lotInfo
									.getLdi1().getEnuCode() });
					if (contentList != null && !contentList.isEmpty()) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < contentList.size(); i++) {
							Object[] values = (Object[]) contentList.get(i);
							// valueMap.put(values[0].toString(),
							// values[1].toString());
							valueMap.put(values[1].toString(),
									values[1].toString());
						}
						listInfos.put(LotConstant.LOT_1, valueMap);
					}
				}
			}

			// 如果批次属性不是禁用
			if (lotInfo.getLdi2() != null
					&& !EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotInfo
							.getLdi2().getInputType())) {
				lotInfos.put(LotConstant.LOT_2, lotInfo.getLdi2()
						.convertToClient());
				if (EnuLotFormat.LOTFORMAT_LIST.equalsIgnoreCase(lotInfo
						.getLdi2().getLotType())) {
					// 如果批次属性是下拉框，下拉框内容取得，放入listInfos
					List<Object[]> contentList = this.commonDao.findByQuery(
							contentListHsql.toString(),
							new String[] { "enucode" }, new Object[] { lotInfo
									.getLdi2().getEnuCode() });
					if (contentList != null && !contentList.isEmpty()) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < contentList.size(); i++) {
							Object[] values = (Object[]) contentList.get(i);
							// valueMap.put(values[0].toString(),
							// values[1].toString());
							valueMap.put(values[1].toString(),
									values[1].toString());
						}
						listInfos.put(LotConstant.LOT_2, valueMap);
					}
				}
			}

			// 如果批次属性不是禁用
			if (lotInfo.getLdi3() != null
					&& !EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotInfo
							.getLdi3().getInputType())) {
				lotInfos.put(LotConstant.LOT_3, lotInfo.getLdi3()
						.convertToClient());
				if (EnuLotFormat.LOTFORMAT_LIST.equalsIgnoreCase(lotInfo
						.getLdi3().getLotType())) {
					// 如果批次属性是下拉框，下拉框内容取得，放入listInfos
					List<Object[]> contentList = this.commonDao.findByQuery(
							contentListHsql.toString(),
							new String[] { "enucode" }, new Object[] { lotInfo
									.getLdi3().getEnuCode() });
					if (contentList != null && !contentList.isEmpty()) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < contentList.size(); i++) {
							Object[] values = (Object[]) contentList.get(i);
							// valueMap.put(values[0].toString(),
							// values[1].toString());
							valueMap.put(values[1].toString(),
									values[1].toString());
						}
						listInfos.put(LotConstant.LOT_3, valueMap);
					}
				}
			}

			// 如果批次属性不是禁用
			if (lotInfo.getLdi4() != null
					&& !EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotInfo
							.getLdi4().getInputType())) {
				lotInfos.put(LotConstant.LOT_4, lotInfo.getLdi4()
						.convertToClient());
				if (EnuLotFormat.LOTFORMAT_LIST.equalsIgnoreCase(lotInfo
						.getLdi4().getLotType())) {
					// 如果批次属性是下拉框，下拉框内容取得，放入listInfos
					List<Object[]> contentList = this.commonDao.findByQuery(
							contentListHsql.toString(),
							new String[] { "enucode" }, new Object[] { lotInfo
									.getLdi4().getEnuCode() });
					if (contentList != null && !contentList.isEmpty()) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < contentList.size(); i++) {
							Object[] values = (Object[]) contentList.get(i);
							// valueMap.put(values[0].toString(),
							// values[1].toString());
							valueMap.put(values[1].toString(),
									values[1].toString());
						}
						listInfos.put(LotConstant.LOT_4, valueMap);
					}
				}
			}

			// 如果批次属性不是禁用
			if (lotInfo.getLdi5() != null
					&& !EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotInfo
							.getLdi5().getInputType())) {
				lotInfos.put(LotConstant.LOT_5, lotInfo.getLdi5()
						.convertToClient());
				if (EnuLotFormat.LOTFORMAT_LIST.equalsIgnoreCase(lotInfo
						.getLdi5().getLotType())) {
					// 如果批次属性是下拉框，下拉框内容取得，放入listInfos
					List<Object[]> contentList = this.commonDao.findByQuery(
							contentListHsql.toString(),
							new String[] { "enucode" }, new Object[] { lotInfo
									.getLdi5().getEnuCode() });
					if (contentList != null && !contentList.isEmpty()) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < contentList.size(); i++) {
							Object[] values = (Object[]) contentList.get(i);
							// valueMap.put(values[0].toString(),
							// values[1].toString());
							valueMap.put(values[1].toString(),
									values[1].toString());
						}
						listInfos.put(LotConstant.LOT_5, valueMap);
					}
				}
			}

			// 如果批次属性不是禁用
			if (lotInfo.getLdi6() != null
					&& !EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotInfo
							.getLdi6().getInputType())) {
				lotInfos.put(LotConstant.LOT_6, lotInfo.getLdi6()
						.convertToClient());
				if (EnuLotFormat.LOTFORMAT_LIST.equalsIgnoreCase(lotInfo
						.getLdi6().getLotType())) {
					// 如果批次属性是下拉框，下拉框内容取得，放入listInfos
					List<Object[]> contentList = this.commonDao.findByQuery(
							contentListHsql.toString(),
							new String[] { "enucode" }, new Object[] { lotInfo
									.getLdi6().getEnuCode() });
					if (contentList != null && !contentList.isEmpty()) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < contentList.size(); i++) {
							Object[] values = (Object[]) contentList.get(i);
							// valueMap.put(values[0].toString(),
							// values[1].toString());
							valueMap.put(values[1].toString(),
									values[1].toString());
						}
						listInfos.put(LotConstant.LOT_6, valueMap);
					}
				}
			}

			// 如果批次属性不是禁用
			if (lotInfo.getLdi7() != null
					&& !EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotInfo
							.getLdi7().getInputType())) {
				lotInfos.put(LotConstant.LOT_7, lotInfo.getLdi7()
						.convertToClient());
				if (EnuLotFormat.LOTFORMAT_LIST.equalsIgnoreCase(lotInfo
						.getLdi7().getLotType())) {
					// 如果批次属性是下拉框，下拉框内容取得，放入listInfos
					List<Object[]> contentList = this.commonDao.findByQuery(
							contentListHsql.toString(),
							new String[] { "enucode" }, new Object[] { lotInfo
									.getLdi7().getEnuCode() });
					if (contentList != null && !contentList.isEmpty()) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < contentList.size(); i++) {
							Object[] values = (Object[]) contentList.get(i);
							// valueMap.put(values[0].toString(),
							// values[1].toString());
							valueMap.put(values[1].toString(),
									values[1].toString());
						}
						listInfos.put(LotConstant.LOT_7, valueMap);
					}
				}
			}

			// 如果批次属性不是禁用
			if (lotInfo.getLdi8() != null
					&& !EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotInfo
							.getLdi8().getInputType())) {
				lotInfos.put(LotConstant.LOT_8, lotInfo.getLdi8()
						.convertToClient());
				if (EnuLotFormat.LOTFORMAT_LIST.equalsIgnoreCase(lotInfo
						.getLdi8().getLotType())) {
					// 如果批次属性是下拉框，下拉框内容取得，放入listInfos
					List<Object[]> contentList = this.commonDao.findByQuery(
							contentListHsql.toString(),
							new String[] { "enucode" }, new Object[] { lotInfo
									.getLdi8().getEnuCode() });
					if (contentList != null && !contentList.isEmpty()) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < contentList.size(); i++) {
							Object[] values = (Object[]) contentList.get(i);
							// valueMap.put(values[0].toString(),
							// values[1].toString());
							valueMap.put(values[1].toString(),
									values[1].toString());
						}
						listInfos.put(LotConstant.LOT_8, valueMap);
					}
				}
			}

			// 如果批次属性不是禁用
			if (lotInfo.getLdi9() != null
					&& !EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotInfo
							.getLdi9().getInputType())) {
				lotInfos.put(LotConstant.LOT_9, lotInfo.getLdi9()
						.convertToClient());
				if (EnuLotFormat.LOTFORMAT_LIST.equalsIgnoreCase(lotInfo
						.getLdi9().getLotType())) {
					// 如果批次属性是下拉框，下拉框内容取得，放入listInfos
					List<Object[]> contentList = this.commonDao.findByQuery(
							contentListHsql.toString(),
							new String[] { "enucode" }, new Object[] { lotInfo
									.getLdi9().getEnuCode() });
					if (contentList != null && !contentList.isEmpty()) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < contentList.size(); i++) {
							Object[] values = (Object[]) contentList.get(i);
							// valueMap.put(values[0].toString(),
							// values[1].toString());
							valueMap.put(values[1].toString(),
									values[1].toString());
						}
						listInfos.put(LotConstant.LOT_9, valueMap);
					}
				}
			}

			// 如果批次属性不是禁用
			if (lotInfo.getLdi10() != null
					&& !EnuLotFieldType.LOTFIELD_1.equalsIgnoreCase(lotInfo
							.getLdi10().getInputType())) {
				lotInfos.put(LotConstant.LOT_10, lotInfo.getLdi10()
						.convertToClient());
				if (EnuLotFormat.LOTFORMAT_LIST.equalsIgnoreCase(lotInfo
						.getLdi10().getLotType())) {
					// 如果批次属性是下拉框，下拉框内容取得，放入listInfos
					List<Object[]> contentList = this.commonDao.findByQuery(
							contentListHsql.toString(),
							new String[] { "enucode" }, new Object[] { lotInfo
									.getLdi10().getEnuCode() });
					if (contentList != null && !contentList.isEmpty()) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
						for (int i = 0; i < contentList.size(); i++) {
							Object[] values = (Object[]) contentList.get(i);
							// valueMap.put(values[0].toString(),
							// values[1].toString());
							valueMap.put(values[1].toString(),
									values[1].toString());
						}
						listInfos.put(LotConstant.LOT_10, valueMap);
					}
				}
			}
		}
		retMap.put("LOT_MAP", lotInfos);
		retMap.put("LIST_MAP", listInfos);

		return retMap;
	}

	public Object getEntityInstance(String className) {
		if (className == null) {
			return null;
		}

		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return EntityFactory.getEntity(clazz);
	}

	@Override
	public Map loadDetailInfo(Map params) {
		Map retMap = new HashMap();
		Long id = new Long(params.get("id").toString());
		String enClass = params.get("className").toString();

		Entity detailEntity = this.commonDao.load(
				((Entity) getEntityInstance(enClass)).getClass(), id);
		// AsnDetail detailEntity = (AsnDetail)
		// this.commonDao.load(((Entity)getEntityInstance(enClass)).getClass(),
		// id);
		/*
		 * System.out.println("XXXXXXXXXXXX");
		 * System.out.println(detailEntity.getShit());
		 * System.out.println("XXXXXXXXXXXX");
		 */
		try {
			// Method detailMethod =
			// detailEntity.getClass().getMethod("getLotData");
			Method detailMethod = MethodUtils.getAccessibleMethod(
					detailEntity.getClass(), "getLotData", new Class[0]);
			LotInputData ld = (LotInputData) detailMethod.invoke(detailEntity);
			String[] lotdata = new String[] { ld.getProperty1(),
					ld.getProperty2(), ld.getProperty3(), ld.getProperty4(),
					ld.getProperty5(), ld.getProperty6(), ld.getProperty7(),
					ld.getProperty8(), ld.getProperty9(), ld.getProperty10() };
			retMap.put("lotData", lotdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}

}
