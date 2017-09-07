package com.eai.common.test;

import java.util.Properties;

import javax.naming.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import com.eai.common.utils.FileConfigurationUtils;
import com.eai.common.utils.FileUtils;

public abstract class CommonTesterAbstract {
	protected Logger logger = LogManager.getLogger(this.getClass());
	private static Properties testProperties = null;
	
	@Before
	public void initTest() throws Exception {
		if(getTestProperties().getProperty(Context.INITIAL_CONTEXT_FACTORY) != null){
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, loadMandatoryProperty(Context.INITIAL_CONTEXT_FACTORY));
			System.setProperty(Context.PROVIDER_URL, loadMandatoryProperty(Context.PROVIDER_URL));
			System.setProperty(Context.SECURITY_PRINCIPAL, loadMandatoryProperty(Context.SECURITY_PRINCIPAL));
			System.setProperty(Context.SECURITY_CREDENTIALS, loadMandatoryProperty(Context.SECURITY_CREDENTIALS));
		}
	}
	public String loadMandatoryProperty(String key){
		String result = getTestProperties().getProperty(key);
		if(result == null){
			throw new RuntimeException("Missing property definition for: "+key);
		}
		return result;
	}

	protected static String getTestFilePath(String file){
		return FileUtils.getPath(getTestResourcePath(), file);
	}
	protected static String getTestResourcePath(){
		return "test/resources/";
	}
	protected static String getCommonTestFilePath(String file){
		return FileUtils.getPath(getTestResourcePath(), file);
	}
	
	protected static Properties getTestProperties(){
		if(testProperties == null){
			testProperties = FileConfigurationUtils.loadPropertiesFile(getCommonTestFilePath("test.properties"));
		}
		return testProperties;
	}
}
