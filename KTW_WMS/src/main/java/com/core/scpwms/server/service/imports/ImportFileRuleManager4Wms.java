package com.core.scpwms.server.service.imports;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.core.scpwms.server.model.file.FileImportRule4Wms;

public interface ImportFileRuleManager4Wms {

    /**
     * 保存规则
     * @param rule
     */
    @Transactional
    public void save(FileImportRule4Wms fileImportRule);

    /**
     * 文件状态改为处理中
     * @param rule
     */
    @Transactional
    public void updateImportFileStatus(Long id);

    /**
     * 
     * <p>状态</p>
     *
     * @param id
     */
    @Transactional
    public void updateImportFileToStatus(Long id, String fileStatus);
    
    /**
     * 
     * <p>状态改为处理完成，无异常</p>
     *
     * @param id
     */
    @Transactional
    public void updateImportFileToEndStatus(Long id);

    /**
     * 
     * <p>状态改为处理完成，有异常</p>
     *
     * @param id
     */
    @Transactional
    public void updateImportFileToEndStatusWithError(Long id);

    /**
     * 
     * <p>状态改为强制处理成功</p>
     *
     * @param id
     */
    @Transactional
    public void updateImportFileToForceEndStatus(Long id);

    /**
     * 
     * <p>状态改为已删除</p>
     *
     * @param id
     */
    @Transactional
    public void deleteImportFile(Long id);
    
    /**
     * 
     * <p>文件处理。</p>
     *
     * @param id
     * @return
     */
    @Transactional
    public Map<String, String> dealFile(Long id);
    
    /**
     * 
     * <p>文件处理。</p>
     *
     * @param id
     * @return
     */
    @Transactional
    public Map<String, String> cancelFile(Long id);
    
    /**
     * 
     * <p>文件强制处理。（跳过异常行）</p>
     *
     * @param id
     * @return
     */
    @Transactional
    public Map<String, String> forceDealFile(Long id);

    /**
     * 
     * <p>文件上传。</p>
     *
     * @param params
     * @return
     */
    @Transactional
    Map updateMasterFileInfo(Map params);

    /**
     * 
     * <p>文件上传画面，文件规则初始化</p>
     *
     * @param params
     * @return
     */
    @Transactional
    Map initClientData4Mst(Map params);
    
    /**
     * 
     * <p>文件上传画面，文件规则初始化</p>
     *
     * @param params
     * @return
     */
    @Transactional
    Map initClientData4Asn(Map params);
    
    /**
     * 
     * <p>文件上传画面，文件规则初始化</p>
     *
     * @param params
     * @return
     */
    @Transactional
    Map initClientData4Obd(Map params);
	
	@Transactional
	public void saveOutputFile( Long ruleId );
}
