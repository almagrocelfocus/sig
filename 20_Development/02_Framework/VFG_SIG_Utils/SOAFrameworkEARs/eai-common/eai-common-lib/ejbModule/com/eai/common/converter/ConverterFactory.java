package com.eai.common.converter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.eai.common.EAIConstants;

import com.eai.common.exception.EAIConverterException;
import com.eai.common.exception.EAIValidatorException;
import com.eai.common.utils.FileConfigurationUtils;
import com.eai.common.utils.DateUtils;
import com.eai.common.utils.StringUtils;
import com.eai.common.validator.ValidatorFactory;

public class ConverterFactory {
	
	private ConverterFactory(){
	}
	
	private static ConverterFactory singletonObject = new ConverterFactory();
	public static ConverterFactory getInstance(String basePath) {
		return singletonObject;
	}
	
	public static <T> T convert(Class<T> targetClass, Object sourceObjLst) {
		return GenericDataConverter.getUIObject(targetClass, sourceObjLst);
	}
	public static <T> List<T> convert(Class<T> targetClass, List<?> sourceObjLst){
		return GenericDataConverter.getUIObject(targetClass, sourceObjLst);
	}
	
	/* START - CONVERT ACTIONS */
	public static final IConverter<String> getStringConverter() {
		return new ConverterAdapter<String>(){
			public String convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public String convert (Object obj, Object... elementTitle){
				if( obj == null){
					return null;
				}
				return  obj.toString();
			}
		};
	}
	
	public static final IConverter<String> getMandatoryStringConverter(){
		return new ConverterAdapter<String>(){
			public String convert (Object obj, Object... elementTitle){
				if( ! ValidatorFactory.getMandatoryValidator().isValid(obj) ){
					throw new EAIConverterException(ValidatorFactory.getMandatoryValidator().getErrorMessage(elementTitle));
				}
				return obj.toString();
			}
			public String convert(Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
		};
	}
	
	public static final IConverter<Integer> getStringToIntegerConverter() {
        return new ConverterAdapter<Integer>() {
        	public Integer convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public Integer convert (Object obj, Object... elementTitle){
            	if( obj == null || obj.toString().compareTo(EAIConstants.EMPTY_STRING) == 0){
            		return null;
            	}
            	try{
            		return Integer.valueOf(obj.toString());
            	} catch (NumberFormatException nfe){
					throw new EAIConverterException(nfe);				
            	}
            	 
            }
        };
    }
	
	public static final IConverter<String> getIntegerToStringConverter() {
        return new ConverterAdapter<String>() {
        	public String convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public String convert (Object obj, Object... elementTitle){
            	if( obj == null){
            		return null;
            	}
            	
            	return Integer.toString((Integer)obj);            	 
            }
        };
    }
	
	public static final IConverter<Long> getStringToLongConverter() {
        return new ConverterAdapter<Long>() {
        	public Long convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public Long convert (Object obj, Object... elementTitle){
            	if( obj == null){
            		return null;
            	}
            	try{
            		return Long.valueOf(obj.toString());
            	} catch (NumberFormatException e){
            		throw new EAIValidatorException(e);
            	}
            	 
            }
        };
    }
	
	public static final IConverter<String> getLongToStringConverter() {
        return new ConverterAdapter<String>() {
        	public String convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public String convert (Object obj, Object... elementTitle){
            	if( obj == null){
            		return null;
            	}
            	
            	return Long.toString((Long)obj);            	 
            }
        };
    }
	
	public static final IConverter<Date> getStringToDateConverter() {
        return new ConverterAdapter<Date>() {
        	public Date convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public Date convert (Object obj, Object... elementTitle){
            	if( obj == null){
            		return null;
            	}
            	
        		String date = (String)obj;
        		//remove '-' with the exception of the '-' after time info
        		 int dateDelimiter = date.indexOf("T");
        		 if( dateDelimiter > -1 ){
        		  date = date.substring( 0, dateDelimiter).replace("-","") + date.substring( dateDelimiter);
        		 }
        		 
        		 //remove W3C T symbol between date and time and separators
        		 date = date.replace("T","").replace("Z","").replace(".","").replace(":", "");
                for (SimpleDateFormat sdf : DateUtils.getW3CDateFormatLst()) {            
                   try{
                	   return sdf.parse(date);
                   }catch (ParseException e) {
   						//Try next date format
   					}                      
                }
                throw new EAIConverterException(FileConfigurationUtils.getMessage("error.dateconversion", date));				
            }
        };
    }
	
	public static final IConverter<String> getDateToStringConverter(){
		return new ConverterAdapter<String>(){
			public String convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public String convert (Object obj, Object... elementTitle){
				if( obj == null){
					return null;
				}
			    return DateUtils.getW3CDateFormat().format(obj);
			}
		};
	}
	
	public static final IConverter<XMLGregorianCalendar> getDateToXMLGregorianConverter(){
		return new ConverterAdapter<XMLGregorianCalendar>(){
			public XMLGregorianCalendar convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public XMLGregorianCalendar convert (Object obj, Object... elementTitle){
				if( obj == null){
					return null;
				}
				if(! (obj instanceof Date)){
					throw new EAIConverterException("Date object required!");	
				}
				
				GregorianCalendar calendar = new GregorianCalendar();
				calendar.setTime((Date)obj);
				try {
					return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
				} catch (DatatypeConfigurationException e) {
					throw new EAIConverterException(e);
				}
			}
		};
	}
	
	public static final IConverter<Timestamp> getStringToTimestampConverter() {
        return new ConverterAdapter<Timestamp>() {
        	public Timestamp convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public Timestamp convert (Object obj, Object... elementTitle){
            	if( obj == null){
            		return null;
            	}
            	return new Timestamp( getStringToDateConverter().convert( obj ).getTime() );
            }
        };
    }
	
	public static final IConverter<String> getTimestampToStringConverterConverter() {
        return new ConverterAdapter<String>() {
        	public String convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public String convert (Object obj, Object... elementTitle){
				return DateUtils.getW3CDateFormat().format(obj);
            }
        };
    }
	
	public static final IConverter<String> getBooleanToStringConverter(){
		return new ConverterAdapter<String>(){
			public String convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public String convert (Object obj, Object... elementTitle){
				if( obj == null){
					return null;
				}
				return Boolean.toString((Boolean)obj);
			}
		};
	}
	
	public static final IConverter<Boolean> getStringToBooleanConverter(){
		return new ConverterAdapter<Boolean>(){
			public Boolean convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public Boolean convert (Object obj, Object... elementTitle){
				if( obj == null){
					return null;
				}
				return Boolean.valueOf(obj.toString());
			}
		};
	}
	
	public static final IConverter<String> getDoubleToStringConverter(){
		return getStringConverter();
	}
	
	public static final IConverter<Double> getStringToDoubleConverter(){
		return new ConverterAdapter<Double>(){
			public Double convert (Object obj){
				return this.convert(obj, EAIConstants.EMPTY_STRING);
			}
			public Double convert (Object obj, Object... elementTitle){
				if( StringUtils.isNullOrEmpty(obj) ){
					return null;
				}
				try {
					return Double.valueOf(((String) obj).trim());
				} catch (NumberFormatException nfe) {
					throw new EAIConverterException(nfe);				
				}
			}
		};
	}
	/* END - CONVERT ACTIONS */
}
