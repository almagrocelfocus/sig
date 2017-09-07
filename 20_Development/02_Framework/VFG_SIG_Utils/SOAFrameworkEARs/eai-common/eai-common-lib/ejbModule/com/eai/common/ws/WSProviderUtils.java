package com.eai.common.ws;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.eai.common.utils.FileUtils;
import com.eai.common.utils.XMLUtils;
import com.eai.common.ws.entity.Provider;
import com.eai.common.ws.entity.Provider.ProviderContainer;

public final class WSProviderUtils {
	private static final String PROVIDERS_XSD = "META-INF/config/providers.xsd";
	
	public static final boolean validateProviderConfig(String configXML){
		return validateProviderConfig(configXML, PROVIDERS_XSD);
	}
	public static final boolean validateProviderConfig(String configXML, String filePath){
		return XMLUtils.validateXML(configXML, FileUtils.getFileAsString(filePath));
	}
	
	public static final ProviderContainer loadProviderContainer(String configXML){
		ProviderContainer providerContainer = new ProviderContainer();
		
		Document doc = XMLUtils.getDocFromXML(configXML);
		NodeList providersLst = doc.getElementsByTagName("provider");
		if(providersLst != null){
			for(int i = 0; i < providersLst.getLength(); i++){
				Provider providerInfo = new Provider();
				Node provider = providersLst.item(i);
				if(provider != null && provider.getChildNodes() != null){
					for(int j = 0; j < provider.getChildNodes().getLength(); j++){
						Node providerInfoNode = provider.getChildNodes().item(j);
						if("name".equals(providerInfoNode.getNodeName() )){
							providerInfo.setName(providerInfoNode.getNodeValue());
						}
						if("description".equals(providerInfoNode.getNodeName() )){
							providerInfo.setDescription(providerInfoNode.getNodeValue());
						}
						if("url".equals(providerInfoNode.getNodeName() )){
							providerInfo.setUrl(providerInfoNode.getNodeValue());
						}
					}
				}
				providerContainer.getProviderLst().add(providerInfo);
			}
		}
		NodeList currentProviderNodeLst = doc.getElementsByTagName("currentProviderId");
		if( currentProviderNodeLst != null  && currentProviderNodeLst.getLength() > 0){
			providerContainer.setCurrentProvider( currentProviderNodeLst.item(0).getNodeValue() );
		}
		return providerContainer;
	}
}
