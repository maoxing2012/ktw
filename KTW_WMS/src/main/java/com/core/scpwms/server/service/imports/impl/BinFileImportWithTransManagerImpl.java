package com.core.scpwms.server.service.imports.impl;

import java.util.ArrayList;
import java.util.List;

import com.core.db.server.service.pojo.DefaultBaseManager;
import com.core.scpview.client.utils.StringUtils;
import com.core.scpwms.server.domain.BinDpsInfo;
import com.core.scpwms.server.enumerate.EnuBinLockType;
import com.core.scpwms.server.model.warehouse.Bin;
import com.core.scpwms.server.model.warehouse.BinProperties;
import com.core.scpwms.server.model.warehouse.StorageType;
import com.core.scpwms.server.model.warehouse.Warehouse;
import com.core.scpwms.server.model.warehouse.WhArea;
import com.core.scpwms.server.service.imports.BinFileImportWithTransManager;

public class BinFileImportWithTransManagerImpl extends BaseFileImportWithTransManagerImpl implements BinFileImportWithTransManager {
	private static int COLUMN_NUM = 20;
	// ID
//	private static int INX_BIN_ID = 0;
	// WH CODE
	private static int INX_WH_CD = 1;
	// 库区编号
	private static int INX_WA_CD = 2;
	// 功能区编号
	private static int INX_ST_CODE = 3;
	// 库位属性编号
	private static int INX_BIN_PRO_CODE = 4;
	// 库区名称
//	private static int INX_WA_NM = 5;
	// 功能区名称
//	private static int INX_ST_NM = 6;
	// 库位属性名称
//	private static int INX_BIN_PRO_NM = 7;
	// 库位编号
	private static int INX_BIN_CODE = 8;
	// 有效
	private static int INX_DISABLED = 9;
	// 过道
	private static int INX_AISLE = 10;
	// 开间
	private static int INX_STACK = 11;
	// 列
	private static int INX_DEPTH = 12;
	// 层
	private static int INX_LEVEL = 13;
	// 锁名称
	private static int INX_LOCK_NM = 14;
	// 锁原因
	private static int INX_LOCK_RS = 15;
	// 商品混放
	private static int INX_SKU_MIX = 16;
	// 批次混放
	private static int INX_LOT_MIX = 17;
	// 货主混放
	private static int INX_OWNER_MIX = 18;
	// 备注
	private static int INX_DESC = 19;
	@Override
	public Boolean createBin(List<String> excelLine, int index, List<String[]> result) {
		excelLine = formatExcelLine(excelLine, COLUMN_NUM);
		
		if( StringUtils.isEmpty(excelLine.get(INX_WH_CD).trim()) ){
			result.add(new String[]{String.valueOf(index), "倉庫番号	：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(INX_WA_CD).trim()) ){
			result.add(new String[]{String.valueOf(index), "倉庫エリア番号	：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(INX_ST_CODE).trim()) ){
			result.add(new String[]{String.valueOf(index), "保管ゾーン番号	：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(INX_BIN_PRO_CODE).trim()) ){
			result.add(new String[]{String.valueOf(index), "棚タイプ番号：必須項目。"});
			return null;
		}
		
		if( StringUtils.isEmpty(excelLine.get(INX_BIN_CODE).trim()) ){
			result.add(new String[]{String.valueOf(index), "棚番号：必須項目。"});
			return null;
		}
		
		Warehouse wh = getWarehouse(excelLine.get(INX_WH_CD).trim());
		if( wh == null ){
			result.add(new String[]{ String.valueOf(index), "倉庫番号：" + excelLine.get(INX_WH_CD).trim() + "、マスタデータ不備。" });
			return null;
		}
		
		WhArea whArea = getWhArea(excelLine.get(INX_WA_CD).trim(), wh.getId());
		if( whArea == null ){
			result.add(new String[]{ String.valueOf(index), "倉庫エリア番号：" + excelLine.get(INX_WA_CD).trim() + "、マスタデータ不備。" });
			return null;
		}
		
		StorageType st = getStorageType(excelLine.get(INX_ST_CODE).trim(), whArea.getId());
		if( st == null ){
			result.add(new String[]{ String.valueOf(index), "保管ゾーン番号：" + excelLine.get(INX_ST_CODE).trim() + "、マスタデータ不備。" });
			return null;
		}
		
		BinProperties bp = getBinProperties(excelLine.get(INX_BIN_PRO_CODE).trim(), wh.getId());
		if( bp == null ){
			result.add(new String[]{ String.valueOf(index), "棚タイプ番号：" + excelLine.get(INX_BIN_PRO_CODE).trim() + "、マスタデータ不備。" });
			return null;
		}
		
		Boolean isNew = null;
		Bin bin = getBin(excelLine.get(INX_BIN_CODE).trim(), wh.getId());
		if (bin == null) {
			bin = new Bin();
			isNew = Boolean.TRUE;
		}
		else{
			isNew = Boolean.FALSE;
		}
		bin.setWh(wh);
		bin.setStorageType(st);
		bin.setProperties(bp);
		bin.setBinCode(excelLine.get(INX_BIN_CODE).trim());
		bin.setDescription(excelLine.get(INX_DESC).trim());

		// 过道，开间，列，层
		bin.setAisle(getInteger(excelLine.get(INX_AISLE).trim()));
		bin.setStack(getInteger(excelLine.get(INX_STACK).trim()));
		bin.setDepth(getInteger(excelLine.get(INX_DEPTH).trim()));
		bin.setLevel(getInteger(excelLine.get(INX_LEVEL).trim()));
		
		// 商品混放
		bin.getProcessProperties().setSkuMixed(getCanDiv(excelLine.get(INX_SKU_MIX).trim()));
		// 批次混放
		bin.getProcessProperties().setLotMixed(getCanDiv(excelLine.get(INX_LOT_MIX).trim()));
		// 货主混放
		bin.getProcessProperties().setOwnerMixed(getCanDiv(excelLine.get(INX_OWNER_MIX).trim()));
		
		// 有效
		bin.setDisabled(getDisableDiv(excelLine.get(INX_DISABLED).trim()));
		
		// 库位锁
		bin.setLockStatus(getBinLockDiv(excelLine.get(INX_LOCK_NM).trim()));
		bin.setLockReason(excelLine.get(INX_LOCK_RS	).trim());
		
		// 保存库位信息
		commonDao.store(bin);

		// 保存库容信息
		bin.getBinInvInfo().setBin(bin);
		commonDao.store(bin.getBinInvInfo());

		return isNew;
	}

}
