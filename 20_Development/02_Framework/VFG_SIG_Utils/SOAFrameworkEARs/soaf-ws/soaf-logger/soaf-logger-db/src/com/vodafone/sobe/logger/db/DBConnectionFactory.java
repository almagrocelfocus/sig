package com.vodafone.sobe.logger.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionFactory {
	private static Connection conn;
	
	private DBConnectionFactory(){
	}
	
	public static synchronized Connection getDBConnection() throws NamingException, SQLException{
		if(DBConnectionFactory.conn == null){

			Context initContext = new InitialContext();
			DataSource ds = (DataSource)initContext.lookup("soaf-logger-db");
			
			DBConnectionFactory.conn = ds.getConnection();
			
			try{
				DBConnectionFactory.conn.setAutoCommit(true);
			}
			catch(Exception e){
				
			}
			
			return DBConnectionFactory.conn;
		}else{
			return DBConnectionFactory.conn;
		}
	}
	
}
