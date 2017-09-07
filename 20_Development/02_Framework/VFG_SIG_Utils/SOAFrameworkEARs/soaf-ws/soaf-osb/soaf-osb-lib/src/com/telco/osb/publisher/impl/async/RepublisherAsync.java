package com.telco.osb.publisher.impl.async;

import java.util.UUID;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.telco.osb.configurator.db.ServiceConfiguratorLocal;
import com.telco.osb.connectors.publisher.PublisherConnector;
import com.telco.osb.connectors.publisher.entity.PublisherReturn;
import com.telco.osb.publisher.PublisherConstants;
import com.telco.osb.publisher.PublisherEnums;
import com.telco.osb.publisher.db.PublisherLocal;
import com.telco.osb.publisher.db.dao.SoafRepublisher;
import com.telco.osb.publisher.exceptions.ExceptionMessageFormatter;

@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") }, mappedName = PublisherConstants.REPUBLISHER_ASYNC_QUEUE)
public class RepublisherAsync implements MessageListener {
	private static final org.slf4j.ext.XLogger XLOGGER = org.slf4j.ext.XLoggerFactory.getXLogger(RepublisherAsync.class);
	
	public RepublisherAsync() {
	}

	@EJB
	private PublisherLocal publisherRepository;

	@EJB
	private ServiceConfiguratorLocal serviceConfiguratorRepository;

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		XLOGGER.entry(message);
		
		
		String messageId = null;
		try {
			if (message == null) {
				throw new IllegalArgumentException("Message can't be null");
			}

			messageId = message.getJMSMessageID() != null ? message.getJMSMessageID() : UUID.randomUUID().toString();
			XLOGGER.debug(messageId);
			MapMessage mapMessage = (MapMessage) message;
			String soafRepublisherId = mapMessage.getString(PublisherConstants.REPUBLISHER_ASYNC_ID);
			Long soafRepublisherConfigId = Long.valueOf(mapMessage.getString(PublisherConstants.REPUBLISHER_ASYNC_CONFIG_ID));
			Integer soafRepublisherMaxRetries = Integer.valueOf(mapMessage.getString(PublisherConstants.REPUBLISHER_ASYNC_MAX_RETRIES));
			XLOGGER.debug(soafRepublisherId);

			SoafRepublisher soafRepublisher = publisherRepository.getSoafRepublisher(soafRepublisherId);
			soafRepublisher.setMaxNumberOfRetries(soafRepublisherMaxRetries);
			soafRepublisher.setCurrentConfigurationId(soafRepublisherConfigId);
			XLOGGER.debug(soafRepublisher.toString());
			
			PublisherConnector connector = new PublisherConnector(serviceConfiguratorRepository);
			PublisherReturn returnCode = connector.republishEvent(soafRepublisher);

			if (returnCode.isSuccess()) {
				soafRepublisher.setStatus(PublisherEnums.RepublisherType.COMPLETED.toString());
			} else if (soafRepublisher.getNumberOfRetries() >= soafRepublisher.getMaxNumberOfRetries()) {
				soafRepublisher.setStatus(PublisherEnums.RepublisherType.COMPLETED_WITH_ERROR.toString());
			} else {
				// if the error message change we retry from scratch
				if (soafRepublisher.getErrorCode() != null && !soafRepublisher.getErrorCode().equals(returnCode.getErrorCode())) {
					soafRepublisher.setNumberOfRetries(0);
				}
				soafRepublisher.setStatus(PublisherEnums.RepublisherType.ERROR.toString());
			}
			soafRepublisher.setErrorCode(returnCode.getErrorCode());
			soafRepublisher.setErrorMessage(returnCode.getErrorMessage());
			soafRepublisher.setRepubInfo(returnCode.getRepubInfo());
		} catch (JMSException e) {
			XLOGGER.error(ExceptionMessageFormatter.formatMessage(e));
		} catch (Exception e) {
			XLOGGER.error(ExceptionMessageFormatter.formatMessage(e));
		}
		XLOGGER.exit();
	}
}
