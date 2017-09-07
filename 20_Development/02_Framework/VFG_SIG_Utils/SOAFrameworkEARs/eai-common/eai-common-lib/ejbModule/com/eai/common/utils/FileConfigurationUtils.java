package com.eai.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.eai.common.EAIConstants;

import com.eai.common.exception.ConfigurationException;
import com.eai.common.exception.FileException;

public class FileConfigurationUtils {
	private static final String baseConfiguration = "SystemConfiguration.properties";
	private static final String baseMessages = "ErrorCatalogue.properties";
	private static String baseConfigurationPath = null;
	private static Map<String, Properties> chachedProperties = new HashMap<String, Properties>();
	private static String NOT_FOUND_PROPERTY_PREFIX = "Not found property";
	private static String NOT_FOUND_PROPERTY = NOT_FOUND_PROPERTY_PREFIX+" '%1$s' for bundle '%2$s'";
	private FileConfigurationUtils(String baseConfigurationPath){}
	
	public static void initConfiguration(String baseConfigurationPath){
		setBaseConfigurationPath(baseConfigurationPath);
	}
	
	private static void setBaseConfigurationPath(String baseConfigurationPath) {
		FileConfigurationUtils.baseConfigurationPath = baseConfigurationPath;
	}
	private static String getBaseConfigurationPath() {
		return FileConfigurationUtils.baseConfigurationPath;
	}
	
	public static Map<String, Properties> getChachedProperties() {
		return chachedProperties;
	}
	
	public static String getConfiguration(String configurationBundle, String key, Object... values){
		if( getBaseConfigurationPath() == null ){
			return null;
		}
		Properties prop = getChachedProperties().get(configurationBundle);
		if( prop == null ){
			getChachedProperties().put(configurationBundle, loadPropertiesFile( getBaseConfigurationPath() + EAIConstants.DEFAULT_PATH_DELIMITER + baseConfiguration ));
		}
		String propValue = prop.getProperty(key);
		return propValue == null ? String.format(NOT_FOUND_PROPERTY, key, configurationBundle) : String.format( propValue, values);
	}
	public static Properties loadPropertiesFile(String filePath){
		Properties prop = new Properties();
		FileInputStream fis = null;
		try {
			fis = FileUtils.getFileInputStream( filePath );
			prop.load(fis);
			return prop;
		} catch (FileException e) {
			EAILogger.error(e);
			throw e;
		} catch (IOException e) {
			EAILogger.error(e);
			throw new FileException(e);
		} catch (Throwable e) {
			EAILogger.error(e);
			throw new ConfigurationException(e);
		} finally {
			FileUtils.forceClose(fis);
		}
	}
	public static Properties loadPropertiesFromClasspath(Class<?> className, String propertiesFilename){
		Properties prop = new Properties();
		FileInputStream fis = null;
		try {
			InputStream inputStream = className.getClassLoader().getResourceAsStream(propertiesFilename);
			prop.load(inputStream);
			return prop;
		} catch (FileException e) {
			EAILogger.error(e);
			throw e;
		} catch (IOException e) {
			EAILogger.error(e);
			throw new FileException(e);
		} catch (Throwable e) {
			EAILogger.error(e);
			throw new ConfigurationException(e);
		} finally {
			FileUtils.forceClose(fis);
		}
	}
	public static String getConfiguration(String key, Object... values){
		return getConfiguration(baseConfiguration, key, values);
	}
	public static String getMessage(String key, Object... values){
		return getConfiguration(baseMessages, key, values);
	}
	
	/**
	 * Web in a context of a J2EE load enviroment information
	 * 
	 * @param key
	 * @param args - replace constants in error messages $1%s
	 * @return
	 */
	public static String[] getWebConfiguration(String key){
		String configValue = null;
		try{
			Context envContext = (Context)(new InitialContext()).lookup("java:comp/env");
			configValue = (String)envContext.lookup(key);
			
		}catch(NamingException e){
			EAILogger.error(e);
	    }
		return StringUtils.getStringArray(configValue, ",");
	}
	
	public static boolean isEmptyProperty(String value){
		return StringUtils.isNullOrEmpty( value ) ? true : value.startsWith( NOT_FOUND_PROPERTY_PREFIX );
	}
}
