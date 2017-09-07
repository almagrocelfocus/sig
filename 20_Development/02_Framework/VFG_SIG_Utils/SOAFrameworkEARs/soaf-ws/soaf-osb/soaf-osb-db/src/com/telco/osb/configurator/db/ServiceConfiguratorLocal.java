package com.telco.osb.configurator.db;

import java.util.List;

import javax.ejb.Local;

import com.telco.osb.configurator.db.dao.SoafServiceConfigurator;

@Local
public interface ServiceConfiguratorLocal {
	public List<SoafServiceConfigurator> getServiceConfigurationList(String domain, String category, String target, String service,
			String version, String username);

	public SoafServiceConfigurator getServiceConfigurationByName(String domain, String category, String target, String service,
			String version, String username, String paramName);

	public SoafServiceConfigurator getJavaServiceConfigurationByName(String domain, String category, String service, String version,
			String username, String paramName);
}
