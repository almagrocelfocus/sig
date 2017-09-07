package com.telco.osb.publisher.timer;
import javax.ejb.Local;

@Local
public interface RepublisherTimerExecLocal {
	public long startTimer();
	public long startTimer(String frequency);
	public void stopTimer();
	public void execute();
}
