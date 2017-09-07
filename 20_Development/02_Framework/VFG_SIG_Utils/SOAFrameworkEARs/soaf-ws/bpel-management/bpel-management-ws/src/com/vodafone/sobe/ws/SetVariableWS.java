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
import oracle.soa.management.facade.bpel.BPELServiceEngine;
import oracle.soa.management.facade.flow.CommonFault;
import oracle.soa.management.util.FaultFilter;
import oracle.soa.management.util.flow.CommonFaultFilter;

import com.vodafone.sobe.bpel.management.BpelManagerLocal;
import com.vodafone.sobe.ws.dto.GetVariables.GetVariablesRequest;
import com.vodafone.sobe.ws.dto.GetVariables.GetVariablesResponse;
import com.vodafone.sobe.ws.dto.SetVariable.SetVariableRequest;
import com.vodafone.sobe.ws.dto.SetVariable.SetVariableResponse;


@WebService(targetNamespace = "http://ws.sobe.vodafone.com/BpelManagement/SetVariable")
public class SetVariableWS {
	
	@EJB
	private BpelManagerLocal bpelManager;
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	// Get Variables Service
	//---------------------------------------------------------------------------------------------------------------------------------------
	@WebMethod
	public @WebResult(name="SetVariableResponse") SetVariableResponse SetVariable(@WebParam(name="setVariableRequest") SetVariableRequest request,
																			 @WebParam(name = "serviceHeader",
																			  		   header = true,
																			  		   mode = WebParam.Mode.OUT, partName = "serviceHeader") Holder<ServiceHeader> serviceHeader){
		
		SetVariableResponse response = new SetVariableResponse();
		ResponseCodes responseCodes = null;
		
		try{
			CommonFaultFilter commonFaultFilter = new CommonFaultFilter();
        	commonFaultFilter.setFlowId(request.getFlowId());
        	
        	List<CommonFault> commonFaultList = bpelManager.getLocator().getCommonFaults(commonFaultFilter);

        	if(commonFaultList.size() > 0){
        		responseCodes = ResponseCodes.UNKNOWN_VARIABLE;

            	String[] variables = bpelManager.getLocator().getVariableNames(commonFaultList.get(0));
            	            	
                for(int i = 0; i < variables.length; i++){
                	if(request.getVariable().equalsIgnoreCase(variables[i])){
                		bpelManager.getLocator().setVariableValue(commonFaultList.get(0), variables[i], "", request.getNewValue());
                		responseCodes = ResponseCodes.SUCCESS;
                		break;
                	}
                }
                
        	}
        	else{
        		responseCodes = ResponseCodes.FLOW_ID_NOT_FOUND_OR_NOT_RECOVERABLE;
        	}
        	
			
		}catch(Exception e){
			System.out.println("\n\n---> Exception in SetVariable\n\n");
			e.printStackTrace();
			responseCodes = ResponseCodes.FAILURE;
		}
		
		//Prepare Response
		serviceHeader.value = new ServiceHeader(responseCodes);
		
		return response;
	}

}
