package com.vodafone.sobe.reporting;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vodafone.sobe.logger.db.SOAFReportPatterns;

public class SoafCommonJdbcAppender {
	private SoafCommonJdbcAppender() {
	}

	private static final String DYNAMIC_KEY = "dynamicKey";
	public static void append(Map<String, String> map, String mathcherKey) {
		// Labels Parser start
		Pattern pattern = SOAFReportPatterns.LABELS_PATTERN;
		Matcher matcher = pattern.matcher(mathcherKey);

		String labels = "";
		Map<String, String> dictionary = new HashMap<String, String>();
		if (matcher.find()) {
			labels = matcher.group(1);

			String[] labelValues = labels.split(";");

			for (String s : labelValues) {
				String[] label = s.split("=");
				if (label.length == 2) {
					//dictionary.put(label[0], label[1]);
					if(DYNAMIC_KEY.equals(label[0])){
						String[] dynamicKVP = label[1].split("_",2); // Get the first split 
						if(dynamicKVP.length == 2){
							dictionary.put(dynamicKVP[0], dynamicKVP[1]);							
						}
						else{
							dictionary.put(dynamicKVP[0], ""); // Empty value
						}                                 
						
					}
					else{
						dictionary.put(label[0], label[1]);
					}
				}
			}
		}
		// Labels Parser end
		com.vodafone.sobe.logger.db.Logger ll = new com.vodafone.sobe.logger.db.Logger();
		ll.log(map, dictionary);
	}
}
