package com.telco.osb.publisher.db;

import java.util.List;

import javax.ejb.Local;

import com.telco.osb.publisher.db.dao.SoafRepublisher;

@Local
public interface PublisherLocal {
	public void storeOperation(String messageId, String requestId, String correlationId, String errorCode, String status, String request,
			String repubInfo, String domain, String category, String target, String service, String version, String user);

	public SoafRepublisher executeRepublication(SoafRepublisher soafRepublisher);

	public List<SoafRepublisher> getSoafRepublisherToPublish(int maxNumberOfQueryResults);

	public SoafRepublisher getSoafRepublisher(String id);
}
