package com.core.scpwms.server.service.sku.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.enumerate.EnuPackageLevel;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.PackageInfo;
import com.core.scpwms.server.service.common.BinHelper;
import com.core.scpwms.server.service.sku.PackageManager;

/**
 * 
 * <p>
 * 包装管理实现类。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 陈 <br/>
 *         修改履历 <br/>
 *         2013/02/06 : MBP 陈: 初版创建<br/>
 */
public class PackageManagerImpl extends DefaultBaseManager implements
		PackageManager {

	private BinHelper binHelper;

	public BinHelper getBinHelper() {
		return this.binHelper;
	}

	public void setBinHelper(BinHelper binHelper) {
		this.binHelper = binHelper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.sku.PackageManager#savePackageInfo(com
	 * .core.scpwms.server.model.common.PackageInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void savePackageInfo(PackageInfo packageInfo) {
		if (packageInfo.isNew()) {
			StringBuffer hql = new StringBuffer(
					"from PackageInfo pinfo where lower(pinfo.code) = :pinfoCode");
			List<PackageInfo> pinfos = commonDao.findByQuery(hql.toString(),
					new String[] { "pinfoCode" }, new Object[] { packageInfo
							.getCode().toLowerCase() });
			if (!pinfos.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}

			packageInfo.getP1000().setPackageLevel(EnuPackageLevel.PK1000);
			packageInfo.getP1000().setPackageInfo(packageInfo);

			packageInfo.getP2100().setPackageLevel(EnuPackageLevel.PK2100);
			packageInfo.getP2100().setPackageInfo(packageInfo);

			packageInfo.getP2000().setPackageLevel(EnuPackageLevel.PK2000);
			packageInfo.getP2000().setPackageInfo(packageInfo);

			packageInfo.getP3000().setPackageLevel(EnuPackageLevel.PK3000);
			packageInfo.getP3000().setPackageInfo(packageInfo);

			packageInfo.getP4000().setPackageLevel(EnuPackageLevel.PK4000);
			packageInfo.getP4000().setPackageInfo(packageInfo);

			packageInfo.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		} else {
			packageInfo.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}

		packageInfo.getP1000()
				.setContainer(
						packageInfo.getP1000().getContainer() != null
								&& packageInfo.getP1000().getContainer()
										.getId() == null ? null : packageInfo
								.getP1000().getContainer());
		packageInfo.getP2100()
				.setContainer(
						packageInfo.getP2100().getContainer() != null
								&& packageInfo.getP2100().getContainer()
										.getId() == null ? null : packageInfo
								.getP2100().getContainer());
		packageInfo.getP2000()
				.setContainer(
						packageInfo.getP2000().getContainer() != null
								&& packageInfo.getP2000().getContainer()
										.getId() == null ? null : packageInfo
								.getP2000().getContainer());
		packageInfo.getP3000()
				.setContainer(
						packageInfo.getP3000().getContainer() != null
								&& packageInfo.getP3000().getContainer()
										.getId() == null ? null : packageInfo
								.getP3000().getContainer());
		packageInfo.getP4000()
				.setContainer(
						packageInfo.getP4000().getContainer() != null
								&& packageInfo.getP4000().getContainer()
										.getId() == null ? null : packageInfo
								.getP4000().getContainer());

		this.commonDao.store(packageInfo.getP1000());
		this.commonDao.store(packageInfo.getP2100());
		this.commonDao.store(packageInfo.getP2000());
		this.commonDao.store(packageInfo.getP3000());
		this.commonDao.store(packageInfo.getP4000());
		this.commonDao.store(packageInfo);

		// 刷新关联的库容
		String hql = " select sku.id from Sku sku where sku.properties.packageInfo.id = :packId ";
		List<Long> skuIds = commonDao.findByQuery(hql, "packId",
				packageInfo.getId());
		if (skuIds != null && skuIds.size() > 0) {
			for (Long skuId : skuIds) {
				binHelper.refreshBinInvInfoBySku(skuId);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.sku.PackageManager#disabledPackage(java
	 * .util.List)
	 */
	@Override
	public void disabledPackage(List<Long> ids) {
		for (Long id : ids) {
			PackageInfo dbObj = this.getCommonDao().load(PackageInfo.class, id);
			dbObj.setDisabled(Boolean.FALSE);
			dbObj.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.getCommonDao().store(dbObj);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.sku.PackageManager#enablePackage(java.
	 * util.List)
	 */
	@Override
	public void enablePackage(List<Long> ids) {
		for (Long id : ids) {
			PackageInfo dbObj = this.getCommonDao().load(PackageInfo.class, id);
			dbObj.setDisabled(Boolean.TRUE);
			dbObj.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.getCommonDao().store(dbObj);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.sku.PackageManager#deletePackge(java.util
	 * .List)
	 */
	@Override
	public void deletePackge(List<Long> ids) {
		for (Long id : ids) {
			// 履历
			List<Long> hisIds = commonDao
					.findByQuery(
							" select id from InventoryHistory where invInfo.packageDetail.packageInfo.id = :packId",
							"packId", id);
			if (hisIds != null && hisIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			// 商品
			List<Long> skuIds = commonDao
					.findByQuery(
							" select sku.id from Sku sku where sku.properties.packageInfo.id = :packId",
							"packId", id);
			if (skuIds != null && skuIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			// 策略
			List<Long> parIds = commonDao
					.findByQuery(
							" select id from PutAwayRule where packageInfo.id = :packId or packageDetail.packageInfo.id = :packId",
							"packId", id);
			if (parIds != null && parIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			List<Long> purIds = commonDao
					.findByQuery(
							" select id from PickUpRule where packageInfo.id = :packId or packageDetail.packageInfo.id = :packId",
							"packId", id);
			if (purIds != null && purIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			PackageInfo pkginfo = load(PackageInfo.class, id);
			delete(pkginfo.getP1000());
			delete(pkginfo.getP2000());
			delete(pkginfo.getP2100());
			delete(pkginfo.getP3000());
			delete(pkginfo.getP4000());
			delete(pkginfo);
		}
	}

	@Override
	public List<PackageDetail> getPackageDetails(Long packInfoId) {
		StringBuffer hql = new StringBuffer(" from PackageDetail pd where pd.packageInfo.id = :packInfoId ");
		hql.append(" and pd.code is not null ");
		hql.append(" and pd.name is not null ");
		hql.append(" and pd.coefficient > 0 ");
		hql.append(" order by pd.coefficient, pd.packageLevel ");
		
		return commonDao.findByQuery(hql.toString(), "packInfoId", packInfoId);
	}
}
