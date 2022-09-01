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
public class ${testClassName} extends ${decisionBaseClass} {
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
    <@addTestCases />
}
<#macro addTestCases>
    <#list testCases.testCase>
        <#items as tc>
    @org.junit.Test
    public void testCase${tckUtil.testCaseId(tc)}() {
        <@initializeInputs tc/>

        <@checkResults tc/>
        <#if tckUtil.isGenerateProto()>

        <@checkProtoResults tc/>
        </#if>
    }

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

<#macro initializeInputs testCase>
        ${tckUtil.annotationSetClassName()} ${tckUtil.annotationSetVariableName()} = ${tckUtil.defaultConstructor(tckUtil.annotationSetClassName())};
        ${tckUtil.eventListenerClassName()} ${tckUtil.eventListenerVariableName()} = ${tckUtil.defaultConstructor(tckUtil.defaultEventListenerClassName())};
        ${tckUtil.externalExecutorClassName()} ${tckUtil.externalExecutorVariableName()} = ${tckUtil.defaultConstructor(tckUtil.defaultExternalExecutorClassName())};
        ${tckUtil.cacheInterfaceName()} ${tckUtil.cacheVariableName()} = ${tckUtil.defaultConstructor(tckUtil.defaultCacheClassName())};
    <#if tckUtil.isMockTesting()>
        java.util.Map<String, Object> ${tckUtil.mockContextVariable()} = new java.util.LinkedHashMap<>();
    </#if>
    <#list testCase.inputNode>
        // Initialize input data
        <#items as input>
        <#assign inputInfo = tckUtil.extractInputNodeInfo(testCases, testCase, input) >
        <#assign inputVariableName = tckUtil.inputDataVariableName(inputInfo) >
        <#assign inputValue = tckUtil.toNativeExpression(inputInfo) >
        ${tckUtil.toNativeType(inputInfo)} ${inputVariableName} = ${inputValue};
        <#if tckUtil.isCached(inputInfo)>
        ${tckUtil.cacheVariableName()}.bind("${inputVariableName}", ${inputVariableName});
        </#if>
        <#if tckUtil.isMockTesting()>
        ${tckUtil.mockContextVariable()}.put("${inputVariableName}", ${inputVariableName});
        </#if>
        </#items>
    </#list>
</#macro>

<#macro checkResults testCase>
    <#list testCase.resultNode>
        <#items as result>
        // Check ${result.name}
        <#assign resultInfo = tckUtil.extractResultNodeInfo(testCases, testCase, result) >
        <#assign elementQName = tckUtil.qualifiedName(resultInfo) >
        <#assign expectedValue = tckUtil.toNativeExpression(resultInfo) >
        <#assign argList = tckUtil.drgElementArgumentList(resultInfo) >
        <@addMissingInputs testCase result />
        <#if resultInfo.isDecision()>
           <#if tckUtil.isSingletonDecision()>
        checkValues(${expectedValue}, ${tckUtil.singletonDecisionInstance(elementQName)}.apply(${argList}));
           <#else>
        checkValues(${expectedValue}, ${tckUtil.defaultConstructor(elementQName)}.apply(${argList}));
           </#if>
        <#elseif resultInfo.isDS() || resultInfo.isBKM()>
        checkValues(${expectedValue}, ${elementQName}.apply(${argList}));
        </#if>
        </#items>
    </#list>
</#macro>

<#macro checkProtoResults testCase>
    <#list testCase.resultNode>
        <#items as result>
        // Check ${result.name} with proto request
        <#assign resultInfo = tckUtil.extractResultNodeInfo(testCases, testCase, result) >
        ${tckUtil.qualifiedRequestMessageName(resultInfo)}.Builder ${tckUtil.builderVariableName(resultInfo)} = ${tckUtil.qualifiedRequestMessageName(resultInfo)}.newBuilder();
        <#list tckUtil.drgElementTypeSignature(resultInfo) as parameter>
        <#assign variableNameProto>${parameter.left}Proto${result?index}</#assign>
        ${tckUtil.toNativeTypeProto(parameter.right)} ${variableNameProto} = ${tckUtil.toNativeExpressionProto(parameter)};
        <#if tckUtil.isProtoReference(parameter.right)>
        if (${variableNameProto} != null) {
            ${tckUtil.builderVariableName(resultInfo)}.${tckUtil.protoSetter(parameter, "${variableNameProto}")};
        }
        <#else>
        ${tckUtil.builderVariableName(resultInfo)}.${tckUtil.protoSetter(parameter, "${variableNameProto}")};
        </#if>
        </#list>
        ${tckUtil.qualifiedRequestMessageName(resultInfo)} ${tckUtil.requestVariableName(resultInfo)} = ${tckUtil.builderVariableName(resultInfo)}.build();
        checkValues(${tckUtil.toNativeExpressionProto(resultInfo)}, ${tckUtil.defaultConstructor(tckUtil.qualifiedName(resultInfo))}.apply(${tckUtil.drgElementArgumentListProto(resultInfo)}).${tckUtil.protoGetter(resultInfo)});
        </#items>
    </#list>
</#macro>

<#macro addMissingInputs testCase resultNode>
    <#list tckUtil.missingArguments(testCases, testCase, resultNode) >
        <#items as triplet>
        ${triplet[0]} ${triplet[1]} = ${triplet[2]};
        </#items>
    </#list>
</#macro>