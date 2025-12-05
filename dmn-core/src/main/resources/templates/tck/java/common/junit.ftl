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
        ${tckUtil.executionContextClassName()} ${tckUtil.executionContextVariableName()} = ${tckUtil.executionContextBuilderClassName()}.executionContext().build();
        ${tckUtil.cacheInterfaceName()} ${tckUtil.cacheVariableName()} = ${tckUtil.executionContextVariableName()}.getCache();
    <#list inputNodeInfoList>
        // Initialize arguments
        <#items as inputInfo>
        <#assign inputVariableName = tckUtil.inputDataVariableName(inputInfo)>
        <#assign inputValue = tckUtil.toNativeExpression(inputInfo)>
        ${tckUtil.toNativeType(inputInfo)} ${inputVariableName} = ${inputValue};
        <#if tckUtil.isCached(inputInfo)>
        ${tckUtil.cacheVariableName()}.bind("${inputVariableName}", ${inputVariableName});
        </#if>
        </#items>
    </#list>
        <@addMissingApplyArguments inputNodeInfoList resultInfo/>
</#macro>

<#macro checkResult inputNodeInfoList resultInfo>
        // Check '${resultInfo.nodeName}'
        <#assign elementQName = tckUtil.qualifiedName(resultInfo)>
        <#assign expectedValue = tckUtil.toNativeExpression(resultInfo)>
        <#assign parentArgList = tckUtil.drgElementArgumentList(resultInfo)>
        <#if resultInfo.isDecision()>
           <#if tckUtil.isSingletonDecision()>
        checkValues(${expectedValue}, ${tckUtil.singletonDecisionInstance(elementQName)}.apply(${parentArgList}));
           <#else>
        checkValues(${expectedValue}, ${tckUtil.defaultConstructor(elementQName)}.apply(${parentArgList}));
           </#if>
        <#elseif resultInfo.isDS() || resultInfo.isBKM()>
        checkValues(${expectedValue}, ${elementQName}.apply(${parentArgList}));
        </#if>
</#macro>

<#macro addMissingApplyArguments inputNodeInfoList resultInfo>
    <#list tckUtil.missingArguments(inputNodeInfoList, resultInfo)>
        <#items as triplet>
        ${triplet[0]} ${triplet[1]} = ${triplet[2]};
        </#items>
    </#list>
</#macro>