package com.core.scpwms.server.model.task;

import java.util.Date;

import com.core.db.server.model.Entity;
import com.core.scpwms.server.model.outbound.WaveDoc;
import com.core.scpwms.server.model.warehouse.Labor;

/**
 * 散件拣货中间表
 * @author mousachi
 *
 */
@SuppressWarnings("all")
public class WarehouseTaskProcess extends Entity {
	Long whId;
	Long ownerId;
	Long userId;
	String caseNumberA;
	String caseNumberB;
	String caseNumberC;
	String caseNumberD;
	String caseNumberE;
	String caseNumberF;
	String caseNumberG;
	String caseNumberH;
	String caseNumberI;
	
	public Long getWhId() {
		return whId;
	}
	public void setWhId(Long whId) {
		this.whId = whId;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCaseNumberA() {
		return caseNumberA;
	}
	public void setCaseNumberA(String caseNumberA) {
		this.caseNumberA = caseNumberA;
	}
	public String getCaseNumberB() {
		return caseNumberB;
	}
	public void setCaseNumberB(String caseNumberB) {
		this.caseNumberB = caseNumberB;
	}
	public String getCaseNumberC() {
		return caseNumberC;
	}
	public void setCaseNumberC(String caseNumberC) {
		this.caseNumberC = caseNumberC;
	}
	public String getCaseNumberD() {
		return caseNumberD;
	}
	public void setCaseNumberD(String caseNumberD) {
		this.caseNumberD = caseNumberD;
	}
	public String getCaseNumberE() {
		return caseNumberE;
	}
	public void setCaseNumberE(String caseNumberE) {
		this.caseNumberE = caseNumberE;
	}
	public String getCaseNumberF() {
		return caseNumberF;
	}
	public void setCaseNumberF(String caseNumberF) {
		this.caseNumberF = caseNumberF;
	}
	public String getCaseNumberG() {
		return caseNumberG;
	}
	public void setCaseNumberG(String caseNumberG) {
		this.caseNumberG = caseNumberG;
	}
	public String getCaseNumberH() {
		return caseNumberH;
	}
	public void setCaseNumberH(String caseNumberH) {
		this.caseNumberH = caseNumberH;
	}
	public String getCaseNumberI() {
		return caseNumberI;
	}
	public void setCaseNumberI(String caseNumberI) {
		this.caseNumberI = caseNumberI;
	}

}
