package com.telco.osb.connectors.publisher.entity;

import java.io.Serializable;

public class PublisherReturn implements Serializable{
	
	private static final long serialVersionUID = 3515323431818535442L;
	
	private String errorCode;
	private String errorMessage;
	private String repubInfo;

	//DEFAULT ERROR CODE
	private static String ERROR_CODE = "300030001";
	private static String DEFAULT_MESSAGE = "Service error: E000";

	public PublisherReturn() {
		errorCode = ERROR_CODE;
		errorMessage = DEFAULT_MESSAGE;
	}

	public PublisherReturn(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public PublisherReturn(String errorCode, String errorMessage, String repubInfo) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.repubInfo = repubInfo;
	}

	public PublisherReturn(Exception e) {
		this(ERROR_CODE, (e == null || e.getMessage() == null) ? DEFAULT_MESSAGE : e.getMessage());
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isSuccess() {
		return errorCode != null && errorCode.endsWith("0000000");
	}

	public String getRepubInfo() {
		return repubInfo;
	}

	public void setRepubInfo(String repubInfo) {
		this.repubInfo = repubInfo;
	}
}
