package com.core.scpwms.server.service.common.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.core.scpwms.server.enumerate.EnuInvStatus;
import com.core.scpwms.server.service.common.GetJdeInventoryDao;

public class GetJdeInventoryDaoImpl extends HibernateDaoSupport implements
		GetJdeInventoryDao {

	@Override
	public Long getJdeInvByCondition(final String version, final String owner,
			final String skuCode) {
		return (Long) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						// 需要执行的存储过程
						String procedure = "{call PKG_STOCK.GET_STOCK(?,?,?)}";
						CallableStatement callableStatement = session
								.connection().prepareCall(procedure);
						// 设置输入参数
						callableStatement.setString(1, version);
						callableStatement.setString(2, owner);
						callableStatement.setString(3, skuCode);
						// callableStatement.setString(4, dispLot);
						// callableStatement.setString(5, trackSeq);
						callableStatement.execute();
						callableStatement.close();
						return 0l;
					}
				});
	}

	@Override
	public Double getJdeUsableQty(final String ownerCd,
			final String skuShortCd, final String lotInfo,
			final String projectNo, final String invStatus) {
		return (Double) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						// 需要执行的存储过程
						String procedure = "{call PKG_STOCK.GET_STOCK_HARD(?,?,?,?,?,?)}";
						CallableStatement callableStatement = session
								.connection().prepareCall(procedure);
						// 分布场所（货主）
						callableStatement.setString(1, ownerCd);
						// 商品的短项目号
						callableStatement.setLong(2, new Long( skuShortCd ));
						// 批次属性
						callableStatement.setString(3, lotInfo);
						// 批次说明
						callableStatement.setString(4, projectNo);
						// 库存属性
						String jdeInvStatus = EnuInvStatus.AVAILABLE
								.equals(invStatus) ? "180" : "280";
						callableStatement.setString(5, jdeInvStatus);
						// 注册输出参数
						callableStatement.registerOutParameter(6,
								java.sql.Types.BIGINT);
						callableStatement.execute();
						
						// 取回输出参数
						Double result = new Double(callableStatement
								.getLong(6));
						callableStatement.close();

						return result;
					}
				});
	}

	@Override
	public Long updateAddresseeInfo(final String addresseeCode) {
		return (Long) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						// 需要执行的存储过程
						String procedure = "{call PKG_EDI.GET_ADDRESSEE(?,?)}";
						
						CallableStatement callableStatement = session
								.connection().prepareCall(procedure);
						
						callableStatement.setLong(1, addresseeCode == null ? 0L : new Long(addresseeCode));
						
						callableStatement.registerOutParameter(2,
								java.sql.Types.BIGINT);
						
						callableStatement.execute();
						// 取回输出参数
						Long result = callableStatement.getLong(2);
						callableStatement.close();

						return result;
					}
				});
	}
}
