xquery version "2004-draft" encoding "UTF-8";
(:: pragma bea:global-element-parameter parameter="$header" element="soap-env:Header" location="../../schemas/soap_v1.1.xsd" ::)
(:: pragma  parameter="$externalHeader" type="xs:anyType" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/proxyService/externalHeaderToHeader/";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
declare namespace soap-env = "http://schemas.xmlsoap.org/soap/envelope/";

declare function xf:externalHeaderToHeader($header as element(soap-env:Header),
	$externalHeader as element(*)*)
    as element(*)* {
        $header/he:headerExtended,
        $externalHeader
};

declare variable $header as element(soap-env:Header)? external;
declare variable $externalHeader as element(*)* external;

xf:externalHeaderToHeader($header,
	$externalHeader)