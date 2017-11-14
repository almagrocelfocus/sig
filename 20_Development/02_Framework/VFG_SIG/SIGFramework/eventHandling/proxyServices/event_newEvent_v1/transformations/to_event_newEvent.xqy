xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.telco.com/SIGFramework/eventHandling/pub/event_newEvent/v1";
(:: import schema at "../../../pub/event_newEvent_v1/schemas/event_newEvent.xsd" ::)

declare variable $event_newEventRequest as element() (:: schema-element(ns1:event_newEventRequest) ::) external;

declare function local:func($event_newEventRequest as element() (:: schema-element(ns1:event_newEventRequest) ::)) as element(){
	<fixme />
};

local:func($event_newEventRequest)
