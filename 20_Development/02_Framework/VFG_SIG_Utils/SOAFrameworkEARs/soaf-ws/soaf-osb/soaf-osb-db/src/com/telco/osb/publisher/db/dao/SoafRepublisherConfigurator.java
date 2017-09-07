package com.telco.osb.publisher.db.dao;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SOAF_REPUBLISHER_CONFIGURATOR database table.
 * 
 */
@Entity
@Table(name="SOAF_REPUBLISHER_CONFIGURATOR")
public class SoafRepublisherConfigurator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SOAF_REPUBLISHER_CONFIGURATOR_ID_GENERATOR", sequenceName="SOAF_REPUBLISHER_CONF_ID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SOAF_REPUBLISHER_CONFIGURATOR_ID_GENERATOR")
	private long id;

	@Column(name="\"DOMAIN\"")
	private String domain;

	@Column(name="ERROR_CODE")
	private String errorCode;

	@Column(name="MAX_NUMBER_OF_RETRIES")
	private long maxNumberOfRetries;

	@Column(name="SECONDS_BETWEEN_RETRIES")
	private long secondsBetweenRetries;

	@Column(name="\"SERVICE\"")
	private String service;

	@Column(name="\"VERSION\"")
	private String version;

    public SoafRepublisherConfigurator() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getMaxNumberOfRetries() {
		return this.maxNumberOfRetries;
	}

	public void setMaxNumberOfRetries(long maxNumberOfRetries) {
		this.maxNumberOfRetries = maxNumberOfRetries;
	}

	public long getSecondsBetweenRetries() {
		return this.secondsBetweenRetries;
	}

	public void setSecondsBetweenRetries(long secondsBetweenRetries) {
		this.secondsBetweenRetries = secondsBetweenRetries;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}