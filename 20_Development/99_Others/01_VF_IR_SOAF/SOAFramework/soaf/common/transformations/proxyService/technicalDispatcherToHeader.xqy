xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace he="http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
(:: import schema at "../../schemas/headerExtended_v1.xsd" ::)

declare namespace xf = "http://tempuri.org/SOAFramework/soaf/common/transformations/proxyService/technicalDispatcherToHeader2/";

declare variable $domain as xs:string external;
declare variable $category as xs:string external;
declare variable $service as xs:string external;
declare variable $version as xs:string external;
declare variable $operation as xs:string? external;
declare variable $soapAction as xs:string? external;
declare variable $header as element() (:: schema-element(he:headerExtended) ::) external;

declare function local:func($domain as xs:string, 
                            $category as xs:string, 
                            $service as xs:string, 
                            $version as xs:string, 
                            $operation as xs:string?, 
                            $soapAction as xs:string?, 
                            $header as element() (:: schema-element(he:headerExtended) ::)) 
                            as element() (:: schema-element(he:headerExtended) ::) {
    <he:headerExtended>
        <he:originalService>{fn:data($header/he:originalService)}</he:originalService>
        <he:messageId>{fn:data($header/he:messageId)}</he:messageId>
        <he:requestId>{fn:data($header/he:requestId)}</he:requestId>
        {
            if ($header/he:correlationId)
            then <he:correlationId>{fn:data($header/he:correlationId)}</he:correlationId>
            else ()
        }
        <he:source>{fn:data($header/he:source)}</he:source>
        {
            if ($header/he:priority)
            then <he:priority>{fn:data($header/he:priority)}</he:priority>
            else ()
        }
        {
            if ($header/he:username)
            then <he:username>{fn:data($header/he:username)}</he:username>
            else ()
        }
        {
            if ($header/he:timestamp)
            then <he:timestamp>{fn:data($header/he:timestamp)}</he:timestamp>
            else ()
        }
        {
            if ($header/he:externalMessageId)
            then <he:externalMessageId>{fn:data($header/he:externalMessageId)}</he:externalMessageId>
            else <he:externalMessageId>{fn-bea:uuid()}</he:externalMessageId>
        }
        {
            xf:specificInterface($domain, $category, $service, $version, $operation, $soapAction)
        }
        {
            if ($header/he:contextInformation)
            then 
                <he:contextInformation>
                    <he:requestorIp>{fn:data($header/he:contextInformation/he:requestorIp)}</he:requestorIp>
                    <he:requestorAgent>{fn:data($header/he:contextInformation/he:requestorAgent)}</he:requestorAgent>
                    {
                        if ($header/he:contextInformation/he:sessionId)
                        then <he:sessionId>{fn:data($header/he:contextInformation/he:sessionId)}</he:sessionId>
                        else ()
                    }
                </he:contextInformation>
            else ()
        }
        {
            if ($header/he:repubContext)
            then 
                <he:repubContext>
                    <he:numberOfRetries>{fn:data($header/he:repubContext/he:numberOfRetries)}</he:numberOfRetries>
                    <he:maxNumberOfRetries>{fn:data($header/he:repubContext/he:maxNumberOfRetries)}</he:maxNumberOfRetries>
                    <he:repubInfo>{fn:data($header/he:repubContext/he:repubInfo)}</he:repubInfo>
                </he:repubContext>
            else ()
        }
        {
            if ($header/he:attributeList)
            then 
                <he:attributeList>
                    {
                        for $attribute in $header/he:attributeList/he:attribute
                        return 
                        <he:attribute>
                            <he:name>{fn:data($attribute/he:name)}</he:name>
                            <he:value>{fn:data($attribute/he:value)}</he:value>
                            {
                                if ($attribute/he:context)
                                then <he:context>{fn:data($attribute/he:context)}</he:context>
                                else ()
                            }
                        </he:attribute>
                    }
                </he:attributeList>
            else ()
        }
        {
            for $adapterInformation in $header/he:adapterInformation
            return 
            <he:adapterInformation>
                <he:targetIp>{fn:data($adapterInformation/he:targetIp)}</he:targetIp>
                <he:adapterExecutionMili>{fn:data($adapterInformation/he:adapterExecutionMili)}</he:adapterExecutionMili>
            </he:adapterInformation>
        }
        {
            if ($header/he:responseCodes)
            then 
                <he:responseCodes>
                    <he:responseCode>{fn:data($header/he:responseCodes/he:responseCode)}</he:responseCode>
                    <he:responseMessage>{fn:data($header/he:responseCodes/he:responseMessage)}</he:responseMessage>
                    {
                        if ($header/he:responseCodes/he:externalError)
                        then 
                            <he:externalError>
                                <he:errorCode>{fn:data($header/he:responseCodes/he:externalError/he:errorCode)}</he:errorCode>
                                {
                                    if ($header/he:responseCodes/he:externalError/he:errorMessage)
                                    then <he:errorMessage>{fn:data($header/he:responseCodes/he:externalError/he:errorMessage)}</he:errorMessage>
                                    else ()
                                }
                                {
                                    if ($header/he:responseCodes/he:externalError/he:system)
                                    then <he:system>{fn:data($header/he:responseCodes/he:externalError/he:system)}</he:system>
                                    else ()
                                }
                            </he:externalError>
                        else ()
                    }
                </he:responseCodes>
            else ()
        }
    </he:headerExtended>
};

(: Function to be changed to allow other paths :)
declare function xf:specificInterface($domain as xs:string,
    $category as xs:string,
    $service as xs:string,
    $version as xs:string,
    $operation as xs:string?,
    $soapAction as xs:string?)
    as element(he:technicalInterface) {
        <he:technicalInterface>
        	<he:name>{concat($domain, '/', $category, '/', 'businessServices/', $service, '_v', $version, '/', $service)}</he:name>
        	<he:domain>{$domain}</he:domain>
        	<he:category>{$category}</he:category>
        	<he:target>businessServices</he:target>
        	<he:service>{$service}</he:service>
        	<he:version>{$version}</he:version>
        	{
	        	if(string($soapAction) != '') then <he:soapAction>{$soapAction}</he:soapAction> else (),
	        	if($operation) then <he:operation>{$operation}</he:operation> else ()
        	}
        </he:technicalInterface>
};

local:func($domain, $category, $service, $version, $operation, $soapAction, $header)
