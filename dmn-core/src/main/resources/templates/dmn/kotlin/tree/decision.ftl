<#include "drgElementCommon.ftl">
<#if javaPackageName?has_content>
package ${javaPackageName}
</#if>

import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["decision.ftl", "${modelRepository.name(drgElement)}"])
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
            apply(${transformer.drgElementDefaultArgumentsExtraCache(transformer.drgElementDefaultArgumentsExtra(transformer.drgElementArgumentListWithConversionFromString(drgElement)))})
        } catch (e: Exception) {
            logError("Cannot apply decision '${javaClassName}'", e)
            null
        }
    }

    <#if transformer.isCaching()>
    fun apply(${transformer.drgElementSignatureExtra(transformer.drgElementSignatureWithConversionFromString(drgElement))}): ${transformer.drgElementOutputType(drgElement)} {
        return try {
            val ${transformer.cacheVariableName()} = ${transformer.defaultCacheClassName()}()
            apply(${transformer.drgElementArgumentsExtraCache(transformer.drgElementArgumentsExtra(transformer.drgElementArgumentListWithConversionFromString(drgElement)))})
        } catch (e: Exception) {
            logError("Cannot apply decision '${javaClassName}'", e)
            null
        }
    }

    </#if>
    fun apply(${transformer.drgElementSignatureExtraCache(transformer.drgElementSignatureExtra(transformer.drgElementSignatureWithConversionFromString(drgElement)))}): ${transformer.drgElementOutputType(drgElement)} {
        return try {
            apply(${transformer.drgElementArgumentsExtraCache(transformer.drgElementArgumentsExtra(transformer.drgElementArgumentListWithConversionFromString(drgElement)))})
        } catch (e: Exception) {
            logError("Cannot apply decision '${javaClassName}'", e)
            null
        }
    }

    </#if>
    fun apply(${transformer.drgElementSignature(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
        return apply(${transformer.drgElementDefaultArgumentsExtraCache(transformer.drgElementDefaultArgumentsExtra(transformer.drgElementArgumentList(drgElement)))})
    }

    fun apply(${transformer.drgElementSignatureExtraCache(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
        <@applyMethodBody drgElement />
    }
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
    }
}
