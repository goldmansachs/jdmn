<#--
    Apply method body
-->
<#macro applyMethodBody drgElement>
        try {
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
    </#if>
</#macro>

<#--
    Import required BKMs
-->
<#macro importRequiredBKMs drgElement>
    <#list modelRepository.directSubBKMs(drgElement)>
        <#items as subBKM>
import static ${transformer.qualifiedName(javaPackageName, transformer.drgElementClassName(subBKM))}.${transformer.bkmFunctionName(subBKM)};
        </#items>

    </#list>
</#macro>

<#--
    Sub decisions fields
-->
<#macro addSubDecisionFields drgElement>
    <#list modelRepository.topologicalSort(drgElement)>
        <#items as subDecision>
    private final ${transformer.qualifiedName(javaPackageName, transformer.drgElementClassName(subDecision))} ${transformer.drgElementVariableName(subDecision)};
        </#items>
    </#list>
</#macro>

<#macro setSubDecisionFields drgElement>
    <#list modelRepository.topologicalSort(drgElement)>
        <#items as subDecision>
        this.${transformer.drgElementVariableName(subDecision)} = ${transformer.drgElementVariableName(subDecision)};
        </#items>
    </#list>
</#macro>

<#--
    Decision table
-->
<#macro addEvaluateDecisionTableMethod drgElement>
    ${transformer.drgElementOutputType(drgElement)} evaluate(${transformer.drgElementEvaluateSignature(drgElement)}) {
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
            output_ = ruleOutput_ == null ? null : ((${transformer.ruleOutputClassName(drgElement)})ruleOutput_).${transformer.getter(drgElement, expression.output[0])};
            </#if>
        }

        return output_;
    <#elseif modelRepository.isMultipleHit(expression.hitPolicy)>
        if (ruleOutputList_.noMatchedRules()) {
            // Default value
            output_ = ${transformer.defaultValue(drgElement)};
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
            output_ = ruleOutputs_.stream().map(o -> ((${transformer.ruleOutputClassName(drgElement)})o).${transformer.getter(drgElement, expression.output[0])}).collect(Collectors.toList());
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
    public ${transformer.abstractRuleOutputClassName()} rule${rule_index}(${transformer.drgElementSignatureExtra(transformer.ruleSignature(drgElement))}) {
        // Rule metadata
        ${transformer.drgRuleMetadataClassName()} ${transformer.drgRuleMetadataFieldName()} = new ${transformer.drgRuleMetadataClassName()}(${rule_index}, "${transformer.annotationEscapedText(rule)}");

        <@startRule drgElement rule_index />

        // Apply rule
        ${transformer.ruleOutputClassName(drgElement)} output_ = new ${transformer.ruleOutputClassName(drgElement)}(false);
        if (${transformer.condition(drgElement, rule)}) {
            <@matchRule drgElement rule_index />

            // Compute output
            output_.setMatched(true);
            <#list expression.output as output>
            output_.${transformer.setter(drgElement, output)}(${transformer.outputEntryToJava(drgElement, rule.outputEntry[output_index], output_index)});
                <#if modelRepository.isOutputOrderHit(expression.hitPolicy) && transformer.priority(drgElement, rule.outputEntry[output_index], output_index)?exists>
            output_.${transformer.prioritySetter(drgElement, output)}(${transformer.priority(drgElement, rule.outputEntry[output_index], output_index)});
                </#if>
            </#list>

            <@addAnnotation drgElement rule rule_index />
        }

        <@endRule drgElement rule_index "output_" />

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
        ${transformer.abstractRuleOutputClassName()} tempRuleOutput_ = rule${rule_index}(${transformer.drgElementArgumentsExtra(transformer.ruleArgumentList(drgElement))});
        ruleOutputList_.add(tempRuleOutput_);
        boolean matched_ = tempRuleOutput_.isMatched();
        <#else >
        if (!matched_) {
            tempRuleOutput_ = rule${rule_index}(${transformer.drgElementArgumentsExtra(transformer.ruleArgumentList(drgElement))});
            ruleOutputList_.add(tempRuleOutput_);
            matched_ = tempRuleOutput_.isMatched();
        }
        </#if>
        <#else >
        ruleOutputList_.add(rule${rule_index}(${transformer.drgElementArgumentsExtra(transformer.ruleArgumentList(drgElement))}));
        </#if>
        </#items>
    </#list>
</#macro>

<#macro addConversionMethod drgElement>
    <#if modelRepository.isCompoundDecisionTable(drgElement)>
    public ${transformer.drgElementOutputClassName(drgElement)} toDecisionOutput(${transformer.ruleOutputClassName(drgElement)} ruleOutput_) {
        ${transformer.itemDefinitionJavaClassName(transformer.drgElementOutputClassName(drgElement))} result_ = ${transformer.defaultConstructor(transformer.itemDefinitionJavaClassName(transformer.drgElementOutputClassName(drgElement)))};
        <#assign expression = modelRepository.expression(drgElement)>
        <#list expression.output as output>
        result_.${transformer.setter(drgElement, output)}(ruleOutput_ == null ? null : ruleOutput_.${transformer.getter(drgElement, output)});
        </#list>
        return result_;
    }
    </#if>
</#macro>

<#--
    Expression
-->
<#macro expressionApplyBody drgElement>
            <@applySubDecisions drgElement/>
            // ${transformer.evaluateElementCommentText(drgElement)}
            ${transformer.drgElementOutputType(drgElement)} output_ = evaluate(${transformer.drgElementEvaluateArgumentList(drgElement)});

            <@endDRGElementAndReturn drgElement "output_" />
</#macro>

<#macro addEvaluateExpressionMethod drgElement>
    ${transformer.drgElementOutputType(drgElement)} evaluate(${transformer.drgElementEvaluateSignature(drgElement)}) {
    <#assign stm = transformer.expressionToJava(drgElement)>
    <#if transformer.isCompoundStatement(stm)>
        <#list stm.statements as child>
        ${child.expression}
        </#list>
    <#else>
        return ${stm.expression};
    </#if>
    }
</#macro>

<#--
    Evaluate all sub-decisions
-->
<#macro applySubDecisions drgElement>
    <#list modelRepository.topologicalSortWithMarkers(drgElement)>
            //
            // Evaluate child decisions
            //
    <#items as object>

        <#if object.class.simpleName == "TDecision">
            // ${transformer.evaluateElementCommentText(object)}
        <#if transformer.isLazyEvaluated(object)>
            ${transformer.lazyEvalClassName()}<${transformer.drgElementOutputType(object)}> ${transformer.drgElementVariableName(object)} = new ${transformer.lazyEvalClassName()}<>(() -> this.${transformer.drgElementVariableName(object)}.evaluate(${transformer.drgElementEvaluateArgumentList(object)}));
        <#else>
            ${transformer.drgElementOutputType(object)} ${transformer.drgElementVariableName(object)} = this.${transformer.drgElementVariableName(object)}.evaluate(${transformer.drgElementEvaluateArgumentList(object)});
        </#if>

            <@endDRGElement object transformer.drgElementVariableName(object) />
        <#else>
            <@startDRGElement object.decision/>
        </#if>
    </#items>

    </#list>
</#macro>

<#--
    Events
-->
<#macro startDRGElement drgElement>
            // ${transformer.startElementCommentText(drgElement)}
            long ${transformer.drgElementVariableName(drgElement)}StartTime_ = System.currentTimeMillis();
            ${transformer.argumentsClassName()} ${transformer.drgElementVariableName(drgElement)}Arguments_ = new ${transformer.argumentsClassName()}();
            <#list transformer.drgElementArgumentNameList(drgElement)>
            <#items as arg>
            ${transformer.drgElementVariableName(drgElement)}Arguments_.put("${arg}", ${arg});
            </#items>
            </#list>
            ${transformer.eventListenerVariableName()}.startDRGElement(<@drgElementAnnotation drgElement/>, ${transformer.drgElementVariableName(drgElement)}Arguments_);
</#macro>

<#macro endDRGElement drgElement output>
            // ${transformer.endElementCommentText(drgElement)}
            ${transformer.eventListenerVariableName()}.endDRGElement(<@drgElementAnnotation drgElement/>, ${transformer.drgElementVariableName(drgElement)}Arguments_, ${output}, (System.currentTimeMillis() - ${transformer.drgElementVariableName(drgElement)}StartTime_));
</#macro>

<#macro endDRGElementAndReturn drgElement output>
            <@endDRGElement drgElement output/>

            return ${output};
</#macro>

<#macro startRule drgElement rule_index>
        // Rule start
        ${transformer.eventListenerVariableName()}.startRule(<@drgElementAnnotation drgElement/>, <@ruleAnnotation/>);
</#macro>

<#macro matchRule drgElement rule_index>
            // Rule match
            ${transformer.eventListenerVariableName()}.matchRule(<@drgElementAnnotation drgElement/>, <@ruleAnnotation/>);
</#macro>

<#macro endRule drgElement rule_index output>
        // Rule end
        ${transformer.eventListenerVariableName()}.endRule(<@drgElementAnnotation drgElement/>, <@ruleAnnotation/>, ${output});
</#macro>

<#macro drgElementAnnotation drgElement>${transformer.qualifiedName(javaPackageName, transformer.drgElementClassName(drgElement))}.${transformer.drgElementMetadataFieldName()}</#macro>

<#macro ruleAnnotation>${transformer.drgRuleMetadataFieldName()}</#macro>

<#--
    Annotations
-->
<#macro addAnnotation drgElement rule rule_index>
            // Add annotation
            ${transformer.annotationSetVariableName()}.addAnnotation("${drgElement.name}", ${rule_index}, ${transformer.annotation(drgElement, rule)});
</#macro>
