package com.eai.common;

public final class EAIConstants {
	
	public static final String DEFAULT_PERSISTENCE_UNIT = "eai-common-lib";
	
	public static final String[] PATH_DELIMITER = new String[]{"\\","/"};
	public static final String DEFAULT_PATH_DELIMITER = EAIConstants.PATH_DELIMITER[1];

	public static final String EMPTY_VALUE = "<NULL>";
	public static final String EMPTY_STRING = "";
	public static final String LIST_DELIMITER = ";";
	public static final String START_LINE = "start";
	public static final String LINE_SEPERATOR = System.getProperty("line.separator");
	public static final String WINDOWS_LINE_SEPERATOR = "\r\n";
	
	public static final String DEFAULT_ENCODING_PROPERTY = "encoding.source";
	public static final String EXCEL_DEFAULT_ENCODING_PROPERTY = "excel.default.encoding";
	public static final String RTF_DEFAULT_ENCODING_PROPERTY = "rtf.default.encoding";
	public static final String EMAIL_DEFAULT_ENCODING_PROPERTY = "email.default.encoding";
	public static final String XML_XPATH_FACTORY_SUFFIX = "EAICommonEngineXPATHFactory";
	public static final String XML_XPATH_FACTORY = "javax.xml.xpath.XPathFactory:"+XML_XPATH_FACTORY_SUFFIX;
	public static final String XML_XPATH_DEFAULT_PROPERTY = "xml.xpath."+XML_XPATH_FACTORY_SUFFIX;
	public static final String UNKWNOWN = "?";
	
	public static final String ERROR_TRANSFORMATION = "ERROR_TRANSFORMATION";
	
	private EAIConstants(){
	}
}
