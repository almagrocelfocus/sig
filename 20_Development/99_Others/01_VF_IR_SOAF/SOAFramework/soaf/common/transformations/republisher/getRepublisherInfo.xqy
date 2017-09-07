xquery version "2004-draft" encoding "UTF-8";
(:: pragma bea:global-element-parameter parameter="$header" element="soap-env:Header" location="../../schemas/soap_v1.1.xsd" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/republisher/getRepublisherInfo/";
declare namespace soap-env = "http://schemas.xmlsoap.org/soap/envelope/";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";

declare function xf:getRepublisherInfo($header as element(soap-env:Header)?)
    as xs:string? {
        $header/he:headerExtended[1]/he:repubContext[1]/he:repubInfo[1]
};

declare variable $header as element(soap-env:Header)? external;

xf:getRepublisherInfo($header)