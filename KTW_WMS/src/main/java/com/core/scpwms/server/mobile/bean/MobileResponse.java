package com.core.scpwms.server.mobile.bean;

import java.util.HashMap;
import java.util.Map;

import com.core.scpwms.server.util.LocaleUtil;
import com.google.gwt.user.client.rpc.IsSerializable;

public class MobileResponse implements IsSerializable{
	
	// 消息类型
	// M：消息Message，“处理成功。”
	// M1：重要消息Message，需要弹出框确认。“密码已经更新！”
	// E：报错Error，“超过库存可用量，无法执行！”
	// W：警告消息Warning，“收货完成，有待质检商品，请注意及时处理！”
	// C:确认信息Confirm，“本批次的生产日期早于库存的生产日期，确定要收货？”
	private String severityMsgType = "M";

	// 跳转的画面id
	private String targetPageId;
	
	// 消息内容
	private String severityMsg;
	
	// 返回值
	private Map<String,Object> results = new HashMap<String,Object>();

	public String getSeverityMsgType() {
		return this.severityMsgType;
	}

	public void setSeverityMsgType(String severityMsgType) {
		this.severityMsgType = severityMsgType;
	}

	public String getTargetPageId() {
		return this.targetPageId;
	}

	public void setTargetPageId(String targetPageId) {
		this.targetPageId = targetPageId;
	}

	public String getSeverityMsg() {
		return this.severityMsg;
	}

	public void setSeverityMsg(String severityMsg) {
		this.severityMsg = LocaleUtil.getText(severityMsg);
	}

	public Map<String, Object> getResults() {
		return this.results;
	}

	public void setResults(Map<String, Object> results) {
		this.results = results;
	}

}
