package com.core.scpwms.server.model.lot;

import com.core.db.server.model.DomainModel;
import com.core.scpview.client.ui.lot.ClientLotInfo;
import com.core.scpwms.server.enumerate.EnuLotFieldType;

/**
 * 批次属性详细 每个批次属性内包含10条明细 新建批次属性，即新建10条明细
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 * 
 */
@SuppressWarnings("all")
public class LotDetailInfo extends DomainModel {

	/** 批次属性显示标题 */
	private String title;

	/**
	 * 批次属性数据类型
	 * 
	 * @See EnuLotFormat
	 * */
	private String lotType;

	/**
	 * 批次属性数据输入控制 控制是否启用
	 * 
	 * @see EnuLotFieldType
	 * */
	private String inputType = EnuLotFieldType.LOTFIELD_1;

	/**
	 * 选择框内容的属性Key
	 */
	private String enuCode;

	/**
	 * 能输入的最大长度
	 */
	private Integer length;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLotType() {
		return this.lotType;
	}

	public void setLotType(String lotType) {
		this.lotType = lotType;
	}

	public String getInputType() {
		return this.inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getEnuCode() {
		return this.enuCode;
	}

	public void setEnuCode(String enuCode) {
		this.enuCode = enuCode;
	}

	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	@Override
	public String toString() {
		if (EnuLotFieldType.LOTFIELD_1.equals(inputType)) {
			return inputType;
		}

		else if (inputType != null) {
			return title + "," + lotType + "," + inputType
					+ (null == enuCode ? "" : "," + enuCode);
		}

		return null;
	}

	public ClientLotInfo convertToClient() {
		ClientLotInfo rv = new ClientLotInfo();
		rv.setEnuCode(this.getEnuCode());
		rv.setInputType(this.getInputType());
		rv.setLotType(this.getLotType());
		rv.setTitle(this.getTitle());
		return rv;
	}

}
