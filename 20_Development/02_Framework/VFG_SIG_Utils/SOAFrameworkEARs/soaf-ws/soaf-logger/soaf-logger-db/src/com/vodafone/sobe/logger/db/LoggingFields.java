package com.vodafone.sobe.logger.db;

public class LoggingFields {
	
	private String requestId = "";
	private String correlationId = "";
	
	private String domain = "";
	private String category = "";
	private String target = "";
	private String service = "";
	private String operation = "";
	private String version = "";
	private String source = "";
	private String targetEndpoint = "";
	
	
	private String logLevel = "ERROR";
	private String task = "";
	private String username = "";
	private String timestamp = "";
	private String statusCode = "";
	private String statusMessage = "";
	private String engine = "";

	private String requestorRequestId = "";
	private String requestorIp = "";
	private String requestorAgent = "";
	private String sessionId = "";
	private String action = "";
	private String objectId = "";
	private String description = "";
	private String timeToComplete = "";
	private String adapterTimeSum = "";
	private String targetRequestId = "";
	private String targetIp = "";
	private String targetAgent = "";
	
	private String labels = "";
	
	private String payload = "";
	
	private String dynamicKeys = "";
	private String dynamicKey = "";
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public String getRequestorRequestId() {
		return requestorRequestId;
	}
	public void setRequestorRequestId(String requestorRequestId) {
		this.requestorRequestId = requestorRequestId;
	}
	public String getRequestorIp() {
		return requestorIp;
	}
	public void setRequestorIp(String requestorIp) {
		this.requestorIp = requestorIp;
	}
	public String getRequestorAgent() {
		return requestorAgent;
	}
	public void setRequestorAgent(String requestorAgent) {
		this.requestorAgent = requestorAgent;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTimeToComplete() {
		return timeToComplete;
	}
	public void setTimeToComplete(String timeToComplete) {
		this.timeToComplete = timeToComplete;
	}
	public String getAdapterTimeSum() {
		return adapterTimeSum;
	}
	public void setAdapterTimeSum(String adapterTimeSum) {
		this.adapterTimeSum = adapterTimeSum;
	}
	public String getTargetRequestId() {
		return targetRequestId;
	}
	public void setTargetRequestId(String targetRequestId) {
		this.targetRequestId = targetRequestId;
	}
	public String getTargetIp() {
		return targetIp;
	}
	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}
	public String getTargetAgent() {
		return targetAgent;
	}
	public void setTargetAgent(String targetAgent) {
		this.targetAgent = targetAgent;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}	

	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}	
	
	public String getOperation() {
		return operation;
	}
	
	public void setOperation(String operation) {
		this.operation = operation;
	}		

	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getTargetEndpoint() {
		return targetEndpoint;
	}
	
	public void setTargetEndpoint(String targetEndpoint) {
		this.targetEndpoint = targetEndpoint;
	}
	
	public String getPayload() {
		return payload;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public String getDynamicKeys() {
		return dynamicKeys;
	}
	
	public void setDynamicKeys(String dynamicKeys) {
		this.dynamicKeys = dynamicKeys;
	}
	
	public String getDynamicKey() {
		return dynamicKey;
	}
	
	public void setDynamicKey(String dynamicKey) {
		this.dynamicKey = dynamicKey;
	}
}
