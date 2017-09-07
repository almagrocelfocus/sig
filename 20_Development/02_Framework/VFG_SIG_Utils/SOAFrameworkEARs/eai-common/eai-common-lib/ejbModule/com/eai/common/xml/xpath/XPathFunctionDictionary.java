package com.eai.common.xml.xpath;

import java.io.Serializable;
import java.util.List;

import javax.xml.xpath.XPathFunctionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathFunctionDictionary implements Serializable {
	private static final long serialVersionUID = -8589808662741137638L;
	private static final XPathFunctionDictionary _this = new XPathFunctionDictionary();
	
	private XPathFunctionDictionary(){
	}
	
	public static XPathFunctionDictionary getInstance(){
		return _this;
	}
	
	@SuppressWarnings({"rawtypes"})
	public static final IXPathFunction testfunction(){
		return new XPathFunctionAdapter(){
			public Object evaluate(List args) throws XPathFunctionException {
				String result = "";
				NodeList nodeList = (NodeList) args.get(1);
				for( int i = 0; i < nodeList.getLength(); i++ ){
					Node no = nodeList.item(i);
					result += " -- "+no.getNodeValue();
				}
				return args.get(0) + result;
			}
		};
	}
}
