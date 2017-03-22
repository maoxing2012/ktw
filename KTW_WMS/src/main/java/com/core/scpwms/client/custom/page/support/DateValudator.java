package com.core.scpwms.client.custom.page.support;

import java.util.Arrays;
import java.util.Date;

import com.core.scpview.client.ui.InputUI;
import com.core.scpview.client.ui.element.DateUI;
import com.core.scpview.client.utils.LocaleUtils;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
/**
 * 
 * 
 * <p>
 * 日期格式验证辅助类
 * </p>
 * 
 * @version 1.0
 * @author MBP : dengxg </br>
 *          </br>
 *         2013-9-6 : MBP dengxg: 初版创建</br>
 */
public class DateValudator {
	private static DateValudator valudator=null;
	private DateValudator() {
	}
	
	public static DateValudator getInscance(){
		if (valudator == null) {
			valudator = new DateValudator();
		}
		return valudator;
	}
	/**
	 * 针对动态form框中日期Item 格式的验证！
	 * @param form 动态form框！
	 * @return 验证为通过返回false
	 */
	public  boolean validate(DynamicForm form) {
		for (FormItem item : Arrays.asList(form.getFields())) {
			if (item instanceof DateItem) {
				DateItem dateItem = (DateItem) item;
				return validate(dateItem);
			}
		}
		return true;
	}
	
	/**
	 * 单个DateUi格式 及空值 验证！
	 * @param input	  日期
	 * @param message 提示信息
	 * @return boolean
	 */
	public boolean validate(InputUI input,String message){
		if (input instanceof DateUI) {
			DateItem dateItem = (DateItem) input.getInputFormItem();
			DateUI ui=	(DateUI) input;
			if ((ui.isRequired() || dateItem.getRequired()) && (ui.getValue()==null || ui.getValue().toString().trim().equals(""))) {
				SC.say(LocaleUtils.getText(message));
				return false;
			}
			if (dateItem.getValue() != null) {
				return validate(dateItem);
			} 
				

		}
		return true;
	}
	
	private boolean validate(DateItem dateItem){
		
		if (dateItem.getValue() != null && !(dateItem.getValue() instanceof Date)) {
			//format title:message
			SC.say(LocaleUtils.getText(dateItem.getTitle())		//title
					+":"
					+LocaleUtils.getText("recevingRegist_planQty.dateFormatError"));	//message
			dateItem.setSelectOnFocus(true);
			dateItem.setSelectionRange(0, 15);
			dateItem.focusInItem();
			return false;
		}
		return true;
	}

}
