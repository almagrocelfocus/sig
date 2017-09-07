package com.vodafone.sobe.ws.dto.GetInstanceDetails;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Date;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class GetInstanceDetailsResponse {

	static final String ENTRY = "entry";
    static final String AUDIT_TRAIL = "audit_trail";
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
    private AuditTrail auditTrail;
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	public GetInstanceDetailsResponse(){
		
	}
	
	public GetInstanceDetailsResponse(String flowTrace){
		
		try{
            
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            
            InputStream inputStream = new StringBufferInputStream(flowTrace);
            
            XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);
            
            List<Entry> entryList;
            
            Entry entry = null;
            auditTrail = null;
            
            while(eventReader.hasNext()){
                XMLEvent event = eventReader.nextEvent();
                
                if(event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    
                    if(startElement.getName().getLocalPart() == AUDIT_TRAIL){
                    	auditTrail = new AuditTrail(startElement);
                    	continue;
                    }
                    
                    if(startElement.getName().getLocalPart() == ENTRY){
                        entry = new Entry(startElement);
                        continue;
                    }
                    
                    if(auditTrail != null && entry != null){
                    	entry.addElement(startElement, eventReader);
                    }
                    
                }
                
                if(event.isEndElement()){
                    EndElement endElement = event.asEndElement();
                    
                    if(endElement.getName().getLocalPart().equals(ENTRY)){
                        auditTrail.AddEntry(entry);
                        entry = null;
                    }
                }
            }
            
            auditTrail.BuildTreeStructure();
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
