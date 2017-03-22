package com.core.scpwms.server.service.imports.impl.ns;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.supercsv.prefs.CsvPreference;

import com.core.business.model.domain.ContractInfo;
import com.core.business.model.domain.CreateInfo;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.scpwms.server.constant.WmsConstant4Ktw;
import com.core.scpwms.server.enumerate.EnuAsnDetailStatus;
import com.core.scpwms.server.enumerate.EnuAsnStatus;
import com.core.scpwms.server.enumerate.EnuBusRolsType;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.outbound.OutboundDelivery;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Carrier;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.imports.ObdFileImportWithTransManager;
import com.core.scpwms.server.service.outbound.OutboundDeliveryManager;
import com.core.scpwms.server.service.transport.TransportManager;
import com.core.scpwms.server.util.DateUtil;

/**
 * 日本食研的出库订单的导入
 * 
 * @author mousachi
 *
 */
public class NsObdFileImportWithTransManagerImpl extends NsSkuFileImportWithTransManagerImpl implements ObdFileImportWithTransManager, NsObdConstant {
	private Warehouse wh = null;
	private Plant plant = null;
	private Owner owner = null;
	
	private BCManager bcManager;
	private OrderStatusHelper orderStatusHelper;
	private OutboundDeliveryManager obdManager;
	private TransportManager transportManager;
	private Map<String, BizOrg> shopMap = new HashMap<String, BizOrg>();
	private Map<String, Carrier> carrierMap = new HashMap<String, Carrier>();
	private Map<String, OrderType> orderTypeMap = null;
	private Map<String, Sku> skuMap = new HashMap<String, Sku>();
	
	public BCManager getBcManager() {
		return bcManager;
	}

	public void setBcManager(BCManager bcManager) {
		this.bcManager = bcManager;
	}

	public OrderStatusHelper getOrderStatusHelper() {
		return orderStatusHelper;
	}

	public void setOrderStatusHelper(OrderStatusHelper orderStatusHelper) {
		this.orderStatusHelper = orderStatusHelper;
	}

	public OutboundDeliveryManager getObdManager() {
		return obdManager;
	}

	public void setObdManager(OutboundDeliveryManager obdManager) {
		this.obdManager = obdManager;
	}
	
	public TransportManager getTransportManager() {
		return transportManager;
	}

	public void setTransportManager(TransportManager transportManager) {
		this.transportManager = transportManager;
	}

	/**
	 * 没有头行
	 */
	@Override
	public String[] createObd(String[][] csv, List<String[]> result, Owner owner, boolean isForce) {
        // 订单数
        int headNum = 0;
        // 订单明细数
        int detailNum = 0;

        this.wh = owner.getPlant().getWh();
        this.plant =  owner.getPlant();
        this.owner = owner;
        this.shopMap.clear();
        this.carrierMap.clear();
        this.skuMap.clear();
        this.orderTypeMap = getOrderTypeMap(OBD);

        if (csv.length > 0) {
            String etd = "";//出荷日
            String eta = "";//納品日
            String shopCd = "";//納品先コード
            String custBillNo = "";//受注番号
            String carrierCd = ""; // 運送業者
            String tempDiv = "";//温度帯

            // 头信息
            List<OutboundDelivery> headList = new ArrayList<OutboundDelivery>();
            // 明细信息
            List<OutboundDeliveryDetail> detailList = new ArrayList<OutboundDeliveryDetail>();
            
            // 当前头信息
            OutboundDelivery nowObd = null;

            // 头行不要
            for (int i = 0; i < csv.length; i++) {
                if (COLUMN_NUM > csv[i].length) {
                	result.add(new String[] { "1", "不正なファイル。" });
                    break;
                }

                // 订单号，出库日一致
                if (etd.equals(csv[i][SHIP_DATE]) && eta.equals(csv[i][DELIVERY_DATE]) 
                		&& shopCd.equals(csv[i][SHOP_CD]) && custBillNo.equals(csv[i][JYUTYU_NO]) 
                		&& carrierCd.equals(csv[i][CARRIER_CD]) && tempDiv.equals(csv[i][TEMP_DIV])) {
                    // =============DETAIL=================
                	if( nowObd != null ){
	                    OutboundDeliveryDetail detail = createDetail(result, csv[i], i + 1, nowObd);
	                    if( detail != null ){
		                    detailList.add(detail);
		                    detailNum++;
	                    }
                    }
                } else {
                	etd = csv[i][SHIP_DATE];//出荷日
                	eta = csv[i][DELIVERY_DATE];// 納品日
                	shopCd = csv[i][SHOP_CD];//納品先コード
                    custBillNo = csv[i][JYUTYU_NO];//受注No
                    carrierCd = csv[i][CARRIER_CD];//運送業者 
                    tempDiv = csv[i][TEMP_DIV];//温度帯

                    // =============HEAD=================
                    nowObd = createHead(result, csv[i], i + 1);
                    if( nowObd != null ){
	                    headList.add(nowObd);
	                    headNum++;
                    }

                    // =============DETAIL=================
                    if( nowObd != null ){
                    	OutboundDeliveryDetail detail = createDetail(result, csv[i], i + 1, nowObd);
	                    if( detail != null ){
		                    detailList.add(detail);
		                    detailNum++;
	                    }
                    }
                }
            }

            // 强制更新，跳过异常行
            if (isForce) {
            	List<Long> obdIds = new ArrayList<Long>();
                for (OutboundDelivery head : headList) {
                    // 无明细的头，跳过
                    if (head.getDetails() == null || head.getDetails().size() == 0)
                        continue;
                    
                    getCommonDao().store(head);
                    orderStatusHelper.changeStatus(head, EnuAsnStatus.STATUS100);
                    obdManager.updateTotalInfo(head.getId());
                    obdIds.add(head.getId());
                }
                if( obdIds != null && obdIds.size() > 0 ){
                	obdManager.publish(obdIds);
                }
            }
            // 非强制更新
            else {
                // 无异常
                if (result.size() == 0) {
                	List<Long> obdIds = new ArrayList<Long>();
                    for (OutboundDelivery head : headList) {
                        getCommonDao().store(head);
                        orderStatusHelper.changeStatus(head, EnuAsnStatus.STATUS100);
                        obdManager.updateTotalInfo(head.getId());
                        obdIds.add(head.getId());
                    }
                    
                    if( obdIds != null && obdIds.size() > 0 ){
                    	obdManager.publish(obdIds);
                    }
                }
                // 有异常
                else {
                	return null;
                }
            }
        }
        
        // 回传信息
        return new String[]{ String.valueOf(headNum), String.valueOf(detailNum) };
	}

	@Override
	public CsvPreference getCsvPreference() {
		return new CsvPreference('\'', '\t', "\r\n");
	}
	
	private OutboundDeliveryDetail createDetail(List<String[]> result, String[] csvRow, int rowNo, OutboundDelivery obd){
		if( obd == null )
			return null;
		
		boolean isValid = Boolean.TRUE;
		
		OutboundDeliveryDetail obdDetail = new OutboundDeliveryDetail();
		obdDetail.setObd(obd);
		obdDetail.setStatus(EnuAsnDetailStatus.STATUS100);
		obdDetail.setLineNo(getDouble(csvRow[LINE_NO]));
		obdDetail.setSubLineNo(getInteger(csvRow[SUB_LINE_NO]));
		
		Sku sku = getNsSku(owner.getId(), csvRow[SKU_CD]);
		if(sku == null){
			isValid = Boolean.FALSE;
			result.add(new String[]{String.valueOf(rowNo), "マスタ不備、商品コード：" + csvRow[SKU_CD]});
			return null;
		}
		
		if(sku != null) {
			obdDetail.setSku(sku);
			// 库存类型 
			obdDetail.setStockDiv(sku.getStockDiv());
			// 包装
			obdDetail.setPackageDetail(sku.getProperties().getPackageInfo().getP1000());
		}
		// 预定的保质期
		obdDetail.setExpDate(csvRow[EXP_DATE].length() == 8 ? DateUtil.getDate(csvRow[EXP_DATE], DateUtil.PURE_DATE_FORMAT) : null);
		
		//------------------------------------------------
		String canReversal = csvRow[CAN_REVERSAL];// 2：保质期不能颠倒
		String canMix = csvRow[CAN_MIX];//2：保质期不能混有
		String lastExp = csvRow[EXP_DATE3].trim();// 上次发货的保值期
		String minExp = csvRow[EXP_DATE2].trim();//客户指定的最小保质期
		String orderType = obd.getOrderType().getCode();
		
		// 保质期不能混
		if( canMix != null && "2".equals(canMix) ){
			obdDetail.setCanMixExp(Boolean.FALSE);
		}
		
		// 最小保质期是上次的发货的保值期
		if( canReversal != null && "2".equals(canReversal) && lastExp.length() == 8 ){
			obdDetail.setExpDateMin(DateUtil.getDate(lastExp, DateUtil.PURE_DATE_FORMAT));
		}
		
		// 出荷
		if( WmsConstant4Ktw.OBD01.equals(orderType) ){
			if( minExp.length() == 8 ){
				// 没有要求比上次保质期，直接设置
				if( obdDetail.getExpDateMin() == null ){
					obdDetail.setExpDateMin(DateUtil.getDate(minExp, DateUtil.PURE_DATE_FORMAT));
				}
				// 如果有要求上次保质期，选择比较大的保值期设置
				else if( lastExp.compareTo(minExp) < 0 ){
					obdDetail.setExpDateMin(DateUtil.getDate(minExp, DateUtil.PURE_DATE_FORMAT));
				}
			}
		}
		// 出库
		else{
			// 没有设上次，指定有设置
			if( lastExp.length() == 0 && minExp.length() == 8 ){
				obdDetail.setExpDateMin(DateUtil.getDate(minExp, DateUtil.PURE_DATE_FORMAT));// 指定最小批次
			}
			else if( lastExp.length() == 8 && minExp.length() == 8 ){
				obdDetail.setExpDateMin(DateUtil.getDate(minExp, DateUtil.PURE_DATE_FORMAT));
				obdDetail.setExpDateMax(DateUtil.getDate(minExp, DateUtil.PURE_DATE_FORMAT));
				obdDetail.setExpDateUni(DateUtil.getDate(minExp, DateUtil.PURE_DATE_FORMAT));// 指定批次
			}
		}
		
		// 预期数量（基本单位）
		// 出荷数（基本）/基本チェック数
		double planQty = DoubleUtil.div(getDouble(csvRow[BASE_QTY]), sku.getProperties().getBaseCheckQty4NS() == null ? 1D : sku.getProperties().getBaseCheckQty4NS());
		double sumInputQty = getDouble(csvRow[SUM_INPUT_QTY]);
		double sumBaseQty = getDouble(csvRow[SUM_BASE_QTY]);
		double sumQty =  DoubleUtil.div(sumBaseQty, sku.getProperties().getBaseCheckQty4NS());
		obdDetail.setPlanQty(planQty);
		
		// 预计收货的库存状态
		obdDetail.setInvStatus(getWmsInvStatus(csvRow[INV_STATUS]));
		
		// ---------------------扩展字段
		// 整个订单明细
		obdDetail.setExtString1(toString4Arr(csvRow));
		// 伝票番号
		obdDetail.setExtString2(csvRow[ORDER_SEQ]);
		// 入力単位
		obdDetail.setExtString3(csvRow[INPUT_UNIT]);
		// 消費是区分 0:外税　１：内税　２：非課税
		obdDetail.setExtString4(csvRow[TAX_DIV]);
		
		Double inputQty = getDouble(csvRow[INPUT_QTY]);
		Double baseQty = getDouble(csvRow[BASE_QTY]);
		
		
		Double totalInputQtyDouble = getDouble(csvRow[SUM_INPUT_QTY]);
		Double totalBaseQtyDouble = getDouble(csvRow[SUM_BASE_QTY]);
		Double baseInputCoefficient = DoubleUtil.div(totalBaseQtyDouble, totalInputQtyDouble);

		// 基本単位、入力単位的折算系数
		obdDetail.setExtDouble1(baseInputCoefficient);
		// 税率
		obdDetail.setExtDouble2(getDouble(csvRow[TAX_RATE]));
		// 入力数
		obdDetail.setExtDouble3(inputQty);
		// 基本数
		obdDetail.setExtDouble4(baseQty);
		// 入力单位的总数量
		obdDetail.setExtDouble5(sumInputQty);
		// PS单位的总数量
		obdDetail.setExtDouble6(sumQty);
		
		// 入力単にの単価
		obdDetail.setExtPrice1(getDouble(csvRow[INPUT_PRICE]));
		
		if( obd.getRelatedBill3() == null || !obd.getRelatedBill3().contains(csvRow[ORDER_SEQ]) ){
			if( obd.getRelatedBill3() == null ){
				obd.setRelatedBill3("");
			}
			
			if( obd.getRelatedBill3().length() > 0 ){
				obd.setRelatedBill3(obd.getRelatedBill3() + ",");
			}
			
			obd.setRelatedBill3(obd.getRelatedBill3() + csvRow[ORDER_SEQ]);
		}
		
		if( !isValid )
			return null;
		
		obd.getDetails().add(obdDetail);
		return obdDetail;
	}
	
	// 生成头部
    private OutboundDelivery createHead(List<String[]> result, String[] csvRow, int rowNo) {
        String today = DateUtil.getCurrentDate(DateUtil.PURE_DATE_FORMAT);
        boolean isValid = Boolean.TRUE;
        
        OutboundDelivery obd = new OutboundDelivery();
        obd.setWh(wh);
        obd.setPlant(plant);
        obd.setOwner(owner);
        
        // 客户订单号
        obd.setRelatedBill1(csvRow[JYUTYU_NO]);//受注No
        obd.setRelatedBill2(csvRow[ORDER_SEQ]);// 代表伝票番号
        
        //　専用伝票区分
        // 如果的　伝票発行区分是1，用1出荷案内
        if( "1".equals(csvRow[DELIVERY_BILL_DIV2]) ){
        	obd.setExtString1("3");// 0：自社伝票　1：専用伝票　2：CS伝票　3:出荷案内 4:CS専用
        }
        // 伝票情報！＝オール０
        else if( "2".equals(csvRow[DELIVERY_BILL_DIV]) 
        		&& !StringUtils.isEmpty(csvRow[DELIVERY_INFO]) 
        		&& StringUtils.isNumeric(csvRow[DELIVERY_INFO])
        		&& Long.parseLong(csvRow[DELIVERY_INFO]) > 0L ){
        	obd.setExtString1("4");// 0：自社伝票　1：専用伝票　2：CS伝票　3:出荷案内 4:CS専用
        }
        else{
        	obd.setExtString1(csvRow[DELIVERY_BILL_DIV]);// 0：自社伝票　1：専用伝票　2：CS伝票　3:出荷案内 4:CS専用
        }
        
        // 伝票情報
        obd.setExtString2(csvRow[DELIVERY_INFO]);
        // 担当営業所コード
        obd.setExtString3(csvRow[SALES_PART]);
        // 34（原）専用伝票区分
        obd.setExtString4(csvRow[DELIVERY_BILL_DIV]);
        // 35伝票発行区分
        obd.setExtString5(csvRow[DELIVERY_BILL_DIV2]);
        // 6納品先コード
        obd.setExtString6(csvRow[SHOP_CD]);
        // 44得意先コード
        obd.setExtString7(csvRow[CUST_CD]);
        // 温度帯
        obd.setTempDiv(getWmsTempDivCode(csvRow[TEMP_DIV]));
        // 开单时间
        obd.setTransactionDate(new Date());
        
        // 出荷日
        // TODO
//        if( today.compareTo(csvRow[SHIP_DATE]) > 0 ){
//        	result.add(new String[]{ String.valueOf(rowNo), "過去の日付：" + csvRow[SHIP_DATE]});
//        	isValid = Boolean.FALSE;
//        }
        obd.setEtd( DateUtil.getDate(csvRow[SHIP_DATE], DateUtil.PURE_DATE_FORMAT));
        
        // 納品日
        if( !StringUtils.isEmpty(csvRow[DELIVERY_DATE]) ){
        	obd.setEta( DateUtil.getDate(csvRow[DELIVERY_DATE], DateUtil.PURE_DATE_FORMAT));
        }
        
        // 收件人
        obd.setCustomer(getNSShop(csvRow[SHOP_CD], csvRow[SHOP_NM], csvRow[SHOP_NM2], csvRow[SHOP_ADD1], csvRow[SHOP_ADD2], csvRow[SHOP_POST], csvRow[SHOP_TEL]));
        
        // 收件人信息
        obd.setCustomerInfo(new ContractInfo());
        obd.getCustomerInfo().setLinkman(csvRow[SHOP_LINKMAN]);
        obd.getCustomerInfo().setAddress(csvRow[SHOP_ADD1]);
        obd.getCustomerInfo().setAddressExt(csvRow[SHOP_ADD2]);
        obd.getCustomerInfo().setPostcode(csvRow[SHOP_POST]);
        obd.getCustomerInfo().setPhone(csvRow[SHOP_TEL]);
        
        // 承运商
        obd.setCarrier(getNSCarrier(csvRow[CARRIER_CD], csvRow[CARRIER_NM]));
        
        // 接口接收/手工创建
        obd.setEdiData(Boolean.TRUE);
        
        // 单据类型(收货限定)
        OrderType orderType = getNsOrderType(csvRow[ORDER_DIV]);
        if( orderType == null ){
        	result.add(new String[]{ String.valueOf(rowNo), "不正な出荷・出庫区分：" + csvRow[ORDER_DIV]});
        	isValid = Boolean.FALSE;
        }
        else{
        	obd.setObdNumber(bcManager.getOrderSequence(orderType, wh.getId()));
        }
        obd.setOrderType(orderType);
        
        // 创建日期
        obd.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        
        if( !isValid )
        	return null;
        
        return obd;
    }
    
    private Sku getNsSku(Long ownerId, String skuCode ){
    	if( skuMap.get(skuCode) == null ){
			Sku sku = getSku(skuCode, ownerId);
			
			if( sku != null ){
				skuMap.put(skuCode, sku);
			}
			return sku;
		}
		else{
			return skuMap.get(skuCode);
		}
    }

	private BizOrg getNSShop( String shopCode , String shopName, String shopName2, String add1, String add2, String post, String tel){
		if( shopMap.get(shopCode) == null ){
			BizOrg shop = getShop(shopCode, owner.getId());
			if( shop == null ){
				shop = new BizOrg();
				shop.setCode(shopCode);
				shop.setName(shopName+shopName2);
				shop.setPlant(plant);
				shop.setOwner(owner);
				shop.setType(EnuBusRolsType.C);
				shop.setContractInfo(new ContractInfo());
				shop.getContractInfo().setAddress(add1);
				shop.getContractInfo().setAddressExt(add2);
				shop.getContractInfo().setPostcode(post);
				shop.getContractInfo().setPhone(tel);
				shop.setCreateInfo(new CreateInfo("BAT"));
				commonDao.store(shop);
				
				// 自动设置Course
				List<Long> bizorgIds = new ArrayList<Long>(1);
				bizorgIds.add(shop.getId());
				transportManager.resetCourse(bizorgIds);
			}
			shopMap.put(shop.getCode(), shop);
			return shop;
		}
		else{
			return shopMap.get(shopCode);
		}
	}
	
	private Carrier getNSCarrier( String carrierCode, String carrierName){
		if( StringUtils.isEmpty(carrierCode) )
			return null;
		
		if( carrierMap.get(carrierCode) == null ){
			Carrier carrier = getCarrier(carrierCode, plant.getId());
			if( carrier == null ){
				carrier = new Carrier();
				carrier.setCode(carrierCode);
				carrier.setName(carrierName);
				carrier.setPlant(plant);
				carrier.setWh(plant.getWh());
				carrier.setCreateInfo(new CreateInfo("BAT"));
				commonDao.store(carrier);
			}
			carrierMap.put(carrier.getCode(), carrier);
			return carrier;
		}
		else{
			return carrierMap.get(carrierCode);
		}
	}
	
	private OrderType getNsOrderType( String nsOrderTypeCode ){
		if( "1".equals(nsOrderTypeCode) ){
			//標準出荷
			return orderTypeMap.get(WmsConstant4Ktw.OBD01);
		}
		else if( "2".equals(nsOrderTypeCode) ){
			//移動出荷
			return orderTypeMap.get(WmsConstant4Ktw.OBD02);
		}
		else 
			return null;
	}
	
	
	private Sku getSku(Long whId, String skuCode, String[] invInfos ){
		NsSkuBean nsSku = new NsSkuBean();
		nsSku.setSkuCode(invInfos[SKU_CD]);
		nsSku.setSkuName(invInfos[SKU_NM]);
		nsSku.setSpecNo(invInfos[SPEC_NO]);
		nsSku.setTempDiv(invInfos[TEMP_DIV]);
		nsSku.setWeight( getDouble(invInfos[WEIGHT]));
		nsSku.setBaseCheckQty( getDouble(invInfos[BASE_CHECK_QTY]) );
		nsSku.setBaseUnitCode(null);
		nsSku.setBaseUnitName(invInfos[BASE_UNIT_NM]);
		nsSku.setBlIn( getDouble(invInfos[BL_IN]) );
		nsSku.setBlUnitCode(null);
		nsSku.setBlUnitName(invInfos[BL_UNIT_NM]);
		nsSku.setCsIn( getDouble(invInfos[CS_IN]) );
		nsSku.setCsUnitCode(null);
		nsSku.setCsUnitName(invInfos[CS_UNIT_NM]);
		nsSku.setSpecs(invInfos[SPECS]);
		nsSku.setPackFlg("1");
		nsSku.setJanCode(invInfos[JAN_CODE]);
		nsSku.setShipExpDays(null);
		nsSku.setExpDays(null);
		Sku sku = updateSku4NS(whId, nsSku);
		return sku;
	}

	public static Long getWmsTempDivCode( String tempDiv ){
		if( "0".equals(tempDiv)){
			return EnuTemperatureDiv.CW;
		}
		else if( "1".equals(tempDiv) ){
			return EnuTemperatureDiv.LC;
		}
		else if( "2".equals(tempDiv) ){
			return EnuTemperatureDiv.LD;
		}
		else{
			return EnuTemperatureDiv.UNDEF;
		}
	}
	
}
