package com.telco.osb.publisher.scheduler.ws.entity;

import java.io.Serializable;

public class RepublisherTimerRequest implements Serializable {
	private static final long serialVersionUID = -7787263380579178640L;

	private String runIntervalSeconds;

	public String getRunIntervalSeconds() {
		return runIntervalSeconds;
	}

	public void setRunIntervalSeconds(String runIntervalSeconds) {
		this.runIntervalSeconds = runIntervalSeconds;
	}
}
