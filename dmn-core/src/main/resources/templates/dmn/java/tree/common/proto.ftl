<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#macro protoAdapters drgElement>
    <#if transformer.isGenerateProto()>

    public static java.util.Map<String, Object> requestToMap(${transformer.qualifiedRequestMessageName(drgElement)} ${transformer.requestVariableName(drgElement)}) {
        <@convertProtoRequestToMap drgElement />
    }

    public static ${transformer.drgElementOutputType(drgElement)} responseToOutput(${transformer.qualifiedResponseMessageName(drgElement)} ${transformer.responseVariableName(drgElement)}) {
        <@convertProtoResponseToOutput drgElement />
    }
    </#if>
</#macro>

<#macro applyMethod drgElement>
    <#if transformer.isGenerateProto()>

    public ${transformer.qualifiedResponseMessageName(drgElement)} applyProto(${transformer.drgElementSignatureProto(drgElement)}) {
    <@applyRequest drgElement />
    }
    </#if>
</#macro>

<#macro makeArgumentsFromRequestMessage drgElement staticContext>
    <#assign parameters = transformer.drgElementTypeSignature(drgElement) />
        // Create arguments from Request Message
    <#list parameters as parameter>
        ${transformer.toNativeType(parameter.right)} ${parameter.left} = ${transformer.extractParameterFromRequestMessage(drgElement, parameter, staticContext)};
    </#list>
</#macro>

<#macro applyRequest drgElement>
    <@makeArgumentsFromRequestMessage drgElement false/>

    <#assign outputVariable = "output_" />
    <#assign outputVariableProto = "outputProto_" />
    <#assign responseMessageName = transformer.qualifiedResponseMessageName(drgElement) />
    <#assign outputType = transformer.drgElementOutputFEELType(drgElement) />
        // Invoke apply method
        ${transformer.drgElementOutputType(drgElement)} ${outputVariable} = apply(${transformer.drgElementArgumentList(drgElement)});

        // Convert output to Response Message
        ${responseMessageName}.Builder builder_ = ${responseMessageName}.newBuilder();
        ${transformer.drgElementOutputTypeProto(drgElement)} ${outputVariableProto} = ${transformer.convertValueToProtoNativeType(outputVariable, outputType, false)};
    <#if transformer.isProtoReference(outputType)>
        if (${outputVariableProto} != null) {
            builder_.${transformer.protoSetter(drgElement, "${outputVariableProto}")};
        }
    <#else>
        builder_.${transformer.protoSetter(drgElement, "${outputVariableProto}")};
    </#if>
        return builder_.build();
</#macro>

<#macro convertProtoRequestToMap drgElement>
    <@makeArgumentsFromRequestMessage drgElement true/>

    <#assign mapVariable = "map_" />
    <#assign reference = modelRepository.makeDRGElementReference(drgElement) />
    <#assign inputDataClosure = transformer.inputDataClosure(reference) />
        // Create map
        java.util.Map<String, Object> ${mapVariable} = new java.util.LinkedHashMap<>();
        <#list inputDataClosure as r >
            <#assign inputData = r.element />
            <#assign displayName = modelRepository.displayName(inputData) />
            <#assign variableName = transformer.nativeName(inputData) />
        ${mapVariable}.put("${displayName}", ${variableName});
        </#list>
        return ${mapVariable};
</#macro>

<#macro convertProtoResponseToOutput drgElement>
        // Extract and convert output
        <#assign source = transformer.responseVariableName(drgElement) />
        <#assign memberType = transformer.drgElementOutputFEELType(drgElement) />
        <#assign value>${source}.${transformer.protoGetter(drgElement)}</#assign>
        <#assign exp = transformer.extractMemberFromProtoValue(value, memberType, true) />
        return ${exp};
</#macro>
