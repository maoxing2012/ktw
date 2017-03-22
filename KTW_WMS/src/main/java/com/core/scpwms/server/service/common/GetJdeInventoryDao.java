package com.core.scpwms.server.service.common;

public interface GetJdeInventoryDao {
	/**
	 * 比较版本、货主代码、商品代码、色号（批次）、工程号（批次说明）
	 */
	public Long getJdeInvByCondition(String version, String owner,
			String skuCode);

	/**
	 * 
	 * P_MCU IN VARCHAR2 -- 经营单位 P_ITM IN NUMBER --短项目号 P_LOCN IN VARCHAR2 --色号
	 * P_LOTN IN VARCHAR2 --批次说明 P_STATUS IN VARCHAR2 --状态 180 280
	 * 
	 * @param P_MCU
	 * @return
	 */
	public Double getJdeUsableQty(String ownerCd, String skuShortCd,
			String lotInfo, String projectNo, String invStatus);
	
	/**
	 * 
	 * <p>请添加注释</p>
	 *
	 * @return
	 */
	public Long updateAddresseeInfo( String addresseeCode );
}
