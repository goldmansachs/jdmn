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
    <@apply.applyPojo drgElement />
</#macro>

<#macro applyPojo drgElement >
    def apply(${transformer.drgElementSignature(drgElement)}) -> ${transformer.drgElementOutputType(drgElement)}:
        <#if drgElement.class.simpleName == "TDecisionService">
        <@applyServiceMethodBody drgElement />
        <#else>
        <@applyMethodBody drgElement />
        </#if>
</#macro>

<#--
    Apply method body
-->
<#macro applyMethodBody drgElement>
        try:
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
        <@events.startDRGElement drgElement/>

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

        <@events.startRule drgElement rule_index />

        # Apply rule
        output_: ${transformer.qualifiedRuleOutputClassName(drgElement)} = ${transformer.qualifiedRuleOutputClassName(drgElement)}(False)
        if (${transformer.condition(drgElement, rule, rule_index)}):
            <@events.matchRule drgElement rule_index />

            # Compute output
            output_.setMatched(True)
            <#list expression.output as output>
            output_.${transformer.outputClauseVariableName(drgElement, output)} = ${transformer.outputEntryToNative(drgElement, rule.outputEntry[output_index], output_index)}
                <#if modelRepository.isOutputOrderHit(expression.hitPolicy) && transformer.outputClausePriority(drgElement, rule.outputEntry[output_index], output_index)?exists>
            output_.${transformer.outputClausePriorityVariableName(drgElement, output)} = ${transformer.outputClausePriority(drgElement, rule.outputEntry[output_index], output_index)}
                </#if>
            </#list>

            <@addAnnotation drgElement rule rule_index />

        <@events.endRule drgElement rule_index "output_" />

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
            if ${transformer.cacheVariableName()}.contains("${modelRepository.name(drgElement)}"):
                # Retrieve value from cache
                output_: ${transformer.drgElementOutputType(drgElement)} = ${transformer.cacheVariableName()}.lookup("${modelRepository.name(drgElement)}")

                <@events.endDRGElementAndReturnIndent "    " drgElement "output_" />
            else:
                # ${transformer.evaluateElementCommentText(drgElement)}
                output_: ${transformer.drgElementOutputType(drgElement)} = self.evaluate(${transformer.drgElementArgumentList(drgElement)})
                ${transformer.cacheVariableName()}.bind("${modelRepository.name(drgElement)}", output_)

                <@events.endDRGElementAndReturnIndent "    " drgElement "output_" />
        <#else>
            # ${transformer.evaluateElementCommentText(drgElement)}
            output_: ${transformer.drgElementOutputType(drgElement)} = self.evaluate(${transformer.drgElementArgumentList(drgElement)})

            <@events.endDRGElementAndReturn drgElement "output_" />
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
            ${extraIndent}${transformer.cacheVariableName()}.bind("${modelRepository.name(inputDecision.element)}", ${transformer.drgElementReferenceVariableName(inputDecision)})
        </#items>

    </#list>
</#macro>

<#--
    Annotations
-->
<#macro addAnnotation drgElement rule rule_index>
            # Add annotation
            ${transformer.annotationSetVariableName()}.addAnnotation("${drgElement.name}", ${rule_index}, ${transformer.annotation(drgElement, rule)})
</#macro>
