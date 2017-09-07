package com.telco.osb.configurator.db.dao;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the SOAF_SERVICE_CONFIGURATOR database table.
 * 
 */
@Entity
@Table(name = "SOAF_SERVICE_CONFIGURATOR")
@NamedNativeQueries({
		@NamedNativeQuery(name = "SoafServiceConfigurator.findAllSoafServiceConfiguratorByUk", query = "SELECT ESC.ID, ESC.DOMAIN, ESC.CATEGORY, ESC.TARGET, ESC.SERVICE, ESC.VERSION, ESC.PARAM_NAME, ESC.PARAM_VALUE "
				+ " FROM SOAF_SERVICE_CONFIGURATOR ESC "
				+ " WHERE "
				+ "   CALCULATE_DOMAIN_WEIGHT(ESC.ID, ESC.DOMAIN, ESC.CATEGORY, ESC.TARGET, ESC.VERSION, ESC.USERNAME) =   "
				+ "     (SELECT MAX(CALCULATE_DOMAIN_WEIGHT(ESC_INNER.ID, ESC_INNER.DOMAIN, ESC_INNER.CATEGORY, ESC_INNER.TARGET, ESC_INNER.VERSION, ESC_INNER.USERNAME)) AS DOMAIN_WEIGHT "
				+ " 		 FROM SOAF_SERVICE_CONFIGURATOR ESC_INNER  "
				+ " 	 WHERE  "
				+ "       (ESC_INNER.DOMAIN = ?1 OR ESC_INNER.DOMAIN = 'ALL') AND  "
				+ "       (ESC_INNER.CATEGORY = ?2 OR ESC_INNER.CATEGORY = 'ALL') AND  "
				+ "       (ESC_INNER.TARGET = ?3 OR ESC_INNER.TARGET = 'ALL') AND  "
				+ "       (ESC_INNER.SERVICE = ?4 OR ESC_INNER.SERVICE = 'ALL') AND  "
				+ "       (ESC_INNER.VERSION = ?5 OR ESC_INNER.VERSION = 'ALL') AND "
				+ "       (ESC_INNER.USERNAME = ?6 OR ESC_INNER.USERNAME = 'ALL') AND "
				+ "       (ESC.PARAM_NAME = ESC_INNER.PARAM_NAME ) "
				+ "     ) ", resultClass = SoafServiceConfigurator.class),
		@NamedNativeQuery(name = "SoafServiceConfigurator.findAllSoafServiceConfiguratorByUkAndParam", query = "SELECT ESC.ID, ESC.DOMAIN, ESC.CATEGORY, ESC.TARGET, ESC.SERVICE, ESC.VERSION, ESC.PARAM_NAME, ESC.PARAM_VALUE "
				+ " FROM SOAF_SERVICE_CONFIGURATOR ESC "
				+ " WHERE "
				+ "   CALCULATE_DOMAIN_WEIGHT(ESC.ID, ESC.DOMAIN, ESC.CATEGORY, ESC.TARGET, ESC.VERSION, ESC.USERNAME) =   "
				+ "     (SELECT MAX(CALCULATE_DOMAIN_WEIGHT(ESC_INNER.ID, ESC_INNER.DOMAIN, ESC_INNER.CATEGORY, ESC_INNER.TARGET, ESC_INNER.VERSION, ESC_INNER.USERNAME)) AS DOMAIN_WEIGHT "
				+ " 		 FROM SOAF_SERVICE_CONFIGURATOR ESC_INNER  "
				+ " 	 WHERE  "
				+ "       (ESC_INNER.DOMAIN = ?1 OR ESC_INNER.DOMAIN = 'ALL') AND  "
				+ "       (ESC_INNER.CATEGORY = ?2 OR ESC_INNER.CATEGORY = 'ALL') AND  "
				+ "       (ESC_INNER.TARGET = ?3 OR ESC_INNER.TARGET = 'ALL') AND  "
				+ "       (ESC_INNER.SERVICE = ?4 OR ESC_INNER.SERVICE = 'ALL') AND  "
				+ "       (ESC_INNER.VERSION = ?5 OR ESC_INNER.VERSION = 'ALL') AND "
				+ "       (ESC_INNER.USERNAME = ?6 OR ESC_INNER.USERNAME = 'ALL') AND "
				+ "       (ESC.PARAM_NAME = ESC_INNER.PARAM_NAME ) "
				+ "     ) " + "   AND ESC.PARAM_NAME = ?7 ", resultClass = SoafServiceConfigurator.class) })
public class SoafServiceConfigurator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SOAF_SERVICE_CONFIGURATOR_ID_GENERATOR", sequenceName = "SOAF_SERVICE_CONF_ID_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOAF_SERVICE_CONFIGURATOR_ID_GENERATOR")
	private long id;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "\"DOMAIN\"")
	private String domain;

	@Column(name = "PARAM_NAME")
	private String paramName;

	@Column(name = "PARAM_VALUE")
	private String paramValue;

	@Column(name = "\"SERVICE\"")
	private String service;

	@Column(name = "TARGET")
	private String target;

	@Column(name = "\"VERSION\"")
	private String version;
	
	@Column(name = "USERNAME")
	private String username;

	public SoafServiceConfigurator() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}