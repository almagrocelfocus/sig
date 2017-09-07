package com.telco.osb.publisher.scheduler.ws;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.telco.osb.publisher.scheduler.ws.entity.RepublisherTimerRequest;
import com.telco.osb.publisher.scheduler.ws.entity.RepublisherTimerResponse;
import com.telco.osb.publisher.scheduler.ws.entity.StopRepublisherTimerResponse;
import com.telco.osb.publisher.timer.RepublisherTimerExecLocal;

@WebService(targetNamespace = "http://ws.esb.telco.com/Publisher/scheduler/Republisher")
public class RepublisherTimer {

	@EJB
	private RepublisherTimerExecLocal republisherTimerExec;

	@WebMethod
	@WebResult(name = "RepublisherTimerResponse")
	public RepublisherTimerResponse start(@WebParam(name = "RepublisherTimerRequest") RepublisherTimerRequest request) {
		RepublisherTimerResponse resp = new RepublisherTimerResponse();
		long runIntervalSetted = republisherTimerExec.startTimer(request == null ? null : request.getRunIntervalSeconds());
		resp.setStatusMessage("Timer created successfully. Will run with an interval of " + runIntervalSetted / 1000 + " seconds");
		return resp;
	}

	@WebMethod
	@WebResult(name = "StopRepublisherTimerResponse")
	public StopRepublisherTimerResponse stop() {
		StopRepublisherTimerResponse resp = new StopRepublisherTimerResponse();
		republisherTimerExec.stopTimer();
		resp.setStatusMessage("Timer stopped successfully");
		return resp;
	}
	
	@WebMethod
	public void execute() {
		republisherTimerExec.execute();
	}
}
