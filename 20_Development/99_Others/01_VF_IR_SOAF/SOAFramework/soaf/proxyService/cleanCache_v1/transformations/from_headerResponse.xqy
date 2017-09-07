xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.telco.com/SOAFramework/soaf/common/schemas/commonTypes/v1";
(:: import schema at "../../../common/schemas/commonTypes_v1.xsd" ::)

declare variable $source as xs:string external;
declare variable $responseCode as xs:string external;
declare variable $responseDescription as xs:string external;

declare function local:func($source as xs:string,
                            $responseCode as xs:string, 
                            $responseDescription as xs:string) 
                            as element() (:: element(*, ns1:serviceHeader) ::) {
    <ns1:serviceHeader>
        <source>{fn:data($source)}</source>
        <ns1:responseCodes>
            <responseCode>{fn:data($responseCode)}</responseCode>
            <responseMessage>{fn:data($responseDescription)}</responseMessage>
        </ns1:responseCodes>
    </ns1:serviceHeader>
};

local:func($source,$responseCode, $responseDescription)
