package com.core.scpwms.server.service.warehouse;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.warehouse.Labor;

/**
 * @description 作业人员设定
 * @author MBP:xiaoyan<br/>
 * @createDate 2013-1-31
 * @version V1.0<br/>
 */
@Transactional(readOnly = false)
public interface LaborManager extends BaseManager {
	/**
	 * 
	 * <p>
	 * 新增
	 * </p>
	 * 
	 * @param labor
	 */
	@Transactional
	void storeLabor(Labor labor);

	/**
	 * 
	 * <p>
	 * 删除
	 * </p>
	 * 
	 * @param ids
	 */
	@Transactional
	void deleteLabor(List<Long> ids);

	/**
	 * 
	 * <p>
	 * 生效
	 * </p>
	 * 
	 * @param ids
	 */
	@Transactional
	void enable(List<Long> ids);

	/**
	 * 
	 * <p>
	 * 失效
	 * </p>
	 * 
	 * @param ids
	 */
	@Transactional
	void disable(List<Long> ids);

	/**
	 * 
	 * <p>
	 * 添加作业组－作业人员对应关系
	 * </p>
	 * 
	 * @param laborId
	 * @param ids
	 */
	@Transactional
	void addLaborGroup(Long laborId, List<Long> ids);

	/**
	 * 
	 * <p>
	 * 删除作业组－作业人员对应关系
	 * </p>
	 * 
	 * @param ids
	 */
	@Transactional
	void deleteLaborGroup(List<Long> ids);

	/**
	 * 
	 * <p>
	 * 从作业组删除作业人员
	 * </p>
	 * 
	 * @param lgId
	 * @param ids
	 */
	@Transactional
	void deleteLaborFromLaborGroup(Long lgId, List<Long> ids);
	
	/**
	 * 
	 * <p>
	 * 从作业人员删除作业组
	 * </p>
	 * 
	 * @param lgId
	 * @param ids
	 */
	@Transactional
	void deleteLaborGroupFromLabor(Long laborId, List<Long> ids);

	/**
	 * 
	 * <p>
	 * 添加作业人员到作业组
	 * </p>
	 * 
	 * @param lgId
	 * @param ids
	 */
	@Transactional
	void addLabor2LaborGroup(Long lgId, List<Long> ids);
	
	/**
	 * 
	 * <p>添加作业组到作业人员</p>
	 *
	 * @param laborId
	 * @param ids
	 */
	@Transactional
	void addLaborGroup2Labor(Long laborId, List<Long> ids);

	@Transactional
	Labor getLabor(String laborCode, Long whId);

	@Transactional
	Labor getLabor(String laborCode, String whCode);
}
