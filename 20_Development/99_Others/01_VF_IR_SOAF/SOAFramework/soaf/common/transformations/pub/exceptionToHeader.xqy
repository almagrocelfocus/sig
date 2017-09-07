xquery version "2004-draft" encoding "UTF-8";
(:: pragma bea:global-element-parameter parameter="$header" element="soap-env:Header" location="../../schemas/soap_v1.1.xsd" ::)
(:: pragma bea:global-element-parameter parameter="$body" element="soap-env:Body" location="../../schemas/soap_v1.1.xsd" ::)
(:: pragma parameter="$fault" type="xs:anyType" ::)
(:: pragma parameter="$inbound" type="xs:anyType" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/pub/exceptionToHeader/";
declare namespace soap-env = "http://schemas.xmlsoap.org/soap/envelope/";
declare namespace con = "http://www.bea.com/wli/sb/context";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";

declare function xf:exceptionToHeader($header as element(soap-env:Header)?,
    $body as element(soap-env:Body)?,
    $fault as element()?,
    $inbound as element()?)
    as element()*{
    	if($header/he:responseCodes[1])
    	then $header/*
    	else
			$header/*,
			if($body/*:Fault) then ( 
				xf:DecodeWSError($header, $body, $body/*:Fault, $inbound)
			) else if($fault and fn:namespace-uri($fault) = fn:namespace-uri(<soap-env:Fault/>)) then (
	    		xf:DecodeWSError($header, $body, $fault, $inbound)
	    	(: else check for OSB generic fault :) 
	    	) else if($fault and fn:namespace-uri($fault) = fn:namespace-uri(<con:fault/>) ) then (
	    		xf:DecodeOSBError($header, $body, $fault, $inbound)
	    	) else ( 
	    		(: Unknown error type, to be decoded :)
	    		xf:ResponseCodes('1','Unknown fault type','1','Unknown fault type',
	    		false())
	    	)
};

declare function xf:DecodeWSError($header as element(soap-env:Header)?,
    $body as element(soap-env:Body)?,
    $fault as element()?,
    $inbound as element(con:endpoint)?)
    as element(he:responseCodes) {
    	xf:ResponseCodes($fault/*:faultcode/text(),
    		concat($fault/*:faultstring/text(), '&#10;', data($fault/*:detail)),
    		$fault/*:faultcode/text(),
    		concat(xf:DecodeService((), $inbound), ':', $fault/*:faultstring/text(), '&#10;', data($fault/*:detail)),
    		true())
};

declare function xf:DecodeOSBError($header as element(soap-env:Header)?,
    $body as element(soap-env:Body)?,
    $fault as element(con:fault)?,
    $inbound as element(con:endpoint)?)
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
    				concat(xf:DecodeService($fault, $inbound), ': ', $fault/con:reason),
    				false())
    	(: default error management :)
    	else
    		xf:ResponseCodes(
    			$fault/con:errorCode/text(),
    			$fault/con:reason,
    			$fault/con:errorCode/text(),
    			concat(xf:DecodeService($fault, $inbound), ': ', $fault/con:reason),
    			false())
};

declare function xf:concatNode($node as element()?)
	as xs:string?{
		for $val in $node/*
		return 
			if (not( ($val) is ($node/*[last()])[1] )) then
				concat(local-name($val), '=', $val, ",")
            else
            	concat(local-name($val), '=', $val)
};

declare function xf:DecodeService($fault as element(con:fault)?,
	$inbound as element(con:endpoint)?)
    as xs:string {
    	let $serviceName := replace(concat('[',if($inbound/@name) then $inbound/@name else 'UNKNWON',']'),'\$','/')
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
    $isBusinessServiceFault as xs:boolean)
    as element(he:responseCodes) {
		(: Apply error masking, error masking at this moment is defined by file, it can be latter added to database for example:)
	    <he:responseCodes>
	    	{
		    	if($errorCode = 'BEA-382505') then (
		    		<he:responseCode>{'0030002'}</he:responseCode>,
		    		<he:responseMessage>{$errorMessage}</he:responseMessage>
		    	) else if($errorCode = 'BEA-380002') then (
		    		<he:responseCode>{'0030003'}</he:responseCode>,
		    		<he:responseMessage>{concat('Target system unavailable: ',$errorMessage)}</he:responseMessage>
		    	) else if($errorCode = 'BEA-386200') then (
		    		<he:responseCode>{'0030005'}</he:responseCode>,
		    		<he:responseMessage>General web service security error</he:responseMessage>
		    	) else if($isBusinessServiceFault) then (
		    		<he:responseCode>{'0030004'}</he:responseCode>,
		    		<he:responseMessage>{concat('Target system fault: ', $errorMessage)}</he:responseMessage>
		    	) else (
		    		<he:responseCode>{'0030001'}</he:responseCode>,
		    		<he:responseMessage>{concat('Internal EAI error, check logs for more details: ', $errorMessage)}</he:responseMessage>
		    	),
		    	if($externalErrorCode or $externalErrorMessage) 
		    	then 
		    		<he:externalError>{
			        	if($externalErrorCode) then <he:errorCode>{$externalErrorCode}</he:errorCode> else(),
			        	if($externalErrorMessage) then <he:errorMessage>{$externalErrorMessage}</he:errorMessage> else(),
			        	<he:system>EAI</he:system>
		    		}</he:externalError>
				else()
	    }</he:responseCodes>
};

declare variable $header as element(soap-env:Header)? external;
declare variable $body as element(soap-env:Body)? external;
declare variable $fault as element()? external;
declare variable $inbound as element()? external;

xf:exceptionToHeader($header,
    $body,
    $fault,
    $inbound)