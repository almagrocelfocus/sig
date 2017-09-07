package com.vodafone.sobe.ws.db;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.vodafone.sobe.ws.db.entity.EaiServiceConfigurator;

@Stateless(mappedName="ServiceConfigurator")
@Remote(ServiceConfiguratorRemote.class)
@Local(ServiceConfiguratorLocal.class)
public class ServiceConfigurator implements ServiceConfiguratorLocal, ServiceConfiguratorRemote {
	@PersistenceContext(unitName="soaf-service-configurator-db")
	private EntityManager em;
	protected EntityManager getEntityManager(){ return em; }
	
	
	@Override
	public EaiServiceConfigurator readConfiguration(String domain, String target, String service, String version) {
		
		String query = "SELECT e FROM EaiServiceConfigurator e WHERE e.eaiServiceConfiguratorPK.domain = :domain and e.eaiServiceConfiguratorPK.target = :target and e.eaiServiceConfiguratorPK.service = :service and e.eaiServiceConfiguratorPK.version = :version";
		
		String[] queryParams = new String[4];
		queryParams[0] = domain == null || domain.equals("")? "All" : domain;
		queryParams[1] = target == null || target.equals("")? "All" : target;
		queryParams[2] = service == null || service.equals("")? "All" : service;
		queryParams[3] = version == null || version.equals("")? "All" : version;

		EaiServiceConfigurator eaiServiceConfigurator = null;
		for(int i = 3; i >= 0;){
			Query jpaQuery = em.createQuery(query);
			
			jpaQuery.setParameter("domain", queryParams[0]);
			jpaQuery.setParameter("target", queryParams[1]);
			jpaQuery.setParameter("service", queryParams[2]);
			jpaQuery.setParameter("version", queryParams[3]);
			
			@SuppressWarnings("unchecked")
			List<EaiServiceConfigurator> l = jpaQuery.getResultList();
			if(l.size() > 0){
				eaiServiceConfigurator = l.get(0);
			}
			
			if(eaiServiceConfigurator != null || i == 0){ break; }
			while(queryParams[i].equals("All") && i > 0){
				i--;
			}
			queryParams[i] = "All";
		}
		
		return eaiServiceConfigurator;
	}
	
}
