package com.core.scpwms.server.util;

import java.util.List;

import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 不从策略分配出来的执行任务都要对目标库位进行验证 验证的位置 要考虑 是否要放到发行中，还是放到确认中
 * 
 * @author suin
 */
@SuppressWarnings("all")
public class BinValidate {

	/**
	 * 目前验证内容，后期待补充 1.商品限定 有无托盘都要验证 2.批次限定 有无托盘都要验证 3.锁限定 有无托盘都要验证 4.托盘限定 有托盘要验证
	 * 5.预计上架商品的包装限定 无托盘要验证 6.库满度限定 无托盘要验证 7.可分配数量限定 无托盘要验证
	 * 
	 * 参数 目标库位 库位库容详细 批次属性 Bin bin, BinInfo binInfo(需要在调用之前生产),Quant
	 * quant(需要在调用之前生产)
	 * 
	 * 计划包装 计划包装数量 PackageDetail planPack,Double planPackQty
	 * 
	 * 使用托盘状态 String enuPallet 1.HASPALLET(有托盘) 2.NOPALLET(无托盘)
	 */
	public static String AllocateBinValidate(Bin bin, Quant quant, Owner owner, PackageDetail packageDetail, Double qty) {
		// 库位上的库存
		Double currentQty = bin.getBinInvInfo().getBinInfo().getCurrentQty() == null ? 0.0D
				: bin.getBinInvInfo().getBinInfo().getCurrentQty();
		// 待上架的库位
		Double queuedQty = bin.getBinInvInfo().getBinInfo().getQueuedQty() == null ? 0.0D
				: bin.getBinInvInfo().getBinInfo().getQueuedQty();
		// 库位上的库存 + 待上架的库位
		double binInvQty = currentQty.doubleValue() + queuedQty.doubleValue();
				
		// 逻辑库位，数量不限制，混放不限制
		if( bin.getProperties().getIsLogicBin() != null && bin.getProperties().getIsLogicBin().booleanValue() )
			return null;
		
		// 满载度验证(主要是验证当前库位是否已满)，如果是托盘库存只考虑托盘数，如果没有托盘则考虑满载度

//		// 当前数量%与库位属性满载度验证
//		if ((bin.getProperties().getFullScale() != null
//				&& bin.getBinInvInfo().getBinInfo().getFullRateQty() != null && bin
//				.getBinInvInfo().getBinInfo().getFullRateQty().longValue() >= bin
//				.getProperties().getFullScale().longValue())) {
//			return ExceptionConstant.BIN_FULL_SCALE_ERROR;
//		}
//		// 当前体积%与库位属性满载度验证
//		if ((bin.getProperties().getFullScale() != null
//				&& bin.getBinInvInfo().getBinInfo().getFullRateVolumn() != null && bin
//				.getBinInvInfo().getBinInfo().getFullRateVolumn().longValue() >= bin
//				.getProperties().getFullScale().longValue())) {
//			return ExceptionConstant.BIN_FULL_SCALE_ERROR;
//		}
//		// 当前重量%与库位属性满载度验证
//		if ((bin.getProperties().getFullScale() != null
//				&& bin.getBinInvInfo().getBinInfo().getFullRateWeight() != null && bin
//				.getBinInvInfo().getBinInfo().getFullRateWeight().longValue() >= bin
//				.getProperties().getFullScale().longValue())) {
//			return ExceptionConstant.BIN_FULL_SCALE_ERROR;
//		}
//
//		// 当前标准容量与库位属性满载度验证
//		if ((bin.getProperties().getFullScale() != null
//				&& bin.getBinInvInfo().getBinInfo().getFullRateMetric() != null && bin
//				.getBinInvInfo().getBinInfo().getFullRateMetric().longValue() >= bin
//				.getProperties().getFullScale().longValue())) {
//			return ExceptionConstant.BIN_FULL_SCALE_ERROR;
//		}
//		
//		// 存放数量验证(跟据剩余库溶算出预计上架的包装数量)
//		double canPutawayQty = bin.getBinInvInfo().getCanPutAway(quant.getSku(), packageDetail, false);
//		if (qty > canPutawayQty) {
//			return ExceptionConstant.BIN_FULL_SCALE_ERROR;
//		}
		
		return null;
	}
}
