package com.eai.common.service;

import com.eai.common.EAIConstants;

import com.eai.common.exception.DevelopmentException;
import com.eai.common.utils.FileConfigurationUtils;

public class EAIFileConfigurationManager {
	
	private EAIFileConfigurationManager(){
	}
	
	private static EAIConfiguration baseConfiguration = null;
	public static void initConfiguration(EAIConfiguration baseConfiguration){
		EAIFileConfigurationManager.baseConfiguration = baseConfiguration;
		FileConfigurationUtils.initConfiguration(baseConfiguration.getBaseConfigurationPath());
	}

	public static EAIConfiguration getBaseConfiguration() {
		if( EAIFileConfigurationManager.baseConfiguration == null ){
			throw new DevelopmentException("NB Configuration should be set! Invoke initConfiguration in startup service!");
		}
		
		return EAIFileConfigurationManager.baseConfiguration;
	}
	
	public static String getDefaultEncoding() {
		String property = FileConfigurationUtils.getConfiguration(EAIConstants.DEFAULT_ENCODING_PROPERTY);
		return property == null ? "UTF-8" : FileConfigurationUtils.getConfiguration(EAIConstants.DEFAULT_ENCODING_PROPERTY);
	}
	
	public static String getExcelDefaultEncodingProperty() {
		String property = FileConfigurationUtils.getConfiguration(EAIConstants.EXCEL_DEFAULT_ENCODING_PROPERTY);
		return property == null ? "UTF-8" :  FileConfigurationUtils.getConfiguration(EAIConstants.EXCEL_DEFAULT_ENCODING_PROPERTY);
	}
	
	public static String getRTFDefaultEncodingProperty() {
		String property = FileConfigurationUtils.getConfiguration(EAIConstants.RTF_DEFAULT_ENCODING_PROPERTY);
		return property == null ? "UTF-8" :  FileConfigurationUtils.getConfiguration(EAIConstants.RTF_DEFAULT_ENCODING_PROPERTY);
	}
	
	public static String getEmailDefaultEncodingProperty() {
		String property = FileConfigurationUtils.getConfiguration(EAIConstants.EMAIL_DEFAULT_ENCODING_PROPERTY);
		return property == null ? "UTF-8" :  FileConfigurationUtils.getConfiguration(EAIConstants.EMAIL_DEFAULT_ENCODING_PROPERTY);
	}
	
	public static String getDefaultXPATHImpl() {
		String factory = FileConfigurationUtils.getConfiguration(EAIConstants.XML_XPATH_DEFAULT_PROPERTY);
		if( FileConfigurationUtils.isEmptyProperty( factory ) ){
			factory = "com.sun.org.apache.xpath.internal.jaxp.XPathFactoryImpl";
		}
		return factory;
	}
	
	public static String getConfigurationProperty(String name) {
		return FileConfigurationUtils.getConfiguration(name);
	}

	public static String getMessage(String message, Object... values) {
		return FileConfigurationUtils.getMessage(message, values);
	}
	
	public static class EAIConfiguration {
		private String baseConfigurationPath = null;
		
		public EAIConfiguration(String baseConfigurationPath) {	
			setBaseConfigurationPath(baseConfigurationPath);
		}

		public String getBaseConfigurationPath(){
			return baseConfigurationPath;
		}
		public void setBaseConfigurationPath(String baseConfigurationPath){
			this.baseConfigurationPath = baseConfigurationPath;
		}
	}
}
