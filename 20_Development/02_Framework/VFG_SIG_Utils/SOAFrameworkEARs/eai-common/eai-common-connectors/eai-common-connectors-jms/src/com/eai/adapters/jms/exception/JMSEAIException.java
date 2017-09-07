package com.eai.adapters.jms.exception;

import com.eai.common.exception.EAIException;

public class JMSEAIException extends EAIException {
	private static final long serialVersionUID = 5316714977179246753L;
	public static final String DEFAULT_MESSAGE = "SQS general exception";
	
	public JMSEAIException(){
		super();
	}
	
	public JMSEAIException(Throwable ex){
		super(ex);
	}
	
	public JMSEAIException(String message){
		this("[SQSE]", message);
	}
	
	public JMSEAIException(String code, String message){
		this(code, message, message);
	}
	
	public JMSEAIException(String code, String message, String messageDetail){
		super(code, message, messageDetail);
	}
}