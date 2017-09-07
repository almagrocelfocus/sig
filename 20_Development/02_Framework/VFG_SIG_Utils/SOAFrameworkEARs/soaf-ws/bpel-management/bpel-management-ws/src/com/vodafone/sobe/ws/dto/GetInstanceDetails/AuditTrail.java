package com.vodafone.sobe.ws.dto.GetInstanceDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;


@XmlType(name = "AuditTrail", propOrder = {
	    "ecid",
	    "flowId",
	    "flowCorrelationId",
	    "activeInstances",
	    "date",
	    "lastUpdatedDate",
	    "elapsedTime",
	    "rootEntries"
	})
public class AuditTrail {
	
	private static final String ECID = "ecid";
	private static final String FLOW_ID = "flowId";
	private static final String FLOW_CORRELATION_ID = "flowCorrelationId";
	private static final String ACTIVE_INSTANCES = "activeInstances";
	private static final String DATE = "date";
	private static final String LAST_UPDATED_DATE = "lastUpdatedDate";
	private static final String ELAPSED_TIME = "elapsedTime";
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private String ecid;
    private Long flowId;
    private String flowCorrelationId;
    private Integer activeInstances;
    private Date date;
    private Date lastUpdatedDate;
    private Long elapsedTime;
    
    @XmlElementWrapper(name="EntryList")
    @XmlElement(name="Entry")
    private List<Entry> rootEntries;
    
    //<instanceId, entry>
    private HashMap<String, Entry> entriesMap;
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
    public AuditTrail(){
        rootEntries = new ArrayList<Entry>();
        entriesMap = new HashMap<String, Entry>();
    }
	
    public AuditTrail(StartElement startElement){
    	
    	rootEntries = new ArrayList<Entry>();
        entriesMap = new HashMap<String, Entry>();
    	
    	try{
	    	Iterator<Attribute> attributes = startElement.getAttributes();
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS zzz");
	        
	        while(attributes.hasNext()){
	            Attribute attribute = attributes.next();
		            
	            if(attribute.getName().toString().equals(ECID)){
	            	ecid = attribute.getValue();
	            }else if(attribute.getName().toString().equals(FLOW_ID)){
	            	flowId = Long.parseLong(attribute.getValue().toString());//todo can throw exception
	            }else if(attribute.getName().toString().equals(FLOW_CORRELATION_ID)){
	            	flowCorrelationId = attribute.getValue();
	            }else if(attribute.getName().toString().equals(ACTIVE_INSTANCES)){
	            	activeInstances = Integer.parseInt(attribute.getValue().toString());//todo can throw exception
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
    public void AddEntry(Entry newEntry){
        entriesMap.put(newEntry.getInstanceId(), newEntry);
    }
    

    public void BuildTreeStructure(){
        
        for(java.util.Map.Entry<String, Entry> kvp : entriesMap.entrySet()){
            if(kvp.getValue().getParentInstanceId() == null){
                rootEntries.add(kvp.getValue());
            }
        }
        
        for(java.util.Map.Entry<String, Entry> kvp : entriesMap.entrySet()){
            String parentInstanceId = kvp.getValue().getParentInstanceId();
            if(parentInstanceId != null){
               
                if(entriesMap.containsKey(parentInstanceId)){
                    Entry parent = entriesMap.get(parentInstanceId);
                    parent.AddChildEntry(kvp.getValue());
                }
                
            }
        }
                
    }
	
    
    public void AddEvents(EventTrail eventTrail){
    	String entryId = eventTrail.getCid().replace("bpel:", "");
    	
    	Entry topParent = entriesMap.get(entryId);
    	
    	if(topParent == null){
    		return;
    	}
    	
    	
    	HashMap<String, Event> eventMapping = eventTrail.getWikeyToEventMapping();
    	
    	for(java.util.Map.Entry<String, Event> kvp : eventMapping.entrySet()){
    		if(!"".equals(kvp.getValue().getDetails())){
    			topParent.AddEvent(kvp.getValue());
    		}
    	}
    	
    }
	//--------------------------------------
	// Private Methods
	//--------------------------------------
    
    
    
    
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
    public String getEcid() {
        return ecid;
    }

    public void setEcid(String ecid) {
        this.ecid = ecid;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getFlowCorrelationId() {
        return flowCorrelationId;
    }

    public void setFlowCorrelationId(String flowCorrelationId) {
        this.flowCorrelationId = flowCorrelationId;
    }

    public Integer getActiveInstances() {
        return activeInstances;
    }

    public void setActiveInstances(Integer activeInstances) {
        this.activeInstances = activeInstances;
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


	public List<Entry> getRootEntries() {
		return rootEntries;
	}

	public void setRootEntries(List<Entry> rootEntries) {
		this.rootEntries = rootEntries;
	}

    
    
}
