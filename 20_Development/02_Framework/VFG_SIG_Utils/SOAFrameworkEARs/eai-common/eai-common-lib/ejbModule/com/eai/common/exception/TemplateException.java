package com.eai.common.exception;

public class TemplateException extends EAIException {
	private static final long serialVersionUID = -1983802996015454410L;
	
	public static final String DEFAULT_MESSAGE = "Templates error";
	
	public TemplateException(){
		super();
	}
	
	public TemplateException(Throwable ex){
		super(ex);
	}
	
	public TemplateException(String message){
		this("[TE]", message);
	}
	
	public TemplateException(String code, String message){
		this(code, message, message);
	}
	
	public TemplateException(String code, String message, String messageDetail){
		super(code, message, messageDetail);
	}
}
