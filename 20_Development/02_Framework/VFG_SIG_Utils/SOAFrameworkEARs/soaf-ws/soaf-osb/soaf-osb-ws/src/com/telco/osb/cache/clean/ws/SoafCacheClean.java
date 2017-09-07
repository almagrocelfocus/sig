package com.telco.osb.cache.clean.ws;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.telco.osb.cache.clean.CachePublishLocal;

@WebService(targetNamespace = "http://ws.esb.telco.com/SoafCacheClean")
public class SoafCacheClean {
	
	@EJB
	private CachePublishLocal cachePublish;

	@WebMethod
	public @WebResult()
	String soafCacheClean(@WebParam(name = "delayMilliseconds") String delayMilliseconds) {
		if(delayMilliseconds == null ||
				delayMilliseconds.isEmpty()) {
			delayMilliseconds = "0";
		}
		return cachePublish.clearCacheRemote(Long.parseLong(delayMilliseconds));
	}

}