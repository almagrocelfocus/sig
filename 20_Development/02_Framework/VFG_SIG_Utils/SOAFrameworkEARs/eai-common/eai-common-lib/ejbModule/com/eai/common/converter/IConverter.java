package com.eai.common.converter;

public interface IConverter<T> {
	public T convert(Object obj);
	public T convert(Object obj, Object... elementTitle);
	public T safeConvert(Object obj);
	public T safeConvert(Object obj, Object... elementTitle);
}
