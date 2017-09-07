package com.telco.osb.publisher.db.dao;

import java.io.Serializable;
import javax.persistence.*;

import com.telco.osb.publisher.PublisherConstants;

import java.util.Date;

/**
 * The persistent class for the SOAF_REPUBLISHER database table.
 * 
 */
@Entity
@Cacheable(false)
@Table(name = "SOAF_REPUBLISHER")
// Named query not used due to custom functions and oracle specific functions
@NamedNativeQuery(name = "SoafRepublisher.findSoafRepublisherToPublish", query = "SELECT SR.ID, SRC.ID CURRENT_CONFIGURATION_ID, SRC.MAX_NUMBER_OF_RETRIES MAX_NUMBER_OF_RETRIES "
		+ " FROM "
		+ " SOAF_REPUBLISHER SR, SOAF_REPUBLISHER_CONFIGURATOR SRC "
		+ " WHERE "
		+ " CALCULATE_DOMAIN_WEIGHT(SRC.ID, SRC.DOMAIN, SRC.CATEGORY, SRC.TARGET, SRC.VERSION, NULL) =  "
		+ " (SELECT MAX(CALCULATE_DOMAIN_WEIGHT(SRC_INNER.ID, SRC_INNER.DOMAIN, SRC_INNER.CATEGORY, SRC_INNER.TARGET, SRC_INNER.VERSION, NULL)) AS DOMAIN_WEIGHT "
		+ " FROM SOAF_REPUBLISHER_CONFIGURATOR SRC_INNER "
		+ " WHERE "
		+ " (SR.DOMAIN = SRC_INNER.DOMAIN OR SRC_INNER.DOMAIN = 'ALL') AND "
		+ " (SR.CATEGORY = SRC_INNER.CATEGORY OR SRC_INNER.CATEGORY = 'ALL') AND "
		+ " (SR.TARGET = SRC_INNER.TARGET OR SRC_INNER.TARGET = 'ALL') AND "
		+ " (SR.VERSION = SRC_INNER.VERSION OR SRC_INNER.VERSION = 'ALL')) AND "
		+ " (SR.STATUS = 'NEW' OR (SR.STATUS IN ('ERROR','REPUB') "
		// EXPIRED TIME FOR REPUBLICATION
		+ "     AND (SR.LAST_UPDATED_DATE + NUMTODSINTERVAL(SRC.SECONDS_BETWEEN_RETRIES, 'SECOND')) < CURRENT_TIMESTAMP  "
		+ "     AND   NVL(SR.NUMBER_OF_RETRIES, 0) <= NVL(SRC.MAX_NUMBER_OF_RETRIES, 0))) "
		+ " AND ROWNUM <= ? ")
@NamedQueries({
    @NamedQuery(name = "SoafRepublisher.countByCorrelationId", query = "SELECT COUNT(s) FROM SoafRepublisher S WHERE s.correlationId = :correlationId ")
})
public class SoafRepublisher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SOAF_REPUBLISHER_ID_GENERATOR", sequenceName = "SOAF_REPUBLISHER_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOAF_REPUBLISHER_ID_GENERATOR")
	private long id;

	@Column(name = "CORRELATION_ID")
	private String correlationId;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "\"DOMAIN\"")
	private String domain;

	@Column(name = "ERROR_CODE")
	private String errorCode;
	
	@Column(name = "ERROR_MESSAGE")
	private String errorMessage;

	@Column(name = "LAST_UPDATED_BY")
	private String lastUpdatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	@Column(name = "MESSAGE_ID")
	private String messageId;

	@Column(name = "NUMBER_OF_RETRIES")
	private int numberOfRetries;

	@Lob()
	private String request;
	
	@Lob()
	@Column(name = "REPUB_INFO")
	private String repubInfo;

	@Column(name = "REQUEST_ID")
	private String requestId;

	@Column(name = "\"SERVICE\"")
	private String service;

	private String status;

	@Column(name = "\"VERSION\"")
	private String version;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "TARGET")
	private String target;

	public SoafRepublisher() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCorrelationId() {
		return this.correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getLastUpdatedBy() {
		return this.lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int getNumberOfRetries() {
		return this.numberOfRetries;
	}

	public void setNumberOfRetries(int numberOfRetries) {
		this.numberOfRetries = numberOfRetries;
	}

	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRepubInfo() {
		return repubInfo;
	}

	public void setRepubInfo(String repubInfo) {
		this.repubInfo = repubInfo;
	}

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	@PreUpdate
	public void executePreUpdate() {
		lastUpdatedDate = new Date(System.currentTimeMillis());
		if(errorMessage != null && errorMessage.length() > PublisherConstants.ERROR_CODE_MAX_LENGTH){
			errorMessage = errorMessage.substring(0, PublisherConstants.ERROR_CODE_MAX_LENGTH);
		}
	}

	@PrePersist
	public void executePrePersist() {
		createdDate = new Date(System.currentTimeMillis());
		executePreUpdate();
	}

	/*
	 * ADDITIONAL ATTRIBUTES - USED FOR CONFIGURATION CONNECTION
	 */
	@Transient
	private long currentConfigurationId;
	public long getCurrentConfigurationId() {
		return this.currentConfigurationId;
	}
	
	public void setCurrentConfigurationId(long currentConfigurationId) {
		this.currentConfigurationId = currentConfigurationId;
	}
	
	@Transient
	private int maxNumberOfRetries;
	public int getMaxNumberOfRetries() {
		return this.maxNumberOfRetries;
	}
	
	public void setMaxNumberOfRetries(int maxNumberOfRetries) {
		this.maxNumberOfRetries = maxNumberOfRetries;
	}
	
	public void incrementRetries() {
		numberOfRetries++;
	}
}