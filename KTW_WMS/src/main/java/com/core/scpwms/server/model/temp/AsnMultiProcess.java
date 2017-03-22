package com.core.scpwms.server.model.temp;

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
public class AsnMultiProcess extends Entity {
	Long whId;
	Long ownerId;
	Long userId;

	Long asnId0;
	Long asnId1;
	Long asnId2;
	Long asnId3;
	Long asnId4;
	Long asnId5;
	Long asnId6;
	Long asnId7;
	Long asnId8;
	Long asnId9;
	
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
	public Long getAsnId0() {
		return asnId0;
	}
	public void setAsnId0(Long asnId0) {
		this.asnId0 = asnId0;
	}
	public Long getAsnId1() {
		return asnId1;
	}
	public void setAsnId1(Long asnId1) {
		this.asnId1 = asnId1;
	}
	public Long getAsnId2() {
		return asnId2;
	}
	public void setAsnId2(Long asnId2) {
		this.asnId2 = asnId2;
	}
	public Long getAsnId3() {
		return asnId3;
	}
	public void setAsnId3(Long asnId3) {
		this.asnId3 = asnId3;
	}
	public Long getAsnId4() {
		return asnId4;
	}
	public void setAsnId4(Long asnId4) {
		this.asnId4 = asnId4;
	}
	public Long getAsnId5() {
		return asnId5;
	}
	public void setAsnId5(Long asnId5) {
		this.asnId5 = asnId5;
	}
	public Long getAsnId6() {
		return asnId6;
	}
	public void setAsnId6(Long asnId6) {
		this.asnId6 = asnId6;
	}
	public Long getAsnId7() {
		return asnId7;
	}
	public void setAsnId7(Long asnId7) {
		this.asnId7 = asnId7;
	}
	public Long getAsnId8() {
		return asnId8;
	}
	public void setAsnId8(Long asnId8) {
		this.asnId8 = asnId8;
	}
	public Long getAsnId9() {
		return asnId9;
	}
	public void setAsnId9(Long asnId9) {
		this.asnId9 = asnId9;
	}
}
