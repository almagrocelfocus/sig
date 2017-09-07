package com.telco.osb.connectors.publisher;

import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import weblogic.wsee.jws.jaxws.owsm.SecurityPoliciesFeature;

import com.telco.esb.ws.publishercallback.PublisherCallback;
import com.telco.esb.ws.publishercallback.PublisherCallbackService;
import com.telco.osb.configurator.db.ServiceConfiguratorLocal;
import com.telco.osb.connectors.publisher.entity.PublisherReturn;
import com.telco.osb.publisher.PublisherConstants;
import com.telco.osb.publisher.db.dao.SoafRepublisher;
import com.telco.osb.publisher.exceptions.ExceptionMessageFormatter;

public class PublisherConnector {
	private static final org.slf4j.ext.XLogger XLOGGER = org.slf4j.ext.XLoggerFactory.getXLogger(PublisherConnector.class);

	private ServiceConfiguratorLocal serviceConfiguratorRepository;

	public PublisherConnector(ServiceConfiguratorLocal serviceConfiguratorRepository) {
		this.serviceConfiguratorRepository = serviceConfiguratorRepository;
	}

	public PublisherReturn republishEvent(SoafRepublisher soafRepublisher) {
		PublisherReturn result = new PublisherReturn();
		try {
			String url = serviceConfiguratorRepository.getJavaServiceConfigurationByName(soafRepublisher.getDomain(),
					soafRepublisher.getCategory(), soafRepublisher.getService(), soafRepublisher.getVersion(),
					PublisherConstants.PUBLISHER_WS_REPUBLISH_EVENT_URL_PROPERTY_NAME, soafRepublisher.getCreatedBy()).getParamValue();
			String username = serviceConfiguratorRepository.getJavaServiceConfigurationByName(soafRepublisher.getDomain(),
					soafRepublisher.getCategory(), soafRepublisher.getService(), soafRepublisher.getVersion(),
					PublisherConstants.PUBLISHER_WS_USERNAME_PROPERTY_NAME, soafRepublisher.getCreatedBy()).getParamValue();
			String password = serviceConfiguratorRepository.getJavaServiceConfigurationByName(soafRepublisher.getDomain(),
					soafRepublisher.getCategory(), soafRepublisher.getService(), soafRepublisher.getVersion(),
					PublisherConstants.PUBLISHER_WS_PASSWORD_PROPERTY_NAME, soafRepublisher.getCreatedBy()).getParamValue();
			String timeout = serviceConfiguratorRepository.getJavaServiceConfigurationByName(soafRepublisher.getDomain(),
					soafRepublisher.getCategory(), soafRepublisher.getService(), soafRepublisher.getVersion(),
					PublisherConstants.PUBLISHER_WS_TIMEOUT_PROPERTY_NAME, soafRepublisher.getCreatedBy()).getParamValue();

			PublisherCallbackService pcs = new PublisherCallbackService();
			SecurityPoliciesFeature securityFeatures = new SecurityPoliciesFeature(
					new String[] { PublisherConstants.SECURITY_POLICY_ORACLE_USER_PASS });
			PublisherCallback publisherCallbackPort = pcs.getPublisherCallbackPort(securityFeatures);
			updateBindingProvider((BindingProvider) publisherCallbackPort, url, username, password, timeout);

			Holder<String> errorCodeHolder = new Holder<String>();
			Holder<String> errorMessageHolder = new Holder<String>();
			Holder<String> repubInfoHolder = new Holder<String>();
			repubInfoHolder.value = soafRepublisher.getRepubInfo();
			publisherCallbackPort.republishEvent(soafRepublisher.getRequest(), repubInfoHolder, soafRepublisher.getRequestId(),
					soafRepublisher.getCorrelationId(), soafRepublisher.getDomain(), soafRepublisher.getCategory(),
					soafRepublisher.getTarget(), soafRepublisher.getService(), Integer.valueOf(soafRepublisher.getVersion()),
					soafRepublisher.getNumberOfRetries(), Integer.valueOf(soafRepublisher.getMaxNumberOfRetries()), errorCodeHolder,
					errorMessageHolder);
			result = new PublisherReturn(errorCodeHolder.value, errorMessageHolder.value, repubInfoHolder.value);
		} catch (Exception e) {
			XLOGGER.error(ExceptionMessageFormatter.formatMessage(e));
			
			result = new PublisherReturn(e);
			result.setRepubInfo(soafRepublisher.getRepubInfo());
		}
		return result;
	}

	protected void updateBindingProvider(BindingProvider port, String endpointUrl, String username, String password, String timeoutInSec) {
		Map<String, Object> requestContext = port.getRequestContext();
		requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
		requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
		requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
		// Add timeout if present:
		if (timeoutInSec != null && timeoutInSec.matches("^\\d+$")) {
			requestContext.put("com.sun.xml.internal.ws.connect.timeout", Integer.valueOf(timeoutInSec) * 1000);
			requestContext.put("com.sun.xml.internal.ws.request.timeout", Integer.valueOf(timeoutInSec) * 1000);
			requestContext.put("com.sun.xml.ws.connect.timeout", Integer.valueOf(timeoutInSec) * 1000);
			requestContext.put("com.sun.xml.ws.request.timeout", Integer.valueOf(timeoutInSec) * 1000);
		}
	}
}
