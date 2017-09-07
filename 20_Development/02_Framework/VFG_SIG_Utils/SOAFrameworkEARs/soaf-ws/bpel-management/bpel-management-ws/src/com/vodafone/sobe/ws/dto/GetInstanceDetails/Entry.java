package com.vodafone.sobe.ws.dto.GetInstanceDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


@XmlType(name = "Entry", propOrder = {
	    "instanceId",
	    "externalComponent",
	    "parentInstanceId",
	    "retryCount",
	    "actionType",
	    "actionName",
	    "subType",
	    "type",
	    "entityName",
	    "compositeInstanceId",
	    "compositeName",
	    "compositeDN",
	    "applicationName",
	    "status",
	    "date",
	    "lastUpdatedDate",
	    "faultId",
	    "elapsedTime",
	    "timestamp",
	    "xmlPayload",
	    "childEntries"
	})
public class Entry {

	private static final String INSTANCE_ID = "instanceId";
	private static final String PARENT_INSTANCE_ID = "parentInstanceId";
	private static final String FAULT_ID = "faultId";
	private static final String RETRY_COUNT = "retryCount";
	private static final String DATE = "date";
	private static final String LAST_UPDATED_DATE = "lastUpdatedDate";
	private static final String ELAPSED_TIME = "elapsedTime";
	private static final String TIMESTAMP = "timestamp";
    private static final String ACTION_TYPE = "actionType";
    private static final String ACTION_NAME = "actionName";
    private static final String SUB_TYPE = "subType";
    private static final String TYPE = "type";
    
    private static final String ENTITY_NAME = "entityName";
    private static final String COMPOSITE_INSTANCE_ID = "compositeInstanceId";
    private static final String COMPOSITE_NAME = "compositeName";
    private static final String COMPOSITE_DN = "compositeDN";
    private static final String APPLICATION_NAME = "applicationName";
    private static final String STATUS = "status";
    
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
    private String instanceId;
    private String externalComponent;
    private String parentInstanceId;
    private String faultId;
    private Date date;
    private Long timestamp;
    private Integer retryCount;
    
    private Date lastUpdatedDate;
    private Long elapsedTime;
    private String actionType;
    private String actionName;
    
    private String subType;
    private String type;
    
    
    private String entityName;
    private String compositeInstanceId;
    private String compositeName;
    private String compositeDN;
    private String applicationName;
    
    private String status;
    
    private String xmlPayload;
    private Event event;

    private List<Entry> childEntries;
    
	//--------------------------------------
	// Constructors
	//--------------------------------------
	public Entry(){
        childEntries = new ArrayList<Entry>();
    }
    
    public Entry(StartElement startElement){
        
        childEntries = new ArrayList<Entry>();
        
        try{
	        Iterator<Attribute> attributes = startElement.getAttributes();
	                        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz");
	        
	        while(attributes.hasNext()){
	            Attribute attribute = attributes.next();
	            
	            if(attribute.getName().toString().equals(INSTANCE_ID)){
	            	instanceId = attribute.getValue();
	            }else if(attribute.getName().toString().equals(PARENT_INSTANCE_ID)){
	            	parentInstanceId = attribute.getValue();
	            }else if(attribute.getName().toString().equals(ACTION_TYPE)){
	            	actionType = attribute.getValue();
	            }else if(attribute.getName().toString().equals(ACTION_NAME)){
	            	actionName = attribute.getValue();
	            }else if(attribute.getName().toString().equals(FAULT_ID)){
	            	faultId = attribute.getValue();
	            }else if(attribute.getName().toString().equals(RETRY_COUNT)){
	            	retryCount = Integer.parseInt(attribute.getValue());
	            }else if(attribute.getName().toString().equals(DATE)){
	            	date = sdf.parse(attribute.getValue());
	            }else if(attribute.getName().toString().equals(LAST_UPDATED_DATE)){
	            	lastUpdatedDate = sdf.parse(attribute.getValue());
	            }else if(attribute.getName().toString().equals(ELAPSED_TIME)){
	            	elapsedTime = Long.parseLong(attribute.getValue());
	            }else if(attribute.getName().toString().equals(TIMESTAMP)){
	            	timestamp = Long.parseLong(attribute.getValue());
	            }else if(attribute.getName().toString().equals(SUB_TYPE)){
	            	subType = attribute.getValue();
	            }else if(attribute.getName().toString().equals(TYPE)){
	            	type = attribute.getValue();
	            }
	            
	        }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
    }
	
    
	//--------------------------------------
	// Public Methods
	//--------------------------------------
    public void addElement(StartElement startElement, XMLEventReader eventReader) {

    	try{
    		String elementName = startElement.getName().getLocalPart();
    		
    		XMLEvent event = eventReader.nextEvent();
    		String content = event.asCharacters().getData();
    		
	    	if(ENTITY_NAME.equals(elementName)){
	    		entityName = content;
	    	}else if(COMPOSITE_INSTANCE_ID.equals(elementName)){
	    		compositeInstanceId = content;
	    	}else if(COMPOSITE_NAME.equals(elementName)){
	    		compositeName = content;
	    	}else if(COMPOSITE_DN.equals(elementName)){
	    		compositeDN = content;
	    	}else if(APPLICATION_NAME.equals(elementName)){
	    		applicationName = content;
	    	}else if(STATUS.equals(elementName)){
	    		status = content;
	    	}
    	}
    	catch(Exception e){
    		System.out.println("---> StartElement.isEndElement = " + startElement.isEndElement());
    		System.out.println("---> StartElement.getName = " + startElement.getName());
    		System.out.println("---> StartElement.getName.getLocalPart = " + startElement.getName().getLocalPart());
    		e.printStackTrace();
    	}
	}
    
    
    public boolean AddEvent(Event event){
    	
    	if(this.lastUpdatedDate == null){
    		return false;
    	}
    	
    	String eventActionName = event.getActionName();
    	String eventEntityName = event.getEntityName();
    	
    	if(event.getDate().before(this.lastUpdatedDate)){
    		//return false;
    	}
    	
    	if(this.actionName.equals(eventActionName) && this.entityName.equals(eventEntityName)){
    		
    		if(this.event == null){
    			this.event = event;
    		}
    		else{
    			this.event = event;//todo check this
    		}
    		
    		return true;
    	}
    	else{
    		
    		for(Entry child : childEntries){
    			if(child.AddEvent(event)){
    				return true;
    			}
    		}
    		
    		return false;
    	}
    }
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
    public void AddChildEntry(Entry childEntry){
        childEntries.add(childEntry);
    }

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getExternalComponent() {
		return externalComponent;
	}

	public void setExternalComponent(String externalComponent) {
		this.externalComponent = externalComponent;
	}

	public String getParentInstanceId() {
		return parentInstanceId;
	}

	public void setParentInstanceId(String parentInstanceId) {
		this.parentInstanceId = parentInstanceId;
	}

	public String getFaultId() {
		return faultId;
	}

	public void setFaultId(String faultId) {
		this.faultId = faultId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Long getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getCompositeInstanceId() {
		return compositeInstanceId;
	}

	public void setCompositeInstanceId(String compositeInstanceId) {
		this.compositeInstanceId = compositeInstanceId;
	}

	public String getCompositeName() {
		return compositeName;
	}

	public void setCompositeName(String compositeName) {
		this.compositeName = compositeName;
	}

	public String getCompositeDN() {
		return compositeDN;
	}

	public void setCompositeDN(String compositeDN) {
		this.compositeDN = compositeDN;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Entry> getChildEntries() {
		return childEntries;
	}

	public void setChildEntries(List<Entry> childEntries) {
		this.childEntries = childEntries;
	}

	public String getXmlPayload() {
		if(event != null){
			return event.getDetails();
		}
		return xmlPayload;
	}

	public void setXmlPayload(String xmlPayload) {
		this.xmlPayload = xmlPayload;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	
}
