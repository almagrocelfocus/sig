package com.vodafone.sobe.ws.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vodafone.sobe.ws.dto.GetInstanceDetails.Event;

public class SuperfluousMessageAssertion implements EventFilterAssertion {
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private List<Pattern> regexPatternsList;

	//--------------------------------------
	// Constructors
	//--------------------------------------
	public SuperfluousMessageAssertion(){
		regexPatternsList = new ArrayList<Pattern>();
		CompileDefaultAssertions();
	}
	

	//--------------------------------------
	// Public Methods
	//--------------------------------------
	@Override
	public Boolean IsValid(Event newEvent) {

		for(Pattern pattern : regexPatternsList){
			Matcher matcher = pattern.matcher(newEvent.getMessage());
			if (matcher.find()) {
				return false;
			}
		}
		
		return true;
	}

	//--------------------------------------
	// Private Methods
	//--------------------------------------
	private void CompileDefaultAssertions(){
		
		regexPatternsList.add(Pattern.compile("_cr_"));
		regexPatternsList.add(Pattern.compile("_cl_"));
		regexPatternsList.add(Pattern.compile("Updated variable \".*\""));
		regexPatternsList.add(Pattern.compile("Completed assign"));
		regexPatternsList.add(Pattern.compile("None of the if cases is selected."));
		regexPatternsList.add(Pattern.compile("Else is selected."));
		regexPatternsList.add(Pattern.compile("Updated variable.*"));
	}
	

	//--------------------------------------
	// Getters and Setters
	//--------------------------------------

}
