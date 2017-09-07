package com.eai.common.validator;

public class ErrorCatalogue {
	public static IErrorCatalogue getValueOfEnum(String majorStatusCode, String minorStatusCode, IErrorCatalogue... values){
		for( IErrorCatalogue value : values ){
			if( value.getMajorReturnCode().equals( majorStatusCode ) && value.getMinorReturnCode().equals( minorStatusCode ) ){
				return value;
			}
		}
		return null;
	}
}
