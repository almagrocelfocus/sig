xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.telco.com/#DOMAIN#/#CATEGORY#/pub/#SERVICE#/v#VERSIONUM#";
(:: import schema at "../../../pub/#SERVICE##VERSION#/schemas/#SERVICE#.xsd" ::)

declare variable $#SERVICE#Request as element() (:: schema-element(ns1:#SERVICE#Request) ::) external;

declare function local:func($#SERVICE#Request as element() (:: schema-element(ns1:#SERVICE#Request) ::)) as element(){
	<fixme />
};

local:func($#SERVICE#Request)