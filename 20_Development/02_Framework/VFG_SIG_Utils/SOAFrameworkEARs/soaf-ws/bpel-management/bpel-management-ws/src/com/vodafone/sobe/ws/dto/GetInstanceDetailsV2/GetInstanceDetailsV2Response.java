package com.vodafone.sobe.ws.dto.GetInstanceDetailsV2;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class GetInstanceDetailsV2Response {

	static final String AUDIT_TRAIL = "audit_trail";
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private AuditTrail auditTrail;
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	public GetInstanceDetailsV2Response(){
		auditTrail = new AuditTrail();
	}

	public GetInstanceDetailsV2Response(String flowTrace){
		
		try{
            
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            
            InputStream inputStream = new StringBufferInputStream(flowTrace);
            
            XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);

            auditTrail = null;
            
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                
                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    
                    if(startElement.getName().getLocalPart() == AUDIT_TRAIL){
                    	auditTrail = new AuditTrail(startElement);
                    	continue;
                    }   
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
	
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
	public AuditTrail getAuditTrail() {
		return auditTrail;
	}

	public void setAuditTrail(AuditTrail auditTrail) {
		this.auditTrail = auditTrail;
	}
	
	
}
