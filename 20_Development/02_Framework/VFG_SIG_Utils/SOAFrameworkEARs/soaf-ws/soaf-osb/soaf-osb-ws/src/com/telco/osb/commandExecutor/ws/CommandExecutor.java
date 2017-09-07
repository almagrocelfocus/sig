package com.telco.osb.commandExecutor.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://ws.esb.telco.com/commandExecutor/exec")
public class CommandExecutor {

	@WebMethod
	public @WebResult()
	CommandExecutorResponse exec(@WebParam(name = "command") String command, @WebParam(name = "debugMode") String debugMode) {
		
		CommandExecutorResponse resp = new CommandExecutorResponse();
		
		try {
			
			//If debugMode print output commmand:
			if("true".equals(debugMode)) {
				
				Runtime runtime = Runtime.getRuntime();
				// Execute command:
				Process process = runtime.exec(command);				
				String commandOutput = "";
				/* Set up process I/O. */
				BufferedReader input = new BufferedReader(new InputStreamReader(
						process.getInputStream()));
				
				String line = null;
				while ((line = input.readLine()) != null)
				{
					commandOutput += line + "\n";
					//System.out.println(line);
				}	
				resp.setResponseCode("0");
				resp.setResponseMessage("Success");
				resp.setCommandOutput(commandOutput);
			
			// If not debugMode execute and forget:
			} else {
				Runtime.getRuntime().exec(command);
				resp.setResponseCode("0");
				resp.setResponseMessage("Success");
			}
			
		} catch (IOException e) {
			
			resp.setResponseCode("1");
			resp.setResponseMessage(e.getLocalizedMessage());
		}
		
		return resp;
	}

}