package com.core.scpwms.server.service.warehouse.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.model.security.Group;
import com.core.business.model.security.User;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.ui.table.RowData;
import com.core.scpwms.client.bean.WarehouseInfo;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.warehouse.UserGroupWarehouse;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.model.warehouse.WarehouseProperties;
import com.core.scpwms.server.model.warehouse.WarehouseTmsPlatForm;
import com.core.scpwms.server.service.rules.CycleCountRuleManager;
import com.core.scpwms.server.service.rules.PickUpRuleManager;
import com.core.scpwms.server.service.rules.PutAwayRuleManager;
import com.core.scpwms.server.service.rules.WaveRuleManager;
import com.core.scpwms.server.service.warehouse.BinPropertiesManager;
import com.core.scpwms.server.service.warehouse.LaborGroupManager;
import com.core.scpwms.server.service.warehouse.LaborManager;
import com.core.scpwms.server.service.warehouse.WarehouseManager;
import com.core.scpwms.server.service.warehouse.WhAreaManager;

/**
 * 
 * <p>
 * 仓库管理接口实现。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 温 <br/>
 *         修改履历 <br/>
 *         2013/01/13 : MBP 温: 初版创建<br/>
 */
@SuppressWarnings("all")
public class WarehouseManagerImpl extends DefaultBaseManager implements WarehouseManager {
    private WhAreaManager whAreaManager;

	private LaborManager laborManager;

	private LaborGroupManager laborGroupManager;

	private PutAwayRuleManager putawayRuleManager;

	private PickUpRuleManager pickUpRuleManager;

	private CycleCountRuleManager cycleCountRuleManager;

	private WaveRuleManager waveRuleManager;

	private BinPropertiesManager binPropertiesManager;

	public WhAreaManager getWhAreaManager() {
		return this.whAreaManager;
	}

	public void setWhAreaManager(WhAreaManager whAreaManager) {
		this.whAreaManager = whAreaManager;
	}

	public LaborManager getLaborManager() {
		return this.laborManager;
	}

	public void setLaborManager(LaborManager laborManager) {
		this.laborManager = laborManager;
	}

	public LaborGroupManager getLaborGroupManager() {
		return this.laborGroupManager;
	}

	public void setLaborGroupManager(LaborGroupManager laborGroupManager) {
		this.laborGroupManager = laborGroupManager;
	}

	public PutAwayRuleManager getPutawayRuleManager() {
		return this.putawayRuleManager;
	}

	public void setPutawayRuleManager(PutAwayRuleManager putawayRuleManager) {
		this.putawayRuleManager = putawayRuleManager;
	}

	public PickUpRuleManager getPickUpRuleManager() {
		return this.pickUpRuleManager;
	}

	public void setPickUpRuleManager(PickUpRuleManager pickUpRuleManager) {
		this.pickUpRuleManager = pickUpRuleManager;
	}

	public CycleCountRuleManager getCycleCountRuleManager() {
		return this.cycleCountRuleManager;
	}

	public void setCycleCountRuleManager(
			CycleCountRuleManager cycleCountRuleManager) {
		this.cycleCountRuleManager = cycleCountRuleManager;
	}

	public WaveRuleManager getWaveRuleManager() {
		return this.waveRuleManager;
	}

	public void setWaveRuleManager(WaveRuleManager waveRuleManager) {
		this.waveRuleManager = waveRuleManager;
	}

	public BinPropertiesManager getBinPropertiesManager() {
		return this.binPropertiesManager;
	}

	public void setBinPropertiesManager(
			BinPropertiesManager binPropertiesManager) {
		this.binPropertiesManager = binPropertiesManager;
	}

	/**
	 * 根据登陆用户，返回第一个可用仓库
	 */
	public Warehouse getWarehouseByLoginUser(User loginUser) {
		User user = (User) commonDao.load(User.class, loginUser.getId());

		// 如果是Admin用户（有所有仓库的访问权限），返回第一个可用仓库
		if (user.isAdmin()) {
			List result = commonDao
					.findByQuery("select w from Warehouse w where w.disabled=false order by w.code");
			if (result != null && !result.isEmpty()) {
				return (Warehouse) result.get(0);
			}
		}

		// 如果不是Admin用户，返回第一个可用仓库
		List result = commonDao.findByNamedQuery("getWarehouseByUserID",
				new String[] { "loginId" }, new Object[] { loginUser.getId() });
		if (result != null && !result.isEmpty()) {
			return (Warehouse) result.get(0);
		}

		// 没有找到可用仓库，返回Null
		return null;
	}

	/**
	 * 获取当前仓库
	 * 
	 * @param m
	 * @return
	 */
	public Map getCurrentWarehouse(Map m) {
		Map map = new HashMap();
		WarehouseInfo whInfo = new WarehouseInfo();
		whInfo.setWhId("" + WarehouseHolder.getWarehouse().getId());
		whInfo.setWhCode(WarehouseHolder.getWarehouse().getCode());
		whInfo.setWhName(WarehouseHolder.getWarehouse().getName());
		map.put("CURRENTWAREHOUSE", whInfo);
		map.put("CURRENTUSERID", UserHolder.getUser().getId());
		return map;
	}

	/**
	 * 获取当前用户可以访问的仓库
	 * 
	 * @param m
	 * @return
	 */
	public Map getWhListForClient(Map map) {
		Map result = new HashMap();
		List<Warehouse> whList = new ArrayList<Warehouse>();
		User user = load(User.class,
				Long.parseLong(map.get("user.id").toString()));
		if (user.isAdmin()) {
			whList = commonDao
					.findByQuery("select wh from Warehouse wh where wh.disabled=false order by wh.id desc");
		} else {
			Set<Warehouse> whSet = new HashSet<Warehouse>();
			String hsql = "Select ugw.warehouse FROM UserGroupWarehouse ugw WHERE ugw.userGroup.id = :userGroupId order by ugw.warehouse.id";
			for (Group group : user.getGroups()) {

				List<Warehouse> ws = commonDao.findByQuery(hsql,
						new String[] { "userGroupId" },
						new Object[] { group.getId() });
				whSet.addAll(ws);
			}
			whList.addAll(whSet);
		}

		List rowDatas = new ArrayList();
		for (Warehouse warehouse : whList) {
			RowData rowData = new RowData();
			rowData.addColumnValue(warehouse.getId());
			rowData.addColumnValue(warehouse.getName());
			rowData.addColumnValue(warehouse.getShortName());
			rowData.addColumnValue(warehouse.getCode());
			rowDatas.add(rowData);
		}
		result.put("warehouse.list", rowDatas);
		return result;
	}

	/**
	 * 保存仓库
	 * 
	 * @param warehouse
	 * @return
	 */
	public void saveWh(Warehouse warehouse) {
		if (warehouse.isNew()) {
			// 判断是否重复记录
			StringBuffer hql = new StringBuffer(
					"from Warehouse wh where lower(wh.code) = :whCode");
			List<Warehouse> whs = commonDao.findByQuery(hql.toString(),
					new String[] { "whCode" }, new Object[] { warehouse.getCode().toLowerCase() });

			if (!whs.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE,
						true);
			}
			warehouse.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		}

		warehouse.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		
		if( warehouse.getProperties() == null ){
			WarehouseProperties wp = new WarehouseProperties();
			wp.setWh(warehouse);
			wp.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			wp.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			warehouse.setProperties(wp);
		}
		
		commonDao.store(warehouse.getProperties());
		commonDao.store(warehouse);
	}

	/**
	 * 生效指定的仓库
	 * 
	 * @param warehouse
	 *            选择待生效的仓库ID集合
	 * @return
	 */
	@Transactional
	public void enableWh(List<Long> whIdList) {
		for (Long id : whIdList) {
			Warehouse wh = this.commonDao.load(Warehouse.class, id);
			wh.setDisabled(false);

			wh.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(wh);
		}
	}

	/**
	 * 失效指定的仓库 系统只失效没有设置库位的仓库
	 * 
	 * @param warehouseIds
	 *            选择待失效的仓库ID集合
	 */
	@Transactional
	public void disableWh(List<Long> whIdList) {
		for (Long id : whIdList) {
			Warehouse wh = this.commonDao.load(Warehouse.class, id);
			wh.setDisabled(true);

			wh.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(wh);
		}
	}

	@Override
	public void deleteWh(List<Long> ids) {
		for (Long id : ids) {
			// 清业务数据（这个以后做成存储过程，暂时不提供该功能，太危险鸟。）
			// TODO

			// 作业人员和作业组
			List<Long> laborIds = commonDao.findByQuery(
					" select id from Labor where wh.id =:whId", "whId", id);
			if (laborIds != null && laborIds.size() > 0) {
				laborManager.deleteLabor(laborIds);
			}

			List<Long> laborGroupIds = commonDao
					.findByQuery(
							" select id from LaborGroup where wh.id =:whId",
							"whId", id);
			if (laborGroupIds != null && laborGroupIds.size() > 0) {
				laborGroupManager.delete(laborGroupIds);
			}

			// 策略
			// 上架
			List<Long> parIds = commonDao.findByQuery(
					" select id from PutAwayRule where wh.id =:whId", "whId",
					id);
			if (parIds != null && parIds.size() > 0) {
				putawayRuleManager.deleteRules(parIds);
			}

			// 拣货
			List<Long> purIds = commonDao
					.findByQuery(
							" select id from PickUpRule where wh.id =:whId",
							"whId", id);
			if (purIds != null && purIds.size() > 0) {
				pickUpRuleManager.deleteRules(purIds);
			}

			// 循环盘点
			List<Long> ccrIds = commonDao.findByQuery(
					" select id from CycleCountRule where wh.id =:whId",
					"whId", id);
			if (ccrIds != null && ccrIds.size() > 0) {
				cycleCountRuleManager.delete(ccrIds);
			}

			// 波次
			List<Long> wvrIds = commonDao.findByQuery(
					" select id from WaveRule where wh.id =:whId", "whId", id);
			if (wvrIds != null && wvrIds.size() > 0) {
				waveRuleManager.delete(wvrIds);
			}

			// 删除下属的所有库区
			List<Long> whAreaIds = commonDao.findByQuery(
					" select id from WhArea where wh.id =:whId", "whId", id);
			if (whAreaIds != null && whAreaIds.size() > 0) {
				whAreaManager.deleteWhArea(whAreaIds);
			}

			// 库位属性
			List<Long> bpIds = commonDao.findByQuery(
					" select id from BinProperties where wh.id =:whId", "whId",
					id);
			if (bpIds != null && bpIds.size() > 0) {
				binPropertiesManager.deleteBinProperties(bpIds);
			}

			Warehouse wh = commonDao.load(Warehouse.class, id);
			this.commonDao.delete(wh.getProperties());
			wh.setProperties(null);
			this.commonDao.delete(wh);
		}
	}

	@Override
	public void addUserGroup(Long warehouseId, List<Long> ids) {
		String description = "";
		Warehouse wh = this.commonDao.load(Warehouse.class, warehouseId);

		if (ids != null && ids.size() > 0) {
			for (Long userGroupId : ids) {
				String hsql = "from UserGroupWarehouse ugw where ugw.warehouse.id=:whId and ugw.userGroup.id=:userGroupId";
				List<UserGroupWarehouse> exsitDatasList = this.commonDao
						.findByQuery(hsql,
								new String[] { "whId", "userGroupId" },
								new Object[] { warehouseId, userGroupId });
				Group group = this.commonDao.load(Group.class, userGroupId);

				if (exsitDatasList.isEmpty()) {
					description = wh.getName() + "の" + group.getName() + "ユーザグループ";
					UserGroupWarehouse ugw = new UserGroupWarehouse(group, wh, description);
					this.commonDao.store(ugw);
				} else {
					throw new BusinessException("warehouse.userGroup.exsit",
							new String[] { group.getName() });
				}
			}
		}
	}

	@Override
	public void deleteUserGroup(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long userGroudId : ids) {
				UserGroupWarehouse ugw = this.commonDao.load(
						UserGroupWarehouse.class, userGroudId);
				delete(ugw);
			}
		}
	}

	@Override
	public void deleteTmsPlatform(List<Long> whTmsIds) {
		for (Long id : whTmsIds) {
			WarehouseTmsPlatForm wp = commonDao.load(
					WarehouseTmsPlatForm.class, id);
			commonDao.delete(wp);
		}
	}

	@Override
	public void saveTmsPlatform(Long whId, WarehouseTmsPlatForm platForm) {
		Warehouse wh = commonDao.load(Warehouse.class, whId);
		platForm.setWh(wh);
		commonDao.store(platForm);
	}
}
