package com.eai.common.entities;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

import com.eai.common.exception.EAIConverterException;
import com.eai.common.utils.EAILogger;
import com.eai.common.utils.ListUtils;
import com.eai.common.validator.AnnotationValidator;
import com.eai.common.validator.IErrorCatalogue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public abstract class Jsonable implements Serializable {
	private static final long serialVersionUID = 15263299373461060L;
	
	public static <T> String toJSONString(Object obj) {
		return toJSONString(obj, null);
	}
	public static <T> String toJSONString(Object obj, Class<T> serializationView) {
		try {
			StringWriter messageStringWriter = new StringWriter();
	    	ObjectMapper om = new ObjectMapper();
	    	if( serializationView != null ){
	    		om.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		    	ObjectWriter ow = om.writerWithView(serializationView);
		    	ow.writeValue(messageStringWriter, obj);
	    	}else{
	    		om.writeValue(messageStringWriter, obj);
	    	}
	    	return messageStringWriter.toString();
		} catch (Exception e) {
			return "Cannot convert the object to string: " + e.getLocalizedMessage();
		}
	}
	
	public <T> String toString(Class<T> serializationView ) {
		try {
			StringWriter messageStringWriter = new StringWriter();
	    	ObjectMapper om = new ObjectMapper();
	    	if( serializationView != null ){
	    		om.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
		    	ObjectWriter ow = om.writerWithView(serializationView);
		    	ow.writeValue(messageStringWriter, this);
	    	}else{
	    		om.writeValue(messageStringWriter, this);
	    	}
	    	return messageStringWriter.toString();
		} catch (Exception e) {
			return "Cannot convert the object to string: " + e.getLocalizedMessage();
		}
	}
	
	@Override
	public String toString() {
		return toString(null);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T fromString(String jsonString) {
		return (T) fromStringStatic(jsonString, this.getClass());
	}
	public static <T> T fromStringStatic(String jsonString, Class<T> classType) {
		try {
			ObjectMapper om = new ObjectMapper();
			return om.readValue(jsonString, classType);
		} catch (Exception e) {
			EAILogger.error(e);
			throw new EAIConverterException(e);
		}
	}
	
	public List<IErrorCatalogue> validate(){
		return AnnotationValidator.parse(getIErrorCatalogue(), this);
	}
	public IErrorCatalogue validateSingle(){
		List<IErrorCatalogue> result = AnnotationValidator.parse(getIErrorCatalogue(), this);
		return ListUtils.isNullOrEmpty(validate()) ? null : result.get(0);
	}
	@JsonIgnore
	public boolean isValid(){
		return ListUtils.isNullOrEmpty(validate());
	}
	@JsonIgnore
	public IErrorCatalogue[] getIErrorCatalogue(){
		return null;
	}
}
