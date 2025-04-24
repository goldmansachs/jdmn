<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#import "events.ftl" as events />

<#macro applyMethods drgElement>
    <@apply.applyMap drgElement />

    <@apply.applyPojo drgElement />
</#macro>

<#macro applyMap drgElement >
    override fun applyMap(${transformer.drgElementSignatureWithMap(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
    <#if transformer.canGenerateApplyWithMap(drgElement)>
        try {
            return apply(${transformer.drgElementArgumentListWithMap(drgElement)})
        } catch (e: Exception) {
            logError("Cannot apply decision '${javaClassName}'", e)
            return null
        }
    <#else>
        throw ${transformer.constructor(transformer.dmnRuntimeExceptionClassName(), "Not all arguments can be serialized")}
    </#if>
    }
</#macro>

<#macro applyString drgElement >
    <#if transformer.shouldGenerateApplyWithConversionFromString(drgElement)>
    fun applyString(${transformer.drgElementSignatureWithConversionFromString(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
        return try {
            apply(${transformer.drgElementArgumentListWithConversionFromString(drgElement)})
        } catch (e: Exception) {
            logError("Cannot apply decision '${javaClassName}'", e)
            null
        }
    }

    </#if>
</#macro>

<#macro applyPojo drgElement >
    fun apply(${transformer.drgElementSignature(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
        <@applyMethodBody drgElement />
    }
</#macro>

<#--
    Apply method body
-->
<#macro applyMethodBody drgElement>
        try {
        <@events.startDRGElement drgElement/>

        <#if modelRepository.isDecisionTableExpression(drgElement)>
            <@expressionApplyBody drgElement />
       <#elseif modelRepository.isLiteralExpression(drgElement)>
           <@expressionApplyBody drgElement/>
       <#elseif modelRepository.isInvocationExpression(drgElement)>
           <@expressionApplyBody drgElement/>
        <#elseif modelRepository.isContextExpression(drgElement)>
            <@expressionApplyBody drgElement/>
        <#elseif modelRepository.isRelationExpression(drgElement)>
            <@expressionApplyBody drgElement/>
        <#elseif modelRepository.isBKMLinkedToDecision(drgElement)>
             <@expressionApplyBody drgElement/>
        <#elseif modelRepository.isMultiInstanceDecision(drgElement)>
             <@multiInstanceDecisionApplyBody drgElement/>
        <#else >
            logError("${modelRepository.expression(drgElement).class.simpleName} is not implemented yet")
            return null
        </#if>
        } catch (e: Exception) {
            logError("Exception caught in '${modelRepository.name(drgElement)}' evaluation", e)
            return null
        }
</#macro>

<#---
    Evaluate method
-->
<#macro evaluateExpressionMethod drgElement>
    <#if modelRepository.isDecisionTableExpression(drgElement)>

        <@addEvaluateDecisionTableMethod drgElement/>
        <@addRuleMethods drgElement/>
        <@addConversionMethod drgElement/>
    <#elseif modelRepository.isLiteralExpression(drgElement)>

        <@addEvaluateExpressionMethod drgElement/>
    <#elseif modelRepository.isInvocationExpression(drgElement)>

        <@addEvaluateExpressionMethod drgElement/>
    <#elseif modelRepository.isContextExpression(drgElement)>

        <@addEvaluateExpressionMethod drgElement/>
    <#elseif modelRepository.isRelationExpression(drgElement)>

        <@addEvaluateExpressionMethod drgElement/>
    <#elseif modelRepository.isBKMLinkedToDecision(drgElement)>

        <@addEvaluateBKMLinkedToDecisionMethod drgElement/>
    <#elseif modelRepository.isMultiInstanceDecision(drgElement)>

        <@addEvaluateIterationMethod drgElement/>
    </#if>
</#macro>

<#--
    Multi Instance drgElement
-->
<#macro multiInstanceDecisionApplyBody drgElement>
        <#if transformer.isCached(modelRepository.name(drgElement))>
            if (${transformer.cacheVariableName()}.contains("${modelRepository.name(drgElement)}")) {
                // Retrieve value from cache
                var output_: ${transformer.drgElementOutputType(drgElement)} = ${transformer.cacheVariableName()}.lookup("${modelRepository.name(drgElement)}") as ${transformer.drgElementOutputType(drgElement)}

                <@events.endDRGElementAndReturnIndent "    " drgElement "output_" />
            } else {
                // Iterate and aggregate
                var output_: ${transformer.drgElementOutputType(drgElement)} = evaluate(${transformer.drgElementArgumentList(drgElement)})
                ${transformer.cacheVariableName()}.bind("${modelRepository.name(drgElement)}", output_)

                <@events.endDRGElementAndReturnIndent "    " drgElement "output_" />
            }
        <#else>
            // Iterate and aggregate
            var output_: ${transformer.drgElementOutputType(drgElement)} = evaluate(${transformer.drgElementArgumentList(drgElement)})

            <@events.endDRGElementAndReturn drgElement "output_" />
        </#if>
</#macro>

<#macro addEvaluateIterationMethod drgElement>
    private inline fun evaluate(${transformer.drgElementSignature(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
        <@extractParametersFromArgs transformer.drgElementSignatureParameters(drgElement)/>
        <@applySubDecisions drgElement/>
        <#assign multiInstanceDecision = transformer.multiInstanceDecisionLogic(drgElement)/>
        <#assign iterationExpression = multiInstanceDecision.iterationExpression/>
        <#assign iterator = multiInstanceDecision.iterator/>
        <#assign aggregator = multiInstanceDecision.aggregator/>
        <#assign topLevelDecision = multiInstanceDecision.topLevelDecision/>
        <#assign sourceList = transformer.iterationExpressionToNative(drgElement, iterationExpression) />
        <#assign lambdaParamName = transformer.namedElementVariableName(iterator) />
        <#assign lambdaBody = "${transformer.namedElementVariableName(topLevelDecision)}.apply(${transformer.drgElementConvertedArgumentList(topLevelDecision)})" />
        val ${transformer.namedElementVariableName(topLevelDecision)}: ${transformer.qualifiedName(javaPackageName, transformer.drgElementClassName(topLevelDecision))} = ${transformer.qualifiedName(javaPackageName, transformer.drgElementClassName(topLevelDecision))}()
        <#if aggregator == "COLLECT">
        return ${sourceList}?.${transformer.getStream()}?.map({${lambdaParamName} -> ${lambdaBody}})?.collect(Collectors.toList())
        <#elseif aggregator == "SUM">
        return sum(${sourceList}?.${transformer.getStream()}?.map({${lambdaParamName} -> ${lambdaBody}})?.collect(Collectors.toList()))
        <#elseif aggregator == "MIN">
        return = min(${sourceList}?.${transformer.getStream()}?.map({${lambdaParamName} -> ${lambdaBody}})?.collect(Collectors.toList()))
        <#elseif aggregator == "MAX">
        return max(${sourceList}?.${transformer.getStream()}?.map({${lambdaParamName} -> ${lambdaBody}})?.collect(Collectors.toList()))
        <#elseif aggregator == "COUNT">
        return count(${sourceList}?.${transformer.getStream()}?.map({${lambdaParamName} -> ${lambdaBody}})?.collect(Collectors.toList()))
        <#elseif aggregator == "ALLTRUE">
        return ${sourceList}?.${transformer.getStream()}?.allMatch({${lambdaParamName} -> ${lambdaBody} as Boolean})
        <#elseif aggregator == "ANYTRUE">
        return ${sourceList}?.${transformer.getStream()}?.anyMatch({${lambdaParamName} -> ${lambdaBody} as Boolean})
        <#elseif aggregator == "ALLFALSE">
        return ${sourceList}?.${transformer.getStream()}?.allMatch({${lambdaParamName} -> not(${lambdaBody})})
        <#else>
        logError("${aggregator} is not implemented yet")
        return null
        </#if>
    }
</#macro>

<#--
    BKM linked to Decision
-->
<#macro addEvaluateBKMLinkedToDecisionMethod drgElement>
    private inline fun evaluate(${transformer.drgElementSignature(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
        <@extractParametersFromArgs transformer.drgElementSignatureParameters(drgElement)/>
        <@applySubDecisions drgElement/>
        return ${transformer.bkmLinkedToDecisionToNative(drgElement)}
    }
</#macro>

<#--
    Decision table
-->
<#macro addEvaluateDecisionTableMethod drgElement>
    private inline fun evaluate(${transformer.drgElementSignature(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
    <@extractParametersFromArgs transformer.drgElementSignatureParameters(drgElement)/>
    <@applySubDecisions drgElement/>
    <#assign expression = modelRepository.expression(drgElement)>
        <@collectRuleResults drgElement expression />

        // Return results based on hit policy
        var output_: ${transformer.drgElementOutputType(drgElement)}
    <#if modelRepository.isSingleHit(expression.hitPolicy)>
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = ${transformer.defaultValue(drgElement)}
        } else {
            val ruleOutput_: ${transformer.abstractRuleOutputClassName()}? = ruleOutputList_.applySingle(${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)})
            <#if modelRepository.isCompoundDecisionTable(drgElement)>
            output_ = toDecisionOutput(ruleOutput_ as ${transformer.ruleOutputClassName(drgElement)})
            <#else>
            output_ = ruleOutput_?.let({ (ruleOutput_ as ${transformer.ruleOutputClassName(drgElement)}).${transformer.outputClauseVariableName(drgElement, expression.output[0])} })
            </#if>
        }

        return output_
    <#elseif modelRepository.isMultipleHit(expression.hitPolicy)>
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = ${transformer.defaultValue(drgElement)}
            <#if !modelRepository.hasAggregator(expression)>
            if (output_ == null) {
                output_ = this.asList()
            }
            </#if>
        } else {
            val ruleOutputs_: List<${transformer.abstractRuleOutputClassName()}> = ruleOutputList_.applyMultiple(${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)})
        <#if modelRepository.isCompoundDecisionTable(drgElement)>
            <#if modelRepository.hasAggregator(expression)>
            output_ = null
            <#else>
            output_ = ruleOutputs_.stream().map({ ro_ -> toDecisionOutput(ro_ as ${transformer.ruleOutputClassName(drgElement)}) }).collect(Collectors.toList())
            </#if>
        <#else >
            <#if modelRepository.hasAggregator(expression)>
            output_ = ${transformer.aggregator(drgElement, expression, expression.output[0], "ruleOutputs_")}
            <#else>
            output_ = ruleOutputs_.stream().map({ ro_ -> (ro_ as ${transformer.ruleOutputClassName(drgElement)}).${transformer.outputClauseVariableName(drgElement, expression.output[0])} }).collect(Collectors.toList())
            </#if>
        </#if>
        }

        return output_
    <#else>
        logError("Unknown hit policy '" + ${expression.hitPolicy} + "'"))
        return output_
    </#if>
    }

</#macro>

<#macro addRuleMethods drgElement>
    <#assign expression = modelRepository.expression(drgElement)>
    <#list expression.rule>
        <#items as rule>
    @${transformer.ruleAnnotationClassName()}(index = ${rule_index}, annotation = "${transformer.annotationEscapedText(rule)}")
    private fun rule${rule_index}(${transformer.ruleSignature(drgElement)}): ${transformer.abstractRuleOutputClassName()} {
        // Rule metadata
        val ${transformer.drgRuleMetadataFieldName()}: ${transformer.drgRuleMetadataClassName()} = ${transformer.drgRuleMetadataClassName()}(${rule_index}, "${transformer.annotationEscapedText(rule)}")

        <@events.startRule drgElement rule_index />

        // Apply rule
        var output_: ${transformer.ruleOutputClassName(drgElement)} = ${transformer.ruleOutputClassName(drgElement)}(false)
        if (${transformer.condition(drgElement, rule, rule_index)}) {
            <@events.matchRule drgElement rule_index />

            // Compute output
            output_.setMatched(true)
            <#list expression.output as output>
            output_.${transformer.outputClauseVariableName(drgElement, output)} = ${transformer.outputEntryToNative(drgElement, rule.outputEntry[output_index], output_index)}
                <#if modelRepository.isOutputOrderHit(expression.hitPolicy) && transformer.outputClausePriority(drgElement, rule.outputEntry[output_index], output_index)?exists>
            output_.${transformer.outputClausePriorityVariableName(drgElement, output)} = ${transformer.outputClausePriority(drgElement, rule.outputEntry[output_index], output_index)}
                </#if>
            </#list>
            <@addAnnotation drgElement rule rule_index />
        }

        <@events.endRule drgElement rule_index "output_" />

        return output_
    }

        </#items>
    </#list>
</#macro>

<#macro collectRuleResults drgElement expression>
        // Apply rules and collect results
        val ruleOutputList_ = ${transformer.ruleOutputListClassName()}()
    <#assign expression = modelRepository.expression(drgElement)>
    <#list expression.rule>
        <#items as rule>
        <#if modelRepository.isFirstSingleHit(expression.hitPolicy) && modelRepository.atLeastTwoRules(expression)>
        <#if rule?is_first>
        var tempRuleOutput_: ${transformer.abstractRuleOutputClassName()} = rule${rule_index}(${transformer.ruleArgumentList(drgElement)})
        ruleOutputList_.add(tempRuleOutput_)
        var matched_: Boolean = tempRuleOutput_.isMatched()
        <#else >
        if (!matched_) {
            tempRuleOutput_ = rule${rule_index}(${transformer.ruleArgumentList(drgElement)})
            ruleOutputList_.add(tempRuleOutput_)
            matched_ = tempRuleOutput_.isMatched()
        }
        </#if>
        <#else >
        ruleOutputList_.add(rule${rule_index}(${transformer.ruleArgumentList(drgElement)}))
        </#if>
        </#items>
    </#list>
</#macro>

<#macro addConversionMethod drgElement>
    <#if modelRepository.isCompoundDecisionTable(drgElement)>
    fun toDecisionOutput(ruleOutput_: ${transformer.ruleOutputClassName(drgElement)}): ${transformer.drgElementOutputInterfaceName(drgElement)} {
        <#assign className = transformer.drgElementOutputClassName(drgElement)>
        val result_: ${className} = ${transformer.defaultConstructor(className)}
        <#assign expression = modelRepository.expression(drgElement)>
        <#list expression.output as output>
        result_.${transformer.outputClauseVariableName(drgElement, output)} = ruleOutput_.let({ it.${transformer.outputClauseVariableName(drgElement, output)} })
        </#list>
        return result_
    }
    </#if>
</#macro>

<#macro extractParametersFromArgs arguments>
    <#list transformer.extractExtraParametersFromExecutionContext() as stm>
        ${stm}
    </#list>
</#macro>

<#--
    Expression
-->
<#macro expressionApplyBody drgElement>
        <#if transformer.isCached(modelRepository.name(drgElement))>
            if (${transformer.cacheVariableName()}.contains("${modelRepository.name(drgElement)}")) {
                // Retrieve value from cache
                var output_:${transformer.drgElementOutputType(drgElement)} = ${transformer.cacheVariableName()}.lookup("${modelRepository.name(drgElement)}") as ${transformer.drgElementOutputType(drgElement)}

                <@events.endDRGElementAndReturnIndent "    " drgElement "output_" />
            } else {
                // ${transformer.evaluateElementCommentText(drgElement)}
                val output_: ${transformer.drgElementOutputType(drgElement)} = evaluate(${transformer.drgElementArgumentList(drgElement)})
                ${transformer.cacheVariableName()}.bind("${modelRepository.name(drgElement)}", output_)

                <@events.endDRGElementAndReturnIndent "    " drgElement "output_" />
            }
        <#else>
            // ${transformer.evaluateElementCommentText(drgElement)}
            val output_: ${transformer.drgElementOutputType(drgElement)} = evaluate(${transformer.drgElementArgumentList(drgElement)})

            <@events.endDRGElementAndReturn drgElement "output_" />
        </#if>
</#macro>

<#macro addEvaluateExpressionMethod drgElement>
    private inline fun evaluate(${transformer.drgElementSignature(drgElement)}): ${transformer.drgElementOutputType(drgElement)} {
    <#list transformer.extractExtraParametersFromExecutionContext() as stm>
        ${stm}
    </#list>
    <@applySubDecisions drgElement/>
    <#if modelRepository.isFreeTextLiteralExpression(drgElement)>
        return ${transformer.freeTextLiteralExpressionToNative(drgElement)}
    <#else>
        <#assign stm = transformer.expressionToNative(drgElement)>
        <#if transformer.isCompoundStatement(stm)>
            <#list stm.statements as child>
        ${child.text}
            </#list>
        <#else>
        return ${stm.text} as ${transformer.drgElementOutputType(drgElement)}
        </#if>
    </#if>
    }
</#macro>

<#--
    Apply direct sub-decisions
-->
<#macro applySubDecisions drgElement>
    <@applySubDecisionsIndent "" drgElement/>
</#macro>

<#macro applySubDecisionsIndent extraIndent drgElement>
    <#list modelRepository.directSubDecisions(drgElement)>
        ${extraIndent}// Apply child decisions
        <#items as subDecision>
            <#if transformer.isLazyEvaluated(subDecision)>
        ${extraIndent}val ${transformer.drgElementReferenceVariableName(subDecision)}: ${transformer.lazyEvalClassName()}<${transformer.drgElementOutputType(subDecision)}> = ${transformer.lazyEvalClassName()}({ this.${transformer.drgElementReferenceVariableName(subDecision)}.apply(${transformer.drgElementArgumentList(subDecision)}) })
            <#else>
        ${extraIndent}val ${transformer.drgElementReferenceVariableName(subDecision)}: ${transformer.drgElementOutputType(subDecision)} = this.${transformer.drgElementReferenceVariableName(subDecision)}.apply(${transformer.drgElementArgumentList(subDecision)})
            </#if>
        </#items>

    </#list>
</#macro>

<#--
    Annotations
-->
<#macro addAnnotation drgElement rule rule_index>
            <#list transformer.annotations(drgElement, rule)>

            // Add annotations
            <#items as annotation>
            ${transformer.annotationSetVariableName()}.addAnnotation("${drgElement.name}", ${rule_index}, ${annotation})
            </#items>
            </#list>
</#macro>