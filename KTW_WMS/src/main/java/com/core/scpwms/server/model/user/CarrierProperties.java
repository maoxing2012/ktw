package com.core.scpwms.server.model.user;

import com.core.business.model.TrackingEntity;

/**
 * 承运商 作业设定
 * （暂空，扩展TMS功能用，比如份额限定、路线限定、运费计算等）
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class CarrierProperties extends TrackingEntity {
	
	/** 承运商 */
	public Carrier carrier;
}
