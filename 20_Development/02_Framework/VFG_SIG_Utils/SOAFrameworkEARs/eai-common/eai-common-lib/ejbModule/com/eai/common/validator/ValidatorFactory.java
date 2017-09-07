package com.eai.common.validator;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.eai.common.converter.ConverterFactory;
import com.eai.common.exception.EAIValidatorException;
import com.eai.common.utils.ListUtils;
import com.eai.common.utils.StringUtils;

public class ValidatorFactory {
	private static final String EMAIL_REGEX = "^.+@.+\\..+$";
	private static final String NUMBER_REGEX = "^\\d+$";
	private static final String WORDS_REGEX = "^[a-zA-Z\\s]+$";
	private static final String NONEMPTY_STRING_REGEX = "^.+$";
	private static final String MINCHAR_REGEX = "^.{4,}$";
	private static final String RANGE_FORMAT = "^[0-9]+\\-[0-9]+$";

	public static final IValidator getNumberValidator() {
        return new ValidatorAdapter() {
            public boolean isValid(Object... values) {
            	boolean isValid = true;
            	for( Object value : values ){
            		if( !StringUtils.isNullOrEmpty( value ) ){
            			isValid &= value.toString().matches(NUMBER_REGEX);
            		}
            	}
            	return isValid;
            }
        };
    }
	
	public static final IValidator getWordsValidator() {
        return new ValidatorAdapter() {
            public boolean isValid(Object... values) {
            	boolean isValid = true;
            	for( Object value : values ){
            		if( !StringUtils.isNullOrEmpty( value ) ){
            			isValid &= value.toString().matches(WORDS_REGEX);
            		}
            	}
            	return isValid;
            }
        };
    }
	
	public static final IValidator getEmailValidator() {
        return new ValidatorAdapter() {
            public boolean isValid(Object... values) {
            	boolean isValid = true;
            	for( Object value : values ){
            		if( !StringUtils.isNullOrEmpty( value ) ){
            			isValid &= value.toString().matches(EMAIL_REGEX);
            		}
            	}
            	return isValid;
            }
        };
    }

	public static final IValidator getDateValidator(){
		return new ValidatorAdapter() {
            public boolean isValid(Object... valueLst) {
            	boolean isValid = true;
            	for( Object obj : valueLst ){
            		try{
            			if( !(obj instanceof Date ) && !( obj instanceof Timestamp ) ){
            				ConverterFactory.getStringToDateConverter().convert(obj);
            			}
            		}catch (Exception e) {
						isValid = false;
						break;
					}
            	}
                return isValid;
            }
        };
	}
	
	public static final IValidator getMin4CharValidator() {
        return new ValidatorAdapter() {
            public boolean isValid(Object... values) {
            	boolean isValid = true;
            	for( Object value : values ){
            		if( !StringUtils.isNullOrEmpty( value ) ){
            			isValid &= value.toString().matches(MINCHAR_REGEX);
            		}
            	}
            	return isValid;
            }
        };
    }

	public static final IValidator getPTNIFValidator(){
		return new ValidatorAdapter() {
			public boolean isValid(Object... values) {
				boolean isValid = true;
	        	for( Object value : values ){
	        		if( !StringUtils.isNullOrEmpty( value ) ){
	        			isValid &= isValid(value);
	        		}
	        	}
	        	return isValid;
			}
			
            public boolean isValid(Object value) {
            	if( value == null ){
            		return true;
            	}else{
        			String nif = value.toString();
        			
        			char c;
                    int checkDigit = 0;
                    //Verifica se é nulo, se é numérico e se tem 9 dígitos
                    if(nif != null && nif.matches(NUMBER_REGEX) && nif.length() == 9){
                        //Obtem o primeiro número do NIF
                        c = nif.charAt(0);
                        //Verifica se o primeiro número é (1, 2, 5, 6, 8 ou 9)
                        if(c == '1' || c == '2' || c == '5' || c == '6' || c == '8' || c == '9'){
                            //Calculo do Digito de Controlo
                            checkDigit = c * 9;
                            int i = 0;
                            for(i = 2; i <= 8; i++){
                                checkDigit += nif.charAt(i-1) * (10-i);
                            }
                            checkDigit = 11 - (checkDigit % 11);
                            //Se o digito de controle é maior que dez, coloca-o a zero
                            if(checkDigit >= 10){
                                checkDigit = 0;
                            }
                            //Compara o digito de controlo com o último número do NIF
                            //Se igual, o NIF é válido.
                            if(checkDigit == Character.digit(nif.charAt(8),10)){                        	
                                return true;
                            }
                        }
                    }
        			return false;
            	}
            }
        };
		
	}

	public static final IValidator getNonEmptyStringValidator(){
	       return new ValidatorAdapter() {
	            public boolean isValid(Object... values) {
	            	boolean isValid = true;
	            	for( Object value : values ){
	            		if( !StringUtils.isNullOrEmpty( value ) ){
	            			isValid &= value.toString().matches(NONEMPTY_STRING_REGEX);
	            		}
	            	}
	            	return isValid;
	            }
	        };
	}
	
	public static final IValidator getMandatoryValidator() {
		return new ValidatorAdapter(){
			public boolean isValid(Object... values){
				boolean isValid = true;
            	for( Object value : values ){
            		isValid &= !StringUtils.isNullOrEmpty( value );
            	}
            	return isValid;
			}
		};
	}
	
	//region dual arguments validator
	public static final IValidator getEqualStringsValidator(){
		return new ValidatorAdapter(){
			public boolean isValid(Object... values){
				if( values == null || values.length != 2 ){
					throw new EAIValidatorException("2 and only 2 arguments expected");
				}
				return values[0].toString().contentEquals(values[1].toString());
			}
		};
	}

	public static final IValidator getCompareStartEndDate() {
		return new ValidatorAdapter(){
			public boolean isValid(Object... values){
				if( values == null || values.length != 2 || !(values[0] instanceof Date) || !(values[1] instanceof Date)){
					throw new EAIValidatorException("2 and only 2 arguments of type Date expected");
				}
				if( values[0] == null || values[1] == null  ){
					return true;
				}
				Date firstDate = (Date)values[0];
				Date endDate = (Date)values[1];
				return firstDate.compareTo(endDate) > 0;
			}
		};
	}
	
	public static final IValidator getRangeLongByFormatValidator(final List<String> rangeLst) {
		return new ValidatorAdapter(){
			public boolean isValid(Object... values){
				boolean isValid = false;
				if(values == null || rangeLst == null){
					return isValid; 
				}
				for(String range: rangeLst){
					isValid |= getRangeLongByFormatValidator(range).isValid(values);
				}
				
				return isValid;
			}
		};
	}
	
	public static final IValidator getRangeLongByFormatValidator(final String range) {
		return new ValidatorAdapter(){
			public boolean isValid(Object... values){
				boolean isValid = false;
				if( values == null ){
					return isValid; 
				}
				if(range != null){
					if(!range.matches(RANGE_FORMAT)){
						throw new EAIValidatorException(String.format("Unexpected format for range validator '%1$s', expected format is '%2$s'",range,RANGE_FORMAT));
					}
				}
				Long min = ConverterFactory.getStringToLongConverter().convert(range.split("-")[0]);
				Long max = ConverterFactory.getStringToLongConverter().convert(range.split("-")[1]);
				return getRangeLongValidator(min, max).isValid(values);
			}
		};
	}
	
	public static final IValidator getRangeLongValidator(final long min, final long max) {
		return new ValidatorAdapter(){
			public boolean isValid(Object... values){
				boolean isValid = false;
				if( values == null ){
					return isValid; 
				}
				for(Object value : values){
					if(value != null){
						Long valueLong = null;
						if(value instanceof String){
							valueLong = ConverterFactory.getStringToLongConverter().convert(value);
						}else if(value instanceof Long){
							valueLong = (Long) value;
						}else{
							throw new EAIValidatorException("Invalid data type for getRangeLongValidator: "+value.getClass());
						}
						isValid |= valueLong >= min && valueLong <= max;
					}
				}
				return isValid;
			}
		};
	}
	
	public static final IValidator getExistsInListValidator(final List<?> lst) {
		return new ValidatorAdapter(){
			public boolean isValid(Object... values){
				boolean isValid = false;
				if( values == null ){
					return isValid; 
				}
				for(Object value : values){
					isValid |= ListUtils.getObject(lst, value) != null;
				}
				return isValid;
			}
		};
	}
	//endregion
}
