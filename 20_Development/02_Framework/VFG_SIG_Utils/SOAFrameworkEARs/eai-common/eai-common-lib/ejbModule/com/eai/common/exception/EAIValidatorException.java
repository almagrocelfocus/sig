package com.eai.common.exception;


public class EAIValidatorException extends EAIException{
	private static final long serialVersionUID = -5464186740256267476L;
	public static final String DEFAULT_MESSAGE = "Validation error";
	
	public EAIValidatorException(){
		super();
	}
	
	public EAIValidatorException(Throwable ex){
		super(ex);
	}
	
	public EAIValidatorException(String message){
		this("[NBVE]", message);
	}
	
	public EAIValidatorException(String code, String message){
		this(code, message, message);
	}
	
	public EAIValidatorException(String code, String message, String messageDetail){
		super(code, message, messageDetail);
	}
}
