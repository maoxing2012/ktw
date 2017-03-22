package com.core.scpwms.server.model.common;

import com.core.business.model.TrackingEntity;

/**
 * 包装信息
 * 包含基本包装描述
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 *
 */
@SuppressWarnings("all")
public class PackageDetail extends TrackingEntity {
	/** 代码*/
	private String code;
	
	/** 名称 */
	private String name;
	
	/** 包装层级 */
	private String packageLevel;
	
	/** 包装信息 */
	private PackageInfo packageInfo;
	
	/** 包装单位的长 默认为0*/
	private Double length = 0D;
	
	/** 包装单位的宽 默认为0*/
	private Double width = 0D;
	
	/** 包装单位的高 默认为0*/
	private Double height = 0D;
	
	/** 包装单位的体积 默认为0*/
	private Double volume = 0D;
	
	/** 使用包材 */
	private Container container;
	
	/** 
	 * 最小包装折算系数
	 * 基本单位默认为1
	 * 画面必输项目
	 * */
	private Double coefficient = 0D;
	
	/** 是否使用托盘 */
	private Boolean usePallet = Boolean.FALSE;
	
	/** 包装码盘信息 */
	private PalletInfo palletInfo;
	
	/** 包装精度 */
	private Long precision = 0L;
	
	/** 是否是主单位( true:是 ) */
	private Boolean mainPackage = Boolean.FALSE;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageLevel() {
		return packageLevel;
	}

	public void setPackageLevel(String packageLevel) {
		this.packageLevel = packageLevel;
	}

	public Double getLength() {
		if(length == null)
			return 0D;
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		if(width == null)
			return 0D;
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		if(height == null)
			return 0D;		
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getVolume() {
		if(volume == null)
			return 0D;
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getCoefficient() {
		if(coefficient == null)
			return 0D;	
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	public PalletInfo getPalletInfo() {
		return palletInfo;
	}

	public void setPalletInfo(PalletInfo palletInfo) {
		this.palletInfo = palletInfo;
	}

	public Boolean getUsePallet() {
		return usePallet;
	}

	public void setUsePallet(Boolean usePallet) {
		this.usePallet = usePallet;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public PackageInfo getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(PackageInfo packageInfo) {
		this.packageInfo = packageInfo;
	}

	public Long getPrecision() {
		return precision == null ? 0L:precision ;
	}

	public void setPrecision(Long precision) {
		this.precision = precision;
	}
	
	public boolean isBasePackage(){
		return this.coefficient == 1;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    public Boolean getMainPackage() {
        return this.mainPackage;
    }

    public void setMainPackage(Boolean mainPackage) {
        this.mainPackage = mainPackage;
    }
	
}
