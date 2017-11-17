xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.oracle.com/pcbpel/adapter/db/sp/newEventSP";
(:: import schema at "../../../businessServices/newEvent_v1/schemas/newEventSP_sp.xsd" ::)
declare namespace ns2="http://xmlns.telco.com/SIGFramework/eventHandling/pub/newEvent/v1";
(:: import schema at "../../../pub/newEvent_v1/schemas/newEvent.xsd" ::)

declare variable $newEventResponse as element() (:: schema-element(ns1:OutputParameters) ::) external;

declare function local:newEventFunc($newEventResponse as element() (:: schema-element(ns1:OutputParameters) ::)) as element() (:: schema-element(ns2:newEventResponse) ::) {
    <ns2:newEventResponse>
        <ns2:eventId>{fn:data($newEventResponse/ns1:O_EVENT_ID)}</ns2:eventId>
    </ns2:newEventResponse>
};

local:newEventFunc($newEventResponse)
