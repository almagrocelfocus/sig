xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.oracle.com/pcbpel/adapter/db/sp/createAsyncEvent";
(:: import schema at "../schemas/createAsyncEvent_sp.xsd" ::)

declare variable $requestId as xs:string external;
declare variable $correlationId as xs:string external;
declare variable $externalCorrelationId as xs:string external;
declare variable $payloadRequest as xs:string external;
declare variable $targetService as xs:string external;

declare function local:func($requestId as xs:string, 
                            $correlationId as xs:string, 
                            $externalCorrelationId as xs:string, 
                            $payloadRequest as xs:string, 
                            $targetService as xs:string) 
                            as element() (:: schema-element(ns1:InputParameters) ::) {
    <ns1:InputParameters>
      <ns1:I_REQUEST_ID>{ $requestId }</ns1:I_REQUEST_ID>
      <ns1:I_CORRELATION_ID>{ $correlationId }</ns1:I_CORRELATION_ID>
      <ns1:I_EXTERNAL_CORRELATION_ID>{ $externalCorrelationId }</ns1:I_EXTERNAL_CORRELATION_ID>
      <ns1:I_PAYLOAD_REQUEST>{ $payloadRequest }</ns1:I_PAYLOAD_REQUEST>
      <ns1:I_TARGET_SERVICE>{ $targetService }</ns1:I_TARGET_SERVICE>
    </ns1:InputParameters>
};

local:func($requestId, $correlationId, $externalCorrelationId, $payloadRequest, $targetService)
