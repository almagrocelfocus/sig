package com.eai.common.entities;

import java.math.BigDecimal;

import com.eai.common.converter.IConverter;
import com.eai.common.converter.ConverterFactory;
import com.eai.common.exception.EAIDataTransformationException;
import com.eai.common.utils.EAILogger;

public final class EAIEnums {
	private static final String ENUM_NOT_FOUND = "Enumeration value not found: %1$s";
	
	public enum PrimitiveTypes {
		UNKNOWN ("UNKNOWN","UNKNOWN"),
		STRING("String",String.class.getName()),
		BOOLEAN("Boolean", Boolean.class.getName()),
		BOOLEAN_PRIMITIVE("boolean", "boolean"),
		CHAR("Character",Character.class.getName()),
		CHAR_PRIMITIVE("char","char"),
		BYTE("Byte",Byte.class.getName()),
		BYTE_PRIMITIVE("byte","byte"),
		SHORT("Short",Short.class.getName()),
		SHORT_PRIMITIVE("short","short"),
		INT("Integer",Integer.class.getName()),
		INT_PRIMITIVE("int","int"),
		LONG("Long",Long.class.getName()),
		LONG_PRIMITIVE("long","long"),
		FLOAT("Float",Float.class.getName()),
		FLOAT_PRIMITIVE("float","float"),
		DOUBLE("Double",Double.class.getName()),
		DOUBLE_PRIMITIVE("double","double"),
		DATE("java.util.Date",java.util.Date.class.getName()),
		BIG_DECIMAL("BigDecimal",java.math.BigDecimal.class.getName());

		/* Constructor */
		private final String name;
		private final String wrapperName;
		private PrimitiveTypes(String name, String wrapperName) {
			this.name = name;
			this.wrapperName = wrapperName;
		}

		@Override
		public String toString() {
			return name;
		}
		
		public String getWrapperName() {
			return wrapperName;
		}
		
		public static PrimitiveTypes valueOfEnum (String value){
			for( PrimitiveTypes enumVal : PrimitiveTypes.values()){
				if(enumVal.getWrapperName().equals(value) || enumVal.toString().equals(value)){
					return enumVal;
				}
			}
			if( value != null ){
				EAILogger.error(String.format(ENUM_NOT_FOUND, value));
			}
			return UNKNOWN;
		}
		
		public static boolean isPrimitive(Object obj){
			if( obj == null){
				return true;
			}			
			return isPrimitiveClass(obj.getClass()); 
		}
		
		public static boolean isBaseMethod(String methodName){
			if( methodName == null){
				return true;
			}			
			for( String method : getBaseObjectMethods() ){
				if( methodName.equals( method ) ){
					return true;
				}
			}
			
			return false;
		}
		
		public static final String[] getBaseObjectMethods(){
			return new String[]{"getClass"};
		}

		public static boolean isPrimitiveClass(Class<?> className){
			for( PrimitiveTypes enumVal : PrimitiveTypes.values()){
				if(enumVal.getWrapperName().equals(className.getName()) || enumVal.toString().equals(className.getName())){
					return true;
				}
			}
			return false; 
		}
		
		
		public static Object newPrimitiveInstance(Class<?> className){
			switch (valueOfEnum(className.getName())) {
			case BOOLEAN:
				return new Boolean(null);
			case BYTE:
				return new Byte(null);
			case CHAR:
			case CHAR_PRIMITIVE:
				return Character.COMBINING_SPACING_MARK;
			case SHORT:
			case SHORT_PRIMITIVE:
				return Short.MIN_VALUE;
			case INT:
			case INT_PRIMITIVE:
				return Integer.MIN_VALUE; 
			case LONG:
			case LONG_PRIMITIVE:
				return Long.MIN_VALUE;
			case FLOAT:
			case FLOAT_PRIMITIVE:
				return Float.MIN_VALUE;
			case DOUBLE:
			case DOUBLE_PRIMITIVE:
				return Double.MIN_VALUE;
			case BIG_DECIMAL:
				return BigDecimal.ONE;
			case BOOLEAN_PRIMITIVE:
				return false;//cannot identify return dummy value
			default:
				try {
					return className.newInstance();
				} catch (Exception e) {
					throw new EAIDataTransformationException(e);
				}
			} 
		}
		
	}
	
	public enum ConverterPrimitiveTypes {
		UNKNOWN (PrimitiveTypes.UNKNOWN, ConverterFactory.getBooleanToStringConverter(), ConverterFactory.getStringToBooleanConverter()),
		BOOLEAN( PrimitiveTypes.BOOLEAN , ConverterFactory.getBooleanToStringConverter(), ConverterFactory.getStringToBooleanConverter()),
		BOOLEAN_PRIMITIVE( PrimitiveTypes.BOOLEAN_PRIMITIVE , ConverterFactory.getBooleanToStringConverter(), ConverterFactory.getStringToBooleanConverter()),
		CHAR( PrimitiveTypes.CHAR , null, null),
		CHAR_PRIMITIVE( PrimitiveTypes.CHAR_PRIMITIVE, null, null),
		BYTE( PrimitiveTypes.BYTE, null, null),
		SHORT( PrimitiveTypes.SHORT, null, null),
		SHORT_PRIMITIVE( PrimitiveTypes.SHORT_PRIMITIVE, null, null),
		INT( PrimitiveTypes.INT, ConverterFactory.getIntegerToStringConverter(), ConverterFactory.getStringToIntegerConverter()),
		INT_PRIMITIVE( PrimitiveTypes.INT_PRIMITIVE, ConverterFactory.getIntegerToStringConverter(), ConverterFactory.getStringToIntegerConverter()),
		LONG( PrimitiveTypes.LONG, ConverterFactory.getLongToStringConverter(), ConverterFactory.getStringToLongConverter()),
		LONG_PRIMITIVE( PrimitiveTypes.LONG_PRIMITIVE, ConverterFactory.getLongToStringConverter(), ConverterFactory.getStringToLongConverter()),
		FLOAT(PrimitiveTypes.FLOAT, null, null),
		FLOAT_PRIMITIVE(PrimitiveTypes.FLOAT_PRIMITIVE, null, null),
		DOUBLE(PrimitiveTypes.DOUBLE, ConverterFactory.getDoubleToStringConverter(), ConverterFactory.getStringToDoubleConverter()),
		DOUBLE_PRIMITIVE(PrimitiveTypes.DOUBLE_PRIMITIVE, ConverterFactory.getDoubleToStringConverter(), ConverterFactory.getStringToDoubleConverter()),
		DATE(PrimitiveTypes.DATE, ConverterFactory.getDateToStringConverter(), ConverterFactory.getStringToDateConverter(), ConverterFactory.getStringToDateConverter()),
		STRING(PrimitiveTypes.STRING, ConverterFactory.getStringConverter(), ConverterFactory.getStringConverter())
		;

		/* Constructor */
		private final PrimitiveTypes name;
		private final IConverter<?> fromConverter;
		private final IConverter<?> toConverter;
		private final IConverter<?> externalConverter;
		
		private <T> ConverterPrimitiveTypes(PrimitiveTypes name, IConverter<?> fromConverter, IConverter<?> toConverter, IConverter<?> externalConverter ){
			this.name = name;
			this.fromConverter = fromConverter;
			this.toConverter = toConverter;
			this.externalConverter = externalConverter;
		}
		private <T> ConverterPrimitiveTypes(PrimitiveTypes name, IConverter<?> fromConverter, IConverter<?> toConverter){
			this( name, fromConverter, toConverter, null );
		}

		@SuppressWarnings("unchecked")
		public <T> IConverter<T> getFromConverter() {
			return (IConverter<T>) fromConverter;
		}
		
		@SuppressWarnings("unchecked")
		public <T> IConverter<T> getToConverter() {
			return (IConverter<T>) toConverter;
		}
		
		@SuppressWarnings("unchecked")
		public <T> IConverter<T> getExternalConverter() {
			return (IConverter<T>) externalConverter;
		}
		
		@Override
		public String toString() {
			return name.getWrapperName();
		}		
		
		public static <T> ConverterPrimitiveTypes valueOfEnum (Class<T> className){
			for( ConverterPrimitiveTypes enumVal : ConverterPrimitiveTypes.values()){
				if(enumVal.toString().equalsIgnoreCase(className.getName())){
					return enumVal;
				}
			}
			return UNKNOWN;
		}
	}
}
