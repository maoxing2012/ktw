package com.core.scpwms.server.model.inventory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.core.db.server.model.Entity;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.server.enumerate.EnuLotFormat;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.common.SkuProperties;
import com.core.scpwms.server.model.lot.LotInfo;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.util.DateUtil;

/**
 * 库存批次属性相关Quant信息
 * 与Inventory 为1对多的关系，但是反应的是库存批次属性相关内容
 * 含批次号、批次属性、追踪单号信息、SKU信息
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class Quant extends Entity {

    /**
     * 批次号信息
     */
    private String lotSequence;

    /**
     * SKU 
     */
    private Sku sku;

    /**
     * 批次属性
     */
    private LotInputData lotData = new LotInputData();

    /**
     * 收货日期
     */
    private Date inboundDate;

    /**
     * 相关单号
     * 系统默认记录为收货单号，但是实施的时候可根据选择进行修改
     */
    private String trackSeq;

    /**
     * 批次属性在表格内显示值
     */
    private String dispLot;

    public String getLotSequence() {
        return lotSequence;
    }

    public void setLotSequence(String lotSequence) {
        this.lotSequence = lotSequence;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public LotInputData getLotData() {
        return lotData;
    }

    public void setLotData(LotInputData lotData) {
        this.lotData = lotData;
    }

    public Date getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(Date inboundDate) {
        this.inboundDate = inboundDate;
    }

    public String getTrackSeq() {
        return trackSeq;
    }

    public void setTrackSeq(String trackSeq) {
        this.trackSeq = trackSeq;
    }

    public String getDispLot() {
        return dispLot;
    }

    public void setDispLot(String dispLot) {
        this.dispLot = dispLot;
    }

    public Map<String, Date> getExpireInfo() {

        Map<String, Date> result = new HashMap<String, Date>();

        SkuProperties properties = this.getSku().getProperties();

        // 判定SKU的Lot Info是否进行安全期间的管理
        if (properties.getUseExpire() != null && properties.getUseExpire()) {

            // 如果指定了生产日期对应的批次属性，默认使用开始日期加上保质期 作为到期日期
            if (!StringUtils.isEmpty(properties.getLotExpireKey()) && properties.getLotInfo() != null) {
                // 对应批次信息
                LotInfo lot = properties.getLotInfo();
                if (lot.getLdi1() != null && lot.getLdi1().getTitle() != null
                        && properties.getLotExpireKey().equalsIgnoreCase(lot.getLdi1().getTitle())) {
                    // 处理保质期开始时间
                    result.put("START", formatLotInfo(this.getLotData().getProperty1(), lot.getLdi1().getLotType()));
                    // 处理结束日期
                    if (properties.getDayOfExpiry() != null) {
                        result.put("END",
                                DateUtil.nextNDay(result.get("START"), properties.getDayOfExpiry().intValue()));
                    }
                } else if (lot.getLdi2() != null && lot.getLdi2().getTitle() != null
                        && properties.getLotExpireKey().equalsIgnoreCase(lot.getLdi2().getTitle())) {
                    result.put("START", formatLotInfo(this.getLotData().getProperty2(), lot.getLdi2().getLotType()));
                    if (properties.getDayOfExpiry() != null) {
                        result.put("END",
                                DateUtil.nextNDay(result.get("START"), properties.getDayOfExpiry().intValue()));
                    }
                } else if (lot.getLdi3() != null && lot.getLdi3().getTitle() != null
                        && properties.getLotExpireKey().equalsIgnoreCase(lot.getLdi3().getTitle())) {
                    result.put("START", formatLotInfo(this.getLotData().getProperty3(), lot.getLdi3().getLotType()));
                    if (properties.getDayOfExpiry() != null) {
                        result.put("END",
                                DateUtil.nextNDay(result.get("START"), properties.getDayOfExpiry().intValue()));
                    }
                } else if (lot.getLdi4() != null && lot.getLdi4().getTitle() != null
                        && properties.getLotExpireKey().equalsIgnoreCase(lot.getLdi4().getTitle())) {
                    result.put("START", formatLotInfo(this.getLotData().getProperty4(), lot.getLdi4().getLotType()));
                    if (properties.getDayOfExpiry() != null) {
                        result.put("END",
                                DateUtil.nextNDay(result.get("START"), properties.getDayOfExpiry().intValue()));
                    }
                } else if (lot.getLdi5() != null && lot.getLdi5().getTitle() != null
                        && properties.getLotExpireKey().equalsIgnoreCase(lot.getLdi5().getTitle())) {
                    result.put("START", formatLotInfo(this.getLotData().getProperty5(), lot.getLdi5().getLotType()));
                    if (properties.getDayOfExpiry() != null) {
                        result.put("END",
                                DateUtil.nextNDay(result.get("START"), properties.getDayOfExpiry().intValue()));
                    }
                } else if (lot.getLdi6() != null && lot.getLdi6().getTitle() != null
                        && properties.getLotExpireKey().equalsIgnoreCase(lot.getLdi6().getTitle())) {
                    result.put("START", formatLotInfo(this.getLotData().getProperty6(), lot.getLdi6().getLotType()));
                    if (properties.getDayOfExpiry() != null) {
                        result.put("END",
                                DateUtil.nextNDay(result.get("START"), properties.getDayOfExpiry().intValue()));
                    }
                } else if (lot.getLdi7() != null && lot.getLdi7().getTitle() != null
                        && properties.getLotExpireKey().equalsIgnoreCase(lot.getLdi7().getTitle())) {
                    result.put("START", formatLotInfo(this.getLotData().getProperty7(), lot.getLdi7().getLotType()));
                    if (properties.getDayOfExpiry() != null) {
                        result.put("END",
                                DateUtil.nextNDay(result.get("START"), properties.getDayOfExpiry().intValue()));
                    }
                } else if (lot.getLdi8() != null && lot.getLdi8().getTitle() != null
                        && properties.getLotExpireKey().equalsIgnoreCase(lot.getLdi8().getTitle())) {
                    result.put("START", formatLotInfo(this.getLotData().getProperty8(), lot.getLdi8().getLotType()));
                    if (properties.getDayOfExpiry() != null) {
                        result.put("END",
                                DateUtil.nextNDay(result.get("START"), properties.getDayOfExpiry().intValue()));
                    }
                } else if (lot.getLdi9() != null && lot.getLdi9().getTitle() != null
                        && properties.getLotExpireKey().equalsIgnoreCase(lot.getLdi9().getTitle())) {
                    result.put("START", formatLotInfo(this.getLotData().getProperty9(), lot.getLdi9().getLotType()));
                    if (properties.getDayOfExpiry() != null) {
                        result.put("END",
                                DateUtil.nextNDay(result.get("START"), properties.getDayOfExpiry().intValue()));
                    }
                } else if (lot.getLdi10() != null && lot.getLdi10().getTitle() != null
                        && properties.getLotExpireKey().equalsIgnoreCase(lot.getLdi10().getTitle())) {
                    result.put("START", formatLotInfo(this.getLotData().getProperty10(), lot.getLdi10().getLotType()));
                    if (properties.getDayOfExpiry() != null) {
                        result.put("END",
                                DateUtil.nextNDay(result.get("START"), properties.getDayOfExpiry().intValue()));
                    }
                }
            }

            // 如果指定了到期日期的批次属性
            if (!StringUtils.isEmpty(properties.getLotDeadLineKey()) && properties.getLotInfo() != null) {
                // 对应批次信息
                LotInfo lot = properties.getLotInfo();
                if (lot.getLdi1() != null && lot.getLdi1().getTitle() != null
                        && properties.getLotDeadLineKey().equalsIgnoreCase(lot.getLdi1().getTitle())) {
                    // 处理结束日期
                    result.put("END", formatLotInfo(this.getLotData().getProperty1(), lot.getLdi1().getLotType()));
                } else if (lot.getLdi2() != null && lot.getLdi2().getTitle() != null
                        && properties.getLotDeadLineKey().equalsIgnoreCase(lot.getLdi2().getTitle())) {
                    result.put("END", formatLotInfo(this.getLotData().getProperty2(), lot.getLdi2().getLotType()));
                } else if (lot.getLdi3() != null && lot.getLdi3().getTitle() != null
                        && properties.getLotDeadLineKey().equalsIgnoreCase(lot.getLdi3().getTitle())) {
                    result.put("END", formatLotInfo(this.getLotData().getProperty3(), lot.getLdi3().getLotType()));
                } else if (lot.getLdi4() != null && lot.getLdi4().getTitle() != null
                        && properties.getLotDeadLineKey().equalsIgnoreCase(lot.getLdi4().getTitle())) {
                    result.put("END", formatLotInfo(this.getLotData().getProperty4(), lot.getLdi4().getLotType()));
                } else if (lot.getLdi5() != null && lot.getLdi5().getTitle() != null
                        && properties.getLotDeadLineKey().equalsIgnoreCase(lot.getLdi5().getTitle())) {
                    result.put("END", formatLotInfo(this.getLotData().getProperty5(), lot.getLdi5().getLotType()));
                } else if (lot.getLdi6() != null && lot.getLdi6().getTitle() != null
                        && properties.getLotDeadLineKey().equalsIgnoreCase(lot.getLdi6().getTitle())) {
                    result.put("END", formatLotInfo(this.getLotData().getProperty6(), lot.getLdi6().getLotType()));
                } else if (lot.getLdi7() != null && lot.getLdi7().getTitle() != null
                        && properties.getLotDeadLineKey().equalsIgnoreCase(lot.getLdi7().getTitle())) {
                    result.put("END", formatLotInfo(this.getLotData().getProperty7(), lot.getLdi7().getLotType()));
                } else if (lot.getLdi8() != null && lot.getLdi8().getTitle() != null
                        && properties.getLotDeadLineKey().equalsIgnoreCase(lot.getLdi8().getTitle())) {
                    result.put("END", formatLotInfo(this.getLotData().getProperty8(), lot.getLdi8().getLotType()));
                } else if (lot.getLdi9() != null && lot.getLdi9().getTitle() != null
                        && properties.getLotDeadLineKey().equalsIgnoreCase(lot.getLdi9().getTitle())) {
                    result.put("END", formatLotInfo(this.getLotData().getProperty9(), lot.getLdi9().getLotType()));
                } else if (lot.getLdi10() != null && lot.getLdi10().getTitle() != null
                        && properties.getLotDeadLineKey().equalsIgnoreCase(lot.getLdi10().getTitle())) {
                    result.put("END", formatLotInfo(this.getLotData().getProperty10(), lot.getLdi10().getLotType()));
                }
            }
        }
        return result;
    }

    public Date formatLotInfo(String property, String lotType) {

        SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf_yyyyMMdd_hms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (EnuLotFormat.LOTFORMAT_DATE.equalsIgnoreCase(lotType)) {
                return sdf_yyyyMMdd.parse(property);
            } else {
                return sdf_yyyyMMdd_hms.parse(property);
            }
        } catch (Exception ex) {
            return null;
        }
    }

}
