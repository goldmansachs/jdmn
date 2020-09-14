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
    <@addTestCases />
}
<#macro addTestCases>
    <#list testCases.testCase>
        <#items as tc>
    @org.junit.Test
    public void testCase${tc.id}() {
        <@initializeInputs tc/>

        <@checkResults tc/>
        <#if tckUtil.isGenerateProto()>

        <@checkProtoResults tc/>
        </#if>
    }

        </#items>
    </#list>
    private void checkValues(Object expected, Object actual) {
        ${tckUtil.assertClassName()}.assertEquals(expected, actual);
    }
</#macro>

<#macro initializeInputs testCase>
        ${tckUtil.annotationSetClassName()} ${tckUtil.annotationSetVariableName()} = ${tckUtil.defaultConstructor(tckUtil.annotationSetClassName())};
        ${tckUtil.eventListenerClassName()} ${tckUtil.eventListenerVariableName()} = ${tckUtil.defaultConstructor(tckUtil.defaultEventListenerClassName())};
        ${tckUtil.externalExecutorClassName()} ${tckUtil.externalExecutorVariableName()} = ${tckUtil.defaultConstructor(tckUtil.defaultExternalExecutorClassName())};
        ${tckUtil.cacheInterfaceName()} ${tckUtil.cacheVariableName()} = ${tckUtil.defaultConstructor(tckUtil.defaultCacheClassName())};
    <#list testCase.inputNode>
        // Initialize input data
        <#items as input>
        <#assign inputInfo = tckUtil.extractInputNodeInfo(testCases, testCase, input) >
        ${tckUtil.toNativeType(inputInfo)} ${tckUtil.inputDataVariableName(inputInfo)} = ${tckUtil.toNativeExpression(inputInfo)};
        <#if tckUtil.isCached(inputInfo)>
        ${tckUtil.cacheVariableName()}.bind("${tckUtil.inputDataVariableName(inputInfo)}", ${tckUtil.inputDataVariableName(inputInfo)});
        </#if>
        </#items>
    </#list>
</#macro>

<#macro checkResults testCase>
    <#list testCase.resultNode>
        <#items as result>
        // Check ${result.name}
        <#assign resultInfo = tckUtil.extractResultNodeInfo(testCases, testCase, result) >
        checkValues(${tckUtil.toNativeExpression(resultInfo)}, ${tckUtil.defaultConstructor(tckUtil.qualifiedName(resultInfo))}.apply(${tckUtil.drgElementArgumentListExtraCache(tckUtil.drgElementArgumentListExtra(tckUtil.drgElementArgumentList(resultInfo)))}));
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
            ${tckUtil.builderVariableName(resultInfo)}.${tckUtil.protoSetter(parameter)}(${variableNameProto});
        }
        <#else>
        ${tckUtil.builderVariableName(resultInfo)}.${tckUtil.protoSetter(parameter)}(${variableNameProto});
        </#if>
        </#list>
        ${tckUtil.qualifiedRequestMessageName(resultInfo)} ${tckUtil.requestVariableName(resultInfo)} = ${tckUtil.builderVariableName(resultInfo)}.build();
        checkValues(${tckUtil.toNativeExpressionProto(resultInfo)}, ${tckUtil.defaultConstructor(tckUtil.qualifiedName(resultInfo))}.apply(${tckUtil.drgElementArgumentListExtraCacheProto(resultInfo)}).${tckUtil.protoGetter(resultInfo)});
        </#items>
    </#list>
</#macro>