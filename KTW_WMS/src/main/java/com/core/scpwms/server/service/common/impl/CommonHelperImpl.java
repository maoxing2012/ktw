/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: OrderStatusHelperImpl.java
 */

package com.core.scpwms.server.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.WmsConstant;
import com.core.scpwms.server.domain.AllocatePutawayDetail;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.move.MoveDocDetail;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.common.CommonHelper;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.util.DateUtil;

/**
 * <p>
 * 一些常用的共通方法定义在这里
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/14<br/>
 */
public class CommonHelperImpl extends DefaultBaseManager implements CommonHelper {
	private OrderStatusHelper orderStatusHelper;

	public OrderStatusHelper getOrderStatusHelper() {
		return this.orderStatusHelper;
	}

	public void setOrderStatusHelper(OrderStatusHelper orderStatusHelper) {
		this.orderStatusHelper = orderStatusHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.OtSettingManager#getOrderType(
	 * java.lang.Long, java.lang.String)
	 */
	@Override
	public OrderType getOrderType(String orderTypeCode) {
		String hql = "FROM OrderType ot WHERE ot.code = :orderTypeCode and ot.disabled = false";
		List<OrderType> ots = commonDao.findByQuery(hql, new String[] { "orderTypeCode" }, new Object[] {orderTypeCode });

		if (ots == null || ots.size() == 0) {
			return null;
		}

		return ots.get(0);
	}

	@Override
	public List<AllocatePutawayDetail> getAllocatePutawayDetail(
			InventoryInfo invInfo, String putawayDocSequence, String type) {
		StringBuffer hql = new StringBuffer("select ih.id FROM ");

		if (WmsConstant.PUTAWAY_ASN.equals(type)) {
			hql.append(" InboundHistory ");
		} else if (WmsConstant.PUTAWAY_QC.equals(type)) {
			hql.append(" QualityHistory ");
		} else if (WmsConstant.PUTAWAY_PRO.equals(type)) {
			hql.append(" ProcessHistory ");
		} else if (WmsConstant.PUTAWAY_MOV.equals(type)) {
			hql.append(" MoveDocDetail ");
		} else if (WmsConstant.PUTAWAY_OBD.equals(type)) {
			return null;
		}

		hql.append(" ih left join ih.invInfo.owner WHERE 1=1 ");
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();

		// 单号
		if (WmsConstant.PUTAWAY_MOV.equals(type)) {
			hql.append(" AND ih.doc.putawayDocSequence = :putawayDocSequence ");
		} else {
			hql.append(" AND ih.putawayDocSequence = :putawayDocSequence ");
		}
		paramNames.add("putawayDocSequence");
		params.add(putawayDocSequence);

		// 同Owne
		if (invInfo.getOwner() != null) {
			hql.append("AND ih.invInfo.owner.id = :ownerId ");
			paramNames.add("ownerId");
			params.add(invInfo.getOwner().getId());
		} else {
			hql.append("AND ih.invInfo.owner.id is null ");
		}

		// 同包装
		hql.append("AND ih.invInfo.packageDetail.id = :packId ");
		paramNames.add("packId");
		params.add(invInfo.getPackageDetail().getId());

		// 同QUANT
		hql.append("AND ih.invInfo.quant.id = :quantId ");
		paramNames.add("quantId");
		params.add(invInfo.getQuant().getId());

		// 同源库位
		hql.append("AND ih.invInfo.bin.id = :binId ");
		paramNames.add("binId");
		params.add(invInfo.getBin().getId());

		// 状态
		hql.append("AND ih.invInfo.invStatus = :invStatus ");
		paramNames.add("invStatus");
		params.add(invInfo.getInvStatus());

		// 同托盘号
		if (invInfo.getPalletSeq() != null) {
			hql.append("AND ih.invInfo.palletSeq = :palletSeq ");
			paramNames.add("palletSeq");
			params.add(invInfo.getPalletSeq());
		} else {
			hql.append("AND ih.invInfo.palletSeq IS NULL ");
		}

		// 同入库日期
		if (invInfo.getInboundDate() != null) {
			hql.append("AND to_char(ih.invInfo.inboundDate,'YYYY-MM-DD')=:inboundDate ");
			paramNames.add("inboundDate");
			params.add(DateUtil.getStringDate(invInfo.getInboundDate(),
					DateUtil.HYPHEN_DATE_FORMAT));
		} else {
			hql.append("AND ih.invInfo.inboundDate IS NULL ");
		}

		// 同入库单号=工程号
		if (invInfo.getTrackSeq() != null) {
			hql.append("AND ih.invInfo.trackSeq = :trackSeq ");
			paramNames.add("trackSeq");
			params.add(invInfo.getTrackSeq());
		} else {
			hql.append("AND ih.invInfo.trackSeq IS NULL ");
		}

		List<Long> ids = commonDao.findByQuery(hql.toString(),
				paramNames.toArray(new String[paramNames.size()]),
				params.toArray(new Object[params.size()]));

		if (ids != null && ids.size() > 0) {
			List<AllocatePutawayDetail> result = new ArrayList<AllocatePutawayDetail>(
					ids.size());
			for (Long id : ids) {
				AllocatePutawayDetail apd = null;

				if (WmsConstant.PUTAWAY_MOV.equals(type)) {
					apd = commonDao.load(MoveDocDetail.class, id);
				} else if (WmsConstant.PUTAWAY_OBD.equals(type)) {
				}

				if (apd != null) {
					result.add(apd);
				}
			}
			return result;
		}

		return null;
	}

	@Override
	public Warehouse getWhByCode(String whCode) {
		String hql = " select wh.id from Warehouse wh where wh.code = :whCode ";
		
		List<Long> whId = commonDao.findByQuery(hql, "whCode", whCode);
		
		if( whId != null && whId.size() == 1 ){
			return commonDao.load(Warehouse.class, whId.get(0));
		}
		
		return null;
	}

	@Override
	public Plant getPlantByCode(String plantCode) {
		String hql = " select p.id from Plant p where p.code = :plantCode ";
		
		List<Long> plantIds = commonDao.findByQuery(hql, "plantCode", plantCode);
		
		if( plantIds != null && plantIds.size() == 1 ){
			return commonDao.load(Plant.class, plantIds.get(0));
		}
		return null;
	}

	@Override
	public Sku getSkuByCode(String skuCode, Long plantId) {
		String hql = " select sku.id from Sku sku where sku.plant.id = :plantId and sku.code = :skuCode ";
		
		List<Long> skuIds = commonDao.findByQuery(hql, new String[]{"plantId","skuCode"}, new Object[]{ plantId, skuCode });
		
		if( skuIds != null && skuIds.size() == 1 ){
			return commonDao.load(Sku.class, skuIds.get(0));
		}
		
		return null;
	}

	@Override
	public Sku getSkuByCode(String skuCode, String plantCode) {
		String hql = " select sku.id from Sku sku where sku.plant.code = :plantCode and sku.code = :skuCode ";
		
		List<Long> skuIds = commonDao.findByQuery(hql, new String[]{"plantCode","skuCode"}, new Object[]{ plantCode, skuCode });
		
		if( skuIds != null && skuIds.size() == 1 ){
			return commonDao.load(Sku.class, skuIds.get(0));
		}		
		return null;
	}

	@Override
	public BizOrg getBizOrgByCode(String bizOrgCode) {
		String hql = " select bo.id from BizOrg bo where bo.code = :bizOrgCode";
		
		List<Long> boIds = commonDao.findByQuery(hql, "bizOrgCode", bizOrgCode);
		
		if( boIds != null && boIds.size() == 1 ){
			return commonDao.load(BizOrg.class, boIds.get(0));
		}		
		return null;
	}

	@Override
	public Carrier getCarrierByCode(String carrierCode) {
		String hql = " select c.id from Carrier c where c.code = :carrierCode";
		
		List<Long> carriderIds = commonDao.findByQuery(hql, "carrierCode", carrierCode);
		
		if( carriderIds != null && carriderIds.size() == 1 ){
			return commonDao.load(Carrier.class, carriderIds.get(0));
		}		
		return null;
	}

}
