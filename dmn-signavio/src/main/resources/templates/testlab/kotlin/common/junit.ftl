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
package ${packageName}
</#if>

import java.util.*
import java.util.stream.Collectors

@javax.annotation.Generated(value = ["junit.ftl", "${testLab.rootDecisionId}"])
<#assign rootOutputParameter = testLab.rootOutputParameter />
class ${testClassName} : ${decisionBaseClass}() {
    <@addDecisionField />

    <@addTestCases />
}
<#macro addDecisionField>
    private val ${testLabUtil.drgElementVariableName(rootOutputParameter)} = ${testLabUtil.qualifiedName(testLab, rootOutputParameter)}()
</#macro>

<#macro addTestCases>
    <#list testLab.testCases>
        <#items as testCase>
    @org.junit.Test
    fun testCase${(testCase?index + 1)?c}() {
        val ${testLabUtil.annotationSetVariableName()} = ${testLabUtil.annotationSetClassName()}()
        <@addApplyPart testCase/>

        <@addAssertPart testCase/>
    <#if testLabUtil.isGenerateProto()>

        <@addApplyProtoPart testCase/>

        <@addAssertProtoPart testCase/>
    </#if>
    }

        </#items>
    </#list>
    private fun checkValues(expected: Any?, actual: Any?) {
        ${testLabUtil.assertClassName()}.assertEquals(expected, actual)
    }
</#macro>

<#macro addApplyPart testCase>
    <#list testCase.inputValues>
        <#items as input>
        <#assign inputParameterDefinition = testLab.inputParameterDefinitions[input?index] />
        val ${testLabUtil.inputDataVariableName(inputParameterDefinition)}: ${testLabUtil.toNativeType(inputParameterDefinition)} = ${testLabUtil.toNativeExpression(testLab, testCase, input?index)}
        </#items>
    </#list>
        val ${testLabUtil.drgElementVariableName(rootOutputParameter)}: ${testLabUtil.drgElementOutputType(rootOutputParameter)} = this.${testLabUtil.drgElementVariableName(rootOutputParameter)}.apply(${testLabUtil.drgElementArgumentList(rootOutputParameter)})
</#macro>

<#macro addAssertPart testCase>
    <#list testCase.expectedValues>
        <#items as expectedValue>
            <#if testLabUtil.isComplex(expectedValue)>
                <#list expectedValue.slots as slot>
                    <#if testLabUtil.hasListType(rootOutputParameter)>
        checkValues(${testLabUtil.toNativeExpression(testLab, slot.value)}, ${testLabUtil.drgElementVariableName(rootOutputParameter)}?.get(${expectedValue?index}).${testLabUtil.drgElementOutputFieldName(testLab, slot?index)})
                    <#else>
        checkValues(${testLabUtil.toNativeExpression(testLab, slot.value)}, ${testLabUtil.drgElementVariableName(rootOutputParameter)}?.${testLabUtil.drgElementOutputFieldName(testLab, slot?index)})
                    </#if>
                </#list>
            <#else>
        checkValues(${testLabUtil.toNativeExpression(testLab, expectedValue)}, ${testLabUtil.drgElementVariableName(rootOutputParameter)})
            </#if>
        </#items>
    </#list>
</#macro>

<#macro addApplyProtoPart testCase>
        // Make proto request
        var builder_: ${testLabUtil.qualifiedRequestMessageName(rootOutputParameter)}.Builder = ${testLabUtil.qualifiedRequestMessageName(rootOutputParameter)}.newBuilder()
    <#list testCase.inputValues as input>
        <#assign inputParameterDefinition = testLab.inputParameterDefinitions[input?index] />
        <#assign variableNameProto>${testLabUtil.inputDataVariableName(inputParameterDefinition)}Proto</#assign>
        val ${variableNameProto}: ${testLabUtil.toNativeTypeProto(inputParameterDefinition)} = ${testLabUtil.toNativeExpressionProto(inputParameterDefinition)};
        <#if testLabUtil.isProtoReference(inputParameterDefinition)>
        if (${variableNameProto} != null) {
            builder_.${testLabUtil.protoSetter(inputParameterDefinition)}(${variableNameProto});
        }
        <#else>
        builder_.${testLabUtil.protoSetter(inputParameterDefinition)}(${variableNameProto});
        </#if>
    </#list>
        val ${testLabUtil.requestVariableName(rootOutputParameter)}: ${testLabUtil.qualifiedRequestMessageName(rootOutputParameter)} = builder_.build()

        // Invoke apply method
        val ${testLabUtil.responseVariableName(rootOutputParameter)}: ${testLabUtil.qualifiedResponseMessageName(rootOutputParameter)} = this.${testLabUtil.drgElementVariableName(rootOutputParameter)}.apply(${testLabUtil.drgElementArgumentListProto(rootOutputParameter)})
        val ${testLabUtil.drgElementVariableNameProto(rootOutputParameter)}: ${testLabUtil.drgElementOutputTypeProto(rootOutputParameter)} = ${testLabUtil.responseVariableName(rootOutputParameter)}.${testLabUtil.protoGetter(rootOutputParameter)}
</#macro>

<#macro addAssertProtoPart testCase>
        // Check results
    <#list testCase.expectedValues>
        <#items as expectedValue>
            <#if testLabUtil.isComplex(expectedValue)>
                <#list expectedValue.slots>
                    <#items as slot>
                    <#if testLabUtil.hasListType(rootOutputParameter)>
        checkValues(${testLabUtil.toNativeExpression(testLab, slot.value)}, ${testLabUtil.drgElementVariableNameProto(rootOutputParameter)}?.get(${expectedValue?index}).${testLabUtil.protoGetter(rootOutputParameter, testLabUtil.drgElementOutputFieldName(testLab, slot?index))})
                    <#else>
        checkValues(${testLabUtil.toNativeExpression(testLab, slot.value)}, ${testLabUtil.drgElementVariableNameProto(rootOutputParameter)}?.${testLabUtil.protoGetter(rootOutputParameter, testLabUtil.drgElementOutputFieldName(testLab, slot?index))})
                    </#if>
                    </#items>
                </#list>
            <#else>
        checkValues(${testLabUtil.toNativeExpressionProto(testLab, expectedValue)}, ${testLabUtil.drgElementVariableNameProto(rootOutputParameter)})
            </#if>
        </#items>
    </#list>
</#macro>