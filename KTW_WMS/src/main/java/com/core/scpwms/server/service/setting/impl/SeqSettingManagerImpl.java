package com.core.scpwms.server.service.setting.impl;

import java.util.List;

import com.core.business.exception.BusinessException;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.constant.ExceptionConstant;
import com.core.scpwms.server.model.common.SequenceProperties;
import com.core.scpwms.server.service.setting.SeqSettingManager;

/**
 * 
 * <p>
 * 单据编号设定实现类。
 * </p>
 * 
 * @version 1.0
 * @author MBP : 吴 <br/>
 *         修改履历 <br/>
 *         2013/02/21 : MBP 吴: 初版创建<br/>
 */
@SuppressWarnings("all")
public class SeqSettingManagerImpl extends DefaultBaseManager implements
		SeqSettingManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.SeqSettingManager#saveSeq(com.
	 * core.scpwms.server.model.common.SequenceProperties)
	 */
	@Override
	public void saveSeq(SequenceProperties seq) {

		if (seq.isNew()) {
			// 判断是否重复记录
			StringBuffer hql = new StringBuffer(
					"from SequenceProperties sp where lower(sp.code) = :spCode");

			List<SequenceProperties> sps = commonDao.findByQuery(
					hql.toString(), new String[] { "spCode" },
					new Object[] { seq.getCode().toLowerCase() });

			if (!sps.isEmpty()) {
				throw new BusinessException(ExceptionConstant.DUPLICATE_CODE);
			}
			seq.setCreateInfo(new CreateInfo(UserHolder.getUser()));
		} else {
			seq.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		}

		this.getCommonDao().store(seq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.SeqSettingManager#deleteSeq(java
	 * .util.List)
	 */
	@Override
	public void deleteSeq(List<Long> ids) {
		for (Long id : ids) {
			// 是否已经有单据类型引用了
			List<Long> otIds = commonDao
					.findByQuery(
							"select ot.id from OrderType ot where ot.seqProperties.id = :seqId",
							"seqId", id);
			if (otIds != null && otIds.size() > 0) {
				throw new BusinessException(
						ExceptionConstant.CANNOT_DELETE_AS_HAS_HISTORY);
			}

			this.commonDao.delete(this.commonDao.load(SequenceProperties.class,
					id));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.SeqSettingManager#disableSeq(java
	 * .util.List)
	 */
	@Override
	public void disableSeq(List<Long> ids) {
		for (Long id : ids) {
			SequenceProperties sp = commonDao
					.load(SequenceProperties.class, id);
			sp.setDisabled(Boolean.TRUE);
			sp.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(sp);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.core.scpwms.server.service.setting.SeqSettingManager#enableSeq(java
	 * .util.List)
	 */
	@Override
	public void enableSeq(List<Long> ids) {
		for (Long id : ids) {
			SequenceProperties sp = commonDao
					.load(SequenceProperties.class, id);
			sp.setDisabled(Boolean.FALSE);
			sp.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
			commonDao.store(sp);
		}
	}

}
