xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.oracle.com/pcbpel/adapter/db/sp/getSimulation";
(:: import schema at "../../../businessServices/getSimulation_v1/schemas/getSimulation_sp.xsd" ::)

declare variable $nsOperation as xs:string external;
declare variable $request as xs:string external;

declare function local:func($nsOperation as xs:string, 
                            $request as xs:string) 
                            as element() (:: schema-element(ns1:InputParameters) ::) {
    <ns1:InputParamters>
      <ns1:I_NS_OPERATION>{$nsOperation }</ns1:I_NS_OPERATION>
      <ns1:I_REQUEST>{$request}</ns1:I_REQUEST>
    </ns1:InputParamters>
};

local:func($nsOperation, $request)
