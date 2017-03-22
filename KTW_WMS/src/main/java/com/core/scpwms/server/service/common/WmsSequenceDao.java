package com.core.scpwms.server.service.common;

/**
 * 
 * <p>生成流水号</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/08<br/>
 */
public interface WmsSequenceDao {
    /**
     * 
     * <p>生成流水号</p>
     *
     * @param keyName
     * @param typeName
     * @return
     */
	@Deprecated
    Long getSequence(String keyName, String typeName);
    
    /**
     * 
     * <p>生成流水号</p>
     *
     * @param keyName
     * @param typeName
     * @return
     */
    Long getSequence4Postgres(String keyName, String typeName);
    
    /**
     * 流水号重新恢复到0
     * @param keyName
     * @param typeName
     */
    void resetSequence4Postgres( String keyName, String typeName );
}
