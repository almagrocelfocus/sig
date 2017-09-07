package com.vodafone.sobe.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.vodafone.sobe.ws.db.ServiceConfiguratorRemote;
import com.vodafone.sobe.ws.db.entity.EaiServiceConfigurator;
import com.vodafone.sobe.ws.db.entity.EaiServiceConfiguratorParam;

@WebService(targetNamespace = "http://ws.sobe.vodafone.com/SoafServiceConfigurator")
public class ServiceConfiguratorWS{
	
	@WebMethod
	public @WebResult(name="confList") List<Conf> getServiceConfiguration(@WebParam(name="domain") String domain,
					@WebParam(name="target") String target,
					@WebParam(name="service") String service,
					@WebParam(name="version") String version) throws NamingException{
		
		InitialContext ic = new InitialContext();
		
		ServiceConfiguratorRemote  logger = (ServiceConfiguratorRemote)ic.lookup("ServiceConfigurator#com.vodafone.sobe.ws.db.ServiceConfiguratorRemote");
		
		EaiServiceConfigurator eaiServiceConfigurator = logger.readConfiguration(domain, target, service, version);
		List<Conf> confList = new ArrayList<Conf>();
		if(eaiServiceConfigurator != null){
			List<EaiServiceConfiguratorParam> params = eaiServiceConfigurator.getEaiServiceConfiguratorParamList();
			if(params!=null){
				for(EaiServiceConfiguratorParam param : params){
					Conf conf = new Conf(param.getParamName(), param.getParamValue());
					confList.add(conf);
				}
			}
		}
		
		return confList;
		
	}
	
	
}