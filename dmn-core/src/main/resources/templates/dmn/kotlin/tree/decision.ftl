<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#include "drgElementCommon.ftl">
<#if javaPackageName?has_content>
package ${javaPackageName}
</#if>
<#assign repository = transformer.getDMNModelRepository() />

import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "${transformer.escapeInString(modelRepository.name(drgElement))}"])
@${transformer.drgElementAnnotationClassName()}(
    namespace = "${javaPackageName}",
    name = "${modelRepository.name(drgElement)}",
    label = "${modelRepository.label(drgElement)}",
    elementKind = ${transformer.elementKindAnnotationClassName()}.${transformer.elementKind(drgElement)},
    expressionKind = ${transformer.expressionKindAnnotationClassName()}.${transformer.expressionKind(drgElement)},
    hitPolicy = ${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)},
    rulesCount = ${modelRepository.rulesCount(drgElement)}
)
class ${javaClassName}(${transformer.decisionConstructorSignature(drgElement)}) : ${decisionBaseClass}() {
    <#if transformer.shouldGenerateApplyWithConversionFromString(drgElement)>
    fun apply(${transformer.drgElementSignatureWithConversionFromString(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
        return try {
            apply(${transformer.drgElementDefaultArgumentListExtraCacheWithConversionFromString(drgElement)})
        } catch (e: Exception) {
            logError("Cannot apply decision '${javaClassName}'", e)
            null
        }
    }

    fun apply(${transformer.drgElementSignatureExtraCacheWithConversionFromString(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
        return try {
            apply(${transformer.drgElementArgumentListExtraCacheWithConversionFromString(drgElement)})
        } catch (e: Exception) {
            logError("Cannot apply decision '${javaClassName}'", e)
            null
        }
    }

    </#if>
    fun apply(${transformer.drgElementSignature(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
        return apply(${transformer.drgElementDefaultArgumentListExtraCache(drgElement)})
    }

    fun apply(${transformer.drgElementSignatureExtraCache(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
        <@applyMethodBody drgElement />
    }
    <#if transformer.isGenerateProto()>

    fun apply(${transformer.drgElementSignatureProto(drgElement)}): ${transformer.qualifiedResponseMessageName(drgElement)} {
        return apply(${transformer.drgElementDefaultArgumentListExtraCacheProto(drgElement)})
    }

    fun apply(${transformer.drgElementSignatureExtraCacheProto(drgElement)}): ${transformer.qualifiedResponseMessageName(drgElement)} {
    <@applyRequest drgElement />
    }
    </#if>
    <@evaluateExpressionMethod drgElement />

    companion object {
        val ${transformer.drgElementMetadataFieldName()} : ${transformer.drgElementMetadataClassName()} = ${transformer.drgElementMetadataClassName()}(
            "${javaPackageName}",
            "${modelRepository.name(drgElement)}",
            "${modelRepository.label(drgElement)}",
            ${transformer.elementKindAnnotationClassName()}.${transformer.elementKind(drgElement)},
            ${transformer.expressionKindAnnotationClassName()}.${transformer.expressionKind(drgElement)},
            ${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)},
            ${modelRepository.rulesCount(drgElement)}
        )
    <#if transformer.isGenerateProto()>

        @JvmStatic
        fun requestToMap(${transformer.requestVariableName(drgElement)}: ${transformer.qualifiedRequestMessageName(drgElement)}): kotlin.collections.Map<String, Any?> {
            <@convertProtoRequestToMap drgElement />
        }

        @JvmStatic
        fun responseToOutput(${transformer.responseVariableName(drgElement)}: ${transformer.qualifiedResponseMessageName(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
            <@convertProtoResponseToOutput drgElement />
        }
    </#if>
    }
}
<#macro makeArgumentsFromRequestMessage drgElement staticContext indent>
    <#assign parameters = transformer.drgElementTypeSignature(drgElement) />
        ${indent}// Create arguments from Request Message
    <#list parameters as parameter>
        ${indent}val ${parameter.left}: ${transformer.toNativeType(parameter.right)}? = ${transformer.extractParameterFromRequestMessage(drgElement, parameter, staticContext)}
    </#list>
</#macro>

<#macro applyRequest drgElement>
    <@makeArgumentsFromRequestMessage drgElement false ""/>

    <#assign outputVariable = "output_" />
    <#assign outputVariableProto = "outputProto_" />
    <#assign responseMessageName = transformer.qualifiedResponseMessageName(drgElement) />
    <#assign outputType = transformer.drgElementOutputFEELType(drgElement) />
        // Invoke apply method
        <#assign outputVariable = "output_" />
        val ${outputVariable}: ${transformer.drgElementOutputType(drgElement)} = apply(${transformer.drgElementArgumentListExtraCache(drgElement)})

        // Convert output to Response Message
        <#assign responseMessageName = transformer.qualifiedResponseMessageName(drgElement) />
        val builder_: ${responseMessageName}.Builder = ${responseMessageName}.newBuilder()
        <#assign outputType = transformer.drgElementOutputFEELType(drgElement) />
        val ${outputVariableProto} = ${transformer.convertValueToProtoNativeType(outputVariable, outputType, false)}
    <#if transformer.isProtoReference(outputType)>
        if (${outputVariableProto} != null) {
            builder_.${transformer.protoSetter(drgElement)}(${outputVariableProto})
        }
    <#else>
        builder_.${transformer.protoSetter(drgElement)}(${outputVariableProto})
    </#if>
        return builder_.build()
</#macro>

<#macro convertProtoRequestToMap drgElement>
     <@makeArgumentsFromRequestMessage drgElement true "    "/>

    <#assign mapVariable = "map_" />
    <#assign reference = repository.makeDRGElementReference(drgElement) />
    <#assign inputDataClosure = transformer.inputDataClosure(reference) />
            // Create map
            val ${mapVariable}: kotlin.collections.MutableMap<String, Any?> = mutableMapOf()
            <#list inputDataClosure as r >
                <#assign inputData = r.element />
                <#assign displayName = repository.displayName(inputData) />
                <#assign variableName = transformer.nativeName(inputData) />
            ${mapVariable}.put("${displayName}", ${variableName})
            </#list>
            return ${mapVariable}
</#macro>

<#macro convertProtoResponseToOutput drgElement>
            // Extract and convert output
            <#assign source = transformer.responseVariableName(drgElement) />
            <#assign memberType = transformer.drgElementOutputFEELType(drgElement) />
            <#assign value>${source}.${transformer.protoGetter(drgElement)}</#assign>
            <#assign exp = transformer.extractMemberFromProtoValue(value, memberType, true) />
            return ${exp}
</#macro>