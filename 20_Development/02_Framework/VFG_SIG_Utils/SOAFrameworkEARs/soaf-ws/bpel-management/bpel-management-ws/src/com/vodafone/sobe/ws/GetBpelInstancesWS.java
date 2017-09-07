package com.vodafone.sobe.ws;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Holder;

import oracle.soa.management.facade.ComponentInstance;
import oracle.soa.management.facade.CompositeInstance;
import oracle.soa.management.facade.Fault;
import oracle.soa.management.facade.Locator;
import oracle.soa.management.facade.flow.FlowInstance;
import oracle.soa.management.util.ComponentInstanceFilter;
import oracle.soa.management.util.CompositeInstanceFilter;
import oracle.soa.management.util.FaultActionType;
import oracle.soa.management.util.FaultFilter;
import oracle.soa.management.util.flow.FlowInstanceFilter;

import com.vodafone.sobe.bpel.management.BpelManagerLocal;
import com.vodafone.sobe.ws.dto.GetBpelInstances.BpelInst;
import com.vodafone.sobe.ws.dto.GetBpelInstances.GetBpelInstancesRequest;
import com.vodafone.sobe.ws.dto.GetBpelInstances.GetBpelInstancesResponse;
import com.vodafone.sobe.ws.dto.GetInstanceDetails.GetInstanceDetailsRequest;
import com.vodafone.sobe.ws.dto.GetInstanceDetails.GetInstanceDetailsResponse;
import com.vodafone.sobe.ws.dto.Retry.RetryRequest;
import com.vodafone.sobe.ws.dto.Retry.RetryResponse;


@WebService(targetNamespace = "http://ws.sobe.vodafone.com/BpelManagement/GetBpelInstances")
public class GetBpelInstancesWS {
	
	@EJB
	private BpelManagerLocal bpelManager;
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------
	// Get BpelInstances Service
	//---------------------------------------------------------------------------------------------------------------------------------------
	@WebMethod
	public @WebResult(name="GetBpelInstancesResponse") GetBpelInstancesResponse GetBpelInstances(@WebParam(name="GetBpelInstancesRequest") GetBpelInstancesRequest request,
																								 @WebParam(name = "serviceHeader",
																								 		   header = true,
																								 		   mode = WebParam.Mode.OUT, partName = "serviceHeader") Holder<ServiceHeader> serviceHeader){
		
		GetBpelInstancesResponse response = new GetBpelInstancesResponse();
		
		Locator locator = bpelManager.getLocator();
		
		try{
			//Step 1
	        FlowInstanceFilter flowInstanceFilter = FilterFactory.GetFlowInstanceFilter(request);
	        
	        List<FlowInstance> flowInstanceList = locator.getFlowInstances(flowInstanceFilter);
   
	        //Step 2
	        for(FlowInstance flowInstance : flowInstanceList){
	        	
	            CompositeInstanceFilter filter = new CompositeInstanceFilter();
	            
	            String compositeInstanceId = GetCompositeInstanceId(flowInstance);
	            if("".equals(compositeInstanceId)){
	            	continue;
	            }
	            
	            filter.setId(GetCompositeInstanceId(flowInstance));
	            if(request.getState() != null){
	            	filter.setState(request.getState());
	    		}
	            
	            
	            List<CompositeInstance> compositeInstancesList = locator.getCompositeInstances(filter);
	            
	            for(CompositeInstance compositeInstance : compositeInstancesList){
	                BpelInst bpelInstance = new BpelInst();
	                
	                if(flowInstance.getTitle() == null){
	                	bpelInstance.setOrderId("");
	                }
	                else{
	                	bpelInstance.setOrderId(flowInstance.getTitle());
	                }
	                
	                bpelInstance.setFlowId(flowInstance.getFlowId());
	                bpelInstance.setCompositeDN(flowInstance.getInitiatingCompositeDN().getCompositeName());
	                bpelInstance.setState(getStateAsString(compositeInstance.getState()));
	                bpelInstance.setCreatedTime(flowInstance.getCreatedTime());
	                bpelInstance.setUpdatedTime(flowInstance.getUpdatedTime());
	                
	                response.addBpelInstance(bpelInstance);	                
                }
	        }
	        
	        Collections.sort(response.getBpelInstanceList(), new OrderBy());
	        
	        serviceHeader.value = new ServiceHeader(ResponseCodes.SUCCESS);
		}
		catch(Exception e){
			serviceHeader.value = new ServiceHeader(ResponseCodes.FAILURE);
		}
		
		return response;
	}
	
	


	
	
	private String GetCompositeInstanceId(FlowInstance flowInstance){
		
		try {
			List<ComponentInstance> componentInstancesList = flowInstance.getComponentInstances();
			
			if(componentInstancesList.size() > 0){
				
				String compositeInstanceId = "";
				for(ComponentInstance componentInstance : componentInstancesList){
					if("".equals(compositeInstanceId)){
						compositeInstanceId = componentInstance.getCompositeInstanceId();
					}
					else{
						//Check if compositeIds are equal in every componentInstance
						if(!compositeInstanceId.equals(componentInstance.getCompositeInstanceId())){
							//throw new Exception("Assumption Failed! CompositeInstanceIds are different in the componentList. " + compositeInstanceId + " != " + componentInstance.getCompositeInstanceId());
						}
					}
				}
				
				return compositeInstanceId;
			}
			
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	

	private static String getStateAsString(int state)
    {
        // note that this is dependent on wheter the composite state is captured or not
        if (state == CompositeInstance.STATE_COMPLETED_SUCCESSFULLY)
            return ("success");
        else if (state == CompositeInstance.STATE_FAULTED)
            return ("faulted");
        else if (state == CompositeInstance.STATE_RECOVERY_REQUIRED)
            return ("recovery required");
        else if (state == CompositeInstance.STATE_RUNNING)
            return ("running");
        else if (state == CompositeInstance.STATE_STALE)
            return ("stale");
        else if(state== CompositeInstance.STATE_TERMINATED_BY_USER)
        	return ("aborted");
        else
            return ("unknown");
    }
	
	

}
