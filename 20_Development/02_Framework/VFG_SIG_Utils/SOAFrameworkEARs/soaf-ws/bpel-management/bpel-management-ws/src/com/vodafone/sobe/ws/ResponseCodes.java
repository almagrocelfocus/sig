package com.vodafone.sobe.ws;

public enum ResponseCodes {
	
	SUCCESS(0, "Success"),
	FAILURE(1, "Unknown Error"),
	UNKNOWN_FLOW_ID(1, "Unknwon flowId"),
	FLOW_ID_NOT_RECOVERABLE(1, "FlowId is not in a recovery state or is not recoverable"),
	FLOW_ID_NOT_FOUND_OR_NOT_RECOVERABLE(1, "FlowId was not found or it is not recoverable"),
	UNKNOWN_VARIABLE(1, "The variable name was not found");
	
	private final Integer responseCode;
	private final String responseMessage;
	
	private ResponseCodes(int responseCode, String responseMessage){
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	
	public Integer getResponseCode(){
		return responseCode;
	}
	
	public String getResponseMessage(){
		return responseMessage;
	}

}
