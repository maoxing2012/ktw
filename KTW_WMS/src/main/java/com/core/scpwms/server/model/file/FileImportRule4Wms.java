package com.core.scpwms.server.model.file;

import com.core.business.model.imports.FileImportRule;
import com.core.db.server.model.Entity;
import com.core.scpwms.server.model.user.Owner;
import com.core.scpwms.server.model.warehouse.Warehouse;

@SuppressWarnings("serial")
public class FileImportRule4Wms extends Entity {
	private Warehouse wh;

	private Owner owner;
	
	private FileImportRule fileImportRule;

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

	public FileImportRule getFileImportRule() {
		return fileImportRule;
	}

	public void setFileImportRule(FileImportRule fileImportRule) {
		this.fileImportRule = fileImportRule;
	}
}
