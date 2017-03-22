package com.core.scpwms.client.rpc;

import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CustomServiceAsync {
	public abstract void swichWarehouse(Long warehouseId , AsyncCallback callBack);
}