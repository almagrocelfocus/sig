xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns2="http://ws.esb.telco.com/SoafCacheClean";
(:: import schema at "../../../businessServices/cleanCache_v1/schemas/cleanCache.xsd" ::)
declare namespace ns1="http://xmlns.telco.com/SOAFramework/cleanCache/v1";
(:: import schema at "../schemas/cleanCache.xsd" ::)

declare variable $input as element() (:: schema-element(ns1:cleanCacheRequest) ::) external;

declare function local:func($input as element() (:: schema-element(ns1:cleanCacheRequest) ::)) as element() (:: schema-element(ns2:soafCacheClean) ::) {
    <ns2:soafCacheClean>
        {
            if ($input/delayMilliseconds)
            then <delayMilliseconds>{fn:data($input/delayMilliseconds)}</delayMilliseconds>
            else ()
        }
    </ns2:soafCacheClean>
};

local:func($input)
