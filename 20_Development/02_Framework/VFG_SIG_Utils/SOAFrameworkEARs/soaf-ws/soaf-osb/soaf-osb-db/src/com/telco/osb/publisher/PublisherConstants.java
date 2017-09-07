package com.telco.osb.publisher;

public interface PublisherConstants {
	public static final String REPUBLISHER_ASYNC_CONNECTION_FACTORY = "jms/soaf-osb-connection-factory";
	public static final String REPUBLISHER_ASYNC_QUEUE = "jms/soaf-osb-republisher-queue";
	public static final String REPUBLISHER_ASYNC_ID = "SoafRepublisherId";
	public static final String REPUBLISHER_ASYNC_CONFIG_ID = "SoafRepublisherConfigurationId";
	public static final String REPUBLISHER_ASYNC_MAX_RETRIES = "SoafRepublisherMaxRetries";
	
	public static final int ERROR_CODE_MAX_LENGTH = 3000;
	
	public static final String PUBLISHER_WS_REPUBLISH_EVENT_URL_PROPERTY_NAME = "publisher.republishEventUrl";
	public static final String PUBLISHER_WS_TIMEOUT_PROPERTY_NAME = "publisher.timeoutSeconds";
	public static final String PUBLISHER_WS_USERNAME_PROPERTY_NAME = "publisher.username";
	public static final String PUBLISHER_WS_PASSWORD_PROPERTY_NAME = "publisher.password";
	
	public static final String PUBLISHER_SCHEDULER_FREQUENCY_SEC = "publisher.schedulerFrequencyInSeconds";
	public static final String PUBLISHER_SCHEDULER_MAX_RESULTS = "publisher.maxNumberOfQueryResults";
	
	public static final String SECURITY_POLICY_ORACLE_USER_PASS = "oracle/ovd_sql_policy";
}
