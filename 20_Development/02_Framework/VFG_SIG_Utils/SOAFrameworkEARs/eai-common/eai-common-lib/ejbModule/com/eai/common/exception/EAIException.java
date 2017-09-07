package com.eai.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public abstract class EAIException extends RuntimeException{
	private static final long serialVersionUID = -8973930078220434743L;
	
	public static final String DEFAULT_CODE = "1";
	public static final String DEFAULT_MESSAGE = "Unexpected system error";
	private String code;
	private String message;
	private String messageDetails;
	
	public EAIException(){
		this(DEFAULT_CODE, DEFAULT_MESSAGE);
	}
	
	public EAIException(Throwable ex){
		this(DEFAULT_CODE, ex.getMessage(), getStackTrace(ex));
		this.setStackTrace(ex.getStackTrace());
	}
	
	public EAIException(String message){
		this( DEFAULT_CODE, message );
	}
	
	public EAIException(String code, String message){
		this( code, message, message );
	}
	
	public EAIException(String code, String message, String messageDetails){
		setCode(code);
		setMessage(message);
		setMessageDetails(messageDetails);
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}

	public void setMessageDetails(String messageDetails) {
		this.messageDetails = messageDetails;
	}
	public String getMessageDetails() {
		return messageDetails;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	}
	public static String getStackTrace(Throwable aThrowable, int maxLength) {
		String result = getStackTrace(aThrowable);
		if(result.length() > maxLength){
			return result.substring(0, maxLength);
		}
		return result;
	}
}
