package com.vodafone.sobe.ws.dto.GetBpelInstances;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetBpelInstancesRequest implements Serializable {

	private static final long serialVersionUID = 6558133322375748892L;
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private Long flowId;
	private String orderId;
	private Integer state;
	private Date minDate;
	private Date maxDate;
	
	private Integer recordIndexStart;
	private Integer pageSize;
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	
	
	//--------------------------------------
	// Public Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
	public Long getFlowId() {
		return flowId;
	}
	
	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public Integer getState() {
		return state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}
	
	public Date getMinDate() {
		return minDate;
	}
	
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}
	
	public Date getMaxDate() {
		return maxDate;
	}
	
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public Integer getRecordIndexStart() {
		return recordIndexStart;
	}

	public void setRecordIndexStart(Integer recordIndexStart) {
		this.recordIndexStart = recordIndexStart;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	/*
	public Date stringMaxDatetoDate() throws ParseException{	
		System.out.println("EMNTREI stringMaxDatetoDate");
		if (maxDate != null){
		   	String dateStr = maxDate;
		   	System.out.println("MAX: " + maxDate);
	        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        Date date = new Date();
	        date = sf.parse(dateStr);
	        return date;
		}
		else
			return null;
	}
	
	public Date stringMinDatetoDate() throws ParseException{	
		System.out.println("EMNTREI stringMinDatetoDate");
		if (minDate != null){
		   	String dateStr = minDate;
		   	System.out.println("MIN: " + minDate);
	        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        Date date = new Date();
	        date = sf.parse(dateStr);
		   	System.out.println("Date: " + date);
	
	        return date;
        }
		else
			return null;
	}
	*/
	
}


