xquery version "2004-draft";
(:: pragma bea:schema-type-parameter parameter="$interface" type="he:interface" location="../../../common/schemas/headerExtended_v1.xsd" ::)
(:: pragma bea:global-element-return element="sc:getServiceConfigurationList" location="../../../businessServices/serviceConfiguratorService_v1/schemas/serviceConfiguratorService.xsd" ::)

declare namespace sc = "http://ws.esb.telco.com/ServiceConfigurator";
declare namespace he = "http://xmlns.telco.com/SOAFramework/soaf/common/schemas/headerExtended/v1";
declare namespace xf = "http://tempuri.org/SOAFramework/soaf/proxyService/proxyDispatcher_v1/transformation/toGetServiceConfigurationList/";

declare function xf:toGetServiceConfigurationList($interface as element(),
    $username as xs:string?)
    as element(sc:getServiceConfigurationList) {
        <sc:getServiceConfigurationList>
            <domain>{ data($interface/he:domain) }</domain>
            <category>{ data($interface/he:category) }</category>
            <target>{ data($interface/he:target) }</target>
            <service>{ data($interface/he:service) }</service>
            <version>{ data($interface/he:version) }</version>
            {
                for $string in $username
                return
                    <username>{ $string }</username>
            }
        </sc:getServiceConfigurationList>
};

declare variable $interface as element() external;
declare variable $username as xs:string? external;

xf:toGetServiceConfigurationList($interface,
    $username)