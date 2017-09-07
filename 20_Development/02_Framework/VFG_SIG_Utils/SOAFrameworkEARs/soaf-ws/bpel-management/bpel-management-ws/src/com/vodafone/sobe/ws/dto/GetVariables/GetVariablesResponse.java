package com.vodafone.sobe.ws.dto.GetVariables;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class GetVariablesResponse {
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	@XmlElementWrapper(name="Variables")
    @XmlElement(name="Variable")
	private List<Variable> variables;
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	public GetVariablesResponse(){
		variables = new ArrayList<Variable>();
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
	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}
	
}
