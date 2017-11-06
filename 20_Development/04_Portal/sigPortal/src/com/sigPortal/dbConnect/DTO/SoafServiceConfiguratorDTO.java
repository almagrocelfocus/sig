package com.sigPortal.dbConnect.DTO;

import java.io.Serializable;
import java.math.BigInteger;

public class SoafServiceConfiguratorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1149910191845758111L;
	
	private BigInteger id;
	
	private String domain;
	
	private String category;
	
	private String target;
	
	private String version;
	
	private String username;
	
	private String paramName;
	
	private String paramValue;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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

	public String getVersion() {
		return version;
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

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	

}
