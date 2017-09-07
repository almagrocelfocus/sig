package com.eai.common.exception;

public class FileException extends EAIException {
	private static final long serialVersionUID = 5316714977179246753L;
	public static final String DEFAULT_MESSAGE = "File error";
	public static final String NOT_FOUND = "[FE]NF";
	
	public FileException(){
		super();
	}
	
	public FileException(Throwable ex){
		super(ex);
	}
	
	public FileException(String message){
		this("[FE]", message);
	}
	
	public FileException(String code, String message){
		this(code, message, message);
	}
	
	public FileException(String code, String message, String messageDetail){
		super(code, message, messageDetail);
	}
}
