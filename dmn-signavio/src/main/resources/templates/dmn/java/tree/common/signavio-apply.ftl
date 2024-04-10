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
    @java.lang.Override()
    public ${transformer.drgElementOutputType(drgElement)} applyMap(${transformer.drgElementSignatureWithMap(drgElement)}) {
    <#if transformer.canGenerateApplyWithMap(drgElement)>
        try {
            return apply(${transformer.drgElementArgumentListWithMap(drgElement)});
        } catch (Exception e) {
            logError("Cannot apply decision '${javaClassName}'", e);
            return null;
        }
    <#else>
        throw ${transformer.constructor(transformer.dmnRuntimeExceptionClassName(), "\"Not all arguments can be serialized\"")};
    </#if>
    }
</#macro>

<#macro applyString drgElement >
    <#if transformer.shouldGenerateApplyWithConversionFromString(drgElement)>
    public ${transformer.drgElementOutputType(drgElement)} applyString(${transformer.drgElementSignatureWithConversionFromString(drgElement)}) {
        try {
            return apply(${transformer.drgElementArgumentListWithConversionFromString(drgElement)});
        } catch (Exception e) {
            logError("Cannot apply decision '${javaClassName}'", e);
            return null;
        }
    }

    </#if>
</#macro>

<#macro applyPojo drgElement >
    public ${transformer.drgElementOutputType(drgElement)} apply(${transformer.drgElementSignature(drgElement)}) {
        <#if drgElement.class.simpleName == "TDecisionService">
        <@applyServiceMethodBody drgElement />
        <#else>
        <@applyMethodBody drgElement />
        </#if>
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
            logError("${modelRepository.expression(drgElement).class.simpleName} is not implemented yet");
            return null;
        </#if>
        } catch (Exception e) {
            logError("Exception caught in '${modelRepository.name(drgElement)}' evaluation", e);
            return null;
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
                ${transformer.drgElementOutputType(drgElement)} output_ = (${transformer.drgElementOutputType(drgElement)})${transformer.cacheVariableName()}.lookup("${modelRepository.name(drgElement)}");

                <@events.endDRGElementAndReturnIndent "    " drgElement "output_" />
            } else {
                // Iterate and aggregate
                ${transformer.drgElementOutputType(drgElement)} output_ = evaluate(${transformer.drgElementArgumentList(drgElement)});
                ${transformer.cacheVariableName()}.bind("${modelRepository.name(drgElement)}", output_);

                <@events.endDRGElementAndReturnIndent "    " drgElement "output_" />
            }
        <#else>
            // Iterate and aggregate
            ${transformer.drgElementOutputType(drgElement)} output_ = evaluate(${transformer.drgElementArgumentList(drgElement)});

            <@events.endDRGElementAndReturn drgElement "output_" />
        </#if>
</#macro>

<#macro addEvaluateIterationMethod drgElement>
    protected ${transformer.drgElementOutputType(drgElement)} evaluate(${transformer.drgElementSignature(drgElement)}) {
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
        ${transformer.qualifiedName(javaPackageName, transformer.drgElementClassName(topLevelDecision))} ${transformer.namedElementVariableName(topLevelDecision)} = new ${transformer.qualifiedName(javaPackageName, transformer.drgElementClassName(topLevelDecision))}();
        <#if aggregator == "COLLECT">
        return ${sourceList}.${transformer.getStream()}.map(${lambdaParamName} -> ${lambdaBody}).collect(Collectors.toList());
        <#elseif aggregator == "SUM">
        return sum(${sourceList}.${transformer.getStream()}.map(${lambdaParamName} -> ${lambdaBody}).collect(Collectors.toList()));
        <#elseif aggregator == "MIN">
        return = min(${sourceList}.${transformer.getStream()}.map(${lambdaParamName} -> ${lambdaBody}).collect(Collectors.toList()));
        <#elseif aggregator == "MAX">
        return max(${sourceList}.${transformer.getStream()}.map(${lambdaParamName} -> ${lambdaBody}).collect(Collectors.toList()));
        <#elseif aggregator == "COUNT">
        return count(${sourceList}.${transformer.getStream()}.map(${lambdaParamName} -> ${lambdaBody}).collect(Collectors.toList()));
        <#elseif aggregator == "ALLTRUE">
        return ${sourceList}.${transformer.getStream()}.allMatch(${lambdaParamName} -> ${lambdaBody});
        <#elseif aggregator == "ANYTRUE">
        return ${sourceList}.${transformer.getStream()}.anyMatch(${lambdaParamName} -> ${lambdaBody});
        <#elseif aggregator == "ALLFALSE">
        return ${sourceList}.${transformer.getStream()}.allMatch(${lambdaParamName} -> !${lambdaBody});
        <#else>
        logError("${aggregator} is not implemented yet");
        return null;
        </#if>
    }
</#macro>

<#--
    BKM linked to Decision
-->
<#macro addEvaluateBKMLinkedToDecisionMethod drgElement>
    protected ${transformer.drgElementOutputType(drgElement)} evaluate(${transformer.drgElementSignature(drgElement)}) {
        <@extractParametersFromArgs transformer.drgElementSignatureParameters(drgElement)/>
        <@applySubDecisions drgElement/>
        return ${transformer.bkmLinkedToDecisionToNative(drgElement)};
    }
</#macro>

<#--
    Sub decisions fields
-->
<#macro addSubDecisionFields drgElement>
    <#list modelRepository.directSubDecisions(drgElement)>

        <#items as subDecision>
    private final ${transformer.qualifiedName(subDecision)} ${transformer.drgElementReferenceVariableName(subDecision)};
        </#items>
    </#list>
</#macro>

<#macro setSubDecisionFields drgElement>
    <#list modelRepository.directSubDecisions(drgElement)>
        <#items as subDecision>
        this.${transformer.drgElementReferenceVariableName(subDecision)} = ${transformer.drgElementReferenceVariableName(subDecision)};
        </#items>
    </#list>
</#macro>

<#--
    Decision table
-->
<#macro addEvaluateDecisionTableMethod drgElement>
    protected ${transformer.drgElementOutputType(drgElement)} evaluate(${transformer.drgElementSignature(drgElement)}) {
    <@extractParametersFromArgs transformer.drgElementSignatureParameters(drgElement)/>
    <@applySubDecisions drgElement/>
    <#assign expression = modelRepository.expression(drgElement)>
        <@collectRuleResults drgElement expression />

        // Return results based on hit policy
        ${transformer.drgElementOutputType(drgElement)} output_;
    <#if modelRepository.isSingleHit(expression.hitPolicy)>
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = ${transformer.defaultValue(drgElement)};
        } else {
            ${transformer.abstractRuleOutputClassName()} ruleOutput_ = ruleOutputList_.applySingle(${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)});
            <#if modelRepository.isCompoundDecisionTable(drgElement)>
            output_ = toDecisionOutput((${transformer.ruleOutputClassName(drgElement)})ruleOutput_);
            <#else>
            output_ = ruleOutput_ == null ? null : ((${transformer.ruleOutputClassName(drgElement)})ruleOutput_).${transformer.outputClauseGetter(drgElement, expression.output[0])};
            </#if>
        }

        return output_;
    <#elseif modelRepository.isMultipleHit(expression.hitPolicy)>
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = ${transformer.defaultValue(drgElement)};
            <#if !modelRepository.hasAggregator(expression)>
            if (output_ == null) {
                output_ = ${transformer.asEmptyList(drgElement)};
            }
            </#if>
        } else {
            List<? extends ${transformer.abstractRuleOutputClassName()}> ruleOutputs_ = ruleOutputList_.applyMultiple(${transformer.hitPolicyAnnotationClassName()}.${transformer.hitPolicy(drgElement)});
        <#if modelRepository.isCompoundDecisionTable(drgElement)>
            <#if modelRepository.hasAggregator(expression)>
            output_ = null;
            <#else>
            output_ = ruleOutputs_.stream().map(o -> toDecisionOutput(((${transformer.ruleOutputClassName(drgElement)})o))).collect(Collectors.toList());
            </#if>
        <#else >
            <#if modelRepository.hasAggregator(expression)>
            output_ = ${transformer.aggregator(drgElement, expression, expression.output[0], "ruleOutputs_")};
            <#else>
            output_ = ruleOutputs_.stream().map(o -> ((${transformer.ruleOutputClassName(drgElement)})o).${transformer.outputClauseGetter(drgElement, expression.output[0])}).collect(Collectors.toList());
            </#if>
        </#if>
        }

        return output_;
    <#else>
        logError("Unknown hit policy '" + ${expression.hitPolicy} + "'"));
        return output_;
    </#if>
    }

</#macro>

<#macro addRuleMethods drgElement>
    <#assign expression = modelRepository.expression(drgElement)>
    <#list expression.rule>
        <#items as rule>
    @${transformer.ruleAnnotationClassName()}(index = ${rule_index}, annotation = "${transformer.annotationEscapedText(rule)}")
    public ${transformer.abstractRuleOutputClassName()} rule${rule_index}(${transformer.ruleSignature(drgElement)}) {
        // Rule metadata
        ${transformer.drgRuleMetadataClassName()} ${transformer.drgRuleMetadataFieldName()} = new ${transformer.drgRuleMetadataClassName()}(${rule_index}, "${transformer.annotationEscapedText(rule)}");

        <@events.startRule drgElement rule_index />

        // Apply rule
        ${transformer.ruleOutputClassName(drgElement)} output_ = new ${transformer.ruleOutputClassName(drgElement)}(false);
        if (${transformer.condition(drgElement, rule, rule_index)}) {
            <@events.matchRule drgElement rule_index />

            // Compute output
            output_.setMatched(true);
            <#list expression.output as output>
            output_.${transformer.outputClauseSetter(drgElement, output, "${transformer.outputEntryToNative(drgElement, rule.outputEntry[output_index], output_index)}")};
                <#if modelRepository.isOutputOrderHit(expression.hitPolicy) && transformer.outputClausePriority(drgElement, rule.outputEntry[output_index], output_index)?exists>
            output_.${transformer.outputClausePrioritySetter(drgElement, output, "${transformer.outputClausePriority(drgElement, rule.outputEntry[output_index], output_index)}")};
                </#if>
            </#list>
            <@addAnnotation drgElement rule rule_index />
        }

        <@events.endRule drgElement rule_index "output_" />

        return output_;
    }

        </#items>
    </#list>
</#macro>

<#macro collectRuleResults drgElement expression>
        // Apply rules and collect results
        ${transformer.ruleOutputListClassName()} ruleOutputList_ = new ${transformer.ruleOutputListClassName()}();
    <#assign expression = modelRepository.expression(drgElement)>
    <#list expression.rule>
        <#items as rule>
        <#if modelRepository.isFirstSingleHit(expression.hitPolicy) && modelRepository.atLeastTwoRules(expression)>
        <#if rule?is_first>
        ${transformer.abstractRuleOutputClassName()} tempRuleOutput_ = rule${rule_index}(${transformer.ruleArgumentList(drgElement)});
        ruleOutputList_.add(tempRuleOutput_);
        boolean matched_ = tempRuleOutput_.isMatched();
        <#else >
        if (!matched_) {
            tempRuleOutput_ = rule${rule_index}(${transformer.ruleArgumentList(drgElement)});
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }
        </#if>
        <#else >
        ruleOutputList_.add(rule${rule_index}(${transformer.ruleArgumentList(drgElement)}));
        </#if>
        </#items>
    </#list>
</#macro>

<#macro addConversionMethod drgElement>
    <#if modelRepository.isCompoundDecisionTable(drgElement)>
    public ${transformer.drgElementOutputInterfaceName(drgElement)} toDecisionOutput(${transformer.ruleOutputClassName(drgElement)} ruleOutput_) {
        <#assign className = transformer.drgElementOutputClassName(drgElement)>
        ${className} result_ = ${transformer.defaultConstructor(className)};
        <#assign expression = modelRepository.expression(drgElement)>
        <#list expression.output as output>
        result_.${transformer.drgElementOutputSetter(drgElement, output, "ruleOutput_ == null ? null : ruleOutput_.${transformer.outputClauseGetter(drgElement, output)}")};
        </#list>
        return result_;
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
                ${transformer.drgElementOutputType(drgElement)} output_ = (${transformer.drgElementOutputType(drgElement)})${transformer.cacheVariableName()}.lookup("${modelRepository.name(drgElement)}");

                <@events.endDRGElementAndReturnIndent "    " drgElement "output_" />
            } else {
                // ${transformer.evaluateElementCommentText(drgElement)}
                ${transformer.drgElementOutputType(drgElement)} output_ = evaluate(${transformer.drgElementArgumentList(drgElement)});
                ${transformer.cacheVariableName()}.bind("${modelRepository.name(drgElement)}", output_);

                <@events.endDRGElementAndReturnIndent "    " drgElement "output_" />
            }
        <#else>
            // ${transformer.evaluateElementCommentText(drgElement)}
            ${transformer.drgElementOutputType(drgElement)} output_ = evaluate(${transformer.drgElementArgumentList(drgElement)});

            <@events.endDRGElementAndReturn drgElement "output_" />
        </#if>
</#macro>

<#macro addEvaluateExpressionMethod drgElement>
    protected ${transformer.drgElementOutputType(drgElement)} evaluate(${transformer.drgElementSignature(drgElement)}) {
    <#list transformer.extractExtraParametersFromExecutionContext() as stm>
        ${stm}
    </#list>
    <@applySubDecisions drgElement/>
    <#if modelRepository.isFreeTextLiteralExpression(drgElement)>
        return ${transformer.freeTextLiteralExpressionToNative(drgElement)};
    <#else>
        <#assign stm = transformer.expressionToNative(drgElement)>
        <#if transformer.isCompoundStatement(stm)>
            <#list stm.statements as child>
        ${child.text}
            </#list>
        <#else>
        return ${stm.text};
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
        ${extraIndent}${transformer.lazyEvalClassName()}<${transformer.drgElementOutputType(subDecision)}> ${transformer.drgElementReferenceVariableName(subDecision)} = new ${transformer.lazyEvalClassName()}<>(() -> this.${transformer.drgElementReferenceVariableName(subDecision)}.apply(${transformer.drgElementArgumentList(subDecision)}));
            <#else>
        ${extraIndent}${transformer.drgElementOutputType(subDecision)} ${transformer.drgElementReferenceVariableName(subDecision)} = this.${transformer.drgElementReferenceVariableName(subDecision)}.apply(${transformer.drgElementArgumentList(subDecision)});
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
            ${transformer.annotationSetVariableName()}.addAnnotation("${drgElement.name}", ${rule_index}, ${annotation});
            </#items>
            </#list>
</#macro>