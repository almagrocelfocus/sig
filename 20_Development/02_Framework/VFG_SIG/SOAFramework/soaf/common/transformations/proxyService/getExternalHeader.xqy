xquery version "2004-draft" encoding "UTF-8";
(:: pragma bea:global-element-parameter parameter="$header" element="soap-env:Header" location="../../schemas/soap_v1.1.xsd" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/proxyService/getExternalHeader/";
declare namespace soap-env = "http://schemas.xmlsoap.org/soap/envelope/";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";

declare function xf:getExternalHeader($header as element(soap-env:Header))
    as element(*)* {
        $header/*[not(self::he:headerExtended)]
};

declare variable $header as element(soap-env:Header)? external;

xf:getExternalHeader($header)