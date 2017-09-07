xquery version "2004-draft" encoding "UTF-8";
(:: pragma bea:global-element-parameter parameter="$header" element="soapenv:Header" location="../../schemas/soap_v1.1.xsd" ::)
(:: pragma parameter="$inbound" type="xs:anyType" ::)
(:: pragma bea:global-element-return element="soapenv:Header" location="../../schemas/soap_v1.1.xsd" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/proxyService/externalErrorToHeader/";
declare namespace soapenv = "http://schemas.xmlsoap.org/soap/envelope/";
declare namespace con = "http://www.bea.com/wli/sb/context";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
declare namespace ctx = "http://www.bea.com/wli/sb/context";

declare function xf:externalErrorToHeader($header as element(soapenv:Header)?,
    $system as xs:string?,
    $externalErrorCode as xs:string?,
    $externalErrorMessage as xs:string?)
    as element(soapenv:Header) {
    	<soapenv:Header>{
    		$header/*[not(he:responseCodes)],
    		xf:ResponseCodes($externalErrorCode, $externalErrorMessage, $externalErrorCode, $externalErrorMessage, $system)
    	}</soapenv:Header>
};

declare function xf:ResponseCodes($errorCode as xs:string?,
    $errorMessage as xs:string?,
    $externalErrorCode as xs:string?,
    $externalErrorMessage as xs:string?,
    $system as xs:string?)
    as element(he:responseCodes) {
        <he:responseCodes>{
        	if($errorCode) then <he:responseCode>{$errorCode}</he:responseCode> else(),
        	if($errorMessage) then <he:responseMessage>{$errorMessage}</he:responseMessage> else(),
        	if($externalErrorCode or $externalErrorMessage) 
        	then 
        		<he:externalError>{
		        	if($externalErrorCode) then <he:errorCode>{$externalErrorCode}</he:errorCode> else(),
		        	if($externalErrorMessage) then <he:errorMessage>{$externalErrorMessage}</he:errorMessage> else(),
		        	if($system) then <he:system>{$system}</he:system> else()
        		}</he:externalError>
			else()
        }</he:responseCodes>
};

declare variable $header as element(soapenv:Header)? external;
declare variable $system as xs:string? external;
declare variable $externalErrorCode as xs:string? external;
declare variable $externalErrorMessage as xs:string? external;

xf:externalErrorToHeader($header,
    $system,
    $externalErrorCode,
    $externalErrorMessage)