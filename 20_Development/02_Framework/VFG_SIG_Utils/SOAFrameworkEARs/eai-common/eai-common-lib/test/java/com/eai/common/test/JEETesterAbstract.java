package com.eai.common.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.PersistenceContext;

import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.jee.StatefulBean;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import com.eai.common.EAIConstants;
import com.eai.common.db.entity.ConfigurationProperty;
import com.eai.common.utils.EAILogger;
import com.eai.common.utils.StringUtils;

public abstract class JEETesterAbstract extends CommonTesterAbstract{
	protected static Properties configProperties = null;
	protected static Map<String, PersistenceUnit> persistenceUnitMap = new HashMap<String, PersistenceUnit>();
	protected static EjbJar ejbJar = null;
	protected static String defaultPersistenceUnitStatic;
	
	//initialize openEJB context
	public static void setup(String defaultPersistenceUnit, String... persistenceUnits){
		defaultPersistenceUnitStatic = defaultPersistenceUnit;
		propertiesRoot(defaultPersistenceUnit, persistenceUnits);
		beansRoot();
		persistenceRoot();
	}
	
	public abstract Properties config();
	public abstract PersistenceUnit persistence();
	public abstract PersistenceUnit persistenceCommon();
	public abstract EjbJar beans();
	public static String getAlternativePU(Class<?> clazz){
		return null;
	}
	
	private static void propertiesRoot(String defaultPersistenceUnit, String... persistenceUnits) {
		configProperties = new Properties();
		configProperties.put(defaultPersistenceUnit, "new://Resource?type=DataSource");
		configProperties.put(defaultPersistenceUnit + ".JdbcDriver", getTestPropertiesByPrefix(defaultPersistenceUnit, "jdbc.driver"));
		configProperties.put(defaultPersistenceUnit + ".JdbcUrl", getTestPropertiesByPrefix(defaultPersistenceUnit,"jdbc.url"));
		configProperties.put(defaultPersistenceUnit + ".Username", getTestPropertiesByPrefix(defaultPersistenceUnit,"jdbc.user"));
		configProperties.put(defaultPersistenceUnit + ".Password", getTestPropertiesByPrefix(defaultPersistenceUnit,"jdbc.pass"));
		
		if(persistenceUnits != null){
			for(String pu : persistenceUnits){
				configProperties.put(pu, "new://Resource?type=DataSource");
				configProperties.put(pu + ".JdbcDriver", getTestPropertiesByPrefix(pu, "jdbc.driver"));
				configProperties.put(pu + ".JdbcUrl", getTestPropertiesByPrefix(pu, "jdbc.url"));
				configProperties.put(pu + ".Username", getTestPropertiesByPrefix(pu,"jdbc.user"));
				configProperties.put(pu + ".Password", getTestPropertiesByPrefix(pu,"jdbc.pass"));
			}
		}
	}
	private static String getTestPropertiesByPrefix(String prefix, String key){
		Object value = getTestProperties().get(prefix + "." + key);
		return (String) (value != null ? value : getTestProperties().get("test." + key));
	}
	
	@SuppressWarnings("unchecked")
	public static void persistenceRoot() {
		Reflections reflections = new Reflections(ClasspathHelper.forJavaClassPath());
		for( Class<? extends Annotation> annotation : new Class[]{ Entity.class } ){
			Set<Class<?>> modules = reflections.getTypesAnnotatedWith(annotation);
			for( Class<?> classModule : modules ){
				for( String prefix : "com.vodafone;com.eai".toString().split(";") ){
					if( classModule.getName().startsWith(prefix) ){
						if( classModule.getAnnotation(Stateless.class) == null ){//ignore stateless
							PersistenceUnit persistenceUnit = null;
							String puName = getAlternativePU(classModule);
							if(puName != null){
								persistenceUnit = persistenceUnitMap.get(puName);
							}else{
								persistenceUnit = persistenceUnitMap.get(defaultPersistenceUnitStatic);
							}
							EAILogger.info("Added Entity to PU:"+classModule+" to: " + persistenceUnit.getName());
							persistenceUnit.getClazz().add( classModule.getName() );
						}
					}
				}
			}
		}
		persistenceUnitMap.get(defaultPersistenceUnitStatic).getClazz().add(ConfigurationProperty.class.getName());
	}

	
	@SuppressWarnings("unchecked")
	public static void beansRoot() {
		ejbJar = new EjbJar("dummy");
		//load all entities into jar!
		Reflections reflections = new Reflections(ClasspathHelper.forJavaClassPath());
		for( Class<? extends Annotation> annotation : new Class[]{ Stateful.class, Stateless.class } ){
			Set<Class<?>> modules = reflections.getTypesAnnotatedWith(annotation);
			for( Class<?> classModule : modules ){
				for( String prefix : "com.vodafone;com.eai".toString().split(";") ){
					if( classModule.getName().startsWith(prefix) ){
						if( classModule.getAnnotation(Stateful.class) != null ){
							ejbJar.addEnterpriseBean( new StatefulBean(classModule) );
							EAILogger.info("Added Stateful EJB class to jar:"+classModule);
						}else if( classModule.getAnnotation(Stateless.class) != null ){
							ejbJar.addEnterpriseBean( new StatelessBean(classModule) );
							EAILogger.info("Added Stateless EJB class to jar:"+classModule);
						}
						
						for(Field field : classModule.getDeclaredFields()){
							PersistenceContext pc = field.getAnnotation(PersistenceContext.class);
							if(pc != null && !StringUtils.isNullOrEmpty(pc.unitName())){
								PersistenceUnit persistenceUnit = persistenceUnitMap.get(pc.unitName());
								if(persistenceUnit == null){
									persistenceUnit = new PersistenceUnit(pc.unitName());
									persistenceUnit.setProvider(PersistenceProvider.class);
									persistenceUnit.setJtaDataSource(pc.unitName());
									persistenceUnitMap.put(pc.unitName(), persistenceUnit);
									
									EAILogger.info("Created persistence unit: " + pc.unitName());
								}
								break;
							}
						}
					}
				}
			}
		}
		if(persistenceUnitMap.get(defaultPersistenceUnitStatic) == null){
			PersistenceUnit persistenceUnit = new PersistenceUnit(defaultPersistenceUnitStatic);
			persistenceUnit.setProvider(PersistenceProvider.class);
			persistenceUnit.setJtaDataSource(defaultPersistenceUnitStatic);
			persistenceUnitMap.put(defaultPersistenceUnitStatic, persistenceUnit);
			EAILogger.info("Created DEFAULT persistence unit: " + defaultPersistenceUnitStatic);
		}
		
		PersistenceUnit persistenceUnit = new PersistenceUnit(EAIConstants.DEFAULT_PERSISTENCE_UNIT);
		persistenceUnit.setProvider(PersistenceProvider.class);
		persistenceUnit.setJtaDataSource(EAIConstants.DEFAULT_PERSISTENCE_UNIT);
		persistenceUnitMap.put(EAIConstants.DEFAULT_PERSISTENCE_UNIT, persistenceUnit);
		EAILogger.info("Created EAI COMMON persistence unit: " + EAIConstants.DEFAULT_PERSISTENCE_UNIT);
	}
}
