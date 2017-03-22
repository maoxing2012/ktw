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
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuInterCode;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.service.sku.SkuManager;

/**
 * <p>
 * SKU管理接口实现。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 陈 <br/>
 *         修改履历 <br/>
 *         2013/02/05 : MBP 陈: 初版创建<br/>
 */
public class SkuManagerImpl extends DefaultBaseManager implements SkuManager {

	@SuppressWarnings("unchecked")
	@Override
	public void saveSku(Sku sku) {
		if( sku.getDefSupplier() != null && sku.getDefSupplier().getId() == null )
			sku.setDefSupplier(null);
		
		if (sku.isNew()) {
			StringBuffer hql = new StringBuffer( "from Sku sku where lower(sku.code) = :code and sku.owner.id = :ownerId");
			List<Sku> skuList = commonDao.findByQuery(hql.toString(),
					new String[] { "code", "ownerId" },
					new Object[] { sku.getCode().toLowerCase(), sku.getOwner().getId() });

			if (!skuList.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}
			
			Owner owner = commonDao.load(Owner.class, sku.getOwner().getId());
			sku.setPlant(owner.getPlant());
			sku.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		} else {
			sku.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}
		// Pack
		PackageInfo pi = sku.getProperties().getPackageInfo();
		if( pi.isNew() ){
			pi.setCode(sku.getCode());
			pi.setName(sku.getName());
		}
		
		// PackDetail
		PackageDetail p1000 = pi.getP1000();
		if( p1000 == null ){
			p1000 = new PackageDetail();
			pi.setP1000(p1000);
		}
		if( p1000.isNew() ){
			p1000.setPackageLevel(EnuPackageLevel.PK1000);
			p1000.setPackageInfo(pi);
		}
		p1000.setCoefficient(1D);
		
		PackageDetail p2100 = pi.getP2100();
		if( p2100 == null ){
			p2100 = new PackageDetail();
			pi.setP2100(p2100);
		}
		if( p2100.isNew() ){
			p2100.setPackageLevel(EnuPackageLevel.PK2100);
			p2100.setPackageInfo(pi);
		}
		
		PackageDetail p2000 = pi.getP2000();
		if( p2000 == null ){
			p2000 = new PackageDetail();
			pi.setP2000(p2000);
		}
		if( p2000.isNew() ){
			p2000.setPackageLevel(EnuPackageLevel.PK2000);
			p2000.setPackageInfo(pi);
		}
		
		PackageDetail p3000 = pi.getP3000();
		if( p3000 == null ){
			p3000 = new PackageDetail();
			pi.setP3000(p3000);
		}
		if( p3000.isNew() ){
			p3000.setPackageLevel(EnuPackageLevel.PK3000);
			p3000.setPackageInfo(pi);
		}
		
		commonDao.store(pi);
		commonDao.store(p1000);
		commonDao.store(p2100);
		commonDao.store(p2000);
		commonDao.store(p3000);
		commonDao.store(sku);
	}

	@Override
	public void enableSku(List<Long> ids) {
		for (Long id : ids) {
			Sku sku = this.commonDao.load(Sku.class, id);
			sku.setDisabled(Boolean.FALSE);
			sku.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.commonDao.store(sku);
		}
	}

	@Override
	public void disableSku(List<Long> ids) {
		for (Long id : ids) {
			Sku sku = this.commonDao.load(Sku.class, id);
			sku.setDisabled(Boolean.TRUE);
			sku.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			this.commonDao.store(sku);
		}
	}

	@Override
	public void deleteSku(List<Long> ids) {
		for (Long id : ids) {
			// 是否有履历
			List<Long> hisIds = commonDao
					.findByQuery(
							" select id from InventoryHistory where invInfo.quant.sku.id = :skuId ",
							"skuId", id);
			if (hisIds != null && hisIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			// 是否有入库预订
			List<Long> inboundIds = commonDao.findByQuery( " select id from AsnDetail ad where ad.sku.id = :skuId ", "skuId", id);
			if (inboundIds != null && inboundIds.size() > 0) {
				throw new BusinessException(ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}
			
			// 是否有出库预订
			List<Long> outboundIds = commonDao
					.findByQuery(
							" select id from OutboundDeliveryDetail where sku.id = :skuId ",
							"skuId", id);
			if (outboundIds != null && outboundIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}
			
			// barCode也删除
			String hql = " select s.id from SkuInterCode s where s.sku.id = :skuId ";
			List<Long> barCodeIds = commonDao.findByQuery(hql, "skuId", id);
			if( barCodeIds != null && barCodeIds.size() > 0 ){
				for( Long barCodeId : barCodeIds ){
					delete(load(SkuInterCode.class, barCodeId));
				}
			}

			Sku sku = commonDao.load(Sku.class, id);

			// 包装信息删除
			delete( sku.getProperties().getPackageInfo().getP1000() );
			delete( sku.getProperties().getPackageInfo().getP2000() );
			delete( sku.getProperties().getPackageInfo().getP2100() );
			delete( sku.getProperties().getPackageInfo().getP3000() );
			
			sku.getProperties().getPackageInfo().setP1000(null);
			sku.getProperties().getPackageInfo().setP2000(null);
			sku.getProperties().getPackageInfo().setP2100(null);
			sku.getProperties().getPackageInfo().setP3000(null);
			
			delete( sku.getProperties().getPackageInfo() );
			
			sku.getProperties().setPackageInfo(null);
			
			
			delete(sku);
		}
	}


	@Override
	public void addInterCode(Long skuId, String barCode, String packLevel) {
		String hql = " select s.id from SkuInterCode s where s.sku.id = :skuId and s.barCode = :barCode ";
		List<Long> barCodeIds = commonDao.findByQuery(hql, new String[]{"skuId", "barCode"}, new Object[]{skuId, barCode});
		
		SkuInterCode barCodeInfo = null;
		Sku sku = commonDao.load(Sku.class, skuId);
		if( barCodeIds != null && barCodeIds.size() > 0 ){
			barCodeInfo = commonDao.load(SkuInterCode.class, barCodeIds.get(0));
		}
		else{
			barCodeInfo = new SkuInterCode();
			barCodeInfo.setSku(sku);
			barCodeInfo.setBarCode(barCode);
		}
		
		String hql2 = " from PackageDetail pd where pd.packageInfo.id = :packId and pd.packageLevel = :packLevel ";
		List<PackageDetail> packs = commonDao.findByQuery(hql2, new String[]{"packId", "packLevel"}, new Object[]{sku.getProperties().getPackageInfo().getId(), packLevel});
		barCodeInfo.setPackDetail(packs.get(0));
		commonDao.store(barCodeInfo);
	}

	@Override
	public void deleteInterCode(List<Long> barCodes) {
		for( Long barCode : barCodes ){
			commonDao.delete(commonDao.load(SkuInterCode.class, barCode));
		}
	}
	
	private void deleteInterCode( Long skuId, String barCode ){
		String hql = " select s.id from SkuInterCode s where s.sku.id = :skuId and s.barCode = :barCode ";
		List<Long> barCodeIds = commonDao.findByQuery(hql, new String[]{"skuId", "barCode"}, new Object[]{skuId, barCode});
		if( barCodeIds != null && barCodeIds.size() > 0 ){
			deleteInterCode(barCodeIds);
		}
	}
}
