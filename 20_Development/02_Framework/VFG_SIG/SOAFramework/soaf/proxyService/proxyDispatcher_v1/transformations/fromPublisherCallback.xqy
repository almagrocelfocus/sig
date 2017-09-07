xquery version "2004-draft";
(:: pragma bea:global-element-parameter parameter="$responseCodes" element="ns0:responseCodes" location="../../../common/schemas/headerExtended_v1.xsd" ::)
(:: pragma bea:global-element-return element="ns1:republishEventResponse" location="../../../callback/publisherCallback_v1/schemas/publisherCallback.xsd" ::)

declare namespace ns1 = "http://ws.esb.telco.com/PublisherCallback";
declare namespace ns0 = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
declare namespace xf = "http://tempuri.org/SOAFramework/soaf/proxyService/proxyDispatcher_v1/transformation/fromPublisherCallback/";

declare function xf:fromPublisherCallback($responseCodes as element(ns0:responseCodes),
    $repubInfo as xs:string?)
    as element(ns1:republishEventResponse) {
        <ns1:republishEventResponse>
            <errorCode>{ data($responseCodes/ns0:responseCode) }</errorCode>
            <errorMessage>{ data($responseCodes/ns0:responseMessage) }</errorMessage>
            {
                for $string in $repubInfo
                return
                    <repubInfo>{ $string }</repubInfo>
            }
        </ns1:republishEventResponse>
};

declare variable $responseCodes as element(ns0:responseCodes) external;
declare variable $repubInfo as xs:string? external;

xf:fromPublisherCallback($responseCodes,
    $repubInfo)