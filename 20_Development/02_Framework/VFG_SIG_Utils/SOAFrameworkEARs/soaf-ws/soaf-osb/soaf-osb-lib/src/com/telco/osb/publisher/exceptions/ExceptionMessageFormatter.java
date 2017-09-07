package com.telco.osb.publisher.exceptions;

public class ExceptionMessageFormatter {
	private ExceptionMessageFormatter(){
	}
	
	public static String formatMessage(Exception e){
		return String.format("%s - %s", e.getMessage(), e.getStackTrace());
	}
}
