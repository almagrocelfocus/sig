package com.eai.common.utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import javax.xml.xpath.*;

import org.w3c.dom.*;

import com.eai.common.EAIConstants;
import com.eai.common.exception.XMLHelperException;
import com.eai.common.service.EAIFileConfigurationManager;
import com.eai.common.xml.xpath.EAIXPathFunctionResolver;

public class XMLUtils{
	
	private XMLUtils(){
	}
	
	/* START - GLOBAL STATIC INFORMATION */
    private static DocumentBuilderFactory docFac = DocumentBuilderFactory.newInstance();
	public static DocumentBuilderFactory getDocumentBuilderFactory(){
        return docFac;
    }
	
	private static TransformerFactory transFactory = TransformerFactory.newInstance();
    public static TransformerFactory getTransformerFactory(){
        return transFactory;
    }
    
    private static SchemaFactory schemafactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
    public static SchemaFactory getSchemaFactory(){
        return schemafactory;
    }
    /* END - GLOBAL STATIC INFORMATION */
    
    /* START - CREATE INFORMATION BY THREAD - PREVENT MULTIPLE INSTANCE FOR THE SAME OBJECT */
	private static ThreadLocal<XPathFactory> _localStorageXPATHFactory = new ThreadLocal<XPathFactory>(){
		protected XPathFactory initialValue() {
			System.setProperty(EAIConstants.XML_XPATH_FACTORY, EAIFileConfigurationManager.getDefaultXPATHImpl());
			try{
				XPathFactory currentInstance = XPathFactory.newInstance(EAIConstants.XML_XPATH_FACTORY_SUFFIX);
				currentInstance.setXPathFunctionResolver( EAIXPathFunctionResolver.getInstance() );
				return currentInstance;
			}catch(Exception exception){
	            throw new XMLHelperException(exception);
	        }
		}
	};
	public static XPathFactory getXPathFactory(){
    	return _localStorageXPATHFactory.get();
    }
	
	private static ThreadLocal<XPath> _localStorageXPATH = new ThreadLocal<XPath>(){
		protected XPath initialValue() {
			return getXPathFactory().newXPath();
		}
	};
	public static XPath getXPath(){
    	return _localStorageXPATH.get();
    }
	
	private static ThreadLocal<DocumentBuilder> _localDocumentBuilder = new ThreadLocal<DocumentBuilder>(){
		protected DocumentBuilder initialValue() {
			try {
				getDocumentBuilderFactory().setNamespaceAware(false);
				return getDocumentBuilderFactory().newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				throw new XMLHelperException(e);
			}
		}
	};
	public static DocumentBuilder getDocumentBuilder(){
    	return _localDocumentBuilder.get();
    }
	/* END - CREATE INFORMATION BY THREAD - PREVENT MULTIPLE INSTANCE FOR THE SAME OBJECT */

	public static boolean validateXML(String xmlStr, String schemaXML){
		return validateXML( getDocFromXML(xmlStr) , schemaXML);
	}
	
    public static boolean validateXML(Document xmlDoc, String schemaXML){
    	Schema schema = geXSDFromXML(schemaXML);
    	DOMSource xmlSource = new DOMSource(xmlDoc);
    	return validateXML(xmlSource, schema);
    }

    public static boolean validateXML(Source source, Schema schema){
        try{
            Validator validator = schema.newValidator();
            validator.validate(source);
            return true;
        }catch(Exception exception){
        	EAILogger.error(exception);//TODO: review maybe level should be lower since this is not actually an error
        	return false;
        }
    }

    public static Schema geXSDFromXML(String schemaXML) {
    	return geXSDFromXML(schemaXML, EAIFileConfigurationManager.getDefaultEncoding());
    }
    public static Schema geXSDFromXML(String schemaXML, String encoding) {
    	try{
	    	Document schemaDoc = getDoc(schemaXML, encoding);
	    	DOMSource schemaSource = new DOMSource(schemaDoc);
	    	Schema schema = getSchemaFactory().newSchema(schemaSource);
	    	return schema;
    	} catch(Exception e){
    		throw new XMLHelperException(e);
    	}
    }
    
    
    public static Document getDocFromXML(String xmlStr) {
    	return getDocFromXML(xmlStr, EAIFileConfigurationManager.getDefaultEncoding());
    }
    
    public static Document getDocFromXML(String xmlStr, String encoding) {
    	ByteArrayInputStream bais = null;
    	Document result = null;
        try{
        	if( StringUtils.isNullOrEmpty(xmlStr) ){
        		result = getDoc();
        	}else {
        		bais = new ByteArrayInputStream(xmlStr.getBytes(encoding));
        		result = getDocumentBuilder().parse(bais);
        	}
        }catch(Exception exception){
            throw new XMLHelperException(exception);
        }finally{
        	if( bais != null ){
				try {
					bais.close();
				} catch (IOException e) {
					EAILogger.error( e );
				}
        	}
        }
        return result;
    }
    public static Document getDocFromFile(String filePath) {
    	return getDocFromFile( FileUtils.getFile(filePath) );
    }
    public static Document getDocFromFile(File file) {
    	try {
			return getDocumentBuilder().parse(file);
		} catch (Exception e) {
			throw new XMLHelperException(e);
		}
    }

    public static Document getDoc(){
        try{
            return getDocumentBuilder().newDocument();
        } catch(Exception exception){
            throw new XMLHelperException(exception);
        }
    }
    public static Document getDoc(String xml){
    	return getDoc(xml, EAIFileConfigurationManager.getDefaultEncoding());
    }
    public static Document getDoc(String xml, String encoding){
    	try {
			return getDoc(new ByteArrayInputStream(xml.getBytes(encoding)));
    	} catch(Exception exception){
            throw new XMLHelperException(exception);
        }
    }
    public static Document getDoc(InputStream xmlIs){
        try{
            return getDocumentBuilder().parse(xmlIs);
        }catch(Exception exception){
            throw new XMLHelperException(exception);
        }
    }
    
    public static Document getDocFromURL(String urlString){
    	try{
    		return getDocFromURL(new URL(urlString));
    	}catch (Exception e) {
    		throw new XMLHelperException(e);
		}
    }
    public static Document getDocFromURL(URL url){
    	try{
	    	URLConnection conn = url.openConnection();
	    	return getDoc(conn.getInputStream());
    	}catch (Exception e) {
    		throw new XMLHelperException(e);
		}
    }
    
    public static NodeList evaluateXPATHToNodeList(String s, Object obj){
        try{
            return (NodeList)getXPath().evaluate(s, obj, XPathConstants.NODESET);
        }catch(Exception exception){
            throw new XMLHelperException(String.format("Check base expression = '%1$s', the expression must evaluate to Node List", s));
        }
    }

    public static Node evaluateXPATHToNode(String s, Object obj){
        try{
            return (Node)getXPath().evaluate(s, obj, XPathConstants.NODE);
        }catch(Exception exception){
        	throw new XMLHelperException(String.format("Check base expression = '%1$s', the expression must evaluate to a Single Node", s));
        }
    }

    public static String evaluateXPATHToString(String s, Object obj) {
        try{
            return getXPath().evaluate(s, obj);
        }catch(Exception exception){
            throw new XMLHelperException(String.format("Check base expression = '%1$s', the expression must evaluate to a String", s));
        }
    }

    public static String getXMLString(Node node){
    	return getXMLString(node, EAIFileConfigurationManager.getDefaultEncoding(), false);
    }
    public static String getXMLString(Node node, boolean includeEncodingProperty){
    	return getXMLString(node, EAIFileConfigurationManager.getDefaultEncoding(), includeEncodingProperty);
    }
    public static String getXMLString(Node node, String encoding){
    	return getXMLString(node, EAIFileConfigurationManager.getDefaultEncoding(), false);
    }
    public static String getXMLString(Node node, String encoding, boolean includeEncodingProperty){
    	String result = null;
    	
    	ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        try{
            DOMSource domsource = new DOMSource(node);
            StreamResult streamresult = new StreamResult(bytearrayoutputstream);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            if(includeEncodingProperty){
            	transformer.setOutputProperty("encoding", encoding);
            }
            
            transformer.transform(domsource, streamresult);
            result = bytearrayoutputstream.toString(encoding);
        }catch(Exception exception){
            throw new XMLHelperException(exception);
        }finally{
        	if( bytearrayoutputstream != null ){
	        	try {
					bytearrayoutputstream.close();
				} catch (IOException e) {
					EAILogger.error(e);
				}
        	}
        }
        return result;
    }

    public static Document applyXSLTTransformation(Document document, Transformer transformer){
        DOMSource domsource = new DOMSource(document);
        Document document1 = null;
        try{
            document1 = getDocumentBuilder().newDocument();
            DOMResult domresult = new DOMResult(document1);
            transformer.transform(domsource, domresult);
        }catch(Exception exception){
            throw new XMLHelperException(exception);
        }
        return document1;
    }

    public static Transformer getTransformFromXML(String s){
        StreamSource streamsource = new StreamSource(new StringReader(s));
        Transformer transformer = null;
        try{
            transformer = getTransformerFactory().newTransformer(streamsource);
        }catch(Exception exception){
            throw new XMLHelperException(exception);
        }
        return transformer;
    }

    public static Document getDocumentWithNoNamespace(Document document){
        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:output method=\"xml\" version=\"1.0\" encoding=\"UTF-8\" indent=\"yes\"/><xsl:template match=\"*[not (namespace-uri())] | *\"><xsl:element name=\"{local-name(.)}\"> <xsl:for-each select=\"@*\"><xsl:if test=\"not(contains(name(),':' ))\"> <xsl:attribute name=\"{name()}\"><xsl:value-of select=\".\"/></xsl:attribute> </xsl:if> </xsl:for-each><xsl:apply-templates/></xsl:element></xsl:template></xsl:stylesheet>";
        return applyXSLTTransformation(document, getTransformFromXML(s));
    }

    public static Document getDocumentWithNamespace(Document document, String s, String s1){
        String s2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:output method=\"xml\" version=\"1.0\" encoding=\"UTF-8\" indent=\"yes\"/><xsl:param name=\"namespaceURI\"/><xsl:param name=\"namespacePrefix\"/><xsl:template match=\"*[not (namespace-uri())] | *\"><xsl:element name=\"{$namespacePrefix}{local-name(.)}\" namespace=\"{$namespaceURI}\"><xsl:for-each select=\"@*\"><xsl:if test=\"not(contains(name(),':' ))\"><xsl:attribute name=\"{name()}\"><xsl:value-of select=\".\"/></xsl:attribute></xsl:if></xsl:for-each><xsl:apply-templates/></xsl:element></xsl:template></xsl:stylesheet>";
        Transformer transformer = getTransformFromXML(s2);
        transformer.setParameter("namespaceURI", s);
        transformer.setParameter("namespacePrefix", s1);
        return applyXSLTTransformation(document, transformer);
    }
    
    public static String evaluateXPATHString(Node node, String expression){
    	return StringUtils.getStringFromList(evaluateXPATHStringList(node, expression));
    }
    public static List<String> evaluateXPATHStringList(Node node, String expression){
    	List<String> resultLst = new ArrayList<String>();
    	if(expression != null){
    		try{
    		XPathExpression expr = getXPath().compile(expression);
    		NodeList nodeEvalLst = (NodeList) expr.evaluate(node, XPathConstants.NODESET);
    		for (int i = 0; i < nodeEvalLst.getLength(); i++) {
    			Node nodeInner = nodeEvalLst.item(i);
    			resultLst.add(getXMLValue(nodeInner));
    		}
    		}catch (Exception e) {
				throw new XMLHelperException(e);
			}
    	}
    	return resultLst;
    }
    
    public static List<Node> evaluateXPATHNodeList(Node node, String expression){
    	List<Node> resultLst = new ArrayList<Node>();
    	if(expression != null){
    		try{
    		XPathExpression expr = getXPath().compile(expression);
    		NodeList nodeEvalLst = (NodeList) expr.evaluate(node, XPathConstants.NODESET);
    		for (int i = 0; i < nodeEvalLst.getLength(); i++) {
    			resultLst.add(nodeEvalLst.item(i));
    		}
    		}catch (Exception e) {
				throw new XMLHelperException(e);
			}
    	}
    	return resultLst;
    }
    
    public static String getXMLValue(Node node){
    	if(node == null){
    		return null;
    	}
    	switch (node.getNodeType()) {
		case Node.ATTRIBUTE_NODE:
			return node.getNodeValue();
		case Node.TEXT_NODE:
			return node.getNodeValue();
		default:
			return getXMLString(node);
		}
    }
    
    public static String serializePretty(Document document) {
        try {
            Writer out = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(document), new StreamResult(out));
            return out.toString();
        } catch (TransformerConfigurationException e) {
            throw new XMLHelperException(e);
        } catch (TransformerException e) {
            throw new XMLHelperException(e);
        }
    }
}
