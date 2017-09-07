xquery version "2004-draft" encoding "UTF-8";
(:: pragma bea:global-element-parameter parameter="$header" element="soap-env:Header" location="../../schemas/soap_v1.1.xsd" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/proxyService/technicalDispatcherToHeader/";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
declare namespace soap-env = "http://schemas.xmlsoap.org/soap/envelope/";

declare function xf:technicalDispatcherToHeader($domain as xs:string,
	$category as xs:string,
    $service as xs:string,
    $version as xs:int,
    $operation as xs:string?,
    $soapAction as xs:string?,
    $header as element(soap-env:Header))
    as element()* {
    	if($header/he:headerExtended)
    	then (
	    	$header/*[not(self::he:headerExtended)],
	    	<he:headerExtended>{
	    		$header/he:headerExtended/*,
	        	<he:externalMessageId>{fn-bea:uuid()}</he:externalMessageId>,
	        	xf:specificInterface($domain, $category, $service, $version, $operation, $soapAction),
	        	<he:attributeList/>
	        }</he:headerExtended>
		) else fn:error(xs:QName('xs:string'),
					'headerExtended not found, ensure $header variable was changed through externalHeaderToHeader.xq, and proxy Service Type is defined as SOAP service')
};

(: Function to be changed to allow other paths :)
declare function xf:specificInterface($domain as xs:string,
	$category as xs:string,
    $service as xs:string,
    $version as xs:int,
    $operation as xs:string?,
    $soapAction as xs:string?)
    as element(he:technicalInterface) {
        <he:technicalInterface>
        	<he:name>{concat($domain, '/', $category, '/', 'businessServices/', $service, '_v', $version, '/', $service)}</he:name>
        	<he:domain>{$domain}</he:domain>
        	<he:category>{$category}</he:category>
        	<he:target>businessServices</he:target>
        	<he:service>{$service}</he:service>
        	<he:version>{$version}</he:version>
        	{
	        	if(string($soapAction) != '') then <he:soapAction>{$soapAction}</he:soapAction> else (),
	        	if($operation) then <he:operation>{$operation}</he:operation> else ()
        	}
        </he:technicalInterface>
};


declare variable $domain as xs:string external;
declare variable $category as xs:string external;
declare variable $service as xs:string external;
declare variable $version as xs:int external;
declare variable $operation as xs:string? external;
declare variable $soapAction as xs:string? external;
declare variable $header as element(soap-env:Header) external;

xf:technicalDispatcherToHeader($domain,
    $category,
    $service,
    $version,
    $operation,
    $soapAction,
    $header)