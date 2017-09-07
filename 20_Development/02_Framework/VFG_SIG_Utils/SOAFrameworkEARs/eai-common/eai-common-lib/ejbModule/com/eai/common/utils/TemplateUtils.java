package com.eai.common.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.eai.common.template.DocumentContentType;
import com.eai.common.entities.TemplateNode;
import com.eai.common.exception.EAIException;
import com.eai.common.exception.TemplateException;

public class TemplateUtils {
	private static final String LIST_ATTRIBUTE = "list";
	
	private TemplateUtils(){
	}
	
	public static final DocumentContentType getRTFDocumentContentType(String filePath){
		return new DocumentContentType( 
				String.format(DocumentContentType.CONTENT_ID, "RTF", filePath),
				DocumentContentType.RTF_DOCUMENT_CONTENT_TYPE,
				String.format(DocumentContentType.GENERAL_DOCUMENT_CONTENT_DISPOSITION_FORMAT, filePath)
			);
	}
	
	public static final DocumentContentType getExcelDocumentContentType(String filePath){
		return new DocumentContentType( 
				String.format(DocumentContentType.CONTENT_ID, "EXCEL", filePath),
				DocumentContentType.XLS_DOCUMENT_CONTENT_TYPE,
				String.format(DocumentContentType.GENERAL_DOCUMENT_CONTENT_DISPOSITION_FORMAT, filePath)
			);
	}
	
	public static final DocumentContentType getCSVDocumentContentType(String filePath){
		return new DocumentContentType( 
				String.format(DocumentContentType.CONTENT_ID, "CSV", filePath),
				DocumentContentType.CSV_DOCUMENT_CONTENT_TYPE,
				String.format(DocumentContentType.GENERAL_DOCUMENT_CONTENT_DISPOSITION_FORMAT, filePath)
			);
	}
	
	/*
	 * START - Common template methods load
	 */
	/**
	 * Load TemplateNode definition from an XML string
	 * 
	 * @param xml - String with the XML content
	 * @return TemplateNode
	 */
	public static final TemplateNode getTemplateFields(String xml, String encoding){
		try {
			Document doc = XMLUtils.getDocFromXML(xml, encoding);
			return getTemplateNode( doc.getFirstChild() );//top result is always a map (root) element
		} catch (Exception e) {
			if( e instanceof EAIException ){
				throw (EAIException)e;
			}
			throw new TemplateException(e);
		}
	}
	
	/**
	 * Get the TemplateNode which can be converter into a Map for template engine usage
	 * 
	 * @param node - current node being process (used for recurring process)
	 * @return TemplateNode
	 */
	private static final TemplateNode getTemplateNode(Node node){
    	if( node.getNodeType() != Node.ELEMENT_NODE || StringUtils.isNullOrEmpty( node.getChildNodes() ) ){//if this is an end element (text) break the cycle
			return null;
		}
		
   		//start current node creation
   		TemplateNode currentNode = new TemplateNode( node.getNodeName(), node.getAttributes().getNamedItem(LIST_ATTRIBUTE) == null ? false : true ); 
   		
		//prepare the subNodes reference
		for(int i=0; i < node.getChildNodes().getLength(); i++) {
			if( node.getChildNodes().item(i).getNodeType() == Node.TEXT_NODE ){
				currentNode.concatTextValue( node.getChildNodes().item(i).getNodeValue() );
			}else{
				TemplateNode childNode = getTemplateNode(node.getChildNodes().item(i));
				currentNode.mergeChildTemplateNode(childNode);
			}
		}
		
		return currentNode;
	}
	/*
	 * END - Common template methods load
	 */
}
