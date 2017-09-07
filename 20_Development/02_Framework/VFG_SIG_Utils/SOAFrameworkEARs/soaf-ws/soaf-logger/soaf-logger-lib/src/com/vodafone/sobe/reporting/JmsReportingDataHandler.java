package com.vodafone.sobe.reporting;

import com.bea.alsb.platform.PlatformFactory;
import com.bea.alsb.platform.TransactionService;
import com.bea.wli.reporting.MessagecontextDocument;
import com.bea.wli.reporting.ReportingDataHandler;
import com.bea.wli.reporting.jmsprovider.JmsReportingProviderLogger;
import com.bea.wli.reporting.jmsprovider.runtime.ReportMessage;
import com.bea.wli.reporting.jmsprovider.utils.ReportingUtil;
import com.bea.wli.sb.security.SecurityModule;
import com.bea.wli.sb.security.platform.SecurityServiceFactory;
import com.vodafone.sobe.logger.db.SOAFReportHandler;
import com.vodafone.sobe.reporting.exceptions.ExceptionMessageFormatter;
import com.vodafone.sobe.reporting.exceptions.InvalidLogFormatException;

import java.io.InputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlTokenSource;
import weblogic.deployment.jms.PooledConnectionFactory;
import weblogic.deployment.jms.WrappedTransactionalSession;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class JmsReportingDataHandler implements ReportingDataHandler {
	@SuppressWarnings("unchecked")
	private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject) AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

	private PooledConnectionFactory qConFactory;
	private Queue reportingQ;
	
	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SOAFReportHandler.class.getName());

	public JmsReportingDataHandler() throws NamingException, JMSException {

	}

	private void init() throws NamingException, JMSException {
		dolookupJMSArtifacts();
	}

	private void dolookupJMSArtifacts() throws NamingException, JMSException {
		Context ic = null;

		try {
			if (this.qConFactory == null) {
				synchronized (this) {
					if (this.qConFactory == null) {

						ic = ReportingUtil.getInitialContext();

						java.util.Map<String, String> props = new java.util.HashMap<String, String>();
						props.put("JNDIName", "wli.reporting.jmsprovider.ConnectionFactory");

						this.qConFactory = new PooledConnectionFactory("_ALSB_REP_SESS_", 0, true, props);

						SecurityModule sm = SecurityServiceFactory.getSecurityService(PlatformFactory.get()).getSecurityService(KERNEL_ID);
						this.reportingQ = ((Queue) sm.jndiLookup(ic, "wli.reporting.jmsprovider.queue"));
					}
				}
			}
		} finally {
			if (ic != null) {
				try {
					ic.close();
				} catch (NamingException e) {
					LOGGER.error(ExceptionMessageFormatter.formatMessage(e));
				}
			}
		}
	}

	public void handle(XmlObject metadata, String s) throws Exception {
		if (metadata instanceof MessagecontextDocument) {
			ReportMessage msg = new ReportMessage(metadata, s);

			processDataBeforeSending(metadata, msg, 1);
		}
	}

	public void handle(XmlObject metadata, InputStream is) throws Exception {
		if (metadata instanceof MessagecontextDocument) {
			byte[] bindata = ReportingUtil.copyInputStreamtoByteArray(is);
			ReportMessage msg = new ReportMessage(metadata, bindata);

			processDataBeforeSending(metadata, msg, 3);
		}
	}

	public void handle(XmlObject metadata, XmlObject data) throws Exception {
		if (metadata instanceof MessagecontextDocument) {
			ReportMessage msg = new ReportMessage(metadata, data);

			processDataBeforeSending(metadata, msg, 2);
		}
	}

	public void handle(XmlObject metadata, XmlTokenSource data) throws InvalidLogFormatException {
		throw new InvalidLogFormatException();
	}

	public void handle(XmlObject metadata, Serializable data) throws InvalidLogFormatException {
		throw new InvalidLogFormatException();
	}

	public void close() {
		if (this.qConFactory != null) {
			try {
				this.qConFactory.close();
			} catch (JMSException je) {
				LOGGER.error(ExceptionMessageFormatter.formatMessage(je));
			}
		}
	}

	public void flush() {
		//empty implementation
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void processDataBeforeSending(final XmlObject metadata, final ReportMessage msg, final int dataType) throws Exception {
		SecurityModule sm = SecurityServiceFactory.getSecurityService(PlatformFactory.get()).getSecurityService(KERNEL_ID);
		sm.runAlsbPrivilegedAction(new PrivilegedExceptionAction() {
			public Void run() throws Exception {
				JmsReportingDataHandler.this.init();
				JmsReportingDataHandler.this.processDataBeforeSendingAux(metadata, msg, dataType);
				return null;
			}
		});
	}

	private void processDataBeforeSendingAux(XmlObject metadata, ReportMessage msg, int dataType) throws Exception {
		QueueSession qsession = null;
		QueueSender qsender = null;
		QueueConnection qcon = null;
		Transaction transaction = null;
		TransactionManager txMgr = null;
		try {
			qcon = this.qConFactory.createQueueConnection();
			qsession = qcon.createQueueSession(false, 1);
			qsender = qsession.createSender(this.reportingQ);

			txMgr = TransactionService.get().getTransactionManager();
			if (txMgr.getTransaction() != null) {
				boolean isXA = ((WrappedTransactionalSession) qsession).getXAResource() != null;
				if (!isXA) {
					transaction = txMgr.suspend();
				}
			}

			ReportingUtil.validateXmlObject(metadata);
			ObjectMessage message = qsession.createObjectMessage();
			message.setIntProperty("MSGTYPE", 1);
			message.setIntProperty("REPORTINGDATATYPE", dataType);
			message.setObject(msg);
			qsender.send(message);

		} catch (Exception e) {
			JmsReportingProviderLogger.JmsDataHandlerSendingFailed(metadata.xmlText(), e);
			LOGGER.error(ExceptionMessageFormatter.formatMessage(e));
			
			throw e;
		} finally {
			if (qsender != null) {
				qsender.close();
			}
			if (qsession != null) {
				qsession.close();
			}
			if (qcon != null) {
				qcon.close();
			}
			if (transaction != null) {
				txMgr.resume(transaction);
			}
		}
	}

}
