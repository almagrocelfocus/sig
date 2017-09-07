package com.eai.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.eai.common.converter.IConverter;
import com.eai.common.entities.EAIEnums.PrimitiveTypes;
import com.eai.common.exception.EAIDataTransformationException;
import com.eai.common.exception.EAIException;

public class EAIReflectionUtils {
	
	private EAIReflectionUtils(){
	}
	
	private static final String GETTER = "get";
	private static final String SETTER = "set";
	
	public static final boolean isGetter(Method method){
		return method != null && method.getName().startsWith(GETTER) && method.getParameterTypes().length == 0 
				&& method.getName().length() > 3 && method.getReturnType() != null && containsMethodName(method.getDeclaringClass(), getSetterName(method) );
	}
	public static final boolean containsMethodName(Class<?> clazz, String methodName){
		if(methodName == null){
			return false;
		}
		for(Method method : clazz.getMethods()){
			if(methodName.equals(method.getName())){
				return true;
			}
		}
		return false;
	}
	public static final String getSetterName(Method method){
		return SETTER + method.getName().substring(3);
	}
	
	public static final Method getGetterMethod(Object obj, String getterName){
		if(obj == null || getterName == null){
			return null;
		}
		for(Method method : obj.getClass().getMethods()){
			if(getterName.equals(method.getName()) && isGetter(method)){
				return method;
			}
		}
		return null;
	}
	public static final Class<?> getGetterClass(Object obj, String getterName){
		Method method = getGetterMethod(obj, getterName);
		return method == null ? null : method.getReturnType();
	}
	
	public static final Object safeGetMethodWithArg(Object baseObj, String name, Object... args) {
		try{
	        Method methodFinal;
	        if(baseObj == null){
	        	return null;
	        }
	        Class<?> baseClass = baseObj.getClass();
	        int parametersNumber = args != null ? args.length : 0;
	        methodFinal = null;
	        Method amethod[] = baseClass.getMethods();
	        int j = amethod.length;
	        for(int i = 0; i < j; i++){
	            Method method = amethod[i];
	            if(method.getName().compareTo(name) == 0 && (method.getParameterTypes() != null && method.getParameterTypes().length == parametersNumber || method.getParameterTypes() == null && parametersNumber == 0)){
	                methodFinal = method;
	            }
	        }
	
	        if(methodFinal == null){
	        	return null;
	        }
	        
	        return methodFinal.invoke(baseObj, args);
		}catch (Exception e) {
        	throw new EAIDataTransformationException(e);
		}
    }

    public static final Method safeGetMethod(Object baseObj, String name, Object...  args){
        if(baseObj != null){
            Class<?> baseClass = baseObj.getClass();
            Method amethod[] = baseClass.getMethods();
            int j = amethod.length;
            for(int i = 0; i < j; i++){
                Method method = amethod[i];
                if(method.getName().compareTo(name) == 0 && (method.getParameterTypes() == null || method.getParameterTypes().length == 0)){
                    return method;
                }
            }

        }
        return null;
    }

    public static final Object safeGetterInvoke(Object baseObj, String name, Object...  args) {
    	try{
    		Method method = safeGetMethod(baseObj, name, args);
    		if(method == null){
    			return null;
    		}
    		return method.invoke(baseObj, args);
    	}catch (Exception e) {
    		throw new EAIDataTransformationException(e);
		}
    }
    public static final Object safeGetterInvoke(Method method, Object baseObj, Object...  args) {
    	try{
    		if(method == null){
    			return null;
    		}
    		return method.invoke(baseObj, args);
    	}catch (Exception e) {
    		EAILogger.error(e);
    		return null;
		}
    }

    public static final boolean safeSetterInvoke(Object target, String targetName, Object data){
        if(target == null || data == null){
        	return false;
        }
        try {
        	String parameterTypeName = data.getClass().getName();
            Class<?> baseClass = target.getClass();
            for(Method method : baseClass.getMethods()){
                if(method.getName().equals(targetName)){
                    if("java.util.ArrayList".equals(data.getClass().getName())){
                    	parameterTypeName = "java.util.List";
                    }
                    if(method.getParameterTypes().length == 1 && parameterTypeName.equals(getPrimitiveWrapperName(method.getParameterTypes()[0]))){//for cases where we have a primitive set type
                    	safeSetterInvokeNullable(target, method, data);
                    }
                }
            }

        }catch(EAIException ex){
            EAILogger.error(new Object[] {
                ex
            });
            throw ex;
        }catch(Throwable ex){
            EAILogger.error(new Object[] {
                ex
            });
            throw new EAIDataTransformationException(ex);
        }
        return true;
    }

    public static final boolean safeSetterInvokeNullable(Object target, Method targetMethod, Object data){
        if(target == null || targetMethod == null){
            return false;
        }
        
        try{
            if(data == null){
                Object dataArray[] = new Object[1];
                targetMethod.invoke(target, dataArray);
            } else{
                targetMethod.invoke(target, new Object[] {
                    data
                });
            }
        }catch(EAIException ex){
            EAILogger.error(new Object[] {
                targetMethod, ex
            });
            throw ex;
        }catch(Throwable ex){
            EAILogger.error(new Object[] {
                targetMethod, ex
            });
            throw new EAIDataTransformationException(ex);
        }
        return true;
    }

    public static final boolean safeSetterInvoke(Object target, String targetName, Object data, IConverter<?> converter){
        Object tData = converter != null ? converter.convert(data, new Object[] { targetName }) : data;
        return safeSetterInvoke(target, targetName, tData);
    }

    public static final boolean safeSetterInvoke(Object target, String targetName, Object source, String sourceName, IConverter<?> converter){
        return safeSetterInvoke(target, targetName, safeGetterInvoke(source, sourceName, new Object[0]), converter);
    }

    public static final Class<?> getBaseClass(Class<?> classObj){
    	try{
	    	if(classObj.isArray()){
	        	return classObj.getComponentType();
	        }
	        return classObj;
    	}catch (Exception e) {
    		throw new EAIDataTransformationException(e);
		}
    }

    public static final String getPrimitiveWrapperName(Class<?> classObj){
        if(classObj.isPrimitive()){
            return com.eai.common.entities.EAIEnums.PrimitiveTypes.valueOfEnum(classObj.getName()).getWrapperName();
        }else{
            return classObj.getName();
        }
    }

    public static final boolean existsBaseGetter(Class<?> classObj, String setterName){
        if(setterName == null || !setterName.startsWith("set")){
            return false;
        }
        setterName = (new StringBuilder("g")).append(setterName.substring(1)).toString();
        Method amethod[] = classObj.getMethods();
        int j = amethod.length;
        for(int i = 0; i < j; i++){
            Method method = amethod[i];
            if(method.getName().equals(setterName) && (method.getParameterTypes() == null || method.getParameterTypes().length == 0)){
                return true;
            }
        }

        return false;
    }

    public static final boolean existsBaseSetter(Class<?> classObj, String getterName){
        if(getterName == null || !getterName.startsWith("get")){
            return false;
        }
        getterName = (new StringBuilder("s")).append(getterName.substring(1)).toString();
        Method amethod[] = classObj.getMethods();
        int j = amethod.length;
        for(int i = 0; i < j; i++){
            Method method = amethod[i];
            if(method.getName().equals(getterName) && method.getParameterTypes() != null && method.getParameterTypes().length == 1){
                return true;
            }
        }

        return false;
    }

    public static final Object getDataObjectFromGetter(Object dataSourceObj, String sourceGetter){
        if(!com.eai.common.entities.EAIEnums.PrimitiveTypes.isPrimitive(dataSourceObj)){
            String sourceGetterLst[] = sourceGetter.contains(".") ? sourceGetter.split("\\.") : (new String[] {
                sourceGetter
            });
            String as[] = sourceGetterLst;
            int j = as.length;
            for(int i = 0; i < j; i++){
                String currGetter = as[i];
                dataSourceObj = safeGetterInvoke(dataSourceObj, currGetter, new Object[0]);
            }

        }
        return dataSourceObj;
    }
    
    @SuppressWarnings("unchecked")
	public static final <T> Class<T> getReturnTypeClass(Method method){
    	Class<T> returnType = (Class<T>) getParameterizedTypeClass(method.getGenericReturnType());
    	if(returnType == null){
    		return (Class<T>) method.getReturnType();
    	}
    	return returnType;
    }
	@SuppressWarnings("unchecked")
	public static final <T> Class<T> getParameterizedTypeClass(Class<?> targetClass){
    	return (Class<T>) getParameterizedTypeClass(targetClass.getGenericSuperclass());
    }
    @SuppressWarnings("unchecked")
	public static final <T> Class<T> getParameterizedTypeClass(Type type){
    	if(type instanceof ParameterizedType){
    		return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
    	}
    	return null;
    }
    
    @SuppressWarnings("unchecked")
	public static final <T> T createNewInstanceFromParameterizedTypeClass(Class<?> targetClass) {
        return (T) createNewInstance(getParameterizedTypeClass(targetClass));
    }
    
    @SuppressWarnings("unchecked")
	public static final <T> T createNewInstanceFromGetter(Object obj, String getterName){
    	if(obj == null || getterName == null){
    		return null;
    	}
    	
    	for(Method method : obj.getClass().getMethods()){
    		if(getterName.equals(method.getName()) && isGetter(method)){
    			return (T) createNewInstance(method.getReturnType());
    		}
    	}
    	return null;
    }
    
	@SuppressWarnings("unchecked")
	public static final <T> T createNewInstance(Class<T> targetClass) {
		if( targetClass == null ){
			EAILogger.debug("Found possible instanciation issue [EMPTY] for " + EAILogger.getParentClassAndLine() );
		} else if( targetClass.isEnum() ){
			EAILogger.debug("Found possible instanciation issue [ENUM] for " + EAILogger.getParentClassAndLine() );
		} else{
			try {
				if(PrimitiveTypes.isPrimitiveClass(targetClass)){
					return (T) PrimitiveTypes.newPrimitiveInstance(targetClass);
				/* END - Special constructor cases */
				}else{
				/* START - General case */
					return (T) targetClass.newInstance();
				/* END - General case */
				}
			}catch (Exception e) {
				EAILogger.debug("Found possible instanciation issue [ERROR] for " + EAILogger.getParentClassAndLine() + " - " + EAIException.getStackTrace(e));
			}
		}
		return null;// END - General case - impossible to instantiate
	}
	
	public static <T extends Annotation> boolean existsAnnotation(Class<T> annotation, Method method){
		for(Annotation currentAnnotation : method.getAnnotations() ){
			if(annotation.equals(currentAnnotation.annotationType())){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T randomGenerator(Class<T> clazz){
		try{
			if(clazz.isEnum()){
				return clazz.getEnumConstants()[0];
			}
			if(clazz.isArray()){
				T[] o = (T[])Array.newInstance(clazz, 2);
				for(int i = 0; i < 2; i++){
					o[i] = (T) randomGenerator(clazz.getComponentType());
				}
				return (T)o;
			}else{
				if(PrimitiveTypes.isPrimitiveClass(clazz)){
					return (T) PrimitiveTypes.newPrimitiveInstance(clazz);
				}
				T val = clazz.newInstance();
				
				for(Field field : val.getClass().getDeclaredFields()){
					field.setAccessible(true);
					
					if(PrimitiveTypes.isPrimitiveClass(field.getType())){
						Object obj = PrimitiveTypes.newPrimitiveInstance(field.getType());
						if(obj instanceof String){
							obj = UUID.randomUUID().toString();
						}
						field.set(val, obj);
					}else{
						//is list
						if(field.getType().isAssignableFrom(List.class)){
							List lst = new ArrayList();
							field.set(val, lst);
							for(int i = 0; i < 2; i++){
								Class<?> baseClass = EAIReflectionUtils.getParameterizedTypeClass(field.getGenericType());
								lst.add(randomGenerator(baseClass));
							}
						}else{
							field.set(val, randomGenerator(field.getType()));
						}
					}
				}
				return val;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new EAIDataTransformationException(e);
		}
	}
}
