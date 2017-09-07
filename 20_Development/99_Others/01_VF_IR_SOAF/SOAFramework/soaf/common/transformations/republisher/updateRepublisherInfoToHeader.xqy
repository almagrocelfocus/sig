xquery version "2004-draft" encoding "UTF-8";
(:: pragma bea:global-element-parameter parameter="$header" element="ns0:Header" location="../../schemas/soap_v1.1.xsd" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/republisher/updateRepublisherInfoToHeader/";
declare namespace ns0 = "http://schemas.xmlsoap.org/soap/envelope/";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";

declare function xf:updateRepublisherInfoToHeader($header as element(ns0:Header)?,
    $repubInfo as xs:string?)
    as element()* {
        $header/*[not(self::he:headerExtended)],
        xf:remove-elements-deep($header/he:headerExtended, 'repubInfo', $repubInfo)
};


declare function xf:remove-elements-deep( $nodes as node()*,
	$nodeName as xs:string,
	$nodeValue as xs:string?)
	as node()* {
	
	for $node in $nodes
	return
		if ($node instance of element())
		then 
			if (local-name($node) = 'repubInfo')
			then 
				element {node-name($node)}
				{
					$node/@*,
					$nodeValue
				}
			else 
				element {node-name($node)}
				{
					$node/@*,
					xf:remove-elements-deep($node/node(), $nodeName, $nodeValue)
				}
     	else 
     		$node
};

declare variable $header as element(ns0:Header)? external;
declare variable $repubInfo as xs:string? external;

xf:updateRepublisherInfoToHeader($header,
    $repubInfo)