package com.eai.common.db;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.eai.common.EAIConstants;

@Stateless
public class ConfigurationRepository extends ConfigurationRepositoryAbstract implements ConfigurationRepositoryLocal {
	@PersistenceContext(name = EAIConstants.DEFAULT_PERSISTENCE_UNIT)
	private EntityManager em;
	protected EntityManager getEntityManager() {
		return em;
	}

}
