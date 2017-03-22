package com.core.scpwms.server.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.common.ReasonCode;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.inventory.Quant;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 库存辅助接口
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 * 
 */
@Transactional(readOnly = false)
public interface InventoryHelper extends BaseManager {

	/**
	 * 
	 * <p>
	 * 在指定库位增加库存
	 * </p>
	 * 
	 * @param invInfo
	 * @param addQty
	 * @param withBinValiCheck
	 *            是否需要库位验证
	 * @return
	 */
	@Transactional
	public Inventory addInvToBin(InventoryInfo invInfo, double addQty, boolean withBinValiCheck);
	
	/**
	 * 
	 * <p>创建一条0库存</p>
	 *
	 * @param invInfo
	 * @return
	 */
	@Transactional
	public Inventory createZeroInventory( InventoryInfo invInfo );
	
	/**
	 * 
	 * <p>请添加注释</p>
	 *
	 * @param binId
	 * @param containerSeq
	 * @param palletSeq
	 * @return
	 */
	@Transactional
	public boolean isNewPallet2Bin( Long binId, String containerSeq, String palletSeq );

	/**
	 * 
	 * <p>
	 * 在指定库位增加库存,不做入库锁验证
	 * </p>
	 * 
	 * @param invInfo
	 * @param addQty
	 * @param withBinValiCheck
	 * @return
	 */
	@Transactional
	public Inventory addInvToBin4Count(InventoryInfo invInfo, double addQty, boolean withBinValiCheck);
	
	/**
	 * <p>删除0库存，前提是库存数，分配数都是0，而且没有Task指向这个Inv的ID</p>
	 * <p>返回true说明连托盘信息也删除了，返回false说明没有删除托盘信息或者没有托盘信息</p>
	 *
	 * @param inv
	 */
	@Transactional
	public boolean deleteInv( Inventory inv );

	/**
	 * 
	 * <p>
	 * 扣减库存，并扣减分配数
	 * </p>
	 * 
	 * @param inv
	 * @param executeQty
	 */
	@Transactional
	public void removeInv(Inventory inv, Double executeQty);
	
	/**
	 * 
	 * <p>只扣减分配数，不扣库存数</p>
	 *
	 * @param inv
	 * @param executeQty
	 */
	@Transactional
	public void removeAllocateQty( Inventory inv, Double executeQty );

	/**
	 * 
	 * <p>
	 * 扣减库存，但不扣减分配数
	 * </p>
	 * 
	 * @param inv
	 * @param eaQty
	 */
	@Transactional
	public void removeInvWithoutAllocateQty(Inventory inv, Double eaQty);

	/**
	 * 
	 * <p>
	 * 扣减库存，但不扣减分配数,不做出库锁严重
	 * </p>
	 * 
	 * @param inv
	 * @param eaQty
	 */
	@Transactional
	public void removeInvWithoutAllocateQty4Count(Inventory inv, Double eaQty);

	/**
	 * 
	 * <p>
	 * 用WT找库存
	 * </p>
	 * 
	 * @param wt
	 * @return
	 */
	@Transactional
	public Inventory getInv(WarehouseTask wt);

	/**
	 * 
	 * <p>
	 * 用InventoryInfo找库存
	 * </p>
	 * 
	 * @param invInfo
	 * @return
	 */
	@Transactional
	public Inventory getInv(InventoryInfo invInfo);

	/**
	 * 
	 * <p>
	 * 拣选分配占分配数
	 * </p>
	 * 
	 * @param inv
	 * @param allocateQty
	 */
	@Transactional
	public void allocateInv(Inventory inv, double allocateQty);

	/**
	 * 
	 * <p>
	 * 上架分配占分配数
	 * </p>
	 * 
	 * @param inv
	 * @param descBin
	 * @param allocateQty
	 */
	@Transactional
	public void allocateInvByPutaway(Inventory inv, Bin descBin, double allocateQty);

	/**
	 * 
	 * <p>
	 * 拣选分配释放分配数
	 * </p>
	 * 
	 * @param inv
	 * @param unAllocateQty
	 */
	@Transactional
	public void unAllocateInv(Inventory inv, double unAllocateQty);

	/**
	 * 
	 * <p>
	 * 上架分配释放分配数
	 * </p>
	 * 
	 * @param inv
	 * @param descBin
	 * @param unAllocateQty
	 */
	@Transactional
	public void unAllocateInvByPutaway(Inventory inv, Bin descBin, double unAllocateQty);

	/**
	 * 
	 * <p>
	 * 请添加注释
	 * </p>
	 * 
	 * @param ownerId
	 * @param skuId
	 * @param binId
	 * @param lotDate
	 * @param trackSeq
	 * @param invStatus
	 * @param storageType
	 * @return
	 */
	@Transactional
	public List<Inventory> getInvs(Long ownerId, Long skuId, Long binId,
			LotInputData lotDate, String trackSeq, String invStatus,
			String storageType);

	@Transactional
	public void checkBinMix( Long binId, Owner owner, Quant quant );
}
