package com.vodafone.sobe.reporting.exceptions;

public class ExceptionMessageFormatter {
	
	private ExceptionMessageFormatter(){
	}

	public static String formatMessage(Exception e){
		return String.format("%s - %s", e.getMessage(), e.getStackTrace());
	}
	
	
}
