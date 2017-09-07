package com.vodafone.sobe.ws.dto.GetInstanceDetails;

import java.util.Comparator;

public class EventOrderBy implements Comparator<Event>{
	
	//--------------------------------------
		// Instance Variables
		//--------------------------------------
		
		
		//--------------------------------------
		// Constructors
		//--------------------------------------
		public EventOrderBy(){
			
		}
		
		
		
		//--------------------------------------
		// Public Methods
		//--------------------------------------
		@Override
		public int compare(Event arg0, Event arg1) {

			return arg0.getDate().compareTo(arg1.getDate());
		}
		
		//--------------------------------------
		// Private Methods
		//--------------------------------------
		
		
		//--------------------------------------
		// Getters and Setters
		//--------------------------------------

		

}
