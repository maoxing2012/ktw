package com.core.scpwms.server.service.common;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.common.OrderType;

/**
 * 
 * @description   生成番号
 * @author         MBP:xiaoyan<br/>
 * @createDate    2013-2-26
 * @version        V1.0<br/>
 */
@Transactional(readOnly = true)
public interface BCManager extends BaseManager {

    /**
     * 
     * <p>根据订单类型生成订单号</p>
     *
     * @param iot
     * @param whId
     * @return
     */
    @Transactional
    String getOrderSequence(OrderType iot, Long whId);

    /**
     * <p>根据仓库生成WAVE号</p>
     * @param whCode   仓库代码
     * @return
     */
    @Transactional
    String getWaveSeq(String whCode);

    /**
     * <p>根据仓库生成BOX号</p>
     * @param whCode   仓库代码
     * @return
     */
    @Transactional
    String getPackageSeq(String whCode);
    
    /**
     * 
     * <p>箱号</p>
     *
     * @param whId
     * @return
     */
    @Transactional
    String getBoxSequence(Long whId);

//    /**
//     * <p>根据仓库生成WT号</p>
//     * @param whCode   仓库代码
//     * @param typeName [WT]固定
//     * @return
//     */
//    @Transactional
//    String getTaskSeq(String whCode);

    /**
     * 
     * <p>根据仓库生成WO号</p>
     *
     * @param whCode
     * @return
     */
    @Transactional
    String getWarehouseOrderSeq(String whCode);
    
    /**
     * <p>生成LOT号</p>
     * @return
     */
    @Transactional
    String getLotSeq(Long skuId);
    
    /**
     * 
     * <p>生成比较版本序号</p>
     *
     * @return
     */
    @Transactional
    String getVersionSeq();
    
    /**
     * 
     * <p>8位的流水号</p>
     *
     * @param type
     * @return
     */
    @Transactional
    Long getVersionIndex( String type );

    /**
     * 
     * <p>采番规则：
     * 	编码位数：限定编号长度；
     * 	单据编号规则：前缀+前缀分隔符+日期格式+日期后分隔符+流水号范围；
     * 	如：编码位数为：15，前缀为：PIO，前缀分隔符为：－，日期格式为：yyyyMMdd(系统日期)，日期后分隔符：－，流水号范围为：年
     * 	那么生成的号应该是：订单号＝PIO-20130305-（15－（PIO-20130305-的长度13）01），最后生成的号为：PIO-20130305-01
     * 	流水号范围：年、月；如里是年，那么流水号的生成从1累加到这年的结束；如果是月，流水号的生成从1累加到这个月结束
     * 	</p>
     *
     * @param seqLength 编号位数
     * @param prefix 前缀
     * @param separator1 前缀分隔符
     * @param seqDateFormat 日期格式
     * @param separator2 日期后分隔符
     * @param seqGenerateBy 流水号范围 （年/月/日）
     * @param typeName
     * @return
     */
    @Transactional
    String getSeqOrderType(Integer seqLength, String prefix, String separator1, String seqDateFormat, String separator2, String seqGenerateBy, String typeName);

    /**
     * 生成SEQ
     * @param key
     * @param number
     * @param typeName
     * @return
     */
    @Transactional
    String getSequence(String key, int number, String typeName);
    
    /**
     * 
     * <p>生成供应商编号</p>
     *
     * @return
     */
    @Transactional
    String getCarrierCd();

    /**
     * 
     * <p>配载单号</p>
     *
     * @param whCode
     * @return
     */
    @Transactional
    String getStowageNumber(String whCode);

    /**
     * 
     * <p>生成辅料编号</p>
     *
     * @param whCode
     * @return
     */
    @Transactional
    String getAccessoryCode(String whCode);

    /**
     * 
     * <p>计费单号</p>
     *
     * @param whCode
     * @return
     */
    @Transactional
    String getFeeBillCode(String whCode);
    
    /**
     * 
     * <p>生成库存对照用的ID</p>
     *
     * @return
     */
    @Transactional
    String getInventoryId();
    
    @Transactional
    String getSingleGroupNo( Long waveDoc );
    
    /**
     * ヤマトの送り状Ｎｏ
     * @return
     */
    @Transactional
    String getYamatoSequence( String fromNo, String toNo );
    
    /**
     * 福山の送り状Ｎｏ
     * @return
     */
    @Transactional
    String getFukuyamaSequence( String fromNo, String toNo );
}
