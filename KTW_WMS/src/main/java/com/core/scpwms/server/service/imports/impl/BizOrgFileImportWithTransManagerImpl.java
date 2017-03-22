package com.core.scpwms.server.service.imports.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.core.business.model.domain.ContractInfo;
import com.core.business.model.domain.CreateInfo;
import com.core.business.model.domain.UpdateInfo;
import com.core.business.web.security.UserHolder;
import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpwms.server.enumerate.EnuBusRolsType;
import com.core.scpwms.server.filter.WarehouseHolder;
import com.core.scpwms.server.model.user.BizOrg;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.user.Plant;
import com.core.scpwms.server.service.imports.BizOrgFileImportWithTransManager;

public class BizOrgFileImportWithTransManagerImpl extends BaseFileImportWithTransManagerImpl implements BizOrgFileImportWithTransManager {
	
	//0:ID	
	//1:管理会社番号	
	//2:管理会社名	
	//3:ベンダー番号	
	//4:ベンダー名	
	//5:略称	
	//6:状態	
	//7:備考	
	//8:連絡人	
	//9:携帯番号	
	//10:電話番号	
	//11:FAX	
	//12:電子メール	
	//13:郵便番号	
	//14:住所1	
	//15:住所2
	@Override
	public Boolean createVendor(List<String> excelLine, int index, List<String[]> result) {
		excelLine = formatExcelLine(excelLine, 16);
		
		if( StringUtils.isEmpty(excelLine.get(1).trim()) ){
			result.add(new String[]{String.valueOf(index), "管理会社番号：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(3).trim()) ){
			result.add(new String[]{String.valueOf(index), "ベンダー番号：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(4).trim()) ){
			result.add(new String[]{String.valueOf(index), "ベンダー名：必須項目。"});
			return null;
		}
		
		Plant p = getPlant(excelLine.get(1).trim(), WarehouseHolder.getWarehouse().getId());
		if( p == null ){
			result.add(new String[]{ String.valueOf(index), "管理会社番号：" + excelLine.get(1) + "、マスタデータ不備。" });
			return null;
		}
		
		BizOrg vendor = getVendor(p.getId(), excelLine.get(3).trim());
		ContractInfo ci = null;
		Boolean isNew = null;
		//　新規
		if( vendor == null ){
			vendor = new BizOrg();
			vendor.setPlant(p);
			vendor.setType(EnuBusRolsType.V);
			vendor.setCode(excelLine.get(3).trim());
			
			ci = new ContractInfo();
			vendor.setContractInfo(ci);
			
			vendor.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			isNew = Boolean.TRUE;
		}
		//　更新
		else{
			ci = vendor.getContractInfo();
			isNew = Boolean.FALSE;
		}
		
		vendor.setName(excelLine.get(4).trim());
		vendor.setShortName(excelLine.get(5).trim());
		vendor.setDisabled(getDisableDiv(excelLine.get(6).trim()));
		vendor.setDescription(excelLine.get(7).trim());
		
		//8:連絡人	
		//9:携帯番号	
		//10:電話番号	
		//11:FAX	
		//12:電子メール	
		//13:郵便番号	
		//14:住所1	
		//15:住所2
		ci.setLinkman(excelLine.get(8));
		ci.setMobile(excelLine.get(9));
		ci.setPhone(excelLine.get(10));
		ci.setFax(excelLine.get(11));
		ci.setEmail(excelLine.get(12));
		ci.setPostcode(excelLine.get(13));
		ci.setAddress(excelLine.get(14));
		ci.setAddressExt(excelLine.get(15));
		
		vendor.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		
		commonDao.store(vendor);
		return isNew;
	}
	
	//0:ID	
	//1:荷主番号	
	//2:荷主名	
	//3:出荷先番号	
	//4:出荷先名	
	//5:略称	
	//6:状態	
	//7:備考	
	//8:連絡人	
	//9:携帯番号	
	//10:電話番号	
	//11:FAX	
	//12:電子メール	
	//13:郵便番号	
	//14:住所1	
	//15:住所2
	@Override
	public Boolean createShop(List<String> excelLine, int index, List<String[]> result) {
		excelLine = formatExcelLine(excelLine, 16);
		
		if( StringUtils.isEmpty(excelLine.get(1).trim()) ){
			result.add(new String[]{String.valueOf(index), "荷主番号	：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(3).trim()) ){
			result.add(new String[]{String.valueOf(index), "出荷先番号：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(4).trim()) ){
			result.add(new String[]{String.valueOf(index), "出荷先名：必須項目。"});
			return null;
		}
		
		Owner owner = getOwner(excelLine.get(1).trim(), WarehouseHolder.getWarehouse().getId());
		if( owner == null ){
			result.add(new String[]{ String.valueOf(index), "荷主番号：" + excelLine.get(1) + "、マスタデータ不備。" });
			return null;
		}
		
		BizOrg shop = getShop(excelLine.get(3).trim(), owner.getId());
		ContractInfo ci = null;
		Boolean isNew = null;
		//　新規
		if( shop == null ){
			shop = new BizOrg();
			shop.setPlant(owner.getPlant());
			shop.setOwner(owner);
			shop.setType(EnuBusRolsType.C);
			shop.setCode(excelLine.get(3).trim());
			
			ci = new ContractInfo();
			shop.setContractInfo(ci);
			
			shop.setCreateInfo(new CreateInfo(UserHolder.getUser()));
			isNew = Boolean.TRUE;
		}
		//　更新
		else{
			ci = shop.getContractInfo();
			isNew = Boolean.FALSE;
		}
		
		shop.setName(excelLine.get(4).trim());
		shop.setShortName(excelLine.get(5).trim());
		shop.setDisabled(getDisableDiv(excelLine.get(6).trim()));
		shop.setDescription(excelLine.get(7).trim());
		
		//8:連絡人	
		//9:携帯番号	
		//10:電話番号	
		//11:FAX	
		//12:電子メール	
		//13:郵便番号	
		//14:住所1	
		//15:住所2
		ci.setLinkman(excelLine.get(8));
		ci.setMobile(excelLine.get(9));
		ci.setPhone(excelLine.get(10));
		ci.setFax(excelLine.get(11));
		ci.setEmail(excelLine.get(12));
		ci.setPostcode(excelLine.get(13));
		ci.setAddress(excelLine.get(14));
		ci.setAddressExt(excelLine.get(15));
		
		shop.setUpdateInfo(new UpdateInfo(UserHolder.getUser()));
		
		commonDao.store(shop);
		return isNew;
	}
}
