package com.eai.common.validator;

public interface IValidator {
	public boolean isValid(Object... values);
	public String getErrorMessage(Object... params);
}
