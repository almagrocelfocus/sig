package com.eai.common.converter;

import com.eai.common.EAIConstants;

import com.eai.common.utils.StringUtils;

public abstract class TransformationMap {
	
	private TransformationMap(){
	}
	
	public static final String CURRENT_ELEMENT = "this";
	public static String getCurrentElementName(String element){
		return element.compareTo(CURRENT_ELEMENT) == 0 ? EAIConstants.EMPTY_STRING : element;
	}
	
	public static String[] getGetterLst(String getter){
		String[] getterLst = StringUtils.getStringArray(getter,",");
		if( getterLst.length == 0 ){
			getterLst = new String[]{""};
		}
		return getterLst;
	}
}
