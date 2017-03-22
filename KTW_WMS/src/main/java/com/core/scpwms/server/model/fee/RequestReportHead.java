package com.core.scpwms.server.model.fee;

import java.util.Date;

import com.core.business.model.TrackingEntity;
import com.core.scpwms.server.model.user.Owner;

/**
 * 
 * <p>请求书头部</p>
 *
 * @version 1.0
 * @author MBP :毛　幸<br/>
 * 変更履歴 <br/>
 *  2013-6-11 : MBP 毛　幸: 新規作成<br/>
 */
@SuppressWarnings("all")
public class RequestReportHead extends TrackingEntity {

    /** 货主 */
    private Owner owner;

    /** 状态 　@see EnuRequestReportStatus　*/
    private Long status;

    /** 执行日时 */
    private Date processDate;

    /** 基本金 */
    private Double baseAmount;

    /** 請求金額 */
    private Double sumAmount;

    /** 締め日 */
    private Long accountDateDiv;

    /** 期 
     * @EnuTermDiv
     * */
    private String termDiv;

    /** 対象月 */
    private Integer targetMonth;

    /** 备注 */
    private String description;

    /** 期間FROM */
    private Date dayFrom;

    /** 期間TO */
    private Date dayTo;
    
    /** 2-1期FROM */
    private Date term21F;

    /** 2-1期TO */
    private Date term21T;

    /** 2-2期FROM */
    private Date term22F;

    /** 2-2期TO */
    private Date term22T;

    /** 3-1期FROM */
    private Date dayF1;

    /** 3-1期TO */
    private Date dayF2;

    /** 3-2期FROM */
    private Date dayF3;

    /** 3-2期TO */
    private Date dayT1;

    /** 3-3期FROM */
    private Date dayT2;

    /** 3-3期TO */
    private Date dayT3;

    /** 税率 */
    private Integer rate;

    /** 发行元（公司） */
    private Company company;

    public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

    public Long getStatus() {
        return this.status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Date getProcessDate() {
        return this.processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public Double getBaseAmount() {
        return this.baseAmount;
    }

    public void setBaseAmount(Double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Long getAccountDateDiv() {
        return this.accountDateDiv;
    }

    public void setAccountDateDiv(Long accountDateDiv) {
        this.accountDateDiv = accountDateDiv;
    }

    public String getTermDiv() {
        return this.termDiv;
    }

    public void setTermDiv(String termDiv) {
        this.termDiv = termDiv;
    }

    public Integer getTargetMonth() {
        return this.targetMonth;
    }

    public void setTargetMonth(Integer targetMonth) {
        this.targetMonth = targetMonth;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDayFrom() {
        return this.dayFrom;
    }

    public void setDayFrom(Date dayFrom) {
        this.dayFrom = dayFrom;
    }

    public Date getDayTo() {
        return this.dayTo;
    }

    public void setDayTo(Date dayTo) {
        this.dayTo = dayTo;
    }

    public Date getDayF1() {
        return this.dayF1;
    }

    public void setDayF1(Date dayF1) {
        this.dayF1 = dayF1;
    }

    public Date getDayF2() {
        return this.dayF2;
    }

    public void setDayF2(Date dayF2) {
        this.dayF2 = dayF2;
    }

    public Date getDayF3() {
        return this.dayF3;
    }

    public void setDayF3(Date dayF3) {
        this.dayF3 = dayF3;
    }

    public Date getDayT1() {
        return this.dayT1;
    }

    public void setDayT1(Date dayT1) {
        this.dayT1 = dayT1;
    }

    public Date getDayT2() {
        return this.dayT2;
    }

    public void setDayT2(Date dayT2) {
        this.dayT2 = dayT2;
    }

    public Date getDayT3() {
        return this.dayT3;
    }

    public void setDayT3(Date dayT3) {
        this.dayT3 = dayT3;
    }

    public Double getSumAmount() {
        return this.sumAmount;
    }

    public void setSumAmount(Double sumAmount) {
        this.sumAmount = sumAmount;
    }

    public Integer getRate() {
        return this.rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

	public Date getTerm21F() {
		return term21F;
	}

	public void setTerm21F(Date term21f) {
		term21F = term21f;
	}

	public Date getTerm21T() {
		return term21T;
	}

	public void setTerm21T(Date term21t) {
		term21T = term21t;
	}

	public Date getTerm22F() {
		return term22F;
	}

	public void setTerm22F(Date term22f) {
		term22F = term22f;
	}

	public Date getTerm22T() {
		return term22T;
	}

	public void setTerm22T(Date term22t) {
		term22T = term22t;
	}

}
