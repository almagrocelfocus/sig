package com.vodafone.sobe.logger.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import com.eai.common.converter.ConverterFactory;
import com.vodafone.sobe.logger.db.entity.LogEntity;
import com.vodafone.sobe.logger.db.entity.LogKeys;


@Stateless
public class Logger implements LoggerLocal{
	
	private static final org.slf4j.Logger LOGG_ERRORS = org.slf4j.LoggerFactory.getLogger(SOAFReportErrorHandler.class.getName());
	
	@PersistenceContext(unitName = "soaf-logger-db")
	private EntityManager em;
	protected EntityManager getEntityManager(){
		if(em == null){
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("soaf-logger-db");
			
		    this.em = emf.createEntityManager();
		    return em;
		}else{
			return em;
		}
	}
	
	@Override
	public void log(Map<String,String> map, Map<String, String> keyValuePairs) {
		
		String messageId = map.get("message_id");
		String serviceName = map.get("service_name");
		String logLevel = map.get("log_level");
		String task = map.get("task");
		String createdBy = map.get("created_by");
		String timestamp = map.get("timestamp");
		String payload = map.get("payload");
		String statusCode = map.get("status_code");
		String statusMessage = map.get("status_message");
		String engine = map.get("engine");
		
		LogEntity log = new LogEntity();
		log.setMessageId(messageId);
		log.setServiceName(serviceName);
		if(logLevel == null || "".equals(logLevel)){
			//Default value
			log.setLogLevel("Info");
		}else {
			log.setLogLevel(logLevel);
		}
		log.setTask(task);
		log.setCreatedBy(createdBy);
		log.setEngine(engine);
		Timestamp tstamp = new Timestamp(0);
		try {
			Date date = ConverterFactory.getStringToDateConverter().convert(timestamp);
			tstamp = new Timestamp(date.getTime());
		} catch (Exception e) {
			LOGG_ERRORS.error("Exception converting timestamp to date!", e);
		}
		log.setTimestamp(tstamp);
		log.setPayload(payload);
		log.setStatusCode(statusCode);
		log.setStatusMessage(statusMessage);
		
		//KeyValuePairs
		if (keyValuePairs != null && !keyValuePairs.isEmpty()) {
			List<LogKeys> logKeysList = new ArrayList<LogKeys>();
			for (Map.Entry<String, String> entry : keyValuePairs.entrySet()) {
				LogKeys logKeysEntry = new LogKeys(entry.getKey(), entry.getValue());
				logKeysEntry.setIdLog(log);
				logKeysList.add(logKeysEntry);
			}
			log.setLogKeysList(logKeysList);
		}
		persist(log);
	}
	
	
	private synchronized void persist(LogEntity log){
		PreparedStatement psLogkey = null;
		PreparedStatement psLog = null;
		try{
			Connection jndiConnection = DBConnectionFactory.getDBConnection();
			
			UUID logId = UUID.randomUUID();
			//could be changed to thread pool for pre-compilation and better performance
			psLog = jndiConnection.prepareStatement("insert into LOG(ID, MESSAGE_ID, SERVICE_NAME, LOG_LEVEL, TASK, CREATED_BY, TIMESTAMP, PAYLOAD, STATUS_CODE, STATUS_MESSAGE, ENGINE) values(?,?,?,?,?,?,?,?,?,?,?)");
			
			psLog.setString(1, logId.toString());
			psLog.setString(2, log.getMessageId());
			psLog.setString(3, log.getServiceName());
			psLog.setString(4, log.getLogLevel());
			psLog.setString(5, log.getTask());
			psLog.setString(6, log.getCreatedBy());
			psLog.setTimestamp(7, log.getTimestamp());
			psLog.setString(8, log.getPayload());
			psLog.setString(9, log.getStatusCode());
			psLog.setString(10, log.getStatusMessage());
			psLog.setString(11, log.getEngine());
			psLog.execute();
			
			if(log.getLogKeysList() != null){
				//could be changed to thread pool for pre-compilation and better performance
				psLogkey = jndiConnection.prepareStatement("insert into LOG_KEYS(ID, ID_LOG, KEY_NAME, KEY_VALUE) values(?,?,?,?)");
				
				for(LogKeys lk : log.getLogKeysList()){
					UUID lkID = UUID.randomUUID();
				
					psLogkey.setString(1, lkID.toString());
					psLogkey.setString(2, logId.toString());
					psLogkey.setString(3, lk.getKeyName());
					psLogkey.setString(4, lk.getKeyValue());
					psLogkey.execute();
				}
			}
		}catch(Exception e){
			LOGG_ERRORS.error("Failed to store in the DB a message: " + e.getMessage(), e);
		}finally {
			try {
				if (psLog != null){
					psLog.close();
				}
				
				if (psLogkey != null){
					psLogkey.close();
				}
				
			} catch (SQLException sqle) {
				LOGG_ERRORS.error("SQL Exception: " + sqle.getMessage(), sqle);
			}
		}
	}
	
}
