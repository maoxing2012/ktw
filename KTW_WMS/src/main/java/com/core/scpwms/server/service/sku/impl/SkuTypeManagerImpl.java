/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : SkuTypeManagerImpl.java
 */

package com.core.scpwms.server.service.sku.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuType;
import com.core.scpwms.server.service.sku.SkuTypeManager;

/**
 * 
 * <p>
 * Sku类型管理实现。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 温 <br/>
 *         修改履历 <br/>
 *         2013/02/5 : MBP 温: 初版创建<br/>
 */
public class SkuTypeManagerImpl extends DefaultBaseManager implements
		SkuTypeManager {

	/**
	 * 
	 * <p>
	 * 新建或者修改一个Sku类型.
	 * </p>
	 * 
	 * @param skuType
	 *            Sku类型
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveSkuType(SkuType skuType) {
		if (skuType.isNew()) {
			StringBuffer hql = new StringBuffer(
					"from SkuType st where lower(st.code) = :stCode");
			List<SkuType> skuTypeList = commonDao.findByQuery(hql.toString(),
					new String[] { "stCode" }, new Object[] { skuType.getCode()
							.toLowerCase() });

			if (!skuTypeList.isEmpty()) {
				throw new BusinessException("ExistskutypeExcetpion");
			}
			skuType.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		} else {
			skuType.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		this.getCommonDao().store(skuType);
	}

	/**
	 * 
	 * <p>
	 * 使Sku类型生效
	 * </p>
	 * 
	 * @param ids
	 *            选中的id列表
	 */
	@Override
	public void enableSkuType(List<Long> ids) {

		SkuType dbObj = null;

		// 循环画面选中ID
		for (Long id : ids) {
			// 根据ID取得数据对象.
			dbObj = this.getCommonDao().load(SkuType.class, id.longValue());

			// 设置为生效
			dbObj.setDisabled(false);
			// 设置更新者信息
			dbObj.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));

			// 保存数据
			this.getCommonDao().store(dbObj);
		}
	}

	/**
	 * 
	 * <p>
	 * 使Sku类型失效
	 * </p>
	 * 
	 * @param ids
	 *            选中的id列表
	 */
	@Override
	public void disableSkuType(List<Long> ids) {

		SkuType dbObj = null;

		// 循环选中的ID
		for (Long id : ids) {
			dbObj = this.getCommonDao().load(SkuType.class, id.longValue());

			// 设置为失效
			dbObj.setDisabled(true);
			// 设置更新者信息
			dbObj.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));

			// 保存数据
			this.getCommonDao().store(dbObj);
		}
	}

	@Override
	public void deleteSkuType(List<Long> ids) {
		for (Long id : ids) {
			String hsql = "select id from Sku sku where 1=1 and (sku.it1000.id =:skuTypeId or sku.it2000.id =:skuTypeId or sku.it3000.id =:skuTypeId or sku.it4000.id =:skuTypeId) ";
			List<Long> skuList = this.commonDao.findByQuery(hsql, "skuTypeId",
					id);

			if (skuList != null && skuList.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}
			delete(load(SkuType.class, id));
		}
	}
}
