<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#--
    Apply method body
-->
<#macro applyMethodBody drgElement>
        try:
        <@startDRGElement drgElement/>

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
        <#else >
            self.logError("${modelRepository.expression(drgElement).class.simpleName} is not implemented yet")
            return None
        </#if>
        except Exception as e:
            self.logError("Exception caught in '${modelRepository.name(drgElement)}' evaluation", e)
            return None
</#macro>

<#macro applyServiceMethodBody drgElement>
        try:
        <@startDRGElement drgElement/>

        <@bindInputDecisions drgElement/>
        <@expressionApplyBody drgElement/>
        except Exception as e:
            self.logError("Exception caught in '${modelRepository.name(drgElement)}' evaluation", e)
            return None
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
    </#if>
</#macro>

<#--
    Sub decisions fields
-->
<#macro setSubDecisionFields drgElement>
    <#list modelRepository.directSubDecisions(drgElement)>
        <#items as subDecision>
        <#assign member = transformer.drgElementReferenceVariableName(subDecision)>
        <#if transformer.isSingletonDecision()>
            <#assign defaultValue = transformer.singletonDecisionInstance(transformer.qualifiedName(subDecision))/>
        <#else>
            <#assign defaultValue = transformer.defaultConstructor(transformer.qualifiedName(subDecision))/>
        </#if>
        self.${member} = ${defaultValue} if ${member} is None else ${member}
        </#items>
    </#list>
</#macro>

<#--
    Decision table
-->
<#macro addEvaluateDecisionTableMethod drgElement>
    def evaluate(${transformer.drgElementSignature(drgElement)}) -> ${transformer.drgElementOutputType(drgElement)}:
    <@applySubDecisions drgElement/>
    <#assign expression = modelRepository.expression(drgElement)>
        <@collectRuleResults drgElement expression />

        # Return results based on hit policy
        output_: ${transformer.drgElementOutputType(drgElement)}
    <#if modelRepository.isSingleHit(expression.hitPolicy)>
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = ${transformer.defaultValue(drgElement)}
        else:
            ruleOutput_: ${transformer.abstractRuleOutputClassName()} = ruleOutputList_.applySingle(${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)})
            <#if modelRepository.isCompoundDecisionTable(drgElement)>
            output_ = self.toDecisionOutput(ruleOutput_)
            <#else>
            output_ = None if ruleOutput_ is None else ruleOutput_.${transformer.outputClauseVariableName(drgElement, expression.output[0])}
            </#if>

        return output_
    <#elseif modelRepository.isMultipleHit(expression.hitPolicy)>
        if ruleOutputList_.noMatchedRules():
            # Default value
            output_ = ${transformer.defaultValue(drgElement)}
        else:
            ruleOutputs_: typing.List[${transformer.abstractRuleOutputClassName()}] = ruleOutputList_.applyMultiple(${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)})
        <#if modelRepository.isCompoundDecisionTable(drgElement)>
            <#if modelRepository.hasAggregator(expression)>
            output_ = None
            <#else>
            output_ = list(map(lambda o: toDecisionOutput(o), ruleOutputs_))
            </#if>
        <#else >
            <#if modelRepository.hasAggregator(expression)>
            output_ = ${transformer.aggregator(drgElement, expression, expression.output[0], "ruleOutputs_")}
            <#else>
            output_ = list(map(lambda o: o.${transformer.outputClauseVariableName(drgElement, expression.output[0])}, ruleOutputs_))
            </#if>
        </#if>

        return output_
    <#else>
        self.logError("Unknown hit policy '" + ${expression.hitPolicy} + "'"))
        return output_
    </#if>

</#macro>

<#macro addRuleMethods drgElement>
    <#assign expression = modelRepository.expression(drgElement)>
    <#list expression.rule>
        <#items as rule>
    def rule${rule_index}(self, ${transformer.ruleSignature(drgElement)}) -> ${transformer.abstractRuleOutputClassName()}:
        # Rule metadata
        ${transformer.drgRuleMetadataFieldName()}: ${transformer.drgRuleMetadataClassName()} = ${transformer.drgRuleMetadataClassName()}(${rule_index}, "${transformer.annotationEscapedText(rule)}")

        <@startRule drgElement rule_index />

        # Apply rule
        output_: ${transformer.qualifiedRuleOutputClassName(drgElement)} = ${transformer.qualifiedRuleOutputClassName(drgElement)}(False)
        if (${transformer.condition(drgElement, rule, rule_index)}):
            <@matchRule drgElement rule_index />

            # Compute output
            output_.setMatched(True)
            <#list expression.output as output>
            output_.${transformer.outputClauseVariableName(drgElement, output)} = ${transformer.outputEntryToNative(drgElement, rule.outputEntry[output_index], output_index)}
                <#if modelRepository.isOutputOrderHit(expression.hitPolicy) && transformer.outputClausePriority(drgElement, rule.outputEntry[output_index], output_index)?exists>
            output_.${transformer.outputClausePriorityVariableName(drgElement, output)} = ${transformer.outputClausePriority(drgElement, rule.outputEntry[output_index], output_index)}
                </#if>
            </#list>

            <@addAnnotation drgElement rule rule_index />

        <@endRule drgElement rule_index "output_" />

        return output_
        <#if rule_has_next>

        </#if>
        </#items>
    </#list>
</#macro>

<#macro collectRuleResults drgElement expression>
        # Apply rules and collect results
        ruleOutputList_ = ${transformer.ruleOutputListClassName()}()
    <#assign expression = modelRepository.expression(drgElement)>
    <#list expression.rule>
        <#items as rule>
        <#if modelRepository.isFirstSingleHit(expression.hitPolicy) && modelRepository.atLeastTwoRules(expression)>
        <#if rule?is_first>
        tempRuleOutput_: ${transformer.abstractRuleOutputClassName()} = self.rule${rule_index}(${transformer.ruleArgumentList(drgElement)})
        ruleOutputList_.add(tempRuleOutput_)
        matched_: bool = tempRuleOutput_.isMatched()
        <#else >
        if not matched_:
            tempRuleOutput_ = self.rule${rule_index}(${transformer.ruleArgumentList(drgElement)})
            ruleOutputList_.add(tempRuleOutput_)
            matched_ = tempRuleOutput_.isMatched()
        </#if>
        <#else >
        ruleOutputList_.add(self.rule${rule_index}(${transformer.ruleArgumentList(drgElement)}))
        </#if>
        </#items>
    </#list>
</#macro>

<#macro addConversionMethod drgElement>
    <#if modelRepository.isCompoundDecisionTable(drgElement)>

    def toDecisionOutput(self, ruleOutput_: ${transformer.qualifiedName(javaPackageName, transformer.ruleOutputClassName(drgElement))}) -> ${transformer.drgElementOutputInterfaceName(drgElement)}:
        <#assign className = transformer.drgElementOutputClassName(drgElement)>
        result_: ${className} = ${transformer.defaultConstructor(className)}
        <#assign expression = modelRepository.expression(drgElement)>
        <#list expression.output as output>
        <#assign member = transformer.outputClauseVariableName(drgElement, output)/>
        result_.${member} = None if ruleOutput_ is None else ruleOutput_.${member}
        </#list>
        return result_
    </#if>
</#macro>

<#--
    Expression
-->
<#macro expressionApplyBody drgElement>
        <#if transformer.isCached(modelRepository.name(drgElement))>
            if cache_.contains("${modelRepository.name(drgElement)}"):
                # Retrieve value from cache
                output_: ${transformer.drgElementOutputType(drgElement)} = cache_.lookup("${modelRepository.name(drgElement)}")

                <@endDRGElementAndReturnIndent "    " drgElement "output_" />
            else:
                # ${transformer.evaluateElementCommentText(drgElement)}
                output_: ${transformer.drgElementOutputType(drgElement)} = self.evaluate(${transformer.drgElementArgumentList(drgElement)})
                cache_.bind("${modelRepository.name(drgElement)}", output_)

                <@endDRGElementAndReturnIndent "    " drgElement "output_" />
        <#else>
            # ${transformer.evaluateElementCommentText(drgElement)}
            output_: ${transformer.drgElementOutputType(drgElement)} = self.evaluate(${transformer.drgElementArgumentList(drgElement)})

            <@endDRGElementAndReturn drgElement "output_" />
        </#if>
</#macro>

<#macro addEvaluateExpressionMethod drgElement>
    def evaluate(${transformer.drgElementSignature(drgElement)}) -> ${transformer.drgElementOutputType(drgElement)}:
    <@applySubDecisions drgElement/>
    <#assign stm = transformer.expressionToNative(drgElement)>
    <#if transformer.isCompoundStatement(stm)>
        <#list stm.statements as child>
        ${child.text}
        </#list>
    <#else>
        return ${stm.text}
    </#if>
</#macro>

<#macro addEvaluateServiceMethod drgElement>
    def evaluate(${transformer.drgElementSignature(drgElement)}) -> ${transformer.drgElementOutputType(drgElement)}:
    <@applySubDecisions drgElement/>
    <#assign stm = transformer.serviceToNative(drgElement)>
    <#if transformer.isCompoundStatement(stm)>
        <#list stm.statements as child>
        ${child.text}
        </#list>
    <#else>
        return ${stm.text}
    </#if>
</#macro>

<#--
    Apply direct sub-decisions
-->
<#macro applySubDecisions drgElement>
    <@applySubDecisionsIndent "" drgElement/>
</#macro>

<#macro applySubDecisionsIndent extraIndent drgElement>
    <#list modelRepository.directSubDecisions(drgElement)>
        ${extraIndent}# Apply child decisions
        <#items as subDecision>
        <#assign result>self.${transformer.drgElementReferenceVariableName(subDecision)}.apply(${transformer.drgElementArgumentList(subDecision)})</#assign>
            <#if transformer.isLazyEvaluated(subDecision)>
        <#assign lazyEvalClassQName>${transformer.lazyEvalClassName()}</#assign>
        ${extraIndent}${transformer.drgElementReferenceVariableName(subDecision)}: ${lazyEvalClassQName} = ${lazyEvalClassQName}(lambda: ${result})
            <#else>
        ${extraIndent}${transformer.drgElementReferenceVariableName(subDecision)}: ${transformer.drgElementOutputType(subDecision)} = ${result}
            </#if>
        </#items>

    </#list>
</#macro>

<#--
    Bind direct input decisions for DS
-->
<#macro bindInputDecisions drgElement>
    <@bindInputDecisionsIndent "" drgElement/>
</#macro>

<#macro bindInputDecisionsIndent extraIndent drgElement>
    <#list modelRepository.directInputDecisions(drgElement)>
            ${extraIndent}# Bind input decisions
        <#items as inputDecision>
            ${extraIndent}cache_.bind("${modelRepository.name(inputDecision.element)}", ${transformer.drgElementReferenceVariableName(inputDecision)})
        </#items>

    </#list>
</#macro>

<#--
    Events
-->
<#macro startDRGElement drgElement>
            # ${transformer.startElementCommentText(drgElement)}
            ${transformer.namedElementVariableName(drgElement)}StartTime_ = <@currentTimeMillis/>
            ${transformer.argumentsVariableName(drgElement)} = ${transformer.defaultConstructor(transformer.argumentsClassName())}
            <#assign elementNames = transformer.drgElementArgumentDisplayNameList(drgElement)/>
            <#list transformer.drgElementArgumentNameList(drgElement)>
            <#items as arg>
            ${transformer.argumentsVariableName(drgElement)}.put("${transformer.escapeInString(elementNames[arg?index])}", ${arg})
            </#items>
            </#list>
            ${transformer.eventListenerVariableName()}.startDRGElement(<@drgElementAnnotation drgElement/>, ${transformer.argumentsVariableName(drgElement)})
</#macro>

<#macro endDRGElement drgElement output>
    <@endDRGElementIndent "" drgElement output/>
</#macro>

<#macro endDRGElementIndent extraIndent drgElement output>
            ${extraIndent}# ${transformer.endElementCommentText(drgElement)}
            ${extraIndent}${transformer.eventListenerVariableName()}.endDRGElement(<@drgElementAnnotation drgElement/>, ${transformer.argumentsVariableName(drgElement)}, ${output}, (<@currentTimeMillis/> - ${transformer.namedElementVariableName(drgElement)}StartTime_))
</#macro>

<#macro endDRGElementAndReturn drgElement output>
            <@endDRGElementAndReturnIndent "" drgElement output/>
</#macro>

<#macro endDRGElementAndReturnIndent extraIndent drgElement output>
            <@endDRGElementIndent extraIndent drgElement output/>

            ${extraIndent}return ${output}
</#macro>

<#macro startRule drgElement rule_index>
        # Rule start
        ${transformer.eventListenerVariableName()}.startRule(<@drgElementAnnotation drgElement/>, <@ruleAnnotation/>)
</#macro>

<#macro matchRule drgElement rule_index>
            # Rule match
            ${transformer.eventListenerVariableName()}.matchRule(<@drgElementAnnotation drgElement/>, <@ruleAnnotation/>)
</#macro>

<#macro endRule drgElement rule_index output>
        # Rule end
        ${transformer.eventListenerVariableName()}.endRule(<@drgElementAnnotation drgElement/>, <@ruleAnnotation/>, ${output})
</#macro>

<#macro drgElementAnnotation drgElement>self.${transformer.drgElementMetadataFieldName()}</#macro>

<#macro ruleAnnotation>${transformer.drgRuleMetadataFieldName()}</#macro>

<#--
    Annotations
-->
<#macro addAnnotation drgElement rule rule_index>
            # Add annotation
            ${transformer.annotationSetVariableName()}.addAnnotation("${drgElement.name}", ${rule_index}, ${transformer.annotation(drgElement, rule)})
</#macro>

<#macro currentTimeMillis>int(time.time_ns()/1000)</#macro>

<#--
    Import
-->
<#macro importStatements drgElement>
<@importRuntimeStatements drgElement/>
<@importModelElementStatements drgElement/>
</#macro>

<#macro importRuntimeStatements drgElement>
import typing
import decimal
import datetime
import isodate
import time

import ${transformer.jdmnRootPackage()}.runtime.Context
import ${transformer.jdmnRootPackage()}.runtime.DefaultDMNBaseDecision
import ${transformer.jdmnRootPackage()}.runtime.ExecutionContext
import ${transformer.jdmnRootPackage()}.runtime.LambdaExpression
import ${transformer.jdmnRootPackage()}.runtime.LazyEval
import ${transformer.jdmnRootPackage()}.runtime.Pair
import ${transformer.jdmnRootPackage()}.runtime.Range
import ${transformer.jdmnRootPackage()}.runtime.RuleOutput
import ${transformer.jdmnRootPackage()}.runtime.RuleOutputList

import ${transformer.jdmnRootPackage()}.runtime.annotation.Annotation
import ${transformer.jdmnRootPackage()}.runtime.annotation.AnnotationSet
import ${transformer.jdmnRootPackage()}.runtime.annotation.DRGElementKind
import ${transformer.jdmnRootPackage()}.runtime.annotation.ExpressionKind
import ${transformer.jdmnRootPackage()}.runtime.annotation.HitPolicy

import ${transformer.jdmnRootPackage()}.runtime.cache.Cache

import ${transformer.jdmnRootPackage()}.runtime.external.ExternalFunctionExecutor

import ${transformer.jdmnRootPackage()}.runtime.listener.Arguments
import ${transformer.jdmnRootPackage()}.runtime.listener.DRGElement
import ${transformer.jdmnRootPackage()}.runtime.listener.EventListener
import ${transformer.jdmnRootPackage()}.runtime.listener.Rule
</#macro>

<#macro importModelElementStatements drgElement>
<#if transformer.hasComplexInputDatas(drgElement)>

<@importComplexInputDatas drgElement/>
</#if>
<#if transformer.hasDirectSubDecisions(drgElement)>

<@importSubDecisions drgElement/>
</#if>
<#if transformer.hasDirectSubInvocables(drgElement)>

<@importSubInvocables drgElement/>
</#if>
<#if modelRepository.isDecisionTableExpression(drgElement)>

import ${transformer.qualifiedModuleName(javaPackageName, transformer.ruleOutputClassName(drgElement))}
</#if>
</#macro>

<#macro importComplexInputDatas drgElement>
    <#list transformer.drgElementComplexInputClassNames(drgElement)>
        <#items as className>
import ${transformer.qualifiedModuleName(transformer.nativeTypePackageName(modelName), className)}
        </#items>
    </#list>
</#macro>

<#macro importSubDecisions drgElement>
    <#list modelRepository.directSubDecisions(drgElement)>
        <#items as subDecision>
import ${transformer.qualifiedModuleName(javaPackageName, transformer.drgElementClassName(subDecision))}
        </#items>
    </#list>
</#macro>

<#macro importSubInvocables drgElement>
    <#list modelRepository.directSubInvocables(drgElement)>
        <#items as bkm>
import ${transformer.qualifiedModuleName(javaPackageName, transformer.drgElementClassName(bkm))}
        </#items>
    </#list>
</#macro>

<#macro singletonPattern drgElement>
    _instance = None

    def __init__(self):
        raise RuntimeError("Call instance() instead")

    @classmethod
    def instance(cls):
        if cls._instance is None:
            cls._instance = cls.__new__(cls)
            ${decisionBaseClass}.__init__(cls._instance)
    <#if transformer.hasDirectSubDecisions(drgElement)>
            cls._instance.initSubDecisions()
        return cls._instance

    def initSubDecisions(${transformer.drgElementConstructorSignature(drgElement)}):
        <@setSubDecisionFields drgElement/>
    <#else>
        return cls._instance
    </#if>
</#macro>