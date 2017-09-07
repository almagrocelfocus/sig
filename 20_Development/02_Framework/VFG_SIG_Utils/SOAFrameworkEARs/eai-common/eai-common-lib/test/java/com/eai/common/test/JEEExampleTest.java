package com.eai.common.test;

import java.util.Properties;

import org.apache.openejb.jee.EjbJar;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Configuration;
import org.apache.openejb.testing.Module;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.eai.common.EAIConstants;
import com.eai.common.db.ConfigurationRepositoryLocal;
import com.eai.common.utils.EAILogger;

import javax.ejb.EJB;

@RunWith(ApplicationComposer.class)
public class JEEExampleTest extends JEETesterAbstract {
	@EJB
	private ConfigurationRepositoryLocal cr;
	
	@Test
	public void dummyTest(){
		Assert.assertNotNull(cr);
		EAILogger.info( cr.getPropertyValue("test") );
	}
	
	private static final String DEFAULT_PERSISTENCE_UNIT = EAIConstants.DEFAULT_PERSISTENCE_UNIT;
	@BeforeClass
	public static void setup() {
		setup(EAIConstants.DEFAULT_PERSISTENCE_UNIT);
	}

	@Configuration
	@Override
	public Properties config() {
		return configProperties;
	}
	
	@Module
	@Override
	public PersistenceUnit persistence() {
		return persistenceUnitMap.get(DEFAULT_PERSISTENCE_UNIT);
	}
	
	@Module
	@Override
	public PersistenceUnit persistenceCommon() {
		return persistenceUnitMap.get(EAIConstants.DEFAULT_PERSISTENCE_UNIT);
	}

	@Module
	@Override
	public EjbJar beans() {
		return ejbJar;
	}
}
