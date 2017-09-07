package com.eai.common.converter;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import com.eai.common.entities.EAIEnums.PrimitiveTypes;
import com.eai.common.utils.ListUtils;
import com.eai.common.utils.EAIReflectionUtils;
import com.eai.common.utils.StringUtils;

public final class PostConvertTransformation {
	
	private PostConvertTransformation(){
	}
	
	public static final <T> T executePostTransformation(T pojo, Map<Integer, Integer> methodsInPojo){
		if( PrimitiveTypes.isPrimitive(pojo) ){
			return pojo;
		}
		
		if( ! StringUtils.isNullOrEmpty( pojo )){
			if(methodsInPojo.containsKey(pojo.hashCode())){
				return pojo;
			}
			
			methodsInPojo.put(pojo.hashCode(), pojo.hashCode());
			
			if( pojo instanceof IPostConvert ){
				((IPostConvert)pojo).transform();
			}
			
			//iterate over lists
			if( pojo instanceof Collection ){
				for( Object value : (Collection<?>) pojo){
					executePostTransformation(value, methodsInPojo);
				}
			} else if( pojo.getClass().getClass().isArray() ){
				for( Object value : (Object[]) pojo){
					executePostTransformation(value, methodsInPojo);
				}	
			//iterate over single objects
			} else for( Method getter1 : pojo.getClass().getMethods() ){
					for( Method getter2 : pojo.getClass().getMethods() ){
						if((getter1.getName().startsWith("get") && getter2.getName().startsWith("set")) || (getter1.getName().startsWith("get") && getter2.getName().startsWith("set"))){
							if( getter1.getName().startsWith("getParent") ){//getParent is used for backwards reference shouldn't execute the post transformations
								continue;
							}
							
							String getterName1 = getter1.getName().substring(3);
							String getterName2 = getter2.getName().substring(3);
							
							if(getterName1.compareTo(getterName2) == 0 && ListUtils.isNullOrEmpty( getter1.getParameterTypes()) && !PrimitiveTypes.isBaseMethod(getter1.getName())){
								executePostTransformation(EAIReflectionUtils.safeGetterInvoke(pojo, getter1.getName()),methodsInPojo );
								break;
							}
						}	
					}
				}
		}
		return pojo;
	}
}
