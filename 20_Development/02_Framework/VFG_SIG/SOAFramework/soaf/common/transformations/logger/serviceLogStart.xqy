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
                        {
				for $domain in $headerExtended/he:businessInterface[1]/he:domain[1]
				return 
					<log:domain>{$domain/text()}</log:domain>
			}
                        {
				for $category in $headerExtended/he:businessInterface[1]/he:category[1]
				return 
					<log:category>{$category/text()}</log:category>
			}
			<log:target>pub</log:target>
                        {
				for $service in $headerExtended/he:businessInterface[1]/he:service[1]
				return 
					<log:service>{$service/text()}</log:service>
			}
                        {
				for $operation in $headerExtended/he:businessInterface[1]/he:operation[1]
				return 
					<log:operation>{$operation/text()}</log:operation>
			}
                        {
				for $version in $headerExtended/he:businessInterface[1]/he:version[1]
				return 
					<log:version>{$version/text()}</log:version>
			}

                        <log:source>{$headerExtended/he:source[1]/text()}</log:source>
            		<log:targetEndpoint>N/A</log:targetEndpoint>(: Not in context yet :)
			<log:level>{data($headerExtended/he:attributeList[1]/he:attribute[ he:name='LOG_LEVEL' ][1]/he:value[1])}</log:level>
                        <log:dynamicKeys>
                         {
                               if (fn:not(fn:empty($bodyInner))) then
                               for $pathDynamicKeyHeader in $headerExtended/he:attributeList[1]/he:attribute[he:name='LOG_KEY_SERVICE_START']
                                return   
                                  let $dynamicLogBody := local:evalPathImpl(tokenize($pathDynamicKeyHeader/he:value[1]/text(), "/"), local:strip-ns($bodyInner))
                                  return
                                        if (fn:not(fn:empty($dynamicLogBody))) then
                                               <log:dynamicKey>
                                                    <log:dynamicKeyName>{fn:node-name($dynamicLogBody)}</log:dynamicKeyName>
                                                    <log:dynamicKeyValue>{$dynamicLogBody/text()}</log:dynamicKeyValue>
                                                </log:dynamicKey>
                                                
                                            else
                                             ()
                               else ()              
                          }
                          {
                               if (fn:not(fn:empty(<soap-env:Header>{$headerInner}</soap-env:Header>/*:serviceHeader))) then
                               for $pathDynamicKeyHeader in $headerExtended/he:attributeList[1]/he:attribute[he:name='LOG_KEY_SERVICE_START']
                                return   
                                  let $dynamicLogHeader := local:evalPathImpl(tokenize($pathDynamicKeyHeader/he:value[1]/text(), "/"), local:strip-ns(<soap-env:Header>{$headerInner}</soap-env:Header>/*:serviceHeader))
                                  return
                                        if (fn:not(fn:empty($dynamicLogHeader))) then
                                               <log:dynamicKey>
                                                    <log:dynamicKeyName>{fn:node-name($dynamicLogHeader)}</log:dynamicKeyName>
                                                    <log:dynamicKeyValue>{$dynamicLogHeader/text()}</log:dynamicKeyValue>
                                                </log:dynamicKey>
                                                
                                            else
                                             ()
                               else ()              
                          }
                        </log:dynamicKeys>  
			<log:task>SERVICE_START</log:task>
			<log:username>{$headerExtended/he:username[1]/text()}</log:username>
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
				<soap-env:Header>
                                  {
                                    if (fn:not(fn:empty($headerExtended/he:attributeList[1]/he:attribute[he:name='LOG_HIDE_SERVICE_START'])) and fn:not(fn:empty($headerInner))) then
                                       for $pathToHideHeader in $headerExtended/he:attributeList[1]/he:attribute[he:name='LOG_HIDE_SERVICE_START']
                                        return   
                                           local:copy-replace($headerInner,tokenize($pathToHideHeader/he:value[1]/text(), "/"))
            
                                    else  $headerInner
                                  }
                                </soap-env:Header>
				<soap-env:Body>
                                 {
                                    if (fn:not(fn:empty($headerExtended/he:attributeList[1]/he:attribute[he:name='LOG_HIDE_SERVICE_START'])) and fn:not(fn:empty($bodyInner))) then
                                       for $pathToHideBody in $headerExtended/he:attributeList[1]/he:attribute[he:name='LOG_HIDE_SERVICE_START']
                                        return   
                                           local:copy-replace($bodyInner,tokenize($pathToHideBody/he:value[1]/text(), "/"))
            
                                    else  $bodyInner
                                  }
                                </soap-env:Body>
			</log:payload>
		</log:input>
};

declare function local:evalPathImpl($steps as xs:string*, $xml as 
node()*) as node()*
{
   if(empty($steps)) then $xml
   else if($steps[1] = "") then local:evalPathImpl(subsequence($steps, 
2), $xml/root())
   else if(starts-with($steps[1], "@")) then 
local:evalPathImpl(subsequence($steps, 2), $xml/@*[name() = 
substring($steps[1], 2)])
   else local:evalPathImpl(subsequence($steps, 2), $xml/*[name() = 
$steps[1]])
};

declare function local:strip-ns($xml as node()) as node() {
     element { local-name($xml) } {
         for $att in $xml/@*
         return
             attribute {local-name($att)} {$att},
         for $child in $xml/node()
         return
             if ($child instance of element())
             then local:strip-ns($child)
             else $child
         }
};

declare function local:copy-replace($element as element(),$steps as xs:string*) {  
  if (local-name($element) = $steps[2])
  then  local:replace-element-values($element,local:pad-string-to-length('', '*', 4))
  
  else element {node-name($element)}  
               {$element/@*, 
                for $child in $element/node()  
                return if ($child instance of element())  
                       then local:copy-replace($child,$steps)  
                       else $child  
               }  
};  

declare function local:pad-string-to-length
  ( $stringToPad as xs:string? ,
    $padChar as xs:string ,
    $length as xs:integer )  as xs:string {

   substring(
     string-join (
       ($stringToPad, for $i in (1 to $length) return $padChar)
       ,'')
    ,1,$length)
 } ;
 
 
declare function local:replace-element-values
  ( $elements as element()* ,
    $values as xs:string )  as element()* {

   for $element at $seq in $elements
   return element { node-name($element)}
             { $element/@*,
               $values[$seq] }
 } ;

declare variable $headerInner as element()* external;
declare variable $headerExtended as element(he:headerExtended)? external;
declare variable $bodyInner as item()? external;

xf:GetServiceLogStart($headerInner,
	$headerExtended,
	$bodyInner)