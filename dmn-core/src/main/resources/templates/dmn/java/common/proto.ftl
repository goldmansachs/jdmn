<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#macro addMessageTypes messageTypes>
<#list messageTypes as messageType>
message ${messageType.name} {
    <#list messageType.fields as field>
    <#if field.getTypeModifier(transformer.getProtoVersion())??>${field.getTypeModifier(transformer.getProtoVersion())} </#if>${field.getTypeName()} ${field.name} = ${field?index + 1};
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
syntax = "${transformer.getProtoVersion()}";

option java_multiple_files = true;
option java_package = "${protoPackageName}";

package ${protoPackageName};

<#if dataTypes?has_content>
//
// Complex data types
//
<@addMessageTypes dataTypes />
</#if>
<#if responseRequestTypes?has_content>
//
// Request / response types
//
<@addMessageTypes responseRequestTypes />
</#if>
<#if services?has_content>
//
// Services
//
<@addServices services />
</#if>