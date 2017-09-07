package com.eai.common.converter;

public abstract class ConverterAdapter<T> implements IConverter<T>{
	public abstract T convert(Object obj);
	public abstract T convert(Object obj, Object... elementTitle);
	public T safeConvert(Object obj){
		try{
			return convert(obj);
		}catch (Exception e) {
			return null;
		}
	}
	public T safeConvert(Object obj, Object... elementTitle){
		try{
			return convert(obj, elementTitle);
		}catch (Exception e) {
			return null;
		}
	}
}
