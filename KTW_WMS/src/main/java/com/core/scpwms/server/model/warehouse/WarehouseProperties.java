package com.core.scpwms.server.model.warehouse;

import java.util.Set;

import com.core.business.model.TrackingEntity;
import com.core.business.model.domain.ContractInfo;

/**
 * 仓库基本类 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class WarehouseProperties extends TrackingEntity {
    /** 所属仓库 */
    private Warehouse wh;

    /** 总面积(M2) */
    private Double totalArea;

    /** 仓库面积(M2) */
    private Double warehouseArea;

    /** 办公区面积(M2) */
    private Double officeArea;

    /** 加工厂面积(M2) */
    private Double factoryArea;

    /** 其他面积(M2) */
    private Double otherArea;

    /** 有效高度 */
    private Double height;

    /** 管理人员 */
    private Long managerNum;

    /** 叉车司机 */
    private Long forkLiftDriverNum;

    /** 理货员 */
    private Long tallyManNum;

    /** 货架数量 */
    private Long shelfNum;

    /** 叉车数量 */
    private Long forkLiftNum;

    /** 堆高车数量 */
    private Long stakerNum;

    /** 手动液压叉车数量 */
    private Long mhfNum;

    /** 租金(天/M2) */
    private Double rental;

    /** 年租金 */
    private Double rentalYear;
    
    /** OQC比例(%) */
    private Double oqcRate;

    public Warehouse getWh() {
        return this.wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }

    public Double getTotalArea() {
        return this.totalArea;
    }

    public void setTotalArea(Double totalArea) {
        this.totalArea = totalArea;
    }

    public Double getWarehouseArea() {
        return this.warehouseArea;
    }

    public void setWarehouseArea(Double warehouseArea) {
        this.warehouseArea = warehouseArea;
    }

    public Double getOfficeArea() {
        return this.officeArea;
    }

    public void setOfficeArea(Double officeArea) {
        this.officeArea = officeArea;
    }

    public Double getFactoryArea() {
        return this.factoryArea;
    }

    public void setFactoryArea(Double factoryArea) {
        this.factoryArea = factoryArea;
    }

    public Double getOtherArea() {
        return this.otherArea;
    }

    public void setOtherArea(Double otherArea) {
        this.otherArea = otherArea;
    }

    public Double getHeight() {
        return this.height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Long getManagerNum() {
        return this.managerNum;
    }

    public void setManagerNum(Long managerNum) {
        this.managerNum = managerNum;
    }

    public Long getForkLiftDriverNum() {
        return this.forkLiftDriverNum;
    }

    public void setForkLiftDriverNum(Long forkLiftDriverNum) {
        this.forkLiftDriverNum = forkLiftDriverNum;
    }

    public Long getTallyManNum() {
        return this.tallyManNum;
    }

    public void setTallyManNum(Long tallyManNum) {
        this.tallyManNum = tallyManNum;
    }

    public Long getShelfNum() {
        return this.shelfNum;
    }

    public void setShelfNum(Long shelfNum) {
        this.shelfNum = shelfNum;
    }

    public Long getForkLiftNum() {
        return this.forkLiftNum;
    }

    public void setForkLiftNum(Long forkLiftNum) {
        this.forkLiftNum = forkLiftNum;
    }

    public Long getStakerNum() {
        return this.stakerNum;
    }

    public void setStakerNum(Long stakerNum) {
        this.stakerNum = stakerNum;
    }

    public Long getMhfNum() {
        return this.mhfNum;
    }

    public void setMhfNum(Long mhfNum) {
        this.mhfNum = mhfNum;
    }

    public Double getRental() {
        return this.rental;
    }

    public void setRental(Double rental) {
        this.rental = rental;
    }

    public Double getRentalYear() {
        return this.rentalYear;
    }

    public void setRentalYear(Double rentalYear) {
        this.rentalYear = rentalYear;
    }

	public Double getOqcRate() {
		return this.oqcRate;
	}

	public void setOqcRate(Double oqcRate) {
		this.oqcRate = oqcRate;
	}

}
