package com.telco.osb.utils;

import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * OSB related functions
 */
public final class OSBUtils implements Serializable {
	private static final long serialVersionUID = -7282014249296605107L;
	private static final String SYSTEM_DEFAULT = "DEFAULT";
	private static final String SYSTEM_GDSP = "GDSP";
	private static final String SYSTEM_GDSP_SEL = "GDSPSEL";
	private static final String SYSTEM_GDSP_PROVISIONING = "GDSPPROVISIONING";
	private static final String SYSTEM_GDSP_WMR = "GDSPWMR";
	private static final String SYSTEM_SCE = "SCE";
	private static final String SYSTEM_PORTAL = "PORTAL";
	private static final String SYSTEM_OSB = "OSB";
	private static final String SYSTEM_EAI = "EAI";
	private static final String SYSTEM_GD = "GD";
	private static final String SYSTEM_GIG = "GIG";
	private static final String SYSTEM_AUDI_PLUGIN = "AUDI_PLUGIN";
	private static final String SYSTEM_OTA_MP = "OTA_MP";
	private static final String SYSTEM_MBB = "MBB";
	private static final String SYSTEM_EDB = "EDB";
	private static final String SYSTEM_HPSA = "HPSA";
	private static final String SYSTEM_CRAMER = "CRAMER";
	private static final String HEADER_EXTENDED = "headerExtended";
	private static final String RESPONSE_CODES = "responseCodes";
	private static final String RESPONSE_CODE = "responseCode";
	private static final String RESPONSE_MESSAGE = "responseMessage";
	private static final String EXTERNAL_ERROR = "externalError";
	private static final String ERROR_CODE = "errorCode";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String REPUB_CONTEXT = "repubContext";
	private static final String SYSTEM = "system";
	private static final String NUMBER_OF_RETRIES = "numberOfRetries";
	private static final String MAX_NUMBER_OF_RETRIES = "maxNumberOfRetries";

	private static final Map<String, String> ERROR_SYSTEM_CODE = new HashMap<String, String>();
	private static final Map<String, String> M2M_SYSTEM_CODE = new HashMap<String, String>();
	private static final List<String> EXTERNAL_HEADER = new ArrayList<String>();
	private static final List<String> ERROR_CODE_FROM_HEADER = new ArrayList<String>();
	private static final List<String> ERROR_MESSAGE_FROM_HEADER = new ArrayList<String>();
	private static final List<String> EXTERNAL_ERROR_CODE_FROM_HEADER = new ArrayList<String>();
	private static final List<String> EXTERNAL_ERROR_MESSAGE_FROM_HEADER = new ArrayList<String>();
	private static final List<String> EXTERNAL_ERROR_SYSTEM_FROM_HEADER = new ArrayList<String>();
	private static final List<String> RETRY_NUMBER = new ArrayList<String>();
	private static final List<String> MAX_RETRY_NUMBER = new ArrayList<String>();

	static {

		ERROR_SYSTEM_CODE.put(SYSTEM_DEFAULT, "00");
		ERROR_SYSTEM_CODE.put(SYSTEM_OSB, "30");
		ERROR_SYSTEM_CODE.put(SYSTEM_EAI, "30");
		ERROR_SYSTEM_CODE.put(SYSTEM_GDSP, "31");
		ERROR_SYSTEM_CODE.put(SYSTEM_SCE, "32");
		ERROR_SYSTEM_CODE.put(SYSTEM_GD, "33");
		ERROR_SYSTEM_CODE.put(SYSTEM_GIG, "34");
		ERROR_SYSTEM_CODE.put(SYSTEM_SCE, "35");
		ERROR_SYSTEM_CODE.put(SYSTEM_AUDI_PLUGIN, "36");
		ERROR_SYSTEM_CODE.put(SYSTEM_OTA_MP, "37");
		ERROR_SYSTEM_CODE.put(SYSTEM_MBB, "38");

		ERROR_SYSTEM_CODE.put(SYSTEM_EDB, "40");
		ERROR_SYSTEM_CODE.put(SYSTEM_CRAMER, "41");
		ERROR_SYSTEM_CODE.put(SYSTEM_HPSA, "42");

	}
	static {
		// this list was duplicated since M2M OSB project defines different
		// system ids
		M2M_SYSTEM_CODE.put(SYSTEM_DEFAULT, "00");
		M2M_SYSTEM_CODE.put(SYSTEM_GDSP, "01");
		M2M_SYSTEM_CODE.put(SYSTEM_GDSP_SEL, "02");
		M2M_SYSTEM_CODE.put(SYSTEM_GDSP_PROVISIONING, "03");
		M2M_SYSTEM_CODE.put(SYSTEM_GDSP_WMR, "04");
		M2M_SYSTEM_CODE.put(SYSTEM_SCE, "05");
		M2M_SYSTEM_CODE.put(SYSTEM_PORTAL, "10");
		M2M_SYSTEM_CODE.put(SYSTEM_EAI, "30");
		M2M_SYSTEM_CODE.put(SYSTEM_OSB, "30");
		M2M_SYSTEM_CODE.put(SYSTEM_GD, "33");
		M2M_SYSTEM_CODE.put(SYSTEM_GIG, "34");
		M2M_SYSTEM_CODE.put(SYSTEM_AUDI_PLUGIN, "35");
		M2M_SYSTEM_CODE.put(SYSTEM_OTA_MP, "37");
	}

	static {
		EXTERNAL_HEADER.add("externalHeader");
	}

	static {
		ERROR_CODE_FROM_HEADER.add(RESPONSE_CODES);
		ERROR_CODE_FROM_HEADER.add(RESPONSE_CODE);
	}

	static {
		ERROR_MESSAGE_FROM_HEADER.add(RESPONSE_CODES);
		ERROR_MESSAGE_FROM_HEADER.add(RESPONSE_MESSAGE);
	}

	static {
		EXTERNAL_ERROR_CODE_FROM_HEADER.add(RESPONSE_CODES);
		EXTERNAL_ERROR_CODE_FROM_HEADER.add(EXTERNAL_ERROR);
		EXTERNAL_ERROR_CODE_FROM_HEADER.add(ERROR_CODE);
	}

	static {
		EXTERNAL_ERROR_MESSAGE_FROM_HEADER.add(RESPONSE_CODES);
		EXTERNAL_ERROR_MESSAGE_FROM_HEADER.add(EXTERNAL_ERROR);
		EXTERNAL_ERROR_MESSAGE_FROM_HEADER.add(ERROR_MESSAGE);
	}

	static {
		EXTERNAL_ERROR_SYSTEM_FROM_HEADER.add(RESPONSE_CODES);
		EXTERNAL_ERROR_SYSTEM_FROM_HEADER.add(EXTERNAL_ERROR);
		EXTERNAL_ERROR_SYSTEM_FROM_HEADER.add(SYSTEM);
	}

	static {
		RETRY_NUMBER.add(HEADER_EXTENDED);
		RETRY_NUMBER.add(REPUB_CONTEXT);
		RETRY_NUMBER.add(NUMBER_OF_RETRIES);
	}

	static {
		MAX_RETRY_NUMBER.add(HEADER_EXTENDED);
		MAX_RETRY_NUMBER.add(REPUB_CONTEXT);
		MAX_RETRY_NUMBER.add(MAX_NUMBER_OF_RETRIES);
	}

	/*
	 * Constructor
	 */
	private OSBUtils() {
	}

	/**
	 * @param system
	 *            identifier or osb endpoint name present in $inbound variable.
	 * @return system code with 2 digits if is a know system or 00 otherwise.
	 * */
	public static String getErrorSystemCode(String endpoint) {
		String result = null;
		if (endpoint != null && !endpoint.isEmpty()) {
			String[] endpoints = null;
			if (endpoint.contains("$")) {
				endpoints = endpoint.split("\\$");
			} else if (endpoint.contains("/")) {
				endpoints = endpoint.split("/");
			}

			if (endpoints != null && endpoints.length > 1) {
				result = ERROR_SYSTEM_CODE
						.get(endpoints[1].toUpperCase() != null ? endpoints[1]
								.toUpperCase() : SYSTEM_DEFAULT);
			}
		}
		if (result == null) {
			result = ERROR_SYSTEM_CODE.get(SYSTEM_DEFAULT);
		}
		return result;
	}

	/**
	 * @param system
	 *            identifier from string.
	 * @return system code with 2 digits if is a know system or 00 otherwise.
	 * */
	public static String getSystemCode(String systemName) {
		String result = null;
		if (systemName != null && !systemName.isEmpty()) {
			result = ERROR_SYSTEM_CODE.get(systemName.toUpperCase());
		}
		if (result == null) {
			result = ERROR_SYSTEM_CODE.get(SYSTEM_DEFAULT);
		}
		return result;
	}

	/**
	 * Return the original external header
	 * 
	 * @param headerXmlElement
	 * @return
	 */
	public static final Element getExternalHeader(Element headerXmlElement) {
		return extractElementByNameLst(headerXmlElement, EXTERNAL_HEADER);
	}

	/**
	 * Return the error code if exists
	 * 
	 * @return
	 */
	public static final String getErrorCode(Element headerXmlElement) {
		return extractValueByNameLst(headerXmlElement, ERROR_CODE_FROM_HEADER);
	}

	public static final String getErrorMessage(Element headerXmlElement) {
		return extractValueByNameLst(headerXmlElement,
				ERROR_MESSAGE_FROM_HEADER);
	}

	/**
	 * Return the external system error code if exists
	 * 
	 * @return
	 */
	public static final String getExternalErrorCode(Element headerXmlElement) {
		return extractValueByNameLst(headerXmlElement,
				EXTERNAL_ERROR_CODE_FROM_HEADER);
	}

	public static final String getExternalErrorMessage(Element headerXmlElement) {
		return extractValueByNameLst(headerXmlElement,
				EXTERNAL_ERROR_MESSAGE_FROM_HEADER);
	}

	/**
	 * Return the system associated to the external error code
	 * 
	 * @return
	 */
	public static final String getExternalErrorSystem(Element headerXmlElement) {
		return extractValueByNameLst(headerXmlElement,
				EXTERNAL_ERROR_SYSTEM_FROM_HEADER);
	}

	public static final Boolean isMaxRetriesReached(Element headerXmlElement) {
		String numberOfRetries = extractValueByNameLst(headerXmlElement,
				RETRY_NUMBER);
		String maxNumberOfRetries = extractValueByNameLst(headerXmlElement,
				MAX_RETRY_NUMBER);
		if (numberOfRetries != null && !numberOfRetries.isEmpty()
				&& maxNumberOfRetries != null && !maxNumberOfRetries.isEmpty()) {
			return Integer.valueOf(numberOfRetries) >= Integer
					.valueOf(maxNumberOfRetries);
		}
		return false;
	}

	private static final Element extractElementByNameLst(Node node,
			List<String> elementNameLst) {
		NodeList nodeLst = node.getChildNodes();
		Element currentNode = null;
		boolean found = false;
		for (int i = 0; i < elementNameLst.size(); i++) {
			String elementName = elementNameLst.get(i);
			found = false;
			if (nodeLst == null) {
				return null;
			}
			for (int j = 0; j < nodeLst.getLength(); j++) {
				currentNode = (Element) nodeLst.item(j);
				if (currentNode != null
						&& elementName.equalsIgnoreCase(currentNode
								.getLocalName())) {
					nodeLst = currentNode.getChildNodes();
					found = true;
					return currentNode;
				}
			}
		}
		if (!found) {
			return null;
		}
		return currentNode;
	}

	private static final String extractValueByNameLst(Node node,
			List<String> elementNameLst) {
		Element currentNode = extractElementByNameLst(node, elementNameLst);
		// Since getTextContent is not implemented in
		// org.apache.xmlbeans.impl.store.DomImpl we need to go old school
		// Caused by: "java.lang.RuntimeException": DOM Level 3 Not implemented
		// at org.apache.xmlbeans.impl.store.DomImpl._node_getTextContent
		if (currentNode != null && currentNode.getChildNodes().getLength() > 0) {
			StringBuilder strBldr = new StringBuilder();
			for (int i = 0; i < currentNode.getChildNodes().getLength(); i++) {
				Node childNode = currentNode.getChildNodes().item(i);
				if (childNode.getNodeType() == Node.TEXT_NODE) {
					strBldr.append(childNode.getNodeValue());
				}
			}
			return strBldr.toString();
		}
		return null;
	}

	public static final Boolean isLogActive(String systemLogLevel,
			String logLevel) {
		LogLevelEnum systemLog = LogLevelEnum.valueOfEnum(systemLogLevel);
		LogLevelEnum log = LogLevelEnum.valueOfEnum(logLevel);
		return systemLog != LogLevelEnum.OFF
				&& log != LogLevelEnum.OFF
				&& systemLog.getIntLevel() <= LogLevelEnum
						.valueOfEnum(logLevel).getIntLevel();
	}

	private enum LogLevelEnum {
		ALL(0), TRACE(1), DEBUG(2), INFO(3), WARN(4), ERROR(5), OFF(6);

		private int intLevel;

		private LogLevelEnum(int intLevel) {
			this.intLevel = intLevel;
		}

		public static final LogLevelEnum valueOfEnum(String levelStr) {
			if (levelStr != null) {
				for (LogLevelEnum level : LogLevelEnum.values()) {
					if (level.toString().equalsIgnoreCase(levelStr)) {
						return level;
					}
				}
			}
			// by default we want to mark log level as debug
			return OFF;
		}

		public int getIntLevel() {
			return intLevel;
		}
	}

	/**
	 * Convert long to a dateTime
	 * 
	 * @param longTime
	 *            long value time
	 * @return dateTime with yyyy-MM-dd'T'HH:mm:ss format
	 */
	public static String convertLongToDatetime(String longTime) {

		long time = Long.parseLong(longTime);

		Date date = new Date(time);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		f.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		

		return f.format(date).toString();
	}

	/**
	 * Convert dateTime to a long
	 * 
	 * @param dateTime
	 *            dateTime with yyyy-MM-dd'T'HH:mm:ss format
	 * @return long value time
	 * @throws ParseException
	 */
	public static String convertDatetimeToLong(String dateTime)
			throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		f.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date d = f.parse(dateTime);
		long milliseconds = d.getTime();
		return String.valueOf(milliseconds);
	}

}
