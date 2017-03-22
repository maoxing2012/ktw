package com.core.scpwms.server.service.common.impl;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.core.scpwms.server.service.common.WmsSequenceDao;

/**
 * 
 * <p>生成流水号</p>
 *
 * @version 2.0
 * @author 毛幸@MBP<br/>
 *  2014/07/08<br/>
 */
public class SequenceDaoImpl extends HibernateDaoSupport implements WmsSequenceDao {

    @Override
    public Long getSequence(final String keyName, final String typeName) {

        return (Long) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
            	
            	Session newSession = session.getSessionFactory().openSession();
            	
            	newSession.beginTransaction();
            	
                // 需要执行的存储过程
                String procedure = "{call GETSEQUENCE(?,?,?)}";
                CallableStatement callableStatement = newSession.connection().prepareCall(procedure);
                // 设置输入参数
                callableStatement.setString(1, keyName);
                // 设置输入参数
                callableStatement.setString(2, typeName);
                // 注册输出参数
                callableStatement.registerOutParameter(3, java.sql.Types.BIGINT);
                callableStatement.execute();
                // 取回输出参数
                Long result = new Long(callableStatement.getLong(3));
                callableStatement.close();
                
                newSession.getTransaction().commit();
                
                newSession.close();
                
                return result;
            }
        });

    }
    
    @Override
    public Long getSequence4Postgres(final String keyName, final String typeName) {

    	//Postgresql存储过程调用方式
		return (Long) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Session newSession = session.getSessionFactory().openSession();
            	newSession.beginTransaction();
				
				Query query = newSession.createSQLQuery("SELECT GETSEQUENCE('"+keyName+ "' , '"  + typeName + "')");
				BigInteger result = (BigInteger) query.uniqueResult();
				
				newSession.getTransaction().commit();
                newSession.close();
                
				return result.longValue();
			}
		});

    }

	@Override
	public void resetSequence4Postgres(final String keyName, final String typeName) {
		this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Session newSession = session.getSessionFactory().openSession();
            	newSession.beginTransaction();
				
            	String sql = "update wms_sequence set next_id = 0 where sequence_key = ? and sequence_type = ?";
            	SQLQuery query = newSession.createSQLQuery(sql);
            	query.setParameter(0, keyName);
            	query.setParameter(1, typeName);
                
            	query.executeUpdate();
                newSession.getTransaction().commit();
                newSession.close();
                
                return null;
			}
		});
		
	}

}
