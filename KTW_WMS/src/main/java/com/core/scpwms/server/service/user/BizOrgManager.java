/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : CustomerManager.java
 */

package com.core.scpwms.server.service.user;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.user.BizOrg;

/**
 * @description   收货方/客户信息维护 
 * @author         MBP:毛幸<br/>
 * @createDate    2013-2-5
 * @version        V1.0<br/>
 */
@Transactional(readOnly = false)
public interface BizOrgManager extends BaseManager {

    @Transactional
    void customerPropertiesetting(BizOrg customer, Long id);

    @Transactional
    void disableBizOrg(List<Long> ids);

    @Transactional
    void saveBizOrg(BizOrg bizOrg);
    
    @Transactional
    void saveVendor(BizOrg bizOrg);
    
    @Transactional
    void saveShop(BizOrg bizOrg);

    @Transactional
    void deleteBizOrg(List<Long> ids);

    @Transactional
    void enableBizOrg(List<Long> ids);

}
