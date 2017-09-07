package com.eai.common.utils;

import java.util.Collection;

import javax.ejb.Timer;
import javax.ejb.TimerService;

public class TimerUtils {
	
	private TimerUtils(){
	}
	
	@SuppressWarnings("unchecked")
	public static void removeTimer(TimerService timerService, String schedulerRunName) {
		for( Timer timer : (Collection<Timer>) timerService.getTimers() ){
			if( schedulerRunName.equals( timer.getInfo().toString() ) ){
				EAILogger.trace("****** SCHEDULER STOPPED - {} *******", timer.getInfo());
				timer.cancel();
			}
		}
	}
}
