package com.eai.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.eai.common.EAIConstants;


public final class ListUtils {
	
	private ListUtils(){
		
	}
	
	public static final <T> T getObject(T[] objLst, Object value){
		if( StringUtils.isNullOrEmpty(value) || isNullOrEmpty(objLst) ){
			return null;
		}
		for( T val : objLst){
			if( val.equals(value) ){
				return val;
			}
		}	
		return null; 
	}
	public static final <T> T getObject(List<T> objLst, Object value){
		return getObject(objLst, null, value);
	}
	public static final <T> T getObject(List<T> objLst, String getter, Object value){
		if( StringUtils.isNullOrEmpty(value) || isNullOrEmpty(objLst) ){
			return null;
		}
		for( T val : objLst){
			if(getter == null){
				if( val.equals(value) ){
					return val;
				}
			}else {
				Object getterVal = EAIReflectionUtils.safeGetterInvoke(val, getter);
				if(getterVal != null && getterVal.equals(value)){
					return val;
				}
			}
		}	
		return null; 
	}
	
	public static final <T> List<?> getObjectLst(List<T> objLst, String getter){
		List<Object> result = new ArrayList<Object>();
		if( isNullOrEmpty(objLst) || getter == null){
			return result;
		}
		for( Object value : objLst){
			result.add(EAIReflectionUtils.safeGetterInvoke(value, getter));
		}	
		return result; 
	}
	public static final <T> List<T> getObjectLst(List<T> objLst, List<?> valueLst){
		List<T> result = new ArrayList<T>();
		if( StringUtils.isNullOrEmpty(valueLst) || isNullOrEmpty(objLst) ){
			return result;
		}
		for( Object value : valueLst){
			T obj = getObject(objLst, value);
			if( obj != null ){
				result.add(obj);
			}
		}	
		return result; 
	}
	
	public static final <T> List<T> removeObjectLst(List<T> baseLst, List<?> objLst){
		if( StringUtils.isNullOrEmpty(baseLst) || isNullOrEmpty(objLst) ){
			return baseLst;
		}
		for(int i = 0; i < objLst.size(); i++){
			removeObject(baseLst, objLst.get(i));
		}
		return baseLst; 
	}
	public static final <T> List<T> removeObject(List<T> baseLst, Object obj){
		return removeObject(baseLst, null, obj);
	}
	public static final <T> List<T> removeObject(List<T> baseLst, String getter, Object obj){
		if( StringUtils.isNullOrEmpty(baseLst) || StringUtils.isNullOrEmpty(obj) ){
			return baseLst;
		}
		for(int i = 0; i < baseLst.size(); i++){
			Object val = baseLst.get(i);
			if(getter == null){
				if(val != null && val.equals(obj)){
					baseLst.remove(i--);
				}
			}else{
				Object getterVal = EAIReflectionUtils.safeGetterInvoke(val, getter);
				if(getterVal != null && getterVal.equals(obj)){
					baseLst.remove(i--);
				}
			}
		}
		return baseLst; 
	}
	
	
	public static final boolean isNullOrEmpty(List<?> lst){
		if(lst != null && !lst.isEmpty()){
			return false;
		}

		return true;		
	}
	
	public static final boolean isNullOrEmpty(Object[] lst){
		if(lst != null && lst.length>0){
			return false;
		}

		return true;		
	}
	
	public static final boolean isNullOrEmpty(Collection<?> lst){
		if(lst != null && !lst.isEmpty()){
			return false;
		}

		return true;		
	}
	
	public static final boolean isNullOrEmpty(Map<?,?> lst){
		if(lst != null && !lst.isEmpty()){
			return false;
		}

		return true;		
	}

	public static String print( Map<?,?> map ){
		String result = EAIConstants.EMPTY_STRING;
		for( Object key : map.keySet() ){
			Object value = map.get(key);
			if( value instanceof Map ){
				result += print((Map<?,?>)value);
			}else{
				result += "<"+key+">"+value+"</"+key+">";
			}
		}
		return result;
	}
	
	public static String print(Object[] arrayLst) {
		String result = EAIConstants.EMPTY_STRING;
		String split = EAIConstants.EMPTY_STRING;
		if (arrayLst != null) {
			for (Object value : arrayLst) {
				result += split + value;
			}
		}
		return result;
	}
	
	public static String print(List<?> lst) {
		return print(lst,null);
	}
	public static String print(List<?> lst, String getter) {
		String result = EAIConstants.EMPTY_STRING;
		String split = EAIConstants.EMPTY_STRING;
		if (lst != null) {
			for(Object value : lst){
				if(value != null){
					if(getter == null){
						result += split + value.toString();
					}else{
						result += split + EAIReflectionUtils.safeGetterInvoke(value, getter);
					}
				}
				split = "; ";
			}
		}
		return result;
	}
}
