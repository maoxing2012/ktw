package com.core.scpwms.client.custom.page.allocate;

import java.util.ArrayList;
import java.util.List;

import com.core.scpview.client.utils.LocaleUtils;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.grid.ListGridField;

/**
 * 定义画面相关按钮，并初始化右侧相关数据显示样式
 * 
 * @author zhaodaqun
 * */
public class DispatchUIFactory implements IsSerializable {

	// 1.ID（隐）
	// 2.货主编号（隐）
	// 3.货主名称（隐）
	// 4.商品编号
	// 5.商品名称
	// 6.批次属性
	// 7.批次说明（隐）
	// 8.库存状态
	// 9.包装
	// 10.计划上架数量
	// 11.计划上架数量(EA)
	// 12.分配数(EA)
	// 13.上架数(EA)
	// 14.原库位
	// 15.原托盘号（隐）
	// 16.原容器号（隐）
	public static int[] PUTAWAYDOCDETAILDS_WIDTH = new int[] { 
		50,  80, 80, 100, 200, 100, 
		100, 100, 100, 100, 100, 
		100, 100, 100, 100 };

	public static Boolean[] PUTAWAYDOCDETAILDS_VISABLE = new Boolean[] { 
		false, false, false, true, true, true, false, 
		true, true, true, true, true, true,true,
		false, false };

	// 1.ID（隐）
	// 2.库位编号
	// 3.货主编号（隐）
	// 4.货主名称（隐）
	// 5.商品编码
	// 6.商品名称
	// 7.批次号（隐）
	// 8.批次属性
	// 9.批次说明
	// 10.库存状态
	// 11.入库日期
	// 12.包装
	// 13.数量
	// 14.数量(EA)
	// 15.分配数(EA)
	// 16.托盘号（隐）
	// 17.容器号（隐）
	// 18.库位ID（隐）
	public static int[] INVENTORYDETAILDS_WIDTH = new int[] { 50, 100, 80, 100,
			100, 200, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
			100 };

	public static Boolean[] INVENTORYDETAILDS_VISABLE = new Boolean[] { false,
			true, false, false, true, true, false, true, false, true, true, true, true,
			true, true, false, false, false };

	// 1.ID（隐）
	// 2.作业号
	// 3.状态
	// 4.货主编号（隐）
	// 5.货主名称（隐）
	// 6.商品编号
	// 7.商品名称
	// 8.批次属性
	// 9.批次说明（隐）
	// 10.库存状态
	// 11.包装
	// 12.计划上架数
	// 13.计划上架数(EA)
	// 14.已上架数（隐）
	// 15.已上架数(EA)
	// 16.目标库位
	// 17.目标托盘号（隐）
	// 18.目标容器号（隐）
	// 19.原库位
	// 20.原托盘号（隐）
	// 21.原容器号（隐）
	public static int[] WT_WIDTH = new int[] { 
		50,  100, 100, 80, 100, 100, 200, 100,
		100, 100, 100, 100, 100, 100, 
		100, 100, 100, 100, 100, 100, 
		100 };

	public static Boolean[] WTDS_VISABLE = new Boolean[] { 
		false, true, true, false, false, true, true, true, 
		false, true, true, true, true, false, 
		true, true, false, false,true, false, 
		false };

	public static int[] MOVEPLANDOCDETAILDS_WIDTH = new int[] { 50, 100, 150,
			100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100 };

	public static Boolean[] MOVEPLANDOCDETAILDS_VISABLE = new Boolean[] {
			false, true, true, true, true, true, true, true, true, true, true,
			true, true, true, true };

	// 初始化
	public static List<ListGridField> initPutawayDocDetailDS() {
		List<ListGridField> fields = new ArrayList<ListGridField>();
		for (int i = 0; i < PUTAWAYDOCDETAILDS_WIDTH.length; i++) {

			ListGridField col = new ListGridField("PutawayDocDetailDS" + i,LocaleUtils.getText("PutawayDocDetailDS" + i));
			col.setWidth(PUTAWAYDOCDETAILDS_WIDTH[i]);
			col.setCanEdit(false);
			col.setCanFilter(false);
			col.setAlign(Alignment.LEFT);
			col.setHidden(!PUTAWAYDOCDETAILDS_VISABLE[i]);
			fields.add(col);
		}

		return fields;
	}

	// 初始化
	public static List<ListGridField> initInventoryDS() {
		List<ListGridField> fields = new ArrayList<ListGridField>();
		for (int i = 0; i < INVENTORYDETAILDS_WIDTH.length; i++) {
			ListGridField col = new ListGridField("InventoryDetailDS" + i,LocaleUtils.getText("InventoryDetailDS" + i));
			col.setWidth(INVENTORYDETAILDS_WIDTH[i]);
			col.setCanEdit(false);
			col.setCanFilter(false);
			col.setAlign(Alignment.LEFT);
			col.setHidden(!INVENTORYDETAILDS_VISABLE[i]);
			fields.add(col);
		}

		return fields;
	}

	// 初始化
	public static List<ListGridField> initWtDS() {
		List<ListGridField> fields = new ArrayList<ListGridField>();
		for (int i = 0; i < WT_WIDTH.length; i++) {
			ListGridField col = new ListGridField("WarehouseTaskDS" + i,LocaleUtils.getText("WarehouseTaskDS" + i));
			col.setWidth(WT_WIDTH[i]);
			col.setCanEdit(false);
			col.setCanFilter(false);
			col.setAlign(Alignment.LEFT);
			col.setHidden(!WTDS_VISABLE[i]);
			fields.add(col);
		}

		return fields;
	}

	// 初始化
	public static List<ListGridField> initMovePlanDocDetailDS() {
		List<ListGridField> fields = new ArrayList<ListGridField>();
		for (int i = 0; i < MOVEPLANDOCDETAILDS_WIDTH.length; i++) {

			ListGridField col = new ListGridField("MovePlanDocDetailDS" + i,LocaleUtils.getText("MovePlanDocDetailDS" + i));
			col.setWidth(MOVEPLANDOCDETAILDS_WIDTH[i]);
			col.setCanEdit(false);
			col.setCanFilter(false);
			col.setAlign(Alignment.LEFT);
			col.setHidden(!MOVEPLANDOCDETAILDS_VISABLE[i]);
			fields.add(col);
		}

		return fields;
	}
}
