xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.oracle.com/pcbpel/adapter/db/sp/getAsyncEvent";
(:: import schema at "../schemas/getAsyncEvent_sp.xsd" ::)

declare variable $externalCorrelationId as xs:string external;

declare function local:func($externalCorrelationId as xs:string) as element() (:: schema-element(ns1:InputParameters) ::) {
    <ns1:InputParameters>
      <ns1:I_EXTERNAL_CORRELATION_ID>{ $externalCorrelationId }</ns1:I_EXTERNAL_CORRELATION_ID>
    </ns1:InputParameters>
};

local:func($externalCorrelationId)
