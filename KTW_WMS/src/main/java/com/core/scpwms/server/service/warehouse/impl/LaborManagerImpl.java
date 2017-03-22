package com.core.scpwms.server.service.warehouse.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.warehouse.Labor;
import com.core.scpwms.server.model.warehouse.LaborGroup;
import com.core.scpwms.server.model.warehouse.LaborGroupLabor;
import com.core.scpwms.server.service.warehouse.LaborManager;

/**
 * 
 * @description 作业人员实现类
 * @author MBP:xiaoyan<br/>
 * @createDate 2013-8-20
 * @version V1.0<br/>
 */
@SuppressWarnings("all")
public class LaborManagerImpl extends DefaultBaseManager implements
		LaborManager {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.LaborManager#deleteLabor(java
	 * .util.List)
	 */
	@Override
	public void deleteLabor(List<Long> ids) {
		if (!ids.isEmpty()) {
			for (Long id : ids) {
				// 检查是否有业务数据
				String hql = " select id from LaborKpi lk where lk.labor.id = :laborId ";
				List<Long> lkIds = commonDao.findByQuery(hql, "laborId", id);
				if (lkIds != null && lkIds.size() > 0) {
					throw new BusinessException(
							ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
				}

				Labor labor = commonDao.load(Labor.class, id);
				// 删除和laborGroup的绑定关系
				if( labor.getGroups() != null && labor.getGroups().size() > 0 ){
					for( LaborGroupLabor lgl : labor.getGroups() ){
						commonDao.delete(lgl);
					}
				}

				// 删除labor
				this.delete(labor);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.LaborManager#storeLabor(com.
	 * core.scpwms.server.model.warehouse.Labor)
	 */
	@Override
	public void storeLabor(Labor labor) {
		labor.setUser(labor.getUser().getId() == null ? null : labor.getUser());
		if (labor.isNew()) {
			String hsql = "from Labor labor where labor.code=:code and labor.wh.id = :whId";
			List<Labor> loborList = this.commonDao.findByQuery(hsql,
					new String[] { "code", "whId" },
					new Object[] { labor.getCode(),
							WarehouseHolder.getWarehouse().getId() });

			if (!loborList.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}
			labor.setWh(WarehouseHolder.getWarehouse());
			labor.setCreateInfo(new CreateInfo(UserHolder.getUser()));

		}

		labor.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		this.commonDao.store(labor);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.LaborManager#disable(java.util
	 * .List)
	 */
	@Override
	public void disable(List<Long> ids) {
		if (!ids.isEmpty()) {
			for (Long id : ids) {
				Labor labor = this.commonDao.load(Labor.class, id);
				labor.setDisabled(true);

				labor.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
				this.commonDao.store(labor);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.LaborManager#enable(java.util
	 * .List)
	 */
	@Override
	public void enable(List<Long> ids) {
		if (!ids.isEmpty()) {
			for (Long id : ids) {
				Labor labor = this.commonDao.load(Labor.class, id);
				labor.setDisabled(false);

				labor.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
				this.commonDao.store(labor);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.LaborManager#addLaborGroup(java
	 * .lang.Long, java.util.List)
	 */
	@Override
	public void addLaborGroup(Long laborId, List<Long> ids) {
		Labor labor = this.commonDao.load(Labor.class, laborId);
		if (!ids.isEmpty()) {
			for (Long groupId : ids) {
				String hsql = "from LaborGroupLabor lgl where lgl.labor.id=:laborId and lgl.group.id=:groupId ";
				List<LaborGroupLabor> exsitDatasList = this.commonDao
						.findByQuery(hsql,
								new String[] { "laborId", "groupId" },
								new Object[] { laborId, groupId });
				LaborGroup group = this.commonDao.load(LaborGroup.class,
						groupId);

				if (exsitDatasList.isEmpty()) {
					LaborGroupLabor lgl = new LaborGroupLabor(labor, group);
					this.commonDao.store(lgl);
				} else {
					throw new BusinessException("labor.roup.exsit",
							new String[] { group.getName() });
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.LaborManager#deleteLaborGroup
	 * (java.util.List)
	 */
	@Override
	public void deleteLaborGroup(List<Long> ids) {
		if (!ids.isEmpty()) {
			for (Long id : ids) {
				LaborGroupLabor lgl = this.commonDao.load(
						LaborGroupLabor.class, id);
				this.delete(lgl);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.LaborManager#addLabor2LaborGroup
	 * (java.lang.Long, java.util.List)
	 */
	@Override
	public void addLabor2LaborGroup(Long lgId, List<Long> ids) {
		LaborGroup laborGroup = commonDao.load(LaborGroup.class, lgId);

		for (Long id : ids) {
			Labor labor = commonDao.load(Labor.class, id);

			LaborGroupLabor lgl = new LaborGroupLabor();
			lgl.setGroup(laborGroup);
			lgl.setLabor(labor);

			commonDao.store(lgl);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.warehouse.LaborManager#addLabor2LaborGroup
	 * (java.lang.Long, java.util.List)
	 */
	@Override
	public void addLaborGroup2Labor(Long laborId, List<Long> ids) {
		Labor labor = commonDao.load(Labor.class, laborId);

		for (Long id : ids) {
			LaborGroup lg = commonDao.load(LaborGroup.class, id);

			LaborGroupLabor lgl = new LaborGroupLabor();
			lgl.setGroup(lg);
			lgl.setLabor(labor);

			commonDao.store(lgl);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.core.scpwms.server.service.warehouse.LaborManager#
	 * deleteLaborFromLaborGroup(java.lang.Long, java.util.List)
	 */
	@Override
	public void deleteLaborFromLaborGroup(Long lgId, List<Long> ids) {
		for (Long id : ids) {
			LaborGroupLabor lgl = (LaborGroupLabor) commonDao
					.findByQueryUniqueResult(
							"FROM LaborGroupLabor WHERE labor.id = :id AND group.id =:lgId",
							new String[] { "lgId", "id" }, new Object[] { lgId,
									id });
			if (lgl != null)
				commonDao.delete(lgl);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.core.scpwms.server.service.warehouse.LaborManager#
	 * deleteLaborGroupFromLabor(java.lang.Long, java.util.List)
	 */
	@Override
	public void deleteLaborGroupFromLabor(Long laborId, List<Long> ids) {
		for (Long id : ids) {
			LaborGroupLabor lgl = (LaborGroupLabor) commonDao
					.findByQueryUniqueResult(
							"FROM LaborGroupLabor WHERE labor.id = :id AND group.id =:lgId",
							new String[] { "lgId", "id" }, new Object[] { id,
									laborId });
			if (lgl != null)
				commonDao.delete(lgl);
		}
	}

	@Override
	public Labor getLabor(String laborCode, Long whId) {
		String hql = "from Labor labor where labor.code=:laborCode and labor.wh.id =:whId";
		List<Labor> labors = commonDao.findByQuery(hql, new String[] {
				"laborCode", "whId" }, new Object[] { laborCode, whId });

		if (labors != null && labors.size() == 1)
			return labors.get(0);

		return null;
	}

	@Override
	public Labor getLabor(String laborCode, String whCode) {
		String hql = "from Labor labor where labor.code=:laborCode and labor.wh.code =:whCode";
		List<Labor> labors = commonDao.findByQuery(hql, new String[] {
				"laborCode", "whCode" }, new Object[] { laborCode, whCode });

		if (labors != null && labors.size() == 1)
			return labors.get(0);

		return null;
	}


}
