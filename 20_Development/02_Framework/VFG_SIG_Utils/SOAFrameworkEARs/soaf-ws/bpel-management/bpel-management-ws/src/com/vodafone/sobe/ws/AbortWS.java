package com.vodafone.sobe.ws;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Holder;

import com.vodafone.sobe.bpel.management.BpelManagerLocal;
import com.vodafone.sobe.ws.dto.Abort.AbortRequest;
import com.vodafone.sobe.ws.dto.Abort.AbortResponse;


@WebService(targetNamespace = "http://ws.sobe.vodafone.com/BpelManagement/Abort")
public class AbortWS {

	@EJB
	private BpelManagerLocal bpelManager;
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	// Abort Service
	//---------------------------------------------------------------------------------------------------------------------------------------
	@WebMethod
	public @WebResult(name="AbortResponse") AbortResponse Abort(@WebParam(name="abortRequest") AbortRequest request,
																			 @WebParam(name = "serviceHeader",
																			  		   header = true,
																			  		   mode = WebParam.Mode.OUT, partName = "serviceHeader") Holder<ServiceHeader> serviceHeader){
		
		AbortResponse response = new AbortResponse();
		ResponseCodes responseCodes = null;
		
		try{
			bpelManager.getLocator().abortFlows(new long[]{request.getFlowId()});
			
			responseCodes = ResponseCodes.SUCCESS;
			response.setAbortStatus("OK");
		}catch(Exception e){
			System.out.println("\n\n---> Exception in Abort\n\n");
			e.printStackTrace();
			responseCodes = ResponseCodes.FAILURE;
			response.setAbortStatus("FAILURE");
		}
		
		//Prepare Response
		serviceHeader.value = new ServiceHeader(responseCodes);
		
		
		return response;
	}
	
}
