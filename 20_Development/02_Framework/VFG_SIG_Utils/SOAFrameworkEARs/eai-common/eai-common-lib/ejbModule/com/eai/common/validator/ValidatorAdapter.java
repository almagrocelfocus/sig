package com.eai.common.validator;

public abstract class ValidatorAdapter implements IValidator {
    /**
     * Validates the input.
     * 
     * @param input the input.
     * @return <code>true</code> if the input is valid
     */
    public abstract boolean isValid(Object... value);
    
    public String getErrorMessage(Object... params){
    	return String.format("Invalid field", (Object[]) params);
    }
}
