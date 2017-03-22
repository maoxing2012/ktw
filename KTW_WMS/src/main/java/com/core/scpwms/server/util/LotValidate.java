package com.core.scpwms.server.util;

import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.server.enumerate.EnuLotFieldType;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.lot.LotInfo;
import com.core.scpwms.server.model.lot.LotInputData;

/**
 * 
 * <p>验证输入的批次信息是否符合设定</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/30<br/>
 */
@SuppressWarnings("all")
public class LotValidate {

    public static String[] validateLotInf(Sku sku, LotInputData lotDate, boolean isEdit) {

        // 批次属性定义
        LotInfo lotInfo = sku.getProperties().getLotInfo() == null ? sku.getPlant().getWhingProperties().getLotInfo()
                : sku.getProperties().getLotInfo();

        if(lotInfo.getLdi1() != null){
        	if (EnuLotFieldType.LOTFIELD_2.equals(lotInfo.getLdi1().getInputType()) && !isEdit
                    && StringUtils.isEmpty(lotDate.getProperty1())) {
                return new String[] { sku.getName(), lotInfo.getLdi1().getTitle() };

            }
        }

        if(lotInfo.getLdi2() != null){
        	if (EnuLotFieldType.LOTFIELD_2.equals(lotInfo.getLdi2().getInputType()) && !isEdit
                    && StringUtils.isEmpty(lotDate.getProperty2())) {
                return new String[] {  sku.getName(), lotInfo.getLdi2().getTitle() };
            }
        }
        
        if(lotInfo.getLdi3() != null){
        	if (EnuLotFieldType.LOTFIELD_2.equals(lotInfo.getLdi3().getInputType()) && !isEdit
                    && StringUtils.isEmpty(lotDate.getProperty3())) {
                return new String[] {  sku.getName(), lotInfo.getLdi3().getTitle() };
            }
        }

        if(lotInfo.getLdi4() != null){
        	if (EnuLotFieldType.LOTFIELD_2.equals(lotInfo.getLdi4().getInputType()) && !isEdit
                    && StringUtils.isEmpty(lotDate.getProperty4())) {
                return new String[] {  sku.getName(), lotInfo.getLdi4().getTitle() };
            }
        }
        
        if(lotInfo.getLdi5() != null){
        	if (EnuLotFieldType.LOTFIELD_2.equals(lotInfo.getLdi5().getInputType()) && !isEdit
                    && StringUtils.isEmpty(lotDate.getProperty5())) {
                return new String[] {  sku.getName(), lotInfo.getLdi5().getTitle() };
            }
        }

        if(lotInfo.getLdi6() != null){
        	if (EnuLotFieldType.LOTFIELD_2.equals(lotInfo.getLdi6().getInputType()) && !isEdit
                    && StringUtils.isEmpty(lotDate.getProperty6())) {
                return new String[] {  sku.getName(), lotInfo.getLdi6().getTitle() };
            }
        }
        
        if(lotInfo.getLdi7() != null){
        	if (EnuLotFieldType.LOTFIELD_2.equals(lotInfo.getLdi7().getInputType()) && !isEdit
                    && StringUtils.isEmpty(lotDate.getProperty7())) {
                return new String[] {  sku.getName(), lotInfo.getLdi7().getTitle() };
            }
        }
        
        if(lotInfo.getLdi8() != null){
        	if (EnuLotFieldType.LOTFIELD_2.equals(lotInfo.getLdi8().getInputType()) && !isEdit
                    && StringUtils.isEmpty(lotDate.getProperty8())) {
                return new String[] {  sku.getName(), lotInfo.getLdi8().getTitle() };
            }
        }
        
        if(lotInfo.getLdi9() != null){
        	if (EnuLotFieldType.LOTFIELD_2.equals(lotInfo.getLdi9().getInputType()) && !isEdit
                    && StringUtils.isEmpty(lotDate.getProperty9())) {
                return new String[] {  sku.getName(), lotInfo.getLdi9().getTitle() };
            }
        }
        
        if(lotInfo.getLdi10() != null){
        	if (EnuLotFieldType.LOTFIELD_2.equals(lotInfo.getLdi10().getInputType()) && !isEdit
                    && StringUtils.isEmpty(lotDate.getProperty10())) {
                return new String[] { sku.getName(), lotInfo.getLdi10().getTitle() };
            }
        }
        return null;
    }
}
