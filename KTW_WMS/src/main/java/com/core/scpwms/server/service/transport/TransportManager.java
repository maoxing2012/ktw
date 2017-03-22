/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : WhAreaManager.java
 */

package com.core.scpwms.server.service.transport;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.transport.Area;
import com.core.scpwms.server.model.transport.City;
import com.core.scpwms.server.model.transport.Course;
import com.core.scpwms.server.model.transport.PostCode;


@Transactional(readOnly = false)
public interface TransportManager extends BaseManager {

    @Transactional
    void storeArea(Area area);

    @Transactional
    void storeCity(City city);
    
    @Transactional
    void storeCourse( Course course );
    
    @Transactional
    void storePostcode( PostCode postcode );
    
    @Transactional
    void addPostcode2Course( Long courseId, List<Long> postIds );
    
    @Transactional
    void deletePostcodeFromCourse( List<Long> coursePostIds );
    
    @Transactional
    void addBizOrg2Course( Long courseId, List<Long> bizorgIds );
    
    @Transactional
    void deleteBizOrgFromCourse( List<Long> courseBizorgIds );
    
    @Transactional
    void resetCourse( List<Long> bizorgIds );
}
