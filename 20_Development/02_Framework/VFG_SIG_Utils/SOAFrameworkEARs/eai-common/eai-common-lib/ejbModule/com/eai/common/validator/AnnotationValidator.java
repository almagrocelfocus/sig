package com.eai.common.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.eai.common.converter.ConverterFactory;
import com.eai.common.exception.DevelopmentException;
import com.eai.common.utils.EAIReflectionUtils;
import com.eai.common.utils.ListUtils;

public class AnnotationValidator {
	
	private AnnotationValidator(){
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Mandatory {
	    String[] errorCode();
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface IsNumber {
	    String[] errorCode();
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface IsDate {
	    String[] errorCode();
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Choice {
	    String[] errorCode();
	    String[] values();
	}
	
	public static <T> List<IErrorCatalogue> parse(IErrorCatalogue[] errorCatalogue, T objectInstance) {
		if( objectInstance == null || errorCatalogue == null ){
			return null;
		}
		
		List<IErrorCatalogue> resultLst = new ArrayList<IErrorCatalogue>();
        Method[] methods = objectInstance.getClass().getMethods();
	    for (Method method : methods) {
	        if (method.isAnnotationPresent(Mandatory.class)) {
	        	if( !ValidatorFactory.getMandatoryValidator().isValid( EAIReflectionUtils.safeGetterInvoke(method, objectInstance) ) ){//just retrieve error code
	        		resultLst = manageErrorCode(resultLst, objectInstance, method.getAnnotation(Mandatory.class).errorCode(), errorCatalogue);
	        	}
	        }
	        if (method.isAnnotationPresent(IsNumber.class)) {
	        	if( !ValidatorFactory.getNumberValidator().isValid( EAIReflectionUtils.safeGetterInvoke(method, objectInstance) ) ){//just retrieve error code
	        		resultLst = manageErrorCode(resultLst, objectInstance, method.getAnnotation(IsNumber.class).errorCode(), errorCatalogue);
	        	}
	        }
	        if (method.isAnnotationPresent(IsDate.class)) {
	        	if( !ValidatorFactory.getDateValidator().isValid( EAIReflectionUtils.safeGetterInvoke(method, objectInstance) ) ){//just retrieve error code
	        		resultLst = manageErrorCode(resultLst, objectInstance, method.getAnnotation(IsDate.class).errorCode(), errorCatalogue);
	        	}
	        }
	        if (method.isAnnotationPresent(Choice.class)) {
	        	String value = ConverterFactory.getStringConverter().convert( EAIReflectionUtils.safeGetterInvoke(method, objectInstance) );
	        	if( value != null ){
	        		boolean found = false;
		        	for( String choise : method.getAnnotation(Choice.class).values() ){
		        		if( value.equals( choise ) ){
		        			found = true;
		        			break;
		        		}
		        	}
		        	if( !found ){
		        		resultLst = manageErrorCode(resultLst, objectInstance, method.getAnnotation(Choice.class).errorCode(), errorCatalogue);
		        	}
	        	}
	        }
	    }
	    return resultLst;
	}
	
	private static <T> List<IErrorCatalogue> manageErrorCode(List<IErrorCatalogue> resultLst, T objectInstance, String[] errorCode, IErrorCatalogue[] errorCatalogue){
		if(errorCode == null || errorCode.length != 2){ 
			throw new DevelopmentException("Invalid error configuration for class: "+objectInstance.getClass() +" : ErrorCode: "+ListUtils.print(errorCode));
		}
		resultLst.add( ErrorCatalogue.getValueOfEnum(errorCode[0], errorCode[1], errorCatalogue) );
		return resultLst;
	}
}
