package com.core.scpwms.server.model.file;

import com.core.db.server.model.Entity;

/**
 * 文件和订单明细的关联关系
 * @author mousachi
 *
 */
@SuppressWarnings("serial")
public class FileImport4WmsDetail extends Entity {
	private FileImport4Wms fileImport4Wms;
	
	private String relatedClassName;
	
	private Long relatedId;

	public FileImport4Wms getFileImport4Wms() {
		return fileImport4Wms;
	}

	public void setFileImport4Wms(FileImport4Wms fileImport4Wms) {
		this.fileImport4Wms = fileImport4Wms;
	}

	public String getRelatedClassName() {
		return relatedClassName;
	}

	public void setRelatedClassName(String relatedClassName) {
		this.relatedClassName = relatedClassName;
	}

	public Long getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}
	
}
