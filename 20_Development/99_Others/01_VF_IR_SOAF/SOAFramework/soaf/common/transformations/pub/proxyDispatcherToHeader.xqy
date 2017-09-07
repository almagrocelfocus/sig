xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace tns = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/commonTypes/v1";
(:: import schema at "../../schemas/commonTypes_v1.xsd" ::)
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
(:: import schema at "../../schemas/headerExtended_v1.xsd" ::)
declare namespace con = "http://www.bea.com/wli/sb/context";
declare namespace tran = "http://www.bea.com/wli/sb/transports";
declare namespace http = "http://www.bea.com/wli/sb/transports/http";
declare namespace soap-env = "http://schemas.xmlsoap.org/soap/envelope/";
(:: import schema at "../../schemas/soap_v1.1.xsd" ::)
declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/pub/proxyDispatcherToHeader/";


declare variable $header as element(soap-env:Header) external;
declare variable $inbound as element(*)? external;
declare variable $messageID as xs:string external;

declare function local:func($header as element()? (:: schema-element(soap-env:Header)? ::), 
                            $inbound as element(*)?, 
                            $messageID as xs:string) 
                            as element(*)* {
    	let 
    		$tokenServiceName := fn:tokenize($inbound/@name, '\$')
    	return
                $header/*,
	    	<he:headerExtended>
	    		<he:originalService>{substring-after(replace($inbound/@name, '\$', '/'),'/')}</he:originalService>
	        	<he:messageId>{$messageID}</he:messageId>
                        <he:requestId>{fn:data($header/*[local-name()='serviceHeader']/*[local-name()='requestId'][1])}</he:requestId>
                        {
                            if ($header/*[local-name()='serviceHeader']/*[local-name()='correlationId'][1])
                            then <he:correlationId>{fn:data($header/*[local-name()='serviceHeader']/*[local-name()='correlationId'][1])}</he:correlationId>
                            else ()
                        }
                        <he:source>{fn:data($header/*[local-name()='serviceHeader']/*[local-name()='source'][1])}</he:source>
		        <he:username>{$inbound/con:security/con:messageLevelClient/con:username[1]/text()}</he:username>
		        <he:timestamp>{fn:current-dateTime()}</he:timestamp>
	    		{
	        		xf:specificInterface($inbound)
	        	}
	        	<he:contextInformation>
        		{
        			let $hhtpRequest := $inbound/con:transport[1]/con:request[1]
        			return
        			(
        				<he:requestorIp>{$hhtpRequest/http:client-address[1]/text()}</he:requestorIp>,
        				<he:requestorAgent>{$hhtpRequest/tran:headers[1]/http:User-Agent[1]/text()}</he:requestorAgent>,
        				<he:sessionId>{$messageID}</he:sessionId>
        			)
        		}
	        	</he:contextInformation>
	        </he:headerExtended>
};

(: Function to be changed to allow other paths :)
declare function xf:specificInterface($inbound as element(*))
    as element(he:businessInterface) {
    	let $tokenServiceName := fn:tokenize($inbound/@name, '\$'),
    		$domain := $tokenServiceName[2],
    		$category := $tokenServiceName[3],
    		$target := 'proxyServices',
    		$service := $tokenServiceName[6],
    		$version := substring-after($tokenServiceName[5],'_v')
    	return
	        <he:businessInterface>
	        	<he:name>{concat($domain, '/', $category, '/', 'proxyServices/', $service, '_v', $version, '/', $service)}</he:name>
	        	<he:domain>{$domain}</he:domain>
	        	<he:category>{$category}</he:category>
	        	<he:target>{$target}</he:target>
	        	<he:service>{$service}</he:service>
	        	<he:version>{$version}</he:version>
	        	{
	        		for $soapAction in $inbound/con:transport[1]/con:request[1]/tran:headers[1]/http:SOAPAction[1]/text()
		        	return
		        		<he:soapAction>{$soapAction}</he:soapAction>
	        	}
	        </he:businessInterface>
};

local:func($header, $inbound, $messageID)
