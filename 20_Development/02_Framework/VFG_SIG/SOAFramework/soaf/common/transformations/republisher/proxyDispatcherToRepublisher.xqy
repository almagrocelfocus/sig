xquery version "2004-draft";
(:: pragma bea:global-element-parameter parameter="$headerExtended" element="he:headerExtended" location="../../schemas/headerExtended_v1.xsd" ::)
(:: pragma parameter="$bodyInner" type="xs:anyType" ::)
(:: pragma bea:global-element-return element="pub:storeOperation" location="../../../businessServices/publisherService_v1/schemas/publisherService_schema1.xsd" ::)

declare namespace pub = "http://ws.esb.telco.com/Publisher";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/republisher/proxyDispatcherToRepublisher/";

declare function xf:proxyDispatcherToRepublisher($header as element(),
	$bodyInner as element(),
	$customServiceName as xs:string?,
	$customCorrelationId as xs:string?,
	$errorCode as xs:string?)
    as element(pub:storeOperation) {
    	let $headerExtended := $header/he:headerExtended[1],
    		$businessInterface := $headerExtended/he:businessInterface[1]
    	return
	        <pub:storeOperation>
	            <messageId>{ data($headerExtended/he:messageId[1]) }</messageId>
	            {
	                for $requestId in $headerExtended/he:requestId[1]
	                return
	                    <requestId>{ data($requestId) }</requestId>
	            }
	            {
	            (: use custom correlation id, or the existing correlation id if no custom correlation was provided :)
	            if(string($customCorrelationId) != '')
	            then
	            	<correlationId>{ $customCorrelationId }</correlationId>
	            else
	            	for $correlationId in $headerExtended/he:correlationId[1]
	                return
	                    <correlationId>{ data($correlationId) }</correlationId>
	            }
	            {
	            if(string($errorCode) != '')
	            then
	            	<errorCode>{ $errorCode }</errorCode>
	            else
	            	<errorCode>{'0000000'}</errorCode>
	            }
	            <request>{ fn-bea:serialize($bodyInner) }</request>
	            {
	                for $repubInfo in $headerExtended/he:repubContext[1]/he:repubInfo[1]
	                return
	                    <repubInfo>{ data($repubInfo) }</repubInfo>
	            }
	            <domain>{ data($businessInterface/he:domain[1]) }</domain>
	            <category>{ data($businessInterface/he:category[1]) }</category>
	            <target>{ data($businessInterface/he:target[1]) }</target>
	            {
	            if(string($customServiceName) != '')
	            then
	            	<service>{ $customServiceName }</service>
	            else
	            	<service>{ data($businessInterface/he:service[1]) }</service>
	            }
	            <version>{ data($businessInterface/he:version[1]) }</version>
	            {
	                for $username in $headerExtended/he:username
	                return
	                    <username>{ data($username) }</username>
	            }
	        </pub:storeOperation>
};

declare variable $header as element() external;
declare variable $bodyInner as element() external;
declare variable $customServiceName as xs:string? external;
declare variable $customCorrelationId as xs:string? external;
declare variable $errorCode as xs:string? external;

xf:proxyDispatcherToRepublisher($header,
	$bodyInner,
	$customServiceName,
	$customCorrelationId,
	$errorCode)