package com.core.scpwms.server.service.common;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 
 * <p>一些常用的共通方法定义在这里</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/14<br/>
 */
@Transactional(readOnly = false)
public interface BinHelper extends BaseManager {

    /**
     * 
     * <p>从WT，WH中取得最后上架商品信息</p>
     *
     * @param bin
     * @return
     */
    @Transactional
    public Quant getLastPutawayQuant(Bin bin);
    
    /**
     * 
     * <p>从WT，WH中取得最后上架商品信息</p>
     *
     * @param bin
     * @return
     */
    @Transactional
    public InventoryInfo getLastPutawayInvInfo(Bin bin);

    /**
     * 
     * <p>找一个空的复核库位(没有库存，也没有出库单占着)，找不到拿默认的</p>
     *
     * @param whId
     * @param qty
     * @param weight
     * @param volume
     * @param metric
     * @param shipMethod
     * @return
     */
    @Transactional
    public Bin getEmptyCheckBin(Long whId);
    
    /**
     * 
     * <p>找一个空的发货月台（没有库存，没有订单占着）</p>
     *
     * @param whId
     * @return
     */
    @Transactional
    public Bin getEmptyShipBin( Long whId, Long carrierId );
    
    /**
     * 
     * <p>找一个空的盘点库位(没有库存，也没有盘点单占着)</p>
     *
     * @param whId
     * @return
     */
    @Transactional
    public Bin getEmptyCountBin(Long whId);
    
    /**
     * 
     * <p>如果库位上存放的都是同批次，返回True,反之返回False</p>
     *
     * @param binId
     * @return
     */
    @Transactional
    public boolean isAllSameLot(Long binId);
    
    /**
     * 
     * <p>如果库位上存放的都是同批次，返回True,反之返回False</p>
     *
     * @param binId
     * @return
     */
    @Transactional
    public boolean isAllSameOwner(Long ownerId);
    
    /**
     * 
     * <p>如果库位上存放的都是同商品，返回True,反之返回False</p>
     *
     * @param binId
     * @return
     */
    @Transactional
    public boolean isAllSameSku(Long binId);
    
    /**
     * 
     * <p>刷新最后存放信息</p>
     *
     * @param binId
     * @return
     */
    @Transactional
    public String[] refBinInfo(Long binId);
    
    /**
     * 
     * <p>刷新库容信息</p>
     *
     * @param binIds
     */
    @Transactional
    public void refreshBinInvInfo(List<Long> binIds);
    
    /**
     * 
     * <p>刷新库容信息</p>
     *
     * @param binId
     */
    @Transactional
    public void refreshBinInvInfo( Long binId );
    
    /**
     * 
     * <p>刷新商品相关的库容信息</p>
     *
     * @param skuId
     */
    @Transactional
    public void refreshBinInvInfoBySku(Long skuId);
    
    /**
     * 
     * <p>为出库单分配备货库位</p>
     *
     * @param obd
     * @return
     */
    @Transactional
    public Bin getCheckBin(OutboundDelivery obd);
    
    /**
     * 
     * <p>取得一个在途虚拟库位</p>
     *
     * @param whId
     * @return
     */
    @Transactional
    public Bin getShipBin( Long whId );
}
