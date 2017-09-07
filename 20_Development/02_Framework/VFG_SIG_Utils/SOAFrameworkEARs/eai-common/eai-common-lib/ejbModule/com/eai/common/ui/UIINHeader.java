package com.eai.common.ui;

import java.io.Serializable;

public class UIINHeader implements Serializable{
	private static final long serialVersionUID = -7216981884976054770L;
	
	private String requestId;
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	private String contextId;//same as correlationId
	public String getContextId() {
		return contextId;
	}
	public void setContextId(String contextId) {
		this.contextId = contextId;
	}
	
	private String clientId;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	private String locale;
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
}