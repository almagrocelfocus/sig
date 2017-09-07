package com.eai.common.converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eai.common.entities.EAIEnums.ConverterPrimitiveTypes;
import com.eai.common.entities.EAIEnums.PrimitiveTypes;
import com.eai.common.exception.EAIConverterException;
import com.eai.common.exception.EAIDataTransformationException;
import com.eai.common.exception.EAIException;
import com.eai.common.utils.EAILogger;
import com.eai.common.utils.EAIReflectionUtils;
import com.eai.common.utils.StringUtils;

public class GenericDataConverter {
	
	private GenericDataConverter(){
	}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface IgnoreConversion {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T clone(T obj) {
		if(obj == null){
			return null;
		}
		return (T) getUIObject(obj.getClass(), obj);
	}

	public static <T> List<T> getUIObject(Class<T> targetClass, List<?> sourceObjLst) {
		return getUIObject(targetClass, sourceObjLst, null, null);
	}

	public static <T> List<T> getUIObject(Class<T> targetClass, List<?> sourceObjLst, Map<String, String> propertiesMap, Map<String, Map<String, String>> exceptionsMap) {
		List<T> result = new ArrayList<T>();
		if(sourceObjLst != null){
			for(Object sourceObj : sourceObjLst){
				result.add(getUIObject(targetClass, sourceObj, propertiesMap, exceptionsMap));
			}
		}
		return result;
	}

	public static <T> T getUIObject(Class<T> targetClass, Object sourceObjLst) {
		return getUIObject(targetClass, sourceObjLst, null, null);
	}

	public static <T> T getUIObject(Class<T> targetClass, Object sourceObj, Map<String, String> propertiesMap, Map<String, Map<String, String>> exceptionsMap) {
		Map<Integer, Integer> methodsInPojo = new HashMap<Integer, Integer>();
		T result = getUIObject(targetClass, sourceObj, true, propertiesMap, exceptionsMap);
		result = PostConvertTransformation.executePostTransformation(result, methodsInPojo);
		return result;
	}

	@SuppressWarnings("unchecked")
	private static <T> T getUIObject(Class<T> targetClass, Object sourceObj, boolean internal, Map<String, String> propertiesMap, Map<String, Map<String, String>> exceptionsMap) {
		if(sourceObj == null){
			return null;
		}
		
		Object targetObj = null;
		try{
			targetObj = getUIObjectNewInstance(targetClass);

			// primitive transformation to set the base value with the original
			// information
			if(PrimitiveTypes.isPrimitive(targetObj)){
				targetObj = setPrimitiveTypesUI(sourceObj, targetObj);
			}else{// not primitive transformation - loop through data
				for(Method targetGetter : targetClass.getMethods()){
					if(EAIReflectionUtils.isGetter(targetGetter)){
						if(EAIReflectionUtils.existsAnnotation(IgnoreConversion.class, targetGetter)){
							continue;//parent class skip this to prevent overflow
						}
						String targetSetter = EAIReflectionUtils.getSetterName(targetGetter);
						String[] sourceGetterLst = propertiesMap != null && propertiesMap.get(targetSetter) != null ? 
								TransformationMap.getGetterLst(propertiesMap.get(targetSetter)) : new String[]{targetGetter.getName()};
						
						// Check if an exception getter exists
						String exceptionSourceGetter = null;
						if(exceptionsMap != null){
							Map<String, String> exceptionMap = exceptionsMap.get(sourceObj.getClass().getName());
							if(exceptionMap != null){
								exceptionSourceGetter = exceptionMap.get(targetSetter);
							}
						}
						boolean isList = targetGetter.getReturnType().isArray() || Collection.class.isAssignableFrom(targetGetter.getReturnType());
						if(isList && sourceGetterLst == null && exceptionSourceGetter == null){
							continue;//no properties - if we are not a list and we automatically create the object
						}
						
						try{
							// support for multiple setters and drill down through multiple opearions
							boolean breakCycle = false;
							for(String sourceGetter : sourceGetterLst){
								breakCycle = false;
								Method sourceGetterMethod = EAIReflectionUtils.getGetterMethod(sourceObj, sourceGetter);
								if(sourceGetterMethod != null && EAIReflectionUtils.existsAnnotation(IgnoreConversion.class, sourceGetterMethod)){
									continue;//parent class skip this to prevent overflow
								}
								if(isList){//colllection behaviour
									Object dataSourceObj = sourceObj;

									// SET property [EXCEPTION]
									dataSourceObj = getDataObjectFromGetter(sourceObj, exceptionSourceGetter != null ? exceptionSourceGetter : sourceGetter);
									if(dataSourceObj != null){
										// Obtain field name associated with
										// TARGETSETTER
										boolean isArray = targetGetter.getReturnType().isArray();
										Class<?> listClassType = (Class<?>) (isArray ? targetGetter.getReturnType().getComponentType() : EAIReflectionUtils.getReturnTypeClass(targetGetter));
										if(listClassType != null){
											List<Object> targetLst = new ArrayList<Object>();
											//source
											if(dataSourceObj.getClass().isArray()){
												for(Object objectWS : (Object[])dataSourceObj){
													targetLst.add(getUIObject((Class<?>) listClassType, objectWS, true, propertiesMap, exceptionsMap));
												}	
											}else{
												for(Object objectWS : (Collection<?>)dataSourceObj){
													targetLst.add(getUIObject((Class<?>) listClassType, objectWS, true, propertiesMap, exceptionsMap));
												}
											}
											//target
											if(isArray){
												T[] targetLstArray = (T[]) Array.newInstance(listClassType, targetLst.size());
												breakCycle |= EAIReflectionUtils.safeSetterInvoke(targetObj, targetSetter, targetLst.toArray(targetLstArray));
											}else{
												breakCycle |= EAIReflectionUtils.safeSetterInvoke(targetObj, targetSetter, targetLst);
											}
										}
									}
								}else{
									if(!StringUtils.isNullOrEmpty(sourceGetter) || !StringUtils.isNullOrEmpty(exceptionSourceGetter)){
										Object dataSourceObj = sourceObj;
										if(sourceGetter != null){
											dataSourceObj = getDataObjectFromGetter(dataSourceObj, sourceGetter);
										}

										// SET property

										// SET property [EXCEPTION]
										if(exceptionSourceGetter != null){
											dataSourceObj = getDataObjectFromGetter(sourceObj, exceptionSourceGetter);
											breakCycle |= EAIReflectionUtils.safeSetterInvoke(targetObj, targetSetter,
													getUIObject(targetGetter.getReturnType(), dataSourceObj, true, propertiesMap, exceptionsMap));
										}else{
											breakCycle |= EAIReflectionUtils.safeSetterInvoke(targetObj, targetSetter,
													getUIObject(targetGetter.getReturnType(), dataSourceObj, true, propertiesMap, exceptionsMap));
										}
									}else{
										Object leafObject = getUIObjectNewInstance(targetGetter.getReturnType(), targetObj, targetGetter.getName());
										if(leafObject != null){
											EAIReflectionUtils.safeSetterInvoke(targetObj, targetSetter, getUIObject(leafObject.getClass(), sourceObj, true, propertiesMap, exceptionsMap));
											breakCycle |= true;
										}
									}
								}
								// for exception we just execute for the first
								// setter which is override with exception info
								if(breakCycle){
									break;// break for cycle
								}
							}
						}catch(EAIConverterException e){
							throw new EAIDataTransformationException("[DT]", "Error transforming for setter: " + targetSetter, EAIException.getStackTrace(e));
						}catch(EAIException e){
							throw e;
						}catch(Throwable e){
							throw new EAIDataTransformationException("[DT]", "Error transforming for setter: " + targetSetter, EAIException.getStackTrace(e));
						}
					}
				}
			}
		}catch(EAIConverterException e){
			String sourceValue = StringUtils.toString(sourceObj);
			throw new EAIConverterException(sourceValue);
		}catch(EAIException e){
			throw e;
		}catch(Throwable e){
			throw new EAIDataTransformationException(e);
		}
		return (T) targetObj;
	}

	/*
	 * PRIVATE METHODS
	 */
	private static Object getDataObjectFromGetter(Object dataSourceObj, String sourceGetter) {
		return EAIReflectionUtils.getDataObjectFromGetter(dataSourceObj, sourceGetter);
	}

	private static Object setPrimitiveTypesUI(Object sourceObj, Object targetObj) {
		if(sourceObj == null || targetObj == null){
			return null;
		}
		if(ConverterPrimitiveTypes.UNKNOWN.equals(ConverterPrimitiveTypes.valueOfEnum(sourceObj.getClass()))){
			return null;
		}

		ConverterPrimitiveTypes converter = ConverterPrimitiveTypes.valueOfEnum(targetObj.getClass());
		if(converter.getToConverter() == null || targetObj.getClass().getName().equals(sourceObj.getClass().getName())){
			Object data = sourceObj;
			// If WS inputs is STRING an extra validation is needed
			if(sourceObj instanceof String && StringUtils.isNullOrEmpty((String) sourceObj)){
				data = null;
			}
			targetObj = data;
		}else{
			targetObj = converter.getToConverter().convert(sourceObj);
		}
		return targetObj;
	}

	private static Object getUIObjectNewInstance(Class<?> targetClass) {
		return getUIObjectNewInstance(targetClass, null, null);
	}

	private static Object getUIObjectNewInstance(Class<?> targetClass, Object currentObj, String setterName) {
		if(targetClass.isEnum()){
			// NBLogger.error(String.format("Ignoring for class = %1$s - Setter %2$s",
			// currentClass, setterName));
			return null;// cannot instantiate this kind of objects - should be
						// defined specific setter through object itself
		}
		try{
			if(PrimitiveTypes.isPrimitiveClass(targetClass)){
				return PrimitiveTypes.newPrimitiveInstance(targetClass);
				/* END - Special constructor cases */
			}else{
				/* START - General case */
				return targetClass.newInstance();
				/* END - General case */
			}
		}catch(EAIException e){
			String currentClass = currentObj == null ? null : currentObj.getClass().getName();
			EAILogger.error(String.format("Class = %1$s - Setter %2$s %n%3$s", currentClass, setterName, EAIException.getStackTrace(e)));
		}catch(Throwable e){
			String currentClass = currentObj == null ? null : currentObj.getClass().getName();
			EAILogger.error(String.format("Class = %1$s - Setter %2$s %n%3$s", currentClass, setterName, EAIException.getStackTrace(e)));
		}
		/* END - General case */
		return null;// Impossible to instantiate
	}

	public static Object safeGetterInvokeToExternal(Object baseObj, String name, Object... args) {
		Object result = EAIReflectionUtils.safeGetterInvoke(baseObj, name, args);
		if(StringUtils.isNullOrEmpty(result)){
			return null;
		}
		ConverterPrimitiveTypes converterPrimitiveType = ConverterPrimitiveTypes.valueOfEnum(result.getClass());
		if(converterPrimitiveType != null && converterPrimitiveType.getExternalConverter() != null){
			result = converterPrimitiveType.getExternalConverter().convert(result);
		}
		return result;
	}
}
