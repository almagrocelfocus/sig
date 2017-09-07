xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.telco.com/SOAFrameworkBackendSimulator/wsDispatcher/v1";
(:: import schema at "../../proxyServices/wsDispatcher_v1/schemas/wsDispatcher.xsd" ::)

declare variable $uri as xs:string external;
declare variable $operation as xs:string external;
declare variable $request as xs:string external;

declare function local:func($uri as xs:string, 
                            $operation as xs:string, 
                            $request as xs:string) 
                            as element() (:: schema-element(ns1:wsDispatcherRequest) ::) {
    <ns1:wsDispatcherRequest>
      <uri>{$uri}</uri>
      <operation>{$operation}</operation>
      <request>{$request}</request>
    </ns1:wsDispatcherRequest>
};

local:func($uri, $operation, $request)
