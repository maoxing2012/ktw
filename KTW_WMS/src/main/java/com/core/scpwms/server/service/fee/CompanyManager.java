/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WhAreaManager.java
 */

package com.core.scpwms.server.service.fee;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.fee.Company;

@Transactional(readOnly = false)
public interface CompanyManager extends BaseManager {

	@Transactional
    void store(Company company);

    @Transactional
    void delete(List<Long> companyIds);

    @Transactional
    void disable(List<Long> companyIds);

    @Transactional
    void enable(List<Long> companyIds);


}
