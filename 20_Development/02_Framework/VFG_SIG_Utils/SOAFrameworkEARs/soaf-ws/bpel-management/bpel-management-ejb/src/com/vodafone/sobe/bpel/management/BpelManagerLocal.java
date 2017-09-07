package com.vodafone.sobe.bpel.management;

import javax.ejb.Local;

import oracle.soa.management.facade.Locator;



@Local
public interface BpelManagerLocal {
	
	Locator getLocator();
	
}
