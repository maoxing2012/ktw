/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WhAreaManager.java
 */

package com.core.scpwms.server.service.fee;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.fee.FeeType;

@Transactional(readOnly = false)
public interface FeeTypeManager extends BaseManager {

	@Transactional
    void store(FeeType feeType);

    @Transactional
    void delete(List<Long> feeTypeIds);

    @Transactional
    void disable(List<Long> feeTypeIds);

    @Transactional
    void enable(List<Long> feeTypeIds);
}
