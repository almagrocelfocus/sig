package com.vodafone.sobe.reporting;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectionFactory {
	private static interface Singleton{
		final ConnectionFactory INSTANCE = new ConnectionFactory();
	}
	
	private final DataSource dataSource;

	private ConnectionFactory(){
		Properties properties = new Properties();
		properties.setProperty("user", "soaf");
		properties.setProperty("password", "soaf");
		
		GenericObjectPool pool = new GenericObjectPool();
		
		DriverManagerConnectionFactory connectionFactory = new DriverManagerConnectionFactory("", properties);
		
		new PoolableConnectionFactory(connectionFactory, pool, null, "SELECT 1", false, false, Connection.TRANSACTION_READ_COMMITTED);
		
		this.dataSource = new PoolingDataSource(pool);
	}
	
	public static Connection getDataBaseConnection() throws SQLException{
		return Singleton.INSTANCE.dataSource.getConnection();
	}
	
}
