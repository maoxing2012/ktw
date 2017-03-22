package com.core.scpwms.server.model.change;

import java.util.Date;
import java.util.Set;

import com.core.business.utils.DoubleUtil;
import com.core.db.server.model.Entity;
import com.core.scpwms.server.domain.AllocateDoc;
import com.core.scpwms.server.domain.AllocateDocDetail;
import com.core.scpwms.server.enumerate.EnuLotChangeDocDetailStatus;
import com.core.scpwms.server.enumerate.EnuLotChangeType;
import com.core.scpwms.server.enumerate.EnuOwnerChangeDocDetailStatus;
import com.core.scpwms.server.enumerate.EnuWtProcessType;
import com.core.scpwms.server.model.common.OrderType;
import com.core.scpwms.server.model.common.PackageDetail;
import com.core.scpwms.server.model.common.Sku;
import com.core.scpwms.server.model.inventory.Inventory;
import com.core.scpwms.server.model.lot.LotInputData;
import com.core.scpwms.server.model.task.WarehouseTask;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.util.PrecisionUtils;

/**
  * 批次调整单明细
 */
@SuppressWarnings("all")
public class LotChangeDocDetail extends Entity implements AllocateDocDetail {

    /** 批次调整单 */
    private LotChangeDoc lotChangeDoc;
    
    /** 货主 */
    private Owner owner;

    /** 货品 */
    private Sku sku;

    /** 指定的批次属性 */
    private LotInputData lotData = new LotInputData();
    
    /** 
     * 状态 
     * @see EnuLotChangeDocDetailStatus
     * */
    private Long status;
    
    /**
     * @see EnuLotChangeType
     */
    private String lotChangeType;
    
    /**
     * 行号
     */
    private Double lineNo;

    /** 数量(EA),存在正负数的情况 */
    private Double planQty = 0d;

    /** 包装数量,存在正负数的情况 */
    private Double planPackQty = 0d;

    /** 分配数量(EA),只有正数 */
    private Double allocateQty = 0d;

    /** 执行数量(EA),存在正负数的情况 */
    private Double executeQty = 0d;

    /** 包装单位 */
    private PackageDetail packageDetail;

    /** 描述 */
    private String description;

    /** 扩展字段(工程号) */
    private String extString1;
    private String extString2;
    private String extString3;
    
    private String invStatus;
    
    /** 拣货任务 */
    private Set<WarehouseTask> tasks;
    
    public LotChangeDoc getLotChangeDoc() {
		return this.lotChangeDoc;
	}

	public void setLotChangeDoc(LotChangeDoc lotChangeDoc) {
		this.lotChangeDoc = lotChangeDoc;
	}

	public Sku getSku() {
        return this.sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public LotInputData getLotData() {
        return this.lotData;
    }

    public void setLotData(LotInputData lotData) {
        this.lotData = lotData;
    }

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Double getPlanQty() {
        return this.planQty;
    }

    public void setPlanQty(Double planQty) {
        this.planQty = planQty;
    }

    public Double getPlanPackQty() {
        return this.planPackQty;
    }

    public void setPlanPackQty(Double planPackQty) {
        this.planPackQty = planPackQty;
    }

    public Double getAllocateQty() {
        return this.allocateQty;
    }

    public void setAllocateQty(Double allocateQty) {
        this.allocateQty = allocateQty;
    }

    public Double getExecuteQty() {
        return this.executeQty;
    }

    public void setExecuteQty(Double executeQty) {
        this.executeQty = executeQty;
    }

    public PackageDetail getPackageDetail() {
        return this.packageDetail;
    }

    public void setPackageDetail(PackageDetail packageDetail) {
        this.packageDetail = packageDetail;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<WarehouseTask> getTasks() {
        return this.tasks;
    }

    public void setTasks(Set<WarehouseTask> tasks) {
        this.tasks = tasks;
    }

    public String getExtString1() {
        return this.extString1;
    }

    public void setExtString1(String extString1) {
        this.extString1 = extString1;
    }

    public String getExtString2() {
        return this.extString2;
    }

    public void setExtString2(String extString2) {
        this.extString2 = extString2;
    }

    public String getExtString3() {
        return this.extString3;
    }

    public void setExtString3(String extString3) {
        this.extString3 = extString3;
    }
    
    public Double getLineNo() {
		return this.lineNo;
	}

	public void setLineNo(Double lineNo) {
		this.lineNo = lineNo;
	}
	
	public String getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public String getLotChangeType() {
		return this.lotChangeType;
	}

	public void setLotChangeType(String lotChangeType) {
		this.lotChangeType = lotChangeType;
	}
	
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
    @Override
    public Owner getOwner() {
        return owner;
    }

	@Override
    public OrderType OrderType() {
        return getLotChangeDoc().getOrderType();
    }

    @Override
    public void allocate(Inventory inventory, double quantity) {
        addAllocateQty(quantity);
    }

    @Override
    public AllocateDoc getAllocateDoc() {
        return getLotChangeDoc();
    }

    @Override
    public Bin getBin() {
        return null;
    }

    @Override
    public String getTaskProcessType() {
        return EnuWtProcessType.OTHER;
    }

    @Override
    public Double getUnAllocateQty() {
        return DoubleUtil.sub(planQty, allocateQty);
    }

    @Override
    public Warehouse getWh() {
        return getLotChangeDoc().getWh();
    }

    public double getUnExecuteQty() {
        return DoubleUtil.sub(planQty, executeQty);
    }

    public double getAllocateUnExecuteQty() {
        return DoubleUtil.sub(allocateQty, executeQty);
    }

    public void addPlanQty(double qty) {
    	double baseQty = PrecisionUtils.formatByBasePackage(qty, packageDetail);
        planQty = DoubleUtil.add(planQty, baseQty);
        planPackQty = PrecisionUtils.getPackQty(baseQty, packageDetail);
    	
    	if (EnuLotChangeType.PLUS.equals(lotChangeType)) {
            getLotChangeDoc().addPlanQty(baseQty);
        }
    }

    public void addAllocateQty(double qty) {
    	// 调整出的需要分配库存
        if (EnuLotChangeType.MINUS.equals(lotChangeType)) {
            double baseQty = PrecisionUtils.formatByBasePackage(qty, packageDetail);
            allocateQty = DoubleUtil.add(allocateQty, baseQty);
            getLotChangeDoc().addAllocateQty(baseQty);
        }
    }

    public void addExecuteQty(double qty) {
    	double baseQty = PrecisionUtils.formatByBasePackage(qty, packageDetail);
        executeQty = DoubleUtil.add(executeQty, baseQty);
    	
    	if (EnuLotChangeType.PLUS.equals(lotChangeType)) {
            getLotChangeDoc().addExecuteQty(baseQty);
        }
    }

    public void removePlanQty(double qty) {
    	double baseQty = PrecisionUtils.formatByBasePackage(qty, packageDetail);
        planQty = DoubleUtil.sub(planQty, baseQty);
        planPackQty = PrecisionUtils.getPackQty(baseQty, packageDetail);
    	
    	if (EnuLotChangeType.PLUS.equals(lotChangeType)) {
            getLotChangeDoc().removePlanQty(baseQty);
        }
    }

    public void removeAllocateQty(double qty) {
    	// 调整出的需要分配库存
        if (EnuLotChangeType.MINUS.equals(lotChangeType)) {
            double baseQty = PrecisionUtils.formatByBasePackage(qty, packageDetail);
            allocateQty = DoubleUtil.sub(allocateQty, baseQty);
            getLotChangeDoc().removeAllocateQty(baseQty);
        }
    }

    public void removeExecuteQty(double qty) {
    	double baseQty = PrecisionUtils.formatByBasePackage(qty, packageDetail);
        executeQty = DoubleUtil.sub(executeQty, baseQty);
    	
    	if (EnuLotChangeType.PLUS.equals(lotChangeType)) {
            getLotChangeDoc().removeExecuteQty(baseQty);
        }
    }

    @Override
	public void unallocate(double quantity){
		removeAllocateQty(quantity);
	}

	@Override
	public Date getExpDateMin() {
		return null;
	}

	@Override
	public Date getExpDateMax() {
		return null;
	}

	@Override
	public Boolean getCanMixExp() {
		return null;
	}

}
