package com.eai.common.ws;

import java.io.Serializable;

import com.eai.common.converter.ConverterFactory;
import com.eai.common.ui.UIINHeader;
import com.eai.common.utils.EAIReflectionUtils;

public abstract class WSAbstractProxy implements WSProxy{
	protected void validateResponse(Object response){
		if(response == null) throw new RuntimeException("Empty response!");
		Object header = EAIReflectionUtils.safeGetterInvoke(response, "getHeader");
		if(header == null) throw new RuntimeException("Empty header!");
	}
	protected RuntimeException onException(Throwable e){
		if(e.getCause() != null){
			throw new RuntimeException(e.getCause());
		}else{
			throw new RuntimeException(e);
		}
	}
	protected <T extends Serializable> T prepareCall(Class<T> clazz, UIINHeader header){
		return prepareCall(EAIReflectionUtils.createNewInstance(clazz), header);
	}
	protected <T extends Serializable> T prepareCall(T object, UIINHeader header){
		EAIReflectionUtils.safeSetterInvoke(object, "setHeader", ConverterFactory.convert(EAIReflectionUtils.getGetterClass(object, "getHeader"), header));
		EAIReflectionUtils.safeSetterInvoke(object, "setBody", EAIReflectionUtils.createNewInstanceFromGetter(object, "getBody"));
		return object;
	}
	
	public static void getInstance(){};
}
