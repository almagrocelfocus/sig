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
		
		String requestId = map.get("request_id");
		String correlationId = map.get("correlation_id");
		
		String domain = map.get("domain");
		String category = map.get("category");
		String target = map.get("target");
		String service = map.get("service");
		String operation = map.get("operation");
		String version = map.get("version");
		String source = map.get("source");
		String target_endpoint = map.get("target_endpoint");
		
		String logLevel = map.get("log_level");
		String task = map.get("task");
		String username = map.get("username");
		String timestamp = map.get("timestamp");
		String payload = map.get("payload");
		String statusCode = map.get("status_code");
		String statusMessage = map.get("status_message");
		String engine = map.get("engine");
		
		LogEntity log = new LogEntity();
		
		log.setRequestId(requestId);
		log.setCorrelationId(correlationId);
		
		log.setDomain(domain);
		log.setCategory(category);
		log.setTarget(target);
		log.setService(service);
		log.setOperation(operation);
		log.setVersion(version);
		log.setSource(source);
		log.setTargetEndpoint(target_endpoint);
		
		
		if(logLevel == null || "".equals(logLevel)){
			//Default value
			log.setLogLevel("Info");
		}else {
			log.setLogLevel(logLevel);
		}
		log.setTask(task);
		log.setUsername(username);
		log.setEngine(engine);
		Timestamp tstamp = new Timestamp(0);
		try {
			Date date = ConverterFactory.getStringToDateConverter().convert(timestamp);
			tstamp = new Timestamp(date.getTime());
		} catch (Exception e) {
			LOGG_ERRORS.error("Exception converting timestamp to date!", e);
		}
		log.setTimestamp(tstamp);
		log.setCreationDate(new Timestamp(System.currentTimeMillis())); //current timestamp
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
			psLog = jndiConnection.prepareStatement("insert into LOG(ID, REQUEST_ID, CORRELATION_ID, DOMAIN, CATEGORY, TARGET, SERVICE, OPERATION, VERSION, SOURCE, TARGET_ENDPOINT, LOG_LEVEL, TASK, USERNAME, TIMESTAMP, CREATION_DATE, PAYLOAD, STATUS_CODE, STATUS_MESSAGE, ENGINE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
			psLog.setString(1, logId.toString());
			psLog.setString(2, log.getRequestId()); 		//REQUEST_ID
			psLog.setString(3, log.getCorrelationId());		//CORRELATION_ID
			psLog.setString(4, log.getDomain());			//DOMAIN
			psLog.setString(5, log.getCategory());			//CATEGORY
			psLog.setString(6, log.getTarget());			//TARGET
			psLog.setString(7, log.getService());			//SERVICE
			psLog.setString(8, log.getOperation());			//OPERATION
			psLog.setString(9, log.getVersion());			//VERSION
			psLog.setString(10, log.getSource());			//SOURCE
			psLog.setString(11, log.getTargetEndpoint());	//TARGET_ENDPOINT
			
			psLog.setString(12, log.getLogLevel()); 		//LOG_LEVEL
			psLog.setString(13, log.getTask());				//TASK
			psLog.setString(14, log.getUsername());			//USERNAME
			psLog.setTimestamp(15, log.getTimestamp());		//TIMESTAMP
			psLog.setTimestamp(16, log.getCreationDate());	//CREATION_DATE
			psLog.setString(17, log.getPayload());			//PAYLOAD
			psLog.setString(18, log.getStatusCode());		//STATUS_CODE
			psLog.setString(19, log.getStatusMessage());	//STATUS_MESSAGE
			psLog.setString(20, log.getEngine());			//ENGINE
			psLog.execute();
			
			if(log.getLogKeysList() != null){
				//could be changed to thread pool for pre-compilation and better performance
				psLogkey = jndiConnection.prepareStatement("insert into LOG_KEYS(ID, ID_LOG, REQUEST_ID, CORRELATION_ID, KEY_NAME, KEY_VALUE) values(?,?,?,?,?,?)");
				
				for(LogKeys lk : log.getLogKeysList()){
					UUID lkID = UUID.randomUUID();
				
					psLogkey.setString(1, lkID.toString());
					psLogkey.setString(2, logId.toString());
					psLogkey.setString(3, log.getRequestId()); 		//REQUEST_ID
					psLogkey.setString(4, log.getCorrelationId());	//CORRELATION_ID
					psLogkey.setString(5, lk.getKeyName());
					psLogkey.setString(6, lk.getKeyValue());
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
