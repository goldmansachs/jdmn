<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#assign transformer = tckUtil.transformer/>
import typing
import decimal
import datetime
import isodate
import unittest

import ${transformer.jdmnRootPackage()}.runtime.Assert
import ${transformer.jdmnRootPackage()}.runtime.DefaultDMNBaseDecision
import ${transformer.jdmnRootPackage()}.runtime.ExecutionContext
import ${transformer.jdmnRootPackage()}.runtime.annotation.AnnotationSet
import ${transformer.jdmnRootPackage()}.runtime.cache.DefaultCache
import ${transformer.jdmnRootPackage()}.runtime.external.DefaultExternalFunctionExecutor
import ${transformer.jdmnRootPackage()}.runtime.listener.NopEventListener

<@importElements testCases/>


# Generated(value = ["junit.ftl", "${testCases.modelName}"])
class ${testClassName}(unittest.TestCase, ${decisionBaseClass}):
    <#if tckUtil.isMockTesting()>
    # Default values for mock tests
    DEFAULT_INTEGER_NUMBER: ${tckUtil.getNativeNumberType()} = ${tckUtil.getDefaultIntegerValue()}
    DEFAULT_DECIMAL_NUMBER: ${tckUtil.getNativeNumberType()} = ${tckUtil.getDefaultDecimalValue()}
    DEFAULT_STRING: typing.Optional[str] = ${tckUtil.getDefaultStringValue()}
    DEFAULT_BOOLEAN: typing.Optional[bool] = ${tckUtil.getDefaultBooleanValue()}
    DEFAULT_DATE: ${tckUtil.getNativeDateType()} = ${tckUtil.getDefaultDateValue()}
    DEFAULT_TIME: ${tckUtil.getNativeTimeType()} = ${tckUtil.getDefaultTimeValue()}
    DEFAULT_DATE_TIME: ${tckUtil.getNativeDateAndTimeType()} = ${tckUtil.getDefaultDateAndTimeValue()}
    DEFAULT_DURATION: ${tckUtil.getNativeDurationType()} = ${tckUtil.getDefaultDurationValue()}

    </#if>
    def __init__(self, methodName="runTest"):
        unittest.TestCase.__init__(self, methodName)
        ${decisionBaseClass}.__init__(self)

    <@addTestCases/>
<#macro addTestCases>
    <#list testCases.testCase>
        <#items as tc>
            <#assign inputNodeInfoList = tckUtil.extractInputNodeInfoList(testCases, tc)/>
            <#assign resultNodeInfoList = tckUtil.extractResultNodeInfoList(testCases, tc)/>
            <#list resultNodeInfoList>
                <#items as resultInfo>
    def testCase${tckUtil.testCaseId(tc)}_${resultInfo?index + 1}(self):
        <@initializeApplyArguments inputNodeInfoList resultInfo/>

        <@checkResult inputNodeInfoList resultInfo/>

                </#items>
           </#list>
        </#items>
    </#list>
    def checkValues(self, expected: typing.Any, actual: typing.Any):
        <#if tckUtil.isMockTesting()>
        if isinstance(expected, bool) and actual is None:
            actual = self.DEFAULT_BOOLEAN
        elif isinstance(expected, ${tckUtil.getNativeNumberType()}) and actual is None:
            actual = self.DEFAULT_DECIMAL_NUMBER
        </#if>
        ${tckUtil.defaultConstructor(tckUtil.assertClassName())}.assertEquals(expected, actual)
</#macro>

<#macro initializeApplyArguments inputNodeInfoList resultInfo>
        ${tckUtil.executionContextVariableName()} = ${tckUtil.defaultConstructor(tckUtil.executionContextClassName())}
        ${tckUtil.cacheVariableName()} = ${tckUtil.executionContextVariableName()}.cache
    <#list inputNodeInfoList>
        # Initialize arguments
        <#items as inputInfo>
        <#assign inputVariableName = tckUtil.inputDataVariableName(inputInfo)>
        <#assign inputValue = tckUtil.toNativeExpression(inputInfo)>
        ${inputVariableName}: ${tckUtil.toNativeType(inputInfo)} = ${inputValue}
        <#if tckUtil.isCached(inputInfo)>
        ${tckUtil.cacheVariableName()}.bind("${inputVariableName}", ${inputVariableName})
        </#if>
        </#items>
    </#list>
        <@addMissingApplyArguments inputNodeInfoList resultInfo/>
</#macro>

<#macro checkResult inputNodeInfoList resultInfo>
        # Check '${resultInfo.nodeName}'
        <#assign elementQName = tckUtil.qualifiedName(resultInfo)>
        <#assign expectedValue = tckUtil.toNativeExpression(resultInfo)>
        <#assign parentArgList = tckUtil.drgElementArgumentList(resultInfo)>
        <#if resultInfo.isDecision()>
           <#if tckUtil.isSingletonDecision()>
        self.checkValues(${expectedValue}, ${tckUtil.singletonDecisionInstance(elementQName)}.apply(${parentArgList}))
           <#else>
        self.checkValues(${expectedValue}, ${tckUtil.defaultConstructor(elementQName)}.apply(${parentArgList}))
           </#if>
        <#elseif resultInfo.isDS() || resultInfo.isBKM()>
        self.checkValues(${expectedValue}, ${elementQName}.apply(${parentArgList}))
        </#if>
</#macro>

<#macro addMissingApplyArguments inputNodeInfoList resultInfo>
    <#list tckUtil.missingArguments(inputNodeInfoList resultInfo)>
        <#items as triplet>
        ${triplet[1]}: ${triplet[0]} = ${triplet[2]}
        </#items>
    </#list>
</#macro>

<#macro importElements testCases>
    <#list tckUtil.findComplexInputDatas(testCases)>
# Complex input datas
        <#items as module>
import ${module}
        </#items>

    </#list>
    <#list tckUtil.findDRGElementsUnderTest(testCases)>
# DRG Elements to test
        <#items as element>
import ${transformer.qualifiedModuleName(element)}
        </#items>
    </#list>
</#macro>
