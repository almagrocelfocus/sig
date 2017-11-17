xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns2="http://xmlns.oracle.com/pcbpel/adapter/db/sp/newEventSP";
(:: import schema at "../../../businessServices/newEvent_v1/schemas/newEventSP_sp.xsd" ::)
declare namespace ns1="http://xmlns.telco.com/SIGFramework/eventHandling/pub/newEvent/v1";
(:: import schema at "../../../pub/newEvent_v1/schemas/newEvent.xsd" ::)

declare variable $newEventProxy as element() (:: schema-element(ns1:newEventRequest) ::) external;

declare function local:newEventFunc($newEventProxy as element() (:: schema-element(ns1:newEventRequest) ::)) as element() (:: schema-element(ns2:InputParameters) ::) {
    <ns2:InputParameters>
        <ns2:I_EVENT_NAME>{fn:data($newEventProxy/ns1:eventName)}</ns2:I_EVENT_NAME>
        <ns2:I_CUSTOMER_ID>{fn:data($newEventProxy/ns1:customerId)}</ns2:I_CUSTOMER_ID>
        <ns2:I_EVENT_DETAILS>{$newEventProxy/ns1:eventDetails/*}</ns2:I_EVENT_DETAILS>
        
    </ns2:InputParameters>
};

local:newEventFunc($newEventProxy)
