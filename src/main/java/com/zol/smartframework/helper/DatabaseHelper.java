package com.zol.smartframework.helper;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.swing.text.html.parser.Entity;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * 创建时间：2017年7月3日   
 * @author suzhihui  
 * 数据库操作助手类
 */
public final class DatabaseHelper {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(DatabaseHelper.class);
	
	private static final ThreadLocal<Connection> CONNECTION_HOLDER;
	
	private static final QueryRunner QUERY_RUNNER;
	
	private static final BasicDataSource DATA_SOURCE;
	
	static{
		CONNECTION_HOLDER=new ThreadLocal<>();
		
		QUERY_RUNNER=new QueryRunner();
		
		DATA_SOURCE=new BasicDataSource();
		DATA_SOURCE.setDriverClassName(ConfigHelper.getJdbcDriver());
		DATA_SOURCE.setUrl(ConfigHelper.getJdbcUrl());
		DATA_SOURCE.setUsername(ConfigHelper.getJdbcUsername());
		DATA_SOURCE.setPassword(ConfigHelper.getJdbcPassword());
	}
	
	//获取数据源
	public static DataSource getDataSource(){
		return DATA_SOURCE;
	}
	
	//获取数据库链接
	public static Connection getConnection(){
		Connection connection=CONNECTION_HOLDER.get();
		if(null==connection){
			try {
				connection=DATA_SOURCE.getConnection();
			} catch (SQLException e) {
				LOGGER.error("get connection error",e);
				throw new RuntimeException(e);
			}finally{
				CONNECTION_HOLDER.set(connection);
			}
		}
		return connection;
	}
	
	//开启事务
	public static void beginTransaction(){
		Connection connection=CONNECTION_HOLDER.get();
		if(null!=connection){
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				LOGGER.error("begin transaction error",e);
				throw new RuntimeException(e);
			}finally{
				CONNECTION_HOLDER.set(connection);
			}
		}
	}
	
	//提交事务
	public static void commitTransaction(){
		Connection connection=CONNECTION_HOLDER.get();
		try {
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			LOGGER.error("commit trasnaction error",e);
			throw new RuntimeException(e);
		}finally{
			CONNECTION_HOLDER.remove();
		}
	}
	
	//回滚事务
	public static void rollbackTransaction(){
		Connection connection=CONNECTION_HOLDER.get();
		try {
			connection.rollback();
			connection.close();
		} catch (SQLException e) {
			LOGGER.error("rollback trasnaction error",e);
			throw new RuntimeException(e);
		}finally{
			CONNECTION_HOLDER.remove();
		}
	}

}
