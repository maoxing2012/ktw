package com.core.scpwms.server.model.rules;

import com.core.db.server.model.DomainModel;
import com.core.scpwms.server.model.lot.LotInputData;

/**
 * 上架规则内的批次条件设定
 * 只有SKU选中，此页内输入的条件才生效
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("all")
public class PutAwayRuleLot extends DomainModel{

	/**
	 * 批次属性 1 - 10
	 * 记录10个批次属性的起始
	 */
	private LotInputData lotData1 = new LotInputData();
	
	/**
	 * 批次属性 1 - 10
	 * 记录10个批次属性的结束
	 */
	private LotInputData lotData2 = new LotInputData();
	
	/**
	 * 10个操作符，设置起始条件
	 * 对应 lotData1
	 */
	private String opt_start1;
	private String opt_start2;
	private String opt_start3;
	private String opt_start4;
	private String opt_start5;
	private String opt_start6;
	private String opt_start7;
	private String opt_start8;
	private String opt_start9;
	private String opt_start10;

	/**
	 * 10个操作符，设置结束条件
	 * 对应lotData2
	 */
	private String opt_end1;
	private String opt_end2;
	private String opt_end3;
	private String opt_end4;
	private String opt_end5;
	private String opt_end6;
	private String opt_end7;
	private String opt_end8;
	private String opt_end9;
	private String opt_end10;
	
	private String optMemo;
	
	
	public LotInputData getLotData1() {
		return lotData1;
	}
	public void setLotData1(LotInputData lotData1) {
		this.lotData1 = lotData1;
	}
	public LotInputData getLotData2() {
		return lotData2;
	}
	public void setLotData2(LotInputData lotData2) {
		this.lotData2 = lotData2;
	}
	public String getOpt_start1() {
		return opt_start1;
	}
	public void setOpt_start1(String optStart1) {
		opt_start1 = optStart1;
	}
	public String getOpt_start2() {
		return opt_start2;
	}
	public void setOpt_start2(String optStart2) {
		opt_start2 = optStart2;
	}
	public String getOpt_start3() {
		return opt_start3;
	}
	public void setOpt_start3(String optStart3) {
		opt_start3 = optStart3;
	}
	public String getOpt_start4() {
		return opt_start4;
	}
	public void setOpt_start4(String optStart4) {
		opt_start4 = optStart4;
	}
	public String getOpt_start5() {
		return opt_start5;
	}
	public void setOpt_start5(String optStart5) {
		opt_start5 = optStart5;
	}
	public String getOpt_start6() {
		return opt_start6;
	}
	public void setOpt_start6(String optStart6) {
		opt_start6 = optStart6;
	}
	public String getOpt_start7() {
		return opt_start7;
	}
	public void setOpt_start7(String optStart7) {
		opt_start7 = optStart7;
	}
	public String getOpt_start8() {
		return opt_start8;
	}
	public void setOpt_start8(String optStart8) {
		opt_start8 = optStart8;
	}
	public String getOpt_start9() {
		return opt_start9;
	}
	public void setOpt_start9(String optStart9) {
		opt_start9 = optStart9;
	}
	public String getOpt_start10() {
		return opt_start10;
	}
	public void setOpt_start10(String optStart10) {
		opt_start10 = optStart10;
	}
	public String getOpt_end1() {
		return opt_end1;
	}
	public void setOpt_end1(String optEnd1) {
		opt_end1 = optEnd1;
	}
	public String getOpt_end2() {
		return opt_end2;
	}
	public void setOpt_end2(String optEnd2) {
		opt_end2 = optEnd2;
	}
	public String getOpt_end3() {
		return opt_end3;
	}
	public void setOpt_end3(String optEnd3) {
		opt_end3 = optEnd3;
	}
	public String getOpt_end4() {
		return opt_end4;
	}
	public void setOpt_end4(String optEnd4) {
		opt_end4 = optEnd4;
	}
	public String getOpt_end5() {
		return opt_end5;
	}
	public void setOpt_end5(String optEnd5) {
		opt_end5 = optEnd5;
	}
	public String getOpt_end6() {
		return opt_end6;
	}
	public void setOpt_end6(String optEnd6) {
		opt_end6 = optEnd6;
	}
	public String getOpt_end7() {
		return opt_end7;
	}
	public void setOpt_end7(String optEnd7) {
		opt_end7 = optEnd7;
	}
	public String getOpt_end8() {
		return opt_end8;
	}
	public void setOpt_end8(String optEnd8) {
		opt_end8 = optEnd8;
	}
	public String getOpt_end9() {
		return opt_end9;
	}
	public void setOpt_end9(String optEnd9) {
		opt_end9 = optEnd9;
	}
	public String getOpt_end10() {
		return opt_end10;
	}
	public void setOpt_end10(String optEnd10) {
		opt_end10 = optEnd10;
	}
	public String getOptMemo() {
		return optMemo;
	}
	public void setOptMemo(String optMemo) {
		this.optMemo = optMemo;
	}
	
	
	
}
