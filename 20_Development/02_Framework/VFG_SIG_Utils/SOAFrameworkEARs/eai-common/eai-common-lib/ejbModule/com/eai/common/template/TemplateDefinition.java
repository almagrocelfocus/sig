package com.eai.common.template;

import com.eai.common.EAIConstants;

public class TemplateDefinition {
	private static final String PRINT_FORMAT = 
		"\t<mergefield>"+EAIConstants.LINE_SEPERATOR
		+ "\t\t<list>%1$s</list>"+EAIConstants.LINE_SEPERATOR
		+ "\t\t<listInfo>%2$s</listInfo>"+EAIConstants.LINE_SEPERATOR
		+ "\t\t<name>%3$s</name>"+EAIConstants.LINE_SEPERATOR
		+ "\t\t<description>%4$s</description>"+EAIConstants.LINE_SEPERATOR
		+ "\t</mergefield>"+EAIConstants.LINE_SEPERATOR;
	private boolean parentList;
	private boolean currentList;
	private String listInfo;
	private String name;
	private String description;
	
	public TemplateDefinition(){
	}
	
	public TemplateDefinition(boolean currentList, boolean parentList, String listInfo, String listName, String description){
		setCurrentList(currentList);
		setParentList(parentList);
		setListInfo(listInfo);
		setName(listName);
		setDescription(description);
	}
	
	public boolean isList() {
		return isCurrentList() || isParentList();
	}
	
	public void setCurrentList(boolean currentList) {
		this.currentList = currentList;
	}
	public boolean isCurrentList() {
		return currentList;
	}
	
	public void setParentList(boolean parentList) {
		this.parentList = parentList;
	}
	public boolean isParentList() {
		return parentList;
	}

	public void setListInfo(String listInfo) {
		this.listInfo = listInfo;
	}
	public String getListInfo() {
		return listInfo;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return String.format( PRINT_FORMAT, isList(), getListInfo(), getName(), getDescription());
	}
}
