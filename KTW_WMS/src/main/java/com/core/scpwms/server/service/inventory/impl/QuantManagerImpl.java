package com.core.scpwms.server.service.inventory.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.core.business.model.domain.CreateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.ui.table.RowData;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.client.custom.page.support.ClientLotInfo;
import com.core.scpwms.client.custom.page.support.ClientQuantEditParam;
import com.core.scpwms.client.custom.page.support.ClientQuantInfo;
import com.core.scpwms.server.enumerate.EnuInvenHisType;
import com.core.scpwms.server.enumerate.EnuLotFieldType;
import com.core.scpwms.server.enumerate.EnuLotFormat;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.common.PackageInfo;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.lot.LotConstant;
import com.core.scpwms.server.model.lot.LotInfo;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.InventoryHelper;
import com.core.scpwms.server.service.inventory.QuantManager;
import com.core.scpwms.server.util.DateUtil;

/**
 * 
 * <p>
 * 批次管理实现类。 
 * </p>
 * 
 * @version 1.0
 * @author MBP : 吴 <br/>
 *         修改履历 <br/>
 *         2013/03/01 : MBP 吴: 初版创建<br/>
 */
@SuppressWarnings("all")
public class QuantManagerImpl extends DefaultBaseManager implements QuantManager {

    private BCManager bcManager;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public BCManager getBcManager() {
        return bcManager;
    }

    public void setBcManager(BCManager bcManager) {
        this.bcManager = bcManager;
    }

    public Quant getQuantInfo(Long skuId, LotInputData lotInfo) {
        Sku sku = this.commonDao.load(Sku.class, skuId);
        
        if( lotInfo == null )
        	lotInfo = new LotInputData();
        
        Quant quant = getOldQuant(sku, lotInfo);

        // 库存Quant信息不存在，新建Quant信息，并返回
        if (quant == null) {
            quant = new Quant();
            quant.setSku(sku);
            quant.setLotData(lotInfo);
            quant.setDispLot(quant.getLotData().toString());
            quant.setLotSequence(bcManager.getLotSeq(skuId));
            this.commonDao.store(quant);

        }

        return quant;
    }

    private Quant getOldQuant(Sku sku, LotInputData lotInfo) {
        // 查询是否有现有Quant，此方法需要重新封装
        List<Object> values = new ArrayList<Object>();
        List<String> keys = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        sb.append("From Quant quant where quant.sku.id=:skuId ");
        keys.add("skuId");
        values.add(sku.getId());
        // Lot信息处理
        if (lotInfo != null && !StringUtils.isEmpty(lotInfo.getProperty1())) {
            sb.append(" and quant.lotData.property1=:property1");
            keys.add("property1");
            values.add(lotInfo.getProperty1());
        } else {
            sb.append(" and quant.lotData.property1 is null");
        }

        if (lotInfo != null && !StringUtils.isEmpty(lotInfo.getProperty2())) {
            sb.append(" and quant.lotData.property2=:property2 ");
            keys.add("property2");
            values.add(lotInfo.getProperty2());
        } else {
            sb.append(" and quant.lotData.property2 is null");
        }

        if (lotInfo != null && !StringUtils.isEmpty(lotInfo.getProperty3())) {
            sb.append(" and quant.lotData.property3=:property3");
            keys.add("property3");
            values.add(lotInfo.getProperty3());
        } else {
            sb.append(" and quant.lotData.property3 is null");
        }

        if (lotInfo != null && !StringUtils.isEmpty(lotInfo.getProperty4())) {
            sb.append(" and quant.lotData.property4 =:property4 ");
            keys.add("property4");
            values.add(lotInfo.getProperty4());
        } else {
            sb.append(" and quant.lotData.property4 is null");
        }

        if (lotInfo != null && !StringUtils.isEmpty(lotInfo.getProperty5())) {
            sb.append(" and quant.lotData.property5 =:property5 ");
            keys.add("property5");
            values.add(lotInfo.getProperty5());
        } else {
            sb.append(" and quant.lotData.property5 is null");
        }

        if (lotInfo != null && !StringUtils.isEmpty(lotInfo.getProperty6())) {
            sb.append(" and quant.lotData.property6 =:property6 ");
            keys.add("property6");
            values.add(lotInfo.getProperty6());
        } else {
            sb.append(" and quant.lotData.property6 is null");
        }

        if (lotInfo != null && !StringUtils.isEmpty(lotInfo.getProperty7())) {
            sb.append(" and quant.lotData.property7 =:property7 ");
            keys.add("property7");
            values.add(lotInfo.getProperty7());
        } else {
            sb.append(" and quant.lotData.property7 is null");
        }

        if (lotInfo != null && !StringUtils.isEmpty(lotInfo.getProperty8())) {
            sb.append(" and quant.lotData.property8 =:property8 ");
            keys.add("property8");
            values.add(lotInfo.getProperty8());
        } else {
            sb.append(" and quant.lotData.property8 is null");
        }

        if (lotInfo != null && !StringUtils.isEmpty(lotInfo.getProperty9())) {
            sb.append(" and quant.lotData.property9 =:property9 ");
            keys.add("property9");
            values.add(lotInfo.getProperty9());
        } else {
            sb.append(" and quant.lotData.property9 is null");
        }

        if (lotInfo != null && !StringUtils.isEmpty(lotInfo.getProperty10())) {
            sb.append(" and quant.lotData.property10 =:property10 ");
            keys.add("property10");
            values.add(lotInfo.getProperty10());
        } else {
            sb.append(" and quant.lotData.property10 is null");
        }

        List<Quant> quants = commonDao.findByQuery(sb.toString(), keys.toArray(new String[keys.size()]), values.toArray(new Object[values.size()]));

        if (quants != null && !quants.isEmpty()) {
            return quants.get(0);
        }

        return null;
    }

    /**
     * 
     * <p>
     * 修改批次.
     * </p>
     * 
     * @param quant 批次信息
     * @param lotInfoId1 批次属性id1
     * @param lotInfoId2 批次属性id2
     * @param lotInfoId3 批次属性id3
     * @param lotInfoId4 批次属性id4
     * @param lotInfoId5 批次属性id5
     * @param lotInfoId6 批次属性id6
     * @param lotInfoId7 批次属性id7
     * @param lotInfoId8 批次属性id8
     * @param lotInfoId9 批次属性id9
     * @param lotInfoId10 批次属性id10           
     */
    @Override
    public void saveLot(Long lotInfoId1, Long lotInfoId2, Long lotInfoId3, Long lotInfoId4, Long lotInfoId5,
            Long lotInfoId6, Long lotInfoId7, Long lotInfoId8, Long lotInfoId9, Long lotInfoId10, Quant quant) {

        LotInputData lotData = new LotInputData();

        if (lotInfoId1 != 0) {

            LotInfo lotInfo1 = this.load(LotInfo.class, lotInfoId1);
            lotData.setProperty1(lotInfo1.getLdi1().getEnuCode());
        }

        if (lotInfoId2 != 0) {
            LotInfo lotInfo2 = this.load(LotInfo.class, lotInfoId2);
            lotData.setProperty2(lotInfo2.getLdi2().getEnuCode());
        }

        if (lotInfoId3 != 0) {
            LotInfo lotInfo3 = this.load(LotInfo.class, lotInfoId3);
            lotData.setProperty3(lotInfo3.getLdi3().getEnuCode());
        }

        if (lotInfoId4 != 0) {
            LotInfo lotInfo4 = this.load(LotInfo.class, lotInfoId4);
            lotData.setProperty4(lotInfo4.getLdi4().getEnuCode());
        }

        if (lotInfoId5 != 0) {
            LotInfo lotInfo5 = this.load(LotInfo.class, lotInfoId5);
            lotData.setProperty5(lotInfo5.getLdi5().getEnuCode());
        }

        if (lotInfoId6 != 0) {
            LotInfo lotInfo6 = this.load(LotInfo.class, lotInfoId6);
            lotData.setProperty6(lotInfo6.getLdi6().getEnuCode());
        }

        if (lotInfoId7 != 0) {
            LotInfo lotInfo7 = this.load(LotInfo.class, lotInfoId7);
            lotData.setProperty7(lotInfo7.getLdi7().getEnuCode());
        }

        if (lotInfoId8 != 0) {
            LotInfo lotInfo8 = this.load(LotInfo.class, lotInfoId8);
            lotData.setProperty8(lotInfo8.getLdi8().getEnuCode());
        }

        if (lotInfoId9 != 0) {
            LotInfo lotInfo9 = this.load(LotInfo.class, lotInfoId9);
            lotData.setProperty9(lotInfo9.getLdi9().getEnuCode());
        }

        if (lotInfoId10 != 0) {
            LotInfo lotInfo10 = this.load(LotInfo.class, lotInfoId10);
            lotData.setProperty10(lotInfo10.getLdi10().getEnuCode());
        }

        quant.setLotData(lotData);

        // 保存quant
        this.getCommonDao().store(quant);
    }

    @Override
    public Boolean isNewQuant(Long skuId, LotInputData lotInfo) {
        Sku sku = this.commonDao.load(Sku.class, skuId);
        return getOldQuant(sku, lotInfo) == null ? Boolean.TRUE : Boolean.FALSE;
    }

}
