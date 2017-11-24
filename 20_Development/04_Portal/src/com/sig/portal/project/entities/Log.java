package com.sig.portal.project.entities;

import java.io.Serializable;
import java.sql.Date;

public class Log implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = -8081371799570663905L;
	/**
	 * 
	 */
	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	private String id;
    private String requestId;
    private String corralationId;
    private String domain;
    private String category;
    private String target;
    private String service;
    private String operation;
    private String version;
    private String source;
    private String targetEndPoint;
    private String logLevel;
    private String task;
    private String userName;
    private Date timeStamp;
    private Date creationDate;
    private String payload;
    private String statusCode;
    private String statusMessage;
    private String engine;

    
	/**
	 * @param id
	 * @param requestId
	 * @param corralationId
	 * @param domain
	 * @param category
	 * @param target
	 * @param service
	 * @param operation
	 * @param version
	 * @param source
	 * @param targetEndPoint
	 * @param logLevel
	 * @param task
	 * @param userName
	 * @param timeStamp
	 * @param creationDate
	 * @param payload
	 * @param statusCode
	 * @param statusMessage
	 * @param engine
	 */
	public Log(String id, String requestId, String corralationId, String domain, String category, String target,
			String service, String operation, String version, String source, String targetEndPoint, String logLevel,
			String task, String userName, Date timeStamp, Date creationDate, String payload, String statusCode,
			String statusMessage, String engine) {
		super();
		this.id = id;
		this.requestId = requestId;
		this.corralationId = corralationId;
		this.domain = domain;
		this.category = category;
		this.target = target;
		this.service = service;
		this.operation = operation;
		this.version = version;
		this.source = source;
		this.targetEndPoint = targetEndPoint;
		this.logLevel = logLevel;
		this.task = task;
		this.userName = userName;
		this.timeStamp = timeStamp;
		this.creationDate = creationDate;
		this.payload = payload;
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.engine = engine;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	/**
	 * @return the corralationId
	 */
	public String getCorralationId() {
		return corralationId;
	}
	/**
	 * @param corralationId the corralationId to set
	 */
	public void setCorralationId(String corralationId) {
		this.corralationId = corralationId;
	}
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}
	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}
	/**
	 * @param service the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the targetEndPoint
	 */
	public String getTargetEndPoint() {
		return targetEndPoint;
	}
	/**
	 * @param targetEndPoint the targetEndPoint to set
	 */
	public void setTargetEndPoint(String targetEndPoint) {
		this.targetEndPoint = targetEndPoint;
	}
	/**
	 * @return the logLevel
	 */
	public String getLogLevel() {
		return logLevel;
	}
	/**
	 * @param logLevel the logLevel to set
	 */
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	/**
	 * @return the task
	 */
	public String getTask() {
		return task;
	}
	/**
	 * @param task the task to set
	 */
	public void setTask(String task) {
		this.task = task;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the timeStamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}
	/**
	 * @param payload the payload to set
	 */
	public void setPayload(String payload) {
		this.payload = payload;
	}
	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}
	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	/**
	 * @return the engine
	 */
	public String getEngine() {
		return engine;
	}
	/**
	 * @param engine the engine to set
	 */
	public void setEngine(String engine) {
		this.engine = engine;
	}
    

   
}
