package com.core.scpwms.server.service.imports.impl;

import java.util.ArrayList;
import java.util.List;

import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.server.model.transport.Area;
import com.core.scpwms.server.model.transport.City;
import com.core.scpwms.server.model.transport.Course;
import com.core.scpwms.server.model.transport.CoursePostCode;
import com.core.scpwms.server.model.transport.PostCode;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.service.imports.TransportFileImportWithTransManager;
import com.core.scpwms.server.service.transport.TransportManager;

public class TransportFileImportWithTransManagerImpl extends BaseFileImportWithTransManagerImpl implements TransportFileImportWithTransManager {
	private TransportManager transportManager;
	
	public TransportManager getTransportManager() {
		return transportManager;
	}

	public void setTransportManager(TransportManager transportManager) {
		this.transportManager = transportManager;
	}

	@Override
	public Boolean createCity(List<String> excelLine, int index, List<String[]> result) {
		excelLine = formatExcelLine(excelLine, 5);
		
		if( StringUtils.isEmpty(excelLine.get(1).trim()) ){
			result.add(new String[]{String.valueOf(index), "地域番号	：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(3).trim()) ){
			result.add(new String[]{String.valueOf(index), "市・区番号	：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(4).trim()) ){
			result.add(new String[]{String.valueOf(index), "市・区名	：必須項目。"});
			return null;
		}
		
		Area area = getArea(excelLine.get(1).trim());
		if( area == null ){
			result.add(new String[]{ String.valueOf(index), "地域番号：" + excelLine.get(1).trim() + "、マスタデータ不備。" });
			return null;
		}
		
		Boolean isNew = null;
		City city = getCity(excelLine.get(3).trim());
		if (city == null) {
			city = new City();
			isNew = Boolean.TRUE;
		}
		else{
			isNew = Boolean.FALSE;
		}
		city.setArea(area);
		city.setCode(excelLine.get(3).trim());
		city.setName(excelLine.get(4).trim());

		commonDao.store(city);

		return isNew;
	}
	
	private Area getArea( String areaCode ){
		String hql = " from Area a where a.code = :areaCode ";
		List<Area> areas = commonDao.findByQuery(hql, "areaCode", areaCode);
		
		if( areas == null || areas.size() == 0 )
			return null;
		return areas.get(0);
	}
	
	private City getCity( String cityCode ){
		String hql = " from City c where c.code = :cityCode ";
		List<City> citys = commonDao.findByQuery(hql, "cityCode", cityCode);
		
		if( citys == null || citys.size() == 0 )
			return null;
		return citys.get(0);
	}
	
	private City getCityByName( String cityName ){
		String hql = " from City c where c.name = :cityName ";
		List<City> citys = commonDao.findByQuery(hql, "cityName", cityName);
		
		if( citys == null || citys.size() == 0 )
			return null;
		return citys.get(0);
	}
	
	private Course getCourse( String ownerCode, String courseCode ){
		String hql = " from Course c where c.owner.code = :ownerCode and c.code = :courseCode ";
		List<Course> courses = commonDao.findByQuery(hql, 
				new String[]{ "ownerCode","courseCode" }, new Object[]{ownerCode,courseCode});
		
		if( courses == null || courses.size() == 0 )
			return null;
		return courses.get(0);
	}
	
	private PostCode getPostcode( String postcode ){
		String hql = " from PostCode p where p.postcode = :postcode ";
		List<PostCode> posts = commonDao.findByQuery(hql, "postcode", postcode);
		
		if( posts == null || posts.size() == 0 )
			return null;
		return posts.get(0);
	}
	
	private CoursePostCode getCoursePostCode( Long courseId, Long postId ){
		String hql = "from CoursePostCode cpc where 1=1 "
				+ " and cpc.course.id = :courseId "
				+ " and cpc.postCode.id = :postId";
		
		List<CoursePostCode> cpcId = commonDao.findByQuery(hql, new String[]{ "courseId","postId" }, new Object[]{courseId,postId});
		if( cpcId == null || cpcId.size() == 0 )
			return null;
		return cpcId.get(0);
	}
	
	@Override
	public Boolean createPost(List<String> excelLine, int index, List<String[]> result) {
		excelLine = formatExcelLine(excelLine, 3);
		
		if( StringUtils.isEmpty(excelLine.get(0).trim()) ){
			result.add(new String[]{String.valueOf(index), "郵便番号：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(1).trim()) ){
			result.add(new String[]{String.valueOf(index), "市・区名：必須項目。"});
			return null;
		}
		
		City city = getCityByName(excelLine.get(1).trim());
		if( city == null ){
			result.add(new String[]{ String.valueOf(index), "市・区名：" + excelLine.get(1).trim() + "、マスタデータ不備。" });
			return null;
		}
		
		Boolean isNew = null;
		PostCode postcode = getPostcode(excelLine.get(0).trim());
		if (postcode == null) {
			postcode = new PostCode();
			isNew = Boolean.TRUE;
		}
		else{
			isNew = Boolean.FALSE;
		}
		postcode.setCity(city);
		postcode.setPostcode(excelLine.get(0).trim());
		postcode.setAddr(excelLine.get(2).trim());

		// 保存库位信息
		commonDao.store(postcode);

		return isNew;
	}

	@Override
	public Boolean createCoursePost(List<String> excelLine, int index, List<String[]> result) {
		excelLine = formatExcelLine(excelLine, 3);
		
		if( StringUtils.isEmpty(excelLine.get(0).trim()) ){
			result.add(new String[]{String.valueOf(index), "荷主コード：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(1).trim()) ){
			result.add(new String[]{String.valueOf(index), "コースコード：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(2).trim()) ){
			result.add(new String[]{String.valueOf(index), "郵便番号：必須項目。"});
			return null;
		}
		
		Course course = getCourse(excelLine.get(0).trim(), excelLine.get(1).trim());
		if( course == null ){
			result.add(new String[]{ String.valueOf(index), "コースコード：" + excelLine.get(1).trim() + "、マスタデータ不備。" });
			return null;
		}
		
		PostCode postCode = getPostcode(excelLine.get(2).trim());
		if( postCode == null ){
			result.add(new String[]{ String.valueOf(index), "郵便番号：" + excelLine.get(2).trim() + "、マスタデータ不備。" });
			return null;
		}
		
		Boolean isNew = null;
		CoursePostCode cpc = getCoursePostCode(course.getId(), postCode.getId());
		if (cpc == null) {
			List<Long> postIds = new ArrayList<Long>(1);
			postIds.add(postCode.getId());
			transportManager.addPostcode2Course(course.getId(), postIds);
			isNew = Boolean.TRUE;
		}
		
		return isNew;
	}

}
