package com.vodafone.sobe.ws.dto.GetInstanceDetails;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory(){
        
    }
    
    public com.vodafone.sobe.ws.dto.GetInstanceDetails.EventTrail createEventTrail(){
        return new com.vodafone.sobe.ws.dto.GetInstanceDetails.EventTrail();
    }
    
    
    public com.vodafone.sobe.ws.dto.GetInstanceDetails.Event createEvent(){
        return new com.vodafone.sobe.ws.dto.GetInstanceDetails.Event();
    }
    
}
