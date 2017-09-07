package com.eai.common.exception;


public class EAIConverterException extends EAIException{
	private static final long serialVersionUID = 8601475852521311055L;
	public static final String DEFAULT_MESSAGE = "Unexpected error while converting";
	
	public EAIConverterException(){
		super();
	}
	
	public EAIConverterException(Throwable ex){
		super(ex);
	}
	
	public EAIConverterException(String message){
		this("[EAICE]", message);
	}
	
	public EAIConverterException(String code, String message){
		this(code, message, message);
	}
	
	public EAIConverterException(String code, String message, String messageDetail){
		super(code, message, messageDetail);
	}
}
