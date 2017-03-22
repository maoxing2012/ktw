package com.core.scpwms.client.rpc;

import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;

public interface CustomService extends RemoteService {
	public abstract Map swichWarehouse(Long warehouseId) throws Exception;
}