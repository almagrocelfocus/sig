package com.eai.common.exception;

public class EAIDataTransformationException extends EAIException{
	private static final long serialVersionUID = -4324938916694724169L;
	public static final String DEFAULT_MESSAGE = "Unexpected transformation error";
	
	public EAIDataTransformationException(){
		super();
	}
	
	public EAIDataTransformationException(Throwable ex){
		super(ex);
	}
	
	public EAIDataTransformationException(String message){
		this("[EAIDTE]", message);
	}
	
	public EAIDataTransformationException(String code, String message){
		this(code, message, message);
	}
	
	public EAIDataTransformationException(String code, String message, String messageDetail){
		super(code, message, messageDetail);
	}
}
