package com.telco.osb.cache.clean;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.telco.osb.configurator.db.ServiceConfiguratorLocal;

/**
 * Session Bean implementation class CachePublish
 */
@Stateless
public class CachePublish implements CachePublishLocal {
	
	@EJB
	private ServiceConfiguratorLocal systemPropertyRepository;

    public CachePublish() {
    	
    }
    
	@Override
	public String clearCacheRemote(long delay) {
		
		String result = "";
		
		// clear local cache, just this instance.
		TopicConnection qcon = null;
		TopicSession qsession = null;
		TopicPublisher qsender = null;

		String providerUrl = systemPropertyRepository
				.getServiceConfigurationByName("ALL", "ALL", "ALL", "CachePublish", "1", "ALL", "cache.provider_url").getParamValue();
		
		String securityPrincipal = systemPropertyRepository
				.getServiceConfigurationByName("ALL", "ALL", "ALL", "CachePublish", "1", "ALL", "cache.security_principal").getParamValue();
		
		String securityCredentials = systemPropertyRepository
				.getServiceConfigurationByName("ALL", "ALL", "ALL", "CachePublish", "1", "ALL", "cache.security_credentials").getParamValue();
		
		String providerJmsConnectionFactory = systemPropertyRepository
				.getServiceConfigurationByName("ALL", "ALL", "ALL", "CachePublish", "1", "ALL", "cache.provider_jms_connection_factory").getParamValue();
		
		String providerJmsTopicName = systemPropertyRepository
				.getServiceConfigurationByName("ALL", "ALL", "ALL", "CachePublish", "1", "ALL", "cache.provider_jms_topic_name").getParamValue();

		try {

			Map<String, String> props = new HashMap<String, String>();
			props.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			props.put(Context.PROVIDER_URL, providerUrl);
			props.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
			props.put(Context.SECURITY_CREDENTIALS, securityCredentials);
			Context ctx = new InitialContext(new Hashtable<String, String>(props));
			TopicConnectionFactory qconFactory = (TopicConnectionFactory) ctx
					.lookup(providerJmsConnectionFactory);

			qcon = qconFactory.createTopicConnection();
			qsession = qcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			Topic topic = (Topic) ctx.lookup(providerJmsTopicName);
			qsender = qsession.createPublisher(topic);

			TextMessage msg = qsession.createTextMessage();
			msg.setText("clean cache");

			qcon.start();
			qsender.publish(msg, qsender.getDeliveryMode(), qsender.getPriority(), 60000);
			
			//Delay after publish
			if(delay > 0){
				Thread.sleep(delay);
			}
			
			result = "SOAF cache cleaned";
			
		} catch (Exception e) {
			result = e.getLocalizedMessage();
			e.printStackTrace();
		}
		return result;

	}

}
