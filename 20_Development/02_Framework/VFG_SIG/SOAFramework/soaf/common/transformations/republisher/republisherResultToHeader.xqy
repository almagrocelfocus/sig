xquery version "2004-draft" encoding "UTF-8";
(:: pragma  parameter="$header" type="xs:anyType" ::)
(:: pragma  type="xs:anyType" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformation/republisher/republisherResultToHeader/";
declare namespace pub = "http://ws.esb.telco.com/Publisher";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";

declare function xf:republisherResultToHeader($storeOperationResponse as element(pub:storeOperationResponse),
    $header as element(*))
    as element(*)* {
		$header/*,
		<he:responseCodes>
        	<he:responseCode>{'0000000'}</he:responseCode>
       		<he:responseMessage>Success</he:responseMessage>
    	</he:responseCodes>
};

declare variable $storeOperationResponse as element(pub:storeOperationResponse) external;
declare variable $header as element(*) external;

xf:republisherResultToHeader($storeOperationResponse,
    $header)