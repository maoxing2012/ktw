package com.core.scpwms.server.service.common;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.domain.InventoryInfo;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.putaway.PutawayDocDetail;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 分配帮助类
 * 1、上架单库位分配
 * 2、上架单库位分配取消
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@Transactional(readOnly = false)
public interface AllocateHelper extends BaseManager {
	
	/**
	 * 
	 * <p>上架库位分配(自动)</p>
	 *
	 * @param wtId
	 */
	@Transactional
	public List<Long> allocateMoveWt(Long whId, String containerSeq);

    /**
     * 上架库位分配(自动)
     * @param ids
     */
    @Transactional
    public void allocatePutawayDoc(List<Long> ids);
    
    /**
     * 
     * <p>上架库位分配(手工)</p>
     *
     * @param detail
     * @param bin
     * @param packDetail
     * @param qty
     */
    @Transactional
    public String allocatePutawayDoc( PutawayDocDetail detail, Bin bin, PackageDetail packDetail, Double qty );

    /**
     * 按上架单，上架库位取消分配
     * @param ids
     */
    @Transactional
    public void unAllocatePutawayDoc(List<Long> ids);

    /**
     * 按上架单明细，上架库位取消分配
     * @param ids
     */
    @Transactional
    public void unAllocatePutawayDocDetail(List<Long> ids);
    
    /**
     * 
     * <p>取消上架任务(未加入WO)</p>
     *
     * @param wtId
     */
    @Transactional
    public void unAllocateByWtPutaway(Long wtId);
    
    /**
     * 
     * <p>强制关闭上架任务(未加入WO)</p>
     *
     * @param wtId
     */
    @Transactional
    public void closePutaway(Long wtId);
    
    /**
     * 
     * <p>判断库存是不是在正确的库位上</p>
     *
     * @param invId
     * @return
     */
    @Transactional
    public boolean checkBin4Inventory( Long invId );
    
    /**
     * 
     * <p>建作业任务WT</p>
     *
     * @param invs
     * @param bin
     * @param containerSeq
     * @param processType
     * @return
     */
    @Transactional
    public List<Long> allocate4Container( List<Inventory> invs, Bin bin, String containerSeq, String processType );
}
