package com.eai.common.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eai.common.db.entity.ConfigurationProperty;
import com.eai.common.db.entity.ConfigurationPropertyLocal;
import com.eai.common.entities.EAIEnums.ConverterPrimitiveTypes;

/**
 * Extend this class and define the entity manager used for direct re-use of the database component
 * by default EAI_PROPERTY is used, for custom properties override class DBProperty and setDBPropertyLocal
 */
public abstract class ConfigurationRepositoryAbstract implements ConfigurationRepositoryLocal{
	protected Logger logger;
	public ConfigurationRepositoryAbstract(){
		logger = LogManager.getLogger(this.getClass().getName());
		propertyLocal = new ConfigurationProperty();
	}

	@Override
	public String getPropertyValue(String name) {
		logger.entry(name);
		ConfigurationPropertyLocal property = getProperty(name);
		return logger.exit(property == null ? null : property.getValue());
	}
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getPropertyValue(Class<T> classType, String name) {
		logger.entry(name);
		ConfigurationPropertyLocal property = getProperty(name);
		return (T) ConverterPrimitiveTypes.valueOfEnum(classType).getToConverter().convert(property.getValue());
	}
	@Override
	public ConfigurationPropertyLocal getProperty(String name) {
		logger.entry(name);
		ConfigurationPropertyLocal property = getEntityManager().find(propertyLocal.getClass(), name);
		return logger.exit(property == null ? null : property);
	}

	@Override
	public List<ConfigurationPropertyLocal> getProperties() {
		logger.entry();
		Query query = getEntityManager().createNativeQuery("SELECT * FROM " + propertyLocal.getTableName() , propertyLocal.getClass());
		@SuppressWarnings("unchecked")
		List<ConfigurationPropertyLocal> results = query.getResultList();
    	return logger.exit(results);
	}

	@Override
	public void clearCache() {
		getEntityManager().getEntityManagerFactory().getCache().evictAll();
	}

	/**
	 * @return @PersistenceContext(name = DS) where DS is application specific datasource, or common properties context
	 */
	protected abstract EntityManager getEntityManager();
	private ConfigurationPropertyLocal propertyLocal = null;
	@Override
	public void setDBPropertyLocal(ConfigurationPropertyLocal propertyLocal) {
		this.propertyLocal = propertyLocal;
	}
}
