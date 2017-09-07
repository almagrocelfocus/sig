package com.eai.common.xml.xpath;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionResolver;

import com.eai.common.utils.EAIReflectionUtils;

public class EAIXPathFunctionResolver implements XPathFunctionResolver {
	private static EAIXPathFunctionResolver _this = new EAIXPathFunctionResolver();
	private static final List<String> ACCEPTED_PREFIX = new ArrayList<String>(){
		private static final long serialVersionUID = 5777175193045294338L;
		{
			add("nb");
		}
	};
	
	private EAIXPathFunctionResolver(){}
	public static EAIXPathFunctionResolver getInstance(){
		return _this;
	}
	
	/**
	 * Dynamically retrieve custom functions for NB domain
	 * Note: 
	 * 	input size length is supported through function itself!
	 */
	public XPathFunction resolveFunction(QName inputFunctionName, int inputSizeLen) {
		if( inputFunctionName != null && ACCEPTED_PREFIX.contains(inputFunctionName.getNamespaceURI())){
			return (IXPathFunction) EAIReflectionUtils.safeGetterInvoke(XPathFunctionDictionary.getInstance(), inputFunctionName.getLocalPart().toLowerCase());
		}
		return null;
	}

}
