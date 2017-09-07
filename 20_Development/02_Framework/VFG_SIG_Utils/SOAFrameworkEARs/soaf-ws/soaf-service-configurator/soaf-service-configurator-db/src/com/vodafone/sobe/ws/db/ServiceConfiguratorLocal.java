package com.vodafone.sobe.ws.db;

import javax.ejb.Local;

import com.vodafone.sobe.ws.db.entity.EaiServiceConfigurator;

@Local
public interface ServiceConfiguratorLocal {
	public EaiServiceConfigurator readConfiguration(String domain, String target, String service, String version);
}
