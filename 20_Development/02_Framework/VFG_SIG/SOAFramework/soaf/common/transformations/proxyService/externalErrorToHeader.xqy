xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns1="http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
(:: import schema at "../../schemas/headerExtended_v1.xsd" ::)

declare variable $headerExtended as element() (:: schema-element(ns1:headerExtended) ::) external;
declare variable $ExternalErrorCode as xs:string external;
declare variable $ExternalErrorMessage as xs:string? external;
declare variable $system as xs:string? external;

declare function local:func($headerExtended as element() (:: schema-element(ns1:headerExtended) ::), 
                            $ExternalErrorCode as xs:string, 
                            $ExternalErrorMessage as xs:string?, 
                            $system as xs:string?) 
                            as element() (:: schema-element(ns1:headerExtended) ::) {
    <ns1:headerExtended>
        <ns1:originalService>{fn:data($headerExtended/ns1:originalService)}</ns1:originalService>
        <ns1:messageId>{fn:data($headerExtended/ns1:messageId)}</ns1:messageId>
        <ns1:requestId>{fn:data($headerExtended/ns1:requestId)}</ns1:requestId>
        {
            if ($headerExtended/ns1:correlationId)
            then <ns1:correlationId>{fn:data($headerExtended/ns1:correlationId)}</ns1:correlationId>
            else ()
        }
        <ns1:source>{fn:data($headerExtended/ns1:source)}</ns1:source>
        {
            if ($headerExtended/ns1:priority)
            then <ns1:priority>{fn:data($headerExtended/ns1:priority)}</ns1:priority>
            else ()
        }
        {
            if ($headerExtended/ns1:username)
            then <ns1:username>{fn:data($headerExtended/ns1:username)}</ns1:username>
            else ()
        }
        {
            if ($headerExtended/ns1:timestamp)
            then <ns1:timestamp>{fn:data($headerExtended/ns1:timestamp)}</ns1:timestamp>
            else ()
        }
        {
            if ($headerExtended/ns1:externalMessageId)
            then <ns1:externalMessageId>{fn:data($headerExtended/ns1:externalMessageId)}</ns1:externalMessageId>
            else ()
        }
        {
            if ($headerExtended/ns1:businessInterface)
            then 
                <ns1:businessInterface>
                    <ns1:name>{fn:data($headerExtended/ns1:businessInterface/ns1:name)}</ns1:name>
                    <ns1:domain>{fn:data($headerExtended/ns1:businessInterface/ns1:domain)}</ns1:domain>
                    <ns1:category>{fn:data($headerExtended/ns1:businessInterface/ns1:category)}</ns1:category>
                    <ns1:target>{fn:data($headerExtended/ns1:businessInterface/ns1:target)}</ns1:target>
                    <ns1:service>{fn:data($headerExtended/ns1:businessInterface/ns1:service)}</ns1:service>
                    <ns1:version>{fn:data($headerExtended/ns1:businessInterface/ns1:version)}</ns1:version>
                    {
                        if ($headerExtended/ns1:businessInterface/ns1:operation)
                        then <ns1:operation>{fn:data($headerExtended/ns1:businessInterface/ns1:operation)}</ns1:operation>
                        else ()
                    }
                    {
                        if ($headerExtended/ns1:businessInterface/ns1:soapAction)
                        then <ns1:soapAction>{fn:data($headerExtended/ns1:businessInterface/ns1:soapAction)}</ns1:soapAction>
                        else ()
                    }
                </ns1:businessInterface>
            else ()
        }
        {
            if ($headerExtended/ns1:technicalInterface)
            then 
                <ns1:technicalInterface>
                    <ns1:name>{fn:data($headerExtended/ns1:technicalInterface/ns1:name)}</ns1:name>
                    <ns1:domain>{fn:data($headerExtended/ns1:technicalInterface/ns1:domain)}</ns1:domain>
                    <ns1:category>{fn:data($headerExtended/ns1:technicalInterface/ns1:category)}</ns1:category>
                    <ns1:target>{fn:data($headerExtended/ns1:technicalInterface/ns1:target)}</ns1:target>
                    <ns1:service>{fn:data($headerExtended/ns1:technicalInterface/ns1:service)}</ns1:service>
                    <ns1:version>{fn:data($headerExtended/ns1:technicalInterface/ns1:version)}</ns1:version>
                    {
                        if ($headerExtended/ns1:technicalInterface/ns1:operation)
                        then <ns1:operation>{fn:data($headerExtended/ns1:technicalInterface/ns1:operation)}</ns1:operation>
                        else ()
                    }
                    {
                        if ($headerExtended/ns1:technicalInterface/ns1:soapAction)
                        then <ns1:soapAction>{fn:data($headerExtended/ns1:technicalInterface/ns1:soapAction)}</ns1:soapAction>
                        else ()
                    }
                </ns1:technicalInterface>
            else ()
        }
        {
            if ($headerExtended/ns1:contextInformation)
            then 
                <ns1:contextInformation>
                    <ns1:requestorIp>{fn:data($headerExtended/ns1:contextInformation/ns1:requestorIp)}</ns1:requestorIp>
                    <ns1:requestorAgent>{fn:data($headerExtended/ns1:contextInformation/ns1:requestorAgent)}</ns1:requestorAgent>
                    {
                        if ($headerExtended/ns1:contextInformation/ns1:sessionId)
                        then <ns1:sessionId>{fn:data($headerExtended/ns1:contextInformation/ns1:sessionId)}</ns1:sessionId>
                        else ()
                    }
                </ns1:contextInformation>
            else ()
        }
        {
            if ($headerExtended/ns1:repubContext)
            then 
                <ns1:repubContext>
                    <ns1:numberOfRetries>{fn:data($headerExtended/ns1:repubContext/ns1:numberOfRetries)}</ns1:numberOfRetries>
                    <ns1:maxNumberOfRetries>{fn:data($headerExtended/ns1:repubContext/ns1:maxNumberOfRetries)}</ns1:maxNumberOfRetries>
                    <ns1:repubInfo>{fn:data($headerExtended/ns1:repubContext/ns1:repubInfo)}</ns1:repubInfo>
                </ns1:repubContext>
            else ()
        }
        {
            if ($headerExtended/ns1:attributeList)
            then 
                <ns1:attributeList>
                    {
                        for $attribute in $headerExtended/ns1:attributeList/ns1:attribute
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
            for $adapterInformation in $headerExtended/ns1:adapterInformation
            return 
            
                for $adapterInformation1 in $adapterInformation
                return 
                <ns1:adapterInformation>
                    <ns1:targetIp>{fn:data($adapterInformation/ns1:targetIp)}</ns1:targetIp>
                    <ns1:adapterExecutionMili>{fn:data($adapterInformation/ns1:adapterExecutionMili)}</ns1:adapterExecutionMili>
                </ns1:adapterInformation>
        }
        {
              <ns1:responseCodes>
                  <ns1:responseCode></ns1:responseCode>
                  <ns1:responseMessage></ns1:responseMessage>
                          <ns1:externalError>
                              <ns1:errorCode>{fn:data($ExternalErrorCode)}</ns1:errorCode>
                              <ns1:errorMessage>{fn:data($ExternalErrorMessage)}</ns1:errorMessage>
                              <ns1:system>{fn:data($system)}</ns1:system>
                          </ns1:externalError>
              </ns1:responseCodes>
        }
    </ns1:headerExtended>
};

local:func($headerExtended, $ExternalErrorCode, $ExternalErrorMessage, $system)
