package com.core.scpwms.client.custom.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.core.scpview.client.context.ConfigContext;
import com.core.scpview.client.remote.AjaxServiceUtil;
import com.core.scpview.client.remote.AsyncCallBackAdapter;
import com.core.scpview.client.remote.CommitServiceAsync;
import com.core.scpview.client.ui.canvas.AbstractCustomCanvas;
import com.core.scpview.client.ui.commonWidgets.ButtonUI;
import com.core.scpview.client.ui.element.PasswordUI;
import com.core.scpview.client.ui.element.TextFieldUI;
import com.core.scpview.client.utils.LocaleUtils;
import com.core.scpwms.client.Scpwms;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

@SuppressWarnings("all")
public class ChangePswd extends AbstractCustomCanvas{
	
	protected transient VLayout vlayout;
	protected transient HLayout hlayout;
	protected transient HLayout buttonlayout;
	protected transient TextFieldUI currentUser;
	protected transient PasswordUI currentPassword;
	protected transient PasswordUI newPassword;
	protected transient PasswordUI confirmPassword;
	protected transient DynamicForm dataForm ;
	protected transient ButtonUI saveBtn;
	protected transient ButtonUI cancelBtn;
	
	public  ChangePswd(){
		
	}
	
	public void initPage() {
		initContainer();
		initUI();
		initButtons();
	}

	public void initUI(){
		
		dataForm = new DynamicForm();
		
		//当前用户
		currentUser=new TextFieldUI();
		currentUser.setTitle(LocaleUtils.getText("ChangePswd.currentUser"));
		currentUser.setReadOnly(true);
		currentUser.afterPropertySet();
		currentUser.getInputFormItem().setEndRow(true);
		
		
		//当前密码
		currentPassword=new PasswordUI();
		currentPassword.setTitle(LocaleUtils.getText("ChangePswd.currentPassword"));
		currentPassword.afterPropertySet();
		currentPassword.getInputFormItem().setEndRow(true);
		
		//新密码
		newPassword=new PasswordUI();
		newPassword.setTitle("ChangePswd.newPassword");
		newPassword.setReadOnly(false);
		newPassword.afterPropertySet();
		newPassword.getInputFormItem().setEndRow(true);
		
		//确认新密码
		confirmPassword=new PasswordUI();
		confirmPassword.setTitle("ChangePswd.confirmPassword");
		confirmPassword.setReadOnly(false);
		confirmPassword.afterPropertySet();
		confirmPassword.getInputFormItem().setEndRow(true);

	}
	
	public void initContainer(){
		
		contentCanvas.setAutoWidth();
		contentCanvas.setAutoHeight();
		contentCanvas.setOverflow(Overflow.VISIBLE);
		contentCanvas.setMargin(5);
		
		vlayout=new VLayout();
		vlayout.setMembersMargin(5);
		vlayout.setMinHeight(1);//Minimum height that this Canvas can be resized to. 
		vlayout.setAutoHeight();
		
		hlayout=new HLayout();
		hlayout.setMembersMargin(5);
		hlayout.setOverflow(Overflow.VISIBLE);
		hlayout.setMinHeight(1);
		hlayout.setAutoHeight();

		buttonlayout=new HLayout();
		buttonlayout.setLayoutBottomMargin(10);
		buttonlayout.setMembersMargin(5);
		buttonlayout.setOverflow(Overflow.VISIBLE);
		buttonlayout.setMinHeight(1);
		buttonlayout.setAutoHeight();

		vlayout.addMember(hlayout);
		vlayout.addMember(buttonlayout);
		
		contentCanvas.addChild(vlayout);
		contentCanvas.setWidth(config.getWidth());
		if(config.getWidth()!=null){
			vlayout.setWidth(Integer.parseInt(config.getWidth())-10);
			hlayout.setWidth(Integer.parseInt(config.getWidth())-10);
		}
		contentCanvas.setAutoHeight();
		
	}
	
	public void initButtons(){
		
		if(saveBtn==null){
			saveBtn=new ButtonUI("save","modifyPswd","save","enter",null,"edit"){
				public void onClick(ClickEvent event) {
					saveBtn.getButton().setDisabled(false);
					doChangeEvent();
				}
			};
		}
		if(cancelBtn==null){
			cancelBtn=new ButtonUI("cancel","cancel","cancel","enter",null,"reset"){
				public void onClick(ClickEvent event) {
					cancelBtn.getButton().setDisabled(false);
					Scpwms.doChangePswd.hide();
				}
			};
		}
		buttonlayout.addMember(saveBtn.getButton());
		buttonlayout.addMember(cancelBtn.getButton());
	}
	
	
	public void addUIToCanvas(){
		dataForm.setItems(
				currentUser.getInputFormItem(),
				currentPassword.getInputFormItem(),
				newPassword.getInputFormItem(),
				confirmPassword.getInputFormItem());
		dataForm.setAutoWidth();
		dataForm.setAutoHeight();
		dataForm.setOverflow(Overflow.VISIBLE);
		dataForm.setNumCols(2);
		hlayout.addChild(dataForm);
	}	
	
	public void initData() {
		final Map toServerParams = new HashMap();
		AsyncCallBackAdapter acba = new AsyncCallBackAdapter(){
			protected void exec() {
		    	CommitServiceAsync serviceAsync = AjaxServiceUtil.initialAsyncService(ConfigContext.DEFAULT);
		    	serviceAsync.executeCustom("wmsUserManager","getThisUser",toServerParams,this);
				
			}
			public void onSuccess() {
				Map userName = (Map)result;
				currentUser.setValue(userName.get("CURRENTUSER_NAME"));

			}			
		};
		acba.exec("");//调用AsyncCallBackAdapter类的exec方法才能获取到值
		addUIToCanvas();
		
	}
	
	//画面加载成功后在这里做保存，取消操作
	public void doChangeEvent(){
		final Map fromDataPswd=new HashMap();
		fromDataPswd.put("CURRENTOLDPSWD", currentPassword.getValue());
		fromDataPswd.put("NEWPSWD", newPassword.getValue());
		fromDataPswd.put("CONFIRMPSWD", confirmPassword.getValue());
		if(!"".equals(currentPassword.getValue()) 
				&& !"".equals(newPassword.getValue())
				&& !"".equals(confirmPassword.getValue())){
			
			AsyncCallBackAdapter acbaUser=new AsyncCallBackAdapter(){

				@Override
				protected void exec() {
					CommitServiceAsync serviceAsync=AjaxServiceUtil.initialAsyncService(ConfigContext.DEFAULT);
					serviceAsync.executeCustom("wmsUserManager", "modifyPassword", fromDataPswd, this);
				}

				@Override
				public void onSuccess() {
					currentPassword.resetValue();
					newPassword.resetValue();
					confirmPassword.resetValue();
					SC.say("パスワードを修正しました！");
				}
			};
			acbaUser.exec("");
		}else{
			currentPassword.resetValue();
			newPassword.resetValue();
			confirmPassword.resetValue();
		}		
	}
	
	public List<String> getMessageKeys() {
		List<String> keys=new ArrayList<String>();
		keys.add("ChangePswd.currentUser");
		keys.add("ChangePswd.currentPassword");
		keys.add("ChangePswd.newPassword");
		keys.add("ChangePswd.confirmPassword");
		keys.add("modifyPswd");
		keys.add("cancel");
		return keys;
	}

}
