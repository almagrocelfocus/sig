xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns2="http://xmlns.telco.com/SIGFramework/eventHandling/pub/event_newEvent/v1";
(:: import schema at "../../../pub/event_newEvent_v1/schemas/event_newEvent.xsd" ::)

declare variable $OutputParameters as element() external;

declare function local:func($OutputParameters as element()) as element() (:: schema-element(ns2:event_newEventResponse) ::) {
    <ns2:fixme />
};

local:func($OutputParameters)
