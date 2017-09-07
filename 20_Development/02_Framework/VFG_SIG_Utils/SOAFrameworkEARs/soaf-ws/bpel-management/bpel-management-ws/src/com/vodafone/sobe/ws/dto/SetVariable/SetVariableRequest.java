package com.vodafone.sobe.ws.dto.SetVariable;

public class SetVariableRequest {

	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private Long flowId;
	private String variable;
	private String newValue;
	
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	
	
	//--------------------------------------
	// Public Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public Long getFlowId() {
		return flowId;
	}
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}
	
	
	
}
