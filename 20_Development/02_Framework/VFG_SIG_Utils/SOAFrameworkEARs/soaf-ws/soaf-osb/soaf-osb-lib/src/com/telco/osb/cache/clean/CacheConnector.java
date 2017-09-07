package com.telco.osb.cache.clean;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.telco.osb.cache.clean.db.OCacheLocal;

import weblogic.jws.Transactional;

@MessageDriven(activationConfig = {

		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") },

mappedName = "jms/soaf-osb-db-cache-clean-topic")
@Transactional(true)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CacheConnector implements CacheConnectorLocal, MessageListener {

	@EJB
	OCacheLocal cache;

	@Resource
	private MessageDrivenContext mdc;

	public void onMessage(Message message) {

		TextMessage msg = null;
		try {
			if (message instanceof TextMessage) {
				msg = (TextMessage) message;

				if ("clean cache".equalsIgnoreCase(msg.getText())) {
					cache.clearCache();
				}

			}
		} catch (JMSException e) {
			mdc.setRollbackOnly();
		}
	}
}
