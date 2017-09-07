package com.eai.common.exception;

public class XMLHelperException extends EAIException{
	private static final long serialVersionUID = 3337612043597539457L;
	public static final String DEFAULT_MESSAGE = "XML processing error";
	
	public XMLHelperException(){
		super();
	}
	
	public XMLHelperException(Throwable ex){
		super(ex);
	}
	
	public XMLHelperException(String message){
		this("[XHE]", message);
	}
	
	public XMLHelperException(String code, String message){
		this(code, message, message);
	}
	
	public XMLHelperException(String code, String message, String messageDetail){
		super(code, message, messageDetail);
	}
}
