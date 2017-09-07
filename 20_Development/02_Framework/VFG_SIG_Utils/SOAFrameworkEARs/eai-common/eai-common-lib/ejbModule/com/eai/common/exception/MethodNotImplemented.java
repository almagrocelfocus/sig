package com.eai.common.exception;

import com.eai.common.exception.EAIException;

public class MethodNotImplemented extends EAIException{
	private static final long serialVersionUID = 5443806432861470014L;
	
	public MethodNotImplemented(){
		super();
	}
	
	public MethodNotImplemented(String message){
		super("[MNIE]", message);
	}
}
