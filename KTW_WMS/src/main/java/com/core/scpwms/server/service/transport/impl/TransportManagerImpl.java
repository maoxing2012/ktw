package com.core.scpwms.server.service.transport.impl;

import java.util.List;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.transport.Area;
import com.core.scpwms.server.model.transport.City;
import com.core.scpwms.server.model.transport.Course;
import com.core.scpwms.server.model.transport.CourseBizOrg;
import com.core.scpwms.server.model.transport.CoursePostCode;
import com.core.scpwms.server.model.transport.PostCode;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.service.transport.TransportManager;

public class TransportManagerImpl extends DefaultBaseManager implements TransportManager {

	@Override
	public void storeArea(Area area) {
		commonDao.store(area);
	}

	@Override
	public void storeCity(City city) {
		commonDao.store(city);
	}

	@Override
	public void storeCourse(Course course) {
		if( course.isNew() ){
			course.setWh(WarehouseHolder.getWarehouse());
		}
		commonDao.store(course);
	}

	@Override
	public void storePostcode(PostCode postcode) {
		commonDao.store(postcode);
	}

	@Override
	public void addPostcode2Course(Long courseId, List<Long> postIds) {
		Course course = commonDao.load(Course.class, courseId);
		for( Long postId : postIds ){
			String hqlString = " select cpc.id from CoursePostCode cpc where 1=1 "
					+ " and cpc.course.id = :courseId "
					+ " and cpc.postCode.id = :postId ";
			List<Long> cpcIdsList = commonDao.findByQuery(hqlString, 
					new String[]{"courseId", "postId"}, new Object[]{courseId, postId});
			
			if( cpcIdsList == null || cpcIdsList.size() == 0 ){
				CoursePostCode cpcCode = new CoursePostCode();
				cpcCode.setCourse(course);
				cpcCode.setPostCode(commonDao.load(PostCode.class, postId));
				commonDao.store(cpcCode);
				
				// 关联的BizOrg
				String hql = "select bo.id from BizOrg bo where 1=1 "
						+ " and bo.owner.id = :ownerId "
						+ " and bo.contractInfo.postcode = :postcode "
						+ " and bo.id not in ( select cbz.customer.id from CourseBizOrg cbz where cbz.course.owner.id = :ownerId )";
				List<Long> boIdsList = commonDao.findByQuery(hql, 
						new String[]{"ownerId", "postcode"}, new Object[]{course.getOwner().getId(), cpcCode.getPostCode().getPostcode()});
				if( boIdsList != null && boIdsList.size() > 0 ){
					for( Long boId : boIdsList ){
						CourseBizOrg cbz = new CourseBizOrg();
						cbz.setCourse(course);
						cbz.setCustomer(commonDao.load(BizOrg.class, boId));
						commonDao.store(cbz);
					}
				}
			}
		}
	}

	@Override
	public void deletePostcodeFromCourse(List<Long> coursePostIds) {
		for( Long coursePostId : coursePostIds ){
			commonDao.delete(commonDao.load(CoursePostCode.class, coursePostId));
		}
	}

	@Override
	public void addBizOrg2Course(Long courseId, List<Long> bizorgIds) {
		Course course = commonDao.load(Course.class, courseId);
		for( Long bizorgId : bizorgIds ){
			String hqlString = " select cb.id from CourseBizOrg cb where 1=1 "
					+ " and cb.course.id = :courseId "
					+ " and cb.customer.id = :custId ";
			List<Long> cbIds = commonDao.findByQuery(hqlString, 
					new String[]{"courseId", "custId"}, new Object[]{courseId, bizorgId});
			
			if( cbIds == null || cbIds.size() == 0 ){
				CourseBizOrg cb = new CourseBizOrg();
				cb.setCourse(course);
				cb.setCustomer(commonDao.load(BizOrg.class, bizorgId));
				commonDao.store(cb);
				
				// 要删除其他捆绑
				String hql = "select cb.id from CourseBizOrg cb where 1=1 "
						+ " and cb.course.id <> :courseId "
						+ " and cb.customer.id = :custId ";
				List<Long> otherCbIds = commonDao.findByQuery(hql, 
						new String[]{"courseId", "custId"}, new Object[]{courseId, bizorgId});
				if( otherCbIds != null && otherCbIds.size() > 0 ){
					deleteBizOrgFromCourse(otherCbIds);
				}
			}
		}
	}

	@Override
	public void deleteBizOrgFromCourse(List<Long> courseBizorgIds) {
		for( Long courseBizorgId : courseBizorgIds ){
			commonDao.delete(commonDao.load(CourseBizOrg.class, courseBizorgId));
		}
	}

	@Override
	public void resetCourse(List<Long> bizorgIds) {
		for( Long bizorgId : bizorgIds ){
			BizOrg customerBizOrg = commonDao.load(BizOrg.class, bizorgId);
			
			if( customerBizOrg.getContractInfo().getPostcode() == null 
					|| customerBizOrg.getContractInfo().getPostcode().trim().length() == 0 )
				return;
			
			// 根据邮编找course
			String hql0 = " select cpc.id from CoursePostCode cpc where cpc.course.owner.id = :ownerId and cpc.postCode.postcode = :postcode order by cpc.course.code ";
			List<Long> cpcIdsList = commonDao.findByQuery(hql0, 
					new String[]{"ownerId", "postcode"}, new Object[]{customerBizOrg.getOwner().getId(), customerBizOrg.getContractInfo().getPostcode()});
			if( cpcIdsList == null || cpcIdsList.size() == 0 )
				return;
			CoursePostCode cpc = commonDao.load(CoursePostCode.class, cpcIdsList.get(0));
			
			// 老的绑定
			String hql1 = "select cb.id from CourseBizOrg cb where cb.customer.id = :bizorgId and cb.course.id = :courseId";
			List<Long> oldCbIds = commonDao.findByQuery(hql1, new String[]{ "bizorgId", "courseId"}, new Object[]{ bizorgId, cpc.getCourse().getId()});
			
			if( oldCbIds == null || oldCbIds.size() == 0 ){
				CourseBizOrg cbo = new CourseBizOrg();
				cbo.setCourse(cpc.getCourse());
				cbo.setCustomer(customerBizOrg);
				commonDao.store(cbo);
			}
			
			// 删除其他Course绑定
			String hql2 = "select cb.id from CourseBizOrg cb where cb.customer.id = :bizorgId and cb.course.id <> :courseId";
			List<Long> cbIdsList = commonDao.findByQuery(hql2, new String[]{ "bizorgId", "courseId"}, new Object[]{ bizorgId, cpc.getCourse().getId()});
			if( cbIdsList != null && cbIdsList.size() > 0 ){
				for( Long cbId : cbIdsList ){
					commonDao.delete(commonDao.load(CourseBizOrg.class, cbId));
				}
			}
		}
	}
}
