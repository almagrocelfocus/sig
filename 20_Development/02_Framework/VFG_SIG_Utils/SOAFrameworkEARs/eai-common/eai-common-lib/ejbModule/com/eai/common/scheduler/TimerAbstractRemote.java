package com.eai.common.scheduler;

/**
 * 	Extended interfaces should be @Remote
 */
public interface TimerAbstractRemote {
	/**
	 * Default scheduler execution, should be override if required
	 * @return 30seconds default
	 */
	public Long getSchedulerPollingIntervalMilli();
	public void createTimer(boolean immediate);
	public void removeTimer();
	
	public void runCycle();
	public String getSchedulerRunName();
	
}
