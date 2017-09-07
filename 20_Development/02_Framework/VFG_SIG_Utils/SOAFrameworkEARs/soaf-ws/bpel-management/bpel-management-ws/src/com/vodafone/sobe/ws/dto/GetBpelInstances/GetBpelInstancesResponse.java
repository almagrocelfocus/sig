package com.vodafone.sobe.ws.dto.GetBpelInstances;

import java.util.ArrayList;


public class GetBpelInstancesResponse {
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private ArrayList<BpelInst> bpelInstanceList;
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	public GetBpelInstancesResponse() {
		bpelInstanceList = new ArrayList<BpelInst>();
	}
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	
	
	//--------------------------------------
	// Public Methods
	//--------------------------------------
	public void addBpelInstance(BpelInst bpelInstance)
	{
		bpelInstanceList.add(bpelInstance);
	}
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
	public void setBpelInstanceList(ArrayList<BpelInst> bpelInstanceList){
		this.bpelInstanceList = bpelInstanceList;
	}
	
	public ArrayList<BpelInst> getBpelInstanceList (){
		return bpelInstanceList;
	}
	
}
