package com.vodafone.sobe.reporting.exceptions;

public class LogHandlingException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public LogHandlingException(){
		super();
	}
	
	public LogHandlingException(String message) {
        super(message);
    }
	
	public LogHandlingException(Throwable e) {
        super(e);
    }
}
