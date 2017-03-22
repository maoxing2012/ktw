package com.core.scpwms.server.mobile;

/**
 * 手持接口
 * @author mousachi
 */
public interface MobileServiceManager {
	// ---------基本--------------
	public String helloMobile(String param);
	public String login( String param );
	public String getOwnerList( String param );
	public String getSkuId( String param );
	
	//-------------收货--------------
	/** 取得待收货的ASN单（到货预约） */
	public String searchAsn( String param );
	
	/** 不指定行收货，输入商品信息，取得ASN明细信息。 */
	public String getAsnDetailBySku( String param );
	
	/** 按行收货的收货确认方法 */
	public String receiveAsnDetail( String param );
	
	/** 收货标签再打印 */
	public String printAsnLabel( String param );
	
	// ----------批量收货--------------
	/** 扫ASN单号带出订单信息 */
	public String getAsnInfo4Multi(String param);
	
	/** 开始批量收货模式 */
	public String startMultiAsn(String param);
	
	/** 扫商品，带出收货信息 */
	public String getAsnIdBySku4Multi(String param);
	
	// ----------上架------------------
	/** 扫二维码带出库存信息 */
	public String getInvInfoByQr( String param );
	
	/** 执行库存移动 */
	public String executeInvMove( String param );
	
	/** 扫库位号带出库存信息 */
	public String getInvInfoByBinCode( String param );
	
	/** 执行库位移动 */
	public String executeBinMove( String param );
	
	// ---------合并拣货------------------
	/** 扫波次号，取得该波次的拣货数量信息 */
	public String searchWaveDoc( String param );
	
	/** 扫库位，扫商品，带出拣货信息 */
	public String getPickInfo4Batch( String param );
	
	/** 确认拣货 */
	public String executeBatchPick( String param );
	
	// ----------整箱拣货------------------
	/** 扫整箱号，系统验证该箱号能不能拣货，如果可以带出 已拣货数/待拣货数 */
	public String getCsPickInfo( String param );
	
	/** 拣货确认 */
	public String executeCsPick( String param );
	
	// ----------散件拣货------------------
	/** 扫散件箱号，系统验证该箱号能不能拣货，如果可以带出 已拣货数/待拣货数 */
	public String getPsPickInfo( String param );
	
	/** 散箱拣货开始认 */
	public String startPsPick( String param );
	
	/** 显示拣货信息 */
	public String getNextPickInfo( String param );
	
	/** 拣货确认 */
	public String executePsPick( String param );
	
	// ----------出库复核--------------
	/** 扫箱号带出该箱子内的商品信息 */
	public String getCaseInfo( String param );
	
	/** 确认复合完成 */
	public String checkedCase( String param );
	
	// ----------盘点-----------------
	/** 查询盘点计划一览 */
	public String searchCountPlan( String param );
	
	/** 输入库位，QRCode取得商品信息 */
	public String getCountInfoByQr( String param );
	
	/** 返回盘点结果 */
	public String executeCount( String param );
	
	/** 是否在本次盘点计划内 */
	public String checkBin4Count( String param );
	
	// ----------查询-----------------
	/** 根据商品ID取得商品的详细信息 */
	public String getSkuInfo( String param );
	
	/** 根据商品ID和库位号取得库存信息 */
	public String searchInventory( String param );
	
	/** 根据库存Id取得库存详细信息 */
	public String getInvDetailInfo( String param );
}
