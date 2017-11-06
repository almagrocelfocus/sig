xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns2="http://xmlns.telco.com/SOAFramework/soaf/common/schemas/commonTypes/v1";
(:: import schema at "../../schemas/commonTypes_v1.xsd" ::)
declare namespace ns1="http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
(:: import schema at "../../schemas/headerExtended_v1.xsd" ::)

declare variable $headerExtended as element() (:: schema-element(ns1:headerExtended) ::) external;

declare function local:func($headerExtended as element() (:: schema-element(ns1:headerExtended) ::)) as element() (:: schema-element(ns2:serviceHeader) ::) {
    <ns2:serviceHeader>
        <requestId>{fn:data($headerExtended/ns1:requestId)}</requestId>
        {
            if ($headerExtended/ns1:correlationId)
            then <correlationId>{fn:data($headerExtended/ns1:correlationId)}</correlationId>
            else ()
        }
        <source>{fn:data($headerExtended/ns1:source)}</source>
        {
            if ($headerExtended/ns1:requestorIp)
            then <requestorIp>{fn:data($headerExtended/ns1:requestorIp)}</requestorIp>
            else ()
        }
        {
            if ($headerExtended/ns1:requestTimestamp)
            then <requestTimestamp>{fn:data($headerExtended/ns1:requestTimestamp)}</requestTimestamp>
            else ()
        }
        {
            if ($headerExtended/ns1:username)
            then <username>{fn:data($headerExtended/ns1:username)}</username>
            else ()
        }
        {
            if ($headerExtended/ns1:responseCodes)
            then <ns2:responseCodes>
                <responseCode>{fn:data($headerExtended/ns1:responseCodes/ns1:responseCode)}</responseCode>
                <responseMessage>{fn:data($headerExtended/ns1:responseCodes/ns1:responseMessage)}</responseMessage></ns2:responseCodes>
            else ()
        }
    </ns2:serviceHeader>
};

local:func($headerExtended)
