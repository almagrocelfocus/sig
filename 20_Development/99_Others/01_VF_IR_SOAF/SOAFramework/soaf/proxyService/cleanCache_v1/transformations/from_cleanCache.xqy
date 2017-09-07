xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://ws.esb.telco.com/SoafCacheClean";
(:: import schema at "../../../businessServices/cleanCache_v1/schemas/cleanCache.xsd" ::)
declare namespace ns2="http://xmlns.telco.com/SOAFramework/cleanCache/v1";
(:: import schema at "../schemas/cleanCache.xsd" ::)

declare variable $input as element() (:: schema-element(ns1:soafCacheCleanResponse) ::) external;

declare function local:func($input as element() (:: schema-element(ns1:soafCacheCleanResponse) ::)) as element() (:: schema-element(ns2:cleanCacheResponse) ::) {
    <ns2:cleanCacheResponse>
        {
            if ($input/return)
            then <return>{fn:data($input/return)}</return>
            else ()
        }
    </ns2:cleanCacheResponse>
};

local:func($input)
