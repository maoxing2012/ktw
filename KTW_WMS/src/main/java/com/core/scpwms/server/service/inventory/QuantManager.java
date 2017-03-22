package com.core.scpwms.server.service.inventory;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.lot.LotInputData;

/**
 * 
 * <p>
 * 批次管理接口。 
 * </p>
 * 
 * @version 1.0
 * @author MBP : 吴 <br/>
 *         修改履历 <br/>
 *         2013/03/01 : MBP 吴: 初版创建<br/>
 */
@Transactional(readOnly = false)
public interface QuantManager extends BaseManager {

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
    @Transactional
    public void saveLot(Long lotInfoId1, Long lotInfoId2, Long lotInfoId3, Long lotInfoId4, Long lotInfoId5,
            Long lotInfoId6, Long lotInfoId7, Long lotInfoId8, Long lotInfoId9, Long lotInfoId10, Quant quant);

    /**
     * 
     * <p>取得Quant信息</p>
     *
     * @param skuId
     * @param lotInfo
     * @param inboundDate
     * @param trackSeq
     * @return
     */
    @Transactional
    public Quant getQuantInfo(Long skuId, LotInputData lotInfo);

    /**
     * 
     * <p>是否是新批次</p>
     *
     * @param skuId
     * @param lotInfo
     * @param inboundDate
     * @param trackSeq
     * @return
     */
    @Transactional
    public Boolean isNewQuant(Long skuId, LotInputData lotInfo);

}
