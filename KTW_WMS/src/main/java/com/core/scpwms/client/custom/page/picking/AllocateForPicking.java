/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件名   : ManualAllocateEdit.java
 */

package com.core.scpwms.client.custom.page.picking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.scpview.client.config.ui.SelectTextUIConfig;
import com.core.scpview.client.config.ui.TextUIConfig;
import com.core.scpview.client.engine.BaseDataAccess;
import com.core.scpview.client.engine.IMessagePage;
import com.core.scpview.client.ui.bizcontainer.AbstractCustomCanvasWithCallBack;
import com.core.scpview.client.ui.commonWidgets.ButtonUI;
import com.core.scpview.client.ui.element.NumberTextUI;
import com.core.scpview.client.ui.element.SelectTextUI;
import com.core.scpview.client.ui.element.TextFieldUI;
import com.core.scpview.client.utils.LocaleUtils;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpview.client.utils.ValidateUtil;
import com.core.scpview.client.validate.RequiredValidator;
import com.core.scpwms.client.custom.page.support.PageUIFactory;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * @description 手工分配库位(拣选) 针对出库库存分配、移位库存分配 使用 [businessType]进行区分、 0 = 出库库存分配 1 =
 *              移位库存分配 此处为未来扩展点 (加工、质检抽检等)
 * @author MBP:Alex<br/>
 * @createDate 2013-2-28
 * @version V1.0<br/>
 */
@SuppressWarnings("all")
public class AllocateForPicking extends AbstractCustomCanvasWithCallBack
		implements IMessagePage {

	protected transient VLayout vlayout;

	protected transient HLayout body;

	protected transient VLayout bodyLeft;
	protected transient HLayout bodyLeft_form;
	protected transient HLayout bodyLeft_qry_form;
	protected transient HLayout bodyLeft_buttons;
	protected transient HLayout bodyLeft_qry_buttons;

	public transient SectionStack sectionStackLeft;
	public transient SectionStackSection sectionLet;

	public transient SectionStack sectionStack;

	public transient SectionStackSection sectionTop;
	public transient ListGrid baseGrid;
	public transient List<ListGridField> fields1;
	public transient ObdDocDetailDS docDetailDS;

	public transient SectionStackSection sectionMid;
	public transient ListGrid invGrid;
	public transient List<ListGridField> fields2;
	public transient PickingInventoryDetailDS invDetailDS;

	public transient SectionStackSection sectionDown;
	public transient ListGrid taskGrid;
	public transient List<ListGridField> fields3;
	public transient PickingTaskDS taskDetailDS;

	// 分配区
	protected transient DynamicForm allocateForm;
	protected transient DynamicForm queryForm;

	// 上架计划信息区（头）
	protected transient SelectTextUI docSequence;
	protected transient TextFieldUI lineId;
	protected transient TextFieldUI invId;
	protected transient TextFieldUI taskId;

	// 货主
	//protected transient TextFieldUI ownerName;
	// 商品编号
	protected transient TextFieldUI skuCode;
	// 商品名称
	protected transient TextFieldUI skuName;
	// 拣货库位
	protected transient TextFieldUI bin;
	// 库存状态
	protected transient TextFieldUI invStatus;
	// 批次属性
	protected transient TextFieldUI lotInfo;
	// 批次说明（工程号）
	//protected transient TextFieldUI projectNo;
	// 包装
	protected transient TextFieldUI packDetail;
	// 分配数
	protected transient NumberTextUI allocateQuantity;

	protected transient ButtonUI allocateBtn;
	protected transient ButtonUI cancelBtn;
	protected transient ButtonUI queryBtn;
	protected transient ButtonUI resetBtn;
	protected transient BaseDataAccess dataAccess;

	protected transient Long businessType;

	@Override
	public void initPage() {

		businessType = 0L;
		//
		// if ("waveAllocate".equalsIgnoreCase(this.getPageConfig().getId())) {
		// businessType = 0L;
		// } else if ("moveAllocate"
		// .equalsIgnoreCase(this.getPageConfig().getId())) {
		// businessType = 1L;
		// }
		initContainer();
	}

	/** 初始化主画面 */
	public void initContainer() {
		contentCanvas.setWidth100();
		contentCanvas.setHeight100();
		contentCanvas.setOverflow(Overflow.HIDDEN);
		contentCanvas.setMargin(6);

		vlayout = new VLayout();
		vlayout.setMargin(0);
		vlayout.setMembersMargin(0);
		vlayout.setWidth100();
		vlayout.setHeight100();

		bodyLeft = new VLayout();
		bodyLeft.setHeight100();
		bodyLeft.setWidth(165);
		bodyLeft.setOverflow(Overflow.VISIBLE);
		bodyLeft.setShowResizeBar(false);

		sectionStackLeft = new SectionStack();
		sectionStackLeft.setHeight100();
		sectionStackLeft.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStackLeft.setAnimateSections(false);
		sectionStackLeft.setOverflow(Overflow.HIDDEN);

		sectionLet = new SectionStackSection();
		sectionLet.setCanCollapse(false);
		sectionLet.setExpanded(true);
		sectionLet.setShowHeader(true);
		sectionLet.setTitle(LocaleUtils.getText("allocate.pick.title"));

		initBodyLeft();

		sectionStackLeft.setSections(sectionLet);
		bodyLeft.addMember(sectionStackLeft);

		body = new HLayout();
		body.setWidth100();
		body.setHeight100();
		body.setMembersMargin(2);

		body.addMember(bodyLeft);
		initBodyRight();
		body.addMember(sectionStack);

		vlayout.addMember(body);
		contentCanvas.addChild(vlayout);
	}

	public void initBodyRight() {
		sectionStack = new SectionStack();
		sectionStack.setHeight100();
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setAnimateSections(false);
		sectionStack.setOverflow(Overflow.HIDDEN);

		initSectionTop();
		initSectionMid();
		initSectionDown();

		sectionStack.setSections(sectionTop, sectionMid, sectionDown);
	}

	public void initSectionDown() {
		sectionDown = new SectionStackSection();
		sectionDown.setCanCollapse(false);
		sectionDown.setExpanded(true);
		sectionDown.setShowHeader(true);
		sectionDown.setTitle(LocaleUtils.getText("table.warehousetask"));

		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setMargin(0);
		layout.setMembersMargin(0);
		initWtGrid();
		layout.addMember(taskGrid);
		sectionDown.addItem(layout);
	}

	public void initWtGrid() {
		taskGrid = new ListGrid() {
		};
		taskGrid.setShowAllRecords(true);
		taskGrid.setSelectionType(SelectionStyle.SINGLE);
		taskGrid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);

		// taskGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
		// @Override
		// public void onSelectionChanged(SelectionEvent event) {
		// resetTaskInfoBySelectRow();
		// }
		// });

		taskGrid.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				resetTaskInfoBySelectRow();
			}
		});

		// 设置标题栏属性
		taskGrid.setCanAddFormulaFields(false);
		taskGrid.setCanFreezeFields(false);
		taskGrid.setCanGroupBy(false);
		taskGrid.setCanAutoFitFields(false);
		taskGrid.setCanMultiSort(false);
		// 设置当前页面表格的数据源
		taskDetailDS = PickingTaskDS.getInstance();
		taskGrid.setDataSource(taskDetailDS);
		taskDetailDS.invalidateCache();

		fields3 = PickingUIFactory.initWtDS();
		ListGridField[] allFields1 = new ListGridField[fields3.size()];
		taskGrid.setFields(fields3.toArray(allFields1));

		taskGrid.setShowGroupSummary(false);
		taskGrid.setShowGroupSummaryInHeader(false);

		taskGrid.setEmptyMessage(LocaleUtils.getText("data.empty"));

	}

	public void initSectionMid() {
		sectionMid = new SectionStackSection();
		sectionMid.setCanCollapse(false);
		sectionMid.setExpanded(true);
		sectionMid.setShowHeader(true);
		sectionMid.setTitle(LocaleUtils.getText("table.inventory"));

		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setMargin(0);
		layout.setMembersMargin(0);
		initInventoryGrid();
		layout.addMember(invGrid);

		sectionMid.addItem(layout);
	}

	public void initInventoryGrid() {

		invGrid = new ListGrid() {
		};
		invGrid.setShowAllRecords(true);
		invGrid.setSelectionType(SelectionStyle.SINGLE);
		invGrid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);

		// invGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
		// @Override
		// public void onSelectionChanged(SelectionEvent event) {
		// resetInvInfoBySelectRow();
		// }
		// });

		invGrid.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				resetInvInfoBySelectRow();
			}
		});

		// 设置标题栏属性
		invGrid.setCanAddFormulaFields(false);
		invGrid.setCanFreezeFields(false);
		invGrid.setCanGroupBy(false);
		invGrid.setCanAutoFitFields(false);
		invGrid.setCanMultiSort(false);
		// 设置当前页面表格的数据源
		invDetailDS = PickingInventoryDetailDS.getInstance();
		invGrid.setDataSource(invDetailDS);
		fields2 = PickingUIFactory.initInventoryDS();
		invGrid.setAutoFetchData(true);
		ListGridField[] allFields1 = new ListGridField[fields2.size()];
		invGrid.setFields(fields2.toArray(allFields1));

		invGrid.setShowGroupSummary(false);
		invGrid.setShowGroupSummaryInHeader(false);

		invGrid.setEmptyMessage(LocaleUtils.getText("data.empty"));
	}

	public void initSectionTop() {
		sectionTop = new SectionStackSection();
		sectionTop.setCanCollapse(false);
		sectionTop.setExpanded(true);
		sectionTop.setShowHeader(true);
		sectionTop.setTitle(LocaleUtils
				.getText("allocateForPicking.table.allocate"));

		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.setMargin(0);
		layout.setMembersMargin(0);
		initGridTop();
		layout.addMember(baseGrid);

		sectionTop.addItem(layout);
	}

	/**
     * 
     */
	public void initGridTop() {
		baseGrid = new ListGrid() {
		};
		baseGrid.setShowAllRecords(true);
		baseGrid.setSelectionType(SelectionStyle.SINGLE);
		baseGrid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);

		baseGrid.addRecordClickHandler(new RecordClickHandler() {
			@Override
			public void onRecordClick(RecordClickEvent event) {
				Record source = event.getRecord();
				queryInvByDetailId(source
						.getAttributeAsLong("ObdDocDetailDS_0"));
			}
		});

		// baseGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
		// @Override
		// public void onSelectionChanged(SelectionEvent event) {
		// resetDocInfoBySelectRow();
		// }
		// });

		// 设置标题栏属性
		baseGrid.setCanAddFormulaFields(false);
		baseGrid.setCanFreezeFields(false);
		baseGrid.setCanGroupBy(false);
		baseGrid.setCanAutoFitFields(false);
		baseGrid.setCanMultiSort(false);
		// 设置当前页面表格的数据源
		docDetailDS = ObdDocDetailDS.getInstance();
		baseGrid.setDataSource(docDetailDS);
		baseGrid.setAutoFetchData(true);
		fields1 = PickingUIFactory.initPickingDocDetailDS();
		ListGridField[] allFields1 = new ListGridField[fields1.size()];
		baseGrid.setFields(fields1.toArray(allFields1));

		baseGrid.setShowGroupSummary(false);
		baseGrid.setShowGroupSummaryInHeader(false);

		baseGrid.setEmptyMessage(LocaleUtils.getText("data.empty"));
	}

	public void initBodyLeft() {

		VLayout inSectionLayout = new VLayout();
		inSectionLayout.setHeight100();
		inSectionLayout.setWidth100();

		bodyLeft_form = new HLayout();
		bodyLeft_form.setMargin(2);
		bodyLeft_form.setMembersMargin(2);
		bodyLeft_form.setAutoHeight();
		bodyLeft_form.setWidth100();

		bodyLeft_buttons = new HLayout();
		bodyLeft_buttons.setMargin(5);
		bodyLeft_buttons.setMembersMargin(5);
		bodyLeft_buttons.setAutoHeight();
		bodyLeft_buttons.setWidth100();

		bodyLeft_qry_form = new HLayout();
		bodyLeft_qry_form.setMargin(2);
		bodyLeft_qry_form.setMembersMargin(2);
		bodyLeft_qry_form.setAutoHeight();
		bodyLeft_qry_form.setWidth100();

		bodyLeft_qry_buttons = new HLayout();
		bodyLeft_qry_buttons.setMargin(5);
		bodyLeft_qry_buttons.setMembersMargin(5);
		bodyLeft_qry_buttons.setAutoHeight();
		bodyLeft_qry_buttons.setWidth100();

		queryForm = new DynamicForm();
		queryForm.setNumCols(1);
		queryForm.setMargin(5);
		queryForm.setTitleOrientation(TitleOrientation.TOP);
		queryForm.setAutoFocus(false);

		StringBuffer hsql = new StringBuffer();
		String dispHead = "";

		// 初始化单据选择控件，根据businessType进行筛选
		if (businessType == 0L) {
			hsql.append("select obd.id,obd.obdNumber,obd.relatedBill1 "
					+ "from OutboundDelivery obd where obd.wh=#{SESSION_WAREHOUSE} "
					+ "and obd.status in (210,310,400)");// 加入波次，部分分配，分配完成
			dispHead = LocaleUtils.getTextWithoutException("id") + ","
					+ LocaleUtils.getTextWithoutException("obdNumber") + ","
					+ LocaleUtils.getTextWithoutException("custBillNo");
			docSequence = PageUIFactory.makeSkuRemoteUI("obd.id", "obdAllocate", this, hsql.toString(), dispHead, "obdNumber",null);
			docSequence.setDisplayColumn(2);
			docSequence.setRequired(true);
			docSequence.setValidator(new RequiredValidator());
			docSequence.setListenerContexts(new ArrayList());

			SelectTextUIConfig docSequenceConfig = new SelectTextUIConfig();
			TextUIConfig tuc1 = new TextUIConfig();
			tuc1.setId("obd.id");
			tuc1.setTitle(LocaleUtils.getTextWithoutException("obdNumber"));
			docSequenceConfig.getInputUIConfigs().add(tuc1);

			// TextUIConfig tuc2 = new TextUIConfig();
			// tuc2.setId("wave.planQty");
			// tuc2.setTitle(LocaleUtils.getTextWithoutException("planQty"));
			// docSequenceConfig.getInputUIConfigs().add(tuc2);
			docSequence.setSelectTextUIConfig(docSequenceConfig);
			docSequence.setReadOnly(false);
			docSequence.setCallBackPage(this);
			docSequence.afterPropertySet();
			queryForm.setFields(docSequence.getInputFormItem());

		} else if (businessType == 1L) {
			// SUINAN DO
			hsql.append("select moveDoc.id,moveDoc.docSequence,moveDoc.plant.name from InvApplyDoc moveDoc where moveDoc.warehouse=#{SESSION_WAREHOUSE} and moveDoc.status <= 310 and moveDoc.status >= 200");
			dispHead = LocaleUtils.getTextWithoutException("id")
					+ ","
					+ LocaleUtils
							.getTextWithoutException("moveDoc.docSequence")
					+ ","
					+ LocaleUtils.getTextWithoutException("moveDoc.plant.name");
			docSequence = PageUIFactory.makeSkuRemoteUI("moveDoc.id",
					"moveAllocate", this, hsql.toString(), dispHead,
					"moveDoc.docSequence", null);
			docSequence.setDisplayColumn(2);
			docSequence.setRequired(true);
			docSequence.setValidator(new RequiredValidator());
			docSequence.setListenerContexts(new ArrayList());

			SelectTextUIConfig docSequenceConfig = new SelectTextUIConfig();
			TextUIConfig tuc1 = new TextUIConfig();
			tuc1.setId("moveDoc.docSequence");
			tuc1.setTitle(LocaleUtils
					.getTextWithoutException("moveDoc.docSequence"));
			docSequenceConfig.getInputUIConfigs().add(tuc1);

			TextUIConfig tuc2 = new TextUIConfig();
			tuc2.setId("moveDoc.plant.name");
			tuc2.setTitle(LocaleUtils
					.getTextWithoutException("moveDoc.plant.name"));

			docSequenceConfig.getInputUIConfigs().add(tuc2);
			docSequence.setSelectTextUIConfig(docSequenceConfig);
			docSequence.setReadOnly(false);
			docSequence.setCallBackPage(this);
			docSequence.afterPropertySet();
			queryForm.setFields(docSequence.getInputFormItem());
		}

		bodyLeft_qry_form.addMember(queryForm);

		if (queryBtn == null) {
			queryBtn = new ButtonUI("search", "search", "search", "enter", null, "query") {
				public void onClick(ClickEvent event) {
					queryByDocCode();
				}
			};

			queryBtn.getButton().setWidth(80);
		}

		if (resetBtn == null) {
			resetBtn = new ButtonUI("clear", "clear", "clear", "enter", null,
					"reset") {
				public void onClick(ClickEvent event) {

					SC.confirm(
							LocaleUtils.getText("shippingdoc.reset.confirm"),
							new BooleanCallback() {
								@Override
								public void execute(Boolean value) {
									if (value) {
										resetAll();
									}
								}
							});
				}
			};

			resetBtn.getButton().setWidth(80);
		}

		bodyLeft_qry_buttons.setAlign(Alignment.RIGHT);
		bodyLeft_qry_buttons.setMembersMargin(5);
		bodyLeft_qry_buttons.addMember(queryBtn.getButton());
		bodyLeft_qry_buttons.addMember(resetBtn.getButton());

		allocateForm = new DynamicForm();
		allocateForm.setNumCols(1);
		allocateForm.setMargin(5);
		allocateForm.setTitleOrientation(TitleOrientation.TOP);
		allocateForm.setAutoFocus(false);

		lineId = new TextFieldUI();
		lineId.setReadOnly(true);
		lineId.afterPropertySet();
		lineId.getInputFormItem().setShowTitle(false);
		lineId.getInputFormItem().setVisible(false);

		invId = new TextFieldUI();
		invId.setReadOnly(true);
		invId.afterPropertySet();
		invId.getInputFormItem().setShowTitle(false);
		invId.getInputFormItem().setVisible(false);

		taskId = new TextFieldUI();
		taskId.setReadOnly(true);
		taskId.afterPropertySet();
		taskId.getInputFormItem().setShowTitle(false);
		taskId.getInputFormItem().setVisible(false);

//		ownerName = new TextFieldUI();
//		ownerName.setTitle("owner.name");
//		ownerName.setReadOnly(true);
//		ownerName.afterPropertySet();

		skuCode = new TextFieldUI();
		skuCode.setTitle("sku.code");
		skuCode.setReadOnly(true);
		skuCode.afterPropertySet();

		skuName = new TextFieldUI();
		skuName.setTitle("sku.name");
		skuName.setReadOnly(true);
		skuName.afterPropertySet();

		bin = new TextFieldUI();
		bin.setTitle("pick.bin");
		bin.setReadOnly(true);
		bin.afterPropertySet();

		invStatus = new TextFieldUI();
		invStatus.setTitle("invStatus");
		invStatus.setReadOnly(true);
		invStatus.afterPropertySet();

		lotInfo = new TextFieldUI();
		lotInfo.setTitle("lotInfo");
		lotInfo.setReadOnly(true);
		lotInfo.afterPropertySet();

//		projectNo = new TextFieldUI();
//		projectNo.setTitle("projectNo");
//		projectNo.setReadOnly(true);
//		projectNo.afterPropertySet();

		packDetail = new TextFieldUI();
		packDetail.setTitle("packDetail");
		packDetail.setReadOnly(true);
		packDetail.afterPropertySet();

		// palletNo = new TextFieldUI();
		// palletNo.setTitle("tablePallet");
		// palletNo.setReadOnly(true);
		// palletNo.afterPropertySet();
		//
		// containerNo = new TextFieldUI();
		// containerNo.setTitle("tableContainer");
		// containerNo.setReadOnly(true);
		// containerNo.afterPropertySet();

		allocateQuantity = new NumberTextUI();
		allocateQuantity.setTitle("allocate.quantity");
		allocateQuantity.setRequired(true);
		allocateQuantity.setReadOnly(false);
		allocateQuantity.afterPropertySet();

		allocateForm.setFields(lineId.getInputFormItem(),
				invId.getInputFormItem(), taskId.getInputFormItem(),
				skuCode.getInputFormItem(),
				skuName.getInputFormItem(), invStatus.getInputFormItem(),
				lotInfo.getInputFormItem(), 
				bin.getInputFormItem(), packDetail.getInputFormItem(),
				allocateQuantity.getInputFormItem());

		bodyLeft_form.addMember(allocateForm);

		// 确认分配
		if (allocateBtn == null) {
			allocateBtn = new ButtonUI("save", "allocate.ok", "save", "enter", null, "save") {
				@SuppressWarnings("unchecked")
				public void onClick(ClickEvent event) {
					allocateConfirm();
				}
			};
			allocateBtn.getButton().setWidth(80);
		}
		// 分配取消
		if (cancelBtn == null) {
			cancelBtn = new ButtonUI("cancel", "allocate.cancel", "cancel", "enter",
					null, "cancel") {
				@SuppressWarnings("unchecked")
				public void onClick(ClickEvent event) {
					allocateCancel();
				}
			};
			cancelBtn.getButton().setWidth(80);
		}

		bodyLeft_buttons.setAlign(Alignment.RIGHT);
		bodyLeft_buttons.setMembersMargin(5);
		bodyLeft_buttons.addMember(allocateBtn.getButton());
		bodyLeft_buttons.addMember(cancelBtn.getButton());

		inSectionLayout.addMember(bodyLeft_qry_form);
		inSectionLayout.addMember(bodyLeft_qry_buttons);
		inSectionLayout.addMember(bodyLeft_form);
		inSectionLayout.addMember(bodyLeft_buttons);

		sectionLet.setItems(inSectionLayout);

	}

	public void allocateCancel() {
		if (StringUtils.isEmpty(taskId.getText())) {
			SC.say(LocaleUtils.getText("allocateForPicking.cancel.error"));
			return;
		}

		SC.confirm(LocaleUtils.getText("allocateForPicking.cancel.confirm"),
				new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						if (value) {
							Map toServer = new HashMap();
							toServer.put(
									AllocateForPickingConstant.PARAM_DOC_TASK_ID,
									Long.valueOf(taskId.getText()));
							toServer.put(
									AllocateForPickingConstant.PARAM_DOC_TYPE,
									businessType);
							toServer.put(
									AllocateForPickingConstant.PARAM_DOC_ID,
									(Long) docSequence.getQueryValue());
							getData().doCancelAllocate(toServer);
						}
					}
				});

	}

	public void allocateConfirm() {

		if (StringUtils.isEmpty(lineId.getText())
				|| StringUtils.isEmpty(invId.getText())
				|| StringUtils.isEmpty((String) allocateQuantity.getValue())) {
			SC.say(LocaleUtils.getText("allocateForPicking.input.error"));
			return;
		}

		if (!ValidateUtil.isPositiveInteger((String) allocateQuantity
				.getValue())) {
			SC.say(LocaleUtils
					.getText("allocateForPicking.input.allocateQuantity"));
			return;
		}

		Map toServer = new HashMap();
		toServer.put(AllocateForPickingConstant.PARAM_DOC_DETAIL_ID,
				Long.valueOf(lineId.getText()));
		toServer.put(AllocateForPickingConstant.PARAM_DOC_INV_ID,
				Long.valueOf(invId.getText()));
		toServer.put(AllocateForPickingConstant.PARAM_ALLOCATE_QTY,
				allocateQuantity.getValue());
		toServer.put(AllocateForPickingConstant.PARAM_DOC_TYPE, businessType);
		toServer.put(AllocateForPickingConstant.PARAM_DOC_ID,
				(Long) docSequence.getQueryValue());
		getData().doAllocate(toServer);

	}

	@Override
	public void doDispath(String message) {
		if (AllocateForPickingConstant.MSG_INIT.equalsIgnoreCase(message)) {
			// 刷新表格信息
			fillDocTitle();
			fillDocDetail();
			fillDocTasks();
			clearInvList();
			cancelBtn.getButton().disable();
			allocateBtn.getButton().disable();
		} else if (AllocateForPickingConstant.MSG_RESET
				.equalsIgnoreCase(message)) {
			// 刷新所有信息
			resetAll();
		} else if (AllocateForPickingConstant.MSG_SEARCH_INV
				.equalsIgnoreCase(message)) {
			// 刷新库存
			fillInvList();
			cancelBtn.getButton().disable();
			allocateBtn.getButton().disable();
		} else if (AllocateForPickingConstant.MSG_ALLOCATE
				.equalsIgnoreCase(message)) {
			// 刷新头
			fillDocTitle();
			// 刷新单据明细和库存
			fillDocDetailAfterAllocate();
			// 刷新Task
			fillDocTasks();
			cancelBtn.getButton().disable();
			allocateBtn.getButton().enable();
		} else if (AllocateForPickingConstant.MSG_UNALLOCATE
				.equalsIgnoreCase(message)) {
			// 刷新头
			fillDocTitle();
			fillDocDetail();
			clearInvList();
			// 刷新Task
			fillDocTasks();
			cancelBtn.getButton().disable();
			allocateBtn.getButton().enable();

			resetInputExceptQuery();

		}
	}

	public void fillDocDetailAfterAllocate() {
		// 如果当前明细已经分派完毕，则清空库存表格
		ClientObdDocDetail detail = getData().getDetailByKey();
		if (detail == null) {
			// 未取得当前明细信息，所有相关都刷新
			fillDocDetail();
			clearInvList();
			resetInputExceptQuery();
		} else {

			if (getUnAllocateQty(detail.getPlanQty(), detail.getExecuteQty(),
					detail.getAllocateQty()) <= 0) {
				// 上一次处理的行全部分配结束，所有相关都刷新
				fillDocDetail();
				clearInvList();
				resetInputExceptQuery();
			} else {
				// 还有未分配信息，刷新当前行
				fillDocDetail();

				resetInput4Continue();
				// 刷新库存
				fillInvList();
			}
		}

		// 如果没有分派完毕，继续前处理（刷新库存表格）

	}

	public void fillInvList() {
		invGrid.setData(new ListGridRecord[0]);
		if (getData().getInvs() != null) {
			ListGridRecord[] records = genarateInvListRow(getData().getInvs());
			invDetailDS.setTestData(records);
			invGrid.fetchData();
		}
	}

	public void clearInvList() {
		invGrid.setData(new ListGridRecord[0]);
		invDetailDS.setTestData(new ListGridRecord[0]);
		invGrid.fetchData();
		getData().setInvs(null);
	}

	public ListGridRecord[] genarateInvListRow(List<ClientPickingInventory> invs) {
		ListGridRecord[] records = new ListGridRecord[invs.size()];
		for (int i = 0; i < invs.size(); i++) {
			ClientPickingInventory inv = invs.get(i);
			records[i] = inv.getRecord();
		}
		return records;
	}

	public void fillDocTitle() {
		if (getData().getDoc() != null) {
			sectionTop.setTitle(getData().getDoc().geneTitle());
			// descBin.setValue(getData().getDoc().getBinCode());
		} else {
			sectionTop.setTitle(LocaleUtils
					.getText("allocateForPicking.table.allocate"));
		}
	}

	public void fillDocTasks() {
		taskGrid.setData(new ListGridRecord[0]);
		if (getData().getTasks() != null) {
			ListGridRecord[] records = genarateDocTaskRow(getData().getTasks());
			taskDetailDS.setTestData(records);
			taskGrid.fetchData();
		}
	}

	public ListGridRecord[] genarateDocTaskRow(List<ClientPickingTask> tasks) {
		ListGridRecord[] records = new ListGridRecord[tasks.size()];
		for (int i = 0; i < tasks.size(); i++) {
			ClientPickingTask task = tasks.get(i);
			records[i] = task.getRecord();
		}
		return records;
	}

	public void fillDocDetail() {
		baseGrid.setData(new ListGridRecord[0]);
		if (getData().getDetails() != null) {
			ListGridRecord[] records = genarateDocDetailRow(getData()
					.getDetails());
			docDetailDS.setTestData(records);
			baseGrid.fetchData();
		}
	}

	public ListGridRecord[] genarateDocDetailRow(List<ClientObdDocDetail> details) {
		ListGridRecord[] records = new ListGridRecord[details.size()];
		for (int i = 0; i < details.size(); i++) {
			ClientObdDocDetail detail = details.get(i);
			records[i] = detail.getRecord(getData().getCurrentId());
		}
		return records;
	}

	@Override
	public AllocateForPickingDataAccess getData() {
		return (AllocateForPickingDataAccess) dataAccess;
	}

	@Override
	public List<String> getMessageKeys() {
		return new ArrayList<String>();
	}

	@Override
	public void initData() {
		dataAccess = new AllocateForPickingDataAccess(this);
		resetAll();
	}

	@Override
	public void callBackWithParam(Object obj, String uId) {
		queryByDocCode();
	}

	public void queryByDocCode() {
		if (docSequence.getValue() != null) {
			Map<String, Long> toServer = new HashMap();
			toServer.put(AllocateForPickingConstant.PARAM_DOC_ID,
					(Long) docSequence.getQueryValue());
			toServer.put(AllocateForPickingConstant.PARAM_DOC_TYPE,
					businessType);
			getData().searchByDocCode(toServer);
		} else {
			resetAll();
			return;
		}
	}

	public void queryInvByDetailId(Long detailId) {

		resetDocInfoBySelectRow();

		Map<String, Long> toServer = new HashMap();
		toServer.put(AllocateForPickingConstant.PARAM_DOC_DETAIL_ID, detailId);
		toServer.put(AllocateForPickingConstant.PARAM_DOC_TYPE, businessType);
		getData().queryInvByDetailId(toServer);
	}

	public void resetDocInfoBySelectRow() {
		ListGridRecord record = baseGrid.getSelectedRecord();

		if (record != null) {
			lineId.setValue(record.getAttribute("ObdDocDetailDS_0"));
			//ownerName.setValue(record.getAttribute("ObdDocDetailDS_5"));
			skuCode.setValue(record.getAttribute("ObdDocDetailDS_6"));
			skuName.setValue(record.getAttribute("ObdDocDetailDS_7"));
			lotInfo.setValue(record.getAttribute("ObdDocDetailDS_8"));
			//projectNo.setValue(record.getAttribute("ObdDocDetailDS_9"));
			packDetail.setValue(record.getAttribute("ObdDocDetailDS_10"));
			
			invStatus.resetValue();
			bin.resetValue();

			Double planQty = record.getAttributeAsDouble("ObdDocDetailDS_12");
			Double allocatedQty = record.getAttributeAsDouble("ObdDocDetailDS_13");
			allocateQuantity.setValue(planQty - allocatedQty);

			taskId.resetValue();
		}

		cancelBtn.getButton().disable();
		allocateBtn.getButton().disable();

	}

	public void resetInvInfoBySelectRow() {
		ListGridRecord record = invGrid.getSelectedRecord();

		if (record != null) {
			invId.setValue(record.getAttribute("InventoryDetailDS0"));
			bin.setValue(record.getAttribute("InventoryDetailDS1"));
			invStatus.setValue(record.getAttribute("InventoryDetailDS9"));

//			Double baseQty = record.getAttributeAsDouble("InventoryDetailDS12");
//			Double allocatedQty = record
//					.getAttributeAsDouble("InventoryDetailDS13") == null ? 0D
//					: record.getAttributeAsDouble("InventoryDetailDS13");
//			Double oldAllocateQty = allocateQuantity.getValue() == null ? 0D
//					: (Double) allocateQuantity.getValue();
//
//			if (oldAllocateQty > (baseQty - allocatedQty)) {
//				oldAllocateQty = baseQty - allocatedQty;
//			}
//
//			allocateQuantity.setValue(oldAllocateQty);
			taskId.resetValue();
		} 
		
		allocateBtn.getButton().enable();
		cancelBtn.getButton().disable();
	}

	public void resetTaskInfoBySelectRow() {
		ListGridRecord record = taskGrid.getSelectedRecord();
		if (record != null) {
			taskId.setValue(record.getAttribute("PickTaskDS_0"));

			//ownerName.setValue(record.getAttribute("PickTaskDS_4"));
			skuCode.setValue(record.getAttribute("PickTaskDS_5"));
			skuName.setValue(record.getAttribute("PickTaskDS_6"));
			lotInfo.setValue(record.getAttribute("PickTaskDS_8"));
			//projectNo.setValue(record.getAttribute("PickTaskDS_9"));
			invStatus.setValue(record.getAttribute("PickTaskDS_10"));
			packDetail.setValue(record.getAttribute("PickTaskDS_12"));
			allocateQuantity.setValue(record.getAttribute("PickTaskDS_13"));
			bin.setValue(record.getAttribute("PickTaskDS_15"));

			allocateBtn.getButton().disable();
			cancelBtn.getButton().enable();
		}
	}

	public void resetAll() {
		resetGrid();
		resetInputExceptQuery();
		resetInputQuery();

		cancelBtn.getButton().disable();
		allocateBtn.getButton().disable();

	}

	public void resetGrid() {
		sectionTop.setTitle(LocaleUtils.getText("allocateForPicking.table.allocate"));
		baseGrid.setData(new ListGridRecord[0]);
		docDetailDS.setTestData(new ListGridRecord[0]);
		baseGrid.fetchData();

		taskGrid.setData(new ListGridRecord[0]);
		taskDetailDS.setTestData(new ListGridRecord[0]);
		taskGrid.fetchData();

		invGrid.setData(new ListGridRecord[0]);
		invDetailDS.setTestData(new ListGridRecord[0]);
		invGrid.fetchData();
	}

	public void resetInputExceptQuery() {
		//ownerName.resetValue();
		skuCode.resetValue();
		skuName.resetValue();
		lotInfo.resetValue();
		//projectNo.resetValue();
		bin.resetValue();
		packDetail.resetValue();
		invStatus.resetValue();
		allocateQuantity.resetValue();

		lineId.resetValue();
		invId.resetValue();
		taskId.resetValue();
	}

	public void resetInputQuery() {
		docSequence.resetValue();
	}

	public void resetInput4Continue() {
		//ownerName.resetValue();
		skuCode.resetValue();
		skuName.resetValue();
		lotInfo.resetValue();
		//projectNo.resetValue();
		bin.resetValue();
		packDetail.resetValue();
		allocateQuantity.resetValue();
		invStatus.resetValue();

		invId.resetValue();
		taskId.resetValue();
	}

	public Double getUnAllocateQty(Object obj1, Object obj2, Object obj3) {
		try {
			return Double.parseDouble(String.valueOf(obj1))
					- Double.parseDouble(String.valueOf(obj2))
					- Double.parseDouble(String.valueOf(obj3));
		} catch (Exception ex) {
			return 0D;
		}
	}

}
