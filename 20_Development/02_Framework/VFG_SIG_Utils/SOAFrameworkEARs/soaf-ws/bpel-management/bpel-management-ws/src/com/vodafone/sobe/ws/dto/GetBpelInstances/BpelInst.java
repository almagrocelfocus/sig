package com.vodafone.sobe.ws.dto.GetBpelInstances;

import java.util.Date;

public class BpelInst {
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	private long flowId;
	
	private String orderId;
	
	private String compositeDN;
	
	private String state;
	
	
	private Date createdTime;
	private Date updatedTime;
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	public BpelInst(){
		
	}
	
	public BpelInst (long flowId, String compositeDN, String state, String orderId, Date createdTime, Date updatedTime) {
		this.flowId = flowId;
		this.compositeDN = compositeDN;
		this.state = state;
		this.orderId = orderId;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	
	
	//--------------------------------------
	// Public Methods
	//--------------------------------------
	@Override
	public String toString() {
		return "BpelInst [flowId=" + flowId + ", compositeDN="
				+ compositeDN + ", state=" + state + ", orderId=" + orderId
				+ ", createdTime=" + createdTime + ", updatedTime="
				+ updatedTime + "]";
	}
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------
	public long getFlowId() {
		return flowId;
	}


	public void setFlowId(long flowId) {
		this.flowId = flowId;
	}

	public String getCompositeDN() {
		return compositeDN;
	}


	public void setCompositeDN(String compositeDN) {
		this.compositeDN = compositeDN;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public Date getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	public Date getUpdatedTime() {
		return updatedTime;
	}


	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	

}
