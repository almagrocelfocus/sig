package com.telco.osb.publisher.db;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.telco.osb.publisher.PublisherEnums;
import com.telco.osb.publisher.db.dao.SoafRepublisher;

@Stateless
public class Publisher implements PublisherLocal {
	@PersistenceContext(unitName = "soaf-osb-db")
	private EntityManager em;

	@Override
	public void storeOperation(String messageId, String requestId, String correlationId, String errorCode, String status, String request,
			String repubInfo, String domain, String category, String target, String service, String version, String user) {

		SoafRepublisher republishOperation = new SoafRepublisher();

		republishOperation.setMessageId(messageId);
		republishOperation.setRequestId(requestId);
		republishOperation.setCorrelationId(correlationId);
		republishOperation.setErrorCode(errorCode);
		if (status == null || status.isEmpty()) {
			republishOperation.setStatus(PublisherEnums.RepublisherType.NEW.toString());
		} else {
			republishOperation.setStatus(status);
		}
		republishOperation.setRequest(request);
		republishOperation.setRepubInfo(repubInfo);
		republishOperation.setDomain(domain);
		republishOperation.setCategory(category);
		republishOperation.setTarget(target);
		republishOperation.setService(service);
		republishOperation.setVersion(version);

		republishOperation.setCreatedBy(user);
		republishOperation.setLastUpdatedBy(user);
		republishOperation.setNumberOfRetries(0);

		// Check if operation is already inserted
		int numberOfSubmissions = ((Number) em.createNamedQuery("SoafRepublisher.countByCorrelationId")
				.setParameter("correlationId", correlationId).getSingleResult()).intValue();
		republishOperation.setNumberOfRetries(numberOfSubmissions);
		em.persist(republishOperation);

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public SoafRepublisher executeRepublication(SoafRepublisher soafRepublisher) {
		SoafRepublisher result = em.find(SoafRepublisher.class, soafRepublisher.getId());
		result.incrementRetries();
		result.setStatus(PublisherEnums.RepublisherType.REPUB.toString());
		return em.merge(result);
	}

	@Override
	public List<SoafRepublisher> getSoafRepublisherToPublish(int maxNumberOfQueryResults) {
		Query query = em.createNamedQuery("SoafRepublisher.findSoafRepublisherToPublish");
		query.setParameter(1, maxNumberOfQueryResults);
		List<?> repubWithConfigurations = query.getResultList();
		List<SoafRepublisher> soafRepublishers = new ArrayList<SoafRepublisher>();
		for (Object obj : repubWithConfigurations) {
			Object[] currentObjects = (Object[]) obj;

			SoafRepublisher soafRepublisher = new SoafRepublisher();
			soafRepublisher.setId(Long.valueOf("" + currentObjects[0]));
			soafRepublisher.setCurrentConfigurationId(Long.valueOf("" + currentObjects[1]));
			soafRepublisher.setMaxNumberOfRetries(Integer.valueOf("" + currentObjects[2]));

			soafRepublishers.add(soafRepublisher);
		}
		return soafRepublishers;
	}

	public SoafRepublisher getSoafRepublisher(String id) {
		SoafRepublisher soafRepublisher = em.find(SoafRepublisher.class, new Long(id));
		return soafRepublisher;
	}
}
