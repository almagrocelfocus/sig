xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
(:: import schema at "../../../../../SOAFramework/soaf/common/schemas/headerExtended_v1.xsd" ::)

declare variable $headerExtended as element() (:: schema-element(ns1:headerExtended) ::) external;
declare variable $ResultCode as xs:string external;
declare variable $ResultMessage as xs:string? external;

declare function local:func($headerHextended as element() (:: schema-element(ns1:headerExtended) ::),
                            $ResultCode as xs:string, 
                            $ResultMessage as xs:string?) 
                            as element() (:: schema-element(ns1:headerExtended) ::) {
    <ns1:headerExtended>
        <ns1:originalService>{fn:data($headerHextended/ns1:originalService)}</ns1:originalService>
        <ns1:messageId>{fn:data($headerHextended/ns1:messageId)}</ns1:messageId>
        <ns1:requestId>{fn:data($headerHextended/ns1:requestId)}</ns1:requestId>
        {
            if ($headerHextended/ns1:correlationId)
            then <ns1:correlationId>{fn:data($headerHextended/ns1:correlationId)}</ns1:correlationId>
            else ()
        }
        <ns1:source>{fn:data($headerHextended/ns1:source)}</ns1:source>
        {
            if ($headerHextended/ns1:priority)
            then <ns1:priority>{fn:data($headerHextended/ns1:priority)}</ns1:priority>
            else ()
        }
        {
            if ($headerHextended/ns1:username)
            then <ns1:username>{fn:data($headerHextended/ns1:username)}</ns1:username>
            else ()
        }
        {
            if ($headerHextended/ns1:timestamp)
            then <ns1:timestamp>{fn:data($headerHextended/ns1:timestamp)}</ns1:timestamp>
            else ()
        }
        {
            if ($headerHextended/ns1:externalMessageId)
            then <ns1:externalMessageId>{fn:data($headerHextended/ns1:externalMessageId)}</ns1:externalMessageId>
            else ()
        }
        {
            if ($headerHextended/ns1:businessInterface)
            then 
                <ns1:businessInterface>
                    <ns1:name>{fn:data($headerHextended/ns1:businessInterface/ns1:name)}</ns1:name>
                    <ns1:domain>{fn:data($headerHextended/ns1:businessInterface/ns1:domain)}</ns1:domain>
                    <ns1:category>{fn:data($headerHextended/ns1:businessInterface/ns1:category)}</ns1:category>
                    <ns1:target>{fn:data($headerHextended/ns1:businessInterface/ns1:target)}</ns1:target>
                    <ns1:service>{fn:data($headerHextended/ns1:businessInterface/ns1:service)}</ns1:service>
                    <ns1:version>{fn:data($headerHextended/ns1:businessInterface/ns1:version)}</ns1:version>
                    {
                        if ($headerHextended/ns1:businessInterface/ns1:operation)
                        then <ns1:operation>{fn:data($headerHextended/ns1:businessInterface/ns1:operation)}</ns1:operation>
                        else ()
                    }
                    {
                        if ($headerHextended/ns1:businessInterface/ns1:soapAction)
                        then <ns1:soapAction>{fn:data($headerHextended/ns1:businessInterface/ns1:soapAction)}</ns1:soapAction>
                        else ()
                    }
                </ns1:businessInterface>
            else ()
        }
        {
            if ($headerHextended/ns1:technicalInterface)
            then 
                <ns1:technicalInterface>
                    <ns1:name>{fn:data($headerHextended/ns1:technicalInterface/ns1:name)}</ns1:name>
                    <ns1:domain>{fn:data($headerHextended/ns1:technicalInterface/ns1:domain)}</ns1:domain>
                    <ns1:category>{fn:data($headerHextended/ns1:technicalInterface/ns1:category)}</ns1:category>
                    <ns1:target>{fn:data($headerHextended/ns1:technicalInterface/ns1:target)}</ns1:target>
                    <ns1:service>{fn:data($headerHextended/ns1:technicalInterface/ns1:service)}</ns1:service>
                    <ns1:version>{fn:data($headerHextended/ns1:technicalInterface/ns1:version)}</ns1:version>
                    {
                        if ($headerHextended/ns1:technicalInterface/ns1:operation)
                        then <ns1:operation>{fn:data($headerHextended/ns1:technicalInterface/ns1:operation)}</ns1:operation>
                        else ()
                    }
                    {
                        if ($headerHextended/ns1:technicalInterface/ns1:soapAction)
                        then <ns1:soapAction>{fn:data($headerHextended/ns1:technicalInterface/ns1:soapAction)}</ns1:soapAction>
                        else ()
                    }
                </ns1:technicalInterface>
            else ()
        }
        {
            if ($headerHextended/ns1:contextInformation)
            then 
                <ns1:contextInformation>
                    <ns1:requestorIp>{fn:data($headerHextended/ns1:contextInformation/ns1:requestorIp)}</ns1:requestorIp>
                    <ns1:requestorAgent>{fn:data($headerHextended/ns1:contextInformation/ns1:requestorAgent)}</ns1:requestorAgent>
                    {
                        if ($headerHextended/ns1:contextInformation/ns1:sessionId)
                        then <ns1:sessionId>{fn:data($headerHextended/ns1:contextInformation/ns1:sessionId)}</ns1:sessionId>
                        else ()
                    }
                </ns1:contextInformation>
            else ()
        }
        {
            if ($headerHextended/ns1:repubContext)
            then 
                <ns1:repubContext>
                    <ns1:numberOfRetries>{fn:data($headerHextended/ns1:repubContext/ns1:numberOfRetries)}</ns1:numberOfRetries>
                    <ns1:maxNumberOfRetries>{fn:data($headerHextended/ns1:repubContext/ns1:maxNumberOfRetries)}</ns1:maxNumberOfRetries>
                    <ns1:repubInfo>{fn:data($headerHextended/ns1:repubContext/ns1:repubInfo)}</ns1:repubInfo>
                </ns1:repubContext>
            else ()
        }
        {
            if ($headerHextended/ns1:attributeList)
            then 
                <ns1:attributeList>
                    {
                        for $attribute in $headerHextended/ns1:attributeList/ns1:attribute
                        return 
                        <ns1:attribute>
                            <ns1:name>{fn:data($attribute/ns1:name)}</ns1:name>
                            <ns1:value>{fn:data($attribute/ns1:value)}</ns1:value>
                            {
                                if ($attribute/ns1:context)
                                then <ns1:context>{fn:data($attribute/ns1:context)}</ns1:context>
                                else ()
                            }
                        </ns1:attribute>
                    }
                </ns1:attributeList>
            else ()
        }
        {
            for $adapterInformation in $headerHextended/ns1:adapterInformation
            return 
            <ns1:adapterInformation>
                <ns1:targetIp>{fn:data($adapterInformation/ns1:targetIp)}</ns1:targetIp>
                <ns1:adapterExecutionMili>{fn:data($adapterInformation/ns1:adapterExecutionMili)}</ns1:adapterExecutionMili>
            </ns1:adapterInformation>
        }
        <ns1:responseCodes>
            <ns1:responseCode>{fn:data($ResultCode)}</ns1:responseCode>
            <ns1:responseMessage>{fn:data($ResultMessage)}</ns1:responseMessage>
            {
                if ($headerHextended/ns1:responseCodes/ns1:externalError)
                then 
                    <ns1:externalError>
                        <ns1:errorCode>{fn:data($headerHextended/ns1:responseCodes/ns1:externalError/ns1:errorCode)}</ns1:errorCode>
                        {
                            if ($headerHextended/ns1:responseCodes/ns1:externalError/ns1:errorMessage)
                            then <ns1:errorMessage>{fn:data($headerHextended/ns1:responseCodes/ns1:externalError/ns1:errorMessage)}</ns1:errorMessage>
                            else ()
                        }
                        {
                            if ($headerHextended/ns1:responseCodes/ns1:externalError/ns1:system)
                            then <ns1:system>{fn:data($headerHextended/ns1:responseCodes/ns1:externalError/ns1:system)}</ns1:system>
                            else ()
                        }
                    </ns1:externalError>
                else ()
            }
        </ns1:responseCodes>
    </ns1:headerExtended>
};

local:func( $headerExtended, $ResultCode, $ResultMessage)
