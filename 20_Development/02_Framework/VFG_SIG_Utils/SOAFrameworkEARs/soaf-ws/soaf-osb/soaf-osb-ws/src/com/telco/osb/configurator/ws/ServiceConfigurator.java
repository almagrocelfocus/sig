package com.telco.osb.configurator.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.telco.osb.configurator.db.ServiceConfiguratorLocal;
import com.telco.osb.configurator.db.dao.SoafServiceConfigurator;
import com.telco.osb.configurator.ws.entity.Configuration;

@WebService(targetNamespace = "http://ws.esb.telco.com/ServiceConfigurator")
public class ServiceConfigurator {

	@EJB
	private ServiceConfiguratorLocal serviceConfigurator;

	@WebMethod
	public @WebResult(name = "configurationList")
	List<Configuration> getServiceConfigurationList(@WebParam(name = "domain") String domain, @WebParam(name = "category") String category,
			@WebParam(name = "target") String target, @WebParam(name = "service") String service, @WebParam(name = "version") String version,
			@WebParam(name = "username") String username) {

		List<SoafServiceConfigurator> soafServiceConfigurators = serviceConfigurator.getServiceConfigurationList(domain, category, target,
				service, version, username);
		List<Configuration> configurations = new ArrayList<Configuration>();
		if (soafServiceConfigurators != null) {
			for (SoafServiceConfigurator soafServiceConfigurator : soafServiceConfigurators) {
				Configuration configuration = new Configuration(soafServiceConfigurator.getParamName(),
						soafServiceConfigurator.getParamValue());
				configurations.add(configuration);
			}
		}
		return configurations;
	}

	@WebMethod
	public @WebResult(name = "configuration")
	Configuration getServiceConfigurationByName(@WebParam(name = "domain") String domain, @WebParam(name = "category") String category,
			@WebParam(name = "target") String target, @WebParam(name = "service") String service,
			@WebParam(name = "version") String version, @WebParam(name = "paramName") String paramName, @WebParam(name = "username") String username) {

		SoafServiceConfigurator soafServiceConfigurator = serviceConfigurator.getServiceConfigurationByName(domain, category, target,
				service, version, paramName, username);
		Configuration configuration = null;
		if (soafServiceConfigurator != null) {
			configuration = new Configuration(soafServiceConfigurator.getParamName(), soafServiceConfigurator.getParamValue());
			return configuration;
		}
		return configuration;
	}
	
}