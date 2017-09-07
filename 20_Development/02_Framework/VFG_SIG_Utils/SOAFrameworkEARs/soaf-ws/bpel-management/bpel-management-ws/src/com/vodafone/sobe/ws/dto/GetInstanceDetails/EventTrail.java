package com.vodafone.sobe.ws.dto.GetInstanceDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "audit_trail")
public class EventTrail {

	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private List<Event> event = new ArrayList<Event>();
    
    private Long flowId;
    private String cid;
    
    private HashMap<String, Event> wikeyToEventMapping;
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	
	
	//--------------------------------------
	// Public Methods
	//--------------------------------------
    /*
	public Event GetEvent(String wikey){
		if(wikeyToEventMapping == null){
			System.out.println("---> Building HashMap!");
			BuildHashMap();
		}
		
		return wikeyToEventMapping.get(wikey);
	}
	*/
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	private void BuildHashMap(){
		wikeyToEventMapping = new HashMap<String, Event>();
		
		for(Event eventInstance : event){
			if(!"".equals(eventInstance.getWikey()) && eventInstance.getWikey() != null){
				InsertEvent(eventInstance);
			}
		}
		
		System.out.println("---> HashMap has: " + wikeyToEventMapping.size() + " elements");
		System.out.println("---> List<Event> has: " + event.size() + " elements");
		
		for(java.util.Map.Entry<String, Event> kvp : wikeyToEventMapping.entrySet()){
			System.out.println("--> N = " + kvp.getValue().getN());
		}
		
	}
	
	
	private void InsertEvent(Event eventInstance){
		
		if(!wikeyToEventMapping.containsKey(eventInstance.getWikey())){
			wikeyToEventMapping.put(eventInstance.getWikey(), eventInstance);
			return;
		}

		InsertEvent(wikeyToEventMapping.get(eventInstance.getWikey()), eventInstance);
	}
	
	
	private void InsertEvent(Event alreadyInsertedEvent, Event newEvent){
		
		Date alreadyInsertedEventDate = alreadyInsertedEvent.getDate();
		Date newEventDate = newEvent.getDate();


		if(newEventDate.after(alreadyInsertedEventDate)){
			wikeyToEventMapping.put(newEvent.getWikey(), newEvent);
		}
		else if("".equals(alreadyInsertedEvent.getDetails()) && !"".equals(newEvent.getDetails())){
			wikeyToEventMapping.put(newEvent.getWikey(), newEvent);
		}
		
		
	}
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

    @XmlAttribute(name="flowId")
    public Long getFlowId() {
        return flowId;
    }
    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    @XmlAttribute(name="cid")
    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }


	public HashMap<String, Event> getWikeyToEventMapping() {
		if(wikeyToEventMapping==null){
			BuildHashMap();
		}
		return wikeyToEventMapping;
	}


	public void setWikeyToEventMapping(HashMap<String, Event> wikeyToEventMapping) {
		this.wikeyToEventMapping = wikeyToEventMapping;
	}
    
    
    
}
