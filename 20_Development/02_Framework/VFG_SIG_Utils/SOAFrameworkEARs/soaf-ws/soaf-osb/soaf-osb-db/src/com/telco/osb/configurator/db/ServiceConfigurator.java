package com.telco.osb.configurator.db;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.telco.osb.configurator.db.dao.SoafServiceConfigurator;

@Stateless
public class ServiceConfigurator implements ServiceConfiguratorLocal {
	@PersistenceContext(unitName = "soaf-osb-db")
	private EntityManager em;

	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<SoafServiceConfigurator> getServiceConfigurationList(String domain, String category, String target, String service,
			String version, String username) {
		Query soafServiceConfiguratorsQuery = em.createNamedQuery("SoafServiceConfigurator.findAllSoafServiceConfiguratorByUk");
		soafServiceConfiguratorsQuery.setParameter(1, domain);
		soafServiceConfiguratorsQuery.setParameter(2, category);
		soafServiceConfiguratorsQuery.setParameter(3, target);
		soafServiceConfiguratorsQuery.setParameter(4, service);
		soafServiceConfiguratorsQuery.setParameter(5, version);
		soafServiceConfiguratorsQuery.setParameter(6, username);
		@SuppressWarnings("unchecked")
		List<SoafServiceConfigurator> soafServiceConfigurators = soafServiceConfiguratorsQuery.getResultList();

		return soafServiceConfigurators;
	}

	@Override
	public SoafServiceConfigurator getServiceConfigurationByName(String domain, String category, String target, String service,
			String version, String username, String paramName) {
		Query soafServiceConfiguratorsQuery = em.createNamedQuery("SoafServiceConfigurator.findAllSoafServiceConfiguratorByUkAndParam");
		soafServiceConfiguratorsQuery.setParameter(1, domain);
		soafServiceConfiguratorsQuery.setParameter(2, category);
		soafServiceConfiguratorsQuery.setParameter(3, target);
		soafServiceConfiguratorsQuery.setParameter(4, service);
		soafServiceConfiguratorsQuery.setParameter(5, version);
		soafServiceConfiguratorsQuery.setParameter(6, username);
		soafServiceConfiguratorsQuery.setParameter(7, paramName);
		@SuppressWarnings("unchecked")
		List<SoafServiceConfigurator> soafServiceConfigurators = soafServiceConfiguratorsQuery.getResultList();
		
		SoafServiceConfigurator result = null;
		if (soafServiceConfigurators != null && !soafServiceConfigurators.isEmpty()) {
			result = soafServiceConfigurators.get(0);
		}
		return result;
	}

	@Override
	public SoafServiceConfigurator getJavaServiceConfigurationByName(String domain, String category, String service, String version,
			String username, String paramName) {
		SoafServiceConfigurator result = getServiceConfigurationByName(domain, category, "java", service, version, paramName, username);
		return result;
	}
}
