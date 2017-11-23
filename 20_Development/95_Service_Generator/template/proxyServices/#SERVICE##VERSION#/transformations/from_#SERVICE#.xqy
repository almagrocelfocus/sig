xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns2="http://xmlns.telco.com/#DOMAIN#/#CATEGORY#/pub/#SERVICE#/v#VERSIONUM#";
(:: import schema at "../../../pub/#SERVICE##VERSION#/schemas/#SERVICE#.xsd" ::)

declare variable $OutputParameters as element() external;

declare function local:func($OutputParameters as element()) as element() (:: schema-element(ns2:#SERVICE#Response) ::) {
    <ns2:fixme />
};

local:func($OutputParameters)