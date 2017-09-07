package com.telco.osb.publisher.ws;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.telco.osb.publisher.db.PublisherLocal;

@WebService(targetNamespace = "http://ws.esb.telco.com/Publisher")
public class Publisher {

	@EJB
	private PublisherLocal publisher;

	@WebMethod
	public void storeOperation(@WebParam(name = "messageId") String messageId, @WebParam(name = "requestId") String requestId,
			@WebParam(name = "correlationId") String correlationId, @WebParam(name = "errorCode") String errorCode,
			@WebParam(name = "status") String status, @WebParam(name = "request") String request,
			@WebParam(name = "repubInfo") String repubInfo, @WebParam(name = "domain") String domain,
			@WebParam(name = "category") String category, @WebParam(name = "target") String target,
			@WebParam(name = "service") String service, @WebParam(name = "version") String version,
			@WebParam(name = "username") String username) {

		publisher.storeOperation(messageId, requestId, correlationId, errorCode, status, request, repubInfo, domain, category, target,
				service, version, username);
	}
}
