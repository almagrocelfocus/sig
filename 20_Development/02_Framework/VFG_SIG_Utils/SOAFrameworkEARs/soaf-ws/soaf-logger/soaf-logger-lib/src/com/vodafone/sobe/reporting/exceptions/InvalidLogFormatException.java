package com.vodafone.sobe.reporting.exceptions;

public class InvalidLogFormatException extends Exception{
	private static final long serialVersionUID = 1L;

	public InvalidLogFormatException(){
		super();
	}
	
	public InvalidLogFormatException(String message) {
        super(message);
    }
	
	public InvalidLogFormatException(Throwable e) {
        super(e);
    }
}
