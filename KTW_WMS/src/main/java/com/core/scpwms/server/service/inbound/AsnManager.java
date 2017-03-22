package com.core.scpwms.server.service.inbound;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.core.db.server.service.BaseManager;
import com.core.scpwms.server.model.inbound.Asn;
import com.core.scpwms.server.model.inbound.AsnDetail;
import com.core.scpwms.server.model.inbound.InboundHistory;

/**
 * 
 * <p>
 * 请添加注释
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/08<br/>
 */
@Transactional(readOnly = false)
public interface AsnManager extends BaseManager {
	@Transactional
	public void saveAsn( Asn asn );
	
	@Transactional
	public void saveAsnDetail(Long asnId, AsnDetail asnDetail );
	
	@Transactional
	public void deleteAsnDetail( List<Long> asnDetailIds );
	
	/**
	 * 到货预约
	 * @param asnIds
	 */
	@Transactional
	public void publish( List<Long> asnIds, Long binId );
	
	@Transactional
	public void cancel( List<Long> asnIds );
	
	@Transactional
	public void delete( List<Long> asnIds );
	
	/**
	 * 整单关闭
	 * @param asnIds
	 */
	@Transactional
	public void close( List<Long> asnIds );
	
	/**
	 * 收货
	 * @param asnDetailId
	 * @param qty
	 * @param expDate
	 */
	@Transactional
	public InboundHistory receive( Long asnDetailId, Date expDate, String invStatus, Double qty,  Long laborId );
	
	/**
	 * 批量收货
	 * @param asnDetailIds
	 * @param laborId
	 */
	@Transactional
	public void batchReceive( List<Long> asnDetailIds, Long laborId );
	
	/**
	 * 收货
	 * @param asnDetailId
	 * @param qty
	 * @param expDate
	 */
	@Transactional
	public InboundHistory receive4Through( Long asnDetailId, Double qty, Long laborId );
	
	
	/**
	 * 手工回告
	 * @param asnDetailIds
	 * @param desc
	 */
	@Transactional
	public void closeAsnDetail( List<Long> asnDetailIds, String desc );
	
	/**
	 * 关闭收货
	 * @param asnDetailIds
	 * @param desc
	 */
	@Transactional
	public void forceCloseAsnDetail( List<Long> asnDetailIds, String desc );
	
	/**
	 * 取消收货
	 * @param asnDetailIds
	 * @param desc
	 */
	@Transactional
	public void cancelReceive( List<Long> ibdHisIds, String desc );
	
	/**
	 * 合计头部信息
	 * @param asnId
	 */
	@Transactional
	public void updateTotalInfo( Long asnId );
	
	@Transactional
	public double canReceiveQty( Long asnDetailId );
}
