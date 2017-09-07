package com.eai.common.validator;

public interface IErrorCatalogue {
	public String getMajorReturnCode();
	public String getMinorReturnCode();
	public String getMessageDetails();
	public String getMessageDetails(Object... args);
	public boolean isSuccess();
	public IErrorCatalogue valueOfEnum(String majorCode, String minorCode);
}
