package com.telco.osb.publisher.timer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eai.adapters.jms.utils.JMSUtils;
import com.telco.osb.configurator.db.ServiceConfiguratorLocal;
import com.telco.osb.configurator.db.dao.SoafServiceConfigurator;
import com.telco.osb.publisher.PublisherConstants;
import com.telco.osb.publisher.db.PublisherLocal;
import com.telco.osb.publisher.db.dao.SoafRepublisher;

@Stateless
public class RepublisherTimerExec implements RepublisherTimerExecLocal, TimedObject {
	private long runIntervalMilliseconds = 900000;
	private static final String TIMER_NAME = "RepublisherTimerExecInfo";
	private static final Logger LOGGER = LogManager.getLogger(RepublisherTimerExec.class);

	@Resource
	SessionContext context;

	@Resource(mappedName = PublisherConstants.REPUBLISHER_ASYNC_CONNECTION_FACTORY)
	private QueueConnectionFactory connectionFactory;

	@Resource(mappedName = PublisherConstants.REPUBLISHER_ASYNC_QUEUE)
	private Queue asyncRepublisherQueue;

	@EJB
	private PublisherLocal publisherRepository;

	@EJB
	private ServiceConfiguratorLocal serviceConfiguratorRepository;

	public RepublisherTimerExec() {
	}

	public long startTimer() {
		return startTimer(null);
	}

	public long startTimer(String frequency) {
		if (frequency != null && !frequency.isEmpty()) {
			runIntervalMilliseconds = Long.parseLong(frequency) * 1000;
		} else {
			SoafServiceConfigurator configuration = serviceConfiguratorRepository.getJavaServiceConfigurationByName(null, null, null, null,
					PublisherConstants.PUBLISHER_SCHEDULER_FREQUENCY_SEC, null);
			if (configuration != null && configuration.getParamValue() != null) {
				runIntervalMilliseconds = Long.parseLong(configuration.getParamValue()) * 1000;
			}
		}
		// First stop all timers of this type:
		stopTimer();

		TimerService timerService = context.getTimerService();
		timerService.createTimer(runIntervalMilliseconds, runIntervalMilliseconds, TIMER_NAME);
		LOGGER.trace("RepublisherTimer created successfully. This service will be executed in every " + runIntervalMilliseconds / 1000
				+ " seconds");
		return runIntervalMilliseconds;

	}

	public void stopTimer() {
		TimerService timerService = context.getTimerService();
		Iterator<?> iTimers = timerService.getTimers().iterator();
		while (iTimers.hasNext()) {
			Timer t = (Timer) iTimers.next();
			if (t.getInfo().equals(TIMER_NAME)) {
				t.cancel();
			}
		}
	}

	public void execute() {
		ejbTimeout(null);
	}

	public void ejbTimeout(Timer timer) {
		LOGGER.entry(timer);
		try {
			SoafServiceConfigurator configuration = serviceConfiguratorRepository.getJavaServiceConfigurationByName(null, null, null, null,
					PublisherConstants.PUBLISHER_SCHEDULER_MAX_RESULTS, null);
			int maxNumberOfQueryResults = configuration == null ? 100 : Integer.valueOf(configuration.getParamValue());
			List<SoafRepublisher> soafRepublishers = publisherRepository.getSoafRepublisherToPublish(maxNumberOfQueryResults);
			if (soafRepublishers != null) {
				for (SoafRepublisher soafRepublisher : soafRepublishers) {
					Map<String, Object> requestMessage = new HashMap<String, Object>();
					requestMessage.put(PublisherConstants.REPUBLISHER_ASYNC_ID, "" + soafRepublisher.getId());
					// add not persisted info
					requestMessage.put(PublisherConstants.REPUBLISHER_ASYNC_CONFIG_ID, "" + soafRepublisher.getCurrentConfigurationId());
					requestMessage.put(PublisherConstants.REPUBLISHER_ASYNC_MAX_RETRIES, "" + soafRepublisher.getMaxNumberOfRetries());
					soafRepublisher = publisherRepository.executeRepublication(soafRepublisher);
					// only send message after update for concurrency issues
					JMSUtils.sendMapMessage(connectionFactory, asyncRepublisherQueue, requestMessage);
				}
			}
		} catch (Exception e) {
			LOGGER.catching(e);
			LOGGER.error(e.getLocalizedMessage(), e);
		}
		LOGGER.exit();
	}
}
