package com.eai.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eai.common.EAIConstants;

/**
 * Used to allow logging in classes that we don't want to include a logger writer, for example utils classes
 * Logger will get execution context and write class name
 */
public class EAILogger  {
	
	private EAILogger(){
	}
	
	private static final Logger LOGGER = LogManager.getLogger(EAILogger.class);
    private static final String SEPARATOR = " ---------- %1$s ---------- ";
    
    public static String getParentClassAndLine(){
    	return getClassAndLine(3);
    }
    public static String getClassAndLine(int level) {
    	try{
    		StackTraceElement[] st = Thread.currentThread().getStackTrace();
    		String c = EAIConstants.EMPTY_STRING;
    		if (st.length > level) {
    			c = st[level].getClassName()+"."+st[level].getMethodName()+":"+st[level].getLineNumber();
    		}
    		return c;
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	return "UKNOWN:UNKNOWN";
    }
    private static String getLoggerInvocationClassAndLine() {
    	return getClassAndLine(4);
    }

    public static void trace(Object... args) {
    	LOGGER.trace(String.format(SEPARATOR, getLoggerInvocationClassAndLine()));
    	for(Object arg : args){
    		LOGGER.trace(arg);
    	}
    }
    
    public static void debug(Object... args) {
    	LOGGER.debug(String.format(SEPARATOR, getLoggerInvocationClassAndLine()));
    	for(Object arg : args){
    		LOGGER.debug(arg);
    	}
    }
    
    public static void info(Object... args) {
    	LOGGER.info(String.format(SEPARATOR, getLoggerInvocationClassAndLine()));
    	for(Object arg : args){
    		LOGGER.info(arg);
    	}
    }
    
    public static void error(Object... args) {
    	LOGGER.error(String.format(SEPARATOR, getLoggerInvocationClassAndLine()));
    	for(Object arg : args){
    		LOGGER.error(arg);
    	}
    }
    
    public static void fatal(Object... args) {
    	LOGGER.fatal(String.format(SEPARATOR, getLoggerInvocationClassAndLine()));
    	for(Object arg : args){
    		LOGGER.fatal(arg);
    	}
    }
    
    public static String getName(){
    	return LOGGER.getName();
    }
}