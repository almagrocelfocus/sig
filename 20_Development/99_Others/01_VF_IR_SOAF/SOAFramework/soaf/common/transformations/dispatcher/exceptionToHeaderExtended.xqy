xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace he="http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
(:: import schema at "../../schemas/headerExtended_v1.xsd" ::)
declare namespace soap-env = "http://schemas.xmlsoap.org/soap/envelope/";
declare namespace con = "http://www.bea.com/wli/sb/context";

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/dispatcher/exceptionToHeaderExtended/";

declare variable $headerExtended as element() (:: schema-element(he:headerExtended) ::) external;
declare variable $fault as element() external;
declare variable $inbound as element() external;
declare variable $route as element() external;

declare function local:func($headerExtended as element() (:: schema-element(he:headerExtended) ::), 
                            $fault as element(), 
                            $inbound as element(), 
                            $route as element()) 
                            as element() (:: schema-element(he:headerExtended) ::) {
    <he:headerExtended>
        <he:originalService>{fn:data($headerExtended/he:originalService)}</he:originalService>
        <he:messageId>{fn:data($headerExtended/he:messageId)}</he:messageId>
        <he:requestId>{fn:data($headerExtended/he:requestId)}</he:requestId>
        {
            if ($headerExtended/he:correlationId)
            then <he:correlationId>{fn:data($headerExtended/he:correlationId)}</he:correlationId>
            else ()
        }
        <he:source>{fn:data($headerExtended/he:source)}</he:source>
        {
            if ($headerExtended/he:priority)
            then <he:priority>{fn:data($headerExtended/he:priority)}</he:priority>
            else ()
        }
        {
            if ($headerExtended/he:username)
            then <he:username>{fn:data($headerExtended/he:username)}</he:username>
            else ()
        }
        {
            if ($headerExtended/he:timestamp)
            then <he:timestamp>{fn:data($headerExtended/he:timestamp)}</he:timestamp>
            else ()
        }
        {
            if ($headerExtended/he:externalMessageId)
            then <he:externalMessageId>{fn:data($headerExtended/he:externalMessageId)}</he:externalMessageId>
            else ()
        }
        {
            if ($headerExtended/he:businessInterface)
            then 
                <he:businessInterface>
                    <he:name>{fn:data($headerExtended/he:businessInterface/he:name)}</he:name>
                    <he:domain>{fn:data($headerExtended/he:businessInterface/he:domain)}</he:domain>
                    <he:category>{fn:data($headerExtended/he:businessInterface/he:category)}</he:category>
                    <he:target>{fn:data($headerExtended/he:businessInterface/he:target)}</he:target>
                    <he:service>{fn:data($headerExtended/he:businessInterface/he:service)}</he:service>
                    <he:version>{fn:data($headerExtended/he:businessInterface/he:version)}</he:version>
                    {
                        if ($headerExtended/he:businessInterface/he:operation)
                        then <he:operation>{fn:data($headerExtended/he:businessInterface/he:operation)}</he:operation>
                        else ()
                    }
                    {
                        if ($headerExtended/he:businessInterface/he:soapAction)
                        then <he:soapAction>{fn:data($headerExtended/he:businessInterface/he:soapAction)}</he:soapAction>
                        else ()
                    }
                </he:businessInterface>
            else ()
        }
        {
            if ($headerExtended/he:technicalInterface)
            then 
                <he:technicalInterface>
                    <he:name>{fn:data($headerExtended/he:technicalInterface/he:name)}</he:name>
                    <he:domain>{fn:data($headerExtended/he:technicalInterface/he:domain)}</he:domain>
                    <he:category>{fn:data($headerExtended/he:technicalInterface/he:category)}</he:category>
                    <he:target>{fn:data($headerExtended/he:technicalInterface/he:target)}</he:target>
                    <he:service>{fn:data($headerExtended/he:technicalInterface/he:service)}</he:service>
                    <he:version>{fn:data($headerExtended/he:technicalInterface/he:version)}</he:version>
                    {
                        if ($headerExtended/he:technicalInterface/he:operation)
                        then <he:operation>{fn:data($headerExtended/he:technicalInterface/he:operation)}</he:operation>
                        else ()
                    }
                    {
                        if ($headerExtended/he:technicalInterface/he:soapAction)
                        then <he:soapAction>{fn:data($headerExtended/he:technicalInterface/he:soapAction)}</he:soapAction>
                        else ()
                    }
                </he:technicalInterface>
            else ()
        }
        {
            if ($headerExtended/he:contextInformation)
            then 
                <he:contextInformation>
                    <he:requestorIp>{fn:data($headerExtended/he:contextInformation/he:requestorIp)}</he:requestorIp>
                    <he:requestorAgent>{fn:data($headerExtended/he:contextInformation/he:requestorAgent)}</he:requestorAgent>
                    {
                        if ($headerExtended/he:contextInformation/he:sessionId)
                        then <he:sessionId>{fn:data($headerExtended/he:contextInformation/he:sessionId)}</he:sessionId>
                        else ()
                    }
                </he:contextInformation>
            else ()
        }
        {
            if ($headerExtended/he:repubContext)
            then 
                <he:repubContext>
                    <he:numberOfRetries>{fn:data($headerExtended/he:repubContext/he:numberOfRetries)}</he:numberOfRetries>
                    <he:maxNumberOfRetries>{fn:data($headerExtended/he:repubContext/he:maxNumberOfRetries)}</he:maxNumberOfRetries>
                    <he:repubInfo>{fn:data($headerExtended/he:repubContext/he:repubInfo)}</he:repubInfo>
                </he:repubContext>
            else ()
        }
        {
            if ($headerExtended/he:attributeList)
            then 
                <he:attributeList>
                    {
                        for $attribute in $headerExtended/he:attributeList/he:attribute
                        return 
                        <he:attribute>
                            <he:name>{fn:data($attribute/he:name)}</he:name>
                            <he:value>{fn:data($attribute/he:value)}</he:value>
                            {
                                if ($attribute/he:context)
                                then <he:context>{fn:data($attribute/he:context)}</he:context>
                                else ()
                            }
                        </he:attribute>
                    }
                </he:attributeList>
            else ()
        }
        {
            for $adapterInformation in $headerExtended/he:adapterInformation
            return 
            <he:adapterInformation>
                <he:targetIp>{fn:data($adapterInformation/he:targetIp)}</he:targetIp>
                <he:adapterExecutionMili>{fn:data($adapterInformation/he:adapterExecutionMili)}</he:adapterExecutionMili>
            </he:adapterInformation>
        }
        {
          if($headerExtended/he:responseCodes[1])
          (: headerExtended already have response codes :)
          then $headerExtended/he:responseCodes[1]
          else if($fault and fn:namespace-uri($fault) = fn:namespace-uri(<soap-env:Fault/>))
          (: else check for webservice fault :)
          then (xf:DecodeWSError($headerExtended, $fault, $inbound, $route))
          else if($fault and fn:namespace-uri($fault) = fn:namespace-uri(<con:fault/>))
          (: else check for OSB generic fault :)
          then (xf:DecodeOSBError($headerExtended,$fault, $inbound, $route))
          (: Unknown error type, to be decoded :) 
          else (xf:ResponseCodes('1','Unknown fault type','1','Unknown fault type', false(), 'EAI'))
        }
    </he:headerExtended>
};

declare function xf:DecodeWSError($header as element()?, $fault as element()?, $inbound as element()?, $route as element()?)as element(he:responseCodes) {
    	xf:ResponseCodes($fault/*:faultcode/text(),
    		concat($fault/*:faultstring/text(), '&#10;', data($fault/*:detail/*:message[1]), data($fault/*:detail//*:eis-error-code[1]), ' : ', xf:concatNode($fault/*:detail//*:xmlLocation[1]), data($fault/*:detail//*:eis-error-message[1])),
    		$fault/*:faultcode/text(),
    		concat(xf:DecodeService((), $inbound, $route), ' : ', $fault/*:faultstring/text(), '&#10;', data($fault/*:detail)),
    		true(),
                fn:upper-case(fn:tokenize(data($header/*:originalService), '/')[2]))
};

declare function xf:DecodeOSBError($header as element()?,
    $fault as element(con:fault)?,
    $inbound as element(con:endpoint)?,
    $route as element()?)
    as element(he:responseCodes) {
    	(: decode validation error :)
    	if($fault/con:details/*:ValidationFailureDetail) 
    	then
    		let $messageValidationDetail := $fault/con:details[1]/*:ValidationFailureDetail[1]
    		return 
    			xf:ResponseCodes(
    				$fault/con:errorCode/text(), 
    				concat('Invalid request: ', $messageValidationDetail/*:message[1], ' : ', xf:concatNode($messageValidationDetail/*:xmlLocation[1])),
    				$fault/con:errorCode/text(),
    				concat(xf:DecodeService($fault, $inbound, $route), ': ', $fault/con:reason),
                                false(),
                                fn:upper-case(fn:tokenize(data($header/*:originalService), '/')[2]))
    	(: default error management :)
    	else
    		xf:ResponseCodes(
    			$fault/con:errorCode/text(), 
    			$fault/con:reason, 
    			$fault/con:errorCode/text(),
    			concat(xf:DecodeService($fault, $inbound, $route), ': ', $fault/con:reason),
    			false(),
                        fn:upper-case(fn:tokenize(data($header/*:originalService), '/')[2]))
};

declare function xf:concatNode($node as element()?)
	as xs:string?{
		for $val in $node/*
		return 
			if (not( ($val) is ($node/*[last()])[1] )) then
				concat(local-name($val), ' = ', $val, ",")
            else
            	concat(local-name($val), ' = ', $val)
};

declare function xf:DecodeService($fault as element(con:fault)?,
	$inbound as element(con:endpoint)?,
	$route as element()?)
    as xs:string {
    	let $serviceName := 
    		if($route/*:service)
    		then concat('[', $route/*:service, ']')
    		else replace(concat('[',if($inbound/@name) then $inbound/@name else 'UNKNWON',']'),'\$','/')
    	return 
    		if($fault) 
    		then 
    			concat($serviceName,' - [', $fault/con:location/con:pipeline, '/', $fault/con:location/con:stage, ']')
    		else
    			$serviceName
};

declare function xf:ResponseCodes($errorCode as xs:string?,
    $errorMessage as xs:string?,
    $externalErrorCode as xs:string?,
    $externalErrorMessage as xs:string?,
    $isBusinessServiceFault as xs:boolean,
    $systemName as xs:string)
     as element(he:responseCodes) {
		(: Apply error masking, error masking at this moment is defined by file, it can be latter added to database for example:)
            let $responseCode := dvmtr:lookup('SOAFramework/soaf/def_ErrorMapping', 'error', $errorCode, 'responseCode', '_Unknown')
            let $responseMessage := dvmtr:lookup('SOAFramework/soaf/def_ErrorMapping', 'error', $errorCode, 'responseMessage', '_Unknown')
            let $containsErrorMessage := dvmtr:lookup('SOAFramework/soaf/def_ErrorMapping', 'error', $errorCode, 'containsErrorMessage', '_Unknown')    
                
	   return <he:responseCodes>
	    	{
                        if(fn:starts-with($errorCode, 'BEA-')) then (
		    		<he:responseCode>{$responseCode}</he:responseCode>,
                                if($containsErrorMessage = 'n') then 
                                    <he:responseMessage>{$responseMessage}</he:responseMessage>
                                else if($containsErrorMessage = 'y' and  $responseMessage != '_Unknown') then 
                                    <he:responseMessage>{concat($responseMessage, $errorMessage)}</he:responseMessage>
                                else 
                                    <he:responseMessage>{$errorMessage}</he:responseMessage>
		    	) else if($isBusinessServiceFault) then (
		    		<he:responseCode>{xs:string(dvmtr:lookup('SOAFramework/soaf/def_ErrorMapping', 'error', 'isBusinessServiceFault', 'responseCode', '_Unknown'))}</he:responseCode>,
		    		<he:responseMessage>{concat(xs:string(dvmtr:lookup('SOAFramework/soaf/def_ErrorMapping', 'error', 'isBusinessServiceFault', 'responseMessage', '_Unknown')), $errorMessage)}</he:responseMessage>
		    	) else (
		    		<he:responseCode>{xs:string(dvmtr:lookup('SOAFramework/soaf/def_ErrorMapping', 'error', 'default', 'responseCode', '_Unknown'))}</he:responseCode>,
		    		<he:responseMessage>{concat(xs:string(dvmtr:lookup('SOAFramework/soaf/def_ErrorMapping', 'error', 'default', 'responseMessage', '_Unknown')), $errorMessage)}</he:responseMessage>
		    	),
		    	if($externalErrorCode or $externalErrorMessage) 
		    	then 
		    		<he:externalError>{
			        	if($externalErrorCode) then <he:errorCode>{$externalErrorCode}</he:errorCode> else(),
			        	if($externalErrorMessage) then <he:errorMessage>{$externalErrorMessage}</he:errorMessage> else(),
			        	<he:system>{data($systemName)}</he:system>
		    		}</he:externalError>
				else()
	    }</he:responseCodes>
};
local:func($headerExtended, $fault, $inbound, $route)