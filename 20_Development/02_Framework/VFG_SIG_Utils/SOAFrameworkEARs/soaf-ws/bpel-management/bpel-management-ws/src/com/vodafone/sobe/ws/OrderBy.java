package com.vodafone.sobe.ws;

import java.util.Comparator;

import com.vodafone.sobe.ws.dto.GetBpelInstances.BpelInst;

public class OrderBy implements Comparator<BpelInst>{
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	public OrderBy(){
		
	}
	
	
	
	//--------------------------------------
	// Public Methods
	//--------------------------------------
	@Override
	public int compare(BpelInst arg0, BpelInst arg1) {

		return arg1.getCreatedTime().compareTo(arg0.getCreatedTime());
		//return arg0.getCreatedTime().compareTo(arg1.getCreatedTime());
	}
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------

	

	
}
