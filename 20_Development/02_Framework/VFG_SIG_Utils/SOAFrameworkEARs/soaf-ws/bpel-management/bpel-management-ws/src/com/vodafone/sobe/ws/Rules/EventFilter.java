package com.vodafone.sobe.ws.Rules;

import java.util.ArrayList;
import java.util.List;

public class EventFilter {

	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private List<EventFilterAssertion> assertionsList;

	//--------------------------------------
	// Constructors
	//--------------------------------------
	public EventFilter(){
		assertionsList = new ArrayList<EventFilterAssertion>();
		assertionsList.add(new SuperfluousMessageAssertion());
	}

	//--------------------------------------
	// Public Methods
	//--------------------------------------
	public Boolean IsValid(com.vodafone.sobe.ws.dto.GetInstanceDetails.Event newEvent){
		
		for(EventFilterAssertion assertion : assertionsList){
			if(!assertion.IsValid(newEvent)){
				return false;
			}
		}
		
		return true;
	}

	//--------------------------------------
	// Private Methods
	//--------------------------------------
	

	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
	
	
}
