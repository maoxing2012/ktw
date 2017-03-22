package com.core.scpwms.client;

import java.util.HashMap;
import java.util.Map;

import com.core.scpview.client.BaseWindow;
import com.core.scpview.client.config.page.PageConfig;
import com.core.scpview.client.context.ConfigContext;
import com.core.scpview.client.engine.IPopupPage;
import com.core.scpview.client.remote.AjaxServiceUtil;
import com.core.scpview.client.remote.AsyncCallBackAdapter;
import com.core.scpview.client.remote.CatchPageConfigAsync;
import com.core.scpview.client.remote.CommitServiceAsync;
import com.core.scpview.client.utils.IconFactory;
import com.core.scpview.client.utils.LocaleUtils;
import com.core.scpwms.client.bean.WarehouseInfo;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Scpwms extends BaseWindow {
	
	public static ToolStripButton currentWareHouse;
	public static IPopupPage doSwitchWareHousePage;
	public static IPopupPage doChangePswd;
	public static ClickHandler switchHandler;
	public static ToolStripButton changePassword;

	@Override
	public ToolStrip initToolStrip(ToolStrip topBar) {
		if(topBar == null){
			topBar = new ToolStrip();
		}
		
        topBar.setHeight(33);
        topBar.setWidth100();

        topBar.addSpacer(6);
        ImgButton sgwtHomeButton = new ImgButton();
        sgwtHomeButton.setSrc("icons/24/transport.png");
        sgwtHomeButton.setWidth(24);
        sgwtHomeButton.setHeight(24);
        sgwtHomeButton.setPrompt(LocaleUtils.getText("logo.company.name"));
        sgwtHomeButton.setHoverStyle("interactImageHover");
        sgwtHomeButton.setShowRollOver(false);
        sgwtHomeButton.setShowDownIcon(false);
        sgwtHomeButton.setShowDown(false);
        topBar.addMember(sgwtHomeButton);
        topBar.addSpacer(6);

        Label title = new Label(LocaleUtils.getText("logo.company.name"));
        title.setStyleName("sgwtTitle");
        title.setWidth(300);
        topBar.addMember(title);
        topBar.addFill();
        
        // ActiveT 1304901 4/16 start
        Label imgUser = new Label();
        imgUser.setIcon("icons/16/user_delete.gif");
        imgUser.setHeight(18);
        imgUser.setWidth(18);
        Label loginUser = new Label(LocaleUtils.getText("logo.current.name") + "：" + context.getGlobalParams().get("SESSION_USER_NAME"));
        loginUser.setWidth(180);
        topBar.addMember(imgUser);
        topBar.addMember(loginUser);
        topBar.addSeparator();
        // ActiveT 1304901 4/16 end
		
        currentWareHouse = new ToolStripButton();
        currentWareHouse.setTitle("Loading...");
        currentWareHouse.setIcon(IconFactory.getGifIconDir("database_add"));
        initCurrentWareHouse();
        
        /*
        changePassword =new ToolStripButton();
        changePassword.setTitle(LocaleUtils.getText("eidtUserPassword"));
        changePassword.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				showChangePassword();
			}
	    });
	    */

        // ActiveT 1304901 4/16 start
        changePassword = new ToolStripButton();
        changePassword.setIcon(IconFactory.getPngIconDir("lock"));
//        changePassword.setWidth(18);
//        changePassword.setHeight(18);
        //pwdButton.setSrc("icons/16/user_delete.gif");
//        changePassword.setSrc("icons/16/lock.png");
//        changePassword.setShowFocused(false);
        // ActiveT 1304901 4/19
//        changePassword.setShowFocusedIcon(false);
//        changePassword.setShowRollOver(false);
//        changePassword.setShowDown(false);
        changePassword.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				showChangePassword();
			}
        	
        });
        
        topBar.addMember(changePassword);
        topBar.addSeparator();
        
        topBar.addButton(currentWareHouse); 
        topBar.addSeparator();
        
        ToolStripButton imgButton = new ToolStripButton();
        imgButton.setIcon(IconFactory.getPngIconDir("user_delete"));
//        imgButton.setWidth(18);
//        imgButton.setHeight(18);
//        imgButton.setSrc("icons/16/user_delete.png");
//        imgButton.setShowFocused(false);
//        imgButton.setShowFocusedIcon(false);
//        imgButton.setHoverWidth(110);
//        imgButton.setHoverStyle("interactImageHover");
        
        imgButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
            public void onClick(ClickEvent event) {
            	String logoutConfirmMessage = LocaleUtils.getText("confirm.logout");
            	SC.confirm(logoutConfirmMessage, new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						// TODO Auto-generated method stub
						if(value){
							doLogout();
						}else{
							return;
						}
					}
				});
            }
        });        

        // ActiveT 1304901 6/26
//        ToolStripButton helpButton = new ToolStripButton();
//        helpButton.setIcon(IconFactory.getPngIconDir("help"));
//        helpButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
//            public void onClick(ClickEvent event) {
//            	Window.open("help/index.html", "", "enabled");
//            }
//        });
//        topBar.addMember(helpButton);
//        topBar.addSeparator();

        topBar.addMember(imgButton);
        // ActiveT 1304901 2014/05/28
        // topBar.addSpacer(6); 
        
        return topBar;
	}
	
	
	

	@Override
	public void initNativeMethod() {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterInitConfigContext() {
		showMainPage();
	}
	
	public void showChangePassword(){			
		context.getPageConfig("eidtUserPassword", new CatchPageConfigAsync(){
			public void afterInvotion(PageConfig pageConfig){
				pageConfig.setTitle(LocaleUtils.getText("eidtUserPassword"));
				doChangePswd=pageConfig.createPage(null, null);
				doChangePswd.initData();
				doChangePswd.show();
				resetMessage();
				return;
			}
		});	
	}
	
	public void initCurrentWareHouse(){
		AsyncCallBackAdapter acba = new AsyncCallBackAdapter() {
		    public void exec() {
		    	CommitServiceAsync serviceAsync = AjaxServiceUtil.initialAsyncService(ConfigContext.DEFAULT);
		    	serviceAsync.executeCustom("warehouseManager","getCurrentWarehouse",new HashMap(),this);
		    }
		    
		    public void onSuccess() {
		    	final Map map = (Map)result;
		    	if(currentWareHouse == null){
		    		currentWareHouse = new ToolStripButton();
		    	}
		    	WarehouseInfo warehouseInfo= (WarehouseInfo)map.get("CURRENTWAREHOUSE");
		    	currentWareHouse.setWrap(false);
		    	currentWareHouse.setTitle(warehouseInfo.getWhName());
		        
		        switchHandler=new ClickHandler(){
		        	 public void onClick(ClickEvent event) {
			            	showSwitchWarehousePage(map);
			            }     
		        };
		        currentWareHouse.addClickHandler(switchHandler);
		    }
		    public void onFailure() {
		    }
		    
		};
		acba.exec("");
	}
	
	public static void showSwitchWarehousePage(final Map params){
		context.getPageConfig("doSwitchWareHouse", new CatchPageConfigAsync(){
			public void afterInvotion(PageConfig pageConfig){
				pageConfig.setTitle(LocaleUtils.getText("doSwitchWareHouse"));
				doSwitchWareHousePage=pageConfig.createPage(params, null);
				doSwitchWareHousePage.initData();
				doSwitchWareHousePage.show();
				resetMessage();
				return;
			}
		});
	}
	
	public static void processResultFromSwitchPage(Map result){
		WarehouseInfo whInfo=(WarehouseInfo)result.get("CURRENTWAREHOUSE");
		currentWareHouse.setTitle(whInfo.getWhName());
		doSwitchWareHousePage.hide();
		
	}
	
	public static void processResultFromChangePassword(Map result){
		doChangePswd.hide();
	}


}
