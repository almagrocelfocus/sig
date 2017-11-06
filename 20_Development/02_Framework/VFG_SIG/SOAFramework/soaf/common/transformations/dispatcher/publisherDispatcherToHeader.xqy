xquery version "2004-draft";
(:: pragma bea:global-element-parameter parameter="$header" element="soap-env:Header" location="../../schemas/soap_v1.1.xsd" ::)
(:: pragma bea:global-element-parameter parameter="$republishEvent" element="pc:republishEvent" location="../../../callback/publisherCallback_v1/schemas/publisherCallback.xsd" ::)
(:: pragma  parameter="$inbound" type="xs:anyType" ::)

declare namespace con = "http://www.bea.com/wli/sb/context";
declare namespace http = "http://www.bea.com/wli/sb/transports/http";
declare namespace pc = "http://ws.esb.telco.com/PublisherCallback";
declare namespace tran = "http://www.bea.com/wli/sb/transports";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
declare namespace soap-env = "http://schemas.xmlsoap.org/soap/envelope/";
declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/dispatcher/publisherDispatcherToHeader/";

declare function xf:publisherDispatcherToHeader($header as element(soap-env:Header)?,
	$republishEvent as element(pc:republishEvent),
	$inbound as element(*)?,
	$messageID as xs:string)
    as element(*)* {
    	let $tokenServiceName := fn:tokenize($inbound/@name, '\$')
    	return 
	    	$header/*,
	    	<he:headerExtended>
	    		<he:originalService>{substring-after(replace($inbound/@name, '\$', '/'),'/')}</he:originalService>
	        	<he:messageId>{$messageID}</he:messageId>
	        	{
		        	for $requestId in $republishEvent/requestId[1]/text()
		        	return
		        		<he:requestId>{$requestId}</he:requestId>
		        }
		       	{
		        	for $correlationId in $republishEvent/correlationId[1]/text()
		        	return
		        		<he:correlationId>{$correlationId}</he:correlationId>
		        }
		       	{
		        	for $source in $header/*:ServiceHeader[1]/*:source[1]/text()
		        	return
		        		<he:source>{$source}</he:source>
		        }
                        	{
		        	for $requestorIp in $header/*:ServiceHeader[1]/*:requestorIp[1]/text()
		        	return
		        		<he:requestorIp>{$requestorIp}</he:requestorIp>
		        }
                        {
		        	for $requestTimestamp in $header/*:ServiceHeader[1]/*:requestTimestamp[1]/text()
		        	return
		        		<he:requestTimestamp>{$requestTimestamp}</he:requestTimestamp>
		        }
		        {
		        	for $username in $header/*:ServiceHeader[1]/*:username[1]/text()
		        	return
		        		<he:username>{$username}</he:username>
		        }
		        <he:timestamp>{fn:current-dateTime()}</he:timestamp>
		        <he:businessInterface>
		        	<he:name>{concat($republishEvent/domain, '/', $republishEvent/category, '/', 'proxyServices/', $republishEvent/service, '_v', $republishEvent/version, '/', $republishEvent/service)}</he:name>
		        	<he:domain>{$republishEvent/domain/text()}</he:domain>
		        	<he:category>{$republishEvent/category/text()}</he:category>
		        	<he:target>{$republishEvent/target/text()}</he:target>
		        	<he:service>{$republishEvent/service/text()}</he:service>
		        	<he:version>{$republishEvent/version/text()}</he:version>
		        	{
		        		for $soapAction in $inbound/con:transport[1]/con:request[1]/tran:headers[1]/http:SOAPAction[1]/text()
			        	return
			        		<he:soapAction>{$soapAction}</he:soapAction>
		        	}
		        </he:businessInterface>
                        
	        	<he:contextInformation>
	        		<he:keyName>keyName</he:keyName>
	        		<he:keyValue>keyValue</he:keyValue>
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
	        	{xf:repubContext($republishEvent)}
	        	<he:attributeList/>
	        </he:headerExtended>
};

declare function xf:repubContext($republishEvent as element(pc:republishEvent))
    as element(he:repubContext) {
        <he:repubContext>
        	<he:numberOfRetries>{$republishEvent/numberOfRetries/text()}</he:numberOfRetries>
        	<he:maxNumberOfRetries>{$republishEvent/maxNumberOfRetries/text()}</he:maxNumberOfRetries>
        	<he:repubInfo>{$republishEvent/repubInfo/text()}</he:repubInfo>
        </he:repubContext>
};

declare variable $header as element(soap-env:Header) external;
declare variable $republishEvent as element(pc:republishEvent) external;
declare variable $inbound as element(*) external;
declare variable $messageID as xs:string external;

xf:publisherDispatcherToHeader($header, $republishEvent, $inbound, $messageID)