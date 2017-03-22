/**
 * (C)2014 MBP Corporation. All rights reserved.
 * 项目名称 : 华耐供应链
 * 子项目名称: 仓库系统
 * 文件名: LotInputData.java
 */

package com.core.scpwms.server.model.lot;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.core.db.server.model.DomainModel;
import com.core.scpview.client.ui.lot.ClientLotInfo;
import com.core.scpwms.server.enumerate.EnuLotFieldType;

/**
 * 
 * <p>
 * 批次属性
 * </p>
 * 
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *         2014/06/23<br/>
 */
@SuppressWarnings("all")
public class LotInputData extends DomainModel {
	public static Integer LOT_LENGTH = 16;
	private String property1;
	private String property2;
	private String property3;
	private String property4;
	private String property5;
	private String property6;
	private String property7;
	private String property8;
	private String property9;
	private String property10;

	public LotInputData() {
	}

	public LotInputData(String[] lotDate) {
		property1 = subString(lotDate[0], LOT_LENGTH);
		property2 = subString(lotDate[1], LOT_LENGTH);
		property3 = subString(lotDate[2], LOT_LENGTH);
		property4 = subString(lotDate[3], LOT_LENGTH);
		property5 = subString(lotDate[4], LOT_LENGTH);
		property6 = subString(lotDate[5], LOT_LENGTH);
		property7 = subString(lotDate[6], LOT_LENGTH);
		property8 = subString(lotDate[7], LOT_LENGTH);
		property9 = subString(lotDate[8], LOT_LENGTH);
		property10 = subString(lotDate[9], LOT_LENGTH);

	}

	private String subString(String srcString, Integer length) {
		if (srcString != null && srcString.length() > length) {
			return srcString.substring(0, length);
		}

		return srcString;
	}

	public String getProperty1() {
		return this.property1;
	}

	public void setProperty1(String property1) {
		this.property1 = StringUtils.upperCase(property1);
	}

	public String getProperty2() {
		return this.property2;
	}

	public void setProperty2(String property2) {
		this.property2 = StringUtils.upperCase(property2);
	}

	public String getProperty3() {
		return this.property3;
	}

	public void setProperty3(String property3) {
		this.property3 = StringUtils.upperCase(property3);
	}

	public String getProperty4() {
		return this.property4;
	}

	public void setProperty4(String property4) {
		this.property4 = StringUtils.upperCase(property4);
	}

	public String getProperty5() {
		return this.property5;
	}

	public void setProperty5(String property5) {
		this.property5 = StringUtils.upperCase(property5);
	}

	public String getProperty6() {
		return this.property6;
	}

	public void setProperty6(String property6) {
		this.property6 = StringUtils.upperCase(property6);
	}

	public String getProperty7() {
		return this.property7;
	}

	public void setProperty7(String property7) {
		this.property7 = StringUtils.upperCase(property7);
	}

	public String getProperty8() {
		return this.property8;
	}

	public void setProperty8(String property8) {
		this.property8 = StringUtils.upperCase(property8);
	}

	public String getProperty9() {
		return this.property9;
	}

	public void setProperty9(String property9) {
		this.property9 = StringUtils.upperCase(property9);
	}

	public String getProperty10() {
		return this.property10;
	}

	public void setProperty10(String property10) {
		this.property10 = StringUtils.upperCase(property10);
	}

	public boolean validateLotInfo(Map<String, ClientLotInfo> lotInfos) {

		boolean result = true;

		if (lotInfos.isEmpty()) {
			return true;
		}

		for (int i = 1; i < 11; i++) {
			if (lotInfos.get(LotConstant.LOT_BASE + i) != null) {
				if (EnuLotFieldType.LOTFIELD_2.equalsIgnoreCase(lotInfos.get(
						LotConstant.LOT_BASE + i).getInputType())
						&& StringUtils
								.isEmpty(getPropertieValues(LotConstant.LOT_BASE
										+ i))) {
					result = false;
					break;
				} else {
					continue;
				}
			}
		}

		return result;
	}

	public String getPropertieValues(String key) {
		if (LotConstant.LOT_1.equalsIgnoreCase(key)) {
			return this.property1;
		} else if (LotConstant.LOT_2.equalsIgnoreCase(key)) {
			return this.property2;
		} else if (LotConstant.LOT_3.equalsIgnoreCase(key)) {
			return this.property3;
		} else if (LotConstant.LOT_4.equalsIgnoreCase(key)) {
			return this.property4;
		} else if (LotConstant.LOT_5.equalsIgnoreCase(key)) {
			return this.property5;
		} else if (LotConstant.LOT_6.equalsIgnoreCase(key)) {
			return this.property6;
		} else if (LotConstant.LOT_7.equalsIgnoreCase(key)) {
			return this.property7;
		} else if (LotConstant.LOT_8.equalsIgnoreCase(key)) {
			return this.property8;
		} else if (LotConstant.LOT_9.equalsIgnoreCase(key)) {
			return this.property9;
		} else if (LotConstant.LOT_10.equalsIgnoreCase(key)) {
			return this.property10;
		} else {
			return "";
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (!StringUtils.isEmpty(property1)) {
			sb.append(property1);
			sb.append(",");
		}
		if (!StringUtils.isEmpty(property2)) {
			sb.append(property2);
			sb.append(",");
		}
		if (!StringUtils.isEmpty(property3)) {
			sb.append(property3);
			sb.append(",");
		}
		if (!StringUtils.isEmpty(property4)) {
			sb.append(property4);
			sb.append(",");
		}
		if (!StringUtils.isEmpty(property5)) {
			sb.append(property5);
			sb.append(",");
		}
		if (!StringUtils.isEmpty(property6)) {
			sb.append(property6);
			sb.append(",");
		}
		if (!StringUtils.isEmpty(property7)) {
			sb.append(property7);
			sb.append(",");
		}
		if (!StringUtils.isEmpty(property8)) {
			sb.append(property8);
			sb.append(",");
		}
		if (!StringUtils.isEmpty(property9)) {
			sb.append(property9);
			sb.append(",");
		}
		if (!StringUtils.isEmpty(property10)) {
			sb.append(property10);
			sb.append(",");
		}

		String lotStr = sb.toString();
		if (lotStr.endsWith(",")) {
			lotStr = lotStr.substring(0, lotStr.length() - 1);
		}

		return lotStr;
	}

	public Map<String, String> getLotInfo() {
		Map<String, String> lotInfo = new HashMap<String, String>();
		if (!StringUtils.isEmpty(property1)) {
			lotInfo.put("LOT_1", property1);
		}
		if (!StringUtils.isEmpty(property2)) {
			lotInfo.put("LOT_2", property2);
		}
		if (!StringUtils.isEmpty(property3)) {
			lotInfo.put("LOT_3", property3);
		}
		if (!StringUtils.isEmpty(property4)) {
			lotInfo.put("LOT_4", property4);
		}
		if (!StringUtils.isEmpty(property5)) {
			lotInfo.put("LOT_5", property5);
		}
		if (!StringUtils.isEmpty(property6)) {
			lotInfo.put("LOT_6", property6);
		}
		if (!StringUtils.isEmpty(property7)) {
			lotInfo.put("LOT_7", property7);
		}
		if (!StringUtils.isEmpty(property8)) {
			lotInfo.put("LOT_8", property8);
		}
		if (!StringUtils.isEmpty(property9)) {
			lotInfo.put("LOT_9", property9);
		}
		if (!StringUtils.isEmpty(property10)) {
			lotInfo.put("LOT_10", property10);
		}
		return lotInfo;
	}

	public void setLotInfo(Map<String, Object> params) {
		if (params.get("LOT_1") != null
				&& !StringUtils.isEmpty(params.get("LOT_1").toString())) {
			this.setProperty1((String) params.get("LOT_1"));
		} else {
			this.setProperty1(null);
		}
		if (params.get("LOT_2") != null
				&& !StringUtils.isEmpty(params.get("LOT_2").toString())) {
			this.setProperty2((String) params.get("LOT_2"));
		} else {
			this.setProperty2(null);
		}
		if (params.get("LOT_3") != null
				&& !StringUtils.isEmpty(params.get("LOT_3").toString())) {
			this.setProperty3((String) params.get("LOT_3"));
		} else {
			this.setProperty3(null);
		}
		if (params.get("LOT_4") != null
				&& !StringUtils.isEmpty(params.get("LOT_4").toString())) {
			this.setProperty4((String) params.get("LOT_4"));
		} else {
			this.setProperty4(null);
		}
		if (params.get("LOT_5") != null
				&& !StringUtils.isEmpty(params.get("LOT_5").toString())) {
			this.setProperty5((String) params.get("LOT_5"));
		} else {
			this.setProperty5(null);
		}
		if (params.get("LOT_6") != null
				&& !StringUtils.isEmpty(params.get("LOT_6").toString())) {
			this.setProperty6((String) params.get("LOT_6"));
		} else {
			this.setProperty6(null);
		}
		if (params.get("LOT_7") != null
				&& !StringUtils.isEmpty(params.get("LOT_7").toString())) {
			this.setProperty7((String) params.get("LOT_7"));
		} else {
			this.setProperty7(null);
		}
		if (params.get("LOT_8") != null
				&& !StringUtils.isEmpty(params.get("LOT_8").toString())) {
			this.setProperty8((String) params.get("LOT_8"));
		} else {
			this.setProperty8(null);
		}
		if (params.get("LOT_9") != null
				&& !StringUtils.isEmpty(params.get("LOT_9").toString())) {
			this.setProperty9((String) params.get("LOT_9"));
		} else {
			this.setProperty9(null);
		}
		if (params.get("LOT_10") != null
				&& !StringUtils.isEmpty(params.get("LOT_10").toString())) {
			this.setProperty10((String) params.get("LOT_10"));
		} else {
			this.setProperty10(null);
		}
	}
}
