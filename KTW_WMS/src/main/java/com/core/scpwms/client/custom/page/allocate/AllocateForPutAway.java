/**
 * (C)2012 MBP Corporation. All rights reserved.
 * 系统名称 : MBP-WMS
 * 文件各1�7   : ManualAllocateEdit.java
 */

package com.core.scpwms.client.custom.page.allocate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.core.scpview.client.config.ui.SelectTextUIConfig;
import com.core.scpview.client.config.ui.TextUIConfig;
import com.core.scpview.client.engine.BaseDataAccess;
import com.core.scpview.client.engine.IMessagePage;
import com.core.scpview.client.ui.bizcontainer.AbstractCustomCanvasWithCallBack;
import com.core.scpview.client.ui.commonWidgets.ButtonUI;
import com.core.scpview.client.ui.element.SelectTextUI;
import com.core.scpview.client.ui.element.TextFieldUI;
import com.core.scpview.client.ui.table.RowData;
import com.core.scpview.client.utils.LocaleUtils;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpview.client.utils.ValidateUtil;
import com.core.scpview.client.validate.RequiredValidator;
import com.core.scpwms.client.custom.page.allocate.rcp.AllocationDs;
import com.core.scpwms.client.custom.page.support.ClientAsnUpdataDetail;
import com.core.scpwms.client.custom.page.support.ClientAsnUpdataDetailRecord;
import com.core.scpwms.client.custom.page.support.ClientBinDetailInfo;
import com.core.scpwms.client.custom.page.support.ClientBinDetailRecords;
import com.core.scpwms.client.custom.page.support.ClientWhTaskRecords;
import com.core.scpwms.client.custom.page.support.PageUIFactory;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * @description 手工分配库位
 * @author MBP:Alex<br/>
 * @createDate 2013-2-28
 * @version V1.0<br/>
 */
public class AllocateForPutAway extends AbstractCustomCanvasWithCallBack implements IMessagePage {

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
	protected transient PutawayDocDetailDS docDetailDS;

	public transient SectionStackSection sectionMid;
	public transient ListGrid invGrid;
	public transient List<ListGridField> fields2;
	protected transient InventoryDetailDS invDetailDS;

	public transient SectionStackSection sectionDown;
	public transient ListGrid taskGrid;
	public transient List<ListGridField> fields3;
	protected transient WarehouseTaskDS taskDetailDS;

	protected transient DynamicForm allocateForm;
	protected transient DynamicForm queryForm;

	// 上架计划信息区（头）
	protected transient SelectTextUI docSequence;
    protected transient TextFieldUI invId;
    protected transient TextFieldUI taskId;

	protected transient SelectTextUI binSelect;
	// 商品编号
	protected transient TextFieldUI skuCode;
	// 商品名称
	protected transient TextFieldUI skuName;
	// 批次属性
	protected transient TextFieldUI lotInfo;
	// 批次说明
	//protected transient TextFieldUI projectNo;
	// 库存状态
	protected transient TextFieldUI invStatus;
	// 包装
	protected transient TextFieldUI packList;
	
	protected transient LinkedHashMap<String, String> valueMap;

	protected transient TextFieldUI allocateQuantity;
	protected transient ButtonUI allocateBtn;
	protected transient ButtonUI cancelBtn;
	protected transient ButtonUI queryBtn;
	protected transient BaseDataAccess dataAccess;
	protected transient RecordList nullRds;
	protected transient ButtonUI resetBtn; 
	protected transient Map<String,String> invBinId=new HashMap<String, String>();
	
	@Override
	public void initPage() {
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
		sectionLet.setTitle(LocaleUtils.getText("allocate.title"));

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
		sectionDownTitleSetting(null,0,0);

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
		taskGrid.setSelectionType(SelectionStyle.SIMPLE);
		taskGrid.setSelectionAppearance(SelectionAppearance.ROW_STYLE);

		taskGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				resetTaskInfoBySelectRow();
			}
		});		

		// 设置标题栏
		taskGrid.setCanAddFormulaFields(false);
		taskGrid.setCanFreezeFields(false);
		taskGrid.setCanGroupBy(false);
		taskGrid.setCanAutoFitFields(false);
		taskGrid.setCanMultiSort(false);
		// 设置当前页面表格的数据源
		taskDetailDS = WarehouseTaskDS.getInstance();
		taskGrid.setDataSource(taskDetailDS);
		taskDetailDS.invalidateCache();

		fields3 = DispatchUIFactory.initWtDS();
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
		
		invGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				ListGridRecord selectRecord = invGrid.getSelectedRecord();
				if (selectRecord == null) {
					return;
				}
				String[] ls = selectRecord.getAttributes();
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < ls.length; i++) {
					list.add(selectRecord.getAttribute(ls[i]));
				}
				invId.setValue(selectRecord.getAttribute("InventoryDetailDS0"));
				list.remove(0);
				list.remove(0);
				list.set(0, invBinId.get(selectRecord.getAttributeAsObject("InventoryDetailDS0")));
				RowData rd = new RowData();
				rd.setColumnValues(list);
				binSelect.setValue(rd);
				taskId.resetValue();
				cancelBtn.getButton().disable();
				allocateBtn.getButton().enable();
			}
		});

		// 设置标题栏属
		invGrid.setCanAddFormulaFields(false);
		invGrid.setCanFreezeFields(false);
		invGrid.setCanGroupBy(false);
		invGrid.setCanAutoFitFields(false);
		invGrid.setCanMultiSort(false);
		// 设置当前页面表格的数据源
		invDetailDS = InventoryDetailDS.getInstance();
		invGrid.setDataSource(invDetailDS);
		fields2 = DispatchUIFactory.initInventoryDS();
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
		sectionTopTitleSetting(0,0);

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

		baseGrid.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord selectRecord = baseGrid.getSelectedRecord();
				if(selectRecord != null){
					String putUpId = selectRecord.getAttribute("PutawayDocDetailDS0");
					
					Map<String, String> params = newHashMap();
					AllocationDs allcotaionDs = (AllocationDs) dataAccess;
					params.put("docDetailId", putUpId);
					allcotaionDs.getBinDetail(params);
					
					Map<String, Object> pams = newHashMap();
					pams.put("docDetailId", putUpId);
					allcotaionDs.initTask(pams);
					
					String code = selectRecord.getAttribute("PutawayDocDetailDS3");
					String name = selectRecord.getAttribute("PutawayDocDetailDS4");
					String lotInfoStr = selectRecord.getAttribute("PutawayDocDetailDS5");
					String projectNoStr = selectRecord.getAttribute("PutawayDocDetailDS6");
					String invStatusStr = selectRecord.getAttribute("PutawayDocDetailDS7");
					String packageName = selectRecord.getAttribute("PutawayDocDetailDS8");
					String planQtyString = selectRecord.getAttribute("PutawayDocDetailDS10");
					String allocatedQtyString = selectRecord.getAttribute("PutawayDocDetailDS11");
					
					
					Double unAllocateQty = ( StringUtils.isEmpty(planQtyString) ? 0 : new Double(planQtyString) ) - (StringUtils.isEmpty(allocatedQtyString) ? 0 : new Double(allocatedQtyString));
					packList.setValue(packageName);
					skuCode.setValue(code);
					skuName.setValue(name);
					lotInfo.setValue(lotInfoStr);
					//projectNo.setValue(projectNoStr);
					invStatus.setValue(invStatusStr);
					allocateQuantity.setValue(unAllocateQty.toString());
					
					cancelBtn.getButton().disable();
					allocateBtn.getButton().enable();
				}
			}
		});
		
		baseGrid.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {
				// TODO
			}
		});

		// 设置标题栏属
		baseGrid.setCanAddFormulaFields(false);
		baseGrid.setCanFreezeFields(false);
		baseGrid.setCanGroupBy(false);
		baseGrid.setCanAutoFitFields(false);
		baseGrid.setCanMultiSort(false);
		
		// 设置当前页面表格的数据源
		docDetailDS = PutawayDocDetailDS.getInstance();
		baseGrid.setDataSource(docDetailDS);
		baseGrid.setAutoFetchData(true);
		fields1 = DispatchUIFactory.initPutawayDocDetailDS();
		ListGridField[] allFields1 = new ListGridField[fields1.size()];
		baseGrid.setFields(fields1.toArray(allFields1));

		baseGrid.setShowGroupSummary(false);
		baseGrid.setShowGroupSummaryInHeader(false);

		baseGrid.setEmptyMessage(LocaleUtils.getText("data.empty"));
	}

	public void removeRecords(ListGrid listGrid, DataSource resourec) {
		if (listGrid != null) {
			ListGridRecord[] loadedRds = listGrid.getRecords();
			if(loadedRds.length>0){
				listGrid.setData(nullRds);
//				resourec.setTestData(null);
				listGrid.fetchData();
			}
		}
	}
	public void queryputUpDetail(){
			if(docSequence.getValue()!=null && !docSequence.getValue().toString().trim().equals("")){
				String billSeq=docSequence.getValue().toString();
				Map<String ,Long> params=newHashMap();
				params.put("odd", Long.valueOf(billSeq));
				AllocationDs ads=(AllocationDs) getData();
				ads.getUpdataDetail(params);
			}else{
				sectionTopTitleSetting(0,0);
			}
	}

	public void initBodyLeft(){
		
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
		
		
		queryForm  = new DynamicForm();
		queryForm.setNumCols(1);
		queryForm.setMargin(5);
		queryForm.setTitleOrientation(TitleOrientation.TOP);
		queryForm.setAutoFocus(false);
		
		StringBuffer hsql = new StringBuffer("select pd.id,pd.docSequence,pd.planQty from PutawayDoc pd where pd.status in (200,210,300) ");
		hsql.append("and pd.wh = #{SESSION_WAREHOUSE}");
		
		String dispHead = LocaleUtils.getTextWithoutException("id") + ","+ LocaleUtils.getTextWithoutException("putawayNumber")+ 
		","+ LocaleUtils.getTextWithoutException("planPutawayQty");
		docSequence = PageUIFactory.makeSkuRemoteUI(
				"pd.id",
				"putawayDocAllocateList", 
				this, 
				hsql.toString(), 
				dispHead,
				"putawayPlan",
				null);
		
		docSequence.setDisplayColumn(2);
		docSequence.setRequired(true);
		docSequence.setValidator(new RequiredValidator());
		docSequence.setListenerContexts(new ArrayList());
		
		SelectTextUIConfig docSequenceConfig = new SelectTextUIConfig();
		TextUIConfig tcf = new TextUIConfig();
		//tcf.setNumericPropertyFlag(false);
		tcf.setId("pd.docSequenc");
		tcf.setTitle(LocaleUtils.getTextWithoutException("putawayNumber"));
		
//		TextUIConfig tcf1 = new TextUIConfig();
//		//tcf1.setNumericPropertyFlag(false);
//		tcf1.setId("asnPutawayDoc.planQty");
//		tcf1.setTitle(LocaleUtils.getTextWithoutException("planPutawayQty"));
		
		docSequenceConfig.getInputUIConfigs().add(tcf);
//		docSequenceConfig.getInputUIConfigs().add(tcf1);
		docSequence.setSelectTextUIConfig(docSequenceConfig);
		docSequence.setReadOnly(false);
		docSequence.setCallBackPage(this);
		docSequence.afterPropertySet();	
		
		hsql = new StringBuffer("select bin.id,bin.binCode,bin.description from Bin bin where (bin.lockStatus IS null or bin.lockStatus = 'LOCK_T2') and bin.storageType.role in ( 'R1010','R3000','R4040' ) ");
        //hsql.append("/~bin.binCode: And bin.binCode like {bin.binCode}~/ ");
        //hsql.append("/~bin.description: And bin.description like {bin.description}~/ ");
        hsql.append("and bin.wh = #{SESSION_WAREHOUSE}");
        dispHead = LocaleUtils.getTextWithoutException("id")+","+ 
					        LocaleUtils.getTextWithoutException("binCode")+","+	
					        LocaleUtils.getTextWithoutException("title.desc");
        
        binSelect = PageUIFactory.makeSkuRemoteUI(
        		"allocate.bin.id", 
        		"manualAllocate",
        		this,
        		hsql.toString(),
        		dispHead, 
        		"binCode",
        		"0,20%,80%"
        	);
        binSelect.setDisplayColumn(2);
        binSelect.getInputFormItem().setName("binName");
        binSelect.setRequired(true);
        binSelect.setValidator(new RequiredValidator());
        binSelect.setListenerContexts(new ArrayList());
        
        SelectTextUIConfig binConfig = new SelectTextUIConfig();
        TextUIConfig tuc1 = new TextUIConfig();
        //tuc1.setNumericPropertyFlag(false);
        tuc1.setId("bin.binCode");
        tuc1.setTitle(LocaleUtils.getTextWithoutException("binCode"));
        binConfig.getInputUIConfigs().add(tuc1);
        TextUIConfig tuc2 = new TextUIConfig();
        //tuc2.setNumericPropertyFlag(false);
        tuc2.setId("bin.description");
        tuc2.setTitle(LocaleUtils.getTextWithoutException("title.desc"));
        binConfig.getInputUIConfigs().add(tuc2);
        binSelect.setSelectTextUIConfig(binConfig);
        binSelect.afterPropertySet();	
        
        queryForm.setFields(
        		docSequence.getInputFormItem()
        );
        
        bodyLeft_qry_form.addMember(queryForm);
        
		if(queryBtn==null){
			queryBtn=new ButtonUI("search","search","search","enter",null,"query"){
				public void onClick(ClickEvent event) {
					removeRecords(taskGrid, taskDetailDS);
					removeRecords(invGrid, invDetailDS);
					removeRecords(baseGrid,docDetailDS);
					queryputUpDetail();
					sectionDownTitleSetting(null,0,0);
					clearTextContent();
				}
			};
			queryBtn.getButton().setWidth(100);
		}        
		if(resetBtn==null){
			resetBtn=new ButtonUI("clear","clear","clear","enter",null,"reset"){
				public void onClick(ClickEvent event) {
					
					SC.confirm(LocaleUtils.getText("shippingdoc.reset.confirm"), new BooleanCallback() {
						@Override
						public void execute(Boolean value) {
							if(value){
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
		skuCode = new TextFieldUI();
		skuCode.setTitle("tableSkuCode");
		skuCode.getInputFormItem().setName("skuCode");
		skuCode.setReadOnly(true);
		skuCode.afterPropertySet();		
        
        skuName = new TextFieldUI();
        skuName.setTitle("tableSkuName");
        skuName.getInputFormItem().setName("skuName");
        skuName.setReadOnly(true);
        skuName.afterPropertySet();		
        
        lotInfo = new TextFieldUI();
        lotInfo.setTitle("lotInfo");
        lotInfo.getInputFormItem().setName("lotInfo");
        lotInfo.setReadOnly(true);
        lotInfo.afterPropertySet();	
        
//        projectNo = new TextFieldUI();
//        projectNo.setTitle("projectNo");
//        projectNo.getInputFormItem().setName("projectNo");
//        projectNo.setReadOnly(true);
//        projectNo.afterPropertySet();	
        
        invStatus =  new TextFieldUI();
        invStatus.setTitle("invStatus");
        invStatus.getInputFormItem().setName("invStatus");
        invStatus.setReadOnly(true);
        invStatus.afterPropertySet();
        
        packList = new TextFieldUI();
        packList.setTitle("packDetail");
        packList.setReadOnly(true);
        packList.afterPropertySet();         

        
//        palletNo = new TextFieldUI();
//        palletNo.setTitle("target.pallet");
//        palletNo.getInputFormItem().setName("palletNo");
//        palletNo.setReadOnly(false);
//        palletNo.afterPropertySet();	        
        
//        containerNo = new TextFieldUI();
//        containerNo.setTitle("target.container");
//        containerNo.getInputFormItem().setName("containerNo");
//        containerNo.setReadOnly(false);
//        containerNo.afterPropertySet();	
        
        allocateQuantity = new TextFieldUI();
        allocateQuantity.setTitle("allocate.quantity");
        allocateQuantity.getInputFormItem().setName("allcateQty");
        allocateQuantity.setRequired(true);
        allocateQuantity.setReadOnly(false);
        allocateQuantity.afterPropertySet();
        
        
        allocateForm.setFields(
        		invId.getInputFormItem(),
        		taskId.getInputFormItem(),
        		skuCode.getInputFormItem(),
        		skuName.getInputFormItem(),
        		binSelect.getInputFormItem(),
        		lotInfo.getInputFormItem(),
        		//projectNo.getInputFormItem(),
        		invStatus.getInputFormItem(),
        		//palletNo.getInputFormItem(),
        		//containerNo.getInputFormItem(),
        		packList.getInputFormItem(),
        		allocateQuantity.getInputFormItem()
        );
        
        bodyLeft_form.addMember(allocateForm);
        //确认分配
		if(allocateBtn==null){
			allocateBtn=new ButtonUI("save","allocate.ok","save","enter",null,"save"){
				@SuppressWarnings("unchecked")
				public void onClick(ClickEvent event) {
					allocateConfirm();
				}
			};
			allocateBtn.getButton().setWidth(100);
		}
	
		if(cancelBtn==null){
			cancelBtn=new ButtonUI("cancel","cancel","cancel","cancel",null,"cancel"){
				@SuppressWarnings("unchecked")
				public void onClick(ClickEvent event) {
					allocateCancel();
				}
			};
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
	
	public void allocateConfirm(){
		if(binSelect.getValue()==null){
			SC.say(LocaleUtils.getText("allocateForMovePlan.bin.error"));
			return;
		}
		if( StringUtils.isEmpty((String)allocateQuantity.getInputFormItem().getValue())){
			SC.say(LocaleUtils.getText("allocateForPicking.input.error"));
			return;
		}
//		if(!ValidateUtil.isPositiveInteger((String)allocateQuantity.getInputFormItem().getValue())){
//			SC.say(LocaleUtils.getText("allocateForPicking.input.allocateQuantity"));
//			return;
//		}
		SC.confirm(LocaleUtils.getText("allocateForPutaway.allocate.confirm"),new BooleanCallback() {
			@Override
			public void execute(Boolean value) {
				if(value){
					AllocationDs ads=(AllocationDs) getData();
					ListGridRecord selectRecord=baseGrid.getSelectedRecord();
					if(selectRecord==null){
						SC.say(LocaleUtils.getText("allocateForPicking.input.error"));
						return;
					}
					String docDetailId=selectRecord.getAttribute("PutawayDocDetailDS0");
					Map values=newHashMap();
					values.put("docDetailId", docDetailId);
					values.put("binIdUI",binSelect.getValue());
					values.put("allcateQty", allocateQuantity.getInputFormItem().getValue());
					values.put("containerNo", allocateForm.getValue("containerNo"));
					values.put("palletNo", allocateForm.getValue("palletNo"));
					ads.cofirmAllotation(values);
					removeRecords(taskGrid, taskDetailDS);
//					binSelect.getInputFormItem().clearValue();
//					allocateQuantity.getInputFormItem().clearValue();
					clearTextContent();
				}
			}
		});	
		
	}
	
	public void allocateCancel(){
		if(StringUtils.isEmpty(taskId.getText()) ){
			SC.say(LocaleUtils.getText("allocateForPutaway.cancel.error"));
			return;
		}
		SC.confirm(LocaleUtils.getText("allocateForPutaway.cancel.confirm"),new BooleanCallback() {
			@Override
			public void execute(Boolean value) {
				if(value){
					ListGridRecord[] slectRds=taskGrid.getSelectedRecords();
					Map params=newHashMap();
					if(slectRds!=null && slectRds.length>0){
						List<Long> listIds=new ArrayList<Long>();
						for(ListGridRecord rd:slectRds){
							listIds.add(Long.parseLong(rd.getAttribute("WarehouseTaskDS0")));
						}
						AllocationDs ads=(AllocationDs) getData();
						if(listIds!=null ){
							params.put("selectRecords", listIds);
							ads.cancelAllovation(params);
							removeRecords(taskGrid, taskDetailDS);
							clearTextContent();
						}
					}
				}
			}
		});
	}
	@Override
	public void doDispath(String message) {
		AllocationDs ds = (AllocationDs) getData();
		if (message.equals("BIN_DETAILINFO")) {
			List<ListGridRecord> rds = new ArrayList<ListGridRecord>();
			removeRecords(invGrid, invDetailDS);
			invBinId.clear();
			
			for (ClientBinDetailInfo infor : ds.getCbdInfo()) {
				ClientBinDetailRecords relBinRecords = ClientBinDetailRecords.getInstance(infor);
				ListGridRecord record = relBinRecords.getRecord();
				for(String key:relBinRecords.getTemBinId().keySet()){
					invBinId.put(key,relBinRecords.getTemBinId().get(key));
				}
				rds.add(record);
			}
			ListGridRecord[] listRds = new ListGridRecord[rds.size()];
			rds.toArray(listRds);
			invGrid.setData(listRds);
			cancelBtn.getButton().disable();
			allocateBtn.getButton().enable();
		}
		if (message.equalsIgnoreCase("Query_updata_Detail")) {
			removeRecords(baseGrid, null);
			List<ClientAsnUpdataDetail> asnUpdataList = ds.getClientUpdata();
			if(asnUpdataList!=null){
				ClientAsnUpdataDetailRecord putUpdataInstance = ClientAsnUpdataDetailRecord.getInstance(asnUpdataList);
				ListGridRecord[] rds = (ListGridRecord[]) putUpdataInstance.getRecords();
				double planNb = 0;
				double allotaNb = 0;
				for (ListGridRecord record : rds) {
					planNb = (Double.parseDouble(record.getAttribute("PutawayDocDetailDS9")))+ planNb;
					allotaNb = (Double.parseDouble(record.getAttribute("PutawayDocDetailDS10")))+ allotaNb;
				}
				sectionTopTitleSetting(planNb,allotaNb);
				baseGrid.setData(rds);
			}
			else{
				sectionTopTitleSetting(0,0);
			}
			ClientWhTaskRecords taskRds=ClientWhTaskRecords.getInstance(ds.getTaskInfo());
			taskGrid.setData(taskRds.getRecords());
			Record[] tsrds=taskRds.getRecords();
			taskGrid.setData(tsrds);
			cancelBtn.getButton().disable();
			allocateBtn.getButton().enable();	

		}
		if (message.equalsIgnoreCase("initData")) {
			ClientWhTaskRecords taskRds=ClientWhTaskRecords.getInstance(ds.getTaskInfo());
			Record[] rds=taskRds.getRecords();
			taskGrid.setData(rds);
			Set<String> set=new HashSet<String>();
			double planCount=0;
			
//			WarehouseTaskDS0=ID
//			WarehouseTaskDS1=作业号
//			WarehouseTaskDS2=货主名称
//			WarehouseTaskDS3=商品编号
//			WarehouseTaskDS4=商品名称
//			WarehouseTaskDS5=批次属性   
//			WarehouseTaskDS6=批次说明
//			WarehouseTaskDS7=库存状态
//			WarehouseTaskDS8=包装
//			WarehouseTaskDS9=计划上架数
//			WarehouseTaskDS10=计划上架数(EA)
//			WarehouseTaskDS11=已上架数
//			WarehouseTaskDS12=已上架数(EA)
//			WarehouseTaskDS13=目标库位
//			WarehouseTaskDS14=目标托盘号
//			WarehouseTaskDS15=目标容器号        
//			WarehouseTaskDS16=原库位
//			WarehouseTaskDS17=原始托盘号
//			WarehouseTaskDS18=原始容器号
			
			if(rds.length>0){
				for(int i=0;i<rds.length;i++){
					String code=rds[i].getAttribute("WarehouseTaskDS3");
					String name=rds[i].getAttribute("WarehouseTaskDS4");
					set.add(code+name);
					planCount=Double.parseDouble(rds[i].getAttribute("WarehouseTaskDS9"))+planCount;
				}
				sectionDownTitleSetting(set,planCount,rds.length);
			}else{
				sectionDown.setTitle(LocaleUtils.getText("table.warehousetask")+" " +0 + LocaleUtils.getText("table.allocationQty")+0 + LocaleUtils.getText("tabel.allocationTaskQty")+0);
			}
			cancelBtn.getButton().disable();
			allocateBtn.getButton().enable();	
		}
		
		if (message.equalsIgnoreCase("confirmAlloInfo") || message.equalsIgnoreCase("cancel")) {
			ClientWhTaskRecords taskRds=ClientWhTaskRecords.getInstance(ds.getTaskInfo());
			ClientAsnUpdataDetailRecord detailRds=ClientAsnUpdataDetailRecord.getInstance(ds.getClientUpdata());
			baseGrid.setData(detailRds.getRecords());
			fillInvList();
			taskGrid.setData(taskRds.getRecords());
			Record[] rds=taskRds.getRecords();
			taskGrid.setData(rds);
			Set<String> set=new HashSet<String>();
			double planCount=0;
			
//			WarehouseTaskDS0=ID
//			WarehouseTaskDS1=作业号
//			WarehouseTaskDS2=货主名称
//			WarehouseTaskDS3=商品编号
//			WarehouseTaskDS4=商品名称
//			WarehouseTaskDS5=批次属性   
//			WarehouseTaskDS6=批次说明
//			WarehouseTaskDS7=库存状态
//			WarehouseTaskDS8=包装
//			WarehouseTaskDS9=计划上架数
//			WarehouseTaskDS10=计划上架数(EA)
//			WarehouseTaskDS11=已上架数
//			WarehouseTaskDS12=已上架数(EA)
//			WarehouseTaskDS13=目标库位
//			WarehouseTaskDS14=目标托盘号
//			WarehouseTaskDS15=目标容器号        
//			WarehouseTaskDS16=原库位
//			WarehouseTaskDS17=原始托盘号
//			WarehouseTaskDS18=原始容器号			
			
			if(rds.length>0){
				for(int i=0;i<rds.length;i++){
					String code=rds[i].getAttribute("WarehouseTaskDS5");
					String name=rds[i].getAttribute("WarehouseTaskDS6");
					set.add(code+name);
					planCount=Double.parseDouble(rds[i].getAttribute("WarehouseTaskDS12"))+planCount;
				}
			}
			
			sectionDownTitleSetting(set,planCount,rds.length);
			refleshDetailTitle(detailRds);
			cancelBtn.getButton().disable();
			allocateBtn.getButton().enable();	
		}

	}
	
	/**
	 * 刷新库存
	 */
	public void fillInvList(){
		AllocationDs ds = (AllocationDs) getData();
		List<ListGridRecord> rds = new ArrayList<ListGridRecord>();
		removeRecords(invGrid, invDetailDS);
		invBinId.clear();
		for (ClientBinDetailInfo infor : ds.getCbdInfo()) {
			ClientBinDetailRecords relBinRecords = ClientBinDetailRecords.getInstance(infor);
			ListGridRecord record = relBinRecords.getRecord();
			for(String key:relBinRecords.getTemBinId().keySet()){
				invBinId.put(key,relBinRecords.getTemBinId().get(key));
			}
			
			rds.add(record);
		}
		ListGridRecord[] listRds = new ListGridRecord[rds.size()];
		rds.toArray(listRds);
		invGrid.setData(listRds);
	}
	
	
	public void resetTaskInfoBySelectRow(){
		ListGridRecord record = taskGrid.getSelectedRecord();
		if(record != null){
			taskId.setValue(record.getAttribute("WarehouseTaskDS0"));
			binSelect.setValue(record.getAttribute("WarehouseTaskDS15"));
			skuCode.setValue(record.getAttribute("WarehouseTaskDS5"));
			skuName.setValue(record.getAttribute("WarehouseTaskDS6"));
			lotInfo.setValue(record.getAttribute("WarehouseTaskDS7"));
			//projectNo.setValue(record.getAttribute("WarehouseTaskDS8"));
			invStatus.setValue(record.getAttribute("WarehouseTaskDS9"));
			packList.setValue(record.getAttribute("WarehouseTaskDS10"));
			allocateQuantity.setValue(record.getAttribute("WarehouseTaskDS11"));
			
			allocateBtn.getButton().disable();
			cancelBtn.getButton().enable();
		}
	}
	
	/**
	 * 刷新明细title
	 * @param detailRds
	 */
	public void refleshDetailTitle(ClientAsnUpdataDetailRecord detailRds){
		ListGridRecord[] fd=(ListGridRecord[]) detailRds.getRecords();
		double planNb=0;
		double allotaNb=0;
		if(fd.length>0){
			for(ListGridRecord record:fd){
				planNb=(Double.parseDouble(record.getAttribute("PutawayDocDetailDS10")))+planNb;
				allotaNb=(Double.parseDouble(record.getAttribute("PutawayDocDetailDS11")))+allotaNb;
			}
			sectionTopTitleSetting(planNb,allotaNb);
		}else{
			sectionTopTitleSetting(0,0);
		}
		
	}
	public void  clearTextContent(){
		skuCode.getInputFormItem().clearValue();
		skuName.getInputFormItem().clearValue();
		lotInfo.getInputFormItem().clearValue();
		//projectNo.getInputFormItem().clearValue();
		invStatus.getInputFormItem().clearValue();
		binSelect.getInputFormItem().clearValue();
		allocateQuantity.getInputFormItem().clearValue();
	}
	
	public void resetAll(){
		resetGrid();
		resetInputText();
		resetInputQuery();
	}
	
	public void resetInputText(){
		skuCode.resetValue();
		skuName.resetValue();
		binSelect.resetValue();
		lotInfo.resetValue();
		//projectNo.resetValue();
		invStatus.resetValue();
		packList.resetValue();	
		taskId.resetValue();
		allocateQuantity.resetValue();		
	}	
	
	public void resetInputQuery(){
		docSequence.resetValue();
	}
	public void resetGrid(){
		sectionTopTitleSetting(0,0);
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
	public Record[] getNewRecord() {
		return new Record[0];
	}

	@Override
	public BaseDataAccess getData() {
		return dataAccess;
	}

	@Override
	public List<String> getMessageKeys() {
		return new ArrayList<String>();
	}

	@Override
	public void initData() {
		dataAccess = new AllocationDs(this);
		removeRecords(invGrid, invDetailDS);
	}

	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}
	
	@Override
	public void callBackWithParam(Object obj, String uId) {
		if(docSequence.getValue() != null){
			queryputUpDetail();
			resetInputText();
		}
	}
	public void sectionTopTitleSetting(double panQty, double allocateQty){
		if(panQty!=0 || allocateQty!=0){
			sectionTop.setTitle(LocaleUtils.getText("table.allocate") + " " + LocaleUtils.getText("table.putaway.detail.plan.qty") + panQty + " " + LocaleUtils.getText("table.putaway.detail.allocate.qty") + allocateQty);
		}else
		{
			sectionTop.setTitle(LocaleUtils.getText("table.allocate") + " " + LocaleUtils.getText("table.putaway.detail.plan.qty") + 0 + " " + LocaleUtils.getText("table.putaway.detail.allocate.qty") + 0);
		}
	}
	public void sectionDownTitleSetting(Set size,double allcateQty,double allocateTaskQty){
		if(size!=null && !size.isEmpty()){
			sectionDown.setTitle(LocaleUtils.getText("table.warehousetask") + size.size() + " " + LocaleUtils.getText("table.allocationQty") + allcateQty  + " " + LocaleUtils.getText("tabel.allocationTaskQty")+allocateTaskQty);
		}
		else{
			sectionDown.setTitle(LocaleUtils.getText("table.warehousetask") + 0 + " " + LocaleUtils.getText("table.allocationQty")+0 + " " + LocaleUtils.getText("tabel.allocationTaskQty")+0);
		}
	}


}
