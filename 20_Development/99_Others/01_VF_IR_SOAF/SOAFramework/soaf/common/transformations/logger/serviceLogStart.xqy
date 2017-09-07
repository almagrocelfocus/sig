xquery version "2004-draft" encoding "UTF-8";
(:: pragma bea:global-element-parameter parameter="$headerExtended" element="he:headerExtended" location="../../schemas/headerExtended_v1.xsd" ::)
(:: pragma  parameter="$headerInner" type="xs:anyType" ::)
(:: pragma  parameter="$bodyInner" type="xs:anyType" ::)
(:: pragma bea:global-element-return element="log:input" location="../../schemas/logSchema_v1.xsd" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/logger/serviceLogStart/";
declare namespace soap-env = "http://schemas.xmlsoap.org/soap/envelope/";
declare namespace log = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/logSchema/v1";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
declare namespace scf = "http://com.telco.osb.utils/osb/custom/functions/ServerUtilsCustomXpathFunc";

declare function xf:GetServiceLogStart($headerInner as element()*,
	$headerExtended as element(he:headerExtended)?,
	$bodyInner as item()?)
    as element(log:input) {
		<log:input>
			<log:serviceName>{$headerExtended/he:originalService/text()}</log:serviceName>
			<log:level>{data($headerExtended/he:attributeList[1]/he:attribute[ he:name='LOG_LEVEL' ][1]/he:value[1])}</log:level>
			<log:task>SERVICE_START</log:task>
			<log:createdBy>serviceLogStart</log:createdBy>
			<log:timestamp>{fn:current-dateTime()}</log:timestamp>
			<log:engine>{scf:serverName()}</log:engine>
			<log:statusCode>0</log:statusCode>
			<log:statusMessage>Success</log:statusMessage>
			{
				for $messageId in $headerExtended/he:messageId[1]
				return 
					<log:messageId>{$messageId/text()}</log:messageId>
			}
			{
				for $externalMessageId in $headerExtended/he:externalMessageId[1]
				return 
					<log:externalMessageId>{$externalMessageId/text()}</log:externalMessageId>
			}
			{
				for $requestId in $headerExtended/he:requestId[1]
				return 
					<log:requestId>{$requestId/text()}</log:requestId>
			}
			{ 
				for $correlationId in $headerExtended/he:correlationId[1]
				return 
					<log:correlationId>{$correlationId/text()}</log:correlationId>
			}
			{ 
				for $requestorRequestId in $headerExtended/he:username[1]
				return 
					<log:requestorRequestId>{$requestorRequestId/text()}</log:requestorRequestId>
			}
			{ 
				for $requestorIp in $headerExtended/he:contextInformation[1]/he:requestorIp[1]
				return 
					<log:requestorIp>{$requestorIp/text()}</log:requestorIp>
			}
			{ 
				for $requestorAgent in $headerExtended/he:contextInformation[1]/he:requestorAgent[1]
				return 
					<log:requestorAgent>{$requestorAgent/text()}</log:requestorAgent>
			}
			{ 
				for $sessionId in $headerExtended/he:contextInformation[1]/he:sessionId[1]
				return 
					<log:sessionId>{$sessionId/text()}</log:sessionId>
			}
			<log:action>{$headerExtended/he:originalService[1]/text()}</log:action>
			<log:objectId>{$headerExtended/he:businessInterface[1]/he:service[1]/text()}</log:objectId>
			<log:description>Received request</log:description>
			<log:timeToComplete>{1000*(fn:seconds-from-duration(xdt:dayTimeDuration(fn:current-dateTime() - xs:dateTime($headerExtended/he:timestamp/text()))) + (fn:minutes-from-duration(xdt:dayTimeDuration(fn:current-dateTime() - xs:dateTime($headerExtended/he:timestamp/text()))) * 60.0) + (fn:hours-from-duration(xdt:dayTimeDuration(fn:current-dateTime() - xs:dateTime($headerExtended/he:timestamp/text()))) * 3600.0))}</log:timeToComplete>
			<log:adapterTimeSum>{1000*(fn:sum($headerExtended/he:contextInformation[1]/he:adapterExecutionMiliList))}</log:adapterTimeSum>
			(: targetRequestId will be the same as the entry id, so we print the key :) 
			{
				for $targetRequestId in $headerExtended/he:username[1]
				return 
					<log:targetRequestId>{$targetRequestId/text()}</log:targetRequestId>
			}
			<log:targetIp>NA</log:targetIp>(: Not in context yet :)
			<log:payload>
				<soap-env:Header>{$headerInner}</soap-env:Header>
				<soap-env:Body>{$bodyInner}</soap-env:Body>
			</log:payload>
		</log:input>
};

declare variable $headerInner as element()* external;
declare variable $headerExtended as element(he:headerExtended)? external;
declare variable $bodyInner as item()? external;

xf:GetServiceLogStart($headerInner,
	$headerExtended,
	$bodyInner)