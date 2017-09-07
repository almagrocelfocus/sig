package com.eai.common.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eai.common.EAIConstants;

import com.eai.common.utils.ListUtils;
import com.eai.common.utils.StringUtils;

public class TemplateNode {
	private static final String TEXT_VALUE_CONCAT_FORMAT = "%1$s %2$s"; 
	private boolean isList = false;
	private Map<String, Object> innerNodes = null;
	private List<TemplateNode> innerNodesLst = null;
	private String textValue = EAIConstants.EMPTY_STRING;
	private String nodeName = null;
	
	/*
	 * START - CONSTRUCTORS
	 */
	public TemplateNode(String nodeName, boolean isList){
		this(nodeName, isList, null);
	}
	
	public TemplateNode(String nodeName, boolean isList, Map<String, Object> innerNodes){
		this(nodeName, isList, innerNodes, null);
	}
	
	public TemplateNode(String nodeName, boolean isList, Map<String, Object> innerNodes, List<TemplateNode> innerNodesLst){
		setNodeName(nodeName);
		setList(isList);
		setInnerNodes(innerNodes);
		setInnerNodesLst(innerNodesLst);
	}
	/*
	 * END - CONSTRUCTORS
	 */
	
	/*
	 * START - METHODS
	 */
	public void mergeChildTemplateNode(TemplateNode childNode){
		if( childNode == null ){
			return;
		}
		
		if( childNode.isList() ){//add node list
			getInnerNodesLst().add(childNode);
		}else{//not a list - merge inner nodes
			if( !StringUtils.isNullOrEmpty( childNode.getTextValue() ) ){//inner child text - should be mapped to current element
				getInnerNodes().put(childNode.getNodeName(), childNode.getTextValue());
			}
			
			if( !ListUtils.isNullOrEmpty( childNode.getInnerNodes() ) ){
				getInnerNodes().put( childNode.getNodeName(), childNode.getInnerNodes());
			}
			
			if( !ListUtils.isNullOrEmpty( childNode.getInnerNodesLst() ) ){
				getInnerNodes().put( childNode.getNodeName(), childNode.convertToTemplateMap());
			}
		}
	}
	
	public void concatTextValue(String value){
		value = value.replaceAll("\\s*$", "");//remove whitespace for concatenation
		if( StringUtils.isNullOrEmpty( getTextValue() ) ){
			setTextValue( value);
		}else{
			if( !StringUtils.isNullOrEmpty( value ) ){
				setTextValue( String.format(TEXT_VALUE_CONCAT_FORMAT, getTextValue(), value));
			}
		}
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	public Map<String,Object> convertToTemplateMap(){
		Map<String, Object> result = new HashMap<String, Object>();
		if( !ListUtils.isNullOrEmpty(innerNodes) ){
			//result.putAll( TemplateNode.convertNodeMapToTemplateMap( innerNodes ) );
			result.putAll( innerNodes );
		}
		if( !ListUtils.isNullOrEmpty(innerNodesLst) ){
			for( TemplateNode childNode : innerNodesLst ){
				List childLst = (List) result.get( childNode.getNodeName() );
				if( childLst == null ){
					childLst = new ArrayList<Object>();
					result.put(childNode.getNodeName(), childLst);
				}
				childLst.add( childNode.convertToTemplateMap() );
			}
		}
		return result;
	}
	/*
	 * END - METHODS
	 */
	
	/*
	 * START - GETTERS / SETTERS
	 */
	public void setInnerNodes(Map<String, Object> innerNodes) {
		this.innerNodes = innerNodes;
	}
	public Map<String, Object> getInnerNodes() {
		if( innerNodes == null ){
			innerNodes = new HashMap<String, Object>();
		}
		return innerNodes;
	}
	
	public void setInnerNodesLst(List<TemplateNode> innerNodesLst) {
		this.innerNodesLst = innerNodesLst;
	}
	public List<TemplateNode> getInnerNodesLst() {
		if( innerNodesLst == null ){
			innerNodesLst = new ArrayList<TemplateNode>();
		}
		return innerNodesLst;
	}

	public void setList(boolean isList) {
		this.isList = isList;
	}
	public boolean isList() {
		return isList;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	public String getTextValue() {
		return textValue;
	}
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeName() {
		return nodeName;
	}
	/*
	 * END - GETTERS / SETTERS
	 */
	@SuppressWarnings({"rawtypes"})
	public String toString(){
		StringBuilder result = new StringBuilder("<"+getNodeName()+">");
		result.append(getTextValue());
		if( !ListUtils.isNullOrEmpty(innerNodes) ){
			for( String key : innerNodes.keySet() ){
				if( innerNodes.get(key) instanceof Map ){
					result.append( "<"+key+">" );
					result.append( ListUtils.print((Map)innerNodes.get(key)) );
					result.append( "</"+key+">" );
				}else{
					result.append( "<"+key+">"+innerNodes.get(key)+"</"+key+">" );
				}
			}
		}
		if( !ListUtils.isNullOrEmpty(innerNodesLst) ){
			for( TemplateNode node : innerNodesLst ){
				result.append( node.toString() );
			}
		}
		result.append("</"+getNodeName()+">");
		return result.toString();
	}
	
	
}
