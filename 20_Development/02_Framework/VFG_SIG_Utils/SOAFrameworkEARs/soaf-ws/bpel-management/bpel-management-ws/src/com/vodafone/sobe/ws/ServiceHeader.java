package com.vodafone.sobe.ws;

public class ServiceHeader {
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private ResponseCodes responseCodes;
	private Integer responseCode;
	private String responseMessage;
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	public ServiceHeader(){
		
	}

	
	public ServiceHeader(ResponseCodes responseCodes){
		this.responseCodes = responseCodes;
	}
	
	
	//--------------------------------------
	// Public Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
	
	public Integer getResponseCode(){
		return responseCodes.getResponseCode();
	}
	
	
	public String getResponseMessage(){
		return responseCodes.getResponseMessage();
	}
	
	
	
	public void setResponseCodes(ResponseCodes responseCodes){
		this.responseCodes = responseCodes;
	}


	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}


	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
	
}
