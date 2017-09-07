package com.eai.common.db;

import java.util.List;

import javax.ejb.Local;

import com.eai.common.db.entity.ConfigurationPropertyLocal;

/** 
 *	Local interfaces to support the base use of Configurations Properties for requested database
 *	Only one implementation by project should be used since in included helpers it's assumed only one implementation for EJB wiring //FIXME: review if we should use local
 *	be centralized in a single table and the datamodel should be as defined in scripts project folder 
 */
@Local
public interface ConfigurationRepositoryLocal {
	public ConfigurationPropertyLocal getProperty(String name);
	public String getPropertyValue(String name);
	public <T> T getPropertyValue(Class<T> classType, String name);
	public List<ConfigurationPropertyLocal> getProperties();
	public void clearCache();
	public void setDBPropertyLocal(ConfigurationPropertyLocal propertyLocal);
}
