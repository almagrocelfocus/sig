package com.vodafone.sobe.logger.db;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LoggerOperations includes extract functions 
 * 
 *
 */
public class LoggerOperations{
	
	
	public static Map<String, String> extractKeyValuesFromLabels(String dynamicKeys){
		
		 Map<String, String> kvpMap = new HashMap<String, String>();
		 Matcher MATCHER_DYNAMIC_KEY = SOAFReportPatterns.DYNAMIC_KEY_PATTERN.matcher(dynamicKeys);
		 String DYNAMIC_KEY;
		 
		    while (MATCHER_DYNAMIC_KEY.find()) {
		    	
		        DYNAMIC_KEY = MATCHER_DYNAMIC_KEY.group(1);
		        kvpMap.put(extractPatternFromPayload(DYNAMIC_KEY,SOAFReportPatterns.DYNAMIC_KEY_NAME_PATTERN), extractPatternFromPayload(DYNAMIC_KEY,SOAFReportPatterns.DYNAMIC_KEY_VALUE_PATTERN));
		    }
		    
		   return kvpMap;
	}
	
	public static String extractPatternFromPayload(String payload, Pattern pattern) {
		Matcher matcher = pattern.matcher(payload);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return "N/A";
	}
	
	// To Check if some values that are not on payload can be extracted from
	// metadata
	public static void extractDataFromMetadata(LoggingFields loggingFields, String metadata) {

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
	
}
