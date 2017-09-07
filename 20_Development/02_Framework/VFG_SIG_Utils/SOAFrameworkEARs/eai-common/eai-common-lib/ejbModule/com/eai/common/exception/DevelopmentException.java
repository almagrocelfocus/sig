package com.eai.common.exception;

public class DevelopmentException extends EAIException{
	private static final long serialVersionUID = 1L;
	
	public DevelopmentException(String message){
		super("[DE]", message);
	}
}
