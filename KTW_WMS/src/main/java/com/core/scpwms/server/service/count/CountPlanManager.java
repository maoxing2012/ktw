package com.core.scpwms.server.service.count;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.domain.InvEditData;
import com.core.scpwms.server.model.count.CountPlan;

/**
 * 
 * @description 盘点接口类
 * @author MBP:xiaoyan<br/>
 * @createDate 2014-2-26
 * @version V1.0<br/>
 */
@Transactional(readOnly = false)
public interface CountPlanManager extends BaseManager {

	/**
	 * 
	 * <p>
	 * 指定盘点
	 * </p>
	 * 
	 * @param cp
	 */
	@Transactional
	public void newSkuBin(CountPlan cp);

	/**
	 * 
	 * <p>
	 * 循环盘点
	 * </p>
	 * 
	 * @param cp
	 */
	@Transactional
	public void newCircle(CountPlan cp);

	/**
	 * <p>
	 * 动碰盘点
	 * </p>
	 * 
	 * @param cp
	 */
	@Transactional
	public void newDynamic(CountPlan cp);
	
	/**
	 * <p>
	 * 全盘
	 * </p>
	 * 
	 * @param cp
	 */
	@Transactional
	public void newAll(CountPlan cp);

	/**
	 * <p>
	 * 修改
	 * </p>
	 * 
	 * @param cp
	 */
	@Transactional
	public void save(CountPlan cp);

	/**
	 * 
	 * <p>
	 * 删除
	 * </p>
	 * 
	 * @param ids
	 */
	@Transactional
	public void deleteCount(List<Long> ids);

	/**
	 * 发布
	 * 
	 * @param ids
	 */
	@Transactional
	public void publish(List<Long> ids);

	/**
	 * 回退
	 * 
	 * @param ids
	 */
	@Transactional
	public void cancel(List<Long> ids);

	/**
	 * 关闭
	 * 
	 * @param ids
	 */
	@Transactional
	public void close(List<Long> ids);

	/**
	 * SKU明细保存
	 * 
	 * @param ids
	 * @param countPlanId
	 */
	@Transactional
	void saveSkuDetail(List<Long> ids, Long countPlanId);

	/**
	 * BIN明细保存
	 * 
	 * @param ids
	 * @param countPlanId
	 */
	@Transactional
	void saveBinDetail(List<Long> ids, Long countPlanId);

	/**
	 * 明细删除
	 * 
	 * @param ids
	 */
	@Transactional
	public void deleteCountDetail(List<Long> ids);

	/**
	 * 盘点确认
	 * 
	 * @param ids
	 * @param params
	 */
	@Transactional
	public void countConfirm(List<Long> ids, Map params);
	
	/**
	 * <p>盘点确认</p>
	 *
	 * @param countRecordId
	 * @param countPackQty
	 * @param packId
	 * @param countUserId
	 */
	@Transactional
	public void countConfirm(Long countRecordId, Double countPackQty, Long packId, Long laborId);

	/**
	 * 
	 * <p>
	 * 盘点完成(无差异)
	 * </p>
	 * 
	 * @param ids
	 */
	@Transactional
	public void closeCountRecord(List<Long> ids);

	/**
	 * 盘点登记
	 * 
	 * @param countRecord
	 * @param sku
	 * @param bin
	 * @param packDetail
	 * @param property1
	 */
	@Transactional
	public void saveRegister(Long countPlanId, Long skuId, Long binId, Double qty, Date expDate, String invStatus);
	
	/**
	 * 盘点登记
	 * 
	 * @param countRecord
	 * @param sku
	 * @param bin
	 * @param packDetail
	 * @param property1
	 */
	@Transactional
	public void saveRegister(Long countPlanId, Long skuId, Long binId, Double qty, Date expDate, String invStatus, String trackSeq, Date inboundDate, Long laborId);

	/**
	 * 差异确认
	 * 
	 * @param countRecordIds
	 * @param desc
	 * 
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void adjust(List<Long> countRecordIds, String desc) throws Exception;

	/**
	 * 
	 * <p>
	 * 生成复盘单
	 * </p>
	 * 
	 * @param ids
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@Transactional
	public void geneRecordBill(List<Long> ids);

	/**
	 * 
	 * <p>
	 * 盘亏确认
	 * </p>
	 * 
	 * @param countRecordIds
	 * @param reasonCodeId
	 * @param desc
	 * 
	 * @return
	 */
	@Transactional
	public List<InvEditData> inventoryConfirm(List<Long> countRecordIds,String desc);

	/**
	 * 
	 * <p>
	 * 添加所有存货库位
	 * </p>
	 * 
	 * @param countPlanId
	 * @param onlyInventory
	 */
	@Transactional
	public void addAllBin(CountPlan cp);
	
	/**
	 * 
	 * <p>取得待盘点的单数</p>
	 *
	 * @param whId
	 */
	@Transactional
	public Long getUnCountPlanCount(Long whId);
}
