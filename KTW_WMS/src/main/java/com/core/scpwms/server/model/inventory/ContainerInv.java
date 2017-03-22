package com.core.scpwms.server.model.inventory;

import java.util.Date;
import java.util.Set;

import com.core.db.server.model.Entity;
import com.core.scpwms.server.model.common.Container;
import com.core.scpwms.server.model.warehouse.Bin;

/**
 * 托盘库存（可扩展为周转车、笼车库存）
 * 用来描述使用托盘等大型容器进行管理的库存
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class ContainerInv extends Entity {

    /**
     * 容器编号
     */
    private String containerSeq;

    /**
     * 托盘编号
     */
    private String palletSeq;

    /**
     * 是否托盘库存
     */
    private Boolean bePallet = Boolean.FALSE;

    /**
     * 容器
     */
    private Container container;

    /** 
     * 容器放置库位 
     */
    private Bin bin;

    /** 附注 */
    private String remark;

    /** 
     * 包含库存 
     */
    private Set<Inventory> invs;
    
    /**
     * 上架日期
     */
    private Date putawayDate;
    
    /**
     * 层位
     */
    private Long level;

    public String getContainerSeq() {
        return this.containerSeq;
    }

    public void setContainerSeq(String containerSeq) {
        this.containerSeq = containerSeq;
    }

    public String getPalletSeq() {
        return this.palletSeq;
    }

    public void setPalletSeq(String palletSeq) {
        this.palletSeq = palletSeq;
    }

    public Boolean getBePallet() {
        return this.bePallet;
    }

    public void setBePallet(Boolean bePallet) {
        this.bePallet = bePallet;
    }

    public Container getContainer() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Bin getBin() {
        return this.bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Set<Inventory> getInvs() {
        return this.invs;
    }

    public void setInvs(Set<Inventory> invs) {
        this.invs = invs;
    }

	public Date getPutawayDate() {
		return this.putawayDate;
	}

	public void setPutawayDate(Date putawayDate) {
		this.putawayDate = putawayDate;
	}

	public Long getLevel() {
		return this.level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

}
