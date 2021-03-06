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
          <correlationId>{fn:data($headerExtended/ns1:correlationId)}</correlationId>
        <source>{fn:data($headerExtended/ns1:source)}</source>
        {
            if ($headerExtended/ns1:attributeList)
            then <ns2:attributes>
                {
                    for $attribute in $headerExtended/ns1:attributeList/ns1:attribute
                    return 
                    <ns2:attribute>
                        <name>{fn:data($attribute/ns1:name)}</name>
                        <value>{fn:data($attribute/ns1:value)}</value></ns2:attribute>
                }</ns2:attributes>
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
