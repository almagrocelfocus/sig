package com.vodafone.sobe.logger.db;

import java.util.Map;
import javax.ejb.Local;


@Local
public interface LoggerLocal{
	
	public void log(Map<String,String> map, Map<String, String> dictionary);
}
