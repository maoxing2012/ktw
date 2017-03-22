package com.core.scpwms.server.service.imports;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

public interface ImportFileManager4Wms {
	// 处理成功
	public static final String SUCCESS = "SUCCESS";
    // 有异常数据，处理中止
	public static final String SUCCESS_WITH_ERROR = "SUCCESS_WITH_ERROR";
    // 跳过异常数据，处理完成
	public static final String FORCE_SUCCESS = "FORCE_SUCCESS";
    
    @Transactional
    public void dealFile(List<Long> ids);
    
    @Transactional
    public void cancelFile(List<Long> ids);
    
    @Transactional
    public void forceDealFile(List<Long> ids);

    @Transactional
    public void deleteFile(List<Long> ids);
    
	@Transactional
	public Map getInitList(Map params);
	
	@Transactional
	public Map doProcess(Map params);
	
	@Transactional
	public void resetStatus(List<Long> ids);
}
