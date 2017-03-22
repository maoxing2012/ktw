package com.core.scpwms.server.model.warehouse;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.Entity;
import com.core.scpwms.server.domain.BinInfo;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.util.PrecisionUtils;

/**
 * 
 * <p>
 * 库容信息
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/21<br/>
 */
@SuppressWarnings("all")
public class BinInvInfo extends Entity {
	private Bin bin;

	private BinInfo binInfo = new BinInfo();

	public Bin getBin() {
		return this.bin;
	}

	public void setBin(Bin bin) {
		this.bin = bin;
	}

	public BinInfo getBinInfo() {
		return this.binInfo;
	}

	public void setBinInfo(BinInfo binInfo) {
		this.binInfo = binInfo;
	}

	/**
	 * 
	 * <p>
	 * 刷新使用率数据
	 * </p>
	 * 
	 */
	public void releshRate() {
		// 逻辑库位不用更新库容
		if (bin.getProperties().getIsLogicBin())
			return;

		Double baseQty = bin.getProperties().getQuantity();
		Double baseVolumn = bin.getProperties().getVolume();
		Double baseWeight = bin.getProperties().getWeight();
		Double baseMetric = bin.getProperties().getMeasure();
		Double palletBaseQty = bin.getProperties().getPalletCount();

		if (baseQty != null && baseQty > 0.0) {
			this.binInfo.setFullRateQty(DoubleUtil.round(
					(binInfo.getCurrentQty() + binInfo.getQueuedQty())
							/ baseQty * 100, 2));
		}

		if (baseVolumn != null && baseVolumn > 0.0) {
			this.binInfo.setFullRateVolumn(DoubleUtil.round(
					(binInfo.getCurrentVolumn() + binInfo.getQueuedVolumn())
							/ baseVolumn * 100, 2));
		}

		if (baseWeight != null && baseWeight > 0.0) {
			this.binInfo.setFullRateWeight(DoubleUtil.round(
					(binInfo.getCurrentWeight() + binInfo.getQueuedWeight())
							/ baseWeight * 100, 2));
		}

		if (baseMetric != null && baseMetric > 0.0) {
			this.binInfo.setFullRateMetric(DoubleUtil.round(
					(binInfo.getCurrentMetric() + binInfo.getQueuedMetric())
							/ baseMetric * 100, 2));
		}

		if (palletBaseQty != null && palletBaseQty > 0.0) {
			this.binInfo.setFullRatePallet(DoubleUtil.round(
					(binInfo.getCurrentPallet() + binInfo.getQueuedPallet())
							/ palletBaseQty * 100, 2));
		}
	}

	/**
	 * 
	 * <p>
	 * 增加非托盘库存的待上架的数量(体积，重量，标准度量)
	 * </p>
	 * 
	 * @param qty
	 * @param volumn
	 * @param weight
	 * @param metric
	 */
	public void addQueuedInvInfoNoPallet(Sku sku, Double qty, Double volumn,
			Double weight, Double metric) {
		// 逻辑库位不用更新库容
		if (bin.getProperties().getIsLogicBin())
			return;

		this.binInfo.setQueuedQty(DoubleUtil.add(binInfo.getQueuedQty(), qty));
		this.binInfo.setQueuedVolumn(DoubleUtil.add(binInfo.getQueuedVolumn(),
				volumn));
		this.binInfo.setQueuedWeight(DoubleUtil.add(binInfo.getQueuedWeight(),
				weight));
		this.binInfo.setQueuedMetric(DoubleUtil.add(binInfo.getQueuedMetric(),
				metric));

		releshRate();
	}

	/**
	 * 
	 * <p>
	 * 移除非托盘库存的待上架的数量(体积，重量，标准度量)
	 * </p>
	 * 
	 * @param qty
	 * @param volumn
	 * @param weight
	 * @param metric
	 */
	public void removeQueuedInvInfoNoPallet(Sku sku, Double qty, Double volumn,
			Double weight, Double metric) {
		// 逻辑库位不用更新库容
		if (bin.getProperties().getIsLogicBin())
			return;

		this.binInfo.setQueuedQty(DoubleUtil.sub(binInfo.getQueuedQty(), qty));
		this.binInfo.setQueuedVolumn(DoubleUtil.sub(binInfo.getQueuedVolumn(),
				volumn));
		this.binInfo.setQueuedWeight(DoubleUtil.sub(binInfo.getQueuedWeight(),
				weight));
		this.binInfo.setQueuedMetric(DoubleUtil.sub(binInfo.getQueuedMetric(),
				metric));
		
		if (this.binInfo.getCurrentQty() == null
				|| this.binInfo.getCurrentQty() <= 0D) {
			if (this.binInfo.getQueuedQty() == null
					|| this.binInfo.getQueuedQty() <= 0D) {
				clearBinInfo();
			}
		}

		releshRate();
	}

	/**
	 * 
	 * <p>
	 * 增加非托盘库存的库容的数量(体积，重量，标准度量)
	 * </p>
	 * 
	 * @param qty
	 * @param volumn
	 * @param weight
	 * @param metric
	 */
	public void addInvInfoNoPallet(Sku sku, Double qty, Double volumn,
			Double weight, Double metric) {
		// 逻辑库位不用更新库容
		if (bin.getProperties().getIsLogicBin())
			return;

		this.binInfo
				.setCurrentQty(DoubleUtil.add(binInfo.getCurrentQty(), qty));
		this.binInfo.setCurrentVolumn(DoubleUtil.add(
				binInfo.getCurrentVolumn(), volumn));
		this.binInfo.setCurrentWeight(DoubleUtil.add(
				binInfo.getCurrentWeight(), weight));
		this.binInfo.setCurrentMetric(DoubleUtil.add(
				binInfo.getCurrentMetric(), metric));
		
		releshRate();
	}

	/**
	 * 
	 * <p>
	 * 移除非托盘库存的库容的数量(体积，重量，标准度量)
	 * </p>
	 * 
	 * @param qty
	 * @param volumn
	 * @param weight
	 * @param metric
	 */
	public void removeInvInfoNoPallet(Sku sku, Double qty, Double volumn,
			Double weight, Double metric) {
		// 逻辑库位不用更新库容
		if (bin.getProperties().getIsLogicBin())
			return;

		this.binInfo
				.setCurrentQty(DoubleUtil.sub(binInfo.getCurrentQty(), qty));
		this.binInfo.setCurrentVolumn(DoubleUtil.sub(
				binInfo.getCurrentVolumn(), volumn));
		this.binInfo.setCurrentWeight(DoubleUtil.sub(
				binInfo.getCurrentWeight(), weight));
		this.binInfo.setCurrentMetric(DoubleUtil.sub(
				binInfo.getCurrentMetric(), metric));

		if (this.binInfo.getCurrentQty() == null
				|| this.binInfo.getCurrentQty() <= 0D) {
			if (this.binInfo.getQueuedQty() == null
					|| this.binInfo.getQueuedQty() <= 0D) {
				clearBinInfo();
			}
		}

		releshRate();
	}

	/**
	 * 
	 * <p>
	 * 增加非托盘库存的库容的数量
	 * </p>
	 * 
	 * @param inv
	 * @param addQty
	 */
	public void addInvInfoNoPallet(InventoryInfo invInfo, double addQty) {
		// 逻辑库位不用更新库容
		if (bin.getProperties().getIsLogicBin())
			return;

		if (addQty > 0) {
			double qty = addQty;
			double volumn;
			double weight = DoubleUtil.mul(invInfo.getQuant().getSku().getGrossWeight(), qty);

			// 如果包装上有体积，用包装体积×包装数
			if (invInfo.getPackageDetail().getVolume() != null
					&& invInfo.getPackageDetail().getVolume().doubleValue() > 0.0) {
				double packQty = PrecisionUtils.getPackQty(qty,
						invInfo.getPackageDetail());
				volumn = DoubleUtil.mul(invInfo.getPackageDetail().getVolume(),
						packQty);
			}
			// 如果包装上没有设置体积，用SKU的体积×基本包装数
			else {
				volumn = DoubleUtil.mul(
						invInfo.getQuant().getSku().getVolume(), qty);
			}

			addInvInfoNoPallet(invInfo.getQuant().getSku(), qty, volumn, weight, 0D);
		}
	}

	/**
	 * 
	 * <p>
	 * 移除非托盘库存的库容的数量
	 * </p>
	 * 
	 * @param inv
	 * @param removeQty
	 */
	public void removeInvInfoNoPallet(InventoryInfo invInfo, double removeQty) {
		// 逻辑库位不用更新库容
		if (bin.getProperties().getIsLogicBin())
			return;

		if (removeQty > 0) {
			double qty = removeQty;
			double volumn;
			double weight = DoubleUtil.mul(invInfo.getQuant().getSku().getGrossWeight(), qty);

			// 如果包装上有体积，用包装体积×包装数
			if (invInfo.getPackageDetail().getVolume() != null
					&& invInfo.getPackageDetail().getVolume().doubleValue() > 0.0) {
				double packQty = PrecisionUtils.getPackQty(qty,
						invInfo.getPackageDetail());
				volumn = DoubleUtil.mul(invInfo.getPackageDetail().getVolume(),
						packQty);
			}
			// 如果包装上没有设置体积，用SKU的体积×基本包装数
			else {
				volumn = DoubleUtil.mul(
						invInfo.getQuant().getSku().getVolume(), qty);
			}

			removeInvInfoNoPallet(invInfo.getQuant().getSku(), qty, volumn, weight, 0D);
		}
	}

	/**
	 * 
	 * <p>
	 * 增加非托盘库存的待上架的数量
	 * </p>
	 * 
	 * @param wt
	 */
	public void addQueuedInvInfoNoPallet(InventoryInfo invInfo, double addQty) {
		// 逻辑库位不用更新库容
		if (bin.getProperties().getIsLogicBin())
			return;

		if (addQty > 0) {
			double qty = addQty;
			double volumn;
			double weight = DoubleUtil.mul(invInfo.getQuant().getSku().getGrossWeight(), qty);

			// 如果包装上有体积，用包装体积×包装数
			if (invInfo.getPackageDetail().getVolume() != null
					&& invInfo.getPackageDetail().getVolume().doubleValue() > 0.0) {
				double packQty = PrecisionUtils.getPackQty(qty,
						invInfo.getPackageDetail());
				volumn = DoubleUtil.mul(invInfo.getPackageDetail().getVolume(),
						packQty);
			}
			// 如果包装上没有设置体积，用SKU的体积×基本包装数
			else {
				volumn = DoubleUtil.mul(invInfo.getQuant().getSku().getVolume(), qty);
			}

			addQueuedInvInfoNoPallet(invInfo.getQuant().getSku(), qty, volumn, weight, 0D);
		}
	}

	/**
	 * 
	 * <p>
	 * 移除非托盘库存的待上架的数量
	 * </p>
	 * 
	 * @param wt
	 */
	public void removeQueuedInvInfoNoPallet(InventoryInfo invInfo,
			double removeQty) {
		// 逻辑库位不用更新库容
		if (bin.getProperties().getIsLogicBin())
			return;

		if (removeQty > 0) {
			double qty = removeQty;
			double volumn;
			double weight = DoubleUtil.mul(invInfo.getQuant().getSku().getGrossWeight(), qty);

			// 如果包装上有体积，用包装体积×包装数
			if (invInfo.getPackageDetail().getVolume() != null
					&& invInfo.getPackageDetail().getVolume().doubleValue() > 0.0) {
				double packQty = PrecisionUtils.getPackQty(qty,
						invInfo.getPackageDetail());
				volumn = DoubleUtil.mul(invInfo.getPackageDetail().getVolume(),
						packQty);
			}
			// 如果包装上没有设置体积，用SKU的体积×基本包装数
			else {
				volumn = DoubleUtil.mul(
						invInfo.getQuant().getSku().getVolume(), qty);
			}

			removeQueuedInvInfoNoPallet(invInfo.getQuant().getSku(), qty, volumn, weight, 0D);
		}
	}

	/**
	 * 
	 * <p>
	 * 增加等待上架的托盘数量
	 * </p>
	 * 
	 * @param palletQty
	 */
	public void addQueuedPallet(Double palletQty) {
		if (palletQty > 0) {
			binInfo.addQueuedPallet(palletQty);
			releshRate();
		}
	}

	/**
	 * 
	 * <p>
	 * 移除等待上架的托盘数量
	 * </p>
	 * 
	 * @param palletQty
	 */
	public void removeQueuedPallet(Double palletQty) {
		if (palletQty > 0) {
			binInfo.removeQueuedPallet(palletQty);
			releshRate();
		}
	}

	/**
	 * 
	 * <p>
	 * 增加库位上的托盘数量
	 * </p>
	 * 
	 * @param palletQty
	 */
	public void addCurrentPallet(Double palletQty) {
		if (palletQty > 0) {
			binInfo.addCurrentPallet(palletQty);
			releshRate();
		}
	}

	/**
	 * 
	 * <p>
	 * 移除库位上的托盘数量
	 * </p>
	 * 
	 * @param palletQty
	 */
	public void removeCurrentPallet(Double palletQty) {
		if (palletQty > 0) {
			binInfo.removeCurrentPallet(palletQty);
			releshRate();
		}
	}

	/**
	 * 
	 * <p>
	 * 清除存货信息
	 * </p>
	 * 
	 */
	public void clearBinInfo() {
		binInfo.setLastLotInfo(null);
		binInfo.setLastSku(null);
		binInfo.setLastSupId(null);
		binInfo.setLastOwnerId(null);
	}

	/**
	 * 
	 * <p>
	 * 清除拣货信息
	 * </p>
	 * 
	 */
	public void clearPickInfo() {
		binInfo.setLastPickLotInfo(null);
		binInfo.setLastPickSku(null);
	}

	/**
	 * 
	 * <p>
	 * 更新存货信息
	 * </p>
	 * 
	 * @param inv
	 */
//	public void refleshLastStoreInfo(Quant quant) {
//		binInfo.setLastLotInfo(quant.getId());
//		binInfo.setLastSku(quant.getSku().getId());
//	}
	
	public void refleshLastStoreInfo(InventoryInfo invInfo) {
		binInfo.setLastLotInfo(invInfo.getQuant().getId());
		binInfo.setLastSku(invInfo.getQuant().getSku().getId());
		binInfo.setLastOwnerId(invInfo.getOwner() == null ? null : invInfo.getOwner().getId());
	}
	
	/**
	 * 
	 * <p>
	 * 更新拣货信息
	 * </p>
	 * 
	 * @param inv
	 */
	public void refleshLastPickInfo(Quant quant) {
		binInfo.setLastPickLotInfo(quant.getId());
		binInfo.setLastPickSku(quant.getSku().getId());
	}

	/**
	 * 
	 * <p>
	 * 取得该库位还能上架的数量(按体积，重量，数量，标准度量验证以后)
	 * </p>
	 * 
	 * @param detail
	 * @param bin
	 * @param binInfo
	 * @param isAuto
	 * @return
	 */
	public double getCanPutAway(Sku sku, PackageDetail detail, Boolean isAuto) {

		// 可容纳重量
		double allowWQty = 999999999;
		// 可容纳体积
		double allowVQty = 999999999;
		// 可容纳数量
		double allowQQty = 999999999;
		// 可容纳标准度量
		double allowMQty = 999999999;
		// 按托盘折算后的可容纳数量
//		double allowPQQty = 999999999;
		// 最小的可容纳量
		double allowQty = 999999999;

		// 库满度
		double maxRate = bin.getProperties().getFullScale() == null ? 100 : bin
				.getProperties().getFullScale();
		if (maxRate <= 0) {
			maxRate = 100;
		}

//		// 　自动分配，库满度上限>100，按照100算
//		if (isAuto) {
//			// if (maxRate > 100) {
//			// maxRate = 100;
//			// }
//		}
//		// 非自动分配，库满度上限<100,按照100算
//		else {
//			if (maxRate < 100) {
//				maxRate = 100;
//			}
//		}

		// 统计重量、体积、数量能在库位的存放数字，取最小的作为实际结果

		// 可放体积（包装体积）
		if (detail.getVolume() != null && detail.getVolume() > 0.0
				&& bin.getProperties().getVolume() != null
				&& bin.getProperties().getVolume() > 0) {
			allowVQty = ((maxRate - binInfo.getFullRateVolumn()) / 100)
					* bin.getProperties().getVolume() / detail.getVolume();
		} else if (sku.getVolume() != null && sku.getVolume() > 0.0
				&& bin.getProperties().getVolume() != null
				&& bin.getProperties().getVolume() > 0) {
			allowVQty = ((maxRate - binInfo.getFullRateVolumn()) / 100)
					* bin.getProperties().getVolume()
					/ (DoubleUtil.mul(sku.getVolume(), detail.getCoefficient()));
		}

//		// 按照托盘来计算
//		if (bin.getProperties().getPalletCount() != null
//				&& bin.getProperties().getPalletCount() > 0.0) {
//			if (sku.getProperties().getPackageInfo().getP3000()
//					.getCoefficient() != null
//					&& sku.getProperties().getPackageInfo().getP3000()
//							.getCoefficient() > 0.0) {
//				// 该商品的托盘包装系数×库位可容纳的托盘数
//				double fullPalletQty = sku.getProperties().getPackageInfo()
//						.getP3000().getCoefficient()
//						* bin.getProperties().getPalletCount();
//				allowPQQty = (maxRate - (binInfo.getFullRatePallet())) / 100
//						* fullPalletQty / detail.getCoefficient();
//			}
//		}

		// 可放数量(包装数量)
		if (bin.getProperties().getQuantity() != null
				&& bin.getProperties().getQuantity() > 0.0) {
			allowQQty = (maxRate - (binInfo.getFullRateQty())) / 100
					* bin.getProperties().getQuantity()
					/ detail.getCoefficient();
		}

		// 可放重量(包装重量)
		if (sku.getGrossWeight() != null && sku.getGrossWeight() > 0.0
				&& bin.getProperties().getWeight() != null
				&& bin.getProperties().getWeight() > 0.0) {
			allowWQty = ((maxRate - binInfo.getFullRateWeight()) / 100)
					* bin.getProperties().getWeight()
					/ (DoubleUtil.mul(sku.getGrossWeight(),
							detail.getCoefficient()));
		}

		// 比较三项结果
//		if (allowQty > allowPQQty) {
//			allowQty = allowPQQty;
//		}
		if (allowQty > allowQQty) {
			allowQty = allowQQty;
		}
		if (allowQty > allowWQty) {
			allowQty = allowWQty;
		}
		if (allowQty > allowVQty) {
			allowQty = allowVQty;
		}
		if (allowQty > allowMQty) {
			allowQty = allowMQty;
		}

		// 根据包装小数位数 做小数处理
		// 并转成基本包装数量
		allowQty = PrecisionUtils.getBaseQty(allowQty, detail);

		return allowQty;
	}
}
