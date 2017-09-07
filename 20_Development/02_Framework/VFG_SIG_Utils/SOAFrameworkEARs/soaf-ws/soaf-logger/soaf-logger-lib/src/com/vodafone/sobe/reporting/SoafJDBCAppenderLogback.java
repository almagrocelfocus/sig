package com.vodafone.sobe.reporting;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

public class SoafJDBCAppenderLogback extends AppenderBase<ILoggingEvent>{

	@Override
	protected void append(ILoggingEvent logEvent) {
		String s = logEvent.getMessage();
		SoafCommonJdbcAppender.append(logEvent.getMDCPropertyMap(), logEvent.getMessage().toString());
	}
}
