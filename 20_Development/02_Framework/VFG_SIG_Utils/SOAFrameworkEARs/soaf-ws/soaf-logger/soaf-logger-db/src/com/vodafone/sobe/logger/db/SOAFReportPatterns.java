package com.vodafone.sobe.logger.db;

import java.util.regex.Pattern;

public class SOAFReportPatterns {
	
	
	public static final Pattern REQUEST_ID_PATTERN;
	public static final Pattern CORRELATION_ID_PATTERN;
	
	public static final Pattern	DOMAIN_PATTERN;
	public static final Pattern	CATEGORY_PATTERN;
	public static final Pattern	TARGET_PATTERN;
	public static final Pattern	SERVICE_PATTERN;
	public static final Pattern	OPERATION_PATTERN;
	public static final Pattern	VERSION_PATTERN;
	public static final Pattern	SOURCE_PATTERN;
	public static final Pattern	TARGET_ENDPOINT_PATTERN;
	
	
	public static final Pattern LOG_LEVEL_PATTERN;
	public static final Pattern TASK_PATTERN;
	public static final Pattern USERNAME_PATTERN;
	public static final Pattern TIMESTAMP_PATTERN;
	public static final Pattern STATUS_CODE_PATTERN;
	public static final Pattern STATUS_MESSAGE_PATTERN;
	public static final Pattern ENGINE_PATTERN;
	
	
	public static final Pattern REQUESTOR_REQUEST_ID_PATTERN;
	public static final Pattern REQUESTOR_IP_PATTERN;
	public static final Pattern REQUESTOR_AGENT_PATTERN;
	public static final Pattern SESSION_ID_PATTERN;
	public static final Pattern ACTION_PATTERN;
	public static final Pattern OBJECT_ID_PATTERN;
	public static final Pattern DESCRIPTION_PATTERN;
	public static final Pattern TIME_TO_COMPLETE_PATTERN;
	public static final Pattern ADAPTER_TIME_SUM_PATTERN;
	public static final Pattern TARGET_REQUEST_ID_PATTERN;
	public static final Pattern TARGET_IP_PATTERN;
	public static final Pattern TARGET_AGENT_PATTERN;
	
	public static final Pattern LABELS_PATTERN;
	
	public static final Pattern PAYLOAD_PATTERN;
	
	private SOAFReportPatterns(){
		
	}
	
	static{
		REQUEST_ID_PATTERN = Pattern.compile("<.*requestId>(.*)</.*requestId>");
		CORRELATION_ID_PATTERN = Pattern.compile("<.*correlationId>(.*)</.*correlationId>");
		
		DOMAIN_PATTERN = Pattern.compile("<.*domain>(.*)</.*domain>");
		CATEGORY_PATTERN = Pattern.compile("<.*category>(.*)</.*category>");
		TARGET_PATTERN = Pattern.compile("<.*target>(.*)</.*target>");
		SERVICE_PATTERN = Pattern.compile("<.*service>(.*)</.*service>");
		OPERATION_PATTERN = Pattern.compile("<.*operation>(.*)</.*operation>");
		VERSION_PATTERN = Pattern.compile("<.*version>(.*)</.*version>");
		
		SOURCE_PATTERN = Pattern.compile("<.*source>(.*)</.*source>");
		TARGET_ENDPOINT_PATTERN = Pattern.compile("<.*targetEndpoint>(.*)</.*targetEndpoint>");
		
		
		LOG_LEVEL_PATTERN = Pattern.compile("<.*level>(.*)</.*level>");
		TASK_PATTERN = Pattern.compile("<.*task>(.*)</.*task>");
		USERNAME_PATTERN = Pattern.compile("<.*username>(.*)</.*username>");
		TIMESTAMP_PATTERN = Pattern.compile("<.*timestamp>(.*)</.*timestamp>");
		STATUS_CODE_PATTERN = Pattern.compile("<.*statusCode>(.*)</.*statusCode>");
		//STATUS_MESSAGE_PATTERN = Pattern.compile("<.*statusMessage>(.*)</.*statusMessage>", Pattern.DOTALL);
		STATUS_MESSAGE_PATTERN = Pattern.compile("<[a-zA-Z0-9:]*statusMessage>(.*?)</[a-zA-Z0-9:]*statusMessage>", Pattern.DOTALL);
		ENGINE_PATTERN = Pattern.compile("<.*engine>(.*)</.*engine>");
				
		REQUESTOR_REQUEST_ID_PATTERN = Pattern.compile("<.*requestorRequestId>(.*)</.*requestorRequestId>");
		REQUESTOR_IP_PATTERN = Pattern.compile("<.*requestorIp>(.*)</.*requestorIp>");
		REQUESTOR_AGENT_PATTERN = Pattern.compile("<.*requestorAgent>(.*)</.*requestorAgent>");
		SESSION_ID_PATTERN = Pattern.compile("<.*sessionId>(.*)</.*sessionId>");
		ACTION_PATTERN = Pattern.compile("<.*action>(.*)</.*action>");
		OBJECT_ID_PATTERN = Pattern.compile("<.*objectId>(.*)</.*objectId>");
		DESCRIPTION_PATTERN = Pattern.compile("<.*description>(.*)</.*description>");
		TIME_TO_COMPLETE_PATTERN = Pattern.compile("<.*timeToComplete>(.*)</.*timeToComplete>");
		ADAPTER_TIME_SUM_PATTERN = Pattern.compile("<.*adapterTimeSum>(.*)</.*adapterTimeSum>");
		TARGET_REQUEST_ID_PATTERN = Pattern.compile("<.*targetRequestId>(.*)</.*targetRequestId>");
		TARGET_IP_PATTERN = Pattern.compile("<.*targetIp>(.*)</.*targetIp>");
		TARGET_AGENT_PATTERN = Pattern.compile("<.*targetAgent>(.*)</.*targetAgent>");

		LABELS_PATTERN = Pattern.compile("<.*labels>(.*)</.*labels>");
		
		PAYLOAD_PATTERN = Pattern.compile("<[a-zA-Z0-9:]*payload>(.*?)</[a-zA-Z0-9:]*payload>", Pattern.DOTALL);
	
	}
}
