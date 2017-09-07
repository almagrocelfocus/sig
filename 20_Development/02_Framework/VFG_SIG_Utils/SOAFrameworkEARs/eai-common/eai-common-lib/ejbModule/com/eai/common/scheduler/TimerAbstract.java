package com.eai.common.scheduler;

import javax.annotation.Resource;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eai.common.utils.TimerUtils;

/**
 *	Abstract class for scheduled tasks, managing the default support for scheduled actions
 * 	Extended classes can be for example beans of type:
 * 		@Stateless(mappedName = "TimerAbstract")
 * 		@Remote(TimerAbstractRemote.class)
 * 
 * TimerAbstract - class name, or other unique
 * TimerAbstractRemote - interface implemented
 * 
 */
public abstract class TimerAbstract implements TimerAbstractRemote {
	protected Logger logger;

	//region timer specific actions
	@Resource
	TimerService timerService;
	
	/**
	 * Default scheduler execution, should be override if required
	 * 
	 * @return 30seconds default
	 */
	public Long getSchedulerPollingIntervalMilli(){
		return 30000L;
	}
	
	public TimerAbstract() {
		logger = LogManager.getLogger(this.getClass().getName());
	}
	
	/**
	 * Class to create a pooling to the OEMP_SCHEDULER table. 
	 * Each pooling will validate which tasks should be executed.
	 * Note, all task executions are processed asynchronously. 
	 */
	public void createTimer(boolean immediate) {
		logger.entry();

		//clean all timers and create a new one:
		removeTimer();
		timerService.createTimer(immediate ? 0 : getSchedulerPollingIntervalMilli(), getSchedulerPollingIntervalMilli(), getSchedulerRunName());
		logger.trace("****** SCHEDULER STARTED - {} *******", getSchedulerRunName() );
		
		logger.exit();
	}

	public void removeTimer() {
		logger.entry();
		TimerUtils.removeTimer(timerService, getSchedulerRunName());
		logger.exit();
	}


	@Timeout
	public void timeout(Timer timer) {
		logger.entry(timer);
		logger.trace("Scheduler polling for pending tasks - {}",timer.getInfo());
		runCycleInternal();
		logger.exit();
	}

	private void runCycleInternal(){
		try{
			runCycle();
		}catch (Exception e) {
			logger.error(e);
		}
	}
	public abstract void runCycle();

	public abstract String getSchedulerRunName();
	//endregion
}
