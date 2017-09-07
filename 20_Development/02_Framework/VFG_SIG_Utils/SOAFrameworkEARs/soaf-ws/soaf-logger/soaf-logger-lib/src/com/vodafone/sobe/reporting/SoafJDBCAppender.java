package com.vodafone.sobe.reporting;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name="SoafJDBCAppender", category="Core", elementType="appender", printObject=true)
public class SoafJDBCAppender extends AbstractAppender{
	
	@SuppressWarnings("unchecked")
	public SoafJDBCAppender(String name, Filter filter, @SuppressWarnings("rawtypes") Layout layout, boolean handleExceptions, String jndiName){
		super(name, filter, layout, handleExceptions);
	}
	
	
	@PluginFactory
	public static SoafJDBCAppender createAppender(@PluginAttribute("name") String name,
													@PluginAttribute("ignoreExceptions") String ignore,
													@PluginAttribute("jndiName") String jndi,
													@SuppressWarnings("rawtypes") @PluginElement("Layout") Layout layout,
													@PluginElement("Filters") Filter filter){
		
		
		boolean ignoreExceptions = Boolean.parseBoolean(ignore);
		
		return new SoafJDBCAppender(name, filter, layout, ignoreExceptions, jndi);
	}


	@Override
	public void append(LogEvent logEvent) {
		SoafCommonJdbcAppender.append(logEvent.getContextMap(), logEvent.getMessage().toString());
	}
}
