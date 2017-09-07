package com.vodafone.sobe.ws.dto.GetInstanceDetails;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAttribute;

public class Event {

	
	
	public static final Pattern MESSAGE_PATTERN = Pattern.compile("\"(.*?)\".*\"(.*?)\"");
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private String sid;
    private Integer cat;
    private String wikey;
    private Integer state;    
    private Integer n;
    private Date date;
    private Integer type;
    
    private String label;
    private String psid;
    private String message;
    private String details;
	
    
    
	//--------------------------------------
	// Constructors
	//--------------------------------------
	
	
	//--------------------------------------
	// Public Methods
	//--------------------------------------
    public String getActionName(){
    	Matcher matcher = MESSAGE_PATTERN.matcher(message);
    	
    	if(matcher.find()){
    		return matcher.group(1);
    	}
    	
    	return "";
    }
    
    
    public String getEntityName(){
    	Matcher matcher = MESSAGE_PATTERN.matcher(message);
    	
    	if(matcher.find()){
    		return matcher.group(2);
    	}
    	
    	return "";
    }
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
    @XmlAttribute(name="sid")
    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }
    
    @XmlAttribute(name="cat")
    public Integer getCat() {
        return cat;
    }
    public void setCat(Integer cat) {
        this.cat = cat;
    }

    @XmlAttribute(name="n")
    public Integer getN() {
        return n;
    }
    public void setN(Integer n) {
        this.n = n;
    }

    @XmlAttribute(name="date")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    @XmlAttribute(name="type")
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }

    @XmlAttribute(name="label")
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    @XmlAttribute(name="psid")
    public String getPsid() {
        return psid;
    }
    public void setPsid(String psid) {
        this.psid = psid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    @XmlAttribute(name="wikey")
	public String getWikey() {
		return wikey;
	}
	
	public void setWikey(String wikey) {
		this.wikey = wikey;
	}
	
	@XmlAttribute(name="state")
	public Integer getState() {
		return state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}
    
    
	
}
