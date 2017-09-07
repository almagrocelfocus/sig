package com.vodafone.sobe.ws;

import java.util.Date;
import java.util.GregorianCalendar;

import com.vodafone.sobe.ws.dto.GetBpelInstances.GetBpelInstancesRequest;
import com.vodafone.sobe.ws.dto.GetInstanceDetails.GetInstanceDetailsRequest;
import com.vodafone.sobe.ws.dto.GetInstanceDetailsV2.GetInstanceDetailsV2Request;

import oracle.soa.management.util.flow.FlowInstanceFilter;

public class FilterFactory {
	
	//--------------------------------------
	// Instance Variables
	//--------------------------------------
	
	
	//--------------------------------------
	// Constructors
	//--------------------------------------
	
	
	//--------------------------------------
	// Public Methods
	//--------------------------------------
	public static FlowInstanceFilter GetFlowInstanceFilter(GetBpelInstancesRequest request){
		
		FlowInstanceFilter flowInstanceFilter = new FlowInstanceFilter();
		
		if(request.getPageSize() != null && request.getPageSize() > 0){
			flowInstanceFilter.setPageSize(request.getPageSize());
		}
		
		if(request.getRecordIndexStart() != null && request.getRecordIndexStart() >= 0){
			flowInstanceFilter.setPageStart(request.getRecordIndexStart());
		}
		
		if(request.getFlowId() != null){
			flowInstanceFilter.setFlowId(request.getFlowId());
		}
		
		
		if(!"".equals(request.getOrderId())){
			flowInstanceFilter.setInstanceName(request.getOrderId());
		}
		
		//todo complete this
		if(request.getState() != null){
			
		}
		
		
		if(request.getMinDate() != null){
			flowInstanceFilter.setMinCreationDate(request.getMinDate());
		}
		
		
		if(request.getMaxDate() != null){
			flowInstanceFilter.setMaxCreationDate(request.getMaxDate());
		}
		
		
		if(request.getMinDate() == null && request.getMaxDate() == null){
			Date bigBangDate = new Date((long)0);
			flowInstanceFilter.setMinCreationDate(bigBangDate);
		}
		
		
		return flowInstanceFilter;
	}

	public static FlowInstanceFilter GetFlowInstanceFilter(GetInstanceDetailsRequest request) {

		FlowInstanceFilter flowInstanceFilter = new FlowInstanceFilter();
		
		if(request.getFlowId() != null){
			flowInstanceFilter.setFlowId(request.getFlowId());
		}
		
		Date bigBangDate = new Date((long)0);
		flowInstanceFilter.setMinCreationDate(bigBangDate);
		
		return flowInstanceFilter;
	}
	
	public static FlowInstanceFilter GetFlowInstanceFilter(GetInstanceDetailsV2Request request) {

		FlowInstanceFilter flowInstanceFilter = new FlowInstanceFilter();
		
		if(request.getFlowId() != null){
			flowInstanceFilter.setFlowId(request.getFlowId());
		}
		
		Date bigBangDate = new Date((long)0);
		flowInstanceFilter.setMinCreationDate(bigBangDate);
		
		return flowInstanceFilter;
	}
	
	//--------------------------------------
	// Private Methods
	//--------------------------------------
	
	
	//--------------------------------------
	// Getters and Setters
	//--------------------------------------

}
