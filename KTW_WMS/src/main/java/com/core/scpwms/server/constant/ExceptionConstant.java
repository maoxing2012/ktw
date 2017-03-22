package com.core.scpwms.server.constant;

/**
 * 
 * <p>
 * 异常消息
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/07/14<br/>
 */
public interface ExceptionConstant {
	// //////////////////////警告////////////////////////////////////
	/** 因为目标批次和原批次一致，不做任何处理。 */
	public static final String WARN_SAME_LOT = "warn.same.lot";

	// //////////////////////共同////////////////////////////////////
	/** 编号重复！ */
	public static final String DUPLICATE_CODE = "duplicate.code";

	/** 该库存不存在！ */
	public static final String INVENTORY_NOT_FOUND = "inventory.not.found";

	/** 空订单！ */
	public static final String EMPTY_ORDER = "empty.order";

	/** 状态已经更新！ */
	public static final String ERROR_STATUS = "error.status";

	/** 找不到相关订单类型！{0} */
	public static final String CANNOT_FIND_ORDER_TYPE = "cannot.find.order.type";

	/** 处理成功，有警告消息！ */
	// public static final String SUCCESS_WITH_WARNING = "success.with.warning";

	/** 有商品未分配到库位，请点击右下角按钮查阅详情。 */
	public static final String HAS_DETAIL_NOT_ALL_ALLOCATE_BIN_SEE_DETAIL = "has.detail.not.all.allocate.bin.see.detail";

	/** 找不到符合条件的策略！ */
	public static final String CANNOT_FIND_RULE = "cannot.find.rule";

	/** 没有明细内容，不能发行！ */
	public static final String CANNOT_PUBLISH_AS_NO_DETAIL = "cannot.publish.as.no.detail";

	/** 开始时间不能大于结束时间！ */
	public static final String TIME_FROM_BIGGER_THEN_TO = "time.from.bigger.than.to";

	/** 开始日期不能大于结束日期！ */
	public static final String DATE_FROM_BIGGER_THEN_TO = "date.from.bigger.than.to";

	/** 批次信息必须输入！商品：{0} 批次属性：{1} */
	public static final String REQUIRED_LOT = "required.lot";

	/** 请指定一个库位(或库位组，功能区，库区)。 */
	public static final String BIN_BG_ST_WA_REQUIRED = "bin.bg.st.wa.required";

	/** 该库位上有库存(或待上架的库存)，暂时无法修改库位属性。请清空库存后再试一次。 */
	public static final String CANNOT_EDIT_BIN_PROPERTIES_AS_HAS_INVENTORY = "cannot.edit.bin.properties.as.has.inventory";

	/** 该库位上已经存放有不同货主的库存，不能调整为货主不混放。请调整库存后再试一次。 */
	public static final String CANNOT_CHANGE_TO_UNMIX_OWNER_AS_HAS_DIFFERENT = "cannot.change.to.unmix.owner.as.has.different";
	
	/** 该库位上已经存放有不同批次的库存，不能调整为批次不混放。请调整库存后再试一次。 */
	public static final String CANNOT_CHANGE_TO_UNMIX_LOT_AS_HAS_DIFFERENT = "cannot.change.to.unmix.lot.as.has.different";

	/** 该库位上已经存放有不同商品的库存，不能调整为商品不混放。请调整库存后再试一次。 */
	public static final String CANNOT_CHANGE_TO_UNMIX_SKU_AS_HAS_DIFFERENT = "cannot.change.to.unmix.sku.as.has.different";
	
	/** 已经有业务记录，无法删除。 */
	public static final String CANNOT_DELETE_AS_HAS_HISTORY = "cannot.delete.as.has.history";
	
	// //////////////////////入库上架////////////////////////////////////
	/** 已经加入其他计划，不能取消收货！ */
	public static final String CANNOT_CANCEL_RECEIVE_HAS_PLAN = "cannot.cancel.receive.has.other.plan";

	/** 已经被分配，不能取消收货！ */
	public static final String CANNOT_CANCEL_RECEIVE_ALLOCATE_INVENTORY = "cannot.cancel.receive.allocate.inventory";

	/** 无法创建收货上架单！(已经创建了上架单或者库存已经被分配) */
	public static final String CANNOT_CREATE_PUTAWAY = "cannot.create.putaway";

	/** 已经有分配数，不能取消该上架明细！请取消分配以后再尝试。 */
	public static final String CANNOT_DELETE_PUTAWAY_DETAIL_AS_ALLOCATED = "cannot.delete.putaway.detail.as.allocated";

	/** 超过了溢收范围。溢收上限：{0} */
	public static final String EXCEED_IB_LIMIT = "exceed.ib.limit";

	/** 请输入至少一项费用。 */
	public static final String INPUT_PROCESS_INBOUND_FEE = "input.process.inbound.fee";

	/** 库位分配未完成，无法创建上架作业单。 */
	public static final String CANNOT_CREATE_PUTAWAY_AS_UNALLOCATE = "cannot.create.putaway.as.unallocate";

	/** 上架数量超过了待上架的数量。 */
	public static final String PUTAWAY_QTY_MORE_THAN_PLAN_QTY = "puaway.qty.more.than.plan.qty";

	/** 因为JDE已经收货到良品库位上，此商品必须按良品收货。 */
	public static final String CANNOT_RECEIVE_AS_UNAVAILABLE = "cannot.receive.as.unavailable";

	/** 因为JDE已经收货到不良品库位上，此商品必须按不良品收货。 */
	public static final String CANNOT_RECEIVE_AS_AVAILABLE = "cannot.receive.as.available";

	/** 该订单不能修改JDE指定的批次属性或批次说明。 */
	public static final String CANNOT_MODIFY_LOT_AS_JDE = "cannot.modify.lot.as.jde";

	/** 尚有未执行完成的作业任务，无法关闭该作业单。请先执行完成(或关闭)相关的作业任务后，再试一次。 */
	public static final String CANNOT_CLOSE_WO_AS_HAS_WT_TODO = "cannot.close.wo.as.has.wt.todo";

	// //////////////////////////库内////////////////////////////////////////////
	/** 冻结数量超过当期库存可用数量。 */
	public static final String CANNOT_LOCK_AS_MORE_THAN_AVAILABLE_QTY = "cannot.lock.as.more.than.available.qty";

	/** 移动数量超过库存可用数量。 */
	public static final String CANNOT_MOVE_AS_MORE_THAN_AVAILABLE_QTY = "cannot.move.as.more.than.available.qty";

	/** 调整数量超过库存可用数量。 */
	public static final String CANNOT_ADJUST_AS_MORE_THAN_AVAILABLE_QTY = "cannot.adjust.as.more.than.available.qty";

	/** 源库位有出锁。 */
	public static final String SRC_BIN_HAS_OUT_LOCK = "src.bin.has.out.lock";

	/** 目标库位有入锁。 */
	public static final String DESC_BIN_HAS_IN_LOCK = "desc.bin.has.in.lock";
	
	/** 该库位不能混放货主。 */
	public static final String CANNOT_MIX_OWNER = "cannot.mix.owner";
	
	/** 该库位不能混放商品。 */
	public static final String CANNOT_MIX_SKU = "cannot.mix.sku";

	/** 该库位不能混放批次。 */
	public static final String CANNOT_MIX_LOT = "cannot.mix.lot";

	/** 该库位是托盘库位。 */
	public static final String PALLET_BIN_ERROR = "pallet.bin.error";

	/** 超过库存能承载的范围。 */
	public static final String BIN_FULL_SCALE_ERROR = "bin.full.scale.error";

	/** 原货主和目标货主一样。 */
	public static final String SAME_SRC_DESC_OWNER = "same.src.desc.owner";
	
	/** 原库位和目标库位一样。 */
	public static final String SAME_SRC_DESC_BIN = "same.src.desc.bin";

	/** 输入调整后的批次单价。 */
	public static final String INPUT_LOT_PRICE = "input.lotPrice";

	/** 备货区的库存不能冻结。 */
	public static final String CANNOT_LOCK_AS_CHECK_BIN = "cannot.lock.as.check.bin";

	/** 备货区的库存不能修改批次或批次属性。 */
	public static final String CANNOT_EDIT_LOT_AS_CHECK_BIN = "cannot.edit.lot.as.check.bin";

	/** 备货区的库存不能改变包装。 */
	public static final String CANNOT_EDIT_PACK_AS_CHECK_BIN = "cannot.edit.pack.as.check.bin";

	/** 调整数量超过JDE的可用数量。 */
	public static final String CANNOT_MODIFY_QTY_AS_MORE_THAN_JDE_AVAILABLE_QTY = "cannot.modify.qty.as.more.than.jde.available.qty";

	/** 因有商品未分配到库存,执行失败! 请点击右下角按钮查阅详情。 */
	public static final String CANNOT_EXECUTE_HAS_DETAIL_NOT_ALL_ALLOCATE_SEE_DETAIL = "cannot.execute.has.detail.not.all.allocate.see.detail";

	/** 批次调整单{0}在执行过程中因为库位设定的原因(混放限制，容量限制等)，批次调整后的部分商品被调整入180库位。请注意调整库位。 */
	public static final String CANNOT_RETURN_OLD_BIN_AS_UNMIX = "cannot.return.old.bin.as.unmix";

	/** 找不到180库位。请新建一个编号180的虚拟库位。 */
	public static final String CANNOT_FIND_BIN_180 = "cannot.find.bin.180";

	/** 找不到满足条件的库存，请修改条件后再试一次。 */
	public static final String CANNOT_FIND_BIN_TO_OPT = "cannot.find.bin.to.opt";

	/** 请指定一个库位组。 */
	public static final String BG_REQUIRED = "bg.required";
	
	/** 请输入库容下限。 */
	public static final String MINBININV_REQUIRED = "minBinInv.required";
	
	/** 托盘库位不能放散件。 */
	public static final String CANNOT_PUT_PALLET_BIN_WITHOUT_PALLET = "cannot.put.pallet.bin.without.pallet";
	
	/** 您输入的库位编号{0}和容器当前所在的库位号{1}不一致。 */
	public static final String INPUT_BIN_NOT_MATCH_PALLET_BIN = "input.bin.not.match.pallet.bin";
	
	// //////////////////////////质检////////////////////////////////////////////
	/** 输入数量超过待质检数量。 */
	public static final String CANNOT_QC_AS_MORE_THAN_UNQC_QTY = "cannot.qc.as.more.than.unqc.qty";

	// //////////////////////////加工////////////////////////////////////////////
	/** 指定的加工库位已经被占用。 */
	public static final String PROCESS_BIN_NOT_EMPTY = "process.bin.not.empty";

	/** 找不到空的加工库位。 */
	public static final String PROCESS_BIN_NOT_FIND = "process.bin.not.find";

	/** 加工数量超过库存可用数量。 */
	public static final String CANNOT_PROCESS_AS_MORE_THAN_AVAILABLE_QTY = "cannot.process.as.more.than.available.qty";

	// //////////////////////////出库////////////////////////////////////////////
	/** 备货日晚于送货日！ */
	public static final String STOCK_DATE_AFTER_ETA = "stockdate.after.eta";

	/** 备货日不能早于今天！ */
	public static final String STOCK_DATE_BEFORE_TODAY = "stockdate.before.today";

	/** 找不到复核库位！ */
	public static final String CHECK_BIN_NOT_FIND = "check.bin.not.find";

	/** 不能直接发运！ */
	public static final String CANNOT_DIRECT_SHIP = "cannot.direct.ship";

	/** 找不到满足条件的出库单。 */
	public static final String WAVE_ERROR_HAVE_NO_OBD = "wave.error.haveNoObd";

	/** 库存不足！商品：{0} */
	public static final String INVENTORY_NOT_ENOUGH = "inventory.not.enough";

	/** 请取消分配后才能取消波次计划。 */
	public static final String CANNOT_CANCEL_WAVE_AS_ALLOCATE = "cannot.cancel.wave.as.allocate";

	/** 分配数量超过库存可用数量。 */
	public static final String CANNOT_ALLOCATE_AS_MORE_THAN_AVAILABLE_QTY = "cannot.allocate.as.more.than.available.qty";

	/** 分配数量超过待分配数量。 */
	public static final String CANNOT_ALLOCATE_AS_MORE_THAN_TOALLOCATE_QTY = "cannot.allocate.as.more.than.toAllocate.qty";

	/** 该波次计划下的所有拣货任务都已经加入拣货单。 */
	public static final String WV_ALL_WT_ADD_TO_WO = "wv.all.wt.add.to.wo";

	/** 成功生成了{0}张拣货单。 */
	public static final String AUTO_CREATE_WO = "auto.create.wo";

	/** 备货区尚有货品，无法强制关闭，请退拣或继续发货。 */
	public static final String CANNOT_CLOSE_OBD_AS_HAS_INVENTORY_AT_CHECKBIN = "cannot.close.obd.as.has.inventory.at.checkbin";

	/** 备货区已无货品，无需退拣。可以强制关闭该订单。 */
	public static final String CANNOT_CREATE_RETURN_OBD_AS_HAS_NO_INVENTORY_AT_CHECKBIN = "cannot.create.return.obd.as.has.no.inventory.at.checkbin";

	/** 尚有未执行完成的拣货任务{0},无法关闭该波次计划。 */
	public static final String CANNOT_CLOSE_WV_AS_HAS_OPEN_WT = "cannot.close.wv.as.has.open.wt";

	/** 有商品未分配到库存。 */
	public static final String HAS_DETAIL_NOT_ALL_ALLOCATE = "has.detail.not.all.allocate";

	/** 有商品未分配到库存，请点击右侧按钮查阅详情。 */
	public static final String HAS_DETAIL_NOT_ALL_ALLOCATE_SEE_DETAIL = "has.detail.not.all.allocate.see.detail";

	/** 尚有未执行完成的拣货任务{0},无法复核完成。 */
	public static final String CANNOT_CHECK_OBD_AS_HAS_WT_TODO = "cannot.check.obd.as.has.wt.todo";

	/** 已经加入配载计划，无法取消。请取消配载后再试一次。 */
	public static final String CANNOT_CANCEL_OBD_AS_HAS_STOWAGE_PLAN = "canno.cancel.obd.as.has.stowage.plan";

	/** 已经加入配载计划，无法修改发运方式。请取消配载后再试一次。 */
	public static final String CANNOT_EDIT_SHIP_METHOD_AS_HAS_STOWAGE_PLAN = "cannot.edit.shipmethod.as.has.stowage.plan";

	/** 尚有未发运的出库单，不能关闭该配载计划。请全部发运完成以后再试一次。 */
	public static final String CANNOT_CLOSE_STOWAGE_AS_HAS_OBD_DETAIL_TODO = "cannot.close.stowage.as.has.obd.detail.todo";
	
	/** 箱号不能重复。 */
	public static final String SAME_OUTBOUND_PACKAGE_BOXSEQUENCE = "same.outbound.package.boxsequence";

	// //////////////////////////盘点////////////////////////////////////////////
	/** 找不到盘点对象！ */
	public static final String NO_COUNT_RECORD = "no.count.record";

	/** 找不到空的盘点库位。 */
	public static final String COUNT_BIN_NOT_FIND = "count.bin.not.find";

	/** 该盘点单已经关闭或者取消，不能做盘点登记。 */
	public static final String CLOSED_COUNT_PLAN = "closed.count.plan";

	/** 从商品盘不能做盘点登记。 */
	public static final String CANNOT_COUNT_REGISTER_AS_FROM_SKU = "cannot.count.register.as.from.sku";

	/** 库位不在盘点计划中。{0} */
	public static final String NOTIN_COUNT_PLAN_BIN = "notin.count.plan.bin";

	/** 请输入动碰盘点的期间范围。 */
	public static final String DYNAMIC_DATE_REQUIRED = "dyanmic.date.required";

	/** 请清空盘点单明细后再使用全部添加存货库位功能。 */
	public static final String CANNOT_ADD_ALL_AS_HAS_COUNT_DETAILS = "cannot.add.all.as.has.count.details";

	/** 找不到满足条件的库位。 */
	public static final String CANNOT_FIND_BIN_FOR_COUNT = "cannot.find.bin.for.count";

	// ///////////////////////////////辅料////////////////////////////////////////////////
	/** 出库数量超过库存可用数量。 */
	public static final String CANNOT_ACCESSORY_AS_MORE_THAN_AVAILABLE_QTY = "cannot.accessory.as.more.than.available.qty";

	// ///////////////////////////////计费统计////////////////////////////////////////////////
	/** 作业量合计超过了总作业量！ */
	public static final String MORE_THAN_TOTAL_WORKLOAD = "more.than.total.workload";

	/** 作业量已结算，不能修改！ */
	public static final String CLOSED_LABOR_KPI = "closed.labor.kpi";

	/** 所占比例合计小于100。 */
	public static final String LESS_THAN_100_PERCENT = "less.than.100.percent";

	// ///////////////////////////////破损////////////////////////////////////////////////
	/** 请选择关联的入库单。 */
	public static final String INPUT_INBOUND_DELIVERY = "input.inbound.delivery";

	/** 找不到对应的收货记录。商品:{0},批次属性:{1},批次说明:{2}。 */
	public static final String CANNOT_FIND_SKU_INBOUND = "cannot.find.sku.inbound";

	/** 找不到对应的发货记录。商品:{0},批次属性:{1},批次说明:{2}。 */
	public static final String CANNOT_FIND_SKU_OUTBOUND = "cannot.find.sku.outbound";

	/** 破损申请数量超过收货数量。 */
	public static final String CANNOT_SCRAP_AS_MORE_THAN_INBOUND_QTY = "cannot.scrap.as.more.than.inbound.qty";

	/** 破损申请数量超过发货数量。 */
	public static final String CANNOT_SCRAP_AS_MORE_THAN_OUTBOUND_QTY = "cannot.scrap.as.more.than.outbound.qty";

	// ///////////////////////////////配载////////////////////////////////////////////////
	/** 该配载计划已经被导入。请删除后可以从新导入。 */
	public static final String CANNOT_LOAD_AS_HAS_OLD_STOWAGE_PLAN = "cannot.load.as.has.old.stowange.plan";

	/** 找到该配载单号相关的配载信息。 */
	public static final String CANNOT_FIND_STOWAGE_PLAN = "cannot.find.stowage.plan";

	// ///////////////////////////////WT////////////////////////////////////////////////
	/** 已经加入作业单(WO)不能取消该作业任务(WT)! */
	public static final String CANNOT_CANCEL_TASK_AS_PLANED = "cannot.cancel.task.as.planed";
	
	
	// ///////////////////////////////PACK////////////////////////////////////////////////
	/** 不同发运方式的出库单不能混合装箱。 */
	public static final String CANNOT_PACK_AS_DIF_SHIP_METHOD = "cannot.pack.as.dif.ship.method";

	// ///////////////////////////////基础数据///////////////////////////////////////////

	/** 库位{0}已经加入库位组，无法删除。 */
	public static final String CANNOT_DELETE_BIN_AS_ADD_TO_BINGROUP = "cannot.delete.bin.as.add.to.bingroup";

	/** 库位{0}有关联的策略，无法删除。 */
	public static final String CANNOT_DELETE_BIN_AS_ADD_RULE = "cannot.delete.bin.as.add.rule";

	/** 库位{0}已经有业务记录，无法删除。 */
	public static final String CANNOT_DELETE_BIN_AS_HAS_HISTORY = "cannot.delete.bin.as.has.history";
	
	/** 电子标签库位，无法删除。 */
	public static final String CANNOT_DELETE_BIN_AS_DPS = "cannot.delete.bin.as.dps";
	
	//////////////////////////////EDI////////////////////////////////////////////////
	/** 该仓库不存在。*/
	public static final String CANNOT_FIND_WH = "cannot.find.wh";
	
	/** {0}编号{1}的基础资料信息不存在 。*/
	public static final String CANNOT_FIND = "cannot.find";
	
	/** 该用户不存在。*/
	public static final String CANNOT_FIND_USER = "cannot.find.user";

	/** 密码不能为空。*/
	public static final String EMPTY_PASSWORD = "empty.password";
	
	/** 密码错误。*/
	public static final String WRONG_PASSWORD = "wrong.password";
	
	/** 该用户帐号已经被锁。*/
	public static final String LOACKED_USER = "locked.user";
	
	/** 该用户帐号已经失效。*/
	public static final String DISABLED_USER = "disabled.user";
	
	/** 该用户帐号已经过了有效期。*/
	public static final String EXPIRED_USER = "expired.user";
	
	/** 该密码已经过了有效期。*/
	public static final String EXPIRED_PASSWORD = "expired.password";
	
	/** 该用户没有仓库权限。*/
	public static final String CANNOT_FIND_USERGROUP_WH = "cannot.find.usergroup.wh";
	
	/** 该用户没有绑定的作业人员信息。*/
	public static final String CANNOT_FIND_LABOR = "cannot.find.labor";
	
	/** 找不到逻辑库位属性。*/
	public static final String CANNOT_FIND_LOGIC_BIN_PROPERTIES = "cannot.find.logic.bin.properties";

	public static final String SYSTEM_EXCEPTION = "catch.exeption.unexpected";
	
	/** 予定数を超えました。*/
	public static final String OVER_PLANQTY = "over.planqty";
	
	/** 找不到满足条件的数据 */
	public static final String CANNOT_FIND_DATA = "cannot.find.data";
}
