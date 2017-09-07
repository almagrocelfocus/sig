package com.vodafone.sobe.reporting;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.Message;
import org.junit.Test;

import com.eai.common.test.CommonTesterAbstract;
import com.vodafone.sobe.logger.db.Logger;

public class ExampleTest extends CommonTesterAbstract {	
	private org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class.getName());
	@Test
	public void loggerTest(){
		
		
		ThreadContext.put("key", "value");
		logger.info("test");
		logger.error("ERROR");
		
		
	}
}
