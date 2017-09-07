package com.eai.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.eai.common.EAIConstants;

import com.eai.common.converter.ConverterFactory;
import com.eai.common.exception.FileException;
import com.eai.common.service.EAIFileConfigurationManager;

public final class StringUtils {
	
	private StringUtils(){
	}
	
	/* START - Validation Utils */
	public static final boolean isNullOrEmpty(Object str){
		return str == null || isNullOrEmpty(str.toString());
	}
	public static final boolean isNullOrEmpty(String str){
		if(str != null && !str.equals(EAIConstants.EMPTY_STRING) && !str.equals(EAIConstants.EMPTY_VALUE) ){
			return false;
		}
		return true;		
	}
	/* END - Validation Utils */
	
	/* START - String transformation Utils */
	public static final List<String> getStringList(String value){
		return getStringList(value, EAIConstants.LIST_DELIMITER);
	}
	
	public static final List<String> getStringList(String value, String delimiter){
		List<String> ret = new ArrayList<String>();
		if( isNullOrEmpty(value) ){
			//do nothing
		}else if( isNullOrEmpty(delimiter) ){
			ret.add(value);
		}else{
			for( String val : value.split(delimiter) ){
				ret.add(val);
			}
		}
		return ret;
	}
	
	public static final String[] getStringArray(String value){
		return getStringArray(value, EAIConstants.LIST_DELIMITER);
	}
	public static final String[] getStringArray(String value, String delimiter){
		if( isNullOrEmpty(value) ){
			return new String[]{};
		}else{
			return value.split(delimiter);
		}
	}
	
	
	public static final String getStringFromList(String[] valueLst){
		return getStringFromList(valueLst, EAIConstants.LIST_DELIMITER);
	}
	
	public static final String getStringFromList(String[] valueLst, String delimiter ){
		String ret = EAIConstants.EMPTY_STRING;
		if( ListUtils.isNullOrEmpty(valueLst) ){
			//nothing
		}else{
			for( String val : valueLst ){
				ret += val + delimiter;
			}
			//remove last delimiter
			ret = ret.substring(0, ret.length() - delimiter.length());
		}
		return ret;
	}
	
	public static final String getStringFromList(List<String> valueLst){
		return getStringFromList(valueLst, EAIConstants.LIST_DELIMITER);
	}
	
	public static final String getStringFromList(List<String> valueLst, String delimiter ){
		String ret = EAIConstants.EMPTY_STRING;
		if( ListUtils.isNullOrEmpty(valueLst) ){
			//nothing
		}else{
			for( String val : valueLst ){
				ret += val + delimiter;
			}
			//remove last delimiter
			ret = ret.substring(0, ret.length() - delimiter.length());
		}
		return ret;
	}

	/**
	 * Return the String Number array with all the number occurrences found in a string  
	 * 
	 * @param name
	 * @return
	 */
	public static final List<Integer> decodeNumberString(String value){
		if( !StringUtils.isNullOrEmpty(value) ){
			Matcher intMatch = Pattern.compile( "\\d+" ).matcher( value ); 
			
			List<Integer> intLst = new ArrayList<Integer>();
			while (intMatch.find()) {
				String intPattern = intMatch.group(0);
				
				intLst.add( ConverterFactory.getStringToIntegerConverter().convert( intPattern ) );
			}
			return intLst;
		}
		return null;
	}
	/* END - String transformation Utils */
	
	public static final boolean isWhitespace(String textValue) {
		return isNullOrEmpty( textValue.replaceAll("[\\s\\n\\r]+| ", "") );
	}

	public static final String strip(String str) {
		return strip(str, null);
	}
	
	public static final String   strip(String   str, String   stripChars) {
		if (isNullOrEmpty(str)) {
			return str;
		}
		str = stripStart(str, stripChars);
		return stripEnd(str, stripChars);
	}
	
	public static final String   stripStart(String   str, String   stripChars) {

		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	public static final String stripEnd(String   str, String   stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}

		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}
	
	public static final String toString(Object obj){
		if( StringUtils.isNullOrEmpty( obj ) ){
			return EAIConstants.EMPTY_STRING;
		}
		return obj.toString();
	}
	
	public static final String readInputStreamAsString(String filePath) {
		return readInputStreamAsString(FileUtils.getFileReader(filePath));
	}
	public static final String readInputStreamAsString(String filePath, String encoding) {
		return readInputStreamAsString(FileUtils.getFileReader(filePath), encoding, true);
	}
	public static final String readInputStreamAsString(InputStreamReader inputStreamReader) {
		return readInputStreamAsString(inputStreamReader, EAIFileConfigurationManager.getDefaultEncoding(), true);
	}
	public static final String readInputStreamAsString(InputStreamReader inputStreamReader, String encoding) {
		return readInputStreamAsString(inputStreamReader, encoding, false);
	}
	private static final String readInputStreamAsString(InputStreamReader inputStreamReader, String encoding, boolean close) {
		String result = null;
	    ByteArrayOutputStream buf = null;
	    int resultReader;
		try {
			buf = new ByteArrayOutputStream();
			resultReader = inputStreamReader.read();
			while(resultReader != -1) {
				byte b = (byte)resultReader;
				buf.write(b);
				resultReader = inputStreamReader.read();
			}
			if( encoding != null ){
				result = buf.toString(encoding);
			}else{
				result = buf.toString();
			}
		} catch (Exception e) {
			throw new FileException(e);
		} finally {
			FileUtils.forceClose(buf);
			if( close ){
				FileUtils.forceClose(inputStreamReader);
			}
		}
	    return result;
	}
	
	public static final boolean equals(Object obj1, Object obj){
		if(obj1 != null){
			String obj1Str = obj1.toString();
			String obj2Str = obj != null ? obj.toString() : null;
			return obj1Str != null && obj1Str.equals(obj2Str);
		}
		return obj1 == obj;
	}
}
