package com.core.scpwms.client.custom.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.core.scpview.client.engine.BaseDataAccess;
import com.core.scpview.client.engine.IMessagePage;
import com.core.scpview.client.engine.UploadListener;
import com.core.scpview.client.ui.canvas.AbstractCustomCanvas;
import com.core.scpview.client.ui.canvas.Upload;
import com.core.scpview.client.utils.LocaleUtils;
import com.core.scpwms.client.custom.page.support.UploadFileConstant;
import com.core.scpwms.client.custom.page.support.UploadFileDataAccess;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SimpleUploadFilePage2 extends AbstractCustomCanvas implements UploadListener,IMessagePage{

	protected transient BaseDataAccess data;
	protected transient Upload upload;
	
	/**
	 * 整个容器
	 */
    protected transient VLayout allItemLayout;
    protected transient HLayout lsitLayout;  
    protected transient SelectItem ruleSelect;
    protected transient LinkedHashMap<String, String> ruleMap;
    
	@Override
	public void initPage() {
		
		//主画面
		contentCanvas.setWidth100();  
		contentCanvas.setHeight100();  		
		contentCanvas.setOverflow(Overflow.VISIBLE);
		contentCanvas.setMargin(6);		
		
		initDataAccessor();
		
		allItemLayout = new VLayout();
		allItemLayout.setMargin(0);
		allItemLayout.setMembersMargin(5);
		allItemLayout.setWidth100();
		allItemLayout.setHeight100();
		allItemLayout.setOverflow(Overflow.VISIBLE);		
		
		lsitLayout = new HLayout();
		lsitLayout.setHeight100();
		lsitLayout.setWidth100();	
		
		DynamicForm dataForm = new DynamicForm();
        dataForm.setNumCols(3);
        dataForm.setWidth100();
        dataForm.setHeight100();
        dataForm.setTitleOrientation(TitleOrientation.LEFT);		
        Object[] object = new Object[]{"0"};
        dataForm.setColWidths(object);
		
		ruleMap = new LinkedHashMap<String, String>();
		ruleSelect = new SelectItem();
		ruleSelect.setTitle(LocaleUtils.getText("rule.rule"));
		ruleSelect.setTitleStyle("scpRequireTitle");
		ruleSelect.setValueMap(ruleMap);
		ruleSelect.setWrapTitle(false);
		dataForm.setFields(ruleSelect);
		lsitLayout.addMember(dataForm);
		allItemLayout.addMember(lsitLayout);
		
		upload = new Upload(); 
		upload.setUploadListener(this);
		upload.setAction("gwt.fileUpload");
		allItemLayout.addMember(upload);
		contentCanvas.addChild(allItemLayout);
		contentCanvas.setWidth(config.getWidth());
		contentCanvas.setAutoHeight();
	}
	
    protected void initDataAccessor(){
    	data = new UploadFileDataAccess(this,UploadFileConstant.MANAGER);
	}	

	@Override
	public List<String> getMessageKeys() {
		return new ArrayList<String>();
	}

	@Override
	public void initData() {
		getData().getInitPageDate4Asn(new HashMap());
	}

	@Override
	public void uploadComplete(String param) {
		Map pp = new HashMap();
		pp.put(UploadFileConstant.ORG_NAME,upload.getFile());
		pp.put(UploadFileConstant.TARGET_NAME, param);
		pp.put(UploadFileConstant.RULE_SELECT, ruleSelect.getValue());
		getData().updateFileInfo(pp);
	}

	@Override
	public void doDispath(String message) {
		if(UploadFileConstant.MSG_INIT.equalsIgnoreCase(message)){
			ruleMap.clear();
			ruleSelect.clearValue();
			ruleMap.putAll(getData().getUploadRules());
			ruleSelect.setValueMap(ruleMap);
		}else if(UploadFileConstant.MSG_UPDATE.equalsIgnoreCase(message)){
			ruleSelect.clearValue();
//			SC.say("上传完毕。");
			// ActiveT 1304901 2014/05/21
			parentPage.reload(false);
			Window window=(Window) contentCanvas.getParentElement().getParentElement();
			window.hide();
		}
	}

	@Override
	public UploadFileDataAccess getData() {
		return (UploadFileDataAccess)data;
	}

}
