package com.core.scpwms.client.custom.page.support;

import com.core.scpview.client.engine.IPage;
import com.core.scpview.client.factory.ValidatorFactory;
import com.core.scpview.client.ui.InputUI;
import com.core.scpview.client.ui.element.DateUI;
import com.core.scpview.client.ui.element.NumberTextUI;
import com.core.scpview.client.ui.element.SelectTextUI;
import com.core.scpview.client.ui.element.TextFieldUI;
import com.core.scpview.client.utils.LocaleUtils;


public class PageUIFactory {
	
	// ActiveT 1304901 5/16
	public static SelectTextUI makeSkuRemoteUI(String id,String pageId,IPage ownerPage,String hsql,String dispHead,String title, String colWidths){
		SelectTextUI skuUI = new SelectTextUI(ownerPage);
		skuUI.setId(id);
		skuUI.setPageId(pageId);
		skuUI.setHql(hsql);
		skuUI.setDisplayedTableHead(dispHead);
		skuUI.setTitle(LocaleUtils.getTextWithoutException(title));
		// ActiveT 1304901 5/16
		skuUI.setColWidths(colWidths);
		return skuUI;
	}
	
	
	
	
	public static InputUI makeUIForLot(ClientLotInfo lotInfo,String uiId,boolean validate){
		
		if(ClientEnuLotFormat.LOTFORMAT_CHAR.equalsIgnoreCase(lotInfo.getLotType())){
			TextFieldUI inputUI=new TextFieldUI();
			inputUI.setTitle(lotInfo.getTitle());
			inputUI.setId(uiId);
			if(validate && ClientEnuLotFormat.LOTFIELD_2.equalsIgnoreCase(lotInfo.getInputType())){
				inputUI.setValidator(ValidatorFactory.getValidator("",true));
				inputUI.setRequired(true);
			}
			return inputUI;
		}else if(ClientEnuLotFormat.LOTFORMAT_NUMERIC.equalsIgnoreCase(lotInfo.getLotType())){
			NumberTextUI inputUI=new NumberTextUI();
			inputUI.setTitle(lotInfo.getTitle());
			inputUI.setId(uiId);
			if(validate && ClientEnuLotFormat.LOTFIELD_2.equalsIgnoreCase(lotInfo.getInputType())){
				inputUI.setValidator(ValidatorFactory.getValidator("positiveInteger",true));
				inputUI.setRequired(true);
			}else{
				inputUI.setValidator(ValidatorFactory.getValidator("positiveInteger",false));
			}
			return inputUI;
		}else if(ClientEnuLotFormat.LOTFORMAT_DATE.equalsIgnoreCase(lotInfo.getLotType())){
			DateUI inputUI=new DateUI();
			inputUI.setTitle(lotInfo.getTitle());
			inputUI.setId(uiId);
//			inputUI.setDefaultCurrentDate(true);
			inputUI.setShowTime(false);
			if(validate && ClientEnuLotFormat.LOTFIELD_2.equalsIgnoreCase(lotInfo.getInputType())){
				inputUI.setValidator(ValidatorFactory.getValidator("date",true));
				inputUI.setRequired(true);
			}else{
				inputUI.setValidator(ValidatorFactory.getValidator("date",false));
			}
			return inputUI;
		}else if(ClientEnuLotFormat.LOTFORMAT_DATETIME.equalsIgnoreCase(lotInfo.getLotType())){
			DateUI inputUI=new DateUI();
			inputUI.setTitle(lotInfo.getTitle());
			inputUI.setId(uiId);
//			inputUI.setDefaultCurrentDate(true);
			inputUI.setShowTime(true);
			if(validate && ClientEnuLotFormat.LOTFIELD_2.equalsIgnoreCase(lotInfo.getInputType())){
				inputUI.setValidator(ValidatorFactory.getValidator("datetime",true));
				inputUI.setRequired(true);
			}else{
				inputUI.setValidator(ValidatorFactory.getValidator("datetime",false));
			}
			return inputUI;
		}
		
		return null;
	}
	
}
