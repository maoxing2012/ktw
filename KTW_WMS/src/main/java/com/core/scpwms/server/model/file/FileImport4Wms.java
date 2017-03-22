package com.core.scpwms.server.model.file;

import com.core.business.model.imports.FileImport;
import com.core.db.server.model.Entity;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Warehouse;

/**
 * 上传文件记录
 * 
 * @author <a href="mailto:daqun.zhao@mbpsoft.com">daqun.zhao</a>
 */
@SuppressWarnings("serial")
public class FileImport4Wms extends Entity {
	private Warehouse wh;

	private Owner owner;
	
	private FileImport fileImport;

	public Warehouse getWh() {
		return wh;
	}

	public void setWh(Warehouse wh) {
		this.wh = wh;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public FileImport getFileImport() {
		return fileImport;
	}

	public void setFileImport(FileImport fileImport) {
		this.fileImport = fileImport;
	}
	
}
