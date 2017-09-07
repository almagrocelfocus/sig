package com.vodafone.sobe.logger.db;

import java.util.regex.Pattern;

public class SOAFReportPatterns {
	
	public static final Pattern MESSAGE_ID_PATTERN;
	public static final Pattern SERVICE_NAME_PATTERN;
	public static final Pattern LOG_LEVEL_PATTERN;
	public static final Pattern TASK_PATTERN;
	public static final Pattern CREATED_BY_PATTERN;
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
	
	private SOAFReportPatterns(){
		
	}
	
	static{
		MESSAGE_ID_PATTERN = Pattern.compile("<.*messageId>(.*)</.*messageId>");
		SERVICE_NAME_PATTERN = Pattern.compile("<.*serviceName>(.*)</.*serviceName>");
		LOG_LEVEL_PATTERN = Pattern.compile("<.*level>(.*)</.*level>");
		TASK_PATTERN = Pattern.compile("<.*task>(.*)</.*task>");
		CREATED_BY_PATTERN = Pattern.compile("<.*createdBy>(.*)</.*createdBy>");
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
	}
}
