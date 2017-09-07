package com.vodafone.sobe.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Holder;

import oracle.soa.management.facade.Fault;
import oracle.soa.management.facade.Locator;
import oracle.soa.management.facade.ServiceEngine;
import oracle.soa.management.facade.bpel.BPELServiceEngine;
import oracle.soa.management.facade.bpmn.BPMNServiceEngine;
import oracle.soa.management.facade.flow.CommonFault;
import oracle.soa.management.facade.hw.WorkflowServiceEngine;
import oracle.soa.management.util.FaultFilter;
import oracle.soa.management.util.flow.CommonFaultFilter;

import com.vodafone.sobe.bpel.management.BpelManagerLocal;
import com.vodafone.sobe.ws.dto.GetVariables.GetVariablesRequest;
import com.vodafone.sobe.ws.dto.GetVariables.GetVariablesResponse;
import com.vodafone.sobe.ws.dto.GetVariables.Variable;

@WebService(targetNamespace = "http://ws.sobe.vodafone.com/BpelManagement/GetVariables")
public class GetVariablesWS {

	@EJB
	private BpelManagerLocal bpelManager;
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	// Get Variables Service
	//---------------------------------------------------------------------------------------------------------------------------------------
	@WebMethod
	public @WebResult(name="GetVariablesResponse") GetVariablesResponse GetVariables(@WebParam(name="getVariablesRequest") GetVariablesRequest request,
																			 @WebParam(name = "serviceHeader",
																			  		   header = true,
																			  		   mode = WebParam.Mode.OUT, partName = "serviceHeader") Holder<ServiceHeader> serviceHeader){
		
		GetVariablesResponse response = new GetVariablesResponse();
		ResponseCodes responseCodes = null;
		
		try{
            CommonFaultFilter commonFaultFilter = new CommonFaultFilter();
        	commonFaultFilter.setFlowId(request.getFlowId());
        	
        	List<CommonFault> commonFaultList = bpelManager.getLocator().getCommonFaults(commonFaultFilter);
        	
        	if(commonFaultList.size() > 0){
        		
        		String[] variableNames = bpelManager.getLocator().getVariableNames(commonFaultList.get(0));
            	
                for(int i = 0; i < variableNames.length; i++){
                	String variableValue = bpelManager.getLocator().getVariableValue(commonFaultList.get(0), variableNames[i], "");

                	response.getVariables().add(new Variable(variableNames[i], variableValue));
                }
                
                responseCodes = ResponseCodes.SUCCESS;
        	}
        	else{
        		responseCodes = ResponseCodes.FLOW_ID_NOT_FOUND_OR_NOT_RECOVERABLE;
        	}
			
		}catch(Exception e){
			System.out.println("\n\n---> Exception in GetVariables\n\n");
			e.printStackTrace();
			responseCodes = ResponseCodes.FAILURE;
		}
		
		//Prepare Response
		serviceHeader.value = new ServiceHeader(responseCodes);
		
		return response;
	}
	
}
