package com.core.scpwms.client.custom.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.core.scpview.client.BaseWindow;
import com.core.scpview.client.context.ConfigContext;
import com.core.scpview.client.factory.UIFactory;
import com.core.scpview.client.remote.AjaxServiceUtil;
import com.core.scpview.client.remote.AsyncCallBackAdapter;
import com.core.scpview.client.remote.CommitServiceAsync;
import com.core.scpview.client.ui.canvas.AbstractCustomCanvas;
import com.core.scpview.client.ui.commonWidgets.ButtonUI;
import com.core.scpview.client.ui.element.TextFieldUI;
import com.core.scpview.client.ui.table.RowData;
import com.core.scpview.client.utils.LocaleUtils;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.client.Scpwms;
import com.core.scpwms.client.bean.WarehouseInfo;
import com.core.scpwms.client.rpc.CustomService;
import com.core.scpwms.client.rpc.CustomServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

@SuppressWarnings("all")
public class SwitchWarehouse extends AbstractCustomCanvas{
	
	public SwitchWarehouse(){
		
	}
	
	protected transient HLayout hlayout;
	protected transient HLayout buttonlayout;
	protected transient VLayout vlayout;
	
	protected transient TextFieldUI currentWh;
	protected transient SelectItem listBox;
	protected transient DynamicForm dataForm ;
	protected transient ButtonUI saveBtn;
	
	public transient LinkedHashMap<String, String> valueMap;
	
	public void hide() {
	}

	public void show() {
	}

	public void initPage() {
		initContainer();
		initUI();
		initButtons();
	}
	
	public void initButtons(){
		if(saveBtn == null ) {
			saveBtn = new ButtonUI("save", "save", "save", "enter", null,"save"){
				public void onClick(ClickEvent event) {
					saveBtn.getButton().setDisabled(true);
					doSwitch();
				}
			};
		}
		
		
		buttonlayout.addMember(saveBtn.getButton());
	}
	
	
	
	public void addUIToCanvas(){
		dataForm.setItems(currentWh.getInputFormItem(),listBox);
		dataForm.setAutoWidth();
		dataForm.setAutoHeight();
		dataForm.setOverflow(Overflow.VISIBLE);
		dataForm.setNumCols(2);
		hlayout.addChild(dataForm);
	}
	
	public void initUI(){
		dataForm = new DynamicForm();
		dataForm.setAutoFocus(true);
		
		//当前仓库
		currentWh = new TextFieldUI();
		currentWh.setTitle("SwitchWh.currentWh");
		currentWh.setReadOnly(true);
		currentWh.afterPropertySet();
		currentWh.getInputFormItem().setEndRow(true);
		
		//可切换仓库
		listBox = UIFactory.getListBox();
		listBox.setTitle(LocaleUtils.getText("SwitchWh.whList"));
		listBox.setCanEdit(true);
		listBox.setEndRow(true);
		listBox.setShowTitle(true);
		listBox.setCanFocus(true);
		listBox.setAnimatePickList(false);
		valueMap = new LinkedHashMap<String, String>();
		listBox.setValueMap(valueMap);
		
	}
	
	public void initContainer(){
		contentCanvas.setAutoWidth();
		contentCanvas.setAutoHeight();
		contentCanvas.setOverflow(Overflow.VISIBLE);
		contentCanvas.setMargin(5);
		
		vlayout = new VLayout();
		vlayout.setMembersMargin(5);
		vlayout.setMinHeight(1);
		vlayout.setAutoHeight();
		
		hlayout = new HLayout();
		hlayout.setMembersMargin(5);
		hlayout.setOverflow(Overflow.VISIBLE);
		hlayout.setMinHeight(1);
		hlayout.setAutoHeight();
		
		buttonlayout = new HLayout();
		buttonlayout.setLayoutBottomMargin(10);
		buttonlayout.setMembersMargin(5);
		buttonlayout.setOverflow(Overflow.VISIBLE);
		buttonlayout.setMinHeight(1);
		buttonlayout.setAutoHeight();
		
		vlayout.addMember(hlayout);
		vlayout.addMember(buttonlayout);
		
		contentCanvas.addChild(vlayout);
		
		contentCanvas.setWidth(config.getWidth());
		
		
		if(!StringUtils.isEmpty(config.getWidth())){
			vlayout.setWidth(Integer.parseInt(config.getWidth())-10);
			hlayout.setWidth(Integer.parseInt(config.getWidth())-10);
		}
		
		contentCanvas.setAutoHeight();		
		
	}
	
	public void initData() {
		
		currentWh.setValue(Scpwms.currentWareHouse.getTitle());
		
		final Map toServerParams = new HashMap();
		toServerParams.put("user.id", params.get("CURRENTUSERID"));
		
		AsyncCallBackAdapter acba = new AsyncCallBackAdapter() {
		    public void exec() {
		    	CommitServiceAsync serviceAsync = AjaxServiceUtil.initialAsyncService(ConfigContext.DEFAULT);
		    	serviceAsync.executeCustom("warehouseManager","getWhListForClient",toServerParams,this);
		    }
		    
		    public void onSuccess() {
		    	Map map = (Map)result;
				initUIByParams(map);     
		    }
		    public void onFailure() {
		    }
		    
		};
		acba.exec("");		

	}
	
	
	public void initUIByParams(Map map){
		
		List rowDatas = (List)map.get("warehouse.list");
		valueMap = new LinkedHashMap<String, String>();
		for(int i=0;i < rowDatas.size();i ++){
			List columnValues = ((RowData)rowDatas.get(i)).getColumnValues();
			valueMap.put(String.valueOf(columnValues.get(0)),(String) columnValues.get(1));
		}
		listBox.setValueMap(valueMap);
		
		
		addUIToCanvas();
		
		
	}
	
	public void doSwitch(){
		//check 
		if(listBox.getValue() != null && !"".equals(listBox.getValue().toString())){
			if(!currentWh.getValue().toString().equalsIgnoreCase(valueMap.get(listBox.getValue().toString()))){
				
				CustomServiceAsync asyncService = (CustomServiceAsync) GWT.create(CustomService.class);
				ServiceDefTarget endpoint = (ServiceDefTarget) asyncService;
				String server = GWT.getModuleBaseURL()+ "*.switchWh";
				endpoint.setServiceEntryPoint(server);
				
//				asyncService.swichPlatForm(Long.valueOf(Long.parseLong(listBox.getValue().toString())), new AsyncCallback() 
				asyncService.swichWarehouse(Long.valueOf(Long.parseLong(listBox.getValue().toString())) , new AsyncCallback(){
					public void onFailure(Throwable caught) {
						SC.say(caught.getMessage());
						saveBtn.getButton().setDisabled(false);
					}
					
					public void onSuccess(Map result) {
						
//						BaseWindow.bseWindow.context.setGlobalParams((Map<String, Object>) result.get("globals"));
						BaseWindow.bseWindow.context.setGlobalParams((Map)result.get("globals"));
						
		                Tab[] tabs = BaseWindow.bseWindow.mainTabSet.getTabs();
		                // ActiveT 1304901 6/19
		                int[] tabsToRemove = new int[tabs.length];

		                for (int i = 1; i < tabs.length; i++) {
		                    tabsToRemove[i - 1] = i;
		                }
		                
		                BaseWindow.bseWindow.mainTabSet.removeTabs(tabsToRemove);
		                // ActiveT 1304901 6/19
		                //BaseWindow.bseWindow.mainTabSet.selectTab(0);						
						
		                WarehouseInfo whInfo = new WarehouseInfo();
		                whInfo.setWhName((String)result.get("warehouseName"));
		                result.put("CURRENTWAREHOUSE", whInfo);
		                Scpwms.processResultFromSwitchPage(result);
		                saveBtn.getButton().setDisabled(false);
					}

					@Override
					public void onSuccess(Object obj) {
						
						onSuccess((Map)obj);
						
					}
					
					
				});
				
			}else{
				saveBtn.getButton().setDisabled(false);
				return;
			}
		}else{
			saveBtn.getButton().setDisabled(false);
			return;
		}
	}

	public List<String> getMessageKeys() {
		List<String> keys = new ArrayList<String>();
		keys.add("SwitchWh.currentWh");
		keys.add("SwitchWh.whList");
		keys.add("save");
		keys.add("cancel");
		return keys;
	}

}
