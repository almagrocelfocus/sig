package com.vodafone.sobe.logger.db;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.xmlbeans.XmlObject;
import org.slf4j.MDC;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.joran.util.ConfigurationWatchListUtil;

import com.bea.wli.reporting.jmsprovider.runtime.ReportMessage;

@MessageDriven(mappedName = "wli.reporting.jmsprovider.queue", name = "SOAFReportHandler")
public class SOAFReportHandler implements MessageListener {

	private static final org.slf4j.Logger LOGG = org.slf4j.LoggerFactory.getLogger(SOAFReportHandler.class.getName());
	private static final org.slf4j.Logger LOGG_EVENT = org.slf4j.LoggerFactory.getLogger(SOAFReportEventHandler.class.getName());
	private static final org.slf4j.Logger LOGG_ERRORS = org.slf4j.LoggerFactory.getLogger(SOAFReportErrorHandler.class.getName());
	
	private static final String TRACE = "TRACE";
	private static final String DEBUG = "DEBUG";
	private static final String INFO = "INFO";
	private static final String WARN = "WARN";
	private static final String ERROR = "ERROR";
	private static final String FATAL = "FATAL";

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof ObjectMessage) {
				
//				//Check logback.xml location:
//		        LoggerContext loggerContext = ((ch.qos.logback.classic.Logger)LOGG).getLoggerContext();
//		        URL mainURL = ConfigurationWatchListUtil.getMainWatchURL(loggerContext);
//		        System.out.println(mainURL);
//		        LOGG.info("Logback used '{}' as the configuration file.", mainURL);
				
				ObjectMessage myObject = (ObjectMessage) message;
				String messageClassName = myObject.getObject().getClass().getSimpleName();
				if ("ReportMessage".equals(messageClassName)) {
					ReportMessage myReportMessage = (ReportMessage) myObject.getObject();

					//handleReportData(myReportMessage.getXmlPayload(), myReportMessage.getXmlPayload().toString());
					handleReportData(myReportMessage.getMetadata(), myReportMessage.getXmlPayload().toString());
				}
			}
			
		} catch (JMSException e) {
			LOGG_ERRORS.error("Message is invalid!", e);
		}
	}

	public void handleReportData(XmlObject xmlMetadata, String payload) {

		LoggingFields loggingFields = new LoggingFields();

		try {
			extractDataFromPayload(loggingFields, payload);

			extractDataFromMetadata(loggingFields, xmlMetadata.toString());

			sendLog(loggingFields, payload, xmlMetadata.toString());

		} catch (Exception e) {
			LOGG_ERRORS.error("Failed to log a message!", e);
		}
	}

	private void extractDataFromPayload(LoggingFields loggingFields, String payload) {
		// Payload parsing start
		if (payload == null || payload.isEmpty()) {
			return;
		}

		loggingFields.setRequestId(extractPatternFromPayload(payload, SOAFReportPatterns.REQUEST_ID_PATTERN));
		loggingFields.setCorrelationId(extractPatternFromPayload(payload, SOAFReportPatterns.CORRELATION_ID_PATTERN));
		
		loggingFields.setDomain(extractPatternFromPayload(payload, SOAFReportPatterns.DOMAIN_PATTERN));
		loggingFields.setCategory(extractPatternFromPayload(payload, SOAFReportPatterns.CATEGORY_PATTERN));
		loggingFields.setTarget(extractPatternFromPayload(payload, SOAFReportPatterns.TARGET_PATTERN));
		loggingFields.setService(extractPatternFromPayload(payload, SOAFReportPatterns.SERVICE_PATTERN));
		loggingFields.setOperation(extractPatternFromPayload(payload, SOAFReportPatterns.OPERATION_PATTERN));
		loggingFields.setVersion(extractPatternFromPayload(payload, SOAFReportPatterns.VERSION_PATTERN));
		loggingFields.setSource(extractPatternFromPayload(payload, SOAFReportPatterns.SOURCE_PATTERN));
		loggingFields.setTargetEndpoint(extractPatternFromPayload(payload, SOAFReportPatterns.TARGET_ENDPOINT_PATTERN));
		
		loggingFields.setLogLevel(extractPatternFromPayload(payload, SOAFReportPatterns.LOG_LEVEL_PATTERN));
		loggingFields.setTask(extractPatternFromPayload(payload, SOAFReportPatterns.TASK_PATTERN));
		loggingFields.setUsername(extractPatternFromPayload(payload, SOAFReportPatterns.USERNAME_PATTERN));
		
		loggingFields.setTimestamp(extractPatternFromPayload(payload, SOAFReportPatterns.TIMESTAMP_PATTERN));
		loggingFields.setStatusCode(extractPatternFromPayload(payload, SOAFReportPatterns.STATUS_CODE_PATTERN));
		loggingFields.setStatusMessage(extractPatternFromPayload(payload, SOAFReportPatterns.STATUS_MESSAGE_PATTERN));
		loggingFields.setEngine(extractPatternFromPayload(payload, SOAFReportPatterns.ENGINE_PATTERN));
		loggingFields.setRequestorRequestId(extractPatternFromPayload(payload, SOAFReportPatterns.REQUESTOR_REQUEST_ID_PATTERN));
		loggingFields.setRequestorIp(extractPatternFromPayload(payload, SOAFReportPatterns.REQUESTOR_IP_PATTERN));
		loggingFields.setRequestorAgent(extractPatternFromPayload(payload, SOAFReportPatterns.REQUESTOR_AGENT_PATTERN));
		loggingFields.setSessionId(extractPatternFromPayload(payload, SOAFReportPatterns.SESSION_ID_PATTERN));
		loggingFields.setAction(extractPatternFromPayload(payload, SOAFReportPatterns.ACTION_PATTERN));
		loggingFields.setObjectId(extractPatternFromPayload(payload, SOAFReportPatterns.OBJECT_ID_PATTERN));
		loggingFields.setDescription(extractPatternFromPayload(payload, SOAFReportPatterns.DESCRIPTION_PATTERN));
		loggingFields.setTimeToComplete(extractPatternFromPayload(payload, SOAFReportPatterns.TIME_TO_COMPLETE_PATTERN));
		loggingFields.setAdapterTimeSum(extractPatternFromPayload(payload, SOAFReportPatterns.ADAPTER_TIME_SUM_PATTERN));
		loggingFields.setTargetRequestId(extractPatternFromPayload(payload, SOAFReportPatterns.TARGET_REQUEST_ID_PATTERN));
		loggingFields.setTargetIp(extractPatternFromPayload(payload, SOAFReportPatterns.TARGET_IP_PATTERN));
		loggingFields.setTargetAgent(extractPatternFromPayload(payload, SOAFReportPatterns.TARGET_AGENT_PATTERN));
		
		loggingFields.setPayload(extractPatternFromPayload(payload, SOAFReportPatterns.PAYLOAD_PATTERN));
		
	}

	private String extractPatternFromPayload(String payload, Pattern pattern) {
		Matcher matcher = pattern.matcher(payload);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "N/A";
	}

	// To Check if some values that are not on payload can be extracted from
	// metadata
	private void extractDataFromMetadata(LoggingFields loggingFields, String metadata) {

		if ("".equals(loggingFields.getTimestamp())) {
			// <rep:timestamp>2014-02-25T14:19:06.584Z</rep:timestamp>
			Pattern timestampPattern = Pattern.compile("<timestamp>(.*)</timestamp>");
			Matcher matcher = timestampPattern.matcher(metadata);

			if (matcher.find()) {
				loggingFields.setTimestamp(matcher.group(1));
			}
		}

		// Labels Parser start
		Pattern pattern = SOAFReportPatterns.LABELS_PATTERN;
		Matcher matcher = pattern.matcher(metadata);

		if (matcher.find()) {
			loggingFields.setLabels(matcher.group(1));
		}
		// Labels Parser end

	}

	private void sendLog(LoggingFields loggingFields, String payload, String metadata) {

		setLogMDC(loggingFields, payload);

		boolean isEventTask = "SERVICE_END".equalsIgnoreCase(loggingFields.getTask())
				|| "SERVICE_ERROR".equalsIgnoreCase(loggingFields.getTask());
		if (TRACE.equalsIgnoreCase(loggingFields.getLogLevel())) {
			LOGG.trace(metadata);
		} else if (DEBUG.equalsIgnoreCase(loggingFields.getLogLevel())) {
			LOGG.debug(metadata);
		} else if (INFO.equalsIgnoreCase(loggingFields.getLogLevel())) {
			LOGG.info(metadata);
		} else if (WARN.equalsIgnoreCase(loggingFields.getLogLevel())) {
			LOGG.warn(metadata);
		} else if (ERROR.equalsIgnoreCase(loggingFields.getLogLevel())) {
			LOGG.error(metadata);
		} else if (FATAL.equalsIgnoreCase(loggingFields.getLogLevel())) {
			LOGG_ERRORS.error("FATAL not supported in Logback configuration!! Use error instead!");
			LOGG_ERRORS.error(metadata);
		}

		if (isEventTask) {
			sendLogEventTask(loggingFields.getLogLevel(), metadata);
		}
	}

	private void sendLogEventTask(String logLevel, String metadata) {
		if (TRACE.equalsIgnoreCase(logLevel)) {
			LOGG_EVENT.trace(metadata);
		} else if (DEBUG.equalsIgnoreCase(logLevel)) {
			LOGG_EVENT.debug(metadata);
		} else if (INFO.equalsIgnoreCase(logLevel)) {
			LOGG_EVENT.info(metadata);
		} else if (WARN.equalsIgnoreCase(logLevel)) {
			LOGG_EVENT.warn(metadata);
		} else if (ERROR.equalsIgnoreCase(logLevel)) {
			LOGG_EVENT.error(metadata);
		} else if (FATAL.equalsIgnoreCase(logLevel)) {
			LOGG_ERRORS.error("FATAL not supported in Logback configuration!! Use error instead!");
			LOGG_ERRORS.error(metadata);
		}
	}

	private void setLogMDC(LoggingFields loggingFields, String payload) {

		MDC.put("request_id", loggingFields.getRequestId());
		MDC.put("correlation_id",loggingFields.getCorrelationId());
		
		MDC.put("domain", loggingFields.getDomain());
		MDC.put("category", loggingFields.getCategory());
		MDC.put("target", loggingFields.getTarget());
		MDC.put("service", loggingFields.getService());
		MDC.put("operation", loggingFields.getOperation());
		MDC.put("version", loggingFields.getVersion());
		MDC.put("source", loggingFields.getSource());
		MDC.put("target_endpoint", loggingFields.getTargetEndpoint());
		
		MDC.put("log_level", loggingFields.getLogLevel());
		MDC.put("task", loggingFields.getTask());
		MDC.put("username", loggingFields.getUsername());
		MDC.put("engine", loggingFields.getEngine());
		MDC.put("timestamp", loggingFields.getTimestamp());
		
		MDC.put("payload", "N/A".equals(loggingFields.getPayload())|| "".equals(loggingFields.getPayload()) ? "N/A" : "<payload>" + loggingFields.getPayload() + "</payload>");
		
		MDC.put("status_code", loggingFields.getStatusCode());
		MDC.put("status_message", loggingFields.getStatusMessage());
		MDC.put("labels", loggingFields.getLabels());

		MDC.put("requestor_request_id", loggingFields.getRequestorRequestId());
		MDC.put("requestor_ip", loggingFields.getRequestorIp());
		MDC.put("requestor_agent", loggingFields.getRequestorAgent());
		MDC.put("session_id", loggingFields.getSessionId());
		MDC.put("action", loggingFields.getAction());
		MDC.put("object_id", loggingFields.getObjectId());
		MDC.put("description", loggingFields.getDescription());
		MDC.put("time_to_complete", loggingFields.getTimeToComplete());
		MDC.put("adapter_time_sum", loggingFields.getAdapterTimeSum());
		MDC.put("target_request_id", "".equals(loggingFields.getTargetRequestId()) ? "N/A" : loggingFields.getTargetRequestId());
		MDC.put("target_ip", "".equals(loggingFields.getTargetIp()) ? "N/A" : loggingFields.getTargetIp());
		MDC.put("target_agent", "".equals(loggingFields.getTargetAgent()) ? "N/A" : loggingFields.getTargetAgent());
	
		Map<String, String> labelsKVP = extractKVPFromLabels(loggingFields.getLabels());
		
	}
	
	private final String DYNAMIC_KEY = "dynamicKey";
	private Map<String, String> extractKVPFromLabels(String labels){
		
		Map<String, String> kvpMap = new HashMap<String, String>();
		
		String[] labelValues = labels.split(";");
		
		for (String s : labelValues) {
			String[] label = s.split("=");
			if (label.length == 2) {
				
				if(DYNAMIC_KEY.equals(label[0])){
					String[] dynamicKVP = label[1].split("_");
					if(dynamicKVP.length == 2){
						kvpMap.put(dynamicKVP[0], dynamicKVP[1]);
					}else{LOGG_ERRORS.warn("Dynamic Key Malformed: " + label[1]);}
				}
				else{
					kvpMap.put(label[0], label[1]);
				}
				
			}
		}
		
		
		return kvpMap;
	}

}