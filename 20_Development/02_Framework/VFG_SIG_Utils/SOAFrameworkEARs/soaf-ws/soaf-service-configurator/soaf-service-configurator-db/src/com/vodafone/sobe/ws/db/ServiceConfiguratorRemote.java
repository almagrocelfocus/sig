package com.vodafone.sobe.ws.db;

import javax.ejb.Remote;

import com.vodafone.sobe.ws.db.entity.EaiServiceConfigurator;

@Remote
public interface ServiceConfiguratorRemote {
	public EaiServiceConfigurator readConfiguration(String domain, String target, String service, String version);
}
