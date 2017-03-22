/**
 * 
 */
package com.core.scpwms.server.service.imports.impl.ns;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.core.business.exception.BusinessException;
import com.core.business.utils.DoubleUtil;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.service.imports.InventoryFileImportWithTransManager;
import com.core.scpwms.server.service.inventory.InvManager;
import com.core.scpwms.server.util.DateUtil;

public class NsInventoryFileImportWithTransManager extends NsSkuFileImportWithTransManagerImpl implements InventoryFileImportWithTransManager {
	public static final int INV_LENGTH = 24;
	//0:倉庫ＣＤ	
	public static final int INV_WH_CD = 0;
	//1:商品コード
	public static final int INV_SKU_CD = 1;
	//2:商品名	
	public static final int INV_SKU_NM = 2;
	//3:特別品ＮＯ	
	public static final int INV_SPEC_NO = 3;
	//4:賞味期間	
	public static final int INV_EXP_DAYS = 4;
	//5:在庫ステータス	
	public static final int INV_INV_STATUS = 5;
	//6:賞味期限	
	public static final int INV_EXP_DATE = 6;
	//7:数量（基本）	
	public static final int INV_QTY = 7;
	//8:温度帯	
	public static final int INV_TEMP_DIV = 8;
	//9:基本単位当たり重量	
	public static final int INV_WEIGHT = 9;
	//10:基本チェック数	
	public static final int INV_BASE_CHECK_QTY = 10;
	//11:基本単位コード	
	public static final int INV_BASE_UNIT_CD = 11;
	//12:基本単位名	
	public static final int INV_BASE_UNIT_NM = 12;
	//13:包装入数	
	public static final int INV_BL_IN = 13;
	//14:包装単位コード	
	public static final int INV_BL_UNIT_CD = 14;
	//15:包装単位名	
	public static final int INV_BL_UNIT_NM = 15;
	//16:ケース入数	
	public static final int INV_CS_IN = 16;
	//17:ケース単位コード	
	public static final int INV_CS_UNIT_CD = 17;
	//18:ケース単位名称	
	public static final int INV_CS_UNIT_NM = 18;
	//19:包装形態名称	
	public static final int INV_SPECS = 19;
	//20:同梱不可フラグ	
	public static final int INV_PACK_FLG = 20;
	//21:JANCD	
	public static final int INV_JAN_CD = 21;
	//22:ITF_14_CD_B	
	public static final int INV_ITF_CD1 = 22;
	//23:ITF_14_CD_C
	public static final int INV_ITF_CD2 = 23;
	
	private InvManager invManager;
	
	private static Map<String, Sku> skuMap = new HashMap<String, Sku>();
	
	public InvManager getInvManager() {
		return invManager;
	}

	public void setInvManager(InvManager invManager) {
		this.invManager = invManager;
	}

	@Override
	public List<String[]> createInventory(Long whId, Long ownerId, List<List<String>> fileList) {
		List<String[]> result = new ArrayList<String[]>();
		
		if( fileList == null || fileList.size() == 0 )
			return result;
		
		skuMap.clear();
		
		int excelLineNo = 2;
		for( List<String> excelLine : fileList ){
			System.out.println( "----------" + excelLineNo );
			
			excelLine = formatExcelLine(excelLine, INV_LENGTH);
			
			Bin bin = getBin(excelLine.get(INV_WH_CD).trim(), whId);
			if(bin == null){
				result.add(new String[]{ String.valueOf(excelLineNo), "棚番を登録してください。" + excelLine.get(INV_WH_CD).trim() });
			}
			
			String skuCode = excelLine.get(INV_SKU_CD);
			if( StringUtils.isEmpty(skuCode) ){
				result.add(new String[]{ String.valueOf(excelLineNo), "商品名：必須項目。" });
			}
			Sku sku = getSku(whId, ownerId, skuCode, excelLine);
			String invStatus = getWmsInvStatus(excelLine.get(INV_INV_STATUS));
			Date expDate = DateUtil.getDate("29990101", DateUtil.PURE_DATE_FORMAT);
			if( !StringUtils.isEmpty(excelLine.get(INV_EXP_DATE)) && excelLine.get(INV_EXP_DATE).length() == 8 ){
				expDate = DateUtil.getDate(excelLine.get(INV_EXP_DATE), DateUtil.PURE_DATE_FORMAT);
			}
			
			Double qty = DoubleUtil.div(getDouble(excelLine.get(INV_QTY)), sku.getProperties().getBaseCheckQty4NS(), 0);
			invManager.newInv(ownerId, sku.getId(), bin.getId(), invStatus, expDate, qty, "初期在庫");
			excelLineNo++;
		}
		
		return result;
	}
	
	private Sku getSku(Long whId, Long ownerId, String skuCode, List<String> invInfos ){
		Sku sku = skuMap.get(skuCode);
		if( sku == null ){
			sku = getSku(skuCode, ownerId);
			if( sku == null ){
				NsSkuBean nsSku = new NsSkuBean();
				nsSku.setSkuCode(invInfos.get(INV_SKU_CD));
				nsSku.setSkuName(invInfos.get(INV_SKU_NM));
				nsSku.setSpecNo(invInfos.get(INV_SPEC_NO));
				nsSku.setTempDiv(invInfos.get(INV_TEMP_DIV));
				nsSku.setWeight( getDouble(invInfos.get(INV_WEIGHT)));
				nsSku.setBaseCheckQty( getDouble(invInfos.get(INV_BASE_CHECK_QTY)) );
				nsSku.setBaseUnitCode(invInfos.get(INV_BASE_UNIT_CD));
				nsSku.setBaseUnitName(invInfos.get(INV_BASE_UNIT_NM));
				nsSku.setBlIn( getDouble(invInfos.get(INV_BL_IN)) );
				nsSku.setBlUnitCode(invInfos.get(INV_BL_UNIT_CD));
				nsSku.setBlUnitName(invInfos.get(INV_BL_UNIT_NM));
				nsSku.setCsIn( getDouble(invInfos.get(INV_CS_IN)) );
				nsSku.setCsUnitCode(invInfos.get(INV_CS_UNIT_CD));
				nsSku.setCsUnitName(invInfos.get(INV_CS_UNIT_NM));
				nsSku.setSpecs(invInfos.get(INV_SPECS));
				nsSku.setPackFlg(invInfos.get(INV_PACK_FLG));
				nsSku.setJanCode(invInfos.get(INV_JAN_CD));
				nsSku.setItfCode1(invInfos.get(INV_ITF_CD1));
				nsSku.setItfCode2(invInfos.get(INV_ITF_CD2));
				nsSku.setShipExpDays(null);
				nsSku.setExpDays(getInteger(invInfos.get(INV_EXP_DAYS)));
				sku = updateSku4NS(whId, nsSku);
				skuMap.put(skuCode, sku);
				return sku;
			}
			else{
				skuMap.put(skuCode, sku);
				return sku;
			}
		}
		else{
			return sku;
		}
	}
	
	public static void main(String[] args){
		Date expDate = DateUtil.getDate("99999999", DateUtil.PURE_DATE_FORMAT);
		System.out.println(expDate);
	}
}
