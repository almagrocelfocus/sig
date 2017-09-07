xquery version "2004-draft" encoding "UTF-8";
(:: pragma bea:global-element-parameter parameter="$getServiceConfigurationListResponse" element="sc:getServiceConfigurationListResponse" location="../../../businessServices/serviceConfiguratorService_v1/schemas/serviceConfiguratorService.xsd" ::)
(:: pragma bea:global-element-return element="he:attribute" location="../../../common/schemas/headerExtended_v1.xsd" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/proxyService/proxyDispatcher_v1/transformation/fromGetServiceConfigurationListToHeaderAttributes/";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
declare namespace sc = "http://ws.esb.telco.com/ServiceConfigurator";

declare function xf:fromGetServiceConfigurationListToHeaderAttributes($getServiceConfigurationListResponse as element(sc:getServiceConfigurationListResponse),
	$context as xs:string)
    as element(he:attribute)* {
    	let $contextElem :=  if($context) then <he:context>{data($context)}</he:context> else ()
        return
	        for $configuration in $getServiceConfigurationListResponse/configurationList
	        return
				<he:attribute>
	            	<he:name>{$configuration/name/text()}</he:name>
	                <he:value>{$configuration/value/text()}</he:value>
	                {$contextElem}
				</he:attribute>
};

declare variable $getServiceConfigurationListResponse as element(sc:getServiceConfigurationListResponse) external;
declare variable $context as xs:string external;

xf:fromGetServiceConfigurationListToHeaderAttributes($getServiceConfigurationListResponse, $context)