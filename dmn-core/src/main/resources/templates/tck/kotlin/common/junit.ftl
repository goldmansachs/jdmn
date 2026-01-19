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

@javax.annotation.Generated(value = ["junit.ftl", "${testCases.modelName}"])
class ${testClassName} : ${decisionBaseClass}<Object>() {
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
    fun testCase${tc.id}_${rn?index + 1}() {
        <@initializeApplyArguments inputNodeInfoList resultInfo/>

        <@checkResult inputNodeInfoList resultInfo/>
    }

                </#items>
           </#list>
        </#items>
    </#list>
    private fun checkValues(expected: Any?, actual: Any?) {
        ${tckUtil.assertClassName()}.assertEquals(expected, actual)
    }
</#macro>

<#macro initializeApplyArguments inputNodeInfoList resultInfo>
        val ${tckUtil.executionContextVariableName()} = ${tckUtil.executionContextBuilderClassName()}.executionContext().build()
        val ${tckUtil.cacheVariableName()} = ${tckUtil.executionContextVariableName()}.getCache()
    <#list inputNodeInfoList>
        // Initialize arguments
        <#items as inputInfo>
        val ${tckUtil.inputDataVariableName(inputInfo)}: ${tckUtil.toNativeType(inputInfo)} = ${tckUtil.toNativeExpression(inputInfo)}
        <#if tckUtil.isCached(inputInfo)>
        ${tckUtil.cacheVariableName()}.bind("${tckUtil.inputDataVariableName(inputInfo)}", ${tckUtil.inputDataVariableName(inputInfo)})
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
        checkValues(${expectedValue}, ${tckUtil.defaultConstructor(elementQName)}.apply(${parentArgList}))
</#macro>

<#macro addMissingApplyArguments inputNodeInfoList resultInfo>
    <#list tckUtil.missingArguments(inputNodeInfoList, resultInfo)>
        <#items as triplet>
        val ${triplet[1]}: ${triplet[0]} = ${triplet[2]}
        </#items>
    </#list>
</#macro>