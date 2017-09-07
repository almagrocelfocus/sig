xquery version "1.0" encoding "utf-8";
(:: pragma bea:global-element-parameter parameter="$header" element="soap-env:Header" location="../../schemas/soap_v1.1.xsd" ::)

(:: OracleAnnotationVersion "1.0" ::)

declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";

declare variable $xml as node() external;
declare variable $path as xs:string external;



declare function local:evalPath($path as xs:string, $xml as node()*) as 
node()*
  
{

  if (fn:not(fn:empty($xml))) then
  let $dynamicLog := local:evalPathImpl(tokenize($path, "/"), local:strip-ns($xml))
  return
    if (fn:not(fn:empty($dynamicLog))) then
    <he:dynLog>{fn:concat(fn:node-name($dynamicLog),'_',$dynamicLog/text())}</he:dynLog>
    else
      <he:dynLog/>
    (: More details  
    <he:dynLog>
        <he:dynLogKey>{fn:node-name($dynamicLog)}</he:dynLogKey>
        <he:dynLogValue>{$dynamicLog/text()}</he:dynLogValue>
    </he:dynLog> :)
   else  <he:dynLog/> 
   
};

declare function local:evalPathImpl($steps as xs:string*, $xml as 
node()*) as node()*
{
   if(empty($steps)) then $xml
   else if($steps[1] = "") then local:evalPathImpl(subsequence($steps, 
2), $xml/root())
   else if(starts-with($steps[1], "@")) then 
local:evalPathImpl(subsequence($steps, 2), $xml/@*[name() = 
substring($steps[1], 2)])
   else local:evalPathImpl(subsequence($steps, 2), $xml/*[name() = 
$steps[1]])
};

declare function local:strip-ns($xml as node()) as node() {
     element { local-name($xml) } {
         for $att in $xml/@*
         return
             attribute {local-name($att)} {$att},
         for $child in $xml/node()
         return
             if ($child instance of element())
             then local:strip-ns($child)
             else $child
         }
};

local:evalPath($path, $xml)
