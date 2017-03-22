package com.core.scpwms.server.service.user;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.user.Addressee;

/**
 * 
 * @description   收件人接口
 * @author         MBP:xiaoyan<br/>
 * @createDate    2014-1-7
 * @version        V1.0<br/>
 */
@Transactional(readOnly = false)
public interface AddresseeManager extends BaseManager {
	
	/**
	 * 
	 * <p>保存</p>
	 *
	 * @param addressee
	 */
	@Transactional
	void store(Addressee addressee);
	
	/**
	 * 
	 * <p>删除</p>
	 *
	 * @param ids
	 */
	@Transactional
	void delete(List<Long> ids);
	
	/**
	 * 
	 * <p>生效</p>
	 *
	 * @param ids
	 */
	@Transactional
	void enable(List<Long> ids);
	
	/**
	 * 
	 * <p>失效</p>
	 *
	 * @param ids
	 */
	@Transactional
	void disable(List<Long> ids);

}
