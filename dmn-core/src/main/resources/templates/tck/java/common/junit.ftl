<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#if packageName?has_content>
package ${packageName};
</#if>

import java.util.*;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = {"junit.ftl", "${testCases.modelName}"})
public class ${testClassName} extends ${decisionBaseClass}<Object> {
    <#if tckUtil.isMockTesting()>
    // Default values for mock tests
    private static final ${tckUtil.getNativeNumberType()} DEFAULT_INTEGER_NUMBER = ${tckUtil.getDefaultIntegerValue()};
    private static final ${tckUtil.getNativeNumberType()} DEFAULT_DECIMAL_NUMBER = ${tckUtil.getDefaultDecimalValue()};
    private static final java.lang.String DEFAULT_STRING = ${tckUtil.getDefaultStringValue()};
    private static final Boolean DEFAULT_BOOLEAN = ${tckUtil.getDefaultBooleanValue()};
    private static final ${tckUtil.getNativeDateType()} DEFAULT_DATE = ${tckUtil.getDefaultDateValue()};
    private static final ${tckUtil.getNativeTimeType()} DEFAULT_TIME = ${tckUtil.getDefaultTimeValue()};
    private static final ${tckUtil.getNativeDateAndTimeType()} DEFAULT_DATE_TIME = ${tckUtil.getDefaultDateAndTimeValue()};
    private static final ${tckUtil.getNativeDurationType()} DEFAULT_DURATION = ${tckUtil.getDefaultDurationValue()};

    </#if>
    <@addTestCases/>
}
<#macro addTestCases>
    <#list testCases.testCase>
        <#items as tc>
            <#assign inputNodeInfoList = tckUtil.extractInputNodeInfoList(testCases, tc)/>
            <#list tc.resultNode>
                <#items as rn>
                    <#assign resultInfo = tckUtil.extractResultNodeInfo(testCases, tc, rn)/>
    @org.junit.jupiter.api.Test
    public void testCase${tckUtil.testCaseId(tc)}_${rn?index + 1}() {
        <@initializeApplyArguments inputNodeInfoList resultInfo/>

        <@checkResult inputNodeInfoList resultInfo/>
    }

                </#items>
           </#list>
        </#items>
    </#list>
    private void checkValues(Object expected, Object actual) {
    <#if tckUtil.isMockTesting()>
        if (expected instanceof Boolean && actual == null) {
            actual = DEFAULT_BOOLEAN;
        } else if (expected instanceof java.lang.Number && actual == null) {
            actual = DEFAULT_DECIMAL_NUMBER;
        }
    </#if>
        ${tckUtil.assertClassName()}.assertEquals(expected, actual);
    }
</#macro>

<#macro initializeApplyArguments inputNodeInfoList resultInfo>
    <#assign drgElement = resultInfo.reference.element/>
    <#assign inputClassName = tckUtil.contextClassName()/>
    <#assign inputVariableName = tckUtil.inputVariableName()/>
        // Initialize input
        ${inputClassName} ${inputVariableName} = ${tckUtil.defaultConstructor(inputClassName)};
    <#list inputNodeInfoList>
        <#items as inputInfo>
            <#assign displayName = tckUtil.displayName(inputInfo)/>
            <#assign memberValue = tckUtil.toNativeExpression(inputInfo)/>
            <#if inputInfo.isInputData()>
        ${inputVariableName}.${tckUtil.contextSetter(displayName, memberValue)};
            <#elseif inputInfo.isDecision()>
                <#if resultInfo.isDS() || resultInfo.isBKM()>
        ${inputVariableName}.${tckUtil.contextSetter(displayName, memberValue)};
                </#if>
            </#if>
        </#items>
    </#list>
</#macro>

<#macro checkResult inputNodeInfoList resultInfo>
        // Check '${resultInfo.nodeName}'
        ${tckUtil.executionContextClassName()} ${tckUtil.executionContextVariableName()} = ${tckUtil.executionContextBuilderClassName()}.executionContext().build();
        <#assign elementQName = tckUtil.qualifiedName(resultInfo)/>
        <#assign expectedValue = tckUtil.toNativeExpression(resultInfo)/>
        <#assign parentArgList = tckUtil.drgElementArgumentListApplyContext(resultInfo)/>
        <#if resultInfo.isDecision()>
            <#if tckUtil.hasMockedDirectSubDecisions(resultInfo, inputNodeInfoList)>
                <@instantiateSubDecisions inputNodeInfoList resultInfo/>
        checkValues(${expectedValue}, ${tckUtil.constructor(resultInfo)}.applyContext(${parentArgList}));
            <#else>
                <#if tckUtil.isSingletonDecision()>
        checkValues(${expectedValue}, ${tckUtil.singletonDecisionInstance(elementQName)}.applyContext(${parentArgList}));
                <#else>
        checkValues(${expectedValue}, ${tckUtil.defaultConstructor(elementQName)}.applyContext(${parentArgList}));
                </#if>
            </#if>
        <#elseif resultInfo.isDS() || resultInfo.isBKM()>
        checkValues(${expectedValue}, ${elementQName}.applyContext(${parentArgList}));
        </#if>
</#macro>

<#macro instantiateSubDecisions inputNodeInfoList resultInfo>
    <#list tckUtil.directSubDecisions(resultInfo)>
        <#items as reference>
            <#assign subDecisionQName = tckUtil.qualifiedName(reference)/>
            <#assign subDecisionVarName = tckUtil.drgElementReferenceVariableName(reference)/>
            <#if tckUtil.hasInputNodeInfo(reference, inputNodeInfoList)>
                <#assign subDecisionInputInfo = tckUtil.findInputNodeInfo(reference, inputNodeInfoList)/>
                <#assign inputValue = tckUtil.toNativeExpression(subDecisionInputInfo)/>
                <#assign childArgList = tckUtil.drgElementArgumentList(subDecisionInputInfo)/>
        ${subDecisionQName} ${subDecisionVarName} = org.mockito.Mockito.mock(${subDecisionQName}.class);
        org.mockito.Mockito.when(${subDecisionVarName}.apply(${childArgList})).thenReturn(${inputValue});
            <#else>
                <#assign subDecisionConstructor = tckUtil.defaultConstructor(subDecisionQName)/>
        ${subDecisionQName} ${subDecisionVarName} = ${subDecisionConstructor};
            </#if>
        </#items>
    </#list>
</#macro>