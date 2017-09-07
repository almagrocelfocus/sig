package com.eai.common.exception;

public class EnumNotFoundException extends EAIException{
	private static final long serialVersionUID = 3337612043597539457L;
	public static final String DEFAULT_MESSAGE = "Enumeration not found";
	
	public EnumNotFoundException(){
		super();
	}
	
	public EnumNotFoundException(Throwable ex){
		super(ex);
	}
	
	public EnumNotFoundException(String message){
		this("[ENFE]", message);
	}
	
	public EnumNotFoundException(String code, String message){
		this(code, message, message);
	}
	
	public EnumNotFoundException(String code, String message, String messageDetail){
		super(code, message, messageDetail);
	}
}
