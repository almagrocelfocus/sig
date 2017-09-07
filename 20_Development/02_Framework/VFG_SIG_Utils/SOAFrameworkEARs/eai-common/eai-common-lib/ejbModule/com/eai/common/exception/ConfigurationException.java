package com.eai.common.exception;

public class ConfigurationException extends EAIException {
	private static final long serialVersionUID = -1863287089326425119L;
	public static final String DEFAULT_MESSAGE = "Error in configuration load";
	
	public ConfigurationException(){
		super();
	}
	
	public ConfigurationException(Throwable ex){
		super(ex);
	}
	
	public ConfigurationException(String message){
		this("[CE]", message);
	}
	
	public ConfigurationException(String code, String message){
		this(code, message, message);
	}
	
	public ConfigurationException(String code, String message, String messageDetail){
		super(code, message, messageDetail);
	}

}
