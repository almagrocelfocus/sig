package com.telco.osb.publisher.scheduler.ws.entity;

import java.io.Serializable;

public class StopRepublisherTimerResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String statusMessage;

	public StopRepublisherTimerResponse() {
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
