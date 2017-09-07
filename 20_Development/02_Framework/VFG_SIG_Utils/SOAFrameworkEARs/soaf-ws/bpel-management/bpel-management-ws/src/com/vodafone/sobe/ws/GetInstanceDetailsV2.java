package com.vodafone.sobe.ws;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.Holder;

import oracle.soa.management.facade.ComponentInstance;
import oracle.soa.management.facade.Locator;
import oracle.soa.management.facade.flow.FlowInstance;
import oracle.soa.management.util.flow.FlowInstanceFilter;

import com.vodafone.sobe.bpel.management.BpelManagerLocal;
import com.vodafone.sobe.ws.dto.GetInstanceDetails.Event;
import com.vodafone.sobe.ws.dto.GetInstanceDetails.EventOrderBy;
import com.vodafone.sobe.ws.dto.GetInstanceDetails.EventTrail;
import com.vodafone.sobe.ws.dto.GetInstanceDetailsV2.AuditTrail;
import com.vodafone.sobe.ws.dto.GetInstanceDetailsV2.GetInstanceDetailsV2Request;
import com.vodafone.sobe.ws.dto.GetInstanceDetailsV2.GetInstanceDetailsV2Response;

@WebService(targetNamespace="http://ws.sobe.vodafone.com/BpelManagement/GetInstanceDetailsV2")
public class GetInstanceDetailsV2 {

	
	@EJB
	private BpelManagerLocal bpelManager;
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	// GetInstanceDetailsV2 Service
	//---------------------------------------------------------------------------------------------------------------------------------------
	@WebMethod(operationName="GetInstanceDetailsV2")
	public @WebResult(name="GetInstanceDetailsV2Response") GetInstanceDetailsV2Response RetrieveInstanceDetailsV2(@WebParam(name="getInstanceDetailsV2Request") GetInstanceDetailsV2Request request,
																			 @WebParam(name = "serviceHeader",
																			  		   header = true,
																			  		   mode = WebParam.Mode.OUT, partName = "serviceHeader") Holder<ServiceHeader> serviceHeader){
		
		GetInstanceDetailsV2Response response = null;
		ResponseCodes responseCodes = ResponseCodes.SUCCESS;
		
		try{
			Locator locator = bpelManager.getLocator();
			
			FlowInstanceFilter flowInstanceFilter = FilterFactory.GetFlowInstanceFilter(request);
			
			List<FlowInstance> flowInstanceList = locator.getFlowInstances(flowInstanceFilter);
			
			if(flowInstanceList != null && flowInstanceList.size() > 0){
				
				FlowInstance flowInstance = flowInstanceList.get(0);
				
				response = new GetInstanceDetailsV2Response(flowInstance.getFlowTrace());
				
				//Payloads
				List<ComponentInstance> componentInstancesList = flowInstance.getComponentInstances();
				for(ComponentInstance componentInstance : componentInstancesList){
					HandlePayloadTrail(response.getAuditTrail(), componentInstance);
				}
				
				responseCodes = ResponseCodes.SUCCESS;
			}
			else{
				responseCodes = ResponseCodes.UNKNOWN_FLOW_ID;
			}
			
			Collections.sort(response.getAuditTrail().getEventList(), new EventOrderBy());
		}
		catch(Exception e){
			e.printStackTrace();
			responseCodes = ResponseCodes.FAILURE;
		}
		
		
		//Prepare Response
		serviceHeader.value = new ServiceHeader(responseCodes);
		
		return response;
	}
	
	
	private void HandlePayloadTrail(AuditTrail auditTrail, ComponentInstance componentInstance){
		
		try{
			String payloadTrail = componentInstance.getAuditTrail();
			
			InputStream inputStream = new StringBufferInputStream(payloadTrail);
			
			JAXBContext jc = JAXBContext.newInstance("com.vodafone.sobe.ws.dto.GetInstanceDetails");
			
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			
			EventTrail eventTrail = (EventTrail)unmarshaller.unmarshal(inputStream);
			
			for(Event newEvent : eventTrail.getEvent()){
				auditTrail.addEvent(newEvent);
			}
		}
		catch(javax.xml.bind.JAXBException e){
			e.printStackTrace();
		}
		
		
	}
	
	
}
