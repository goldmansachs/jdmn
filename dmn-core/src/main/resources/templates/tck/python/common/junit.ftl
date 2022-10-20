<#--
    Copyright 2016 Goldman Sachs.

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.

    You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations under the License.
-->
<#assign transformer = tckUtil.transformer />
import typing
import decimal
import datetime
import isodate
import unittest

import ${transformer.jdmnRootPackage()}.runtime.Assert
import ${transformer.jdmnRootPackage()}.runtime.DefaultDMNBaseDecision
import ${transformer.jdmnRootPackage()}.runtime.annotation.AnnotationSet
import ${transformer.jdmnRootPackage()}.runtime.cache.DefaultCache
import ${transformer.jdmnRootPackage()}.runtime.external.DefaultExternalFunctionExecutor
import ${transformer.jdmnRootPackage()}.runtime.listener.NopEventListener

<@importElements testCases />


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

    <@addTestCases />
<#macro addTestCases>
    <#list testCases.testCase>
        <#items as tc>
    def testCase${tckUtil.testCaseId(tc)}(self):
        <@initializeInputs tc/>

        <@checkResults tc/>

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

<#macro initializeInputs testCase>
        ${tckUtil.annotationSetVariableName()} = ${tckUtil.defaultConstructor(tckUtil.annotationSetClassName())}
        ${tckUtil.eventListenerVariableName()} = ${tckUtil.defaultConstructor(tckUtil.defaultEventListenerClassName())}
        ${tckUtil.externalExecutorVariableName()} = ${tckUtil.defaultConstructor(tckUtil.defaultExternalExecutorClassName())}
        ${tckUtil.cacheVariableName()} = ${tckUtil.defaultConstructor(tckUtil.defaultCacheClassName())}
    <#if tckUtil.isMockTesting()>
        ${tckUtil.mockContextVariable()} = {}
    </#if>
    <#list testCase.inputNode>
        # Initialize input data
        <#items as input>
        <#assign inputInfo = tckUtil.extractInputNodeInfo(testCases, testCase, input) >
        <#assign inputVariableName = tckUtil.inputDataVariableName(inputInfo) >
        <#assign inputValue = tckUtil.toNativeExpression(inputInfo) >
        ${inputVariableName}: ${tckUtil.toNativeType(inputInfo)} = ${inputValue}
        <#if tckUtil.isCached(inputInfo)>
        ${tckUtil.cacheVariableName()}.bind("${inputVariableName}", ${inputVariableName})
        </#if>
        <#if tckUtil.isMockTesting()>
        ${tckUtil.mockContextVariable()}["${inputVariableName}"] = ${inputVariableName}
        </#if>
        </#items>
    </#list>
</#macro>

<#macro checkResults testCase>
    <#list testCase.resultNode>
        <#items as result>
        # Check ${result.name}
        <#assign resultInfo = tckUtil.extractResultNodeInfo(testCases, testCase, result) >
        <#assign elementQName = tckUtil.qualifiedName(resultInfo) >
        <#assign expectedValue = tckUtil.toNativeExpression(resultInfo) >
        <#assign argList = tckUtil.drgElementArgumentList(resultInfo) >
        <@addMissingInputs testCase result />
        <#if resultInfo.isDecision()>
           <#if tckUtil.isSingletonDecision()>
        self.checkValues(${expectedValue}, ${tckUtil.singletonDecisionInstance(elementQName)}.apply(${argList}))
           <#else>
        self.checkValues(${expectedValue}, ${tckUtil.defaultConstructor(elementQName)}.apply(${argList}))
           </#if>
        <#elseif resultInfo.isDS() || resultInfo.isBKM()>
        self.checkValues(${expectedValue}, ${elementQName}.apply(${argList}))
        </#if>
        </#items>
    </#list>
</#macro>

<#macro addMissingInputs testCase resultNode>
    <#list tckUtil.missingArguments(testCases, testCase, resultNode) >
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
