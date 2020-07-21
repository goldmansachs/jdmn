syntax = "${transformer.protoVersion()}";

option java_multiple_files = true;
option java_package = "${javaPackageName}";

package ${javaPackageName};

//
// Complex data types
//
<@addMessageTypes dataTypes />

//
// Request / response types
//
<@addMessageTypes responseRequestTypes />

//
// Services
//
<@addServices services />

<#macro addMessageTypes messageTypes>
<#list messageTypes as messageType>
message ${messageType.name} {
    <#list messageType.fields as field>
    <#if field.getTypeModifier(transformer.protoVersion())??>${field.getTypeModifier(transformer.protoVersion())} </#if>${field.getTypeName()} ${field.name} = ${field?index + 1};
    </#list>
}

</#list>
</#macro>

<#macro addServices services>
<#list services as service>
service ${service.name} {
    rpc apply(${service.requestMessageType}) returns (${service.responseMessageType});
}

</#list>
</#macro>
