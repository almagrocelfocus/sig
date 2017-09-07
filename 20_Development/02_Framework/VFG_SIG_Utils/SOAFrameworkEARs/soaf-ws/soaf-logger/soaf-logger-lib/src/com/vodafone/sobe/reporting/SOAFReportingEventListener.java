package com.vodafone.sobe.reporting;


import weblogic.application.ApplicationLifecycleEvent;
import weblogic.application.ApplicationLifecycleListener;
import weblogic.application.ApplicationException;
import weblogic.security.Security;
import weblogic.security.SubjectUtils;
import java.security.PrivilegedExceptionAction;
import java.security.PrivilegedActionException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import com.bea.wli.reporting.ReportingDataManager;
import com.vodafone.sobe.logger.db.Logger;


public class SOAFReportingEventListener extends ApplicationLifecycleListener{

	//----------------------------------------
	// INSTANCE VARIABLES
	//----------------------------------------
	private static ReportingDataManager _manager = null;

	private JmsReportingDataHandler soafReportingDataHandler;
	
	private static final org.slf4j.Logger LOGG = org.slf4j.LoggerFactory.getLogger(Logger.class.getName());
		
	//----------------------------------------
	// PUBLIC METHODS
	//----------------------------------------
	@SuppressWarnings("rawtypes")
	public void postStart(ApplicationLifecycleEvent evt) throws ApplicationException{
		
		try{
			Security.runAs(SubjectUtils.getAnonymousUser(), 
					new PrivilegedExceptionAction(){
						public Object run() throws PrivilegedActionException, NamingException, JMSException{
							
							soafReportingDataHandler = new JmsReportingDataHandler();
							
							_manager = ReportingDataManager.getManager();
							_manager.addHandler(soafReportingDataHandler);
							
							return null;
						}
					});
		}catch(PrivilegedActionException pae){
			LOGG.error("Error: " + pae.getMessage(), pae);
		}catch(Exception e){
			LOGG.error("Generic Error: " + e.getMessage(), e);
		}
	}
	
	public void preStop(ApplicationLifecycleEvent evt){
		soafReportingDataHandler.close();
		_manager.removeHandler(soafReportingDataHandler);
	}


}
