package com.vodafone.sobe.ws.dto.GetVariables;

import java.util.ArrayList;
import java.util.List;

public class Variable {

	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private String variableName;
	private String variableValue;
	
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	public Variable(){
		
	}
	
	public Variable(String variableName, String variableValue){
		this.variableName = variableName;
		this.variableValue = variableValue;
	}
	
	//--------------------------------------
	// Public Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
	public String getVariableValue() {
		return variableValue;
	}
	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}
	
}
