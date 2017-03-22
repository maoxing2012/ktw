package com.core.scpwms.client.custom.page.picking;

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
public class PickingUIFactory implements IsSerializable {

	// 1.ID(隐)
	// 2.客户单号
	// 3.行号
	// 4.状态
	// 5.货主编号(隐)
	// 6.货主名称
	// 7.商品编码
	// 8.商品名称
	// 9.批次属性
	// 10.批次说明(隐)
	// 11.包装
	// 12.预期数
	// 13.预期数(EA)
	// 14.分配数(EA)
	// 15.拣货数(EA)
	// 16.发货数(EA)
	public static int[] DOC_DETAIL_DS_WIDTH = new int[] { 
			50, 100, 50, 100,
			80, 100, 100, 200, 
			100, 100, 100, 100, 
			100, 100, 50, 50  };

	public static Boolean[] DOC_DETAIL_DS_VISABLE = new Boolean[] { 
			false, true, true, true, 
			false, true, true, true, 
			true, false, true, false,
			true, true, false, false };

	// 1.ID(隐)
	// 2.库位编号
	// 3.货主编号(隐)
	// 4.货主名称(隐)
	// 5.商品编码
	// 6.商品名称
	// 7.批次号(隐)
	// 8.批次属性
	// 9.批次说明(隐)
	// 10.库存状态
	// 11.入库日期
	// 12.包装
	// 13.数量(隐)
	// 14.数量(EA)
	// 15.分配数(EA)
	// 16.托盘号(隐)
	// 17.容器号(隐)
	public static int[] INVENTORYDETAILDS_WIDTH = new int[] { 
			50, 100, 80, 100, 
			100, 200, 100, 100, 
			100, 100, 100, 100, 
			100, 100, 100, 100, 100 };

	public static Boolean[] INVENTORYDETAILDS_VISABLE = new Boolean[] { 
			false, true, false, false, 
			true, true, false, true, 
			false, true, true, true,
			false, true, true, false, false };

	// 1.ID(隐)
	// 2.作业号(隐)
	// 3.状态(隐)
	// 4.货主编号(隐)
	// 5.货主名称(隐)
	// 6.商品编号
	// 7.商品名称
	// 8.批次号(隐)
	// 9.批次属性
	// 10.批次说明(隐)
	// 11.库存状态
	// 12.入库日期(隐)
	// 13.包装
	// 14.计划拣选数(隐)
	// 15.计划拣选数(EA)
	// 16.拣选库位
	// 17.拣选托盘号(隐)
	// 18.拣选容器号(隐)
	// 19.目标库位
	// 20.目标托盘号(隐)
	// 21.目标容器号(隐)
	public static int[] PICK_WT_WIDTH = new int[] { 
			50, 110, 80, 80, 100,
			100, 200, 100, 100, 100, 
			100, 100, 80, 100, 100, 
			100, 100, 100, 100, 100, 100};

	public static Boolean[] PICK_WTDS_VISABLE = new Boolean[] { 
		    false, false, false, false, false,
		    true, true, false, true, false, 
		    true, false, true, false, true, 
		    true, false, false , false, false , false };

	// 初始化
	public static List<ListGridField> initPickingDocDetailDS() {
		List<ListGridField> fields = new ArrayList<ListGridField>();
		for (int i = 0; i < DOC_DETAIL_DS_WIDTH.length; i++) {

			ListGridField col = new ListGridField("ObdDocDetailDS_" + i,
					LocaleUtils.getText("ObdDocDetailDS_" + i));
			col.setWidth(DOC_DETAIL_DS_WIDTH[i]);
			col.setCanEdit(false);
			col.setCanFilter(false);
			col.setAlign(Alignment.LEFT);
			col.setHidden(!DOC_DETAIL_DS_VISABLE[i]);
			fields.add(col);
		}

		return fields;
	}

	// 初始化
	public static List<ListGridField> initInventoryDS() {
		List<ListGridField> fields = new ArrayList<ListGridField>();
		for (int i = 0; i < INVENTORYDETAILDS_WIDTH.length; i++) {
			ListGridField col = new ListGridField("InventoryDetailDS" + i,
					LocaleUtils.getText("InventoryDetailDS" + i));
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
		for (int i = 0; i < PICK_WT_WIDTH.length; i++) {
			ListGridField col = new ListGridField("PickTaskDS_" + i,
					LocaleUtils.getText("PickTaskDS_" + i));
			col.setWidth(PICK_WT_WIDTH[i]);
			col.setCanEdit(false);
			col.setCanFilter(false);
			col.setAlign(Alignment.LEFT);
			col.setHidden(!PICK_WTDS_VISABLE[i]);
			fields.add(col);
		}

		return fields;
	}

}
