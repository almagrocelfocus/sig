package com.telco.osb.publisher.scheduler.ws.entity;

import java.io.Serializable;

public class RepublisherTimerResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String statusMessage;

	public RepublisherTimerResponse() {
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
