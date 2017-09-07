package com.telco.osb.publisher.scheduler.ws;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.telco.osb.publisher.timer.RepublisherTimerExecLocal;

public class StopRepublisherTimer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private transient RepublisherTimerExecLocal republisherTimerExec;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		republisherTimerExec.stopTimer();
		response.getOutputStream().println("Scheduled task succesfully started: "+this.getClass().getName());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
