package com.telco.osb.commandExecutor.ws;

import java.io.Serializable;

public class CommandExecutorResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String responseCode;
	private String responseMessage;
	private String commandOutput;

	public CommandExecutorResponse() {
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getCommandOutput() {
		return commandOutput;
	}

	public void setCommandOutput(String commandOutput) {
		this.commandOutput = commandOutput;
	}

}
