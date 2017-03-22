package com.core.scpwms.server.model.warehouse;

import java.util.HashSet;
import java.util.Set;

import com.core.business.model.TrackingEntity;
import com.core.db.server.model.Entity;

/**
 * 库位组
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class BinBinGroup extends Entity {

    private Bin bin;

    private BinGroup binGroup;

    public Bin getBin() {
        return this.bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public BinGroup getBinGroup() {
        return this.binGroup;
    }

    public void setBinGroup(BinGroup binGroup) {
        this.binGroup = binGroup;
    }

}
