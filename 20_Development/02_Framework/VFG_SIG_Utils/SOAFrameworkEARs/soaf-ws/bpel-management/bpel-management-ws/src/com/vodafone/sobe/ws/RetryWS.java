package com.vodafone.sobe.ws;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Holder;

import oracle.soa.management.facade.ComponentInstance;
import oracle.soa.management.facade.Fault;
import oracle.soa.management.facade.flow.FlowInstance;
import oracle.soa.management.util.FaultActionType;
import oracle.soa.management.util.FaultFilter;
import oracle.soa.management.util.flow.FlowInstanceFilter;

import com.vodafone.sobe.bpel.management.BpelManagerLocal;
import com.vodafone.sobe.ws.dto.Retry.RetryRequest;
import com.vodafone.sobe.ws.dto.Retry.RetryResponse;

@WebService(targetNamespace = "http://ws.sobe.vodafone.com/BpelManagement/Retry")
public class RetryWS{

	@EJB
	private BpelManagerLocal bpelManager;
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	// Retry Service
	//---------------------------------------------------------------------------------------------------------------------------------------
	@WebMethod
	public @WebResult(name="RetryResponse") RetryResponse Retry(@WebParam(name="componentInstanceId") RetryRequest request, 
																@WebParam(name = "serviceHeader",
																		  header = true,
																		  mode = WebParam.Mode.OUT, partName = "serviceHeader") Holder<ServiceHeader> serviceHeader){

		RetryResponse retryResponse = new RetryResponse();
		ResponseCodes responseCodes = null;
		
		try {
			
			FlowInstanceFilter flowInstanceFilter = new FlowInstanceFilter();
			flowInstanceFilter.setFlowId(Long.parseLong(request.getFlowId()));
			
			List<FlowInstance> flowInstancesList = bpelManager.getLocator().getFlowInstances(flowInstanceFilter);
			
			if(flowInstancesList == null || flowInstancesList.size() == 0){
				responseCodes = ResponseCodes.UNKNOWN_FLOW_ID;
			}
			else{
				
				for(FlowInstance flowInstance : flowInstancesList){
					
					if(flowInstance.getRecoverableFaults() == 0){responseCodes = ResponseCodes.FLOW_ID_NOT_RECOVERABLE; continue;}
						
					FaultFilter faultFilter = new FaultFilter();
					faultFilter.setFlowId(flowInstance.getFlowId());
					
					List<Fault> faultList = bpelManager.getLocator().getFaults(faultFilter);
					
					for(Fault fault : faultList){
						if(fault.isRecoverable()){
							bpelManager.getLocator().recoverFault(Long.parseLong(fault.getId()), FaultActionType.ACTION_RETRY);
						}
					}
					
					responseCodes = ResponseCodes.SUCCESS;
				}
				
			}
			
		} catch (RemoteException e) {
			responseCodes = ResponseCodes.FAILURE;
		} catch (Exception e) {
			responseCodes = ResponseCodes.FAILURE;
		}
		
		//Prepare Response
		serviceHeader.value = new ServiceHeader(responseCodes);
		retryResponse.setRetryStatus("");
		
		return retryResponse;
	}
	
	
}
