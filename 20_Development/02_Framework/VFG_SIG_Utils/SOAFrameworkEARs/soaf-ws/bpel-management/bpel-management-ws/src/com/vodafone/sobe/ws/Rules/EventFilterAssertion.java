package com.vodafone.sobe.ws.Rules;

public interface EventFilterAssertion {
	
	public Boolean IsValid(com.vodafone.sobe.ws.dto.GetInstanceDetails.Event newEvent);
	
}
