package com.vodafone.sobe.ws;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.Holder;

import oracle.soa.management.facade.ComponentInstance;
import oracle.soa.management.facade.Locator;
import oracle.soa.management.facade.flow.FlowInstance;
import oracle.soa.management.util.flow.FlowInstanceFilter;

import com.vodafone.sobe.bpel.management.BpelManagerLocal;
import com.vodafone.sobe.ws.dto.GetInstanceDetails.AuditTrail;
import com.vodafone.sobe.ws.dto.GetInstanceDetails.EventTrail;
import com.vodafone.sobe.ws.dto.GetInstanceDetails.GetInstanceDetailsRequest;
import com.vodafone.sobe.ws.dto.GetInstanceDetails.GetInstanceDetailsResponse;

@WebService(targetNamespace = "http://ws.sobe.vodafone.com/BpelManagement/GetInstanceDetails")
public class GetInstanceDetailsWS {

	@EJB
	private BpelManagerLocal bpelManager;
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	// Get Instance Details Service
	//---------------------------------------------------------------------------------------------------------------------------------------
	@WebMethod(operationName="GetInstanceDetails")
	public @WebResult(name="GetInstanceDetailsResponse") GetInstanceDetailsResponse RetrieveInstanceDetails(@WebParam(name="getInstanceDetailsRequest") GetInstanceDetailsRequest request,
																									   		@WebParam(name = "serviceHeader",
																									   				  header = true,
																									   				  mode = WebParam.Mode.OUT, partName = "serviceHeader") Holder<ServiceHeader> serviceHeader){
		GetInstanceDetailsResponse response = null;
		ResponseCodes responseCodes = ResponseCodes.SUCCESS;
		
		try{			
			Locator locator = bpelManager.getLocator();
		
			
			FlowInstanceFilter flowInstanceFilter = FilterFactory.GetFlowInstanceFilter(request);
			
			List<FlowInstance> flowInstanceList = locator.getFlowInstances(flowInstanceFilter);
			
			if(flowInstanceList != null && flowInstanceList.size() > 0){
				
				FlowInstance flowInstance = flowInstanceList.get(0);
				
				response = new GetInstanceDetailsResponse(flowInstance.getFlowTrace());
				
				//Payloads
				List<ComponentInstance> componentInstancesList = flowInstance.getComponentInstances();
				for(ComponentInstance componentInstance : componentInstancesList){
					HandlePayloadTrail(response.getAuditTrail(), componentInstance);
				}
				
				responseCodes = ResponseCodes.SUCCESS;
			}
			else{
				responseCodes = ResponseCodes.FAILURE;
			}
			
		}catch(Exception e){
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
			
			
			auditTrail.AddEvents(eventTrail);
			
		}catch(JAXBException e){
			e.printStackTrace();
		}
	}
	
	
	
}
