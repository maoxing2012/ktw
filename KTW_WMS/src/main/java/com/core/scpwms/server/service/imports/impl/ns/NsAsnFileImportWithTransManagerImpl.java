package com.core.scpwms.server.service.imports.impl.ns;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.supercsv.prefs.CsvPreference;

import com.core.business.model.domain.CreateInfo;
import com.core.business.utils.DoubleUtil;
import com.core.business.web.security.UserHolder;
import com.core.scpwms.server.constant.WmsConstant4Ktw;
import com.core.scpwms.server.enumerate.EnuAsnDetailStatus;
import com.core.scpwms.server.enumerate.EnuAsnStatus;
import com.core.scpwms.server.enumerate.EnuBusRolsType;
import com.core.scpwms.server.enumerate.EnuFixDiv;
import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.enumerate.EnuPackageLevel;
import com.core.scpwms.server.enumerate.EnuStockDiv;
import com.core.scpwms.server.enumerate.EnuTemperatureDiv;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inbound.Asn;
import com.core.scpwms.server.model.inbound.AsnDetail;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.service.common.BCManager;
import com.core.scpwms.server.service.common.OrderStatusHelper;
import com.core.scpwms.server.service.imports.AsnFileImportWithTransManager;
import com.core.scpwms.server.service.imports.SkuFileImportWithTransManager;
import com.core.scpwms.server.service.imports.impl.BaseFileImportWithTransManagerImpl;
import com.core.scpwms.server.service.inbound.AsnManager;
import com.core.scpwms.server.util.DateUtil;

/**
 * 日本食研的入库数据导入
 * @author mousachi
 *
 */
public class NsAsnFileImportWithTransManagerImpl extends NsSkuFileImportWithTransManagerImpl implements AsnFileImportWithTransManager,NsAsnConstant {
	private Warehouse wh = null;
	private Plant plant = null;
	private Owner owner = null;
	
	private BCManager bcManager;
	private OrderStatusHelper orderStatusHelper;
	private SkuFileImportWithTransManager skuFileImportWithTransManager;
	private AsnManager asnManager;
	private Map<String, BizOrg> vendorMap = new HashMap<String, BizOrg>();
	private Map<String, OrderType> orderTypeMap = null;
	
	/**
	 * 没有头行
	 */
	@Override
	public String[] createAsn(String[][] csv, List<String[]> result, Owner owner, boolean isForce) {
        // 订单数
        int headNum = 0;
        // 订单明细数
        int detailNum = 0;

        this.wh = owner.getPlant().getWh();
        this.plant =  owner.getPlant();
        this.owner = owner;
        this.orderTypeMap = getOrderTypeMap(ASN);

        if (csv.length > 0) {
            String eta = "";
            String custBillNo = "";

            // 头信息
            List<Asn> headList = new ArrayList<Asn>();
            // 明细信息
            List<AsnDetail> detailList = new ArrayList<AsnDetail>();
            // delete list
            List<Asn> toDeleteHeadList = new ArrayList<Asn>();
            
            // 当前头信息
            Asn nowAsn = null;

            // 头行不要
            for (int i = 0; i < csv.length; i++) {
                if (COLUMN_NUM > csv[i].length) {
                	result.add(new String[] { "1", "不正なファイル。" });
                    break;
                }

                // 订单号，出库日一致
                if (eta.equals(csv[i][ETA]) && custBillNo.equals(csv[i][ORDER_NO])) {
                    // =============DETAIL=================
                	if( nowAsn != null ){
	                    AsnDetail detail = createDetail(result, csv[i], i + 1, nowAsn);
	                    if( detail != null ){
		                    detailList.add(detail);
		                    detailNum++;
	                    }
                    }
                } else {
                    eta = csv[i][ETA];
                    custBillNo = csv[i][ORDER_NO];

                    // =============HEAD=================
                    Asn oldAsn = getOldAsn(DateUtil.getDate(eta, DateUtil.PURE_DATE_FORMAT), custBillNo, owner.getId());
                    if( oldAsn != null && EnuAsnStatus.STATUS100.equals(oldAsn.getStatus()) ){
                    	toDeleteHeadList.add(oldAsn);
                    }
                    else if( oldAsn != null && !EnuAsnStatus.STATUS100.equals(oldAsn.getStatus()) ){
                    	result.add(new String[] { String.valueOf(i + 1), 
                    			"同指示番号の入荷伝票はすでに受付済になりましたので、上書き不可。伝票番号：" + custBillNo});
                    	nowAsn = null;
                    	continue;
                    }
                    
                    nowAsn = createHead(result, csv[i], i + 1);
                    if( nowAsn != null ){
	                    headList.add(nowAsn);
	                    headNum++;
                    }

                    // =============DETAIL=================
                    if( nowAsn != null ){
	                    AsnDetail detail = createDetail(result, csv[i], i + 1, nowAsn);
	                    if( detail != null ){
		                    detailList.add(detail);
		                    detailNum++;
	                    }
                    }
                }
            }

            // 强制更新，跳过异常行
            if (isForce) {
            	if( toDeleteHeadList != null && toDeleteHeadList.size() > 0 ){
            		for (Asn head : toDeleteHeadList) {
                        asnManager.delete(head);
                    }
            	}
            	
                for (Asn head : headList) {
                    // 无明细的头，跳过
                    if (head.getDetails() == null || head.getDetails().size() == 0)
                        continue;
                    
                    getCommonDao().store(head);
                    orderStatusHelper.changeStatus(head, EnuAsnStatus.STATUS100);
                    asnManager.updateTotalInfo(head.getId());
                }
            }
            // 非强制更新
            else {
                // 无异常
                if (result.size() == 0) {
                	if( toDeleteHeadList != null && toDeleteHeadList.size() > 0 ){
                		for (Asn head : toDeleteHeadList) {
                            asnManager.delete(head);
                        }
                	}
                	
                    for (Asn head : headList) {
                        getCommonDao().store(head);
                        orderStatusHelper.changeStatus(head, EnuAsnStatus.STATUS100);
                        asnManager.updateTotalInfo(head.getId());
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
	
	private Asn getOldAsn( Date eta, String custBillNo, Long ownerId ){
		// 取消状态的订单不在对比范围内
		String hql = " select asn.id from Asn asn where asn.owner.id = :ownerId and asn.relatedBill1 = :custBillNo and asn.eta = :eta and asn.status <> 0";
		List<Long> asnIds = commonDao.findByQuery(hql, 
				new String[]{"ownerId", "eta" , "custBillNo"}, new Object[]{ownerId, eta, custBillNo});
		
		if( asnIds == null || asnIds.size() == 0 )
			return null;
		
		else 
			return commonDao.load(Asn.class, asnIds.get(0));
	}
	
	private AsnDetail createDetail(List<String[]> result, String[] csvRow, int rowNo, Asn asn){
		if( asn == null )
			return null;
		
		boolean isValid = Boolean.TRUE;
		
		AsnDetail asnDetail = new AsnDetail();
		asnDetail.setAsn(asn);
		asnDetail.setStatus(EnuAsnDetailStatus.STATUS100);
		asnDetail.setLineNo(getDouble(csvRow[LINE_NO]));
		asnDetail.setSubLineNo(getInteger(csvRow[SUB_LINE_NO]));
		Sku sku = getSku(csvRow[SKU_CD], owner.getId());
		if(sku == null){
			// 自动新建
			sku = createSku(asn.getWh().getId(), csvRow, result, rowNo);
			// 如果新建失败
			if( sku == null ){
				isValid = Boolean.FALSE;
			}
		}
		if( sku != null ){
			asnDetail.setSku(sku);
			// 库存类型 
			asnDetail.setStockDiv(sku.getStockDiv());
			// 包装
			asnDetail.setPlanPackage(sku.getProperties().getPackageInfo().getP1000());
		}
		// 指定批次属性收货
		asnDetail.setExpDate(csvRow[EXP_DATE].length() == 8 ? DateUtil.getDate(csvRow[EXP_DATE], DateUtil.PURE_DATE_FORMAT) : null);
		
		// 预期数量（基本单位）
		// 出荷数（基本）/基本チェック数
		double planQty = DoubleUtil.div(getDouble(csvRow[PLAN_QTY]), sku.getProperties().getBaseCheckQty4NS());
		asnDetail.setPlanQty(planQty);
		
		// 预计收货的库存状态
		asnDetail.setInvStatus(getWmsInvStatus(csvRow[STATUS_DIV]));
		
		// 扩展字段
		asnDetail.setExtString1(toString4Arr(csvRow));
		
		if( !isValid )
			return null;
		
		asn.getDetails().add(asnDetail);
		return asnDetail;
	}
	
	// 生成头部
    private Asn createHead(List<String[]> result, String[] csvRow, int rowNo) {
        String today = DateUtil.getCurrentDate(DateUtil.PURE_DATE_FORMAT);
        boolean isValid = Boolean.TRUE;
        
        Asn asn = new Asn();
        asn.setWh(wh);
        asn.setPlant(plant);
        asn.setOwner(owner);
        // 客户订单号
        asn.setRelatedBill1(csvRow[ORDER_NO]);
        // 开单时间
        asn.setTransactionDate(new Date());
        // 预计到达时间
        // TODO
//        if( today.compareTo(csvRow[ETA]) > 0 ){
//        	result.add(new String[]{ String.valueOf(rowNo), "過去の日付：" + csvRow[ETA]});
//        	isValid = Boolean.FALSE;
//        }
        asn.setEta( DateUtil.getDate(csvRow[ETA], DateUtil.PURE_DATE_FORMAT));
        // 发件人/供应商
        asn.setSupplier(getNSVendor(csvRow[SHIP_WH_CD], csvRow[SHIP_WH_NM]));
        // 接口接收/手工创建
        asn.setEdiData(Boolean.TRUE);
        // 单据类型(收货限定)
        OrderType orderType = getNsOrderType(csvRow[ASN_DIV]);
        if( orderType == null ){
        	result.add(new String[]{ String.valueOf(rowNo), "不正な入庫区分：" + csvRow[ASN_DIV]});
        	isValid = Boolean.FALSE;
        }
        else{
        	// ASN单号
            asn.setAsnNumber(bcManager.getOrderSequence(orderType, wh.getId()));
        }
        asn.setOrderType(orderType);
        //　運送便CD
        asn.setExtString1(csvRow[TRANS_BIN_CD]);
        // 運送便名
        asn.setExtString2(csvRow[TRANS_BIN_NM]);
        // 送り状No
        asn.setTransNumber(csvRow[DELIVERY_NO]);
        // 创建日期
        asn.setCreateInfo(new CreateInfo(UserHolder.getUser()));
        
        if( !isValid )
        	return null;
        
        return asn;
    }

	private BizOrg getNSVendor( String vendorCode , String vendorName ){
		if( StringUtils.isEmpty(vendorCode) ){
			vendorCode = owner.getCode();
			vendorName = owner.getName() + "　ベンダー";
		}
		
		if( vendorMap.get(vendorCode) == null ){
			BizOrg vendor = getVendor(plant.getId(), vendorCode);
			if( vendor == null ){
				vendor = new BizOrg();
				vendor.setCode(vendorCode);
				vendor.setName(vendorName);
				vendor.setPlant(plant);
				vendor.setType(EnuBusRolsType.V);
				vendor.setCreateInfo(new CreateInfo("BAT"));
				commonDao.store(vendor);
			}
			vendorMap.put(vendor.getCode(), vendor);
			return vendor;
		}
		else{
			return vendorMap.get(vendorCode);
		}
	}
	
	private OrderType getNsOrderType( String nsOrderTypeCode ){
		if( "1".equals(nsOrderTypeCode) ){
			//仕入れ入荷
			return orderTypeMap.get(WmsConstant4Ktw.ASN01);
		}
		else if( "2".equals(nsOrderTypeCode) ){
			//移動入荷
			return orderTypeMap.get(WmsConstant4Ktw.ASN02);
		}
		else 
			return null;
	}
	
	private Sku createSku( Long whId, String[] csvRow , List<String[]> result, int rowNo){
		NsSkuBean nsSku = new NsSkuBean();
		nsSku.setSkuCode(csvRow[SKU_CD]);
		nsSku.setSkuName(csvRow[SKU_NM]);
		nsSku.setSpecNo(null);
		nsSku.setTempDiv(csvRow[TEMP_DIV]);
		nsSku.setWeight( getDouble(csvRow[WEIGHT]));
		nsSku.setBaseCheckQty( getDouble(csvRow[BASE_CHECK_QTY]) );
		nsSku.setBaseUnitCode(csvRow[PS_UNIT_CD]);
		nsSku.setBaseUnitName(csvRow[PS_UNIT]);
		nsSku.setBlIn( getDouble(csvRow[BL_IN]) );
		nsSku.setBlUnitCode(csvRow[BL_UNIT_CD]);
		nsSku.setBlUnitName(csvRow[BL_UNIT]);
		nsSku.setCsIn( getDouble(csvRow[CS_IN]) );
		nsSku.setCsUnitCode(csvRow[CS_UNIT_CD]);
		nsSku.setCsUnitName(csvRow[CS_UNIT]);
		nsSku.setSpecs(csvRow[SPECS]);
		nsSku.setPackFlg(csvRow[PACK_FLG]);
		nsSku.setJanCode(csvRow[JAN]);
		nsSku.setItfCode1(csvRow[BL_ITF]);
		nsSku.setItfCode2(csvRow[CS_ITF]);
		nsSku.setShipExpDays(getInteger(csvRow[SHIP_EXP_DAYS]));
		nsSku.setExpDays(null);
		Sku sku = updateSku4NS(whId, nsSku);
		
		return sku;
	}
	
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
	
	public SkuFileImportWithTransManager getSkuFileImportWithTransManager() {
		return skuFileImportWithTransManager;
	}

	public void setSkuFileImportWithTransManager(
			SkuFileImportWithTransManager skuFileImportWithTransManager) {
		this.skuFileImportWithTransManager = skuFileImportWithTransManager;
	}

	public AsnManager getAsnManager() {
		return asnManager;
	}

	public void setAsnManager(AsnManager asnManager) {
		this.asnManager = asnManager;
	}
}
