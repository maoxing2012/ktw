package com.core.scpwms.server.service.sku.impl;

import java.util.List;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuSupplier;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.service.sku.SkuSupplierManager;

public class SkuSupplierManagerImpl extends DefaultBaseManager implements SkuSupplierManager{

	@Override
	public void deleteSuppliers(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
            for (Long id : ids) {
                SkuSupplier ss = getCommonDao().load(SkuSupplier.class, id);
                if (ss != null) {
                    Sku sku = load(Sku.class,ss.getSku().getId());
                    sku.setDefSupplier(null);
                    commonDao.store(sku);
                    getCommonDao().delete(ss);
                }
            }
	     }	
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveSuppliers(Long skuId, List<Long> supplierIds) {
		if (supplierIds != null && supplierIds.size() > 0) {
			//临时变量，存放供应商ID
            Sku sku = getCommonDao().load(Sku.class, skuId);
            for (Long id : supplierIds) {
                // 该供应商是不是已经被加过了？
                String hql = "select ss.id FROM SkuSupplier ss where ss.sku.id=:skuId and ss.supplier.id=:supplierId ";
                List<SkuSupplier> elements = this.commonDao.findByQuery(hql, new String[] { "skuId", "supplierId" }, new Object[] {
                		skuId, id });
                // 如果已经加过了，跳过
                if (elements != null && elements.size() > 0)
                    continue;

                BizOrg supplier = getCommonDao().load(BizOrg.class, id);
                SkuSupplier ss = new SkuSupplier();
                ss.setSku(sku);
                ss.setSupplier(supplier);
                getCommonDao().store(ss);  
            }
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public void defaultSupplier(List<Long> skuId, List<Long> ids) {	
		if(skuId != null){
			Sku sku = getCommonDao().load(Sku.class, skuId.get(0));
			//画面只能传一个供应商对应关系值
			if(!ids.isEmpty() && ids.size() == 1){
				//去DB去查询该商品对应的所有供应商
				List<SkuSupplier> ssList = this.commonDao.findByQuery("from SkuSupplier ss where ss.sku.id=:skuId", 
						new String[]{"skuId"}, 
						new Object[]{skuId});
				//如果有，设置默认全部为空
				if(!ssList.isEmpty()){
					for (SkuSupplier ss : ssList){
						ss.setDefSupplier(null);
						commonDao.store(ss);
					}
				}
				
				//再，处理画面传过来的默认供应商
				for(Long ssId : ids){
					SkuSupplier ssInfo = getCommonDao().load(SkuSupplier.class, ssId);
					BizOrg supplier = getCommonDao().load(BizOrg.class, ssInfo.getSupplier().getId());
					//设置默认供应商
					ssInfo.setDefSupplier(Boolean.TRUE);
					this.commonDao.store(ssInfo);
					sku.setDefSupplier(supplier);
					this.commonDao.store(sku);
				}
				
			}
		}
	}

}
