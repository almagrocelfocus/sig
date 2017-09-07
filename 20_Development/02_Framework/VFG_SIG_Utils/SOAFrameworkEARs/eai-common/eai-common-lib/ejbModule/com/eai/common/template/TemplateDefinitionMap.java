package com.eai.common.template;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.eai.common.EAIConstants;


public class TemplateDefinitionMap {
	private Map<String,TemplateDefinition> definitionMap = null;
	
	public TemplateDefinitionMap(){
		setDefinitionMap(new HashMap<String, TemplateDefinition>());
	}
	
	public void put(String key, TemplateDefinition value){
		getDefinitionMap().put(key,value);
	}
	
	public TemplateDefinition get(String key){
		return getDefinitionMap().get(key);
	}
	
	public void setDefinitionMap(Map<String,TemplateDefinition> definition) {
		this.definitionMap = definition;
	}

	public Map<String,TemplateDefinition> getDefinitionMap() {
		return definitionMap;
	}
	
	@Override
	public String toString(){
		String result = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" 
				+ EAIConstants.LINE_SEPERATOR + "<fields>"+ EAIConstants.LINE_SEPERATOR;
		for( String key : getDefinitionMap().keySet() ){
			result += get(key);
		}
		result += "</fields>";
		return result;
	}
	
	public InputStream getInputStream(){
		return new ByteArrayInputStream( this.toString().getBytes() );
	}
	
}
