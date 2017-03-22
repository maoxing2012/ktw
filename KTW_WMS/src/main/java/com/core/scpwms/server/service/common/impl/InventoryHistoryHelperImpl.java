package com.core.scpwms.server.service.common.impl;

import java.util.Date;

import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.history.InventoryHistory;
import com.core.scpwms.server.model.inbound.AsnDetail;
import com.core.scpwms.server.model.inbound.InboundHistory;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.outbound.OutboundDeliveryDetail;
import com.core.scpwms.server.model.outbound.OutboundHistory;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.task.WtHistory;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.service.common.InventoryHistoryHelper;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 
 * @description 库存履历辅助实现类
 * @author MBP:xiaoyan<br/>
 * @createDate 2014-2-13
 * @version V1.0<br/>
 */
public class InventoryHistoryHelperImpl extends DefaultBaseManager implements
		InventoryHistoryHelper {

	@Override
	public InventoryHistory saveInvHistory(Inventory inv, Double updateQtyEa,
			OrderType iot, Labor labor, String hisType, String orderSeq,
			Long refId, String memo, String relatedBill1, String relatedBill2) {
		InventoryHistory invHistory = new InventoryHistory();

		PackageDetail basePack = inv.getBasePackage();
		// 基本单位数量
		double updateQty = PrecisionUtils.formatByBasePackage(updateQtyEa, basePack);

		invHistory.setHisType(hisType);
		invHistory.setIot(iot);
		invHistory.setLabor(labor);
		invHistory.setOperateTime(new Date());
		invHistory.setOrderSeq(orderSeq);
		invHistory.setQtyEach(new Double(updateQty));
		invHistory.setQtyPkg(new Double(updateQty));
		invHistory.setWh(inv.getWh());
		invHistory.setReferenceHis(refId);
		invHistory.setOperateMemo(memo);
		invHistory.setRelatedBill1(relatedBill1);
		invHistory.setRelatedBill2(relatedBill2);
		invHistory.setInvInfo(inv.getInventoryInfo());

		invHistory.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		commonDao.store(invHistory);

		return invHistory;
	}
	
	@Override
	public InboundHistory saveInboundHistory(AsnDetail asnDetail, Bin bin, String container, PackageDetail pack, Quant quant, double receiveQty, String invStatus, Long laborId) {
		InboundHistory inboundHistory = new InboundHistory();
		InventoryInfo invInfo = new InventoryInfo();
		invInfo.setOwner(asnDetail.getOwner() == null ? asnDetail.getAsn().getOwner() : asnDetail.getOwner());
		invInfo.setQuant(quant);
		invInfo.setBin(bin);
		invInfo.setPalletSeq(null);
		invInfo.setPackageDetail(pack);
		invInfo.setTrackSeq(asnDetail.getAsn().getAsnNumber());
		invInfo.setInboundDate(new Date());
		invInfo.setInvStatus(invStatus);
		invInfo.setContainerSeq(container);
		inboundHistory.setInvInfo(invInfo);

		inboundHistory.setWh(asnDetail.getAsn().getWh());
		inboundHistory.setAsn(asnDetail.getAsn());
		inboundHistory.setDetail(asnDetail);
		inboundHistory.setExecuteQty(receiveQty);
		inboundHistory.setReceiveDate(invInfo.getInboundDate());
		inboundHistory.setLabor(commonDao.load(Labor.class, laborId));
		
		inboundHistory.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		inboundHistory.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		commonDao.store(inboundHistory);
		return inboundHistory;
	}

	@Override
	public WtHistory saveWtHistory(WarehouseTask wt, InventoryInfo srcInv,
			InventoryInfo descInv, Labor labor, double qty, Boolean freeStatus, Date operDate) {
		WtHistory wh = new WtHistory();

		wh.setWt(wt);

		wh.setInvInfo(srcInv);
		wh.setLabor(labor);

		wh.setDescBin(descInv.getBin());
		wh.setDescPalletSeq(descInv.getPalletSeq());
		wh.setDescContainerSeq(descInv.getContainerSeq());

		wh.addExecuteQty(qty);
		wh.setOperateTime(operDate == null ? new Date() : operDate);//作业时间
		wh.setRelatedBill1(wt.getDocSequence());// 上架单号/质检单号/加工单号/移位单号等
		wh.setRelatedBill2(wt.getWo() != null ? wt.getWo().getOrderSequence()
				: null);// WO号
		wh.setRelatedBill3(wt.getTaskSequence());// WT号
		wh.setCreateInfo(new CreateInfo(UserHolder.getUser()));

		commonDao.store(wh);
		return wh;
	}

	@Override
	public OutboundHistory saveShipHistory(OutboundDeliveryDetail detail,
			InventoryInfo invInfo, double qty, Labor labor, Boolean freeStatus) {
		OutboundHistory sh = new OutboundHistory();
		sh.setWh(detail.getWh());
		sh.setObd(detail.getObd());
		sh.setDetail(detail);
		sh.setInvInfo(invInfo);
		sh.addExecuteQty(qty);
		sh.setLabor(labor);
		sh.setShipDate(new Date());
		sh.setCreateInfo(new CreateInfo(UserHolder.getUser()));

		commonDao.store(sh);
		return sh;
	}

}
