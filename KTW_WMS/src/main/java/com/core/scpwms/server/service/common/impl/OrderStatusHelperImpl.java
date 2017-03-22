/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: OrderStatusHelperImpl.java
 */

package com.core.scpwms.server.service.common.impl;

import java.util.Date;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.model.security.User;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.domain.OrderTrackingEntityWithStatus;
import com.core.scpwms.server.model.base.OrderStatus;
import com.core.scpwms.server.service.common.OrderStatusHelper;

/**
 * <p>
 * 修改订单状态
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/14<br/>
 */
public class OrderStatusHelperImpl extends DefaultBaseManager implements
		OrderStatusHelper {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.common.OrderStatusHelper#changeStatus(
	 * com.core.scpwms.server.model.base.OrderTrackingEntityWithStatus,
	 * java.lang.String)
	 */
	@Override
	public void changeStatus(OrderTrackingEntityWithStatus order, Long newStatus) {
		changeStatus4Other(order, newStatus, null);
	}

	@Override
	public void changeStatus4Edi(OrderTrackingEntityWithStatus order,
			Long newStatus) {
		changeStatus4Other(order, newStatus, "EDI");
	}

	@Override
	public void changeStatus4Other(OrderTrackingEntityWithStatus order,
			Long newStatus, String operatorName) {
		OrderStatus os = new OrderStatus();
		os.setStatusFrom(order.getStatus() != null ? order.getStatus()
				.toString() : null);
		os.setStatusEnum(order.getStatusEnum());
		os.setStatusTo(newStatus != null ? newStatus.toString() : null);

		if (order.getId() == null) {
			throw new BusinessException("save order first!");
		}
		os.setOrderId(order.getId());

		int inx = order.getClass().getSimpleName().indexOf("$") == -1 ? order
				.getClass().getSimpleName().length() : order.getClass()
				.getSimpleName().indexOf("$");
		os.setOrderType(order.getClass().getSimpleName().substring(0, inx));

		os.setCreateInfo(operatorName != null ? new CreateInfo(operatorName)
				: new CreateInfo(UserHolder.getUser()));
		os.setUpdateInfo(operatorName != null ? new UpdateInfo(operatorName)
				: new UpdateInfo(UserHolder.getUser()));

		commonDao.store(os);

		order.changeStatus(newStatus);
		order.setUpdateInfo(os.getUpdateInfo());

	}

	@Override
	public Date getLastDate4StatusChange(Long orderId, String orderType,
			String enumType, Long statusFrom, Long statusTo) {
		StringBuffer hql = new StringBuffer(
				" select max(os.createInfo.updateTime) from OrderStatus os where 1=1");
		hql.append(" and os.orderId = :orderId ");
		hql.append(" and os.orderType = :orderType ");
		hql.append(" and os.statusEnum = :enumType ");
		hql.append(" and os.statusFrom = :statusFrom ");
		hql.append(" and os.statusTo = :statusTo ");

		return (Date) commonDao.findByQueryUniqueResult(hql.toString(),
				new String[] { "orderId", "orderType", "enumType",
						"statusFrom", "statusTo" }, new Object[] { orderId,
						orderType, enumType, statusFrom, statusTo });
	}

	@Override
	public void changeStatus(OrderTrackingEntityWithStatus order,
			Long newStatus, User user) {
		OrderStatus os = new OrderStatus();
		os.setStatusFrom(order.getStatus() != null ? order.getStatus()
				.toString() : null);
		os.setStatusEnum(order.getStatusEnum());
		os.setStatusTo(newStatus != null ? newStatus.toString() : null);

		if (order.getId() == null) {
			throw new BusinessException("save order first!");
		}
		os.setOrderId(order.getId());

		int inx = order.getClass().getSimpleName().indexOf("$") == -1 ? order
				.getClass().getSimpleName().length() : order.getClass()
				.getSimpleName().indexOf("$");
		os.setOrderType(order.getClass().getSimpleName().substring(0, inx));

		os.setCreateInfo(new CreateInfo(user));
		os.setUpdateInfo(new UpdateInfo(user));

		commonDao.store(os);

		order.changeStatus(newStatus);
		order.setUpdateInfo(os.getUpdateInfo());
	}

}
