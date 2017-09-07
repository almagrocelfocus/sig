package com.vodafone.sobe.ws.dto.GetInstanceDetailsV2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

import com.vodafone.sobe.ws.Rules.EventFilter;

@XmlType(name = "AuditTrail", propOrder = {
	    "flowId",
	    "date",
	    "lastUpdatedDate",
	    "elapsedTime",
	    "eventList"
	})
public class AuditTrail {
	
	private static final String FLOW_ID = "flowId";
	private static final String DATE = "date";
	private static final String LAST_UPDATED_DATE = "lastUpdatedDate";
	private static final String ELAPSED_TIME = "elapsedTime";

	//--------------------------------------
	// Instance Variables
	//--------------------------------------
    private Long flowId;
    private Date date;
    private Date lastUpdatedDate;
    private Long elapsedTime;
    
    
    private List<com.vodafone.sobe.ws.dto.GetInstanceDetails.Event> eventList;
    
    private EventFilter eventFilter;

	//--------------------------------------
	// Constructors
	//--------------------------------------
    public AuditTrail(){
    	eventList = new ArrayList<com.vodafone.sobe.ws.dto.GetInstanceDetails.Event>();
    	eventFilter = new EventFilter();
    }

    public AuditTrail(StartElement startElement){
    	eventList = new ArrayList<com.vodafone.sobe.ws.dto.GetInstanceDetails.Event>();
    	eventFilter = new EventFilter();
    	
    	try{
    		
Iterator<Attribute> attributes = startElement.getAttributes();
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz");
	        
	        while(attributes.hasNext()){
	            Attribute attribute = attributes.next();
		            
	            if(attribute.getName().toString().equals(FLOW_ID)){
	            	flowId = Long.parseLong(attribute.getValue().toString());//todo can throw exception
	            }else if(attribute.getName().toString().equals(DATE)){
	            	date = sdf.parse(attribute.getValue());
	            }else if(attribute.getName().toString().equals(LAST_UPDATED_DATE)){
	            	lastUpdatedDate = sdf.parse(attribute.getValue());
	            }else if(attribute.getName().toString().equals(ELAPSED_TIME)){
	            	elapsedTime = Long.parseLong(attribute.getValue());
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
    public void addEvent(com.vodafone.sobe.ws.dto.GetInstanceDetails.Event newEvent){
    	if(eventFilter.IsValid(newEvent)){
    		eventList.add(newEvent);
    	}
    	else{
    		System.out.println("---> Discarding event with message: " + newEvent.getMessage());
    	}
    }

	

	//--------------------------------------
	// Private Methods
	//--------------------------------------


	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
    public List<com.vodafone.sobe.ws.dto.GetInstanceDetails.Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<com.vodafone.sobe.ws.dto.GetInstanceDetails.Event> eventList) {
		this.eventList = eventList;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	
	
}
